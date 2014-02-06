
package ieci.tecdoc.sbo.acs.std;

import ieci.tecdoc.sbo.acs.base.AcsObjTokenHdr;
import ieci.tecdoc.sbo.acs.base.AcsObjOwner;
import ieci.tecdoc.core.db.DbConnection;

public final class AcsMdoObj
{
   
   private AcsMdoObj()
   {
   }
   
   
   public static AcsObjTokenHdr createObjTokenHdr(int objId)
                                throws Exception
   {
      AcsObjTokenHdr      token = null;
      AcsDaoObjHdrRecOwner rec = null;
      
      rec = AcsDaoObjHdrTbl.selectRow(objId); 
      
      token = new AcsObjTokenHdr(objId, rec.getOwnerId(),                   
                                 rec.getOwnerId()); 
      
      return token;
   } 
   
   public static boolean isUserObjOwner(int objId, int userId)
                         throws Exception
   {
      boolean is = false;

      is = AcsDaoObjHdrTbl.isObjOwner(objId, userId, AcsObjOwner.USER);

      return is;      
   }
   
   public static void removeObj(int objId, boolean initTrans)
                      throws Exception
   {
      
      try
      {
         if (initTrans)
            DbConnection.beginTransaction(); 
            
         AcsDaoObjHdrTbl.deleteRow(objId);
         AcsDaoObjPermTbl.deleteRow(objId);        

         if (initTrans)
            DbConnection.endTransaction(true);            
         

      }
      catch (Exception e)
      {

         if (initTrans)
            DbConnection.ensureEndTransaction(e);
         else
            throw(e);
      }   
   }
     
} // class
