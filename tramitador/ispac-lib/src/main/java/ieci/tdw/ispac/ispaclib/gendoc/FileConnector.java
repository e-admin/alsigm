/*
 * Created on 05-oct-2004
 */
package ieci.tdw.ispac.ispaclib.gendoc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import ieci.tdw.ispac.api.connector.DocumentProperties;
import ieci.tdw.ispac.api.connector.IDocConnector;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;

/**
 * @author Lema
 */
public class FileConnector implements IDocConnector
{
	private static FileConnector mInstance = null;

	protected String msRepositoryPath = null;
	protected int mMaxDirectoryFiles = 1024;
	protected final static int CHUNK_SIZE = 5120;

	private FileConnector() 
	throws ISPACException
	{
		try
		{
			ISPACConfiguration parameters = ISPACConfiguration.getInstance();

			msRepositoryPath = parameters.get(ISPACConfiguration.REPOSITORY_PATH);
			if (msRepositoryPath.endsWith("/"))
				msRepositoryPath = msRepositoryPath.substring(0,msRepositoryPath.length() - 1);
			mMaxDirectoryFiles = Integer.parseInt(parameters
					.get(ISPACConfiguration.MAX_DIRECTORY_FILES));
		} 
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
	}
	
	
	public static synchronized FileConnector getInstance() 
	throws ISPACException
	{
		if (mInstance == null)
			mInstance = new FileConnector();
		return mInstance;
	}
	public boolean existsDocument(Object session, String sGUID) 
	throws ISPACException
	{
		StringBuffer sBuffer = new StringBuffer();
		boolean exists = false;
		int Identifier = Integer.parseInt(sGUID);

		try
		{
			sBuffer.append(msRepositoryPath);
			sBuffer.append("/store");
			sBuffer.append(Integer.toString(Identifier / mMaxDirectoryFiles));
			sBuffer.append("/");
			sBuffer.append(Integer.toString(Identifier));
			sBuffer.append(".doc");
			File in = new File(sBuffer.toString());
			exists = in.exists();
		} 
		catch (Exception e)
		{
			throw new ISPACException(e);
		}

		return exists;
	}

	public void getDocument(Object session, String sGUID, OutputStream stream) 
	throws ISPACException
	{
		int Identifier = Integer.parseInt(sGUID);

		StringBuffer sBuffer = new StringBuffer();

		BufferedInputStream in = null;
		BufferedOutputStream out = new BufferedOutputStream(stream);

		try
		{
			sBuffer.append(msRepositoryPath);
			sBuffer.append("/store");
			sBuffer.append(Integer.toString(Identifier / mMaxDirectoryFiles));
			sBuffer.append("/");
			sBuffer.append(Integer.toString(Identifier));
			sBuffer.append(".doc");

			in = new BufferedInputStream(new FileInputStream(sBuffer.toString()));

			byte[] chunk = new byte[CHUNK_SIZE];
			int bytes;
			while ((bytes = in.read(chunk)) != -1)
			{
				out.write(chunk, 0, bytes);
			}
			out.flush();
		} 
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{
			try
			{	if (in != null) in.close();} 
			catch (Exception e)
			{}
		}
	}

	public String newDocument(Object session, InputStream stream, int length, String sProperties) 
	throws ISPACException
	{
		BufferedInputStream in = new BufferedInputStream(stream);;
		BufferedOutputStream out = null;
		String sGUID = null;
		
		try
		{
		    DocumentProperties properties = new DocumentProperties(sProperties);
			
			if (properties.getDocumentId() == 0)
			{
				throw new ISPACException("Falta el identificador del documento");
			}
			
			sGUID = Integer.toString(properties.getDocumentId());

			StringBuffer sBuffer = new StringBuffer();

			sBuffer.append(msRepositoryPath);
			sBuffer.append("/store");
			sBuffer.append(Integer.toString(properties.getDocumentId() / mMaxDirectoryFiles));
			File file = new File(sBuffer.toString());

			if (!file.exists())
				file.mkdir();

			sBuffer.append("/");
			
			String sPath = sBuffer.toString();
			
			sBuffer.append(sGUID);
			sBuffer.append(".doc");
 
			out = new BufferedOutputStream(new FileOutputStream(sBuffer.toString()));

			byte[] chunk = new byte[CHUNK_SIZE];
			int bytes;
			
			while (length > 0)
			{
				bytes = CHUNK_SIZE;
				if (length < CHUNK_SIZE)
				{
					bytes = length;
				}

				if ((bytes = in.read(chunk, 0, bytes)) == -1)
					break;
				
				length -= bytes;
				out.write(chunk, 0, bytes);
			}
			
			out.flush();
			
			sBuffer.setLength(0);
			sBuffer.append(sPath);
			
			sBuffer.append(sGUID);
			sBuffer.append(".mime");
			
			writeMimeType(sBuffer.toString(), properties.getMimeType());
		} 
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{
			try
			{
			    if (out != null) out.close();
			} 
			catch (Exception e)
			{}
		}
		
		return sGUID;
	}

	public String updateDocument(Object session, String sGUID, InputStream stream, int length, String sProperties)
	throws ISPACException
	{
	
		BufferedInputStream in = new BufferedInputStream(stream);;
		BufferedOutputStream out = null;
		
		try
		{
		    DocumentProperties properties = new DocumentProperties(sProperties);

		    StringBuffer sBuffer = new StringBuffer();

			sBuffer.append(msRepositoryPath);
			sBuffer.append("/store");
			sBuffer.append(Integer.toString(Integer.parseInt(sGUID) / mMaxDirectoryFiles));
			File file = new File(sBuffer.toString());

			if (!file.exists())
				file.mkdir();

			sBuffer.append("/");
			
			String sPath = sBuffer.toString();
			
			sBuffer.append(sGUID);
			sBuffer.append(".doc");
 
			out = new BufferedOutputStream(new FileOutputStream(sBuffer.toString()));

			byte[] chunk = new byte[CHUNK_SIZE];
			int bytes;
			
			while (length > 0)
			{
				bytes = CHUNK_SIZE;
				if (length < CHUNK_SIZE)
				{
					bytes = length;
				}

				if ((bytes = in.read(chunk, 0, bytes)) == -1)
					break;
				
				length -= bytes;
				out.write(chunk, 0, bytes);
			}
			
			out.flush();
			
			sBuffer.setLength(0);
			sBuffer.append(sPath);
			
			sBuffer.append(sGUID);
			sBuffer.append(".mime");
			
			writeMimeType(sBuffer.toString(), properties.getMimeType());
			
		} 
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{
			try
			{
			    if (out != null) out.close();
			} 
			catch (Exception e)
			{}
		}
		return null;
	}
	
	public String getMimeType(Object session, String sGUID)
	throws ISPACException {
		
		String sMimeType = null;
		
		int Identifier = Integer.parseInt(sGUID);

		StringBuffer sBuffer = new StringBuffer();

		try
		{
			sBuffer.append(msRepositoryPath);
			sBuffer.append("/store");
			sBuffer.append(Integer.toString(Identifier / mMaxDirectoryFiles));
			sBuffer.append("/");
			sBuffer.append(Integer.toString(Identifier));
			sBuffer.append(".mime");

			sMimeType = readMimeType(sBuffer.toString());
		} 
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		
		return sMimeType;
	}

	public int getDocumentSize(Object session, String sGUID)
	throws ISPACException {
		
		int size = -1;
		
		int Identifier = Integer.parseInt(sGUID);

		StringBuffer sBuffer = new StringBuffer();

		try
		{
			sBuffer.append(msRepositoryPath);
			sBuffer.append("/store");
			sBuffer.append(Integer.toString(Identifier / mMaxDirectoryFiles));
			sBuffer.append("/");
			sBuffer.append(Integer.toString(Identifier));
			sBuffer.append(".doc");

		    File in = new File( sBuffer.toString());
		    size = (int) in.length();
		} 
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		
		return size;
	}

	public void deleteDocument(Object session, String sGUID) 
	throws ISPACException
	{
		int Identifier = Integer.parseInt(sGUID);

		StringBuffer sBuffer = new StringBuffer();

		try
		{
			File file = null;
			
			sBuffer.append(msRepositoryPath);
			sBuffer.append("/store");
			sBuffer.append(Integer.toString(Identifier / mMaxDirectoryFiles));
			sBuffer.append("/");
			sBuffer.append(Integer.toString(Identifier));
			sBuffer.append(".doc");

			file = new File(sBuffer.toString());
			file.delete();
			
			sBuffer.setLength(0);
			sBuffer.append(msRepositoryPath);
			sBuffer.append("/store");
			sBuffer.append(Integer.toString(Identifier / mMaxDirectoryFiles));
			sBuffer.append("/");
			sBuffer.append(Integer.toString(Identifier));
			sBuffer.append(".mime");
			
			file = new File(sBuffer.toString());
			file.delete();
		} 
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
	}

	public void createRepository(Object session) throws ISPACException
	{
		try
		{
			File file = new File(msRepositoryPath);
			if (!file.exists())
				file.mkdir();
		} 
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
	}
	
	public String getProperties(Object session, String sGUID)
	throws ISPACException
	{
	  return null;
	}

	protected void writeMimeType( String sPath, String sMimeType) 
	throws IOException, FileNotFoundException {
		
		PrintWriter writer = null;
	    
		try
		{
		    writer = new PrintWriter( new BufferedWriter(new FileWriter( sPath)));
		    writer.println( sMimeType);
		}
		finally
		{
			if (writer != null) writer.close();
		}
	}

	protected String readMimeType( String sPath)
	throws IOException, FileNotFoundException {
	    
		LineNumberReader reader = null;
		String sMimeType = null;
		
		try
		{
		    reader = new LineNumberReader( new BufferedReader(new FileReader( sPath)));
		    sMimeType = reader.readLine();
		}
		finally
		{
			if (reader != null) reader.close();
		}
		
		return sMimeType;
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
