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
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.BandejaSalidaIntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaSalidaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIRSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralSalidaVO;

public class IbatisBandejaSalidaIntercambioRegistralDAOImpl implements
BandejaSalidaIntercambioRegistralDAO {

private SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();

private static final Logger logger = Logger.getLogger(IbatisBandejaSalidaIntercambioRegistralDAOImpl.class);

private static final String SAVE_INTERCAMBIO_REGISTRAL_SALIDA = "IntercambioRegistralSalidaVO.addIntercambioRegistralSalidaVO";

private static final String GET_BY_ID_INTERCAMBIO_REGISTRAL_SIR_OFICINA = "IntercambioRegistralSalidaVO.getIntercambiosRegistralesByIdIntercambioRegistralSirOficina";

private static final String GET_BANDEJA_SALIDA_ITEM_REGISTRO = "IntercambioRegistralSalidaVO.getBandejaSalidaItemRegistro";
private static final String GET_INTERCAMBIO_REGISTRAL_SALIDA_BY_ID = "IntercambioRegistralSalidaVO.getIntercambioRegistralSalidaById";
private static final String GET_BANDEJA_SALIDA_BY_ESTADO_OFICINA = "IntercambioRegistralSalidaVO.getBandejaSalidaByEstadoOficina";
private static final String GET_BANDEJA_SALIDA_BY_ID_INTERCAMBIO_REGISTRAL_SIR_OFICINA = "IntercambioRegistralSalidaVO.getBandejaSalidaByIdIntercambioRegistralSirOficina";

private static final String GET_BANDEJA_SALIDA_BY_ESTADO_OFICINA_Y_LIBRO = "IntercambioRegistralSalidaVO.getBandejaSalidaByEstadoOficinaYLibro";
private static final String UPDATE_INTERCAMBIO_REGISTRAL_SALIDA = "IntercambioRegistralSalidaVO.updateIntercambioRegistralSalidaVO";
private static final String UPDATE_STATE_INTERCAMBIO_REGISTRAL_SALIDA = "IntercambioRegistralSalidaVO.updateStateIntercambioRegistralSalidaVO";

private static final String DELETE_INTERCAMBIO_REGISTRAL_SALIDA_BY_ID_ARCH_ID_FDR = "IntercambioRegistralSalidaVO.deleteIntercambioRegistralSalidaByIdArchIdFolderVO";
private static final String GET_INTERCAMBIOS_REGISTRALES = "IntercambioRegistralSalidaVO.getIntercambiosRegistralesSalida";
private static final String GET_INTERCAMBIOS_REGISTRALES_BY_ID_REG_ID_LIBRO = "IntercambioRegistralSalidaVO.getIntercambiosRegistralesByIdRegistroIdLibroIdOficina";

private static final String SAVE_DETALLE_ESTADO_INTERCAMBIO_REGISTRAL_SALIDA = "IntercambioRegistralSalidaVO.addDetalleEstadoIntercambioRegistralSalidaVO";
private static final String GET_DETALLE_ESTADO_INTERCAMBIO_REGISTRAL_SALIDA_BY_ID_EXREG="IntercambioRegistralSalidaVO.getHistorialEstadoByIntercambioRegistralSalida";

private static final String FIND_ASIENTOS_REGISTRALES = "IntercambioRegistralSalidaVO.findIntercambiosRegistrales";
private static final String COUNT_FIND_ASIENTOS_REGISTRALES = "IntercambioRegistralSalidaVO.countFindIntercambiosRegistrales";
private static final String FIND_BANDEJA_SALIDA_BY_CRITERIOS = "IntercambioRegistralSalidaVO.findBandejaSalidaByCriterios";
private static final String COUNT_FIND_BANDEJA_SALIDA_BY_CRITERIOS = "IntercambioRegistralSalidaVO.CountFindBandejaSalidaByCriterios";

public IntercambioRegistralSalidaVO get(Long id) {
try{
	IntercambioRegistralSalidaVO intercambioRegistralSalida = (IntercambioRegistralSalidaVO)getSqlMapClientTemplate().queryForObject(GET_INTERCAMBIO_REGISTRAL_SALIDA_BY_ID,id);
	return intercambioRegistralSalida;
}
catch (DataAccessException e) {
	logger.error("Error en la busqueda de un intercambio registral de salida", e);

	throw new RuntimeException(e);
}
}

public void save(IntercambioRegistralSalidaVO intecambioRegistralSalida) {
try{

	getSqlMapClientTemplate().insert(SAVE_INTERCAMBIO_REGISTRAL_SALIDA,intecambioRegistralSalida);
}
catch (DataAccessException e) {
	logger.error("Error en la insercción de un intercambio registral de salida", e);

	throw new RuntimeException(e);
}
}

public void saveDetalleEstado(EstadoIntercambioRegistralSalidaVO detalleEstadoIntercambioRegistral){
try{
	getSqlMapClientTemplate().insert(SAVE_DETALLE_ESTADO_INTERCAMBIO_REGISTRAL_SALIDA, detalleEstadoIntercambioRegistral);

}catch (DataAccessException e){
	logger.error("Error en la insercción de un intercambio registral de salida", e);

	throw new RuntimeException(e);
}
}

public void updateEstado(
	IntercambioRegistralSalidaVO intercambioRegistralSalida,
	EstadoIntercambioRegistralSalidaVO estado) {
try{
	intercambioRegistralSalida.setEstado(estado.getEstado());
	intercambioRegistralSalida.setFechaEstado(GregorianCalendar.getInstance().getTime());
	intercambioRegistralSalida.setComentarios(estado.getComentarios());
	getSqlMapClientTemplate().update(UPDATE_STATE_INTERCAMBIO_REGISTRAL_SALIDA, intercambioRegistralSalida);
}
catch (DataAccessException e) {
	logger.error("Error en la actualización de estado de un intercambio registral de salida", e);

	throw new RuntimeException(e);
}

}

public List<IntercambioRegistralSalidaVO> findByIdIntercambioRegistralSirYOficina(
	String idIntercambioRegistralSir, Integer idOficina) {
try{
	HashMap params = new HashMap();
	params.put("idIntercambioRegistralSir", idIntercambioRegistralSir);
	params.put("idOficina",idOficina);
	List<IntercambioRegistralSalidaVO> result = (List<IntercambioRegistralSalidaVO>)getSqlMapClientTemplate().queryForList(GET_BY_ID_INTERCAMBIO_REGISTRAL_SIR_OFICINA,params);
	return result;
}
catch (DataAccessException e) {
	logger.error("Error en la busqueda de bandeja de salida por estado y oficina", e);

	throw new RuntimeException(e);
}
}


public List<BandejaSalidaItemVO> getBandejaSalidaByIdIntercambioRegistralSirYOficina(String idIntercambioRegistralSir, Integer idOficina){
try{
	HashMap params = new HashMap();
	params.put("idIntercambioRegistralSir", idIntercambioRegistralSir);
	params.put("idOficina",idOficina);
	List<BandejaSalidaItemVO> bandejaSalida = (List<BandejaSalidaItemVO>)getSqlMapClientTemplate().queryForList(GET_BANDEJA_SALIDA_BY_ID_INTERCAMBIO_REGISTRAL_SIR_OFICINA,params);
	return bandejaSalida;
}
catch (DataAccessException e) {
	logger.error("Error en la busqueda de bandeja de salida por estado y oficina", e);

	throw new RuntimeException(e);
}
}



public List<BandejaSalidaItemVO> getBandejaSalidaByEstadoYOficina(Integer estado,Integer idOficina) {
try{
	HashMap<String, Integer> params = new HashMap<String, Integer>();
	params.put("estado", estado);
	params.put("idOficina",idOficina);
	List<BandejaSalidaItemVO> bandejaSalida = (List<BandejaSalidaItemVO>)getSqlMapClientTemplate().queryForList(GET_BANDEJA_SALIDA_BY_ESTADO_OFICINA,params);
	return bandejaSalida;
}
catch (DataAccessException e) {
	logger.error("Error en la busqueda de bandeja de salida por estado y oficina", e);

	throw new RuntimeException(e);
}
}

public List<BandejaSalidaItemVO> getBandejaSalidaByEstadoOficinaYLibro(Integer estado,
	Integer idOficina, Integer idLibro) {
try{
	HashMap<String, Integer> params = new HashMap<String, Integer>();
	params.put("estado", estado);
	params.put("idOficina",idOficina);
	params.put("idLibro", idLibro);
	List<BandejaSalidaItemVO> bandejaSalida = (List<BandejaSalidaItemVO>)getSqlMapClientTemplate().queryForList(GET_BANDEJA_SALIDA_BY_ESTADO_OFICINA_Y_LIBRO,params);
	return bandejaSalida;
}
catch (DataAccessException e) {
	logger.error("Error en la busqueda de bandeja de salida por estado y oficina", e);

	throw new RuntimeException(e);
}
}


public BandejaSalidaItemVO completarBandejaSalidaItem(BandejaSalidaItemVO bandejaSalidaItemVO) {
try{

	BandejaSalidaItemVO bandejaSalida = (BandejaSalidaItemVO)getSqlMapClientTemplate().queryForObject(GET_BANDEJA_SALIDA_ITEM_REGISTRO,bandejaSalidaItemVO);
	bandejaSalidaItemVO.setNumeroRegistro(bandejaSalida.getNumeroRegistro());
	bandejaSalidaItemVO.setFechaRegistro(bandejaSalida.getFechaRegistro());
	bandejaSalidaItemVO.setEstadoRegistro(bandejaSalida.getEstadoRegistro());
	return bandejaSalidaItemVO;
}
catch (DataAccessException e) {
	logger.error("Error en la completacion de un elemento de la bandeja de salida", e);
	throw new RuntimeException(e);
}
}

public List<IntercambioRegistralSalidaVO> getIntercambiosRegistralesSalida(
	Integer estado) {
try{

	List<IntercambioRegistralSalidaVO> intercambiosSalida = (List<IntercambioRegistralSalidaVO>)getSqlMapClientTemplate().queryForList(GET_INTERCAMBIOS_REGISTRALES,estado);
	return intercambiosSalida;
}
catch (DataAccessException e) {
	logger.error("Error en la busqueda de intercambios registrales por estado", e);

	throw new RuntimeException(e);
}
}



public void deleteByIdArchIdFdr(Integer idLibro, Integer idRegistro, Integer idOficina){
try{
	HashMap<String, Integer> params = new HashMap<String, Integer>();
	params.put("idArch", idLibro);
	params.put("idFdr", idRegistro);
	params.put("idOficina", idOficina);
	getSqlMapClientTemplate().delete(DELETE_INTERCAMBIO_REGISTRAL_SALIDA_BY_ID_ARCH_ID_FDR, params);
}
catch (DataAccessException e) {
	logger.error("Error en la eliminacion de un intercambio registral de salida", e);

	throw new RuntimeException(e);
}
}

public void updateIntercambioRegistralSalidaVO(IntercambioRegistralSalidaVO intercambioRegistralSalida)
{
try{
	getSqlMapClientTemplate().update(UPDATE_INTERCAMBIO_REGISTRAL_SALIDA, intercambioRegistralSalida);
}
catch (DataAccessException e) {
	logger.error("Error en la actualización de un intercambio registral de salida", e);

	throw new RuntimeException(e);
}
}


/**
* {@inheritDoc}
*/
public List<IntercambioRegistralSalidaVO> getIntercambiosRegistralesSalida(
	Integer idRegistro, Integer idLibro, Integer idOficina) {
try{
	HashMap<String, Integer> params = new HashMap<String, Integer>();
	params.put("idLibro", idLibro);
	params.put("idRegistro", idRegistro);
	params.put("idOficina", idOficina);

	// obtenemos los intercambios registrales de un registro
	List<IntercambioRegistralSalidaVO> intercambios = (List<IntercambioRegistralSalidaVO>) getSqlMapClientTemplate()
			.queryForList(GET_INTERCAMBIOS_REGISTRALES_BY_ID_REG_ID_LIBRO,
					params);

	return intercambios;
}
catch (DataAccessException e) {
	logger.error("Error en la getIntercambiosRegistralesSalida de un intercambio registral de salida", e);

	throw new RuntimeException(e);
}
}

public List<EstadoIntercambioRegistralSalidaVO> getDetalleEstadosIntercambioRegistralSalida(Long idExReg){
List<EstadoIntercambioRegistralSalidaVO> result = null;

try{
	result = (List<EstadoIntercambioRegistralSalidaVO>) getSqlMapClientTemplate()
			.queryForList(GET_DETALLE_ESTADO_INTERCAMBIO_REGISTRAL_SALIDA_BY_ID_EXREG, idExReg);

}catch (DataAccessException e) {
	logger.error("Error en la busqueda de intercambios registrales por id de Intercambio", e);
	throw new RuntimeException(e);
}

return result;
}

public List<BandejaSalidaItemVO> findByCriterios(
	EstadoIntercambioRegistralSalidaEnumVO estado,
	CriteriosBusquedaIRSalidaVO criterios) {

logger.debug("Realizando búsqueda de asientos registrales en base a los siguientes criterios: '"
		+ criterios + "'");

Map<String, Object> map = new HashMap<String, Object>();

map.put("estado", estado.getValue());
map.put("criterios", criterios.getCriterios());
map.put("orden", criterios.getOrderBy());

try {
	List<BandejaSalidaItemVO> result = null;

	result = (List<BandejaSalidaItemVO>) getSqlMapClientTemplate()
			.queryForList(FIND_ASIENTOS_REGISTRALES, map);

	return result;
} catch (DataAccessException e) {
	logger.error(
			"Error al obtener la búsqueda de intercambios registrales",
			e);

	throw new RuntimeException(e);
}
}



public List<BandejaSalidaItemVO> findByCriterios(
	EstadoIntercambioRegistralSalidaEnumVO estado,
	CriteriosBusquedaIRSalidaVO criterios, Integer idLibro) {
logger.debug("Realizando búsqueda de asientos registrales en base a los siguientes criterios: '"
		+ criterios + "'");

Map<String, Object> map = new HashMap<String, Object>();

map.put("estado", estado.getValue());
map.put("criterios", criterios.getCriterios());
map.put("orden", criterios.getOrderBy());
map.put("idLibro", idLibro);

try {
	List<BandejaSalidaItemVO> result = null;

	result = (List<BandejaSalidaItemVO>) getSqlMapClientTemplate()
			.queryForList(FIND_BANDEJA_SALIDA_BY_CRITERIOS, map);

	return result;
} catch (DataAccessException e) {
	logger.error(
			"Error al obtener la búsqueda de intercambios registrales",
			e);

	throw new RuntimeException(e);
}
}

public PaginatedArrayList<BandejaSalidaItemVO> findByCriterios(
	EstadoIntercambioRegistralSalidaEnumVO estado,
	CriteriosBusquedaIRSalidaVO criterios, PageInfo pageInfo) {

logger.debug("Realizando búsqueda de asientos registrales en base a los siguientes criterios: '"
		+ criterios + "'");

Map<String, Object> map = new HashMap<String, Object>();

map.put("estado", estado.getValue());
map.put("criterios", criterios.getCriterios());
map.put("orden", criterios.getOrderBy());

try {
	List<BandejaSalidaItemVO> result = null;

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

		result = (List<BandejaSalidaItemVO>) getSqlMapClientTemplate()
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
		PaginatedArrayList<BandejaSalidaItemVO> resultados = new PaginatedArrayList<BandejaSalidaItemVO>(
				pageInfo);
		resultados.setFullListSize(fullListSize);
		resultados.setList(result);
		return resultados;

	} else {
		result = (List<BandejaSalidaItemVO>) getSqlMapClientTemplate()
				.queryForList(FIND_ASIENTOS_REGISTRALES, map);
		// Información de los resultados paginados
		PaginatedArrayList<BandejaSalidaItemVO> resultados = new PaginatedArrayList<BandejaSalidaItemVO>(
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



public PaginatedArrayList<BandejaSalidaItemVO> findByCriterios(
	EstadoIntercambioRegistralSalidaEnumVO estado,
	CriteriosBusquedaIRSalidaVO criterios, Integer idLibro,
	PageInfo pageInfo) {
logger.debug("Realizando búsqueda de asientos registrales en base a los siguientes criterios: '"
		+ criterios + "'");

Map<String, Object> map = new HashMap<String, Object>();

map.put("estado", estado.getValue());
map.put("criterios", criterios.getCriterios());
map.put("orden", criterios.getOrderBy());
map.put("idLibro", idLibro);

try {
	List<BandejaSalidaItemVO> result = null;

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

		result = (List<BandejaSalidaItemVO>) getSqlMapClientTemplate()
				.queryForList(FIND_BANDEJA_SALIDA_BY_CRITERIOS, map,
						skipResults, maxResults);
		// Obtener el total de resultados
		int fullListSize;
		if (((maxResults == SqlExecutor.NO_MAXIMUM_RESULTS) || (result
				.size() < maxResults))
				&& (skipResults == SqlExecutor.NO_SKIPPED_RESULTS)) {
			fullListSize = result.size();
		} else {
			fullListSize = (Integer) getSqlMapClientTemplate()
					.queryForObject(COUNT_FIND_BANDEJA_SALIDA_BY_CRITERIOS,
							map);
		}

		// Información de los resultados paginados
		PaginatedArrayList<BandejaSalidaItemVO> resultados = new PaginatedArrayList<BandejaSalidaItemVO>(
				pageInfo);
		resultados.setFullListSize(fullListSize);
		resultados.setList(result);
		return resultados;

	} else {
		result = (List<BandejaSalidaItemVO>) getSqlMapClientTemplate()
				.queryForList(FIND_BANDEJA_SALIDA_BY_CRITERIOS, map);
		// Información de los resultados paginados
		PaginatedArrayList<BandejaSalidaItemVO> resultados = new PaginatedArrayList<BandejaSalidaItemVO>(
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
}	}

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
