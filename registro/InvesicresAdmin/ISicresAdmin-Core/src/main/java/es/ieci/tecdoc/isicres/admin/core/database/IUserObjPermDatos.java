package es.ieci.tecdoc.isicres.admin.core.database;

/*$Id*/


import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDeleteFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputStatement;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputStatement;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRow;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRows;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicTable;
import es.ieci.tecdoc.isicres.admin.core.beans.IUserObjPermImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.IUserObjsPermsImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class IUserObjPermDatos extends IUserObjPermImpl {
	private static Logger logger = Logger.getLogger(IUserObjPermDatos.class);

	public IUserObjPermDatos() {

	}

	public IUserObjPermDatos(IUserObjPermImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}
	
	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setDstType(statement.getLongInteger(index++));
		setDstId(statement.getLongInteger(index++));
		setObjId(statement.getLongInteger(index++));
		setAPerm(statement.getLongInteger(index++));
		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		statement.setLongInteger(index++, getDstType());
		statement.setLongInteger(index++, getDstId());
		statement.setLongInteger(index++, getObjId());
		statement.setLongInteger(index++, getAPerm());
		return new Integer(index);
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		IUserObjPermTabla table = new IUserObjPermTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo iuserobjperms...");
		}

		try {

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("insert");
			rowsInfo.add(rowInfo);

			DynamicFns.insert(db, tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("iuserobjperms añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo iuserobjperms.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.IUSEROBJPERMS_INSERT,
					e);
		}
	}

	public static void delete(int dstType, int dstId, int objId, DbConnection db) throws ISicresAdminDAOException {
		IUserObjPermTabla table = new IUserObjPermTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando iuserobjperms...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getAllByIds(dstType, dstId, objId));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado iuserobjperms.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando iuserobjperms");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.IUSEROBJPERMS_DELETE);
		}
	}

	public static void deleteDestino(int dstType, int dstId, DbConnection db) throws ISicresAdminDAOException {
		IUserObjPermTabla table = new IUserObjPermTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando iuserobjperms...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getByDestAndType(dstId, dstType));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado iuserobjperms.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando iuserobjperms");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.IUSEROBJPERMS_DELETE);
		}
	}

	public static void deleteObjeto(int objId, DbConnection db) throws ISicresAdminDAOException {
		IUserObjPermTabla table = new IUserObjPermTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando iuserobjperms...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getByObjAndType(objId));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado iuserobjperms.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando iuserobjperms");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.IUSEROBJPERMS_DELETE);
		}
	}
	
	public static IUserObjsPermsImpl getPermisos(int book, int type, DbConnection db)
			throws ISicresAdminDAOException {
		IUserObjsPermsImpl permisos = new IUserObjsPermsImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		IUserObjPermTabla table = new IUserObjPermTabla();
		IUserObjPermDatos permiso;
		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de iuserobjperms");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfoProcedure.setClassName(IUserObjPermDatos.class.getName());
			rowInfoProcedure.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getByBookAndType(book, type), false,
					tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				permiso = (IUserObjPermDatos) rowInfoProcedure.getRow(counter);
				permisos.add(permiso);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de iuserobjperms obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de iuserobjperms");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return permisos;
	}

}
