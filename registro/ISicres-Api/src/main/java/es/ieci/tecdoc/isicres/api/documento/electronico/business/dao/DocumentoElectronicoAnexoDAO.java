package es.ieci.tecdoc.isicres.api.documento.electronico.business.dao;


import java.util.List;

import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.DocumentoElectronicoAnexoDatosFirmaVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.DocumentoElectronicoAnexoVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.IdentificadorDocumentoElectronicoAnexoVO;

public interface DocumentoElectronicoAnexoDAO {
	
	/**
	 * Metodo que almacena el documento electronico anexo 
	 * @return
	 */
	public DocumentoElectronicoAnexoVO save(DocumentoElectronicoAnexoVO documento);
	
	/**
	 * Metodo que recupera un documento electronico anexo a partir de su id
	 * @param id
	 * @return
	 */
	public DocumentoElectronicoAnexoVO get(Long id);
	
	/**
	 * Metodo que recupera un documento electronico anexo a partir de los campos idRegistro idLibro idPagina 
	 * @param id
	 * @return
	 */
	public DocumentoElectronicoAnexoVO get(IdentificadorDocumentoElectronicoAnexoVO id);
	
	/**
	 * Metodo que recupera la informacion de los datos de la firma asociados directamente a un documento.
	 *  
	 * @param idDocumentoElectronicoAnexo
	 * @return
	 */
	public DocumentoElectronicoAnexoDatosFirmaVO getDatosFirmaByIdDocumentoElectronico(Long idDocumentoElectronicoAnexo);
	
	/**
	 * Metodo que recupera la informacion de los datos de la firma asociados directamente a un identificador de firma
	 * @param id
	 * @return
	 */
	public DocumentoElectronicoAnexoDatosFirmaVO getDatosFirmaById(Long id);
	
	/**
	 * Metodo que recupera la informacion de las  firmas asociados al identificador de un documento (excluida informacion de autofirma).
	 * @param id
	 * @return
	 */
	public List<DocumentoElectronicoAnexoVO> getFirmas(Long id);
	
	/**
	 * Metodo que recupera la lista de identificadores de las  firmas asociados al identificador de un documento (excluida informacion de autofirma).
	 * @param id
	 * @return
	 */
	public List<IdentificadorDocumentoElectronicoAnexoVO> getIdentificadoresFirmas(Long id);
	
	/**
	 * Metodo que recupera todos los documentos electronicos asociados a un registro
	 * @param idLibro
	 * @param idRegistro
	 * @return
	 */
	public List <DocumentoElectronicoAnexoVO> getDocumentosElectronicoAnexoByRegistro(Long idLibro, Long idRegistro);
	
	
	/**
	 * Metodo que recupera todos los identificadores de documentos electronicos asociados a un registro
	 * @param idLibro
	 * @param idRegistro
	 * @return
	 */
	public List<IdentificadorDocumentoElectronicoAnexoVO> getIdentificadoresDocumentoElectronicoAnexoByRegistro(Long idLibro, Long idRegistro);
	
	/**
	 * 
	 * Metodo que ado el identificador de un documento, nos devolvera el documento al que firma
	 * @param idDocumentoFirma
	 * @return
	 */
	public DocumentoElectronicoAnexoVO getDocumentoFirmado(Long idDocumentoFirma);
    

}
