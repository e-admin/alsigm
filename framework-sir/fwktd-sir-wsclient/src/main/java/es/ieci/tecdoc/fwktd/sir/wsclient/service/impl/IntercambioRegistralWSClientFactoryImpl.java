package es.ieci.tecdoc.fwktd.sir.wsclient.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.fwktd.sir.ws.service.IntercambioRegistralWS;

/**
 * Factoria para la generación de clientes web con soporte multientidad
 * 
 * LAs urls del servicio web al que apuntará el cliente generado serán del tipo:
 * 
 * Sin multientidad :"* /serviceName"
 * Con multientidad "* /idEntidad/serviceName"  
 * 
 * @author IECISA
 *
 */
public class IntercambioRegistralWSClientFactoryImpl implements IntercambioRegistralWSClientFactory {
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(IntercambioRegistralWSClientFactoryImpl.class);

	
	/**
	 * direccion del endpoint del servicio web sin multientidad
	 */
	protected String address;

	
	
	public String getAddress() {

		
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.fwktd.sir.wsclient.service.impl.IntercambioRegistralWSClientFactory#getIntercambioRegistralWS()
	 */
	public IntercambioRegistralWS getIntercambioRegistralWS() {

		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IntercambioRegistralWS.class);
		
		//obtenemos la dirección del endpoint
		factory.setAddress(getMultiEntityAddress());
		
		IntercambioRegistralWS intercambioRegistralWSClient = (IntercambioRegistralWS) factory.create();
		
		//timeout de 10min?
		long timeout = 600000 ;
		//configuracion del timeout del cliente del servicio web 0==espera infinita
		Client client = ClientProxy.getClient(intercambioRegistralWSClient);
		if (client != null) {
			HTTPConduit conduit = (HTTPConduit) client.getConduit();
			HTTPClientPolicy policy = new HTTPClientPolicy();

			policy.setConnectionTimeout(timeout);
			policy.setReceiveTimeout(timeout);
			conduit.setClient(policy);
		}
		
		return intercambioRegistralWSClient;
	}
	
	/**
	 * Método que obtiene url del servicio web, tiene en cuenta multientidad
	 * LAs urls del servicio web al que apuntará el cliente generado serán del tipo:
	 * 
	 * Sin multientidad :"* /serviceName"
	 * Con multientidad "* /idEntidad/serviceName"  
	 * @return
	 */
	public String getMultiEntityAddress() {
		String result=null;
		String address=getAddress();
		
		String entidad=MultiEntityContextHolder.getEntity();
		
		if (StringUtils.isEmpty(entidad)){
			result=address;
		}else{
			
			if (logger.isDebugEnabled()){
				logger.debug("Obteniendo direción para servicio web de la entidad:"+entidad);
			}
			StringBuffer multiEntityAddress= new StringBuffer();
			address=StringUtils.removeEndIgnoreCase(address, "/");
			
			String service=StringUtils.substringAfterLast(address,"/");
			
			multiEntityAddress.append((StringUtils.substringBeforeLast(address,"/")));
			multiEntityAddress.append("/");
			multiEntityAddress.append(entidad);
			multiEntityAddress.append("/");
			multiEntityAddress.append(service);
						
			result=multiEntityAddress.toString();
			
		}
		
		if (logger.isDebugEnabled()){
			logger.debug("Obtenida direción para servicio web de la entidad "+entidad+":"+result);
		}
		return result;
		
	}
}
