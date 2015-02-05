package es.ieci.tecdoc.fwktd.sir.api.manager.impl.ws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sir.api.manager.ConfiguracionManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.TrazabilidadManager;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir1.RespuestaWS;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir1.TrazabilidadWS;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir1.WS_SIR1Service;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir1.WS_SIR1_PortType;
import es.ieci.tecdoc.fwktd.sir.core.exception.SIRException;
import es.ieci.tecdoc.fwktd.sir.core.util.ToStringLoggerHelper;
import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;

/**
 * Implementación del manager de trazabilidad que utiliza el servicio web
 * WS_SIR1_WS_DE_TRAZABILIDAD del SIR.
 *
 * Recoge y sirve la información de trazabilidad del asiento registral, conforme
 * a los estados descritos en SICRES 3.0.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TrazabilidadManagerWSSIR1Impl implements TrazabilidadManager {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TrazabilidadManagerWSSIR1Impl.class);

	private static final String WS_SIR1_URL_PARAM_NAME = "WS_SIR1.url";

	/**
	 * Localizador del servicio WS_SIR1
	 */
	private WS_SIR1Service serviceLocator = null;

	/**
	 * Gestor de configuración.
	 */
	private ConfiguracionManager configuracionManager = null;


    /**
     * Constructor.
     */
    public TrazabilidadManagerWSSIR1Impl() {
        super();
    }

	public WS_SIR1Service getServiceLocator() {
		return serviceLocator;
	}

	public void setServiceLocator(WS_SIR1Service serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

	public ConfiguracionManager getConfiguracionManager() {
		return configuracionManager;
	}

	public void setConfiguracionManager(ConfiguracionManager configuracionManager) {
		this.configuracionManager = configuracionManager;
	}

	/**
	 * Consulta los estados de trazabilidad insertados asociados a un
	 * intercambio.
	 *
	 * @param codEntReg
	 *            Código de la Entidad Registral.
	 * @param codigoIntercambio
	 *            Código único de intercambio de registro.
	 * @param isRegistro
	 *            Flag que indica si se trata de un registro o un mensaje.
	 *            True-Fichero de intercambio, False-Fichero de mensaje.
	 * @param soloEstadoFinal
	 *            Flag que indica si se desean recuperar todos los estados, o
	 *            únicamente los finales (Enviado, Reenviado, Rechazado o
	 *            Confirmado). True: indica que se quieren recuperar solo los
	 *            estados finales. False: se recuperarán todos los estados.
	 * @return Información de la trazabilidad.
	 */
	public List<TrazabilidadVO> getTrazabilidad(String codEntReg,
			String codigoIntercambio, boolean isRegistro,
			boolean soloEstadoFinal) {

		logger.info(
				"Llamada a getTrazabilidad: codEntReg=[{}], codigoIntercambio=[{}], isRegistro=[{}], soloEstadoFinal=[{}]",
				new Object[] { codEntReg, codigoIntercambio, isRegistro, soloEstadoFinal });

		Assert.notNull(getServiceLocator(), "'serviceLocator' must not be null");
		Assert.notNull(codEntReg, "'codEntReg' must not be null");
		Assert.notNull(codigoIntercambio, "'codigoIntercambio' must not be null");

		List<TrazabilidadVO> listaTrazas = new ArrayList<TrazabilidadVO>();

		try {
			
			// Obtener la lista de trazas
			TrazabilidadWS[] trazas = getService(codEntReg).recuperarTrazabilidad(
					codigoIntercambio, (isRegistro ? "S" : "N"), soloEstadoFinal);
			if (trazas != null) {
				for (TrazabilidadWS traza : trazas) {
					if (traza != null) {
						logger.debug("Traza: {}", traza);
						listaTrazas.add(getTrazabilidadVO(traza));
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("Error al obtener las trazas", e);
			throw new SIRException("error.sir.ws.wssir1", null,
					"Error en la llamada al servicio de trazabilidad (WS_SIR1)");
		}

		return listaTrazas;
    }

	/**
	 * Inserta una traza asociada a un intercambio.
	 * 
	 * @param traza
	 *            Información de la trazabilidad.
	 * @return
	 */
	public void insertarTrazabilidad(TrazabilidadVO traza) {

		if (logger.isInfoEnabled()){
			logger.info("Llamada a insertarTrazabilidad: traza=[{}]", ToStringLoggerHelper.toStringLogger(traza));
		}

		Assert.notNull(getServiceLocator(), "'serviceLocator' must not be null");
		Assert.notNull(traza, "'traza' must not be null");

		RespuestaWS respuesta = null;
		
		try {
			
			// Insertar la traza
			respuesta = getService(traza.getCodigoEntidadRegistralOrigen())
					.insertarTrazabilidad(
					traza.getCodigoNodo(),
					traza.getCodigoEntidadRegistralDestino(), 
					traza.getCodigoEntidadRegistralOrigen(), 
					traza.getDescripcionEntidadRegistralDestino(), 
					traza.getDescripcionEntidadRegistralOrigen(), 
					traza.getCodigoUnidadTramitacionDestino(),
					traza.getDescripcionUnidadTramitacionDestino(), 
					traza.getCodigoUnidadTramitacionOrigen(), 
					traza.getDescripcionUnidadTramitacionOrigen(), 
					traza.getDescripcionErrorAlternativa(),
					toCalendar(traza.getFechaAlta()), 
					toCalendar(traza.getFechaModificacion()), 
					traza.getCodigoIntercambio(), 
					traza.getNombreFicheroIntercambio(), 
					traza.getCodigoError(),
					traza.getCodigoEstado(), 
					traza.getMotivoRechazo(), 
					traza.getTamanyoDocs(), 
					(traza.isRegistro() ? "S" : "N"));
	
		} catch (Exception e) {
			logger.error("Error al insertar la traza: " + ToStringLoggerHelper.toStringLogger(traza), e);
			throw new SIRException("error.sir.ws.wssir1", null,
					"Error en la llamada al servicio de trazabilidad (WS_SIR1)");
		}

		// Comprobar la respuesta del servicio web
		if (respuesta != null) {
		
			// la respuesta en caso de exito de trazabilidad está devolviendo 0 en vez de 00
			if (respuesta.getCodigo().equals("0")){
				respuesta.setCodigo("00");
			}
			WSSIRHelper.checkRespuesta(respuesta.getCodigo(), respuesta.getDescripcion());
		}
	}

    /**
     * Compone un objeto TrazabilidadVO a partir de un TrazabilidadWS.
     * @param traza objeto TrazabilidadWS
     * @return objeto TrazabilidadVO
     */
    protected TrazabilidadVO getTrazabilidadVO(TrazabilidadWS traza) {

    	TrazabilidadVO trazaVO = new TrazabilidadVO();

    	trazaVO.setCodigo(traza.getCodigo());
    	trazaVO.setDescripcion(traza.getDescripcion());
    	trazaVO.setCodigoError(traza.getCdError());
    	trazaVO.setDescripcionErrorAlternativa(traza.getDsErrorAlternativa());
    	trazaVO.setCodigoErrorServicio(traza.getCdErrorServicio());
    	trazaVO.setDescripcionErrorServicio(traza.getDsErrorServicio());    	
    	trazaVO.setCodigoIntercambio(traza.getCdIntercambio());
    	trazaVO.setCodigoEntidadRegistralOrigen(traza.getCdOrOrigen());
    	trazaVO.setDescripcionEntidadRegistralOrigen(traza.getDsOrOrigen());
    	trazaVO.setCodigoEntidadRegistralDestino(traza.getCdOrDestino());
    	trazaVO.setDescripcionEntidadRegistralDestino(traza.getDsOrDestino());
    	trazaVO.setCodigoUnidadTramitacionOrigen(traza.getCdUgOrigen());
    	trazaVO.setDescripcionUnidadTramitacionOrigen(traza.getDsUgOrigen());
    	trazaVO.setCodigoUnidadTramitacionDestino(traza.getCdUgDestino());
    	trazaVO.setDescripcionUnidadTramitacionDestino(traza.getDsUgDestino());
    	trazaVO.setCodigoEstado(traza.getCdEstado());
    	trazaVO.setCodigoNodo(traza.getCdNodoError());
    	trazaVO.setNombreFicheroIntercambio(traza.getDsNombreFichero());
    	trazaVO.setMotivoRechazo(traza.getDsMotivoRechazo());
    	trazaVO.setFechaAlta(WSSIRHelper.parseDateTrazabilidad(traza.getFechaAlta()));
    	trazaVO.setFechaModificacion(WSSIRHelper.parseDateTrazabilidad(traza.getFechaMod()));
    	trazaVO.setRegistro(StringUtils.equalsIgnoreCase(traza.getRegistro(), "S"));
    	trazaVO.setTamanyoDocs(traza.getTamanyoDocs() != null ? traza.getTamanyoDocs() : 0);

    	return trazaVO;
    }
    
	protected Calendar toCalendar(Date date) {
		Calendar calendar = null;
		
		if (date != null) {
			calendar = Calendar.getInstance();
			calendar.setTime(date);
		}
		
		return calendar;
	}

	/**
	 * Obtiene el servicio a partir de la entidad registral de destino.
	 * 
	 * @param codEntReg
	 *            Código de Entidad Registral.
	 * @return Servicio.
	 * @throws ServiceException
	 *             si ocurre algún error al cargar el servicio.
	 * @throws MalformedURLException
	 *             si la URL del servicio web no es correcta.
	 */
	private WS_SIR1_PortType getService(String codEntReg)
			throws MalformedURLException, ServiceException {

		logger.info(
				"Obteniendo el WS_SIR1_PortType para la entidad registral [{}]",
				codEntReg);

		// URL del servicio
		String servicePortAddress = getConfiguracionManager()
				.getValorConfiguracion(
						new String[] {
								codEntReg + "." + WS_SIR1_URL_PARAM_NAME,
								WS_SIR1_URL_PARAM_NAME });
		logger.info("URL obtenida para el servicio web WS_SIR1_PortType: {}",
				servicePortAddress);

		return getServiceLocator().getWS_SIR1(new URL(servicePortAddress));
	}
}
