
package ieci.tecdoc.idoc.core.database;

import java.util.ArrayList;

public class DynamicRows 
{
   /**
    * Construye un objeto de la clase.
    *  
    */
 
   public DynamicRows()
   {
      list = new ArrayList();
   }

   /**
    * Devuelve el número de objetos de tipo fila.
    * 
    * @return El número de objetos de tipo fila mencionado.
    */

   public int count() {

      return list.size();
   }

   /**
    * Añade un objeto a la lista.
    * 
    * @param row
    *           El objeto de tipo fila.
    *  
    */

   public void add(DynamicRow row) {

      list.add(row);
   }

   /**
    * Devuelve un objeto de tipo fila de la lista.
    * 
    * @param index
    *           Indice del objeto que se desea recuperar.
    * 
    * @return El objeto de tipo fila mencionado.
    */

   public DynamicRow get(int index) {

      return (DynamicRow)list.get(index);
   }

   private ArrayList list;

}