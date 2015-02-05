import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ConfigSpringTestCase extends TestCase{
	protected static String  SPRING_BEANS_FILE ="notificacion-config-beans.xml";
	
	public void testwtIeciBasePlaceholderConfigurer() { 
		ApplicationContext context =new ClassPathXmlApplicationContext(SPRING_BEANS_FILE);
		Map configMap=(Map)context.getBean("NOTIFICACIONES.SISNOT_BD_CONFIG");
		assertEquals(configMap.get("SISNOT_BD_DRIVER"),"org.postgresql.Driver"); 
    } 
}
 