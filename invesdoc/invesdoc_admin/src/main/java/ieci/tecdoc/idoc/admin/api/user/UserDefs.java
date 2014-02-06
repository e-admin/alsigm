package ieci.tecdoc.idoc.admin.api.user;

import ieci.tecdoc.sbo.acs.base.AcsPermission;
import ieci.tecdoc.sbo.acs.base.AcsProduct;
import ieci.tecdoc.sbo.acs.std.AcsMdoProfile;

/**
 * Proporciona las definiciones de todos los identificadores relativos a la
 * administración de usuarios.
 */
 
public class UserDefs 
{
   /**
    * Identificador de producto de las <b>Herramientas de Sistema</b>.
    */
   public static final int PRODUCT_SYSTEM = AcsProduct.ISYS;

   /**
    * Identificador de producto del <b>Administrador de Usuarios</b>.
    */
   public static final int PRODUCT_USER = AcsProduct.IUSER;
   
   /**
    * Identificador de producto de la <b>Aplicación de Consulta</b>.
    */
   public static final int PRODUCT_IDOC = AcsProduct.IDOC;
   
   /**
    * Identificador de producto de <b>invesFlow</b>.
    */
   public static final int PRODUCT_IFLOW = AcsProduct.IFLOW;
   
   /**
    * Identificador de producto de <b>inveSicres</b>.
    */
   public static final int PRODUCT_ISICRES = AcsProduct.ISICRES;
   
   /**
    * Identificador de producto del <b>Administrador de Volúmenes</b>.
    */
   public static final int PRODUCT_VOLUME = AcsProduct.IVOL;
   
   /**
    * Identificador de perfil <b>Sin Derechos</b>.
    */
   public static final int PROFILE_NONE = AcsMdoProfile.USER_TYPE_NONE;

   /**
    * Identificador de perfil <b>Estándar</b>.
    */
   public static final int PROFILE_STANDARD = AcsMdoProfile.USER_TYPE_STANDARD;

   /**
    * Identificador de perfil <b>Administrador</b>.
    */
   public static final int PROFILE_MANAGER = AcsMdoProfile.USER_TYPE_MANAGER;

   /**
    * Identificador de perfil <b>Superusuario</b>.
    */
   public static final int PROFILE_SUPERUSER = AcsMdoProfile.USER_TYPE_SUPERUSER;

   /**
    * Ningún permiso.
    */
   public static final int PERMISSION_NONE = AcsPermission.NONE;

   /**
    * Permiso de búsqueda.
    */
   public static final int PERMISSION_QUERY = AcsPermission.QUERY;

   /**
    * Permiso de actualización.
    */
   public static final int PERMISSION_UPDATE = AcsPermission.UPDATE;

   /**
    * Permiso de creación.
    */
   public static final int PERMISSION_CREATION = AcsPermission.CREATION;

   /**
    * Permiso de borrado.
    */
   public static final int PERMISSION_DELETION = AcsPermission.DELETION;

   /**
    * Permiso de impresión.
    */
   public static final int PERMISSION_PRINTING = AcsPermission.PRINTING;

   /**
    * Todos los permisos.
    */
   public static final int PERMISSION_ALL = AcsPermission.ALL;
   
   /**
    * Estado del usuario por definición.
    */
   public static final int USER_STAT_DEF = 0;
   
   /**
    * Estado del usuario bloqueado.
    */
   public static final int USER_STAT_LOCKED = 1;
   
   private UserDefs()
   {
   }
}