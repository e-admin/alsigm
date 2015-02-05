package es.ieci.tecdoc.fwktd.csv.web.delegate.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos;
import es.ieci.tecdoc.fwktd.csv.web.delegate.DocumentoDelegate;
import es.ieci.tecdoc.fwktd.csv.web.helper.DocumentoAdapterHelper;
import es.ieci.tecdoc.fwktd.csv.web.vo.InfoDocumentoVO;

/**
 * Implementación del delegate de documentos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DocumentoDelegateImpl implements DocumentoDelegate {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(DocumentoDelegateImpl.class);

	/**
	 * Servicio de gestión de documentos.
	 */
	private ServicioDocumentos servicioDocumentos = null;

	/**
	 * Constructor.
	 */
	public DocumentoDelegateImpl() {
		super();
	}

	public ServicioDocumentos getServicioDocumentos() {
		return servicioDocumentos;
	}

	public void setServicioDocumentos(ServicioDocumentos servicioDocumentos) {
		this.servicioDocumentos = servicioDocumentos;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.web.delegate.DocumentoDelegate#getInfoDocumento(java.lang.String, java.util.Locale)
	 */
	public InfoDocumentoVO getInfoDocumento(String csv, Locale locale) {

		logger.info("Llamada a getInfoDocumento: csv=[{}], locale=[{}]", csv,
				locale);

		return DocumentoAdapterHelper.getInfoDocumentoVO(
				getServicioDocumentos().getInfoDocumentoByCSV(csv), locale);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.web.delegate.DocumentoDelegate#existeContenidoDocumento(java.lang.String)
	 */
	public boolean existeContenidoDocumento(String id) {

		logger.info("Llamada a existeContenidoDocumento: id=[{}]", id);

		return getServicioDocumentos().existeContenidoDocumento(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.web.delegate.DocumentoDelegate#writeDocumento(java.lang.String, java.io.OutputStream)
	 */
	public void writeDocumento(String id, OutputStream outputStream)
			throws IOException {

		logger.info("Llamada a writeDocumento: id=[{}]", id);

		getServicioDocumentos().writeDocumento(id, outputStream);
	}

}
