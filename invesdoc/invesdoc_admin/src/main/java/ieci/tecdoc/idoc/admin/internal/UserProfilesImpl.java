
package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.exception.UserErrorCodes;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;

import java.util.ArrayList;

public class UserProfilesImpl implements UserProfiles 
{

   public UserProfile getProductProfile(int productId) throws Exception 
   {
      int counter;
      UserProfile profile = null;
      boolean found = false;
      
      for (counter = 0; counter < count(); counter++)
      {
         profile = get(counter);
         if (profile.getProduct() == productId)
         {
            found = true;
            break;
         }
      }
      
      if (!found)
         AdminException.throwException(UserErrorCodes.
                                       EC_USER_NOT_PROFILE_FOR_PRODUCT);
         
      return profile;
   }

   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "Profiles";
      UserProfile profile;
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
         profile = get(i);
         bdr.addFragment(profile.toXML(header));
      }

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

	public String toString()
	{
      return toXML(false);
   }

   /**
    * Construye un objeto de la clase.
    * 
    */

   protected UserProfilesImpl()
   {
      list = new ArrayList();
   }
   
   /**
    * Devuelve el número de perfiles.
    * 
    * @return El número de perfiles mencionado.
    */

   protected int count() 
   {
      return list.size();
   }

   /**
    * Devuelve un perfil de la lista.
    * 
    * @param index
    *           Indice del perfil que se desea recuperar.
    * 
    * @return El perfil mencionado.
    */

   protected UserProfile get(int index) 
   {
      return (UserProfile)list.get(index);
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
         ((UserProfileImpl)get(counter)).setId(id);
      }
   }
   
   /**
    * Añade un perfiles a la lista. Uso interno. No usar.
    * 
    * @param profile
    *           El perfil mencionado.
    *  
    */

   protected void add(UserProfile profile) 
   {
      list.add(profile);
   }

    private ArrayList list;
}