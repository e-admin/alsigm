/**
 * @author José Antonio Nogales Rincón
 * 
 * Fecha de Creación: 14-mar-2007
 */
package ieci.tecdoc.sgm.rde.database;

import java.io.Serializable;

//import ieci.tecdoc.core.db.DbConnection;

//import ieci.tecdoc.sgm.rde.Configuracion;
import ieci.tecdoc.sgm.rde.database.DBSessionManager;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.core.config.impl.spring.Config;
import ieci.tecdoc.sgm.core.config.impl.spring.DefaultConfiguration;
import ieci.tecdoc.sgm.core.db.DataSourceManager;
import ieci.tecdoc.sgm.rde.datatypes.TipoMimeImpl;
import ieci.tecdoc.sgm.rde.datatypes.TiposMime;
import ieci.tecdoc.sgm.rde.database.exception.DbExcepcion;
import ieci.tecdoc.sgm.rde.database.exception.DbCodigosError;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de asociaciones extensión-tipo mime del RDE.
 *  
 */
public class TipoMimeDatos extends TipoMimeImpl implements Serializable {
   private static final Logger logger = Logger.getLogger(TipoMimeDatos.class);
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
   public Integer loadAllValues(DbOutputStatement statement, Integer idx)
   throws DbExcepcion {

      int index = idx.intValue();
      
      try{
        extension = statement.getShortText(index ++);
        mimeType = statement.getShortText(index ++);
      }catch(Exception e){
        throw new DbExcepcion(DbCodigosError.EC_GET_ALL_VALUES, e.getCause());
      }
      
      return new Integer(index);
   }
   
   
   /**
    * Recupera todos los valor de la extensión los documentos
    * de la sentencia de consulta que se pasa como parámetro.
    *
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del parámetro del conector que
    * se recoge de la consulta.
    * @return Indice de posición del parámetro recogido
    * @throws DbExcepcion Si se produce algún error.
    */
   public Integer loadExtension(DbOutputStatement statement, Integer idx)
   throws DbExcepcion {

     int index = idx.intValue();
      
     try{
       this.extension = statement.getShortText(index ++);
     }catch(Exception e){
       throw new DbExcepcion(DbCodigosError.EC_GET_EXTENSION_VALUES, e.getCause());
     }
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
   public Integer insert(DbInputStatement statement, Integer idx)
   throws DbExcepcion {

     int index = idx.intValue();
     
     try{
       statement.setShortText(index++, extension);
       statement.setShortText(index ++, mimeType);
     }catch(Exception e){
       throw new DbExcepcion(DbCodigosError.EC_INSERT_ALL_VALUES);
     }

     return new Integer(index);
   }
   
   
   /**
    * Realiza la consulta por extension.
    *
    * @param extension Extension.
    * @throws DbExcepcion Si se produce algún error.
    */
   public void load(String extension, String entidad) throws DbExcepcion {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      TipoMimeTabla table = new TipoMimeTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Load Mime Type <-- Extension: " + extension);
      
      try {
          dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(TipoMimeTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(TipoMimeDatos.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select(dbConn, table.getByExtensionQual(extension), tableInfo, rowsInfo)) {
            throw new DbExcepcion(DbCodigosError.EC_SELECT_BY_MIME_TYPE_NOT_FOUND);
         }
         logger.debug("Load Mime Type --> Mime Type: " + this.toString());
      } catch (Exception e) {
        throw new DbExcepcion(DbCodigosError.EC_SELECT_BY_MIME_TYPE_EXCEPTION, e.getCause());
      } finally {
        try{
          if (dbConn.existConnection())
             dbConn.close();
        }catch(Exception e){ }
      }
   }
   
   
   /**
    * Recupera todos las extensiones asociadas a un mime type
    *
    * @param mimeTypeValue MimeType
    * @throws DbExcepcion Si se produce algún error.
    */
   public static TiposMime loadMimeTypes(String mimeTypeValue, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      TipoMimeTabla table = new TipoMimeTabla();
      TiposMime mimeTypes = new TiposMime();
      TipoMimeDatos mimeType;
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Load Mime Types <-- Mime Type ID: " + mimeTypeValue);
      
      try {
          dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(TipoMimeTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.setClassName(TipoMimeDatos.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.selectMultiple(dbConn, table.getByMimeTypeQual(mimeTypeValue), false, tableInfo, rowsInfo)) {
            throw new DbExcepcion(DbCodigosError.EC_LOAD_MIME_TYPES_BY_EXTENSION_NOT_FOUND);
         }
         
         for (int i = 0; i < rowInfo.getRowCount(); i++) {
            mimeType = (TipoMimeDatos)rowInfo.getRow(i);
            mimeTypes.add(mimeType);
            
         }
         logger.debug("Load Mime Types --> Mime Types: " + mimeTypes.toString());
      } catch (Exception e) {
        throw new DbExcepcion(DbCodigosError.EC_LOAD_MIME_TYPES_BY_EXTENSION_EXCEPTION, e.getCause());
      } finally {
        try{
          if (dbConn.existConnection())
             dbConn.close();
        }catch(Exception e){}
      }
      
      return mimeTypes;
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
      TipoMimeTabla table = new TipoMimeTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Add Mime Type <-- Mime Type: " + this.toString());
      
      try {
          dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(TipoMimeTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnsName");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(TipoMimeDatos.class.getName());
         rowInfo.setValuesMethod("insert");
         rowsInfo.add(rowInfo);
         
         DynamicFns.insert(dbConn, tableInfo, rowsInfo);
      } catch (Exception e) {
         throw new DbExcepcion(DbCodigosError.EC_ADD_VALUE, e.getCause());
      } finally {
         try{
          if (dbConn.existConnection())
             dbConn.close();
        }catch(Exception e){}
      }
   }
   

   /**
    * Borra todos las extensiones asociadas a un mime type.
    *
    * @param mimeType Mime type.
    * @throws Exception Si se produce algún error.
    */
   public void delete(String entidad) throws Exception {
      TipoMimeTabla table = new TipoMimeTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Delete Mime Type <-- Mime Type ID: " + mimeType);
      
      try {
          dbConn.open(DBSessionManager.getSession(entidad));
         
         DbDeleteFns.delete(dbConn, table.getTableName(), table.getByMimeTypeQual(mimeType));
         
      } catch (Exception e) {
         throw new DbExcepcion(DbCodigosError.EC_DELETE_ALL_VALUES);
      } finally {
         try{
          if (dbConn.existConnection())
             dbConn.close();
        }catch(Exception e){}
      }
   }
}
