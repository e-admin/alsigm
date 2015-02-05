package es.ieci.test.services;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import es.ieci.plusvalias.api.Adquiriente;
import es.ieci.plusvalias.api.Inmueble;
import es.ieci.plusvalias.api.Plusvalia;
import es.ieci.plusvalias.service.ReportService;


public class ReportHelperTest extends TestCase{
	public static final Logger logger = Logger.getLogger(ReportHelperTest.class);

	private ApplicationContext context;
	private ReportService service;

	public void setUp(){
		this.context = new FileSystemXmlApplicationContext(new String[]{"src/main/webapp/WEB-INF/data-layer.xml", "src/main/webapp/WEB-INF/service-layer.xml" });
		this.service = (ReportService) this.context.getBean("reportService");
	}

	public void testGenerarReport() throws Exception{
		logger.debug("Generando Report");

		service.setReportDir("src/main/webapp/WEB-INF/reports/");

		Adquiriente adquiriente = new Adquiriente();
		adquiriente.setNombreCompleto("Solis Calleja, Antonio");
		adquiriente.setNif("30969974z");
		adquiriente.setCuotaParticipacion(75);

		Inmueble inmueble = new Inmueble();
		inmueble.setRefCatastral("4471127PD7047A0027");
		inmueble.setNumero("26");
		inmueble.setNombrevia("Montero");

		Plusvalia plusvalia = new Plusvalia();
		plusvalia.setInmueble(inmueble);
		plusvalia.getResultado().setTotalLiquidacion(476.34);

		double importeLiquidacion = 125.93;

		String tipoDocumento = "calculo";
		service.generarReport(tipoDocumento, adquiriente, plusvalia , importeLiquidacion, "3409374958345");

		tipoDocumento = "pago";
		service.generarReport(tipoDocumento, adquiriente, plusvalia , importeLiquidacion, "34059834053");
	}
}