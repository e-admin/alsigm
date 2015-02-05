package es.ieci.tecdoc.fwktd.sir.api.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.server.pagination.PaginatedArrayList;
import es.ieci.tecdoc.fwktd.sir.api.dao.AsientoRegistralDao;
import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriterioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.EstadoAsientoRegistraVO;

/**
 * DAO de asientos registrales.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AsientoRegistralDaoImpl extends
		IbatisGenericDaoImpl<AsientoRegistralVO, String> implements
		AsientoRegistralDao {

	private static final String CODIGO_ENTIDAD_REGISTRAL = "codigoEntidadRegistral";
	private static final String IDENTIFICADOR_INTERCAMBIO = "identificadorIntercambio";

	protected static final String COUNT_FIND_ASIENTOS_REGISTRALES = "AsientoRegistralVO.countFindAsientosRegistrales";
	protected static final String FIND_ASIENTOS_REGISTRALES = "AsientoRegistralVO.findAsientosRegistrales";
	protected static final String GET_CODIGO_INTERCAMBIO = "AsientoRegistralVO.getCodigoIntercambio";
	protected static final String  GET_DESCRIPCION_TIPO_ANOTACION_INTERCAMBIO="AsientoRegistralVO.getDescripcionTipoAnotacionIntercambio";
	protected static final String GET_ESTADO = "AsientoRegistralVO.getEstado";
	protected static final String GET_ASIENTO_REGISTRAL_BY_ENTIDAD_REGISTRAL_Y_CODIGO_INTERCAMBIO = "AsientoRegistralVO.getAsientoRegistralByEntidadRegistralYCodigoIntercambio";

	/**
	 * Constructor con parámetros de la clase. Establece el tipo de entidad a
	 * persistir/recuperar.
	 *
	 * @param aPersistentClass
	 *            tipo de objeto a persistir/recuperar
	 */
	public AsientoRegistralDaoImpl(Class<AsientoRegistralVO> aPersistentClass) {
		super(aPersistentClass);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.dao.AsientoRegistralDao#countAsientosRegistrales(es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO)
	 */
	public int countAsientosRegistrales(CriteriosVO criterios) {

		// Comprobar si se han definido criterios
		if ((criterios == null) || CollectionUtils.isEmpty(criterios.getCriterios())) {
			logger.info("Obteniendo el número de asientos registrales sin criterios");
			return count();
		}

		logger.info("Obteniendo el número de asientos registrales en base a los siguientes criterios: {}", criterios);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("tablasAuxiliares", getTablasAuxiliares(criterios));
		map.put("criterios", criterios.getCriterios());

		return (Integer) getSqlMapClientTemplate().queryForObject(COUNT_FIND_ASIENTOS_REGISTRALES, map);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.dao.AsientoRegistralDao#findAsientosRegistrales(es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO)
	 */
	@SuppressWarnings("unchecked")
	public List<AsientoRegistralVO> findAsientosRegistrales(
			CriteriosVO criterios) {

		// Comprobar si se han definido criterios
		if ((criterios == null) || CollectionUtils.isEmpty(criterios.getCriterios())) {
			logger.info("Realizando búsqueda de asientos registrales sin criterios");
			return getAll();
		}

		logger.info("Realizando búsqueda de asientos registrales en base a los siguientes criterios: {}", criterios);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("tablasAuxiliares", getTablasAuxiliares(criterios));
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
			List<AsientoRegistralVO> list = (List<AsientoRegistralVO>) getSqlMapClientTemplate().queryForList(FIND_ASIENTOS_REGISTRALES, map,
					skipResults, maxResults);

			// Obtener el total de resultados
			int fullListSize;
			if (((maxResults == SqlExecutor.NO_MAXIMUM_RESULTS) || (list.size() < maxResults))
					&& (skipResults == SqlExecutor.NO_SKIPPED_RESULTS)) {
				fullListSize = list.size();
			} else {
				fullListSize = (Integer) getSqlMapClientTemplate().queryForObject(COUNT_FIND_ASIENTOS_REGISTRALES, map);
			}

			// Información de los resultados paginados
			PaginatedArrayList<AsientoRegistralVO> resultados = new PaginatedArrayList<AsientoRegistralVO>(pageInfo);
			resultados.setFullListSize(fullListSize);
			resultados.setList(list);

			return resultados;

		} else {
			return (List<AsientoRegistralVO>) getSqlMapClientTemplate().queryForList(FIND_ASIENTOS_REGISTRALES, map);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.dao.AsientoRegistralDao#getCodigoIntercambio(java.lang.String)
	 */
	public String getCodigoIntercambio(String id) {

		logger.info("Obteniendo el código de intercambio del asiento registral [{}]", id);

		String codigoIntercambio = (String) getSqlMapClientTemplate()
				.queryForObject(GET_CODIGO_INTERCAMBIO, id);

		return codigoIntercambio;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.dao.AsientoRegistralDao#getEstado(java.lang.String)
	 */
	public EstadoAsientoRegistraVO getEstado(String id) {

		logger.info("Obteniendo el estado del asiento registral [{}]", id);

		EstadoAsientoRegistraVO estado = (EstadoAsientoRegistraVO) getSqlMapClientTemplate()
				.queryForObject(GET_ESTADO, id);

		return estado;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.dao.AsientoRegistralDao#getAsientoRegistral(java.lang.String,java.lang.String)
	 */
	public AsientoRegistralVO getAsientoRegistral(String codigoEntidadRegistral,
			String identificadorIntercambio) {

		logger.info(
				"Obteniendo el asiento registral a partir del código de entidad registral [{}] e identificador de intercambio [{}]",
				codigoEntidadRegistral, identificadorIntercambio);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put(CODIGO_ENTIDAD_REGISTRAL, codigoEntidadRegistral);
		map.put(IDENTIFICADOR_INTERCAMBIO, identificadorIntercambio);

		return (AsientoRegistralVO) getSqlMapClientTemplate()
				.queryForObject(
						GET_ASIENTO_REGISTRAL_BY_ENTIDAD_REGISTRAL_Y_CODIGO_INTERCAMBIO,
						map);
	}

	private static List<String> getTablasAuxiliares(CriteriosVO criterios) {

		List<String> tablas = new ArrayList<String>();

		if (CollectionUtils.isNotEmpty(criterios.getCriterios())) {
			for (CriterioVO criterio : criterios.getCriterios()) {
				CriterioEnum nombre = criterio.getNombre();
				if ((nombre != null)
						&& StringUtils.isNotBlank(nombre.getTable())
						&& !CriterioEnum.TABLE_ASIENTOS_REGISTRALES.equals(nombre.getTable())
						&& !tablas.contains(nombre.getTable())) {
					tablas.add(nombre.getTable());
				}
			}
		}

		return tablas;
	}

	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.fwktd.sir.api.dao.AsientoRegistralDao#getdescripcionTipoAnotacion(java.lang.String)
	 */
	public String getDescripcionTipoAnotacion(String id) {
		logger.info("Obteniendo la descripcion del tipo de anotacion de intercambio del asiento registral [{}]", id);

		String codigoIntercambio = (String) getSqlMapClientTemplate()
				.queryForObject(GET_DESCRIPCION_TIPO_ANOTACION_INTERCAMBIO, id);

		return codigoIntercambio;
	}
}
