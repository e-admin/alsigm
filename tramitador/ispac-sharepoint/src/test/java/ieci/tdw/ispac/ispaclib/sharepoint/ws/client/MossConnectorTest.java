package ieci.tdw.ispac.ispaclib.sharepoint.ws.client;

import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.sharepoint.gendoc.moss.MossConnector;
import ieci.tdw.ispac.ispaclib.sharepoint.gendoc.moss.MossConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import junit.framework.TestCase;

public class MossConnectorTest extends TestCase {

	private ClientContext context = null;

	public void setUp() throws Exception {

		Connection cnt = null;

		// SQLServer
		String driverClassName ="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://IP:1433;database=BD";
		String username = "";
		String password = "";

		try {
			Class.forName(driverClassName);
			cnt = DriverManager.getConnection(url, username, password);
			DbCnt dbCnt = new DbCnt();
			dbCnt.setConnection(cnt);
			context = new ClientContext(dbCnt);
		} catch (Exception e) {
		}
	}

	// private final String inputXML =
	// "<?xml version=\"1.0\" encoding=\"UTF-8\"?><properties xmlns=\"http://ASINA.AsientoRegistro\"><documento id=\'f9a5a408-ed45-4b77-a485-2d94c2a864c0\'><tipo>n</tipo><nombre>Anexo1.doc</nombre><extension>doc</extension></documento><datosRegistro><NumeroRegistro>1000</NumeroRegistro><FechaPresentacion>01/01/2009</FechaPresentacion><HoraPresentacion>10:00:00</HoraPresentacion><FechaEfectiva>02/01/2009</FechaEfectiva><HoraEfectiva>11:00:00</HoraEfectiva></datosRegistro><tramite id=\'n\'><name></name></tramite><solicitante><identificacion>33333333T</identificacion><nombre>Pedro</nombre></solicitante><mimetype></mimetype></properties>";
	// private final String inputXML =
	// "<?xml version=\"1.0\" encoding=\"UTF-8\"?><properties><documento id=\'f9a5a408-ed45-4b77-a485-2d94c2a864c0\'><tipo>n</tipo><nombre>"
	// +
	// "_docTRES.doc" +
	// "</nombre><extension>doc</extension></documento><datosRegistro><NumeroRegistro>1000</NumeroRegistro><FechaPresentacion>01/01/2009</FechaPresentacion><HoraPresentacion>10:00:00</HoraPresentacion><FechaEfectiva>02/01/2009</FechaEfectiva><HoraEfectiva>11:00:00</HoraEfectiva></datosRegistro><tramite id=\'n\'><name></name></tramite><solicitante><identificacion>33333333T</identificacion><nombre>Pedro</nombre></solicitante><mimetype></mimetype></properties>";
	String guid = "{C80207CD-00AD-46F2-803A-E1C06F544989}";

	private final String inputXML = "<?xml version='1.0' encoding='ISO-8859-1'?><doc_properties><property><name>document_id</name><value>113</value></property><property><name>document_type</name><value>2</value></property><property><name>document_name</name><value><![CDATA[Pruebas2.doc]]></value></property><property><name>procedure_id</name><value>3</value></property><property><name>procedure_name</name><value><![CDATA[Reclamaciones, quejas y sugerencias]]></value></property><property><name>expedient_id</name><value>1</value></property><property><name>expedient_name</name><value><![CDATA[EXP2009/000001]]></value></property><property><name>user_guid</name><value>1-4</value></property><property><name>user_name</name><value><![CDATA[1-4]]></value></property><property><name>stage_id</name><value>6</value></property><property><name>stage_name</name><value><![CDATA[Fase Inicio]]></value></property><property><name>task_id</name><value>5</value></property><property><name>task_name</name><value><![CDATA[Admisión a trámite]]></value></property><property><name>mimetype</name><value><![CDATA[image/x-windows-bmp]]></value></property><property><name>expedient_initdate_year</name><value>2011</value></property><property><name>expedient_initdate_month</name><value>03</value></property><property><name>expedient_initdate_day</name><value>23</value></property></doc_properties>";

	// public final void testNewUpdateDocument() {
	// String guid = null;
	// try {
	// MossConnector mossConnector = new MossConnector();
	// InputStream in = new FileInputStream("D:/datos/temp/_docUNO.doc");
	// guid = mossConnector.newDocument(null, in, 19968,
	// "<?xml version=\"1.0\" encoding=\"UTF-8\"?><properties><documento><nombre>UNO.doc</nombre></documento></properties>");
	// in.close();
	//
	// in = new FileInputStream("D:/datos/temp/_docDOS.doc");
	// guid = mossConnector.updateDocument(null, guid, in, 28672,
	// "<?xml version=\"1.0\" encoding=\"UTF-8\"?><properties><documento><nombre>DOS.doc</nombre></documento></properties>");
	// in.close();
	//
	// // in = new FileInputStream("D:/datos/temp/_docExcel.xls");
	// // guid = mossConnector.updateDocument(null, guid, in, 13824,
	// "<?xml version=\"1.0\" encoding=\"UTF-8\"?><properties><documento><nombre>EXCEL.xls</nombre></documento></properties>");
	// // in.close();
	//
	// //mossConnector.deleteDocument(null, guid);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// assertNotNull(guid);
	// }

	// public final void testReadConfig() {
	// try{
	// MossConnector mossConnector = MossConnector.getInstance();
	// }catch(Exception e){
	// }
	// }

	public final void testNewDocument() {
		String guid = null;
		try {
			// MossConnector mossConnector = new MossConnector();

			InputStream in = new FileInputStream("D:/tmp/Pruebas.doc");
 			guid = MossConnector.getInstance(context).newDocument(null, in, 26112,
					inputXML);

			in.close();
			// mossConnector.deleteDocument(null, guid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(guid);
	}

	 public final void testUpdateDocument(){
		 try {
			 MossConnector mossConnector = MossConnector.getInstance(context);
			 // InputStream in = new FileInputStream("D:/datos/temp/_docDOS.doc");
			 // mossConnector.setDocument(null, guid, in, 28672, inputXML);

			 InputStream in = new FileInputStream("D:/tmp/Pruebas2.doc");
			 mossConnector.updateDocument(null, guid, in, 26112, inputXML);

			 //InputStream in = new FileInputStream("D:/tmp/Pruebas3.pdf");
			 //mossConnector.updateDocument(null, guid, in, 12313, inputXML);

			 in.close();
			 //mossConnector.deleteDocument(null, guid);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 assertNotNull(guid);
	 }

	 public final void testGetDocument() {
		 FileOutputStream out = null;
		 try {
			 File f = File.createTempFile("ispac", null);
			 f.deleteOnExit();
			 out = new FileOutputStream(f);
			 Object session = null;
			 MossConnector mossConnector = MossConnector.getInstance(context);
			 mossConnector.getDocument(session, guid, out);
			 out.close();
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 assertNotNull(out);
	 }

	 public final void testExistsDocument() {
		 boolean result = false;
		 try {
			 MossConnector mossConnector = MossConnector.getInstance(context);
			 result = mossConnector.existsDocument(null, guid);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 assertTrue(result);
	 }

	 public final void testGetDocumentSize() {
		 int result = 0;
		 try {
			 MossConnector mossConnector = MossConnector.getInstance(context);
			 // InputStream in;
			 // in = new FileInputStream("C:/IBM/rationalsdp/workspace/ASINA/xsd/AsientoRegistro.xml");
			 // String properties = readFileAsString("C:/IBM/rationalsdp/workspace/ASINA/xsd/AsientoRegistro.xml");
			 // String guid = mossConnector.newDocument(null, in, 1747968, properties);
			 result = mossConnector.getDocumentSize(null, guid);
			 //mossConnector.deleteDocument(null, guid);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 assertTrue(result > 0);
	 }

	 public final void testGetMimeType() {
		 String result = "";
		 try {
			 MossConnector mossConnector = MossConnector.getInstance(context);
			 // InputStream in;
			 // in = new FileInputStream("C:/IBM/rationalsdp/workspace/ASINA/xsd/AsientoRegistro.xml");
			 // String properties = readFileAsString("C:/IBM/rationalsdp/workspace/ASINA/xsd/AsientoRegistro.xml");
			 // String guid = mossConnector.newDocument(null, in, 1747968, properties);
			 // String guid = "gggggggg-ed45-4b77-a485-2d94c2a864c0";
			 result = mossConnector.getMimeType(null, guid);
			 //mossConnector.deleteDocument(null, guid);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 assertTrue(!result.equals(""));
	 }

	 public final void testGetProperties() {
		 String result = "";
		 try {
			 MossConnector mossConnector = MossConnector.getInstance(context);
			 result = mossConnector.getProperties(null, guid);
			 //mossConnector.deleteDocument(null, guid);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 assertTrue(!result.equals(""));
	 }

	 public final void testGetProperty() {
		 String result = "";
		 try {
			 MossConnector mossConnector = MossConnector.getInstance(context);
			 result = mossConnector.getProperty(null, guid, MossConstants.METADATA_FILENAME);
			 //mossConnector.deleteDocument(null, guid);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 assertTrue(!result.equals(""));
	 }

	 /*
	 public final void testGetRepositoryInfo() {
		 String result = "";
		 try {
			 MossConnector mossConnector = MossConnector.getInstance(null);
			 result = mossConnector.getRepositoryInfo(null, "1");
			 System.out.println(result);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 assertTrue(!result.equals(""));
	 }
	 */

	 public final void testSetProperty() {
		 String result = null;
		 String value = null;
		 try {
			 MossConnector mossConnector = MossConnector.getInstance(context);
			 //System.out.println("Before:"+mossConnector.getProperty(null, guid, "ows_DocGUID"));
			 //mossConnector.setProperty(null, guid, "BaseName", "__3333__");
			 //System.out.println("After:"+mossConnector.getProperty(null, guid, "ows_DocGUID"));
			 value = mossConnector.getProperty(null, guid, MossConstants.PROPERTY_TITLE);
			 mossConnector.setProperty(null, guid, MossConstants.METADATA_TITLE, value.toUpperCase());
			 result = mossConnector.getProperty(null, guid, MossConstants.PROPERTY_TITLE);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 assertEquals(result, value.toUpperCase());
	 }

	// public final void testSetDocument() {
	// String guid = null;
	//
	// try {
	// MossConnector mossConnector = new MossConnector();
	// InputStream in;
	// in = new
	// FileInputStream("C:/IBM/rationalsdp/workspace/Asina/xsd/AsientoRegistro.xml");
	// String properties =
	// readFileAsString("C:/IBM/rationalsdp/workspace/Asina/xsd/AsientoRegistro.xml");
	// guid = mossConnector.newDocument(null, in, 1747968, properties);
	// mossConnector.deleteDocument(null, guid);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// assertNotNull(guid);
	// }

	public final void testDeleteDocument() {
		try {
			MossConnector mossConnector = MossConnector.getInstance(context);
			// InputStream in;
			// in = new
			// FileInputStream("C:/IBM/rationalsdp/workspace/ASINA/xsd/AsientoRegistro.xml");
			// String properties =
			// readFileAsString("C:/IBM/rationalsdp/workspace/ASINA/xsd/AsientoRegistro.xml");
			// String guid = mossConnector.newDocument(null, in, 1747968,
			// properties);
			//String guid = "{103324BA-FA6E-41D1-90F1-8C46993191F4}";
			mossConnector.deleteDocument(null, guid);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertTrue(true);
	}

	/*
	 * @Test public final void testCloseSession() {
	 * fail("No se ha implementado todavía"); // TODO }
	 *
	 * @Test public final void testCreateSession() {
	 * fail("No se ha implementado todavía"); // TODO }
	 */

//	/**
//	 * @param filePath
//	 *            the name of the file to open. Not sure if it can accept URLs
//	 *            or just filenames. Path handling could be better, and buffer
//	 *            sizes are hardcoded
//	 */
//	private static String readFileAsString(String filePath)
//			throws java.io.IOException {
//		StringBuffer fileData = new StringBuffer(1000);
//		BufferedReader reader = new BufferedReader(new FileReader(filePath));
//		char[] buf = new char[1024];
//		int numRead = 0;
//		while ((numRead = reader.read(buf)) != -1) {
//			String readData = String.valueOf(buf, 0, numRead);
//			fileData.append(readData);
//			buf = new char[1024];
//		}
//		reader.close();
//		return fileData.toString();
//	}
}
