package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.ConfiguracionIntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadAdministrativaIntercambioRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO;

public class IbatisConfiguracionIntercambioRegistralDAOImpl implements
		ConfiguracionIntercambioRegistralDAO {
	private static final Logger logger = Logger.getLogger(IbatisConfiguracionIntercambioRegistralDAOImpl.class);

	private static final String GET_UNIDAD_TRAMITACION_BY_CODE_SCR_ORGS = "UnidadTramitacionIntercambioRegistralVO.getUnidadTramitacionByCodeScrOrgs";
	private static final String GET_UNIDAD_TRAMITACION_BY_ID_ORGS = "UnidadTramitacionIntercambioRegistralVO.getUnidadTramitacionByIdOrgs";
	private static final String GET_ENTIDAD_REGISTRAL_BY_ID_OFIC = "EntidadRegistralVO.getEntidadRegistralByIdOfic";

	private static final String GET_UNIDAD_ADMINISTRATIVA_BY_CODE_ENTITY_REG = "UnidadAdministrativaIntercambioRegistralVO.getUnidadAdministrativaByCodeEntityReg";

	private static final String GET_UNIDAD_ADMINISTRATIVA_BY_CODE_ENTITY = "UnidadAdministrativaIntercambioRegistralVO.getUnidadAdministrativaByCodeEntity";

	private static final String GET_UNIDAD_ADMINISTRATIVA_BY_CODE_ENTITY_AND_TRAM_UNIT = "UnidadAdministrativaIntercambioRegistralVO.getUnidadAdministrativaByCodeEntityAndTramunit";

	private static final String GET_UNIDAD_TRAMITACION_BY_ID_OFICINA = "UnidadAdministrativaIntercambioRegistralVO.getUnidadAdministrativaByIdOficina";



	protected SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();


	public UnidadAdministrativaIntercambioRegistralVO getUnidadAdmimistrativaByCodigoEntidadRegistral(
			String codigo) {
		try{
			UnidadAdministrativaIntercambioRegistralVO unidadAdministrativa = (UnidadAdministrativaIntercambioRegistralVO)getSqlMapClientTemplate().queryForObject(GET_UNIDAD_ADMINISTRATIVA_BY_CODE_ENTITY_REG, codigo);
			return unidadAdministrativa;
		} catch (DataAccessException exception) {
			logger.error("Error en la obtención de una unidad administrativa a partir de lso codigos comunes de entidad registral", exception);
			throw new RuntimeException(exception);
		}
	}

	public List<UnidadAdministrativaIntercambioRegistralVO> getUnidadAdministrativaByCodidgoER(
			String codigo) {
		try{
			List<UnidadAdministrativaIntercambioRegistralVO> unidadAdministrativa = (List<UnidadAdministrativaIntercambioRegistralVO>)getSqlMapClientTemplate().queryForList(GET_UNIDAD_ADMINISTRATIVA_BY_CODE_ENTITY, codigo);
			return unidadAdministrativa;
		} catch (DataAccessException exception) {
			logger.error("Error en la obtención de una unidad administrativa a partir de lso codigos comunes de entidad registral", exception);
			throw new RuntimeException(exception);
		}
	}


	public UnidadAdministrativaIntercambioRegistralVO getUnidadAdmimistrativaByCodigoEntidadRegistralYUnidadTramitacion(
			String codigoTramunit, String codigoEntidadRegistral) {
		try{
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("codeTramunit", codigoTramunit);
			params.put("codeEntity", codigoEntidadRegistral);
			UnidadAdministrativaIntercambioRegistralVO unidadAdministrativa = (UnidadAdministrativaIntercambioRegistralVO)getSqlMapClientTemplate().queryForObject(GET_UNIDAD_ADMINISTRATIVA_BY_CODE_ENTITY_AND_TRAM_UNIT, params);
			return unidadAdministrativa;
		} catch (DataAccessException exception) {
			logger.error("Error en la obtención de una unidad administrativa a partir de lso codigos comunes de unidad de tramitacion", exception);

			throw new RuntimeException(exception);
		}
	}

	public EntidadRegistralVO getEntidadRegistralVOByIdScrOfic(String idSrcOfic) {
		try {
			Integer idOfic = Integer.parseInt(idSrcOfic);
			EntidadRegistralVO entidad = null;

			entidad = (EntidadRegistralVO) getSqlMapClientTemplate().queryForObject(GET_ENTIDAD_REGISTRAL_BY_ID_OFIC, idOfic);

			return entidad;
		} catch (DataAccessException exception) {
			logger.error("Error en la obtención de la configuración de una entidad registral", exception);

			throw new RuntimeException(exception);
		}
	}


	public UnidadTramitacionIntercambioRegistralVO getUnidadTramitacionIntercambioRegistralVOByIdScrOrgs(
			String idScrOrgs) {
		try {
			UnidadTramitacionIntercambioRegistralVO unidad=null;

			if(StringUtils.isNotEmpty(idScrOrgs))
			{
				Integer idOrgs = Integer.parseInt(idScrOrgs);

				unidad = (UnidadTramitacionIntercambioRegistralVO) getSqlMapClientTemplate().queryForObject(GET_UNIDAD_TRAMITACION_BY_ID_ORGS, idOrgs);

			}
			return unidad;
		} catch (DataAccessException exception) {
			logger.error("Error en la obtención de la configuración de una unidad de tramitación", exception);

			throw new RuntimeException(exception);
		}
	}



	public UnidadTramitacionIntercambioRegistralVO getUnidadTramitacionIntercambioRegistralVOByCodeScrOrgs(
			String codeScrOrgs) {
		try {


			UnidadTramitacionIntercambioRegistralVO unidad = (UnidadTramitacionIntercambioRegistralVO) getSqlMapClientTemplate().queryForObject(GET_UNIDAD_TRAMITACION_BY_CODE_SCR_ORGS, codeScrOrgs);

			return unidad;
		} catch (DataAccessException exception) {
			logger.error("Error en la obtención de la configuración de una unidad de tramitación", exception);

			throw new RuntimeException(exception);
		}
	}



	public UnidadAdministrativaIntercambioRegistralVO getUnidadAdministrativaByOficina(
			Integer idOficina) {
		try {

			UnidadAdministrativaIntercambioRegistralVO unidad = (UnidadAdministrativaIntercambioRegistralVO) getSqlMapClientTemplate().queryForObject(GET_UNIDAD_TRAMITACION_BY_ID_OFICINA, idOficina);

			return unidad;
		} catch (DataAccessException exception) {
			logger.error("Error en la obtención de la unidad administrativa de la oficina", exception);

			throw new RuntimeException(exception);
		}
	}


	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	public final void setSqlMapClient(SqlMapClient aSqlMapClient) {
		this.sqlMapClientTemplate.setSqlMapClient(aSqlMapClient);
	}



}
