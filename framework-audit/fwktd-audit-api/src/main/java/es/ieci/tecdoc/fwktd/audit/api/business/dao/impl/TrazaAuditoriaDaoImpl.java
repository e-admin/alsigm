package es.ieci.tecdoc.fwktd.audit.api.business.dao.impl;

import java.util.List;

import es.ieci.tecdoc.fwktd.audit.api.business.dao.TrazaAuditoriaDao;
import es.ieci.tecdoc.fwktd.audit.core.vo.AppAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.CriteriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginatedList;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginationContext;
import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;

public class TrazaAuditoriaDaoImpl extends IbatisGenericDaoImpl<TrazaAuditoriaVO, String> implements
		TrazaAuditoriaDao {

	public TrazaAuditoriaDaoImpl(Class<TrazaAuditoriaVO> aPersistentClass) {
		super(aPersistentClass);
	}

	@SuppressWarnings("unchecked")
	public List<TrazaAuditoriaVO> findByCriteria(CriteriaVO criteria) {
		return getSqlMapClientTemplate().queryForList("TrazaAuditoriaVO.findByCriteria", criteria);
	}

	public Integer countByCriteria(CriteriaVO criteria) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"TrazaAuditoriaVO.countByCriteria", criteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.fwktd.audit.api.business.dao.TrazaAuditoriaDao#findByCriteria
	 * (es.ieci.tecdoc.fwktd.audit.core.vo.seach.CriteriaVO,
	 * es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginationContext)
	 */
	public PaginatedList<TrazaAuditoriaVO> findByCriteria(CriteriaVO criteria,
			PaginationContext paginationContext) {

		PaginatedList<TrazaAuditoriaVO> result = new PaginatedList<TrazaAuditoriaVO>(
				paginationContext);

		// si nunca se ha lanzado la consulta se actualiza el valor del
		// resultado, si reutilizamos el paginationContext no se volvería a
		// ejecutar
		if (!paginationContext.hasTotalCount()) {
			updateTotalCount(criteria, paginationContext);
		}

		// obtenemos los resultados
		List resultList = getSqlMapClientTemplate().queryForList("TrazaAuditoriaVO.findByCriteria",
				criteria, paginationContext.getSkipResults(),
				paginationContext.getMaxResultsByPage());

		// actualizamos los datos del tamaño de la pagina obtenida actualmente
		paginationContext.setPageElementsCount(resultList.size());
		result.setList(resultList);

		return result;
	}

	private void updateTotalCount(CriteriaVO criteria, PaginationContext paginationContext) {
		paginationContext.updateTotalCount(countByCriteria(criteria));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.audit.api.business.dao.TrazaAuditoriaDao#getIdAplicacion(java.lang.String)
	 */
	public AppAuditoriaVO getIdAplicacion(String appDescription) {

		Object object = getSqlMapClientTemplate().queryForObject(
				"AppAuditoriaVO.getAppAuditoriaVOByAppDesc",appDescription);

		AppAuditoriaVO appAuditoriaVO = (AppAuditoriaVO) object;

		return appAuditoriaVO;
	}
}
