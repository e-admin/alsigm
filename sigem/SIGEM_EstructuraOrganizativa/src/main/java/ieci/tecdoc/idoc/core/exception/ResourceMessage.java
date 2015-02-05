
package ieci.tecdoc.idoc.core.exception;

import java.util.ResourceBundle;
import java.util.MissingResourceException;

public class ResourceMessage 
{
   private ResourceMessage()
   {
   }

   public static String getMessage(long errorCode, String resourceFile)
   {
      String msg = null;
      ResourceBundle resBundle;

      try 
      {
         resBundle = ResourceBundle.getBundle(resourceFile);
         msg = resBundle.getString(new Long(errorCode).toString());
      }
      catch (MissingResourceException mre) 
      {
         msg = null;
         mre.printStackTrace(System.err);
      }
      catch (Exception exc) 
      {
         msg = null;
         exc.printStackTrace(System.err);
      }
      
      return msg;
   }

}