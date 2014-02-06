package ieci.tecdoc.sbo.idoc.folder.std;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.sbo.util.types.SboType;
import ieci.tecdoc.sbo.idoc.dao.DaoUtil;
import ieci.tecdoc.sbo.idoc.dao.DaoXNIdTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoXNIdRow;

public final class FolderMdoXNId
{
   private static final int TYPE_CLF      = 6;
   private static final int TYPE_PAGE     = 8;
  
   private FolderMdoXNId()
   {
   }

   //Devuelve el primero
   public static int generateNextDividerId(String tblPrefix, int fdrId, int numIds)
                     throws Exception
   {
      int           id;
      String        tblName = DaoUtil.getXNIdTblName(tblPrefix);
      int           nextId = SboType.NULL_ID;
      DaoXNIdTbl    tbl    = new DaoXNIdTbl(tblName);
      DaoXNIdRow    rec    = null;

      try
      {

         DbConnection.beginTransaction();
         
         //Puede solicitarse un nextid de clf que no existe en la tabla de nextids
         
         if (tbl.nextIdExists(TYPE_CLF, fdrId))
         { 
            tbl.incrementNextId(TYPE_CLF, fdrId, numIds);
         }
         else
         {
            rec = new DaoXNIdRow();
            
            rec.m_type     = TYPE_CLF;
            rec.m_parentId = fdrId;
            rec.m_id       = numIds + 1;
            
            tbl.insertRow(rec);
         }

         id = tbl.getNextId(TYPE_CLF, fdrId);

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
   public static int generateNextiDocPageId(String tblPrefix, int fdrId, int numIds)
                     throws Exception
   {
      int           id;
      String        tblName = DaoUtil.getXNIdTblName(tblPrefix);
      int           nextId = SboType.NULL_ID;
      DaoXNIdTbl    tbl    = new DaoXNIdTbl(tblName);
      DaoXNIdRow    rec    = null;

      try
      {

         DbConnection.beginTransaction();
         
         //Puede solicitarse un nextid de página que no existe en la tabla de nextids
         
         if (tbl.nextIdExists(TYPE_PAGE, fdrId))
         { 
            tbl.incrementNextId(TYPE_PAGE, fdrId, numIds);
         }
         else
         {
            rec = new DaoXNIdRow();
            
            rec.m_type     = TYPE_PAGE;
            rec.m_parentId = fdrId;
            rec.m_id       = numIds + 1;
            
            tbl.insertRow(rec);
         }

         id = tbl.getNextId(TYPE_PAGE, fdrId);

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
   

   public static void removeFolderXNId(String tblPrefix, int fdrId)
                      throws Exception
   {
      int           id;
      String        tblName = DaoUtil.getXNIdTblName(tblPrefix);
      int           nextId  = SboType.NULL_ID;
      DaoXNIdTbl    tbl     = new DaoXNIdTbl(tblName);     

      tbl.deleteChildrenRows(fdrId);

   }

} // class
