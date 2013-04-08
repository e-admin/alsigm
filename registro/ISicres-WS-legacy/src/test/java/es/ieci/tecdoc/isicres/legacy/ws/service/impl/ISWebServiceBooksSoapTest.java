package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import es.ieci.tecdoc.isicres.legacy.ws.constants.WSFieldTypeConstants;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.ArrayOfWSBook;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.ArrayOfWSField;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.ISWebServiceBooksSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.UsernameTokenClass;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.WSBook;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.WSField;

/**
 * Pruebas de integración para verificar el correcto funcionamiento del WS con
 * interfaz <code>ISWebServiceBooksSoap</code>.
 * 
 * @see ISWebServiceBooksSoap
 * 
 * @author IECISA
 * 
 */
public class ISWebServiceBooksSoapTest extends
		AbstractDependencyInjectionSpringContextTests {

	protected String[] getConfigLocations() {
		String[] result = new String[] { "beans/appContextTest.xml" };
		return result;
	}

	
	public void testLogin(){
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		
		//SIGEM - Oficina 001
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);
		int bookId = 1;
		ArrayOfWSField result = client.wsGetBookSchema(bookId, security);
		assertNotNull(result);
	}
	
	
	public void testLoginUserWithoutOffice(){
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		
		//SIGEM - Oficina 001
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		//value.setOfficeCode("001");
		security.setUsernameToken(value);
		int bookId = 1;
		ArrayOfWSField result = client.wsGetBookSchema(bookId, security);
		assertNotNull(result);
		
		//Operador 1 - Oficina 001
		security = new Security();
		value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		//value.setOfficeCode("001");
		security.setUsernameToken(value);

		ArrayOfWSField wsGetBookSchema = client.wsGetBookSchema(1, security);
		assertNotNull(wsGetBookSchema);

		wsGetBookSchema = client.wsGetBookSchema(2, security);
		assertNotNull(wsGetBookSchema);
		
	}
	
	public void testLoginUserWithNoOfficesAssigned(){
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		

		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("usuariosinoficina");
		value.setPassword("sigem");
		//value.setOfficeCode("TRA");
		//value.setOfficeCode("001");
		security.setUsernameToken(value);
		int bookId = 1;
		ArrayOfWSField result = client.wsGetBookSchema(bookId, security);
		assertNotNull(result);					
		
	}
	
	public void testLoginUserWithOffice(){
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		
		//SIGEM - Oficina 001
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);
		int bookId = 1;
		ArrayOfWSField result = client.wsGetBookSchema(bookId, security);
		assertNotNull(result);
		
		//Operador 1 - Oficina 001
		security = new Security();
		value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("999");
		security.setUsernameToken(value);

		ArrayOfWSField wsGetBookSchema = client.wsGetBookSchema(1, security);
		assertNotNull(wsGetBookSchema);

		wsGetBookSchema = client.wsGetBookSchema(2, security);
		assertNotNull(wsGetBookSchema);
	}
	/**
	 * Verifica que el usuario <i>sigem</i> autenticado en la oficina con codigo
	 * <i>001</id> puede recuperar la definición del libro con identificador
	 * <i>1</i>.
	 */
	public void testWsGetBookSchemaForBookWithId1() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		//value.setOfficeCode("001");
		security.setUsernameToken(value);

		int bookId = 1;
		ArrayOfWSField result = client.wsGetBookSchema(bookId, security);
		assertNotNull(result);
		int fields = 20;
		assertEquals(fields, result.getWSField().size());
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label",
						"Número de registro")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label",
						"Fecha de registro")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label", "Usuario")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label",
						"Fecha de trabajo")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label",
						"Oficina de registro")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label", "Estado")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label", "Origen")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label", "Destino")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label", "Remitentes")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label",
						"Nº. registro original")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label",
						"Tipo de registro original")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label",
						"Fecha de registro original")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label",
						"Registro original")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label",
						"Tipo de transporte")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label", "Tipo de asunto")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label", "Resumen")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label", "Comentario")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label",
						"Referencia de Expediente")));
		assertNotNull(CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label",
						"Fecha del documento")));
		// Check for a detail
		WSField wsField = (WSField) CollectionUtils.find(result.getWSField(),
				new BeanPropertyValueEqualsPredicate("label",
						"Registro original"));
		assertNotNull(wsField);
		assertEquals("Fld13", wsField.getName());
		assertEquals(13, wsField.getId());
		assertEquals(0, wsField.getLength());

		System.out.println("Types: ");
		for (WSField wsFieldIterator : result.getWSField()) {
			
			System.out.println(wsFieldIterator.getLabel() + ": " + wsFieldIterator.getType());
		}

		/*
		 * TODO: La propiedad Type de la clase WSField, en plataforma Microsoft
		 * devuelve valores como char, datetime, number, etc. en la plataforma
		 * JAVA se devuelve valor numérico que suponemos es la equivalencia.
		 */
		assertEquals(WSFieldTypeConstants.TYPE_NUMBER, wsField.getType());
		assertTrue(wsField.isHasValidation());
	}

	/**
	 * Verifica que el usuario <i>operador1</i> autenticado en la oficina con
	 * codigo <i>001</id> no puede recuperar la definición del libro con
	 * identificador <i>5</i> por no disponer de suficientes permisos.
	 */
	public void testWsGetBookSchemaOperador1UnsufficientPermissions() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		try {
			client.wsGetBookSchema(5, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Verifica que el usuario <i>operador1</i> autenticado en la oficina con
	 * codigo <i>001</id> puede recuperar la definición de los libros con
	 * identificadores <i>1</i> y <i>2</i>.
	 */
	public void testWsGetBookSchemaOperador1() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		ArrayOfWSField wsGetBookSchema = client.wsGetBookSchema(1, security);
		assertNotNull(wsGetBookSchema);

		wsGetBookSchema = client.wsGetBookSchema(2, security);
		assertNotNull(wsGetBookSchema);
	}

	/**
	 * Verifica que el usuario <i>operador2</i> autenticado en la oficina con
	 * codigo <i>999</id> no puede recuperar la definición del libro con
	 * identificador <i>7</i> porque no tiene permisos suficientes (no los tiene
	 * ni el usuario ni la oficina).
	 */
	public void testWsGetBookSchemaOperador2() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador2");
		value.setPassword("operador2");
		value.setOfficeCode("999");
		security.setUsernameToken(value);

		try {
			client.wsGetBookSchema(7, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Verifica que el usuario <code>distribuidor2</code> autenticado en la
	 * oficina con código <i>003</i> puede recuperar la definición del libro con
	 * identificador <i>6</i>.
	 */
	public void testWsGetBookSchemaDistribuidor2() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor2");
		value.setPassword("distribuidor2");
		value.setOfficeCode("003");
		security.setUsernameToken(value);

		ArrayOfWSField wsGetBookSchema = client.wsGetBookSchema(6, security);
		assertNotNull(wsGetBookSchema);
		assertEquals(15, wsGetBookSchema.getWSField().size());

	}

	/**
	 * Verifica que el usuario <i>operador2</i> autenticado en la oficina con
	 * codigo <i>003</id> no puede recuperar la definición del libro con
	 * identificador <i>1</i> por no disponer de suficientes permisos.
	 */
	public void testWsGetBookSchemaOperador2UnsufficientPermissions() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador2");
		value.setPassword("operador2");
		value.setOfficeCode("003");
		security.setUsernameToken(value);

		try {
			client.wsGetBookSchema(1, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();

	}

	/**
	 * Verifica que el usuario <i>sigem</i> autenticado en la oficina con código
	 * <i>001</i> puede recuperar la definición de cualquiera de los libros
	 * existentes.
	 */
	public void testWsGetBookSchemaSigemAnyBookSchema() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		// book with id=1 (office code = 001)
		ArrayOfWSField wsGetBookSchema = client.wsGetBookSchema(1, security);
		assertNotNull(wsGetBookSchema);

		// book with id=2 (office code = 001)
		wsGetBookSchema = client.wsGetBookSchema(2, security);
		assertNotNull(wsGetBookSchema);

		// book with id=5 (office code = 003)
		security.getUsernameToken().setOfficeCode("003");
		wsGetBookSchema = client.wsGetBookSchema(5, security);
		assertNotNull(wsGetBookSchema);

		// book with id=6 (office code = 003)
		wsGetBookSchema = client.wsGetBookSchema(6, security);
		assertNotNull(wsGetBookSchema);

		// book with id=7 (office code = 999)
		security.getUsernameToken().setOfficeCode("999");
		wsGetBookSchema = client.wsGetBookSchema(7, security);
		assertNotNull(wsGetBookSchema);
	}

	/**
	 * Verifica que el usuario <i>operador1</i> autenticado en la oficina
	 * <i>001</i> tiene acceso al libro de entrada etiquetado como <i>Libro de
	 * Entrada</i>.
	 */
	public void testGetInputBooksOperador1() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		ArrayOfWSBook result = client.wsGetInputBooks(security);
		assertNotNull(result);
		assertEquals(1, result.getWSBook().size());
		WSBook wsBook_1 = (WSBook) CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 1));
		assertNotNull(wsBook_1);

		// Check book details
		assertEquals(1, wsBook_1.getId());
		assertEquals("Libro de Entrada", wsBook_1.getName());
		assertEquals(1, wsBook_1.getType());
		assertTrue(wsBook_1.isCanCreate());
		assertTrue(wsBook_1.isCanModify());
		assertFalse(wsBook_1.isReadOnly());
	}

	/**
	 * Verifica que el usuario <i>operador1</i> autenticado en la oficina
	 * <i>001</i> tiene acceso al libro de salida etiquetado como <i>Libro de
	 * Salida</i>.
	 */
	public void testGetOutputBooksOperador1() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		ArrayOfWSBook result = client.wsGetOutputBooks(security);
		assertNotNull(result);
		assertEquals(1, result.getWSBook().size());
		WSBook wsBook = (WSBook) CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 2));
		assertNotNull(wsBook);

		// check book details
		assertEquals(2, wsBook.getId());
		assertEquals("Libro de Salida", wsBook.getName());
		assertEquals(2, wsBook.getType());
		assertTrue(wsBook.isCanCreate());
		assertTrue(wsBook.isCanModify());
		assertFalse(wsBook.isReadOnly());

	}

	/**
	 * Verifica que el usuario <i>operador2</i> autenticado en la oficina
	 * <i>999</i> no tiene acceso a ningún libro de entrada.
	 */
	public void testGetInputBooksOperador2() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador2");
		value.setPassword("operador2");
		value.setOfficeCode("999");
		security.setUsernameToken(value);

		ArrayOfWSBook result = client.wsGetInputBooks(security);
		assertNotNull(result);
		assertEquals(0, result.getWSBook().size());
	}

	/**
	 * Verifica que el usuario <i>operador2</i> autenticado en la oficina
	 * <i>999</i> no tiene acceso a ningún libro de salida.
	 */
	public void testGetOutputBooksOperador2() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador2");
		value.setPassword("operador2");
		value.setOfficeCode("999");
		security.setUsernameToken(value);

		ArrayOfWSBook result = client.wsGetOutputBooks(security);
		assertNotNull(result);
		assertEquals(0, result.getWSBook().size());

	}

	/**
	 * Verifica que el usuario <i>distribuidor1</i> autenticado en la oficina
	 * <i>001</i> tiene acceso al libro de entrada etiquetado como <i>Libro de
	 * entrada</i>. Si se autentica en la oficina con código <i>003</i> el libro
	 * de entrada al que tiene acceso es el etiquetado como <i>Libro de entrada
	 * auxiliar</i>.
	 */
	public void testGetInputBooksDistribuidor1() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor1");
		value.setPassword("distribuidor1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		ArrayOfWSBook result = client.wsGetInputBooks(security);
		assertNotNull(result);
		assertEquals(1, result.getWSBook().size());
		WSBook wsBook = (WSBook) CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 1));

		// check book details
		assertEquals(1, wsBook.getId());
		assertEquals("Libro de Entrada", wsBook.getName());
		assertEquals(1, wsBook.getType());
		assertTrue(wsBook.isCanCreate());
		assertTrue(wsBook.isCanModify());
		assertFalse(wsBook.isReadOnly());
	}

	/**
	 * Verifica que el usuario <i>distribuidor1</i> autenticado en la oficina
	 * <i>001</i> tiene acceso al libro de salida etiquetado como <i>Libro de
	 * Salida</i>.
	 */
	public void testGetOutputBooksDistribuidor1() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor1");
		value.setPassword("distribuidor1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		ArrayOfWSBook result = client.wsGetOutputBooks(security);
		assertNotNull(result);
		assertEquals(1, result.getWSBook().size());

		WSBook wsBook = (WSBook) CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 2));
		assertEquals(2, wsBook.getId());
		assertEquals("Libro de Salida", wsBook.getName());
		assertEquals(2, wsBook.getType());
		assertTrue(wsBook.isCanCreate());
		assertTrue(wsBook.isCanModify());
		assertFalse(wsBook.isReadOnly());
	}

	/**
	 * Verifica que el usuario <i>distribuidor2</i> autenticado en la oficina
	 * <i>003</i> tiene acceso al libro de entrada etiquetado como <i>Libro de
	 * entrada auxiliar</i>.
	 */
	public void testGetInputBooksDistribuidor2() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor2");
		value.setPassword("distribuidor2");
		value.setOfficeCode("003");
		security.setUsernameToken(value);

		ArrayOfWSBook result = client.wsGetInputBooks(security);
		assertNotNull(result);
		assertEquals(1, result.getWSBook().size());

		WSBook wsBook = (WSBook) CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 5));
		assertEquals(5, wsBook.getId());
		assertEquals("Libro de entrada auxiliar", wsBook.getName());
		assertEquals(1, wsBook.getType());
		assertTrue(wsBook.isCanCreate());
		assertTrue(wsBook.isCanModify());
		assertFalse(wsBook.isReadOnly());
	}

	/**
	 * Verifica que el usuario <i>distribuidor2</i> autenticado en la oficina
	 * <i>003</i> tiene acceso al libro de salida etiquetado como <i>Libro de
	 * salida auxiliar</i>.
	 */
	public void testGetOutputBooksDistribuidor2() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("distribuidor2");
		value.setPassword("distribuidor2");
		value.setOfficeCode("003");
		security.setUsernameToken(value);

		ArrayOfWSBook result = client.wsGetOutputBooks(security);
		assertNotNull(result);
		assertEquals(1, result.getWSBook().size());

		WSBook wsBook = (WSBook) CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 6));
		assertEquals(6, wsBook.getId());
		assertEquals("Libro de salida auxiliar", wsBook.getName());
		assertEquals(2, wsBook.getType());
		assertTrue(wsBook.isCanCreate());
		assertTrue(wsBook.isCanModify());
		assertFalse(wsBook.isReadOnly());

	}

	/**
	 * Verifica la recuperación de los libros de entrada que puede obtener el
	 * usuario <i>sigem</i> con perfil <i>superusuario</i>. Al tener este perfil
	 * no se tiene en cuenta la oficina con la que nos conectamos y recibe
	 * siempre todos los libros de entrada.
	 */
	public void testGetInputBooksSigem() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		// Input books from office with code 001
		ArrayOfWSBook result = client.wsGetInputBooks(security);
		assertNotNull(result);
		assertEquals(3, result.getWSBook().size());
		assertNotNull(CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 1)));
		assertNotNull(CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 5)));
		assertNotNull(CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 7)));

		// Input books from office with code 003
		security.getUsernameToken().setOfficeCode("003");
		result = client.wsGetInputBooks(security);
		assertNotNull(result);
		assertEquals(3, result.getWSBook().size());
		assertNotNull(CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 1)));
		assertNotNull(CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 5)));
		assertNotNull(CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 7)));

		// Input books from office with code 999
		security.getUsernameToken().setOfficeCode("999");
		result = client.wsGetInputBooks(security);
		assertNotNull(result);
		assertEquals(3, result.getWSBook().size());
		assertNotNull(CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 1)));
		assertNotNull(CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 5)));
		assertNotNull(CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 7)));

	}

	/**
	 * Verifica la recuperación de los libros de salida que puede obtener el
	 * usuario <i>sigem</i> con perfil <i>superusuario</i>. Al tener este perfil
	 * no se tiene en cuenta la oficina con la que nos conectamos y recibe
	 * siempre todos los libros de salida.
	 */
	public void testGetOutputBooksSigem() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		// Input books from office with code 001
		ArrayOfWSBook result = client.wsGetOutputBooks(security);
		assertNotNull(result);
		assertEquals(2, result.getWSBook().size());
		assertNotNull(CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 2)));
		assertNotNull(CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 6)));

		// Input books from office with code 003
		security.getUsernameToken().setOfficeCode("003");
		result = client.wsGetOutputBooks(security);
		assertNotNull(result);
		assertEquals(2, result.getWSBook().size());
		assertNotNull(CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 2)));
		assertNotNull(CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 6)));

		// Input books from office with code 999
		security.getUsernameToken().setOfficeCode("999");
		result = client.wsGetOutputBooks(security);
		assertNotNull(result);
		assertEquals(2, result.getWSBook().size());
		assertNotNull(CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 2)));
		assertNotNull(CollectionUtils.find(result.getWSBook(),
				new BeanPropertyValueEqualsPredicate("id", 6)));

	}
	
	/**
	 * Verifica que si un libro está cerrado no se puede modificar, aunque lo obtenga un superusuario.
	 * 
	 * Se prueba con el usuario <i>sigem</i> con perfil <i>superusuario</i>. 
	 * 
	 */
	public void testPermissionsClosedInputBooksSigem() {
		ISWebServiceBooksSoap client = (ISWebServiceBooksSoap) this.applicationContext
				.getBean("clientWebServiceBooks");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		
		security.setUsernameToken(value);

		// Input books from office with code 001
		ArrayOfWSBook inputBooks = client.wsGetInputBooks(security);
		assertNotNull(inputBooks);
		

		for (WSBook wsBook:inputBooks.getWSBook()){
			System.out.println(wsBook.getName()+" - "+wsBook.getId());
			System.out.println("Can Create: " + wsBook.isCanCreate());
			System.out.println("Can Modify: " + wsBook.isCanModify());
			System.out.println("Is Readonly: "+ wsBook.isReadOnly());
			if (wsBook.isReadOnly()){
				assertEquals(wsBook.isCanModify(),false);
				assertEquals(wsBook.isCanCreate(),false);
			}
			//El libro 5 está cerrado
			if (wsBook.getId()==5){
				assertFalse(wsBook.isCanModify());
				assertFalse(wsBook.isCanCreate());
				assertTrue(wsBook.isReadOnly());
			}
		}
		
		
		ArrayOfWSBook outputBooks = client.wsGetOutputBooks(security);
		assertNotNull(outputBooks);
		

		for (WSBook wsBook:outputBooks.getWSBook()){
			System.out.println(wsBook.getName()+" - "+wsBook.getId());
			System.out.println("Can Create: " + wsBook.isCanCreate());
			System.out.println("Can Modify: " + wsBook.isCanModify());
			System.out.println("Is Readonly: "+ wsBook.isReadOnly());
		
			if (wsBook.isReadOnly()){				
				assertEquals(wsBook.isCanModify(),false);
				assertEquals(wsBook.isCanCreate(),false);
			}
		}
		
		
	}
}
