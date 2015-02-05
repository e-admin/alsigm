package es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.impl;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

import es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.DocumentoTipoDocumentalSicresDAO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.DocumentoTipoDocumentalSicresVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.IdentificadorDocumentoElectronicoAnexoVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.TipoDocumentalSicresEnumVO;

public class IbatisDocumentoTipoDocumentalSicresDAOImpl implements DocumentoTipoDocumentalSicresDAO{

	protected static final Logger logger = Logger.getLogger(IbatisDocumentoTipoDocumentalSicresDAOImpl.class);
	
	protected SqlMapClientTemplate sqlMapClientTemplate=new SqlMapClientTemplate();
	
	private static final String SAVE_SCR_PAGEREPOSITORY = "DocumentoTipoDocumentalSicresVO.addPageRepository";
	private static final String GET_SCR_PAGEREPOSITORY = "DocumentoTipoDocumentalSicresVO.getPageRepositoryById";
	private static final String UPDATE_SCR_PAGEREPOSITORY="DocumentoTipoDocumentalSicresVO.updatePageRepository";
	
	private static final String TIPO_DOCUMENTAL_SICRES_KEY="tipoDocumentalSicres";
	private static final String ID_DOCUMENTO_KEY="idDocumento";
	
	public DocumentoTipoDocumentalSicresVO save(DocumentoTipoDocumentalSicresVO documento) {
		try{
			getSqlMapClientTemplate().insert(SAVE_SCR_PAGEREPOSITORY,documento);
		} catch (DataAccessException e) {
			logger.error("Error en la insercción de un documento con tipo documental", e);
			throw new RuntimeException(e);
		}
		return documento;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DocumentoTipoDocumentalSicresVO update(IdentificadorDocumentoElectronicoAnexoVO id,TipoDocumentalSicresEnumVO tipoDocumentalSicres){
		try{
			HashMap params=new HashMap();
			params.put(ID_DOCUMENTO_KEY,id);
			params.put(TIPO_DOCUMENTAL_SICRES_KEY,tipoDocumentalSicres);
			
			int resp=getSqlMapClientTemplate().update(UPDATE_SCR_PAGEREPOSITORY,params);
			logger.debug("");
		} catch (DataAccessException e) {
			logger.error("Error en la insercción de un documento con tipo documental", e);
			throw new RuntimeException(e);
		}
		return get(id);
	}
	
	public DocumentoTipoDocumentalSicresVO get(IdentificadorDocumentoElectronicoAnexoVO id) {
		DocumentoTipoDocumentalSicresVO result=null;
		try{ 
			result=(DocumentoTipoDocumentalSicresVO) getSqlMapClientTemplate().queryForObject(GET_SCR_PAGEREPOSITORY, id);
		} catch (DataAccessException e) {
			logger.error("Error en la consulta de un documento con tipo documental", e);
			throw new RuntimeException(e);
		}
        if(result == null){
            logger.warn("El objeto de tipo '{" +DocumentoTipoDocumentalSicresVO.class.getName()+"}' con id '{"+id+"}' no se ha encontrado...");
        } 
        return result;
	}

	public DocumentoTipoDocumentalSicresVO get(Long idLibro,Long idRegistro,Long idPagina) {
		IdentificadorDocumentoElectronicoAnexoVO id=new IdentificadorDocumentoElectronicoAnexoVO();
		id.setIdLibro(idLibro); 
		id.setIdRegistro(idRegistro);
		id.setIdPagina(idPagina);
		
		DocumentoTipoDocumentalSicresVO result=get(id);
        return result;
	}

	public String getIdDocumento(IdentificadorDocumentoElectronicoAnexoVO id) {
		DocumentoTipoDocumentalSicresVO result=get(id);
		return (result==null?null:result.getIdDocumento());
	}
	
	public TipoDocumentalSicresEnumVO getTipoDocumentalSicres(IdentificadorDocumentoElectronicoAnexoVO id) {
		DocumentoTipoDocumentalSicresVO result=get(id);
		return (result==null?null:result.getTipoDocumentalSicres());
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
