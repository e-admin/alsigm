package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.keys.IDocKeys;

import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.server.pagination.PaginatedArrayList;
import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriterioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.api.business.keys.ConstantKeys;
import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.business.manager.RegistroManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.CampoAdicionalRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoFisicoVO;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PaginaDocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroOriginalVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoTransporteVO;
import es.ieci.tecdoc.isicres.api.business.vo.TransporteVO;
import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.contextholder.IsicresContextHolder;
import es.ieci.tecdoc.isicres.api.intercambio.registral.business.util.IntercambioRegistralConfiguration;
import es.ieci.tecdoc.isicres.api.intercambio.registral.business.util.IntercambioRegistralConfigurationKeys;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.BandejaEntradaIntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.BandejaSalidaIntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralException;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralExceptionCodes;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.ConfiguracionIntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.DireccionesIntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralEntradaManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralSIRManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.TipoTransporteIntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.mapper.AsientoRegistralMapper;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.mapper.CriteriosVOMapper;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.util.InteresadoUtils;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaEntradaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CiudadExReg;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIREntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralEntradaEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralEntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InteresadoExReg;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.PaisExReg;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.ProvinciaExReg;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.TipoRegistroEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.TipoTransporteIntercambioRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadAdministrativaIntercambioRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO;

public class IntercambioRegistralEntradaManagerImpl implements
		IntercambioRegistralEntradaManager {

	/**
	 *
	 */
	private static final String ESPACIO = " ";

	/**
	 *
	 */
	private static final String SALTO_LINEA = "\n";

	private static final String BLANK = ESPACIO;

	private static final int maxLengthCampoInteresado = 95;

	private static Logger logger = Logger
			.getLogger(IntercambioRegistralEntradaManagerImpl.class);

	protected IntercambioRegistralSIRManager intercambioRegistralSIRManager;

	/**
	 * Manager para leer configuraciones de intercmabios registrales (mapeos de
	 * entidades registrales y unidades de tramitacion)
	 */
	protected ConfiguracionIntercambioRegistralManager configuracionIntercambioRegistralManager;
	/**
	 * Manager para leer y actualizar la bandeja de entrada de intercambio
	 * registral
	 */
	protected BandejaEntradaIntercambioRegistralDAO bandejaEntradaIntercambioRegistralDAO;

	protected BandejaSalidaIntercambioRegistralDAO bandejaSalidaIntercambioRegistralDAO;

	/**
	 * Incrementer para obtener el id del intercambio registral de entrada
	 * aceptado
	 */
	protected DataFieldMaxValueIncrementer intercambioRegistralEntradaIncrementer;

	protected TipoTransporteIntercambioRegistralManager tipoTransporteIntercambioRegistralManager;

	protected DireccionesIntercambioRegistralManager direccionesIntercambioRegistralRegManager;

	protected CriteriosVOMapper criteriosVOMapper;

	public List<BandejaEntradaItemVO> getBandejaEntradaIntercambioRegistral(
			Integer estado, Integer idOficina) {

		List<BandejaEntradaItemVO> result = null;

		// para los aceptos o rechazados hacemos busqueda local

		if (EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO.getValue() == estado
				.intValue()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Obtenemos la bandeja de entrada para el estado ACEPTADOS");
			}
			result = getBandejaEntradaIntercambioRegistralAceptados(idOficina);
		}

		if (EstadoIntercambioRegistralEntradaEnumVO.RECHAZADO.getValue() == estado
				.intValue()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Obtenemos la bandeja de entrada para el estado rechazados");
			}
			result = getBandejaEntradaIntercambioRegistralRechazados(idOficina);
		}

		if (EstadoIntercambioRegistralEntradaEnumVO.PENDIENTE.getValue() == estado
				.intValue()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Obtenemos la bandeja de entrada para el estado pendiente");
			}
			result = getBandejaEntradaIntercambioRegistralPendientes(idOficina,
					estado);
		}

		if (EstadoIntercambioRegistralEntradaEnumVO.REENVIADO.getValue() == estado
				.intValue()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Obtenemos la bandeja de entrada para el estado reenviado");
			}
			result = getBandejaEntradaIntercambioRegistralReenviados(idOficina,
					estado);
		}

		return result;
	}

	/**
	 * Metodo que hace la busqueda en el modulo intermedio de la plataforma sir
	 *
	 * @param idOficina
	 * @param estado
	 * @return
	 */
	private List<BandejaEntradaItemVO> getBandejaEntradaIntercambioRegistralPendientes(
			Integer idOficina, Integer estado) {

		estado = convertEstadoToSIREnum(estado);

		if (logger.isDebugEnabled()) {
			logger.debug("Obtenemos la bandeja de entrada para el estado pendientes :"
					+ " y la oficina: " + idOficina);
		}

		// Para los pendientes realizamos la consulta contra el sir
		EntidadRegistralVO entidadRegistralDestino = getConfiguracionIntercambioRegistralManager()
				.getEntidadRegistralVOByIdScrOfic(String.valueOf(idOficina));
		List<BandejaEntradaItemVO> bandejaEntradaItems = new ArrayList<BandejaEntradaItemVO>();

		// Si entidadRegistralOrigen == null es que no hay oficina de
		// intercambio registral configurada para la instalación
		if (entidadRegistralDestino != null) {
			CriteriosVO criterios = new CriteriosVO();
			CriterioVO criterioEstado;
			CriterioVO criterioEntidadDestino = new CriterioVO(
					CriterioEnum.ASIENTO_CODIGO_ENTIDAD_REGISTRAL_DESTINO,
					entidadRegistralDestino.getCode());
			criterios.addCriterioVO(criterioEntidadDestino);

			criterioEstado = new CriterioVO(CriterioEnum.ASIENTO_ESTADO,
					OperadorCriterioEnum.IN,
					new Integer[] { EstadoAsientoRegistralEnum.RECIBIDO
							.getValue(),EstadoAsientoRegistralEnum.DEVUELTO.getValue() });
			criterios.addCriterioVO(criterioEstado);

			List<AsientoRegistralVO> bandejaEntrada = getIntercambioRegistralSIRManager()
					.findAsientosRegistrales(criterios);

			AsientoRegistralMapper mapper = new AsientoRegistralMapper();
			for (AsientoRegistralVO asientoRegistralVO : bandejaEntrada) {
				BandejaEntradaItemVO bandejaItem = mapper
						.toBandejaEntradaItemVO(asientoRegistralVO);
				bandejaEntradaItems.add(bandejaItem);
			}
		} else {
			logger.info("No hay configuracion de intercambio registral para esta oficina: "
					+ idOficina);
			throw new IntercambioRegistralException(
					"No se puede enviar porque esta oficina no tiene configurada su Entidad Registral del Directorio Común de Organismos.",
					IntercambioRegistralExceptionCodes.ERROR_CODE_OFICINA_NO_MAPEADA);
		}
		return bandejaEntradaItems;
	}

	/**
	 * Método que hace la busqueda en el modulo intermedio de la plataforma sir
	 * los intercambios en estado reenviado.
	 *
	 * @param idOficina
	 * @param estado
	 * @return Listado de intercambios reenviados
	 *
	 */
	private List<BandejaEntradaItemVO> getBandejaEntradaIntercambioRegistralReenviados(
			Integer idOficina, Integer estado) {

		return getBandejaEntradaIntercambioRegistralDAO()
				.getBandejaEntradaByEstado(
						EstadoIntercambioRegistralEntradaEnumVO.REENVIADO_VALUE,
						idOficina);
	}

	private Integer convertEstadoToSIREnum(Integer estado) {

		if (estado == EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO_VALUE) {
			estado = EstadoAsientoRegistralEnum.ACEPTADO.getValue();
		}

		if (estado == EstadoIntercambioRegistralEntradaEnumVO.PENDIENTE_VALUE) {
			// Lo que para nosotros es PENDIENTE de aceptar, en el CIR es
			// RECIBIDO por el CIR
			estado = EstadoAsientoRegistralEnum.RECIBIDO.getValue();
		}

		if (estado == EstadoIntercambioRegistralEntradaEnumVO.RECHAZADO_VALUE) {
			estado = EstadoAsientoRegistralEnum.RECHAZADO.getValue();
		}

		if (estado == EstadoIntercambioRegistralEntradaEnumVO.REENVIADO_VALUE) {
			estado = EstadoAsientoRegistralEnum.REENVIADO.getValue();
		}

		return estado;
	}

	private List<BandejaEntradaItemVO> getBandejaEntradaIntercambioRegistralAceptados(
			Integer idOficina) {
		return getBandejaEntradaIntercambioRegistralDAO()
				.getBandejaEntradaByEstado(
						EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO_VALUE,
						idOficina);
	}

	private List<BandejaEntradaItemVO> getBandejaEntradaIntercambioRegistralRechazados(
			Integer idOficina) {
		return getBandejaEntradaIntercambioRegistralDAO()
				.getBandejaEntradaByEstado(
						EstadoIntercambioRegistralEntradaEnumVO.RECHAZADO_VALUE,
						idOficina);
	}

	public void guardarIntercambioRegistralEntrada(
			IntercambioRegistralEntradaVO intercambioRegistralEntrada) {

		Long id = getIntercambioRegistralEntradaIncrementer().nextLongValue();
		if (logger.isDebugEnabled()) {
			logger.debug("Se va a guardar el intercambio registral de entrada con id de intercambio = "
					+ intercambioRegistralEntrada.getIdIntercambioRegistral());
		}
		intercambioRegistralEntrada.setId(id);
		getBandejaEntradaIntercambioRegistralDAO().save(
				intercambioRegistralEntrada);
	}

	public BandejaEntradaItemVO completarBandejaEntradaItem(
			BandejaEntradaItemVO bandejaEntradaItemVO) {
		return getBandejaEntradaIntercambioRegistralDAO()
				.completarBandejaEntradaItem(bandejaEntradaItemVO);
	}

	public void rechazarIntercambioRegistralEntradaById(
			String idIntercambioInterno, String tipoRechazo,
			String observaciones) {

		if (logger.isDebugEnabled()) {
			logger.debug("Se va a rechazar el intercambio registral de entrada con id de intercambio = "
					+ idIntercambioInterno);
		}

		UsuarioVO usuario = IsicresContextHolder.getContextoAplicacion()
				.getUsuarioActual();
		Integer idOficina = Integer.parseInt(usuario.getConfiguracionUsuario()
				.getOficina().getId());

		String idEntidad = usuario.getConfiguracionUsuario().getIdEntidad();

		// obtener datos del intercambio registral
		AsientoRegistralVO asientoRegistral = getIntercambioRegistralSIRManager()
				.getAsientoRegistral(idIntercambioInterno);

		// si codigoREgistralInicio y codigoRegistralDestino no permitimos rechazar
		if (asientoRegistral.getCodigoEntidadRegistralInicio().equalsIgnoreCase(asientoRegistral.getCodigoEntidadRegistralDestino())){
			//
			throw new IntercambioRegistralException("No se puede rechazar intercambio que tu mismo eres origen",IntercambioRegistralExceptionCodes.ERROR_CODE_RECHAZO_ACEPTACION_ORIGEN);
		}

		IntercambioRegistralEntradaVO intercambioRegistralEntradaVO = populateIntercambioRegistralEntrada(
				usuario, asientoRegistral, idOficina, idIntercambioInterno,
				EstadoIntercambioRegistralEntradaEnumVO.RECHAZADO,
				observaciones);



		// y actualizamos estado en el SIR
		getIntercambioRegistralSIRManager().rechazarAsientoRegistral(
				idIntercambioInterno,
				TipoRechazoEnum.getTipoRechazo(Integer.valueOf(tipoRechazo)),
				observaciones);

		// verificar si existía ya un intercambio en scr_exreg_in y si existe lo
		// eliminamos para no liar al usuario
		String idIntercambioRegistralSir = asientoRegistral
				.getIdentificadorIntercambio();
		List<IntercambioRegistralEntradaVO> intercambiosPrevios = getBandejaEntradaIntercambioRegistralDAO()
				.getIntercambioRegistralEntradaByIdIntercambioRegistralSir(
						idOficina, idIntercambioRegistralSir);
		for (Iterator iterator = intercambiosPrevios.iterator(); iterator
				.hasNext();) {
			IntercambioRegistralEntradaVO intercambioRegistralEntrada = (IntercambioRegistralEntradaVO) iterator
					.next();
			getBandejaEntradaIntercambioRegistralDAO().delete(
					intercambioRegistralEntrada);

		}
		// Guardamos en la tabal scr_exregin
		guardarIntercambioRegistralEntrada(intercambioRegistralEntradaVO);

	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralEntradaManager#reenviarIntercambioRegistralEntradaById(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO)
	 */
	public void reenviarIntercambioRegistralEntradaById(
			String idIntercambioRegistralEntrada,
			UnidadTramitacionIntercambioRegistralVO nuevoDestino,
			String observaciones) {

		//validamos que el nuevo destino se corresponda la unid. de tramitacion con la entidad
		validateRelacionEntidadRegistralUnidadTramitacion(nuevoDestino);

		UsuarioVO usuario = IsicresContextHolder.getContextoAplicacion()
				.getUsuarioActual();

		Integer idOficina = Integer.parseInt(usuario.getConfiguracionUsuario()
				.getOficina().getId());

		String idEntidad = usuario.getConfiguracionUsuario().getIdEntidad();

		// TODO: Almacena el asiento registral en la tabla scr_exregin
		// obtener datos del intercambio registral
		AsientoRegistralVO asientoRegistral = getIntercambioRegistralSIRManager()
				.getAsientoRegistral(idIntercambioRegistralEntrada);
		// vamos a a realizar actualizacion del estado del intercambio en local,
		// pasa a estado aceptado EstadoIntercambioRegistralEntradaEnumVO

		IntercambioRegistralEntradaVO intercambioRegistralEntradaVO = populateIntercambioRegistralEntrada(
				usuario, asientoRegistral, idOficina,
				idIntercambioRegistralEntrada,
				EstadoIntercambioRegistralEntradaEnumVO.REENVIADO,
				observaciones);

		// Guardamos en la tabal scr_exregin y actualizamos estado en el SIR
		guardarIntercambioRegistralEntrada(intercambioRegistralEntradaVO);

		// TODO: ¿Contacto puede ser fullName? contacto y usuario es opcional,
		// no se envia
		getIntercambioRegistralSIRManager().reenviarAsientoRegistral(
				idIntercambioRegistralEntrada, null, null, observaciones,
				nuevoDestino);

		//comprobar si está en scr_exreg (hemos sido los emisores originales y nos los han rechazado o reenviado)
		// los pasamos a estado enviado a un nuevo destino
		String idIntercambioRegistralSir = asientoRegistral.getIdentificadorIntercambio();
		List<IntercambioRegistralSalidaVO> intercambioRegistralSalidas = getBandejaSalidaIntercambioRegistralDAO().findByIdIntercambioRegistralSirYOficina(idIntercambioRegistralSir , idOficina);
		if (intercambioRegistralSalidas !=null){
			for (Iterator iterator = intercambioRegistralSalidas.iterator(); iterator
					.hasNext();) {
				IntercambioRegistralSalidaVO intercambioRegistralSalidaItem = (IntercambioRegistralSalidaVO) iterator
						.next();
				EstadoIntercambioRegistralSalidaVO estadoEnviado = new EstadoIntercambioRegistralSalidaVO();
				estadoEnviado.setEstado(EstadoIntercambioRegistralSalidaEnumVO.ENVIADO);
				estadoEnviado.setFechaEstado(new Date());
				estadoEnviado.setIdExReg(intercambioRegistralSalidaItem.getId());
				estadoEnviado.setUserName(usuario.getLoginName());
				getBandejaSalidaIntercambioRegistralDAO().updateEstado(intercambioRegistralSalidaItem, estadoEnviado  );
				//actualizamos el nuevo destin
				intercambioRegistralSalidaItem.setCodeEntity(nuevoDestino.getCodeEntity());
				intercambioRegistralSalidaItem.setNameEntity(nuevoDestino.getNameEntity());
				intercambioRegistralSalidaItem.setCodeTramunit(nuevoDestino.getCodeTramunit());
				intercambioRegistralSalidaItem.setNameTramunit(nuevoDestino.getNameTramunit());
				getBandejaSalidaIntercambioRegistralDAO().updateIntercambioRegistralSalidaVO(intercambioRegistralSalidaItem);
			}
		}



	}

	/**
	 * Método que valida la relación entre el cod. Entidad y el cod. de la Unid.
	 * si por configuración esta activa la validación:
	 * intercambioRegistral.properties
	 *
	 * @param nuevoDestino
	 */
	private void validateRelacionEntidadRegistralUnidadTramitacion(
			UnidadTramitacionIntercambioRegistralVO nuevoDestino) {
		// comprobamos si esta activa la validación para verificar la relación
		// entre entidad y unidad
		if (IntercambioRegistralConfiguration.getInstance()
				.getActiveValidationRelationEntidadUnidad()) {
			// se comprueba si el codigo de unidad es distinto de blanco
			if (StringUtils.isNotBlank(nuevoDestino.getCodeTramunit())) {
				// si esta rellena, pasamos a comprobar si existe una relación
				// con
				// la entidad registral
				if (!getConfiguracionIntercambioRegistralManager()
						.existRelacionUnidOrgaOficina(nuevoDestino.getCodeEntity(),
								nuevoDestino.getCodeTramunit())) {
					// al no existir relación entre la unidad y la entidad
					// devolvemos excepción alertando del problema
					throw new IntercambioRegistralException(
							"La unid. de tramitación indicada no se corresponde con la entidad registral",
							IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_UNID_TRAMITA_ENTIDAD_REG);
				}
			}
		}
	}

	public List<IntercambioRegistralEntradaVO> getHistorialIntercambioRegistralEntrada(
			String idLibro, String idRegistro, String idOficina) {

		IntercambioRegistralEntradaVO intercambioRegistralEntrada = new IntercambioRegistralEntradaVO();
		intercambioRegistralEntrada.setIdLibro(Long.parseLong(idLibro));
		intercambioRegistralEntrada.setIdRegistro(Long.parseLong(idRegistro));
		if (idOficina != null){
			intercambioRegistralEntrada.setIdOfic(Integer.parseInt(idOficina));
		}

		List<IntercambioRegistralEntradaVO> infoEstados = getBandejaEntradaIntercambioRegistralDAO()
				.getInfoEstado(intercambioRegistralEntrada);

		return infoEstados;
	}

	public void aceptarIntercambioRegistralEntradaById(
			String idIntercambioInterno, String idLibro, String user,
			Integer idOficina, String codOficina, boolean llegoDocFisica) {

		// TODO injection en spring
		RegistroManager registroManager = IsicresManagerProvider.getInstance()
				.getRegistroManager();
		UsuarioVO usuario = IsicresContextHolder.getContextoAplicacion()
				.getUsuarioActual();

		// obtener datos del intercambio registral
		AsientoRegistralVO asientoRegistral = getIntercambioRegistralSIRManager()
				.getAsientoRegistral(idIntercambioInterno);
		String idIntercambioRegistralSir = asientoRegistral.getIdentificadorIntercambio();


		//si fue el original del registro no se crea el registro se acepta contra el sir pero no se crea registro
		if (asientoRegistral.getCodigoEntidadRegistralInicio()
				.equalsIgnoreCase(
						asientoRegistral.getCodigoEntidadRegistralDestino())) {


				//actualizamos el estado de los existentens en la bandeja de salida scr_exreg (existe fijo porque si fuimos origen ...)
				List<IntercambioRegistralSalidaVO> intercambioRegistralSalidas = getBandejaSalidaIntercambioRegistralDAO().findByIdIntercambioRegistralSirYOficina(idIntercambioRegistralSir , idOficina);
				if (intercambioRegistralSalidas !=null){
					for (Iterator iterator = intercambioRegistralSalidas.iterator(); iterator
							.hasNext();) {
						IntercambioRegistralSalidaVO intercambioRegistralSalidaItem = (IntercambioRegistralSalidaVO) iterator
								.next();
						EstadoIntercambioRegistralSalidaVO estadoEnviado = new EstadoIntercambioRegistralSalidaVO();
						estadoEnviado.setEstado(EstadoIntercambioRegistralSalidaEnumVO.ACEPTADO);
						estadoEnviado.setFechaEstado(new Date());
						estadoEnviado.setIdExReg(intercambioRegistralSalidaItem.getId());
						estadoEnviado.setUserName(usuario.getLoginName());
						getBandejaSalidaIntercambioRegistralDAO().updateEstado(intercambioRegistralSalidaItem, estadoEnviado  );
						//actualizamos el nuevo destino con nosotros mismos
						intercambioRegistralSalidaItem.setCodeEntity(asientoRegistral.getCodigoEntidadRegistralDestino());
						intercambioRegistralSalidaItem.setNameEntity(asientoRegistral.getDescripcionEntidadRegistralDestino());
						intercambioRegistralSalidaItem.setCodeTramunit(asientoRegistral.getCodigoUnidadTramitacionDestino());
						intercambioRegistralSalidaItem.setNameTramunit(asientoRegistral.getDescripcionUnidadTramitacionDestino());
						getBandejaSalidaIntercambioRegistralDAO().updateIntercambioRegistralSalidaVO(intercambioRegistralSalidaItem);
		}

				}
				// hacemos la llamada al sir para aceptarlo, tenemos que poner
				getIntercambioRegistralSIRManager().validarAsientoRegistral(
						asientoRegistral.getId(), asientoRegistral.getNumeroRegistro(),
						asientoRegistral.getFechaRegistro());
		}else{
		// vamos a crear el resgistro
		BaseRegistroVO registro = completarRegistroVO(asientoRegistral,
				usuario, idLibro);

		registro = registroManager.createRegistroEntrada(usuario,
				(RegistroEntradaVO) registro);

		registroManager.attachDocuments(registro.getId(),
				getAnexos(asientoRegistral, registro), usuario);

		// vamos a a realizar actualizacion del estado del intercambio en local,
		// pasa a estado aceptado EstadoIntercambioRegistralEntradaEnumVO
		IntercambioRegistralEntradaVO intercambioRegistralEntradaVO = populateIntercambioRegistralEntrada(
				usuario, asientoRegistral, idOficina, Long.parseLong(idLibro),
				Long.parseLong(registro.getIdRegistro()), idIntercambioInterno,
				EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO, null);



		// hacemos la llamada al sir para aceptarlo, tenemos que poner
		// el numero de registro que hemos generado
		getIntercambioRegistralSIRManager().validarAsientoRegistral(
				asientoRegistral.getId(), registro.getNumeroRegistro(),
				registro.getFechaRegistro());

		// verificar si existía ya un intercambio en scr_exreg_in y si existe lo eliminamos para no liar al usuario
		 List<IntercambioRegistralEntradaVO> intercambiosPrevios = getBandejaEntradaIntercambioRegistralDAO().getIntercambioRegistralEntradaByIdIntercambioRegistralSir(idOficina, idIntercambioRegistralSir);
		for (Iterator iterator = intercambiosPrevios.iterator(); iterator
				.hasNext();) {
			IntercambioRegistralEntradaVO intercambioRegistralEntrada = (IntercambioRegistralEntradaVO) iterator
					.next();
			getBandejaEntradaIntercambioRegistralDAO().delete(intercambioRegistralEntrada);


		}

		// guardamos el nuevo
		guardarIntercambioRegistralEntrada(intercambioRegistralEntradaVO);

	}






	}


	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralEntradaManager#getIntercambioRegistralEntradaByRegistro(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public IntercambioRegistralEntradaVO getIntercambioRegistralEntradaByRegistro(
			Integer idLibro, Integer idRegistro, Integer estado){
		return getBandejaEntradaIntercambioRegistralDAO().getIntercambioRegistralEntradaByRegistro(idLibro, idRegistro, estado);
	}

	protected IntercambioRegistralEntradaVO populateIntercambioRegistralEntrada(
			UsuarioVO usuario, AsientoRegistralVO asientoRegistral,
			Integer idOficina, String idIntercambioInterno,
			EstadoIntercambioRegistralEntradaEnumVO estado, String observaciones) {

		IntercambioRegistralEntradaVO result = null;

		result = populateIntercambioRegistralEntrada(usuario, asientoRegistral,
				idOficina, null, null, idIntercambioInterno, estado,
				observaciones);

		return result;
	}

	protected IntercambioRegistralEntradaVO populateIntercambioRegistralEntrada(
			UsuarioVO usuario, AsientoRegistralVO asientoRegistral,
			Integer idOficina, Long idLibro, Long idRegistro,
			String idIntercambioInterno,
			EstadoIntercambioRegistralEntradaEnumVO estado, String observaciones) {

		IntercambioRegistralEntradaVO result = new IntercambioRegistralEntradaVO();

		result.setIdRegistro(idRegistro);
		result.setIdLibro(idLibro);

		result.setIdOfic(idOficina);
		result.setEstado(estado);
		result.setFechaEstado(Calendar.getInstance().getTime());
		result.setIdIntercambioRegistral(asientoRegistral
				.getIdentificadorIntercambio());
		result.setIdIntercambioInterno(idIntercambioInterno);
		result.setTipoOrigen(Integer.parseInt(asientoRegistral
				.getTipoRegistro().getValue()));
		//Fecha del intercambio registral
		result.setFechaIntercambio(asientoRegistral.getFechaEstado());

		result.setUsername(usuario.getLoginName());

		// Entidad de tramitacion inicial del intercambio
		result.setCodeEntity(asientoRegistral.getCodigoEntidadRegistralInicio());

		// validamos si la descripción de la entidad es nula y el código de la
		// entidad esta completo, insertamos como descripción el código de la
		// entidad
		if (StringUtils.isEmpty(asientoRegistral
				.getDescripcionEntidadRegistralInicio())
				&& StringUtils.isNotEmpty(asientoRegistral
						.getCodigoEntidadRegistralInicio())) {
			// añadimos el codigo de entidad como descripcion
			result.setNameEntity(asientoRegistral
					.getCodigoEntidadRegistralInicio());
			UnidadAdministrativaIntercambioRegistralVO unidadOrigen = null;

			List<UnidadAdministrativaIntercambioRegistralVO> listaUnidadOrigen = getConfiguracionIntercambioRegistralManager()
					.getUnidadAdmimistrativaByCodigoEntidadRegistral(asientoRegistral
							.getCodigoEntidadRegistralInicio());

			if (CollectionUtils.isNotEmpty(listaUnidadOrigen)) {
				unidadOrigen = listaUnidadOrigen.get(0);
				if (unidadOrigen != null) {
					result.setNameEntity(unidadOrigen.getName());
				}
			}

		} else {
			// aladimos la descripción de la entidad
			result.setNameEntity(asientoRegistral
					.getDescripcionEntidadRegistralInicio());
		}

		// Unidad de tramitacion inicial del intercambio
		/*
		 * TODO: ¿Cuál es la unidad de tramitación correspondiente en el asiento
		 * registral? ¿Origen o Destino?
		 */
		result.setCodeTramunit(asientoRegistral
				.getCodigoUnidadTramitacionOrigen());

		// validamos si el nombre de la unidad de tramitación es nula y el
		// código de ésta está completo, insertamos como descripción el código
		// de la unidad
		if (StringUtils.isEmpty(asientoRegistral
				.getDescripcionUnidadTramitacionOrigen())
				&& StringUtils.isNotEmpty(asientoRegistral
						.getCodigoUnidadTramitacionOrigen())) {

			UnidadAdministrativaIntercambioRegistralVO unidadAdministrativaOrigen = configuracionIntercambioRegistralManager.getUnidadAdmimistrativaByCodigoEntidadRegistralYUnidadTramitacion(asientoRegistral
					.getCodigoUnidadTramitacionOrigen(), asientoRegistral
					.getCodigoEntidadRegistralInicio());
			if (unidadAdministrativaOrigen!=null && StringUtils.isNotEmpty(unidadAdministrativaOrigen.getName())){
				result.setNameTramunit(unidadAdministrativaOrigen.getName());
			}else{
			// añadimos el codigo de la unidad como nombre
				result.setNameTramunit(asientoRegistral
					.getCodigoUnidadTramitacionOrigen());
			}

		} else {
			// añadimos la descripción de la unidad de tramitación
			result.setNameTramunit(asientoRegistral
					.getDescripcionUnidadTramitacionOrigen());
		}

		// numero registro inicial
		result.setNumeroRegistroOrigen(asientoRegistral
				.getNumeroRegistroInicial());
		// contacto de usuario
		result.setContactoOrigen(asientoRegistral.getContactoUsuario());

		result.setComentarios(observaciones);

		return result;
	}

	private AsientoRegistralVO getAsientoRegistral(
			String idIntercambioRegistral, Integer idOficina) {
		AsientoRegistralVO asiento = null;
		EntidadRegistralVO entidadRegistralDestino = getConfiguracionIntercambioRegistralManager()
				.getEntidadRegistralVOByIdScrOfic(String.valueOf(idOficina));
		CriteriosVO criterios = new CriteriosVO();
		CriterioVO criterioIdentificador = new CriterioVO(
				CriterioEnum.ASIENTO_IDENTIFICADOR_INTERCAMBIO,
				idIntercambioRegistral);
		CriterioVO criterioEntidadDestino = new CriterioVO(
				CriterioEnum.ASIENTO_CODIGO_ENTIDAD_REGISTRAL,
				entidadRegistralDestino.getCode());
		criterios.addCriterioVO(criterioIdentificador);
		criterios.addCriterioVO(criterioEntidadDestino);
		List<AsientoRegistralVO> asientoRegistral = getIntercambioRegistralSIRManager()
				.findAsientosRegistrales(criterios);
		if (asientoRegistral != null && asientoRegistral.size() == 1) {
			asiento = asientoRegistral.get(0);
		}
		return asiento;
	}

	private BaseRegistroVO completarRegistroVO(
			AsientoRegistralVO asientoRegistral, UsuarioVO usuario,
			String idLibro) {

		if (logger.isDebugEnabled()) {
			logger.debug("Se va a completar la información del intercambio para aceptarla");
		}
		RegistroEntradaVO registro = new RegistroEntradaVO();

		mapearCamposAdicionales(registro, asientoRegistral);

		// Mapeamos los terceros
		mapearTerceros(registro, asientoRegistral);

		registro.setComentario(asientoRegistral.getObservacionesApunte());

		// Mapear el resto de campos en comentarios (el tipo de asunto se
		// incluye en el campo comentario)
		mapearCamposEnComentarios(registro, asientoRegistral);
		// Mapeamos los datos propios del registro
		registro.setUsuarioRegistro(usuario);
		registro.setOficinaRegistro(usuario.getConfiguracionUsuario()
				.getOficina());
		registro.setIdLibro(idLibro);

		registro.setResumen(asientoRegistral.getResumen());
		registro.setReferenciaExpediente(asientoRegistral.getNumeroExpediente());

		RegistroOriginalVO registroOriginal = new RegistroOriginalVO();
		registroOriginal.setNumeroRegistroOriginal(asientoRegistral
				.getNumeroRegistro());
		registroOriginal.setFechaRegistroOriginal(asientoRegistral
				.getFechaRegistro());
		registroOriginal.setEntidadRegistral(registro
				.getUnidadAdministrativaOrigen());
		if (asientoRegistral.getTipoRegistro() == TipoRegistroEnum.ENTRADA) {
			registroOriginal.setTipoRegistroOriginal(Integer
					.toString(TipoRegistroEnumVO.ENTRADA_VALUE));
		} else {
			registroOriginal.setTipoRegistroOriginal(Integer
					.toString(TipoRegistroEnumVO.SALIDA_VALUE));
		}

		mapearTransporte(asientoRegistral, registro);

		// Setear el origen y el destino
		mapearUnidadesAdministrativas(registro, asientoRegistral);

		mapearComentariosAnexos(asientoRegistral, registro);

		registro.setRegistroOriginal(registroOriginal);

		if (logger.isDebugEnabled()) {
			logger.debug("Se completo la recuperacion de informacion del intercambio con identificador = "
					+ asientoRegistral.getIdentificadorIntercambio());
		}
		return registro;
	}

	/**
	 * @param asientoRegistral
	 * @param registro
	 */
	private void mapearComentariosAnexos(AsientoRegistralVO asientoRegistral,
			RegistroEntradaVO registro) {
		List<AnexoVO> listaAnexos = asientoRegistral.getAnexos();
		if (CollectionUtils.isNotEmpty(listaAnexos)){
			StringBuffer comentariosAnexos = new StringBuffer();
			comentariosAnexos.append(SALTO_LINEA);
			comentariosAnexos.append("Ficheros involucrados en el intercambio: \n");
			for (AnexoVO anexoVO : listaAnexos) {
				comentariosAnexos.append(anexoVO.getNombreFichero());
				if (StringUtils.isNotBlank(anexoVO.getObservaciones())){
					comentariosAnexos.append(ESPACIO);
					comentariosAnexos.append("(");
					comentariosAnexos.append(anexoVO.getObservaciones());
					comentariosAnexos.append(")");
				}
				comentariosAnexos.append(SALTO_LINEA);
			}
			registro.setComentario(registro.getComentario()+comentariosAnexos.toString());
		}
	}

	/**
	 * @param asientoRegistral
	 * @param registro
	 */
	private void mapearTransporte(AsientoRegistralVO asientoRegistral,
			RegistroEntradaVO registro) {
		if (asientoRegistral.getTipoTransporte() != null
				|| asientoRegistral.getNumeroTransporte() != null) {
			TransporteVO transporte = new TransporteVO();
			transporte.setNumeroTransporte(asientoRegistral
					.getNumeroTransporte());
			if (asientoRegistral.getTipoTransporte() != null) {
				//obtenemos el tipo de transporte según los datos del asiento registral
				TipoTransporteIntercambioRegistralVO tipoTransporteIR = getTipoTransporteIntercambioRegistral(asientoRegistral);

				if (tipoTransporteIR != null) {
					transporte.setId(String.valueOf(tipoTransporteIR
							.getIdTipoTransporte()));
					TipoTransporteVO tipoTransporte = new TipoTransporteVO();
					tipoTransporte.setId(String.valueOf(tipoTransporteIR
							.getIdTipoTransporte()));
					tipoTransporte.setDescripcion(tipoTransporteIR
							.getDescripcion());
					transporte.setTipoTransporte(tipoTransporte);
				}
			}
			registro.setTransporte(transporte);
		}
	}

	/**
	 * Método que obtiene el tipo de transporte que corresponde con los datos del asiento registral
	 *
	 * @param asientoRegistral - Datos del asiento registral
	 * @return {@link TipoTransporteIntercambioRegistralVO}
	 */
	private TipoTransporteIntercambioRegistralVO getTipoTransporteIntercambioRegistral(
			AsientoRegistralVO asientoRegistral) {
		//codigo de transporte del SIR (01, 02...)
		String codigoSir = asientoRegistral.getTipoTransporte()
				.getValue();

		// obtenemos el identificador del tipo de transporte (SCR_TT) por
		// defecto para el transporte utilizado en el SIR (SCR_TTEXREG)
		String idTipoTransporteByDefault = IntercambioRegistralConfiguration
				.getInstance()
				.getProperty(
						IntercambioRegistralConfigurationKeys.TYPE_TRANSPORT_BY_DEFAULT
								+ codigoSir);

		// obtenemos el número de tipos de transporte mapeados para un
		// mismo valor del SIR
		int countTipoTransporte = tipoTransporteIntercambioRegistralManager
				.getCountTipoTransporteByCodigo(codigoSir);

		TipoTransporteIntercambioRegistralVO tipoTransporteIR = null;
		// si se recupera más de un tipo de transporte
		if ((countTipoTransporte > 1)
				&& (StringUtils
						.isNotBlank(idTipoTransporteByDefault))) {
			// pasamos a buscar el tipo de transporte indicado por
			// configuración para el código del SIR
			tipoTransporteIR = tipoTransporteIntercambioRegistralManager
					.getTipoTransporteByCodigoAndIDScrTT(codigoSir,
							new Integer(idTipoTransporteByDefault));
		} else {
			// solamente existe como máximo un tipo de transporte para el código
			tipoTransporteIR = tipoTransporteIntercambioRegistralManager
					.getTipoTransporteByCodigo(codigoSir);
		}

		return tipoTransporteIR;
	}

	private void mapearCamposAdicionales(RegistroEntradaVO registro,
			AsientoRegistralVO asientoRegistral) {

		CampoAdicionalRegistroVO campoExpone = new CampoAdicionalRegistroVO();
		campoExpone.setName(String.valueOf(AxSf.FLD501_FIELD_ID));
		campoExpone.setValue(asientoRegistral.getExpone());
		registro.getCamposAdicionales().add(campoExpone);

		CampoAdicionalRegistroVO campoSolicita = new CampoAdicionalRegistroVO();
		campoSolicita.setName(String.valueOf(AxSf.FLD502_FIELD_ID));
		campoSolicita.setValue(asientoRegistral.getSolicita());
		registro.getCamposAdicionales().add(campoSolicita);

		// TODO ¿Hay que validar que el libro tenga este campo? Ahora peta si el
		// libro no lo tiene...
		CampoAdicionalRegistroVO campoAdicionalIsIntercambioRegistral = new CampoAdicionalRegistroVO();
		campoAdicionalIsIntercambioRegistral.setName(String
				.valueOf(AxSf.FLD503_FIELD_ID));
		campoAdicionalIsIntercambioRegistral
				.setValue(ConstantKeys.REG_IS_INTERCAMBIO_REGISTRAL);
		registro.getCamposAdicionales().add(
				campoAdicionalIsIntercambioRegistral);

		if (DocumentacionFisicaEnum.DOCUMENTACION_FISICA_REQUERIDA == asientoRegistral
				.getDocumentacionFisica()) {
			CampoAdicionalRegistroVO campoDocFisicaRequerida = new CampoAdicionalRegistroVO();
			campoDocFisicaRequerida.setName(String
					.valueOf(AxSf.FLD504_FIELD_ID));
			campoDocFisicaRequerida.setValue("1");
			registro.getCamposAdicionales().add(campoDocFisicaRequerida);
		} else if (DocumentacionFisicaEnum.DOCUMENTACION_FISICA_COMPLEMENTARIA == asientoRegistral
				.getDocumentacionFisica()) {
			CampoAdicionalRegistroVO campoDocFisicaCompl = new CampoAdicionalRegistroVO();
			campoDocFisicaCompl.setName(String.valueOf(AxSf.FLD505_FIELD_ID));
			campoDocFisicaCompl.setValue("1");
			registro.getCamposAdicionales().add(campoDocFisicaCompl);
		} else {
			CampoAdicionalRegistroVO noDocFisica = new CampoAdicionalRegistroVO();
			noDocFisica.setName(String.valueOf(AxSf.FLD506_FIELD_ID));
			noDocFisica.setValue("1");
			registro.getCamposAdicionales().add(noDocFisica);
		}

	}

	private RegistroEntradaVO mapearCamposEnComentarios(
			RegistroEntradaVO registro, AsientoRegistralVO asientoRegistral) {
		// Inicializamos el buffer con el comentario del registro, a no ser que
		// no tenga ninguno
		StringBuffer comentario = new StringBuffer("");
		if (StringUtils.isNotEmpty(registro.getComentario())) {
			comentario.append(SALTO_LINEA);
		}
		if (StringUtils.isNotEmpty(asientoRegistral
				.getIdentificadorIntercambio())) {
			comentario.append("Ident. Intercambio: ")
					.append(asientoRegistral.getIdentificadorIntercambio())
					.append(SALTO_LINEA);
		}
		if (StringUtils.isNotEmpty(asientoRegistral.getAplicacion())) {
			comentario.append("Aplicación: ")
					.append(asientoRegistral.getAplicacion()).append(SALTO_LINEA);
		}
		if (StringUtils.isNotEmpty(asientoRegistral.getNumeroRegistroInicial())) {
			comentario.append("Num. Registro Inicial: ")
					.append(asientoRegistral.getNumeroRegistroInicial())
					.append(SALTO_LINEA);
		}

		if (StringUtils.isNotEmpty(asientoRegistral.getReferenciaExterna())) {
			comentario.append("Referencia Externa: ")
					.append(asientoRegistral.getReferenciaExterna())
					.append(SALTO_LINEA);
		}

		// El tipo de asunto lo incluimos en el comentario, ya que el cod. puede
		// que no exista en el entorno que acepta el intercambio
		if (StringUtils.isNotEmpty(asientoRegistral.getCodigoAsunto())) {
			comentario.append("Cod. Asunto: ")
					.append(asientoRegistral.getCodigoAsunto()).append(SALTO_LINEA);
		}

		if (asientoRegistral.getTipoTransporte() != null) {
			comentario.append("Tipo de Transporte: ")
					.append(asientoRegistral.getTipoTransporte().getName())
					.append(SALTO_LINEA);
		}
		if (StringUtils.isNotEmpty(asientoRegistral.getNumeroTransporte())) {
			comentario.append("Num. Transporte:")
					.append(asientoRegistral.getNumeroTransporte())
					.append(SALTO_LINEA);
		}
		if (StringUtils.isNotEmpty(asientoRegistral
				.getCodigoEntidadRegistralOrigen())) {

			// Obtnemos la descripcion de la entidad registral, si la
			// descripcion que recibimos es vacio lo seteamos con el codigo de
			// la entidad
			String descripcionEntidad = asientoRegistral
					.getDescripcionEntidadRegistralOrigen();
			if (StringUtils.isEmpty(descripcionEntidad)) {
				descripcionEntidad = asientoRegistral
						.getCodigoEntidadRegistralOrigen();
			}
			comentario.append("Entidad Origen del Intercambio: ")
					.append(asientoRegistral.getCodigoEntidadRegistralOrigen())
					.append(" - ").append(descripcionEntidad).append(SALTO_LINEA);
		}

		if (StringUtils.isNotEmpty(asientoRegistral
				.getCodigoUnidadTramitacionOrigen())) {

			// Obtnemos la descripcion de la unidad de tramitacion, si la
			// descripcion que recibimos es vacia lo seteamos con el codigo de
			// la unidad
			String descripcionUnidad = asientoRegistral
					.getDescripcionUnidadTramitacionOrigen();
			if (StringUtils.isEmpty(descripcionUnidad)) {
				descripcionUnidad = asientoRegistral
						.getCodigoUnidadTramitacionOrigen();
			}

			comentario
					.append("Unidad de Tramitaicón Origen del Intercambio: ")
					.append(asientoRegistral.getCodigoUnidadTramitacionOrigen())
					.append(" - ").append(descripcionUnidad).append(SALTO_LINEA);
		}

		if (StringUtils.isNotEmpty(asientoRegistral
				.getCodigoEntidadRegistralInicio())) {

			// Obtnemos la descripcion de la entidad registral inicial, si la
			// descripcion que recibimos es vacio lo seteamos con el codigo de
			// la entidad inicial
			String descripcionEntidadInicial = asientoRegistral
					.getDescripcionEntidadRegistralInicio();
			if (StringUtils.isEmpty(descripcionEntidadInicial)) {
				descripcionEntidadInicial = asientoRegistral
						.getCodigoEntidadRegistralInicio();
			}

			comentario.append("Entidad Inicial del Intercambio: ")
					.append(asientoRegistral.getCodigoEntidadRegistralInicio())
					.append(" - ").append(descripcionEntidadInicial)
					.append(SALTO_LINEA);
		}

		comentario = mapearRepresentantes(comentario, asientoRegistral);

		String comentarioResult = registro.getComentario();
		if (comentarioResult == null) {
			comentarioResult = "";
		}
		comentarioResult += comentario.toString();
		registro.setComentario(comentarioResult);

		if (logger.isDebugEnabled()) {
			logger.debug("Mapeados campos en el siguiente comentario: "
					+ comentario);
		}
		return registro;
	}

	private StringBuffer mapearRepresentantes(StringBuffer comentario,
			AsientoRegistralVO asientoRegistral) {
		for (InteresadoVO interesado : asientoRegistral.getInteresados()) {
			if ((StringUtils.isNotEmpty(interesado.getNombreRepresentante()))
					|| (StringUtils.isNotEmpty(interesado
							.getRazonSocialRepresentante()))) {
				comentario.append("El representante de ")
						.append(getNombreInteresadoString(interesado)).append(" es ")
						.append(getNombreRepresentanteString(interesado))
						.append(SALTO_LINEA);
			}
		}
		return comentario;
	}

	private RegistroEntradaVO mapearTerceros(RegistroEntradaVO registro,
			AsientoRegistralVO asientoRegistral) {
		List<es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO> listaTerceros = new ArrayList<es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO>();

		List<InteresadoVO> listaInteresados = asientoRegistral.getInteresados();
		String terceroString = null;
		String representanteString = null;

		// inicializamos la variable que indica si el interesado es principal
		boolean interesadoPrincipal = true;

		for (int i = 0; i < listaInteresados.size(); i++) {
			InteresadoVO interesado = listaInteresados.get(i);

			InteresadoExReg interesadoExReg = new InteresadoExReg();

			BeanUtils.copyProperties(interesado, interesadoExReg);

			completarDatosInteresado(interesado, interesadoExReg);

			completarDatosRepresentante(interesado, interesadoExReg);

			// Obtenemos la cadena con los datos del interesado
			terceroString = getInteresadoString(interesadoExReg);
			representanteString = getRepresentanteString(interesadoExReg);

			// Asignamos los datos del INTERESADO (el primer interesado es el
			// considerado interesado principal)
			interesadoPrincipal = setDatosInteresadoORepresentante(registro,
					listaTerceros, terceroString, interesadoPrincipal,
					interesadoExReg, false);

			// Asignamos los datos del REPRESENTANTE (el valor que retorna la
			// función setDatosInteresadoORepresentante no afecta a los
			// representantes, solamente a los interesados)
			setDatosInteresadoORepresentante(registro,
					listaTerceros, representanteString, false,
					interesadoExReg, true);
		}
		registro.setInteresados(listaTerceros);
		return registro;
	}

	/**
	 * Método que setea los datos del interesado o del representante según la longitud del campo
	 *
	 * @param registro - Datos del registro
	 * @param listaTerceros - Listado de interesados
	 * @param cadenaDatosPersona - Nombre del interesado
	 * @param interesadoPrincipal - Indica si el interesado se corresponde con el interesado principal
	 * @param interesadoExReg - Datos del interesado
	 *
	 * @return boolean - Retorna el boleano indicando si es el interesado principal
	 */
	private boolean setDatosInteresadoORepresentante(
			RegistroEntradaVO registro,
			List<es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO> listaTerceros,
			String cadenaDatosPersona, boolean interesadoPrincipal,
			InteresadoExReg interesadoExReg, boolean isRepresentante) {
		// verificamos si esa cadena es mayor al tamaño del campo
		if (cadenaDatosPersona.length() > maxLengthCampoInteresado) {
			// descomponemos los datos de la persona en partes (nombre y
			// apellidos, documentacion y/o direccion)
			List<String> datosPersona = getDatosInteresadoOrRepresentante(
					interesadoExReg, isRepresentante);
			// recorremos los datos del interesado (nombre/apellidos,
			// identificacion y direccion) para tratarlos como partes
			// independientes
			for (Iterator<String> it = datosPersona.iterator(); it.hasNext();) {
				String cadenaInteresado = (String) it.next();
				// añadimos la información al listado de interesados
				interesadoPrincipal = addInteresado(registro, listaTerceros,
						cadenaInteresado, interesadoPrincipal);
			}
		} else {
			// mantenemos toda la información en una sola linea, añadimos la
			// información al listado de interesados
			interesadoPrincipal = addInteresado(registro, listaTerceros,
					cadenaDatosPersona, interesadoPrincipal);
		}
		return interesadoPrincipal;
	}

	/**
	 * Método que devuelve los datos de la persona según se indique si es
	 * interesado o representante
	 *
	 * @param interesadoExReg
	 *            - Datos completos del interesado
	 * @param isRepresentante
	 *            - boolean indica si es representante o interesado
	 *
	 * @return Listado con los datos de la persona
	 */
	private List<String> getDatosInteresadoOrRepresentante(
			InteresadoExReg interesadoExReg, boolean isRepresentante) {
		List<String> datosPersona = null;
		// comprobamos si es representante o interesado
		if (isRepresentante) {
			// datos del representante
			datosPersona = InteresadoUtils
					.getDatosRepresentanteArray(interesadoExReg);
		} else {
			// datos del interesado
			datosPersona = InteresadoUtils
					.getDatosInteresadoArray(interesadoExReg);
		}
		return datosPersona;
	}

	/**
	 * @param interesado
	 * @param interesadoExReg
	 */
	private void completarDatosRepresentante(InteresadoVO interesado,
			InteresadoExReg interesadoExReg) {
		if (StringUtils.isNotEmpty(interesado
				.getCodigoProvinciaRepresentante())) {
			ProvinciaExReg provincia = direccionesIntercambioRegistralRegManager
					.getProvinciaExRegByCodigo(interesado
							.getCodigoProvinciaRepresentante());
			if (provincia != null) {
				interesadoExReg.setNombreProvinciaRepresentante(provincia
						.getNombre());
			}
			if (StringUtils.isNotEmpty(interesado
					.getCodigoMunicipioRepresentante())) {
				CiudadExReg municipio = direccionesIntercambioRegistralRegManager
						.getCiudadExRegByCodigo(interesado
								.getCodigoProvinciaRepresentante(),
								interesado
										.getCodigoMunicipioRepresentante());
				if (municipio != null) {
					interesadoExReg
							.setNombreMunicipioRepresentante(municipio
									.getNombre());
				}
			}
		}

		if (StringUtils.isNotEmpty(interesado.getCodigoPaisRepresentante())){
			PaisExReg pais = direccionesIntercambioRegistralRegManager.getPaisExRegByCodigo(interesado.getCodigoPaisRepresentante());
			if (pais != null){
				interesadoExReg.setNombrePaisRepresentante(pais.getNombre());
			}
		}
	}

	/**
	 * @param interesado
	 * @param interesadoExReg
	 */
	private void completarDatosInteresado(InteresadoVO interesado,
			InteresadoExReg interesadoExReg) {
		if (StringUtils.isNotEmpty(interesado.getCodigoProvinciaInteresado())) {
			ProvinciaExReg provincia = direccionesIntercambioRegistralRegManager
					.getProvinciaExRegByCodigo(interesado
							.getCodigoProvinciaInteresado());
			if (provincia != null) {
				interesadoExReg.setNombreProvinciaInteresado(provincia
						.getNombre());
			}
			if (StringUtils.isNotEmpty(interesado
					.getCodigoMunicipioInteresado())) {
				CiudadExReg municipio = direccionesIntercambioRegistralRegManager
						.getCiudadExRegByCodigo(
								interesado.getCodigoProvinciaInteresado(),
								interesado.getCodigoMunicipioInteresado());
				if (municipio != null) {
					interesadoExReg.setNombreMunicipioInteresado(municipio
							.getNombre());
				}
			}
		}
		if (StringUtils.isNotEmpty(interesado.getCodigoPaisInteresado())) {
			PaisExReg pais = direccionesIntercambioRegistralRegManager
					.getPaisExRegByCodigo(interesado.getCodigoPaisInteresado());
			if (pais != null) {
				interesadoExReg.setNombrePaisInteresado(pais.getNombre());
			}
		}
	}

	private boolean addInteresado(
			RegistroEntradaVO registro,
			List<es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO> listaTerceros,
			String terceroString, boolean interesadoPrincipal) {

		es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO tercero = new es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO();

		// Comprobamos si la cadena es distinta de vacia
		if (!StringUtils.isEmpty(terceroString)) {
			// comprobamos si es interesado principal, para ello
			// consideramos el primer interesado válido, como interesado
			// principal
			tercero.setPrincipal(interesadoPrincipal);
			tercero.setNombre(terceroString);
			if (interesadoPrincipal) {
				// Asignamos el tercero como interesado principal
				registro.setInteresadoPrincipal(tercero);
				// resetamos la variable que indica si el interesado es
				// interesado principal, ya que solo puede ser principal el
				// primer interesado valido
				interesadoPrincipal = false;
			} else {
				// añadimos el resto de interesados al listado de terceros
				listaTerceros.add(tercero);
			}
		}
		return interesadoPrincipal;
	}

	private void mapearUnidadesAdministrativas(RegistroEntradaVO registro,
			AsientoRegistralVO asientoRegistral) {


		String codigoEntidadRegistralInicio = asientoRegistral.getCodigoEntidadRegistralInicio();
		String codigoEntidadRegistralDestino = asientoRegistral.getCodigoEntidadRegistralDestino();
		String codigoUnidadTramitacionDestino = asientoRegistral.getCodigoUnidadTramitacionDestino();

		if (StringUtils.isNotEmpty(codigoEntidadRegistralInicio)){
			logger.debug("Buscando el codigo de la entidad registral de inicio'" + codigoEntidadRegistralInicio+"'");

			UnidadAdministrativaIntercambioRegistralVO unidadOrigen = null;

			List<UnidadAdministrativaIntercambioRegistralVO> listaUnidadOrigen = getConfiguracionIntercambioRegistralManager()
					.getUnidadAdmimistrativaByCodigoEntidadRegistral(codigoEntidadRegistralInicio);

			if (CollectionUtils.isNotEmpty(listaUnidadOrigen)) {
				unidadOrigen = listaUnidadOrigen.get(0);
				if (unidadOrigen != null) {
					UnidadAdministrativaVO unidadAdministrativaOrigen = new UnidadAdministrativaVO();

					unidadAdministrativaOrigen.setCodigoUnidad(unidadOrigen
							.getCode());
					registro.setUnidadAdministrativaOrigen(unidadAdministrativaOrigen);
				}
			}

		}
		if (StringUtils.isNotEmpty(codigoEntidadRegistralDestino)) {
			logger.debug("Buscando el codigo de la entidad registral de destino'"
					+ codigoEntidadRegistralDestino + "'");
			logger.debug("Buscando el codigo de la unidad de tramitacion de destino'"
					+ codigoUnidadTramitacionDestino + "'");

			UnidadAdministrativaIntercambioRegistralVO unidadDestino = getConfiguracionIntercambioRegistralManager()
					.getUnidadAdmimistrativaByCodigoEntidadRegistralYUnidadTramitacion(
							codigoUnidadTramitacionDestino,
							codigoEntidadRegistralDestino);
			if (unidadDestino == null) {
				List<UnidadAdministrativaIntercambioRegistralVO> listaUnidadDestino = getConfiguracionIntercambioRegistralManager()
						.getUnidadAdmimistrativaByCodigoEntidadRegistral(
								codigoEntidadRegistralDestino);
				if (CollectionUtils.isNotEmpty(listaUnidadDestino)){
					unidadDestino = listaUnidadDestino.get(0);
				}
			}
			if (unidadDestino != null) {
				UnidadAdministrativaVO unidadAdministrativaDestino = new UnidadAdministrativaVO();
				unidadAdministrativaDestino.setCodigoUnidad(unidadDestino
						.getCode());
				registro.setUnidadAdministrativaDestino(unidadAdministrativaDestino);
			}
		}




		String codigoOrigenEntidadRegistral = asientoRegistral
				.getCodigoEntidadRegistralOrigen();
		String codigoOrigenUnidadTram = asientoRegistral
				.getCodigoUnidadTramitacionOrigen();
		StringBuffer comentario = new StringBuffer("");

		comentario
				.append("Este registro ha sido creado a partir de un intercambio registral con el siguiente origen y destino:");
		comentario.append(SALTO_LINEA);
		if (StringUtils.isNotEmpty(codigoOrigenEntidadRegistral)) {
			comentario.append("Codigo DC Entidad Registral origen:"
					+ codigoOrigenEntidadRegistral);
			comentario.append(SALTO_LINEA);
		}
		if (StringUtils.isNotEmpty(codigoOrigenUnidadTram)) {
			comentario.append("Codigo DC Unidad Tramitación origen:"
					+ codigoOrigenUnidadTram);
			comentario.append(SALTO_LINEA);
		}
		if (StringUtils.isNotEmpty(codigoEntidadRegistralDestino)) {
			comentario.append("Codigo DC Entidad registral destino:"
					+ codigoEntidadRegistralDestino);
			comentario.append(SALTO_LINEA);
		}
		if (StringUtils.isNotEmpty(codigoUnidadTramitacionDestino)) {
			comentario.append("Codigo DC Unidad Tramitación destino:"
					+ codigoUnidadTramitacionDestino);
			comentario.append(SALTO_LINEA);
		}

		String comentarioResult = registro.getComentario();
		if (comentarioResult == null) {
			comentarioResult = "";
		}
		comentarioResult += comentario.toString();
		registro.setComentario(comentarioResult);
	}

	/**
	 * Métdo que obtiene los anexos de un registro
	 *
	 * @param asientoRegistral
	 *            - Información del asiento registral
	 * @param registro
	 *            - Información básica del registro
	 *
	 * @return Listado de objetos {@link DocumentoRegistroVO}
	 */
	private List<DocumentoRegistroVO> getAnexos(
			AsientoRegistralVO asientoRegistral, BaseRegistroVO registro) {
		List<AnexoVO> anexos = asientoRegistral.getAnexos();

		if (logger.isDebugEnabled()) {
			logger.debug("Se van a recupear los anexos del intercambio registral con identificador = "
					+ asientoRegistral.getIdentificadorIntercambio());
		}

		if (anexos == null || anexos.size() == 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("El intercambio no tiene anexos");
			}
			return null;
		}

		// TODO se desactiva la validación de los anexos, ya que solamente se
		// validaba el nombre y de momento se permite nombres repetidos
		// Verificamos los anexos adjuntados y son los que se deberían enviar en el método mapearListAnexoVOaListDocumentoRegistroVO

		// List<AnexoVO> anexosVerificados = verificarAnexos(anexos);

		// Generamos el listado de DocumentosRegistroVO a partir de los anexos
		// verificados
		List<DocumentoRegistroVO> documentoRegistro = mapearListAnexoVOaListDocumentoRegistroVO(anexos);

		// retornamos el array de documentos
		return documentoRegistro;
	}

	/**
	 * Método que genera un listado de objetos tipo {@link DocumentoRegistroVO}
	 *
	 * @param anexos
	 *            - {@link AnexoVO}
	 *
	 * @return listado de objetos tipo {@link DocumentoRegistroVO}
	 */
	private List<DocumentoRegistroVO> mapearListAnexoVOaListDocumentoRegistroVO(
			List<AnexoVO> anexos) {

		List<DocumentoRegistroVO> result = new ArrayList<DocumentoRegistroVO>();

		// Generamos documento
		DocumentoRegistroVO documento = new DocumentoRegistroVO();
		// Generamos array de paginas del documento
		List<PaginaDocumentoRegistroVO> paginas = generatePaginasDocumentoRegistro(anexos);

		documento.setName("Doc. Inter.");
		documento.setPaginas(paginas);
		result.add(documento);

		return result;
	}

	/**
	 * Método que genera un listado de objetos {@link PaginaDocumentoRegistroVO}
	 *
	 * @param anexos
	 *            - {@link AnexoVO}
	 *
	 * @return listado de objetos {@link PaginaDocumentoRegistroVO}
	 */
	private List<PaginaDocumentoRegistroVO> generatePaginasDocumentoRegistro(
			List<AnexoVO> anexos) {

		AnexoVO anexo = null;
		// contador de pagina
		int numPagina = 1;

		List<PaginaDocumentoRegistroVO> paginas = new ArrayList<PaginaDocumentoRegistroVO>();
		for (Iterator it = (Iterator) anexos.iterator(); it.hasNext();) {
			anexo = (AnexoVO) it.next();

			// Obtenemos la información de la pagina
			PaginaDocumentoRegistroVO pagina = createPaginaDocumentoRegistro(
					anexo, numPagina);

			// añadimos la pagina al array de paginas
			paginas.add(pagina);

			// incrementamos el numero de pagina
			numPagina++;
		}
		return paginas;
	}

	/**
	 * Método que valida que los anexos del intercambio son correctos. En la
	 * actualidad, se valida que el nombre de los ficheros no se repita, si es
	 * asi añadimos un sufijo
	 *
	 * @param anexosVO
	 *            - Listado de objetos {@link AnexoVO}
	 *
	 * @return Listado de objetos {@link AnexoVO} verificados y adaptados
	 */
	private List<AnexoVO> verificarAnexos(List<AnexoVO> anexosVO) {
		// Inicializamos el array de anexos verificados
		List<AnexoVO> result = new ArrayList<AnexoVO>();

		// Generamos una coleccion para verificar el nombre de las paginas
		// dentro de un documento
		Map<String, AnexoVO> nombresDeAnexosVO = new HashMap<String, AnexoVO>();
		// Creamos la variable contador para nombre de anexos con el mismo
		// nombre
		int count_nombreAnexo_duplicado = 0;

		for (Iterator it = (Iterator) anexosVO.iterator(); it.hasNext();) {
			AnexoVO anexoVO = (AnexoVO) it.next();

			// obtenemos el nombre de la pagina/fichero
			String nombreFichero = obtenerNombrePagina(
					count_nombreAnexo_duplicado, anexoVO, nombresDeAnexosVO);

			// añadimos la información del nombre al hashMap para que no se
			// repita
			nombresDeAnexosVO.put(nombreFichero, anexoVO);

			// asignamos el nombre correcto al anexoVO
			anexoVO.setNombreFichero(nombreFichero);

			// añadimos el anexoVO a los anexos verificados
			result.add(anexoVO);
		}

		// retornamos el array de anexos verificados
		return result;
	}

	/**
	 * Método que crea un objeto {@link PaginaDocumentoRegistroVO} a partir de
	 * los datos recibidos como parámetros
	 *
	 * @param anexoVO
	 *            - {@link AnexoVO}
	 * @param numPagina
	 *            - Número de la pagina
	 *
	 * @return {@link PaginaDocumentoRegistroVO}
	 */
	private PaginaDocumentoRegistroVO createPaginaDocumentoRegistro(
			AnexoVO anexoVO, int numPagina) {

		PaginaDocumentoRegistroVO pagina = new PaginaDocumentoRegistroVO();

		// Contenido del fichero
		byte[] contenido = getIntercambioRegistralSIRManager()
				.getContenidoAnexo(anexoVO.getId());

		// obtenemos el objeto del documento físico
		DocumentoFisicoVO documentoFisico = createDocumentoFisicoVO(contenido,
				anexoVO.getNombreFichero());

		// añadimos a la pagina del documento fisico
		pagina.setDocumentoFisico(documentoFisico);

		// añadimos el nombre a la pagina
		pagina.setName(anexoVO.getNombreFichero());

		// añadimos el numero de pagina
		pagina.setNumeroPagina(numPagina);

		return pagina;
	}

	/**
	 * Método que genera un {@link DocumentoRegistroVO} a partir de los datos
	 * recibidos
	 *
	 * @param contenido
	 *            - Array de bytes con el contenido del fichero
	 * @param nombreFichero
	 *            - Nombre del fichero
	 *
	 * @return - {@link DocumentoFisicoVO}
	 */
	private DocumentoFisicoVO createDocumentoFisicoVO(byte[] contenido,
			String nombreFichero) {
		DocumentoFisicoVO documentoFisico = new DocumentoFisicoVO();
		// seteamos el valor del nombre de la pagina
		documentoFisico.setName(nombreFichero);

		// asignamos el contenido del fichero
		documentoFisico.setContent(contenido);

		// a partir del nombre del fichero obtenemos la extensión
		documentoFisico.setExtension(nombreFichero.substring(
				nombreFichero.lastIndexOf(".") + 1, nombreFichero.length()));

		return documentoFisico;
	}

	/**
	 * Método que obtiene el nombre de la pagina/fichero, validando que no se
	 * repita en un mismo documento dos paginas con el mismo nombre
	 *
	 * @param count_nombreAnexo_duplicado
	 *            - Numero de la pagina
	 * @param anexoVO
	 *            - Información de la pagina/documento/fichero
	 * @param nombresDeAnexosVO
	 *            - Colección con los anexos
	 * @param documentoFisico
	 *            - {@link DocumentoFisicoVO}
	 *
	 * @return Nombre de la pagina/fichero
	 */
	private String obtenerNombrePagina(int count_nombreAnexo_duplicado,
			AnexoVO anexoVO, Map<String, AnexoVO> nombresDeAnexosVO) {

		// Obtenemos el nombre original de la pagina
		String result = anexoVO.getNombreFichero();

		// obtenemos el nombre del fichero
		String nombreFichero = result.substring(0, result.lastIndexOf("."));
		// obtenemos la extension del fichero
		String extension = result.substring(result.lastIndexOf("."),
				result.length());

		// comprobamos que el nombre de la pagina no exista ya en el documento
		if (nombresDeAnexosVO.containsKey(result)) {
			// si existe añadimos un sufijo, para que no existan dos ficheros
			// con el mismo nombre
			count_nombreAnexo_duplicado++;
			String sufijo = "_" + count_nombreAnexo_duplicado;

			// comprobamos que el nombre de la pagina maximo sea de 64
			// caracteres
			if ((result + sufijo).length() > IDocKeys.MAX_LENGTH_NAME_FILE) {
				// si excede el tamaño recortamos el nombre del documento
				result = (nombreFichero.substring(0,
						IDocKeys.MAX_LENGTH_NAME_FILE
								- (sufijo.length() + extension.length()))
						+ sufijo + extension);
			} else {
				// generamos el nombre del documento
				result = nombreFichero + sufijo + extension;
			}
		} else {
			// No existe el nombre de la pagina en el documento, por tanto
			// procedemos a comprobar que el nombre tiene una longitud válida
			// (64 caracteres)
			if (result.length() > IDocKeys.MAX_LENGTH_NAME_FILE) {
				// truncamos el nombre del fichero a 64 caracteres
				result = (nombreFichero.substring(0,
						(IDocKeys.MAX_LENGTH_NAME_FILE - extension.length())))
						+ extension;
			}
		}

		return result;
	}

	/**
	 * Construye el texto completo del interesado no validado que se establecerá en el registro.
	 *
	 * @param interesado
	 * @return Texto del interesado no validado.
	 */
	private String getInteresadoString(InteresadoExReg interesado) {


		StringBuffer nombreTercero = new StringBuffer("");

		String nombre = getNombreInteresadoString(interesado);
		String direccion = InteresadoUtils.getDireccionInteresado(interesado);

		nombreTercero.append(nombre);
		if (StringUtils.isNotBlank(direccion)){
			nombreTercero.append(" - ");
			nombreTercero.append(direccion);
		}

		return nombreTercero.toString();
	}

	/**
	 * Construye el texto completo del interesado no validado que se establecerá en el registro.
	 *
	 * @param interesado
	 * @return Texto del interesado no validado.
	 */
	private String getNombreInteresadoString(InteresadoVO interesado) {
		StringBuffer nombreTercero = new StringBuffer("");

		if (!StringUtils.isEmpty(interesado.getNombreInteresado())) {
			nombreTercero.append(interesado.getNombreInteresado())
					.append(BLANK);
		}

		if (!StringUtils.isEmpty(interesado.getPrimerApellidoInteresado())) {
			nombreTercero.append(interesado.getPrimerApellidoInteresado())
					.append(BLANK);
		}

		if (!StringUtils.isEmpty(interesado.getSegundoApellidoInteresado())) {
			nombreTercero.append(interesado.getSegundoApellidoInteresado())
					.append(BLANK);
		}

		if (!StringUtils.isEmpty(interesado.getRazonSocialInteresado())) {
			nombreTercero.append(interesado.getRazonSocialInteresado());
		}

		if (interesado.getTipoDocumentoIdentificacionInteresado() != null) {
			nombreTercero.append(" - ").append(
					interesado.getTipoDocumentoIdentificacionInteresado()
							.getValue());
		}

		if (!StringUtils.isEmpty(interesado
				.getDocumentoIdentificacionInteresado())) {
			nombreTercero.append(": ").append(
					interesado.getDocumentoIdentificacionInteresado());
		}

		return nombreTercero.toString();
	}

	private String getNombreRepresentanteString(InteresadoVO interesado) {
		StringBuffer nombreTercero = new StringBuffer("");

		if (!StringUtils.isEmpty(interesado.getNombreRepresentante())) {
			nombreTercero.append(interesado.getNombreRepresentante()).append(
					BLANK);
		}

		if (!StringUtils.isEmpty(interesado.getPrimerApellidoRepresentante())) {
			nombreTercero.append(interesado.getPrimerApellidoRepresentante())
					.append(BLANK);
		}

		if (!StringUtils.isEmpty(interesado.getSegundoApellidoRepresentante())) {
			nombreTercero.append(interesado.getSegundoApellidoRepresentante())
					.append(BLANK);
		}

		if (!StringUtils.isEmpty(interesado.getRazonSocialRepresentante())) {
			nombreTercero.append(interesado.getRazonSocialRepresentante());
		}

		if (interesado.getTipoDocumentoIdentificacionRepresentante() != null) {
			nombreTercero.append(" - ").append(
					interesado.getTipoDocumentoIdentificacionRepresentante()
							.getValue());
		}

		if (!StringUtils.isEmpty(interesado
				.getDocumentoIdentificacionRepresentante())) {
			nombreTercero.append(": ").append(
					interesado.getDocumentoIdentificacionRepresentante());
		}

		return nombreTercero.toString();
	}

	private String getRepresentanteString(InteresadoExReg interesado) {
		StringBuffer nombreTercero = new StringBuffer("");

		String nombre = getNombreRepresentanteString(interesado);
		nombreTercero.append(nombre);

		String direccion = InteresadoUtils.getDireccionRepresentante(interesado);
		if (StringUtils.isNotBlank(direccion)){
			nombreTercero.append(" - ");
			nombreTercero.append(direccion);
		}
		return nombreTercero.toString();
	}

	public ConfiguracionIntercambioRegistralManager getConfiguracionIntercambioRegistralManager() {
		return configuracionIntercambioRegistralManager;
	}

	public void setConfiguracionIntercambioRegistralManager(
			ConfiguracionIntercambioRegistralManager configuracionIntercambioRegistralManager) {
		this.configuracionIntercambioRegistralManager = configuracionIntercambioRegistralManager;
	}

	public BandejaEntradaIntercambioRegistralDAO getBandejaEntradaIntercambioRegistralDAO() {
		return bandejaEntradaIntercambioRegistralDAO;
	}

	public void setBandejaEntradaIntercambioRegistralDAO(
			BandejaEntradaIntercambioRegistralDAO bandejaEntradaIntercambioRegistralDAO) {
		this.bandejaEntradaIntercambioRegistralDAO = bandejaEntradaIntercambioRegistralDAO;
	}

	public BandejaSalidaIntercambioRegistralDAO getBandejaSalidaIntercambioRegistralDAO() {
		return bandejaSalidaIntercambioRegistralDAO;
	}

	public void setBandejaSalidaIntercambioRegistralDAO(
			BandejaSalidaIntercambioRegistralDAO bandejaSalidaIntercambioRegistralDAO) {
		this.bandejaSalidaIntercambioRegistralDAO = bandejaSalidaIntercambioRegistralDAO;
	}

	public DataFieldMaxValueIncrementer getIntercambioRegistralEntradaIncrementer() {
		return intercambioRegistralEntradaIncrementer;
	}

	public void setIntercambioRegistralEntradaIncrementer(
			DataFieldMaxValueIncrementer intercambioRegistralEntradaIncrementer) {
		this.intercambioRegistralEntradaIncrementer = intercambioRegistralEntradaIncrementer;
	}

	public IntercambioRegistralSIRManager getIntercambioRegistralSIRManager() {
		return intercambioRegistralSIRManager;
	}

	public void setIntercambioRegistralSIRManager(
			IntercambioRegistralSIRManager intercambioRegistralSIRManager) {
		this.intercambioRegistralSIRManager = intercambioRegistralSIRManager;
	}

	public TipoTransporteIntercambioRegistralManager getTipoTransporteIntercambioRegistralManager() {
		return tipoTransporteIntercambioRegistralManager;
	}

	public void setTipoTransporteIntercambioRegistralManager(
			TipoTransporteIntercambioRegistralManager tipoTransporteIntercambioRegistralManager) {
		this.tipoTransporteIntercambioRegistralManager = tipoTransporteIntercambioRegistralManager;
	}

	public DireccionesIntercambioRegistralManager getDireccionesIntercambioRegistralRegManager() {
		return direccionesIntercambioRegistralRegManager;
	}

	public void setDireccionesIntercambioRegistralRegManager(
			DireccionesIntercambioRegistralManager direccionesIntercambioRegistralRegManager) {
		this.direccionesIntercambioRegistralRegManager = direccionesIntercambioRegistralRegManager;
	}

	public List<BandejaEntradaItemVO> findBandejaEntradaByCriterios(
			EstadoIntercambioRegistralEntradaEnumVO estado,
			CriteriosBusquedaIREntradaVO criterios) {

		if (estado!=EstadoIntercambioRegistralEntradaEnumVO.PENDIENTE){
			return getBandejaEntradaIntercambioRegistralDAO().findByCriterios(estado, criterios);
		} else{

			CriteriosVO criteriosSIR = criteriosVOMapper.map(criterios);
			if (criteriosSIR == null){
				criteriosSIR = new CriteriosVO();
			}
			CriterioVO criterioEstado = new CriterioVO();
			criterioEstado.setNombre(CriterioEnum.ASIENTO_ESTADO);
			criterioEstado.setOperador(OperadorCriterioEnum.IN);
			EstadoAsientoRegistralEnum [] estadosPendientesEntrada = {EstadoAsientoRegistralEnum.RECIBIDO,EstadoAsientoRegistralEnum.ENVIADO};
			criterioEstado.setValor(estadosPendientesEntrada);

			criteriosSIR.addCriterioVO(criterioEstado);


			List<AsientoRegistralVO> bandejaEntrada = intercambioRegistralSIRManager.findAsientosRegistrales(criteriosSIR);
			List<BandejaEntradaItemVO> bandejaEntradaItems = new ArrayList<BandejaEntradaItemVO>();
			AsientoRegistralMapper mapper = new AsientoRegistralMapper();
			for (AsientoRegistralVO asientoRegistralVO : bandejaEntrada) {
				BandejaEntradaItemVO bandejaItem = mapper
						.toBandejaEntradaItemVO(asientoRegistralVO);
				bandejaEntradaItems.add(bandejaItem);
			}
			return bandejaEntradaItems;
		}
	}

	public PaginatedArrayList<BandejaEntradaItemVO> findBandejaEntradaByCriterios(
			EstadoIntercambioRegistralEntradaEnumVO estado,
			CriteriosBusquedaIREntradaVO criterios,
			PageInfo pageInfo) {
		if (estado!=EstadoIntercambioRegistralEntradaEnumVO.PENDIENTE){
			return getBandejaEntradaIntercambioRegistralDAO().findByCriterios(estado, criterios, pageInfo);
		} else{
			CriteriosVO criteriosSIR = getCriteriosBusquedaAsientosPendientes(criterios);
			criteriosSIR.setPageInfo(pageInfo);
			PaginatedArrayList<AsientoRegistralVO> bandejaEntrada = (PaginatedArrayList<AsientoRegistralVO>) intercambioRegistralSIRManager.findAsientosRegistrales(criteriosSIR);
			PaginatedArrayList<BandejaEntradaItemVO> bandejaEntradaItems = new PaginatedArrayList<BandejaEntradaItemVO>(bandejaEntrada.getPageInfo());
			AsientoRegistralMapper mapper = new AsientoRegistralMapper();
			for (AsientoRegistralVO asientoRegistralVO : bandejaEntrada.getList()) {
				BandejaEntradaItemVO bandejaItem = mapper
						.toBandejaEntradaItemVO(asientoRegistralVO);
				bandejaEntradaItems.add(bandejaItem);
			}
			return bandejaEntradaItems;
		}

	}

	private CriteriosVO getCriteriosBusquedaAsientosPendientes(
			CriteriosBusquedaIREntradaVO criterios) {
		CriteriosVO criteriosSIR = criteriosVOMapper.map(criterios);
		if (criteriosSIR == null){
			criteriosSIR = new CriteriosVO();
		}
		CriterioVO criterioEstado = new CriterioVO();
		criterioEstado.setNombre(CriterioEnum.ASIENTO_ESTADO);
		criterioEstado.setOperador(OperadorCriterioEnum.IN);
		EstadoAsientoRegistralEnum [] estadosPendientesEntrada = {EstadoAsientoRegistralEnum.RECIBIDO,EstadoAsientoRegistralEnum.ENVIADO};
		criterioEstado.setValor(estadosPendientesEntrada);

		criteriosSIR.addCriterioVO(criterioEstado);
		return criteriosSIR;
	}



	public CriteriosVOMapper getCriteriosVOMapper() {
		return criteriosVOMapper;
	}

	public void setCriteriosVOMapper(CriteriosVOMapper criteriosVOMapper) {
		this.criteriosVOMapper = criteriosVOMapper;
	}







}
