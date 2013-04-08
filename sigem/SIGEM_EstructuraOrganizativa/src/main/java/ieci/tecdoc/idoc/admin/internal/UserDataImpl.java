/**
 * 
 */
package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.user.UserData;
import ieci.tecdoc.idoc.admin.database.UserDataTable;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;

import org.apache.log4j.Logger;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class UserDataImpl implements UserData {

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.idoc.admin.api.user.UserData#getCargo()
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.idoc.admin.api.user.UserData#setCargo()
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.idoc.admin.api.user.UserData#getEmail()
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.idoc.admin.api.user.UserData#setEmail()
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.idoc.admin.api.user.UserData#getTfnoMovil()
	 */
	public String getTfnoMovil() {
		return tfnoMovil;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.idoc.admin.api.user.UserData#setTfnoMovil()
	 */
	public void setTfnoMovil(String tfnoMovil) {
		this.tfnoMovil = tfnoMovil;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.idoc.admin.api.user.UserData#getIdCert()
	 */
	public String getIdCert() {
		return idCert;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.idoc.admin.api.user.UserData#setIdCert()
	 */
	public void setIdCert(String idCert) {

		this.idCert = idCert;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.idoc.admin.api.user.UserData#setNombre(java.lang.String)
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.idoc.admin.api.user.UserData#getNombre()
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.idoc.admin.api.user.UserData#setApellidos(java.lang.String)
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.idoc.admin.api.user.UserData#getApelliidos()
	 */
	public String getApelliidos() {
		return apellidos;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.idoc.admin.api.user.UserData#load(int, java.lang.String)
	 */
	public void load(int userId, String entidad) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		UserDataTable userDataTable = new UserDataTable();

		DbConnection dbConn = new DbConnection();

		dbConn.open(DBSessionManager.getSession(entidad));
		tableInfo.setTableObject(userDataTable);
		tableInfo.setClassName(UserDataTable.class.getName());
		tableInfo.setTablesMethod(UserDataTable.GET_TABLE_NAME_METHOD);
		tableInfo
				.setColumnsMethod(UserDataTable.GET_INSERT_COLUMN_NAMES_METHOD);
		rowInfo.addRow(this);
		rowInfo.setClassName(UserDataImpl.class.getName());
		rowInfo.setValuesMethod("loadAllValues");

		rowsInfo.add(rowInfo);
		try {
			DynamicFns.select(dbConn, userDataTable.getLoadIdBaseQual(userId),
					tableInfo, rowsInfo);
		} catch (Exception e) {
			throw e;
		} finally {
			dbConn.close();
		}

	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.idoc.admin.api.user.UserData#loadFromIdCert(java.lang.String,
	 *      java.lang.String)
	 */
	public void loadFromIdCert(String idCert, String entidad) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		UserDataTable userDataTable = new UserDataTable();

		DbConnection dbConn = new DbConnection();

		dbConn.open(DBSessionManager.getSession(entidad));
		tableInfo.setTableObject(userDataTable);
		tableInfo.setClassName(UserDataTable.class.getName());
		tableInfo.setTablesMethod(UserDataTable.GET_TABLE_NAME_METHOD);
		tableInfo
				.setColumnsMethod(UserDataTable.GET_INSERT_COLUMN_NAMES_METHOD);
		rowInfo.addRow(this);
		rowInfo.setClassName(UserDataImpl.class.getName());
		rowInfo.setValuesMethod("loadAllValues");

		rowsInfo.add(rowInfo);
		try {
			DynamicFns.select(dbConn,
					userDataTable.getLoadIdCertBaseQual(idCert), tableInfo,
					rowsInfo);
		} catch (Exception e) {
			throw e;
		} finally {
			dbConn.close();
		}
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();

		if (_logger.isDebugEnabled())
			_logger.debug("getValues");

		id = statement.getLongInteger(index++);
		cargo = statement.getLongText(index++);
		email = statement.getLongText(index++);
		tfnoMovil = statement.getLongText(index++);
		idCert = statement.getLongText(index++);
		nombre = statement.getLongText(index++);
		apellidos = statement.getLongText(index++);

		return new Integer(index);
	}

	/**
	 * Actualiza en base de datos información almacenada por esta clase.
	 * 
	 * @param statement
	 * @param idx
	 * @return
	 * @throws java.lang.Exception
	 */

	public Integer updateValues(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();

		if (_logger.isDebugEnabled())
			_logger.debug("updateValues");

		statement.setLongText(index++, cargo);
		statement.setLongText(index++, email);
		statement.setLongText(index++, tfnoMovil);
		statement.setLongText(index++, idCert);
		statement.setLongText(index++, nombre);
		statement.setLongText(index++, apellidos);

		return new Integer(index);
	}

	/**
	 * Guarda en base de datos informaci�n almacenada por esta clase.
	 * 
	 * @param statement
	 * @param idx
	 * @return
	 * @throws java.lang.Exception
	 */

	public Integer insertValues(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();

		if (_logger.isDebugEnabled())
			_logger.debug("insertValues");

		statement.setLongInteger(index++, id);
		statement.setLongText(index++, cargo);
		statement.setLongText(index++, email);
		statement.setLongText(index++, tfnoMovil);
		statement.setLongText(index++, idCert);
		statement.setLongText(index++, nombre);
		statement.setLongText(index++, apellidos);

		return new Integer(index);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.idoc.admin.api.user.UserData#store(java.lang.String)
	 */
	public void store(String entidad) throws Exception {
		insert(entidad);

	}

	public void insert(String entidad) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		UserDataTable table = new UserDataTable();

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			tableInfo.setTableObject(table);
			tableInfo.setClassName(UserDataTable.class.getName());
			tableInfo.setTablesMethod(UserDataTable.GET_TABLE_NAME_METHOD);
			tableInfo
					.setColumnsMethod(UserDataTable.GET_INSERT_COLUMN_NAMES_METHOD);

			rowInfo.addRow(this);
			rowInfo.setClassName(UserDataImpl.class.getName());
			rowInfo.setValuesMethod("insertValues");
			rowsInfo.add(rowInfo);

			DynamicFns.insert(dbConn, tableInfo, rowsInfo);

		} catch (Exception e) {
			throw e;
		} finally {
			dbConn.close();
		}
	}

	public boolean checkUserDataExists(String entidad) throws Exception {
		int count;
		UserDataTable table = new UserDataTable();

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			count = DbSelectFns.selectCount(dbConn, table.getBaseTableName(),
					table.getLoadIdBaseQual(id));
			if (count > 0)
				return true;
			else
				return false;

		} catch (Exception e) {
			throw e;
		} finally {
			dbConn.close();
		}

	}

	private void updateUserData(String entidad) throws Exception {
		UserDataTable table = new UserDataTable();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo;
		DynamicRow rowInfo;

		if (_logger.isDebugEnabled())
			_logger.debug("update");

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			tableInfo.setTableObject(table);
			tableInfo.setClassName(UserDataTable.class.getName());
			tableInfo.setTablesMethod(UserDataTable.GET_TABLE_NAME_METHOD);
			tableInfo
					.setColumnsMethod(UserDataTable.GET_UPDATE_COLUMN_NAMES_METHOD);

			rowInfo = new DynamicRow();
			rowsInfo = new DynamicRows();

			rowInfo.addRow(this);
			rowInfo.setClassName(UserDataImpl.class.getName());
			rowInfo.setValuesMethod("updateValues");
			rowsInfo.add(rowInfo);

			DynamicFns.update(dbConn, table.getLoadIdBaseQual(id), tableInfo,
					rowsInfo);
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}
	}

	public void update(String entidad) throws Exception {

		if (checkUserDataExists(entidad)) {
			updateUserData(entidad);
		} else {
			insert(entidad);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.idoc.admin.api.user.UserData#delete(java.lang.String)
	 */
	public void delete(String entidad) throws Exception {
		boolean commit = false;
		boolean inTrans = false;
		UserDataTable table = new UserDataTable();

		if (_logger.isDebugEnabled())
			_logger.debug("delete");

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			// tabla de detalle
			DbDeleteFns.delete(dbConn, table.getBaseTableName(),
					table.getLoadIdBaseQual(id));
		} catch (Exception e) {
			throw e;
		} finally {
			dbConn.close();
		}

	}

	public String toString() {
		return toXML(false);
	}

	public String toXML(boolean header) {
		XmlTextBuilder bdr;
		String tagName = "UserData";

		bdr = new XmlTextBuilder();
		if (header) {
			bdr.setStandardHeader();
		}

		bdr.addOpeningTag(tagName);

		bdr.addSimpleElement("Id", Integer.toString(id));
		bdr.addSimpleElement("Cargo", cargo);
		bdr.addSimpleElement("Email", email);
		bdr.addSimpleElement("TfnoMovil", tfnoMovil);
		bdr.addSimpleElement("IdCert", idCert);
		bdr.addClosingTag(tagName);

		return bdr.getText();
	}

	// Datos personales
	int id;
	String cargo;
	String email;
	String tfnoMovil;
	String idCert;
	String nombre;
	String apellidos;
	private static final Logger _logger = Logger.getLogger(UserDataImpl.class);

}
