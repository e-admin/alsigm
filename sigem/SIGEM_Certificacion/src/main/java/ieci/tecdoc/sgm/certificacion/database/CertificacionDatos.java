/**
 * @author José Antonio Nogales Rincón
 * 
 * Fecha de Creación: 14-mar-2007
 */
package ieci.tecdoc.sgm.certificacion.database;

import java.io.Serializable;

//import ieci.tecdoc.core.db.DbConnection;

import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.certificacion.datatype.CertificacionImpl;
import ieci.tecdoc.sgm.certificacion.datatype.Certificaciones;
import ieci.tecdoc.sgm.certificacion.exception.CertificacionException;
import ieci.tecdoc.sgm.certificacion.exception.CodigosErrorCertificacionException;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de asociaciones extensión-tipo mime del RDE.
 *  
 */
public class CertificacionDatos extends CertificacionImpl implements Serializable {
   private static final Logger logger = Logger.getLogger(CertificacionDatos.class);
   protected boolean isDebugeable = true;
   
   /**
    * Recupera todos los valores de los parámetros de la sentencia
    * de consulta que se pasa como parámetro.
    *
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro que se recoge
    * de la consulta.
    * @return Indice de posición del último parámetro recogido
    * @throws DbExcepcion Si se produce algún error.
    */
   public Integer loadAllValues(DbOutputStatement statement, Integer idx) throws Exception {
      int index = idx.intValue();
      
      idFichero = statement.getShortText(index ++);
      idUsuario = statement.getShortText(index ++);
      
      return new Integer(index);
   }
   
   
   /**
    * Genera la sentencia de inserción de un mime type.
    *
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro que se recoge
    * de la consulta.
    * @return Indice de posición del último parámtro recogido
    * @throws Exception Si se produce algún error.
    */
   public Integer insert(DbInputStatement statement, Integer idx) throws Exception {
     int index = idx.intValue();
 
     statement.setShortText(index++, idFichero);
     statement.setShortText(index ++, idUsuario);

     return new Integer(index);
   }
   
   
   /**
    * Realiza la consulta por extension.
    *
    * @param extension Extension.
    * @throws DbExcepcion Si se produce algún error.
    */
   public void load(String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      CertificacionTabla table = new CertificacionTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Load Certificacion <-- IdFichero: " + idFichero + ", IdUsuario: " + idUsuario);
      
      try {
         dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(CertificacionTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(CertificacionDatos.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select(dbConn, table.getByFicheroAndUsuarioQual(idFichero, idUsuario), tableInfo, rowsInfo)) {
            throw new CertificacionException(CodigosErrorCertificacionException.ERROR_OBTENER_CERTIFICACION);
         }
         
         logger.debug("Load Certificacion --> Certificacion: " + this.toString());
      } catch (Exception e) {
    	  throw new CertificacionException(CodigosErrorCertificacionException.ERROR_OBTENER_CERTIFICACION, e.getCause());
      } finally {
          if (dbConn.existConnection())
             dbConn.close();
      }
   }
   
   
   /**
    * Recupera todos las extensiones asociadas a un mime type
    *
    * @param mimeTypeValue MimeType
    * @throws DbExcepcion Si se produce algún error.
    */
   public static Certificaciones getCertificaciones(String idUsuario, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      CertificacionTabla table = new CertificacionTabla();
      Certificaciones certificaciones = new Certificaciones();
      CertificacionDatos certificacion;
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Load Certificaciones <-- Id Usuario: " + idUsuario);
      
      try {
         dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(CertificacionTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.setClassName(CertificacionDatos.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.selectMultiple(dbConn, table.getByUsuarioQual(idUsuario), false, tableInfo, rowsInfo)) {
        	 throw new CertificacionException(CodigosErrorCertificacionException.ERROR_OBTENER_CERTIFICACIONES);
         }
         
         for (int i = 0; i < rowInfo.getRowCount(); i++) {
            certificacion = (CertificacionDatos)rowInfo.getRow(i);
            certificaciones.add(certificacion);
            
         }
         logger.debug("Load Certificaciones --> Certificaciones: " + certificaciones.toString());
      } catch (Exception e) {
    	  throw new CertificacionException(CodigosErrorCertificacionException.ERROR_OBTENER_CERTIFICACIONES, e.getCause());
      } finally {
          if (dbConn.existConnection())
             dbConn.close();
      }
      
      return certificaciones;
   }

   
   /**
    * Añade un mime type.
    *
    * @throws Exception Si se produce algún error.
    */
   public void add(String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      CertificacionTabla table = new CertificacionTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Add Certificacion <-- Certificacion: " + this.toString());
      
      try {
         dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(CertificacionTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(CertificacionDatos.class.getName());
         rowInfo.setValuesMethod("insert");
         rowsInfo.add(rowInfo);
         
         DynamicFns.insert(dbConn, tableInfo, rowsInfo);
      } catch (Exception e) {
    	  throw new CertificacionException(CodigosErrorCertificacionException.ERROR_INSERTAR_CERTIFICACION, e.getCause());
      } finally {
          if (dbConn.existConnection())
             dbConn.close();
      }
   }
   

   /**
    * Borra todos las extensiones asociadas a un mime type.
    *
    * @param mimeType Mime type.
    * @throws Exception Si se produce algún error.
    */
   public void delete(String entidad) throws Exception {
      CertificacionTabla table = new CertificacionTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Delete Certificacion <-- IdFichero: " + idFichero);
      
      try {
         dbConn.open(DBSessionManager.getSession(entidad));
         
         DbDeleteFns.delete(dbConn, table.getTableName(), table.getByFicheroQual(idFichero));
         
      } catch (Exception e) {
    	  throw new CertificacionException(CodigosErrorCertificacionException.ERROR_ELIMINAR_CERTIFICACION, e.getCause());
      } finally {
         try{
          if (dbConn.existConnection())
             dbConn.close();
        }catch(Exception e){}
      }
   }
}
