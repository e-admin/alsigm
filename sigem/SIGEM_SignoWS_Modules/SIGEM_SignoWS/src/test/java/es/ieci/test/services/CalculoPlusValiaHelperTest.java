package es.ieci.test.services;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import es.ieci.plusvalias.api.Adquiriente;
import es.ieci.plusvalias.api.DatosLiquidacion;
import es.ieci.plusvalias.api.Inmueble;
import es.ieci.plusvalias.api.Plusvalia;
import es.ieci.plusvalias.api.Transmitente;
import es.ieci.plusvalias.util.CalculoPlusvaliaHelper;
import es.ieci.plusvalias.util.Constants;


public class CalculoPlusValiaHelperTest extends TestCase{
	
public static final Logger logger = Logger.getLogger(CalculoPlusValiaHelperTest.class);
	
	private ApplicationContext context;
	
	private CalculoPlusvaliaHelper service;
	
	public void setUp(){
		this.context = new FileSystemXmlApplicationContext(new String[]{"src/main/webapp/WEB-INF/data-layer.xml", "src/main/webapp/WEB-INF/service-layer.xml" });
		this.service = (CalculoPlusvaliaHelper) this.context.getBean("calculoPlusvaliaHelper");
	}

	public void testProcedureIsValid(){
		String procedure = "0104";
		int pos = Arrays.binarySearch(Constants.PROCEDURES_CODE, procedure);
		logger.debug("found: " + pos);
	}
	
	public void testCalcularPlusvalia(){
		logger.debug("Calculo de plusvalias");

		int bonificacion = 0;
		double cuotaParticipacion = 0.5;
		GregorianCalendar cal = new GregorianCalendar(2010, 7, 1);
		Date fechaTransActual = cal.getTime();
		cal = new GregorianCalendar(2009, 2, 1);
		Date fechaTransAnterior = cal.getTime();
		String refCatastral = "4471127PD7047A0027";
		// Pleno dominio
		int numDerecho = 1;
		double superficieSolar = 0;
		
		DatosLiquidacion  datosLiquidacion = new DatosLiquidacion();
		datosLiquidacion.setFechaTransActual(fechaTransActual);
		
		Transmitente transmitiente = new Transmitente();
		transmitiente.setBonificacion(bonificacion);
		transmitiente.setFechaTransAnterior(fechaTransAnterior);
		
		Adquiriente adquiriente = new Adquiriente();
		adquiriente.setEdad(30);
		adquiriente.setCuotaParticipacion(cuotaParticipacion);
		adquiriente.setNumDerecho(numDerecho);

		Inmueble inmueble = new Inmueble();
		inmueble.setRefCatastral(refCatastral);

		
		transmitiente.setPorcentajeTransmitido(superficieSolar);
		
		// ResultadoUnitario plusvalia = service.calcularPlusvalia(inmueble, adquiriente, transmitiente, datosLiquidacion);
		
		if (logger.isDebugEnabled()){
			// logger.debug(plusvalia);
		}
		
	}
	
	public void testCalcularPlusvaliaTotal(){
		if (logger.isDebugEnabled())
		{
			logger.debug("Calculo de plusvalias");
		}

		int bonificacion = 0;
		double cuotaParticipacion = 0.5;
		GregorianCalendar cal = new GregorianCalendar(2010, 7, 1);
		Date fechaTransActual = cal.getTime();
		cal = new GregorianCalendar(2009, 2, 1);
		Date fechaTransAnterior = cal.getTime();
		String refCatastral = "4471127PD7047A0027";
		// Pleno dominio
		int numDerecho = 1;
		double superficieSolar = 0;
		String codigoActoJuridico = "0104";
		
		DatosLiquidacion  datosLiquidacion = new DatosLiquidacion();
		datosLiquidacion.setFechaTransActual(fechaTransActual);
		
		Transmitente transmitiente = new Transmitente();
		transmitiente.setBonificacion(bonificacion);
		transmitiente.setFechaTransAnterior(fechaTransAnterior);
		
		Transmitente[] transmitientes = {transmitiente};
		
		Adquiriente adquiriente = new Adquiriente();
		adquiriente.setEdad(30);
		adquiriente.setCuotaParticipacion(cuotaParticipacion);
		adquiriente.setNumDerecho(numDerecho);
		
		Adquiriente[] adquirientes = {adquiriente};
		
		transmitiente.setPorcentajeTransmitido(superficieSolar);
		
		Plusvalia plusvalia = null;
		try {
			//plusvalia = service.calcular(codigoActoJuridico, refCatastral, adquirientes, transmitientes, datosLiquidacion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals( "importe incorrecto",  34.951268133639005, plusvalia.getResultado().getTotalLiquidacion(), 0);
		
		if (logger.isDebugEnabled()){
			logger.debug(plusvalia);
		}
		
	}
}
