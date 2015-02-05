package es.ieci.tecdoc.fwktd.csv.api.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.csv.api.connector.aplicacionExterna.AplicacionExternaConnector;
import es.ieci.tecdoc.fwktd.csv.api.connector.aplicacionExterna.AplicacionExternaConnectorRegistry;
import es.ieci.tecdoc.fwktd.csv.api.connector.generacionCSV.CSVConnector;
import es.ieci.tecdoc.fwktd.csv.api.helper.DocumentoHelper;
import es.ieci.tecdoc.fwktd.csv.api.manager.AplicacionManager;
import es.ieci.tecdoc.fwktd.csv.api.manager.DocumentoManager;
import es.ieci.tecdoc.fwktd.csv.api.manager.TiemposManager;
import es.ieci.tecdoc.fwktd.csv.api.vo.AplicacionVO;
import es.ieci.tecdoc.fwktd.csv.api.vo.DocumentoVO;
import es.ieci.tecdoc.fwktd.csv.api.xml.connectionConfig.ConfiguracionConexion;
import es.ieci.tecdoc.fwktd.csv.core.exception.CSVException;
import es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos;
import es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm;

/**
 * Implementación local del servicio de gestión de documentos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ServicioDocumentosImpl implements ServicioDocumentos {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ServicioDocumentosImpl.class);

	/**
	 * Manager de aplicaciones.
	 */
	private AplicacionManager aplicacionManager = null;

	/**
	 * Manager de documentos.
	 */
	private DocumentoManager documentoManager = null;

	/**
	 * Registro de conectores con aplicaciones externas.
	 */
	private AplicacionExternaConnectorRegistry aplicacionExternaConnectorRegistry = null;

	/**
	 * Manager de tiempos.
	 */
	private TiemposManager tiemposManager = null;

	/**
	 * Generadores de CSV.
	 */
	private CSVConnector csvConnector = null;

	/**
	 * Constructor.
	 */
	public ServicioDocumentosImpl() {
		super();
	}

	public AplicacionManager getAplicacionManager() {
		return aplicacionManager;
	}

	public void setAplicacionManager(AplicacionManager aplicacionManager) {
		this.aplicacionManager = aplicacionManager;
	}

	public DocumentoManager getDocumentoManager() {
		return documentoManager;
	}

	public void setDocumentoManager(DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}

	public AplicacionExternaConnectorRegistry getAplicacionExternaConnectorRegistry() {
		return aplicacionExternaConnectorRegistry;
	}

	public void setAplicacionExternaConnectorRegistry(
			AplicacionExternaConnectorRegistry aplicacionExternaConnectorRegistry) {
		this.aplicacionExternaConnectorRegistry = aplicacionExternaConnectorRegistry;
	}

	public TiemposManager getTiemposManager() {
		return tiemposManager;
	}

	public void setTiemposManager(TiemposManager tiemposManager) {
		this.tiemposManager = tiemposManager;
	}

	public CSVConnector getCsvConnector() {
		return csvConnector;
	}

	public void setCsvConnector(CSVConnector csvConnector) {
		this.csvConnector = csvConnector;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioGeneracionCSV#generarCSV(es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm)
	 */
	public InfoDocumentoCSV generarCSV(InfoDocumentoCSVForm infoDocumentoForm) {

		logger.info("Llamada a generarCSV: infoDocumentoForm=[{}]",
				infoDocumentoForm);

		Assert.notNull(infoDocumentoForm,
				"'infoDocumentoForm' must not be null");
		Assert.hasText(infoDocumentoForm.getNombre(),
				"'infoDocumentoForm.nombre' must not be empty");
		Assert.hasText(infoDocumentoForm.getTipoMime(),
				"'infoDocumentoForm.tipoMime' must not be empty");
		Assert.hasText(infoDocumentoForm.getCodigoAplicacion(),
				"'infoDocumentoForm.codigoAplicacion' must not be empty");
		Assert.notNull(getCsvConnector(),
			"'csvConnector' must be configured");

		// Generar el CSV
		String csv = getCsvConnector().generarCSV(infoDocumentoForm);

		// Fecha actual
		Date fechaActual = getTiemposManager().getFechaActual();

		// Componer la información del documento
		DocumentoVO documento = new DocumentoVO();
		documento.setNombre(infoDocumentoForm.getNombre());
		documento.setDescripcion(DocumentoHelper.getDescripcion(infoDocumentoForm));
		documento.setTipoMIME(infoDocumentoForm.getTipoMime());
		documento.setFechaCreacion(infoDocumentoForm.getFechaCreacion());
		documento.setFechaCaducidad(infoDocumentoForm.getFechaCaducidad());
		documento.setCsv(csv);
		documento.setFechaCSV(fechaActual);
		documento.setDisponible(infoDocumentoForm.isDisponible());

		// Añadir la información de la aplicación
		AplicacionVO aplicacion = getAplicacionManager().getAplicacionByCodigo(infoDocumentoForm.getCodigoAplicacion());
		if (aplicacion != null) {
			documento.setAplicacion(aplicacion);
		} else {
			throw new CSVException(
					"error.csv.application.codeNotFound",
					new String[] { infoDocumentoForm.getCodigoAplicacion() },
					"No existe ninguna aplicación con el código: "
							+ infoDocumentoForm.getCodigoAplicacion());
		}

		//Guardar la información del documento
		documento = getDocumentoManager().save(documento);

		return DocumentoHelper.getInfoDocumentoCSV(documento);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#getInfoDocumento(java.lang.String)
	 */
	public InfoDocumentoCSV getInfoDocumento(String id) {

		logger.info("Llamada a getInfoDocumento: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		// Obtener la información del documento
		DocumentoVO documento = getDocumentoManager().get(id);

		// Rellenar el objeto a devolver
		InfoDocumentoCSV infoDocumentoCSV = DocumentoHelper.getInfoDocumentoCSV(documento);
		if (infoDocumentoCSV == null) {
			logger.info("El documento con identificador [{}] no existe", id);
		}

		return infoDocumentoCSV;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#getInfoDocumentoByCSV(java.lang.String)
	 */
	public InfoDocumentoCSV getInfoDocumentoByCSV(String csv) {

		logger.info("Llamada a getInfoDocumentoByCSV: csv=[{}]", csv);

		Assert.hasText(csv, "'csv' must not be empty");

		// Obtener la información del documento
		DocumentoVO documento = getDocumentoManager().getDocumentoByCSV(csv);

		// Rellenar el objeto a devolver
		InfoDocumentoCSV infoDocumentoCSV = DocumentoHelper.getInfoDocumentoCSV(documento);
		if (infoDocumentoCSV == null) {
			logger.info("El documento con CSV [{}] no existe", csv);
		}

		return infoDocumentoCSV;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#getDocumento(java.lang.String)
	 */
	public DocumentoCSV getDocumento(String id) {

		logger.info("Llamada a getDocumento: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		DocumentoCSV documentoCSV = null;

		// Obtener la información del documento
		DocumentoVO documentoVO = getDocumentoManager().get(id);
		if (documentoVO != null) {
			documentoCSV = getDocumentoCSV(documentoVO);
		} else {
			logger.info("El documento con identificador [{}] no existe", id);
		}

		return documentoCSV;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#getDocumentoByCSV(java.lang.String)
	 */
	public DocumentoCSV getDocumentoByCSV(String csv) {

		logger.info("Llamada a getDocumento: csv=[{}]", csv);

		Assert.hasText(csv, "'csv' must not be empty");

		DocumentoCSV documentoCSV = null;

		// Obtener la información del documento
		DocumentoVO documentoVO = getDocumentoManager().getDocumentoByCSV(csv);
		if (documentoVO != null) {
			documentoCSV = getDocumentoCSV(documentoVO);
		} else {
			logger.info("El documento con CSV [{}] no existe", csv);
		}

		return documentoCSV;
	}

	protected DocumentoCSV getDocumentoCSV(DocumentoVO documento) {

		// Transformar la información del documento
		DocumentoCSV documentoCSV = DocumentoHelper.getDocumentoCSV(documento);

		// Obtener la configuración de conexión a la aplicación externa asociada al documento
		ConfiguracionConexion configuracion = null;

        try {

    		// Parsear la información de conexión de la aplicación
        	configuracion = ConfiguracionConexion.parseText(documento.getAplicacion().getInfoConexion());

        } catch (Throwable t) {
            logger.error("Error al parsear el XML de configuración de la conexión con la aplicación [" + documentoCSV.getCodigoAplicacion() + "]", t);
			throw new CSVException(
					"error.csv.application.unmarshalConnectionConfig",
					new String[] { documentoCSV.getCodigoAplicacion() },
					"Error al parsear el XML de configuración de la conexión con la aplicación: "
							+ documentoCSV.getCodigoAplicacion());
        }

		AplicacionExternaConnector conector = getAplicacionExternaConnectorRegistry().getConector(configuracion.getConector());
		if (conector == null) {
			logger.warn("No se ha encontrado el conector [{}]",
					configuracion.getConector());
			throw new CSVException(
					"error.csv.application.connectorNotFound",
					new String[] { configuracion.getConector() },
					"No se ha encontrado el conector con aplicaciones externas con identificador: "
							+ configuracion.getConector());
		}

		// Obtener el contenido del documento
		documentoCSV.setContenido(conector.getContenidoDocumento(documento.getCsv(), configuracion.getParametros()));

		return documentoCSV;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#saveInfoDocumento(es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV)
	 */
	public InfoDocumentoCSV saveInfoDocumento(InfoDocumentoCSV infoDocumento) {

		logger.info("Llamada a saveInfoDocumento: infoDocumento=[{}]",
				infoDocumento);

		Assert.notNull(infoDocumento, "'infoDocumento' must not be null");
		Assert.hasText(infoDocumento.getCsv(),
				"'infoDocumentoForm.csv' must not be empty");
		Assert.hasText(infoDocumento.getNombre(),
				"'infoDocumentoForm.nombre' must not be empty");
		Assert.hasText(infoDocumento.getTipoMime(),
				"'infoDocumentoForm.tipoMime' must not be empty");
		Assert.hasText(infoDocumento.getCodigoAplicacion(),
				"'infoDocumentoForm.codigoAplicacion' must not be empty");

		// Obtener la información del documento
		DocumentoVO documento = getDocumentoManager().save(DocumentoHelper.getDocumentoVO(infoDocumento, getAplicacionManager(), false));

		// Rellenar el objeto a devolver
		return DocumentoHelper.getInfoDocumentoCSV(documento);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#updateInfoDocumento(es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV)
	 */
	public InfoDocumentoCSV updateInfoDocumento(InfoDocumentoCSV infoDocumento) {

		logger.info("Llamada a updateInfoDocumento: infoDocumento=[{}]",
				infoDocumento);

		Assert.notNull(infoDocumento, "'infoDocumento' must not be null");
		Assert.hasText(infoDocumento.getId(), "'infoDocumentoForm.id' must not be empty");
		Assert.hasText(infoDocumento.getCsv(), "'infoDocumentoForm.csv' must not be empty");
		Assert.hasText(infoDocumento.getNombre(), "'infoDocumentoForm.nombre' must not be empty");
		Assert.hasText(infoDocumento.getTipoMime(), "'infoDocumentoForm.tipoMime' must not be empty");
		Assert.hasText(infoDocumento.getCodigoAplicacion(), "'infoDocumentoForm.codigoAplicacion' must not be empty");

		// Comprobar la existencia del documento
		if (!getDocumentoManager().exists(infoDocumento.getId())) {
			logger.warn("El documento con identificador [{}] no existe", infoDocumento.getId());
			throw new CSVException(
					"error.csv.document.idNotFound",
					new String[] { infoDocumento.getId() },
					"No existe ningún documento con el identificador: "
							+ infoDocumento.getId());
		}

		// Actualizar la información del documento
		DocumentoVO documento = getDocumentoManager().update(
				DocumentoHelper.getDocumentoVO(infoDocumento,
						getAplicacionManager(), true));

		// Rellenar el objeto a devolver
		return DocumentoHelper.getInfoDocumentoCSV(documento);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#deleteInfoDocumento(java.lang.String)
	 */
	public void deleteInfoDocumento(String id) {

		logger.info("Llamada a deleteInfoDocumento: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		// Eliminar la información del documento
		getDocumentoManager().delete(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#existeContenidoDocumento(java.lang.String)
	 */
	public boolean existeContenidoDocumento(String id) {

		logger.info("Llamada a existeContenidoDocumento: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		// Obtener la información del documento
		DocumentoVO documento = getDocumentoManager().get(id);
		if (documento == null) {
			logger.warn("El documento con identificador [{}] no existe", id);
			throw new CSVException(
					"error.csv.document.idNotFound", new String[] { id },
					"No existe ningún documento con el identificador: " + id);
		}

		// Obtener la configuración de conexión a la aplicación externa asociada al documento
		ConfiguracionConexion configuracion = getDocumentoManager().getConfiguracionConexion(documento);
		AplicacionExternaConnector conector = getAplicacionExternaConnectorRegistry().getConector(configuracion.getConector());
		if (conector == null) {
			logger.warn("No se ha encontrado el conector [{}]",
					configuracion.getConector());
			throw new CSVException(
					"error.csv.application.connectorNotFound",
					new String[] { configuracion.getConector() },
					"No se ha encontrado el conector con aplicaciones externas con identificador: "
							+ configuracion.getConector());
		}

		return conector.existeDocumento(documento.getCsv(), configuracion.getParametros());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#getContenidoDocumento(java.lang.String)
	 */
	public byte[] getContenidoDocumento(String id) {

		logger.info("Llamada a getContenidoDocumento: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		// Obtener la información del documento
		DocumentoVO documento = getDocumentoManager().get(id);
		if (documento == null) {
			logger.warn("El documento con identificador [{}] no existe", id);
			throw new CSVException(
					"error.csv.document.idNotFound", new String[] { id },
					"No existe ningún documento con el identificador: " + id);
		}

		// Obtener la configuración de conexión a la aplicación externa asociada al documento
		ConfiguracionConexion configuracion = getDocumentoManager().getConfiguracionConexion(documento);
		AplicacionExternaConnector conector = getAplicacionExternaConnectorRegistry().getConector(configuracion.getConector());
		if (conector == null) {
			logger.warn("No se ha encontrado el conector [{}]",
					configuracion.getConector());
			throw new CSVException(
					"error.csv.application.connectorNotFound",
					new String[] { configuracion.getConector() },
					"No se ha encontrado el conector con aplicaciones externas con identificador: "
							+ configuracion.getConector());
		}

		return conector.getContenidoDocumento(documento.getCsv(), configuracion.getParametros());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#writeDocumento(java.lang.String, java.io.OutputStream)
	 */
	public void writeDocumento(String id, OutputStream outputStream)
			throws IOException {

		logger.info("Llamada a writeDocumento: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");
		Assert.notNull(outputStream, "'outputStream' must not be null");

		// Obtener la información del documento
		DocumentoVO documento = getDocumentoManager().get(id);
		if (documento == null) {
			logger.warn("El documento con identificador [{}] no existe", id);
			throw new CSVException(
					"error.csv.document.idNotFound", new String[] { id },
					"No existe ningún documento con el identificador: " + id);
		}

		// Obtener la configuración de conexión a la aplicación externa asociada al documento
		ConfiguracionConexion configuracion = getDocumentoManager().getConfiguracionConexion(documento);
		AplicacionExternaConnector conector = getAplicacionExternaConnectorRegistry().getConector(configuracion.getConector());
		if (conector == null) {
			logger.warn("No se ha encontrado el conector [{}]",
					configuracion.getConector());
			throw new CSVException(
					"error.csv.application.connectorNotFound",
					new String[] { configuracion.getConector() },
					"No se ha encontrado el conector con aplicaciones externas con identificador: "
							+ configuracion.getConector());
		}

		conector.writeDocumento(documento.getCsv(), outputStream, configuracion.getParametros());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#revocarDocumento(java.lang.String)
	 */
	public void revocarDocumento(String csv) {

		logger.info("Llamada a revocarDocumento: csv=[{}]", csv);

		Assert.hasText(csv, "'csv' must not be empty");

		// Obtener la información del documento
		DocumentoVO documento = getDocumentoManager().getDocumentoByCSV(csv);
		if (documento != null) {

			logger.info("Se va a revocar la disponibilidad del documento: {}", documento);

			documento.setDisponible(false);
			getDocumentoManager().update(documento);

			logger.info("Documento [{}] revocado", csv);

		} else {
			logger.warn("El documento con CSV [{}] no existe", csv);
			throw new CSVException(
					"error.csv.document.csvNotFound", new String[] { csv },
					"No existe ningún documento con el CSV: " + csv);
		}
	}

}
