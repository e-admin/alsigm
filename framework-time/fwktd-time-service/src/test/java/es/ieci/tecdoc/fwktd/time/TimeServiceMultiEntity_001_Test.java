package es.ieci.tecdoc.fwktd.time;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.fwktd.time.exception.TimeException;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/beans/fwktd-time-applicationContext.xml"})
public class TimeServiceMultiEntity_001_Test extends TestCase {

	@Autowired
	FactoryTimeService fwktd_time_factoryTimeService;


	private final String ENTITY_001 = "001";
	private final String DATE_FORMAT = "EEEE dd/MM/yyyy hh:mm:ss";

	protected InitialContext initialContext;

	public TimeServiceMultiEntity_001_Test() {
		super();
		MultiEntityContextHolder.setEntity(ENTITY_001);
		configureJndi();
	}

	protected void configureJndi() {
	try {
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.xbean.spring.jndi.SpringInitialContextFactory");
		initialContext=new InitialContext();
		} catch (NamingException ex){
			Assert.fail(ex.getMessage());
			ex.printStackTrace();
		}
	}
	@Test
	public void testCurrentDate_1(){
		TimeService timeService = fwktd_time_factoryTimeService.getTimeService();
		try {
			Date date = timeService.getCurrentDate();
			Assert.assertNotNull(date);
			System.out.println("Fecha actual: "+date);
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			System.out.println("Fecha actual: "+dateFormat.format(date));

		} catch (TimeException e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

}
