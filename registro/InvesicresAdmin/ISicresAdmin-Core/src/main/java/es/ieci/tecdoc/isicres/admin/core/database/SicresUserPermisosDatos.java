package es.ieci.tecdoc.isicres.admin.core.database;

/*$Id*/


import java.util.Date;

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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUserPermisosImpl;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;

public class SicresUserPermisosDatos extends SicresUserPermisosImpl {
	private static Logger logger = Logger.getLogger(SicresUserPermisosDatos.class);

	public SicresUserPermisosDatos(){
	}

	public SicresUserPermisosDatos(SicresUserPermisosImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setIdUsr(statement.getLongInteger(index++));
		setTmstamp(statement.getDateTime(index++));
		setPerms(statement.getLongInteger(index++));

		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getIdUsr());
		statement.setDateTime(index++, new Date());
		statement.setLongInteger(index++, getPerms());

		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setDateTime(index++, new Date());
		statement.setLongInteger(index++, getPerms());

		return new Integer(index);
	}

	public void load(int identificador, DbConnection db) throws ISicresRPAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUserPermisosTabla table = new SicresUserPermisosTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_usrperms...");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);

			if (!DynamicFns.select(db, table.getById(identificador),
					tableInfo, rowsInfo)) {
				throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.SCR_USRPERMS_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_usrperms obtenidos.");
			}
		} catch (ISicresRPAdminDAOException iRPADAOException){
			if(logger.isDebugEnabled()){
				logger.debug("No se ha encontrado fila en scr_usrperms", iRPADAOException);
			}
			throw iRPADAOException;
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_usrperms");
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void add(DbConnection db) throws ISicresRPAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUserPermisosTabla table = new SicresUserPermisosTabla();

		try {
			if(logger.isDebugEnabled()) {
				logger.debug("Añadiendo scr_usrperms...");
			}

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("insert");
			rowsInfo.add(rowInfo);

			DynamicFns.insert(db, tableInfo, rowsInfo);
			if(logger.isDebugEnabled()) {
				logger.debug("scr_usrperms añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_usrperms.", e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.SCR_USRPERMS_INSERT, e);
		}
	}

	public void delete(DbConnection db) throws ISicresRPAdminDAOException {
		SicresUserPermisosTabla table = new SicresUserPermisosTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_userident...");
		}
		try {
			DbDeleteFns.delete(db, table.getTableName(), table
					.getById(getIdUsr()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_usrperms.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_usrperms");
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.SCR_USRPERMS_DELETE);
		}
	}

	public void update(DbConnection db) throws ISicresRPAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUserPermisosTabla table = new SicresUserPermisosTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_usrperms.");
			}

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getUpdateColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("update");
			rowsInfo.add(rowInfo);

			DynamicFns.update(db, table.getById(getIdUsr()),
					tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizado scr_usrperms.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_usrperms", e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.SCR_USRPERMS_UPDATE);
		}
	}

}
