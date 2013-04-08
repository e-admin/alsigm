package ieci.tdw.ispac.ispaclib.gendoc;

import ieci.tdw.ispac.api.connector.IDocConnector;
import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.InputStream;
import java.io.OutputStream;

public class FTPConnector implements IDocConnector
{

	private static FTPConnector mInstance = null;	
	
	private FTPConnector() 
	throws ISPACException
	{
	}

	public static synchronized FTPConnector getInstance() 
	throws ISPACException
	{
		if (mInstance == null)
			mInstance = new FTPConnector();
		return mInstance;
	}
	
	public boolean existsDocument(Object session, String sGUID) 
	throws ISPACException
	{

		boolean exists = false;

		return exists;
	}

	public void getDocument(Object session, String sGUID, OutputStream out) 
	throws ISPACException
	{
	}

	public String newDocument(Object session, InputStream in, int length, String strProperties) 
	throws ISPACException
	{
		String strObjectId = null;

		return strObjectId;
	}

	public String updateDocument(Object session, String sGUID, InputStream in, int length, String sProperties)
	throws ISPACException
	{
		return null;
	}
	
	public String getMimeType(Object session, String sGUID)
	throws ISPACException {
		
		return null;
	}
	
	public int getDocumentSize(Object session, String sGUID)
	throws ISPACException {
		return -1;
	}
	
	public void deleteDocument(Object session, String sGUID) 
	throws ISPACException
	{
	}

	public void createRepository(Object session) 
	throws ISPACException
	{
	}

	public String getProperties(Object session, String sGUID)
	throws ISPACException
	{
	  return null;
	}

	public void setProperty(Object session, String sGUID, String name, String value) throws ISPACException {
		// TODO Auto-generated method stub
		
	}

	public String getProperty(Object session, String sGUID, String property) throws ISPACException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRepositoryInfo(Object session, String repId) throws ISPACException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object createSession() throws ISPACException {
		// TODO Auto-generated method stub
		return null;
	}

	public void closeSession(Object session) throws ISPACException {
		// TODO Auto-generated method stub
		
	}
}