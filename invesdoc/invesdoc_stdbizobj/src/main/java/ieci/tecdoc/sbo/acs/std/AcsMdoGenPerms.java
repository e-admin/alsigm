
package ieci.tecdoc.sbo.acs.std;

import ieci.tecdoc.sbo.acs.base.AcsProduct;
import ieci.tecdoc.sbo.acs.base.AcsPermission;
import ieci.tecdoc.sbo.acs.base.AcsDefs;
import ieci.tecdoc.sbo.acs.base.AcsPermDst;

public final class AcsMdoGenPerms
{

   private AcsMdoGenPerms()
   {
   }
   
   public static int getUseriDocGenPerms(int userId) throws Exception
   {
      int genPerms;
      int prodId  = AcsProduct.IDOC;
      int dstType = AcsPermDst.USER; 
      
      if (userId == AcsDefs.SYS_XSUPERUSER_ID) 
         genPerms = AcsPermission.ALL;
      else      
         genPerms = AcsDaoGenPermTbl.selectGenPerms(dstType, userId, prodId);      
      
      return genPerms;
   }
   
   public static int getDeptiDocGenPerms(int deptId) throws Exception
   {
      int genPerms;
      int prodId  = AcsProduct.IDOC;
      int dstType = AcsPermDst.DEPT; 
      
      genPerms = AcsDaoGenPermTbl.selectGenPerms(dstType, deptId, prodId);      
      
      return genPerms;
   }
   
   public static int getGroupiDocGenPerms(int groupId) throws Exception
   {
      int genPerms;
      int prodId  = AcsProduct.IDOC;
      int dstType = AcsPermDst.GROUP; 
      
      genPerms = AcsDaoGenPermTbl.selectGenPerms(dstType, groupId, prodId);      
      
      return genPerms;
   }
   
   
   
} // class
