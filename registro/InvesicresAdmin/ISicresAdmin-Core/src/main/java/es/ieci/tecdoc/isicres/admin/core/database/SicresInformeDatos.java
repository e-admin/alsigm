package es.ieci.tecdoc.isicres.admin.core.database;


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
import es.ieci.tecdoc.isicres.admin.beans.Informe;
import es.ieci.tecdoc.isicres.admin.beans.InformesBean;
import es.ieci.tecdoc.isicres.admin.core.UtilsBD;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresInformeDatos extends Informe {
	private static Logger logger = Logger.getLogger(SicresInformeDatos.class);

	// Constructor
	public SicresInformeDatos() {

	}

	public SicresInformeDatos(Informe bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresInformeTabla table = new SicresInformeTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_report...");
		}

		try {

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAddColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("insert");
			rowsInfo.add(rowInfo);

			DynamicFns.insert(db, tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("scr_report añadida.");
			}
		} catch (Exception e) {

			// TODO Hacer esto de los errores de foreing key de forma generica
			if (UtilsBD.isErrorDuplicateKey(e.getMessage())) {
				logger.error("Error eliminando scr_report. Error foreing key");
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_REPORT_UNIQUE_KEY_ERROR);
			} else {
				logger.error("Error añadiendo scr_report.", e);
				throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_REPORT_INSERT);
			}
		}

	}

	/**
	 * Recupera todos los informes (tabla src_reports)
	 *
	 * @param db
	 * @return
	 */
	public static InformesBean getInformes(DbConnection db) {
		InformesBean informesBean = new InformesBean();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresInformeTabla table = new SicresInformeTabla();
		SicresInformeDatos tipoInformeDAO;
		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_reports");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getColumnsNamesList");

			rowInfoProcedure.setClassName(SicresInformeDatos.class.getName());
			rowInfoProcedure.setValuesMethod("loadValuesList");
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getOrderByDesc(), false,
					tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				tipoInformeDAO = (SicresInformeDatos) rowInfoProcedure
						.getRow(counter);
				informesBean.add(tipoInformeDAO);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_reportaux obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_reportsaux");
		}

		return informesBean;
	}

	/**
	 * Retorna el detalle de un informe
	 *
	 * @param identificador
	 * @param db
	 * @throws ISicresAdminDAOException
	 */
	public void load(int identificador, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresInformeTabla table = new SicresInformeTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_report...");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);

			if (!DynamicFns.select(db, table.getById(identificador), tableInfo,
					rowsInfo,false)) {
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_report obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_report");
			else
				logger.error("Error obteniendo datos de scr_report");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	/**
	 * Descarga un informe
	 *
	 * @param identificador
	 * @param db
	 * @throws ISicresAdminDAOException
	 */
	public void download(int identificador, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresInformeTabla table = new SicresInformeTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_report...");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);

			if (!DynamicFns.select(db, table.getById(identificador), tableInfo,
					rowsInfo, false)) {
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_report obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_report");
			else
				logger.error("Error obteniendo datos de scr_report");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	/**
	 * Carga los valores recuperados de la base de datos en el objeto bean
	 *
	 * @param statement
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		setId(statement.getLongInteger(index++));
		setReport(statement.getLongText(index++));
		setTypeReport(statement.getLongInteger(index++));
		setTypeArch(statement.getLongInteger(index++));
		setAllArch(statement.getLongInteger(index++));
		setAllOfics(statement.getLongInteger(index++));
		setAllPerfs(statement.getLongInteger(index++));
		setDescription(statement.getLongText(index++));
		setFileData(statement.getBytes(index++));
		return new Integer(index);
	}

	/**
	 * Carga los valores recuperados de la base de datos en el objeto bean
	 *
	 * @param statement
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	public Integer loadValuesList(DbOutputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		setId(statement.getLongInteger(index++));
		setReport(statement.getLongText(index++));
		setTypeReport(statement.getLongInteger(index++));
		setTypeArch(statement.getLongInteger(index++));
		setAllArch(statement.getLongInteger(index++));
		setAllOfics(statement.getLongInteger(index++));
		setAllPerfs(statement.getLongInteger(index++));
		setDescription(statement.getLongText(index++));
		return new Integer(index);
	}

	/**
	 *
	 * @param statement
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if(getFileData().length!=0){
			statement.setLongText(index++, getReport());
		}
		statement.setLongInteger(index++, getTypeReport());
		statement.setLongInteger(index++, getTypeArch());
		statement.setLongInteger(index++, getAllArch());
		statement.setLongInteger(index++, getAllOfics());
		statement.setLongInteger(index++, getAllPerfs());
		statement.setLongText(index++, getDescription());
		if(getFileData().length!=0){
			statement.setBytes(index++, getFileData());
		}

		return new Integer(index);
	}

	/**
	 *
	 * @param db
	 * @throws ISicresAdminDAOException
	 */
	public void update(DbConnection db) throws ISicresAdminDAOException {

		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresInformeTabla table = new SicresInformeTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_report.");
			}

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			if(getFileData().length!=0){
				tableInfo.setColumnsMethod("getALLUpdateColumnNames");
			}else{
				tableInfo.setColumnsMethod("getUpdateColumnNames");
			}

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("update");
			rowsInfo.add(rowInfo);

			DynamicFns.update(db, table.getById(getId()), tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizado scr_report.");
			}
		} catch (Exception e) {
			// TODO Hacer esto de los errores de foreing key de forma generica
			if (UtilsBD.isErrorDuplicateKey(e.getMessage())) {
				logger.error("Error eliminando scr_report. Error foreing key");
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_REPORT_UNIQUE_KEY_ERROR);
			} else {
				logger.error("Error actualizando scr_report", e);
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_REPORT_UPDATE, e);
			}
		}
	}

	/**
	 *
	 * @param db
	 * @throws ISicresAdminDAOException
	 */
	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresInformeTabla table = new SicresInformeTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_report...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getById(getId()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_report.");
			}
		} catch (Exception e) {

			// TODO Hacer esto de los errores de foreing key de forma generica
			if (UtilsBD.isErrorForeignKey(e.getMessage())) {
				logger.error("Error eliminando scr_report. Error foreing key");
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_CA_DELETE_FOREIGN_KEY_ERROR);
			} else {
				logger.error("Error eliminando scr_report");
				throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_REPORT_DELETE);
			}
		}
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getId());
		statement.setLongText(index++, getReport());
		statement.setLongInteger(index++, getTypeReport());
		statement.setLongInteger(index++, getTypeArch());
		statement.setLongInteger(index++, getAllArch());
		statement.setLongInteger(index++, getAllOfics());
		statement.setLongInteger(index++, getAllPerfs());
		statement.setLongText(index++, getDescription());
		statement.setBytes(index, getFileData());

		return new Integer(index);
	}

}
