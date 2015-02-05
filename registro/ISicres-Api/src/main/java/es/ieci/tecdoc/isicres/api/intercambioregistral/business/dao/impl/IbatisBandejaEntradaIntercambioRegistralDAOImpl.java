package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.BandejaEntradaIntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaEntradaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralEntradaVO;

public class IbatisBandejaEntradaIntercambioRegistralDAOImpl implements
		BandejaEntradaIntercambioRegistralDAO {

	private SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();
	private static final Logger logger = Logger.getLogger(IbatisBandejaEntradaIntercambioRegistralDAOImpl.class);
	private static final String SAVE_INTERCAMBIO_REGISTRAL_ENTRADA = "IntercambioRegistralEntradaVO.addIntercambioRegistralEntradaVO";
	private static final String GET_BANDEJA_ENTRADA_BY_ESTADO = "IntercambioRegistralEntradaVO.getBandejaEntradaByEstado";
	private static final String GET_BANDEJA_ENTRADA_ITEM_REGISTRO = "IntercambioRegistralEntradaVO.getBandejaEntradaItemRegistro";
	private static final String GET_INFO_ESTADO_BY_REGISTRO = "IntercambioRegistralEntradaVO.getBandejaEntradaByRegistro";



	public void save(IntercambioRegistralEntradaVO intercambioRegistralEntrada) {
		try{

			getSqlMapClientTemplate().insert(SAVE_INTERCAMBIO_REGISTRAL_ENTRADA,intercambioRegistralEntrada);
		}
		catch (DataAccessException e) {
			logger.error("Error en la insercción de un intercambio registral de entrada", e);

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
