package es.ieci.tecdoc.fwktd.dir3.api.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;

import es.ieci.tecdoc.fwktd.dir3.api.dao.DatosBasicosOficinaDao;
import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.server.pagination.PaginatedArrayList;

/**
 * DAO de datos básicos de oficinas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DatosBasicosOficinaDaoImpl extends
		IbatisGenericDaoImpl<DatosBasicosOficinaVO, String>
		implements DatosBasicosOficinaDao {

	protected static final String COUNT_FIND_OFICINAS = "DatosBasicosOficinaVO.countFindOficinas";
	protected static final String FIND_OFICINAS = "DatosBasicosOficinaVO.findOficinas";

	/**
	 * Constructor con parámetros de la clase. Establece el tipo de entidad a
	 * persistir/recuperar.
	 *
	 * @param aPersistentClass
	 *            tipo de objeto a persistir/recuperar
	 */
	public DatosBasicosOficinaDaoImpl(
			Class<DatosBasicosOficinaVO> aPersistentClass) {
		super(aPersistentClass);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.api.dao.DatosBasicosOficinaDao#countOficinas(es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios)
	 */
	public int countOficinas(Criterios<CriterioOficinaEnum> criterios) {

		// Comprobar si se han definido criterios
		if ((criterios == null) || CollectionUtils.isEmpty(criterios.getCriterios())) {
			logger.info("Obteniendo el número de oficinas sin criterios");
			return count();
		}

		logger.info("Obteniendo el número de oficinas en base a los siguientes criterios: {}", criterios);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("criterios", criterios.getCriterios());

		return (Integer) getSqlMapClientTemplate().queryForObject(COUNT_FIND_OFICINAS, map);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.api.dao.DatosBasicosOficinaDao#findOficinas(es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios)
	 */
	@SuppressWarnings("unchecked")
	public List<DatosBasicosOficinaVO> findOficinas(
			Criterios<CriterioOficinaEnum> criterios) {

 		// Comprobar si se han definido criterios
		if ((criterios == null) || CollectionUtils.isEmpty(criterios.getCriterios())) {
			logger.info("Realizando búsqueda de oficinas sin criterios");
			return getAll();
		}

		logger.info("Realizando búsqueda de oficinas en base a los siguientes criterios: {}", criterios);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("criterios", criterios.getCriterios());
		map.put("orden", criterios.getOrderBy());

		// Comprobar si hay que paginar los resultados
		PageInfo pageInfo = criterios.getPageInfo();
		if (pageInfo != null) {

			// Número de resultados a ignorar
			int skipResults = SqlExecutor.NO_SKIPPED_RESULTS;

			// Número máximo de resultados.
			int maxResults = SqlExecutor.NO_MAXIMUM_RESULTS;

			if ((pageInfo.getPageNumber() > 0) && (pageInfo.getObjectsPerPage() > 0)) {
				skipResults = (pageInfo.getPageNumber() - 1) * pageInfo.getObjectsPerPage();
				maxResults = pageInfo.getObjectsPerPage();
			} else if (pageInfo.getMaxNumItems() > 0) {
				maxResults = pageInfo.getMaxNumItems();
			}

			// Obtener los resultados a mostrar en la página
			List<DatosBasicosOficinaVO> list = (List<DatosBasicosOficinaVO>) getSqlMapClientTemplate().queryForList(FIND_OFICINAS, map,
					skipResults, maxResults);

			// Obtener el total de resultados
			int fullListSize;
			if (((maxResults == SqlExecutor.NO_MAXIMUM_RESULTS) || (list.size() < maxResults))
					&& (skipResults == SqlExecutor.NO_SKIPPED_RESULTS)) {
				fullListSize = list.size();
			} else {
				fullListSize = (Integer) getSqlMapClientTemplate().queryForObject(COUNT_FIND_OFICINAS, map);
			}

			// Información de los resultados paginados
			PaginatedArrayList<DatosBasicosOficinaVO> resultados = new PaginatedArrayList<DatosBasicosOficinaVO>(pageInfo);
			resultados.setFullListSize(fullListSize);
			resultados.setList(list);

			return resultados;

		} else {
			return (List<DatosBasicosOficinaVO>) getSqlMapClientTemplate().queryForList(FIND_OFICINAS, map);
		}
	}

}
