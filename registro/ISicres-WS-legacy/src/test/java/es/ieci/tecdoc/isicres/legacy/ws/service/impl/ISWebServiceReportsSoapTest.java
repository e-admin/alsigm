package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import javax.xml.ws.soap.SOAPFaultException;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoPlantillaInformeEnum;
import es.ieci.tecdoc.isicres.ws.legacy.service.reports.ArrayOfWSReport;
import es.ieci.tecdoc.isicres.ws.legacy.service.reports.ISWebServiceReportsSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.reports.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.reports.UsernameTokenClass;
import es.ieci.tecdoc.isicres.ws.legacy.service.reports.WSReport;

/**
 * Pruebas de integración para verificar el correcto funcionamiento del WS con
 * interfaz <code>ISWebServiceReportsSoap</code>.
 * 
 * @see ISWebServiceReportsSoap
 * 
 * @author IECISA
 * 
 */
public class ISWebServiceReportsSoapTest extends
		AbstractDependencyInjectionSpringContextTests {

	private static final String CLIENT_WEB_SERVICE_REGISTERS = "clientWebServiceReports";

	protected String[] getConfigLocations() {
		return new String[] { "beans/appContextTest.xml" };
	}

	/**
	 * 
	 */
	public void testWSCreateInputRegisterCertificate() {
		ISWebServiceReportsSoap client = (ISWebServiceReportsSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;
		int registerIdentification = 185;
		int reportIdentification = 4;

		byte[] wsCreateInputRegisterCertificate = client
				.wsCreateInputRegisterCertificate(bookIdentification,
						registerIdentification, reportIdentification, security);

		assertNotNull(wsCreateInputRegisterCertificate);
	}

	/**
	 * 
	 */
	public void testWSCreateInputRegisterCertificateWrongBookType() {
		ISWebServiceReportsSoap client = (ISWebServiceReportsSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 2;
		int registerIdentification = 185;
		int reportIdentification = 4;

		try {
			client.wsCreateInputRegisterCertificate(bookIdentification,
					registerIdentification, reportIdentification, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();

	}

	/**
	 * 
	 */
	public void testWSCreateOutputRegisterCertificate() {
		ISWebServiceReportsSoap client = (ISWebServiceReportsSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 2;
		int registerIdentification = 10;
		int reportIdentification = 8;

		byte[] wsCreateOutputRegisterCertificate = client
				.wsCreateOutputRegisterCertificate(bookIdentification,
						registerIdentification, reportIdentification, security);

		assertNotNull(wsCreateOutputRegisterCertificate);
	}

	/**
	 * Verifica la obtención de los informes de tipo certificado para el usuario
	 * <i>sigem</i> en los libros con identificadores <i>1</i> y <i>2</i>
	 */
	public void testWSLoadSigemCertificates() {
		ISWebServiceReportsSoap client = (ISWebServiceReportsSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;
		ArrayOfWSReport wsLoadCertificates = client.wsLoadCertificates(
				bookIdentification, security);

		assertNotNull(wsLoadCertificates);
		assertEquals(1, wsLoadCertificates.getWSReport().size());
		WSReport report = wsLoadCertificates.getWSReport().get(0);
		assertEquals(4, report.getId());
		assertEquals("120.zip", report.getName());
		assertEquals(TipoPlantillaInformeEnum.CERTIFICADO_REGISTRO.getValue(),
				report.getType());

		bookIdentification = 2;
		wsLoadCertificates = client.wsLoadCertificates(bookIdentification,
				security);
		assertNotNull(wsLoadCertificates);
		assertEquals(1, wsLoadCertificates.getWSReport().size());
		report = wsLoadCertificates.getWSReport().get(0);
		assertEquals(8, report.getId());
		assertEquals("220.zip", report.getName());
		assertEquals(TipoPlantillaInformeEnum.CERTIFICADO_REGISTRO.getValue(),
				report.getType());
	}

	/**
	 * Verifica la obtención de los informes de tipo certificado para el usuario
	 * <i>operador1</i> en los libros con identificadores <i>1</i> y <i>2</i>
	 */
	public void testWSLoadOperador1Certificates() {
		ISWebServiceReportsSoap client = (ISWebServiceReportsSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;
		ArrayOfWSReport wsLoadCertificates = client.wsLoadCertificates(
				bookIdentification, security);

		assertNotNull(wsLoadCertificates);
		assertEquals(1, wsLoadCertificates.getWSReport().size());
		WSReport report = wsLoadCertificates.getWSReport().get(0);
		assertEquals(4, report.getId());
		assertEquals("120.zip", report.getName());
		assertEquals(TipoPlantillaInformeEnum.CERTIFICADO_REGISTRO.getValue(),
				report.getType());

		bookIdentification = 2;
		wsLoadCertificates = client.wsLoadCertificates(bookIdentification,
				security);
		assertNotNull(wsLoadCertificates);
		assertEquals(1, wsLoadCertificates.getWSReport().size());
		report = wsLoadCertificates.getWSReport().get(0);
		assertEquals(8, report.getId());
		assertEquals("220.zip", report.getName());
		assertEquals(TipoPlantillaInformeEnum.CERTIFICADO_REGISTRO.getValue(),
				report.getType());
	}
}
