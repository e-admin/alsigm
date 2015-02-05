package ieci.tdw.ispac.ispaclib.test;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.documentum.gendoc.DocumentumConnector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class test {

	DocumentumConnector documentum = null;
	int documentId = 9;
	int documentType = 1;
	String strDocumentName = "Nombre del documento";
	int procedureId = 2;
	String strProcedureName = "Nombre del procedimiento";
	int expedientId = 3;
	String strExpedientName = "Nombre del expediente";
	String strUserGUID = "GUID del usuario";
	String strUserDN = "DN del usuario";
	int stageId = 4;
	String strStageName = "Nombre de la fase";
	int taskId = 5;
	String strTaskName = "Nombre de la tarea";
	String strMimeType = "application/msword";
	
	public static void main(String[] args) {

		
		try
		{
			//ISPACConfiguration parameters = ISPACConfiguration.getInstance();

      test t = new test();
      String strObjectId = t.setDocument();
      t.getDocument( strObjectId);
      //t.deleteDocument( strObjectId);
		}
		catch( Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public test()
	throws ISPACException {
		
		documentum = DocumentumConnector.getInstance();
		
		//documentum.createStruct();
	}

	public String setDocument() 
	throws ISPACException, FileNotFoundException, IOException {
		
	  StringBuffer strBuffer = new StringBuffer();
			
    strBuffer.append( "<?xml version='1.0' encoding='UTF8'?>");
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
     
	  File file = new File( "c:/temp", "querydoc.doc");
		FileInputStream in = new FileInputStream( file.getPath());
		
		Object session = documentum.createSession();
		try {
			String strDocumentId = documentum.newDocument(session, in, (int) file.length(),strBuffer.toString());
			in.close();
			return strDocumentId;
		}finally {
			documentum.closeSession(session);
    	}		
	}

  public void getDocument( String strObjectId) 
  throws ISPACException, FileNotFoundException, IOException {
	
    File file = new File( "c:/temp", "output.doc");
	  FileOutputStream out = new FileOutputStream( file.getPath());
		
		Object session = documentum.createSession();
		try {
			documentum.getDocument(session, strObjectId,out);
		}finally {
			documentum.closeSession(session);
		}
	  
	  out.close();
  }

  public void deleteDocument( String strObjectId) 
  throws ISPACException, FileNotFoundException, IOException {
	
		Object session = documentum.createSession();
		try {
			  documentum.deleteDocument(session, strObjectId);
		}finally {
			documentum.closeSession(session);
		}

  
  }
}

