
package ieci.tecdoc.idoc.admin.api.user;

import ieci.tecdoc.idoc.admin.internal.LdapGroupsImpl;

/**
 * Maneja la lista de grupos invesDoc relacionados con un servicio de 
 * directorio Ldap.
 */

public class LdapGroups 
{

   /**
    * Construye un objeto de la clase.
    *  
    */

   public LdapGroups()
   {
      _groupsImpl = new LdapGroupsImpl();
   }

   /**
    * Devuelve el número de grupos.
    * 
    * @return El número de grupo mencionado.
    */

   public int count() 
   {
      return _groupsImpl.count();
   }

   /**
    * Carga la lista de grupos con su información básica.
    * 
    * @throws Exception Si se produce algún error en la carga de los grupos.
    */

   public void loadLite() throws Exception 
   {
      _groupsImpl.load(false);
   }

   /**
    * Carga la lista de grupos con toda su información.
    * 
    * @throws Exception Si se produce algún error en la carga de los grupos.
    */

   public void loadFull() throws Exception 
   {
      _groupsImpl.load(true);
   }

   /**
    * Devuelve un grupo de la lista.
    * 
    * @param index Indice del grupo que se desea recuperar.
    * 
    * @return El grupo mencionado.
    */

   public LdapGroup getGroup(int index) 
   {
      return _groupsImpl.get(index);
   }
   
   /**
    * Obtiene la información de la lista de grupos en formato XML.
    *  
    * @return La lista de grupo mencionada.
    */

   public String toXML()
   {
      return _groupsImpl.toXML(true);
   }

   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *  
    * @return La representación mencionada.
    */

	public String toString()
   {
      return _groupsImpl.toString();
   }


   private LdapGroupsImpl _groupsImpl;
}