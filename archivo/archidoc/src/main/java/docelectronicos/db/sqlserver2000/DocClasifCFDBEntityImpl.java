package docelectronicos.db.sqlserver2000;

import ieci.core.db.DbConnection;

import common.db.DbDataSource;

import docelectronicos.db.DocClasifCFDBEntityImplBase;
import docelectronicos.vos.DocClasificadorVO;

/**
 * 
 * TODO ZHACER SQLSERVER2000
 */
public class DocClasifCFDBEntityImpl extends DocClasifCFDBEntityImplBase {
	/**
	 * @param dataSource
	 */
	public DocClasifCFDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public DocClasifCFDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * docelectronicos.db.DocClasifCFDBEntityImplBase#getClasificadorPadre(java
	 * .lang.String)
	 */
	public DocClasificadorVO getClasificadorPadre(String id) {
		return null;
	}

}
