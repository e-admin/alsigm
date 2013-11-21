package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.impl;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;

import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.server.pagination.PaginatedArrayList;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.BandejaEntradaIntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaEntradaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIREntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralEntradaEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralEntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralSalidaVO;

public class IbatisBandejaEntradaIntercambioRegistralDAOImpl implements
		BandejaEntradaIntercambioRegistralDAO {



	private SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();
	private static final Logger logger = Logger.getLogger(IbatisBandejaEntradaIntercambioRegistralDAOImpl.class);

	private static final String GET_INTERCAMBIO_REGISTRAL_ENTRADA_BY_REGISTRO_SQLMAP = "IntercambioRegistralEntradaVO.getIntercambioRegistralEntradaByRegistro";
	private static final String GET_INTERCAMBIO_REGISTRAL_ENTRADA_BY_ID_INTERCAMBIO_SIR_IDOFI = "IntercambioRegistralEntradaVO.getIntercambioRegistralEntradaByIdIntercambioRegistralSirOfi";
	private static final String DELETE_INTERCAMBIO_REGISTRAL_ENTRADA = "IntercambioRegistralEntradaVO.deleteIntercambioRegistralEntradaVO";
	private static final String SAVE_INTERCAMBIO_REGISTRAL_ENTRADA = "IntercambioRegistralEntradaVO.addIntercambioRegistralEntradaVO";
	private static final String GET_BANDEJA_ENTRADA_BY_ESTADO = "IntercambioRegistralEntradaVO.getBandejaEntradaByEstado";
	private static final String GET_BANDEJA_ENTRADA_ITEM_REGISTRO = "IntercambioRegistralEntradaVO.getBandejaEntradaItemRegistro";
	private static final String GET_INFO_ESTADO_BY_REGISTRO = "IntercambioRegistralEntradaVO.getBandejaEntradaByRegistro";
	private static final String FIND_ASIENTOS_REGISTRALES = "IntercambioRegistralEntradaVO.findIntercambiosRegistrales";
	private static final String COUNT_FIND_ASIENTOS_REGISTRALES = "IntercambioRegistralEntradaVO.countFindIntercambiosRegistrales";
	private static final String UPDATE_ESTADO_INTERCAMBIO_ENTRADA = "IntercambioRegistralEntradaVO.updateIntercambioRegistralEntradaVO";


	public void save(IntercambioRegistralEntradaVO intercambioRegistralEntrada) {
		try{

			getSqlMapClientTemplate().insert(SAVE_INTERCAMBIO_REGISTRAL_ENTRADA,intercambioRegistralEntrada);
		}
		catch (DataAccessException e) {
			logger.error("Error en la insercción de un intercambio registral de entrada", e);

			throw new RuntimeException(e);
		}
	}

	public void delete(IntercambioRegistralEntradaVO intercambioRegistralEntrada) {
		try{

			getSqlMapClientTemplate().delete(DELETE_INTERCAMBIO_REGISTRAL_ENTRADA,intercambioRegistralEntrada);
		}
		catch (DataAccessException e) {
			logger.error("Error en la insercción de un intercambio registral de entrada", e);

			throw new RuntimeException(e);
		}
	}


	public void updateEstado(
			IntercambioRegistralEntradaVO intercambioRegistralEntrada,
			EstadoIntercambioRegistralEntradaEnumVO estado) {
		try {
			intercambioRegistralEntrada.setEstado(estado);
			intercambioRegistralEntrada.setFechaEstado(GregorianCalendar
					.getInstance().getTime());
			getSqlMapClientTemplate().update(UPDATE_ESTADO_INTERCAMBIO_ENTRADA,
					intercambioRegistralEntrada);
		} catch (DataAccessException e) {
			logger.error(
					"Error en la actualización de estado de un intercambio registral de salida",
					e);

			throw new RuntimeException(e);
		}

	}


	public List<IntercambioRegistralEntradaVO> getInfoEstado(
			IntercambioRegistralEntradaVO intecambioRegistralEntrada) {
		try{
			List<IntercambioRegistralEntradaVO> result = null;

			result = (List<IntercambioRegistralEntradaVO>) getSqlMapClientTemplate()
					.queryForList(GET_INFO_ESTADO_BY_REGISTRO,
							intecambioRegistralEntrada);

			return result;
		}catch (DataAccessException e) {
			logger.error("Error al obtener la información del estado del Intercambio Registral por registro", e);

			throw new RuntimeException(e);
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.BandejaEntradaIntercambioRegistralDAO#getIntercambioRegistralEntradaByRegistro(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public IntercambioRegistralEntradaVO getIntercambioRegistralEntradaByRegistro(
			Integer idLibro, Integer idRegistro, Integer estado) {
		if (logger.isDebugEnabled()) {
			logger.debug("getIntercambioRegistralEntradaByRegistro(Integer idLibro, Integer idRegistro, Integer idOficina,Integer estado) - start (): ["
					+ idLibro
					+ ","
					+ idRegistro
					+ ","
					+ estado + "]");
		}
		try {
			IntercambioRegistralEntradaVO result = null;
			HashMap<String, Integer> params = new HashMap<String, Integer>();
			params.put("estado", estado);
			params.put("idLibro", idLibro);
			params.put("idRegistro", idRegistro);
			result = (IntercambioRegistralEntradaVO) getSqlMapClientTemplate()
					.queryForObject(
							GET_INTERCAMBIO_REGISTRAL_ENTRADA_BY_REGISTRO_SQLMAP,
							params);

			if (logger.isDebugEnabled()) {
				logger.debug("getIntercambioRegistralEntradaByRegistro(Integer, Integer, Integer, Integer) - end");
			}
			return result;
		} catch (DataAccessException e) {
			logger.error(
					"Error al obtener la información del Intercambio Registral por registro",
					e);

			throw new RuntimeException(e);
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	public List <IntercambioRegistralEntradaVO> getIntercambioRegistralEntradaByIdIntercambioRegistralSir(
			Integer idOficina, String idIntercambioRegistralSir) {
		if (logger.isDebugEnabled()) {
			logger.debug("getIntercambioRegistralEntradaByRegistro(Integer idOficina,String idIntercambioRegistralSir) - start (): ["
					+ idOficina
					+ ","
					+ idIntercambioRegistralSir
					+  "]");
		}
		try {
			List <IntercambioRegistralEntradaVO> result = null;
			HashMap params = new HashMap();
			params.put("idOficina", idOficina);
			params.put("idIntercambioRegistralSir", idIntercambioRegistralSir);

			result = (List<IntercambioRegistralEntradaVO>) getSqlMapClientTemplate()
					.queryForList(
							GET_INTERCAMBIO_REGISTRAL_ENTRADA_BY_ID_INTERCAMBIO_SIR_IDOFI,
							params);

			if (logger.isDebugEnabled()) {
				logger.debug("getIntercambioRegistralEntradaByRegistro(Integer, String) - end");
			}
			return result;
		} catch (DataAccessException e) {
			logger.error(
					"Error al obtener la información del Intercambio Registral por idOficina e idIntercmabioRegistralSir",
					e);

			throw new RuntimeException(e);
		}
	}


	public List<BandejaEntradaItemVO> getBandejaEntradaByEstado(Integer estado, Integer idOficina) {
		try{
			HashMap<String, Integer> params = new HashMap<String, Integer>();
			params.put("estado", estado);
			params.put("idOfic", idOficina);

			List<BandejaEntradaItemVO> bandejaEntrada = (List<BandejaEntradaItemVO>)getSqlMapClientTemplate().queryForList(GET_BANDEJA_ENTRADA_BY_ESTADO,params);

			return bandejaEntrada;
		}
		catch (DataAccessException e) {
			logger.error("Error en la busqueda de bandeja de entrada por estado y oficina", e);

			throw new RuntimeException(e);
		}
	}

	public BandejaEntradaItemVO completarBandejaEntradaItem(BandejaEntradaItemVO bandejaEntradaItemVO) {
		try{

			BandejaEntradaItemVO bandejaEntrada = (BandejaEntradaItemVO)getSqlMapClientTemplate().queryForObject(GET_BANDEJA_ENTRADA_ITEM_REGISTRO,bandejaEntradaItemVO);
			bandejaEntradaItemVO.setNumeroRegistro(bandejaEntrada.getNumeroRegistro());
			bandejaEntradaItemVO.setOrigen(bandejaEntrada.getOrigen());
			bandejaEntradaItemVO.setFechaRegistro(bandejaEntrada.getFechaRegistro());
			bandejaEntradaItemVO.setOrigenName(bandejaEntrada.getOrigenName());
			return bandejaEntradaItemVO;
		}
		catch (DataAccessException e) {
			logger.error("Error en la completacion de un elemento de la bandeja de entrada", e);

			throw new RuntimeException(e);
		}
	}

	public List<BandejaEntradaItemVO> findByCriterios(
			EstadoIntercambioRegistralEntradaEnumVO estado,
			CriteriosBusquedaIREntradaVO criterios) {

		logger.debug("Realizando búsqueda de asientos registrales en base a los siguientes criterios: '"
				+ criterios + "'");

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("estado", estado.getValue());
		map.put("criterios", criterios.getCriterios());
		map.put("orden", criterios.getOrderBy());

		try {
			List<BandejaEntradaItemVO> result = null;

			result = (List<BandejaEntradaItemVO>) getSqlMapClientTemplate()
					.queryForList(FIND_ASIENTOS_REGISTRALES, map);

			return result;
		} catch (DataAccessException e) {
			logger.error(
					"Error al obtener la búsqueda de intercambios registrales",
					e);

			throw new RuntimeException(e);
		}
	}


	public PaginatedArrayList<BandejaEntradaItemVO> findByCriterios(
			EstadoIntercambioRegistralEntradaEnumVO estado,
			CriteriosBusquedaIREntradaVO criterios, PageInfo pageInfo) {

		logger.debug("Realizando búsqueda de asientos registrales en base a los siguientes criterios: '"
				+ criterios + "'");

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("estado", estado.getValue());
		map.put("criterios", criterios.getCriterios());
		map.put("orden", criterios.getOrderBy());

		try {
			List<BandejaEntradaItemVO> result = null;

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

				result = (List<BandejaEntradaItemVO>) getSqlMapClientTemplate()
						.queryForList(FIND_ASIENTOS_REGISTRALES, map,
								skipResults, maxResults);
				// Obtener el total de resultados
				int fullListSize;
				if (((maxResults == SqlExecutor.NO_MAXIMUM_RESULTS) || (result
						.size() < maxResults))
						&& (skipResults == SqlExecutor.NO_SKIPPED_RESULTS)) {
					fullListSize = result.size();
				} else {
					fullListSize = (Integer) getSqlMapClientTemplate()
							.queryForObject(COUNT_FIND_ASIENTOS_REGISTRALES,
									map);
				}

				// Información de los resultados paginados
				PaginatedArrayList<BandejaEntradaItemVO> resultados = new PaginatedArrayList<BandejaEntradaItemVO>(
						pageInfo);
				resultados.setFullListSize(fullListSize);
				resultados.setList(result);
				return resultados;

			} else {
				result = (List<BandejaEntradaItemVO>) getSqlMapClientTemplate()
						.queryForList(FIND_ASIENTOS_REGISTRALES, map);
				// Información de los resultados paginados
				PaginatedArrayList<BandejaEntradaItemVO> resultados = new PaginatedArrayList<BandejaEntradaItemVO>(
						pageInfo);
				resultados.setFullListSize(result.size());
				resultados.setList(result);
				return resultados;
			}
		} catch (DataAccessException e) {
			logger.error(
					"Error al obtener la búsqueda de intercambios registrales",
					e);

			throw new RuntimeException(e);
		}
	}

	public final void setSqlMapClient(SqlMapClient aSqlMapClient) {
		this.sqlMapClientTemplate.setSqlMapClient(aSqlMapClient);
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}




}
