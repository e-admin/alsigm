package es.ieci.tecdoc.fwktd.dir3.api.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;

import es.ieci.tecdoc.fwktd.dir3.api.dao.DatosBasicosRelacionUnidOrgOficinaDao;
import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosRelacionUnidOrgOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.server.pagination.PaginatedArrayList;

/**
 * DAO de datos básicos de las relaciones entre las unid. orgánicas y las
 * oficinas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DatosBasicosRelacionUnidOrgOficinaDaoImpl extends
		IbatisGenericDaoImpl<DatosBasicosRelacionUnidOrgOficinaVO, String>
		implements DatosBasicosRelacionUnidOrgOficinaDao {

	protected static final String COUNT_FIND_RELACIONES = "DatosBasicosRelacionUnidOrgOficinaVO.countRelacionesUnidOrgOficina";
	protected static final String FIND_RELACIONES = "DatosBasicosRelacionUnidOrgOficinaVO.findRelacionesUnidOrgOficina";
	protected static final String DELETE_RELACION = "DatosBasicosRelacionUnidOrgOficinaVO.deleteDatosBasicosRelacionUnidOrgOficinaVO";

	/**
	 * Constructor con parámetros de la clase. Establece el tipo de entidad a
	 * persistir/recuperar.
	 *
	 * @param aPersistentClass
	 *            tipo de objeto a persistir/recuperar
	 */
	public DatosBasicosRelacionUnidOrgOficinaDaoImpl(
			Class<DatosBasicosRelacionUnidOrgOficinaVO> aPersistentClass) {
		super(aPersistentClass);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.api.dao.DatosBasicosRelacionUnidOrgOficinaDao#countRelacionesUnidOrgOficina(es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios)
	 */
	public int countRelacionesUnidOrgOficina(
			Criterios<CriterioOficinaEnum> criterios) {

		// Comprobar si se han definido criterios
		if ((criterios == null)
				|| CollectionUtils.isEmpty(criterios.getCriterios())) {
			logger.info("Obteniendo el número de relaciones entre unid. orgánicas y oficinas sin criterios");
			return count();
		}

		logger.info(
				"Obteniendo el número de relaciones entre unid. orgánicas y oficinas en base a los siguientes criterios: {}",
				criterios);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("criterios", criterios.getCriterios());

		return (Integer) getSqlMapClientTemplate().queryForObject(
				COUNT_FIND_RELACIONES, map);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.api.dao.DatosBasicosRelacionUnidOrgOficinaDao#deleteRelacionesUnidOrgOficina()
	 */
	public void deleteRelacionesUnidOrgOficina(String codOficina,
			String codUnidOrg) {

		// Comprobar si se han definido criterios
		StringBuffer sb = new StringBuffer();
		sb.append(
				"Borrando la relaciones entre unid. orgánicas y oficinas en base a los siguientes criterios: {")
				.append("código oficina: [").append(codOficina)
				.append("] y código unid. orgánica: [").append(codUnidOrg)
				.append("]}");
		logger.info(sb.toString());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigoOficina", codOficina);
		map.put("codigoUnidadOrganica", codUnidOrg);

		getSqlMapClientTemplate().delete(DELETE_RELACION, map);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.api.dao.DatosBasicosRelacionUnidOrgOficinaDao#findRelacionesUnidOrgOficina(es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios)
	 */
	@SuppressWarnings("unchecked")
	public List<DatosBasicosRelacionUnidOrgOficinaVO> findRelacionesUnidOrgOficina(
			Criterios<CriterioOficinaEnum> criterios) {

		// Comprobar si se han definido criterios
		if ((criterios == null)
				|| CollectionUtils.isEmpty(criterios.getCriterios())) {
			logger.info("Realizando búsqueda de relaciones entre unid. orgánicas y oficinas sin criterios");
			return getAll();
		}

		logger.info(
				"Realizando búsqueda de relaciones entre unid. orgánicas y oficinas en base a los siguientes criterios: {}",
				criterios);

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

			if ((pageInfo.getPageNumber() > 0)
					&& (pageInfo.getObjectsPerPage() > 0)) {
				skipResults = (pageInfo.getPageNumber() - 1)
						* pageInfo.getObjectsPerPage();
				maxResults = pageInfo.getObjectsPerPage();
			} else if (pageInfo.getMaxNumItems() > 0) {
				maxResults = pageInfo.getMaxNumItems();
			}

			// Obtener los resultados a mostrar en la página
			List<DatosBasicosRelacionUnidOrgOficinaVO> list = (List<DatosBasicosRelacionUnidOrgOficinaVO>) getSqlMapClientTemplate()
					.queryForList(FIND_RELACIONES, map, skipResults, maxResults);

			// Obtener el total de resultados
			int fullListSize;
			if (((maxResults == SqlExecutor.NO_MAXIMUM_RESULTS) || (list.size() < maxResults))
					&& (skipResults == SqlExecutor.NO_SKIPPED_RESULTS)) {
				fullListSize = list.size();
			} else {
				fullListSize = (Integer) getSqlMapClientTemplate()
						.queryForObject(COUNT_FIND_RELACIONES, map);
			}

			// Información de los resultados paginados
			PaginatedArrayList<DatosBasicosRelacionUnidOrgOficinaVO> resultados = new PaginatedArrayList<DatosBasicosRelacionUnidOrgOficinaVO>(
					pageInfo);
			resultados.setFullListSize(fullListSize);
			resultados.setList(list);

			return resultados;

		} else {
			return (List<DatosBasicosRelacionUnidOrgOficinaVO>) getSqlMapClientTemplate()
					.queryForList(FIND_RELACIONES, map);
		}
	}

}
