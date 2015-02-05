package ieci.tecdoc.sgm.registro.terceros.connector.impl;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.registro.terceros.connector.ServicioRegistroTelematicoTercerosConnector;
import ieci.tecdoc.sgm.registro.terceros.connector.config.ConnectorConfiguration;
import ieci.tecdoc.sgm.registro.terceros.connector.helper.AdapterTerceroHelper;
import ieci.tecdoc.sgm.registro.terceros.connector.vo.TerceroVO;
import ieci.tecdoc.sgm.registro.terceros.ws.ServicioRegistroTelematicoTercerosConnectorWebServiceServiceLocator;
import ieci.tecdoc.sgm.registro.terceros.ws.ServicioRegistroTelematicoTercerosConnectorWebService_PortType;

/**
 * Implementación del conector que llamará al servicio web definido en la configuración
 * @author iecisa
 *
 */
public class TercerosConnectorWSClientImpl implements
		ServicioRegistroTelematicoTercerosConnector {

	private final Logger logger = Logger.getLogger(TercerosConnectorWSClientImpl.class);

	/**
	 * Configuración donde estará el Endpoint (URL donde está desplegado el WS) y config de seguridad si hay
	 */
	private ConnectorConfiguration config;


	public TercerosConnectorWSClientImpl() {

	}

	/**
	 * {@inheritDoc}
	 */
	public TerceroVO buscarTercero(String identificador) {
		ieci.tecdoc.sgm.registro.terceros.ws.TerceroVO terceroWS = null;
		String endpoint = config.getWSEndpoint();
		try {
			ServicioRegistroTelematicoTercerosConnectorWebServiceServiceLocator locator = new ServicioRegistroTelematicoTercerosConnectorWebServiceServiceLocator();
			locator.setServicioRegistroTelematicoTercerosConnectorWebServiceEndpointAddress(endpoint);
			ServicioRegistroTelematicoTercerosConnectorWebService_PortType service = locator
					.getServicioRegistroTelematicoTercerosConnectorWebService();
			terceroWS = service.buscarTercero(identificador);

		} catch (Exception e) {
			logger.error("Error al obtener el tercero del sistema externo mediante WS con el siguiente endpoint: "+endpoint, e);
		}

		return AdapterTerceroHelper.getTerceroVO(terceroWS);
	}

	/**
	 * {@inheritDoc}
	 */
	public TerceroVO buscarTerceroPorEntidad(String entidad,String identificador) {
		ieci.tecdoc.sgm.registro.terceros.ws.TerceroVO terceroWS = null;
		String endpoint = config.getWSEndpoint();
		try {
			ServicioRegistroTelematicoTercerosConnectorWebServiceServiceLocator locator = new ServicioRegistroTelematicoTercerosConnectorWebServiceServiceLocator();
			locator.setServicioRegistroTelematicoTercerosConnectorWebServiceEndpointAddress(endpoint);
			ServicioRegistroTelematicoTercerosConnectorWebService_PortType service = locator
					.getServicioRegistroTelematicoTercerosConnectorWebService();
			terceroWS = service.buscarTerceroPorEntidad(entidad,identificador);

		} catch (Exception e) {
			logger.error("Error al obtener el tercero del sistema externo mediante WS con el siguiente endpoint: "+endpoint, e);
		}

		return AdapterTerceroHelper.getTerceroVO(terceroWS);
	}


	public void setConnectorConfig(ConnectorConfiguration config){
		this.config = config;
	}
}
