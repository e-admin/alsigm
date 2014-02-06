
package ieci.tecdoc.idoc.admin.api.user;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;

/**
 * Mantiene el conjunto de perfiles de un usuario.
 */
 
public interface UserProfiles 
{

   /**
    * Devuelve un perfil de la lista a partir del identificador de producto. 
    * 
    * @param productId Identificador del producto cuyo perfil se desea.
    * @return El perfil mencionado.
    * @throws Exception Si el perfil para el producto solicitado no existe.
    */

   public UserProfile getProductProfile(int productId) throws Exception; 

   /**
    * Obtiene la información de los perfiles de un usuario en formato XML.
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