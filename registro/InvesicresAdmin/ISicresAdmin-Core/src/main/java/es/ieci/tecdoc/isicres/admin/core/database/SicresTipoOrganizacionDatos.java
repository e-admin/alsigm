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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOrganizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresTiposOrganizacionesImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresTipoOrganizacionDatos extends SicresTipoOrganizacionImpl {
	private static Logger logger = Logger
			.getLogger(SicresTipoOrganizacionDatos.class);

	public SicresTipoOrganizacionDatos() {

	}

	public SicresTipoOrganizacionDatos(SicresTipoOrganizacionImpl bean)
			throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setCode(statement.getLongText(index++));
		setDescription(statement.getLongText(index++));

		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		statement.setLongInteger(index++, getId());
		statement.setLongText(index++, getCode());
		statement.setLongText(index++, getDescription());
		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongText(index++, getDescription());
		return new Integer(index);
	}

	public void load(int identificador, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresTipoOrganizacionTabla table = new SicresTipoOrganizacionTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_typeadm...");
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
						ISicresAdminDAOException.SCR_TYPEADMIN_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_typeadm obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_typeadm");
			else
				logger.error("Error obteniendo datos de scr_typeadm");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void load(String pCodigo, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresTipoOrganizacionTabla table = new SicresTipoOrganizacionTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_typeadm...");
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

			if (!DynamicFns.select(db, table.getByCode(pCodigo), tableInfo,
					rowsInfo)) {
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_TYPEADMIN_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_typeadm obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_typeadm");
			else
				logger.error("Error obteniendo datos de scr_typeadm");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresTipoOrganizacionTabla table = new SicresTipoOrganizacionTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_typeadm...");
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
				logger.debug("scr_typeadm añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_typeadm.", e);
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.SCR_TYPEADMIN_INSERT, e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresTipoOrganizacionTabla table = new SicresTipoOrganizacionTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_typeadm...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getById(getId()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_typeadm.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_typeadm");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.SCR_TYPEADMIN_DELETE);
		}
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresTipoOrganizacionTabla table = new SicresTipoOrganizacionTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_typeadm.");
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
				logger.debug("Actualizado scr_typeadm.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_typeadm", e);
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.SCR_TYPEADMIN_UPDATE, e);
		}
	}

	public static SicresTiposOrganizacionesImpl getTiposOrganizaciones(
			DbConnection db) throws ISicresAdminDAOException {
		SicresTiposOrganizacionesImpl tipos = new SicresTiposOrganizacionesImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresTipoOrganizacionTabla table = new SicresTipoOrganizacionTabla();
		SicresTipoOrganizacionDatos tipo;
		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_typeadm");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfoProcedure.setClassName(SicresTipoOrganizacionDatos.class
					.getName());
			rowInfoProcedure.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getWithoutER()
					+ table.getOrderByDesc(), false, tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				tipo = (SicresTipoOrganizacionDatos) rowInfoProcedure
						.getRow(counter);
				tipos.add(tipo);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_typeadm obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_typeadm");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return tipos;
	}
}
