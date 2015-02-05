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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionesImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresOrganizacionDatos extends SicresOrganizacionImpl {
	private static Logger logger = Logger
			.getLogger(SicresOrganizacionDatos.class);

	public SicresOrganizacionDatos() {

	}

	public SicresOrganizacionDatos(SicresOrganizacionImpl bean)
			throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setCode(statement.getLongText(index++));
		setIdFather(statement.getLongInteger(index++));
		setAcron(statement.getLongText(index++));
		setName(statement.getLongText(index++));
		setCreationDate(statement.getDateTime(index++));
		setDisableDate(statement.getDateTime(index++));
		setType(statement.getLongInteger(index++));
		setEnabled(statement.getLongInteger(index++));
		setCif(statement.getLongText(index++));
		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		statement.setLongInteger(index++, getId());
		statement.setLongText(index++, getCode());
		statement.setLongInteger(index++, getIdFather());
		statement.setLongText(index++, getAcron());
		statement.setLongText(index++, getName());
		statement.setDateTime(index++, getCreationDate());
		statement.setDateTime(index++, getDisableDate());
		statement.setLongInteger(index++, getType());
		statement.setLongInteger(index++, getEnabled());
		statement.setLongText(index++, getCif());
		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongText(index++, getCode());
		statement.setLongInteger(index++, getIdFather());
		statement.setLongText(index++, getAcron());
		statement.setLongText(index++, getName());
		statement.setDateTime(index++, getCreationDate());
		statement.setDateTime(index++, getDisableDate());
		statement.setLongInteger(index++, getType());
		statement.setLongInteger(index++, getEnabled());
		statement.setLongText(index++, getCif());
		return new Integer(index);
	}

	public void load(int identificador, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOrganizacionTabla table = new SicresOrganizacionTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_orgs...");
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
						ISicresAdminDAOException.SCR_ORGS_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_orgs obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_orgs");
			else
				logger.error("Error obteniendo datos de scr_orgs");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void load(String code, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOrganizacionTabla table = new SicresOrganizacionTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_orgs...");
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

			if (!DynamicFns.select(db, table.getByCode(code), tableInfo,
					rowsInfo)) {
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_ORGS_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_orgs obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_orgs");
			else
				logger.error("Error obteniendo datos de scr_orgs");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOrganizacionTabla table = new SicresOrganizacionTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_orgs...");
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
				logger.debug("scr_orgs añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_orgs.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_ORGS_INSERT,
					e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresOrganizacionTabla table = new SicresOrganizacionTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_orgs...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getById(getId()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_orgs.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_orgs");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_ORGS_DELETE);
		}
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOrganizacionTabla table = new SicresOrganizacionTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_orgs.");
			}

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getUpdateColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("update");
			rowsInfo.add(rowInfo);

			DynamicFns.update(db, table.getById(getId()), tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizado scr_orgs.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_orgs", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_ORGS_UPDATE,
					e);
		}
	}

	public static SicresOrganizacionesImpl getOrganizacionesByType(int type,
			DbConnection db) throws ISicresAdminDAOException {
		SicresOrganizacionesImpl oficinas = new SicresOrganizacionesImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresOrganizacionTabla table = new SicresOrganizacionTabla();
		SicresOrganizacionDatos oficina;
		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_orgs");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfoProcedure.setClassName(SicresOrganizacionDatos.class
					.getName());
			rowInfoProcedure.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getByType(type)
					+ table.getOrderByDesc(), false, tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				oficina = (SicresOrganizacionDatos) rowInfoProcedure
						.getRow(counter);
				oficinas.add(oficina);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_orgs obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_orgs");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return oficinas;
	}

	public static SicresOrganizacionesImpl getOrganizacionesByFather(
			int idPadre, DbConnection db) throws ISicresAdminDAOException {
		SicresOrganizacionesImpl oficinas = new SicresOrganizacionesImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresOrganizacionTabla table = new SicresOrganizacionTabla();
		SicresOrganizacionDatos oficina;
		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_orgs");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfoProcedure.setClassName(SicresOrganizacionDatos.class
					.getName());
			rowInfoProcedure.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getByFather(idPadre)
					+ table.getOrderByDesc(), false, tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				oficina = (SicresOrganizacionDatos) rowInfoProcedure
						.getRow(counter);
				oficinas.add(oficina);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_orgs obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_orgs");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return oficinas;
	}
}
