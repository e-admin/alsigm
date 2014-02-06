package ieci.tecdoc.sbo.idoc.folder.std;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.sbo.util.types.SboType;
import ieci.tecdoc.sbo.idoc.dao.DaoXNextIdTbl;


public final class FolderMdoXNextId
{
   public static final int TYPE_FDR      = 3;
   public static final int TYPE_EXT_FLD  = 5;
   public static final int TYPE_IDOC_DOC = 7; 

   private FolderMdoXNextId()
   {
   }

   public static int generateNextFolderId(int archId)
         throws Exception
   {
      int id;
      int nextFdrId = SboType.NULL_ID;

      try
      {

         DbConnection.beginTransaction();

         DaoXNextIdTbl.incrementNextId(TYPE_FDR, archId);

         id = DaoXNextIdTbl.getNextId(TYPE_FDR, archId);

         nextFdrId = id - 1;

         DbConnection.endTransaction(true);

         return nextFdrId;

      }
      catch (Exception e)
      {
         DbConnection.ensureEndTransaction(e);
         return nextFdrId;
      }

   }
   
   //Devuelve el primero
   public static int generateNextExtFldIds(int archId, int numIds)
                     throws Exception
   {
      int id;
      int nextId = SboType.NULL_ID;

      try
      {

         DbConnection.beginTransaction();

         DaoXNextIdTbl.incrementNextId(TYPE_EXT_FLD, archId, numIds);

         id = DaoXNextIdTbl.getNextId(TYPE_EXT_FLD, archId);

         nextId = id - numIds;

         DbConnection.endTransaction(true);

         return nextId;

      }
      catch (Exception e)
      {
         DbConnection.ensureEndTransaction(e);
         return nextId;
      }

   }
   
   //Devuelve el primero
   public static int generateNextiDocDocumentIds(int archId, int numIds)
                     throws Exception
   {
      int id;
      int nextId = SboType.NULL_ID;

      try
      {

         DbConnection.beginTransaction();

         DaoXNextIdTbl.incrementNextId(TYPE_IDOC_DOC, archId, numIds);

         id = DaoXNextIdTbl.getNextId(TYPE_IDOC_DOC, archId);

         nextId = id - numIds;

         DbConnection.endTransaction(true);

         return nextId;

      }
      catch (Exception e)
      {
         DbConnection.ensureEndTransaction(e);
         return nextId;
      }

   }
   
   

} // class
