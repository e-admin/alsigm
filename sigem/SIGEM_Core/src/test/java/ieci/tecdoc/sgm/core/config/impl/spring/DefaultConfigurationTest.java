package ieci.tecdoc.sgm.core.config.impl.spring;

import java.util.Map;


import junit.framework.TestCase;

public class DefaultConfigurationTest extends TestCase {
	
	
	protected Config configuration;
	
	protected void setUp() throws Exception {
		
		super.setUp();
		configuration= DefaultConfiguration.getConfiguration();
		
	}
	
	protected Object getBean(String beanName){
		Object result=null;
		try {
			result=configuration.getBean(beanName);
		} catch (Exception e) {
			throw new RuntimeException("Error cargando bean "+beanName+":"+e.getLocalizedMessage());	
		}
		
		return result;
	}
	
	public void testGetBeanSIGEM_Ports(){
			
		Object o = null;
		try {
			o= configuration.getBean("SIGEM_PORTS");
		} catch (Exception e) {
			throw new RuntimeException("Error cargando bean SIGEM_PORTS:"+e.getMessage());			
		}
		assertNotNull(o);
		Map map=(Map) o;
		String valor=null;
		valor =(String) map.get("HTTP_PORT");
		assertNotNull(valor);
		assertEquals("8080", valor);
		
		valor =(String)map.get("HTTPS_PORT");
		assertNotNull(valor);
		assertEquals("4443", valor);
		
		valor =(String)map.get("CERT_PORT");
		assertNotNull(valor);
		assertEquals("8443", valor);
		
		valor =(String)map.get("HTTP_FRONTEND_PORT");
		assertNotNull(valor);
		assertEquals("80", valor);
		
		valor =(String)map.get("HTTPS_FRONTEND_PORT");
		assertNotNull(valor);
		assertEquals("443", valor);
		
		valor =(String)map.get("HTTPS_FRONTEND_AUTHCLIENT_PORT");
		assertNotNull(valor);
		assertEquals("843", valor);
		
			
	}
//	
//	public void testGetEndPoints(){
//		String locator = "SIGEM_ServicioAdministracionSesionesBackOffice.LOCATOR";
//		Object obj=getBean(locator);
//		assertNotNull(obj);
//		
//	}
	
	

	

}
