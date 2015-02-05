/**
 * @author Marco Fiore
 * 
 * Fecha de Creación: 10 - 01 - 2008
 */
package ieci.tecdoc.sgm.entidades.database;

import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.core.db.DataSourceManager;
import ieci.tecdoc.sgm.entidades.beans.Entidad;
import ieci.tecdoc.sgm.entidades.exception.EntidadException;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * Modela los datos asociados a un entidad de SIGEM.
 *  
 */
public class EntidadDatos extends Entidad implements Serializable {
   private static final Logger logger = Logger.getLogger(EntidadDatos.class);
   
   public EntidadDatos(){
	   super();
   }
   
   public EntidadDatos(Entidad entidad){
	   setIdentificador(entidad.getIdentificador());
	   setNombreCorto(entidad.getNombreCorto());
	   setNombreLargo(entidad.getNombreLargo());
	   setCodigoINE(entidad.getCodigoINE());
   }
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
   throws Exception {

      int index = idx.intValue();

      setIdentificador(statement.getShortText(index ++));
      setNombreCorto(statement.getShortText(index ++));
      setNombreLargo(statement.getShortText(index ++));
      setCodigoINE(statement.getShortText(index++));
      
      return new Integer(index);
   }

   public Integer loadIdValues(DbOutputStatement statement, Integer idx)
   	throws Exception {

      int index = idx.intValue();

      setIdentificador(statement.getShortText(index ++));
      
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
   throws Exception {

     int index = idx.intValue();

     statement.setShortText(index++, getIdentificador());
     statement.setShortText(index ++, getNombreCorto());
     statement.setShortText(index++, getNombreLargo());
     statement.setShortText(index++, getCodigoINE());
     
     return new Integer(index);
   }
   
   
	  public Integer update(DbInputStatement statement, Integer idx)
	  throws Exception {

	    int index = idx.intValue();
	    statement.setShortText(index++, getNombreCorto());
	    statement.setShortText(index++, getNombreLargo());
	    statement.setShortText(index++, getCodigoINE());
	    
	     return new Integer(index);
	  }
   
   
   /**
    * Realiza la consulta por Entidad / contraseña.
    *
    * @param extension Extension.
    * @throws DbExcepcion Si se produce algún error.
    */
   public void load(String identificador) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      EntidadTabla table = new EntidadTabla();
      DbConnection dbConn = new DbConnection();
      
      if(logger.isDebugEnabled()){
    	  logger.debug("Obteniendo datos de entidad...");
      }
      
      try {
         dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(EntidadTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(EntidadDatos.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select(dbConn, table.getById(identificador), tableInfo, rowsInfo)) {
            throw new EntidadException(EntidadException.EC_NOT_FOUND);
         }
         if(logger.isDebugEnabled()){
        	 logger.debug("Datos de entidad obtenidos.");
         }
      } catch (Exception e) {
        throw new EntidadException(EntidadException.EC_GENERIC_ERROR, e.getCause());
      } finally {
        try{
          if (dbConn.existConnection()){
              dbConn.close();        	  
              if(logger.isDebugEnabled()){
            	  logger.debug("Sesión cerrada.");
              }
          }
        }catch(Exception e){ 
        	logger.error("Error cerrando sesión.", e);
        	throw new EntidadException(EntidadException.EC_CLOSE_CONNECTION, e.getCause());
        }
      }
   }
   

   /**
    * Añade un usuario.
    *
    * @throws Exception Si se produce algún error.
    */
   public void add() throws EntidadException {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      EntidadTabla table = new EntidadTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Añadiendo entidad...");
      
      try {
    	  dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(EntidadTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(EntidadDatos.class.getName());
         rowInfo.setValuesMethod("insert");
         rowsInfo.add(rowInfo);
         
         DynamicFns.insert(dbConn, tableInfo, rowsInfo);
         logger.debug("Entidad añadida.");         
      } catch (Exception e) {
    	  logger.error("Error añadiendo entidad.", e);
         throw new EntidadException(EntidadException.EC_INSERT, e.getCause());
      } finally {
         try{
          if (dbConn.existConnection()){
              dbConn.close();
        	  if(logger.isDebugEnabled()){
            	  logger.debug("Sesión cerrada.");
              }
          }
        }catch(Exception e){
        	logger.error("Error cerrando sesión.", e);        	
        	throw new EntidadException(EntidadException.EC_CLOSE_CONNECTION, e.getCause());
        }
      }
   }
   

   /**
    * Borra todos las extensiones asociadas a un mime type.
    *
    * @param mimeType Mime type.
    * @throws Exception Si se produce algún error.
    */
   public void delete() throws EntidadException {
	   EntidadTabla table = new EntidadTabla();
	   DbConnection dbConn = new DbConnection();
	   if(logger.isDebugEnabled()){
		   logger.debug("Eliminando entidad...");		   
	   }
	   try {
		   dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));
		   DbDeleteFns.delete(dbConn, table.getTableName(), table.getById(getIdentificador()));
		   if(logger.isDebugEnabled()){
			   logger.debug("Entidad eliminada.");		   
		   }		   
	   } catch (Exception e) {
		   throw new EntidadException(EntidadException.EC_DELETE);
	   } finally {
         try{
          if (dbConn.existConnection()){
              dbConn.close();
        	  if(logger.isDebugEnabled()){
            	  logger.debug("Sesión cerrada.");
              }              
          }
        }catch(Exception e){
        	logger.error("Error cerrando sesión.", e);        	
        	throw new EntidadException(EntidadException.EC_CLOSE_CONNECTION, e.getCause());
        }
        
      }
   }
   
	  public void update() throws EntidadException {
		     DynamicTable tableInfo = new DynamicTable();
		     DynamicRows rowsInfo = new DynamicRows();
		     DynamicRow rowInfo = new DynamicRow();
		     EntidadTabla table = new EntidadTabla();
		     DbConnection dbConn = new DbConnection();
		     
		     try {
		    	 dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));
		        tableInfo.setTableObject(table);
		        tableInfo.setClassName(EntidadTabla.class.getName());
		        tableInfo.setTablesMethod("getTableName");
		        tableInfo.setColumnsMethod("getUpdateColumnNames");
		        
		        rowInfo.addRow(this);
		        rowInfo.setClassName(EntidadDatos.class.getName());
		        rowInfo.setValuesMethod("update");
		        rowsInfo.add(rowInfo);
		        
		        DynamicFns.update(dbConn, table.getById(getIdentificador()), tableInfo, rowsInfo);
		     } catch (Exception e) {
		        throw new EntidadException(EntidadException.EC_UPDATE);
		     } finally {
		       try{
		         if (dbConn.existConnection()){
			           dbConn.close();		        	 
		        	  if(logger.isDebugEnabled()){
		            	  logger.debug("Sesión cerrada.");
		              }
		         }

		       }catch(Exception ee){
		          logger.error("Error cerrando sesión.", ee);		    	   
		    	  throw new EntidadException(EntidadException.EC_CLOSE_CONNECTION, ee);
		       }
		     }
		  }	  
   
	  
	  
	  public void obtenerIdentificadorEntidad() throws EntidadException{
		  DynamicTable tableInfo = new DynamicTable();
		  DynamicRows rowsInfo = new DynamicRows();
		  DynamicRow rowInfo = new DynamicRow();
		  EntidadTabla table = new EntidadTabla();
		  DbConnection dbConn =  new DbConnection();
		  
	      if(logger.isDebugEnabled()){
	    	  logger.debug("Obteniendo identificador entidad...");
	      }
		  
		  try {
			  dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));
			  tableInfo.setTableObject(table);
			  tableInfo.setClassName(EntidadTabla.class.getName());
			  tableInfo.setTablesMethod("getTableName");
			  tableInfo.setColumnsMethod("getMaxIdEntidad");
			  rowInfo.setClassName(EntidadDatos.class.getName());
		      rowInfo.setValuesMethod("loadIdValues");
		      rowsInfo.add(rowInfo);
		      DynamicFns.select(dbConn, null, tableInfo, rowsInfo);
		      
		      if(logger.isDebugEnabled()){
		    	  logger.debug("Obtencion de identificador entidad.");
		      }		      
		  }catch(Exception e){
			  logger.error("Error obteniendo identificador de entidad.", e);
			  throw new EntidadException(EntidadException.EC_GENERIC_ERROR, e);
		  } finally {
		       try{
		    		   if (dbConn.existConnection()){
		    			   dbConn.close();
		    	           if(logger.isDebugEnabled()){
		    	        	   logger.debug("Sesión cerrada.");
		    	           }
		    		   }
		    			   
			   }catch(Exception ee){
				   throw new EntidadException(EntidadException.EC_CLOSE_CONNECTION, ee);
			   }
		  }
	  }	  
}
