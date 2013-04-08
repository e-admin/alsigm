package ieci.tecdoc.sgm.rde.database;

import java.util.Date;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.rde.datatypes.DocumentoTemporalImpl;

/**
 * Gestiona el acceso (inserciones, modificaciones, etc.) a la tabla de
 * enlace entre registros y documentos almacenados.
 *
 */
public class DocumentoTemporalTabla extends DocumentoTemporalImpl {
   private static final String TABLE_NAME = "SGMRTTMP_DOCUMENTOS";
   private static final String CN_SESSIONID = "SESIONID";
   private static final String CN_GUID = "GUID";
   private static final String CN_TIMESTAMP = "FECHA";
   private static final String ALL_COLUMN_NAMES = CN_SESSIONID + "," + CN_GUID + "," + CN_TIMESTAMP;

   /**
    * Constructor de la clase DocumentsTable
    */
   public DocumentoTemporalTabla() {
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
    * Devuelve el nombre de la columna sessionId
    * @return String Nombre de la columna sessionId
    */
   public String getContentColumnName(){
     return CN_SESSIONID;
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
    * @param sessionId Valor del campo sessionId
    * @return String Clausula de consulta por sessionId
    */
   public String getBySessionIdQual(String sessionId) {
      String qual;

      qual = "WHERE " + CN_SESSIONID + " = '" + DbUtil.replaceQuotes(sessionId) + "'";

      return qual;
   }

   /**
    * Devuelve la cadena de ordenación por guid
    * @return String Cadena de ordenación
    */
   public String getOrderByGuid(){
     return "ORDER BY " + CN_GUID + " ASC";
   }


   /**
    * Construye una expresión de búsqueda para eliminar todas los docuemntos temporales
    * caducadas.
    *
    * @param timeout Tiempo (en minutos) en el que la sesión caduca.
    * @return La expresión mencionada.
    */
   public String getByTimeoutQualDBConn(int timeout, DbConnection conn) throws Exception
   {
      Date date;

      date = DateTimeUtil.addMinutes(DateTimeUtil.getCurrentDateTime(), -timeout);

  	  return "WHERE " + CN_TIMESTAMP + " < " + DbUtil.getNativeDateTimeSyntax(conn, date, false);
   }
}
