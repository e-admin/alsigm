package ieci.tdw.ispac.ispaclib.gendoc;

import ieci.tdw.ispac.api.connector.DocumentProperties;
import ieci.tdw.ispac.api.connector.IDocConnector;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbQuery;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Blob;
import java.sql.PreparedStatement;

public class DataBaseConnector implements IDocConnector
{

	private static DataBaseConnector mInstance = null;	
	private final static int CHUNK_SIZE = 10240;

	private DataBaseConnector()
	{
	}
	public static synchronized DataBaseConnector getInstance() 
	throws ISPACException
	{
		if (mInstance == null)
			mInstance = new DataBaseConnector();
		return mInstance;
	}
	
	public boolean existsDocument(Object session, String sGUID)
	throws ISPACException
	{
		boolean exists = false;
		DbQuery rs = null;

		DbCnt cnt = new DbCnt();

		cnt.getConnection();

		try
		{
			StringBuffer sBuffer = new StringBuffer();

			sBuffer.append("SELECT COUNT(*) FROM SPAC_DOCOBJ WHERE ID = ");
			sBuffer.append(sGUID);
			rs = cnt.executeDbQuery(sBuffer.toString());
			if (rs.next())
			{
				if (rs.getInt(1) != 0)	exists = true;
			}
		}
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{
			if (rs != null) rs.close();
			cnt.closeConnection();
		}

		return exists;
	}

	public void getDocument(Object session, String sGUID, OutputStream stream)
	throws ISPACException
	{
		DbQuery rs = null;
		BufferedInputStream in = null;
		BufferedOutputStream out = new BufferedOutputStream(stream);

		DbCnt cnt = new DbCnt();

		cnt.getConnection();

		try
		{
			StringBuffer sBuffer = new StringBuffer();

			sBuffer.append("SELECT OBJ FROM SPAC_DOCOBJ WHERE ID = ");
			sBuffer.append(sGUID);
			rs = cnt.executeDbQuery(sBuffer.toString());
//			if (rs.next())
//			{
//				Blob blob = rs.getBLOB(1);
//				in = new BufferedInputStream(blob.getBinaryStream());
//				byte[] chunck = new byte[CHUNK_SIZE];
//				int bytes;
//				while ((bytes = in.read(chunck)) != -1)
//				{
//					out.write(chunck, 0, bytes);
//				}
//				out.flush();
//			}
			if (rs.next())
			{
				byte[] chunk = new byte[CHUNK_SIZE];
				int bytes;
				if (cnt.isPostgreSQLDbEngine()){
					in = new BufferedInputStream(rs.getBinaryStream("OBJ"));
				}else{
					Blob blob = rs.getBLOB("OBJ");
					in = new BufferedInputStream(blob.getBinaryStream());
				}
				while ((bytes = in.read(chunk)) != -1)
				{
					out.write(chunk, 0, bytes);
				}
				out.flush();
			}
		}
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{
			try
			{
				out.close();
				if (rs != null) rs.close();
				if (in != null) in.close();
			}
			catch (Exception e)
			{}
			cnt.closeConnection();
		}
	}

	public String newDocument(Object session, InputStream stream, int length, String sProperties)
	throws ISPACException
	{
		DbQuery rs = null;
		BufferedInputStream  in = new BufferedInputStream(stream);
		boolean bTX = false;
		String sGUID = null;

		DbCnt cnt = new DbCnt();
		cnt.getConnection();

		try
		{
			cnt.openTX();

		    DocumentProperties properties = new DocumentProperties(sProperties);

			StringBuffer sBuffer = new StringBuffer();
			// Elimina el documento si ya existe
			sBuffer.append("DELETE FROM SPAC_DOCOBJ WHERE ID = " + properties.getDocumentId());
			cnt.directExec(sBuffer.toString());

			// Se añade el nuevo documento
			sBuffer.setLength( 0);
			sBuffer.append("INSERT INTO SPAC_DOCOBJ(ID,TAM,MIMETYPE,OBJ) VALUES(");
			sBuffer.append(Integer.toString( properties.getDocumentId()));
			sBuffer.append(",?,'");
			sBuffer.append(properties.getMimeType());
			sBuffer.append("',? )");

			PreparedStatement pstmt = cnt.prepareStatement(sBuffer.toString());
			pstmt.setInt(1,length);
			pstmt.setBinaryStream(2,in,length);
			pstmt.execute();
			pstmt.close();

			sGUID = Integer.toString(properties.getDocumentId());

			bTX = true;
		}
		catch (Exception e)
		{
			bTX = false;
			throw new ISPACException(e);
		}
		finally
		{
			try
			{
				in.close();
				cnt.closeTX( bTX);
				if (rs != null) rs.close();
			}
			catch (Exception e)
			{}
			cnt.closeConnection();
		}

		return sGUID;
	}


	public String updateDocument(Object session, String sGUID, InputStream stream, int length, String sProperties)
	throws ISPACException
	{
		BufferedInputStream in = new BufferedInputStream(stream);
		boolean bTX = false;
		DbCnt cnt = new DbCnt();
		cnt.getConnection();

		try
		{
		    DocumentProperties properties = new DocumentProperties(sProperties);

			cnt.openTX();

			StringBuffer sBuffer = new StringBuffer();

			sBuffer.append("UPDATE SPAC_DOCOBJ SET TAM = ?, OBJ = ? ");
			if (properties.getMimeType().length() != 0)
			{
				sBuffer.append(", MIMETYPE = '");
				sBuffer.append(properties.getMimeType());
				sBuffer.append("'");
			}
			sBuffer.append(" WHERE ID = ");
			sBuffer.append(sGUID);

			PreparedStatement pstmt = cnt.prepareStatement(sBuffer.toString());
			pstmt.setInt( 1, length);
			pstmt.setBinaryStream(2,in,length);
			pstmt.execute();
			pstmt.close();


			bTX = true;
		}
		catch (Exception e)
		{
			bTX = false;
			throw new ISPACException(e);
		}
		finally
		{
			try
			{
				cnt.closeTX( bTX);
				in.close();
			}
			catch (Exception e)
			{}
			cnt.closeConnection();
		}
		return null;
	}


	public void deleteDocument(Object session, String sGUID)
	throws ISPACException
	{
		DbCnt cnt = new DbCnt();

		cnt.getConnection();

		try
		{
			StringBuffer sBuffer = new StringBuffer();

			sBuffer.append("DELETE FROM SPAC_DOCOBJ WHERE ID = ");
			sBuffer.append(sGUID);

			cnt.directExec(sBuffer.toString());
		}
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{
			cnt.closeConnection();
		}
	}

	public String getMimeType(Object session, String sGUID)
	throws ISPACException {

		DbQuery rs = null;
		String sMimeType = null;

		DbCnt cnt = new DbCnt();

		cnt.getConnection();

		try
		{
			StringBuffer sBuffer = new StringBuffer();

			sBuffer.append("SELECT MIMETYPE FROM SPAC_DOCOBJ WHERE ID = ");
			sBuffer.append(sGUID);
			rs = cnt.executeDbQuery(sBuffer.toString());
			if (rs.next())
			{
				sMimeType = rs.getString(1);
			}
		}
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{
			if (rs != null) rs.close();
			cnt.closeConnection();
		}

		return sMimeType;
	}

	public int getDocumentSize(Object session, String sGUID)
	throws ISPACException
	{
		DbQuery rs = null;
		DbCnt cnt = new DbCnt();

		cnt.getConnection();

		try
		{
			StringBuffer sBuffer = new StringBuffer();

			sBuffer.append("SELECT TAM FROM SPAC_DOCOBJ WHERE ID = ");
			sBuffer.append(sGUID);
			rs = cnt.executeDbQuery(sBuffer.toString());

			if (!rs.next())
			    return -1;

			return rs.getInt(1);
		}
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{
			if (rs != null) rs.close();
			cnt.closeConnection();
		}
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

	public String getProperty(Object session, String sGUID, String property) throws ISPACException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setProperty(Object session, String sGUID, String name, String value) throws ISPACException {
		// TODO Auto-generated method stub
		
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