package es.ieci.tecdoc.isicres.api.documento.electronico.business.manager.impl;

import es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.DocumentoTipoDocumentalSicresDAO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.manager.DocumentoTipoDocumentalSicresManager;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.DocumentoTipoDocumentalSicresVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.IdentificadorDocumentoElectronicoAnexoVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.TipoDocumentalSicresEnumVO;

public class DocumentoTipoDocumentalSicresManagerImpl implements DocumentoTipoDocumentalSicresManager {

	protected DocumentoTipoDocumentalSicresDAO documentoTipoDocumentalDAO;

	public DocumentoTipoDocumentalSicresVO save(
			DocumentoTipoDocumentalSicresVO documento) {
		return getDocumentoTipoDocumentalDAO().save(documento);
	}

	public DocumentoTipoDocumentalSicresVO update(
			IdentificadorDocumentoElectronicoAnexoVO id,
			TipoDocumentalSicresEnumVO tipoDocumentalSicres) {
		return getDocumentoTipoDocumentalDAO().update(id,tipoDocumentalSicres);
	}

	public DocumentoTipoDocumentalSicresVO get(
			IdentificadorDocumentoElectronicoAnexoVO id) {
		return getDocumentoTipoDocumentalDAO().get(id);
	}

	public DocumentoTipoDocumentalSicresVO get(Long idLibro, Long idRegistro,
			Long idPagina) {
		return getDocumentoTipoDocumentalDAO().get(idLibro,idRegistro,idPagina);
	}

	public String getIdDocumento(IdentificadorDocumentoElectronicoAnexoVO id) {
		return getDocumentoTipoDocumentalDAO().getIdDocumento(id);
	}

	public TipoDocumentalSicresEnumVO getTipoDocumentalSicres(
			IdentificadorDocumentoElectronicoAnexoVO id) {
		return getDocumentoTipoDocumentalDAO().getTipoDocumentalSicres(id);
	}

	public DocumentoTipoDocumentalSicresDAO getDocumentoTipoDocumentalDAO() {
		return documentoTipoDocumentalDAO;
	}

	public void setDocumentoTipoDocumentalDAO(
			DocumentoTipoDocumentalSicresDAO documentoTipoDocumentalDAO) {
		this.documentoTipoDocumentalDAO = documentoTipoDocumentalDAO;
	}


}
