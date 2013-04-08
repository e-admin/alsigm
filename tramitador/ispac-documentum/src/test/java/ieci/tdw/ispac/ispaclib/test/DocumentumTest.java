/*
 * Created on 17-jun-2004
 *
 */
package ieci.tdw.ispac.ispaclib.test;

import junit.framework.TestCase;

/**
 * @author Enrique de Lema
 *
 */
public class DocumentumTest extends TestCase {
/*
	private DocumentumConnector documentum = null;
	private int documentId = 1;
	private int documentType = 1;
	private String strDocumentName = "Nombre del documento";
	private int procedureId = 1;
	private String strProcedureName = "Nombre del procedimiento";
	private int expedientId = 1;
	private String strExpedientName = "Nombre del expediente";
	private String strUserGUID = "GUID del usuario";
	private String strUserDN = "DN del usuario";
	private int stageId = 1;
	private String strStageName = "Nombre de la fase";
	private int taskId = 1;
	private String strTaskName = "Nombre de la tarea";
	private String strMimeType = "application/msword";

	protected void setUp() 
	throws Exception {
		
		super.setUp();
		
		try
		{
			//ISPACConfiguration parameters = ISPACConfiguration.getInstance();

			documentum = new DocumentumConnector();
		}
		catch( ISPACException e)
		{
			e.printStackTrace();
			throw e;
		}
		documentId = 1;
	}
    
    public void testInitiateStruct() {
        try
        {
            documentum.createRepository();
        }
        catch( ISPACException e)
        {
            e.printStackTrace();
        }
    }
    
	
	public void testExistsDocument() {
		
		boolean exists = false;
		
		try
		{
			exists = documentum.existsDocument( documentId);
		}
		catch( ISPACException e)
		{
			e.printStackTrace();
		}
	}
	public void testGetDocument() {
		boolean exists = false;
		
		try
		{
	    File file = new File( "c:/temp", "output.doc");
			FileOutputStream out = new FileOutputStream( file.getPath());
			
			documentum.getDocument( out, documentId);
			out.close();
		}
		catch( Exception e)
		{
			e.printStackTrace();
		}
	}
	public void testSetDocument() {
		try
		{
	    StringBuffer strBuffer = new StringBuffer();
			
      strBuffer.append( "<?xml version='1.0' encoding='UTF8'? >");
      strBuffer.append( "<properties>");
      strBuffer.append( "<document id='" + Integer.toString( documentId) + "'>");
      strBuffer.append( "<type>" + Integer.toString( documentType) + "</type>");
      strBuffer.append( "<name><![CDATA[" + strDocumentName + "]]></name>");
      strBuffer.append( "</document>");
      strBuffer.append( "<procedure id='" + Integer.toString( procedureId) + "'>");
      strBuffer.append( "<name><![CDATA[" + strProcedureName + "]]></name>");
      strBuffer.append( "</procedure>");
      strBuffer.append( "<expedient id='" + Integer.toString( expedientId) + "'>");
      strBuffer.append( "<name><![CDATA[" + strExpedientName + "]]></name>");
      strBuffer.append( "</expedient>");
      strBuffer.append( "<user>");
      strBuffer.append( "<guid><![CDATA[" + strUserGUID + "]]></guid>");
      strBuffer.append( "<dn><![CDATA[" + strUserDN + "]]></dn>");
      strBuffer.append( "</user>");
      strBuffer.append( "<stage id='" + Integer.toString( stageId) + "'>");
      strBuffer.append( "<name><![CDATA[" + strStageName + "]]></name>");
      strBuffer.append( "</stage>");
      strBuffer.append( "<task id='" + Integer.toString( taskId) + "'>");
      strBuffer.append( "<name><![CDATA[" + strTaskName + "]]></name>");
      strBuffer.append( "</task>");
      strBuffer.append( "<mimetype>");
      strBuffer.append( "<name><![CDATA[" + strMimeType + "]]></name>");
      strBuffer.append( "</mimetype>");
      strBuffer.append( "</properties>");
      
	    File file = new File( "c:/temp", "input.doc");
			FileInputStream in = new FileInputStream( file.getPath());
			
			documentum.setDocument( in, strBuffer.toString());
			in.close();
		}
		catch( Exception e)
		{
			e.printStackTrace();
		}
	}
	public void testDeleteDocument() {
		try
		{
			documentum.deleteDocument( documentId);
		}
		catch( ISPACException e)
		{
			e.printStackTrace();
		}
	}
*/
}
