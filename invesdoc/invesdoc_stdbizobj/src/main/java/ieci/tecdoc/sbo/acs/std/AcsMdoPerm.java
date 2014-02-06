
package ieci.tecdoc.sbo.acs.std;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.acs.base.AcsPermDst;
import ieci.tecdoc.sbo.util.types.SboType;
import ieci.tecdoc.sbo.acs.base.AcsPermission;

public final class AcsMdoPerm
{

   private AcsMdoPerm()
   {
   }  
   
   public static boolean permissionExists(int dstType, int dstId, int objId,
                                          int perm)
                         throws Exception
   {         
      return AcsDaoObjPermTbl.permExists(dstType, dstId, objId, perm);
   }
   
   public static boolean hasPermission(AcsAccessToken accToken,
                                       int objId, int perm)
                         throws Exception
   {
      
      boolean                    hasPerm = false;
      IeciTdLongIntegerArrayList groupIds;
      int                        dstId;
      int                        count, i;
           
      dstId = accToken.getUserId();
      if (permissionExists(AcsPermDst.USER, dstId, objId, perm))
         return true;
      
      dstId = accToken.getDeptId();
      if (dstId != SboType.NULL_ID)
      {
         if (permissionExists(AcsPermDst.DEPT, dstId, objId, perm))
            return true; 
      }
      
      groupIds = accToken.getGroupIds();
      
      count = groupIds.count();
      
      for (i = 0; i < count; i++)
      {
         dstId = groupIds.get(i);
         if (permissionExists(AcsPermDst.GROUP, dstId, objId, perm))
            return true;        
      }
      
      return hasPerm;
            
   }
   
   public static IeciTdLongIntegerArrayList getPermissions(AcsAccessToken accToken,
                                                           int objId, 
                                                           AcsCrtrPermInfo crtrInfo)
                                            throws Exception
   {
       
      IeciTdLongIntegerArrayList perms     = new IeciTdLongIntegerArrayList(); 
      IeciTdLongIntegerArrayList userPerms; 
      IeciTdLongIntegerArrayList deptPerms;
      IeciTdLongIntegerArrayList groupPerms;        
      IeciTdLongIntegerArrayList groupIds;
      int                        dstId;
      int                        count, i;
      int                        perm;
                 
      dstId = accToken.getUserId();
      userPerms = AcsDaoObjPermTbl.selectPerms(AcsPermDst.USER, dstId, objId);
      
      for(i = 0; i < userPerms.count(); i++)
      {
         perm = userPerms.get(i);
         perms.add(perm);         
         
         if (perm == AcsPermission.CREATION)
         {
            crtrInfo.setType(AcsPermDst.USER);
            crtrInfo.setId(dstId);            
         }
      }
      
      
      dstId = accToken.getDeptId();
      if (dstId != SboType.NULL_ID)
      {
         deptPerms = AcsDaoObjPermTbl.selectPerms(AcsPermDst.DEPT, dstId, objId);
      
         for(i = 0; i < deptPerms.count(); i++)
         {
            perm = deptPerms.get(i);
            
            if (!perms.find(perm))
            {
               perms.add(perm);
         
               if (perm == AcsPermission.CREATION)
               {
                  crtrInfo.setType(AcsPermDst.DEPT);
                  crtrInfo.setId(dstId);                  
               }
            }
         }          
      }
      
      groupIds = accToken.getGroupIds();
      
      count = groupIds.count();
      
      for (i = 0; i < count; i++)
      {
         dstId = groupIds.get(i);
         
         groupPerms = AcsDaoObjPermTbl.selectPerms(AcsPermDst.GROUP, dstId, objId);
      
         for(int j = 0; j < groupPerms.count(); j++)
         {
            perm = groupPerms.get(j);
            
            if (!perms.find(perm))
            {
               perms.add(perm);
               
               if (perm == AcsPermission.CREATION)
               {
                  crtrInfo.setType(AcsPermDst.GROUP);
                  crtrInfo.setId(dstId);                 
               }
            }
         } 
                
      }
      
      return perms;
            
   }
   
   
   
} // class
