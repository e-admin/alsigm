package ieci.tecdoc.sgm.catastro;

import ieci.tecdoc.sgm.localizador.hadler.HandlerLocalizador;

import java.net.URL;

import org.apache.commons.lang.StringUtils;

import com.localgis.ln.ISOALocalGISHttpBindingStub;
import com.localgis.ln.ISOALocalGISLocator;

public class CatastroFactory {
	
	private static CatastroFactory oInstance;
	
	private CatastroFactory(){
	}
	
	public static CatastroFactory getInstance(){
		if(oInstance == null){
			synchronized (CatastroFactory.class) {
				if(oInstance == null){
					oInstance = new CatastroFactory();
				}
			}
		}
		return oInstance;
	}
	
	public ISOALocalGISHttpBindingStub obtenerServicioCatastro(){
		ISOALocalGISHttpBindingStub sCatastro = null;
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
			 sCatastro = (ISOALocalGISHttpBindingStub)locator.getISOALocalGISHttpPort(new URL(url));
			 sCatastro.setHandler(new HandlerLocalizador(mode, user, pass, passType));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return sCatastro;
	}
	
}
