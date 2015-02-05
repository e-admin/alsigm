/*
 * Created on 08-ago-2005
 * @author IECI S.A.
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ieci.tecdoc.sgm.registro.util.database;

import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.registro.exception.RegistroCodigosError;
import ieci.tecdoc.sgm.registro.exception.RegistroExcepcion;
import ieci.tecdoc.sgm.registro.util.RegistroSecuenciaImpl;

import org.apache.log4j.Logger;


/**
 * Wrapper de ejecución de sentencias SQL sobre la tabla
 * SGMRTREGISTRO_SECUENCIA;
 *
 * @see RegistroSecuenciaTabla
 */
public class RegistroSecuenciaDatos extends RegistroSecuenciaImpl {
   
   private static final Logger logger = Logger.getLogger(RegistroSecuenciaDatos.class);
   
   public RegistroSecuenciaDatos() {
   }
   
   /**
    * Recupera todos los valores de los parámetros de la sentencia
    * de consulta que se pasa como parámetro.
    *
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro que se recoge
    * de la consulta.
    * @return Indice de posición del último parámetro recogido
    * @throws Exception Si se produce algún error.
    */
   public Integer loadAllValues(DbOutputStatement statement, Integer idx)
   throws Exception {

      int index = idx.intValue();
      
      year = statement.getShortText(index ++);
      label = statement.getShortText(index ++);
      sequence = statement.getLongInteger(index ++);
      return new Integer(index);
   }
   
   /**
    * Genera la sentencia de inserción de un registro.
    *
    * @param statement Sentencia SQL precompilada
    * @param idx Indice de posición del primer parámetro en la sentencia.
    * @return Indice de posición del último parámetro en la sentencia.
    * @throws Exception Si se produce algún error.
    */
   public Integer insert(DbInputStatement statement, Integer idx)
   throws Exception {
      int index = idx.intValue();

      statement.setShortText(index ++, year);
      statement.setShortText(index ++, label);
      statement.setLongInteger(index ++, sequence);
      
      return new Integer(index);
   }
   
   /**
    * Genera la sentencia de actualización de un registro.
    *
    * @param statement Sentencia SQL precompilada
    * @param idx Indice de posición del primer parámetro en la sentencia.
    * @return Indice de posición del último parámetro en la sentencia.
    * @throws Exception Si se produce algún error.
    */
   public Integer updateAll(DbInputStatement statement, Integer idx) throws Exception {
      int index = idx.intValue();
      
      statement.setShortText(index ++, year);
      statement.setShortText(index ++, label);
      statement.setLongInteger(index ++, sequence);
      
      return new Integer(index);
   }
   
   /**
    * Genera la sentencia de actualización de secuencia de un registro.
    *
    * @param statement Sentencia SQL precompilada
    * @param idx Indice de posición del primer parámetro en la sentencia.
    * @return Indice de posición del último parámetro en la sentencia.
    * @throws Exception Si se produce algún error.
    */
   public Integer updateSequence(DbInputStatement statement, Integer idx) throws Exception {
      int index = idx.intValue();
      
      statement.setLongInteger(index ++, sequence);
      
      return new Integer(index);
   }
   
   /**
    * Genera la sentencia de actualización del año y la secuencia de un
    * registro.
    *
    * @param statement Sentencia SQL precompilada
    * @param idx Indice de posición del primer parámetro en la sentencia.
    * @return Indice de posición del último parámetro en la sentencia.
    * @throws Exception Si se produce algún error.
    */
   public Integer updateSequenceAndYearValues(DbInputStatement statement, Integer idx)
   throws Exception {
      int index = idx.intValue();

      statement.setShortText(index++, year);
      statement.setLongInteger(index++, sequence);
      
      return new Integer(index);
   }
   
   /**
    * Recupera un  registro.
    *
    * @throws Exception Si se produce algún error.
    */
   public void load(String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      RegistroSecuenciaTabla table = new RegistroSecuenciaTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Load Registry Number Sequence <--");
      
      try {
         dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(RegistroSecuenciaTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnsName");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(RegistroSecuenciaDatos.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select(dbConn, "", tableInfo, rowsInfo)) {
           throw new RegistroExcepcion(RegistroCodigosError.EC_REGISTRY_NUMBER_NOT_INITIALIZED);
         }
         
         logger.debug("Load Registry Number Sequence --> Sequence: " + this.toString());
      } catch (RegistroExcepcion e) {
         throw e;
      } catch (Exception e) {
        throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
              dbConn.close();
      }
   }
   
   /**
    * Recupera un  registro con el valor de la oficina que se pasa como parámetro.
    *
    * @throws Exception Si se produce algún error.
    */
   public void load(String label, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      RegistroSecuenciaTabla table = new RegistroSecuenciaTabla();
      DbConnection dbConn = new DbConnection();
      boolean close = false;
      
      logger.debug("Load Registry Number Sequence for Office <-- Label: " + label);
      
      try {
         close = !dbConn.existConnection();
         if (close){
        	 //dbConn.open(Configuracion.getDatabaseConnection());
         	 dbConn.open(DBSessionManager.getSession(entidad));
         }
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(RegistroSecuenciaTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnsName");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(RegistroSecuenciaDatos.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select(dbConn, table.getByLabelQual(label) , tableInfo, rowsInfo)) {
           throw new RegistroExcepcion(RegistroCodigosError.EC_REGISTRY_NUMBER_NOT_INITIALIZED);
         }
         
         logger.debug("Load Registry Number Sequence --> Sequence: " + this.toString());
      } catch (RegistroExcepcion e) {
         throw e;
      } catch (Exception e) {
        throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
      } finally {
         if (close)
            dbConn.close();
      }
   }
   
   /**
    * Recupera un  registro con el valor de la oficina y el año.
    * 
    * @param label Campo label (oficina).
    * @param year Campo año.
    * @throws RegistroExcepcion Si no existe el registro de la secuencia para la oficina y el año.
    * @throws Exception Si se produce algún error.
    */
   public void load(String label, String entidad, String year) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      RegistroSecuenciaTabla table = new RegistroSecuenciaTabla();
      DbConnection dbConn = new DbConnection();
      boolean close = false;
      
      logger.debug("Load Registry Number Sequence for Office <-- Label: " + label);
      
      try {
         close = !dbConn.existConnection();
         if (close){
        	 //dbConn.open(Configuracion.getDatabaseConnection());
         	 dbConn.open(DBSessionManager.getSession(entidad));
         }
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(RegistroSecuenciaTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnsName");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(RegistroSecuenciaDatos.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select(dbConn, table.getByLabelQual(label, year) , tableInfo, rowsInfo)) {
           throw new RegistroExcepcion(RegistroCodigosError.EC_REGISTRY_NUMBER_NOT_INITIALIZED);
         }
         
         logger.debug("Load Registry Number Sequence --> Sequence: " + this.toString());
      } catch (RegistroExcepcion e) {
         throw e;
      } catch (Exception e) {
        throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
      } finally {
         if (close)
            dbConn.close();
      }
   }
   
   /**
    * Recupera el número de registro a partir de la oficina y el año.
    * 
    * @param dbConn Conexión de BD de la entidad correspondiente.
    * @param label Campo label (oficina).
    * @param year Campo año.
    * 
    * @throws RegistroExcepcion Si no existe el registro de la secuencia para la oficina y el año
    * 							se retorna el código EC_REGISTRY_NUMBER_NOT_INITIALIZED, en otro caso EC_ERR_DB.
    * @throws Exception Si se produce algún error.
    */
   public void load(DbConnection dbConn, String label, String year) throws Exception {
	   
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      RegistroSecuenciaTabla table = new RegistroSecuenciaTabla();
      
      logger.debug("Load Registry Number Sequence for Office <-- Label: " + label);
      
      try {
    	  tableInfo.setTableObject(table);
    	  tableInfo.setClassName(RegistroSecuenciaTabla.class.getName());
    	  tableInfo.setTablesMethod("getTableName");
    	  tableInfo.setColumnsMethod("getAllColumnsName");
         
    	  rowInfo.addRow(this);
    	  rowInfo.setClassName(RegistroSecuenciaDatos.class.getName());
    	  rowInfo.setValuesMethod("loadAllValues");
    	  rowsInfo.add(rowInfo);
         
    	  if (!DynamicFns.select(dbConn, table.getByLabelQual(label, year) , tableInfo, rowsInfo)) {
    		  throw new RegistroExcepcion(RegistroCodigosError.EC_REGISTRY_NUMBER_NOT_INITIALIZED);
    	  }
         
    	  logger.debug("Load Registry Number Sequence --> Sequence: " + this.toString());
      }
      catch (RegistroExcepcion e) {
    	  throw e;
      }
      catch (Exception e) {
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
      }
   }
   
   /**
    * Actualiza el contador de registro.
    *
    * @throws Exception Si se produce algún error.
    */
   public void updateSequence(String entidad) throws Exception {
      RegistroSecuenciaTabla table = new RegistroSecuenciaTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Update Registry Number Sequence <-- Sequence: " + this.toString());
      
      try {
         dbConn.open(DBSessionManager.getSession(entidad));
         
         DbUtil.executeStatement(dbConn,table.getUpdateSequence());
         load(entidad);
      } catch (RegistroExcepcion e) {
         throw e;
      } catch (Exception e) {
        throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
              dbConn.close();
      }
   }
   
   /**
    * Actualiza el contador de registro para una oficina.
    * @param label Etiqueta que contiene el código de oficina
    * @throws Exception Si se produce algún error.
    */
   public void updateSequence(String label, String entidad) throws Exception {
      RegistroSecuenciaTabla table = new RegistroSecuenciaTabla();
      DbConnection dbConn = new DbConnection();
     
      logger.debug("Update Registry Number Sequence for Office <-- Sequence: " + this.toString());
      
      try {
       	 dbConn.open(DBSessionManager.getSession(entidad));
       	 
         DbUtil.executeStatement(dbConn, table.getUpdateSequence(label));
         load(label, entidad);
      } catch (RegistroExcepcion e) {
         throw e;
      } catch (Exception e) {
        throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
              dbConn.close();
      }
   }
   
   /**
    * Actualiza el contador de registro para una oficina.
    * @param label Etiqueta que contiene el código de oficina
    * @param year Año de la secuencia
    * @throws Exception Si se produce algún error.
    */
   public void updateSequence(String label, String entidad, String year) throws Exception {
      RegistroSecuenciaTabla table = new RegistroSecuenciaTabla();
      DbConnection dbConn = new DbConnection();
     
      logger.debug("Update Registry Number Sequence for Office <-- Sequence: " + this.toString());
      
      try {
       	 dbConn.open(DBSessionManager.getSession(entidad));
       	 
         DbUtil.executeStatement(dbConn, table.getUpdateSequence(label, year));
         load(label, entidad, year);
      } catch (RegistroExcepcion e) {
         throw e;
      } catch (Exception e) {
        throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
              dbConn.close();
      }
   }
   
   /**
    * Actualiza el contador de registro para una oficina.
    * 
    * @param dbConn Conexión de BD de la entidad correspondiente.
    * @param label Etiqueta que contiene el código de oficina.
    * @param year Año de la secuencia.
    * 
    * @throws Exception Si se produce algún error.
    */
   public void updateSequence(DbConnection dbConn, String label, String year) throws Exception {
	   
      RegistroSecuenciaTabla table = new RegistroSecuenciaTabla();
     
      logger.debug("Update Registry Number Sequence for Office <-- Sequence: " + this.toString());
      
      try {
    	  DbUtil.executeStatement(dbConn, table.getUpdateSequence(label, year));
    	  load(dbConn, label, year);
      }
      catch (RegistroExcepcion e) {
    	  throw e;
      }
      catch (Exception e) {
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
      }
   }
   
   /**
    * Actualiza el contador de registro.
    *
    * @throws Exception Si se produce algún error.
    */
   public void updateSequenceAndYear(String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      RegistroSecuenciaTabla  table = new RegistroSecuenciaTabla();
      DbConnection dbConn = new DbConnection();

      logger.debug("UpdateRegistry Number Sequence And Year <-- Sequence: " + this.toString());
      
      try {
       	 dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(RegistroSecuenciaTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getUpdateSequenceAndYearColumnsName");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(RegistroSecuenciaDatos.class.getName());
         rowInfo.setValuesMethod("updateSequenceAndYearValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.update(dbConn, "", tableInfo, rowsInfo);
      } catch (RegistroExcepcion e) {
         throw e;
      } catch (Exception e) {
        throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
      } finally {
         if (dbConn.existConnection())
             dbConn.close();
      }
   }
   
   /**
    * Actualiza el contador de registro para una oficina.
    * @param label Etiqueta que contiene el código de oficina
    * @throws Exception Si se produce algún error.
    */
   public void updateSequenceAndYear(String label, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      RegistroSecuenciaTabla  table = new RegistroSecuenciaTabla();
      DbConnection dbConn = new DbConnection();

      logger.debug("UpdateRegistry Number Sequence And Year for Office <-- Sequence: " + this.toString());
      
      try {
       	 dbConn.open(DBSessionManager.getSession(entidad));

       	 tableInfo.setTableObject(table);
         tableInfo.setClassName(RegistroSecuenciaTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getUpdateSequenceAndYearColumnsName");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(RegistroSecuenciaDatos.class.getName());
         rowInfo.setValuesMethod("updateSequenceAndYearValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.update(dbConn, table.getByLabelQual(label), tableInfo, rowsInfo);
      } catch (RegistroExcepcion e) {
         throw e;
      } catch (Exception e) {
        throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
              dbConn.close();
      }
   }
   
   /**
    * Inserta un registro.
    *
    * @throws Exception Si se produce algún error.
    */
   public void insert(String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      RegistroSecuenciaTabla table = new RegistroSecuenciaTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Insert Registry Number Sequence <-- Sequence: " + this.toString());
      
      try {
         dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(RegistroSecuenciaTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnsName");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(RegistroSecuenciaDatos.class.getName());
         rowInfo.setValuesMethod("insert");
         rowsInfo.add(rowInfo);
         
         DynamicFns.insert(dbConn, tableInfo, rowsInfo);
         
      } catch (RegistroExcepcion e) {
         throw e;
      } catch (Exception e) {
        throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
              dbConn.close();
      }
   }
   
   /**
    * Inserta un registro.
    *
    * @throws Exception Si se produce algún error.
    */
   public void insert(DbConnection dbConn) throws Exception {
	   
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      RegistroSecuenciaTabla table = new RegistroSecuenciaTabla();
      
      logger.debug("Insert Registry Number Sequence <-- Sequence: " + this.toString());
      
      try {
    	  tableInfo.setTableObject(table);
    	  tableInfo.setClassName(RegistroSecuenciaTabla.class.getName());
    	  tableInfo.setTablesMethod("getTableName");
    	  tableInfo.setColumnsMethod("getAllColumnsName");
         
    	  rowInfo.addRow(this);
    	  rowInfo.setClassName(RegistroSecuenciaDatos.class.getName());
    	  rowInfo.setValuesMethod("insert");
    	  rowsInfo.add(rowInfo);
         
    	  DynamicFns.insert(dbConn, tableInfo, rowsInfo);
      }
      catch (RegistroExcepcion e) {
    	  throw e;
      }
      catch (Exception e) {
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
      }
   }
   
   /**
    * Borra un registro.
    *
    * @throws Exception Si se produce algún error.
    */
   public void delete(String entidad) throws Exception {
      RegistroSecuenciaTabla table = new RegistroSecuenciaTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Delete Rgistry Number Sequence <-- Sequence Year: " + this.getYear());
      
      try {
       	 dbConn.open(DBSessionManager.getSession(entidad));
         
         DbDeleteFns.delete(dbConn, table.getTableName(), table.getByYearQual(year));
         
      } catch (RegistroExcepcion e) {
         throw e;
      } catch (Exception e) {
        throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
              dbConn.close();
      }
   }
   
   
}
