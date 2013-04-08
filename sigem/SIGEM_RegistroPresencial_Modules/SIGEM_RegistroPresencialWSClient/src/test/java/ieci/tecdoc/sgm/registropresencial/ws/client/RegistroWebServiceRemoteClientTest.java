package ieci.tecdoc.sgm.registropresencial.ws.client;

import ieci.tecdoc.sgm.core.services.registro.DocumentQuery;
import ieci.tecdoc.sgm.core.services.registro.RegisterWithPagesInfo;
import ieci.tecdoc.sgm.core.services.registro.RegistroException;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebServiceServiceLocator;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;

import javax.xml.rpc.ServiceException;

import junit.framework.Assert;
import junit.framework.TestCase;

public class RegistroWebServiceRemoteClientTest extends TestCase {

	public void testAcceptDistribution() {
		RegistroWebServiceRemoteClient oClient = new RegistroWebServiceRemoteClient();
		ServicioRegistroWebServiceServiceLocator oLocator = new ServicioRegistroWebServiceServiceLocator();
		try {
			oClient.setService(oLocator.getServicioRegistroWebService());
		} catch (ServiceException e) {
			fail(e.getMessage());
		}
		ieci.tecdoc.sgm.core.services.registro.UserInfo oInfo = new ieci.tecdoc.sgm.core.services.registro.UserInfo();
		oInfo.setUserName("tramitador");
		oInfo.setPassword("sigem");
		oInfo.setLocale(new Locale("es"));
		String registerNumber = "200800100000013";
		Integer bookId = new Integer(6);
		try {
			ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
			entidad.setIdentificador("000");
			oClient.acceptDistribution(oInfo, registerNumber, bookId, entidad);
		} catch (RegistroException e) {
			fail(e.getMessage());
		}
		Assert.assertTrue(true);
	}

	public void testArchiveDistribution() {
		RegistroWebServiceRemoteClient oClient = new RegistroWebServiceRemoteClient();
		ServicioRegistroWebServiceServiceLocator oLocator = new ServicioRegistroWebServiceServiceLocator();
		try {
			oClient.setService(oLocator.getServicioRegistroWebService());
		} catch (ServiceException e) {
			fail(e.getMessage());
		}
		ieci.tecdoc.sgm.core.services.registro.UserInfo oInfo = new ieci.tecdoc.sgm.core.services.registro.UserInfo();
		oInfo.setUserName("tramitador");
		oInfo.setPassword("sigem");
		oInfo.setLocale(new Locale("es"));
		String registerNumber = "200800100000114";
		Integer bookId = new Integer(6);
		try {
			ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
			entidad.setIdentificador("001");
			oClient.archiveDistribution(oInfo, registerNumber, bookId, entidad);
		} catch (RegistroException e) {
			fail(e.getMessage());
		}
		Assert.assertTrue(true);
	}

	public void testChangeInputDistribution() {
		RegistroWebServiceRemoteClient oClient = new RegistroWebServiceRemoteClient();
		ServicioRegistroWebServiceServiceLocator oLocator = new ServicioRegistroWebServiceServiceLocator();
		try {
			oClient.setService(oLocator.getServicioRegistroWebService());
		} catch (ServiceException e) {
			fail(e.getMessage());
		}
		ieci.tecdoc.sgm.core.services.registro.UserInfo oInfo = new ieci.tecdoc.sgm.core.services.registro.UserInfo();
		oInfo.setUserName("DOS");
		oInfo.setPassword("DOS");
		oInfo.setLocale(new Locale("es"));
		String registerNumber = "200800100000125";
		String code = "002";
		try {
			ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
			entidad.setIdentificador("00001");
			oClient.changeInputDistribution(oInfo, registerNumber, code,
					entidad);
		} catch (RegistroException e) {
			fail(e.getMessage());
		}
		Assert.assertTrue(true);
	}

	public void testChangeOutputDistribution() {
		RegistroWebServiceRemoteClient oClient = new RegistroWebServiceRemoteClient();
		ServicioRegistroWebServiceServiceLocator oLocator = new ServicioRegistroWebServiceServiceLocator();
		try {
			oClient.setService(oLocator.getServicioRegistroWebService());
		} catch (ServiceException e) {
			fail(e.getMessage());
		}
		ieci.tecdoc.sgm.core.services.registro.UserInfo oInfo = new ieci.tecdoc.sgm.core.services.registro.UserInfo();
		oInfo.setUserName("sigem");
		oInfo.setPassword("sigem");
		oInfo.setLocale(new Locale("es"));
		String registerNumber = "200800100000125";
		Integer bookId = new Integer(5);
		String code = "004";
		try {
			ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
			entidad.setIdentificador("00001");
			oClient.changeOutputDistribution(oInfo, registerNumber, code,
					entidad);
		} catch (RegistroException e) {
			fail(e.getMessage());
		}
		Assert.assertTrue(true);
	}

	public void testCreateFolder() {
		RegistroWebServiceRemoteClient oClient = new RegistroWebServiceRemoteClient();
		ServicioRegistroWebServiceServiceLocator oLocator = new ServicioRegistroWebServiceServiceLocator();
		try {
			oClient.setService(oLocator.getServicioRegistroWebService());
		} catch (ServiceException e) {
			fail(e.getMessage());
		}
		ieci.tecdoc.sgm.core.services.registro.UserInfo oInfo = new ieci.tecdoc.sgm.core.services.registro.UserInfo();
		oInfo.setUserName("sigem");
		oInfo.setPassword("sigem");
		oInfo.setLocale(new Locale("es"));
		Integer bookId = new Integer(5);
		ieci.tecdoc.sgm.core.services.registro.FieldInfo[] atts = new ieci.tecdoc.sgm.core.services.registro.FieldInfo[5];
		ieci.tecdoc.sgm.core.services.registro.FieldInfo fieldInfo = null;
		fieldInfo = new ieci.tecdoc.sgm.core.services.registro.FieldInfo();
		fieldInfo.setFieldId("7");
		fieldInfo.setValue("A13");
		atts[0] = fieldInfo;
		fieldInfo = new ieci.tecdoc.sgm.core.services.registro.FieldInfo();
		fieldInfo.setFieldId("8");
		fieldInfo.setValue("004");
		atts[1] = fieldInfo;
		fieldInfo = new ieci.tecdoc.sgm.core.services.registro.FieldInfo();
		fieldInfo.setFieldId("16");
		fieldInfo.setValue("TLIC");
		atts[2] = fieldInfo;
		fieldInfo = new ieci.tecdoc.sgm.core.services.registro.FieldInfo();
		fieldInfo.setFieldId("17");
		fieldInfo.setValue("Resumen JUnit");
		atts[3] = fieldInfo;
		fieldInfo = new ieci.tecdoc.sgm.core.services.registro.FieldInfo();
		fieldInfo.setFieldId("9");
		fieldInfo.setValue("Remitente JUnit");
		atts[4] = fieldInfo;
		ieci.tecdoc.sgm.core.services.registro.PersonInfo[] inter = new ieci.tecdoc.sgm.core.services.registro.PersonInfo[1];
		ieci.tecdoc.sgm.core.services.registro.PersonInfo pInfo = null;
		pInfo = new ieci.tecdoc.sgm.core.services.registro.PersonInfo();
		pInfo.setPersonName("241324134W");
		inter[0] = pInfo;
		ieci.tecdoc.sgm.core.services.registro.RegisterInfo oReg = null;
		try {
			ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
			entidad.setIdentificador("00001");
			oReg = oClient.createFolder(oInfo, bookId, atts, inter, null,
					entidad);
		} catch (RegistroException e) {
			fail(e.getMessage());
		}
		Assert.assertTrue(true);
	}

	public void testImportFolder() {

		RegistroWebServiceRemoteClient oClient = new RegistroWebServiceRemoteClient();
		ServicioRegistroWebServiceServiceLocator oLocator = new ServicioRegistroWebServiceServiceLocator();
		try {
			oClient.setService(oLocator.getServicioRegistroWebService());
		} catch (ServiceException e) {
			fail(e.getMessage());
		}
		ieci.tecdoc.sgm.core.services.registro.UserInfo oInfo = new ieci.tecdoc.sgm.core.services.registro.UserInfo();
		oInfo.setUserName("tramitador");
		oInfo.setPassword("sigem");
		oInfo.setLocale(new Locale("es"));
		Integer bookId = new Integer(5);
		ieci.tecdoc.sgm.core.services.registro.FieldInfo[] atts = new ieci.tecdoc.sgm.core.services.registro.FieldInfo[9];
		ieci.tecdoc.sgm.core.services.registro.FieldInfo fieldInfo = null;
		fieldInfo = new ieci.tecdoc.sgm.core.services.registro.FieldInfo();
		fieldInfo.setFieldId("1");
		fieldInfo.setValue("06" + (long) (Math.random() * 9)
				+ (long) (Math.random() * 9) + (long) (Math.random() * 9)
				+ (long) (Math.random() * 9) + (long) (Math.random() * 9)
				+ (long) (Math.random() * 9) + (long) (Math.random() * 9)
				+ (long) (Math.random() * 9));
		atts[0] = fieldInfo;

		fieldInfo = new ieci.tecdoc.sgm.core.services.registro.FieldInfo();
		Date date = new Date();
		fieldInfo.setFieldId("2");
		fieldInfo.setValue("24-10-2007 00:00:00");
		atts[1] = fieldInfo;

		fieldInfo = new ieci.tecdoc.sgm.core.services.registro.FieldInfo();
		fieldInfo.setFieldId("3");
		fieldInfo.setValue("jesus");
		atts[2] = fieldInfo;

		fieldInfo = new ieci.tecdoc.sgm.core.services.registro.FieldInfo();
		fieldInfo.setFieldId("4");
		fieldInfo.setValue("24-10-2007");
		atts[3] = fieldInfo;

		fieldInfo = new ieci.tecdoc.sgm.core.services.registro.FieldInfo();
		fieldInfo.setFieldId("5");
		fieldInfo.setValue("001");
		atts[4] = fieldInfo;

		fieldInfo = new ieci.tecdoc.sgm.core.services.registro.FieldInfo();
		fieldInfo.setFieldId("6");
		fieldInfo.setValue("0");
		atts[5] = fieldInfo;

		fieldInfo = new ieci.tecdoc.sgm.core.services.registro.FieldInfo();
		fieldInfo.setFieldId("8");
		fieldInfo.setValue("002");
		atts[6] = fieldInfo;

		fieldInfo = new ieci.tecdoc.sgm.core.services.registro.FieldInfo();
		fieldInfo.setFieldId("16");
		fieldInfo.setValue("TLIC");
		atts[7] = fieldInfo;

		fieldInfo = new ieci.tecdoc.sgm.core.services.registro.FieldInfo();
		fieldInfo.setFieldId("19");
		fieldInfo.setValue("8845673");
		atts[8] = fieldInfo;

		ieci.tecdoc.sgm.core.services.registro.PersonInfo[] inter = new ieci.tecdoc.sgm.core.services.registro.PersonInfo[2];
		ieci.tecdoc.sgm.core.services.registro.PersonInfo pInfo = null;
		pInfo = new ieci.tecdoc.sgm.core.services.registro.PersonInfo();
		pInfo.setPersonName("241324134W");
		inter[0] = pInfo;
		pInfo = new ieci.tecdoc.sgm.core.services.registro.PersonInfo();
		pInfo.setPersonName("807432007W");
		inter[1] = pInfo;

		ieci.tecdoc.sgm.core.services.registro.DocumentInfo[] documents = new ieci.tecdoc.sgm.core.services.registro.DocumentInfo[2];
		ieci.tecdoc.sgm.core.services.registro.DocumentInfo docs = null;

		InputStream inputStream = null;
		ByteArrayOutputStream outputStream = null;
		byte[] buffer = null;
		 try {
			inputStream = new BufferedInputStream(new FileInputStream(
					"C:\\prueba.pdf"));
			outputStream = new ByteArrayOutputStream();
			copy(inputStream, outputStream);
			buffer = outputStream.toByteArray();
			docs = new ieci.tecdoc.sgm.core.services.registro.DocumentInfo();
			docs.setDocumentContent(buffer);
			docs.setDocumentName("prueba");
			docs.setExtension("pdf");
			docs.setFileName("C:\\prueba.pdf");
			docs.setPageName("PagPrueba.pdf");
			documents[0] = docs;

			inputStream = new BufferedInputStream(new FileInputStream(
					"C:\\prueba2.txt"));
			outputStream = new ByteArrayOutputStream();
			copy(inputStream, outputStream);
			buffer = outputStream.toByteArray();
			docs = new ieci.tecdoc.sgm.core.services.registro.DocumentInfo();
			docs.setDocumentContent(buffer);
			docs.setDocumentName("prueba2");
			docs.setExtension("txt");
			docs.setFileName("C:\\prueba2.txt");
			docs.setPageName("PagPrueba2.txt");
			documents[1] = docs;

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ieci.tecdoc.sgm.core.services.registro.RegisterInfo oReg = null;
		try {
			ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
			entidad.setIdentificador("00001");
			oReg = oClient.importFolder(oInfo, bookId, atts, inter, documents,
					entidad);
			assertEquals(oReg.getNumber(), atts[0].getValue());
		} catch (RegistroException e) {
			fail(e.getMessage());
		}
	}

	public void testGetDocumentFolder() {

		RegistroWebServiceRemoteClient oClient = new RegistroWebServiceRemoteClient();
		ServicioRegistroWebServiceServiceLocator oLocator = new ServicioRegistroWebServiceServiceLocator();
		try {
			oClient.setService(oLocator.getServicioRegistroWebService());
		} catch (ServiceException e) {
			fail(e.getMessage());
		}
		ieci.tecdoc.sgm.core.services.registro.UserInfo oInfo = new ieci.tecdoc.sgm.core.services.registro.UserInfo();
		oInfo.setUserName("tramitador");
		oInfo.setPassword("sigem");
		oInfo.setLocale(new Locale("es"));
		Integer bookId = new Integer(1);
		Integer folderId = new Integer(88);
		Integer docID = new Integer(275);
		Integer pageID = new Integer(1);
		DocumentQuery oDoc = null;
		try {
			ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
			entidad.setIdentificador("000");
			oDoc = oClient.getDocumentFolder(oInfo, bookId, folderId, docID,
					pageID, entidad);
		} catch (RegistroException e) {
			fail(e.getMessage());
		}
		Assert.assertTrue(true);
	}

	public void testGetOutputFolderForNumber() {

		RegistroWebServiceRemoteClient oClient = new RegistroWebServiceRemoteClient();
		ServicioRegistroWebServiceServiceLocator oLocator = new ServicioRegistroWebServiceServiceLocator();
		try {
			oClient.setService(oLocator.getServicioRegistroWebService());
		} catch (ServiceException e) {
			fail(e.getMessage());
		}
		ieci.tecdoc.sgm.core.services.registro.UserInfo oInfo = new ieci.tecdoc.sgm.core.services.registro.UserInfo();
		oInfo.setUserName("sigem");
		oInfo.setPassword("sigem");
		oInfo.setLocale(new Locale("es"));
		RegisterWithPagesInfo oregister = null;
		try {
			ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
			entidad.setIdentificador("000");
			oregister = oClient.getOutputFolderForNumber(oInfo, new Integer(0), "200700100000001", entidad);
		} catch (RegistroException e) {
			fail(e.getMessage());
		}
		Assert.assertTrue(true);
	}

	public void testGetInputFolderForNumber() {

		RegistroWebServiceRemoteClient oClient = new RegistroWebServiceRemoteClient();
		ServicioRegistroWebServiceServiceLocator oLocator = new ServicioRegistroWebServiceServiceLocator();
		try {
			oClient.setService(oLocator.getServicioRegistroWebService());
		} catch (ServiceException e) {
			fail(e.getMessage());
		}
		ieci.tecdoc.sgm.core.services.registro.UserInfo oInfo = new ieci.tecdoc.sgm.core.services.registro.UserInfo();
		oInfo.setUserName("sigem");
		oInfo.setPassword("sigem");
		oInfo.setLocale(new Locale("es"));
		RegisterWithPagesInfo oregister = null;
		try {
			ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
			entidad.setIdentificador("000");
			oregister = oClient.getInputFolderForNumber(oInfo, new Integer(0), "200700100000001", entidad);
		} catch (RegistroException e) {
			fail(e.getMessage());
		}
		Assert.assertTrue(true);
	}

	public void testGetInputDistribution() {
		RegistroWebServiceRemoteClient oClient = new RegistroWebServiceRemoteClient();
		ServicioRegistroWebServiceServiceLocator oLocator = new ServicioRegistroWebServiceServiceLocator();
		try {
			oClient.setService(oLocator.getServicioRegistroWebService());
		} catch (ServiceException e) {
			fail(e.getMessage());
		}
		ieci.tecdoc.sgm.core.services.registro.UserInfo oInfo = new ieci.tecdoc.sgm.core.services.registro.UserInfo();
		oInfo.setUserName("sigem");
		oInfo.setPassword("sigem");
		oInfo.setLocale(new Locale("es"));
		ieci.tecdoc.sgm.core.services.registro.DistributionInfo[] oDistr = null;
		try {
			ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
			entidad.setIdentificador("00001");
			oDistr = oClient.getInputDistribution(oInfo, new Integer(1),
					new Integer(0), new Integer(10), new Integer(0),
					Boolean.TRUE, entidad);
		} catch (RegistroException e) {
			fail(e.getMessage());
		}
		Assert.assertTrue(true);
	}

	public void testGetOutputDistribution() {

		RegistroWebServiceRemoteClient oClient = new RegistroWebServiceRemoteClient();
		ServicioRegistroWebServiceServiceLocator oLocator = new ServicioRegistroWebServiceServiceLocator();
		try {
			oClient.setService(oLocator.getServicioRegistroWebService());
		} catch (ServiceException e) {
			fail(e.getMessage());
		}
		ieci.tecdoc.sgm.core.services.registro.UserInfo oInfo = new ieci.tecdoc.sgm.core.services.registro.UserInfo();
		oInfo.setUserName("sigem");
		oInfo.setPassword("sigem");
		oInfo.setLocale(new Locale("es"));
		ieci.tecdoc.sgm.core.services.registro.DistributionInfo[] oDistr = null;
		try {
			ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
			entidad.setIdentificador("00001");
			oDistr = oClient.getOutputDistribution(oInfo, new Integer(1),
					new Integer(0), new Integer(10), new Integer(0),
					Boolean.TRUE, entidad);
		} catch (RegistroException e) {
			fail(e.getMessage());
		}
		Assert.assertTrue(true);
	}

	public void testRejectDistribution() {

		RegistroWebServiceRemoteClient oClient = new RegistroWebServiceRemoteClient();
		ServicioRegistroWebServiceServiceLocator oLocator = new ServicioRegistroWebServiceServiceLocator();
		try {
			oClient.setService(oLocator.getServicioRegistroWebService());
		} catch (ServiceException e) {
			fail(e.getMessage());
		}
		ieci.tecdoc.sgm.core.services.registro.UserInfo oInfo = new ieci.tecdoc.sgm.core.services.registro.UserInfo();
		oInfo.setUserName("DOS");
		oInfo.setPassword("DOS");
		oInfo.setLocale(new Locale("es"));
		String registerNumber = "200800100000109";
		String remarks = "Estoy haciendo pruebas";
		try {
			ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
			entidad.setIdentificador("00001");
			oClient.rejectDistribution(oInfo, registerNumber, remarks, entidad);
		} catch (RegistroException e) {
			fail(e.getMessage());
		}
		Assert.assertTrue(true);
	}


	public void testExistMatterTypeInOffice(){
		RegistroWebServiceRemoteClient oClient = new RegistroWebServiceRemoteClient();
		ServicioRegistroWebServiceServiceLocator oLocator = new ServicioRegistroWebServiceServiceLocator();
		try {
			oClient.setService(oLocator.getServicioRegistroWebService());
		} catch (ServiceException e) {
			fail(e.getMessage());
		}

		ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
		entidad.setIdentificador("000");
		ieci.tecdoc.sgm.core.services.registro.UserInfo oInfo = new ieci.tecdoc.sgm.core.services.registro.UserInfo();
		oInfo.setUserName("sigem");
		oInfo.setPassword("sigem");
		oInfo.setLocale(new Locale("es"));

		//TIPO DE ASUNTO VALIDO
		try {
			String matterTypeCode = "TLIC";
			String officeCode = "001";
			Assert.assertTrue(oClient.existMatterTypeInOffice(oInfo, matterTypeCode, officeCode, entidad));
		} catch (RegistroException e) {
			fail(e.getMessage());
		}


		//TIPO DE ASUNTO INEXISTENTE
		try {
			String matterTypeCode = "TLIC1";
			String officeCode = "001";
			Assert.assertFalse(oClient.existMatterTypeInOffice(oInfo, matterTypeCode, officeCode, entidad));
		} catch (RegistroException e) {
			fail(e.getMessage());
		}

		//USUARIO INCORRECTO
		try {
			String matterTypeCode = "TLIC";
			String officeCode = "001";
			oInfo.setUserName("sigem1");

			oClient.existMatterTypeInOffice(oInfo, matterTypeCode, officeCode, entidad);
			fail("No se ha lanzado la excepción de usuario inválido");
		} catch (RegistroException e1) {
			Assert.assertEquals(2100010054L, e1.getErrorCode());
		}
	}


//	public void testGetService() {
//		fail("Not yet implemented");
//	}
//
//	public void testSetService() {
//		fail("Not yet implemented");
//	}

	private void copy(InputStream in, OutputStream out) throws IOException {
		try {
			int c;
			byte[] buffer = new byte[1024];
			while ((c = in.read(buffer)) != -1) {
				out.write(buffer, 0, c);
				out.flush();
			}
		} finally {
			close(in);
			close(out);
		}
	}

	private void close(OutputStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e1) {
				// Ignored
			}
		}
	}

	private void close(InputStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e1) {
				// Ignored
			}
		}
	}

}
