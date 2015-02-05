package ieci.tdw.applets.idocscan;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConfigMessages
{

   private static final String         m_bundleName   = "ieci.tdw.applets.idocscan.applet";
   private static final ResourceBundle m_bundle       = ResourceBundle.getBundle(ConfigMessages.m_bundleName);

   public ConfigMessages()
   {
   }

   public static String getString(String key)
   {
      if (key == null) 
         return '!' + key + '!';
      try
      {

         String message =  m_bundle.getString(key);
         
         return message;
      }
      catch (MissingResourceException e)
      {
         // Por tratar
      }

      return '!' + key + '!';
   }
}