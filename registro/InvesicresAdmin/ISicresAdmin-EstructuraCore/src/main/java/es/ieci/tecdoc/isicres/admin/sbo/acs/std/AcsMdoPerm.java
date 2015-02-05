
package es.ieci.tecdoc.isicres.admin.sbo.acs.std;


import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdLongIntegerArrayList;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsAccessToken;
import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsPermDst;
import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsPermission;
import es.ieci.tecdoc.isicres.admin.sbo.util.types.SboType;

public final class AcsMdoPerm
{

   private AcsMdoPerm()
   {
   }  
   
   public static boolean permissionExists(DbConnection dbConn, int dstType, int dstId, int objId,
                                          int perm)
                         throws Exception
   {         
      return AcsDaoObjPermTbl.permExists(dbConn, dstType, dstId, objId, perm);
   }
   
   public static boolean hasPermission(DbConnection dbConn, AcsAccessToken accToken,
                                       int objId, int perm)
                         throws Exception
   {
      
      boolean                    hasPerm = false;
      IeciTdLongIntegerArrayList groupIds;
      int                        dstId;
      int                        count, i;
           
      dstId = accToken.getUserId();
      if (permissionExists(dbConn, AcsPermDst.USER, dstId, objId, perm))
         return true;
      
      dstId = accToken.getDeptId();
      if (dstId != SboType.NULL_ID)
      {
         if (permissionExists(dbConn, AcsPermDst.DEPT, dstId, objId, perm))
            return true; 
      }
      
      groupIds = accToken.getGroupIds();
      
      count = groupIds.count();
      
      for (i = 0; i < count; i++)
      {
         dstId = groupIds.get(i);
         if (permissionExists(dbConn, AcsPermDst.GROUP, dstId, objId, perm))
            return true;        
      }
      
      return hasPerm;
            
   }
   
   public static IeciTdLongIntegerArrayList getPermissions(DbConnection dbConn, AcsAccessToken accToken,
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
      userPerms = AcsDaoObjPermTbl.selectPerms(dbConn, AcsPermDst.USER, dstId, objId);
      
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
         deptPerms = AcsDaoObjPermTbl.selectPerms(dbConn, AcsPermDst.DEPT, dstId, objId);
      
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
         
         groupPerms = AcsDaoObjPermTbl.selectPerms(dbConn, AcsPermDst.GROUP, dstId, objId);
      
         for(i = 0; i < groupPerms.count(); i++)
         {
            perm = groupPerms.get(i);
            
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
