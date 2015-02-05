package ieci.tecdoc.sgm.pe.database;
/*
 * $Id: EntidadBancariaDatos.java,v 1.1.2.2 2008/03/14 11:22:22 jnogales Exp $
 */
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.pe.EntidadBancariaImpl;
import ieci.tecdoc.sgm.pe.database.exception.DbCodigosError;
import ieci.tecdoc.sgm.pe.database.exception.DbExcepcion;
import ieci.tecdoc.sgm.pe.exception.GuidIncorrectoCodigosError;
import ieci.tecdoc.sgm.pe.exception.GuidIncorrectoExcepcion;

import java.io.Serializable;

import org.apache.log4j.Logger;


public class EntidadBancariaDatos extends EntidadBancariaImpl implements Serializable{
	
	/** 
	 * Instancia del logger.
	 */
	private static final Logger logger = Logger.getLogger(EntidadBancariaDatos.class);
	
	/**
	 * Recupera todos los valores de los parámetros de la sentencia
	 * de consulta que se pasa como parámetro.
	 * @param statement Sentencia sql precompilada.
	 * @param idx Indice de posición del primer parámetro que se recoge
	 * de la consulta.
	 * @return Indice de posición del último parámetro recogido
	 * @throws DbExcepcion Si se produce algún error.
	 */
	public Integer loadAllValues(DbOutputStatement statement, Integer idx) throws DbExcepcion{
		 if(logger.isDebugEnabled()){
			 StringBuffer sbMensaje = new StringBuffer("loadAllValues >> statement: ");
			 sbMensaje.append(statement.toString()).append(" idx entrada: ").append(idx);
	         logger.debug(sbMensaje.toString());			 
		 }
		 int index = idx.intValue();
	       
	     try{
	    	 idEntidad = statement.getShortText(index ++);
	    	 nombre = statement.getShortText(index ++);
	     }catch(Exception e){
	    	 throw new DbExcepcion(DbCodigosError.EC_GET_ALL_VALUES, e);
	     }

	     if (logger.isDebugEnabled()){
	    	 StringBuffer sbMensaje = new StringBuffer("loadAllValues << codigo: ");
	    	 sbMensaje.append(idEntidad);
	    	 sbMensaje.append(" nombre: ").append(nombre);
	         logger.debug(sbMensaje.toString());
	     }
	     
	     return new Integer(index);	       
	 }
	
	  /**
	   * Genera la sentencia de inserción de una EntidadBancaria.
	   *
	   * @param statement Sentencia sql precompilada.
	   * @param idx Indice de posición del primer parámetro que se recoge
	   * de la consulta.
	   * @return Indice de posición del último parámtro recogido
	   * @throws Exception Si se produce algún error.
	   */
	  public Integer insert(DbInputStatement statement, Integer idx)
	  throws DbExcepcion {

		if(logger.isDebugEnabled()){
			StringBuffer sbMensaje = new StringBuffer("insert >> statement: ");
			sbMensaje.append(statement.toString()).append(" idx entrada: ").append(idx);
		    logger.debug(sbMensaje.toString());			 
		}

	    int index = idx.intValue();
	    
	    try{	    	
	      statement.setShortText(index++, idEntidad);
	      statement.setShortText(index++, nombre);	      
	    }catch(Exception e){
	      throw new DbExcepcion(DbCodigosError.EC_INSERT_ALL_VALUES,e);
	    }

	    if (logger.isDebugEnabled()){
	    	StringBuffer sbMensaje = new StringBuffer("insert << statement: ");
	    	sbMensaje.append(statement.toString());
	    	logger.debug(sbMensaje.toString());
	    }
	      
	    return new Integer(index);
	  }

	  /**
	   * Realiza la consulta por guid.
	   *
	   * @param guid GUID del documento.
	 * @throws DbExcepcion 
	   * @throws DbExcepcion Si se produce algún error.
	   */
	  public void load(String pcId, String entidad)
	     throws GuidIncorrectoExcepcion, DbExcepcion {
	     
		if (	((pcId == null) || ("".equals(pcId)))
		){
	      throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
		}
	    DynamicTable tableInfo = new DynamicTable();
	    DynamicRows rowsInfo = new DynamicRows();
	    DynamicRow rowInfo = new DynamicRow();
	    EntidadBancariaTabla table = new EntidadBancariaTabla();
	    DbConnection dbConn = new DbConnection();
	     
	    if (logger.isDebugEnabled()){
	    	 StringBuffer sbMensaje = new StringBuffer("delete >> pk: ").append(idEntidad).append("-").append(nombre);
		    logger.debug(sbMensaje);	    	
	    }

	    boolean incorrectGuid = false;
	    
	    try {
//	       dbConn.open(Configuracion.getDatabaseConnection());
	       dbConn.open(DBSessionManager.getSession(entidad));	       
	       tableInfo.setTableObject(table);
	       tableInfo.setClassName( EntidadBancariaTabla.class.getName());
	       tableInfo.setTablesMethod("getTableName");
	       tableInfo.setColumnsMethod("getAllColumnNames");
	        
	       rowInfo.addRow(this);
	       rowInfo.setClassName(EntidadBancariaDatos.class.getName());
	       rowInfo.setValuesMethod("loadAllValues");
	       rowsInfo.add(rowInfo);
	        
	       if (!DynamicFns.select(dbConn, pcId, tableInfo, rowsInfo)) {
	          incorrectGuid = true;
	       }
	    } catch (Exception e) {
	    	throw new DbExcepcion(DbCodigosError.EC_RETRIEVE_DOCUMENT, e);
	    } finally {
	       try{
	         if (dbConn.existConnection())
	           dbConn.close();
	       }catch(Exception ee){}
	       
	       if (incorrectGuid)
	         throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
	    }
	  }
	  
	  /**
	   * Añade un documento.
	 * @throws DbExcepcion 
	   *
	   * @throws Exception Si se produce algún error.
	   */
	  public void add(String entidad) throws DbExcepcion {
	     DynamicTable tableInfo = new DynamicTable();
	     DynamicRows rowsInfo = new DynamicRows();
	     DynamicRow rowInfo = new DynamicRow();
	     EntidadBancariaTabla table = new EntidadBancariaTabla();
	     DbConnection dbConn = new DbConnection();
	     
	     if(logger.isDebugEnabled()){
	    	 StringBuffer sbMensaje = new StringBuffer("delete >> pk: ").append(idEntidad).append("-").append(nombre);
	    	 logger.debug(sbMensaje.toString());	 
	     }
	     
	     try {
//	        dbConn.open(Configuracion.getDatabaseConnection());
	        dbConn.open(DBSessionManager.getSession(entidad));	        
	        
	        tableInfo.setTableObject(table);
	        tableInfo.setClassName(EntidadBancariaTabla.class.getName());
	        tableInfo.setTablesMethod("getTableName");
	        tableInfo.setColumnsMethod("getAllColumnNames");
	        
	        rowInfo.addRow(this);
	        rowInfo.setClassName(EntidadBancariaDatos.class.getName());
	        rowInfo.setValuesMethod("insert");
	        rowsInfo.add(rowInfo);
	        
	        DynamicFns.insert(dbConn, tableInfo, rowsInfo);
	     } catch (Exception e) {
	        throw new DbExcepcion(DbCodigosError.EC_ADD_VALUE, e);
	     } finally {
	       try{
	         if (dbConn.existConnection())
	           dbConn.close();
	       }catch(Exception ee){
	    	   throw new DbExcepcion(DbCodigosError.EC_CLOSE_CONNECTION, ee);
	       }
	     }
	  }	  
	  
	  /**
	   * Borra los documentos asociadas a un guid.
	 * @throws DbExcepcion 
	   * @throws Exception Si se produce algún error.
	   */
	  public void delete(String entidad) 
	    throws GuidIncorrectoExcepcion, DbExcepcion {
		 
		 EntidadBancariaTabla table = new EntidadBancariaTabla();
	     
	     if(logger.isDebugEnabled()){
	    	 StringBuffer sbMensaje = new StringBuffer("delete >> pk: ").append(idEntidad).append("-").append(nombre);
	    	 logger.debug(sbMensaje.toString());
	     }
	     
	     if (	((idEntidad == null) || ("".equals(idEntidad)))
	     ){
	       throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
	     }
	     
	     DbConnection dbConn = new DbConnection();
	     
	     try {
//	        dbConn.open(Configuracion.getDatabaseConnection());
	    	dbConn.open(DBSessionManager.getSession(entidad));	        
	        
	        DbDeleteFns.delete(dbConn, table.getTableName(), idEntidad);
	        
	     } catch (Exception e) {
	        throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID,e);
	     } finally {
	       try{
	         if (dbConn.existConnection())
	           dbConn.close();
	       }catch(Exception ee){
	         throw new DbExcepcion(DbCodigosError.EC_CLOSE_CONNECTION, ee);
	       }
	     }
	  }	  
}
