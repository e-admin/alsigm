
package ieci.tecdoc.idoc.admin.api.archive;

import ieci.tecdoc.sbo.acs.base.AcsAccessType;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveFlag;
import ieci.tecdoc.sbo.idoc.dao.DaoArchDetType;
import ieci.tecdoc.sbo.idoc.dao.DaoDATNodeType;


/**
 * Proporciona las definiciones de todos los identificadores relativos a la
 * administración de directorios y archivadores.
 */

public class ArchiveDefs 
{
   private ArchiveDefs()
   {
   }
   
    /**
    * Identificador de <I>Directorio Estándar</I>.
    */
   public static int DIR_TYPE_STANDARD = 0;

   /**
    * Identificador de <I>Directorio inveSicres</I>.
    */
   public static int DIR_TYPE_SICRES = 1;
   
   /**
    * Identificador de <I>Archivador Estándar</I>
    */
   public static int ARCH_TYPE_STANDARD = 0;
   
   /**
    * Identificador de <I>Archivador invesSicres entrada</I>
    */
   public static int ARCH_TYPE_SICRES_INPUT = 1;
   
   /**
    * Identificador de <I>Archivador invesSicres salida</I>
    */
   public static int ARCH_TYPE_SICRES_OUTPUT = 2;
   
   /**
    * Identificador de flag de archivador <I> ninguno </I>
    */
   public static int ARCH_FLAG_NONE = ArchiveFlag.NONE;
   
   /**
    * Identificador de flag de archivador <I> Búsqueda en contenido de fichero </I>
    */
   public static int ARCH_FLAG_FTSINCONTENTS = ArchiveFlag.FTS_IN_CONTENTS;
   
   /**
    * Identificador de <I> Detalle de campos e índices en archivador </I>
    */
   public static int ARCH_DET_FLD_DEF = DaoArchDetType.DET_TYPE_FLD_DEF;
   
   /**
    * Identificador de <I> Detalle de miscelánea en archivador </I>
    */
   public static int ARCH_DET_MISC_DEF = DaoArchDetType.DET_TYPE_MISC_DEF;
   
   /**
    * Identificador de <I> Detalle de clasificadores en archivador </I>
    */
   public static int ARCH_DET_CLF_DEF = DaoArchDetType.DET_TYPE_FLD_DEF;
   
   /**
    * Identificador de <I> Detalle de validaciones en archivador </I>
    */
   public static int ARCH_DET_VLD_DEF = DaoArchDetType.DET_TYPE_VLD_DEF;
   
   /** 
    * El acceso al elemento es público.
    */
   public static int ACCESS_TYPE_PUBLIC = AcsAccessType.ACS_PUBLIC;

   /** 
    * El acceso al elemento es protegido.
    */
   public static int ACCESS_TYPE_PROTECTED = AcsAccessType.ACS_PROTECTED;
   
   /** 
    * El nodo corresponde a un archivador.
    */
   public static int DAT_NODE_TYPE_ARCHIVE = DaoDATNodeType.ARCH;

   /** 
    * El nodo corresponde a un directorio.
    */
   public static int DAT_NODE_TYPE_DIRECTORY = DaoDATNodeType.DIR;
   
   
}