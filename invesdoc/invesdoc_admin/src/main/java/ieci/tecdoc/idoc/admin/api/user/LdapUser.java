
package ieci.tecdoc.idoc.admin.api.user;

import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
import ieci.tecdoc.idoc.admin.api.user.Permissions;

/**
 * Proporciona toda la funcionalidad necesaria para manejar usuarios invesDoc 
 * relacionados con un servicio de directorio Ldap.
 */

public interface LdapUser
{

   /**
    * Permite crear un usuario a partir de uno existente en un servidor Ldap.
    *
    * @param guid guid del usuario en ldap.
    * @param fullName  fullname del usuario en ldap
    * @return El usuario .
    * @throws Exception Si se ha producido algún error en la creación 
    */
    
   public void createUser(String guid, String fullName)
                  throws Exception;
   /**
    * Permite crear un usuario a partir de uno existente en un servidor Ldap.
    *
    * @param root Dn del nodo raíz a partir del cual buscar.
    * @param attr Atributo de búsqueda (cn).
    * @param value Valor del atributo de búsqueda (el nombre del 
    * usuario).
    * @return El usuario que cumple los criterios de búsqueda.
    * @throws Exception Si se ha producido algún error en la creación (por 
    * ejemplo, el usuario no existe en el servidor Ldap).
    */
    
   public void createFromLdapUser(String root, String attr, String value)
                  throws Exception;

   /**
    * Carga un usuario.
    * 
    * @param userId Identificador del usuario.
    * @throws Exception Si se produce algún error al leer la información del 
    * usuario.
    */

   public void load(int userId) throws Exception;
   
   /**
    * Carga un usuario.
    * 
    * @param userGuid Identificador del usuario ldap.
    * @throws Exception Si se produce algún error al leer la información del 
    * usuario.
    */

   public void loadFromGuid(String userGuid) throws Exception;

   /**
    * Guarda el usuario. Se utiliza tanto para inserciones como para 
    * actualizaciones.
    * 
    * @throws Exception Si se produce algún error al guardar. Por ejemplo, 
    * el usuario ya existe.
    */

   public void store() throws Exception;

   /**
    * Elimina el usuario.
    * 
    * @throws Exception Si se produce algún error al eliminar.
    */

   public void delete() throws Exception;

   /**
    * Devuelve el identificador único del usuario.
    * 
    * @return El identificador mencionado.
    */

   public String getGuid();

   /**
    * Devuelve el nombre completo del usuario.
    * 
    * @return El nombre mencionado.
    */

   public String getFullName();

   /**
    * Devuelve el identificador del usuario.
    * 
    * @return El identificador mencionado.
    */

   public int getId();


   /**
    * Devuelve la lista de perfiles del usuario.
    * 
    * @return La lista mencionada.
    */

   public UserProfiles getProfiles(); 

   /**
    * Devuelve la lista de permisos del usuario.
    * 
    * @return La lista mencionada.
    */

   public Permissions getPermissions();

   /**
    * Obtiene la información del usuario Ldap en formato XML.
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