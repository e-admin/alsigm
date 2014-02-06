package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.sbo.util.types.SboType;
import ieci.tecdoc.sbo.config.CfgMdoConfig;


public final class FssBnoFile
{	

	//~ Constructors -----------------------------------------------------------

	private FssBnoFile(){}

	//~ Methods ----------------------------------------------------------------

	public static byte [] retrieveFile(int fileId)
								 throws Exception
	{

		byte [] fileCont = null;

		try
		{

			DbConnection.open(CfgMdoConfig.getDbConfig());
			
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
	public static int storeFileInVolume(int volId, FssFileInfo info,
										         FssFtsInfo ftsInfo, byte [] fileCont)
							throws Exception
	{

		int fileId = SboType.NULL_ID;

		try
		{

			DbConnection.open(CfgMdoConfig.getDbConfig());
         
			fileId = FssMdoFile.storeFileInVolume(volId, info, ftsInfo,
                                               fileCont);
			
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
	public static int storeFileInList(int listId, FssFileInfo info,
									          FssFtsInfo ftsInfo, 
                                     byte [] fileCont)
						   throws Exception
	{

		int fileId = SboType.NULL_ID;

		try
		{

			DbConnection.open(CfgMdoConfig.getDbConfig());
			
			fileId = FssMdoFile.storeFileInList(listId, info, ftsInfo,
                                             fileCont);
			
			DbConnection.close();

			return fileId;

		}
		 catch(Exception e)
		{

			DbConnection.ensureClose(e);
			
			return fileId;

		}

	}
	
	public static void deleteFile(int fileId)
						    throws Exception
	{

		try
		{

			DbConnection.open(CfgMdoConfig.getDbConfig());
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
