package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.db.DBSessionManager;

import ieci.tecdoc.sbo.util.types.SboType;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbConnectionConfig;

import java.sql.Connection;


public final class FssBnoFileEx
{

	private FssBnoFileEx(){}


	public static byte [] retrieveFile(int fileId, String entidad)
								 throws Exception
	{

		byte [] fileCont = null;
		DbConnection dbConn=new DbConnection();
		try
		{
			dbConn.open(DBSessionManager.getSession(entidad));
			fileCont = FssMdoFile.retrieveFile(dbConn, fileId, entidad);
		}
		 catch(Exception e)
		{
			 throw(e);
		}finally{
			dbConn.close();
		}
			return fileCont;
	}

	public static byte [] retrieveFile(DbConnectionConfig dbConConfig,
									           int fileId, String entidad)
								 throws Exception
	{

		byte [] fileCont = null;
		DbConnection dbConn=new DbConnection();
		try
		{

			dbConn.open(DBSessionManager.getSession(entidad));
			
			fileCont = FssMdoFile.retrieveFile(dbConn, fileId, entidad);
		}
		 catch(Exception e)
		{
			 throw(e);
		}finally{
			dbConn.close();
		}
		 return fileCont;
	}

	// Si el fichero no tiene búsqueda en contenido de documento dejar
	// ftsInfo a null
	public static int storeFileInVolume(Connection jdbcCnt, int volId, FssFileInfo info,
										         FssFtsInfo ftsInfo, byte [] fileCont, String entidad)
						   throws Exception
	{

		int fileId = SboType.NULL_ID;
		DbConnection dbConn=new DbConnection();
		try
		{

			dbConn.open(jdbcCnt);
			
			fileId = FssMdoFile.storeFileInVolume(dbConn, volId, info, ftsInfo, fileCont, entidad);
		}
		 catch(Exception e)
		{
			 throw (e);
		}finally{
			dbConn.close();
		}
		return fileId;
	}

	// Si el fichero no tiene búsqueda en contenido de documento dejar
	// ftsInfo a null
	public static int storeFileInVolume(DbConnectionConfig dbConConfig,
										         int volId, FssFileInfo info,
										         FssFtsInfo ftsInfo, byte [] fileCont, String entidad)
						   throws Exception
	{

		int fileId = SboType.NULL_ID;

		DbConnection dbConn=new DbConnection();
		try
		{

			dbConn.open(DBSessionManager.getSession(entidad));
			
			fileId = FssMdoFile.storeFileInVolume(dbConn, volId, info, ftsInfo, fileCont, entidad);
		}
		 catch(Exception e)
		{
			 throw (e);
		}finally{
			dbConn.close();
		}
		return fileId;
	}

	// Si el fichero no tiene búsqueda en contenido de documento dejar
	// ftsInfo a null
	public static int storeFileInList(Connection jdbCnt, int listId, FssFileInfo info,
									          FssFtsInfo ftsInfo, byte [] fileCont, String entidad)
						   throws Exception
	{

		int fileId = SboType.NULL_ID;
		DbConnection dbConn=new DbConnection();
		try
		{

			dbConn.open(jdbCnt);
			
			fileId = FssMdoFile.storeFileInList(dbConn, listId, info, ftsInfo, fileCont, entidad);
		}
		 catch(Exception e)
		{
			 throw (e);
		}finally{
			dbConn.close();
		}
		 return fileId;
	}

	// Si el fichero no tiene búsqueda en contenido de documento dejar
	// ftsInfo a null
	public static int storeFileInList(DbConnectionConfig dbConConfig,
									          int listId, FssFileInfo info,
									          FssFtsInfo ftsInfo, byte [] fileCont, String entidad)
						   throws Exception
	{

		int fileId = SboType.NULL_ID;

		DbConnection dbConn=new DbConnection();
		try
		{

			dbConn.open(DBSessionManager.getSession(entidad));
			
			fileId = FssMdoFile.storeFileInList(dbConn, listId, info, ftsInfo, fileCont, entidad);
		}
		 catch(Exception e)
		{
			 throw (e);
		}finally{
			dbConn.close();
		}
		return fileId;
	}

	public static void deleteFile(Connection jdbCnt, int fileId, String entidad)
						   throws Exception
	{
		DbConnection dbConn=new DbConnection();
		try
		{
			dbConn.open(jdbCnt);
			FssMdoFile.deleteFile(dbConn, fileId, entidad);
		}
		 catch(Exception e)
		{
			 throw (e);
		}finally{
			dbConn.close();
		}

	}

	public static void deleteFile(DbConnectionConfig dbConConfig, int fileId, String entidad)
						    throws Exception
	{
		DbConnection dbConn=new DbConnection();
		try
		{

			dbConn.open(DBSessionManager.getSession(entidad));
			FssMdoFile.deleteFile(dbConn, fileId, entidad);
		}
		 catch(Exception e)
		{
			 throw (e);
		}finally{
			dbConn.close();
		}

	}

}
 // class
