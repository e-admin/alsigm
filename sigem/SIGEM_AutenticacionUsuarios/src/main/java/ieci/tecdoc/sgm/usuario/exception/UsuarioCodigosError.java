/*
 * Created on 11-ago-2005
 * @autor IECI S.A.
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ieci.tecdoc.sgm.usuario.exception;

/**
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UsuarioCodigosError 
{

   /**
	* Código de error base.
	*/
   public static final long EC_PREFIX = 4007000;
	
   /**
    * Error en la base de datos de usuarios.
    */
   public static final long EC_ERR_DB = EC_PREFIX + 1;
   
   /**
    * El usuario no es válido.
    */
   public static final long EC_BAD_USER_OR_PASS = EC_PREFIX + 2;

    /**
     * Error al crear el usuario.
     */
    public static final long EC_ADD_USER = EC_PREFIX + 3;

    /**
     * Error al eliminar el usuario.
     */
    public static final long EC_DELETE_USER = EC_PREFIX + 4;

    /**
     * Error al recuperar la información del usuario.
     */
    public static final long EC_GET_USER = EC_PREFIX + 5;

    /**
     * Error al actualizar la información del usuario.
     */
    public static final long EC_UPDATE_USER = EC_PREFIX + 6;

    /**
     * Error obteniendo lista de usuarios.
     */
    public static final long EC_FIND_USERS = EC_PREFIX + 7;

}
