package es.ieci.tecdoc.fwktd.csv.ws.delegate.impl;

import es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos;
import es.ieci.tecdoc.fwktd.csv.ws.delegate.DocumentosDelegate;
import es.ieci.tecdoc.fwktd.csv.ws.helper.DocumentoAdapterHelper;
import es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSVForm;

/**
 * Implementación del delegate para gestionar documentos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DocumentosDelegateImpl implements DocumentosDelegate {

	/**
	 * Servicio de gestión de documentos.
	 */
	private ServicioDocumentos servicioDocumentos = null;

	/**
	 * Constructor.
	 */
	public DocumentosDelegateImpl() {
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
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.DocumentosDelegate#generarCSV(InfoDocumentoCSVForm)
	 */
	public InfoDocumentoCSV generarCSV(InfoDocumentoCSVForm infoDocumentoForm) {
		return DocumentoAdapterHelper
		.getInfoDocumentoCSV(getServicioDocumentos()
				.generarCSV(
						DocumentoAdapterHelper
								.getCoreInfoDocumentoCSVForm(infoDocumentoForm)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.DocumentosDelegate#getInfoDocumento(String)
	 */
	public InfoDocumentoCSV getInfoDocumento(String id) {
		return DocumentoAdapterHelper
				.getInfoDocumentoCSV(getServicioDocumentos().getInfoDocumento(
						id));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.DocumentosDelegate#getInfoDocumentoByCSV(String)
	 */
	public InfoDocumentoCSV getInfoDocumentoByCSV(String csv) {
		return DocumentoAdapterHelper
				.getInfoDocumentoCSV(getServicioDocumentos()
						.getInfoDocumentoByCSV(csv));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.DocumentosDelegate#getDocumento(String)
	 */
	public DocumentoCSV getDocumento(String id) {
		return DocumentoAdapterHelper.getDocumentoCSV(getServicioDocumentos()
				.getDocumento(id));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.DocumentosDelegate#getDocumentoByCSV(String)
	 */
	public DocumentoCSV getDocumentoByCSV(String csv) {
		return DocumentoAdapterHelper.getDocumentoCSV(getServicioDocumentos()
				.getDocumentoByCSV(csv));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.DocumentosDelegate#saveInfoDocumento(InfoDocumentoCSV)
	 */
	public InfoDocumentoCSV saveInfoDocumento(InfoDocumentoCSV infoDocumento) {
		return DocumentoAdapterHelper
				.getInfoDocumentoCSV(getServicioDocumentos().saveInfoDocumento(
						DocumentoAdapterHelper
								.getCoreInfoDocumentoCSV(infoDocumento)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.DocumentosDelegate#updateInfoDocumento(InfoDocumentoCSV)
	 */
	public InfoDocumentoCSV updateInfoDocumento(InfoDocumentoCSV infoDocumento) {
		return DocumentoAdapterHelper
				.getInfoDocumentoCSV(getServicioDocumentos()
						.updateInfoDocumento(
								DocumentoAdapterHelper
										.getCoreInfoDocumentoCSV(infoDocumento)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.DocumentosDelegate#deleteInfoDocumento(java.lang.String)
	 */
	public void deleteInfoDocumento(String id) {
		getServicioDocumentos().deleteInfoDocumento(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.DocumentosDelegate#existeContenidoDocumento(java.lang.String)
	 */
	public boolean existeContenidoDocumento(String id) {
		return getServicioDocumentos().existeContenidoDocumento(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.DocumentosDelegate#getContenidoDocumento(java.lang.String)
	 */
	public byte[] getContenidoDocumento(String id) {
		return getServicioDocumentos().getContenidoDocumento(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.DocumentosDelegate#revocarDocumento(java.lang.String)
	 */
	public void revocarDocumento(String csv) {
		getServicioDocumentos().revocarDocumento(csv);
	}
}
