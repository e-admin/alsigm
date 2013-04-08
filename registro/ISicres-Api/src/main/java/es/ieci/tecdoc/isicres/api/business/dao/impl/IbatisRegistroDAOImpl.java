package es.ieci.tecdoc.isicres.api.business.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

import es.ieci.tecdoc.isicres.api.business.dao.RegistroDAO;
import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;

public class IbatisRegistroDAOImpl implements RegistroDAO {
	
	private static final Logger logger = Logger.getLogger(IbatisRegistroDAOImpl.class);
	

	private static final String GET_NUM_REGISTRO_BY_ID="registro.getNumRegistroById";
	protected SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();
	
	public String getNumRegistroById(IdentificadorRegistroVO id) {
			String result=null;
			try{
				Map params = new HashMap();
				params.put("idLibro", id.getIdLibro().toString());
				params.put("idRegistro", Long.parseLong(id.getIdRegistro()));
				result = (String)getSqlMapClientTemplate().queryForObject(GET_NUM_REGISTRO_BY_ID, params);
				
			}
			catch (DataAccessException e) {
				logger.error("Error en la recuperacion de numero de registro para el registro con id:"+id.getIdRegistro()+" y del libro con id:"+id.getIdLibro(), e);
				
				throw new RuntimeException(e);
			}	
			
			return result;
	}


	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}


	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	public SqlMapClient getSqlMapClient() {
		return getSqlMapClientTemplate().getSqlMapClient();
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.getSqlMapClientTemplate().setSqlMapClient(sqlMapClient);
	}

}
