
package ieci.tecdoc.sbo.idoc.acs;

import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.acs.base.AcsAccessType;
import ieci.tecdoc.sbo.acs.base.AcsObjOwner;
import ieci.tecdoc.sbo.acs.base.AcsObjTokenHdr;
import ieci.tecdoc.sbo.acs.base.AcsPermission;
import ieci.tecdoc.sbo.acs.base.AcsProfile;
import ieci.tecdoc.sbo.acs.std.AcsMdoObj;
import ieci.tecdoc.sbo.acs.std.AcsMdoPerm;
import ieci.tecdoc.sbo.acs.std.AcsMdoProfile;
import ieci.tecdoc.sbo.idoc.dao.DaoArchHdrTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoFdrHdrTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoUtil;
import ieci.tecdoc.sbo.util.idoc8db.Idoc8DbAcsRec;
import ieci.tecdoc.sbo.util.types.SboType;
import ieci.tecdoc.sgm.base.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

public final class AcsMdoFolder
{

   private AcsMdoFolder()
   {
   }
   
   public static AcsInfo getAcsInfo(DbConnection dbConn, String tblPrefix, int fdrId)                                     
                         throws Exception
   {

      boolean       has     = false;      
      Idoc8DbAcsRec acsRow  = null; 
      AcsInfo       acsInfo = null; 
      String        tblName = DaoUtil.getFdrHdrTblName(tblPrefix);
      
      DaoFdrHdrTbl fdrHdrTbl = new DaoFdrHdrTbl(tblName);
      
      acsRow =  fdrHdrTbl.selectAcsInfo(dbConn, fdrId); 
      
      acsInfo = new AcsInfo(acsRow.m_acsType, acsRow.m_acsId);         
            
      return acsInfo;

   } 
   
   public static AcsInfo getAcsInfo(DbConnection dbConn, int archId, int fdrId)                                     
                         throws Exception
   {
      
      String        tblPrefix;      
      AcsInfo       acsInfo = null;   
      
      tblPrefix = DaoArchHdrTbl.selectTblPrefix(dbConn, archId);      
      
      acsInfo = getAcsInfo(dbConn, tblPrefix, fdrId);         
            
      return acsInfo;

   } 
   
   public static boolean hasAuthView(DbConnection dbConn, AcsAccessToken accToken, int archId, int fdrId)
                         throws Exception
   {

      boolean  has     = false; 
      AcsInfo  archAcs = null;
      AcsInfo  fdrAcs  = null;
      
      archAcs = AcsMdoArchive.getAcsInfo(dbConn, archId);   
            
      if (!AcsMdoArchive.hasAuthView(dbConn, accToken, archAcs))
         return false; 
         
      fdrAcs =  getAcsInfo(dbConn, archId, fdrId);
      
      if (fdrAcs.getAcsType() == AcsAccessType.ACS_PUBLIC)
         return true;
      
      has = hasProtectedFdrAuthView(dbConn, accToken, archAcs, fdrAcs); 
      
      return has;

   }
   
   public static boolean hasAuthView(DbConnection dbConn, AcsAccessToken accToken, AcsInfo archAcs, AcsInfo fdrAcs)
                         throws Exception
   {

      boolean  has = false;      
                  
      if (!AcsMdoArchive.hasAuthView(dbConn, accToken, archAcs))
         return false;          
            
      if (fdrAcs.getAcsType() == AcsAccessType.ACS_PUBLIC)
         return true;
      
      has = hasProtectedFdrAuthView(dbConn, accToken, archAcs, fdrAcs); 
      
      return has;

   }
   
   public static boolean hasAuthView(DbConnection dbConn, AcsAccessToken accToken, AcsTokenArchive acsArch,
                                     AcsInfo fdrAcs)
                         throws Exception
   {

      boolean  has      = false; 
      int      fdrAcsId = fdrAcs.getAcsId();
      
       
      if (!acsArch.hasPerm(AcsPermission.QUERY))
         return false;
      
      if (fdrAcs.getAcsType() == AcsAccessType.ACS_PUBLIC)
         return true;
         
      if (AcsMdoProfile.hasiDocProfile(accToken, AcsProfile.IDOC_SUPERUSER))
         return true;
         
      if (AcsMdoArchive.isArchManager(accToken, acsArch))
         return true;
         
      if (isFolderOwner(dbConn, accToken, fdrAcsId))
         return true;
      
      if (AcsMdoPerm.hasPermission(dbConn, accToken, fdrAcsId,
                                   AcsPermission.QUERY) )
         return true;           
      
      return has;

   }   
   
   public static boolean hasAuthDelete(DbConnection dbConn, AcsAccessToken accToken, int archId, int fdrId)
                         throws Exception
   {

      boolean  has     = false; 
      AcsInfo  archAcs = null;
      AcsInfo  fdrAcs  = null;
      
      archAcs = AcsMdoArchive.getAcsInfo(dbConn, archId);   
            
      if (!AcsMdoArchive.hasAuthDelete(dbConn, accToken, archAcs))
         return false; 
         
      fdrAcs =  getAcsInfo(dbConn, archId, fdrId);
      
      if (fdrAcs.getAcsType() == AcsAccessType.ACS_PUBLIC)
         return true;
      
      has = hasProtectedFdrAuthDelete(dbConn, accToken, archAcs, fdrAcs); 
      
      return has;

   }
   
   public static boolean hasAuthDelete(DbConnection dbConn, AcsAccessToken accToken, AcsInfo archAcs, AcsInfo fdrAcs)
                         throws Exception
   {

      boolean  has = false;      
                  
      if (!AcsMdoArchive.hasAuthDelete(dbConn, accToken, archAcs))
         return false;          
            
      if (fdrAcs.getAcsType() == AcsAccessType.ACS_PUBLIC)
         return true;
      
      has = hasProtectedFdrAuthDelete(dbConn, accToken, archAcs, fdrAcs); 
      
      return has;

   }
   
   public static boolean hasAuthDelete(DbConnection dbConn, AcsAccessToken accToken, AcsTokenArchive acsArch,
                                       AcsInfo fdrAcs)
                         throws Exception
   {

      boolean  has      = false; 
      int      fdrAcsId = fdrAcs.getAcsId();
      
      if (!acsArch.hasPerm(AcsPermission.DELETION))
         return false;
         
      if (fdrAcs.getAcsType() == AcsAccessType.ACS_PUBLIC)
         return true;
         
      if (AcsMdoProfile.hasiDocProfile(accToken, AcsProfile.IDOC_SUPERUSER))
         return true;
      
      if (AcsMdoArchive.isArchManager(accToken, acsArch))
         return true;
      
      if (isFolderOwner(dbConn, accToken, fdrAcsId))
         return true;
      
      if (AcsMdoPerm.hasPermission(dbConn, accToken, fdrAcsId,
                                   AcsPermission.DELETION) )
         return true;     
                
      
      return has;

   }  
   
   
   public static boolean hasAuthUpdate(DbConnection dbConn, AcsAccessToken accToken, int archId, int fdrId)
                         throws Exception
   {

      boolean  has     = false; 
      AcsInfo  archAcs = null;
      AcsInfo  fdrAcs  = null;
      
      archAcs = AcsMdoArchive.getAcsInfo(dbConn, archId);   
            
      if (!AcsMdoArchive.hasAuthUpdate(dbConn, accToken, archAcs))
         return false; 
         
      fdrAcs =  getAcsInfo(dbConn, archId, fdrId);
      
      if (fdrAcs.getAcsType() == AcsAccessType.ACS_PUBLIC)
         return true;
      
      has = hasProtectedFdrAuthUpdate(dbConn, accToken, archAcs, fdrAcs); 
      
      return has;

   }
   
   public static boolean hasAuthUpdate(DbConnection dbConn, AcsAccessToken accToken, AcsInfo archAcs,
                                       AcsInfo fdrAcs)
                         throws Exception
   {

      boolean has = false;       
            
      if (!AcsMdoArchive.hasAuthUpdate(dbConn, accToken, archAcs))
         return false; 
      
      if (fdrAcs.getAcsType() == AcsAccessType.ACS_PUBLIC)
         return true;
      
      has = hasProtectedFdrAuthUpdate(dbConn, accToken, archAcs, fdrAcs); 
      
      return has;

   }
   
   public static boolean hasAuthUpdate(DbConnection dbConn, AcsAccessToken accToken, AcsTokenArchive acsArch,
                                       AcsInfo fdrAcs)
                         throws Exception
   {

      boolean  has      = false; 
      int      fdrAcsId = fdrAcs.getAcsId();
      
      if (!acsArch.hasPerm(AcsPermission.UPDATE))
         return false;
         
      if (fdrAcs.getAcsType() == AcsAccessType.ACS_PUBLIC)
         return true;
         
      if (AcsMdoProfile.hasiDocProfile(accToken, AcsProfile.IDOC_SUPERUSER))
         return true;
      
      if (AcsMdoArchive.isArchManager(accToken, acsArch))
         return true;
      
      if (isFolderOwner(dbConn, accToken, fdrAcsId))
         return true;
      
      if (AcsMdoPerm.hasPermission(dbConn, accToken, fdrAcsId,
                                   AcsPermission.UPDATE) )
         return true;     
                
      
      return has;

   }  
   
   public static void deleteAcsFolder(AcsInfo fdrAcs, boolean initTrans, String entidad)
                      throws Exception
   {
      
      if (fdrAcs.getAcsType() == AcsAccessType.ACS_PUBLIC)
         return;
      
      AcsMdoObj.removeObj(fdrAcs.getAcsId(), initTrans, entidad);

   }     
   
   //******************************************************** 
   
   /* Sin chequear que el archivador se pueda ver */
   private static boolean hasProtectedFdrAuthView(DbConnection dbConn, AcsAccessToken accToken, AcsInfo archAcs,
                                                  AcsInfo fdrAcs)  
                          throws Exception
   {

      boolean  has      = false; 
      int      fdrAcsId = fdrAcs.getAcsId();
      
      if (AcsMdoProfile.hasiDocProfile(accToken, AcsProfile.IDOC_SUPERUSER))
         return true;
      
      if (AcsMdoArchive.isArchManager(dbConn, accToken, archAcs.getAcsId()))
         return true;
      
      if (isFolderOwner(dbConn, accToken, fdrAcsId))
         return true;
      
      if (AcsMdoPerm.hasPermission(dbConn, accToken, fdrAcsId,
                                   AcsPermission.QUERY) )
         return true;     
      
      return has;

   } 
   
   public static boolean hasProtectedFdrAuthDelete(DbConnection dbConn, AcsAccessToken accToken, 
                                                   AcsInfo archAcs,
                                                   AcsInfo fdrAcs)
                         throws Exception
   {

      boolean  has       = false; 
      int      fdrAcsId  = fdrAcs.getAcsId();
      int      archAcsId = archAcs.getAcsId();
      
      if (AcsMdoProfile.hasiDocProfile(accToken, AcsProfile.IDOC_SUPERUSER))
         return true;
      
      if (AcsMdoArchive.isArchManager(dbConn, accToken, archAcsId))
         return true;
      
      if (isFolderOwner(dbConn, accToken, fdrAcsId))
         return true;
      
      if (AcsMdoPerm.hasPermission(dbConn, accToken, fdrAcsId,
                                   AcsPermission.DELETION) )
         return true;
      
      return has;

   }
   
   private static boolean isFolderOwner(DbConnection dbConn, AcsAccessToken accToken,
                                        int fdrAcsId)
                          throws Exception
   {
      boolean                    is = false;
      AcsObjTokenHdr             objToken = null;
      IeciTdLongIntegerArrayList groupIds;
      int                        count, i;
      int                        userId, ownerId, deptId, groupId;
      
      objToken = AcsMdoObj.createObjTokenHdr(dbConn, fdrAcsId);
      
      ownerId = objToken.getOwnerId();
      
      if (objToken.getOwnerType() == AcsObjOwner.USER)
      {           
         userId = accToken.getUserId();
         if (userId == ownerId)
            return true;
      }
      
      if (objToken.getOwnerType() == AcsObjOwner.DEPT)
      {           
         deptId = accToken.getDeptId();
         if ( (deptId != SboType.NULL_ID) && (deptId == ownerId))
            return true;
      }
      
      if (objToken.getOwnerType() == AcsObjOwner.GROUP)
      {           
         groupIds = accToken.getGroupIds();
         
         count = groupIds.count();
         
         for (i = 0; i < count; i++)
         {
            groupId = groupIds.get(i);
            if (groupId == ownerId)
               return true;     
         }
      }
      
      return is;      
   }
   
   
   public static boolean hasProtectedFdrAuthUpdate(DbConnection dbConn, AcsAccessToken accToken, 
                                                   AcsInfo archAcs,
                                                   AcsInfo fdrAcs)
                         throws Exception
   {

      boolean  has       = false; 
      int      fdrAcsId  = fdrAcs.getAcsId();
      int      archAcsId = archAcs.getAcsId();
      
      if (AcsMdoProfile.hasiDocProfile(accToken, AcsProfile.IDOC_SUPERUSER))
         return true;
      
      if (AcsMdoArchive.isArchManager(dbConn, accToken, archAcsId))
         return true;
      
      if (isFolderOwner(dbConn, accToken, fdrAcsId))
         return true;
      
      if (AcsMdoPerm.hasPermission(dbConn, accToken, fdrAcsId,
                                   AcsPermission.UPDATE) )
         return true;
      
      return has;
   }
   
 
   
   
} // class
