package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.impl;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.BandejaSalidaIntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaSalidaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriterioBusquedaBandejaSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralSalidaVO;

public class IbatisBandejaSalidaIntercambioRegistralDAOImpl implements
		BandejaSalidaIntercambioRegistralDAO {

	private SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();

	private static final Logger logger = Logger.getLogger(IbatisBandejaSalidaIntercambioRegistralDAOImpl.class);

	private static final String SAVE_INTERCAMBIO_REGISTRAL_SALIDA = "IntercambioRegistralSalidaVO.addIntercambioRegistralSalidaVO";
	private static final String GET_BANDEJA_SALIDA_MOCK = "IntercambioRegistralSalidaVO.getBandejaSalidaMock";
	private static final String GET_BANDEJA_SALIDA_ITEM_REGISTRO = "IntercambioRegistralSalidaVO.getBandejaSalidaItemRegistro";
	private static final String GET_INTERCAMBIO_REGISTRAL_SALIDA_BY_ID = "IntercambioRegistralSalidaVO.getIntercambioRegistralSalidaById";
	private static final String GET_BANDEJA_SALIDA_BY_ESTADO_OFICINA = "IntercambioRegistralSalidaVO.getBandejaSalidaByEstadoOficina";
	private static final String GET_BANDEJA_SALIDA_BY_ESTADO_OFICINA_Y_LIBRO = "IntercambioRegistralSalidaVO.getBandejaSalidaByEstadoOficinaYLibro";
	private static final String UPDATE_INTERCAMBIO_REGISTRAL_SALIDA = "IntercambioRegistralSalidaVO.updateIntercambioRegistralSalidaVO";
	private static final String UPDATE_STATE_INTERCAMBIO_REGISTRAL_SALIDA = "IntercambioRegistralSalidaVO.updateStateIntercambioRegistralSalidaVO";

	private static final String DELETE_INTERCAMBIO_REGISTRAL_SALIDA_BY_ID_ARCH_ID_FDR = "IntercambioRegistralSalidaVO.deleteIntercambioRegistralSalidaByIdArchIdFolderVO";
	private static final String GET_INTERCAMBIOS_REGISTRALES = "IntercambioRegistralSalidaVO.getIntercambiosRegistralesSalida";
	private static final String GET_INTERCAMBIOS_REGISTRALES_BY_ID_REG_ID_LIBRO = "IntercambioRegistralSalidaVO.getIntercambiosRegistralesByIdRegistroIdLibroIdOficina";

	private static final String SAVE_DETALLE_ESTADO_INTERCAMBIO_REGISTRAL_SALIDA = "IntercambioRegistralSalidaVO.addDetalleEstadoIntercambioRegistralSalidaVO";
	private static final String GET_DETALLE_ESTADO_INTERCAMBIO_REGISTRAL_SALIDA_BY_ID_EXREG="IntercambioRegistralSalidaVO.getHistorialEstadoByIntercambioRegistralSalida";

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

	public List<BandejaSalidaItemVO> findByCriterios(List<CriterioBusquedaBandejaSalidaVO> criterios) {
		try{
			//List<BandejaSalidaItemVO> bandejaSalida = getSqlMapClientTemplate().queryForList(FIND_BY_CRITERIOS,criterios);
			List<BandejaSalidaItemVO> bandejaSalida = (List<BandejaSalidaItemVO>)getSqlMapClientTemplate().queryForList(GET_BANDEJA_SALIDA_MOCK);
			return bandejaSalida;
		}
		catch (DataAccessException e) {
			logger.error("Error en la busqueda de bandeja de salida por criterios", e);

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
