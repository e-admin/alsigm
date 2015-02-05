package es.ieci.tecdoc.fwktd.sir.api.manager.impl.fs;

import java.io.File;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.sir.api.manager.CodificacionFicherosManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.CompresionManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.SicresXMLManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.impl.MensajeManagerImpl;
import es.ieci.tecdoc.fwktd.sir.api.vo.FicheroVO;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;
import es.ieci.tecdoc.fwktd.sir.core.exception.SIRException;
import es.ieci.tecdoc.fwktd.util.file.FileUtils;

/**
 * Implementación del manager de envío del fichero de datos de control en
 * formato SICRES 3.0 generado por la aplicación origen. Esta implementación
 * utiliza el sistema de ficheros.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class MensajeManagerFSImpl extends MensajeManagerImpl {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(MensajeManagerFSImpl.class);

	/**
	 * Gestor de codificación de ficheros.
	 */
	private CodificacionFicherosManager codificacionFicherosManager = null;

	/**
	 * Gestor de compresión de ficheros.
	 */
	private CompresionManager compresorManager = null;

	/**
	 * Gestor de XML de SICRES.
	 */
	private SicresXMLManager sicresXMLManager = null;

	/**
	 * Directorio temporal para construir el fichero de datos de control
	 */
	private String directorioTemporal = null;

	/**
	 * Directorio de ficheros enviados
	 */
	private String directorioEnviados = null;

	/**
	 * Indica si se está ejecutando una prueba unitaria.
	 */
	private boolean test = false;

	/**
	 * Constructor.
	 */
	public MensajeManagerFSImpl() {
		super();
	}

	public CodificacionFicherosManager getCodificacionFicherosManager() {
		return codificacionFicherosManager;
	}

	public void setCodificacionFicherosManager(
			CodificacionFicherosManager codificacionFicherosManager) {
		this.codificacionFicherosManager = codificacionFicherosManager;
	}

	public CompresionManager getCompresorManager() {
		return compresorManager;
	}

	public void setCompresorManager(CompresionManager compresorManager) {
		this.compresorManager = compresorManager;
	}

	public SicresXMLManager getSicresXMLManager() {
		return sicresXMLManager;
	}

	public void setSicresXMLManager(SicresXMLManager sicresXMLManager) {
		this.sicresXMLManager = sicresXMLManager;
	}

	public String getDirectorioTemporal() {
		return directorioTemporal;
	}

	public void setDirectorioTemporal(String directorioTemporal) {
		this.directorioTemporal = directorioTemporal;
	}

	public String getDirectorioEnviados() {
		return directorioEnviados;
	}

	public void setDirectorioEnviados(String directorioEnviados) {
		this.directorioEnviados = directorioEnviados;
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

	/**
	 * Envía el fichero de datos de control al nodo distribuido asociado.
	 *
	 * @param mensaje
	 *            Información de mensaje SICRES 3.0.
	 */
	protected void enviar(MensajeVO mensaje) {

		logger.info("Llamada a enviarMensaje: [{}]", mensaje);

		// Componer el XML del mensaje en formato SICRES 3.0
		String xml = getSicresXMLManager().createXMLMensaje(mensaje);
		logger.debug("XML mensaje: {}", xml);

		// Obtener el código del mensaje
		String codigoMensaje = getCodificacionFicherosManager().getCodificacionMensaje(mensaje);
		logger.info("Código de mensaje [{}]", codigoMensaje);

		File ficheroComprimidoTemporal = null;

		try {

			// Crear el fichero de datos de control
			ficheroComprimidoTemporal = getCompresorManager().comprimirFichero(new FicheroVO(codigoMensaje + ".xml", xml.getBytes()));
			logger.info("Fichero de datos de control creado: [{}]", ficheroComprimidoTemporal.getName());

			// Mover el fichero de datos de control
			File ficheroComprimido = new File(getDirectorioEnviados(), codigoMensaje + "." + FileUtils.getExtension(ficheroComprimidoTemporal));
			logger.info("Moviendo el fichero de datos de control [{}] a [{}]", ficheroComprimidoTemporal.getName(), ficheroComprimido.getName());

			if (ficheroComprimido.exists()) {
				File ficheroAnterior = new File(ficheroComprimido.getAbsolutePath());
				File ficheroAnteriorRenombrado = new File(ficheroComprimido.getParentFile(), ficheroComprimido.getName() + "." + new Date().getTime());
				logger.info("El fichero de destino ya existe. Se va a renombrar a: [{}]", ficheroAnteriorRenombrado.getName());

				if (!ficheroAnterior.renameTo(ficheroAnteriorRenombrado)) {
					logger.error("No se ha podido renombrar el fichero de datos de control anterior [{}] a [{}]", ficheroAnterior.getName(), ficheroAnteriorRenombrado.getName());
				}
			}

			if (!ficheroComprimidoTemporal.renameTo(ficheroComprimido)) {
				logger.error("No se ha podido mover el fichero de datos de control [{}] a [{}]", ficheroComprimidoTemporal.getName(), ficheroComprimido.getName());
				throw new SIRException("error.sir.moverFicheroDatosControl", new String [] { codigoMensaje }, "No se ha podido mover el fichero de datos de control");
			}

			logger.info("Fichero de datos de control [{}] creado correctamente", ficheroComprimido.getName());

			// Si es una prueba unitaria, eliminar el fichero creado
			if (isTest()) {
				ficheroComprimido.delete();
				logger.info("Fichero de datos de control [{}] eliminado (TEST)", ficheroComprimido.getName());
			}

		} finally {

			// Eliminar el fichero temporal si existe
			if ((ficheroComprimidoTemporal != null) && ficheroComprimidoTemporal.isFile()) {
				ficheroComprimidoTemporal.delete();
			}
		}
	}
}
