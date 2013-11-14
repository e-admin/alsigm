package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.TipoTransporteIntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.TipoTransporteIntercambioRegistralVO;

public class TipoTransporteIntercambioRegistralDAOImpl implements
		TipoTransporteIntercambioRegistralDAO {

	private static final String DELETE_SQLMAP = "TipoTransporteIntercambioRegistralVO.delete";
	private static final String GET_TIPO_TRANSPORTE_BY_DESC_SQLMAP = "TipoTransporteIntercambioRegistralVO.getTipoTransporteByDesc";
	private static final String GET_TIPO_TRANSPORTE_BY_CODIGO_SQLMAP = "TipoTransporteIntercambioRegistralVO.getTipoTransporteByCodigo";

	private static final String GET_COUNT_TIPO_TRANSPORTE_BY_CODIGO_SQLMAP = "TipoTransporteIntercambioRegistralVO.getCountTipoTransporteByCodigo";
	private static final String GET_TIPO_TRANSPORTE_BY_CODIGO_AND_ID_SCRTT_SQLMAP = "TipoTransporteIntercambioRegistralVO.getTipoTransporteByCodigoAndIDScrTT";

	private static final String GET_TIPO_TRANSPORTE_BY_DESC_LANGUAGES_SQLMAP = "TipoTransporteIntercambioRegistralVO.getTipoTransporteByDescLanguages";
	private static final String GET_TIPO_TRANSPORTE_BY_TIPO_TRANSPORTE_SICRES_SQLMAP = "TipoTransporteIntercambioRegistralVO.getTipoTransporteByIdTipoTransporteSicres";
	private static final String SAVE_SQLMAP = "TipoTransporteIntercambioRegistralVO.save";
	private static final String UPDATE_BY_ID_TIPO_TRANSPORTE_SQLMAP = "TipoTransporteIntercambioRegistralVO.updateByIdTipoTransporte";


	private SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();
	private static final Logger logger = Logger
			.getLogger(TipoTransporteIntercambioRegistralDAOImpl.class);

	public TipoTransporteIntercambioRegistralVO getTipoTransporteByDesc(
			String descripcion) {
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoTransporteByDesc(String) - start");
		}

		TipoTransporteIntercambioRegistralVO tipoTransporteIntercambioRegistralVO = null;
		@SuppressWarnings("unchecked")
		List<TipoTransporteIntercambioRegistralVO> lista = (List<TipoTransporteIntercambioRegistralVO>) getSqlMapClientTemplate()
				.queryForList(
						GET_TIPO_TRANSPORTE_BY_DESC_SQLMAP,
						descripcion);
		if (CollectionUtils.isNotEmpty(lista)){
			tipoTransporteIntercambioRegistralVO = lista.get(0);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoTransporteByDesc(String) - end");
		}
		return tipoTransporteIntercambioRegistralVO;
	}

	/**
	 * Devuelve un tipo de transporte a partir del codigo SIR
	 *
	 * @param codigo Código SIR
	 *
	 * @return
	 */
	public TipoTransporteIntercambioRegistralVO getTipoTransporteByCodigo(
			String codigo){
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoTransporteByCodigo(String) - start");
		}

		TipoTransporteIntercambioRegistralVO tipoTransporteIntercambioRegistralVO = null;
		List<TipoTransporteIntercambioRegistralVO> lista = (List<TipoTransporteIntercambioRegistralVO>) getSqlMapClientTemplate()
		.queryForList(
				GET_TIPO_TRANSPORTE_BY_CODIGO_SQLMAP,
				codigo);

		if (CollectionUtils.isNotEmpty(lista)){
			tipoTransporteIntercambioRegistralVO = lista.get(0);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoTransporteByCodigo(String) - end");
		}
		return tipoTransporteIntercambioRegistralVO;
	}

	public int getCountTipoTransporteByCodigo(String codigo) {
		if (logger.isDebugEnabled()) {
			logger.debug("getCountTipoTransporteByCodigo(String) - start");
		}

		int result = (Integer) getSqlMapClientTemplate().queryForObject(
				GET_COUNT_TIPO_TRANSPORTE_BY_CODIGO_SQLMAP, codigo);

		if (logger.isDebugEnabled()) {
			logger.debug("getCountTipoTransporteByCodigo(String) - end");
		}
		return result;
	}

	public TipoTransporteIntercambioRegistralVO getTipoTransporteByCodigoAndIDScrTT(
			String codigo, Integer idScrTT) {
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoTransporteByCodigoAndIDScrTT(String) - start");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo", codigo);
		map.put("idScrTT", idScrTT);

		TipoTransporteIntercambioRegistralVO result = (TipoTransporteIntercambioRegistralVO) getSqlMapClientTemplate()
				.queryForObject(GET_TIPO_TRANSPORTE_BY_CODIGO_AND_ID_SCRTT_SQLMAP,
						map);

		if (logger.isDebugEnabled()) {
			logger.debug("getTipoTransporteByCodigoAndIDScrTT(String) - end");
		}
		return result;
	}


	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.TipoTransporteIntercambioRegistralDAO#getTipoTransporteByDescLanguages(java.lang.String)
	 */
	public TipoTransporteIntercambioRegistralVO getTipoTransporteByDescLanguages(
			String descripcion){
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoTransporteByDescLanguages(String) - start");
		}

		TipoTransporteIntercambioRegistralVO tipoTransporteIntercambioRegistralVO = null;
		List<TipoTransporteIntercambioRegistralVO> lista = (List<TipoTransporteIntercambioRegistralVO>)  getSqlMapClientTemplate()
				.queryForObject(
						GET_TIPO_TRANSPORTE_BY_DESC_LANGUAGES_SQLMAP,
						descripcion);

		if (CollectionUtils.isNotEmpty(lista)){
			tipoTransporteIntercambioRegistralVO = lista.get(0);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTipoTransporteByDescLanguages(String) - end");
		}
		return tipoTransporteIntercambioRegistralVO;
	}

	/**
	 * Devuelve un tipo de transporte a partir del identificador de SICRES
	 *
	 * @param codigo Identificador del tipo de transporte en Sicres
	 *
	 * @return
	 */
	public TipoTransporteIntercambioRegistralVO getTipoTransporteByIdSicres(
			Integer idTipoTransporte){
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoTransporteByCodigo(String) - start");
		}

		TipoTransporteIntercambioRegistralVO tipoTransporteIntercambioRegistralVO = (TipoTransporteIntercambioRegistralVO) getSqlMapClientTemplate()
				.queryForObject(
						GET_TIPO_TRANSPORTE_BY_TIPO_TRANSPORTE_SICRES_SQLMAP,
						idTipoTransporte);

		if (logger.isDebugEnabled()) {
			logger.debug("getTipoTransporteByCodigo(String) - end");
		}
		return tipoTransporteIntercambioRegistralVO;
	}

	public void updateByIdTipoTransporte(
			TipoTransporteIntercambioRegistralVO tipoTransporteIntercambioRegistralVO) {
		if (logger.isDebugEnabled()) {
			logger.debug("updateCodigoTipoTransporte(TipoTransporteIntercambioRegistralVO) - start");
		}

		getSqlMapClientTemplate().update(UPDATE_BY_ID_TIPO_TRANSPORTE_SQLMAP,
				tipoTransporteIntercambioRegistralVO);

		if (logger.isDebugEnabled()) {
			logger.debug("updateCodigoTipoTransporte(TipoTransporteIntercambioRegistralVO) - end");
		}
	}

	public TipoTransporteIntercambioRegistralVO save(
			TipoTransporteIntercambioRegistralVO tipoTransporteIntercambioRegistralVO) {
		if (logger.isDebugEnabled()) {
			logger.debug("save(TipoTransporteIntercambioRegistralVO) - start");
		}

		TipoTransporteIntercambioRegistralVO tipoTransporteIntercambioRegistralVOSaved = null;
		Object object = getSqlMapClientTemplate().insert(SAVE_SQLMAP,
				tipoTransporteIntercambioRegistralVO);
		if (object != null) {
			tipoTransporteIntercambioRegistralVOSaved = (TipoTransporteIntercambioRegistralVO) object;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("save(TipoTransporteIntercambioRegistralVO) - end");
		}
		return tipoTransporteIntercambioRegistralVOSaved;
	}

	public void delete(Long id) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete(Integer) - start");
		}

		getSqlMapClientTemplate().delete(DELETE_SQLMAP, id);

		if (logger.isDebugEnabled()) {
			logger.debug("delete(Integer) - end");
		}
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {

		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(
			SqlMapClientTemplate sqlMapClientTemplate) {

		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	public final void setSqlMapClient(SqlMapClient aSqlMapClient) {
		this.sqlMapClientTemplate.setSqlMapClient(aSqlMapClient);
	}



}
