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
import es.ieci.tecdoc.isicres.admin.beans.Transporte;
import es.ieci.tecdoc.isicres.admin.beans.Transportes;
import es.ieci.tecdoc.isicres.admin.core.UtilsBD;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresTransporteDatos extends Transporte {
	private static Logger logger = Logger.getLogger(SicresTransporteDatos.class);

	public SicresTransporteDatos() {}

	public SicresTransporteDatos(Transporte bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setTransport(statement.getLongText(index++));
		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		statement.setLongInteger(index++, getId());
		statement.setLongText(index++, getTransport());
		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongText(index++, getTransport());
		return new Integer(index);
	}

	public void load(int identificador, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresTransporteTabla table = new SicresTransporteTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_tt...");
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
						ISicresAdminDAOException.SCR_TT_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_tt obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_tt");
			else
				logger.error("Error obteniendo datos de scr_tt");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresTransporteTabla table = new SicresTransporteTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_tt...");
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
				logger.debug("scr_tt añadida.");
			}
		} catch (Exception e) {
			//TODO Hacer esto de los errores de foreing key de forma generica
			if(UtilsBD.isErrorDuplicateKey(e.getMessage())){
				logger.error("Error añadiendo scr_tt. Error duplicate key");
				throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_TT_UNIQUE_KEY_ERROR);
			}else{
				logger.error("Error añadiendo scr_tt.", e);
				throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_TT_INSERT,e);
			}
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresTransporteTabla table = new SicresTransporteTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_tt...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getById(getId()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_tt.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_tt");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_TT_DELETE);
		}
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresTransporteTabla table = new SicresTransporteTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_tt.");
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
				logger.debug("Actualizado scr_tt.");
			}
		} catch (Exception e) {
			if (UtilsBD.isErrorDuplicateKey(e.getMessage())) {
				logger.error("Error añadiendo scr_tt. Error duplicate key");
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_TT_UNIQUE_KEY_ERROR);
			} else {
				logger.error("Error actualizando scr_tt", e);
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_TT_UPDATE, e);
			}
		}
	}

	public static Transportes getTransportes(DbConnection db)
			throws ISicresAdminDAOException {
		Transportes transportes = new Transportes();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresTransporteTabla table = new SicresTransporteTabla();
		SicresTransporteDatos transporte;
		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_tt");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfoProcedure.setClassName(SicresTransporteDatos.class.getName());
			rowInfoProcedure.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getOrderByDesc(), false,
					tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				transporte = (SicresTransporteDatos) rowInfoProcedure.getRow(counter);
				transportes.add(transporte);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_tt obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_tt");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return transportes;
	}
}
