
package es.ieci.tecdoc.isicres.admin.estructura.dao;

import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.LdapUsersImpl;

/**
 * Maneja la lista de usuarios invesDoc relacionados con un servicio de
 * directorio Ldap.
 */

public class LdapUsers
{

   /**
    * Construye un objeto de la clase.
    *
    */

   public LdapUsers()
   {
      _usersImpl = new LdapUsersImpl();
   }

   /**
    * Devuelve el número de usuarios.
    *
    * @return El número de usuarios mencionado.
    */

   public int count()
   {
      return _usersImpl.count();
   }

   /**
    * Carga la lista de usuarios con su información básica.
    *
    * @throws Exception Si se produce algún error en la carga de los usuarios.
    */

   public void loadLite(String entidad) throws Exception
   {
      _usersImpl.load(false, entidad);
   }

   /**
    * Carga la lista de usuarios con toda su información.
    *
    * @throws Exception Si se produce algún error en la carga de los usuarios.
    */

   public void loadFull(String entidad) throws Exception
   {
      _usersImpl.load(true, entidad);
   }

   public void loadUsersLdapAssociated(int []idsUser, int idOfic, String entidad) throws Exception {
	  _usersImpl.loadUsersLdapAssociated(idsUser, idOfic, entidad);
   }

   public void loadByAplicacion(int aplicacion, int deptId[], boolean sinPermisos, boolean usuarios, boolean superusuarios, String entidad) throws Exception
   {
      _usersImpl.loadByAplicacion(aplicacion, deptId, sinPermisos, usuarios, superusuarios, entidad);
   }

   public void loadByIdsUser(int aplicacion, int idsUser[],
			boolean superusers, String entidad) throws Exception {
		_usersImpl.loadByIdsUser(aplicacion, idsUser, superusers, entidad);
	}

   /**
    * Devuelve un usuario de la lista.
    *
    * @param index Indice del usuario que se desea recuperar.
    *
    * @return El usuario mencionado.
    */

   public LdapUser getUser(int index)
   {
      return _usersImpl.get(index);
   }

   /**
    * Obtiene la información de la lista de usuarios en formato XML.
    *
    * @return La lista de usuarios mencionada.
    */

   public String toXML()
   {
      return _usersImpl.toXML(true);
   }

   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *
    * @return La representación mencionada.
    */

	public String toString()
   {
      return _usersImpl.toString();
   }


   private LdapUsersImpl _usersImpl;
}