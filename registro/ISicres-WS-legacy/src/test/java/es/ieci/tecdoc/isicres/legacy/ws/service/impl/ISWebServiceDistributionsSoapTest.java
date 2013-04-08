package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import java.util.List;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import es.ieci.tecdoc.isicres.api.business.vo.enums.EstadoDistribucionEnum;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.XMLGregorianCalendarHelper;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.ISWebServiceDistributionsSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.UsernameTokenClass;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSAcceptDistributionEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSAcceptDistributionExResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSDistribution;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSDistributionsResponse;

/**
 * Pruebas de integración para verificar el correcto funcionamiento del WS con
 * interfaz <code>ISWebServiceDistributionsSoap</code>.
 * 
 * @see ISWebServiceDistributionsSoap
 * 
 * @author IECISA
 * 
 */
public class ISWebServiceDistributionsSoapTest extends
		AbstractDependencyInjectionSpringContextTests {

	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		client = (ISWebServiceDistributionsSoap) this.applicationContext
				.getBean("clientWebServiceDistributions");
	}

	/**
	 * Verifica que el usuario <i>distribuidor2</i> puede aceptar la
	 * distribución asociada al registro con número <i>201000100000093</i>.
	 */
	public void testWsAcceptDistribution() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor2");
		value.setPassword("distribuidor2");
		value.setOfficeCode("003");
		security.setUsernameToken(value);

		getClient().wsAcceptDistribution("201000100000093", security);

	}

	/**
	 * Verifica que el usuario <i>sigem</i> puede aceptar la distribución con
	 * número de registro <i>20100020000000302</i>. Al ser una distribución de
	 * un registro de salida, se indica el identificador del libro de entrada en
	 * el que se generará una copia del registro original distribuido.
	 */
	public void testWsAcceptDistributionExOpWithBookId() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSAcceptDistributionEx parameters = new WSAcceptDistributionEx();
		parameters.setNumReg("20100020000000302");
		parameters.setBookId(1);
		WSAcceptDistributionExResponse response = getClient()
				.wsAcceptDistributionExOp(parameters, security);

		assertNotNull(response);

	}

	/**
	 * Verifica que el usuario <i>sigem</i> no puede aceptar un registro de
	 * salida distribuido sin indicar el identificador del libro de entrada en
	 * el que crear la copia del registro distribuido original debido a que el
	 * usuario tiene permisos de creación en más de un libro de entrada y no se
	 * sabe resolver cuál de ellos escoger.
	 */
	public void testWsAcceptDistributionExOpWithoutBookIdAndPermissionsInMoreThanOneInputBook() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSAcceptDistributionEx parameters = new WSAcceptDistributionEx();
		parameters.setNumReg("20100020000000302");
		parameters.setBookId(0);

		try {
			getClient().wsAcceptDistributionExOp(parameters, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();

	}

	/**
	 * Verifica que el usuario <i>distribuidor2</i> puede aceptar el registro de
	 * salida distribuido con número de registro <i>20100020000000302</i> sin
	 * necesidad de indicar el libro de entrada en el que crear la copia del
	 * registro original porque sólo tiene permisos de creación en un libro de
	 * entrada.
	 */
	public void testWsAcceptDistributionExOpWithoutBookIdAndPermissionsInOnlyOneInputBook() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor2");
		value.setPassword("distribuidor2");
		value.setOfficeCode("003");
		security.setUsernameToken(value);

		WSAcceptDistributionEx parameters = new WSAcceptDistributionEx();
		parameters.setNumReg("20100020000000302");
		parameters.setBookId(0);

		WSAcceptDistributionExResponse response = getClient()
				.wsAcceptDistributionExOp(parameters, security);
		assertNotNull(response);

	}

	/**
	 * Verifica que el usuario <i>sigem</i> no puede aceptar la distribución con
	 * número de registro asociado <i>1</i> porque no existe ninguna
	 * distribución con esas características en estado <i>PENDIENTE</i> en su
	 * bandeja de entrada.
	 */
	public void testWsAcceptUnknownDistribution() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		try {
			getClient().wsAcceptDistribution("1", security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Comprueba que el usuario <code>operador1</code> no tiene permisos para
	 * aceptar distribuciones.
	 */
	public void testWsAcceptDistributionOperador1NoPermission() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		try {
			getClient().wsAcceptDistribution("1", security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();

	}

	/**
	 * Comprueba que el usuario <code>operador2</code> no tiene permisos para
	 * aceptar distribuciones.
	 */
	public void testWsAcceptDistributionOperador2NoPermission() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador2");
		value.setPassword("operador2");
		value.setOfficeCode("999");
		security.setUsernameToken(value);

		try {
			getClient().wsAcceptDistribution("1", security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();

	}

	/**
	 * Verifica que el usuario <i>sigem</i> no puede aceptar la distribución con
	 * número de registro asociado <i>1</i> por facilitar unas credenciales
	 * incorrectas.
	 */
	public void testWsAcceptDistributionInvalidLogin() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("foo");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		try {
			getClient().wsAcceptDistribution("1", security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();

	}

	/**
	 * Recupera las distribuciones de entrada del usuario <i>sigem</i> que se
	 * encuentra en cada uno de los estados [PENDIENTE, ACEPTADO,
	 * REDISTRIBUIDO].
	 */
	public void testWSLoadInputDistributions() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int state = 5;
		int initValue = 1;
		int size = 10;
		WSDistributionsResponse wsLoadInputDistributions = getClient()
				.wsLoadInputDistributions(state, initValue, size, security);

		assertNotNull(wsLoadInputDistributions);
		assertEquals(1, wsLoadInputDistributions.getTotal());

		state = 1;
		wsLoadInputDistributions = getClient().wsLoadInputDistributions(state,
				initValue, size, security);

		assertNotNull(wsLoadInputDistributions);
		assertEquals(0, wsLoadInputDistributions.getTotal());
		assertEquals(0, wsLoadInputDistributions.getList().getWSDistribution()
				.size());

		state = 2;
		wsLoadInputDistributions = getClient().wsLoadInputDistributions(state,
				initValue, size, security);

		assertNotNull(wsLoadInputDistributions);
		assertEquals(0, wsLoadInputDistributions.getTotal());
		assertEquals(0, wsLoadInputDistributions.getList().getWSDistribution()
				.size());
	}

	/**
	 * Recupera las distribuciones de entrada del usuario <i>distribuidor2</i>
	 * que se encuentran en estado <i>ACEPTADO</i>. Sólo tiene una distribución
	 * de estas características
	 */
	public void testWSLoadInputDistributionsDistribuidor2() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor2");
		value.setPassword("distribuidor2");
		value.setOfficeCode("003");
		security.setUsernameToken(value);

		int state = 2;
		int initValue = 1;
		int size = 10;
		WSDistributionsResponse wsLoadInputDistributions = getClient()
				.wsLoadInputDistributions(state, initValue, size, security);

		assertNotNull(wsLoadInputDistributions);
		assertEquals(1, wsLoadInputDistributions.getList().getWSDistribution()
				.size());
		assertEquals(1, wsLoadInputDistributions.getTotal());
		WSDistribution distribution = wsLoadInputDistributions.getList()
				.getWSDistribution().get(0);
		assertNotNull(distribution);
		assertEquals(1, distribution.getArchiveId());
		assertEquals("Libro de Entrada", distribution.getBookName());
		assertEquals(1, distribution.getBookType());
		assertEquals(9, distribution.getDestinationId());
		assertEquals("distribuidor2", distribution.getDestinationName());
		assertEquals(1, distribution.getDestinationType());
		assertEquals(XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDateTime(2010, 7, 8, 17, 17,
						15, 733).toDateTime().toDate()), distribution
				.getDistributionDate());
		assertEquals(167, distribution.getFolderId());
		assertEquals(114, distribution.getId());
		assertEquals(StringUtils.EMPTY, distribution.getMessage());
		assertEquals(XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDateTime(2010, 7, 6, 11, 18,
						39).toDateTime().toDate()), distribution
				.getRegisterDate());
		assertEquals("SERVICIO DE TRAMITACIÓN DE LICENCIAS", distribution
				.getRegisterDestinationName());
		assertNull(distribution.getRegisterMatter());
		assertEquals("LICENCIA DE OBRA MENOR", distribution
				.getRegisterMatterTypeName());
		assertEquals("201000100000093", distribution.getRegisterNumber());
		assertEquals(8, distribution.getSenderId());
		assertEquals("OFICINA DE REGISTRO2", distribution.getSenderName());
		assertEquals(2, distribution.getSenderType());
		assertEquals(2, distribution.getState());
		assertEquals(XMLGregorianCalendarHelper
				.toXMLGregorianCalendar(new LocalDateTime(2010, 7, 12, 12, 54,
						00, 800).toDateTime().toDate()), distribution
				.getStateDate());
		assertEquals("ACEPTADO", distribution.getStateDescription());
		assertEquals("DISTRIBUIDOR2", distribution.getUser());

	}

	/**
	 * Verifica que el usuario <i>sigem</i> puede recuperar las diez primeras
	 * distribuciones de entrada del usuario con identificador
	 * <i>9</i>(distribuidor2) que se encuentran en cada uno de los estados
	 * permitidos.
	 */
	public void testWSLoadInputDistributionsByUserId() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int state = 1;
		int userId = 21; // distribuidor2
		int initValue = 1;
		int size = 10;
		WSDistributionsResponse distributionsByUserId = getClient()
				.wsLoadInputDistributionsByUserId(state, userId, initValue,
						size, security);

		assertNotNull(distributionsByUserId);
		assertEquals(0, distributionsByUserId.getTotal());
		assertEquals(0, distributionsByUserId.getList().getWSDistribution()
				.size());

		state = 2;
		distributionsByUserId = getClient().wsLoadInputDistributionsByUserId(
				state, userId, initValue, size, security);

		assertNotNull(distributionsByUserId);
		assertEquals(1, distributionsByUserId.getTotal());
		assertEquals(1, distributionsByUserId.getList().getWSDistribution()
				.size());

		state = 3;
		distributionsByUserId = getClient().wsLoadInputDistributionsByUserId(
				state, userId, initValue, size, security);

		assertNotNull(distributionsByUserId);
		assertEquals(0, distributionsByUserId.getTotal());
		assertEquals(0, distributionsByUserId.getList().getWSDistribution()
				.size());

		state = 4;
		distributionsByUserId = getClient().wsLoadInputDistributionsByUserId(
				state, userId, initValue, size, security);

		assertNotNull(distributionsByUserId);
		assertEquals(0, distributionsByUserId.getTotal());
		assertEquals(0, distributionsByUserId.getList().getWSDistribution()
				.size());

		state = 5;
		distributionsByUserId = getClient().wsLoadInputDistributionsByUserId(
				state, userId, initValue, size, security);

		assertNotNull(distributionsByUserId);
		assertEquals(0, distributionsByUserId.getTotal());
		assertEquals(0, distributionsByUserId.getList().getWSDistribution()
				.size());

	}

	/**
	 * Verifica la recuperación de las distribuciones de salida del usuario
	 * <i>sigem</i> en estado <i>PENDIENTE</i>.
	 */
	public void testWSLoadOutputDistributions() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int state = 1;
		int initValue = 1;
		int size = 10;
		WSDistributionsResponse wsDistributionsResponse = getClient()
				.wsLoadOutputDistributions(state, initValue, size, security);

		assertNotNull(wsDistributionsResponse);
		assertEquals(0, wsDistributionsResponse.getTotal());
		assertEquals(0, wsDistributionsResponse.getList().getWSDistribution()
				.size());
	}

	/**
	 * Verifica que el usuario <i>sigem</i> puede recuperar las diez primeras
	 * distribuciones de salida del usuario con identificador
	 * <i>9</i>(distribuidor2) que se encuentran en estado <i>PENDIENTE</i>.
	 */
	public void testWSLoadOutputDistributionsByUserId() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		int size = 10;
		int userId = 21;
		int initValue = 1;
		int state = 1;
		WSDistributionsResponse wsDistributionsResponse = getClient()
				.wsLoadOutputDistributionsByUserId(state, userId, initValue,
						size, security);

		assertNotNull(wsDistributionsResponse);
		assertEquals(2, wsDistributionsResponse.getTotal());
		assertEquals(2, wsDistributionsResponse.getList().getWSDistribution()
				.size());

	}

	/**
	 * Prueba la búsqueda de las distribuciones de entrada por una condición sql
	 */
	public void testWSLoadInputDistributionsByCondition(){
		
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		//value.setOfficeCode("001");
		security.setUsernameToken(value);
		
		int size = 1000;

		int initValue = 1;	
		
		String query = "id_orig=3 and state=1";

		WSDistributionsResponse wsDistributionsResponse = getClient().wsLoadInputDistributionsByCondition(query, initValue, size, security);
		
		assertNotNull(wsDistributionsResponse);

		System.out.println("Consulta: " + query);
		System.out.println("Usuario: " + value.getUsername());
		List <WSDistribution> list = wsDistributionsResponse.getList().getWSDistribution();
		for (WSDistribution wsDistribution: list){
			assertEquals(wsDistribution.getSenderId(),3);
			assertEquals(wsDistribution.getState(),1);
			System.out.println("Distribution Id: " + wsDistribution.getId());
			//System.out.println("Destination Id: " + wsDistribution.getDestinationId());
		}
		
		Security securityOpe = new Security();
		UsernameTokenClass valueOpe = new UsernameTokenClass();

		securityOpe.setUsernameToken(value);
		valueOpe.setUsername("distribuidor2");
		valueOpe.setPassword("distribuidor2");
		//valueOpe.setOfficeCode("1");
		securityOpe.setUsernameToken(valueOpe);
		
		System.out.println("Consulta: " + query);
		System.out.println("Usuario: " + valueOpe.getUsername());
		wsDistributionsResponse = getClient().wsLoadInputDistributionsByCondition(query, initValue, size, securityOpe);
		
		assertNotNull(wsDistributionsResponse);
		
		List <WSDistribution> listOperador1 = wsDistributionsResponse.getList().getWSDistribution();
		for (WSDistribution wsDistribution: listOperador1){
			assertEquals(wsDistribution.getSenderId(),3);
			assertEquals(wsDistribution.getState(),1);
			System.out.println("Distribution Id: " + wsDistribution.getId());
			//System.out.println("Destination Id: " + wsDistribution.getDestinationId());
		}
		


	}
	
	
	/**
	 * Prueba la búsqueda de las distribuciones de salida por una condición sql
	 */
	public void testWSLoadOutputDistributionsByCondition(){
		
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("999");
		security.setUsernameToken(value);
		
		int size = 1000;

		int initValue = 1;	
		
		String query = "id_orig=3 and state=1";

		WSDistributionsResponse wsDistributionsResponse = getClient().wsLoadOutputDistributionsByCondition(query, initValue, size, security);
		
		assertNotNull(wsDistributionsResponse);

		System.out.println("Consulta: " + query);
		System.out.println("Usuario: " + value.getUsername());
		List <WSDistribution> list = wsDistributionsResponse.getList().getWSDistribution();
		for (WSDistribution wsDistribution: list){
			assertEquals(wsDistribution.getSenderId(),3);
			assertEquals(wsDistribution.getState(),1);
			System.out.println("Distribution Id: " + wsDistribution.getId());
			//System.out.println("Destination Id: " + wsDistribution.getDestinationId());
		}
		
		Security securityOpe = new Security();
		UsernameTokenClass valueOpe = new UsernameTokenClass();

		securityOpe.setUsernameToken(value);
		valueOpe.setUsername("operador2");
		valueOpe.setPassword("operador2");
		//valueOpe.setOfficeCode("1");
		securityOpe.setUsernameToken(valueOpe);
		
		System.out.println("Consulta: " + query);
		System.out.println("Usuario: " + valueOpe.getUsername());
		wsDistributionsResponse = getClient().wsLoadOutputDistributionsByCondition(query, initValue, size, securityOpe);
		
		assertNotNull(wsDistributionsResponse);
		
		List <WSDistribution> listOperador1 = wsDistributionsResponse.getList().getWSDistribution();
		for (WSDistribution wsDistribution: listOperador1){
			assertEquals(wsDistribution.getSenderId(),3);
			assertEquals(wsDistribution.getState(),1);
			System.out.println("Distribution Id: " + wsDistribution.getId());
			//System.out.println("Destination Id: " + wsDistribution.getDestinationId());
		}
		


	}
	
	/**
	 * Prueba la búsqueda de distribuciones si tener en cuenta el usuario
	 */
	public void testWSLoadDistributionsByConditionEx(){
		
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);
		
		int size = 10;
		int initValue = 1;
		
		
		String query = "id_orig=3";

		WSDistributionsResponse wsDistributionsResponse = getClient().wsLoadDistributionsByConditionEx(query, initValue, size, security);
		
		assertNotNull(wsDistributionsResponse);

		System.out.println("Consulta: " + query);
		List <WSDistribution> list = wsDistributionsResponse.getList().getWSDistribution();
		for (WSDistribution wsDistribution: list){
			assertEquals(wsDistribution.getSenderId(),3);
			System.out.println("Distribution Id: " + wsDistribution.getId());
		}
		
		Security securityOpe = new Security();
		UsernameTokenClass valueOpe = new UsernameTokenClass();

		securityOpe.setUsernameToken(value);
		valueOpe.setUsername("distribuidor2");
		valueOpe.setPassword("distribuidor2");
		//valueOpe.setOfficeCode("1");
		securityOpe.setUsernameToken(valueOpe);
		
		System.out.println("Consulta: " + query);
		WSDistributionsResponse wsDistributionsResponse2 = getClient().wsLoadDistributionsByConditionEx(query, initValue, size, securityOpe);
		
		assertNotNull(wsDistributionsResponse2);
		
		List <WSDistribution> listOperador1 = wsDistributionsResponse2.getList().getWSDistribution();
		for (WSDistribution wsDistribution: listOperador1){
			assertEquals(wsDistribution.getSenderId(),3);
			System.out.println("Distribution Id: " + wsDistribution.getId());
		}
		
		assertEquals(wsDistributionsResponse.getTotal(), wsDistributionsResponse2.getTotal());
		assertEquals(listOperador1.size(),list.size());
		assertEquals(listOperador1.size(),size);
		System.out.println("Distribuciones totales en la consulta: " + wsDistributionsResponse.getTotal());
		
	}
	
	/**
	 * Verifica que el usuario <i>distribuidor2</i> puede archivar la
	 * distribución con número de registro asociado <i>20100020000000302</i>.
	 */
	public void testWSArchiveDistribution() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor2");
		value.setPassword("distribuidor2");
		value.setOfficeCode("003");
		security.setUsernameToken(value);

		String numReg = "201000100000093";
		getClient().wsArchiveDistribution(numReg, security);

	}

	/**
	 * Verifica que el usuario <i>operador1</i> no puede archivar la
	 * distribución con número de registro asociado <i>20100000000000101</i>
	 * porque no dispone de permisos suficientes.
	 */
	public void testWSArchiveDistributionOperador1NoPermission() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		String numReg = "20100000000000101";

		try {
			getClient().wsArchiveDistribution(numReg, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Verifica que el usuario <i>operador2</i> no puede archivar la
	 * distribución con número de registro asociado <i>20100000000000101</i>
	 * porque no dispone de permisos suficientes.
	 */
	public void testWSArchiveDistributionOperador2NoPermission() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador2");
		value.setPassword("operador2");
		value.setOfficeCode("999");
		security.setUsernameToken(value);

		String numReg = "20100000000000101";

		try {
			getClient().wsArchiveDistribution(numReg, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Verifica que el usuario <i>distribuidor1</i> puede rechazar la
	 * distribución con número de registro asociado <i>201000100000093</i>
	 * especificando como motivo de rechazo <i>Motivo del rechazo</i>.
	 */
	public void testWSRejectDistribution() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor1");
		value.setPassword("distribuidor1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		String numReg = "201000100000093";
		String matter = "Motivo del rechazo";
		getClient().wsRejectDistribution(numReg, matter, security);
	}

	/**
	 * Verifica que el motivo de rechazo es un campo obligatorio para rechazar
	 * una distribución.
	 */
	public void testWSRejectDistributionNoMatter() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor1");
		value.setPassword("distribuidor1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		String numReg = "201000100000093";
		String matter = null;
		try {
			getClient().wsRejectDistribution(numReg, matter, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Verifica que el usuario <i>operador1</i> no puede rechazar la
	 * distribución con número de registro asociado <i>20100000000000101</i>
	 * porque no dispone de permisos suficientes.
	 */
	public void testWSRejectDistributionOperador1NoPermission() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		String numReg = "20100000000000101";
		String matter = "Porque sí";

		try {
			getClient().wsRejectDistribution(numReg, matter, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Verifica que el usuario <i>operador2</i> no puede rechazar la
	 * distribución con número de registro asociado <i>20100000000000101</i>
	 * porque no dispone de permisos suficientes.
	 */
	public void testWSRejectDistributionOperador2NoPermission() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador2");
		value.setPassword("operador2");
		value.setOfficeCode("999");
		security.setUsernameToken(value);

		String numReg = "20100000000000101";
		String matter = "Porque sí";

		try {
			getClient().wsRejectDistribution(numReg, matter, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Verifica que el usuario <i>distribuidor2</i> puede cambiar el destino de
	 * la distribucion con número de registro asociado <i>201000100000093</i>.
	 * El nuevo destino es el que tiene por código <i>MEH</i>. Es necesario que
	 * este destino tenga una lista de distribución dada de alta en la tabla
	 * <code>scr_distlist</code>.
	 */
	public void testWSRedistributeInputDistribution() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor2");
		value.setPassword("distribuidor2");
		value.setOfficeCode("003");
		security.setUsernameToken(value);

		String numReg = "201000100000093";
		String codeDest = "MEH";
		getClient().wsRedistributeInputDistribution(numReg, codeDest, security);
	}

	/**
	 * Prueba la redistribución de una distribución de entrada de un 
	 * registro que tiene varias distribuciones
	 */
	public void testWSRedistributeInputDistribution5() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor2");
		value.setPassword("distribuidor2");
		
		security.setUsernameToken(value);

		//String numReg = "201200100000007";
		String numReg="201299900000001";
		String codeDest = "002";
		getClient().wsRedistributeInputDistribution(numReg, codeDest, security);
	}
	
	

	/**
	 * Verifica que el usuario <i>operador1</i> no tiene permisos suficientes
	 * para cambiar el destino a la distribución con número de registro asociado
	 * <i>20100000000000100</i>.
	 */
	public void testWSRedistributeInputDistributionOperador1NoPermission() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		String numReg = "20100000000000100";
		String codeDest = "Ahí va";

		try {
			getClient().wsRedistributeInputDistribution(numReg, codeDest,
					security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Verifica que el usuario <i>operador2</i> no tiene permisos suficientes
	 * para cambiar el destino de la distribución con número de registro
	 * asociado <i>20100000000000100</i>.
	 */
	public void testWSRedistributeInputDistributionOperador2NoPermission() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador2");
		value.setPassword("operador2");
		value.setOfficeCode("999");
		security.setUsernameToken(value);

		String numReg = "20100000000000100";
		String codeDest = "Ahí va";

		try {
			getClient().wsRedistributeInputDistribution(numReg, codeDest,
					security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * 
	 */
	public void testWSRedistributeOutputDistribution() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor2");
		value.setPassword("distribuidor2");
		value.setOfficeCode("003");
		security.setUsernameToken(value);

		String numReg = "201000100000093";
		String codeDest = "MEH";
		getClient()
				.wsRedistributeOutputDistribution(numReg, codeDest, security);
	}

	/**
	 * 
	 */
	public void testWSRedistributeOutputDistributionOperador1NoPermission() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		String numReg = "20100000000000100";
		String codeDest = "000";

		try {
			getClient().wsRedistributeOutputDistribution(numReg, codeDest,
					security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * 
	 */
	public void testWSRedistributeOutputDistributionOperador2NoPermission() {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador2");
		value.setPassword("operador2");
		value.setOfficeCode("999");
		security.setUsernameToken(value);

		String numReg = "20100000000000100";
		String codeDest = "000";

		try {
			getClient().wsRedistributeOutputDistribution(numReg, codeDest,
					security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Logado con el usuario sigem se crea una distribución desde la oficina de reg. telemático
	 * al usuario SIGEM.
	 * 
	 */
	public void  testWSRegisterDistributeSIGEMToDist2(){
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		security.setUsernameToken(value);
		
		String numReg = "201299900000002";
		int bookId = 1;
		int senderType = 2;
		int senderId = 5;
		String senderName="OFICINA DE REGISTRO TELEMATICO";
		int destinationType=1;
		int destinationId = 3; //SIGEM
		String destinationName = "sigem";
		String matter = "Distribución de REG. TELEMATICO a sigem";
		
		WSDistribution wsDistribution = getClient().wsRegisterDistribute(numReg, bookId, senderType, senderId, senderName, destinationType, destinationId, destinationName, matter, security);
		
		assertEquals(wsDistribution.getDestinationId(),destinationId);
		assertEquals(wsDistribution.getDestinationName(),destinationName);
		assertEquals(wsDistribution.getDestinationType(),destinationType);		

		assertEquals(wsDistribution.getSenderId(),senderId);
		assertEquals(wsDistribution.getSenderName(),senderName);
		assertEquals(wsDistribution.getSenderType(),senderType);
		assertEquals(wsDistribution.getState(),EstadoDistribucionEnum.PENDIENTE.getValue());
		
	}
	
	/**
	 * Logado con el susuario sigem, se crea una distribución desde el distribuidor 1 al distribuidor 2
	 */
	public void  testWSRegisterDistributeDist1ToDist2(){
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		security.setUsernameToken(value);
		
		String numReg = "201299900000001";
		int bookId = 1;
		int senderType = 1;
		int senderId = 20;
		String senderName="distribuidor1";
		int destinationType=1;
		int destinationId = 21; //Distribuidor 2
		String destinationName = "distribuidor2";
		String matter = "Distribución de dist1 a dist2";
		
		WSDistribution wsDistribution = getClient().wsRegisterDistribute(numReg, bookId, senderType, senderId, senderName, destinationType, destinationId, destinationName, matter, security);
		
		assertEquals(wsDistribution.getDestinationId(),destinationId);
		assertEquals(wsDistribution.getDestinationName(),destinationName);
		assertEquals(wsDistribution.getDestinationType(),destinationType);		
		assertEquals(wsDistribution.getSenderId(),senderId);
		assertEquals(wsDistribution.getSenderName(),senderName);
		assertEquals(wsDistribution.getSenderType(),senderType);
		assertEquals(wsDistribution.getState(),EstadoDistribucionEnum.PENDIENTE.getValue());
		
	}


	/**
	 * Logado con el usuario sigem y la oficina 999 se crea una distribución desde distribuidor 2
	 * a distribuidor 1.
	 */
	public void  testWSRegisterDistributeDist2ToDist1Office999(){
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("999");
		security.setUsernameToken(value);
		
		String numReg = "201299900000003";
		int bookId = 1;
		int senderType = 1;
		int senderId = 21;
		String senderName="distribuidor2";
		int destinationType=1;
		int destinationId = 20; //Distribuidor 1
		String destinationName = "distribuidor1";
		String matter = "Distribución de dist2 a dist1 desde oficina 999";
		
		WSDistribution wsDistribution = getClient().wsRegisterDistribute(numReg, bookId, senderType, senderId, senderName, destinationType, destinationId, destinationName, matter, security);
		
		assertEquals(wsDistribution.getDestinationId(),destinationId);
		assertEquals(wsDistribution.getDestinationName(),destinationName);
		assertEquals(wsDistribution.getDestinationType(),destinationType);		
		assertEquals(wsDistribution.getSenderId(),senderId);
		assertEquals(wsDistribution.getSenderName(),senderName);
		assertEquals(wsDistribution.getSenderType(),senderType);
		assertEquals(wsDistribution.getState(),EstadoDistribucionEnum.PENDIENTE.getValue());
		
	}
	
	/**
	 * Realiza una redistribucion manual de distribuidor2 a distribuidor1
	 */
	public void testWSRedistributeInputDistributionManual(){
		
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor2");
		value.setPassword("distribuidor2");
		
		security.setUsernameToken(value);
		
		String numReg = "201200100000008";
		//String numReg="201299900000001";
		
		int destinationType=1;
		int destinationId=20;
		String destinationName="distribuidor1";
		getClient().wsRedistributeInputDistributionManual(numReg, destinationType, destinationId, destinationName, "Redistribución JUnit de dist2 a dist1", security);
		
	}
	
	/**
	 * Realiza una redistribucion manual de distribuidor2 a distribuidor1.
	 * Debe de cambiar todas las distribuciones del registro. 
	 * Este registro tiene varias distribuciones 
	 */
	public void testWSRedistributeInputDistributionsManual(){
		
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor2");
		value.setPassword("distribuidor2");
		
		security.setUsernameToken(value);
		
		String numReg = "201200100000009";
		
		int destinationType=1;
		int destinationId=20;
		String destinationName="distribuidor1";
		getClient().wsRedistributeInputDistributionManual(numReg, destinationType, destinationId, destinationName, "Redistribución JUnit de dist2 a dist1",security);	
	}
	
	/**
	 * Realiza una redistribucion manual de distribuidor2 a distribuidor1
	 */
	public void testWSRedistributeOutputDistributionManual(){
		
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor2");
		value.setPassword("distribuidor2");
		
		security.setUsernameToken(value);
		
		String numReg = "201200100000007";
		//String numReg="201299900000001";
		
		int destinationType=1;
		int destinationId=20;
		String destinationName="distribuidor1";
		getClient().wsRedistributeOutputDistributionManual(numReg, destinationType, destinationId, destinationName, "Redistribución JUnit de dist2 a dist1", security);
		
	}

	public ISWebServiceDistributionsSoap getClient() {
		return client;
	}

	protected String[] getConfigLocations() {
		String[] result = new String[] { "beans/appContextTest.xml" };
		return result;
	}

	// Members
	protected ISWebServiceDistributionsSoap client;

}
