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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresContadorCentralImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresContadorCentralDatos extends SicresContadorCentralImpl {
	private static Logger logger = Logger
			.getLogger(SicresContadorCentralDatos.class);

	public SicresContadorCentralDatos() {

	}

	public SicresContadorCentralDatos(SicresContadorCentralImpl bean)
			throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setAn(statement.getLongInteger(index++));
		setNumReg(statement.getLongInteger(index++));
		setIdArch(statement.getLongInteger(index++));

		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		statement.setLongInteger(index++, getAn());
		statement.setLongInteger(index++, getNumReg());
		statement.setLongInteger(index++, getIdArch());		

		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getNumReg());		
		return new Integer(index);
	}

	public void load(int type, int year, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresContadorCentralTabla table = new SicresContadorCentralTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_cntcentral...");
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

			if (!DynamicFns.select(db, table.getByIds(type, year), tableInfo,
					rowsInfo)) {
//				throw new RPAdminDAOException(
	//					RPAdminDAOException.SCR_CNTCENTRAL_NOT_FOUND);
				setAn(year);
				setIdArch(type);
				setNumReg(0);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_cntcentral obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_cntcentral");
			else
				logger.error("Error obteniendo datos de scr_cntcentral");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresContadorCentralTabla table = new SicresContadorCentralTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_cntcentral...");
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
				logger.debug("scr_cntcentral añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_cntcentral.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CNTCENTRAL_INSERT,
					e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresContadorCentralTabla table = new SicresContadorCentralTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_cntcentral...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getByIds(getIdArch(), getAn()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_cntcentral.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_cntcentral");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CNTCENTRAL_DELETE);
		}
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresContadorCentralTabla table = new SicresContadorCentralTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_cntcentral.");
			}

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getUpdateColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("update");
			rowsInfo.add(rowInfo);

			DynamicFns.update(db, table.getByIds(getIdArch(), getAn()), tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizado scr_cntcentral.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_cntcentral", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CNTCENTRAL_UPDATE,
					e);
		}
	}
}
