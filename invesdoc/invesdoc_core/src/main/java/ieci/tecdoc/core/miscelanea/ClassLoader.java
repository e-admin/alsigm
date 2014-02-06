
package ieci.tecdoc.core.miscelanea;

/**
 * Crea clases a partir de nombres de clases.
 */

public class ClassLoader {

   /**
    * Crea una clase a partir de un nombre de clase.
    * 
    * @param className
    *           Nombre de la clase.
    * @return La clase mencionada.
    * @throws R02SException
    *           Si no se puede crear la clase.
    */
   
   public static Class getClass(String className) throws Exception 
   {

      Class objectClass = null;

      try 
      {
         objectClass = Class.forName(className, true,
                              Thread.currentThread().getContextClassLoader());
      }
      catch (ClassNotFoundException e) 
      {
         try 
         {
            objectClass = Class.forName(className);
         }
         catch (ClassNotFoundException exc) 
         {
            throw exc;
         }
      }

      return objectClass;
      
   }
   
}
