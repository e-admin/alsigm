/**
 * @author José Antonio Nogales Rincón
 *
 * Fecha de Creación: 14-mar-2007
 */
package ieci.tecdoc.sgm.rde.database;

import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.rde.datatypes.TipoMimeImpl;

/**
 * Gestiona el acceso (inserciones, modificaciones, etc.) a la tabla de
 * asociaciones extensión-tipo mime.
 *
 */
public class TipoMimeTabla extends TipoMimeImpl {
   private static final String TABLE_NAME = "SGMRDETIPOSMIME";
   private static final String CN_EXTENSION = "EXTENSION";
   private static final String CN_MIMETYPE = "TIPOMIME";
   private static final String ALL_COLUMN_NAMES = CN_EXTENSION + "," + CN_MIMETYPE;

   /**
    * Constructor de la clase MimeTypeTable
    */
   public TipoMimeTabla() {
   }

   /**
    * Devuelve el nombre de la tabla
    * @return String Nombre de la tabla
    */
   public String getTableName() {
      return TABLE_NAME;
   }

   /**
    * Devuelve los nombres de las columnas
    * @return String Nombres de las columnas
    */
   public String getAllColumnNames() {
      return ALL_COLUMN_NAMES;
   }

   /**
    * Devuelve el nombre de la columna extensión
    * @return String Nombre de la columna extensión
    */
   public String getExtensionColumnName(){
     return CN_EXTENSION;
   }

   /**
    * Devuelve el nombre de la columna mimetype
    * @return String Nombre de la columna mimetype
    */
   public String getMimeTypeColumnName(){
     return CN_MIMETYPE;
   }

   /**
    * Devuelve la clausula de consulta por extensión
    * @param extension Valor del campo extensión
    * @return String Clausula de consulta por extensión
    */
   public String getByExtensionQual(String extension) {
      String qual;

      qual = "WHERE " + CN_EXTENSION + " = '" + DbUtil.replaceQuotes(extension) + "'";

      return qual;
   }

   /**
    * Devuelve la clausula de consulta por mimetype
    * @param mimeType Valor del campo mimetype
    * @return String Clausula de consulta por mimetype
    */
   public String getByMimeTypeQual(String mimeType) {
     String qual;

     qual = "WHERE " + CN_MIMETYPE + " = '" + DbUtil.replaceQuotes(mimeType) + "'";

     return qual;
  }

}
