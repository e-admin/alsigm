package ieci.tecdoc.sgm.geolocalizacion;

import ieci.tecdoc.sgm.geolocalizacion.datatypes.ServicioURL;
import ieci.tecdoc.sgm.localizador.hadler.HandlerLocalizador;

import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.localgis.ln.ISOALocalGISHttpBindingStub;
import com.localgis.ln.ISOALocalGISLocator;

public class GeoLocalizacionFactory {
	
	private static final Logger logger = Logger.getLogger(GeoLocalizacionFactory.class);
	
	private static GeoLocalizacionFactory oInstance;
	
	private GeoLocalizacionFactory(){
	}
	
	public static GeoLocalizacionFactory getInstance(){
		if(oInstance == null){
			synchronized (GeoLocalizacionFactory.class) {
				if(oInstance == null){
					oInstance = new GeoLocalizacionFactory();
				}
			}
		}
		return oInstance;
	}
	
	public ISOALocalGISHttpBindingStub obtenerServicioGeoLocalizacion(){
		ISOALocalGISHttpBindingStub sGeoLocalizacion = null;
		try {
			
			String proxyHost = Configuracion.obtenerPropiedad(Configuracion.KEY_PROXY_HOST);
			if (StringUtils.isNotBlank(proxyHost)) {
			
				System.getProperties().put(Configuracion.KEY_PROXY_HOST, proxyHost);
				System.getProperties().put(Configuracion.KEY_PROXY_PORT, Configuracion.obtenerPropiedad(Configuracion.KEY_PROXY_PORT));
				System.getProperties().put(Configuracion.KEY_PROXY_USER, Configuracion.obtenerPropiedad(Configuracion.KEY_PROXY_USER));
				System.getProperties().put(Configuracion.KEY_PROXY_PASS, Configuracion.obtenerPropiedad(Configuracion.KEY_PROXY_PASS));

	    		// Nueva propiedad de configuración para el proxy de
	    		// Excepciones del proxy: a list of hosts that should be reached directly, bypassing the proxy.
	    		// This is a list of patterns separated by '|'. The patterns may start or end with a '*' for wildcards.
	    		// Any host matching one of these patterns will be reached through a direct connection instead of through a proxy.
	    		String nonProxyHostsProperty = Configuracion.obtenerPropiedad(Configuracion.KEY_NON_PROXY_HOSTS);
	    		if (nonProxyHostsProperty != null) {
	    			System.getProperties().put(Configuracion.KEY_NON_PROXY_HOSTS, nonProxyHostsProperty);
	    		}
			}

			String mode = Configuracion.obtenerPropiedad(Configuracion.KEY_SECURITY_MODE);
			String user = Configuracion.obtenerPropiedad(Configuracion.KEY_SECURITY_USERTOKEN_USER);
			String pass = Configuracion.obtenerPropiedad(Configuracion.KEY_SECURITY_USERTOKEN_PASS);
			String passType = Configuracion.obtenerPropiedad(Configuracion.KEY_SECURITY_USERTOKEN_PASSTYPE);
			String url = Configuracion.obtenerPropiedad(Configuracion.KEY_WS_URL);
			 ISOALocalGISLocator locator = new ISOALocalGISLocator();
			 sGeoLocalizacion = (ISOALocalGISHttpBindingStub)locator.getISOALocalGISHttpPort(new URL(url));
			 sGeoLocalizacion.setHandler(new HandlerLocalizador(mode, user, pass, passType));
		} catch (Exception e1) {
			logger.error("Error en la obtencion del servicio de LocalGIS [obtenerServicioGeoLocalizacion][Exception]", e1.fillInStackTrace());
		}
		
		return sGeoLocalizacion;
	}
	
	public ServicioURL obtenerServicioURL(String valor1, String valor2, int tipoValidacion){
		HttpClient consulta = new HttpClient();	
		String URL = Configuracion.obtenerPropiedad(Configuracion.KEY_SERVICE_IP);
		PostMethod metodo = new PostMethod(URL);
		
		String proxyHost = Configuracion.obtenerPropiedad(Configuracion.KEY_PROXY_HOST);
		String proxyPort = Configuracion.obtenerPropiedad(Configuracion.KEY_PROXY_PORT);
		if (proxyHost != null && !"".equals(proxyHost) && proxyPort != null && !"".equals(proxyPort) ) {
			consulta.getHostConfiguration().setProxy(
				proxyHost,
				new Integer(proxyPort).intValue()
			);
			consulta.getState().setProxyCredentials(
				null,
				proxyHost,
				new UsernamePasswordCredentials(
					Configuracion.obtenerPropiedad(Configuracion.KEY_PROXY_USER),
					Configuracion.obtenerPropiedad(Configuracion.KEY_PROXY_PASS)
				)
			);
		} 
		
		if (tipoValidacion == VALIDACION_POR_VIA)
			return configurarPorVia(valor1, new Integer(valor2).intValue(), consulta, metodo);
		else if (tipoValidacion == VALIDACION_POR_NUMERO_PORTAL)
			return configurarPorPortal(new Integer(valor1).intValue(), new Integer(valor2).intValue(), consulta, metodo);
		else if (tipoValidacion == VALIDACION_POR_ID_PORTAL)
			return configurarPorIdPortal(new Integer(valor1).intValue(), consulta, metodo);
		return null;
	}
	
	private ServicioURL configurarPorVia(String nombreVia, int codigoINEMunicipio, HttpClient consulta, PostMethod metodo){
		String filter = Configuracion.obtenerPropiedad(Configuracion.KEY_FILTER_VALIDATE_VIA); 
		
		int index1 = filter.indexOf(PATRON_NOMBRE_VIA);
		filter = filter.substring(0, index1)  + nombreVia + filter.substring(index1 + PATRON_NOMBRE_VIA.length());
		int index2 = filter.indexOf(PATRON_CODIGO_INE);
		filter = filter.substring(0, index2)  + codigoINEMunicipio + filter.substring(index2 + PATRON_CODIGO_INE.length());
		
		metodo.addParameter(REQUEST, Configuracion.obtenerPropiedad(Configuracion.KEY_PARAM_REQUEST));
		metodo.addParameter(VERSION, Configuracion.obtenerPropiedad(Configuracion.KEY_PARAM_VERSION));
		metodo.addParameter(SERVICE, Configuracion.obtenerPropiedad(Configuracion.KEY_PARAM_SERVICE));
		metodo.addParameter(TYPENAME, Configuracion.obtenerPropiedad(Configuracion.KEY_PARAM_VIA_TYPENAME));
		metodo.addParameter(NAMESPACE, Configuracion.obtenerPropiedad(Configuracion.KEY_PARAM_NAMESPACE));
		metodo.addParameter(FILTER, filter);
		
		return new ServicioURL(consulta, metodo);
	}
	
	private ServicioURL configurarPorPortal(int idVia, int numeroPortal, HttpClient consulta, PostMethod metodo){
		String filter = Configuracion.obtenerPropiedad(Configuracion.KEY_FILTER_VALIDATE_PORTAL); 
		
		int index1 = filter.indexOf(PATRON_ID_VIA);
		filter = filter.substring(0, index1)  + idVia + filter.substring(index1 + PATRON_ID_VIA.length());
		int index2 = filter.indexOf(PATRON_NUMERO_PORTAL);
		filter = filter.substring(0, index2)  + numeroPortal + filter.substring(index2 + PATRON_NUMERO_PORTAL.length());
		
		metodo.addParameter(REQUEST, Configuracion.obtenerPropiedad(Configuracion.KEY_PARAM_REQUEST));
		metodo.addParameter(VERSION, Configuracion.obtenerPropiedad(Configuracion.KEY_PARAM_VERSION));
		metodo.addParameter(SERVICE, Configuracion.obtenerPropiedad(Configuracion.KEY_PARAM_SERVICE));
		metodo.addParameter(TYPENAME, Configuracion.obtenerPropiedad(Configuracion.KEY_PARAM_PORTAL_TYPENAME));
		metodo.addParameter(NAMESPACE, Configuracion.obtenerPropiedad(Configuracion.KEY_PARAM_NAMESPACE));
		metodo.addParameter(FILTER, filter);
		
		return new ServicioURL(consulta, metodo);
	}
	
	private ServicioURL configurarPorIdPortal(int idPortal, HttpClient consulta, PostMethod metodo){
		String filter = Configuracion.obtenerPropiedad(Configuracion.KEY_FILTER_VALIDATE_ID_PORTAL); 
		
		int index1 = filter.indexOf(PATRON_ID_PORTAL);
		filter = filter.substring(0, index1)  + idPortal + filter.substring(index1 + PATRON_ID_PORTAL.length());
				
		metodo.addParameter(REQUEST, Configuracion.obtenerPropiedad(Configuracion.KEY_PARAM_REQUEST));
		metodo.addParameter(VERSION, Configuracion.obtenerPropiedad(Configuracion.KEY_PARAM_VERSION));
		metodo.addParameter(SERVICE, Configuracion.obtenerPropiedad(Configuracion.KEY_PARAM_SERVICE));
		metodo.addParameter(TYPENAME, Configuracion.obtenerPropiedad(Configuracion.KEY_PARAM_PORTAL_TYPENAME));
		metodo.addParameter(NAMESPACE, Configuracion.obtenerPropiedad(Configuracion.KEY_PARAM_NAMESPACE));
		metodo.addParameter(FILTER, filter);
		
		return new ServicioURL(consulta, metodo);
	}
	
	private final String PATRON_NOMBRE_VIA = "##NOMBRE_VIA##";
	private final String PATRON_CODIGO_INE = "##CODIGO_INE##";
	private final String PATRON_ID_VIA = "##ID_VIA##";
	private final String PATRON_NUMERO_PORTAL = "##NUMERO_PORTAL##";
	private final String PATRON_ID_PORTAL = "##ID_PORTAL##";
	private final String REQUEST = "REQUEST"; 
	private final String VERSION = "version"; 
	private final String SERVICE = "SERVICE"; 
	private final String TYPENAME = "TYPENAME"; 
	private final String NAMESPACE = "NAMESPACE"; 
	private final String FILTER = "FILTER";
	
	public static final int VALIDACION_POR_VIA = 1;
	public static final int VALIDACION_POR_NUMERO_PORTAL = 2;
	public static final int VALIDACION_POR_ID_PORTAL = 3;
}
