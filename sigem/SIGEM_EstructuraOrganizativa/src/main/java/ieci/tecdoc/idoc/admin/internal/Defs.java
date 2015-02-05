package ieci.tecdoc.idoc.admin.internal;
import ieci.tecdoc.sbo.acs.base.AcsDefs;
import ieci.tecdoc.sbo.acs.base.AcsObjOwner;
import ieci.tecdoc.sbo.acs.base.AcsPermDst;
import ieci.tecdoc.sbo.acs.base.AcsPermission;
import ieci.tecdoc.sbo.fss.core.FssMdoUtil;
import ieci.tecdoc.sbo.idoc.folder.std.FolderMdoXNextId;
import ieci.tecdoc.sbo.util.types.SboType;

public class Defs 
{
   private Defs()
   {
   }
 
   /**
    * indica sin permisos
    */
   public static final int OBJ_PERM_NONE = AcsPermission.NONE;
   
   /**
    * indica permiso de consulta
    */
   public static final int OBJ_PERM_QUERY = AcsPermission.QUERY;
   
   /**
    * indica permiso de actualización
    */
   public static final int OBJ_PERM_UPDATE = AcsPermission.UPDATE;
   
   /**
    * indica permiso de creación
    */
   public static final int OBJ_PERM_CREATION = AcsPermission.CREATION;
   
   /**
    * indica permiso de borrado
    */
   public static final int OBJ_PERM_DELETION = AcsPermission.DELETION;
 
   
   /**
    * Indica que el usuario es al que se aplican los permisos.
    */
   public static final int DESTINATION_USER = AcsPermDst.USER;

   /**
    * Indica que el departamento es al que se aplican los permisos.
    */
   public static final int DESTINATION_DEPT = AcsPermDst.DEPT;

   /**
    * Indica que el grupo es al que se aplican los permisos.
    */
   public static final int DESTINATION_GROUP = AcsPermDst.GROUP;

   
   public static final int SYSSUPERUSER_ID = AcsDefs.SYS_XSUPERUSER_ID;
   public static final String SYSSUPERUSER_NAME = "SYSSUPERUSER";
   
   public static final int OBJECT_OWNER_TYPE_DIRECTORY = 4;
   public static final int OBJECT_OWNER_TYPE_ARCHIVE = 5;
   public static final int OBJECT_OWNER_TYPE_FOLDER = 6;
   public static final int OBJECT_OWNER_TYPE_FORMAT = 7;
   public static final int OBJECT_OWNER_TYPE_REPORT = 8;
   
   public static final int OBJECT_OWNER_TYPE_USER = AcsObjOwner.USER;
   public static final int OBJECT_OWNER_TYPE_GROUP = AcsObjOwner.GROUP;
   public static final int OBJECT_OWNER_TYPE_DEPT = AcsObjOwner.DEPT;
   
   public static final int ARCH_TYPE_STANDARD = 1;
   public static final int ARCH_TYPE_SICRES = 2;

   public static final int NEXT_ID_TYPE_DIRECTORY = 1;
   public static final int NEXT_ID_TYPE_ARCHIVE = 2;
   
   public static final int NODE_TYPE_DIRECTORY = 1;
   public static final int NODE_TYPE_ARCHIVE = 2;
   
   public static final int NULL_ID = SboType.NULL_ID;
   
   public static final int NEXT_ID_USER_TABLE_TYPE_USER = 1;
   public static final int NEXT_ID_USER_TABLE_TYPE_DEPT = 2;
   public static final int NEXT_ID_USER_TABLE_TYPE_GROUP = 3;
   public static final int NEXT_ID_USER_TABLE_TYPE_OBJ = 4;
   
   public static final int USER_FLAG_NULL = 0;
   public static final int USER_NUMBADCNTS = 0;
   
   public static final int GROUP_STANDARD = 0;
   public static final int DEPT_STANDARD = 0;
   
   /**
    * Identificador de versión del detalle
    */   
   public static final String ARCH_DETAIL_VERSION = "01.00";
   public static final String ARCH_MISC_DETAIL_VERSION = "01.01";
   
   public static final int NEXT_ID_XNID_TABLE_TYPE_FOLDER= FolderMdoXNextId.TYPE_FDR;
   public static final int NEXT_ID_XNID_TABLE_TYPE_EXT_FLD= FolderMdoXNextId.TYPE_EXT_FLD;
   public static final int NEXT_ID_XNID_TABLE_TYPE_IDOC_DOC= FolderMdoXNextId.TYPE_IDOC_DOC;
   
	/**
	 * Identificador del estado para el repositorio, volumen y 
	 * lista de volúmenes por defecto.
	 */
	public static final int STATE_DEF = FssMdoUtil.STAT_DEF;
	/**
	 * Valor nulo del sistema operativo.
	 */
	public static final int OS_NULL = 0;
	/**
	 * Valor nulo del identificador de tipo de repositorio.
	 */
	public static final int REP_TYPE_NULL = 0;
	/**
	 * Identificador del repositorio de flag null.
	 */
	public static final int REP_FLAG_NULL = FssMdoUtil.REP_FLAG_NULL;
	/**
    * Identificador del flag de repositorios Centera para habilitar (evitar colisión)
    */
   public static final int REP_FLAG_CENTERA_ENABLE_COLLISION = FssMdoUtil.REP_FLAG_CENTERA_ENABLE_COLLISION_AVOIDANCE;	
	/**
	 * Nombre de la tabla para la obtención de los identificadores para la
	 * inserciones de los objetos.
	 */
	public static final String NEXT_ID_TBL_NAME = FssMdoUtil.NEXT_ID_TBL_NAME;
	/**
	 * Identificador de tipo "repositorio" para la obtención de los
	 * identificadores en la inserción
	 */
	public static final int NEXT_ID_TYPE_REP = FssMdoUtil.NEXT_ID_TYPE_REP;
	/**
	 * Identificador de tipo "volumen" para la obtención de los
	 * identificadores en la inserción
	 */
	public static final int NEXT_ID_TYPE_VOL = FssMdoUtil.NEXT_ID_TYPE_VOL;
	/**
	 * Identificador de tipo "lista de volúmenes" para la obtención de los
	 * identificadores en la inserción
	 */
	public static final int NEXT_ID_TYPE_LISTVOL = FssMdoUtil.NEXT_ID_TYPE_LIST;
	/**
	 * Identificador de que el volumen no es temporal.
	 */
	public static final int VOL_NOT_TEMP = 0;
	/**
	 * Identificador del volumen de flag null.
	 */
	public static final int VOL_FLAG_NULL = FssMdoUtil.VOL_FLAG_NULL;
	/**
	 * Identificador inicial para el directorio actual de volúmenes.
	 */
	public static final int VOL_ACTDIR_INITIAL = 1;
	/**
	 * Tamaño inicial en bytes para el volumen. 
	 */
	public static final String VOL_ACTSIZE_INITIAL = "0";
	/**
	 * Identificador inicial para el número de ficheros de un volumen.
	 */
	public static final int VOL_NUMFILES_INIT = 0;
	/**
	 * Identificador de la lista de volúmenes de flag null.
	 */
	public static final int LISTVOL_FLAG_NULL = 0;
	/**
	 * Nombre de fichero al crear un repositorio.
	 */
	public static final String REP_FILE_NAME = "REPLABEL";
	/**
	 * Nombre de fichero al crear un volumen.
	 */
	public static final String VOL_FILE_NAME = "VOLLABEL";
	/**
	 * Tamaño máximo del volumen por defecto (500 MB).En bytes.
	 */
	public static final String VOL_MAXSIZE_DEFAULT = "524288000";
	
	/**
	 * Identificador del directorio raiz
	 */
	public static final int ROOT_DIR_ID = 0;
	
	/**
	 * Identificdor del departamento raiz
	 */
	public static final int ROOT_DEPT_ID = 0;
	
   
}