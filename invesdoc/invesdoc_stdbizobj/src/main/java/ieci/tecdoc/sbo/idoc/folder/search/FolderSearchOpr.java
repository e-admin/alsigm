
package ieci.tecdoc.sbo.idoc.folder.search;

import ieci.tecdoc.core.search.SearchOpr;

/**
 * Clase que define los operadores de búsqueda 
 * <li> IN_AND: todos los valores se encuentran en la colección de valores
 * de un campo multivalor
 * <li> IN_OR: algunos valores se encuentran en la colección de valores
 * de un campo multivalor
 * <li> LIKE_AND: todos los valores son como alguno de la colección de valores
 * de un campo multivalor
 * <li> LIKE_OR: algunos valores son como alguno de la colección de valores
 * de un campo multivalor
 * <li> EQUAL: igual
 * <li> DISTINCT: distinto
 * <li> GREATER: mayor que
 * <li> GREATER_EQUAL: mayor o igual que
 * <li> LOWER: menor
 * <li> LOWER_EQUAL: menor que 
 * <li> BETWEEN: entre dos valores
 * <li> LIKE: como
 * <li> OR: o
 * <li> FULL_TEXT: búsqueda documental dentro de un campo relacional. La expresión
 * documental de búsqueda se cumple.
 * <li> FULL_TEXT_NOT: búsqueda documental dentro de un campo relacional. La expresión
 * documental de búsqueda no se cumple.       
 * <li> IS_NULL: es nulo. 
 * <li> IS_NOT_NULL: no es nulo.        
 * @author
 */


public class FolderSearchOpr extends SearchOpr
{   
   /**
    * todos los valores se encuentran en la colección de valores
    * de un campo multivalor
    */
   public static final String IN_AND        =  "C&";
   
   /**
    * todos los valores son como alguno de la colección de valores
    * de un campo multivalor
    */
   public static final String IN_OR         =  "C|";
   
   /**
    * todos los valores son como alguno de la colección de valores
    * de un campo multivalor
    */
   public static final String LIKE_AND      =  "%&";
   
   /**
    * algunos valores son como alguno de la colección de valores
    * de un campo multivalor
    */
   public static final String LIKE_OR       =  "%|"; 

   /**
    * Constructor
    */
   private FolderSearchOpr()
   {
      super();
   }

   /**
    * Es un operador de un campo multivalor
    * @param opr operador
    * @return true si es un operador de un campo multivalor; false en caso contrario
    */
   public static boolean isMultOperator(String opr)
                     
   {
      boolean is = false;

      if ( opr.equals(FolderSearchOpr.IN_AND)||
           opr.equals(FolderSearchOpr.IN_OR) ||
           opr.equals(FolderSearchOpr.LIKE_AND)||
           opr.equals(FolderSearchOpr.LIKE_OR))
         is = true;
    
      return is;      
   }

   /**
    * Es un operador documental
    * @param opr operador
    * @return true si es un operador documental; false en caso contrario
    */
   public static boolean isDocOperator(String opr)
                     
   {
      boolean is = false;

      if ( opr.equals(SearchOpr.FULL_TEXT)||
           opr.equals(SearchOpr.FULL_TEXT_NOT) )
         is = true;
    
      return is;      
   }
   
} // class
