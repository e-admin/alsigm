package es.ieci.tecdoc.isicres.admin.business.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

import es.ieci.tecdoc.isicres.admin.business.dao.IntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.admin.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.admin.business.vo.UnidadRegistralVO;

public class IntercambioRegistralDAOIbatisImpl implements IntercambioRegistralDAO {

	private SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();
	private static final Logger logger = Logger.getLogger(IntercambioRegistralDAOIbatisImpl.class);

	public List getEntidadesRegistrales() {
		try {
			List result = getSqlMapClientTemplate().queryForList("EntidadRegistralVO.getEntidadesRegistrales");
			return result;
		} catch (DataAccessException exception) {
			logger.error(exception.getMessage(), exception);
			throw new RuntimeException(exception);
		}
	}

	public EntidadRegistralVO addEntidadRegistral(
			EntidadRegistralVO entidadRegistral) {
		try {
			Integer id = (Integer)getSqlMapClientTemplate().insert("EntidadRegistralVO.addEntidadRegistralVO", entidadRegistral);

			return entidadRegistral;
		} catch (DataAccessException exception) {
			logger.error(exception.getMessage(), exception);
			throw new RuntimeException(exception);
		}
	}

	public UnidadRegistralVO addUnidadRegistral(
			UnidadRegistralVO unidadRegistral) {
		try{
			Integer id = (Integer)getSqlMapClientTemplate().insert("UnidadRegistralVO.addUnidadRegistralVO", unidadRegistral);

			return unidadRegistral;
		} catch (DataAccessException exception) {
			logger.error(exception.getMessage(), exception);
			throw new RuntimeException(exception);
		}
	}

	public void deleteEntidadRegistral(EntidadRegistralVO entidadRegistral) {
		try{
			int numBorrados = getSqlMapClientTemplate().delete("EntidadRegistralVO.deleteEntidadRegistralVO", entidadRegistral);
		} catch (DataAccessException exception) {
			logger.error(exception.getMessage(), exception);
			throw new RuntimeException(exception);
		}

	}

	public void deleteUnidadRegistral(UnidadRegistralVO unidadRegistral) {
		try{
			int numBorrados = getSqlMapClientTemplate().delete("UnidadRegistralVO.deleteUnidadRegistralVO", unidadRegistral);
			//TODO ¿Comprobamos si se borró alguna fila o no?
		} catch (DataAccessException exception) {
			logger.error(exception.getMessage(), exception);
			throw new RuntimeException(exception);
		}

	}


	public EntidadRegistralVO getEntidadRegistral(int id) {
		try {
			EntidadRegistralVO result = (EntidadRegistralVO)getSqlMapClientTemplate().queryForObject("EntidadRegistralVO.getEntidadRegistralVO",new Integer(id));
			return result;
		} catch (DataAccessException exception) {
			logger.error(exception.getMessage(), exception);
			throw new RuntimeException(exception);
		}
	}

	public UnidadRegistralVO getUnidadRegistral(int id) {
		try {
			UnidadRegistralVO result = (UnidadRegistralVO)getSqlMapClientTemplate().queryForObject("UnidadRegistralVO.getUnidadRegistralVO",new Integer(id));

			return result;
		} catch (DataAccessException exception) {
			logger.error(exception.getMessage(), exception);
			throw new RuntimeException(exception);
		}
	}

	public List getUnidadRegistralByIdOrgs(int idOrgs) {
		try {
			List result = getSqlMapClientTemplate().queryForList(
					"UnidadRegistralVO.getUnidadRegistralVOByIdOrgs",
					new Integer(idOrgs));
			return result;
		} catch (DataAccessException exception) {
			logger.error(exception.getMessage(), exception);
			throw new RuntimeException(exception);
		}
	}

	public void updateEntidadRegistral(
			EntidadRegistralVO entidadRegistral) {
		try{
			int num = getSqlMapClientTemplate().update("EntidadRegistralVO.updateEntidadRegistralVO", entidadRegistral);
			//TODO ¿Comprobar si se actualizó alguna fila?
		} catch (DataAccessException exception) {
			logger.error(exception.getMessage(), exception);
			throw new RuntimeException(exception);
		}
	}

	public void updateUnidadRegistral(
			UnidadRegistralVO unidadRegistral) {
		try{
			int num = getSqlMapClientTemplate().update("UnidadRegistralVO.updateUnidadRegistralVO", unidadRegistral);
			//TODO ¿Comprobar si se actualizó alguna fila?
		} catch (DataAccessException exception) {
			logger.error(exception.getMessage(), exception);
			throw new RuntimeException(exception);
		}
	}

	public List findEntidadesRegistrales(EntidadRegistralVO entidadEjemplo) {
		try {
			List result = getSqlMapClientTemplate().queryForList("EntidadRegistralVO.findEntidadesRegistrales",entidadEjemplo);
			return result;
		} catch (DataAccessException exception) {
			logger.error(exception.getMessage(), exception);
			throw new RuntimeException(exception);
		}
	}


	public List findUnidadesRegistrales(UnidadRegistralVO unidadEjemplo) {
		try {
			List result = getSqlMapClientTemplate().queryForList("UnidadRegistralVO.findUnidadesRegistrales",unidadEjemplo);
			return result;
		} catch (DataAccessException exception) {
			logger.error(exception.getMessage(), exception);
			throw new RuntimeException(exception);
		}
	}

	public List findEntitadaRegistralByOficina(int idOfic) {
		try {
			List result = getSqlMapClientTemplate().queryForList("EntidadRegistralVO.findEntitadaRegistralByOficina", new Integer(idOfic));
			return result;
		} catch (DataAccessException exception) {
			logger.error(exception.getMessage(), exception);
			throw new RuntimeException(exception);
		}
	}

	public final void setSqlMapClient(SqlMapClient aSqlMapClient) {
		this.sqlMapClientTemplate.setSqlMapClient(aSqlMapClient);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}





}
