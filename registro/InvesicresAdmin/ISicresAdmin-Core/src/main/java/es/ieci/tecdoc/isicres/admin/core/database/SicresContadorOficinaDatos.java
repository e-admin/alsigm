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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresContadorOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresContadoresOficinasImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresLibrosOficinasImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresContadorOficinaDatos extends SicresContadorOficinaImpl {
	private static Logger logger = Logger
			.getLogger(SicresContadorOficinaDatos.class);

	public SicresContadorOficinaDatos() {

	}

	public SicresContadorOficinaDatos(SicresContadorOficinaImpl bean)
			throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setAn(statement.getLongInteger(index++));
		setOficina(statement.getLongInteger(index++));		
		setNumReg(statement.getLongInteger(index++));
		setIdArch(statement.getLongInteger(index++));

		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		statement.setLongInteger(index++, getAn());
		statement.setLongInteger(index++, getOficina());
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

	public void load(int type, int year, int oficina, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresContadorOficinaTabla table = new SicresContadorOficinaTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_cntoficina...");
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

			if (!DynamicFns.select(db, table.getByTypeYearAndOfic(type, year, oficina), tableInfo,
					rowsInfo)) {
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_CNTOFICINA_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_cntoficina obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_cntoficina");
			else
				logger.error("Error obteniendo datos de scr_cntoficina");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresContadorOficinaTabla table = new SicresContadorOficinaTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_cntoficina...");
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
				logger.debug("scr_cntoficina añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_cntoficina.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CNTOFICINA_INSERT,
					e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresContadorOficinaTabla table = new SicresContadorOficinaTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_cntoficina...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getByTypeYearAndOfic(getIdArch(), getAn(), getOficina()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_cntoficina.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_cntoficina");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CNTOFICINA_DELETE);
		}
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresContadorOficinaTabla table = new SicresContadorOficinaTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_cntoficina.");
			}

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getUpdateColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("update");
			rowsInfo.add(rowInfo);

			DynamicFns.update(db, table.getByTypeYearAndOfic(getIdArch(), getAn(), getOficina()), tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizado scr_cntoficina.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_cntoficina", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CNTOFICINA_UPDATE,
					e);
		}
	}
	
	public static SicresContadoresOficinasImpl getContadores(int anyo, int tipo,
			DbConnection db) throws ISicresAdminDAOException {
		SicresContadoresOficinasImpl lista = new SicresContadoresOficinasImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresContadorOficinaTabla table = new SicresContadorOficinaTabla();
		SicresContadorOficinaDatos bean;
		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_cntoficina");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfoProcedure.setClassName(SicresContadorOficinaDatos.class
					.getName());
			rowInfoProcedure.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getByTypeAndYear(tipo, anyo), false,
					tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				bean = (SicresContadorOficinaDatos) rowInfoProcedure
						.getRow(counter);
				lista.add(bean);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_cntoficina obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_cntoficina");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return lista;
	}

}
