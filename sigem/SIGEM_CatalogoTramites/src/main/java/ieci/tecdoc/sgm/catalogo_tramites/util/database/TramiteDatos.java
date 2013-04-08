
package ieci.tecdoc.sgm.catalogo_tramites.util.database;

import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesCodigosError;
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.catalogo_tramites.util.Tramite;
import ieci.tecdoc.sgm.catalogo_tramites.util.TramiteConsulta;
import ieci.tecdoc.sgm.catalogo_tramites.util.TramiteImpl;
import ieci.tecdoc.sgm.catalogo_tramites.util.Tramites;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de trámites.
 * @see TramiteTable
 * @see Tramite
 * @see TramiteImpl
 * @see Tramites
 */
public class TramiteDatos extends TramiteImpl implements Serializable {
  private static final Logger logger = Logger.getLogger(TramiteDatos.class);
  private static final boolean isDebugeable = true;
  
  /**
   * Construcor de la clase ProcedureData
   */
  public TramiteDatos() {
   }
   
  
   /**
    * Constructor de la clase ProcedureData a partir de un objeto
    * @param procedure Datos del trámite
    */
   public TramiteDatos(Tramite procedure) {
      this.setId(procedure.getId());
      this.setTopic(procedure.getTopic());
      this.setDescription(procedure.getDescription());
      this.setAddressee(procedure.getAddressee());
      this.setLimitDocs(procedure.getLimitDocs());
      this.setFirma(procedure.getFirma());
      this.setOficina(procedure.getOficina());
      this.setIdProcedimiento(procedure.getIdProcedimiento());
   }
   
   
   /**
    * Recupera todos los valores de los parámetros de la sentencia de consulta
    * que se pasa como parámetro.
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro que se recoge de la
    *  consulta.
    * @return Indice de posición del último parámetro recogido
    * @throws Exception Si se produce algún error.
    */
   public Integer loadAllValues(DbOutputStatement statement, Integer idx)
   throws Exception {

      int index = idx.intValue();
      
      id = statement.getShortText(index++);
      topic = statement.getShortText(index++);
      description = statement.getShortText(index++);
      addressee = statement.getShortText(index++);
      if (statement.getShortText(index++).equals("1")) {
         limitDocs = true;
      } else {
         limitDocs = false;
      }
      if (statement.getShortText(index++).equals("1")) {
          firma = true;
       } else {
          firma = false;
       }
      oficina = statement.getShortText(index++);
      idProcedimiento = statement.getShortText(index++);
      return new Integer(index);
   }

   
   /**
    * Genera la sentencia de inserción de un registro.
    * @param statement Sentencia SQL precompilada.
    * @param idx Indice de posición del primer parámetro en la sentencia.
    * @return Indice de posición del último parámetro en la sentencia.
    * @throws Exception Si se produce algún error.
    */
   public Integer insert(DbInputStatement statement, Integer idx)
   throws Exception {
      int index = idx.intValue();

      statement.setShortText(index++, id);
      statement.setShortText(index++, topic);
      statement.setShortText(index++, description);
      statement.setShortText(index++, addressee);
      if (limitDocs) {
         statement.setShortText(index++, "1");
      } else {
         statement.setShortText(index++, "0");
      }
      if (firma) {
          statement.setShortText(index++, "1");
       } else {
          statement.setShortText(index++, "0");
       }
      statement.setShortText(index++, oficina);
      statement.setShortText(index++, idProcedimiento);
      return new Integer(index);
   }
   

   /**
    * Genera la sentencia de actualización de un registro.
    * @param statement Sentencia SQL precompilada.
    * @param idx Indice de posición del primer parámetro en la sentencia.
    * @return Indice de posición del último parámetro en la sentencia.
    * @throws Exception Si se produce algún error.
    */
   public Integer updateAll(DbInputStatement statement, Integer idx)
   throws Exception {
      int index = idx.intValue();
 
      statement.setShortText(index++, topic);
      statement.setShortText(index++, description);
      statement.setShortText(index++, addressee);
      if (limitDocs) {
         statement.setShortText(index++, "1");
      } else {
         statement.setShortText(index++, "0");
      }
      if (firma) {
          statement.setShortText(index++, "1");
       } else {
          statement.setShortText(index++, "0");
       }
      statement.setShortText(index++, oficina);
      statement.setShortText(index++, idProcedimiento);
      return new Integer(index);
      
   }
   
   /**
    * Recupera un registro con el identificador de procedimiento o trámite que
    * se pasa como parámetro.
    * @param id Identificador de trámite.
    * @throws Exception Si se produce algún error.
    */
   public void load(String procedureId, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      TramiteTable table = new TramiteTable();
      DbConnection dbConn = new DbConnection();
      boolean load = true;
      
      if (isDebugeable)
        logger.debug("Load Procedure <-- Procedure ID: " + procedureId);
      
      try {
      	 //dbConn.open(Configuracion.getDatabaseConnection());
         dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(TramiteTable.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(TramiteDatos.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select(dbConn, table.getByIdQual(procedureId), tableInfo, rowsInfo)) {
            load = false;
         }
         
         logger.debug("Load Procedure --> Procedure: " + this.toString());
      } catch (CatalogoTramitesExcepcion e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_PROCEDURE);
      } catch (Exception e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
	           dbConn.close();
         if (!load)
            throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_BAD_PROCEDUREID);
      }
   }
   
   
   /**
    * Inserta un registro.
    * @throws Exception Si se produce algún error.
    */
   public void insert(String entidad) throws Exception {      
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      TramiteTable table = new TramiteTable();
      DbConnection dbConn = new DbConnection();

      logger.debug("Insert Procedure <-- Procedure: " + this.toString());
      
      try {
       	 //dbConn.open(Configuracion.getDatabaseConnection());
         dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(TramiteTable.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(TramiteDatos.class.getName());
         rowInfo.setValuesMethod("insert");
         rowsInfo.add(rowInfo);
         
         DynamicFns.insert(dbConn, tableInfo, rowsInfo);
         
      } catch (CatalogoTramitesExcepcion e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_PROCEDURE);
      } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
	           dbConn.close();
      }
   }
   
   
   /**
    * Actualiza el registro cuyo identificador de trámite se pasa como
    * parámetro.
    * @throws Exception Si se produce algún error.
    */
   public void update(String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      TramiteTable table = new TramiteTable();
      DbConnection dbConn = new DbConnection();

      logger.debug("Update Procedure <-- Procedure: " + this.toString());
      
      try {
       	 //dbConn.open(Configuracion.getDatabaseConnection());
         dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(TramiteTable.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getUpdateColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(TramiteDatos.class.getName());
         rowInfo.setValuesMethod("updateAll");
         rowsInfo.add(rowInfo);
         
         DynamicFns.update(dbConn, table.getByIdQual(id), tableInfo, rowsInfo);
      } catch (CatalogoTramitesExcepcion e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_PROCEDURE);
      } catch (Exception e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
         if (dbConn.existConnection())
	           dbConn.close();
      }
   }
   
   
   /**
    * Borra el registro cuyo identificador de trámite se pasa como parámetro.
    * @param id Identificador de trámite.
    * @throws Exception Si se produce algún error.
    */
   public void delete(String entidad) throws Exception {
      TramiteTable table = new TramiteTable();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Delete Procedure <-- Procedure ID: " + id);
      
      try {
       	 //dbConn.open(Configuracion.getDatabaseConnection());
         dbConn.open(DBSessionManager.getSession(entidad));
         
         DbDeleteFns.delete(dbConn, table.getTableName(), table.getByIdQual(id));         
         DocumentoTramiteDatos.delete(id, entidad);

      } catch (CatalogoTramitesExcepcion e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_PROCEDURE);
      } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
	           dbConn.close();
      }
   }

   
   /**
    * Recupera la lista de trámites.
    *
    * @return Lista de trámites.
    * @throws Exception Si se produce algún error.
    */
   public static Tramites getProcedures(String entidad) throws Exception {
      Tramites procedures = new Tramites();
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfoProcedure = new DynamicRow();
      TramiteTable table = new TramiteTable();
      DbConnection dbConn = new DbConnection();
      TramiteDatos procedure;
      int counter, size;

      logger.debug("Get Procedures");
      
      try {
       	 //dbConn.open(Configuracion.getDatabaseConnection());
         dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(TramiteTable.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfoProcedure.setClassName(TramiteDatos.class.getName());
         rowInfoProcedure.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfoProcedure);
         
         DynamicFns.selectMultiple(dbConn, table.getOrderByDesc(), false, tableInfo, rowsInfo);
         size = rowInfoProcedure.getRowCount();
         
         for (counter = 0; counter < size; counter++) {
            procedure = (TramiteDatos)rowInfoProcedure.getRow(counter);
            procedures.add(procedure);
         }
         
      } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_PROCEDURES);
      } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
	           dbConn.close();
      }
      
      return procedures;
   }
   
   /**
    * Realiza una consulta determinada
    *
    * @param query Datos para realizar la consulta
    * @return Trámites devueltos por la consulta
    * @throws Exception Si se produce algún error.
    */
   public DynamicRow query(TramiteConsulta query, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      TramiteTable table = new TramiteTable();
      ArrayList queryList = new ArrayList();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Query Procedure <-- Query: " + query.toString());
      
      try {
       	 dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(TramiteTable.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(this.getClass().getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         for (int i = 0; i<=2; i++) {
            queryList.add(i,"");
         }
         if (query.getId() != null)
            queryList.set(0,query.getId());
         if (query.getTopic() != null)
            queryList.set(1,query.getTopic());
         if (query.getAddressee() != null)
            queryList.set(2,query.getAddressee());
         
         DynamicFns.selectMultiple(dbConn, table.getByQuery(queryList),false,tableInfo, rowsInfo);
         
      } catch (Exception e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
              dbConn.close();
      }
      return rowInfo;
   }
}
