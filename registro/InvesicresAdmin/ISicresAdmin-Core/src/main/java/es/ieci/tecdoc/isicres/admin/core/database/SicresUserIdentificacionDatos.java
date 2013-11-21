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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUserIdentificacionImpl;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;

public class SicresUserIdentificacionDatos extends SicresUserIdentificacionImpl {
	private static Logger logger = Logger.getLogger(SicresUserIdentificacionDatos.class);

	public SicresUserIdentificacionDatos(){

	}

	public SicresUserIdentificacionDatos(SicresUserIdentificacionImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setUserId(statement.getLongInteger(index++));
		setTmstamp(statement.getDateTime(index++));
		setFirstName(statement.getShortText(index++));
		setSecondName(statement.getShortText(index++));
		setSurname(statement.getShortText(index++));

		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getUserId());
		statement.setDateTime(index++, getTmstamp());
		statement.setShortText(index++, getFirstName());
		statement.setShortText(index++, getSecondName());
		statement.setShortText(index++, getSurname());

		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setDateTime(index++, getTmstamp());
		statement.setShortText(index++, getFirstName());
		statement.setShortText(index++, getSecondName());
		statement.setShortText(index++, getSurname());

		return new Integer(index);
	}

	public void load(int identificador, DbConnection db) throws ISicresRPAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUserIdentificacionTabla table = new SicresUserIdentificacionTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_userident...");
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
				throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.SCR_USRIDENT_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_userident obtenidos.");
			}
		} catch (ISicresRPAdminDAOException iRPADAOException) {
			if (logger.isDebugEnabled()){
				logger.debug("No se ha encontrado fila en scr_userident", iRPADAOException);
			}
			throw iRPADAOException;
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_userident");
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void add(DbConnection db) throws ISicresRPAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUserIdentificacionTabla table = new SicresUserIdentificacionTabla();

		if(logger.isDebugEnabled()){
			logger.debug("Añadiendo scr_userident...");
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
			if(logger.isDebugEnabled()){
				logger.debug("scr_userident añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_userident.", e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.SCR_USRIDENT_INSERT, e);
		}
	}

	public void delete(DbConnection db) throws ISicresRPAdminDAOException {
		SicresUserIdentificacionTabla table = new SicresUserIdentificacionTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_userident...");
		}
		try {
			DbDeleteFns.delete(db, table.getTableName(), table
					.getById(getUserId()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_userident.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_userident");
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.SCR_USRIDENT_DELETE);
		}
	}

	public void update(DbConnection db) throws ISicresRPAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUserIdentificacionTabla table = new SicresUserIdentificacionTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_userident.");
			}

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getUpdateColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("update");
			rowsInfo.add(rowInfo);

			DynamicFns.update(db, table.getById(getUserId()),
					tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizado scr_userident.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_userident", e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.SCR_USRIDENT_UPDATE);
		}
	}

}
