package es.ieci.tecdoc.fwktd.sir.api.manager.impl.ws;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sir.api.manager.AnexoManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.ConfiguracionManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.impl.FicheroIntercambioManagerImpl;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir6b.RespuestaWS;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir6b.WS_SIR6_BService;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir6b.WS_SIR6_B_PortType;
import es.ieci.tecdoc.fwktd.sir.core.exception.SIRException;
import es.ieci.tecdoc.fwktd.sir.core.util.ToStringLoggerHelper;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;

/**
 * Implementación del manager de envío de ficheros de datos de intercambio en
 * formato SICRES 3.0 generado por la aplicación de registro. Esta
 * implementación utiliza el servicio web WS_SIR6_B del CIR.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class FicheroIntercambioManagerWSSIR6BImpl extends
		FicheroIntercambioManagerImpl {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(FicheroIntercambioManagerWSSIR6BImpl.class);

	private static final String WS_SIR6_B_URL_PARAM_NAME = "WS_SIR6_B.url"; 

	/**
	 * Localizador del servicio WS_SIR6_B
	 */
	private WS_SIR6_BService serviceLocator = null;

	/**
	 * Gestor de anexos.
	 */
	private AnexoManager anexoManager = null;

	/**
	 * Gestor de configuración.
	 */
	private ConfiguracionManager configuracionManager = null;

	/**
	 * Constructor.
	 */
	public FicheroIntercambioManagerWSSIR6BImpl() {
		super();
	}

	public AnexoManager getAnexoManager() {
		return anexoManager;
	}

	public void setAnexoManager(AnexoManager anexoManager) {
		this.anexoManager = anexoManager;
	}

	public ConfiguracionManager getConfiguracionManager() {
		return configuracionManager;
	}

	public void setConfiguracionManager(ConfiguracionManager configuracionManager) {
		this.configuracionManager = configuracionManager;
	}

	public WS_SIR6_BService getServiceLocator() {
		return serviceLocator;
	}

	public void setServiceLocator(WS_SIR6_BService serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.impl.FicheroIntercambioManagerImpl#enviar(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO)
	 */
	protected void enviar(AsientoRegistralVO asiento) {

		if (logger.isInfoEnabled()){
			logger.info("Llamada a enviarFicheroIntercambio: [{}]", ToStringLoggerHelper.toStringLogger(asiento));
		}

		Assert.notNull(getServiceLocator(), "'serviceLocator' must not be null");

		RespuestaWS respuesta = null;
		
		// Componer el XML del fichero de intercambio en formato SICRES 3.0
		// con los documentos incluidos en el XML (attached)
		String xml = getSicresXMLManager().createXMLFicheroIntercambio(asiento, true);
		logger.debug("XML fichero de intercambio: {}", xml);

		try {
			
			// Envío del fichero de datos de intercambio con los documentos
			// incluidos en el XML (attached)
			respuesta = getService(asiento.getCodigoEntidadRegistralOrigen())
					.recepcionFicheroDeAplicacion(xml);
	
		} catch (Exception e) {
			logger.error("Error al enviar el fichero de intercambio: " + ToStringLoggerHelper.toStringLogger(asiento), e);
			throw new SIRException("error.sir.ws.wssir6b", null,
					"Error en la llamada al servicio de recepción de ficheros de datos de intercambio (WS_SIR6_B)");
		}

		// Comprobar la respuesta del servicio web
		if (respuesta != null) {
			WSSIRHelper.checkRespuesta(respuesta.getCodigo(), respuesta.getDescripcion());
		}
	}

	/**
	 * Obtiene el servicio a partir de la entidad registral que gestiona el
	 * asiento registral.
	 * 
	 * @param codEntReg
	 *            Código de la Entidad Registral.
	 * @return Servicio.
	 * @throws ServiceException
	 *             si ocurre algún error al cargar el servicio.
	 * @throws MalformedURLException
	 *             si la URL del servicio web no es correcta.
	 */
	private WS_SIR6_B_PortType getService(String codEntReg)
			throws MalformedURLException, ServiceException {

		logger.info(
				"Obteniendo el WS_SIR6_B_PortType para la entidad registral [{}]",
				codEntReg);

		// URL del servicio
		String servicePortAddress = getConfiguracionManager()
				.getValorConfiguracion(
						new String[] {
								codEntReg + "." + WS_SIR6_B_URL_PARAM_NAME,
								WS_SIR6_B_URL_PARAM_NAME });
		logger.info("URL obtenida para el servicio web WS_SIR6_B: {}",
				servicePortAddress);

		return getServiceLocator().getWS_SIR6_B(new URL(servicePortAddress));
	}
}
