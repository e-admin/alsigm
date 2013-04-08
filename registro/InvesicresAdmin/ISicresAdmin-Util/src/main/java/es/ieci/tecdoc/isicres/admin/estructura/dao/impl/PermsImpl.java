
package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;

import java.util.ArrayList;

import es.ieci.tecdoc.isicres.admin.core.xml.lite.XmlTextBuilder;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Permission;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Permissions;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminUserKeys;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminBasicException;

public class PermsImpl implements Permissions
{
   /**
    * Construye un objeto de la clase.
    *
    * o departamento).
    */

   protected PermsImpl()
   {
      list = new ArrayList();
   }

   public String toXML(boolean header)
   {
      XmlTextBuilder bdr;
      String tagName = "Permissions";
      Permission perm;

      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
         perm = get(i);
         bdr.addFragment(perm.toXML(header));
      }

      bdr.addClosingTag(tagName);

      return bdr.getText();
   }

	public String toString()
	{
      return toXML(false);
   }

   public Permission getProductPermission(int productId) throws Exception
   {
      int counter;
      Permission perm = null;
      boolean found = false;

      for (counter = 0; counter < count(); counter++)
      {
         perm = get(counter);
         if (perm.getProduct() == productId)
         {
            found = true;
            break;
         }
      }

      if (!found)
         ISicresAdminBasicException.throwException(ISicresAdminUserKeys.
                                       EC_USER_NOT_PERM_FOR_PRODUCT);

      return perm;
   }


   public Permission getProductPermissionById(int productId) throws Exception
   {
      int counter;
      Permission perm = null;
      boolean found = false;

      for (counter = 0; counter < count(); counter++)
      {
         perm = get(counter);
         if (perm.getProduct() == productId)
         {
            found = true;
            break;
         }
      }

      return perm;
   }

   /**
    * Devuelve el número de permisos.
    *
    * @return El número de permisos mencionado.
    */

   protected int count()
   {
      return list.size();
   }

   /**
    * Devuelve un permiso de la lista.
    *
    * @param index
    *           Indice del permiso que se desea recuperar.
    *
    * @return El permiso mencionado.
    */

   public Permission get(int index)
   {
      return (Permission)list.get(index);
   }


   /**
    * Cambia el identificador de usuario de todos los elementos de la lista.
    *
    * @param id El identificador mencionado.
    *
    */

   protected void setId(int id)
   {
      int counter;

      for (counter = 0; counter < count(); counter++)
      {
         ((PermImpl)get(counter)).setId(id);
      }
   }

   /**
    * Añade un permisos a la lista. Uso interno. No usar.
    *
    * @param perm El permiso mencionado.
    *
    */

   protected void add(Permission perm)
   {
      list.add(perm);
   }

   public ArrayList getList() {
	   return list;
   }

   private ArrayList list;
}