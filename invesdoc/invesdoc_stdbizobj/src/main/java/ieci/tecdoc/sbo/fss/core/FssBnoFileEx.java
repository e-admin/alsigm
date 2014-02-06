package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.sbo.util.types.SboType;

import java.sql.Connection;


public final class FssBnoFileEx
{

	private FssBnoFileEx(){}


	public static byte [] retrieveFile(Connection jdbcCnt, int fileId)
								 throws Exception
	{

		byte [] fileCont = null;

		try
		{

			DbConnection.open(jdbcCnt);
			
			fileCont = FssMdoFile.retrieveFile(fileId);
			
			DbConnection.close();

			return fileCont;

		}
		 catch(Exception e)
		{

			DbConnection.ensureClose(e);

			return fileCont;

		}

	}

	public static byte [] retrieveFile(DbConnectionConfig dbConConfig,
									           int fileId)
								 throws Exception
	{

		byte [] fileCont = null;

		try
		{

			DbConnection.open(dbConConfig);
			
			fileCont = FssMdoFile.retrieveFile(fileId);
			
			DbConnection.close();

			return fileCont;

		}
		 catch(Exception e)
		{

			DbConnection.ensureClose(e);

			return fileCont;

		}

	}

	// Si el fichero no tiene búsqueda en contenido de documento dejar
	// ftsInfo a null
	public static int storeFileInVolume(Connection jdbcCnt, int volId, FssFileInfo info,
										         FssFtsInfo ftsInfo, byte [] fileCont)
						   throws Exception
	{

		int fileId = SboType.NULL_ID;

		try
		{

			DbConnection.open(jdbcCnt);
			
			fileId = FssMdoFile.storeFileInVolume(volId, info, ftsInfo, fileCont);
			
			DbConnection.close();

			return fileId;

		}
		 catch(Exception e)
		{

			DbConnection.ensureClose(e);

			return fileId;

		}

	}

	// Si el fichero no tiene búsqueda en contenido de documento dejar
	// ftsInfo a null
	public static int storeFileInVolume(DbConnectionConfig dbConConfig,
										         int volId, FssFileInfo info,
										         FssFtsInfo ftsInfo, byte [] fileCont)
						   throws Exception
	{

		int fileId = SboType.NULL_ID;

		try
		{

			DbConnection.open(dbConConfig);
			
			fileId = FssMdoFile.storeFileInVolume(volId, info, ftsInfo, fileCont);
			
			DbConnection.close();

			return fileId;

		}
		 catch(Exception e)
		{

			DbConnection.ensureClose(e);

			return fileId;

		}

	}

	// Si el fichero no tiene búsqueda en contenido de documento dejar
	// ftsInfo a null
	public static int storeFileInList(Connection jdbCnt, int listId, FssFileInfo info,
									          FssFtsInfo ftsInfo, byte [] fileCont)
						   throws Exception
	{

		int fileId = SboType.NULL_ID;

		try
		{

			DbConnection.open(jdbCnt);
			
			fileId = FssMdoFile.storeFileInList(listId, info, ftsInfo, fileCont);
			
			DbConnection.close();

			return fileId;

		}
		 catch(Exception e)
		{

			DbConnection.ensureClose(e);			

			return fileId;

		}

	}

	// Si el fichero no tiene búsqueda en contenido de documento dejar
	// ftsInfo a null
	public static int storeFileInList(DbConnectionConfig dbConConfig,
									          int listId, FssFileInfo info,
									          FssFtsInfo ftsInfo, byte [] fileCont)
						   throws Exception
	{

		int fileId = SboType.NULL_ID;

		try
		{

			DbConnection.open(dbConConfig);
			
			fileId = FssMdoFile.storeFileInList(listId, info, ftsInfo, fileCont);
			
			DbConnection.close();

			return fileId;

		}
		 catch(Exception e)
		{

			DbConnection.ensureClose(e);		

			return fileId;

		}

	}

	public static void deleteFile(Connection jdbCnt, int fileId)
						   throws Exception
	{

		try
		{

			DbConnection.open(jdbCnt);
			FssMdoFile.deleteFile(fileId);
			DbConnection.close();

		}
		 catch(Exception e)
		{

			DbConnection.ensureClose(e);

		}

	}

	public static void deleteFile(DbConnectionConfig dbConConfig, int fileId)
						    throws Exception
	{

		try
		{

			DbConnection.open(dbConConfig);
			FssMdoFile.deleteFile(fileId);
			DbConnection.close();

		}
		 catch(Exception e)
		{

			DbConnection.ensureClose(e);

		}

	}

}
 // class
