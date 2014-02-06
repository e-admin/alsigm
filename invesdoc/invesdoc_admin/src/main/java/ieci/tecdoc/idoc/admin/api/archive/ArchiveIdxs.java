package ieci.tecdoc.idoc.admin.api.archive;

/**
 * Proporciona toda la funcionalidad necesaria para manejar índices. 
 */
import java.util.ArrayList;


public interface ArchiveIdxs
{
   
   /**
    * Inicializa la estructura de índices
    *
    */
   public void clear();
   
   /**
    * Obtiene el número de índices en la estructura. 
    * 
    * @return Número de índices
    */
   public int count();
   
   /**
    * Añade la definición de un índice.
    * 
    * @param item Definición del índice
    */
   public void addIdx(ArchiveIdx item) throws Exception;
   
   /**
    * Añade la definición de un índice.
    * 
    * @param name Nombre del índice
    * @param isUnique true/false
    * @param idsFld Lista con los identificadores de los campos que
    * componen el índice
    */
   public void add(String name, boolean isUnique,
         ArrayList idsFld) throws Exception;
   
   /**
    * Obtiene la definición del índice. 
    * 
    * @param index Índice de la estructura
    * @return Errores
    */
   public ArchiveIdx get(int index);
   
   /**
    * Obtiene el identificador del índice por su nombre.
    * 
    * @param name Nombre del índice
    * @return Identificador del índice
    * @throws Exception Errores
    */
   public int getIdxIdByName(String name) throws Exception;
   
   /**
    * Obtiene la información de los índices en formato XML.
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
