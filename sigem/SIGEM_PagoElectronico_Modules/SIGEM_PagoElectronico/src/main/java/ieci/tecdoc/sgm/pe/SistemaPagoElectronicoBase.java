package ieci.tecdoc.sgm.pe;

import ieci.tecdoc.sgm.pe.database.LiquidacionDatos;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public abstract class SistemaPagoElectronicoBase implements SistemaPagoElectronico {

	private static final String HTTP_PROXY_HOST="http.proxyHost";
	private static final String HTTPS_PROXY_HOST="https.proxyHost";
	private static final String HTTP_PROXY_PORT="https.proxyPort";
	private static final String HTTPS_PROXY_PORT="https.proxyPort";
	private static final String HTTP_PROXY_USER="http.proxyUser";
	private static final String HTTPS_PROXY_USER="https.proxyUser";
	private static final String HTTP_PROXY_PASS="http.proxyPassword";
	private static final String HTTPS_PROXY_PASS="https.proxyPassword";
	private static final String HTTP_NON_PROXY_HOSTS="http.nonProxyHosts";
	private static final String HTTPS_NON_PROXY_HOSTS="https.nonProxyHosts";
	
	private final String JAVAX_NET_DEBUG="javax.net.debug";
	private final String JAVAX_NET_SSL_TRUSTSTORE="javax.net.ssl.trustStore";
	private final String JAVAX_NET_SSL_TRUSTSTOREPASSWORD="javax.net.ssl.trustStorePassword";
	
	/**
	 * Método que aplica la configuración que llega como parámetro.
	 */
	public void configure(Map phmConfig) throws PagoElectronicoExcepcion{
		// datos del proxy 
		String valor = ConfiguracionComun.obtenerPropiedad(ConfiguracionComun.KEY_PROXY_HOST);
		if(StringUtils.isNotEmpty(valor)){
	 		System.setProperty(HTTP_PROXY_HOST,valor);
	 		System.setProperty(HTTPS_PROXY_HOST,valor);
		}
		valor = ConfiguracionComun.obtenerPropiedad(ConfiguracionComun.KEY_PROXY_PORT);
		if(StringUtils.isNotEmpty(valor)){
	 		System.setProperty(HTTP_PROXY_PORT,valor);
	 		System.setProperty(HTTPS_PROXY_PORT,valor);
		}
		valor = ConfiguracionComun.obtenerPropiedad(ConfiguracionComun.KEY_PROXY_USER);
		if(StringUtils.isNotEmpty(valor)){
	 		System.setProperty(HTTP_PROXY_USER,valor);
	 		System.setProperty(HTTPS_PROXY_USER,valor);
		}
		final String user=valor;
		valor = ConfiguracionComun.obtenerPropiedad(ConfiguracionComun.KEY_PROXY_PASS);
		if(StringUtils.isNotEmpty(valor)){
	 		System.setProperty(HTTP_PROXY_PASS,valor);
	 		System.setProperty(HTTPS_PROXY_PASS,valor);
		}
		final String passwd=valor;
		if(StringUtils.isNotEmpty(user) && StringUtils.isNotEmpty(passwd)){
			Authenticator.setDefault(new Authenticator(){
				protected PasswordAuthentication getPasswordAuthentication() {  
			        return new PasswordAuthentication(user, passwd.toCharArray());  
			    }
			});
		}
		valor = ConfiguracionComun.obtenerPropiedad(ConfiguracionComun.KEY_NON_PROXY_HOSTS);
		if(StringUtils.isNotEmpty(valor)){
	 		System.setProperty(HTTP_NON_PROXY_HOSTS,valor);
	 		System.setProperty(HTTPS_NON_PROXY_HOSTS,valor);
		}
		
		// datos de acceso a redes
		valor = ConfiguracionComun.obtenerPropiedad(ConfiguracionComun.KEY_JAVA_NET_DEBUG);
		if(StringUtils.isNotEmpty(valor)){
	 		System.getProperties().put(JAVAX_NET_DEBUG,valor);
		}	
		
		// datos de seguridad
		valor = ConfiguracionComun.obtenerPropiedad(ConfiguracionComun.KEY_JAVA_NET_SSL_TRUSTSTORE);
		if(StringUtils.isNotEmpty(valor)){
	 		System.getProperties().put(JAVAX_NET_SSL_TRUSTSTORE,valor);
		}	
		valor = ConfiguracionComun.obtenerPropiedad(ConfiguracionComun.KEY_JAVA_NET_SSL_TRUSTSTORE_PASS);
		if(StringUtils.isNotEmpty(valor)){
	 		System.getProperties().put(JAVAX_NET_SSL_TRUSTSTOREPASSWORD,valor);
		}
	}

	//La implementacion por defecto recupera la información de la BD
	public Cuaderno60Modalidad1_2[] consultaCuaderno60Modalidad1_2(CriterioBusquedaPago oCriterio) throws PagoElectronicoExcepcion{
		ArrayList listaLiquidaciones=buscarLiquidaciones(oCriterio);
		Cuaderno60Modalidad1_2[] cuadernos=new Cuaderno60Modalidad1_2[listaLiquidaciones.size()];
		for(int i=0;i<listaLiquidaciones.size();i++){
			LiquidacionDatos liquidacion=(LiquidacionDatos)listaLiquidaciones.get(i);
			
			Cuaderno60Modalidad1_2 cuaderno=new Cuaderno60Modalidad1_2();
			cuaderno.set(liquidacion);
			cuadernos[i]=cuaderno;
		}
		return cuadernos;
	}
	
	//La implementacion por defecto recupera la información de la BD
	public Cuaderno60Modalidad3[] consultaCuaderno60Modalidad3(CriterioBusquedaPago oCriterio) throws PagoElectronicoExcepcion{
		ArrayList listaLiquidaciones=buscarLiquidaciones(oCriterio);
		Cuaderno60Modalidad3[] cuadernos=new Cuaderno60Modalidad3[listaLiquidaciones.size()];
		for(int i=0;i<listaLiquidaciones.size();i++){
			LiquidacionDatos liquidacion=(LiquidacionDatos)listaLiquidaciones.get(i);
			
			Cuaderno60Modalidad3 cuaderno=new Cuaderno60Modalidad3();
			cuaderno.set(liquidacion);
			cuadernos[i]=cuaderno;
		}
		return cuadernos;
	}
	
	public Cuaderno57[] consultaCuaderno57(CriterioBusquedaPago oCriterio) throws PagoElectronicoExcepcion{
		ArrayList listaLiquidaciones=buscarLiquidaciones(oCriterio);
		Cuaderno57[] cuadernos=new Cuaderno57[listaLiquidaciones.size()];
		for(int i=0;i<listaLiquidaciones.size();i++){
			LiquidacionDatos liquidacion=(LiquidacionDatos)listaLiquidaciones.get(i);
			
			Cuaderno57 cuaderno=new Cuaderno57();
			cuaderno.set(liquidacion);
			cuadernos[i]=cuaderno;
		}
		return cuadernos;
	}
	
	private ArrayList buscarLiquidaciones(CriterioBusquedaPago oCriterio) throws PagoElectronicoExcepcion{
		LiquidacionDatos liquidacionDatos=new LiquidacionDatos();
		ArrayList listaADevolver=null;
		try{
			listaADevolver=liquidacionDatos.buscarLiquidaciones(oCriterio, oCriterio.getEntidad());
		}catch(Exception e){
			Logger.getLogger(this.getClass()).debug(e);
			listaADevolver=new ArrayList();
		}
		return listaADevolver;
	}
	
	public abstract Cuaderno60Modalidad1_2 pagoCuaderno60Modalidad1_2(Cuaderno60Modalidad1_2 poCuaderno) throws PagoElectronicoExcepcion;
	public abstract Cuaderno60Modalidad3 pagoCuaderno60Modalidad3(Cuaderno60Modalidad3 poCuaderno) throws PagoElectronicoExcepcion ;
	public abstract Cuaderno57 pagoCuaderno57(Cuaderno57 poCuaderno) throws PagoElectronicoExcepcion ;

}
