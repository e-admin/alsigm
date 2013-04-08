package ieci.tecdoc.sgm.admin.database;

import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.core.db.DataSourceManager;
import ieci.tecdoc.sgm.admin.AdministracionException;
import ieci.tecdoc.sgm.admin.beans.PerfilImpl;
import ieci.tecdoc.sgm.admin.beans.Perfiles;

import java.io.Serializable;
import org.apache.log4j.Logger;

/*
 * $Id: PerfilDatos.java,v 1.1.2.5 2008/05/08 07:02:09 jnogales Exp $
 */

public class PerfilDatos extends PerfilImpl implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1751744316037097977L;
	private static final Logger logger = Logger.getLogger(PerfilDatos.class);
	protected boolean isDebugeable = true;
	
	public PerfilDatos(){
	}

	public PerfilDatos(PerfilImpl p) {
		setIdAplicacion(p.getIdAplicacion());
		setIdEntidad(p.getIdEntidad());
		setIdUsuario(p.getIdUsuario());
	}
	
	public Integer loadAllValues(DbOutputStatement statement, Integer idx) throws Exception {
		int index = idx.intValue();

		setIdEntidad(statement.getShortText(index++));
		setIdAplicacion(statement.getShortText(index++));
		setIdUsuario(statement.getShortText(index++));
		      
		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx) throws Exception {
		int index = idx.intValue();

		// logger.debug("INSERT");

		statement.setShortText(index++, getIdEntidad());
		statement.setShortText(index++, getIdAplicacion());
		statement.setShortText(index++, getIdUsuario());

		return new Integer(index);
	}
	
	public Integer update(DbInputStatement statement, Integer idx) 
	throws Exception {

		int index = idx.intValue();

		statement.setShortText(index++, getIdAplicacion());
		statement.setShortText(index++, getIdUsuario());
		return new Integer(index);
	}
	
	/**
	 * Realiza la consulta de perfil de usuario por entidad.
	 *
	 * @param idUsuario identificador del usuario.
	 * @throws AdministracionException Si se produce algún error.
	 */
	public Perfiles load(String idEntidad, String idUsuario) throws AdministracionException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		PerfilTabla table = new PerfilTabla();
		DbConnection dbConn = new DbConnection();
		Perfiles perfiles = new Perfiles();
		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo datos de perfil de usuario por entidad...");
		}

		try {
	        dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));

			tableInfo.setTableObject(table);
			tableInfo.setClassName(PerfilTabla.class.getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			//rowInfo.addRow(this);
			rowInfo.setClassName(PerfilDatos.class.getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);

			if (!DynamicFns.selectMultiple(dbConn, table.getByUser4Entity(idEntidad, idUsuario), false, tableInfo, rowsInfo)) {
				throw new AdministracionException(AdministracionException.EC_ROLE_NOT_FOUND);
			}
		    for (int i = 0; i < rowInfo.getRowCount(); i++) {
		    	PerfilImpl perfil = new PerfilImpl();
	    		perfil = (PerfilDatos) rowInfo.getRow(i);
	    		if (perfil != null && perfil.getIdEntidad() != null)
		    		perfiles.add(perfil);
		    }
			if(logger.isDebugEnabled()){
				logger.debug("Datos de Perfil de Usuario obtenidos.");
			}

		} catch (Exception e) {
			logger.error("Error recogiendo datos de Perfil de Usuario.", e);
			throw new AdministracionException(AdministracionException.EC_ROLE_NOT_FOUND, e.getCause());
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
				throw new AdministracionException(AdministracionException.EC_CLOSE_CONNECTION, e.getCause());
			}
		}
		return perfiles;
	}

	/**
	 * Realiza la consulta de perfil de usuario para todas las entidades.
	 *
	 * @param idUsuario identificador del usuario.
	 * @throws AdministracionException Si se produce algún error.
	 */
	public Perfiles load(String idUsuario) throws AdministracionException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		PerfilTabla table = new PerfilTabla();
		DbConnection dbConn = new DbConnection();
		Perfiles perfiles = new Perfiles();
		
		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo datos de perfil de usuario...");
		}

		try {
	        dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));

			tableInfo.setTableObject(table);
			tableInfo.setClassName(PerfilTabla.class.getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			//rowInfo.addRow(this);
			rowInfo.setClassName(PerfilDatos.class.getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);

			if (!DynamicFns.selectMultiple(dbConn, table.getByUser(idUsuario),false, tableInfo, rowsInfo)) {
				throw new AdministracionException(AdministracionException.EC_ROLE_NOT_FOUND);
			}

		    for (int i = 0; i < rowInfo.getRowCount(); i++) {
		    	PerfilImpl perfil = new PerfilImpl();
		    	perfil = (PerfilDatos) rowInfo.getRow(i);
		    	if (perfil != null && perfil.getIdEntidad() != null)
		    		perfiles.add(perfil);
		    }

			if(logger.isDebugEnabled()){
				logger.debug("Datos de Perfil de Usuario obtenidos.");
			}

		} catch (Exception e) {
			logger.error("Error recogiendo datos de Perfil de Usuario.", e);
			throw new AdministracionException(AdministracionException.EC_ROLE_NOT_FOUND, e.getCause());
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
				throw new AdministracionException(AdministracionException.EC_CLOSE_CONNECTION, e.getCause());
			}
		}
		return perfiles;
	}

	public void insert()throws AdministracionException{
		DbConnection dbConn = new DbConnection();

		logger.debug("Añadiendo Perfil...");

		try {
	        dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));
	        add(dbConn);
		}catch(AdministracionException ex){
			throw ex;
		}catch(Exception e){
			logger.error("Error añadiendo Perfil.", e);
			throw new AdministracionException(AdministracionException.EC_GENERIC_ERROR, e.getCause());
		}finally {
			try{
				if (dbConn.existConnection()){
					dbConn.close();
					if(logger.isDebugEnabled()){
						logger.debug("Sesión cerrada.");
					}
				}
			} catch(Exception e){
				logger.error("Error cerrando sesión.", e);        	
				throw new AdministracionException(AdministracionException.EC_CLOSE_CONNECTION, e.getCause());
			}
		}

	}
	/**
	 * Añade una aplicacion/entidad a un usuario.
	 *
	 * @throws Exception Si se produce algún error.
	 */
	private void add(DbConnection dbConn) throws AdministracionException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		PerfilTabla table = new PerfilTabla ();

		logger.debug("Añadiendo Perfil...");

		try {

			tableInfo.setTableObject(table);
			tableInfo.setClassName(PerfilTabla.class.getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(PerfilDatos.class.getName());
			rowInfo.setValuesMethod("insert");
			rowsInfo.add(rowInfo);

			DynamicFns.insert(dbConn, tableInfo, rowsInfo);
			logger.debug("Perfil añadido.");         
		} catch (Exception e) {
			logger.error("Error añadiendo Perfil.", e);
			throw new AdministracionException(AdministracionException.EC_ROLE_INSERT, e.getCause());
		} 
	}


	/**
	 * Borra una los permisos de acceso a una aplicación para 
	 * un usuario por entidad.
	 *
	 * @param mimeType Mime type.
	 * @throws Exception Si se produce algún error.
	 */
	public void delete() throws AdministracionException {
		PerfilTabla table = new PerfilTabla();
		DbConnection dbConn = new DbConnection();
		if(logger.isDebugEnabled()){
			logger.debug("Eliminando Perfil...");		   
		}
		try {
	        dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));
			DbDeleteFns.delete(dbConn, table.getTableName(), table.getByRole(getIdEntidad(),getIdUsuario(), getIdAplicacion()));
			if(logger.isDebugEnabled()){
				logger.debug("Perfil eliminado.");		   
			}		   
		} catch (Exception e) {
			throw new AdministracionException(AdministracionException.EC_ROLE_DELETE);
		} finally {
			try{
				if (dbConn.existConnection()){
					dbConn.close();
					if(logger.isDebugEnabled()){
						logger.debug("Conexión cerrada.");
					}              
				}
			}catch(Exception e){
				logger.error("Error cerrando conexión.", e);        	
				throw new AdministracionException(AdministracionException.EC_CLOSE_CONNECTION, e.getCause());
			}
		}
	}
	
	/**
	 * Borra los permisos de un usuario para una entidad
	 * @param idUsuario Identificador de usuario
	 * @param idEntidad Identificador de entidad
	 * @throws AdministracionException Si se produce algún error
	 */
	public void delete(String idUsuario, String idEntidad) throws AdministracionException {
		PerfilTabla table = new PerfilTabla();
		DbConnection dbConn = new DbConnection();
		if(logger.isDebugEnabled()){
			logger.debug("Eliminando Perfiles por usuario y entidad...");		   
		}
		try {
	        dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));
			DbDeleteFns.delete(dbConn, table.getTableName(), table.getByUser4Entity(idEntidad ,idUsuario));
			if(logger.isDebugEnabled()){
				logger.debug("Perfil eliminado.");		   
			}		   
		} catch (Exception e) {
			throw new AdministracionException(AdministracionException.EC_ROLE_DELETE);
		} finally {
			try{
				if (dbConn.existConnection()){
					dbConn.close();
					if(logger.isDebugEnabled()){
						logger.debug("Conexión cerrada.");
					}              
				}
			}catch(Exception e){
				logger.error("Error cerrando conexión.", e);        	
				throw new AdministracionException(AdministracionException.EC_CLOSE_CONNECTION, e.getCause());
			}
		}
	}
	
	/**
	 * Borra todos los permisos de administración para un usuario
	 * @param idUsuario Identificador de usuario
	 * @throws AdministracionException Si se produce algún error
	 */
	public void delete(String idUsuario) throws AdministracionException {
		PerfilTabla table = new PerfilTabla();
		DbConnection dbConn = new DbConnection();
		if(logger.isDebugEnabled()){
			logger.debug("Eliminando Perfiles por usuario y entidad...");		   
		}
		try {
	        dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));
			DbDeleteFns.delete(dbConn, table.getTableName(), table.getByUser(idUsuario));
			if(logger.isDebugEnabled()){
				logger.debug("Perfil eliminado.");		   
			}		   
		} catch (Exception e) {
			throw new AdministracionException(AdministracionException.EC_ROLE_DELETE);
		} finally {
			try{
				if (dbConn.existConnection()){
					dbConn.close();
					if(logger.isDebugEnabled()){
						logger.debug("Conexión cerrada.");
					}              
				}
			}catch(Exception e){
				logger.error("Error cerrando conexión.", e);        	
				throw new AdministracionException(AdministracionException.EC_CLOSE_CONNECTION, e.getCause());
			}
		}
	}

	
	public void update(String[] idAplcaciones, String idUsuario, String idEntidad) throws AdministracionException {
		PerfilTabla table = new PerfilTabla();
		DbConnection dbConn = new DbConnection();

		try {
	        dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));
			dbConn.beginTransaction();
			setIdUsuario(idUsuario);
			setIdEntidad(idEntidad);
			DbDeleteFns.delete(dbConn, table.getTableName(), table.getByUser4Entity(
					 getIdEntidad(),getIdUsuario()));
			
			for (int i=0; i < idAplcaciones.length; i++){
				setIdAplicacion(idAplcaciones[i]);
				add(dbConn);
			}
			dbConn.endTransaction(true);
		} catch (Exception e) {
			throw new AdministracionException(AdministracionException.EC_ROLE_UPDATE);
		} finally {
			try{
				if (dbConn.inTransaction()){
					dbConn.endTransaction(false);
				}
				if (dbConn.existConnection()){
					dbConn.close();		        	 
					if(logger.isDebugEnabled()){
						logger.debug("Sesión cerrada.");
					}
				}

			}catch(Exception ee){
				logger.error("Error cerrando sesión.", ee);		    	   
				throw new AdministracionException(AdministracionException.EC_CLOSE_CONNECTION, ee);
			}
		}
	}	  
	

	
}
