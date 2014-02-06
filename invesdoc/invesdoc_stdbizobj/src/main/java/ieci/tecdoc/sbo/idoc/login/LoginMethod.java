
package ieci.tecdoc.sbo.idoc.login;

public final class LoginMethod
{  
   /**
    * Define los métodos de establecimiento de sesión
    */    
   
   /** 
    * Método estándar, es decir, contra el sistema de usuarios Invesdoc 
    */
   public static final int STANDARD = 1;
   
   /**
    * Método ldap, es decir, contra un directorio ldap donde se encuentran
    * definidos los usuarios. Cada usuario del directorio debe estar relacionado
    * con un usuario Invesdoc.  
    */
   public static final int LDAP     = 2;
   
   /**
    * Método Single Sign On de Active Directory. 
    */
   public static final int SSO_LDAP = 3;   
   
} // class
