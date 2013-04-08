package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.soap.SOAPFaultException;

import junit.framework.Assert;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.ieci.tecdoc.common.isicres.AxSf;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.XMLGregorianCalendarHelper;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.ISWebServiceDistributionsSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSAddField;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamDocument;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamField;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamPerson;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ISWebServiceRegistersSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.UsernameTokenClass;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSAddField;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSDocumentsResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSInputRegister;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSInputRegistersResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSLoadInputRegistersEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSLoadInputRegistersExResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSOutputRegister;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSOutputRegistersResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamDocument;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamField;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamInputRegisterEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamOutputRegisterEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamPerson;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSPerson;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSRegister;

/**
 * Pruebas de integración para verificar el correcto funcionamiento del WS con
 * interfaz <code>ISWebServiceRegistersSoap</code>.
 * 
 * @see ISWebServiceRegistersSoap
 * 
 * @author IECISA
 * 
 */
public class ISWebServiceRegistersSoapTest extends
		AbstractDependencyInjectionSpringContextTests {

	private static final String CLIENT_WEB_SERVICE_REGISTERS = "clientWebServiceRegisters";

	protected String[] getConfigLocations() {
		return new String[] { "beans/appContextTest.xml" };
	}

	/**
	 *
	 */
	public void testAttachNullDocuments() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		try {
			client.wsAttachPage(1, 1, (ArrayOfWSParamDocument) null, security);
		} catch (SOAPFaultException sfe) {
			Assert.assertEquals(IllegalArgumentException.class, sfe.getCause()
					.getClass());
		}

	}

	/**
	 *
	 */
	public void testAttachDocuments() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		String matter = "Resumen del registro";
		XMLGregorianCalendar originalDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());

		WSParamInputRegisterEx datas = generateInputRegister(matter,
				originalDate);

		int bookIdentification = 1;
		WSRegister newRegister = (WSRegister) client.wsNewInputRegister(
				bookIdentification, datas, security);

		ArrayOfWSParamDocument documents = new ArrayOfWSParamDocument();

		WSParamDocument document1 = new WSParamDocument();
		document1.setDocumentName("carpeta");
		document1.setFileName("document1.txt");
		document1.setDocumentContent(new String(
				"Contenido de un documento de prueba.").getBytes());

		WSParamDocument document2 = new WSParamDocument();
		document2.setDocumentName("carpeta");
		document2.setFileName("documento-referenciado.txt");
		document2.setDocumentLocation("524");
		document2.setDocumentContent("Documento referenciado".getBytes());

		WSParamDocument document3 = new WSParamDocument();
		document3.setFileName("documento-sin-carpeta.txt");
		document3.setDocumentContent("Documento sin carpeta".getBytes());

		WSParamDocument document4 = new WSParamDocument();
		document4.setDocumentName("carpeta");
		document4.setFileName("document4.xt");
		document4.setDocumentContent("Documento 4".getBytes());
		// document3.setDocumentLocation("524");

		documents.getWSParamDocument().add(document1);
		documents.getWSParamDocument().add(document2);
		documents.getWSParamDocument().add(document3);
		documents.getWSParamDocument().add(document4);

		try {
			client.wsAttachPage(bookIdentification, newRegister.getFolderId(),
					documents, security);
		} catch (SOAPFaultException sfe) {
			Assert.assertEquals(IllegalArgumentException.class, sfe.getCause()
					.getClass());
		}

	}

	/**
	 *
	 */
	public void testAttachDocumentsWithWrongUser() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("foo");
		value.setPassword("bar");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		try {
			client.wsAttachPage(1, 1,
					(ArrayOfWSParamDocument) new ArrayOfWSParamDocument(),
					security);
		} catch (SOAPFaultException sfe) {
			Assert.assertEquals(IllegalArgumentException.class, sfe.getCause()
					.getClass());
		}

	}

	/**
	 *
	 */
	public void testAttachDocumentsWithEmptyDocuments() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		String matter = "Resumen del registro";
		XMLGregorianCalendar originalDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());

		WSParamInputRegisterEx datas = generateInputRegister(matter,
				originalDate);

		int bookIdentification = 1;
		WSRegister newRegister = (WSRegister) client.wsNewInputRegister(
				bookIdentification, datas, security);

		try {
			client.wsAttachPage(bookIdentification, newRegister.getFolderId(),
					(ArrayOfWSParamDocument) new ArrayOfWSParamDocument(),
					security);
		} catch (SOAPFaultException sfe) {
			Assert.assertEquals(IllegalArgumentException.class, sfe.getCause()
					.getClass());
		}

	}

	/**
	 *
	 */
	public void testAttachDocumentsWithUnknowRegister() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		try {
			client.wsAttachPage(1, 4,
					(ArrayOfWSParamDocument) new ArrayOfWSParamDocument(),
					security);
		} catch (SOAPFaultException sfe) {
			Assert.assertEquals(IllegalArgumentException.class, sfe.getCause()
					.getClass());
		}

	}

	/**
	 *
	 */
	public void testAttachDocumentsWithUnknowBook() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		try {
			client.wsAttachPage(4, 1,
					(ArrayOfWSParamDocument) new ArrayOfWSParamDocument(),
					security);
		} catch (SOAPFaultException sfe) {
			Assert.assertEquals(IllegalArgumentException.class, sfe.getCause()
					.getClass());
		}

	}

	/**
	 * Verifica que el usuario <i>operador1</i> autenticado en la oficina con
	 * código <i>001</i> puede crear un registro aportando los siguientes datos:
	 * <ul>
	 * <li>Fecha de registro: fecha actual</li>
	 * <li>Oficina de registro</li>
	 * <li>Unidad administrativa de origen: Oficina de registro 1(001)</li>
	 * <li>Unidad administrativa de destino: Servicio de tramitación de
	 * licencias (003)</li>
	 * </ul>
	 * 
	 * El estado del registro resultante será <i>INCOMPLETO</i>, debido a que no
	 * se indica tipo de asunto.
	 */
	public void testWSNewInputRegisterIncompleteState() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSParamInputRegisterEx datas = new WSParamInputRegisterEx();
		datas.setOriginalDate(XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date()));
		datas.setOriginalType(1);
		datas.setSender("001");
		datas.setDestination("003");

		int bookIdentification = 1;
		WSRegister newRegister = client.wsNewInputRegister(bookIdentification,
				datas, security);
		assertEquals(newRegister.getBookId(), bookIdentification);
		assertEquals(newRegister.getState(), 1);

	}

	/**
	 * Verifica que el usuario <i>operador1</i> autenticado en la oficina
	 * <i>001</i> no puede crear un registro de entrada con el origen <i>ER1</i>
	 * por estar dado de baja.
	 */
	public void testWSNewInputRegisterWrongSender() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSParamInputRegisterEx datas = new WSParamInputRegisterEx();
		datas.setOriginalDate(XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date()));
		datas.setOriginalType(1);
		datas.setSender("ER1");
		datas.setDestination("003");

		int bookIdentification = 1;
		try {
			client.wsNewInputRegister(bookIdentification, datas, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Verifica que el usuario <i>operador1</i> autenticado en la oficina con
	 * código <i>001</i> puede crear un registro aportando los siguientes datos:
	 * <ul>
	 * <li>Fecha de registro: fecha actual</li>
	 * <li>Oficina de registro</li>
	 * <li>Unidad administrativa de origen: Oficina de registro 1(001)</li>
	 * <li>Unidad administrativa de destino: Servicio de tramitación de
	 * licencias (003)</li>
	 * <li>Asunto : Licencia de obra menor (TLIC)</li>
	 * </ul>
	 * 
	 * El estado del registro resultante será <i>COMPLETO</i>.
	 */
	public void testWSNewInputRegisterBasicCompleteState() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		String matter = "Resumen del registro";
		XMLGregorianCalendar originalDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());

		WSParamInputRegisterEx datas = generateInputRegister(matter,
				originalDate);

		int bookIdentification = 1;
		WSRegister newRegister = (WSRegister) client.wsNewInputRegister(
				bookIdentification, datas, security);
		assertNotNull(newRegister);
		assertEquals(1, newRegister.getBookId());
		assertEquals(0, newRegister.getState());
		assertEquals("001", newRegister.getOffice());
		assertEquals("OFICINA DE REGISTRO1", newRegister.getOfficeName());
		assertEquals("OPERADOR1", newRegister.getUserName());
		assertEquals(originalDate.getDay(), newRegister.getDate().getDay());
		assertEquals(originalDate.getMonth(), newRegister.getDate().getMonth());
		assertEquals(originalDate.getYear(), newRegister.getDate().getYear());

		WSInputRegister inputRegister = client.wsLoadInputRegisterFromId(1,
				newRegister.getFolderId(), security);
		assertNotNull(inputRegister);
		assertEquals(inputRegister.getMatter(), matter);

	}

	/**
	 * @param matter
	 * @param originalDate
	 * @return
	 */
	private WSParamInputRegisterEx generateInputRegister(String matter,
			XMLGregorianCalendar originalDate) {
		WSParamInputRegisterEx datas = new WSParamInputRegisterEx();
		datas.setOriginalDate(originalDate);
		datas.setOriginalType(1);
		datas.setSender("001");
		datas.setDestination("003");
		datas.setMatterType("TLIC");

		datas.setMatter(matter);
		return datas;
	}

	public void testWSNewInputRegisterFullDataCompleteState() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSParamInputRegisterEx datas = new WSParamInputRegisterEx();
		XMLGregorianCalendar originalDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());
		datas.setOriginalDate(originalDate);
		datas.setOriginalType(1);
		datas.setOriginalNumber("00000000000000000025");
		datas.setOriginalEntity("A04102");
		datas.setSender("001");
		datas.setDestination("003");

		// En el atributo matter meter el resumen
		String resumen = "Resumen creado en el campo matter";
		datas.setMatter(resumen);
		datas.setMatterType("TLIC");
		datas.setTransportNumber("1928");
		datas.setTransportType("1");

		// Documentos

		datas.setDocuments(new ArrayOfWSParamDocument());

		WSParamDocument document1 = new WSParamDocument();
		document1.setDocumentName("carpeta");
		document1.setFileName("documento1.txt");
		document1.setDocumentContent(new String(
				"Contenido de un documento de prueba.").getBytes());

		WSParamDocument document2 = new WSParamDocument();
		document2.setDocumentName("carpeta");
		document2.setFileName("documento-referenciado.txt");
		document2.setDocumentContent(new String(
				"Contenido de documento-referenciado.").getBytes());
		// document2.setDocumentLocation("524");

		WSParamDocument document3 = new WSParamDocument();
		document3.setFileName("documento-sin-carpeta.txt");
		document3.setDocumentName("carpeta");
		document3.setDocumentContent(new String(
				"Contenido de documento-sin-carpeta.").getBytes());
		// document3.setDocumentLocation("524");

		datas.getDocuments().getWSParamDocument().add(document1);
		// datas.getDocuments().getWSParamDocument().add(document2);
		// datas.getDocuments().getWSParamDocument().add(document3);

		// Terceros
		datas.setPersons(new ArrayOfWSParamPerson());

		WSParamPerson person1 = new WSParamPerson();
		person1.setPersonName("Hilario Menéndez");
		WSParamPerson person2 = new WSParamPerson();
		person2.setPersonName("Juan Abella");

		datas.getPersons().getWSParamPerson().add(person1);
		datas.getPersons().getWSParamPerson().add(person2);

		// Campos adicionales
		WSAddField campo1 = new WSAddField();
		campo1.setFieldId(21);
		campo1.setValue("Texto adicional");

		WSAddField campo2 = new WSAddField();
		campo2.setFieldId(22);
		campo2.setValue("05-07-2010");

		datas.setAddFields(new ArrayOfWSAddField());

		// datas.getAddFields().getWSAddField().add(campo1);
		// datas.getAddFields().getWSAddField().add(campo2);

		int bookIdentification = 1;
		WSRegister register = (WSRegister) client.wsNewInputRegister(
				bookIdentification, datas, security);
		assertNotNull(register);
		assertNotNull(register.getFolderId());
		assertEquals(1, register.getBookId());
		assertNotNull(register.getNumber());
		assertEquals(originalDate.getDay(), register.getDate().getDay());
		assertEquals(originalDate.getMonth(), register.getDate().getMonth());
		assertEquals(originalDate.getYear(), register.getDate().getYear());
		assertEquals("OPERADOR1", register.getUserName());
		assertEquals(originalDate.getDay(), register.getSystemDate().getDay());
		assertEquals(originalDate.getMonth(), register.getSystemDate()
				.getMonth());
		assertEquals(originalDate.getYear(), register.getSystemDate().getYear());
		assertEquals(0, register.getState());
		assertEquals("001", register.getOffice());
		assertEquals("OFICINA DE REGISTRO1", register.getOfficeName());

		WSInputRegister inputRegister = client.wsLoadInputRegisterFromId(
				bookIdentification, register.getFolderId(), security);
		assertEquals(inputRegister.getMatter(), resumen);

	}

	/**
	 * Verifica que el usuario <i>distribuidor1</i> autenticado en la oficina
	 * con código <i>001</i> no puede crear un registro de entrada en el libro
	 * <i>1</i> por no disponer de permisos de creación.
	 */
	public void testWSNewInputRegisterDistribuidor1NoPermission() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor1");
		value.setPassword("distribuidor1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSParamInputRegisterEx datas = new WSParamInputRegisterEx();
		int bookIdentification = 1;
		try {
			client.wsNewInputRegister(bookIdentification, datas, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Verifica que el usuario <code>operador1</code> autenticado en la oficina
	 * con código <code>operador1</code> crea un registro de salida completo en
	 * el libro de salida con identificador <i>2</i>. Además de los datos
	 * obligatorios se especifican 3 documentos y dos terceros.
	 */
	public void testWSNewOutputRegisterFullDataCompleteState() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		String resumen = "Resumen del registro de salida";
		XMLGregorianCalendar originalDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());

		WSParamOutputRegisterEx datas = generateOutputRegister(resumen);

		datas.setAddFields(new ArrayOfWSAddField());

		// Documentos
		datas.setDocuments(new ArrayOfWSParamDocument());

		WSParamDocument document1 = new WSParamDocument();
		document1.setDocumentName("carpeta");
		document1.setFileName("documento1.txt");
		document1.setDocumentContent(new String(
				"Contenido de un documento de prueba.").getBytes());

		WSParamDocument document2 = new WSParamDocument();
		document2.setDocumentName("carpeta");
		document2.setFileName("documento-referenciado.txt");
		document2.setDocumentContent(new String(
				"Contenido de documento-referenciado.").getBytes());
		document2.setDocumentLocation("524");

		WSParamDocument document3 = new WSParamDocument();
		document3.setFileName("documento-sin-carpeta.txt");
		document3.setDocumentLocation("524");
		document3.setDocumentContent(new String(
				"Contenido de documento-sin-carpeta.").getBytes());

		datas.getDocuments().getWSParamDocument().add(document1);
		datas.getDocuments().getWSParamDocument().add(document2);
		datas.getDocuments().getWSParamDocument().add(document3);

		// Terceros
		datas.setPersons(new ArrayOfWSParamPerson());

		WSParamPerson person1 = new WSParamPerson();
		person1.setPersonName("Hilario Menéndez");
		WSParamPerson person2 = new WSParamPerson();
		person2.setPersonName("Juan Abella");

		datas.getPersons().getWSParamPerson().add(person1);
		datas.getPersons().getWSParamPerson().add(person2);

		int bookIdentification = 2;
		WSRegister register = (WSRegister) client.wsNewOutputRegister(
				bookIdentification, datas, security);
		assertNotNull(register);
		assertNotNull(register.getFolderId());
		assertEquals(2, register.getBookId());
		assertNotNull(register.getNumber());
		assertEquals(originalDate.getDay(), register.getDate().getDay());
		assertEquals(originalDate.getMonth(), register.getDate().getMonth());
		assertEquals(originalDate.getYear(), register.getDate().getYear());
		assertEquals("OPERADOR1", register.getUserName());
		assertEquals(originalDate.getDay(), register.getSystemDate().getDay());
		assertEquals(originalDate.getMonth(), register.getSystemDate()
				.getMonth());
		assertEquals(originalDate.getYear(), register.getSystemDate().getYear());
		assertEquals(0, register.getState());
		assertEquals("001", register.getOffice());
		assertEquals("OFICINA DE REGISTRO1", register.getOfficeName());

		WSOutputRegister outputRegister = client.wsLoadOutputRegisterFromId(
				bookIdentification, register.getFolderId(), security);
		assertNotNull(outputRegister);
		assertEquals(outputRegister.getMatter(), resumen);

	}

	/**
	 * @param resumen
	 * @return
	 */
	private WSParamOutputRegisterEx generateOutputRegister(String resumen) {
		WSParamOutputRegisterEx datas = new WSParamOutputRegisterEx();

		datas.setSender("001");
		datas.setDestination("003");

		datas.setMatter(resumen);
		datas.setMatterType("TLIC");
		datas.setTransportNumber("1928");
		datas.setTransportType("1");
		return datas;
	}

	/**
	 * Verifica que el usuario <i>distribuidor1</i> autenticado en la oficina
	 * con código <i>001</i> no puede crear un registro de salida en el libro
	 * con identificador <i>5</i> por no disponer de permisos de creación sobre
	 * el citado libro.
	 */
	public void testWSNewOutputRegisterDistribuidor1NoPermission() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor1");
		value.setPassword("distribuidor1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 5;
		WSParamOutputRegisterEx datas = new WSParamOutputRegisterEx();
		try {
			client.wsNewOutputRegister(bookIdentification, datas, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Verifica que el usuario <code>distribuidor1</code> autenticado en la
	 * oficina con código <code>001</code> no puede crear un registro de salida
	 * en un libro que no existe.
	 */
	public void testWSNewOutputRegisterDistribuidor1UnknownBook() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor1");
		value.setPassword("distribuidor1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 15;
		WSParamOutputRegisterEx datas = new WSParamOutputRegisterEx();
		try {
			client.wsNewOutputRegister(bookIdentification, datas, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 *
	 */
	public void testWSUpdateInputRegister() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;

		String matter = "Resumen del registro";
		XMLGregorianCalendar originalDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());

		WSParamInputRegisterEx datas = generateInputRegister(matter,
				originalDate);

		WSRegister newRegister = (WSRegister) client.wsNewInputRegister(
				bookIdentification, datas, security);
		assertNotNull(newRegister);

		ArrayOfWSParamField fields = new ArrayOfWSParamField();

		WSParamField field1 = new WSParamField();
		field1.setFldId(19);
		String textoAdicional = "Texto adicional modificado";
		field1.setValue(textoAdicional);
		WSParamField field2 = new WSParamField();
		field2.setFldId(20);
		field2.setValue("10-06-2010");

		WSParamField wsFieldComentarios = new WSParamField();
		wsFieldComentarios.setFldId(AxSf.FLD18_FIELD_ID);
		String comentario = "Registro creado desde el test testWSLoadOutputRegistersConComentarios";
		wsFieldComentarios.setValue(comentario);

		fields.getWSParamField().add(wsFieldComentarios);
		fields.getWSParamField().add(field1);
		fields.getWSParamField().add(field2);

		client.wsUpdateInputRegister(bookIdentification,
				newRegister.getFolderId(), fields, security);

		WSInputRegister wsInputRegister = client.wsLoadInputRegisterFromId(
				bookIdentification, newRegister.getFolderId(), security);

		List<WSAddField> addFields = wsInputRegister.getAddFields()
				.getWSAddField();
		for (WSAddField addField : addFields) {
			System.out.println(addField.getFieldId() + ": "
					+ addField.getValue());
			if (addField.getFieldId() == 19) {
				assertEquals(addField.getValue(), textoAdicional);
			}
			if (addField.getFieldId() == AxSf.FLD18_FIELD_ID) {
				assertEquals(addField.getValue(), comentario);
			}
		}
	}
	
	/**
	 * Comprueba si se actualizan los terceros no validados
	 */
	public void testWSUpdateTercerosInputRegister() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;

		String matter = "Resumen del registro";
		XMLGregorianCalendar originalDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());

		WSParamInputRegisterEx datas = generateInputRegister(matter,
				originalDate);
		
		ArrayOfWSParamPerson personsCreate = new ArrayOfWSParamPerson();
		WSParamPerson person = new WSParamPerson();
		String personName = "Persona1";
		person.setPersonName(personName);
		
		personsCreate.getWSParamPerson().add(person);
		datas.setPersons(personsCreate);

		WSRegister newRegister = (WSRegister) client.wsNewInputRegister(
				bookIdentification, datas, security);
				
		assertNotNull(newRegister);
		
		WSInputRegister inputRegister = client.wsLoadInputRegisterFromId(
				bookIdentification, newRegister.getFolderId(), security);


		List<WSPerson> persons = inputRegister.getPersons().getWSPerson();
		
		assertNotNull(persons);
		assertEquals(persons.get(0).getName(), personName);				
				

		ArrayOfWSParamField fields = new ArrayOfWSParamField();

		WSParamField fieldTercero1 = new WSParamField();
		fieldTercero1.setFldId(9);
		String interesado1 = "Persona2";
		fieldTercero1.setValue(interesado1);
		fields.getWSParamField().add(fieldTercero1);
		
		WSParamField fieldTercero2 = new WSParamField();
		fieldTercero2.setFldId(9);
		String interesado2 = "Persona3";
		fieldTercero2.setValue(interesado2);
		fields.getWSParamField().add(fieldTercero2);
		
		
		client.wsUpdateInputRegister(bookIdentification,
				newRegister.getFolderId(), fields, security);

		WSInputRegister wsInputRegister = client.wsLoadInputRegisterFromId(
				bookIdentification, newRegister.getFolderId(), security);

		persons = wsInputRegister.getPersons().getWSPerson();
		
		assertNotNull(persons);
		assertEquals(persons.get(0).getName(), interesado1);
		assertEquals(persons.get(1).getName(), interesado2);

	}
	
	/**
	 * Comprueba si se eliminan los terceros no validados
	 */
	public void testWSDeleteTercerosInputRegister() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;

		String matter = "Resumen del registro";
		XMLGregorianCalendar originalDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());

		WSParamInputRegisterEx datas = generateInputRegister(matter,
				originalDate);
		
		ArrayOfWSParamPerson personsCreate = new ArrayOfWSParamPerson();
		WSParamPerson person = new WSParamPerson();
		String personName = "Persona1";
		person.setPersonName(personName);
		
		personsCreate.getWSParamPerson().add(person);
		datas.setPersons(personsCreate);

		WSRegister newRegister = (WSRegister) client.wsNewInputRegister(
				bookIdentification, datas, security);
				
		assertNotNull(newRegister);
		
		WSInputRegister inputRegister = client.wsLoadInputRegisterFromId(
				bookIdentification, newRegister.getFolderId(), security);


		List<WSPerson> persons = inputRegister.getPersons().getWSPerson();
		
		assertNotNull(persons);
		assertEquals(persons.get(0).getName(), personName);				
				

		ArrayOfWSParamField fields = new ArrayOfWSParamField();

		WSParamField fieldTercero1 = new WSParamField();
		fieldTercero1.setFldId(9);
		String interesado1 = StringUtils.EMPTY;
		//String interesado1 = null;
		fieldTercero1.setValue(interesado1);
		fields.getWSParamField().add(fieldTercero1);					
		
		client.wsUpdateInputRegister(bookIdentification,
				newRegister.getFolderId(), fields, security);

		WSInputRegister wsInputRegister = client.wsLoadInputRegisterFromId(
				bookIdentification, newRegister.getFolderId(), security);

		persons = wsInputRegister.getPersons().getWSPerson();
		
		assertEquals(persons.size(),0);
		
	}
	
	/**
	 * Comprueba que no se eliminan los terceros no validados al modificar un registro si 
	 * no se especifica ningún interesado
	 */
	public void testWSDoNotDeleteInteresadosInputRegister() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;

		String matter = "Resumen del registro";
		XMLGregorianCalendar originalDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());

		WSParamInputRegisterEx datas = generateInputRegister(matter,
				originalDate);
		
		ArrayOfWSParamPerson personsCreate = new ArrayOfWSParamPerson();
		WSParamPerson person = new WSParamPerson();
		String personName = "Persona1";
		person.setPersonName(personName);
		
		personsCreate.getWSParamPerson().add(person);
		datas.setPersons(personsCreate);

		WSRegister newRegister = (WSRegister) client.wsNewInputRegister(
				bookIdentification, datas, security);
				
		assertNotNull(newRegister);
		
		WSInputRegister inputRegister = client.wsLoadInputRegisterFromId(
				bookIdentification, newRegister.getFolderId(), security);


		List<WSPerson> persons = inputRegister.getPersons().getWSPerson();
		
		assertNotNull(persons);
		assertEquals(persons.get(0).getName(), personName);				
				

		ArrayOfWSParamField fields = new ArrayOfWSParamField();

		WSParamField field1 = new WSParamField();
		field1.setFldId(19);
		String refExpediente = "005646540";
		
		field1.setValue(refExpediente);
		
		fields.getWSParamField().add(field1);
		
		
		client.wsUpdateInputRegister(bookIdentification,
				newRegister.getFolderId(), fields, security);

		WSInputRegister wsInputRegister = client.wsLoadInputRegisterFromId(
				bookIdentification, newRegister.getFolderId(), security);

		persons = wsInputRegister.getPersons().getWSPerson();
		
		assertNotNull(persons);
		assertEquals(persons.get(0).getName(), personName);				
		
	}


	/**
	 *
	 */
	public void testWSUpdateInputRegisterUnknownField() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);
		int bookIdentification = 1;

		String matter = "Resumen del registro";
		XMLGregorianCalendar originalDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());

		WSParamInputRegisterEx datas = generateInputRegister(matter,
				originalDate);

		WSRegister newRegister = (WSRegister) client.wsNewInputRegister(
				bookIdentification, datas, security);
		assertNotNull(newRegister);

		ArrayOfWSParamField fields = new ArrayOfWSParamField();

		WSParamField field1 = new WSParamField();
		field1.setFldId(25);
		field1.setValue("Campo que no existe");

		fields.getWSParamField().add(field1);
		try {
			client.wsUpdateInputRegister(bookIdentification,
					newRegister.getFolderId(), fields, security);
			fail();
		} catch (SOAPFaultException sfe) {

		}
	}

	/**
	 *
	 */
	public void testWSUpdateOutputRegisterSenders() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		ArrayOfWSParamField fields = new ArrayOfWSParamField();
		int registerIdentification = 5;
		int bookIdentification = 2;

		WSParamField field1 = new WSParamField();
		field1.setFldId(9);
		field1.setIndex(1);
		field1.setValue("Remitente 1");

		WSParamField field2 = new WSParamField();
		field2.setFldId(9);
		field2.setIndex(2);
		field2.setValue("Remitente 2");

		fields.getWSParamField().add(field1);
		fields.getWSParamField().add(field2);

		client.wsUpdateOutputRegister(bookIdentification,
				registerIdentification, fields, security);
	}

	/**
	 *
	 */
	public void testWSUpdateOutputRegister() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		String matter = "Resumen del registro de salida modificado";

		WSParamOutputRegisterEx datas = generateOutputRegister(matter);

		int bookIdentification = 2;

		WSRegister newRegister = (WSRegister) client.wsNewOutputRegister(
				bookIdentification, datas, security);
		assertNotNull(newRegister);

		ArrayOfWSParamField fields = new ArrayOfWSParamField();

		WSParamField field1 = new WSParamField();
		field1.setFldId(13);
		field1.setValue("Campo 13 modificado");

		WSParamField fieldOrigen = new WSParamField();
		fieldOrigen.setFldId(7);
		//String origenId = "4888";
		String origenId = "002";
		fieldOrigen.setValue(origenId);
		
		fields.getWSParamField().add(field1);
		fields.getWSParamField().add(fieldOrigen);

		client.wsUpdateOutputRegister(bookIdentification,
				newRegister.getFolderId(), fields, security);
		WSOutputRegister outputRegister = client.wsLoadOutputRegisterFromId(bookIdentification, newRegister.getFolderId(), security);
		System.out.println(outputRegister.getSender());
	}

	/**
	 *
	 */
	public void testWSLoadInputRegisters() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int size = 1000;
		int bookIdentification = 1;
		String condition = null;
		int initValue = 1;
		WSInputRegistersResponse wsLoadInputRegisters = client
				.wsLoadInputRegisters(bookIdentification, condition, initValue,
						size, security);

		assertNotNull(wsLoadInputRegisters);

		for (WSInputRegister inputRegister : wsLoadInputRegisters.getList()
				.getWSInputRegister()) {
			System.out.println("Número de registro: "
					+ inputRegister.getNumber());
			ArrayOfWSAddField addFields = inputRegister.getAddFields();
			List<WSAddField> addFieldsList = addFields.getWSAddField();
			for (WSAddField addField : addFieldsList) {
				System.out.println("Field " + addField.getFieldId() + ": "
						+ addField.getValue());
			}
		}
	}

	/**
	 * Busca los registros de entrada que tengan comentarios. El campo
	 * comentario es la columna FLD18.
	 */
	public void testWSLoadInputRegistersConComentarios() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int size = 1;
		int bookIdentification = 1;

		String matter = "Registro prueba testWSLoadInputRegistersConComentarios";

		XMLGregorianCalendar originalDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());

		WSParamInputRegisterEx datas = generateInputRegister(matter,
				originalDate);

		datas.setAddFields(new ArrayOfWSAddField());
		WSAddField wsFieldComentarios = new WSAddField();
		wsFieldComentarios.setFieldId(AxSf.FLD18_FIELD_ID);
		String comentario = "Registro creado desde el test testWSLoadInputRegistersConComentarios";
		wsFieldComentarios.setValue(comentario);

		datas.getAddFields().getWSAddField().add(wsFieldComentarios);

		WSRegister newRegister = (WSRegister) client.wsNewInputRegister(
				bookIdentification, datas, security);

		// Buscamos registros con comentarios
		String condition = "fld1='" + newRegister.getNumber() + "'";
		int initValue = 1;
		WSInputRegistersResponse wsLoadInputRegisters = client
				.wsLoadInputRegisters(bookIdentification, condition, initValue,
						size, security);

		assertNotNull(wsLoadInputRegisters);
		assertEquals(size, wsLoadInputRegisters.getList().getWSInputRegister()
				.size());
		for (WSInputRegister inputRegister : wsLoadInputRegisters.getList()
				.getWSInputRegister()) {
			System.out.println("Número de registro: "
					+ inputRegister.getNumber());

			ArrayOfWSAddField addFields = inputRegister.getAddFields();
			List<WSAddField> addFieldsList = addFields.getWSAddField();
			for (WSAddField addField : addFieldsList) {
				System.out.println("Field " + addField.getFieldId() + ": "
						+ addField.getValue());
				if (addField.getFieldId() == AxSf.FLD18_FIELD_ID) {
					assertNotNull(addField.getValue());
				}
			}
		}
	}

	/**
	 * Prueba para comprobar que se cargan los datos de los registros originales
	 */
	public void testWSLoadInputRegistersByOriginalNumber() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		String originalNumber = "00000000000000000025";
		int size = 10;
		int bookIdentification = 1;
		String condition = "fld10='" + originalNumber + "'";
		int initValue = 1;
		WSInputRegistersResponse wsLoadInputRegisters = client
				.wsLoadInputRegisters(bookIdentification, condition, initValue,
						size, security);

		assertNotNull(wsLoadInputRegisters);
		assertEquals(size, wsLoadInputRegisters.getList().getWSInputRegister()
				.size());

		for (WSInputRegister inputRegister : wsLoadInputRegisters.getList()
				.getWSInputRegister()) {
			System.out.println("Número de registro: "
					+ inputRegister.getNumber());
			assertEquals(inputRegister.getOriginalNumber(), originalNumber);
		}
	}

	/**
	 *
	 */
	public void testwsLoadInputRegistersExOp() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSLoadInputRegistersEx parameters = new WSLoadInputRegistersEx();
		parameters.setCondition(StringUtils.EMPTY);
		parameters.setInitValue(1);
		parameters.setSize(20);

		WSLoadInputRegistersExResponse wsLoadInputRegistersExOp = client
				.wsLoadInputRegistersExOp(parameters, security);

		assertNotNull(wsLoadInputRegistersExOp);
		assertEquals(parameters.getSize(), wsLoadInputRegistersExOp
				.getWSLoadInputRegistersExResult().getList()
				.getWSInputRegister().size());
		for (WSInputRegister inputRegister : wsLoadInputRegistersExOp
				.getWSLoadInputRegistersExResult().getList()
				.getWSInputRegister()) {
			System.out.println("Número de registro: "
					+ inputRegister.getNumber());
		}
	}

	/**
	 * Verifica que el usuario <code>operador2</code> autenticado en la oficina
	 * con código <i>999</i> no puede recuperar los registros de entrada del
	 * libro con identificador <i>7</i> porque no tiene permisos de consulta
	 * sobre el citado libro.
	 */
	public void testWSLoadInputRegistersOperador2() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador2");
		value.setPassword("operador2");
		value.setOfficeCode("999");
		security.setUsernameToken(value);

		int bookIdentification = 8;
		String condition = StringUtils.EMPTY;
		int initValue = 1;
		int size = 10;
		try {
			client.wsLoadInputRegisters(bookIdentification, condition,
					initValue, size, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Prueba que el usuario operador2 puede hacer una consulta sobre libros que
	 * no tiene permisos con el método wsLoadInputRegistersExOp
	 */
	public void testWSLoadInputRegistersExOpOperador2() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador2");
		value.setPassword("operador2");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSLoadInputRegistersEx parameters = new WSLoadInputRegistersEx();
		String condition = "fld6=1";

		parameters.setCondition(condition);
		int bookIdentification = 6;
		int initValue = 1;
		int size = 100;
		parameters.setInitValue(initValue);
		parameters.setSize(size);

		WSLoadInputRegistersExResponse wsLoadInputRegistersExOp = client
				.wsLoadInputRegistersExOp(parameters, security);

		assertNotNull(wsLoadInputRegistersExOp);

		for (WSInputRegister inputRegister : wsLoadInputRegistersExOp
				.getWSLoadInputRegistersExResult().getList()
				.getWSInputRegister()) {
			System.out.println("Número de registro: "
					+ inputRegister.getNumber());
		}

	}

	/**
	 * Verifica la recuperación de los registros de entrada para el usuario
	 * <i>sigem</i> conectado a la oficina con código <i>001</i>.
	 */
	public void testWSLoadInputRegistersSigem() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int size = 1000;
		int bookIdentification = 1;
		String condition = null;
		int initValue = 1;
		WSInputRegistersResponse wsLoadInputRegisters = client
				.wsLoadInputRegisters(bookIdentification, condition, initValue,
						size, security);

		assertNotNull(wsLoadInputRegisters);
		for (WSInputRegister inputRegister : wsLoadInputRegisters.getList()
				.getWSInputRegister()) {
			System.out.println("Número de registro: "
					+ inputRegister.getNumber());
		}
	}

	/**
	 *
	 */
	public void testWSLoadOutputRegisters() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 2;
		String condition = null;
		int initValue = 1;
		int size = 100;
		WSOutputRegistersResponse wsLoadOutputRegisters = client
				.wsLoadOutputRegisters(bookIdentification, condition,
						initValue, size, security);

		assertNotNull(wsLoadOutputRegisters);
		for (WSOutputRegister outputRegister : wsLoadOutputRegisters.getList()
				.getWSOutputRegister()) {
			System.out.println("Número de registro: "
					+ outputRegister.getNumber());
			ArrayOfWSAddField addFields = outputRegister.getAddFields();
			List<WSAddField> addFieldsList = addFields.getWSAddField();
			for (WSAddField addField : addFieldsList) {
				System.out.println("Field " + addField.getFieldId() + ": "
						+ addField.getValue());
			}
		}
	}

	/**
	 *
	 */
	public void testWSLoadOutputRegistersConComentarios() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 2;

		WSParamOutputRegisterEx datas = new WSParamOutputRegisterEx();

		datas.setSender("001");
		datas.setDestination("003");
		String resumen = "Resumen del registro de salida";
		datas.setMatter(resumen);
		datas.setMatterType("TLIC");
		datas.setTransportNumber("1928");
		datas.setTransportType("1");

		datas.setAddFields(new ArrayOfWSAddField());
		WSAddField wsFieldComentarios = new WSAddField();
		wsFieldComentarios.setFieldId(AxSf.FLD14_FIELD_ID);
		String comentario = "Registro creado desde el test testWSLoadOutputRegistersConComentarios";
		wsFieldComentarios.setValue(comentario);

		datas.getAddFields().getWSAddField().add(wsFieldComentarios);

		WSRegister register = (WSRegister) client.wsNewOutputRegister(
				bookIdentification, datas, security);

		String condition = "fld1= '" + register.getNumber() + "'";
		int initValue = 1;
		int size = 1;
		WSOutputRegistersResponse wsLoadOutputRegisters = client
				.wsLoadOutputRegisters(bookIdentification, condition,
						initValue, size, security);

		assertNotNull(wsLoadOutputRegisters);
		assertEquals(size, wsLoadOutputRegisters.getList()
				.getWSOutputRegister().size());

		for (WSOutputRegister outputRegister : wsLoadOutputRegisters.getList()
				.getWSOutputRegister()) {
			System.out.println("Número de registro: "
					+ outputRegister.getNumber());

			ArrayOfWSAddField addFields = outputRegister.getAddFields();
			List<WSAddField> addFieldsList = addFields.getWSAddField();
			for (WSAddField addField : addFieldsList) {
				System.out.println("Field " + addField.getFieldId() + ": "
						+ addField.getValue());
				if (addField.getFieldId() == AxSf.FLD14_FIELD_ID) {
					assertNotNull(addField.getValue());
					assertEquals(comentario, addField.getValue());
				}
			}
		}

	}

	/**
	 * Verifica que el usuario <code>operador2</code> autenticado en la oficina
	 * con código <i>999</i> no puede recuperar los registros de salida del
	 * libro con identificador <i>7</i> porque no tiene permisos de consulta
	 * sobre el citado libro.
	 */

	public void testWSLoadOutputRegistersOperador2() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador2");
		value.setPassword("operador2");
		value.setOfficeCode("999");
		security.setUsernameToken(value);

		int bookIdentification = 7;
		String condition = StringUtils.EMPTY;
		int initValue = 1;
		int size = 10;
		try {
			client.wsLoadOutputRegisters(bookIdentification, condition,
					initValue, size, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();

	}

	/**
	 *
	 */
	public void testWSLoadInputRegisterFromId() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		String matter = "Registro prueba loadInputRegisterFromId";

		XMLGregorianCalendar originalDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());

		WSParamInputRegisterEx datas = generateInputRegister(matter,
				originalDate);
		String originalNumber = "00000000000000000025";
		datas.setOriginalNumber(originalNumber);
		datas.setOriginalType(1);
		String originalEntity = "A04102";
		datas.setOriginalEntity(originalEntity);
		String transportNumber = "1928";

		datas.setTransportNumber(transportNumber);

		int bookIdentification = 1;
		WSRegister newRegister = (WSRegister) client.wsNewInputRegister(
				bookIdentification, datas, security);

		WSInputRegister wsInputRegister = client.wsLoadInputRegisterFromId(
				bookIdentification, newRegister.getFolderId(), security);

		assertNotNull(wsInputRegister);
		assertEquals(1, wsInputRegister.getBookId());
		assertEquals(newRegister.getFolderId(), wsInputRegister.getFolderId());
		assertEquals(newRegister.getDate(), wsInputRegister.getDate());

		// assertEquals("MEH", wsInputRegister.getDestination());
		// assertEquals("MINISTERIO DE ECONOMIA Y HACIENDA", wsInputRegister
		// .getDestinationName());

		assertEquals(matter, wsInputRegister.getMatter());

		assertEquals("TLIC", wsInputRegister.getMatterType());
		assertEquals("LICENCIA DE OBRA MENOR",
				wsInputRegister.getMatterTypeName());
		assertEquals(newRegister.getNumber(), wsInputRegister.getNumber());
		assertEquals(newRegister.getOffice(), wsInputRegister.getOffice());
		assertEquals(newRegister.getOfficeName(),
				wsInputRegister.getOfficeName());
		assertEquals(originalEntity, wsInputRegister.getOriginalEntity());
		assertEquals("Agricultura, Comercio e Industria",
				wsInputRegister.getOriginalEntityName());
		assertEquals(originalNumber, wsInputRegister.getOriginalNumber());
		assertEquals(1, wsInputRegister.getOriginalType().intValue());
		assertEquals(originalDate, wsInputRegister.getOriginalDate());

		assertEquals("001", wsInputRegister.getSender());
		assertEquals("SERVICIO DE RELACIONES CON EL CIUDADANO",
				wsInputRegister.getSenderName());
		assertEquals(0, wsInputRegister.getState());
		// assertEquals(originalDate, wsInputRegister.getSystemDate());

		assertEquals(transportNumber, wsInputRegister.getTransportNumber());
		assertNull(wsInputRegister.getTransportType());
		assertEquals("OPERADOR1", wsInputRegister.getUserName());

		// assertEquals(1,
		// wsInputRegister.getAddFields().getWSAddField().size());

		// assertEquals(2,
		// wsInputRegister.getDocuments().getWSDocument().size());
		// assertEquals(2, wsInputRegister.getPersons().getWSPerson().size());
	}

	/**
	 *
	 */
	public void testWSLoadUnknownInputRegisterFromId() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;
		int registerIdentification = 1670;

		try {
			client.wsLoadInputRegisterFromId(bookIdentification,
					registerIdentification, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 *
	 */
	public void testWSLoadOutputRegisterFromId() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 2;
		int registerIdentification = 5;

		WSOutputRegister wsOutputRegister = client.wsLoadOutputRegisterFromId(
				bookIdentification, registerIdentification, security);

		assertNotNull(wsOutputRegister);
		assertEquals(2, wsOutputRegister.getBookId());
		assertEquals(5, wsOutputRegister.getFolderId());
		assertEquals("003", wsOutputRegister.getDestination());
		assertEquals("SERVICIO DE TRAMITACIÓN DE LICENCIAS",
				wsOutputRegister.getDestinationName());
		assertEquals("LICENCIA DE OBRA MENOR", wsOutputRegister.getMatter());
		assertEquals("TLIC", wsOutputRegister.getMatterType());
		assertEquals("LICENCIA DE OBRA MENOR",
				wsOutputRegister.getMatterTypeName());
		assertEquals("201000100000005", wsOutputRegister.getNumber());
		assertEquals("001", wsOutputRegister.getOffice());
		assertEquals("OFICINA DE REGISTRO1", wsOutputRegister.getOfficeName());
		assertEquals("001", wsOutputRegister.getSender());
		assertEquals("SERVICIO DE RELACIONES CON EL CIUDADANO",
				wsOutputRegister.getSenderName());
		assertEquals(0, wsOutputRegister.getState());
		assertEquals("1928", wsOutputRegister.getTransportNumber());
		assertNull(wsOutputRegister.getTransportType());
		assertEquals("OPERADOR1", wsOutputRegister.getUserName());
		assertEquals(1, wsOutputRegister.getAddFields().getWSAddField().size());
		assertEquals(
				XMLGregorianCalendarHelper.toXMLGregorianCalendar(new LocalDateTime(
						2010, 7, 6, 11, 39, 43).toDateTime().toDate()),
				wsOutputRegister.getDate());
		assertEquals(2, wsOutputRegister.getDocuments().getWSDocument().size());
		assertEquals(2, wsOutputRegister.getPersons().getWSPerson().size());
		assertEquals(
				XMLGregorianCalendarHelper.toXMLGregorianCalendar(new LocalDate(
						2010, 7, 6).toDateMidnight().toDate()),
				wsOutputRegister.getSystemDate());
	}

	/**
	 * @throws IOException
	 * 
	 */
	public void testWSAttachPageEx() throws IOException {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;

		String matter = "Test WSAttachPageEX";
		XMLGregorianCalendar originalDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());

		WSParamInputRegisterEx datas = generateInputRegister(matter,
				originalDate);

		WSRegister newRegister = (WSRegister) client.wsNewInputRegister(
				bookIdentification, datas, security);

		ArrayOfWSParamDocument documents = new ArrayOfWSParamDocument();
		WSParamDocument doc1 = new WSParamDocument();
		doc1.setDocumentName("carpeta");
		doc1.setDocumentLocation("524");
		doc1.setFileName("fichero-adjuntado-por-referencia.tiff");
		File file = new File("/tmp/TIFFS/tif1.tiff");

		FileInputStream fi = new FileInputStream(file);

		byte[] fileContents = IOUtils.toByteArray(fi);
		doc1.setDocumentContent(fileContents);

		documents.getWSParamDocument().add(doc1);

		WSDocumentsResponse wsAttachPageEx = client.wsAttachPageEx(
				bookIdentification, newRegister.getFolderId(), documents,
				security);

		assertNotNull(wsAttachPageEx);
		assertEquals(1, wsAttachPageEx.getList().getWSDocument().size());
		assertEquals(1, wsAttachPageEx.getTotal());
	}

	
	/**
	 * @throws IOException
	 *
	 */
	public void testWSAttachPageRegistroSalida() throws IOException {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 2;

		String matter = "Test testWSAttachPageRegistroSalida";


		WSParamOutputRegisterEx datas = generateOutputRegister(matter);

		WSRegister newRegister = (WSRegister) client.wsNewOutputRegister(
				bookIdentification, datas, security);

		ArrayOfWSParamDocument documents = new ArrayOfWSParamDocument();
		WSParamDocument doc1 = new WSParamDocument();
		doc1.setDocumentName("carpeta");
		//doc1.setDocumentLocation("524");
		doc1.setFileName("fichero-adjuntado-por-referencia.tiff");
		File file = new File("/tmp/TIFFS/00000001.TIF");

		FileInputStream fi = new FileInputStream(file);

		byte[] fileContents = IOUtils.toByteArray(fi);
		doc1.setDocumentContent(fileContents);

		documents.getWSParamDocument().add(doc1);

		WSDocumentsResponse wsAttachPageEx = client.wsAttachPageEx(
				bookIdentification, newRegister.getFolderId(), documents,
				security);


		assertNotNull(wsAttachPageEx);
		assertEquals(1, wsAttachPageEx.getList().getWSDocument().size());
		assertEquals(1, wsAttachPageEx.getTotal());
	}

	
	/**
	 *
	 */
	public void testWSGetPage() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;

		String matter = "Resumen del registro";
		XMLGregorianCalendar originalDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());

		WSParamInputRegisterEx datas = generateInputRegister(matter,
				originalDate);

		WSRegister newRegister = (WSRegister) client.wsNewInputRegister(
				bookIdentification, datas, security);

		ArrayOfWSParamDocument documents = new ArrayOfWSParamDocument();

		WSParamDocument document1 = new WSParamDocument();
		document1.setDocumentName("carpeta");
		document1.setFileName("document1.txt");
		document1.setDocumentContent(new String(
				"Contenido de un documento de prueba.").getBytes());

		WSParamDocument document2 = new WSParamDocument();
		document2.setDocumentName("carpeta");
		document2.setFileName("documento-referenciado.txt");
		document2.setDocumentLocation("524");
		document2.setDocumentContent("Documento referenciado".getBytes());

		WSParamDocument document3 = new WSParamDocument();
		document3.setDocumentName("carpeta");
		document3.setFileName("documento-sin-carpeta.txt");
		document3.setDocumentContent("Documento sin carpeta".getBytes());

		WSParamDocument document4 = new WSParamDocument();
		document4.setDocumentName("carpeta");
		document4.setFileName("document4.txt");
		document4.setDocumentContent("Documento 4".getBytes());
		// document3.setDocumentLocation("524");

		documents.getWSParamDocument().add(document1);
		documents.getWSParamDocument().add(document2);
		documents.getWSParamDocument().add(document3);
		documents.getWSParamDocument().add(document4);

		client.wsAttachPage(bookIdentification, newRegister.getFolderId(),
				documents, security);

		// Test: El documentIndex y pageIndex empiezan en 1, no en 0.
		int documentIndex = 1;
		int pageIndex = 4;
		byte[] wsGetPage = client.wsGetPage(bookIdentification,
				newRegister.getFolderId(), documentIndex, pageIndex, security);

		assertNotNull(wsGetPage);
		assertEquals("Documento 4", new String(wsGetPage));
	}

	/**
	 *
	 */
	public void testWSGetUnknownPage() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;
		int registerIdentification = 167;
		int documentIndex = 2;
		int pageIndex = 3;
		byte[] wsGetPage = client.wsGetPage(bookIdentification,
				registerIdentification, documentIndex, pageIndex, security);

		assertNotNull(wsGetPage);
		assertEquals("Contenido de un documento de prueba.", new String(
				wsGetPage));
	}

	/**
	 *
	 */
	public void testWSGetPageEx() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;

		String matter = "Resumen del registro";
		XMLGregorianCalendar originalDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());

		WSParamInputRegisterEx datas = generateInputRegister(matter,
				originalDate);

		WSRegister newRegister = (WSRegister) client.wsNewInputRegister(
				bookIdentification, datas, security);

		ArrayOfWSParamDocument documents = new ArrayOfWSParamDocument();

		WSParamDocument document1 = new WSParamDocument();
		document1.setDocumentName("carpeta1");
		document1.setFileName("document1.txt");
		document1.setDocumentContent(new String(
				"Contenido de un documento de prueba.").getBytes());

		WSParamDocument document2 = new WSParamDocument();
		document2.setDocumentName("carpeta1");
		document2.setFileName("documento-referenciado.txt");
		document2.setDocumentLocation("524");
		document2.setDocumentContent("Documento referenciado".getBytes());

		WSParamDocument document3 = new WSParamDocument();
		document3.setDocumentName("carpeta2");
		document3.setFileName("documento2.txt");
		document3.setDocumentContent("Documento sin carpeta".getBytes());

		WSParamDocument document4 = new WSParamDocument();
		document4.setDocumentName("carpeta2");
		document4.setFileName("documento4.txt");
		document4.setDocumentContent("Documento4".getBytes());
		// document3.setDocumentLocation("524");

		documents.getWSParamDocument().add(document1);
		documents.getWSParamDocument().add(document2);
		documents.getWSParamDocument().add(document3);
		documents.getWSParamDocument().add(document4);

		client.wsAttachPage(bookIdentification, newRegister.getFolderId(),
				documents, security);

		// El documentId no se utiliza en este método
		int documentId = 0;
		int pageId = 4;
		byte[] wsGetPageEx = client.wsGetPageEx(bookIdentification,
				newRegister.getFolderId(), documentId, pageId, security);

		assertNotNull(wsGetPageEx);
		assertEquals("Documento4", new String(wsGetPageEx));
	}

	/**
	 * Verifica que el usuario <i>operador1</i> no puede cancelar un registro de
	 * un libro de entrada por no tener el perfil de <i>superusuario</i>.
	 */
	public void testWSCancelInputRegisterNoSuperuser() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;
		int registerIdentification = 74;

		try {
			client.wsCancelInputRegister(bookIdentification,
					registerIdentification, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}

		fail();
	}

	/**
	 * Verifica que el usuario <i>sigem</i> con perfil de <i>superusuario</i>
	 * puede cancelar un registro de un libro de entrada.
	 */
	public void testWSCancelInputRegisterSuperuser() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;
		int registerIdentification = 74;

		client.wsCancelInputRegister(bookIdentification,
				registerIdentification, security);
	}

	public void testWSCancelInputRegisterWithDistributions() {

		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);

		ISWebServiceDistributionsSoap clientDistributions = (ISWebServiceDistributionsSoap) this.applicationContext
				.getBean("clientWebServiceDistributions");

		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;

		String matter = "Resumen del registro";
		XMLGregorianCalendar originalDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());

		WSParamInputRegisterEx datas = generateInputRegister(matter,
				originalDate);

		WSRegister wsRegister = client.wsNewInputRegister(bookIdentification,
				datas, security);

		int destinationType = 1;
		int destinationId = 20;
		String destinationName = "distribuidor1";
		String matterDist = "Test distribución testWSCancelInputRegisterWithDistributions";

		es.ieci.tecdoc.isicres.ws.legacy.service.distributions.Security security1 = new es.ieci.tecdoc.isicres.ws.legacy.service.distributions.Security();
		es.ieci.tecdoc.isicres.ws.legacy.service.distributions.UsernameTokenClass value1 = new es.ieci.tecdoc.isicres.ws.legacy.service.distributions.UsernameTokenClass();
		value1.setUsername("sigem");
		value1.setPassword("sigem");

		security1.setUsernameToken(value1);
		clientDistributions.wsRegisterDistribute(wsRegister.getNumber(),
				bookIdentification, destinationType, 21, "distribuidor2", 1,
				destinationId, destinationName, matterDist, security1);

		client.wsCancelInputRegister(bookIdentification,
				wsRegister.getFolderId(), security);

	}

	/**
	 * Verifica que el usuario <i>operador1</i> no puede cancelar un registro de
	 * un libro de salida por no ser <i>superusuario</i>.
	 */
	public void testWSCancelOutputRegisterNoSuperuser() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 2;
		int registerIdentification = 5;

		try {
			client.wsCancelOutputRegister(bookIdentification,
					registerIdentification, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}

		fail();
	}

	/**
	 * Verifica que el usuario <i>sigem</i> con perfil de <i>superusuario</i>
	 * puede cancelar un registro de un libro de salida.
	 */
	public void testWSCancelOutputRegisterSuperUser() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 2;
		int registerIdentification = 5;
		client.wsCancelOutputRegister(bookIdentification,
				registerIdentification, security);
	}

	/**
	 * Verifica que el usuario <i>sigem</i> con perfil de <i>superusuario</i> no
	 * puede cancelar un registro perteneciente a un libro de entrada como si
	 * fuera de salida.
	 */
	public void testWSCancelOutputRegisterSuperUserWrongBook() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;
		int registerIdentification = 74;

		try {
			client.wsCancelOutputRegister(bookIdentification,
					registerIdentification, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}

		fail();
	}

	/**
	 *
	 */
	public void testWSImportInputRegisterIncompleteState() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;
		String regNumber = "20100010000001200";
		XMLGregorianCalendar regDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate(2010, 7, 7)
						.toDateMidnight().toDate());
		String user = security.getUsernameToken().getUsername();
		XMLGregorianCalendar sysDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate().toDateMidnight()
						.toDate());
		String office = "001";
		WSParamInputRegisterEx datas = new WSParamInputRegisterEx();

		WSRegister inputRegister = client.wsImportInputRegister(
				bookIdentification, regNumber, regDate, user, sysDate, office,
				datas, security);

		assertNotNull(inputRegister);
		assertEquals(bookIdentification, inputRegister.getBookId());
		assertNotNull(inputRegister.getFolderId());
		assertEquals(1, inputRegister.getState());
		assertEquals(regNumber, inputRegister.getNumber());
		assertEquals("001", inputRegister.getOffice());
		assertEquals("OFICINA DE REGISTRO1", inputRegister.getOfficeName());
		assertEquals("OPERADOR1", inputRegister.getUserName());
		assertEquals(regDate, inputRegister.getDate());
		assertEquals(sysDate, inputRegister.getSystemDate());
	}

	/**
	 *
	 */
	public void testWSImportInputRegisterWrongBookType() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 2;
		String regNumber = "20100010000000200";
		XMLGregorianCalendar regDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate(2010, 7, 7)
						.toDateMidnight().toDate());
		String user = security.getUsernameToken().getUsername();
		XMLGregorianCalendar sysDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate().toDateMidnight()
						.toDate());
		String office = "001";
		WSParamInputRegisterEx datas = new WSParamInputRegisterEx();

		try {
			client.wsImportInputRegister(bookIdentification, regNumber,
					regDate, user, sysDate, office, datas, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 *
	 */
	public void testWSImportDuplicateInputRegister() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;
		String regNumber = "20100010000000200";
		XMLGregorianCalendar regDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate(2010, 7, 7)
						.toDateMidnight().toDate());
		String user = security.getUsernameToken().getUsername();
		XMLGregorianCalendar sysDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate().toDateMidnight()
						.toDate());
		String office = "001";
		WSParamInputRegisterEx datas = new WSParamInputRegisterEx();

		try {
			client.wsImportInputRegister(bookIdentification, regNumber,
					regDate, user, sysDate, office, datas, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();

	}

	public void testWSImportInputRegisterCompleteState() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;
		String regNumber = "20100010000001201";

		XMLGregorianCalendar regDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate(2010, 7, 7)
						.toDateMidnight().toDate());
		String user = security.getUsernameToken().getUsername();
		XMLGregorianCalendar sysDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate().toDateMidnight()
						.toDate());
		String office = "001";
		WSParamInputRegisterEx datas = new WSParamInputRegisterEx();
		datas.setSender("001");
		datas.setDestination("003");
		datas.setMatter("Resumen");
		datas.setMatterType("TLIC");

		WSRegister inputRegister = client.wsImportInputRegister(
				bookIdentification, regNumber, regDate, user, sysDate, office,
				datas, security);

		assertNotNull(inputRegister);
	}

	public void testWSImportInputRegisterFullDataCompleteState() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 1;
		/*
		 * TODO: Una vez importado un registro con este número de registro, ya
		 * no se podrá importar otro registro con el mismo número, por lo que el
		 * test fallará. Hay que buscar una solución para generar el número de
		 * registro dinámicamente y asegurarse de que no esté repetido.
		 */
		String regNumber = "20100010000002202";
		XMLGregorianCalendar regDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate(2010, 7, 7)
						.toDateMidnight().toDate());
		String user = security.getUsernameToken().getUsername();
		XMLGregorianCalendar sysDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate(2010, 7, 8)
						.toDateMidnight().toDate());
		String office = "001";
		WSParamInputRegisterEx datas = new WSParamInputRegisterEx();
		datas.setSender("001");
		datas.setDestination("003");
		datas.setMatter("Resumen");
		datas.setMatterType("TLIC");

		datas.setAddFields(new ArrayOfWSAddField());
		WSAddField field1 = new WSAddField();
		field1.setFieldId(19);
		field1.setValue("Valor del campo 19");
		datas.getAddFields().getWSAddField().add(field1);

		WSAddField wsFieldComentarios = new WSAddField();
		wsFieldComentarios.setFieldId(AxSf.FLD18_FIELD_ID);
		String comentario = "Registro creado desde el test testWSImportInputRegisterFullDataCompleteState";
		wsFieldComentarios.setValue(comentario);
		datas.getAddFields().getWSAddField().add(wsFieldComentarios);

		datas.setDocuments(new ArrayOfWSParamDocument());
		WSParamDocument doc1 = new WSParamDocument();
		doc1.setDocumentName("documento1");
		doc1.setFileName("fichero1");
		doc1.setDocumentContent(new String(
				"Contenido del fichero1 del documento1").getBytes());
		datas.getDocuments().getWSParamDocument().add(doc1);

		datas.setOriginalDate(XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate(2010, 7, 8)
						.toDateMidnight().toDate()));
		// datas.setOriginalEntity("");
		datas.setOriginalNumber("0000000202");
		datas.setOriginalType(1);

		datas.setPersons(new ArrayOfWSParamPerson());
		WSParamPerson person1 = new WSParamPerson();
		person1.setPersonName("Hilario");
		datas.getPersons().getWSParamPerson().add(person1);

		datas.setTransportNumber("2000");
		datas.setTransportType("Tipo de transporte 2000");

		WSRegister wsRegister = client.wsImportInputRegister(
				bookIdentification, regNumber, regDate, user, sysDate, office,
				datas, security);

		assertNotNull(wsRegister);
		assertEquals(bookIdentification, wsRegister.getBookId());
		assertNotNull(wsRegister.getFolderId());
		assertEquals(0, wsRegister.getState());
		assertEquals(regNumber, wsRegister.getNumber());
		assertEquals("001", wsRegister.getOffice());
		assertEquals("OFICINA DE REGISTRO1", wsRegister.getOfficeName());
		assertEquals("OPERADOR1", wsRegister.getUserName());
		assertEquals(regDate, wsRegister.getDate());
		assertEquals(sysDate, wsRegister.getSystemDate());

		WSInputRegister wsInputRegister = client.wsLoadInputRegisterFromId(
				bookIdentification, wsRegister.getFolderId(), security);

		ArrayOfWSAddField addFields = wsInputRegister.getAddFields();
		List<WSAddField> addFieldsList = addFields.getWSAddField();
		for (WSAddField addField : addFieldsList) {
			System.out.println("Field " + addField.getFieldId() + ": "
					+ addField.getValue());
			if (addField.getFieldId() == AxSf.FLD18_FIELD_ID) {
				assertNotNull(addField.getValue());
			}
		}

	}

	/**
	 *
	 */
	public void testWSImportOutputRegisterNoPermission() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador2");
		value.setPassword("operador2");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 2;
		String regNumber = "20100020000000300";
		XMLGregorianCalendar regDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());
		String user = security.getUsernameToken().getUsername();
		XMLGregorianCalendar sysDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new Date());
		String office = "001";
		WSParamOutputRegisterEx datas = new WSParamOutputRegisterEx();

		try {
			client.wsImportOutputRegister(bookIdentification, regNumber,
					regDate, user, sysDate, office, datas, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 *
	 */
	public void testWSImportOutputRegisterIncompleteState() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 2;
		String regNumber = "20100020000001300";
		XMLGregorianCalendar regDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate(2010, 7, 8)
						.toDateMidnight().toDate());
		String user = security.getUsernameToken().getUsername();
		XMLGregorianCalendar sysDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate(2010, 7, 8)
						.toDateMidnight().toDate());
		String office = "001";
		WSParamOutputRegisterEx datas = new WSParamOutputRegisterEx();

		WSRegister outputRegister = client.wsImportOutputRegister(
				bookIdentification, regNumber, regDate, user, sysDate, office,
				datas, security);

		assertNotNull(outputRegister);
		assertEquals(bookIdentification, outputRegister.getBookId());
		assertNotNull(outputRegister.getFolderId());
		assertEquals(1, outputRegister.getState());
		assertEquals(regNumber, outputRegister.getNumber());
		assertEquals("001", outputRegister.getOffice());
		assertEquals("OFICINA DE REGISTRO1", outputRegister.getOfficeName());
		assertEquals("OPERADOR1", outputRegister.getUserName());
		assertEquals(regDate, outputRegister.getDate());
		assertEquals(sysDate, outputRegister.getSystemDate());
	}

	/**
	 *
	 */
	public void testWSImportOutputRegisterCompleteState() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 2;
		String regNumber = "20100020000001301";
		XMLGregorianCalendar regDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate(2010, 7, 8)
						.toDateMidnight().toDate());
		String user = security.getUsernameToken().getUsername();
		XMLGregorianCalendar sysDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate(2010, 7, 8)
						.toDateMidnight().toDate());
		String office = "001";
		WSParamOutputRegisterEx datas = new WSParamOutputRegisterEx();
		datas.setSender("001");
		datas.setDestination("003");
		datas.setMatterType("TLIC");
		datas.setMatter("Solicitud para mejoras en el jardin");

		WSRegister wsRegister = client.wsImportOutputRegister(
				bookIdentification, regNumber, regDate, user, sysDate, office,
				datas, security);

		assertNotNull(wsRegister);
		assertEquals(bookIdentification, wsRegister.getBookId());
		assertNotNull(wsRegister.getFolderId());
		assertEquals(0, wsRegister.getState());
		assertEquals(regNumber, wsRegister.getNumber());
		assertEquals("001", wsRegister.getOffice());
		assertEquals("OFICINA DE REGISTRO1", wsRegister.getOfficeName());
		assertEquals("OPERADOR1", wsRegister.getUserName());
		assertEquals(regDate, wsRegister.getDate());
		assertEquals(sysDate, wsRegister.getSystemDate());
	}

	/**
	 *
	 */
	public void testWSImportOutputRegisterFullDataCompleteState() {
		ISWebServiceRegistersSoap client = (ISWebServiceRegistersSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_REGISTERS);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookIdentification = 2;
		String regNumber = "20100020000001302";
		XMLGregorianCalendar regDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate(2010, 7, 8)
						.toDateMidnight().toDate());
		String user = security.getUsernameToken().getUsername();
		XMLGregorianCalendar sysDate = XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDate(2010, 7, 8)
						.toDateMidnight().toDate());
		String office = "001";
		WSParamOutputRegisterEx datas = new WSParamOutputRegisterEx();
		datas.setSender("001");
		datas.setDestination("003");
		datas.setMatterType("TLIC");
		datas.setMatter("Solicitud para mejoras en el jardin");

		datas.setAddFields(new ArrayOfWSAddField());
		WSAddField field1 = new WSAddField();
		field1.setFieldId(15);
		field1.setValue("05-07-2010");
		datas.getAddFields().getWSAddField().add(field1);

		WSAddField wsFieldComentarios = new WSAddField();
		wsFieldComentarios.setFieldId(AxSf.FLD14_FIELD_ID);
		String comentario = "Registro creado desde el test testWSImportOutputRegisterFullDataCompleteState";
		wsFieldComentarios.setValue(comentario);

		datas.getAddFields().getWSAddField().add(wsFieldComentarios);

		datas.setDocuments(new ArrayOfWSParamDocument());
		WSParamDocument doc1 = new WSParamDocument();
		doc1.setDocumentName("documento1");
		doc1.setFileName("fichero1");
		doc1.setDocumentContent(new String(
				"Contenido del fichero1 del documento1").getBytes());
		datas.getDocuments().getWSParamDocument().add(doc1);

		datas.setPersons(new ArrayOfWSParamPerson());
		WSParamPerson person1 = new WSParamPerson();
		person1.setPersonName("Juan Abella");
		datas.getPersons().getWSParamPerson().add(person1);

		datas.setTransportNumber("3000");
		datas.setTransportType("Tipo de transporte 3000");

		WSRegister wsRegister = client.wsImportOutputRegister(
				bookIdentification, regNumber, regDate, user, sysDate, office,
				datas, security);

		assertNotNull(wsRegister);
		assertEquals(bookIdentification, wsRegister.getBookId());
		assertNotNull(wsRegister.getFolderId());
		assertEquals(0, wsRegister.getState());
		assertEquals(regNumber, wsRegister.getNumber());
		assertEquals("001", wsRegister.getOffice());
		assertEquals("OFICINA DE REGISTRO1", wsRegister.getOfficeName());
		assertEquals("OPERADOR1", wsRegister.getUserName());
		assertEquals(regDate, wsRegister.getDate());
		assertEquals(sysDate, wsRegister.getSystemDate());

		WSOutputRegister wsOutputRegister = client.wsLoadOutputRegisterFromId(
				bookIdentification, wsRegister.getFolderId(), security);

		ArrayOfWSAddField addFields = wsOutputRegister.getAddFields();
		List<WSAddField> addFieldsList = addFields.getWSAddField();
		for (WSAddField addField : addFieldsList) {
			System.out.println("Field " + addField.getFieldId() + ": "
					+ addField.getValue());
			if (addField.getFieldId() == AxSf.FLD14_FIELD_ID) {
				assertNotNull(addField.getValue());
				assertEquals(comentario, addField.getValue());
			}
		}
	}
}
