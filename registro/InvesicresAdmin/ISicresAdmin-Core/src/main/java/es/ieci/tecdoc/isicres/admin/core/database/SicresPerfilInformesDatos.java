package es.ieci.tecdoc.isicres.admin.core.database;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import es.ieci.tecdoc.isicres.admin.beans.OptionBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.beans.PerfilInformeBean;
import es.ieci.tecdoc.isicres.admin.beans.PerfilesInformeBean;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresPerfilInformeImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresPerfilInformesDatos  extends SicresPerfilInformeImpl implements
		ISicresDatos {

	private static Logger logger = Logger.getLogger(SicresPerfilInformesDatos.class);

	public SicresPerfilInformesDatos() {

	}

	public SicresPerfilInformesDatos(SicresPerfilInformeImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresPerfilInformesTabla table = new SicresPerfilInformesTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_reportperf...");
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
				logger.debug("scr_reportperf añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_reportperf.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_REPORTPERF_INSERT,e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresPerfilInformesTabla table = new SicresPerfilInformesTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_reportperf...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getById(getId()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_reportperf.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_reportofic");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_REPORTPERF_DELETE);
		}
	}

	public void deleteByIdReport(DbConnection db) throws ISicresAdminDAOException {
		SicresPerfilInformesTabla table = new SicresPerfilInformesTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_reportperf...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getByIdReport(getIdReport()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_reportperf.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_reportperf");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_REPORTPERF_DELETE);
		}
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		statement.setLongInteger(index++, getId());
		statement.setLongInteger(index++,getIdReport());
		statement.setLongInteger(index++,getIdPerf());

		return new Integer(index);
	}

	public void load(int identificador, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresPerfilInformesTabla table = new SicresPerfilInformesTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_reportperf...");
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
						ISicresAdminDAOException.SCR_REPORTPERF_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_reportperf obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_reportperf");
			else
				logger.error("Error obteniendo datos de scr_reportperf");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setIdReport(statement.getLongInteger(index++));
		setIdPerf(statement.getLongInteger(index++));
		return new Integer(index);
	}

	public Integer loadAllValuesByIdMatter(DbOutputStatement statement, Integer idx)
	throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setIdReport(statement.getLongInteger(index++));
		setIdPerf(statement.getLongInteger(index++));
		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++,getIdReport());
		statement.setLongInteger(index++,getIdPerf());

		return new Integer(index);
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresPerfilInformesTabla table = new SicresPerfilInformesTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_reportperf.");
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
				logger.debug("Actualizado scr_reportperf.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_reportperf", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_REPORTPERF_UPDATE,
					e);
		}
	}


	/**
	 *
	 * @param idReport
	 * @param db
	 * @return
	 * @throws ISicresAdminDAOException
	 */
	public PerfilesInformeBean getPerfilesByIdReport(int idReport, DbConnection db, OptionsBean listaPerfiles) throws ISicresAdminDAOException {
		PerfilesInformeBean lista = new PerfilesInformeBean();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresPerfilInformesTabla table = new SicresPerfilInformesTabla();

		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_reportperf");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod(SicresPerfilInformesTabla.GET_TABLE_NAME_WITH_PERFILES_METHOD);
			tableInfo.setColumnsMethod(SicresPerfilInformesTabla.GET_ALL_COLUMN_NAMES_WITH_PERFILES);

			rowInfoProcedure.setClassName(this.getClass().getName());
			rowInfoProcedure.setValuesMethod(LOAD_ALL_VALUES_METHOD);
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getPerfilesByIdReport(idReport), false,tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			Iterator it_perfiles = listaPerfiles.getLista().iterator();
			Map mapaPerfiles = new HashMap();
			while(it_perfiles.hasNext()){
				OptionBean op = (OptionBean)it_perfiles.next();
				mapaPerfiles.put(op.getCodigo(), op);
			}


			for (counter = 0; counter < size; counter++) {
				SicresPerfilInformeImpl perfilInforme = (SicresPerfilInformeImpl) rowInfoProcedure.getRow(counter);
				OptionBean op = (OptionBean)mapaPerfiles.get(new Integer(perfilInforme.getIdPerf()).toString());

				PerfilInformeBean bean = new PerfilInformeBean();
				bean.setCodigoPerfil(new Integer(perfilInforme.getIdPerf()).toString());
				bean.setIdReport(perfilInforme.getIdReport());
				bean.setId(perfilInforme.getId());
				bean.setIdPerfil(Integer.parseInt(op.getCodigo()));
				bean.setCodigoPerfil(op.getCodigo());
				bean.setNombrePerfil(op.getDescripcion());
				lista.add(bean);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_reportperf obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_reportperf", e);
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.SCR_REPORT_PERF_LOAD);
		}

		return lista;
	}

}
