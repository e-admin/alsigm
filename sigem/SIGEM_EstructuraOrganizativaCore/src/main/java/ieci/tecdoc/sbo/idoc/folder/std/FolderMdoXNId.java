package ieci.tecdoc.sbo.idoc.folder.std;


import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.sbo.idoc.dao.DaoUtil;
import ieci.tecdoc.sbo.idoc.dao.DaoXNIdRow;
import ieci.tecdoc.sbo.idoc.dao.DaoXNIdTbl;
import ieci.tecdoc.sbo.util.types.SboType;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

public final class FolderMdoXNId
{
   private static final int TYPE_CLF      = 6;
   private static final int TYPE_PAGE     = 8;
  
   private FolderMdoXNId()
   {
   }

   //Devuelve el primero
   public static int generateNextDividerId(String tblPrefix, int fdrId, int numIds, String entidad)
                     throws Exception
   {
      int           id;
      String        tblName = DaoUtil.getXNIdTblName(tblPrefix);
      int           nextId = SboType.NULL_ID;
      DaoXNIdTbl    tbl    = new DaoXNIdTbl(tblName);
      DaoXNIdRow    rec    = null;

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession(entidad));

         dbConn.beginTransaction();
         
         //Puede solicitarse un nextid de clf que no existe en la tabla de nextids
         
         if (tbl.nextIdExists(dbConn, TYPE_CLF, fdrId))
         { 
            tbl.incrementNextId(dbConn, TYPE_CLF, fdrId, numIds);
         }
         else
         {
            rec = new DaoXNIdRow();
            
            rec.m_type     = TYPE_CLF;
            rec.m_parentId = fdrId;
            rec.m_id       = numIds + 1;
            
            tbl.insertRow(dbConn, rec);
         }

         id = tbl.getNextId(dbConn, TYPE_CLF, fdrId);

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
   public static int generateNextiDocPageId(String tblPrefix, int fdrId, int numIds, String entidad)
                     throws Exception
   {
      int           id;
      String        tblName = DaoUtil.getXNIdTblName(tblPrefix);
      int           nextId = SboType.NULL_ID;
      DaoXNIdTbl    tbl    = new DaoXNIdTbl(tblName);
      DaoXNIdRow    rec    = null;

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession(entidad));

         dbConn.beginTransaction();
         
         //Puede solicitarse un nextid de página que no existe en la tabla de nextids
         
         if (tbl.nextIdExists(dbConn, TYPE_PAGE, fdrId))
         { 
            tbl.incrementNextId(dbConn, TYPE_PAGE, fdrId, numIds);
         }
         else
         {
            rec = new DaoXNIdRow();
            
            rec.m_type     = TYPE_PAGE;
            rec.m_parentId = fdrId;
            rec.m_id       = numIds + 1;
            
            tbl.insertRow(dbConn, rec);
         }

         id = tbl.getNextId(dbConn, TYPE_PAGE, fdrId);

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
   

   public static void removeFolderXNId(DbConnection dbConn, String tblPrefix, int fdrId)
                      throws Exception
   {
      int           id;
      String        tblName = DaoUtil.getXNIdTblName(tblPrefix);
      int           nextId  = SboType.NULL_ID;
      DaoXNIdTbl    tbl     = new DaoXNIdTbl(tblName);     

      tbl.deleteChildrenRows(dbConn, fdrId);

   }

} // class
