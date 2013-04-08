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
import es.ieci.tecdoc.isicres.admin.beans.OficinaInformeBean;
import es.ieci.tecdoc.isicres.admin.beans.OficinasInformeBean;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaInformeImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresOficinaInformesDatos extends SicresOficinaInformeImpl implements
		ISicresDatos {

	private static Logger logger = Logger.getLogger(SicresOficinaTipoAsuntoDatos.class);

	public SicresOficinaInformesDatos() {

	}

	public SicresOficinaInformesDatos(SicresOficinaInformeImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOficinaInformesTabla table = new SicresOficinaInformesTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_reportofic...");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod(ISicresTabla.GET_TABLES_NAME_METHOD);
			tableInfo.setColumnsMethod(ISicresTabla.GET_COLUMN_NAMES_METHOD);

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod(INSERT_METHOD);
			rowsInfo.add(rowInfo);

			DynamicFns.insert(db, tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("scr_reportofic añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_reportofic.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_REPORTOFIC_INSERT,e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresOficinaInformesTabla table = new SicresOficinaInformesTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_reportofic...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getById(getId()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_reportofic.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_reportofic");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_REPORTOFIC_DELETE);
		}
	}

	public void deleteByIdReport(DbConnection db) throws ISicresAdminDAOException {
		SicresOficinaInformesTabla table = new SicresOficinaInformesTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_reportofic...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getByIdReport(getIdReport()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_reportofic.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_reportofic");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_REPORTOFIC_DELETE);
		}
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		statement.setLongInteger(index++, getId());
		statement.setLongInteger(index++,getIdReport());
		statement.setLongInteger(index++,getIdOfic());

		return new Integer(index);
	}

	public void load(int identificador, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOficinaTipoAsuntoTabla table = new SicresOficinaTipoAsuntoTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_reportofic...");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod(ISicresTabla.GET_TABLES_NAME_METHOD);
			tableInfo.setColumnsMethod(ISicresTabla.GET_COLUMN_NAMES_METHOD);

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod(LOAD_ALL_VALUES_METHOD);
			rowsInfo.add(rowInfo);

			if (!DynamicFns.select(db, table.getById(identificador), tableInfo,
					rowsInfo)) {
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_REPORTOFIC_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_reportofic obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_reportofic");
			else
				logger.error("Error obteniendo datos de scr_reportofic");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setIdReport(statement.getLongInteger(index++));
		setIdOfic(statement.getLongInteger(index++));
		setNameOfic(statement.getLongText(index++));
		setCodeOfic(statement.getLongText(index++));

		return new Integer(index);
	}

	public Integer loadAllValuesByIdMatter(DbOutputStatement statement, Integer idx)
	throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setIdReport(statement.getLongInteger(index++));
		setIdOfic(statement.getLongInteger(index++));
		setNameOfic(statement.getLongText(index++));
		setCodeOfic(statement.getLongText(index++));

		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++,getIdReport());
		statement.setLongInteger(index++,getIdOfic());

		return new Integer(index);
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOficinaTipoAsuntoTabla table = new SicresOficinaTipoAsuntoTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_reportofic.");
			}

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod(ISicresTabla.GET_TABLES_NAME_METHOD);
			tableInfo.setColumnsMethod(ISicresTabla.GET_UPDATE_COLUMN_NAMES);

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod(UPDATE_METHOD);
			rowsInfo.add(rowInfo);

			DynamicFns.update(db, table.getById(getId()), tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizado scr_reportofic.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_reportofic", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_REPORTOFIC_UPDATE,
					e);
		}
	}



	public OficinasInformeBean getOficinasByIdReport(int idReport, DbConnection db) throws ISicresAdminDAOException {
		OficinasInformeBean lista = new OficinasInformeBean();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresOficinaInformesTabla table = new SicresOficinaInformesTabla();

		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_reportofic");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod(SicresOficinaInformesTabla.GET_TABLE_NAME_WITH_OFICINA_METHOD);
			tableInfo.setColumnsMethod(SicresOficinaInformesTabla.GET_ALL_COLUMN_NAMES_WITH_OFICINA);

			rowInfoProcedure.setClassName(this.getClass().getName());
			rowInfoProcedure.setValuesMethod(LOAD_ALL_VALUES_METHOD);
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getOficinasByIdReport(idReport), false,tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				SicresOficinaInformeImpl informe = (SicresOficinaInformeImpl)rowInfoProcedure.getRow(counter);

				OficinaInformeBean bean = new OficinaInformeBean();
				bean.setId(informe.getId());
				bean.setCodigoOficina(informe.getCodeOfic());
				bean.setIdOficina(informe.getIdOfic());
				bean.setIdInforme(informe.getIdReport());
				bean.setNombreOficina(informe.getNameOfic());

				lista.add(bean);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_reportofic obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_reportofic");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return lista;
	}

}
