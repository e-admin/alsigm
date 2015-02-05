package es.ieci.tecdoc.fwktd.audit.api.business.manager.impl;

import java.util.List;

import es.ieci.tecdoc.fwktd.audit.api.business.dao.TrazaAuditoriaDao;
import es.ieci.tecdoc.fwktd.audit.api.business.manager.AuditoriaManager;
import es.ieci.tecdoc.fwktd.audit.core.vo.AppAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.CriteriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginatedList;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginationContext;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl;

public class AuditoriaManagerImpl extends BaseManagerImpl<TrazaAuditoriaVO, String> implements AuditoriaManager {

	public AuditoriaManagerImpl(BaseDao<TrazaAuditoriaVO, String> genericDao) {
		super(genericDao);
	}

	public Integer countByCriteria(CriteriaVO criteria) {
		return ((TrazaAuditoriaDao) getDao()).countByCriteria(criteria);
	}

	public List<TrazaAuditoriaVO> findByCriteria(CriteriaVO criteria) {
		return ((TrazaAuditoriaDao) getDao()).findByCriteria(criteria);
	}

	public PaginatedList<TrazaAuditoriaVO> findByCriteria(CriteriaVO criteria,
			PaginationContext paginationContext) {
		return ((TrazaAuditoriaDao) getDao()).findByCriteria(criteria, paginationContext);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.audit.api.business.manager.AuditoriaManager#getIdAplicacion(java.lang.String)
	 */
	public AppAuditoriaVO getIdAplicacion(String appDescripion) {		
		return ((TrazaAuditoriaDao) getDao()).getIdAplicacion(appDescripion);
	}

}
