package es.ieci.tecdoc.isicres.api.documento.electronico.business.manager;

import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.DocumentoTipoDocumentalSicresVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.IdentificadorDocumentoElectronicoAnexoVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.TipoDocumentalSicresEnumVO;


public interface DocumentoTipoDocumentalSicresManager {
	/**
	 * Metodo que almacena el documento en la tabla SCR_PAGEREPOSITORY
	 * @param  
	 * @return
	 */
	public DocumentoTipoDocumentalSicresVO save(DocumentoTipoDocumentalSicresVO documento);
	
	/**
	 * Metodo que almacena el documento en la tabla SCR_PAGEREPOSITORY
	 * @param  
	 * @return
	 */
	public DocumentoTipoDocumentalSicresVO update(IdentificadorDocumentoElectronicoAnexoVO id,TipoDocumentalSicresEnumVO tipoDocumentalSicres);
		
	/**
	 * Metodo que recupera un documento a partir su identificador (Campos idLibro,idCarpeta,idPagina e idDocumento) 
	 * @param id, Objeto identificador del documento a recuperar
	 * @return Un VO de tipo DocumentoTipoDocumentalV, con el documento incluyendo el tipo
	 */
	public DocumentoTipoDocumentalSicresVO get(IdentificadorDocumentoElectronicoAnexoVO id);
	
	
	/**
	 * Metodo que recupera un documento a partir su identificador (Campos idLibro,idCarpeta,idPagina e idDocumento) 
	 * @param idLibro, identificador del libro del documento a recuperar
	 * @param idCarpeta, identificador de la carpeta del documento a recuperar
	 * @param idPagina, identificador de la página del documento a recuperar
	 * @param idDocumento, identificador de invesdoc del documento a recuperar 
	 * @return Un VO de tipo DocumentoTipoDocumentalV, con el documento incluyendo el tipo
	 */
	public DocumentoTipoDocumentalSicresVO get(Long idLibro, Long idRegistro, Long idPagina);
	
	/**
	 * 
	 * Metodo Que devuelve el tipo documental de un documento identificado por su id
	 * @param id, Objeto identificador del documento cuyo tipo documental se va a recuperar
	 * @return un VO de tipo TipoDocumentalEnumVO con el tipo documental.
	 */
	public String getIdDocumento(IdentificadorDocumentoElectronicoAnexoVO id);
	
	/**
	 * 
	 * Metodo Que devuelve el tipo documental de un documento identificado por su id
	 * @param id, Objeto identificador del documento cuyo tipo documental se va a recuperar
	 * @return un VO de tipo TipoDocumentalEnumVO con el tipo documental.
	 */
	public TipoDocumentalSicresEnumVO getTipoDocumentalSicres(IdentificadorDocumentoElectronicoAnexoVO id);

}
