
package es.ieci.tecdoc.isicres.admin.estructura.dao;


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

   public void createUser(String guid, String fullName, String entidad)
                  throws Exception;

   /**
    * Permite crear un usuario a partir de uno existente en un servidor ldap y con unos profiles específicos
    * @param guid guid del usuario en ldap.
    * @param fullName  fullname del usuario en ldap
    * @param entidad Entidad en la que se crea el usuario
    * @param _profiles Lista de perfiles
    * @throws Exception
    */
   public void createUserWithProfiles(String guid, String fullName, String entidad, UserProfiles _profiles) throws Exception;

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

   public void createFromLdapUser(String root, String attr, String value, String entidad)
                  throws Exception;

   /**
    * Carga un usuario.
    *
    * @param userId Identificador del usuario.
    * @throws Exception Si se produce algún error al leer la información del
    * usuario.
    */

   public void load(int userId, String entidad) throws Exception;

   /**
    * Carga los datos básicos de un usuario.
    *
    * @param userId Identificador del usuario.
    * @throws Exception Si se produce algún error al leer la información del
    * usuario.
    */

   public void loadBasic(int userId, String entidad) throws Exception;

   /**
    * Carga un usuario.
    *
    * @param userGuid Identificador del usuario ldap.
    * @throws Exception Si se produce algún error al leer la información del
    * usuario.
    */

   public void loadFromGuid(String userGuid, String entidad) throws Exception;

   /**
    * Guarda el usuario. Se utiliza tanto para inserciones como para
    * actualizaciones.
    *
    * @throws Exception Si se produce algún error al guardar. Por ejemplo,
    * el usuario ya existe.
    */

   public void store(String entidad) throws Exception;

   /**
    * Elimina el usuario.
    *
    * @throws Exception Si se produce algún error al eliminar.
    */

   public void delete(String entidad) throws Exception;

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

   public GenericProfiles getProfilesImpl();

   public GenericPerms getPermissionsImpl();

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

	public void resetProfiles();

	/**
	 * Asigna los permisos por defecto al usuario
	 */
	public void assingDefaultPerms(String guid, String entidad) throws Exception;
}