package ieci.tecdoc.sgm.autenticacion.util.database;


import ieci.tecdoc.sgm.autenticacion.exception.AutenticacionExcepcion;
import ieci.tecdoc.sgm.autenticacion.exception.SeguridadCodigosError;
import ieci.tecdoc.sgm.autenticacion.exception.SeguridadExcepcion;
import ieci.tecdoc.sgm.autenticacion.util.SesionInfo;
import ieci.tecdoc.sgm.autenticacion.util.SesionInfoImpl;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * Clase que representa las acciones que se pueden realizar sobre la tabla
 * que almacena las sesiones de usuarios, SGMAFSESION_INFO
 *
 */
public class SesionInfoDatos extends SesionInfoImpl implements Serializable {
  private static final Logger logger = Logger.getLogger(SesionInfoDatos.class);

   /**
    * Constructor de la clase SesionInfoDatos que no acepta parámetros
    * de entrada.
    */
   public SesionInfoDatos() {
   }

   /**
    * Constructor de la clase SesionInfoDatos que acepta como parámetros
    * un objeto de tipo SesionInfo que contiene datos de la sesión
    * @param sessionInfo Datos de la sesión
    */
   public SesionInfoDatos(SesionInfo sessionInfo) {
      this.setHookId(sessionInfo.getHookId());
      this.setLoginDate(sessionInfo.getLoginDate());
      this.setSender(sessionInfo.getSender());
      this.setSessionId(sessionInfo.getSessionId());
      this.setIdEntidad(sessionInfo.getIdEntidad());
   }

   /**
    * Recupera un registro con el identificador de sesión que se pasa como
    * parámetro.
    * @param sessionId Identificador de sesión.
    * @throws Exception Si se produce algún error.
    */
   public void load(String sessionId, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      SesionInfoTabla table = new SesionInfoTabla();
      DbConnection dbConn = new DbConnection();

      logger.debug("Load Session <-- SessionId: " + sessionId);

      try {
         //dbConn.open(Configuracion.getDatabaseConnection());
    	 dbConn.open(DBSessionManager.getSession(entidad));

         tableInfo.setTableObject(table);
         tableInfo.setClassName(SesionInfoTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnsName");

         rowInfo.addRow(this);
         rowInfo.setClassName(SesionInfoDatos.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);

         if (!DynamicFns.select(dbConn, table.getBySessionIdQual(sessionId), tableInfo,
                 rowsInfo)) {
            throw new SeguridadExcepcion(SeguridadCodigosError.EC_BAD_SESSION);
         }

         logger.debug("Load Session --> Session: " + this.toString());
      } catch (AutenticacionExcepcion exc) {
         throw exc;
      } catch (Exception e) {
         throw new SeguridadExcepcion(SeguridadCodigosError.EC_ERR_DB);
      } finally {
      	 if (dbConn.existConnection())
            dbConn.close();
      }
   }

   /**
    * Recupera el identificador de conector utilizado en una sesión (si la
    * sesión ya ha sido creada previamente.
    * @param sessionId Identificador de sesión.
    * @throws Exception Si se produce algún error.
    */
   public void loadHook(String sessionId, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      SesionInfoTabla table = new SesionInfoTabla();
      DbConnection dbConn = new DbConnection();

      logger.debug("Load Hook <-- SessionId: " + sessionId);

      try {
    	 //dbConn.open(Configuracion.getDatabaseConnection());
     	 dbConn.open(DBSessionManager.getSession(entidad));

         tableInfo.setTableObject(table);
         tableInfo.setClassName(SesionInfoTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getHookIdColumnName");

         rowInfo.addRow(this);
         rowInfo.setClassName(SesionInfoDatos.class.getName());
         rowInfo.setValuesMethod("loadHookValue");
         rowsInfo.add(rowInfo);

         if (!DynamicFns.select(dbConn, table.getBySessionIdQual(sessionId), tableInfo,
                 rowsInfo)) {
            hookId = null;
         }

         logger.debug("Load Hook --> HoodId: " + this.getHookId());
      } catch (AutenticacionExcepcion exc) {
         throw exc;
      } catch (Exception e) {
         throw new SeguridadExcepcion(SeguridadCodigosError.EC_ERR_DB);
      } finally {
      	 if (dbConn.existConnection())
            dbConn.close();
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
      SesionInfoTabla table = new SesionInfoTabla();
      DbConnection dbConn = new DbConnection();

      logger.debug("Insert Session <-- Session: " + this.toString());

      try {
    	 //dbConn.open(Configuracion.getDatabaseConnection());
     	 dbConn.open(DBSessionManager.getSession(entidad));

         tableInfo.setTableObject(table);
         tableInfo.setClassName(SesionInfoTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnsName");

         rowInfo.addRow(this);
         rowInfo.setClassName(SesionInfoDatos.class.getName());
         rowInfo.setValuesMethod("insert");
         rowsInfo.add(rowInfo);

         DynamicFns.insert(dbConn,tableInfo, rowsInfo);
      } catch (AutenticacionExcepcion e) {
         throw e;
      } catch (Exception e) {
         throw new SeguridadExcepcion(SeguridadCodigosError.EC_ERR_DB);
      } finally {
         if (dbConn.existConnection())
            dbConn.close();
      }
   }

   /**
    * Elimina este registro.
    * @throws Exception Si se produce algún error.
    */
   public void delete(String entidad) throws Exception {
      SesionInfoTabla table = new SesionInfoTabla();
      DbConnection dbConn = new DbConnection();

      logger.debug("Delete Session <-- SessionId: " + this.getSessionId());

      try {
    	 //dbConn.open(Configuracion.getDatabaseConnection());
     	 dbConn.open(DBSessionManager.getSession(entidad));

         DbDeleteFns.delete(dbConn, table.getTableName(), table
                 .getBySessionIdQual(getSessionId()));

      } catch (AutenticacionExcepcion e) {
         throw e;
      } catch (Exception e) {
         throw new SeguridadExcepcion(SeguridadCodigosError.EC_ERR_DB);
      } finally {
         if (dbConn.existConnection())
            dbConn.close();
      }
   }

   /**
    * Elimina las sesión anteriores a una fecha (sesiones caducadas)
    * @param timeout Tiempo (en minutos) en el que la sesión caduca.
    * @throws Exception Si se produce algún error.
    */
   public static void clean(int timeout, String entidad) throws Exception {
      SesionInfoTabla table = new SesionInfoTabla();
      DbConnection dbConn = new DbConnection();

      logger.debug("Clean Session <-- TimeOut: " + timeout);

      try {
    	 //dbConn.open(Configuracion.getDatabaseConnection());
     	 dbConn.open(DBSessionManager.getSession(entidad));

         DbDeleteFns.delete(dbConn, table.getTableName(), table.getByTimeoutQualDBConn(timeout, dbConn));

      } catch (AutenticacionExcepcion e) {
         throw e;
      } catch (Exception e) {
         throw new SeguridadExcepcion(SeguridadCodigosError.EC_ERR_DB);
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

      hookId = statement.getShortText(index++);
      sessionId = statement.getShortText(index++);
      loginDate = statement.getShortText(index++);
      sender.setName(statement.getShortText(index++));
      sender.setId(statement.getShortText(index++));
      sender.setEmail(statement.getShortText(index++));
      sender.setCertificateIssuer(statement.getShortText(index++));
      sender.setCertificateSN(statement.getShortText(index++));
      sender.setInQuality(statement.getShortText(index++));
      sender.setSocialName(statement.getShortText(index++));
      sender.setCIF(statement.getShortText(index++));
      idEntidad = statement.getShortText(index++);
      sender.setFirstName(statement.getShortText(index++));
      sender.setSurName(statement.getShortText(index++));
      sender.setSurName2(statement.getShortText(index++));

      return new Integer(index);
   }

   /**
    * Recupera el identificador de conector.
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro que se recoge de la
    *  consulta.
    * @return Indice de posición del último parámetro recogido
    * @throws Exception Si se produce algún error.
    */
   public Integer loadHookValue(DbOutputStatement statement, Integer idx)
   throws Exception {

      int index = idx.intValue();

      hookId = statement.getShortText(index++);

      return new Integer(index);
   }

   /**
    * Genera la sentencia de inserción de un registro.
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro en la sentencia.
    * @return Indice de posición del último parámetro en la sentencia.
    * @throws Exception Si se produce algún error.
    */
   public Integer insert(DbInputStatement statement, Integer idx)
   throws Exception {
      int index = idx.intValue();

      statement.setShortText(index++, hookId);
      statement.setShortText(index++, sessionId);
      statement.setShortText(index++, loginDate);
      statement.setShortText(index++, sender.getName());
      statement.setShortText(index++, sender.getId());
      statement.setShortText(index++, sender.getEmail());
      statement.setShortText(index++, sender.getCertificateIssuer());
      statement.setShortText(index++, sender.getCertificateSN());
      statement.setShortText(index++, sender.getInQuality());
      statement.setShortText(index++, sender.getSocialName());
      statement.setShortText(index++, sender.getCIF());
      statement.setShortText(index++, idEntidad);
      statement.setShortText(index++, sender.getFirstName());
      statement.setShortText(index++, sender.getSurName());
      statement.setShortText(index++, sender.getSurName2());

      return new Integer(index);
   }

   /**
    * Genera la sentencia de actualización de un blob.
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro en la sentencia.
    * @return Indice de posición del último parámetro en la sentencia.
    * @throws Exception Si se produce algún error.
    */
   public Integer updateBlob(DbOutputStatement statement, Integer idx) throws Exception {
      int index = idx.intValue();
      return new Integer(index);
   }

}