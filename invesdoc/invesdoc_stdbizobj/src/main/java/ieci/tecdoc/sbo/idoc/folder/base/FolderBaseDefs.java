package ieci.tecdoc.sbo.idoc.folder.base;

/**
 * Contiene definiciones de constantes necesarias para el api
 * 
 * @author 
 *
 */
public final class FolderBaseDefs
{
   /** Identificador del clasificador raiz */
   public static final int    CLF_ROOT_ID             = 0;
   /** Nombre del clasificador raiz */
   public static final String CLF_ROOT_NAME           = "Carpeta";

   /** Tamaño máximo del nombre de un nodo del árbol */
   public static final int    DOCTREE_NODE_NAME_LEN   = 254;
   /** Tamaño máximo del nombre del clasificador */
   public static final int    DIVIDERTREE_NODE_NAME_LEN   = 32;
   
   /** Tipo de campo relacional */
   public static final int    FLD_TYPE_REL            = 1;
   /** Tipo de campo texto largo */
   public static final int    FLD_TYPE_EXT            = 2;
   /** Tipo de campo multivalor */
   public static final int    FLD_TYPE_MUL            = 3;

   
   private FolderBaseDefs()
   {
   }

   
} // class
