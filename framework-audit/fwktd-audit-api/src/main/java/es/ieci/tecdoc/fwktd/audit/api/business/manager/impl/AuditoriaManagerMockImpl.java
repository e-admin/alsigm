package es.ieci.tecdoc.fwktd.audit.api.business.manager.impl;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.audit.api.business.manager.AuditoriaManager;
import es.ieci.tecdoc.fwktd.audit.core.vo.AppAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.CriteriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginatedList;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginationContext;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;

public class AuditoriaManagerMockImpl implements AuditoriaManager {

	public AuditoriaManagerMockImpl(BaseDao<TrazaAuditoriaVO, String> genericDao) {
		
	}

	public Integer countByCriteria(CriteriaVO criteria) {
		return new Integer(1);
	}

	public List<TrazaAuditoriaVO> findByCriteria(CriteriaVO criteria) {
		return new ArrayList<TrazaAuditoriaVO>();
	}
	
	public AuditoriaManagerMockImpl(){}

	public void delete(String arg0) {
		// TODO Plantilla de método auto-generado
		
	}

	public void deleteAll(List<? extends TrazaAuditoriaVO> list) {
		// TODO Plantilla de método auto-generado
		
	}

	public TrazaAuditoriaVO save(TrazaAuditoriaVO arg0) {
		// TODO Plantilla de método auto-generado
		return null;
	}

	public TrazaAuditoriaVO update(TrazaAuditoriaVO arg0) {
		// TODO Plantilla de método auto-generado
		return null;
	}

	public int count() {
		// TODO Plantilla de método auto-generado
		return 0;
	}

	public boolean exists(String arg0) {
		// TODO Plantilla de método auto-generado
		return false;
	}

	public TrazaAuditoriaVO get(String arg0) {
		// TODO Plantilla de método auto-generado
		return null;
	}

	public List<TrazaAuditoriaVO> getAll() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	public List<TrazaAuditoriaVO> getAllDistinct() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	public PaginatedList<TrazaAuditoriaVO> findByCriteria(CriteriaVO criteria,
			PaginationContext paginationContext) {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.audit.api.business.manager.AuditoriaManager#getIdAplicacion(java.lang.String)
	 */
	public AppAuditoriaVO getIdAplicacion(String appDescripion) {
		// TODO Plantilla de m�todo auto-generado
		return null;
	}

}
