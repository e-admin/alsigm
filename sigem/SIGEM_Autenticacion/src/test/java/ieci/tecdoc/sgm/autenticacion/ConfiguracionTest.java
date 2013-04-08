package ieci.tecdoc.sgm.autenticacion;
import java.util.Properties;

import junit.framework.TestCase;


public class ConfiguracionTest extends TestCase {

	
	public void testCargaProperties(){
		  Properties propiedades = Configuracion.getConfigFileAutenticacion();
		  assertNotNull(propiedades);
		 
	}
}
