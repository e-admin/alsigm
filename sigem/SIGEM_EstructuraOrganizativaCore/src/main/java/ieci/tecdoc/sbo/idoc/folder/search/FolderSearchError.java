
package ieci.tecdoc.sbo.idoc.folder.search;

/**
 * Recoge las descripciones de los errores más comunes en una búsqueda
 * @author
 */

public final class FolderSearchError
{
   
   private static final String EC_PREFIX = "IECI_TECDOC_IDOC_FOLDER_SEARCH_";   
   
   // **************************************************************************
        
   /**
    * Código: parámetro inválido
    */
   public static final String EC_INVALID_PARAM = 
   EC_PREFIX + "INVALID_PARAM"; 
   
   /**
    * Mensaje: parámetro inválido
    */
   public static final String EM_INVALID_PARAM =
   "Invalid Param";
   
   // **************************************************************************
   
   /**
    * Código: campo inválido
    */
   public static final String EC_INVALID_SEARCH_FLD = 
   EC_PREFIX + "INVALID_SEARCH_FLD"; 
   
   /**
    * Mensaje: campo inválido
    */   
   public static final String EM_INVALID_SEARCH_FLD =
   "Incorrect field to search ";

   // **************************************************************************
   
   /**
    * Código: operador inválido
    */   
   public static final String EC_INVALID_SEARCH_OPR = 
   EC_PREFIX + "INVALID_SEARCH_OPR"; 
   
   /**
    * Mensaje: operador inválido
    */   
   public static final String EM_INVALID_SEARCH_OPR =
   "Incorrect search operator";
   
   // **************************************************************************

   /**
    * Código: campo de ordenación inválido
    */   
   public static final String EC_INVALID_SEARCH_ORDER_FLD = 
   EC_PREFIX + "INVALID_SEARCH_ORDER_FLD"; 
      
   /**
    * Mensaje: campo de ordenación inválido
    */   
   public static final String EM_INVALID_SEARCH_ORDER_FLD =
   "Incorrect field to order the query ";
   
   // **************************************************************************

   /**
    * Código: no encontrado
    */   
   public static final String  EC_NOT_FOUND  = 
   EC_PREFIX +  "NOT_FOUND";

   /**
    * Mensaje: no encontrado
    */   
   public static final String  EM_NOT_FOUND  = 
   "Not found";
      
   // **************************************************************************

   /**
    * Constructor
    */
   private FolderSearchError()
   {
   }
   
} // class
