package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

import com.ieci.tecdoc.common.isicres.AxSf;

import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.server.pagination.PaginatedArrayList;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.business.manager.RegistroManager;
import es.ieci.tecdoc.isicres.api.business.vo.CampoGenericoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoLibroEnum;
import es.ieci.tecdoc.isicres.api.intercambio.registral.business.util.IntercambioRegistralConfiguration;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.BandejaSalidaIntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralException;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralExceptionCodes;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.ConfiguracionIntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralGeneradorObjetosManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralSIRManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralSalidaManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaSalidaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIRSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO;

public class IntercambioRegistralSalidaManagerImpl implements
		IntercambioRegistralSalidaManager {

	private static Logger logger = Logger.getLogger(IntercambioRegistralSalidaManagerImpl.class);

	/**
	 * dao para obtencion de la configuracion de intercambio registral
	 */
	protected ConfiguracionIntercambioRegistralManager configuracionIntercambioRegistralManager;

	/**
	 * Manager para el SIR
	 */
	protected IntercambioRegistralSIRManager intercambioRegistralSIRManager;

	protected RegistroManager registroManager;



	/**
	 * dao para obtencion de los elementos de la bandeja de salida de intercambio
	 */
	protected BandejaSalidaIntercambioRegistralDAO bandejaSalidaIntercambioRegistralDAO;

	/**
	 * Manager para construir objetos necesitados por el SIR
	 */
	protected IntercambioRegistralGeneradorObjetosManager intercambioRegistralGeneradorObjetosManager;

	/**
	 * Incrementer para obtener el id del intercambio registral de salida
	 */
	protected DataFieldMaxValueIncrementer intercambioRegistralSalidaIncrementer;


	/**
	 * Incrementer para obtener el id de las actualizaciones de estado del intercambio registral de salida
	 */
	protected DataFieldMaxValueIncrementer intercambioRegistralSalidaEstadoIncrementer;


	public boolean isIntercambioRegistral(String idUnidadAdministrativa) {

		boolean result=false;
		if(!StringUtils.isEmpty(idUnidadAdministrativa))
		{
			UnidadTramitacionIntercambioRegistralVO unidadDestino = getConfiguracionIntercambioRegistralManager().getUnidadTramitacionIntercambioRegistralVOByIdScrOrgs(idUnidadAdministrativa);

			if(unidadDestino!=null && StringUtils.isNotEmpty(unidadDestino.getCodeEntity()))
			{
				result=true;
			}
		}
		if(logger.isDebugEnabled())
		{
			String resultString = result?"SI":"NO";
			logger.debug("La unidad admnistrativa "+idUnidadAdministrativa+" "+resultString+" tiene configuracion de intercambio registral");
		}

		return result;
	}

	/**
	 * @deprecated
	 */
	public void toIntercambioRegistral(String idLibro, String idRegistro, String idOfic,
			String tipoOrigen, String idUnidadTramitacionDestino, String user) {

		if(isIntercambioRegistral(idUnidadTramitacionDestino))
		{

			IntercambioRegistralSalidaVO intercambioRegistral = new IntercambioRegistralSalidaVO();

			intercambioRegistral.setId(getIntercambioRegistralSalidaIncrementer().nextLongValue());
			intercambioRegistral.setIdLibro(Long.valueOf(idLibro));
			intercambioRegistral.setIdOfic(Integer.valueOf(idOfic));
			intercambioRegistral.setIdRegistro(Long.valueOf(idRegistro));
			intercambioRegistral.setEstado(EstadoIntercambioRegistralSalidaEnumVO.PENDIENTE);
			intercambioRegistral.setFechaEstado(Calendar.getInstance().getTime());
			intercambioRegistral.setTipoOrigen(Integer.valueOf(tipoOrigen));
			intercambioRegistral.setUsername(user);
			getBandejaSalidaIntercambioRegistralDAO().save(intercambioRegistral);

			//AUDITORIA DE CAMBIOS DE ESTADO
			saveHistorialCambiosEstado(intercambioRegistral);

			if(logger.isDebugEnabled())
			{
				logger.debug("Guardado el intercambio registral de salida con id = "+intercambioRegistral.getId());
			}
		}
		else
		{
			logger.info("No se puede guardar el intercambio registral de salida porque la unidad de tramitacion destino no tiene configuracion de intercambio registral mapeada.");
			throw new IntercambioRegistralException("No se puede guardar el intercambio registral de salida porque la unidad de tramitacion destino no tiene configuracion de intercambio registral mapeada.", IntercambioRegistralExceptionCodes.ERROR_CODE_UNIDAD_TRAMITACION_NO_MAPEADA);
		}
	}

	public void toIntercambioRegistralManual(List<String> idRegistros,
			String idLibro, String idOfic, String tipoOrigen, String user) {
		for (String idRegistro : idRegistros) {
			if(!isInBandejasalidaIntercambioRegistral(idRegistro, idLibro))
			{
				IntercambioRegistralSalidaVO intercambioRegistral = new IntercambioRegistralSalidaVO();

				intercambioRegistral.setId(getIntercambioRegistralSalidaIncrementer().nextLongValue());
				intercambioRegistral.setIdLibro(Long.valueOf(idLibro));
				intercambioRegistral.setIdOfic(Integer.valueOf(idOfic));
				intercambioRegistral.setIdRegistro(Long.valueOf(idRegistro));
				intercambioRegistral.setEstado(EstadoIntercambioRegistralSalidaEnumVO.PENDIENTE);
				intercambioRegistral.setFechaEstado(Calendar.getInstance().getTime());
				intercambioRegistral.setTipoOrigen(Integer.valueOf(tipoOrigen));
				intercambioRegistral.setUsername(user);
				getBandejaSalidaIntercambioRegistralDAO().save(intercambioRegistral);

				if(logger.isDebugEnabled())
				{
					logger.debug("Guardado el intercambio registral de salida con id = "+intercambioRegistral.getId());
				}
			}
			else
			{
				logger.info("El registro del libro = "+idLibro+" con id = "+idRegistro+" YA ESTÁ en la bandeja de salida de intercambio registral.");
			}
		}

	}

	private void saveBandejaSalida(IntercambioRegistralSalidaVO intercambioRegistralSalida,IdentificadorRegistroVO identificadorRegistro,TipoLibroEnum tipoLibro){

		getBandejaSalidaIntercambioRegistralDAO().save(intercambioRegistralSalida);

		saveHistorialCambiosEstado(intercambioRegistralSalida);

		UsuarioVO usuario = IsicresManagerProvider.getInstance().getContextoAplicacionManager().getUsuarioActual();

		//Actualizamos los datos de registro referentes a registo
		List <CampoGenericoRegistroVO> camposGenericos = new ArrayList<CampoGenericoRegistroVO>();
		CampoGenericoRegistroVO campoInvolucradoIntercambioRegistral = new CampoGenericoRegistroVO(Integer.toString(AxSf.FLD503_FIELD_ID),"1");
		camposGenericos.add(campoInvolucradoIntercambioRegistral);

		if (TipoLibroEnum.ENTRADA  == tipoLibro){
			registroManager.updateRegistroEntradaIR(usuario, identificadorRegistro, camposGenericos );
		}else{
			registroManager.updateRegistroSalidaIR(usuario, identificadorRegistro, camposGenericos );
		}
	}



	public void anularIntercambioRegistralSalidaById(String id) {

		BandejaSalidaIntercambioRegistralDAO dao = getBandejaSalidaIntercambioRegistralDAO();
		IntercambioRegistralSalidaVO intecambioRegistralSalida = dao.get(Long.parseLong(id));

		EstadoIntercambioRegistralSalidaVO estadoAnulado = new EstadoIntercambioRegistralSalidaVO();
		estadoAnulado.setEstado(EstadoIntercambioRegistralSalidaEnumVO.ANULADO);
		estadoAnulado.setFechaEstado(new Date());
		estadoAnulado.setIdExReg(Long.parseLong(id));
		//TODO obtener este usuario
		String userName = "usuario anulando";
		estadoAnulado.setUserName(userName );
		updateEstado(intecambioRegistralSalida, estadoAnulado  );
		if(logger.isDebugEnabled())
		{
			logger.debug("Anulado el intercambio registral de salida con id = "+id);
		}
	}




	public String enviarIntercambioRegistralSalida(String idLibro,
			String idRegistro, String idOfic, String username,
			String tipoOrigen,
			UnidadTramitacionIntercambioRegistralVO unidadDestino) {

		String result = null;

		IdentificadorRegistroVO identificadorRegistro = new IdentificadorRegistroVO(
				idRegistro, idLibro);
		TipoLibroEnum tipoLibro = TipoLibroEnum.getEnum(Integer
				.parseInt(tipoOrigen));

		List<IntercambioRegistralSalidaVO> listaIntercambiosSalida = getBandejaSalidaIntercambioRegistralDAO()
				.getIntercambiosRegistralesSalida(Integer.valueOf(idRegistro),
						Integer.valueOf(idLibro), Integer.valueOf(idOfic));

		ListIterator<IntercambioRegistralSalidaVO> itr = listaIntercambiosSalida
				.listIterator();
		IntercambioRegistralSalidaVO temp = null;

		IntercambioRegistralSalidaVO intercambioSalida = new IntercambioRegistralSalidaVO();
		intercambioSalida.setIdLibro(Long.parseLong(idLibro));
		intercambioSalida.setIdRegistro(Long.parseLong(idRegistro));
		intercambioSalida.setIdOfic(Integer.parseInt(idOfic));
		intercambioSalida.setTipoOrigen(Integer.parseInt(tipoOrigen));
		intercambioSalida.setUsername(username);

		// enviamos al sir y actualizamos valores el vo intercambioSalida
		result = enviarIntercambioRegistralSalida(intercambioSalida,
				unidadDestino);

		// guardamos en la bandeja de salida
		saveBandejaSalida(intercambioSalida, identificadorRegistro, tipoLibro);

		return result;
	}


	/**
	 * {@inheritDoc}
	 */
	public void undoAnularIntercambioRegistral(String id) {
		BandejaSalidaIntercambioRegistralDAO dao = getBandejaSalidaIntercambioRegistralDAO();
		IntercambioRegistralSalidaVO intecambioRegistralSalida = dao.get(Long.parseLong(id));

		EstadoIntercambioRegistralSalidaVO estadoPendiente = new EstadoIntercambioRegistralSalidaVO();
		estadoPendiente.setEstado(EstadoIntercambioRegistralSalidaEnumVO.PENDIENTE);
		estadoPendiente.setFechaEstado(new Date());
		estadoPendiente.setIdExReg(Long.parseLong(id));
		//TODO obtener este usuario
		String userName = "usuario desanulando";
		estadoPendiente.setUserName(userName );

		updateEstado(intecambioRegistralSalida, estadoPendiente);
		if(logger.isDebugEnabled())
		{
			logger.debug("Desanulado el intercambio registral de salida con id = "+id);
		}

	}

	public void updateEstado(
			IntercambioRegistralSalidaVO intecambioRegistralSalida,
			EstadoIntercambioRegistralSalidaVO estado) {
		getBandejaSalidaIntercambioRegistralDAO().updateEstado(intecambioRegistralSalida, estado);

		//AUDITORIA DE CAMBIOS DE ESTADO
		saveHistorialCambiosEstado(intecambioRegistralSalida);
	}

	public List<IntercambioRegistralSalidaVO> getIntercambiosRegistralesSalida(
			Integer estado) {
		return getBandejaSalidaIntercambioRegistralDAO().getIntercambiosRegistralesSalida(estado);
	}

	public BandejaSalidaItemVO completarBandejaSalidaItem(
			BandejaSalidaItemVO bandejaSalidaItemVO) {
		bandejaSalidaItemVO = getBandejaSalidaIntercambioRegistralDAO().completarBandejaSalidaItem(bandejaSalidaItemVO);
		return bandejaSalidaItemVO;
	}

	public List<BandejaSalidaItemVO> getBandejaSalidaIntercambioRegistral(
			Integer estado, Integer idOficina) {
		return getBandejaSalidaIntercambioRegistralDAO().getBandejaSalidaByEstadoYOficina(estado,idOficina);
	}

	public List<BandejaSalidaItemVO> getBandejaSalidaIntercambioRegistral(
			Integer estado, Integer idOficina, Integer idLibro) {
		return getBandejaSalidaIntercambioRegistralDAO().getBandejaSalidaByEstadoOficinaYLibro(estado,idOficina, idLibro);
	}

	public IntercambioRegistralSalidaVO getIntercambioRegistralSalidaById(
			String id) {
		IntercambioRegistralSalidaVO intercambio = null;
		try{
			intercambio= getBandejaSalidaIntercambioRegistralDAO().get(Long.parseLong(id));
		}
		catch (NumberFormatException e) {
			logger.error("Error al parsear el ID de intercambio registral", e);
		}
		return intercambio;
	}


	public void deleteIntercambioRegistralSalida(Integer idLibro,
			Integer idRegistro, Integer idOficina) {
		//TODO ¿Comprobar que está pendiente?
		//¿Si está enviado...excepcion? retornamos true/false?
		getBandejaSalidaIntercambioRegistralDAO().deleteByIdArchIdFdr(idLibro, idRegistro,idOficina);

		if (logger.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append(
					"Eliminado el intercambio registral de salida del registro ")
					.append(idRegistro).append(" del libro ").append(idLibro);
			logger.debug(sb.toString());
		}

	}


	//TODO no se usa no vale para nada de momento
	public void reenviarIntercambioRegistralSalidaById(
			String id, String usuario,String contacto,String descripcionReenvio, UnidadTramitacionIntercambioRegistralVO nuevoDestino) {

			getIntercambioRegistralSIRManager().reenviarAsientoRegistral(id, usuario,contacto,descripcionReenvio,nuevoDestino);

			// comprobar si esta tambien en la tabla scr_exreg (puedo haber sido emisor original y si es asi actualizar estado a eenviado)
			IntercambioRegistralSalidaVO intecambioRegistralSalida = null;
			intecambioRegistralSalida = getBandejaSalidaIntercambioRegistralDAO().get(Long.parseLong(id));

			EstadoIntercambioRegistralSalidaVO estadoEnviado = new EstadoIntercambioRegistralSalidaVO();
			estadoEnviado.setEstado(EstadoIntercambioRegistralSalidaEnumVO.ENVIADO);
			estadoEnviado.setFechaEstado(new Date());
			estadoEnviado.setIdExReg(Long.parseLong(id));
			estadoEnviado.setUserName(usuario );
		//	aa
			updateEstado(intecambioRegistralSalida , estadoEnviado);


			//getBandejaSalidaIntercambioRegistralDAO().getBandejaSalidaByIdIntercambioRegistralSirYOficina(idIntercambioRegistralSir, idOficina);
	}

	public List<IntercambioRegistralSalidaVO> getHistorialIntercambioRegistralSalida(String idLibro,
			String idRegistro, String idOficina) {

		List<IntercambioRegistralSalidaVO> intercambiosRegistralSalidaVO = null;
		if (idOficina == null){
			intercambiosRegistralSalidaVO = getBandejaSalidaIntercambioRegistralDAO()
				.getIntercambiosRegistralesSalida(Integer.parseInt(idRegistro), Integer.parseInt(idLibro), null);
		}else{
			intercambiosRegistralSalidaVO = getBandejaSalidaIntercambioRegistralDAO()
					.getIntercambiosRegistralesSalida(Integer.parseInt(idRegistro), Integer.parseInt(idLibro), Integer.parseInt(idOficina));
		}

		for(Iterator<IntercambioRegistralSalidaVO> it=intercambiosRegistralSalidaVO.iterator();it.hasNext();){
			IntercambioRegistralSalidaVO intercambioReg = it.next();

			//obtenemos la informacion de los cambio de estado para el intercambio registral
			List<EstadoIntercambioRegistralSalidaVO> cambiosEstadoIntercambioRegistral = getBandejaSalidaIntercambioRegistralDAO()
					.getDetalleEstadosIntercambioRegistralSalida(
							intercambioReg.getId());

			intercambioReg.setEstadosIntercambioRegistralSalida(cambiosEstadoIntercambioRegistral);
		}

		return intercambiosRegistralSalidaVO;

	}

	/**
	 * Metodo que inserta el detalle de un cambio de estado
	 * @param intercambioRegistral
	 */
	private void saveHistorialCambiosEstado(
			IntercambioRegistralSalidaVO intercambioRegistral) {
		//creamos un detalle con la información del estado del intercambio registral
		EstadoIntercambioRegistralSalidaVO estado = new EstadoIntercambioRegistralSalidaVO();
		estado.setEstado(intercambioRegistral.getEstado());
		estado.setFechaEstado(intercambioRegistral.getFechaEstado());
		estado.setId(getIntercambioRegistralSalidaEstadoIncrementer().nextLongValue());
		estado.setIdExReg(intercambioRegistral.getId());
		estado.setUserName(intercambioRegistral.getUsername());

		//almacenamos los datos
		getBandejaSalidaIntercambioRegistralDAO().saveDetalleEstado(estado);

	}

	/**
	 * Método privado para enviar el registro al SIR y actualizar su estado, una
	 * vez que ya tenemos compuestos los objetos necesarios.
	 *
	 * @param intercambioRegistralSalida
	 * @param asientoParaIntercambio
	 * @return String. El identificador retornado por el SIR para el intercambio
	 */
	private String enviarIntercambioRegistralSalida(
			IntercambioRegistralSalidaVO intercambioRegistralSalida,
			UnidadTramitacionIntercambioRegistralVO unidadDestino) {

		// Convertimos y obtenemos datos para los objetos que necesita el SIR
		AsientoRegistralFormVO asientoRegistralIntercambio = getIntercambioRegistralGeneradorObjetosManager()
				.getAsientoRegistralIntercambioRegistralVO(
						intercambioRegistralSalida, unidadDestino);

		//validamos los datos del intercambio
		validateDatosIR(asientoRegistralIntercambio);

		// Externalizar propiedad que indique si es una prueba
		if (true) {
			asientoRegistralIntercambio
					.setIndicadorPrueba(IndicadorPruebaEnum.NORMAL);
		}

		// enviamos el intercambio registral al modulo intermedio
		AsientoRegistralVO asiento = getIntercambioRegistralSIRManager()
				.enviarAsientoRegistral(asientoRegistralIntercambio);
		if (StringUtils.isNotEmpty(asiento.getCodigoError())) {
			logger
					.error("Ha ocurrido un error en el envio del asiento de intercambio registral con id = "
							+ intercambioRegistralSalida.getId());
			logger.error("Codigo de error = " + asiento.getCodigoError());
			logger.error("Descripcion de error = "
					+ asiento.getDescripcionError());
			throw new IntercambioRegistralException(
					"Ha ocurrido un error en el envio del asiento de intercambio registral",
					IntercambioRegistralExceptionCodes.ERROR_NOT_SEND_INTERCAMBIO_REGISTRAL);
		}

		intercambioRegistralSalida
				.setId(getIntercambioRegistralSalidaIncrementer()
						.nextLongValue());
		intercambioRegistralSalida.setIdIntercambioRegistral(asiento
				.getIdentificadorIntercambio());
		intercambioRegistralSalida.setIdIntercambioInterno(asiento.getId());
		intercambioRegistralSalida.setCodeEntity(asiento
				.getCodigoEntidadRegistralDestino());
		intercambioRegistralSalida.setNameEntity(asiento
				.getDescripcionEntidadRegistralDestino());
		intercambioRegistralSalida.setCodeTramunit(asiento
				.getCodigoUnidadTramitacionDestino());
		intercambioRegistralSalida.setNameTramunit(asiento
				.getDescripcionUnidadTramitacionDestino());
		intercambioRegistralSalida
				.setEstado(EstadoIntercambioRegistralSalidaEnumVO.ENVIADO);
		intercambioRegistralSalida.setFechaIntercambio(Calendar.getInstance()
				.getTime());
		intercambioRegistralSalida.setFechaEstado(Calendar.getInstance()
				.getTime());

		if (logger.isDebugEnabled()) {
			logger
					.debug("Se ha enviado correctamente el asiento de intercambio registral con id = "
							+ intercambioRegistralSalida.getId()
							+ " e identificador de intercambio = "
							+ asiento.getIdentificadorIntercambio());
		}

		return intercambioRegistralSalida.getIdIntercambioRegistral();
	}

	/**
	 * Méotodo que valida los datos para realizar el IR
	 * @param asientoRegistralIntercambio
	 */
	private void validateDatosIR(
			AsientoRegistralFormVO asientoRegistralIntercambio) {

		//validamos que la unidad de destino se corresponda con la entidad de destino
		validateRelacionEntidadRegistralUnidadTramitacion(
				asientoRegistralIntercambio.getCodigoEntidadRegistralDestino(),
				asientoRegistralIntercambio.getCodigoUnidadTramitacionDestino());
	}

	/**
	 * Método que valida la relación entre el cod. Entidad y el cod. de la Unid.
	 * si por configuración esta activa la validación:
	 * intercambioRegistral.properties
	 *
	 * @param codigoEntidad
	 * @param codigoUnidTram
	 */
	private void validateRelacionEntidadRegistralUnidadTramitacion(
			String codigoEntidad, String codigoUnidTram) {

		// comprobamos si esta activa la validación para verificar la relación
		// entre entidad y unidad
		if (IntercambioRegistralConfiguration.getInstance()
				.getActiveValidationRelationEntidadUnidad()) {
			//validamos la relación
			activeValidateRelacionEntidadRegistralUnidadTramitacion(
					codigoEntidad, codigoUnidTram);
		}
	}

	/**
	 * Método que valida la relación entre el cod. Entidad y el cod. de la Unid.
	 * @param codigoEntidad
	 * @param codigoUnidTram
	 */
	private void activeValidateRelacionEntidadRegistralUnidadTramitacion(
			String codigoEntidad, String codigoUnidTram) {
		if (logger.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append("Validando relación de la entidad [")
					.append(codigoEntidad)
					.append("] con la unid. tramitación [")
					.append(codigoUnidTram).append("]");
			logger.debug(sb.toString());
		}

		// se comprueba si el codigo de unidad es distinto de blanco
		if (StringUtils.isNotBlank(codigoUnidTram)) {
			// si esta rellena, pasamos a comprobar si existe una relación
			// con
			// la entidad registral
			if (!getConfiguracionIntercambioRegistralManager()
					.existRelacionUnidOrgaOficina(codigoEntidad,
							codigoUnidTram)) {
				// al no existir relación entre la unidad y la entidad
				// devolvemos excepción alertando del problema
				throw new IntercambioRegistralException(
						"La unid. de tramitación indicada no se corresponde con la entidad registral",
						IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_UNID_TRAMITA_ENTIDAD_REG);
			}
		}
	}

	public BandejaSalidaIntercambioRegistralDAO getBandejaSalidaIntercambioRegistralDAO() {
		return bandejaSalidaIntercambioRegistralDAO;
	}

	public void setBandejaSalidaIntercambioRegistralDAO(
			BandejaSalidaIntercambioRegistralDAO bandejaSalidaIntercambioRegistralDAO) {
		this.bandejaSalidaIntercambioRegistralDAO = bandejaSalidaIntercambioRegistralDAO;
	}



	public ConfiguracionIntercambioRegistralManager getConfiguracionIntercambioRegistralManager() {
		return configuracionIntercambioRegistralManager;
	}


	public void setConfiguracionIntercambioRegistralManager(
			ConfiguracionIntercambioRegistralManager configuracionIntercambioRegistralManager) {
		this.configuracionIntercambioRegistralManager = configuracionIntercambioRegistralManager;
	}


	public IntercambioRegistralGeneradorObjetosManager getIntercambioRegistralGeneradorObjetosManager() {
		return intercambioRegistralGeneradorObjetosManager;
	}


	public void setIntercambioRegistralGeneradorObjetosManager(
			IntercambioRegistralGeneradorObjetosManager intercambioRegistralGeneradorObjetosManager) {
		this.intercambioRegistralGeneradorObjetosManager = intercambioRegistralGeneradorObjetosManager;
	}


	public DataFieldMaxValueIncrementer getIntercambioRegistralSalidaIncrementer() {
		return intercambioRegistralSalidaIncrementer;
	}

	public DataFieldMaxValueIncrementer getIntercambioRegistralSalidaEstadoIncrementer() {
		return intercambioRegistralSalidaEstadoIncrementer;
	}

	public void setIntercambioRegistralSalidaEstadoIncrementer(
			DataFieldMaxValueIncrementer intercambioRegistralSalidaEstadoIncrementer) {
		this.intercambioRegistralSalidaEstadoIncrementer = intercambioRegistralSalidaEstadoIncrementer;
	}

	public void setIntercambioRegistralSalidaIncrementer(
			DataFieldMaxValueIncrementer intercambioRegistralSalidaIncrementer) {
		this.intercambioRegistralSalidaIncrementer = intercambioRegistralSalidaIncrementer;
	}


	public IntercambioRegistralSIRManager getIntercambioRegistralSIRManager() {
		return intercambioRegistralSIRManager;
	}


	public void setIntercambioRegistralSIRManager(
			IntercambioRegistralSIRManager intercambioRegistralSIRManager) {
		this.intercambioRegistralSIRManager = intercambioRegistralSIRManager;
	}


	public boolean isInBandejasalidaIntercambioRegistral(String idRegistro,
			String idLibro) {
		// TODO Auto-generated method stub
		return false;
	}

	public RegistroManager getRegistroManager() {
		return registroManager;
	}

	public void setRegistroManager(RegistroManager registroManager) {
		this.registroManager = registroManager;
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralSalidaManager#findBandejaSalidaByCriterios(es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO, es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIRSalidaVO)
	 */
	public List<BandejaSalidaItemVO> findBandejaSalidaByCriterios(
			EstadoIntercambioRegistralSalidaEnumVO estado, CriteriosBusquedaIRSalidaVO criterios, Integer idLibro) {
		return getBandejaSalidaIntercambioRegistralDAO().findByCriterios(estado, criterios, idLibro);
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralSalidaManager#findBandejaSalidaByCriterios(es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO, es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIRSalidaVO, es.ieci.tecdoc.fwktd.server.pagination.PageInfo)
	 */
	public PaginatedArrayList<BandejaSalidaItemVO> findBandejaSalidaByCriterios(
			EstadoIntercambioRegistralSalidaEnumVO estado,
			CriteriosBusquedaIRSalidaVO criterios, Integer idLibro, PageInfo pageInfo) {
		return getBandejaSalidaIntercambioRegistralDAO().findByCriterios(estado, criterios, idLibro, pageInfo);
	}

}
