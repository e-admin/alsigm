package es.ieci.tecdoc.isicres.api.documento.electronico.business.vo;

import es.ieci.tecdoc.isicres.api.business.vo.BaseIsicresApiVO;

/**
 * Clase que representará a un documento de la tabla SCR_PAGEREPOSITORY, contendrá información adicional sobre su tipo documental
 * @author Iecisa
 */
public class DocumentoTipoDocumentalSicresVO extends BaseIsicresApiVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9140140933201411617L;

	/**
	 * identificador del documento en el repositorio (idLibro,idRegistro,idPagina).
	 */
	private IdentificadorDocumentoElectronicoAnexoVO id;
		
	/**
	 * Campo opcional
	 * Indica el tipo documental del documento: Los valores se almacenan en la tabla SCR_PAGETYPE
		- '0' = Sin tipo.
		- '1' = Documento a compulsar
		- '2' = Firma documento a compulsar
		- '3' = Documento localizador del fichero a compulsar
	 */
	 private TipoDocumentalSicresEnumVO tipoDocumentalSicres;

	 /** identificador del documento correspondiente a la página en InvesDoc */
	 private String idDocumento;
	 
	 
	public IdentificadorDocumentoElectronicoAnexoVO getId() {
		return id;
	}
	public void setId(IdentificadorDocumentoElectronicoAnexoVO id) {
		this.id = id;
	}

	public TipoDocumentalSicresEnumVO getTipoDocumentalSicres() {
		return tipoDocumentalSicres;
	}
	public void setTipoDocumentalSicres(TipoDocumentalSicresEnumVO tipoDocumentalSicres) {
		this.tipoDocumentalSicres = tipoDocumentalSicres;
	} 
	
	public String getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}
}
