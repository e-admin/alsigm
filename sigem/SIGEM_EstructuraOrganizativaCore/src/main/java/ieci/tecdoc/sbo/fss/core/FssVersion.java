
package ieci.tecdoc.sbo.fss.core;


public final class FssVersion
{
   private static final String PACKAGE_VERSION      = "8.9.2";
   private static final String COMPANY_NAME         = "Informática El Corte Inglés, S.A.";
   private static final String PACKAGE_DESCRIPTION  = "Beta";   
   private static final String LEGAL_COPYRIGHT      = "Copyright ©  Informática El Corte Inglés, S.A. 1990-2005. Reservados todos los derechos.";   
     
   private FssVersion() 
   {      
   }
   
   public static String getPackageVersion()
   {
      return PACKAGE_VERSION;
   }

   public static String getPackageDescription()
   {
      return PACKAGE_DESCRIPTION;
   }

   public static String getCompanyName()
   {
      return COMPANY_NAME;
   }

   public static String getLegalCopyRight()
   {
      return LEGAL_COPYRIGHT;
   }
   
} // class
