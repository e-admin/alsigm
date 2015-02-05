
package ieci.tecdoc.idoc.admin.api.user;

public interface BasicUsers 
{

   /**
    * Devuelve el número de usuarios.
    * 
    * @return El número de usuarios mencionado.
    */
   
   public int count(); 

   /**
    * Devuelve un usuario de la lista.
    * 
    * @param index Indice del usuario que se desea recuperar.
    * 
    * @return El usuario mencionado.
    */
    
   public BasicUser get(int index); 

}