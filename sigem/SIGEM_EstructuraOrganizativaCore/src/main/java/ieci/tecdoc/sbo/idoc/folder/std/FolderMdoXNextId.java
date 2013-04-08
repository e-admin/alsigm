package ieci.tecdoc.sbo.idoc.folder.std;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.sbo.idoc.dao.DaoXNextIdTbl;
import ieci.tecdoc.sbo.util.types.SboType;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

public final class FolderMdoXNextId
{
   public static final int TYPE_FDR      = 3;
   public static final int TYPE_EXT_FLD  = 5;
   public static final int TYPE_IDOC_DOC = 7; 

   private FolderMdoXNextId()
   {
   }

   public static int generateNextFolderId(int archId, String entidad)
         throws Exception
   {
      int id;
      int nextFdrId = SboType.NULL_ID;

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession(entidad));

         dbConn.beginTransaction();

         DaoXNextIdTbl.incrementNextId(dbConn, TYPE_FDR, archId);

         id = DaoXNextIdTbl.getNextId(dbConn, TYPE_FDR, archId);

         nextFdrId = id - 1;

         dbConn.endTransaction(true);

         return nextFdrId;

      }
      catch (Exception e)
      {
         return nextFdrId;
      }finally{
    	  dbConn.close();
      }

   }
   
   //Devuelve el primero
   public static int generateNextExtFldIds(int archId, int numIds, String entidad)
                     throws Exception
   {
      int id;
      int nextId = SboType.NULL_ID;

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession(entidad));

         dbConn.beginTransaction();

         DaoXNextIdTbl.incrementNextId(dbConn, TYPE_EXT_FLD, archId, numIds);

         id = DaoXNextIdTbl.getNextId(dbConn, TYPE_EXT_FLD, archId);

         nextId = id - numIds;

         dbConn.endTransaction(true);

         return nextId;

      }
      catch (Exception e)
      {
         return nextId;
      }finally{
    	  dbConn.close();
      }

   }
   
   //Devuelve el primero
   public static int generateNextiDocDocumentIds(int archId, int numIds, String entidad)
                     throws Exception
   {
      int id;
      int nextId = SboType.NULL_ID;

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession(entidad));

         dbConn.beginTransaction();

         DaoXNextIdTbl.incrementNextId(dbConn, TYPE_IDOC_DOC, archId, numIds);

         id = DaoXNextIdTbl.getNextId(dbConn, TYPE_IDOC_DOC, archId);

         nextId = id - numIds;

         dbConn.endTransaction(true);

         return nextId;

      }
      catch (Exception e)
      {
         return nextId;
      }finally{
    	  dbConn.close();
      }

   }
   
   

} // class
