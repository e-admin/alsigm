/**
 * @author Alvaro Ugarte Gómez
 *
 * Fecha de Creación: 02-jun-2004
 */

package ieci.tecdoc.sgm.rde.database;

import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.rde.datatypes.ContenedorDocumentoImpl;

/**
 * Gestiona el acceso (inserciones, modificaciones, etc.) a la tabla de
 * documentos.
 *
 */
public class ContenedorDocumentoTabla extends ContenedorDocumentoImpl {
   private static final String TABLE_NAME = "SGMRDEDOCUMENTOS";
   private static final String CN_GUID = "GUID";
   private static final String CN_CONTENT = "CONTENIDO";
   private static final String CN_HASH = "FILEHASH";
   private static final String CN_EXTENSION = "EXTENSION";
   private static final String CN_TIMESTAMP = "FECHA";
   private static final String ALL_COLUMN_NAMES = CN_GUID + ","
           + CN_CONTENT + ","
           + CN_HASH + ","
           + CN_EXTENSION + ","
           + CN_TIMESTAMP;

   /**
    * Constructor de la clase ContenedorDocumentoTabla
    */
   public ContenedorDocumentoTabla() {
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
    * Devuelve el nombre de la columna guid
    * @return String Nombre de la columna guid
    */
   public String getGuidColumnName(){
     return CN_GUID;
   }


   /**
    * Devuelve el nombre de la columna content
    * @return String Nombre de la columna content
    */
   public String getContentColumnName(){
     return CN_CONTENT;
   }


   /**
    * Devuelve el nombre de la columna hash
    * @return String Nombre de la columna hash
    */
   public String getHashColumnName(){
     return CN_HASH;
   }


   /**
    * Devuelve el nombre de la columna extensión
    * @return String Nombre de la columna extensión
    */
   public String getExtensionColumnName(){
     return CN_EXTENSION;
   }


   /**
    * Devuelve el nombre de la columna timestamp
    * @return String Nombre de la columna timestamp
    */
   public String getTimestampColumnName(){
     return CN_TIMESTAMP;
   }


   /**
    * Devuelve la clausula de consulta por guid
    * @param guid Valor del campo guid
    * @return String Clausula de consulta por guid
    */
   public String getByGuidQual(String guid) {
      String qual;

      qual = "WHERE " + CN_GUID + " = '" + DbUtil.replaceQuotes(guid) + "'";

      return qual;
   }

}