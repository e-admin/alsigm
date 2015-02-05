package ieci.tecdoc.core.search;

public final class SearchError
{

   private static final String EC_PREFIX        = "IECI_TECDOC_CORE_SEARCH_";
   
   // **************************************************************************

   public static final String  EC_INVALID_PARAM  = 
   EC_PREFIX +  "INVALID_PARAM";

   public static final String  EM_INVALID_PARAM  = 
   "Invalid parameter";
   
   // **************************************************************************
   
   public static final String EC_NO_MORE_ROWS = 
   EC_PREFIX + "NO_MORE_FOLDERS"; 

   public static final String EM_NO_MORE_ROWS =
   "Not exists more rows";

   // **************************************************************************

   private SearchError()
   {
   }

} // class
