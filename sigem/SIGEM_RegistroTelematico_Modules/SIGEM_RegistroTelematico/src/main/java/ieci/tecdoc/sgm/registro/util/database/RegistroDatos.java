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
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.core.services.telematico.Registro;
import ieci.tecdoc.sgm.rde.database.util.QueryOperators;
import ieci.tecdoc.sgm.registro.exception.RegistroCodigosError;
import ieci.tecdoc.sgm.registro.exception.RegistroExcepcion;
import ieci.tecdoc.sgm.registro.util.RegistroConsulta;
import ieci.tecdoc.sgm.registro.util.RegistroImpl;

import java.util.ArrayList;

import org.apache.log4j.Logger;


//import java.io.ByteArrayInputStream;

/**
 * Wrapper de control de ejecución de sentencias SQL en la tabla
 * SGMRTREGISTRO.
 *
 * @see RegistroTabla
 * @see Registro
 */
public class RegistroDatos extends RegistroImpl {
	private static final Logger logger = Logger.getLogger(RegistroDatos.class);

   public RegistroDatos() {
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

      registryNumber = statement.getShortText(index ++);
      registryDate = statement.getDateTime(index ++);
      senderId = statement.getShortText(index ++);
      name = statement.getShortText(index ++);
      //surName = statement.getShortText(index ++);
      eMail = statement.getShortText(index ++);
      topic = statement.getShortText(index ++);
      addressee = statement.getShortText(index ++);
      status = statement.getShortInteger(index ++);
      additionalInfo = statement.getBytes(index++);
      senderIdType = statement.getShortInteger(index ++);
      oficina = statement.getShortText(index ++);
      numeroExpediente = statement.getShortText(index ++);
      effectiveDate = statement.getDateTime(index ++);

      return new Integer(index);
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
   public Integer loadInfoValues(DbOutputStatement statement, Integer idx)
   throws Exception {

      int index = idx.intValue();

      registryNumber = statement.getShortText(index ++);
      registryDate = statement.getDateTime(index ++);
      effectiveDate = statement.getDateTime(index ++);
      status = statement.getShortInteger(index ++);
      senderId = statement.getShortText(index ++);
      name = statement.getShortText(index ++);
      //surName = statement.getShortText(index ++);
      topic = statement.getShortText(index ++);
      if (topic != null) {
    	  topic += "##" + statement.getShortText(index ++);
      }
      additionalInfo = statement.getBytes(index++);
      return new Integer(index);
   }

   /**
    * Genera la sentencia de inserción de un registro.
    *
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro en la sentencia.
    * @return Indice de posición del último parámetro en la sentencia.
    * @throws Exception Si se produce algún error.
    */
   public Integer insert(DbInputStatement statement, Integer idx)
   throws Exception {
      int index = idx.intValue();

      statement.setShortText(index++, registryNumber);
      statement.setDateTime(index ++, registryDate);
      statement.setShortText(index ++, senderId);
      statement.setShortText(index ++, name);
      //statement.setShortText(index ++, surName);
      statement.setShortText(index ++, eMail);
      statement.setShortText(index ++, topic);
      statement.setShortText(index ++,addressee);
      statement.setShortInteger(index ++, (short)status);
      statement.setBytes(index ++, additionalInfo);
      statement.setShortInteger(index ++, (short)senderIdType);
      statement.setShortText(index ++, oficina);
      statement.setShortText(index ++, numeroExpediente);
      //statement.setDateTime(index ++, effectiveDate);

      return new Integer(index);
   }

   public Integer updateBlob(DbOutputStatement statement, Integer idx) throws Exception {
      int index = idx.intValue();

      return new Integer(index);
   }

   /**
    * Genera la sentencia de actualización
    * de la fecha efectiva y del estado de un registro.
    *
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro que se recoge
    * de la consulta.
    * @return Indice de posición del último parámtro recogido
    * @throws Exception Si se produce algún error.
    */
   public Integer updateEffectiveDateAndStatus(DbInputStatement statement, Integer idx) throws Exception {
      int index = idx.intValue();

      statement.setDateTime(index ++, effectiveDate);
      statement.setShortInteger(index++, (short)status);

      return new Integer(index);
   }

   /**
    * Genera la sentencia de actualización de estado de un
    * registro.
    *
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro que se recoge
    * de la consulta.
    * @return Indice de posición del último parámtro recogido
    * @throws Exception Si se produce algún error.
    */
   public Integer updateStatus(DbInputStatement statement, Integer idx) throws Exception {
      int index = idx.intValue();

      statement.setShortInteger(index++, (short)status);

      return new Integer(index);
   }

   /**
    * Genera la sentencia de actualización de expediente de un
    * registro.
    *
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro que se recoge
    * de la consulta.
    * @return Indice de posición del último parámtro recogido
    * @throws Exception Si se produce algún error.
    */
   public Integer updateFolderId(DbInputStatement statement, Integer idx) throws Exception {
      int index = idx.intValue();

      return new Integer(index);
   }


   /**
    * Recupera un  registro con el número de registro que se pasa
    * como parámetro.
    *
    * @param registryNumber Número de registro.
    * @throws Exception Si se produce algún error.
    */
   public void load(String registryNumber, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      RegistroTabla table = new RegistroTabla();
      DbConnection dbConn = new DbConnection();

      logger.debug("Load Registry <-- Registry Number: " + registryNumber);

      try {
       	 dbConn.open(DBSessionManager.getSession(entidad));

         tableInfo.setTableObject(table);
         tableInfo.setClassName(RegistroTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnsName");

         rowInfo.addRow(this);
         rowInfo.setClassName(RegistroDatos.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);

         if (!DynamicFns.select(dbConn, table.getByRegistryNumberQual(registryNumber), tableInfo, rowsInfo,false)) {
            throw new RegistroExcepcion(RegistroCodigosError.EC_BAD_REGISTRY_NUMBER);
         }

         logger.debug("Load Registry --> Registry: " + this.toString());
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
    * Realiza una consulta determinada
    *
    * @param query Datos para realizar la consulta
    * @return Registros devueltos por la consulta
    * @throws Exception Si se produce algún error.
    */
   public DynamicRow query(RegistroConsulta query, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      RegistroTabla table = new RegistroTabla();
      ArrayList queryList = new ArrayList();
      DbConnection dbConn = new DbConnection();
      String operator ="";

      logger.debug("Query Registry <-- Query: " + query.toString());

      try {
       	 dbConn.open(DBSessionManager.getSession(entidad));

         tableInfo.setTableObject(table);
         tableInfo.setClassName(RegistroTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnsName");

         rowInfo.addRow(this);
         rowInfo.setClassName(this.getClass().getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         for (int i = 0; i<=12; i++) {
            queryList.add(i,"");
         }
         operator = QueryOperators.getSqlOperator(query.getOprRegistryNumber());
         if (query.getRegistryNumber() != null)
            queryList.set(0,query.getRegistryNumber());
         if (query.getFirstRegistryDate() != null)
            queryList.set(1,query.getFirstRegistryDate());
         if (query.getLastRegistryDate() != null)
            queryList.set(2,query.getLastRegistryDate());
         if (query.getSenderId() != null)
            queryList.set(3, query.getSenderId());
         if (query.getTopic() != null)
            queryList.set(4, query.getTopic());
         if (query.getAddressee() != null)
            queryList.set(5, query.getAddressee());
         if (query.getStatus() != -1 )
            queryList.set(10, String.valueOf(query.getStatus()));
         if (query.getFirstEffectiveDate() != null)
	         queryList.set(11,query.getFirstEffectiveDate());
	     if (query.getLastEffectiveDate() != null)
	         queryList.set(12,query.getLastEffectiveDate());
         DynamicFns.selectMultiple(dbConn, table.getByQuery(dbConn, queryList, operator), false, tableInfo, rowsInfo);

      } catch (Exception e) {
         throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
              dbConn.close();
      }
      return rowInfo;
   }

   /**
    * Recupera registros atendiendo a su estado
    *
    * @param status Estado del registro.
    * @return Registros devueltos con un estado determinado.
    * @throws Exception Si se produce algún error.
    */
   public DynamicRow getRegistriesByStatus(int status, String entidad) throws Exception {
    DynamicTable tableInfo = new DynamicTable();
    DynamicRows rowsInfo = new DynamicRows();
    DynamicRow rowInfo = new DynamicRow();
    RegistroTabla table = new RegistroTabla();
    DbConnection dbConn = new DbConnection();

    logger.debug("Get Registries By Status <-- Status: " + status);

    try {
   	 	dbConn.open(DBSessionManager.getSession(entidad));

       tableInfo.setTableObject(table);
       tableInfo.setClassName(RegistroTabla.class.getName());
       tableInfo.setTablesMethod("getTableName");
       tableInfo.setColumnsMethod("getAllColumnsName");

       rowInfo.addRow(this);
       rowInfo.setClassName(this.getClass().getName());
       rowInfo.setValuesMethod("loadAllValues");
       rowsInfo.add(rowInfo);
       if (!DynamicFns.selectMultiple(dbConn, table.getByRegistryStatusQual(String.valueOf(status)),false,tableInfo, rowsInfo)) {
          throw new RegistroExcepcion(RegistroCodigosError.EC_NO_REGISTRY);
       }
    } catch (Exception e) {
       throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
    } finally {
    	if (dbConn.existConnection())
            dbConn.close();
    }
    return rowInfo;
 }

   /**
    * Recupera registros atendiendo a su estado
    *
    * @param status Estado del registro.
    * @return Registros devueltos con un estado determinado.
    * @throws Exception Si se produce algún error.
    */
   public DynamicRow getRegistriesToShow(RegistroConsulta query, String entidad) throws Exception {
	     DynamicTable tableInfo = new DynamicTable();
	      DynamicRows rowsInfo = new DynamicRows();
	      DynamicRow rowInfo = new DynamicRow();
	      RegistroTabla table = new RegistroTabla();
	      ArrayList queryList = new ArrayList();
	      DbConnection dbConn = new DbConnection();
	      String operator ="";

	      logger.debug("Query Registry <-- Query: " + query.toString());

	      try {
	       	 dbConn.open(DBSessionManager.getSession(entidad));

	         tableInfo.setTableObject(table);
	         tableInfo.setClassName(RegistroTabla.class.getName());
	         tableInfo.setTablesMethod("getTableName");
	         tableInfo.setColumnsMethod("getInfoColumnsName");

	         rowInfo.addRow(this);
	         rowInfo.setClassName(this.getClass().getName());
	         rowInfo.setValuesMethod("loadInfoValues");
	         rowsInfo.add(rowInfo);
	         for (int i = 0; i<=12; i++) {
	            queryList.add(i,"");
	         }

	         operator = "" + query.getOprRegistryNumber();
	         if (query.getRegistryNumber() != null)
	            queryList.set(0,query.getRegistryNumber());
	         if (query.getFirstRegistryDate() != null)
	            queryList.set(1,query.getFirstRegistryDate());
	         if (query.getLastRegistryDate() != null)
	            queryList.set(2,query.getLastRegistryDate());
	         if (query.getSenderId() != null)
	            queryList.set(3, query.getSenderId());
	         if (query.getTopic() != null)
	            queryList.set(4, query.getTopic());
	         if (query.getAddressee() != null)
	            queryList.set(5, query.getAddressee());
	         if (query.getStatus() != -1 )
	            queryList.set(10, String.valueOf(query.getStatus()));
	         if (query.getFirstEffectiveDate() != null)
		         queryList.set(11,query.getFirstEffectiveDate());
		     if (query.getLastEffectiveDate() != null)
		         queryList.set(12,query.getLastEffectiveDate());
	         DynamicFns.selectMultiple(dbConn, table.getAuxInfo() + table.getByQuery(dbConn, queryList, operator), false, tableInfo, rowsInfo);

	      } catch (Exception e) {
	         throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
	      } finally {
	    	  if (dbConn.existConnection())
	              dbConn.close();
	      }
	      return rowInfo;
 }

   /**
    * Actualiza el estado del registro cuyo número de registro
    * se pasa como parámetro.
    *
    * @param registryNumber Número de registro.
    * @throws Exception Si se produce algún error.
    */
   public void updateStatus(String registryNumber, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      RegistroTabla table = new RegistroTabla();
      DbConnection dbConn = new DbConnection();

      boolean commit = false;
      boolean inTrans = false;
      boolean closeTrans = false;

      logger.debug("Update Regitry Status <-- Registry Number: " + registryNumber);

      try {
         dbConn.open(DBSessionManager.getSession(entidad));

         closeTrans = !dbConn.beginTransaction();
         inTrans = true;

         tableInfo.setTableObject(table);
         tableInfo.setClassName(RegistroTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getStatusColumnName");

         rowInfo.addRow(this);
         rowInfo.setClassName(RegistroDatos.class.getName());
         rowInfo.setValuesMethod("updateStatus");
         rowsInfo.add(rowInfo);

         DynamicFns.update(dbConn, table.getByRegistryNumberQual(registryNumber), tableInfo, rowsInfo);

         commit = true;
      } catch (RegistroExcepcion e) {
         throw e;
      } catch (Exception e) {
         throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
      } finally {
         if (inTrans && closeTrans)
            dbConn.endTransaction(commit);

         if (dbConn.existConnection())
             dbConn.close();
      }
   }

   /**
    * Actualiza la fecha efectiva y el estado del registro.
    *
    * @throws Exception Si se produce algún error.
    */
   	public void updateEffectiveDateAndStatus(String entidad) throws Exception {

   		DynamicTable tableInfo = new DynamicTable();
   		DynamicRows rowsInfo = new DynamicRows();
   		DynamicRow rowInfo = new DynamicRow();
   		RegistroTabla table = new RegistroTabla();

   		DbConnection dbConn = new DbConnection();

   		boolean commit = false;
   		boolean inTrans = false;
   		boolean closeTrans = false;

   		logger.debug("Update Regitry Effective Date and Status <-- Registry Number: " + getRegistryNumber());

   		try {
   			dbConn.open(DBSessionManager.getSession(entidad));

   			closeTrans = !dbConn.beginTransaction();
   			inTrans = true;

   			tableInfo.setTableObject(table);
   			tableInfo.setClassName(RegistroTabla.class.getName());
   			tableInfo.setTablesMethod("getTableName");
   			tableInfo.setColumnsMethod("getUpdateEffectiveDateAndStatus");

   			rowInfo.addRow(this);
   			rowInfo.setClassName(RegistroDatos.class.getName());
   			rowInfo.setValuesMethod("updateEffectiveDateAndStatus");
   			rowsInfo.add(rowInfo);

   			DynamicFns.update(dbConn, table.getByRegistryNumberQual(getRegistryNumber()), tableInfo, rowsInfo);

   			commit = true;
   		}
   		catch (RegistroExcepcion e) {
   			throw e;
   		}
   		catch (Exception e) {
   			throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
   		}
   		finally {
   			if (inTrans && closeTrans)
   				dbConn.endTransaction(commit);

   			if (dbConn.existConnection())
   				dbConn.close();
   		}
   	}

   /**
    * Añade un registro.
    *
    * @throws Exception Si se produce algún error.
    */
   public void add(String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      RegistroTabla table = new RegistroTabla();
      DbConnection dbConn = new DbConnection();

      logger.debug("Add Registry <-- Registry: " + this.toString());

      try {
      	 dbConn.open(DBSessionManager.getSession(entidad));

         tableInfo.setTableObject(table);
         tableInfo.setClassName(RegistroTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getInsertColumnsName");

         rowInfo.addRow(this);
         rowInfo.setClassName(RegistroDatos.class.getName());
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
    * Añade un registro.
    *
    * @throws Exception Si se produce algún error.
    */
   	public void add(DbConnection dbConn) throws Exception {

   		DynamicTable tableInfo = new DynamicTable();
   		DynamicRows rowsInfo = new DynamicRows();
   		DynamicRow rowInfo = new DynamicRow();
   		RegistroTabla table = new RegistroTabla();

   		logger.debug("Add Registry <-- Registry: " + this.toString());

   		try {
   			tableInfo.setTableObject(table);
   			tableInfo.setClassName(RegistroTabla.class.getName());
   			tableInfo.setTablesMethod("getTableName");
   			tableInfo.setColumnsMethod("getInsertColumnsName");

   			rowInfo.addRow(this);
   			rowInfo.setClassName(RegistroDatos.class.getName());
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
    * Borra el registro cuyo número de registro que se pasa como
    * parámetro.
    * @throws Exception Si se produce algún error.
    */
   public void delete(String entidad) throws Exception {
      RegistroTabla table = new RegistroTabla();
      DbConnection dbConn = new DbConnection();

      logger.debug("Delete Registry <-- Registry: " + registryNumber);
      try {
       	 dbConn.open(DBSessionManager.getSession(entidad));

         DbDeleteFns.delete(dbConn, table.getTableName(), table.getByRegistryNumberQual(registryNumber));

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