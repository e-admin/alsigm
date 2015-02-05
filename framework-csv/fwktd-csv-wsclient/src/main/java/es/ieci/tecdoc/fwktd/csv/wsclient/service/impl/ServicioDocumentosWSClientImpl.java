package es.ieci.tecdoc.fwktd.csv.wsclient.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos;
import es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm;
import es.ieci.tecdoc.fwktd.csv.ws.service.ServicioDocumentosPortType;
import es.ieci.tecdoc.fwktd.csv.wsclient.helper.DocumentoAdapterHelper;
import es.ieci.tecdoc.fwktd.util.file.FileUtils;

/**
 * Implementación del servicio de gestión de documentos que utiliza el servicio web.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ServicioDocumentosWSClientImpl implements ServicioDocumentos {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ServicioDocumentosWSClientImpl.class);

	/**
	 * Cliente del servicio web de gestión de documentos.
	 */
	private ServicioDocumentosPortType servicioDocumentos = null;

	/**
	 * Constructor.
	 */
	public ServicioDocumentosWSClientImpl() {
		super();
	}

	public ServicioDocumentosPortType getServicioDocumentos() {
		return servicioDocumentos;
	}

	public void setServicioDocumentos(
			ServicioDocumentosPortType servicioDocumentos) {
		this.servicioDocumentos = servicioDocumentos;
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

		return DocumentoAdapterHelper.getInfoDocumentoCSV(getServicioDocumentos().generarCSV(
				DocumentoAdapterHelper.getWSInfoDocumentoCSVForm(infoDocumentoForm)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#getInfoDocumento(java.lang.String)
	 */
	public InfoDocumentoCSV getInfoDocumento(String id) {

		logger.info("Llamada a getInfoDocumento: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		return DocumentoAdapterHelper.getInfoDocumentoCSV(getServicioDocumentos().getInfoDocumento(
				id));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#getInfoDocumentoByCSV(java.lang.String)
	 */
	public InfoDocumentoCSV getInfoDocumentoByCSV(String csv) {

		logger.info("Llamada a getInfoDocumentoByCSV: csv=[{}]", csv);

		Assert.hasText(csv, "'csv' must not be empty");

		return DocumentoAdapterHelper
				.getInfoDocumentoCSV(getServicioDocumentos()
						.getInfoDocumentoByCSV(csv));
	}


	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#getDocumento(java.lang.String)
	 */
	public DocumentoCSV getDocumento(String id) {

		logger.info("Llamada a getDocumento: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		return DocumentoAdapterHelper.getDocumentoCSV(getServicioDocumentos().getDocumento(id));
	}


	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#getDocumentoByCSV(java.lang.String)
	 */
	public DocumentoCSV getDocumentoByCSV(String csv) {

		logger.info("Llamada a getDocumentoByCSV: csv=[{}]", csv);

		Assert.hasText(csv, "'csv' must not be empty");

		return DocumentoAdapterHelper.getDocumentoCSV(getServicioDocumentos().getDocumentoByCSV(csv));
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

		return DocumentoAdapterHelper
				.getInfoDocumentoCSV(getServicioDocumentos()
						.saveInfoDocumento(
								DocumentoAdapterHelper
										.getWSInfoDocumentoCSV(infoDocumento)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioGeneracionCSV#updateInfoDocumento(es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV)
	 */
	public InfoDocumentoCSV updateInfoDocumento(InfoDocumentoCSV infoDocumento) {

		logger.info("Llamada a removeInfoDocumento: infoDocumento=[{}]",
				infoDocumento);

		Assert.notNull(infoDocumento, "'infoDocumento' must not be null");
		Assert.hasText(infoDocumento.getId(), "'infoDocumentoForm.id' must not be empty");
		Assert.hasText(infoDocumento.getCsv(),
				"'infoDocumentoForm.csv' must not be empty");
		Assert.hasText(infoDocumento.getNombre(),
				"'infoDocumentoForm.nombre' must not be empty");
		Assert.hasText(infoDocumento.getTipoMime(),
				"'infoDocumentoForm.tipoMime' must not be empty");
		Assert.hasText(infoDocumento.getCodigoAplicacion(),
				"'infoDocumentoForm.codigoAplicacion' must not be empty");

		return DocumentoAdapterHelper.getInfoDocumentoCSV(getServicioDocumentos()
				.updateInfoDocumento(DocumentoAdapterHelper.getWSInfoDocumentoCSV(infoDocumento)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#deleteInfoDocumento(java.lang.String)
	 */
	public void deleteInfoDocumento(String id) {

		logger.info("Llamada a deleteInfoDocumento: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		getServicioDocumentos().deleteInfoDocumento(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#existeContenidoDocumento(java.lang.String)
	 */
	public boolean existeContenidoDocumento(String id) {

		logger.info("Llamada a existeContenidoDocumento: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		return getServicioDocumentos().existeContenidoDocumento(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos#getContenidoDocumento(java.lang.String)
	 */
	public byte[] getContenidoDocumento(String id) {

		logger.info("Llamada a getContenidoDocumento: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");
		return getServicioDocumentos().getContenidoDocumento(id);
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

		// Obtener el contenido del documento
		byte[] contenido = getServicioDocumentos().getContenidoDocumento(id);
		if (contenido != null) {
			FileUtils.copy(new ByteArrayInputStream(contenido), outputStream);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioGeneracionCSV#revocarDocumento(java.lang.String)
	 */
	public void revocarDocumento(String csv) {

		logger.info("Llamada a revocarDocumento: csv=[{}]", csv);

		Assert.hasText(csv, "'csv' must not be empty");

		getServicioDocumentos().revocarDocumento(csv);
	}

}
