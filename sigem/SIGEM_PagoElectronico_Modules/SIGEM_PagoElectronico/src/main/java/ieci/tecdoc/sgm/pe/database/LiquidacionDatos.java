package ieci.tecdoc.sgm.pe.database;
/*
 * $Id: LiquidacionDatos.java,v 1.1.2.3 2008/10/13 09:08:55 jnogales Exp $
 */
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.pe.CriterioBusquedaLiquidacion;
import ieci.tecdoc.sgm.pe.CriterioBusquedaPago;
import ieci.tecdoc.sgm.pe.LiquidacionImpl;
import ieci.tecdoc.sgm.pe.database.exception.DbCodigosError;
import ieci.tecdoc.sgm.pe.database.exception.DbExcepcion;
import ieci.tecdoc.sgm.pe.exception.GuidIncorrectoCodigosError;
import ieci.tecdoc.sgm.pe.exception.GuidIncorrectoExcepcion;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.log4j.Logger;


public class LiquidacionDatos extends LiquidacionImpl implements Serializable{
	
	/** 
	 * Instancia del logger.
	 */
	private static final Logger logger = Logger.getLogger(LiquidacionDatos.class);
	
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
	    	 referencia = statement.getShortText(index ++);
	    	 idEntidadEmisora = statement.getShortText(index ++);
	    	 idTasa = statement.getShortText(index ++);
	    	 ejercicio = statement.getShortText(index ++);
	    	 vencimiento = statement.getShortText(index ++);
	    	 discriminante = statement.getShortText(index ++);
	    	 remesa = statement.getShortText(index ++);
	    	 nif = statement.getShortText(index ++);
	    	 importe = statement.getShortText(index ++);
	    	 nrc = statement.getShortText(index ++);
	    	 estado = statement.getShortText(index ++);	   
	    	 nombre = statement.getShortText(index ++);
	    	 datosEspecificos = statement.getShortText(index ++);
	    	 inicioPeriodo = statement.getDateTime(index ++);
	    	 finPeriodo = statement.getDateTime(index ++);
	    	 solicitud = statement.getBytes(index ++);
	    	 fechaPago = statement.getDateTime(index ++);
	     }catch(Exception e){
	    	 throw new DbExcepcion(DbCodigosError.EC_GET_ALL_VALUES, e);
	     }

	     if (logger.isDebugEnabled()){
	    	 StringBuffer sbMensaje = new StringBuffer("loadAllValues << referencia: ");
	    	 sbMensaje.append(referencia);
	    	 sbMensaje.append(" idEntidadEmisora: ").append(idEntidadEmisora);
	    	 sbMensaje.append(" idTasa: ").append(idTasa);
	    	 sbMensaje.append(" ejercicio: ").append(ejercicio);
	    	 sbMensaje.append(" vencimiento: ").append(vencimiento);
	    	 sbMensaje.append(" discriminante: ").append(discriminante);
	    	 sbMensaje.append(" remesa: ").append(remesa);
	    	 sbMensaje.append(" importe: ").append(importe);
	    	 sbMensaje.append(" nif: ").append(nif);
	    	 sbMensaje.append(" nrc: ").append(nrc);
	    	 sbMensaje.append(" estado: ").append(estado);
	         logger.debug(sbMensaje.toString());
	     }
	     
	     return new Integer(index);	       
	 }
	
	  /**
	   * Genera la sentencia de inserción de un documento de ingreso de liquidación.
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
	      statement.setShortText(index++, referencia);
	      statement.setShortText(index++, idEntidadEmisora);
	      statement.setShortText(index++, idTasa);	      
	      statement.setShortText(index++, ejercicio);
	      statement.setShortText(index++, vencimiento);
	      statement.setShortText(index++, discriminante);
	      statement.setShortText(index++, remesa);
	      statement.setShortText(index++, nif);
	      statement.setShortText(index++, importe);	      
	      statement.setShortText(index++, nrc);
	      statement.setShortText(index++, estado);
	      statement.setShortText(index++, nombre);
	      statement.setShortText(index++, datosEspecificos);
	      statement.setDateTime(index++, inicioPeriodo);
	      statement.setDateTime(index++, finPeriodo);
	      if(solicitud == null){
	    	  solicitud = new byte[0];
	      }
	      statement.setBytes(index++, solicitud);
	      statement.setDateTime(index++, fechaPago);
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
	   * Genera la sentencia de inserción de un documento de ingreso de liquidación.
	   *
	   * @param statement Sentencia sql precompilada.
	   * @param idx Indice de posición del primer parámetro que se recoge
	   * de la consulta.
	   * @return Indice de posición del último parámtro recogido
	   * @throws Exception Si se produce algún error.
	   */
	  public Integer update(DbInputStatement statement, Integer idx)
	  throws DbExcepcion {

		if(logger.isDebugEnabled()){
			StringBuffer sbMensaje = new StringBuffer("update >> statement: ");
			sbMensaje.append(statement.toString()).append(" idx entrada: ").append(idx);
		    logger.debug(sbMensaje.toString());			 
		}

	    int index = idx.intValue();
	    
	    try{	    	
		      statement.setShortText(index++, idEntidadEmisora);
		      statement.setShortText(index++, idTasa);	      
		      statement.setShortText(index++, ejercicio);
		      statement.setShortText(index++, vencimiento);
		      statement.setShortText(index++, discriminante);
		      statement.setShortText(index++, remesa);
		      statement.setShortText(index++, nif);
		      statement.setShortText(index++, importe);	      
		      statement.setShortText(index++, nrc);
		      statement.setShortText(index++, estado);
		      statement.setShortText(index++, nombre);
		      statement.setShortText(index++, datosEspecificos);
		      statement.setDateTime(index++, inicioPeriodo);
		      statement.setDateTime(index++, finPeriodo);
		      if(solicitud == null){
		    	  solicitud = new byte[0];
		      }		      
		      statement.setBytes(index++, solicitud);
		      statement.setDateTime(index++, fechaPago);
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
	  public void load(String guid, String entidad)
	     throws GuidIncorrectoExcepcion, DbExcepcion {
	     
	    if (guid == null || guid.equals(""))
	      throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
	    DynamicTable tableInfo = new DynamicTable();
	    DynamicRows rowsInfo = new DynamicRows();
	    DynamicRow rowInfo = new DynamicRow();
	    LiquidacionTabla table = new LiquidacionTabla();
	    DbConnection dbConn = new DbConnection();
	     
	    if (logger.isDebugEnabled()){
	    	StringBuffer sbMensaje = new StringBuffer("load >> guid: ").append(guid);
		    logger.debug(sbMensaje);	    	
	    }

	    boolean incorrectGuid = false;
	    
	    try {
	       dbConn.open(DBSessionManager.getSession(entidad));
//	       dbConn.open(Configuracion.getDatabaseConnection());
	       tableInfo.setTableObject(table);
	       tableInfo.setClassName( LiquidacionTabla.class.getName());
	       tableInfo.setTablesMethod("getTableName");
	       tableInfo.setColumnsMethod("getAllColumnNames");
	        
	       rowInfo.addRow(this);
	       rowInfo.setClassName(LiquidacionDatos.class.getName());
	       rowInfo.setValuesMethod("loadAllValues");
	       rowsInfo.add(rowInfo);
	        
	       if (!DynamicFns.select(dbConn, table.getByGuidQual(guid), tableInfo, rowsInfo, false)) {
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
	  public void add(DbConnection dbConn) throws DbExcepcion {
	     DynamicTable tableInfo = new DynamicTable();
	     DynamicRows rowsInfo = new DynamicRows();
	     DynamicRow rowInfo = new DynamicRow();
	     LiquidacionTabla table = new LiquidacionTabla();
	     
	     if(logger.isDebugEnabled()){
	    	 StringBuffer sbMensaje = new StringBuffer("add >> guid: ").append(referencia);
	    	 logger.debug(sbMensaje.toString());	 
	     }
	     if(!dbConn.inTransaction()){
			logger.error("La conexión no tiene una transacción activa.");
			throw new DbExcepcion(DbCodigosError.EC_TRANSACTION_REQUIRED);
	     }
	     
	     try {
	        tableInfo.setTableObject(table);
	        tableInfo.setClassName(LiquidacionTabla.class.getName());
	        tableInfo.setTablesMethod("getTableName");
	        tableInfo.setColumnsMethod("getAllColumnNames");
	        
	        rowInfo.addRow(this);
	        rowInfo.setClassName(LiquidacionDatos.class.getName());
	        rowInfo.setValuesMethod("insert");
	        rowsInfo.add(rowInfo);
	        
	        DynamicFns.insert(dbConn, tableInfo, rowsInfo);
	     } catch (Exception e) {
	        throw new DbExcepcion(DbCodigosError.EC_ADD_VALUE, e);
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
	     LiquidacionTabla table = new LiquidacionTabla();
	     DbConnection dbConn = new DbConnection();
	     
	     if(logger.isDebugEnabled()){
	    	 StringBuffer sbMensaje = new StringBuffer("add >> guid: ").append(referencia);
	    	 logger.debug(sbMensaje.toString());	 
	     }
	     
	     try {
//	        dbConn.open(Configuracion.getDatabaseConnection());
	    	 dbConn.open(DBSessionManager.getSession(entidad));
	        tableInfo.setTableObject(table);
	        tableInfo.setClassName(LiquidacionTabla.class.getName());
	        tableInfo.setTablesMethod("getTableName");
	        tableInfo.setColumnsMethod("getAllColumnNames");
	        
	        rowInfo.addRow(this);
	        rowInfo.setClassName(LiquidacionDatos.class.getName());
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
	   * Añade un documento.
	 * @throws DbExcepcion 
	   *
	   * @throws Exception Si se produce algún error.
	   */
	  public void update(String entidad) throws DbExcepcion {
	     DynamicTable tableInfo = new DynamicTable();
	     DynamicRows rowsInfo = new DynamicRows();
	     DynamicRow rowInfo = new DynamicRow();
	     LiquidacionTabla table = new LiquidacionTabla();
	     DbConnection dbConn = new DbConnection();
	     
	     if(logger.isDebugEnabled()){
	    	 StringBuffer sbMensaje = new StringBuffer("add >> guid: ").append(referencia);
	    	 logger.debug(sbMensaje.toString());	 
	     }
	     
	     try {
//	        dbConn.open(Configuracion.getDatabaseConnection());
	    	dbConn.open(DBSessionManager.getSession(entidad));
	        tableInfo.setTableObject(table);
	        tableInfo.setClassName(LiquidacionTabla.class.getName());
	        tableInfo.setTablesMethod("getTableName");
	        tableInfo.setColumnsMethod("getUpdateColumnNames");
	        
	        rowInfo.addRow(this);
	        rowInfo.setClassName(LiquidacionDatos.class.getName());
	        rowInfo.setValuesMethod("update");
	        rowsInfo.add(rowInfo);
	        
	        DynamicFns.update(dbConn, table.getByGuidQual(referencia), tableInfo, rowsInfo);
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
		 
		 LiquidacionTabla table = new LiquidacionTabla();
	     
	     if(logger.isDebugEnabled()){
	    	 StringBuffer sbMensaje = new StringBuffer("delete >> guid: ").append(referencia);
	    	 logger.debug(sbMensaje.toString());
	     }
	     
	     if ((referencia == null) || ("".equals(referencia))){
	       throw new GuidIncorrectoExcepcion(GuidIncorrectoCodigosError.EC_INCORRECT_GUID);
	     }
	     
	     DbConnection dbConn = new DbConnection();
	     
	     try {
//	        dbConn.open(Configuracion.getDatabaseConnection());
	    	dbConn.open(DBSessionManager.getSession(entidad));
	        DbDeleteFns.delete(dbConn, table.getTableName(), table.getByGuidQual(referencia));
	        
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
	  
	  
	  public ArrayList buscarLiquidaciones(CriterioBusquedaPago criterioPago,String entidad) throws Exception {
		  	 DynamicTable tableInfo = new DynamicTable();
		     DynamicRows rowsInfo = new DynamicRows();
		     DynamicRow rowInfo = new DynamicRow();
		     DbConnection dbConn = new DbConnection();
		     LiquidacionTabla table = new LiquidacionTabla();
		     ArrayList listaADevolver = new ArrayList();
			 
			 logger.debug("buscarLiquidaciones(CriterioBusquedaPago,String)");
			 
		     try {
		        dbConn.open(DBSessionManager.getSession(entidad));
		        
		        tableInfo.setTableObject(table);
		        tableInfo.setClassName(LiquidacionTabla.class.getName());
		        tableInfo.setTablesMethod("getTablaMixta");
		        tableInfo.setColumnsMethod("getAllColumnNamesTablaMixta");
		        rowInfo.setClassName(LiquidacionDatos.class.getName());
			    rowInfo.setValuesMethod("loadAllValues");
			    rowsInfo.add(rowInfo);
		        
		        String clausulaCompleta = null;
		        CriterioBusquedaLiquidacion criterioLiquidacion=new CriterioBusquedaLiquidacion();
		        criterioLiquidacion.setEstado(criterioPago.getEstado());
		        criterioLiquidacion.setNif(criterioPago.getNif());
		        criterioLiquidacion.setNrc(criterioPago.getNRC());
		        criterioLiquidacion.setReferencia(criterioPago.getReferencia());
		        
		        clausulaCompleta = table.getClausulaMixta(criterioPago.getTipo()) +
		        	table.getFindQual(criterioLiquidacion,false) + 
		        	table.getClausulaRangoFechasPago(dbConn,criterioPago.getFechaDesde(),criterioPago.getFechaHasta());
	        	
		        if (!DynamicFns.selectMultiple(dbConn,clausulaCompleta, false, tableInfo, rowsInfo)) {
		        	//throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTES);
		  		}
	        
	        int size = rowInfo.getRowCount();
	   
	        for (int counter = 0; counter < size; counter++) {
	        	LiquidacionDatos liquidacion = (LiquidacionDatos)rowInfo.getRow(counter);
	        	listaADevolver.add(liquidacion);
	         }
	     } catch (Exception e) {
	    	 logger.error("Error durante ejecución de sentencia.",e);
			 throw new DbExcepcion(DbCodigosError.EC_QUERY_EXCEPTION,e);
	     } finally {
	    	 try{
		    	  if (dbConn.existConnection())
		    		dbConn.close();
			 }catch(Exception ee){
				   throw new DbExcepcion(DbCodigosError.EC_CLOSE_CONNECTION, ee);
			 }
	     }
	     
	     return listaADevolver;
	  }
	  
}
