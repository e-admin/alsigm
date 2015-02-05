package es.ieci.tecdoc.fwktd.time;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.ieci.tecdoc.fwktd.time.exception.TimeException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/beans/fwktd-time-applicationContext.xml"})
public class TimeServiceTest extends TestCase {

	@Autowired
	FactoryTimeService fwktd_time_factoryTimeService;

	@Autowired
	TimeService fwktd_time_dateRetrieverNTP;
	@Autowired
	TimeService fwktd_time_dateRetrieverSystem;
	@Autowired
	TimeService fwktd_time_dateRetrieverPostgres;

	@Autowired
	TimeService fwktd_time_dateRetrieverOracle;

	@Autowired
	TimeService fwktd_time_dateRetrieverSqlServer;

	@Autowired
	TimeService fwktd_time_dateRetrieverDB2;

	private final String DATE_FORMAT = "EEEE dd/MM/yyyy hh:mm:ss";

	protected InitialContext initialContext;

	public TimeServiceTest() {
		super();
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
	public void testCurrentDateFactory(){
		TimeService timeService = fwktd_time_factoryTimeService.getTimeService();
		try {
			Date date = timeService.getCurrentDate();
			printResult(date);
		} catch (TimeException e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void testCurrentDateNTP(){
		try {
			Date date = fwktd_time_dateRetrieverNTP.getCurrentDate();
			printResult(date);
		} catch (TimeException e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void testCurrentDateSystem(){
		try {
			Date date = fwktd_time_dateRetrieverSystem.getCurrentDate();
			printResult(date);
		} catch (TimeException e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void testCurrentDatePostgres(){
		try {
			Date date = fwktd_time_dateRetrieverPostgres.getCurrentDate();
			printResult(date);
		} catch (TimeException e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void testCurrentDateOracle(){
		try {
			Date date = fwktd_time_dateRetrieverOracle.getCurrentDate();
			printResult(date);
		} catch (TimeException e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void testCurrentDateSqlServer(){
		try {
			Date date = fwktd_time_dateRetrieverSqlServer.getCurrentDate();
			printResult(date);
		} catch (TimeException e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void testCurrentDateDB2(){
		try {
			Date date = fwktd_time_dateRetrieverDB2.getCurrentDate();
			printResult(date);
		} catch (TimeException e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

	private void printResult(Date date){
		Assert.assertNotNull(date);
		System.out.println("Fecha actual: "+date);
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		System.out.println("Fecha actual: "+dateFormat.format(date));

	}


}
