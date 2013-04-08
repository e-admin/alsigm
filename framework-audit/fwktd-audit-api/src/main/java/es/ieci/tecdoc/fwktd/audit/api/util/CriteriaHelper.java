package es.ieci.tecdoc.fwktd.audit.api.util;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import es.ieci.tecdoc.fwktd.audit.api.constants.CamposTrazaAuditoriaConstants;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.CriteriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.FilterVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.JdbcTypesEnum;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.OperatorEnum;

/**
 * Clase de utilidad para contruir los criterios de búsqueda
 * 
 * @author IECISA
 * 
 */
public class CriteriaHelper {

	/**
	 * Construye un objeto de tipo CriteriaVO {@link CriteriaVO} para realizar
	 * una búsqueda por appId, eventTypeId y fecha de inicio y fecha de fin
	 * 
	 * @param appId
	 *            Identificador de la aplicación
	 * @param eventType
	 *            Identificador del tipo de evento
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return
	 */
	public static CriteriaVO getAppIdEventTypeCriteria(Long appId,
			Long eventTypeId, Date startDate, Date endDate) {

		CriteriaVO criteria = new CriteriaVO();
		// Filtro appId
		FilterVO filterAppId = new FilterVO();
		filterAppId.setField(CamposTrazaAuditoriaConstants.APP_ID_FIELD);
		filterAppId.setOperator(OperatorEnum.ES_IGUAL);
		filterAppId.setValue(String.valueOf(appId));
		filterAppId.setJdbcType(JdbcTypesEnum.BIG_INT);

		// Filtro evenType
		FilterVO filterEventType = new FilterVO();
		filterEventType
				.setField(CamposTrazaAuditoriaConstants.EVENT_TYPE_FIELD);
		filterEventType.setOperator(OperatorEnum.ES_IGUAL);
		filterEventType.setValue(String.valueOf(eventTypeId));
		filterEventType.setJdbcType(JdbcTypesEnum.BIG_INT);

		// Filtro fecha de inicio
		FilterVO filterStartDate = new FilterVO();
		filterStartDate
				.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterStartDate.setOperator(OperatorEnum.ES_MAYOR_IGUAL);
		filterStartDate.setValue(startDate);
		filterStartDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		// Filtro fecha de fin
		FilterVO filterEndDate = new FilterVO();
		filterEndDate.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterEndDate.setOperator(OperatorEnum.ES_MENOR_IGUAL);
		filterEndDate.setValue(endDate);
		filterEndDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		List<FilterVO> filters = new LinkedList<FilterVO>();
		filters.add(filterAppId);
		filters.add(filterEventType);
		filters.add(filterStartDate);
		filters.add(filterEndDate);

		criteria.setFilters(filters);
		return criteria;
	}

	/**
	 * 
	 * Devuelve un objeto de tipo CriteriaVO {@link CriteriaVO} para realizar
	 * búsquedas por usuario y aplicación
	 * 
	 * @param userId
	 *            Identificador del usuario del que queremos obtener las trazas
	 * @param appId
	 *            Identificador de la aplicación
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return
	 */
	public static CriteriaVO getUserIdEventTypeCriteria(String userId,
			Long appId, Date startDate, Date endDate) {

		CriteriaVO criteria = new CriteriaVO();
		// Filtro appId
		FilterVO filterAppId = new FilterVO();
		filterAppId.setField(CamposTrazaAuditoriaConstants.APP_ID_FIELD);
		filterAppId.setOperator(OperatorEnum.ES_IGUAL);
		filterAppId.setValue(String.valueOf(appId));
		filterAppId.setJdbcType(JdbcTypesEnum.BIG_INT);

		// Filtro userId
		FilterVO filterUserId = new FilterVO();
		filterUserId.setField(CamposTrazaAuditoriaConstants.USER_ID_FIELD);
		filterUserId.setOperator(OperatorEnum.ES_IGUAL);
		filterUserId.setValue(userId);

		// Filtro fecha de inicio
		FilterVO filterStartDate = new FilterVO();
		filterStartDate
				.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterStartDate.setOperator(OperatorEnum.ES_MAYOR_IGUAL);
		filterStartDate.setValue(startDate);
		filterStartDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		// Filtro fecha de fin
		FilterVO filterEndDate = new FilterVO();
		filterEndDate.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterEndDate.setOperator(OperatorEnum.ES_MENOR_IGUAL);
		filterEndDate.setValue(endDate);
		filterEndDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		List<FilterVO> filters = new LinkedList<FilterVO>();
		filters.add(filterAppId);
		filters.add(filterUserId);
		filters.add(filterStartDate);
		filters.add(filterEndDate);

		criteria.setFilters(filters);
		return criteria;
	}

	/**
	 * 
	 * Devuelve un objeto de tipo CriteriaVO {@link CriteriaVO} para realizar
	 * búsquedas por usuario
	 * 
	 * @param userId
	 *            Identificador del usuario del que queremos obtener las trazas
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return Criteria para realizar búsquedas por usuario
	 */
	public static CriteriaVO getUserIdCriteria(String userId, Date startDate,
			Date endDate) {

		CriteriaVO criteria = new CriteriaVO();

		// Filtro userId
		FilterVO filterUserId = new FilterVO(
				CamposTrazaAuditoriaConstants.USER_ID_FIELD, userId,
				OperatorEnum.ES_IGUAL);

		// Filtro fecha de inicio
		FilterVO filterStartDate = new FilterVO();
		filterStartDate
				.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterStartDate.setOperator(OperatorEnum.ES_MAYOR_IGUAL);
		filterStartDate.setValue(startDate);
		filterStartDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		// Filtro fecha de fin
		FilterVO filterEndDate = new FilterVO();
		filterEndDate.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterEndDate.setOperator(OperatorEnum.ES_MENOR_IGUAL);
		filterEndDate.setValue(endDate);
		filterEndDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		List<FilterVO> filters = new LinkedList<FilterVO>();
		filters.add(filterUserId);
		filters.add(filterStartDate);
		filters.add(filterEndDate);

		criteria.setFilters(filters);
		return criteria;
	}

	/**
	 * 
	 * Devuelve un objeto de tipo CriteriaVO {@link CriteriaVO} para realizar
	 * búsquedas por usuario y aplicación
	 * 
	 * @param userId
	 *            Identificador del usuario del que queremos obtener las trazas
	 * 
	 * @param appId
	 *            Identificador de la aplicación
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return Criteria para realizar búsquedas por usuario
	 */
	public static CriteriaVO getUserIdAppIdCriteria(String userId, Long appId,
			Date startDate, Date endDate) {
		CriteriaVO criteria = new CriteriaVO();

		// Filtro userId
		FilterVO filterUserId = new FilterVO(
				CamposTrazaAuditoriaConstants.USER_ID_FIELD, userId,
				OperatorEnum.ES_IGUAL);

		// Filtro appId
		FilterVO filterAppId = new FilterVO();
		filterAppId.setField(CamposTrazaAuditoriaConstants.APP_ID_FIELD);
		filterAppId.setOperator(OperatorEnum.ES_IGUAL);
		filterAppId.setValue(String.valueOf(appId));
		filterAppId.setJdbcType(JdbcTypesEnum.BIG_INT);

		// Filtro fecha de inicio
		FilterVO filterStartDate = new FilterVO();
		filterStartDate
				.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterStartDate.setOperator(OperatorEnum.ES_MAYOR_IGUAL);
		filterStartDate.setValue(startDate);
		filterStartDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		// Filtro fecha de fin
		FilterVO filterEndDate = new FilterVO();
		filterEndDate.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterEndDate.setOperator(OperatorEnum.ES_MENOR_IGUAL);
		filterEndDate.setValue(endDate);
		filterEndDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		List<FilterVO> filters = new LinkedList<FilterVO>();
		filters.add(filterUserId);
		filters.add(filterAppId);

		filters.add(filterStartDate);
		filters.add(filterEndDate);

		criteria.setFilters(filters);
		return criteria;
	}

	/**
	 * 
	 * Devuelve un objeto de tipo CriteriaVO {@link CriteriaVO} para realizar
	 * búsquedas por usuario y aplicación
	 * 
	 * @param userId
	 *            Identificador del usuario del que queremos obtener las trazas
	 * @param appId
	 *            Identificador de la aplicación
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return
	 */
	public static CriteriaVO getUserObjectIdCriteria(String userId, Long appId,
			Long eventType, String objectId, Date startDate, Date endDate) {

		CriteriaVO criteria = new CriteriaVO();
		// Filtro appId
		FilterVO filterAppId = new FilterVO();
		filterAppId.setField(CamposTrazaAuditoriaConstants.APP_ID_FIELD);
		filterAppId.setOperator(OperatorEnum.ES_IGUAL);
		filterAppId.setValue(String.valueOf(appId));
		filterAppId.setJdbcType(JdbcTypesEnum.BIG_INT);

		// Filtro eventId
		FilterVO filterEventType = new FilterVO();
		filterEventType
				.setField(CamposTrazaAuditoriaConstants.EVENT_TYPE_FIELD);
		filterEventType.setOperator(OperatorEnum.ES_IGUAL);
		filterEventType.setValue(String.valueOf(eventType));
		filterEventType.setJdbcType(JdbcTypesEnum.BIG_INT);

		// Filtro userId
		FilterVO filterUserId = new FilterVO();
		filterUserId.setField(CamposTrazaAuditoriaConstants.USER_ID_FIELD);
		filterUserId.setOperator(OperatorEnum.ES_IGUAL);
		filterUserId.setValue(userId);

		// Filtro objectId
		FilterVO filterObjectId = new FilterVO();
		filterObjectId.setField(CamposTrazaAuditoriaConstants.OBJECT_ID_FIELD);
		filterObjectId.setOperator(OperatorEnum.ES_IGUAL);
		filterObjectId.setValue(objectId);

		// Filtro fecha de inicio
		FilterVO filterStartDate = new FilterVO();
		filterStartDate
				.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterStartDate.setOperator(OperatorEnum.ES_MAYOR_IGUAL);
		filterStartDate.setValue(startDate);
		filterStartDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		// Filtro fecha de fin
		FilterVO filterEndDate = new FilterVO();
		filterEndDate.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterEndDate.setOperator(OperatorEnum.ES_MENOR_IGUAL);
		filterEndDate.setValue(endDate);
		filterEndDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		List<FilterVO> filters = new LinkedList<FilterVO>();
		filters.add(filterAppId);
		filters.add(filterEventType);
		filters.add(filterUserId);
		filters.add(filterObjectId);
		filters.add(filterStartDate);
		filters.add(filterEndDate);

		criteria.setFilters(filters);
		return criteria;
	}

	/**
	 * 
	 * Devuelve un objeto de tipo CriteriaVO {@link CriteriaVO} para realizar
	 * búsquedas por usuario y aplicación
	 * 
	 * @param userId
	 *            Identificador del usuario del que queremos obtener las trazas
	 * @param appId
	 *            Identificador de la aplicación
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return
	 */
	public static CriteriaVO getUserObjectFieldCriteria(String userId,
			Long appId, Long eventTypeId, String objectId, String objectField,
			Date startDate, Date endDate) {

		CriteriaVO criteria = new CriteriaVO();
		// Filtro appId
		FilterVO filterAppId = new FilterVO();
		filterAppId.setField(CamposTrazaAuditoriaConstants.APP_ID_FIELD);
		filterAppId.setOperator(OperatorEnum.ES_IGUAL);
		filterAppId.setValue(String.valueOf(appId));
		filterAppId.setJdbcType(JdbcTypesEnum.BIG_INT);

		// Filtro evenType
		FilterVO filterEventType = new FilterVO();
		filterEventType
				.setField(CamposTrazaAuditoriaConstants.EVENT_TYPE_FIELD);
		filterEventType.setOperator(OperatorEnum.ES_IGUAL);
		filterEventType.setValue(String.valueOf(eventTypeId));
		filterEventType.setJdbcType(JdbcTypesEnum.BIG_INT);

		// Filtro userId
		FilterVO filterUserId = new FilterVO();
		filterUserId.setField(CamposTrazaAuditoriaConstants.USER_ID_FIELD);
		filterUserId.setOperator(OperatorEnum.ES_IGUAL);
		filterUserId.setValue(userId);

		// Filtro objectId
		FilterVO filterObjectId = new FilterVO();
		filterObjectId.setField(CamposTrazaAuditoriaConstants.OBJECT_ID_FIELD);
		filterObjectId.setOperator(OperatorEnum.ES_IGUAL);
		filterObjectId.setValue(objectId);

		// Filtro objectField
		FilterVO filterObjectField = new FilterVO();
		filterObjectField
				.setField(CamposTrazaAuditoriaConstants.OBJECT_FIELD_FIELD);
		filterObjectId.setOperator(OperatorEnum.ES_IGUAL);

		// Filtro fecha de inicio
		FilterVO filterStartDate = new FilterVO();
		filterStartDate
				.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterStartDate.setOperator(OperatorEnum.ES_MAYOR_IGUAL);
		filterStartDate.setValue(startDate);
		filterStartDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		// Filtro fecha de fin
		FilterVO filterEndDate = new FilterVO();
		filterEndDate.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterEndDate.setOperator(OperatorEnum.ES_MENOR_IGUAL);
		filterEndDate.setValue(endDate);
		filterEndDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		List<FilterVO> filters = new LinkedList<FilterVO>();
		filters.add(filterAppId);
		filters.add(filterEventType);
		filters.add(filterUserId);
		filters.add(filterObjectField);
		filters.add(filterStartDate);
		filters.add(filterEndDate);

		criteria.setFilters(filters);
		return criteria;
	}
	
	/**
	 * 
	 * Devuelve un objeto de tipo CriteriaVO {@link CriteriaVO} para realizar
	 * búsquedas por usuario, aplicación y objeto
	 * 
	 * @param userId
	 *            Identificador del usuario del que queremos obtener las trazas
	 * @param appId
	 *            Identificador de la aplicación
	 * @param objectId
	 *            Identificador del objeto
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return
	 */
	public static CriteriaVO getLikeUserObjectIdCriteria(String userId, Long appId, String objectId,
			Date startDate, Date endDate) {

		CriteriaVO criteria = new CriteriaVO();
		// Filtro appId
		FilterVO filterAppId = new FilterVO();
		filterAppId.setField(CamposTrazaAuditoriaConstants.APP_ID_FIELD);
		filterAppId.setOperator(OperatorEnum.ES_IGUAL);
		filterAppId.setValue(String.valueOf(appId));
		filterAppId.setJdbcType(JdbcTypesEnum.BIG_INT);

		// Filtro userId
		FilterVO filterUserId = new FilterVO();
		filterUserId.setField(CamposTrazaAuditoriaConstants.USER_ID_FIELD);
		filterUserId.setOperator(OperatorEnum.ES_IGUAL);
		filterUserId.setValue(userId);

		// Filtro objectId
		FilterVO filterObjectId = new FilterVO();
		filterObjectId.setField(CamposTrazaAuditoriaConstants.OBJECT_ID_FIELD);
		filterObjectId.setOperator(OperatorEnum.CONTIENE);
		filterObjectId.setValue(objectId);

		// Filtro fecha de inicio
		FilterVO filterStartDate = new FilterVO();
		filterStartDate.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterStartDate.setOperator(OperatorEnum.ES_MAYOR_IGUAL);
		filterStartDate.setValue(startDate);
		filterStartDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		// Filtro fecha de fin
		FilterVO filterEndDate = new FilterVO();
		filterEndDate.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterEndDate.setOperator(OperatorEnum.ES_MENOR_IGUAL);
		filterEndDate.setValue(endDate);
		filterEndDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		List<FilterVO> filters = new LinkedList<FilterVO>();
		filters.add(filterAppId);
		filters.add(filterUserId);
		filters.add(filterObjectId);
		filters.add(filterStartDate);
		filters.add(filterEndDate);

		criteria.setFilters(filters);
		return criteria;
	}
	
	/**
	 * 
	 * Devuelve un objeto de tipo CriteriaVO {@link CriteriaVO} para realizar
	 * búsquedas por aplicación y objeto
	 * 
	 * @param appId
	 *            Identificador de la aplicación
	 * @param objectId
	 *            Identificador del objeto
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return CriteriaVO {@link CriteriaVO} para realizar búsquedas por
	 *         aplicación y objeto
	 */
	public static CriteriaVO getObjectIdCriteria(Long appId, String objectId, Date startDate,
			Date endDate) {

		CriteriaVO criteria = new CriteriaVO();
		// Filtro appId
		FilterVO filterAppId = new FilterVO();
		filterAppId.setField(CamposTrazaAuditoriaConstants.APP_ID_FIELD);
		filterAppId.setOperator(OperatorEnum.ES_IGUAL);
		filterAppId.setValue(String.valueOf(appId));
		filterAppId.setJdbcType(JdbcTypesEnum.BIG_INT);

		// Filtro objectId
		FilterVO filterObjectId = new FilterVO();
		filterObjectId.setField(CamposTrazaAuditoriaConstants.OBJECT_ID_FIELD);
		filterObjectId.setOperator(OperatorEnum.ES_IGUAL);
		filterObjectId.setValue(objectId);

		// Filtro fecha de inicio
		FilterVO filterStartDate = new FilterVO();
		filterStartDate.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterStartDate.setOperator(OperatorEnum.ES_MAYOR_IGUAL);
		filterStartDate.setValue(startDate);
		filterStartDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		// Filtro fecha de fin
		FilterVO filterEndDate = new FilterVO();
		filterEndDate.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterEndDate.setOperator(OperatorEnum.ES_MENOR_IGUAL);
		filterEndDate.setValue(endDate);
		filterEndDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		List<FilterVO> filters = new LinkedList<FilterVO>();
		filters.add(filterAppId);
		filters.add(filterObjectId);
		filters.add(filterStartDate);
		filters.add(filterEndDate);

		criteria.setFilters(filters);
		return criteria;
	}
	
	/**
	 * 
	 * Devuelve un objeto de tipo CriteriaVO {@link CriteriaVO} para realizar
	 * búsquedas por aplicación y objeto
	 * 
	 * @param appId
	 *            Identificador de la aplicación
	 * @param objectId
	 *            Identificador del objeto
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return CriteriaVO {@link CriteriaVO} para realizar búsquedas por
	 *         aplicación y objeto
	 */
	public static CriteriaVO getLikeObjectIdCriteria(Long appId, String objectId, Date startDate,
			Date endDate) {

		CriteriaVO criteria = new CriteriaVO();
		// Filtro appId
		FilterVO filterAppId = new FilterVO();
		filterAppId.setField(CamposTrazaAuditoriaConstants.APP_ID_FIELD);
		filterAppId.setOperator(OperatorEnum.ES_IGUAL);
		filterAppId.setValue(String.valueOf(appId));
		filterAppId.setJdbcType(JdbcTypesEnum.BIG_INT);

		// Filtro objectId
		FilterVO filterObjectId = new FilterVO();
		filterObjectId.setField(CamposTrazaAuditoriaConstants.OBJECT_ID_FIELD);
		filterObjectId.setOperator(OperatorEnum.CONTIENE);
		filterObjectId.setValue(objectId);

		// Filtro fecha de inicio
		FilterVO filterStartDate = new FilterVO();
		filterStartDate.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterStartDate.setOperator(OperatorEnum.ES_MAYOR_IGUAL);
		filterStartDate.setValue(startDate);
		filterStartDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		// Filtro fecha de fin
		FilterVO filterEndDate = new FilterVO();
		filterEndDate.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filterEndDate.setOperator(OperatorEnum.ES_MENOR_IGUAL);
		filterEndDate.setValue(endDate);
		filterEndDate.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		List<FilterVO> filters = new LinkedList<FilterVO>();
		filters.add(filterAppId);
		filters.add(filterObjectId);
		filters.add(filterStartDate);
		filters.add(filterEndDate);

		criteria.setFilters(filters);
		return criteria;
	}
}
