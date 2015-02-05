package ieci.tecdoc.sgm.catalogo_tramites.util.database;


import org.apache.log4j.Logger;

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
import ieci.tecdoc.sgm.catalogo_tramites.util.ConectorAutenticacion;
import ieci.tecdoc.sgm.catalogo_tramites.util.ConectorAutenticacionImpl;
import ieci.tecdoc.sgm.catalogo_tramites.util.ConectoresAutenticacion;
import ieci.tecdoc.sgm.catalogo_tramites.util.database.DBSessionManager;

import java.io.Serializable;

/**
 * Clase que representa las acciones que se pueden realizar sobre la tabla 
 * que almacena las sesiones de usuarios, SGMAFSESION_INFO
 *
 */
public class ConectorAutenticacionDatos extends ConectorAutenticacionImpl implements Serializable {
  private static final Logger logger = Logger.getLogger(ConectorAutenticacionDatos.class);
  
   /**
    * Constructor de la clase SesionInfoDatos que no acepta parámetros 
    * de entrada.
    */
   public ConectorAutenticacionDatos() {
   }
   
   /**
    * Constructor de la clase ConectorAutenticacionDatos que acepta como parámetros 
    * un objeto de tipo ConectorAutenticacion que contiene datos del conector
    * @param conectorAutenticacion Datos del conector de autenticación
    */
   public ConectorAutenticacionDatos(ConectorAutenticacion conectorAutenticacion) {
      this.setConectorId(conectorAutenticacion.getConectorId());
      this.setTramiteId(conectorAutenticacion.getTramiteId());
   }
   
   /**
    * Recupera registros con el identificador de trámite que se pasa como
    * parámetro.
    * @param tramiteId Identificador de tramite.
    * @param conectorId Identificador de conector de autenticación.
    * @throws Exception Si se produce algún error.
    */
   public void load(String tramiteId, String conectorId, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      ConectorAutenticacionTabla table = new ConectorAutenticacionTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Load AuthHook <-- TramiteId: " + tramiteId + " ConectorId: " + conectorId);
      
      try {
    	 //dbConn.open(Configuracion.getDatabaseConnection());
     	 dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(ConectorAutenticacionTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(ConectorAutenticacionDatos.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select(dbConn, table.getByTramiteAndConectorIdQual(tramiteId, conectorId), tableInfo, rowsInfo)) {
            throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_BAD_PROCEDUREID_OR_HOOKID);
         }
         
         logger.debug("Load AuthHook --> AuthHook: " + this.toString());
      } catch (CatalogoTramitesExcepcion e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_AUTENTICATION_HOOK);
      } catch (Exception e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
      	 if (dbConn.existConnection())
            dbConn.close();
      }
   }
   
   
   /**
    * Inserta un conecor de autenticación.
    * @throws Exception Si se produce algún error.
    */
   public void insert(String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      ConectorAutenticacionTabla table = new ConectorAutenticacionTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Insert AuthHook <-- AuthHook: " + this.toString());
      
      try {
    	 //dbConn.open(Configuracion.getDatabaseConnection());
     	 dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(ConectorAutenticacionTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(ConectorAutenticacionDatos.class.getName());
         rowInfo.setValuesMethod("insert");
         rowsInfo.add(rowInfo);
         
         DynamicFns.insert(dbConn, tableInfo, rowsInfo);
      } catch (CatalogoTramitesExcepcion e) {
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_AUTENTICATION_HOOK);
      } catch (Exception e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
         if (dbConn.existConnection())
            dbConn.close();
      }
   }
   
   
   /**
    * Modifica un conecor de autenticación.
    * @throws Exception Si se produce algún error.
    */
   public void update(String oldConectorId, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      ConectorAutenticacionTabla table = new ConectorAutenticacionTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Update AuthHook <-- OldConectorId: " + oldConectorId + " AuthHook: " + this.toString());
      
      try {
    	 //dbConn.open(Configuracion.getDatabaseConnection());
     	 dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(ConectorAutenticacionTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllUpdateColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(ConectorAutenticacionDatos.class.getName());
         rowInfo.setValuesMethod("update");
         rowsInfo.add(rowInfo);
         
         DynamicFns.update(dbConn, table.getByTramiteAndConectorIdQual(tramiteId, oldConectorId) ,tableInfo, rowsInfo);
      } catch (CatalogoTramitesExcepcion e) {
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_AUTENTICATION_HOOK);
      } catch (Exception e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
         if (dbConn.existConnection())
            dbConn.close();
      }
   }
   
   
   /**
    * Elimina un conector de autenticación.
    * @throws Exception Si se produce algún error.
    */
   public void delete(String entidad) throws Exception {
	  ConectorAutenticacionTabla table = new ConectorAutenticacionTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Delete AuthHook <-- tramiteId: " + this.getTramiteId() + " conectorId: " + this.getConectorId());
      
      try {
    	 //dbConn.open(Configuracion.getDatabaseConnection());
     	 dbConn.open(DBSessionManager.getSession(entidad));
     	 
         String query = "";
         if(getConectorId() == null || getConectorId().equals(""))
        	 query = table.getByTramiteIdQual(getTramiteId());
         else query = table.getByTramiteAndConectorIdQual(getTramiteId(), getConectorId());

         DbDeleteFns.delete(dbConn, table.getTableName(), query);
         
      } catch (CatalogoTramitesExcepcion e) {
    	  throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_AUTENTICATION_HOOK);
      } catch (Exception e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
         if (dbConn.existConnection())
            dbConn.close();
      }
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
      
      tramiteId = statement.getShortText(index++);
      conectorId = statement.getShortText(index++);
      
      return new Integer(index);
   }
   
   
   /**
    * Genera la sentencia de inserción de un conector de autenticación.
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro en la sentencia.
    * @return Indice de posición del último parámetro en la sentencia.
    * @throws Exception Si se produce algún error.
    */
   public Integer insert(DbInputStatement statement, Integer idx)
   throws Exception {
      int index = idx.intValue();
      
      statement.setShortText(index++, tramiteId);
      statement.setShortText(index++, conectorId);

      return new Integer(index);
   }
   
   
   /**
    * Genera la sentencia de actualización de un conector de autenticación.
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro en la sentencia.
    * @return Indice de posición del último parámetro en la sentencia.
    * @throws Exception Si se produce algún error.
    */
   public Integer update(DbInputStatement statement, Integer idx)
   throws Exception {
      int index = idx.intValue();
      
      statement.setShortText(index++, conectorId);

      return new Integer(index);
   }
   
   
   /**
    * Recupera la lista de conectores.
    * @param hookType Tipo de conector (-1 si se quieren todos los tipos)
    * @return Lista de conectores.
    * @throws Exception Si se produce algún error.
    */
   public static ConectoresAutenticacion getAuthHooks(String tramiteId, String entidad) throws Exception {
	  ConectoresAutenticacion authHooks = new ConectoresAutenticacion();
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfoDocument = new DynamicRow();
      ConectorAutenticacionTabla table = new ConectorAutenticacionTabla();
      DbConnection dbConn = new DbConnection();
      ConectorAutenticacionDatos authHook;
      int counter, size;
      
      logger.debug("getAuthHooks <-- tramiteId: " + tramiteId);
      
      try {
    	 //dbConn.open(Configuracion.getDatabaseConnection());
     	 dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(ConectorAutenticacionTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfoDocument.setClassName(ConectorAutenticacionDatos.class.getName());
         rowInfoDocument.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfoDocument);
         
         String query = "";
         if (tramiteId != null && !tramiteId.equals(""))
        	 query = table.getByTramiteIdQual(tramiteId);
         
         DynamicFns.selectMultiple(dbConn, query, false, tableInfo, rowsInfo);
         size = rowInfoDocument.getRowCount();
         
         for (counter = 0; counter < size; counter++) {
            authHook = (ConectorAutenticacionDatos)rowInfoDocument.getRow(counter);
            authHooks.add(authHook);
         }
         
      } catch (CatalogoTramitesExcepcion e) {
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_AUTENTICATION_HOOKS);
      } catch (Exception e) {
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
       	 if (dbConn.existConnection())
            dbConn.close();
      }
      
      return authHooks;
   }
}