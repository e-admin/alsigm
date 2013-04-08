
package ieci.tecdoc.sbo.idoc.archive.std;

public final class ArchiveError
{
   
   private static final String EC_PREFIX = "IECI_TECDOC_IDOC_ARCHIVE_";    
   
// **************************************************************************

   public static final String  EC_NOT_AUTH_LOAD_ARCHIVE = 
   EC_PREFIX + "NOT_AUTH_LOAD_ARCHIVE";

   public static final String  EM_NOT_AUTH_LOAD_ARCHIVE =
   "Not authorized to query archive";

   
   // **************************************************************************

   private ArchiveError()
   {
   }
   
} // class
