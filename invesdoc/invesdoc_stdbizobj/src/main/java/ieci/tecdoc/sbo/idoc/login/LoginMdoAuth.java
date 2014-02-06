
package ieci.tecdoc.sbo.idoc.login;

import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.acs.base.AcsProfile;
import ieci.tecdoc.sbo.acs.std.AcsMdoProfile;

public final class LoginMdoAuth
{

   private LoginMdoAuth()
   {
   }
   
   public static boolean hasAuthEnteriDocApp(AcsAccessToken accToken)
                         throws Exception
   {
      
      boolean  has = false; 
      String   profile;
      
      if (AcsMdoProfile.hasiDocProfile(accToken, AcsProfile.IDOC_STANDARD))
         has = true;      
      
      return has;
            
   } 

   public static boolean hasAuthEnteriDocAdmApp(AcsAccessToken accToken)
                         throws Exception
   {
      
      boolean  has = false; 
      String   profile;
      
      if (AcsMdoProfile.hasiDocProfile(accToken, AcsProfile.IDOC_MANAGER))
         has = true;      
      
      return has;
            
   } 

   public static boolean hasAuthEnteriUserAdmApp(AcsAccessToken accToken)
                         throws Exception
   {
      
      boolean  has = false; 
      String   profile;
      
      if (AcsMdoProfile.hasiUserProfile(accToken, AcsProfile.IUSER_STANDARD))
         has = true;      
      
      return has;
            
   } 

   public static boolean hasAuthEnteriVolAdmApp(AcsAccessToken accToken)
                         throws Exception
   {
      
      boolean  has = false; 
      String   profile;
      
      if (AcsMdoProfile.hasiVolProfile(accToken, AcsProfile.IVOL_SUPERUSER))
         has = true;      
      
      return has;
            
   } 
   
} // class
