package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import ieci.tecdoc.core.base64.Base64Util;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.registropresencial.ws.server.ArrayOfDocument;
import ieci.tecdoc.sgm.registropresencial.ws.server.ArrayOfFieldInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.BookId;
import ieci.tecdoc.sgm.registropresencial.ws.server.Document;
import ieci.tecdoc.sgm.registropresencial.ws.server.Documents;
import ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.Fields;
import ieci.tecdoc.sgm.registropresencial.ws.server.Folder;
import ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebService;
import ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class ISWebServiceImportSoapTest extends AbstractDependencyInjectionSpringContextTests{

	
	protected String[] getConfigLocations() {
		String[] result = new String[] { "beans/appContextTest.xml" };
		return result;
	}
	
	public void testWsImportInputRegister() {
		ServicioRegistroWebService client = (ServicioRegistroWebService) this.applicationContext
		.getBean("clientISWebServiceImport");
		UserInfo user = createUser();
		List<PersonInfo> interesados = createInteresados();
		Folder folder= createFolderInput();
		Documents documents = getDocuments();
		folder.setDocumentos(documents);
		Entidad entidad = new Entidad();
		
		RegisterInfo result =client.importFolder(user, interesados, folder, entidad);
		assertNotNull(result.getFolderId());
		
		
	}
	
	public void testWsImportInputRegisterWithoutUser() {
		ServicioRegistroWebService client = (ServicioRegistroWebService) this.applicationContext
		.getBean("clientISWebServiceImport");
		
		List<PersonInfo> interesados = createInteresados();
		Folder folder= createFolderInput();
		Documents documents = getDocuments();
		folder.setDocumentos(documents);
		Entidad entidad = new Entidad();
		try{
		RegisterInfo result =client.importFolder(null, interesados, folder, entidad);
		}catch (Exception e) {
			Assert.assertEquals("No se puede importar un registro sin indicar el usuario", e.getMessage());
			e.toString();
		}
	
	}
	
	public void testWsImportInputRegisterWithWrongUser() {
		ServicioRegistroWebService client = (ServicioRegistroWebService) this.applicationContext
		.getBean("clientISWebServiceImport");
		UserInfo user = createUser();
		user.setUserName("wrongUser");
		List<PersonInfo> interesados = createInteresados();
		Folder folder= createFolderInput();
		Documents documents = getDocuments();
		folder.setDocumentos(documents);
		Entidad entidad = new Entidad();
		try{
		RegisterInfo result =client.importFolder(user, interesados, folder, entidad);
		}catch (Exception e) {
			Assert.assertEquals("No se ha podido loguear al usuario", e.getMessage());
			
		}
	
	}
	
	
	public void testWsImportInputRegisterWithoutFolder() {
		ServicioRegistroWebService client = (ServicioRegistroWebService) this.applicationContext
		.getBean("clientISWebServiceImport");
		UserInfo user = createUser();
		List<PersonInfo> interesados = createInteresados();
	
		Entidad entidad = new Entidad();
		try{
		RegisterInfo result = client.importFolder(user, interesados, null, entidad);
		}catch (Exception e) {
			Assert.assertEquals("No se puede importar un registro si no se indica el parametro Folder", e.getMessage());
		}
	}
	
	public void testWsImportInputRegisterRepeat() {
		ServicioRegistroWebService client = (ServicioRegistroWebService) this.applicationContext
		.getBean("clientISWebServiceImport");
		UserInfo user = createUser();
		List<PersonInfo> interesados = createInteresados();
		Folder folder= createFolderInput();
		Documents documents = getDocuments();
		folder.setDocumentos(documents);
		Entidad entidad = new Entidad();
		try{
		RegisterInfo result =client.importFolder(user, interesados, folder, entidad);
		}catch (Exception e) {
			Assert.assertEquals("No se ha podido crear el registro", e.getMessage());
		}
	}
	public void testWsImportInputRegisterInNotExistBook() {
		ServicioRegistroWebService client = (ServicioRegistroWebService) this.applicationContext
		.getBean("clientISWebServiceImport");
		UserInfo user = createUser();
		List<PersonInfo> interesados = createInteresados();
		Folder folder= createFolderInput();
		BookId bookNotExist = new BookId();
		bookNotExist.setBookId("9898");
		folder.setBookId(bookNotExist);
		Documents documents = getDocuments();
		folder.setDocumentos(documents);
		Entidad entidad = new Entidad();
		try{
		RegisterInfo result =client.importFolder(user, interesados, folder, entidad);
		}catch (Exception e) {
			Assert.assertEquals("Se ha producido un error al abrir el libro[9898]:Resulta imposible abrir el libro.", e.getMessage());
		}
	}
	
	public void testWsImportOutputRegister() {
		ServicioRegistroWebService client = (ServicioRegistroWebService) this.applicationContext
		.getBean("clientISWebServiceImport");
		UserInfo user = createUser();
		List<PersonInfo> interesados = createInteresados();
		Folder folder= createFolderOutput();
		Documents documents = getDocuments();
		folder.setDocumentos(documents);
		Entidad entidad = new Entidad();
		
		RegisterInfo result =client.importFolder(user, interesados, folder, entidad);
		Assert.assertNotNull(result.getFolderId());
	}
	
	public void testWsImportOutputRegisterWithoutUser() {
		ServicioRegistroWebService client = (ServicioRegistroWebService) this.applicationContext
		.getBean("clientISWebServiceImport");
		UserInfo user = createUser();
		List<PersonInfo> interesados = createInteresados();
		Folder folder= createFolderOutput();
		Documents documents = getDocuments();
		folder.setDocumentos(documents);
		Entidad entidad = new Entidad();
		try{
		RegisterInfo result =client.importFolder(null, interesados, folder, entidad);
		}catch (Exception e) {
			Assert.assertEquals("No se puede importar un registro sin indicar el usuario", e.getMessage());
		}
	}
	
	public void testWsImportOutputRegisterWithWrongUser() {
		ServicioRegistroWebService client = (ServicioRegistroWebService) this.applicationContext
		.getBean("clientISWebServiceImport");
		UserInfo user = createUser();
		user.setUserName("wrongUser");
		List<PersonInfo> interesados = createInteresados();
		Folder folder= createFolderOutput();
		Documents documents = getDocuments();
		folder.setDocumentos(documents);
		Entidad entidad = new Entidad();
		try{
		RegisterInfo result =client.importFolder(user, interesados, folder, entidad);
		}catch (Exception e) {
			Assert.assertEquals("No se ha podido loguear al usuario", e.getMessage());
		}
	}
	public void testWsImportOutputRegisterWithoutFolder() {
		ServicioRegistroWebService client = (ServicioRegistroWebService) this.applicationContext
		.getBean("clientISWebServiceImport");
		UserInfo user = createUser();
		user.setUserName("wrongUser");
		List<PersonInfo> interesados = createInteresados();
		Folder folder= createFolderOutput();
		Documents documents = getDocuments();
		folder.setDocumentos(documents);
		Entidad entidad = new Entidad();
		try{
		RegisterInfo result =client.importFolder(user, interesados, null, entidad);
		}catch (Exception e) {
			Assert.assertEquals("No se puede importar un registro si no se indica el parametro Folder", e.getMessage());
		}
	}
	private Documents getDocuments() {
		Documents documents = new Documents();
		ArrayOfDocument arrayOfDocuments = new ArrayOfDocument();
		Document document = new Document();
		document.setDocumentName("testFile.txt");
		document.setFileName("testFile");
		document.setExtension("txt");
		
		try{
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("\\testFile.txt");
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			byte[] temp = new byte[512];
			while (inputStream.read(temp) != -1) 
			{
				byteArrayOutputStream.write(temp);
			}
			document.setDocumentContentB64(Base64Util.encode(byteArrayOutputStream.toByteArray()));
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		arrayOfDocuments.getItem().add(document);
		documents.setDocuments(arrayOfDocuments);
		return documents;
	}

	private Folder createFolderInput() {
		Folder folder = new Folder();
		BookId book = new BookId();
		book.setBookId("1");
		folder.setBookId(book);
		folder.setFolderNumber("20110000000035");
		Fields fields =new Fields();
		ArrayOfFieldInfo arrayOfFields =new ArrayOfFieldInfo();
		FieldInfo fieldInfo1 = new FieldInfo();
		fieldInfo1.setFieldId("2");
		fieldInfo1.setValue("04-04-2011 10:10:12");
		FieldInfo fieldInfo2 = new FieldInfo();
		fieldInfo2.setFieldId("7");
		fieldInfo2.setValue("MAE4311");
		FieldInfo fieldInfo3 = new FieldInfo();
		fieldInfo3.setFieldId("8");
		fieldInfo3.setValue("MAE4323");
		FieldInfo fieldInfo4 = new FieldInfo();
		fieldInfo4.setFieldId("16");
		fieldInfo4.setValue("TLIC");
		arrayOfFields.getItem().add(fieldInfo1);
		arrayOfFields.getItem().add(fieldInfo2);
		arrayOfFields.getItem().add(fieldInfo3);
		arrayOfFields.getItem().add(fieldInfo4);
		fields.setFields(arrayOfFields);
		folder.setFields(fields);

		return folder;
	}

	private Folder createFolderOutput() {
		Folder folder = new Folder();
		BookId book = new BookId();
		book.setBookId("2");
		folder.setBookId(book);
		folder.setFolderNumber("20110010000035");
		Fields fields =new Fields();
		ArrayOfFieldInfo arrayOfFields =new ArrayOfFieldInfo();
		FieldInfo fieldInfo1 = new FieldInfo();
		fieldInfo1.setFieldId("2");
		fieldInfo1.setValue("04-04-2011 10:10:12");
		FieldInfo fieldInfo2 = new FieldInfo();
		fieldInfo2.setFieldId("7");
		fieldInfo2.setValue("MAE4311");
		FieldInfo fieldInfo3 = new FieldInfo();
		fieldInfo3.setFieldId("8");
		fieldInfo3.setValue("MAE4323");
		FieldInfo fieldInfo4 = new FieldInfo();
		fieldInfo4.setFieldId("12");
		fieldInfo4.setValue("TLIC");
		arrayOfFields.getItem().add(fieldInfo1);
		arrayOfFields.getItem().add(fieldInfo2);
		arrayOfFields.getItem().add(fieldInfo3);
		arrayOfFields.getItem().add(fieldInfo4);
		fields.setFields(arrayOfFields);
		folder.setFields(fields);

		return folder;
	}
	
	private List<PersonInfo> createInteresados() {
		List<PersonInfo> interesados= new ArrayList<PersonInfo>();
		PersonInfo person1 = new PersonInfo();
		person1.setPersonName("Nombre Apellidos");
		interesados.add(person1);
		return interesados;
	}

	private UserInfo createUser() {
		UserInfo user = new UserInfo();
		user.setAditionalOfic("OSE");
		user.setUserName("sigem");
		user.setPassword("sigem");
		
		return user;
	}
	
	
}
