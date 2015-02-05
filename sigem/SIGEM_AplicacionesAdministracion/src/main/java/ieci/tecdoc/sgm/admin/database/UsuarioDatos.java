package ieci.tecdoc.sgm.admin.database;

import ieci.tecdoc.sgm.base.crypto.CryptoUtil;
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
import ieci.tecdoc.sgm.admin.beans.UsuarioImpl;
import ieci.tecdoc.sgm.admin.beans.Usuarios;
import ieci.tecdoc.sgm.admin.interfaces.Usuario;

import java.io.Serializable;

import org.apache.log4j.Logger;

/*
 * $Id: UsuarioDatos.java,v 1.1.2.5 2008/10/07 11:42:32 cnavas Exp $
 */

public class UsuarioDatos extends UsuarioImpl implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8694771597926271004L;
	private static final Logger logger = Logger.getLogger(UsuarioDatos.class);
	protected boolean isDebugeable = true;

	public UsuarioDatos() {
		super();
	}
	public UsuarioDatos(Usuario u) {
		setNombre(u.getNombre());
		setPassword(u.getPassword());
		setUsuario(u.getUsuario());
		setApellidos(u.getApellidos());
		setFechaAlta(u.getFechaAlta());
	}

	/**
	 * obtener los valores 
	 */
	public Integer loadAllValues(DbOutputStatement statement, Integer idx) throws Exception {
		int index = idx.intValue();

		setUsuario(statement.getShortText(index++));
		setPassword(statement.getShortText(index++));
		setNombre(statement.getShortText(index++));
		setApellidos(statement.getShortText(index++));
		setFechaAlta(statement.getDateTime(index++));
		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx) throws Exception {
		int index = idx.intValue();

		statement.setShortText(index++, getUsuario());
		statement.setShortText(index++, CryptoUtil.encryptPass(getPassword()));
		statement.setShortText(index++, getNombre());
		statement.setShortText(index++, getApellidos());
		statement.setDateTime(index++, getFechaAlta());
		return new Integer(index);
	}

	/*
	 * Solo  son actualizables de forma genérica el nombre y apellidos
	 */
	public Integer update(DbInputStatement statement, Integer idx) throws Exception {
		int index = idx.intValue();

		statement.setShortText(index++, getNombre());
		statement.setShortText(index++, getApellidos());

		return new Integer(index);
	}

	/*
	 * Actualiza la password (encriptada)
	 */
	public Integer updatePassword (DbInputStatement statement, Integer idx) throws Exception {
		int index = idx.intValue();
		statement.setShortText(index++, CryptoUtil.encryptPass(getPassword()));
		return new Integer(index);
	}


	/**
	 * Realiza la consulta por usuario.
	 *
	 * @param extension Extension.
	 * @throws DbExcepcion Si se produce algún error.
	 */
	public void load(String usuario) throws AdministracionException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		UsuarioTabla table = new UsuarioTabla();
		DbConnection dbConn = new DbConnection();

		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo datos de Usuario...");
		}

		try {
			dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));

			tableInfo.setTableObject(table);
			tableInfo.setClassName(UsuarioTabla.class.getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(UsuarioDatos.class.getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);

			if (!DynamicFns.select(dbConn, table.getByUserId(usuario), tableInfo, rowsInfo)) {
				throw new AdministracionException(AdministracionException.EC_USER_NOT_FOUND);
			}
			if(logger.isDebugEnabled()){
				logger.debug("Datos de Usuario obtenidos.");
			}

		} catch (AdministracionException e) {
			logger.error("Error recogiendo datos de Usuario.", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error recogiendo datos de Usuario.", e);
			throw new AdministracionException(AdministracionException.EC_USER_NOT_FOUND, e.getCause());
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
	}


	/**
	 * Añade un usuario.
	 *
	 * @throws Exception Si se produce algún error.
	 */
	public void add() throws AdministracionException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		UsuarioTabla table = new UsuarioTabla();
		DbConnection dbConn = new DbConnection();

		logger.debug("Añadiendo Usuario...");

		try {
			dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));

			tableInfo.setTableObject(table);
			tableInfo.setClassName(UsuarioTabla.class.getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(UsuarioDatos.class.getName());
			rowInfo.setValuesMethod("insert");
			rowsInfo.add(rowInfo);

			DynamicFns.insert(dbConn, tableInfo, rowsInfo);
			logger.debug("Usuario añadido.");         
		} catch (Exception e) {
			logger.error("Error añadiendo Usuario.", e);
			throw new AdministracionException(AdministracionException.EC_USER_INSERT, e.getCause());
		} finally {
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
	 * Borra todos las extensiones asociadas a un mime type.
	 *
	 * @param mimeType Mime type.
	 * @throws Exception Si se produce algún error.
	 */
	public void delete() throws AdministracionException {
		UsuarioTabla table = new UsuarioTabla();
		DbConnection dbConn = new DbConnection();
		if(logger.isDebugEnabled()){
			logger.debug("Eliminando Usuario...");		   
		}
		try {
			dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));

			DbDeleteFns.delete(dbConn, table.getTableName(), table.getByUserId(getUsuario()));
			if(logger.isDebugEnabled()){
				logger.debug("Usuario eliminado.");		   
			}		   
		} catch (Exception e) {
			throw new AdministracionException(AdministracionException.EC_USER_DELETE);
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
	}

	/**
	 * Actualiza un usuario
	 * @throws AdministracionException Si se produce algún error
	 */
	public void update() throws AdministracionException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		UsuarioTabla table = new UsuarioTabla();
		DbConnection dbConn = new DbConnection();

		try {
			dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));
			tableInfo.setTableObject(table);
			tableInfo.setClassName(UsuarioTabla.class.getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getUpdateColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(UsuarioDatos.class.getName());
			rowInfo.setValuesMethod("update");
			rowsInfo.add(rowInfo);

			DynamicFns.update(dbConn, table.getByUserId(getUsuario()), tableInfo, rowsInfo);
		} catch (Exception e) {
			throw new AdministracionException(AdministracionException.EC_USER_UPDATE);
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
				throw new AdministracionException(AdministracionException.EC_CLOSE_CONNECTION, ee);
			}
		}
	}

	/**
	 * Actualiza un usuario
	 * @throws AdministracionException Si se produce algún error
	 */
	public void updatePassword(String newPassword) throws AdministracionException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		UsuarioTabla table = new UsuarioTabla();
		DbConnection dbConn = new DbConnection();

		try {
			if (authUser(getUsuario(), getPassword())){
				setPassword(newPassword);
				
				dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));
				tableInfo.setTableObject(table);
				tableInfo.setClassName(UsuarioTabla.class.getName());
				tableInfo.setTablesMethod("getTableName");
				tableInfo.setColumnsMethod("getPasswordColumnName");
				
				rowInfo.addRow(this);
				rowInfo.setClassName(UsuarioDatos.class.getName());
				rowInfo.setValuesMethod("updatePassword");
				rowsInfo.add(rowInfo);

				DynamicFns.update(dbConn, table.getByUserId(getUsuario()), tableInfo, rowsInfo);
			}else{
				throw new AdministracionException(AdministracionException.EC_USER_AUTH);
			}
		} catch (AdministracionException e) {
			logger.error("Error cambiando la clave del usuario.", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error cambiando la clave del usuario.", e);
				throw new AdministracionException(AdministracionException.EC_USER_UPDATE);
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
					throw new AdministracionException(AdministracionException.EC_CLOSE_CONNECTION, ee);
				}
			}
		}

		/**
		 * Devuelve el listado de usuarios de administración para una entidad
		 * @param entityId Identificador de unidad
		 * @return Listado de usuarios
		 * @throws AdministracionException Si se produce algún error
		 */
		public Usuarios loadEntityUsers(String entityId) throws AdministracionException {
			DynamicTable tableInfo = new DynamicTable();
			DynamicRows rowsInfo = new DynamicRows();
			DynamicRow rowInfo = new DynamicRow();
			UsuarioTabla table = new UsuarioTabla();
			DbConnection dbConn = new DbConnection();
			Usuarios usuarios = new Usuarios();
			try {
				dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));
				tableInfo.setTableObject(table);
				tableInfo.setClassName(UsuarioTabla.class.getName());
				tableInfo.setTablesMethod("getTableNameExt");
				tableInfo.setColumnsMethod("getEntityUsersColumnNames");

				//rowInfo.addRow(this);
				rowInfo.setClassName(UsuarioDatos.class.getName());
				rowInfo.setValuesMethod("loadAllValues");
				rowsInfo.add(rowInfo);

				if (!DynamicFns.selectMultiple(dbConn, table.getByEntityId(entityId), true, tableInfo, rowsInfo)){
					throw new AdministracionException(AdministracionException.EC_USER_NOT_FOUND);
				}

				for (int i = 0; i < rowInfo.getRowCount(); i++) {
					UsuarioImpl usuario = new UsuarioImpl();
					usuario = (UsuarioDatos) rowInfo.getRow(i);
					usuarios.add(usuario);
				}

			} catch (AdministracionException e) {
				logger.error("Error cargando los usuarios de la entidad.", e);
				throw e;
			} catch (Exception e) {
				logger.error("Error cargando los usuarios de la entidad.", e);
				throw new AdministracionException(AdministracionException.EC_USER_NOT_FOUND);
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
					throw new AdministracionException(AdministracionException.EC_CLOSE_CONNECTION, ee);
				}
			}
			return usuarios;
		}

		/**
		 * Autentifica un usuario
		 * @param user Identificador de usuario
		 * @param password Contraseña encriptada
		 * @return Verdadero si es correcta la autentificación, falso en caso contrario
		 * @throws AdministracionException Si se produce algún error
		 */
		public boolean authUser(String user, String password) throws AdministracionException {
			DynamicTable tableInfo = new DynamicTable();
			DynamicRows rowsInfo = new DynamicRows();
			DynamicRow rowInfo = new DynamicRow();
			UsuarioTabla table = new UsuarioTabla();
			DbConnection dbConn = new DbConnection();
			boolean auth = false;
			try {
				dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));
				tableInfo.setTableObject(table);
				tableInfo.setClassName(UsuarioTabla.class.getName());
				tableInfo.setTablesMethod("getTableName");
				tableInfo.setColumnsMethod("getAllColumnNames");

				rowInfo.addRow(this);
				rowInfo.setClassName(UsuarioDatos.class.getName());
				rowInfo.setValuesMethod("loadAllValues");
				rowsInfo.add(rowInfo);

//				String encryptPassword = CryptoUtil.encryptPass(password);
//				if (DynamicFns.select(dbConn, table.getAuth(user, encryptPassword), tableInfo, rowsInfo)){
//					auth = true;
//				}
				
				if (DynamicFns.select(dbConn, table.getByUserId(user), tableInfo, rowsInfo)) {
					String encryptPassword = CryptoUtil.encryptPass(password);
					if (encryptPassword.equals(getPassword())) {
						auth = true;
					}
				}

			} catch (Exception e) {
				throw new AdministracionException(AdministracionException.EC_USER_NOT_FOUND);
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
					throw new AdministracionException(AdministracionException.EC_CLOSE_CONNECTION, ee);
				}
			}
			return auth;
		}
	}
