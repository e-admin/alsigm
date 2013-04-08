package es.ieci.tecdoc.fwktd.audit.api.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService;
import es.ieci.tecdoc.fwktd.audit.core.vo.AppAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.CriteriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginatedList;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginationContext;

public class ServicioAuditoriaMockImpl implements AuditoriaService {

	private Logger logger = LoggerFactory
			.getLogger(ServicioAuditoriaMockImpl.class);

	private TrazaAuditoriaVO traza1 = new TrazaAuditoriaVO();

	public ServicioAuditoriaMockImpl() {
		traza1.setAppId(new Long(1));
		traza1.setEventType(new Long(1));
		traza1.setEventDate(new Date());
		traza1.setUserId("1");
		traza1.setUserName("user name 1");
		traza1.setUserHostName("user host name");
	}

	public void audit(TrazaAuditoriaVO traza) {
		logger.debug("ServicioAuditoriaMockImpl->traza");
		logger
				.warn("ESTAUTILIZANDO LA CLASE MOCK PARA PRUEBAS ServicioAuditoriaMockImpl");

	}

	public Integer countByCriteria(CriteriaVO criteria) {
		logger.debug("ServicioAuditoriaMockImpl->countByCriteria");
		logger
				.warn("ESTA UTILIZANDO LA CLASE MOCK PARA PRUEBAS ServicioAuditoriaMockImpl");
		return 1;
	}

	public List<TrazaAuditoriaVO> findByCriteria(CriteriaVO traza) {
		logger.debug("ServicioAuditoriaMockImpl->findByCriteria");
		logger
				.warn("EST�? UTILIZANDO LA CLASE MOCK PARA PRUEBAS ServicioAuditoriaMockImpl");

		List<TrazaAuditoriaVO> results = new LinkedList<TrazaAuditoriaVO>();
		results.add(traza1);
		return results;
	}

	public TrazaAuditoriaVO getTraza(String idTraza) {

		return traza1;
	}

	public PaginatedList<TrazaAuditoriaVO> findByCriteria(CriteriaVO criteria,
			PaginationContext paginationContext) {

		PaginatedList<TrazaAuditoriaVO> results = new PaginatedList<TrazaAuditoriaVO>(
				paginationContext);
		results.add(traza1);
		return results;
	}

	public List<TrazaAuditoriaVO> findByAppIdEventType(Long appId,
			Long eventType, Date startDate, Date endDate) {
		List<TrazaAuditoriaVO> results = new LinkedList<TrazaAuditoriaVO>();
		results.add(traza1);
		return results;
	}

	public PaginatedList<TrazaAuditoriaVO> findByAppIdEventType(Long appId,
			Long eventType, Date startDate, Date endDate,
			PaginationContext paginationContext) {
		PaginatedList<TrazaAuditoriaVO> results = new PaginatedList<TrazaAuditoriaVO>(
				paginationContext);
		results.add(traza1);
		return results;
	}

	public List<TrazaAuditoriaVO> findByUserIdAppId(String userId, Long appId,
			Date startDate, Date endDate) {
		List<TrazaAuditoriaVO> results = new LinkedList<TrazaAuditoriaVO>();
		results.add(traza1);
		return results;
	}

	public PaginatedList<TrazaAuditoriaVO> findByUserIdAppId(String userId,
			Long appId, Date startDate, Date endDate,
			PaginationContext paginationContext) {
		PaginatedList<TrazaAuditoriaVO> results = new PaginatedList<TrazaAuditoriaVO>(
				paginationContext);
		results.add(traza1);
		return results;
	}

	public List<TrazaAuditoriaVO> findByUserId(String userId, Date startDate,
			Date endDate) {
		List<TrazaAuditoriaVO> results = new LinkedList<TrazaAuditoriaVO>();
		results.add(traza1);
		return results;
	}

	public List<TrazaAuditoriaVO> findByUserObjectId(String userId, Long appId,
			Long eventType, String objectId, Date startDate, Date endDate) {
		List<TrazaAuditoriaVO> results = new LinkedList<TrazaAuditoriaVO>();
		results.add(traza1);
		return results;
	}

	public PaginatedList<TrazaAuditoriaVO> findByUserObjectId(String userId,
			Long appId, Long eventType, String objectId, Date startDate,
			Date endDate, PaginationContext paginationContext) {
		PaginatedList<TrazaAuditoriaVO> results = new PaginatedList<TrazaAuditoriaVO>(
				paginationContext);
		results.add(traza1);
		return results;
	}

	public List<TrazaAuditoriaVO> findByUserObjectField(String userId,
			Long appId, Long eventType, String objectId, String objectField,
			Date startDate, Date endDate) {
		List<TrazaAuditoriaVO> results = new LinkedList<TrazaAuditoriaVO>();
		results.add(traza1);
		return results;
	}

	public PaginatedList<TrazaAuditoriaVO> findByUserObjectField(String userId,
			Long appId, Long eventType, String objectId, String objectField,
			Date startDate, Date endDate, PaginationContext paginationContext) {
		PaginatedList<TrazaAuditoriaVO> results = new PaginatedList<TrazaAuditoriaVO>(
				paginationContext);
		results.add(traza1);
		return results;
	}

	public PaginatedList<TrazaAuditoriaVO> findByUserId(String userId,
			Date startDate, Date endDate, PaginationContext paginationContext) {
		PaginatedList<TrazaAuditoriaVO> results = new PaginatedList<TrazaAuditoriaVO>(
				paginationContext);
		results.add(traza1);
		return results;
	}
	
	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#findByObjectId(java.lang.Long, java.lang.String, java.util.Date, java.util.Date)
	 */
	public List<TrazaAuditoriaVO> findByObjectId(Long appId, String objectId, Date startDate,
			Date endDate) {
		logger.warn("ESTÁ UTILIZANDO LA CLASE MOCK PARA PRUEBAS ServicioAuditoriaMockImpl");
		List<TrazaAuditoriaVO>results= new LinkedList<TrazaAuditoriaVO>();
		results.add(traza1);
		return results;
	}


	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#findByObjectId(java.lang.Long, java.lang.String, java.util.Date, java.util.Date, es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginationContext)
	 */
	public PaginatedList<TrazaAuditoriaVO> findByObjectId(Long appId, String objectId, Date startDate,
			Date endDate, PaginationContext paginationContext) {
		logger.warn("ESTÁ UTILIZANDO LA CLASE MOCK PARA PRUEBAS ServicioAuditoriaMockImpl");
		PaginatedList<TrazaAuditoriaVO> results = new PaginatedList<TrazaAuditoriaVO>(paginationContext);
		results.add(traza1);
		return results;
	}


	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#findLikeObjectId(java.lang.Long, java.lang.String, java.util.Date, java.util.Date)
	 */
	public List<TrazaAuditoriaVO> findLikeObjectId(Long appId, String objectId, Date startDate,
			Date endDate) {
		logger.warn("ESTÁ UTILIZANDO LA CLASE MOCK PARA PRUEBAS ServicioAuditoriaMockImpl");
		List<TrazaAuditoriaVO>results= new LinkedList<TrazaAuditoriaVO>();
		results.add(traza1);
		return results;
	}


	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#findLikeObjectId(java.lang.Long, java.lang.String, java.util.Date, java.util.Date, es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginationContext)
	 */
	public PaginatedList<TrazaAuditoriaVO> findLikeObjectId(Long appId, String objectId, Date startDate,
			Date endDate, PaginationContext paginationContext) {
		logger.warn("ESTÁ UTILIZANDO LA CLASE MOCK PARA PRUEBAS ServicioAuditoriaMockImpl");
		PaginatedList<TrazaAuditoriaVO> results = new PaginatedList<TrazaAuditoriaVO>(paginationContext);
		results.add(traza1);
		return results;
	}


	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#findLikeUserObjectId(java.lang.String, java.lang.Long, java.lang.String, java.util.Date, java.util.Date)
	 */
	public List<TrazaAuditoriaVO> findLikeUserObjectId(String userId, Long appId, String objectId,
			Date startDate, Date endDate) {
		logger.warn("ESTÁ UTILIZANDO LA CLASE MOCK PARA PRUEBAS ServicioAuditoriaMockImpl");
		List<TrazaAuditoriaVO>results= new LinkedList<TrazaAuditoriaVO>();
		results.add(traza1);
		return results;
	}


	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#findLikeUserObjectId(java.lang.String, java.lang.Long, java.lang.String, java.util.Date, java.util.Date, es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginationContext)
	 */
	public PaginatedList<TrazaAuditoriaVO> findLikeUserObjectId(String userId, Long appId,
			String objectId, Date startDate, Date endDate, PaginationContext paginationContext) {
		logger.warn("ESTÁ UTILIZANDO LA CLASE MOCK PARA PRUEBAS ServicioAuditoriaMockImpl");
		PaginatedList<TrazaAuditoriaVO> results = new PaginatedList<TrazaAuditoriaVO>(paginationContext);
		results.add(traza1);
		return results;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#getAppAuditoria(java.lang.String)
	 */
	public AppAuditoriaVO getAppAuditoria(String appDescripion) {
		logger.warn("ESTÁ UTILIZANDO LA CLASE MOCK PARA PRUEBAS ServicioAuditoriaMockImpl");
		AppAuditoriaVO app = new AppAuditoriaVO();
		app.setAppId(new Long(1));
		return app;
	}

}
