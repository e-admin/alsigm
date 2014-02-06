
package ieci.tecdoc.idoc.admin.api.user;

/**
 * Mantiene el conjunto de permisos de un usuario, grupo o departamento. 
 */
 
public interface Permissions 
{

   /**
    * Devuelve un permiso de la lista a partir del identificador de producto. 
    * 
    * @param productId Identificador del producto cuyo permiso se desea.
    * @return El permiso mencionado.
    * @throws Exception Si el permiso para el producto solicitado no existe.
    */

   public Permission getProductPermission(int productId) throws Exception; 
   
   /**
    * Obtiene la información de los permisos en formato XML.
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