package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.UserErrorCodes;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
import ieci.tecdoc.idoc.admin.database.LdapUsersTable;
import ieci.tecdoc.idoc.admin.database.UserProfilesTable;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;

import org.apache.log4j.Logger;

/**
 * Representa los perfiles de un elemento de invesDoc.
 */

public class GenericProfilesImpl {

	protected GenericProfilesImpl() {
		_profiles = new UserProfilesImpl();
	}

	public UserProfiles getProfiles() {
		return _profiles;
	}

	/**
	 * Inserta la parte de perfiles de las tablas de elemento.
	 * 
	 * @throws Exception
	 *             Si se produce algún error en la inserción del elemento.
	 */

	protected void insertProfiles(String entidad) throws Exception {
		int counter;
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo;
		DynamicRow rowInfo;
		LdapUsersTable table = new LdapUsersTable();

		if (_logger.isDebugEnabled())
			_logger.debug("insertProfiles");

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			tableInfo.setTableObject(table);
			tableInfo.setClassName(LdapUsersTable.class.getName());
			tableInfo.setTablesMethod("getProfilesTableName");
			tableInfo.setColumnsMethod("getInsertProfilesColumnNames");

			for (counter = 0; counter < _profiles.count(); counter++) {
				rowInfo = new DynamicRow();
				rowsInfo = new DynamicRows();

				rowInfo.addRow(_profiles.get(counter));
				rowInfo.setClassName(UserProfileImpl.class.getName());
				rowInfo.setValuesMethod("insertValues");
				rowsInfo.add(rowInfo);

				DynamicFns.insert(dbConn, tableInfo, rowsInfo);
			}

		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}

	}

	/**
	 * Actualiza un perfil.
	 * 
	 * @param id
	 *            Identificador del elemento.
	 * @param productId
	 *            Identificador del producto.
	 * @throws Exception
	 *             Si se produce algún error en la actualización.
	 */

	private void updateProfile(int id, int productId, String entidad)
			throws Exception {
		LdapUsersTable table = new LdapUsersTable();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo;
		DynamicRow rowInfo;

		if (_logger.isDebugEnabled())
			_logger.debug("updateProfile");

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			tableInfo.setTableObject(table);
			tableInfo.setClassName(LdapUsersTable.class.getName());
			tableInfo.setTablesMethod("getProfilesTableName");
			tableInfo.setColumnsMethod("getUpdateProfilesColumnNames");

			rowInfo = new DynamicRow();
			rowsInfo = new DynamicRows();

			rowInfo.addRow(_profiles.getProductProfile(productId));
			rowInfo.setClassName(UserProfileImpl.class.getName());
			rowInfo.setValuesMethod("updateValues");
			rowsInfo.add(rowInfo);

			DynamicFns.update(dbConn, table
					.getUpdateProfilesQual(id, productId), tableInfo, rowsInfo);
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}
	}

	/**
	 * Actualiza la parte de perfiles de las tablas de elemento.
	 * 
	 * @param id
	 *            Identificador del elemento.
	 * @throws Exception
	 *             Si se produce algún error en la actualización.
	 */

	protected void updateProfiles(int id, String entidad) throws Exception {
		if (_logger.isDebugEnabled())
			_logger.debug("updateProfiles");

		for (int i = 0; i < _profiles.count(); i++) {
			UserProfile userProfile = _profiles.get(i);
			if (userProfile.checkProfilesExists(entidad)) {
				userProfile.update(entidad);
			} else {
				userProfile.insert(entidad);
			}
		}

		/*
		 * updateProfile(id, UserDefs.PRODUCT_IDOC, entidad); updateProfile(id,
		 * UserDefs.PRODUCT_SYSTEM, entidad); updateProfile(id,
		 * UserDefs.PRODUCT_USER, entidad); updateProfile(id,
		 * UserDefs.PRODUCT_VOLUME, entidad); updateProfile(id,
		 * UserDefs.PRODUCT_IFLOW, entidad); updateProfile(id,
		 * UserDefs.PRODUCT_ISICRES, entidad);
		 */

		/*
		 * updateProfile(id, 2, entidad); updateProfile(id, 3, entidad);
		 * updateProfile(id, 5, entidad); updateProfile(id, 7, entidad);
		 * updateProfile(id, 8, entidad); updateProfile(id, 4, entidad);
		 */

	}

	/**
	 * Lee los perfiles del elemento invesDoc.
	 * 
	 * @param id
	 *            Identificador del elemento.
	 * @return Si el elemento es administrador o no.
	 * @throws Exception
	 *             Si se produce algún error en la lectura de los perfiles del
	 *             elemento.
	 */

	protected boolean loadProfiles(int id, String entidad) throws Exception {
		int counter;
		UserProfile profile;
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		LdapUsersTable table = new LdapUsersTable();
		// int prof;
		boolean wasAdmin = false;

		if (_logger.isDebugEnabled())
			_logger.debug("loadProfiles");

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			tableInfo.setTableObject(table);
			tableInfo.setClassName(LdapUsersTable.class.getName());
			tableInfo.setTablesMethod("getProfilesTableName");
			tableInfo.setColumnsMethod("getLoadProfilesColumnNames");

			rowInfo.setClassName(UserProfileImpl.class.getName());
			rowInfo.setValuesMethod("loadValues");
			rowsInfo.add(rowInfo);

			if (!DynamicFns.selectMultiple(dbConn, table
					.getLoadProfilesQual(id), false, tableInfo, rowsInfo)) {
				AdminException
						.throwException(UserErrorCodes.EC_USER_NOT_PROFILES);
			}

			// Solo hay filas de una tabla
			for (counter = 0; counter < rowInfo.getRowCount(); counter++) {
				profile = (UserProfileImpl) rowInfo.getRow(counter);
				((UserProfileImpl) profile).setId(id);
				_profiles.add(profile);
			}

			wasAdmin = isIdocAdmin();
			// profile = _profiles.getProductProfile(UserDefs.PRODUCT_IDOC);
			// prof = profile.getProfile();
			// if ((prof == UserDefs.PROFILE_MANAGER) ||
			// (prof == UserDefs.PROFILE_SUPERUSER))
			// {
			// wasAdmin = true;
			// }
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}

		return wasAdmin;
	}

	/**
	 * Establece los perfiles del elemento invesDoc por defecto.
	 * 
	 * @param id
	 *            Identificador del elemento.
	 * @throws Exception
	 *             Si se produce algún error en el establecimiento de los
	 *             perfiles del elemento.
	 */

	protected void setDefaultProfiles(int id) throws Exception {
		UserProfileImpl profile;

		profile = new UserProfileImpl(
				id,
				Integer
						.parseInt(ConstantesGestionUsuariosAdministracion.APLICACION_ADMINISTRACION),
				UserDefs.PROFILE_NONE);
		_profiles.add(profile);

		profile = new UserProfileImpl(
				id,
				Integer
						.parseInt(ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA),
				UserDefs.PROFILE_NONE);
		_profiles.add(profile);

		profile = new UserProfileImpl(
				id,
				Integer
						.parseInt(ConstantesGestionUsuariosAdministracion.APLICACION_CATALOGO_PROCEDIMIENTOS),
				UserDefs.PROFILE_STANDARD);
		_profiles.add(profile);

		profile = new UserProfileImpl(
				id,
				Integer
						.parseInt(ConstantesGestionUsuariosAdministracion.APLICACION_ARCHIVO),
				UserDefs.PROFILE_NONE);
		_profiles.add(profile);

		profile = new UserProfileImpl(
				id,
				Integer
						.parseInt(ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO),
				UserDefs.PROFILE_NONE);
		_profiles.add(profile);

		profile = new UserProfileImpl(
				id,
				Integer
						.parseInt(ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES),
				UserDefs.PROFILE_NONE);
		_profiles.add(profile);

		profile = new UserProfileImpl(
				id,
				Integer
						.parseInt(ConstantesGestionUsuariosAdministracion.APLICACION_CATALOGO_TRAMITES),
				UserDefs.PROFILE_NONE);
		_profiles.add(profile);

		profile = new UserProfileImpl(
				id,
				Integer
						.parseInt(ConstantesGestionUsuariosAdministracion.APLICACION_USUARIOS_PORTAL),
				UserDefs.PROFILE_NONE);
		_profiles.add(profile);

	}

	protected void setProfiles(UserProfilesImpl userProfilesImpl)
			throws Exception {

		if (userProfilesImpl != null) {
			for (int i = 0; i < userProfilesImpl.count(); i++) {
				UserProfile userProfile = (UserProfile) userProfilesImpl.get(i);
				_profiles.add(userProfile);
			}
		}
	}

	/**
	 * Elimina los permisos de un elemento.
	 * 
	 * @param id
	 *            Identificador del elemento.
	 * @throws Exception
	 *             Si se produce algún error en el borrado de los perfiles del
	 *             elemento.
	 */

	protected void deleteProfiles(int id, String entidad) throws Exception {
		LdapUsersTable table = new LdapUsersTable();

		if (_logger.isDebugEnabled())
			_logger.debug("deleteProfiles");

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			DbDeleteFns.delete(dbConn, table.getProfilesTableName(), table
					.getDeleteProfilesQual(id));
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}

	}

	public void resetProfiles() {
		_profiles.clear();
	}

	private boolean existProfile(int idUsuario, int idAplicacion, String entidad) {

		if (_logger.isDebugEnabled())
			_logger.debug("existProfile");

		boolean exist = false;

		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		UserProfilesTable table = new UserProfilesTable();

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			tableInfo.setTableObject(table);
			tableInfo.setClassName(UserProfilesTable.class.getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(GenericProfilesImpl.class.getName());

			exist = DynamicFns.select(dbConn, table
					.getByIdUsuarioIdAplicacionQual(idUsuario, idAplicacion),
					tableInfo, rowsInfo);

		} catch (Exception e) {
			_logger.error(e);
		} finally {
			try {
				dbConn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return exist;
	}

	private void insertProfile(int idUsuario, int idAplicacion, int profile) {

	}

	/**
	 * Metodo que comprueba si el usuario tiene un permiso de manager o
	 * superusario de invesdoc
	 * 
	 * @return
	 */
	private boolean isIdocAdmin() {
		try {
			UserProfile profile = _profiles
					.getProductProfile(UserDefs.PRODUCT_IDOC);
			int prof = profile.getProfile();
			if ((prof == UserDefs.PROFILE_MANAGER)
					|| (prof == UserDefs.PROFILE_SUPERUSER)) {
				return true;
			}
		} catch (Exception e) {
		}

		return false;
	}

	protected UserProfilesImpl _profiles;

	private static final Logger _logger = Logger
			.getLogger(GenericProfilesImpl.class);

}