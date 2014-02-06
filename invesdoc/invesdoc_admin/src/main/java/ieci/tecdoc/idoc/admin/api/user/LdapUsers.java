
package ieci.tecdoc.idoc.admin.api.user;

import ieci.tecdoc.idoc.admin.internal.LdapUsersImpl;

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

   public void loadLite() throws Exception 
   {
      _usersImpl.load(false);
   }

   /**
    * Carga la lista de usuarios con toda su información.
    * 
    * @throws Exception Si se produce algún error en la carga de los usuarios.
    */

   public void loadFull() throws Exception 
   {
      _usersImpl.load(true);
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