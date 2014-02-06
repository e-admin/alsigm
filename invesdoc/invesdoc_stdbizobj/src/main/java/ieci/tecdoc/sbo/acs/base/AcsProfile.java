
package ieci.tecdoc.sbo.acs.base;

public final class AcsProfile
{   
   // **************************************************************************
       
   public static final String SYS_XSUPERUSER =  "SYS_XSUPERUSER";
   
   public static final String SYS_SUPERUSER  =  "SYS_SUPERUSER";
   
   // **************************************************************************
       
   public static final String IDOC_SUPERUSER =  "IDOC_SUPERUSER";
   
   public static final String IDOC_MANAGER   =  "IDOC_MANAGER";
       
   public static final String IDOC_STANDARD  =  "IDOC_STANDARD";
   
   public static final String IDOC_NONE      =  "IDOC_NONE";

   // **************************************************************************
       
   public static final String IUSER_SUPERUSER =  "IUSER_SUPERUSER";
   
   public static final String IUSER_MANAGER   =  "IUSER_MANAGER";
       
   public static final String IUSER_STANDARD  =  "IUSER_STANDARD";
   
   public static final String IUSER_NONE      =  "IUSER_NONE";

   // **************************************************************************
       
   public static final String IVOL_SUPERUSER =  "IVOL_SUPERUSER"; 
   
   public static final String IVOL_NONE      =  "IVOL_NONE";   
   
      
   // **************************************************************************
 
   private AcsProfile()
   {
   }
   
   // **************************************************************************
   
   public static boolean isSysSuperuserProfile(String profile)
   {
      if (SYS_XSUPERUSER.equals(profile) || SYS_SUPERUSER.equals(profile))
         return true;
      else
         return false;
   }
   
} // class
