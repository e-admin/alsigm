
package ieci.tecdoc.idoc.admin.api.user;

/**
 * Maneja los perfiles de un usuario. Cada permiso es una 
 * terna compuesta por: <br> [Identificador de usuario, Identificador de 
 * Producto, Perfil].
 * @see UserDefs 
 */

public interface UserProfile 
{
   /**
    * Devuelve el identificador del usuario.
    * 
    * @return El identificador mencionado.
    */

   public int getUserId();
   
   /**
    * Devuelve el identificador del producto al que se asigna el perfil.
    * 
    * @return El identificador mencionado.
    */

   public int getProduct();
   
   /**
    * Devuelve el perfil asignado al producto.
    * 
    * @return El perfil mencionado.
    */

   public int getProfile();
   
   /**
    * Establece el perfil asignado al producto.
    * 
    * @param profile El perfil mencionado.
    *  
    */

   public void setProfile(int profile); 

   /**
    * Obtiene la información del perfil del usuario en formato XML.
    *  
    * @param header Indica si hay que incluir la cabecera xml o no.
    * @return La información mencionada.
    */

   public String toXML(boolean header); 

   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *  
    * @return La representación mencionada.
    */

	public String toString();

}