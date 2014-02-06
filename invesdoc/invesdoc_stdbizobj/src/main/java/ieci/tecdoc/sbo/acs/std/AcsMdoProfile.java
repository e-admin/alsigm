
package ieci.tecdoc.sbo.acs.std;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.db.DbError;
import ieci.tecdoc.sbo.acs.base.AcsDefs;
import ieci.tecdoc.sbo.acs.base.AcsProfile;
import ieci.tecdoc.sbo.acs.base.AcsProduct;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;

public final class AcsMdoProfile
{
   public static final int  USER_TYPE_NONE       = 0;
   public static final int  USER_TYPE_STANDARD   = 1;
   public static final int  USER_TYPE_MANAGER    = 2;
   public static final int  USER_TYPE_SUPERUSER  = 3;

   private AcsMdoProfile()
   {
   }
   
   
   public static String getiDocProfile(int userId)
                        throws Exception
   {
      String profile = null;
      int    type;
      
      if (userId == AcsDefs.SYS_XSUPERUSER_ID) 
         profile = AcsProfile.SYS_XSUPERUSER;
      else
      {
         if ( isSysSuperuserProfile(userId) )
            profile = AcsProfile.SYS_SUPERUSER;
         else
         {
            type = AcsDaoUserTypeTbl.selectType(userId, AcsProduct.IDOC);
            
            profile = convertiDocTypeToProfile(type);
         }
         
      }        
      
      return profile;
   }
   
   public static boolean hasiDocProfile(AcsAccessToken accToken,
                                        String profile)
                         throws Exception
   {
      
      boolean has = false; 
      
      if (profile.equals(AcsProfile.IDOC_SUPERUSER))
      {         
         if (isDocSuperUserProfile(accToken))
            has = true;
      }
      else if (profile.equals(AcsProfile.IDOC_MANAGER))
      {         
         if (isDocManagerProfile(accToken))
            has = true;
      }
      else if (profile.equals(AcsProfile.IDOC_STANDARD))
      {  
         if (isDocStandardProfile(accToken))
            has = true;
      }
      else
      {  
         throw new IeciTdException(AcsError.EC_INVALID_PARAM,
                                   AcsError.EM_INVALID_PARAM);
      }
     
      return has;
      
   }
   
   public static boolean isSysSuperuserProfile(int userId)
                         throws Exception
   {
      
      boolean is = false;
      int     type;
      
      try
      {         
         type = AcsDaoUserTypeTbl.selectType(userId, AcsProduct.ISYS);
         
         if (type == USER_TYPE_SUPERUSER)
            is = true;
      }
      catch (IeciTdException e)
      {
         if (e.getErrorCode().equals(DbError.EC_NOT_FOUND))
            is = false;
         else
            throw e;
      }
      
      return is;
      
   } 

   public static String getiUserProfile(int userId)
                        throws Exception
   {
      String profile = null;
      int    type;
      
      if (userId == AcsDefs.SYS_XSUPERUSER_ID) 
         profile = AcsProfile.SYS_XSUPERUSER;
      else
      {
         if ( isSysSuperuserProfile(userId) )
            profile = AcsProfile.SYS_SUPERUSER;
         else
         {
            type = AcsDaoUserTypeTbl.selectType(userId, AcsProduct.IUSER);
            
            profile = convertiUserTypeToProfile(type);
         }
         
      }        
      
      return profile;
   }
   
   public static boolean hasiUserProfile(AcsAccessToken accToken,
                                        String profile)
                         throws Exception
   {
      
      boolean has = false; 
      
      if (profile.equals(AcsProfile.IUSER_SUPERUSER))
      {         
         if (isUserSuperUserProfile(accToken))
            has = true;
      }
      else if (profile.equals(AcsProfile.IUSER_MANAGER))
      {         
         if (isUserManagerProfile(accToken))
            has = true;
      }
      else if (profile.equals(AcsProfile.IUSER_STANDARD))
      {  
         if (isUserStandardProfile(accToken))
            has = true;
      }
      else
      {  
         throw new IeciTdException(AcsError.EC_INVALID_PARAM,
                                   AcsError.EM_INVALID_PARAM);
      }
     
      return has;
      
   } 

   public static String getiVolProfile(int userId)
                        throws Exception
   {
      String profile = null;
      int    type;
      
      if (userId == AcsDefs.SYS_XSUPERUSER_ID) 
         profile = AcsProfile.SYS_XSUPERUSER;
      else
      {
         if ( isSysSuperuserProfile(userId) )
            profile = AcsProfile.SYS_SUPERUSER;
         else
         {
            type = AcsDaoUserTypeTbl.selectType(userId, AcsProduct.IVOL);
            
            profile = convertiVolTypeToProfile(type);
         }
         
      }        
      
      return profile;
   }
   
   public static boolean hasiVolProfile(AcsAccessToken accToken,
                                        String profile)
                         throws Exception
   {
      
      boolean has = false; 
      
      if (profile.equals(AcsProfile.IVOL_SUPERUSER))
      {         
         if (isVolSuperUserProfile(accToken))
            has = true;
      }
     
      return has;
      
   } 
   
   //****************************************************************
   
   private static String convertiDocTypeToProfile(int type)
                         throws Exception
   {
      
      String profile = null;
      
      switch(type)
      {
         case USER_TYPE_NONE:
         
            profile = AcsProfile.IDOC_NONE;
            break;
            
         case USER_TYPE_STANDARD:         
         
            profile = AcsProfile.IDOC_STANDARD;
            break;
            
         case USER_TYPE_MANAGER:         
         
            profile = AcsProfile.IDOC_MANAGER;
            break;            
            
         case USER_TYPE_SUPERUSER:         
         
            profile = AcsProfile.IDOC_SUPERUSER;
            break;
            
         default:
         
            throw new IeciTdException(AcsError.EC_INVALID_PARAM,
                                      AcsError.EM_INVALID_PARAM);
      }
      
      return profile;    
           
   }  
   
   //****************************************************************
   
   public static int convertProfileToiDocType(String profile)
                         throws Exception
   {
      
      int iDocType = USER_TYPE_NONE;
      
      if (profile.equals(AcsProfile.IDOC_NONE))
      {
         iDocType = USER_TYPE_NONE;
         return iDocType;
      }
            
      if (profile.equals(AcsProfile.IDOC_STANDARD))
      {
         iDocType = USER_TYPE_STANDARD;
         return iDocType;
      }
            
      if (profile.equals(AcsProfile.IDOC_MANAGER))
      {
         iDocType = USER_TYPE_MANAGER;
         return iDocType;
      }
            
      if (profile.equals(AcsProfile.IDOC_SUPERUSER))
      {
         iDocType = USER_TYPE_SUPERUSER;
         return iDocType;
      }
            
      return iDocType;    
           
   }  
   
   private static boolean isDocSuperUserProfile(AcsAccessToken accToken)
                          throws Exception
   {
      
      boolean is = false;
      String  profile;
      
      profile = accToken.getProfile();
      
      if ( AcsProfile.isSysSuperuserProfile(profile) )
         is = true;
      else
      {
         if (AcsProfile.IDOC_SUPERUSER.equals(profile))
            is = true;
      }
      
      return is;

   }
   
   private static boolean isDocManagerProfile(AcsAccessToken accToken)
                          throws Exception
   {

      boolean is = false;
      String  profile;      

      if ( isDocSuperUserProfile(accToken) )
         is = true;
      else
      {
         profile = accToken.getProfile();
         
         if (AcsProfile.IDOC_MANAGER.equals(profile))
            is = true;
      }

      return is;

   }
   
   private static boolean isDocStandardProfile(AcsAccessToken accToken)
                          throws Exception
   {

      boolean is = false;
      String  profile;      

      if ( isDocManagerProfile(accToken) )
         is = true;
      else
      {
         profile = accToken.getProfile();
         
         if (AcsProfile.IDOC_STANDARD.equals(profile))
            is = true;
      }

      return is;

   }

   //****************************************************************
   
   private static String convertiUserTypeToProfile(int type)
                         throws Exception
   {
      
      String profile = null;
      
      switch(type)
      {
         case USER_TYPE_NONE:
         
            profile = AcsProfile.IUSER_NONE;
            break;
            
         case USER_TYPE_STANDARD:         
         
            profile = AcsProfile.IUSER_STANDARD;
            break;
            
         case USER_TYPE_MANAGER:         
         
            profile = AcsProfile.IUSER_MANAGER;
            break;            
            
         case USER_TYPE_SUPERUSER:         
         
            profile = AcsProfile.IUSER_SUPERUSER;
            break;
            
         default:
         
            throw new IeciTdException(AcsError.EC_INVALID_PARAM,
                                      AcsError.EM_INVALID_PARAM);
      }
      
      return profile;    
           
   } 

   private static boolean isUserSuperUserProfile(AcsAccessToken accToken)
                          throws Exception
   {
      
      boolean is = false;
      String  profile;
      
      profile = accToken.getProfile();
      
      if ( AcsProfile.isSysSuperuserProfile(profile) )
         is = true;
      else
      {
         if (AcsProfile.IUSER_SUPERUSER.equals(profile))
            is = true;
      }
      
      return is;

   }

   private static boolean isUserManagerProfile(AcsAccessToken accToken)
                          throws Exception
   {

      boolean is = false;
      String  profile;      

      if ( isUserSuperUserProfile(accToken) )
         is = true;
      else
      {
         profile = accToken.getProfile();
         
         if (AcsProfile.IUSER_MANAGER.equals(profile))
            is = true;
      }

      return is;

   }
   
   private static boolean isUserStandardProfile(AcsAccessToken accToken)
                          throws Exception
   {

      boolean is = false;
      String  profile;      

      if ( isUserManagerProfile(accToken) )
         is = true;
      else
      {
         profile = accToken.getProfile();
         
         if (AcsProfile.IUSER_STANDARD.equals(profile))
            is = true;
      }

      return is;

   }
   
   //****************************************************************

   private static String convertiVolTypeToProfile(int type)
                         throws Exception
   {
      
      String profile = null;
      
      switch(type)
      {
         case USER_TYPE_NONE:
         
            profile = AcsProfile.IVOL_NONE;
            break;         
            
         case USER_TYPE_SUPERUSER:         
         
            profile = AcsProfile.IVOL_SUPERUSER;
            break;
            
         default:
         
            throw new IeciTdException(AcsError.EC_INVALID_PARAM,
                                      AcsError.EM_INVALID_PARAM);
      }
      
      return profile;    
           
   } 

   private static boolean isVolSuperUserProfile(AcsAccessToken accToken)
                          throws Exception
   {
      
      boolean is = false;
      String  profile;
      
      profile = accToken.getProfile();
      
      if ( AcsProfile.isSysSuperuserProfile(profile) )
         is = true;
      else
      {
         if (AcsProfile.IVOL_SUPERUSER.equals(profile))
            is = true;
      }
      
      return is;

   }  
   
   
} // class
