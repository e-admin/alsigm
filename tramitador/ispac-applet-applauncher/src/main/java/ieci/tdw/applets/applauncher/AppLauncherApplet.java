package ieci.tdw.applets.applauncher;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Logger;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

/** Applet para lanzar una aplicaci&oacute;n en el cliente. */
public final class AppLauncherApplet extends JApplet {

	private static final Logger LOGGER = Logger.getLogger("es.gob.minetur.sigm"); //$NON-NLS-1$

	private static final String PARAM_DEBUG = "debug"; //$NON-NLS-1$
	private static final String PARAM_CONFIG = "config"; //$NON-NLS-1$
	private static final String PARAM_DOC_URL = "docUrl"; //$NON-NLS-1$

	private static final String APP_PREFIX = "app."; //$NON-NLS-1$

	private static final long serialVersionUID = -6055142468830189892L;

    /** Inicia la ejecuci&oacute;n del Applet. */
	@Override
	public void init() {

		String docUrl = null;

		try {
			// Recoger parametro de configuracion
			final String config = getParameter(PARAM_CONFIG);
			if (Boolean.parseBoolean(config)) {
				LOGGER.info("Modo Configuracion"); //$NON-NLS-1$
				AppConfigurationDialog.showDialog();
				return;
			}

			// Recoger URL del documento
			docUrl = getParameter(PARAM_DOC_URL);
			if ( docUrl == null || docUrl.trim().length() == 0 ) {
				JOptionPane.showMessageDialog(
					this,
					AppLauncherMessages.getString(
						"appLauncherApplet.error.noDocument" //$NON-NLS-1$
					),
					AppLauncherMessages.getString(
						"appLauncherApplet.error.warning.title" //$NON-NLS-1$
					),
					JOptionPane.WARNING_MESSAGE
				);
				return;
			}
			LOGGER.info("URL del documento: " + docUrl); //$NON-NLS-1$

			// Obtener la extension del documento
			final String docExt = getDocExt(docUrl);
			LOGGER.info("Extension del documento: " + docExt); //$NON-NLS-1$

			// Obtener la aplicacion para abrir el documento

			// Creación del fichero de propiedades
			final AppLauncherAppletProperties props = new AppLauncherAppletProperties();
			if (Boolean.parseBoolean(getParameter(PARAM_DEBUG))) {
				final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				final PrintStream ps = new PrintStream(baos, true);
				ps.println();
				ps.println("---- AppLauncherAppletProperties begin ----"); //$NON-NLS-1$
				ps.println(props.listProperties());
				ps.println("---- AppLauncherAppletProperties end ----"); //$NON-NLS-1$
				ps.println();
				LOGGER.info(new String(baos.toByteArray()));
			}

			String appPath = props.getStringProperty(APP_PREFIX + docExt);

			// Si el fichero de propiedades no contenia una entrada para esta extension solicitamos la ruta
			// o si el fichero de propiedades contenia la entrada pero la aplicacion ya no existe
			if (appPath == null || appPath.trim().length() == 0  || !props.checkPropertyPath(APP_PREFIX + docExt)) {
				// Obtenemos la aplicacion predeterminada del registro
				String defaultAppPath = WinRegistryUtil.getAssociatedProgram("."+docExt); //$NON-NLS-1$

				// Pedir la aplicacion para abrir el documento
				final AppChooserDialog appPathDialog = AppChooserDialog.showDialog(docExt, defaultAppPath);
				if (appPathDialog.getResult() == AppChooserDialog.CANCEL) {
					return;
				}

				// Recuperar el path de la aplicacion
				appPath = appPathDialog.getAppPath();

				// Comprobar el path de la aplicacion
				if (AppLauncherAppletProperties.checkAppPath(appPath)) {

					// Anadir aplicacion al fichero de configuracion
					props.putStringProperty(APP_PREFIX + docExt, appPath);
				}
				else {
					JOptionPane.showMessageDialog(
						this,
						AppLauncherMessages.getString(
							"appLauncherApplet.error.invalidApp" //$NON-NLS-1$
						),
						AppLauncherMessages.getString(
							"appLauncherApplet.error.error.title" //$NON-NLS-1$
						),
						JOptionPane.ERROR_MESSAGE
					);
					return;
				}
			}

			// Ejecutar el comando
			LOGGER.info("Comando de ejecucion en linea de comandos. AppPath: " + appPath + ", DocUrl: " + docUrl); //$NON-NLS-1$ //$NON-NLS-2$
			ProcessBuilder processBuilder = new ProcessBuilder(
				"\"" + appPath + "\"", "\"" + docUrl + "\"" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			);
			processBuilder.start();

		}
		catch (final Exception t) {
			JOptionPane.showMessageDialog(
				this,
				AppLauncherMessages.getString(
					"appLauncherApplet.error.exception", //$NON-NLS-1$
					new String [] { t.toString() }
				),
				AppLauncherMessages.getString(
					"appLauncherApplet.error.error.title" //$NON-NLS-1$
				),
				JOptionPane.ERROR_MESSAGE
			);
			LOGGER.severe("Error al lanzar la aplicacion (" + docUrl + "): " + t); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	private static String getDocExt(final String docUrl) {
		if ( docUrl != null && docUrl.trim().length() > 0 ) {
			final int dotIndex = docUrl.lastIndexOf('.');
			if (dotIndex > 0) {
				return docUrl.substring(dotIndex + 1);
			}
		}
		return ""; //$NON-NLS-1$
	}
}
