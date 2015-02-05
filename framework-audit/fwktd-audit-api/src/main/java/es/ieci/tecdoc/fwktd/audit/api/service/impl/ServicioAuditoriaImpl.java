package es.ieci.tecdoc.fwktd.audit.api.service.impl;

import java.util.Date;
import java.util.List;

import es.ieci.tecdoc.fwktd.audit.api.business.manager.AuditoriaManager;
import es.ieci.tecdoc.fwktd.audit.api.exception.ExceptionMessageConstans;
import es.ieci.tecdoc.fwktd.audit.api.util.CriteriaHelper;
import es.ieci.tecdoc.fwktd.audit.core.exception.AuditoriaException;
import es.ieci.tecdoc.fwktd.audit.core.exception.handler.AuditoriaExceptionHandler;
import es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService;
import es.ieci.tecdoc.fwktd.audit.core.vo.AppAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.CriteriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginatedList;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginationContext;

public class ServicioAuditoriaImpl implements AuditoriaService {

	protected AuditoriaManager auditoriaManager;
	protected AuditoriaExceptionHandler auditoriaExceptionHandler;

	public AuditoriaManager getAuditoriaManager() {
		return auditoriaManager;
	}

	public void setAuditoriaManager(AuditoriaManager auditoriaManager) {
		this.auditoriaManager = auditoriaManager;
	}

	public AuditoriaExceptionHandler getAuditoriaExceptionHandler() {
		return auditoriaExceptionHandler;
	}

	public void setAuditoriaExceptionHandler(AuditoriaExceptionHandler auditoriaExceptionHandler) {
		this.auditoriaExceptionHandler = auditoriaExceptionHandler;
	}

	public void audit(TrazaAuditoriaVO traza) {

		try {
			auditoriaManager.save(traza);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_SAVE);
		}

	}

	public Integer countByCriteria(CriteriaVO criteria) {
		int count = 0;
		try {
			count = auditoriaManager.countByCriteria(criteria);

		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_COUNT);
		}
		return count;
	}

	public List<TrazaAuditoriaVO> findByCriteria(CriteriaVO criteria) {

		List<TrazaAuditoriaVO> results = null;
		try {
			results = auditoriaManager.findByCriteria(criteria);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);

		}
		return results;
	}

	public TrazaAuditoriaVO getTraza(String idTraza) {

		try {
			return auditoriaManager.get(idTraza);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
			return null;
		}
	}

	public PaginatedList<TrazaAuditoriaVO> findByCriteria(CriteriaVO criteria,
			PaginationContext paginationContext) {

		PaginatedList<TrazaAuditoriaVO> results = null;
		try {
			results = auditoriaManager.findByCriteria(criteria, paginationContext);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);

		}
		return results;
	}

	public List<TrazaAuditoriaVO> findByAppIdEventType(Long appId, Long eventType, Date startDate,
			Date endDate) {
		CriteriaVO criteria = new CriteriaVO();

		criteria = CriteriaHelper.getAppIdEventTypeCriteria(appId, eventType, startDate, endDate);

		List<TrazaAuditoriaVO> results = null;

		try {
			results = auditoriaManager.findByCriteria(criteria);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
		}

		return results;

	}

	public PaginatedList<TrazaAuditoriaVO> findByAppIdEventType(Long appId, Long eventType,
			Date startDate, Date endDate, PaginationContext paginationContext) {
		CriteriaVO criteria = new CriteriaVO();

		criteria = CriteriaHelper.getAppIdEventTypeCriteria(appId, eventType, startDate, endDate);

		PaginatedList<TrazaAuditoriaVO> results = null;

		try {
			results = auditoriaManager.findByCriteria(criteria, paginationContext);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
		}

		return results;
	}

	public List<TrazaAuditoriaVO> findByUserIdAppId(String userId, Long appId, Date startDate,
			Date endDate) {
		CriteriaVO criteria = CriteriaHelper.getUserIdAppIdCriteria(userId, appId, startDate,
				endDate);

		List<TrazaAuditoriaVO> results = null;

		try {
			results = auditoriaManager.findByCriteria(criteria);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
		}
		return results;
	}

	public PaginatedList<TrazaAuditoriaVO> findByUserIdAppId(String userId, Long appId,
			Date startDate, Date endDate, PaginationContext paginationContext) {

		CriteriaVO criteria = CriteriaHelper.getUserIdAppIdCriteria(userId, appId, startDate,
				endDate);

		PaginatedList<TrazaAuditoriaVO> results = null;

		try {
			results = auditoriaManager.findByCriteria(criteria, paginationContext);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
		}
		return results;
	}

	public List<TrazaAuditoriaVO> findByUserId(String userId, Date startDate, Date endDate) {
		CriteriaVO criteria = CriteriaHelper.getUserIdCriteria(userId, startDate, endDate);
		List<TrazaAuditoriaVO> results = null;
		try {
			results = auditoriaManager.findByCriteria(criteria);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
		}
		return results;

	}

	public PaginatedList<TrazaAuditoriaVO> findByUserId(String userId, Date startDate,
			Date endDate, PaginationContext paginationContext) {
		CriteriaVO criteria = CriteriaHelper.getUserIdCriteria(userId, startDate, endDate);
		PaginatedList<TrazaAuditoriaVO> results = null;
		try {
			results = auditoriaManager.findByCriteria(criteria, paginationContext);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
		}
		return results;

	}

	public List<TrazaAuditoriaVO> findByUserObjectId(String userId, Long appId, Long eventType,
			String objectId, Date startDate, Date endDate) {
		CriteriaVO criteria = CriteriaHelper.getUserObjectIdCriteria(userId, appId, eventType,
				objectId, startDate, endDate);

		List<TrazaAuditoriaVO> results = null;
		try {
			results = auditoriaManager.findByCriteria(criteria);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
		}
		return results;
	}

	public PaginatedList<TrazaAuditoriaVO> findByUserObjectId(String userId, Long appId,
			Long eventType, String objectId, Date startDate, Date endDate,
			PaginationContext paginationContext) {
		CriteriaVO criteria = CriteriaHelper.getUserObjectIdCriteria(userId, appId, eventType,
				objectId, startDate, endDate);

		PaginatedList<TrazaAuditoriaVO> results = null;
		try {
			results = auditoriaManager.findByCriteria(criteria, paginationContext);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
		}
		return results;
	}

	public List<TrazaAuditoriaVO> findByUserObjectField(String userId, Long appId, Long eventType,
			String objectId, String objectField, Date startDate, Date endDate) {
		CriteriaVO criteria = CriteriaHelper.getUserObjectFieldCriteria(userId, appId, eventType,
				objectId, objectField, startDate, endDate);
		List<TrazaAuditoriaVO> results = null;
		try {
			results = auditoriaManager.findByCriteria(criteria);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
		}
		return results;
	}

	public PaginatedList<TrazaAuditoriaVO> findByUserObjectField(String userId, Long appId,
			Long eventType, String objectId, String objectField, Date startDate, Date endDate,
			PaginationContext paginationContext) {
		CriteriaVO criteria = CriteriaHelper.getUserObjectFieldCriteria(userId, appId, eventType,
				objectId, objectField, startDate, endDate);
		PaginatedList<TrazaAuditoriaVO> results = null;
		try {
			results = auditoriaManager.findByCriteria(criteria, paginationContext);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
		}
		return results;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#findByObjectId(java.lang.Long,
	 *      java.lang.String, java.util.Date, java.util.Date)
	 */
	public List<TrazaAuditoriaVO> findByObjectId(Long appId, String objectId, Date startDate,
			Date endDate) {
		CriteriaVO criteria = CriteriaHelper.getObjectIdCriteria(appId, objectId, startDate,
				endDate);
		List<TrazaAuditoriaVO> results = null;
		try {
			results = auditoriaManager.findByCriteria(criteria);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
		}
		return results;
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
		CriteriaVO criteria = CriteriaHelper.getObjectIdCriteria(appId, objectId, startDate,
				endDate);
		PaginatedList<TrazaAuditoriaVO> results = null;
		try {
			results = auditoriaManager.findByCriteria(criteria, paginationContext);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
		}
		return results;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#findLikeObjectId(java.lang.Long,
	 *      java.lang.String, java.util.Date, java.util.Date)
	 */
	public List<TrazaAuditoriaVO> findLikeObjectId(Long appId, String objectId, Date startDate,
			Date endDate) {
		CriteriaVO criteria = CriteriaHelper.getLikeObjectIdCriteria(appId, objectId, startDate,
				endDate);
		List<TrazaAuditoriaVO> results = null;
		try {
			results = auditoriaManager.findByCriteria(criteria);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
		}
		return results;
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
		CriteriaVO criteria = CriteriaHelper.getLikeObjectIdCriteria(appId, objectId, startDate,
				endDate);
		PaginatedList<TrazaAuditoriaVO> results = null;
		try {
			results = auditoriaManager.findByCriteria(criteria, paginationContext);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
		}
		return results;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#findLikeUserObjectId(java.lang.String,
	 *      java.lang.Long, java.lang.String, java.util.Date, java.util.Date)
	 */
	public List<TrazaAuditoriaVO> findLikeUserObjectId(String userId, Long appId, String objectId,
			Date startDate, Date endDate) {
		CriteriaVO criteria = CriteriaHelper.getLikeUserObjectIdCriteria(userId, appId, objectId,
				startDate, endDate);

		List<TrazaAuditoriaVO> results = null;
		try {
			results = auditoriaManager.findByCriteria(criteria);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
		}
		return results;
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
		CriteriaVO criteria = CriteriaHelper.getLikeUserObjectIdCriteria(userId, appId, objectId,
				startDate, endDate);

		PaginatedList<TrazaAuditoriaVO> results = null;
		try {
			results = auditoriaManager.findByCriteria(criteria, paginationContext);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_FIND);
		}
		return results;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService#getAppAuditoria(java.lang.String)
	 */
	public AppAuditoriaVO getAppAuditoria(String appDescripion) {
		AppAuditoriaVO app = null;
		try {
			app = auditoriaManager.getIdAplicacion(appDescripion);
		} catch (Exception e) {
			auditoriaExceptionHandler.handleException(e, ExceptionMessageConstans.ERROR_GETAPP);
		}
		if (app == null) {
			auditoriaExceptionHandler.handleException(new AuditoriaException(
					ExceptionMessageConstans.ERROR_APP_NOT_FOUND),
					ExceptionMessageConstans.ERROR_APP_NOT_FOUND);
		}
		return app;
	}

}
