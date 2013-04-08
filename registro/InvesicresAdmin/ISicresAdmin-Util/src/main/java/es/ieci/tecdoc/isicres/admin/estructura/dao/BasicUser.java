
package es.ieci.tecdoc.isicres.admin.estructura.dao;

/**
 * Maneja el identificador y el nombre de un usuario.
 */

public interface BasicUser 
{

   /**
    * Recupera el identificador del usuario.
    *
    * @return El identificador mencionado.
    */
    
   public int getId();
   
   /**
    * Recupera el nombre del usuario.
    *
    * @return El nombre mencionado.
    */

   public String getName();

}