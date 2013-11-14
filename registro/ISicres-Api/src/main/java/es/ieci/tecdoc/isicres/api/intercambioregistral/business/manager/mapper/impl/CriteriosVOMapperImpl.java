package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriterioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralException;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.ConfiguracionIntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.mapper.CriteriosVOMapper;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.types.CriterioBusquedaIREntradaEnum;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.types.OperadorCriterioBusquedaIREnum;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriterioBusquedaIREntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIREntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralEntradaEnumVO;

public class CriteriosVOMapperImpl implements CriteriosVOMapper {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CriteriosVOMapper.class);

	/**
	 * Manager para leer configuraciones de intercmabios registrales (mapeos de
	 * entidades registrales y unidades de tramitacion)
	 */
	protected ConfiguracionIntercambioRegistralManager configuracionIntercambioRegistralManager;

	public CriteriosVO map(
			CriteriosBusquedaIREntradaVO criteriosBusquedaIntercambioRegistralVO) {
		CriteriosVO criteriosVO = null;

		if (criteriosBusquedaIntercambioRegistralVO != null) {
			criteriosVO = new CriteriosVO();
			if (CollectionUtils
					.isNotEmpty(criteriosBusquedaIntercambioRegistralVO
							.getCriterios())) {
				List<CriterioVO> lista = new ArrayList<CriterioVO>();
				for (CriterioBusquedaIREntradaVO criterioBusquedaIntercambioRegistralVO : criteriosBusquedaIntercambioRegistralVO
						.getCriterios()) {
					CriterioVO criterio = map(criterioBusquedaIntercambioRegistralVO);
					lista.add(criterio);
				}
				criteriosVO.setCriterios(lista);
			}
			if (CollectionUtils
					.isNotEmpty(criteriosBusquedaIntercambioRegistralVO
							.getOrderBy())) {
				List<CriterioEnum> orderByList = new ArrayList<CriterioEnum>();
				for (CriterioBusquedaIREntradaEnum criterioBusquedaIREnum : criteriosBusquedaIntercambioRegistralVO
						.getOrderBy()) {
					CriterioEnum criterioEnum = map(criterioBusquedaIREnum);
					orderByList.add(criterioEnum);
				}
				criteriosVO.setOrderBy(orderByList);
			}
		}
		return criteriosVO;
	}

	public CriterioVO map(
			CriterioBusquedaIREntradaVO criterioBusquedaIntercambioRegistralVO) {
		if (logger.isDebugEnabled()) {
			logger.debug("map(CriterioBusquedaIREntradaVO) - start");
		}

		CriterioVO criterioVO = null;

		if (criterioBusquedaIntercambioRegistralVO != null) {
			criterioVO = new CriterioVO();
			if (criterioBusquedaIntercambioRegistralVO.getNombre().equals(
					CriterioBusquedaIREntradaEnum.ID_OFICINA)) {
				mapearCriterioOficina(criterioBusquedaIntercambioRegistralVO,
						criterioVO);
			} else {
				if (criterioBusquedaIntercambioRegistralVO.getNombre().equals(
						CriterioBusquedaIREntradaEnum.STATE)) {
					mapearCriterioEstado(
							criterioBusquedaIntercambioRegistralVO, criterioVO);

				} else {
					mapearCriterioGenerico(
							criterioBusquedaIntercambioRegistralVO, criterioVO);
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("map(CriterioBusquedaIREntradaVO) - end");
		}
		return criterioVO;
	}

	private void mapearCriterioEstado(
			CriterioBusquedaIREntradaVO criterioBusquedaIntercambioRegistralVO,
			CriterioVO criterioVO) {
		criterioVO.setNombre(map(criterioBusquedaIntercambioRegistralVO
				.getNombre()));
		criterioVO.setOperador(map(criterioBusquedaIntercambioRegistralVO
				.getOperador()));
		if (criterioBusquedaIntercambioRegistralVO.getValor() instanceof EstadoIntercambioRegistralEntradaEnumVO) {
			EstadoIntercambioRegistralEntradaEnumVO estadoIntercambioRegistralEntradaEnumVO = (EstadoIntercambioRegistralEntradaEnumVO) criterioBusquedaIntercambioRegistralVO
					.getValor();
			criterioVO
					.setValor(mapearEstadoAsientoEnum(estadoIntercambioRegistralEntradaEnumVO));
		}else {
			throw new IntercambioRegistralException("Error al mapear el criterio de estado. El valor del operador debe de ser de la clase EstadoIntercambioRegistralEntradaEnumVO");
		}
	}

	private void mapearCriterioGenerico(
			CriterioBusquedaIREntradaVO criterioBusquedaIntercambioRegistralVO,
			CriterioVO criterioVO) {
		criterioVO.setNombre(map(criterioBusquedaIntercambioRegistralVO
				.getNombre()));
		criterioVO.setOperador(map(criterioBusquedaIntercambioRegistralVO
				.getOperador()));
		criterioVO.setValor(criterioBusquedaIntercambioRegistralVO.getValor());

	}

	private void mapearCriterioOficina(
			CriterioBusquedaIREntradaVO criterioBusquedaIntercambioRegistralVO,
			CriterioVO criterioVO) {
		String idOficina = String
				.valueOf(criterioBusquedaIntercambioRegistralVO.getValor());
		// Para los pendientes realizamos la consulta contra el sir
		EntidadRegistralVO entidadRegistralDestino = getConfiguracionIntercambioRegistralManager()
				.getEntidadRegistralVOByIdScrOfic(idOficina);
		criterioVO.setValor(entidadRegistralDestino.getCode());
		criterioVO.setOperador(map(criterioBusquedaIntercambioRegistralVO
				.getOperador()));
		criterioVO.setNombre(map(criterioBusquedaIntercambioRegistralVO
				.getNombre()));
	}

	private EstadoAsientoRegistralEnum mapearEstadoAsientoEnum(
			EstadoIntercambioRegistralEntradaEnumVO estadoIntercambioRegistralEntradaEnumVO) {
		switch (estadoIntercambioRegistralEntradaEnumVO.getValue()) {
		case EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO_VALUE:
			return EstadoAsientoRegistralEnum.ACEPTADO;
		case EstadoIntercambioRegistralEntradaEnumVO.PENDIENTE_VALUE:
			return EstadoAsientoRegistralEnum.RECIBIDO;
		case EstadoIntercambioRegistralEntradaEnumVO.RECHAZADO_VALUE:
			return EstadoAsientoRegistralEnum.RECHAZADO;
		case EstadoIntercambioRegistralEntradaEnumVO.REENVIADO_VALUE:
			return EstadoAsientoRegistralEnum.REENVIADO;
		default:
			throw new IntercambioRegistralException(
					"No se ha encontrado un estado equivalente en el SIR");
		}
	}

	public CriterioEnum map(CriterioBusquedaIREntradaEnum criterioBusquedaIREnum) {
		if (logger.isDebugEnabled()) {
			logger.debug("map(CriterioBusquedaIREntradaEnum) - start");
		}

		if (criterioBusquedaIREnum
				.equals(CriterioBusquedaIREntradaEnum.CODE_ENTITY)) {

			return CriterioEnum.ASIENTO_CODIGO_ENTIDAD_REGISTRAL;
		}

		if (criterioBusquedaIREnum
				.equals(CriterioBusquedaIREntradaEnum.CONTACTO_ORIGINAL)) {

			return CriterioEnum.ASIENTO_CONTACTO_USUARIO;
		}
		if (criterioBusquedaIREnum
				.equals(CriterioBusquedaIREntradaEnum.EXCHANGE_DATE)) {

			return CriterioEnum.ASIENTO_FECHA_ENVIO;
		}
		if (criterioBusquedaIREnum
				.equals(CriterioBusquedaIREntradaEnum.ID_EXCHANGE_SIR)) {

			return CriterioEnum.ASIENTO_IDENTIFICADOR_INTERCAMBIO;
		}
		if (criterioBusquedaIREnum
				.equals(CriterioBusquedaIREntradaEnum.STATE_DATE)) {

			return CriterioEnum.ASIENTO_FECHA_ESTADO;
		}
		if (criterioBusquedaIREnum
				.equals(CriterioBusquedaIREntradaEnum.CODE_TRAMUNIT)) {

			return CriterioEnum.ASIENTO_CODIGO_UNIDAD_TRAMITACION_ORIGEN;
		}
		if (criterioBusquedaIREnum
				.equals(CriterioBusquedaIREntradaEnum.COMMENTS)) {

			return CriterioEnum.ASIENTO_OBSERVACIONES_APUNTE;
		}
		if (criterioBusquedaIREnum
				.equals(CriterioBusquedaIREntradaEnum.NAME_ENTITY)) {

			return CriterioEnum.ASIENTO_DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN;
		}
		if (criterioBusquedaIREnum
				.equals(CriterioBusquedaIREntradaEnum.NAME_TRAMUNIT)) {

			return CriterioEnum.ASIENTO_DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN;
		}
		if (criterioBusquedaIREnum
				.equals(CriterioBusquedaIREntradaEnum.NUM_REG_ORIGINAL)) {

			return CriterioEnum.ASIENTO_NUMERO_REGISTRO_INICIAL;
		}
		if (criterioBusquedaIREnum
				.equals(CriterioBusquedaIREntradaEnum.USERNAME)) {
			return CriterioEnum.ASIENTO_CONTACTO_USUARIO;
		}
		if (criterioBusquedaIREnum
				.equals(CriterioBusquedaIREntradaEnum.ID_OFICINA)) {
			return CriterioEnum.ASIENTO_CODIGO_ENTIDAD_REGISTRAL_DESTINO;
		}
		if (criterioBusquedaIREnum.equals(CriterioBusquedaIREntradaEnum.STATE)){
			return CriterioEnum.ASIENTO_ESTADO;
		}

		else {
			logger.error("No se ha encontrado el criterio de busqueda equivalente para el sir");
			throw new IntercambioRegistralException(
					"No se ha encontrado el criterio de busqueda equivalente para el sir");
		}

	}

	private OperadorCriterioEnum map(
			OperadorCriterioBusquedaIREnum operadorCriterioBusquedaIREnum) {
		if (logger.isDebugEnabled()) {
			logger.debug("map(OperadorCriterioBusquedaIREnum) - start");
		}

		if (operadorCriterioBusquedaIREnum.equals(OperadorCriterioBusquedaIREnum.EQUAL)) {

			return OperadorCriterioEnum.EQUAL;
		}
		if (operadorCriterioBusquedaIREnum
				.equals(OperadorCriterioBusquedaIREnum.EQUAL_OR_GREATER_THAN)) {

			return OperadorCriterioEnum.EQUAL_OR_GREATER_THAN;
		}
		if (operadorCriterioBusquedaIREnum
				.equals(OperadorCriterioBusquedaIREnum.EQUAL_OR_LESS_THAN)) {

			return OperadorCriterioEnum.EQUAL_OR_LESS_THAN;
		}
		if (operadorCriterioBusquedaIREnum
				.equals(OperadorCriterioBusquedaIREnum.GREATER_THAN)) {

			return OperadorCriterioEnum.GREATER_THAN;
		}
		if (operadorCriterioBusquedaIREnum.equals(OperadorCriterioBusquedaIREnum.IN)) {

			return OperadorCriterioEnum.IN;
		}
		if (operadorCriterioBusquedaIREnum
				.equals(OperadorCriterioBusquedaIREnum.LESS_THAN)) {

			return OperadorCriterioEnum.LESS_THAN;
		}
		if (operadorCriterioBusquedaIREnum.equals(OperadorCriterioBusquedaIREnum.LIKE)) {

			return OperadorCriterioEnum.LIKE;
		}
		if (operadorCriterioBusquedaIREnum
				.equals(OperadorCriterioBusquedaIREnum.NOT_EQUAL)) {
			return OperadorCriterioEnum.NOT_EQUAL;
		} else {
			logger.error("No se ha encontrado un operador equipavelente para el SIR");
			throw new IntercambioRegistralException(
					"No se ha encontrado un operador equipavelente para el SIR");
		}
	}

	public ConfiguracionIntercambioRegistralManager getConfiguracionIntercambioRegistralManager() {
		return configuracionIntercambioRegistralManager;
	}

	public void setConfiguracionIntercambioRegistralManager(
			ConfiguracionIntercambioRegistralManager configuracionIntercambioRegistralManager) {
		this.configuracionIntercambioRegistralManager = configuracionIntercambioRegistralManager;
	}

}
