package ieci.tecdoc.idoc.admin.api.archive;

/**
 * Proporciona toda la funcionalidad necesaria para manejar índice. 
 */
import java.util.ArrayList;

public interface ArchiveIdx
{
   /**
    * Obtiene el identificador del índice
    * 
    * @return El identificador
    */
   public int getId();
   
   /**
    * Obtiene el nombre del índice
    * 
    * @return El nombre
    */
   public String getName();
   
   
   /**
    * Obtiene si el índice es único ó no
    * 
    * @return (true/false)
    */
   public boolean isUnique();
   
   /**
    * Obtiene una lista de enteros (Integer) con los identificadores
    * de los campos que componen el índice
    * 
    * @return Lista de identificadores de campos
    */
   public ArrayList getFldsId();
   
   /**
    * Establece una lista de enteros (Integer) con los identificadores
    * de los campos que componene el índice
    * 
    * @param idsFld Lista de identificadores de campos
    */
   public void setFldsId(ArrayList fldsId);
   
   /**
    * Obtiene la información del índice en formato XML.
    *
    * @return La información mencionada.
    */

   public String toXML(); 

   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *  
    * @return La representación mencionada.
    */

	public String toString();
}
