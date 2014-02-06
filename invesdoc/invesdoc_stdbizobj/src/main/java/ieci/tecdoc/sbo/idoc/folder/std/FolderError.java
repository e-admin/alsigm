
package ieci.tecdoc.sbo.idoc.folder.std;

public final class FolderError
{
   
   private static final String EC_PREFIX = "IECI_TECDOC_IDOC_FOLDER_";   
   
   // **************************************************************************

   public static final String  EC_NOT_AUTH_LOAD_FDR =
   EC_PREFIX + "NOT_AUTH_LOAD_FOLDER";

   public static final String  EM_NOT_AUTH_LOAD_FDR = 
   "Not authorized to query folder";
   
// **************************************************************************

   public static final String  EC_NOT_AUTH_REMOVE_FDR =
   EC_PREFIX + "NOT_AUTH_REMOVE_FOLDER";

   public static final String  EM_NOT_AUTH_REMOVE_FDR = 
   "Not authorized to remove folder";

// **************************************************************************

   public static final String  EC_NOT_AUTH_CREATE_FDR =
   EC_PREFIX + "NOT_AUTH_CREATE_FOLDER";

   public static final String  EM_NOT_AUTH_CREATE_FDR = 
   "Not authorized to create folders";
   
   // **************************************************************************

   public static final String  EC_NOT_AUTH_EDIT_FDR =
   EC_PREFIX + "NOT_AUTH_EDIT_FOLDER";

   public static final String  EM_NOT_AUTH_EDIT_FDR = 
   "Not authorized to edit folder";
   
   // **************************************************************************

   public static final String  EC_NOT_FDR_NEW =
   EC_PREFIX + "NOT_FDR_NEW";

   public static final String  EM_NOT_FDR_NEW = 
   "Folder already exists";
   
   // **************************************************************************

   public static final String  EC_FDR_IS_NEW =
   EC_PREFIX + "FDR_IS_NEW";

   public static final String  EM_FDR_IS_NEW = 
   "Folder is new";

   
   // **************************************************************************
        
   public static final String EC_FDR_ALREADY_LOCKED = 
   EC_PREFIX + "FDR_ALREADY_LOCKED"; 
   
   public static final String EM_FDR_ALREADY_LOCKED =
   "Folder already locked";
   
   // **************************************************************************
   
   public static final String EC_FDR_NOT_LOCKED_BY_USER = 
   EC_PREFIX + "FDR_NOT_LOCKED_BY_USER"; 
   
   public static final String EM_FDR_NOT_LOCKED_BY_USER =
   "Folder is not locked by user";
   
   // **************************************************************************
   
   public static final String EC_FDR_IS_READ_ONLY = 
   EC_PREFIX + "FDR_IS_READ_ONLY"; 
   
   public static final String EM_FDR_IS_READ_ONLY =
   "Folder is readonly";

   // **************************************************************************
   
   public static final String EC_FDR_IS_REFERENCED_BY_OTHER = 
   EC_PREFIX + "FDR_IS_REFERENCED_BY_OTHER"; 
   
   public static final String EM_FDR_IS_REFERENCED_BY_OTHER =
   "Folder is being referenced by other folder";
   
   // **************************************************************************

   private FolderError()
   {
   }
   
} // class
