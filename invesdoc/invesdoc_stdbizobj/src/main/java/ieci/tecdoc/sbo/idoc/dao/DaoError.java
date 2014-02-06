
package ieci.tecdoc.sbo.idoc.dao;

public final class DaoError
{
   
   private static final String EC_PREFIX = "IECI_TECDOC_IDOC_DAO";   
   
   // **************************************************************************
        
   public static final String  EC_INVALID_PARAM  = 
   EC_PREFIX +  "INVALID_PARAM";

   public static final String  EM_INVALID_PARAM  = 
   "Invalid parameter";
   
   // **************************************************************************

   public static final String  EC_NOT_FOUND  = 
   EC_PREFIX +  "NOT_FOUND";

   public static final String  EM_NOT_FOUND  = 
   "Not found";
   
  //***********************************************************************
  
  

   private DaoError()
   {
   }
   
} // class
