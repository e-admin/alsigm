package ieci.tecdoc.sgm.registro.plugin;

import java.util.Properties;

import junit.framework.TestCase;

public class ConfigLoaderTest extends TestCase {
	
	ConfigLoader configLoader = null;
	
	protected void setUp() throws Exception {
		configLoader = new ConfigLoader();
	}

	public void testProperties(){
		Properties prop=this.configLoader.properties;
		assertNotNull(prop);
		
		
	}
}
