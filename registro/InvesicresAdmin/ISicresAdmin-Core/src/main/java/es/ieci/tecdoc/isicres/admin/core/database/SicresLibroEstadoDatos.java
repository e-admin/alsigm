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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroEstadoImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresLibroEstadoDatos extends SicresLibroEstadoImpl {
	private static Logger logger = Logger
			.getLogger(SicresLibroEstadoDatos.class);

	public SicresLibroEstadoDatos() {

	}

	public SicresLibroEstadoDatos(SicresLibroEstadoImpl bean)
			throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setIdArchReg(statement.getLongInteger(index++));
		setState(statement.getLongInteger(index++));
		setCloseDate(statement.getDateTime(index++));
		setCloseUser(statement.getLongText(index++));
		setNumerationType(statement.getLongInteger(index++));
		setImageAuth(statement.getLongInteger(index++));

		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		statement.setLongInteger(index++, getId());
		statement.setLongInteger(index++, getIdArchReg());
		statement.setLongInteger(index++, getState());		
		statement.setDateTime(index++, getCloseDate());
		statement.setLongText(index++, getCloseUser());
		statement.setLongInteger(index++, getNumerationType());
		statement.setLongInteger(index++, getImageAuth());
		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getState());		
		statement.setDateTime(index++, getCloseDate());
		statement.setLongText(index++, getCloseUser());
		statement.setLongInteger(index++, getNumerationType());
		statement.setLongInteger(index++, getImageAuth());
		return new Integer(index);
	}

	public void load(int identificador, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresLibroEstadoTabla table = new SicresLibroEstadoTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_regstate...");
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

			if (!DynamicFns.select(db, table.getById(identificador), tableInfo,
					rowsInfo)) {
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_REGSTATE_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_regstate obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_regstate");
			else
				logger.error("Error obteniendo datos de scr_regstate");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresLibroEstadoTabla table = new SicresLibroEstadoTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_regstate...");
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
				logger.debug("scr_regstate añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_regstate.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_REGSTATE_INSERT,
					e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresLibroEstadoTabla table = new SicresLibroEstadoTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_regstate...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getById(getIdArchReg()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_regstate.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_regstate");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_REGSTATE_DELETE);
		}
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresLibroEstadoTabla table = new SicresLibroEstadoTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_regstate.");
			}

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getUpdateColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("update");
			rowsInfo.add(rowInfo);

			DynamicFns.update(db, table.getById(getIdArchReg()), tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizado scr_regstate.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_regstate", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_REGSTATE_UPDATE,
					e);
		}
	}
}
