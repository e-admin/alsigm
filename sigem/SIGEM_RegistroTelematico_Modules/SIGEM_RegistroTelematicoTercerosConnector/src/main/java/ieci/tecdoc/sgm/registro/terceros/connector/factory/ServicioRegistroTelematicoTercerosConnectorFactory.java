package ieci.tecdoc.sgm.registro.terceros.connector.factory;

import ieci.tecdoc.sgm.registro.terceros.connector.ServicioRegistroTelematicoTercerosConnector;
import ieci.tecdoc.sgm.registro.terceros.connector.config.ConnectorConfiguration;
import ieci.tecdoc.sgm.registro.terceros.connector.impl.TercerosConnectorWSClientImpl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Factoria para instanciar la implementación del conector de terceros configurado
 * @author iecisa
 *
 */
public class ServicioRegistroTelematicoTercerosConnectorFactory {

	private static Logger logger = Logger.getLogger(ServicioRegistroTelematicoTercerosConnectorFactory.class);

	/**
	 * Mapa donde guardamos los conectores instanciados
	 */
	private static Map<String, ServicioRegistroTelematicoTercerosConnector> connectors = new HashMap<String, ServicioRegistroTelematicoTercerosConnector>();

	public static ServicioRegistroTelematicoTercerosConnector getServicioRegistroTelematicoTercerosConnector(ConnectorConfiguration config){

		ServicioRegistroTelematicoTercerosConnector servicio = null;
		try{
			if(connectors.containsKey(config.getConnector()))
			{
				servicio = connectors.get(config.getConnector());
			}
			else{
				String tercerosConnectorImpl = config.getConnector();
				if(tercerosConnectorImpl!=null)
				{
					servicio = (ServicioRegistroTelematicoTercerosConnector)Class.forName(tercerosConnectorImpl).newInstance();

					//En caso de que se configure por WS, la clase instanciada será siempre la misma, configuramos seguridad
					if(servicio instanceof TercerosConnectorWSClientImpl)
					{
						//Setear config de seguridad
						((TercerosConnectorWSClientImpl)servicio).setConnectorConfig(config);
					}

					connectors.put(config.getConnector(), servicio);
				}
			}
		}
		catch (Exception e) {
			logger.error("Error obteniendo la implementación del conector con el sistema de Terceros con clase: "+config.getConnector(),e);

		}

		return servicio;
	}

}
