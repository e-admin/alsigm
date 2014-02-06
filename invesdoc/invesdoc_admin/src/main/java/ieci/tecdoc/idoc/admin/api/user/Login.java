
package ieci.tecdoc.idoc.admin.api.user;
import ieci.tecdoc.idoc.admin.internal.LoginImpl;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.acs.base.AcsAccessTokenProducts;

/**
 * Proporciona funcionalidad de acceso a la administración de invesDoc. 
 */
 
public class Login 
{
   
   private Login()
   {
   }
   
   /**
    * Realiza la conexión a una aplicación. Valida los permisos del usuario
    * para utilizar dicha aplicación y le proporciona un token de acceso.
    * 
    * @param name Nombre del usuario.
    * @param password Contraseña.
    * @param productId Identificador de la aplicación que desea hacer login.
    * @param numTries Número de intentos incorrectos de usuario-contraseña.
    * @return El token de acceso.
    * @throws java.lang.Exception Si se produce algún error en el login del
    * usuario.
    */
    
   public static AcsAccessToken doLogin(String name, String password, 
                                        int productId, int numTries) 
                                        throws Exception
   {
      return LoginImpl.doLogin(name, password, productId, numTries);
   }
   
   /**
    * Realiza la conexión a las aplicaciones de Administración (Archivos, Usuarios y Volúmenes).
    * Valida los permisos del usuario para utilizar dichas aplicaciónen 
    * y le proporciona un token de acceso para cada una de ellas.
    * 
    * @param name Nombre del usuario.
    * @param password Contraseña. 
    * @param numTries Número de intentos incorrectos de usuario-contraseña.
    * @return Objeto con array de tokens de acceso.
    * @throws java.lang.Exception Si se produce algún error en el login del
    * usuario.
    */
   public static AcsAccessTokenProducts doAdmLogin(String name ,String password,
         														int numTries)
   												 throws Exception
   {
      return LoginImpl.doAdmLogin(name,password,numTries);
   }
}