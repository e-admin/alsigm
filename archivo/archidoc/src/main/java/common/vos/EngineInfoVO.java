package common.vos;

import ieci.core.db.DbEngine;

import common.util.DBFactoryConstants;

/**
 * Factoría de base de datos
 * 
 * @author IECISA
 * 
 */
public class EngineInfoVO {
	/**
	 * Identificador del motor de base de datos {@link DbEngine#ORACLE}
	 * {@link DbEngine#SQLSERVER} {@link DbEngine#DB2} {@link DbEngine#POSTGRES}
	 */
	private int idEngine;

	/**
	 * Identificador de la factoría de base de datos a utilizar
	 * {@link DBFactoryConstants}
	 */
	private String dbFactoryClass;

	private boolean allowHint = true;

	private String databaseProductName;

	/**
	 * @return Entity que gestiona la tabla<b></b>
	 */
	public int getIdEngine() {
		return idEngine;
	}

	/**
	 * @param idEngine
	 *            the idEngine to set
	 */
	public void setIdEngine(int idEngine) {
		this.idEngine = idEngine;
	}

	public void setDbFactoryClass(String dbFactoryClass) {
		this.dbFactoryClass = dbFactoryClass;
	}

	public String getDbFactoryClass() {
		return dbFactoryClass;
	}

	public void setAllowHint(boolean allowHint) {
		this.allowHint = allowHint;
	}

	public boolean isAllowHint() {
		return allowHint;
	}

	public void setDatabaseProductName(String databaseProductName) {
		this.databaseProductName = databaseProductName;
	}

	public String getDatabaseProductName() {
		return databaseProductName;
	}
}
