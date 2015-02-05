package es.ieci.tecdoc.isicres.admin.core.database;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputStatement;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputStatement;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public interface ISicresDatos {
	
	public static String INSERT_METHOD = "insert";
	public static String UPDATE_METHOD = "update";
	public static String LOAD_ALL_VALUES_METHOD = "loadAllValues";
	
	
	public abstract Integer loadAllValues(DbOutputStatement statement, Integer idx) throws Exception;

	public abstract Integer insert(DbInputStatement statement, Integer idx)	throws Exception;

	public abstract Integer update(DbInputStatement statement, Integer idx)throws Exception;

	public abstract void load(int identificador, DbConnection db)  throws ISicresAdminDAOException;

	public abstract void add(DbConnection db) throws ISicresAdminDAOException;

	public abstract void delete(DbConnection db) throws ISicresAdminDAOException;

	public abstract void update(DbConnection db) throws ISicresAdminDAOException;

}