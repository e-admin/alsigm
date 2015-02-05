package es.ieci.tecdoc.fwktd.sir.api.manager.impl.ws;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sir.api.manager.ConfiguracionManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.SicresXMLManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.impl.MensajeManagerImpl;
import es.ieci.tecdoc.fwktd.sir.api.manager.impl.ToStringLoggerApiHelper;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir7.RespuestaWS;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir7.WS_SIR7Service;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir7.WS_SIR7_PortType;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;
import es.ieci.tecdoc.fwktd.sir.core.exception.SIRException;
import es.ieci.tecdoc.fwktd.sir.core.util.ToStringLoggerHelper;

/**
 * Implementación del manager de envío del fichero de datos de control en
 * formato SICRES 3.0 generado por la aplicación origen. Esta implementación
 * utiliza el servicio web WS_SIR7 del CIR.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class MensajeManagerWSSIR7Impl extends MensajeManagerImpl {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(MensajeManagerWSSIR7Impl.class);

	private static final String WS_SIR7_URL_PARAM_NAME = "WS_SIR7.url";
	
	/**
	 * Localizador del servicio WS_SIR7
	 */
	private WS_SIR7Service serviceLocator = null;

	/**
	 * Gestor de XML de SICRES.
	 */
	private SicresXMLManager sicresXMLManager = null;

	/**
	 * Gestor de configuración.
	 */
	private ConfiguracionManager configuracionManager = null;

	/**
	 * Constructor.
	 */
	public MensajeManagerWSSIR7Impl() {
		super();
	}

	public WS_SIR7Service getServiceLocator() {
		return serviceLocator;
	}

	public void setServiceLocator(WS_SIR7Service serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

	public SicresXMLManager getSicresXMLManager() {
		return sicresXMLManager;
	}

	public void setSicresXMLManager(SicresXMLManager sicresXMLManager) {
		this.sicresXMLManager = sicresXMLManager;
	}

	public ConfiguracionManager getConfiguracionManager() {
		return configuracionManager;
	}

	public void setConfiguracionManager(ConfiguracionManager configuracionManager) {
		this.configuracionManager = configuracionManager;
	}

	/**
	 * Envía el fichero de datos de control al nodo distribuido asociado.
	 *
	 * @param mensaje
	 *            Información de mensaje SICRES 3.0.
	 */
	protected void enviar(MensajeVO mensaje) {

		if (logger.isInfoEnabled()){
			logger.info("Llamada a enviarMensaje: [{}]", ToStringLoggerApiHelper.toStringLogger(mensaje));
		}

		Assert.notNull(getServiceLocator(), "'serviceLocator' must not be null");

		RespuestaWS respuesta = null;
		
		// Componer el XML del mensaje en formato SICRES 3.0
		String xml = getSicresXMLManager().createXMLMensaje(mensaje);
		logger.debug("XML mensaje: {}", xml);

		try {
			
			// Envío del fichero de datos de intercambio
			respuesta = getService(mensaje.getCodigoEntidadRegistralOrigen())
					.recepcionMensajeDatosControlDeAplicacion(xml);
			
		} catch (Exception e) {
			logger.error("Error al enviar el mensaje: " + ToStringLoggerApiHelper.toStringLogger(mensaje), e);
			throw new SIRException("error.sir.ws.wssir7", null,
					"Error en la llamada al servicio de recepción de mensaje de datos de control (WS_SIR7)");
		}

		// Comprobar la respuesta del servicio web
		if (respuesta != null) {
			WSSIRHelper.checkRespuesta(respuesta.getCodigo(), respuesta.getDescripcion());
		}

	}

	/**
	 * Obtiene el servicio a partir de la entidad registral.
	 * 
	 * @param codEntReg
	 *            Código de la Entidad Registral 
	 * @return Servicio.
	 * @throws ServiceException
	 *             si ocurre algún error al cargar el servicio.
	 * @throws MalformedURLException
	 *             si la URL del servicio web no es correcta.
	 */
	private WS_SIR7_PortType getService(String codEntReg)
			throws MalformedURLException, ServiceException {

		logger.info(
				"Obteniendo el WS_SIR7_PortType para la entidad registral [{}]",
				codEntReg);

		// URL del servicio
		String servicePortAddress = getConfiguracionManager()
				.getValorConfiguracion(
						new String[] {
								codEntReg + "." + WS_SIR7_URL_PARAM_NAME,
								WS_SIR7_URL_PARAM_NAME });
		logger.info("URL obtenida para el servicio web WS_SIR7: {}",
				servicePortAddress);

		return getServiceLocator().getWS_SIR7(new URL(servicePortAddress));
	}
}
