package es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

import es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.DocumentoElectronicoAnexoDAO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.DocumentoElectronicoAnexoDatosFirmaVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.DocumentoElectronicoAnexoVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.IdentificadorDocumentoElectronicoAnexoVO;


public class IbatisDocumentoElectronicoAnexoDAOImpl	implements DocumentoElectronicoAnexoDAO {
	
	protected static final Logger logger = Logger.getLogger(IbatisDocumentoElectronicoAnexoDAOImpl.class);
	
	protected SqlMapClientTemplate sqlMapClientTemplate=new SqlMapClientTemplate();
	
	private static final String SAVE_SCR_ATTACHMENT = "DocumentoElectronicoAnexoVO.addAttachment";
	private static final String GET_SCR_ATTACHMENT = "DocumentoElectronicoAnexoVO.getAttachmentById";
	private static final String GET_SCR_ATTACHMENT_FIRMADO = "DocumentoElectronicoAnexoVO.getAttachmentFirmadoByIdFirma";
	private static final String GET_SCR_ATTACHMENT_ID_DOCUMENTO_ELECTRONICO_ANEXO = "DocumentoElectronicoAnexoVO.getAttachmentByIdIdDocumentoElectronicoAnexo";
	private static final String GET_SCR_ATTACHMENT_IDLIBRO_IDREGISTRO_DOCUMENTO_ELECTRONICO_ANEXO = "DocumentoElectronicoAnexoVO.getAttachmentByIdLibroIdRegistro";
	private static final String GET_SCR_ATTACHMENT_IDLIBRO_IDREGISTRO_ID_DOCUMENTO_ELECTRONICO_ANEXO = "DocumentoElectronicoAnexoVO.getIdDocumentoElectronicoAnexoByIdLibroIdRegistro";
	
	private static final String SAVE_SCR_ATTACHMENT_SIGN_INFO = "DocumentoElectronicoAnexoVO.addAttachmentSignInfo";
	private static final String GET_SCR_ATTACHMENT_SIGN_INFO_BY_IDATTACHMENT = "DocumentoElectronicoAnexoVO.getAttachmentSignInfoByIdAttachment";
	private static final String GET_SCR_ATTACHMENT_SIGN_INFO_BY_ID = "DocumentoElectronicoAnexoVO.getAttachmentSignInfoById";
	
	private static final String GET_FIRMAS_ID_ATTACHMENT = "DocumentoElectronicoAnexoVO.getFirmasById";
	private static final String GET_ID_DOCUMENTO_FIRMAS_ID_ATTACHMENT = "DocumentoElectronicoAnexoVO.getIdDocumentoElectronicoFirmasById";

	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.DocumentoElectronicoAnexoDAO#save(es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.DocumentoElectronicoAnexoVO)
	 */
	public DocumentoElectronicoAnexoVO save(
			DocumentoElectronicoAnexoVO documento) {
		
		try{
			//insertamos datos de la tabla scr_atthment
			getSqlMapClientTemplate().insert(SAVE_SCR_ATTACHMENT,documento);
			//insertamos dato en la tabla scr_attachment_sign_info
			getSqlMapClientTemplate().insert(SAVE_SCR_ATTACHMENT_SIGN_INFO,documento.getDatosFirma());
		}
		catch (DataAccessException e) {
			logger.error("Error en la insercción de un intercambio registral de entrada", e);
			
			throw new RuntimeException(e);
		}
			
			return documento;
		}
	
	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.DocumentoElectronicoAnexoDAO#get(java.lang.Long)
	 */
	public DocumentoElectronicoAnexoVO get(Long  id){
		DocumentoElectronicoAnexoVO result =(DocumentoElectronicoAnexoVO) getSqlMapClientTemplate().queryForObject(GET_SCR_ATTACHMENT, id);
		
        if(result == null){
            logger.warn("El objeto de tipo '{" +DocumentoElectronicoAnexoVO.class.getName()+"}' con identificador '{"+id+"}' no se ha encontrado...");
        } 
        return result;
    }
	
	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.DocumentoElectronicoAnexoDAO#get(es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.IdentificadorDocumentoElectronicoAnexoVO)
	 */
	public DocumentoElectronicoAnexoVO get(IdentificadorDocumentoElectronicoAnexoVO id) {
		DocumentoElectronicoAnexoVO result =(DocumentoElectronicoAnexoVO) getSqlMapClientTemplate().queryForObject(GET_SCR_ATTACHMENT_ID_DOCUMENTO_ELECTRONICO_ANEXO, id);
		
        if(result == null){
            logger.warn("El objeto de tipo '{" +DocumentoElectronicoAnexoVO.class.getName()+"}' con identificador '{"+id+"}' no se ha encontrado...");
        } 
        return result;
	}
	
	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.DocumentoElectronicoAnexoDAO#getDatosFirmaByIdDocumentoElectronico(java.lang.Long)
	 */
	public DocumentoElectronicoAnexoDatosFirmaVO getDatosFirmaByIdDocumentoElectronico(Long idAttachment) {

		DocumentoElectronicoAnexoDatosFirmaVO result=(DocumentoElectronicoAnexoDatosFirmaVO) getSqlMapClientTemplate().queryForObject(GET_SCR_ATTACHMENT_SIGN_INFO_BY_IDATTACHMENT, idAttachment);
        if(result == null){
            logger.warn("El objeto de tipo '{" +DocumentoElectronicoAnexoDatosFirmaVO.class.getName()+"}' con idAttachment '{"+idAttachment+"}' no se ha encontrado...");
        } 
        return result;
	}
	
	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.DocumentoElectronicoAnexoDAO#getDatosFirmaById(java.lang.Long)
	 */
	public DocumentoElectronicoAnexoDatosFirmaVO getDatosFirmaById(Long id){
		DocumentoElectronicoAnexoDatosFirmaVO result=(DocumentoElectronicoAnexoDatosFirmaVO) getSqlMapClientTemplate().queryForObject(GET_SCR_ATTACHMENT_SIGN_INFO_BY_ID, id);
        if(result == null){
            logger.warn("El objeto de tipo '{" +DocumentoElectronicoAnexoDatosFirmaVO.class.getName()+"}' con id '{"+id+"}' no se ha encontrado...");
        } 
        return result;
	}
	
	
	public List<DocumentoElectronicoAnexoVO> getFirmas(Long id) {
		List<DocumentoElectronicoAnexoVO> result=null;
		result = getSqlMapClientTemplate().queryForList(GET_FIRMAS_ID_ATTACHMENT, id);
		return result;
	}
	
	public List<IdentificadorDocumentoElectronicoAnexoVO> getIdentificadoresFirmas(
			Long id) {
		
		List<IdentificadorDocumentoElectronicoAnexoVO> result=null;
		result = getSqlMapClientTemplate().queryForList(GET_ID_DOCUMENTO_FIRMAS_ID_ATTACHMENT, id);
		return result;
	}
	
	

	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.DocumentoElectronicoAnexoDAO#getDocumentoElectronicoAnexoByRegistro(java.lang.Long, java.lang.Long)
	 */
	public List<DocumentoElectronicoAnexoVO> getDocumentosElectronicoAnexoByRegistro(Long idLibro, Long idRegistro) {
		List<DocumentoElectronicoAnexoVO> result=null;
		Map params=new HashMap<String, Object>();
		params.put("idLibro", idLibro);
		params.put("idRegistro", idRegistro);
		result = getSqlMapClientTemplate().queryForList(GET_SCR_ATTACHMENT_IDLIBRO_IDREGISTRO_DOCUMENTO_ELECTRONICO_ANEXO, params);
		return result;
	}
	
	
	public List<IdentificadorDocumentoElectronicoAnexoVO> getIdentificadoresDocumentoElectronicoAnexoByRegistro(
			Long idLibro, Long idRegistro) {
		List<IdentificadorDocumentoElectronicoAnexoVO> result=null;
		Map params=new HashMap<String, Object>();
		params.put("idLibro", idLibro);
		params.put("idRegistro", idRegistro);
		result = getSqlMapClientTemplate().queryForList(GET_SCR_ATTACHMENT_IDLIBRO_IDREGISTRO_ID_DOCUMENTO_ELECTRONICO_ANEXO, params);
		return result;
	}

	
	public DocumentoElectronicoAnexoVO getDocumentoFirmado(Long idDocumentoFirma) {
		
		DocumentoElectronicoAnexoVO result =(DocumentoElectronicoAnexoVO) getSqlMapClientTemplate().queryForObject(GET_SCR_ATTACHMENT_FIRMADO, idDocumentoFirma);
		
        if(result == null){
            logger.warn("El objeto de tipo '{" +DocumentoElectronicoAnexoVO.class.getName()+"}' al que firma el documento con identificador '{"+idDocumentoFirma+"}' no se ha encontrado...");
        } 
        return result;
	}

	
	
	
	

	public SqlMapClient getSqlMapClient() {
		return getSqlMapClientTemplate().getSqlMapClient();
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.getSqlMapClientTemplate().setSqlMapClient(sqlMapClient);
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	

}
