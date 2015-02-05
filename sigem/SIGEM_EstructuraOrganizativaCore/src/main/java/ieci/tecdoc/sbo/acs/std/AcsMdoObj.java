
package ieci.tecdoc.sbo.acs.std;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.sbo.acs.base.AcsObjTokenHdr;
import ieci.tecdoc.sbo.acs.base.AcsObjOwner;
import ieci.tecdoc.sgm.base.dbex.DbConnection;


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
    	 dbConn.open(DBSessionManager.getSession(entidad));
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
