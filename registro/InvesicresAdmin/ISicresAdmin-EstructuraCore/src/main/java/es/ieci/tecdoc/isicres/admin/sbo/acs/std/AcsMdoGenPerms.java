
package es.ieci.tecdoc.isicres.admin.sbo.acs.std;

import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsDefs;
import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsPermDst;
import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsPermission;
import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsProduct;

public final class AcsMdoGenPerms
{

   private AcsMdoGenPerms()
   {
   }
   
   public static int getUseriDocGenPerms(int userId, String entidad) throws Exception
   {
      int genPerms;
      int prodId  = AcsProduct.IDOC;
      int dstType = AcsPermDst.USER; 
      
      if (userId == AcsDefs.SYS_XSUPERUSER_ID) 
         genPerms = AcsPermission.ALL;
      else      
         genPerms = AcsDaoGenPermTbl.selectGenPerms(dstType, userId, prodId, entidad);      
      
      return genPerms;
   }
   
   public static int getDeptiDocGenPerms(int deptId, String entidad) throws Exception
   {
      int genPerms;
      int prodId  = AcsProduct.IDOC;
      int dstType = AcsPermDst.DEPT; 
      
      genPerms = AcsDaoGenPermTbl.selectGenPerms(dstType, deptId, prodId, entidad);      
      
      return genPerms;
   }
   
   public static int getGroupiDocGenPerms(int groupId, String entidad) throws Exception
   {
      int genPerms;
      int prodId  = AcsProduct.IDOC;
      int dstType = AcsPermDst.GROUP; 
      
      genPerms = AcsDaoGenPermTbl.selectGenPerms(dstType, groupId, prodId, entidad);      
      
      return genPerms;
   }
   
   
   
} // class
