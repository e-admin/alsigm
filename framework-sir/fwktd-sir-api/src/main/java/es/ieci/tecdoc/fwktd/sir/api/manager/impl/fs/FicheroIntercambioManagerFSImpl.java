package es.ieci.tecdoc.fwktd.sir.api.manager.impl.fs;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.sir.api.manager.AnexoManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.CompresionManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.impl.FicheroIntercambioManagerImpl;
import es.ieci.tecdoc.fwktd.sir.api.vo.FicheroVO;
import es.ieci.tecdoc.fwktd.sir.core.exception.SIRException;
import es.ieci.tecdoc.fwktd.sir.core.util.ToStringLoggerHelper;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.util.file.FileUtils;

/**
 * Implementación del manager de envío de ficheros de datos de intercambio
 * en formato SICRES 3.0 generado por la aplicación de registro. Esta
 * implementación utiliza el sistema de ficheros.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class FicheroIntercambioManagerFSImpl extends FicheroIntercambioManagerImpl {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FicheroIntercambioManagerFSImpl.class);

	/**
	 * Gestor de anexos.
	 */
	private AnexoManager anexoManager = null;

	/**
	 * Gestor de compresión de ficheros.
	 */
	private CompresionManager compresorManager = null;

	/**
	 * Directorio temporal para construir el fichero de datos de control
	 */
	private String directorioTemporal = null;

	/**
	 * Directorio de ficheros enviados
	 */
	private String directorioEnviados = null;

	/**
	 * Indica si el contenido de los documentos está incluido en el XML.
	 */
	private boolean attached = true;

	/**
	 * Indica si se está ejecutando una prueba unitaria.
	 */
	private boolean test = false;

	/**
	 * Constructor.
	 */
	public FicheroIntercambioManagerFSImpl() {
		super();
	}

	public AnexoManager getAnexoManager() {
		return anexoManager;
	}

	public void setAnexoManager(AnexoManager anexoManager) {
		this.anexoManager = anexoManager;
	}

	public CompresionManager getCompresorManager() {
		return compresorManager;
	}

	public void setCompresorManager(CompresionManager compresorManager) {
		this.compresorManager = compresorManager;
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

	public boolean isAttached() {
		return attached;
	}

	public void setAttached(boolean attached) {
		this.attached = attached;
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.impl.FicheroIntercambioManagerImpl#enviar(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO)
	 */
	protected void enviar(AsientoRegistralVO asiento) {

		if (logger.isInfoEnabled()){
			logger.info("Llamada a enviar: asiento=[{}]", ToStringLoggerHelper.toStringLogger(asiento));
		}

		File ficheroComprimidoTemporal = null;

		try {

			// Crear el fichero de datos de intercambio comprimido
			if (!isAttached() && CollectionUtils.isNotEmpty(asiento.getAnexos())) {

				// Componer el XML del fichero de intercambio en formato SICRES 3.0
				// con los documentos fuera del XML (detached)
				String xml = getSicresXMLManager().createXMLFicheroIntercambio(asiento, false);
				logger.debug("XML fichero de intercambio: {}", xml);

				// Obtener los ficheros a comprimir
				List<FicheroVO> ficheros = new ArrayList<FicheroVO>();

				// XML del fichero de intercambio
				ficheros.add(new FicheroVO(asiento.getIdentificadorIntercambio() + "_00_0000.xml", xml.getBytes()));

				// Contenido de los anexos
				int secuencia = 1;
				for (AnexoVO anexo : asiento.getAnexos()) {
					ficheros.add(new FicheroVO(
							asiento.getIdentificadorIntercambio() + "_01_"
								+ StringUtils.leftPad(String.valueOf(secuencia++), 4, "0")
								+ "." + FileUtils.getExtension(anexo.getNombreFichero()),
							getAnexoManager().getContenidoAnexo(anexo.getId())));
				}

				ficheroComprimidoTemporal = getCompresorManager().comprimirFicheros(ficheros);

			} else {

				// Componer el XML del fichero de intercambio en formato SICRES 3.0
				// con los documentos incluidos en el XML (attached)
				String xml = getSicresXMLManager().createXMLFicheroIntercambio(asiento, true);
				logger.debug("XML fichero de intercambio: {}", xml);

				ficheroComprimidoTemporal = getCompresorManager().comprimirFichero(new FicheroVO(
						asiento.getIdentificadorIntercambio() + "_00_0000.xml", xml.getBytes()));
			}

			logger.info("Fichero de datos de intercambio creado: [{}]", ficheroComprimidoTemporal.getName());


			// Mover el fichero de datos de intercambio
			File ficheroComprimido = new File(getDirectorioEnviados() + File.separator + ficheroComprimidoTemporal.getName());
			logger.info("Moviendo el fichero de datos de intercambio [{}] a [{}]", ficheroComprimidoTemporal.getName(), ficheroComprimido.getName());

			if (ficheroComprimido.exists()) {
				File ficheroAnterior = new File(ficheroComprimido.getAbsolutePath());
				File ficheroAnteriorRenombrado = new File(ficheroComprimido.getParentFile(), ficheroComprimido.getName() + "." + new Date().getTime());
				logger.info("El fichero de destino ya existe. Se va a renombrar a: [{}]", ficheroAnteriorRenombrado.getName());

				if (!ficheroAnterior.renameTo(ficheroAnteriorRenombrado)) {
					logger.error("No se ha podido renombrar el fichero de datos de intercambio anterior [{}] a [{}]", ficheroAnterior.getName(), ficheroAnteriorRenombrado.getName());
				}
			}

			if (!ficheroComprimidoTemporal.renameTo(ficheroComprimido)) {
				logger.error("No se ha podido mover el fichero de datos de intercambio [{}] a [{}]", ficheroComprimidoTemporal.getName(), ficheroComprimido.getName());
				throw new SIRException("error.sir.moverFicheroDatosIntercambio", new String [] { asiento.getIdentificadorIntercambio() },
						"No se ha podido mover el fichero de datos de intercambio");
			}

			logger.info("Fichero de datos de intercambio [{}] creado correctamente", ficheroComprimido.getName());

			// Si es una prueba unitaria, eliminar el fichero creado
			if (isTest()) {
				ficheroComprimido.delete();
				logger.info("Fichero de datos de intercambio [{}] eliminado (TEST)", ficheroComprimido.getName());
			}

		} finally {

			// Eliminar el fichero temporal si existe
			if ((ficheroComprimidoTemporal != null) && ficheroComprimidoTemporal.isFile()) {
				ficheroComprimidoTemporal.delete();
			}
		}
	}
}
