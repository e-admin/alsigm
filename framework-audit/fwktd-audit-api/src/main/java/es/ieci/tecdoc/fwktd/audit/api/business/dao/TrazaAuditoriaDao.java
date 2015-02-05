package es.ieci.tecdoc.fwktd.audit.api.business.dao;

import java.util.List;

import es.ieci.tecdoc.fwktd.audit.core.vo.AppAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.CriteriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginatedList;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginationContext;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;

public interface TrazaAuditoriaDao extends BaseDao<TrazaAuditoriaVO, String> {
	/**
	 * 
	 * @param criteria
	 * @return
	 */
	List<TrazaAuditoriaVO> findByCriteria(CriteriaVO criteria);

	/**
	 * 
	 * @param criteria
	 * @return
	 */
	Integer countByCriteria(CriteriaVO criteria);

	/**
	 * Obtiene un listado de trazas de auditoria segÃºn el criterio indicado y
	 * la paginacion
	 * 
	 * @param criteria
	 *            - Criterio de Busqueda
	 * @return Listado de objetos {@link TrazaAuditoriaVO}
	 */
	public PaginatedList<TrazaAuditoriaVO> findByCriteria(CriteriaVO criteria,
			PaginationContext paginationContext);

	/**
	 * Devuelve el identificador de la aplicación a partir de su descripción
	 * 
	 * @param appDescripion
	 *            Descripción de la aplicación
	 * @return El identificador de la aplicación a partir de su descripción
	 */
	public AppAuditoriaVO getIdAplicacion(String appDescripion);
}
