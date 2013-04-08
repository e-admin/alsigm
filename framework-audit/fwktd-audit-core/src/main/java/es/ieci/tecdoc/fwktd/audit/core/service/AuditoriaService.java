package es.ieci.tecdoc.fwktd.audit.core.service;

import java.util.Date;
import java.util.List;

import es.ieci.tecdoc.fwktd.audit.core.vo.AppAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.CriteriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginatedList;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginationContext;

/**
 * 
 * Servicio de auditoria del fwktd
 * 
 * @author iecisa
 * 
 */
public interface AuditoriaService {

	/**
	 * Metodo para auditar una traza producida por algún evento desencadenado
	 * por un usuario y aplicación
	 * 
	 * @param traza
	 *            Traza a auditar {@link TrazaAuditoriaVO}
	 */
	public void audit(TrazaAuditoriaVO traza);

	/**
	 * Devuelve una traza de auditoría {@link TrazaAuditoriaVO} a partir del
	 * identificador
	 * 
	 * @param idTraza
	 *            Identificador de la traza que queremos obtener
	 * @return Objeto de la traza {@link TrazaAuditoriaVO} con el identificador
	 *         solicitado
	 */
	public TrazaAuditoriaVO getTraza(String idTraza);

	/**
	 * Devuelve un listado de objetos de trazas de auditoría
	 * {@link TrazaAuditoriaVO} para una aplicación y un evento comprendidos
	 * entre la fecha de inicio y la fecha de fin
	 * 
	 * @param appId
	 *            Identificador de la aplicación
	 * @param eventType
	 *            Identificador del tipo de evento
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return Listado de trazas de auditoría {@link TrazaAuditoriaVO} para una
	 *         aplicación y un evento comprendidos entre la fecha de inicio y la
	 *         fecha de fin
	 */
	public List<TrazaAuditoriaVO> findByAppIdEventType(Long appId, Long eventType, Date startDate,
			Date endDate);

	/**
	 * Devuelve un listado paginado {@link PaginatedList} de objetos de trazas
	 * de auditoría {@link TrazaAuditoriaVO} para una aplicación y un evento
	 * comprendidos entre la fecha de inicio y la fecha de fin
	 * 
	 * @param appId
	 *            Identificador de la aplicación
	 * @param eventType
	 *            Identificador del tipo de evento
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @param paginationContext
	 *            Configuración de la paginación
	 * 
	 * @return Listado de trazas de auditoría {@link TrazaAuditoriaVO} para una
	 *         aplicación y un evento comprendidos entre la fecha de inicio y la
	 *         fecha de fin
	 */
	public PaginatedList<TrazaAuditoriaVO> findByAppIdEventType(Long appId, Long eventType,
			Date startDate, Date endDate, PaginationContext paginationContext);

	/**
	 * Devuelve un listado de objetos de trazas de auditoría
	 * {@link TrazaAuditoriaVO} para un usuario comprendidos entre la fecha de
	 * inicio y la fecha de fin.
	 * 
	 * Responde a la pregunta:
	 * 
	 * ¿Qué ha hecho el usuario tal en intervalo de fechas?
	 * 
	 * @param userId
	 *            Identificador del usuario del que queremos obtener las trazas
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * 
	 * @return Listado de objetos de trazas de auditoría
	 *         {@link TrazaAuditoriaVO} para una aplicación y un usuario
	 *         comprendidos entre la fecha de inicio y la fecha de fin
	 */
	public List<TrazaAuditoriaVO> findByUserId(String userId, Date startDate, Date endDate);

	/**
	 * Devuelve un listado de objetos de trazas de auditoría
	 * {@link TrazaAuditoriaVO} para un usuario comprendidos entre la fecha de
	 * inicio y la fecha de fin.
	 * 
	 * Responde a la pregunta:
	 * 
	 * ¿Qué ha hecho el usuario tal en intervalo de fechas?
	 * 
	 * @param userId
	 *            Identificador del usuario del que queremos obtener las trazas
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @param paginationContext
	 *            Configuración de la paginación
	 * 
	 * @return Listado de objetos de trazas de auditoría
	 *         {@link TrazaAuditoriaVO} para una aplicación y un usuario
	 *         comprendidos entre la fecha de inicio y la fecha de fin
	 */
	public PaginatedList<TrazaAuditoriaVO> findByUserId(String userId, Date startDate,
			Date endDate, PaginationContext paginationContext);

	/**
	 * Devuelve un listado de objetos de trazas de auditoría
	 * {@link TrazaAuditoriaVO} para una aplicación y un usuario comprendidos
	 * entre la fecha de inicio y la fecha de fin
	 * 
	 * @param userId
	 *            Identificador del usuario del que queremos obtener las trazas
	 * @param appId
	 *            Identificador de la aplicación
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @param paginationContext
	 *            Configuración de la paginación
	 * 
	 * @return Listado de objetos de trazas de auditoría
	 *         {@link TrazaAuditoriaVO} para una aplicación y un usuario
	 *         comprendidos entre la fecha de inicio y la fecha de fin
	 */
	public List<TrazaAuditoriaVO> findByUserIdAppId(String userId, Long appId, Date startDate,
			Date endDate);

	/**
	 * Devuelve un listado de objetos de trazas de auditoría
	 * {@link TrazaAuditoriaVO} para una aplicación y un usuario comprendidos
	 * entre la fecha de inicio y la fecha de fin
	 * 
	 * @param userId
	 *            Identificador del usuario del que queremos obtener las trazas
	 * @param appId
	 *            Identificador de la aplicación
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return Listado de objetos de trazas de auditoría
	 *         {@link TrazaAuditoriaVO} para una aplicación y un usuario
	 *         comprendidos entre la fecha de inicio y la fecha de fin
	 */
	public PaginatedList<TrazaAuditoriaVO> findByUserIdAppId(String userId, Long appId,
			Date startDate, Date endDate, PaginationContext paginationContext);

	/**
	 * Devuelve un listado de objetos de trazas de auditoría
	 * {@link TrazaAuditoriaVO} para una aplicación, un objeto y un usuario
	 * comprendidos entre la fecha de inicio y la fecha de fin.
	 * 
	 * Responde a la pregunta:
	 * 
	 * ¿Quién ha creado/actualizado/eliminado el registro con ID=X en la
	 * tabla/Objeto Foo y cuando?
	 * 
	 * @param userId
	 *            Identificador del usuario del que queremos obtener las trazas
	 * @param appId
	 *            Identificador de la aplicación
	 * @param eventType
	 *            Identificador del tipo de evento
	 * @param objectId
	 *            Identificador del objeto
	 * 
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return Listado de objetos de trazas de auditoría
	 *         {@link TrazaAuditoriaVO}
	 */
	public List<TrazaAuditoriaVO> findByUserObjectId(String userId, Long appId, Long eventType,
			String objectId, Date startDate, Date endDate);

	/**
	 * Devuelve un listado de objetos de trazas de auditoría
	 * {@link TrazaAuditoriaVO} para una aplicación, un objeto y un usuario
	 * comprendidos entre la fecha de inicio y la fecha de fin.
	 * 
	 * Responde a la pregunta:
	 * 
	 * ¿Quién ha creado/actualizado/eliminado el registro con ID=X en la
	 * tabla/Objeto Foo y cuando?
	 * 
	 * @param userId
	 *            Identificador del usuario del que queremos obtener las trazas
	 * @param appId
	 *            Identificador de la aplicación
	 * @param eventType
	 *            Identificador del tipo de evento
	 * @param objectId
	 *            Identificador del objeto
	 * 
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return Listado de objetos de trazas de auditoría
	 *         {@link TrazaAuditoriaVO}
	 */
	public PaginatedList<TrazaAuditoriaVO> findByUserObjectId(String userId, Long appId,
			Long eventType, String objectId, Date startDate, Date endDate,
			PaginationContext paginationContext);

	/**
	 * Devuelve un listado de objetos de trazas de auditoría
	 * {@link TrazaAuditoriaVO} para una aplicación, un objeto, un campo del
	 * objeto y un usuario comprendidos entre la fecha de inicio y la fecha de
	 * fin.
	 * 
	 * Responde a la pregunta:
	 * 
	 * ¿Quién ha creado/actualizado/eliminado el registro con ID=X en la
	 * tabla/Objeto Foo y cuando?
	 * 
	 * @param userId
	 *            Identificador del usuario del que queremos obtener las trazas
	 * @param appId
	 *            Identificador de la aplicación
	 * @param eventType
	 *            Identificador del tipo de evento
	 * @param objectId
	 *            Identificador del objeto
	 * @param objectField
	 *            Campo del objeto
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return Listado de objetos de trazas de auditoría
	 *         {@link TrazaAuditoriaVO}
	 */
	public List<TrazaAuditoriaVO> findByUserObjectField(String userId, Long appId, Long eventType,
			String objectId, String objectField, Date startDate, Date endDate);

	/**
	 * Devuelve un listado de objetos de trazas de auditoría
	 * {@link TrazaAuditoriaVO} para una aplicación, un objeto, un campo del
	 * objeto y un usuario comprendidos entre la fecha de inicio y la fecha de
	 * fin.
	 * 
	 * Responde a la pregunta:
	 * 
	 * ¿Qué y quien se ha creado/actualizado/eliminado el campo X del objeto Foo
	 * y cuando ?
	 * 
	 * @param userId
	 *            Identificador del usuario del que queremos obtener las trazas
	 * @param appId
	 *            Identificador de la aplicación
	 * @param eventType
	 *            Identificador del tipo de evento
	 * @param objectId
	 *            Identificador del objeto
	 * @param objectField
	 *            Campo del objeto
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return Listado de objetos de trazas de auditoría
	 *         {@link TrazaAuditoriaVO}
	 */
	public PaginatedList<TrazaAuditoriaVO> findByUserObjectField(String userId, Long appId,
			Long eventType, String objectId, String objectField, Date startDate, Date endDate,
			PaginationContext paginationContext);

	/**
	 * Devuelve un listado de objetos de trazas de auditoría
	 * {@link TrazaAuditoriaVO} para una aplicación, un patrón de búsqueda de
	 * objetos y un usuario comprendidos entre la fecha de inicio y la fecha de
	 * fin.
	 * 
	 * Responde a la pregunta:
	 * 
	 * ¿Quién ha creado/actualizado/eliminado el registro con ID=X en la
	 * tabla/Objeto Foo y cuando?
	 * 
	 * @param userId
	 *            Identificador del usuario del que queremos obtener las trazas
	 * @param appId
	 *            Identificador de la aplicación
	 * @param objectId
	 *            Texto por el que se buscará el Identificador del objeto
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return Listado de objetos de trazas de auditoría
	 *         {@link TrazaAuditoriaVO}
	 */
	public List<TrazaAuditoriaVO> findLikeUserObjectId(String userId, Long appId, String objectId,
			Date startDate, Date endDate);

	/**
	 * Devuelve un listado de objetos de trazas de auditoría
	 * {@link TrazaAuditoriaVO} para una aplicación, un patrón de búsqueda de
	 * objetos y un usuario comprendidos entre la fecha de inicio y la fecha de
	 * fin.
	 * 
	 * Responde a la pregunta:
	 * 
	 * ¿Quién ha creado/actualizado/eliminado el registro con ID=X en la
	 * tabla/Objeto Foo y cuando?
	 * 
	 * @param userId
	 *            Identificador del usuario del que queremos obtener las trazas
	 * @param appId
	 *            Identificador de la aplicación
	 * @param objectId
	 *            Texto por el que se buscará el Identificador del objeto
	 * 
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @param paginationContext
	 *            Configuración de la paginación
	 * 
	 * @return Listado de objetos de trazas de auditoría
	 *         {@link TrazaAuditoriaVO}
	 */
	public PaginatedList<TrazaAuditoriaVO> findLikeUserObjectId(String userId, Long appId,
			String objectId, Date startDate, Date endDate, PaginationContext paginationContext);

	/**
	 * Devuelve un listado de objetos de trazas de auditoría
	 * {@link TrazaAuditoriaVO} para una aplicación y un objeto comprendidos
	 * entre la fecha de inicio y la fecha de fin.
	 * 
	 * Responde a la pregunta:
	 * 
	 * ¿Quién ha creado/actualizado/eliminado el registro con ID=X en la
	 * tabla/Objeto Foo y cuando?
	 * 
	 * @param appId
	 *            Identificador de la aplicación
	 * @param objectId
	 *            Identificador del objeto
	 * 
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @return Listado de objetos de trazas de auditoría
	 *         {@link TrazaAuditoriaVO}
	 */
	public List<TrazaAuditoriaVO> findByObjectId(Long appId, String objectId, Date startDate,
			Date endDate);

	/**
	 * Devuelve un listado de objetos de trazas de auditoría
	 * {@link TrazaAuditoriaVO} para una aplicación y un objeto comprendidos
	 * entre la fecha de inicio y la fecha de fin.
	 * 
	 * Responde a la pregunta:
	 * 
	 * ¿Quién ha creado/actualizado/eliminado el registro con ID=X en la
	 * tabla/Objeto Foo y cuando?
	 * 
	 * @param appId
	 *            Identificador de la aplicación
	 * @param objectId
	 *            Identificador del objeto
	 * 
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * @param paginationContext
	 *            Configuración de la paginación
	 * 
	 * @return Listado de objetos de trazas de auditoría
	 *         {@link TrazaAuditoriaVO}
	 */
	public PaginatedList<TrazaAuditoriaVO> findByObjectId(Long appId, String objectId,
			Date startDate, Date endDate, PaginationContext paginationContext);

	/**
	 * Devuelve un listado de objetos de trazas de auditoría
	 * {@link TrazaAuditoriaVO} para una aplicación y un patrón de búsqueda del
	 * identificador del objeto comprendidos entre la fecha de inicio y la fecha
	 * de fin.
	 * 
	 * Responde a la pregunta:
	 * 
	 * ¿Quién ha creado/actualizado/eliminado el registro con ID=X en la
	 * tabla/Objeto Foo y cuando?
	 * 
	 * @param appId
	 *            Identificador de la aplicación
	 * @param objectId
	 *            Texto con el identificador del objeto por el que buscar
	 * 
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * 
	 * @return Listado de objetos de trazas de auditoría
	 *         {@link TrazaAuditoriaVO}
	 */
	public List<TrazaAuditoriaVO> findLikeObjectId(Long appId, String objectId, Date startDate,
			Date endDate);

	/**
	 * Devuelve un listado de objetos de trazas de auditoría
	 * {@link TrazaAuditoriaVO} para una aplicación y un patrón de búsqueda del
	 * identificador del objeto comprendidos entre la fecha de inicio y la fecha
	 * de fin.
	 * 
	 * Responde a la pregunta:
	 * 
	 * ¿Quién ha creado/actualizado/eliminado el registro con ID=X en la
	 * tabla/Objeto Foo y cuando?
	 * 
	 * @param appId
	 *            Identificador de la aplicación
	 * @param objectId
	 *            Texto con el identificador del objeto por el que buscar
	 * 
	 * @param startDate
	 *            Fecha de inicio
	 * @param endDate
	 *            Fecha de fin
	 * 
	 * @param paginationContext
	 *            Configuración de la paginación
	 * 
	 * @return Listado de objetos de trazas de auditoría
	 *         {@link TrazaAuditoriaVO}
	 */
	public PaginatedList<TrazaAuditoriaVO> findLikeObjectId(Long appId, String objectId,
			Date startDate, Date endDate, PaginationContext paginationContext);

	/**
	 * Obtiene un listado de trazas de auditoria según el criterio indicado
	 * 
	 * @param criteria
	 *            - Criterio de Busqueda
	 * @return Listado de objetos {@link TrazaAuditoriaVO}
	 */
	public List<TrazaAuditoriaVO> findByCriteria(CriteriaVO criteria);

	/**
	 * Obtiene un listado de trazas de auditoria según el criterio indicado y la
	 * paginacion
	 * 
	 * @param criteria
	 * @param paginationContext
	 * @return
	 */
	public PaginatedList<TrazaAuditoriaVO> findByCriteria(CriteriaVO criteria,
			PaginationContext paginationContext);

	/**
	 * Obtiene el numero de resultados coincidentes con el criterio indicado
	 * 
	 * @param criteria
	 *            - Criterio de Busqueda
	 * @return Integer del resultado de la busqueda
	 */
	public Integer countByCriteria(CriteriaVO criteria);

	/**
	 * Devuelve el identificador de la aplicación a partir de su descripción
	 * 
	 * @param appDescripion
	 *            Descripción de la aplicación
	 * @return El identificador de la aplicación a partir de su descripción
	 */
	public AppAuditoriaVO getAppAuditoria(String appDescripion);

}
