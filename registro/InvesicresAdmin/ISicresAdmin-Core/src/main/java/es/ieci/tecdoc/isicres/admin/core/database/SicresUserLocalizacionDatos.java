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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUserLocalizacionImpl;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;

public class SicresUserLocalizacionDatos extends SicresUserLocalizacionImpl {
	private static Logger logger = Logger.getLogger(SicresUserLocalizacionDatos.class);

	public SicresUserLocalizacionDatos(){

	}

	public SicresUserLocalizacionDatos(SicresUserLocalizacionImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setUserId(statement.getLongInteger(index++));
		setTmstamp(statement.getDateTime(index++));
		setAddress(statement.getLongText(index++));
		setCity(statement.getLongText(index++));
		setZip(statement.getLongText(index++));
		setCountry(statement.getLongText(index++));
		setTelephone(statement.getLongText(index++));
		setFax(statement.getLongText(index++));
		setEmail(statement.getLongText(index++));
		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getUserId());
		statement.setDateTime(index++, getTmstamp());
		statement.setLongText(index++, getAddress());
		statement.setLongText(index++, getCity());
		statement.setLongText(index++, getZip());
		statement.setLongText(index++, getCountry());
		statement.setLongText(index++, getTelephone());
		statement.setLongText(index++, getFax());
		statement.setLongText(index++, getEmail());
		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setDateTime(index++, getTmstamp());
		statement.setLongText(index++, getAddress());
		statement.setLongText(index++, getCity());
		statement.setLongText(index++, getZip());
		statement.setLongText(index++, getCountry());
		statement.setLongText(index++, getTelephone());
		statement.setLongText(index++, getFax());
		statement.setLongText(index++, getEmail());
		return new Integer(index);
	}

	public void load(int identificador, DbConnection db) throws ISicresRPAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUserLocalizacionTabla table = new SicresUserLocalizacionTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_userloc...");
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
				throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.SCR_USRLOC_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_userloc obtenidos.");
			}
		} catch (ISicresRPAdminDAOException iRPADAOException){
			if (logger.isDebugEnabled()){
				logger.debug("No se ha encontrado fila en scr_userloc", iRPADAOException);
			}
			throw iRPADAOException;
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_userloc");
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void add(DbConnection db) throws ISicresRPAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUserLocalizacionTabla table = new SicresUserLocalizacionTabla();

		if(logger.isDebugEnabled()){
			logger.debug("Añadiendo scr_userloc...");
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
				logger.debug("scr_userloc añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_userloc.", e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.SCR_USRLOC_INSERT, e);
		}
	}

	public void delete(DbConnection db) throws ISicresRPAdminDAOException {
		SicresUserLocalizacionTabla table = new SicresUserLocalizacionTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_userloc...");
		}
		try {
			DbDeleteFns.delete(db, table.getTableName(), table
					.getById(getUserId()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_userloc.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_userloc");
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.SCR_USRLOC_DELETE);
		}
	}

	public void update(DbConnection db) throws ISicresRPAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUserLocalizacionTabla table = new SicresUserLocalizacionTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_userloc.");
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
				logger.debug("Actualizado scr_userloc.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_userloc", e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.SCR_USRLOC_UPDATE);
		}
	}

}
