/**
 * @author José Antonio Nogales Rincón
 *
 * Fecha de Creación: 14-mar-2007
 */
package ieci.tecdoc.sgm.certificacion.database;

import ieci.tecdoc.sgm.base.dbex.DbUtil;

/**
 * Gestiona el acceso (inserciones, modificaciones, etc.) a la tabla de
 * asociaciones id de documento-id de usuario.
 *
 */
public class CertificacionTabla  {
   private static final String TABLE_NAME = "SGMCERTIFICACION";
   private static final String CN_ID_FICHERO = "ID_FICHERO";
   private static final String CN_ID_USUARIO = "ID_USUARIO";
   private static final String ALL_COLUMN_NAMES = CN_ID_FICHERO + "," + CN_ID_USUARIO;

   /**
    * Constructor de la clase CertificacionTable
    */
   public CertificacionTabla() {
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
    * Devuelve el nombre de la columna identificador de documento
    * @return String Nombre de la columna identificador de documento
    */
   public String getIdFicheroColumnName(){
     return CN_ID_FICHERO;
   }

   /**
    * Devuelve el nombre de la columna identificador de usuario
    * @return String Nombre de la columna identificador de usuario
    */
   public String getIdUsuarioColumnName(){
     return CN_ID_USUARIO;
   }

   /**
    * Devuelve la clausula de consulta por identificador de usuario
    * @param idUsuario Valor del campo identificador de usuario
    * @return String Clausula de consulta por identificador de usuario
    */
   public String getByUsuarioQual(String idUsuario) {
      String qual;

      qual = "WHERE " + CN_ID_USUARIO + " = '" + DbUtil.replaceQuotes(idUsuario) + "'";

      return qual;
   }

   /**
    * Devuelve la clausula de consulta por identificador de documento
    * @param idFichero Valor del campo identificador de documento
    * @return String Clausula de consulta por identificador de documento
    */
   public String getByFicheroQual(String idFichero) {
      String qual;

      qual = "WHERE " + CN_ID_FICHERO + " = '" + DbUtil.replaceQuotes(idFichero) + "'";

      return qual;
   }

   /**
    * Devuelve la clausula de consulta por identificador de usuario y de documento
    * @param idFichero Valor del campo identificador de documento
    * @param idUsuario Valor del campo identificador de usuario
    * @return String Clausula de consulta por identificador de usuario y de documento
    */
   public String getByFicheroAndUsuarioQual(String idFichero, String idUsuario) {
      String qual;

      qual = "WHERE " + CN_ID_USUARIO + " = '" + DbUtil.replaceQuotes(idUsuario) + "' AND " + CN_ID_FICHERO + " = '" + DbUtil.replaceQuotes(idFichero) + "'";

      return qual;
   }

}
