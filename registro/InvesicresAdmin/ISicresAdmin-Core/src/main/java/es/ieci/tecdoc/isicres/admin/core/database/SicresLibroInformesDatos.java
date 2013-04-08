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
import es.ieci.tecdoc.isicres.admin.beans.LibroInformeBean;
import es.ieci.tecdoc.isicres.admin.beans.LibrosInformeBean;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroInformeImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresLibroInformesDatos extends SicresLibroInformeImpl implements
		ISicresDatos {

	private static Logger logger = Logger
			.getLogger(SicresLibroInformesDatos.class);

	public SicresLibroInformesDatos() {

	}

	public SicresLibroInformesDatos(SicresLibroInformeImpl bean)
			throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresLibroInformesTabla table = new SicresLibroInformesTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_reportarch...");
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
				logger.debug("scr_reportarch añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_reportarch.", e);
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.SCR_REPORTARCH_INSERT, e);
		}

	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresLibroInformesTabla table = new SicresLibroInformesTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_reportarch...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getById(getId()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_reportarch.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_reportarch");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_REPORTARCH_DELETE);
		}

	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		statement.setLongInteger(index++, getId());
		statement.setLongInteger(index++, getIdReport());
		statement.setLongInteger(index++, getIdArch());

		return new Integer(index);
	}

	public void load(int identificador, DbConnection db)
			throws ISicresAdminDAOException {
		// TODO Auto-generated method stub

	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {
		// TODO Auto-generated method stub
		int index = idx.intValue();
		setId(statement.getLongInteger(index++));
		setIdReport(statement.getLongInteger(index++));
		setIdArch(statement.getLongInteger(index++));
		setNameLibro(statement.getLongText(index++));
		setIdLibro(statement.getLongText(index++));

		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		// TODO Auto-generated method stub

	}

	public LibrosInformeBean getLibrosByIdReport(int idReport, DbConnection db)
			throws ISicresAdminDAOException {
		LibrosInformeBean lista = new LibrosInformeBean();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresLibroInformesTabla table = new SicresLibroInformesTabla();

		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_reportarch");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo
					.setTablesMethod(SicresLibroInformesTabla.GET_TABLE_NAME_WITH_LIBRO_METHOD);
			tableInfo
					.setColumnsMethod(SicresLibroInformesTabla.GET_ALL_COLUMN_NAMES_WITH_LIBRO);

			rowInfoProcedure.setClassName(this.getClass().getName());
			rowInfoProcedure.setValuesMethod(LOAD_ALL_VALUES_METHOD);
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getLibrosByIdReport(idReport),
					false, tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {

				SicresLibroInformeImpl libroInforme = (SicresLibroInformeImpl) rowInfoProcedure
						.getRow(counter);

				LibroInformeBean bean = new LibroInformeBean();
				bean.setCodigoArchivo(new Integer(libroInforme.getIdArch()).toString());
				bean.setIdArch(libroInforme.getIdArch());
				bean.setIdReport(libroInforme.getIdReport());
				bean.setNombreArchivo(libroInforme.getNameLibro());
				bean.setId(libroInforme.getId());

				lista.add(bean);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_reportofic obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_reportarch");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return lista;
	}

	public void deleteByIdReport(DbConnection db) throws ISicresAdminDAOException {
		SicresLibroInformesTabla table = new SicresLibroInformesTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_reportarch...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getByIdReport(getIdReport()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_reportarch.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_reportarch");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_REPORTARCH_DELETE);
		}
	}

	public static void deleteByLibro(int idLibro, DbConnection db){
		SicresLibroInformesTabla table = new SicresLibroInformesTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_bookofic...");
		}
		try {
			DbDeleteFns.delete(db, table.getTableName(), table.getByLibro(idLibro));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_bookofic.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_bookofic");
		}
	}
}
