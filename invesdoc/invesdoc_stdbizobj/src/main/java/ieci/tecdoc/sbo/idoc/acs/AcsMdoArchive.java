
package ieci.tecdoc.sbo.idoc.acs;

import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.acs.base.AcsAccessType;
import ieci.tecdoc.sbo.acs.base.AcsProfile;
import ieci.tecdoc.sbo.acs.base.AcsPermission;
import ieci.tecdoc.sbo.acs.std.AcsMdoProfile;
import ieci.tecdoc.sbo.acs.std.AcsMdoPerm;
import ieci.tecdoc.sbo.acs.std.AcsMdoObj;
import ieci.tecdoc.sbo.acs.base.AcsObjTokenHdr;
import ieci.tecdoc.sbo.acs.base.AcsObjOwner;
import ieci.tecdoc.sbo.acs.base.AcsPermDst;
import ieci.tecdoc.sbo.acs.std.AcsCrtrPermInfo;
import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.sbo.util.idoc8db.Idoc8DbAcsRec;
import ieci.tecdoc.sbo.idoc.dao.DaoArchHdrTbl;

public final class AcsMdoArchive
{

   private AcsMdoArchive()
   {
   }
   
   public static AcsInfo getAcsInfo(int archId)                                     
                         throws Exception
   {

      boolean       has = false;
      Idoc8DbAcsRec acsRow = null;  
      AcsInfo       acsInfo = null;       
      
      acsRow =  DaoArchHdrTbl.selectAcsInfo(archId); 
      
      acsInfo = new AcsInfo(acsRow.m_acsType, acsRow.m_acsId);     
            
      return acsInfo;

   }    
   
   public static boolean hasAuthView(AcsAccessToken accToken, int archId)                                     
                         throws Exception
   {

      boolean  has     = false;
      AcsInfo  acsInfo = null; 
      
      acsInfo =  getAcsInfo(archId);
      
      has = hasAuthView(accToken, acsInfo);      
      
      return has;

   }   
   
   
   public static boolean hasAuthView(AcsAccessToken accToken, AcsInfo archAcs)                                     
                         throws Exception
   {

      boolean  has = false; 
      int      archAcsType = archAcs.getAcsType();   
      int      archAcsId   = archAcs.getAcsId();        
      
      if (archAcsType == AcsAccessType.ACS_PUBLIC)
         has = true;
      
      if (AcsMdoProfile.hasiDocProfile(accToken, AcsProfile.IDOC_SUPERUSER))
         return true;
      
      if (isArchManager(accToken, archAcsId))
         return true;
      
      if (AcsMdoPerm.hasPermission(accToken, archAcsId,
                                   AcsPermission.QUERY) )
         return true;
      
      return has;

   } 
   
   public static boolean hasAuthDelete(AcsAccessToken accToken, 
                                       AcsInfo archAcs)
                         throws Exception
   {

      boolean  has = false;
      int      archAcsId   = archAcs.getAcsId(); 
      int      archAcsType = archAcs.getAcsType();
      
      if (archAcsType == AcsAccessType.ACS_PUBLIC)
         has = true;  
      
      if (AcsMdoProfile.hasiDocProfile(accToken, AcsProfile.IDOC_SUPERUSER))
         return true;
      
      if (isArchManager(accToken, archAcsId))
         return true;
      
      if (AcsMdoPerm.hasPermission(accToken, archAcsId,
                                   AcsPermission.DELETION))
         return true;
      
      return has;

   }
   
   public static boolean hasAuthCreate(AcsAccessToken accToken, 
                                       int archId)
                         throws Exception
   {

      boolean  has     = false;      
      AcsInfo  acsInfo = null; 
      
      acsInfo =  getAcsInfo(archId);
      
      has = hasAuthCreate(accToken, acsInfo); 
      
      return has;

   }
   
   public static boolean hasAuthCreate(AcsAccessToken accToken, 
                                       AcsInfo  archAcs)
                         throws Exception
   {

      boolean  has         = false; 
      int      archAcsId   = archAcs.getAcsId(); 
      int      archAcsType = archAcs.getAcsType();
      
      if (archAcsType == AcsAccessType.ACS_PUBLIC)
         has = true;  
      
      if (AcsMdoProfile.hasiDocProfile(accToken, AcsProfile.IDOC_SUPERUSER))
         return true;
      
      if (isArchManager(accToken, archAcsId))
         return true;
      
      if (AcsMdoPerm.hasPermission(accToken, archAcsId,
                                   AcsPermission.CREATION))
         return true;
      
      return has;

   }
   
   public static boolean hasAuthCreate(AcsAccessToken accToken, 
                                       AcsTokenArchive acsArch)
                         throws Exception
   {

      boolean  has  = true;      
            
      if (!acsArch.hasPerm(AcsPermission.CREATION))
         return false;
            
      return has;

   }
   
   public static boolean hasAuthUpdate(AcsAccessToken accToken, 
                                       int archId)
                         throws Exception
   {

      boolean  has     = false;      
      AcsInfo  acsInfo = null; 
      
      acsInfo =  getAcsInfo(archId);
      
      has = hasAuthUpdate(accToken, acsInfo); 
      
      return has;

   }
   
   public static boolean hasAuthUpdate(AcsAccessToken accToken, 
                                       AcsInfo  archAcs)
                         throws Exception
   {

      boolean  has         = false; 
      int      archAcsId   = archAcs.getAcsId(); 
      int      archAcsType = archAcs.getAcsType();
      
      if (archAcsType == AcsAccessType.ACS_PUBLIC)
         has = true;  
      
      if (AcsMdoProfile.hasiDocProfile(accToken, AcsProfile.IDOC_SUPERUSER))
         return true;
      
      if (isArchManager(accToken, archAcsId))
         return true;
      
      if (AcsMdoPerm.hasPermission(accToken, archAcsId,
                                   AcsPermission.UPDATE) )
         return true;
      
      return has;

   } 
   
   public static AcsTokenArchive createAcsToken(AcsAccessToken accToken, 
                                                int archAcsId)
                                 throws Exception
   
   {      
      AcsObjTokenHdr             acsArchObj    = null;     
      int                        i,count;
      int                        mgrId;
      int                        crtrId, crtrType;
      AcsCrtrPermInfo            crtrInfo  = null;
      IeciTdLongIntegerArrayList perms     = null;
      AcsTokenArchive            token     = null;     
      
      acsArchObj = AcsMdoObj.createObjTokenHdr(archAcsId);
      
      if (AcsMdoProfile.hasiDocProfile(accToken, AcsProfile.IDOC_SUPERUSER)
          || (isArchManager(accToken, acsArchObj)) )
      {
         perms = AcsPermission.getAllPermissions();
         
         crtrId   = accToken.getUserId();
         crtrType = AcsPermDst.USER;
      }
      else
      {
      
         crtrInfo = new AcsCrtrPermInfo();
         
         perms  = AcsMdoPerm.getPermissions(accToken, archAcsId,
                                            crtrInfo);
         
         crtrId   = crtrInfo.getId();
         crtrType = crtrInfo.getType();
         
      }
      
      mgrId = acsArchObj.getOwnerId();
      
      token = new AcsTokenArchive(archAcsId, mgrId, perms, crtrId,
                                  crtrType);
             
           
      return token;    
      
   }  
   
   //********************************************************
 
   protected static boolean isArchManager(AcsAccessToken accToken,
                                          int acsId)
                          throws Exception
   {
      boolean is = false;
      
      is = AcsMdoObj.isUserObjOwner(acsId, accToken.getUserId());
      
      return is;      
   }
   
   protected static boolean isArchManager(AcsAccessToken accToken,
                                          AcsObjTokenHdr archObj)
                          throws Exception
   {
      boolean is = false;
      
      if ( (archObj.getOwnerType() == AcsObjOwner.USER) &&
           (archObj.getOwnerId() == accToken.getUserId()) )
      {
         is = true;
      }
      
      return is;      
   }
   
   protected static boolean isArchManager(AcsAccessToken accToken,
                                          AcsTokenArchive acsArch)
                          throws Exception
   {
      boolean is = false;
      
      if ( acsArch.getMgrId() == accToken.getUserId() )
      {
         is = true;
      }
      
      return is;      
   }
   
   
   
} // class
