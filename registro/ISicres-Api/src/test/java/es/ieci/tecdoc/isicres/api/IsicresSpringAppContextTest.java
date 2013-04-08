package es.ieci.tecdoc.isicres.api;

import junit.framework.TestCase;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

public class IsicresSpringAppContextTest extends TestCase {
	
	
	
	
	public void doitTest(){
		ApplicationContext context = new ClassPathXmlApplicationContext("beans/applicationContext.xml");
		ApplicationContext result = IsicresSpringAppContext.getApplicationContext();
		assertNotNull(result);
		assertEquals(result, context);
	}
	
	

}
