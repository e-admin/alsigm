package ieci.tecdoc.sgm.admsistema.proceso.accionmultientidad;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.ProcedureElement;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tecdoc.sgm.admsistema.proceso.IProcessManager;
import ieci.tecdoc.sgm.admsistema.vo.AccionMultientidadVO;
import ieci.tecdoc.sgm.sesiones.administrador.ws.client.Sesion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 *
 * Clase para ejecutar la acción de importar procedimientos sobre entidades.
 *
 * @author IECISA
 *
 */
public class ImportarProcedimientoAccionEjecucion extends TramitacionAccionEjecucionBase implements IProcessManager {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(ImportarProcedimientoAccionEjecucion.class);

	public static final String ACTION_SUFIX_DIR = "_ImportarProcedimiento";
	public static final String ACTION_PREFIX_LOG_NAME = "IMPORT_PROCECURE_LOGGER_";

	/**
	 * Constructor.
	 */
	public ImportarProcedimientoAccionEjecucion() {
		// En la clase base se establece el currentDate
		super();
	}

	/**
	 * Ejecución de la acción multientidad.
	 *
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.admsistema.proceso.IProcessManager#execute(java.util.Map)
	 */
	public boolean execute(Map options) throws Exception {

		if (logger.isInfoEnabled()) {
			logger.info("Inicio de Accion Multientidad para Importar un Procedimiento en varias Entidades");
		}

		try {
			// Establecer la fecha actual
			options.put("CURRENT_DATE", getCurrentDate());

			// Configurar el Logger para la acción
			configureProcessActionLogger(options, ACTION_SUFIX_DIR, ACTION_PREFIX_LOG_NAME);

			AccionMultientidadVO accionMultientidadVO = (AccionMultientidadVO) options.get(EjecutarAccion.PARAM_ACCION_MULTIENTIDAD_VO);

			// Mensaje informativo en el inicio con el procedimiento y las entidades en las que se va a importar
			generateInitInfoLog(accionMultientidadVO);

			Sesion sesion = (Sesion) options.get(EjecutarAccion.PARAM_SESION_APP_ADMINISTRACION);
			String userName = sesion.getUsuario();

			// Fichero del procedimiento a importar
			File pcdFile = null;
			String pcdFileName = copyFicheroTemporal(accionMultientidadVO);

			// Importación en las entidades seleccionadas
			String[] entidades = accionMultientidadVO.getEntidades();
			for (int i = 0; i < entidades.length; i++) {

				// Entidad en la que se importará el procedimiento
				String entidad = entidades[i];

				ClientContext context = null;
				getProcessActionLogger().info("Importacion del procedimiento en la entidad '" + entidad + "'");

				try {
					// Establecer la entidad para el acceso a BD
					setOrganizationUserInfo(entidad);

					// Crear el contexto de tramitación
					context = createClientContext(userName);

					// Fichero del procedimiento a importar
					pcdFile = new File(pcdFileName);

					// Importar procecedimiento
					String importLog = context.getAPI().getProcedureAPI().importProcedure(ProcedureElement.PCD_CT_PARENT_ROOT, false, pcdFile, false);

					getProcessActionLogger().info("LOG de importacion del procedimiento en la entidad '" + entidad + "': ");
					getProcessActionLogger().info(importLog);

					getProcessActionLogger().info("Importacion realizada correctamente: el procedimiento del fichero '" + accionMultientidadVO.getNombreFicheroTemporal() + "' se ha importado en la entidad '" + entidad + "'.");
				} catch (ISPACInfo iie) {
					getProcessActionLogger().warn("El procedimiento no se ha importado en la entidad '" + entidad + "': " + iie.getExtendedMessage(context.getLocale()), iie);
				} catch (ISPACException ie) {
					getProcessActionLogger().error("Error al importar el procedimiento en la entidad '" + entidad + "': " + ie.getExtendedMessage(context.getLocale()), ie);
					getProcessActionLogger().error(ie.fillInStackTrace());
					if (ie.getMessage().indexOf("TransactionContainer.release") > 1){
						getProcessActionLogger().info("El error puede ser debido a que no se ha declarado, y/o establecido en la aplicacion de Administracion, el DataSource de la BD de 'tramitador' para la entidad '" + entidad + "'.");
					}
					setOk(false);
				} catch (Exception e) {
					getProcessActionLogger().error("Error al importar el procedimiento en la entidad '" + entidad + "': " + e.getMessage(), e);
					getProcessActionLogger().error(e.fillInStackTrace());
					setOk(false);
				} finally {
					if (context != null) {
						context.releaseAllConnections();
					}
				}
			}

			if (isOk()) {
				getProcessActionLogger().info("[FIN] El proceso de importacion del procedimiento en las entidades seleccionadas ha finalizado correctamente");
			} else  {
				getProcessActionLogger().warn("[FIN] El proceso de importacion del procedimiento en las entidades seleccionadas ha finalizado con ERRORES");
			}
		} finally {
			releaseLogger(getProcessActionLogger());
		}

		if (logger.isInfoEnabled()) {
			logger.info("Fin de Accion Multientidad para Importar un Procedimiento en varias Entidades");
		}

		return isOk();
	}

	/**
	 * Generar en el Log del proceso la información con la configuración seleccionada para la acción multientidad a ejecutar.
	 *
	 * @param accionMultientidadVO Configuración seleccionada para la acción multientidad.
	 */
	protected void generateInitInfoLog(AccionMultientidadVO accionMultientidadVO) {

		StringBuffer msgInfo = new StringBuffer();

		// Mensaje informativo en el inicio con el procedimiento y las entidades en las que se va a importar
		msgInfo.append("ImportarProcedimientoAccionEjecucion - Inicio del proceso de Importacion del procedimiento a partir del fichero '");
		msgInfo.append(accionMultientidadVO.getNombreFicheroTemporal());
		msgInfo.append("' almacenado en la ruta temporal de '");
		msgInfo.append(accionMultientidadVO.getFicheroTemporal());
		msgInfo.append("' para la/s entidad/es ");
		String[] entidades = accionMultientidadVO.getEntidades();
		msgInfo.append(entidades[0]);
		for (int i = 1; i < entidades.length; i++) {
			msgInfo.append(", ");
			msgInfo.append(entidades[i]);
		}
		msgInfo.append(".");

		getProcessActionLogger().info(msgInfo.toString());
	}

	/**
	 * Copiar el fichero temporal del procedimiento al directorio del proceso.
	 *
	 * @param accionMultientidadVO Configuración seleccionada para la acción multientidad.
	 * @return Path del fichero del procedimiento en el directorio del proceso.
	 * @throws Exception
	 */
	protected String copyFicheroTemporal(AccionMultientidadVO accionMultientidadVO) {

		String pcdFileName = accionMultientidadBaseDir + File.separator + getCurrentDate() + "_ImportarProcedimiento" + File.separator + accionMultientidadVO.getNombreFicheroTemporal();

		// Fichero temporal con el fichero del procedimiento a importar
		File tmpPcdFile = new File(accionMultientidadVO.getFicheroTemporal());
		File pcdFile = new File(pcdFileName);

		OutputStream pcdOutputStream = null;

		try {
			pcdOutputStream = new FileOutputStream(pcdFile);
			// Copiar el fichero temporal al directorio del proceso
			FileUtils.copy(tmpPcdFile, pcdOutputStream);
		} catch (IOException ioe) {
			getProcessActionLogger().error("Error al copiar el fichero temporal '" + accionMultientidadVO.getFicheroTemporal() + "' con el procedimiento a importar en el directorio del proceso '" + pcdFileName  +"': " + ioe.getMessage(), ioe);
			getProcessActionLogger().error(ioe.fillInStackTrace());
			setOk(false);
		}
		finally {
			if (pcdOutputStream != null) {
				try {
					pcdOutputStream.flush();
					pcdOutputStream.close();
				} catch (Exception e){
				}
			}

			// Borrar el fichero temporal seleccionado en la configuración de la acción
			tmpPcdFile.delete();
		}

		return pcdFileName;
	}
}
