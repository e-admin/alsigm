package common.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;

import org.apache.log4j.Logger;

import common.vos.IKeyId;
import common.vos.IKeyValue;
import common.vos.KeyValueVO;

/**
 * Clase base para las implementaciones de los interfaces de acceso a datos
 */
public abstract class DBEntityKeyValue extends DBEntity implements
		IDBEntityKeyValue {

	DbColumnDef[] COLUMNAS_KEY_ID = new DbColumnDef[] {
			new DbColumnDef("key", getKeyField()),
			new DbColumnDef("value", getValueField()) };

	public DBEntityKeyValue(DbDataSource dataSource) {
		super(dataSource);
	}

	public DBEntityKeyValue(DbConnection conn) {
		super(conn);
	}

	/** Logger de la clase. */
	protected static Logger logger = Logger.getLogger(DBEntityKeyValue.class);

	public IKeyValue getByKey(String key) {
		return (IKeyValue) getVO(getWhereClauseByKey(key), getTableName(),
				COLUMNAS_KEY_ID, KeyValueVO.class);

	}

	public IKeyValue getByValue(String value) {
		return (IKeyValue) getVO(getWhereClauseByValue(value), getTableName(),
				COLUMNAS_KEY_ID, KeyValueVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.db.IDBEntityKeyValue#getById(java.lang.String)
	 */
	public boolean existeByKey(String key) {
		IKeyId objeto = getByKey(key);

		if (objeto != null && objeto.getId().equals(key)) {
			return true;
		}
		return false;
	}

	public String getWhereClauseByKey(String key) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(getKeyField(), key));

		return qual.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.db.IDBEntityKeyValue#getById(java.lang.String)
	 */
	public boolean existeByValue(String key, String value) {
		IKeyValue objeto = getByValue(value);

		if (objeto != null && !objeto.getId().equals(key)) {
			return true;
		}
		return false;
	}

	public String getWhereClauseByValue(String value) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(getValueField(), value, true));

		return qual.toString();
	}
}
