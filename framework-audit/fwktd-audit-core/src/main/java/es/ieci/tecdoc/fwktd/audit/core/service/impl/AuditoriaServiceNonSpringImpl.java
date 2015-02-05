package es.ieci.tecdoc.fwktd.audit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService;
import es.ieci.tecdoc.fwktd.audit.core.vo.AppAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.CriteriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginatedList;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginationContext;

/**
 * Implementacion del servicio de auditoria para realización de intregraciones
 * que no usen de manera nativa spring
 * 
 * @author Iecisa
 * 
 */
public class AuditoriaServiceNonSpringImpl implements AuditoriaService {

	/**
	 * constructor implementacion por defecto del a traves del modulo api
	 */
	public AuditoriaServiceNonSpringImpl() {
		super();
		new ClassPathXmlApplicationContext(
				"/beans/fwktd-audit-core-applicationContext.xml",
				"/beans/fwktd-audit-api-applicationContext.xml");
	}

	/**
	 * constructor para implementaciones personalizadas
	 * 
	 * @param contextPath
	 */
	public AuditoriaServiceNonSpringImpl(String contextPath) {
		super();
		new ClassPathXmlApplicationContext(
				"fwktd-audit-core/fwktd-audit-core-applicationContext.xml",
				contextPath);
	}

	protected AuditoriaService getAuditoriaService() {
		return (AuditoriaService) FwtkdAuditSpringAppContext
				.getApplicationContext().getBean(
						"fwktd_audit_servicioAuditoria");
	}

	public void audit(TrazaAuditoriaVO traza) {

		getAuditoriaService().audit(traza);

	}

	public Integer countByCriteria(CriteriaVO criteria) {
		return getAuditoriaService().countByCriteria(criteria);
	}

	public List<TrazaAuditoriaVO> findByCriteria(CriteriaVO criteria) {
		return getAuditoriaService().findByCriteria(criteria);
	}

	public PaginatedList<TrazaAuditoriaVO> findByCriteria(CriteriaVO criteria,
			PaginationContext paginationContext) {
		return getAuditoriaService()
				.findByCriteria(criteria, paginationContext);
	}

	public TrazaAuditoriaVO getTraza(String idTraza) {
		return getAuditoriaService().getTraza(idTraza);
	}

	public List<TrazaAuditoriaVO> findByAppIdEventType(Long appId,
			Long eventType, Date startDate, Date endDate) {
		return getAuditoriaService().findByAppIdEventType(appId, eventType,
				startDate, endDate);
	}

	public PaginatedList<TrazaAuditoriaVO> findByAppIdEventType(Long appId,
			Long eventType, Date startDate, Date endDate,
			PaginationContext paginationContext) {
		return getAuditoriaService().findByAppIdEventType(appId, eventType,
				startDate, endDate, paginationContext);
	}

	public List<TrazaAuditoriaVO> findByUserIdAppId(String userId, Long appId,
			Date startDate, Date endDate) {
		return getAuditoriaService().findByUserIdAppId(userId, appId,
				startDate, endDate);
	}

	public PaginatedList<TrazaAuditoriaVO> findByUserIdAppId(String userId,
			Long appId, Date startDate, Date endDate,
			PaginationContext paginationContext) {
		return getAuditoriaService().findByUserIdAppId(userId, appId,
				startDate, endDate, paginationContext);
	}

	public List<TrazaAuditoriaVO> findByUserId(String userId, Date startDate,
			Date endDate) {
		return getAuditoriaService().findByUserId(userId, startDate, endDate);
	}

	public List<TrazaAuditoriaVO> findByUserObjectId(String userId, Long appId,
			Long eventType, String objectId, Date startDate, Date endDate) {
		return getAuditoriaService().findByUserObjectId(userId, appId,
				eventType, objectId, startDate, endDate);
	}

	public PaginatedList<TrazaAuditoriaVO> findByUserObjectId(String userId,
			Long appId, Long eventType, String objectId, Date startDate,
			Date endDate, PaginationContext paginationContext) {
		return getAuditoriaService().findByUserObjectId(userId, appId,
				eventType, objectId, startDate, endDate, paginationContext);
	}

	public List<TrazaAuditoriaVO> findByUserObjectField(String userId,
			Long appId, Long eventType, String objectId, String objectField,
			Date startDate, Date endDate) {
		return getAuditoriaService().findByUserObjectField(userId, appId,
				eventType, objectId, objectField, startDate, endDate);
	}

	public PaginatedList<TrazaAuditoriaVO> findByUserObjectField(String userId,
			Long appId, Long eventType, String objectId, String objectField,
			Date startDate, Date endDate, PaginationContext paginationContext) {
		return getAuditoriaService().findByUserObjectField(userId, appId,
				eventType, objectId, objectField, startDate, endDate,
				paginationContext);
	}

	public PaginatedList<TrazaAuditoriaVO> findByUserId(String userId,
			Date startDate, Date endDate, PaginationContext paginationContext) {

		return getAuditoriaService().findByUserId(userId, startDate, endDate,
				paginationContext);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#findByObjectId(java.lang.Long,
	 *      java.lang.String, java.util.Date, java.util.Date)
	 */
	public List<TrazaAuditoriaVO> findByObjectId(Long appId, String objectId, Date startDate,
			Date endDate) {

		return getAuditoriaService().findByObjectId(appId, objectId, startDate, endDate);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#findByObjectId(java.lang.Long,
	 *      java.lang.String, java.util.Date, java.util.Date,
	 *      es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginationContext)
	 */
	public PaginatedList<TrazaAuditoriaVO> findByObjectId(Long appId, String objectId,
			Date startDate, Date endDate, PaginationContext paginationContext) {

		return getAuditoriaService().findByObjectId(appId, objectId, startDate, endDate,
				paginationContext);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#findLikeObjectId(java.lang.Long,
	 *      java.lang.String, java.util.Date, java.util.Date)
	 */
	public List<TrazaAuditoriaVO> findLikeObjectId(Long appId, String objectId, Date startDate,
			Date endDate) {
		return getAuditoriaService().findLikeObjectId(appId, objectId, startDate, endDate);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#findLikeObjectId(java.lang.Long,
	 *      java.lang.String, java.util.Date, java.util.Date,
	 *      es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginationContext)
	 */
	public PaginatedList<TrazaAuditoriaVO> findLikeObjectId(Long appId, String objectId,
			Date startDate, Date endDate, PaginationContext paginationContext) {
		return getAuditoriaService().findLikeObjectId(appId, objectId, startDate, endDate,
				paginationContext);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#findLikeUserObjectId(java.lang.String,
	 *      java.lang.Long, java.lang.String, java.util.Date, java.util.Date)
	 */
	public List<TrazaAuditoriaVO> findLikeUserObjectId(String userId, Long appId, String objectId,
			Date startDate, Date endDate) {
		return getAuditoriaService().findLikeUserObjectId(userId, appId, objectId, startDate,
				endDate);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#findLikeUserObjectId(java.lang.String,
	 *      java.lang.Long, java.lang.String, java.util.Date, java.util.Date,
	 *      es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginationContext)
	 */
	public PaginatedList<TrazaAuditoriaVO> findLikeUserObjectId(String userId, Long appId,
			String objectId, Date startDate, Date endDate, PaginationContext paginationContext) {
		return getAuditoriaService().findLikeUserObjectId(userId, appId, objectId, startDate,
				endDate, paginationContext);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#getAppAuditoria(java.lang.String)
	 */
	public AppAuditoriaVO getAppAuditoria(String appDescripion) {
		
		return getAuditoriaService().getAppAuditoria(appDescripion);
	}


}
