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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionesImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresListaDistribucionDatos extends SicresListaDistribucionImpl {
	private static Logger logger = Logger
			.getLogger(SicresListaDistribucionDatos.class);

	public SicresListaDistribucionDatos() {

	}

	public SicresListaDistribucionDatos(SicresListaDistribucionImpl bean)
			throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setIdOrgs(statement.getLongInteger(index++));
		setTypeDest(statement.getLongInteger(index++));
		setIdDest(statement.getLongInteger(index++));
		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		statement.setLongInteger(index++, getId());
		statement.setLongInteger(index++, getIdOrgs());
		statement.setLongInteger(index++, getTypeDest());
		statement.setLongInteger(index++, getIdDest());
		return new Integer(index);
	}

	public void load(int id, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresListaDistribucionTabla table = new SicresListaDistribucionTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_distlist...");
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

			if (!DynamicFns.select(db, table.getById(id),
					tableInfo, rowsInfo)) {
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_DISTLIST_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_distlist obtenidos.");
			}
		} catch (Exception e) {
			if(e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_distlist");
			else
				logger.error("Error obteniendo datos de scr_distlist");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresListaDistribucionTabla table = new SicresListaDistribucionTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_distlist...");
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
				logger.debug("scr_distlist añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_distlist.", e);
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.SCR_DISTLIST_INSERT, e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresListaDistribucionTabla table = new SicresListaDistribucionTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_distlist...");
		}
		try {
			DbDeleteFns.delete(db, table.getTableName(), table.getById(getId()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_distlist.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_distlist");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.SCR_DISTLIST_DELETE);
		}
	}

	public static void deleteDistribucionesOrganizacion(int idOrg, DbConnection db) throws ISicresAdminDAOException {
		SicresListaDistribucionTabla table = new SicresListaDistribucionTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_distlist...");
		}
		try {
			DbDeleteFns.delete(db, table.getTableName(), table.getByIdOrg(idOrg));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_distlist.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_distlist");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.SCR_DISTLIST_DELETE);
		}
	}
	
	public static SicresListaDistribucionesImpl getDistribucionesOrganizacion(int idOrg,
			DbConnection db) throws ISicresAdminDAOException {
		SicresListaDistribucionesImpl lista = new SicresListaDistribucionesImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresListaDistribucionTabla table = new SicresListaDistribucionTabla();
		SicresListaDistribucionDatos bean;
		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_distlist");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfoProcedure.setClassName(SicresListaDistribucionDatos.class
					.getName());
			rowInfoProcedure.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getByIdOrg(idOrg), false,
					tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				bean = (SicresListaDistribucionDatos) rowInfoProcedure
						.getRow(counter);
				lista.add(bean);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_distlist obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_distlist");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return lista;
	}

}
