
package es.ieci.tecdoc.isicres.admin.sbo.acs.std;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsObjOwner;
import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsObjTokenHdr;


public final class AcsMdoObj
{
   
   private AcsMdoObj()
   {
   }
   
   
   public static AcsObjTokenHdr createObjTokenHdr(DbConnection dbConn, int objId)
                                throws Exception
   {
      AcsObjTokenHdr      token = null;
      AcsDaoObjHdrRecOwner rec = null;
      
      rec = AcsDaoObjHdrTbl.selectRow(dbConn, objId); 
      
      token = new AcsObjTokenHdr(objId, rec.getOwnerId(),                   
                                 rec.getOwnerId()); 
      
      return token;
   } 
   
   public static boolean isUserObjOwner(DbConnection dbConn, int objId, int userId)
                         throws Exception
   {
      boolean is = false;

      is = AcsDaoObjHdrTbl.isObjOwner(dbConn, objId, userId, AcsObjOwner.USER);

      return is;      
   }
   
   public static void removeObj(int objId, boolean initTrans, String entidad)
                      throws Exception
   {
      
	   DbConnection dbConn=new DbConnection();
      try
      {
    	 dbConn.open(DBSessionManager.getSession());
         if (initTrans)
            dbConn.beginTransaction(); 
            
         AcsDaoObjHdrTbl.deleteRow(dbConn, objId);
         AcsDaoObjPermTbl.deleteRow(dbConn, objId);        

         if (initTrans)
            dbConn.endTransaction(true);            
         

      }
      catch (Exception e)
      {
            throw(e);
      }finally{
    	  dbConn.close();
      }
   }
     
} // class
