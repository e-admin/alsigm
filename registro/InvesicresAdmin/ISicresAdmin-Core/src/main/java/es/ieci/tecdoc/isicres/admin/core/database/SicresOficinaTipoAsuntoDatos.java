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
import es.ieci.tecdoc.isicres.admin.beans.OficinaTipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.OficinasTipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaTipoAsuntoImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresOficinaTipoAsuntoDatos extends SicresOficinaTipoAsuntoImpl implements
		ISicresDatos {

	private static Logger logger = Logger.getLogger(SicresOficinaTipoAsuntoDatos.class);

	public SicresOficinaTipoAsuntoDatos() {

	}

	public SicresOficinaTipoAsuntoDatos(SicresOficinaTipoAsuntoImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOficinaTipoAsuntoTabla table = new SicresOficinaTipoAsuntoTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_caofic...");
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
				logger.debug("scr_caofic añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_caofic.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CAOFIC_INSERT,e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresOficinaTipoAsuntoTabla table = new SicresOficinaTipoAsuntoTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_caofic...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getById(getId()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_caofic.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_caofic");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CAOFIC_DELETE);
		}
	}

	public void deleteByIdMatter(DbConnection db) throws ISicresAdminDAOException {
		SicresOficinaTipoAsuntoTabla table = new SicresOficinaTipoAsuntoTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_caofic...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getByIdMatter(getIdMatter()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_caofic.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_caofic");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CAOFIC_DELETE);
		}
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		statement.setLongInteger(index++, getId());
		statement.setLongInteger(index++,getIdMatter());
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
			logger.debug("Obteniendo datos de scr_caofic...");
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
						ISicresAdminDAOException.SCR_CAOFIC_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_caofic obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_caofic");
			else
				logger.error("Error obteniendo datos de scr_caofic");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setIdMatter(statement.getLongInteger(index++));
		setIdOfic(statement.getLongInteger(index++));
		setNameOfic(statement.getLongText(index++));
		setCodeOfic(statement.getLongText(index++));

		return new Integer(index);
	}

	public Integer loadAllValuesByIdMatter(DbOutputStatement statement, Integer idx)
	throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setIdMatter(statement.getLongInteger(index++));
		setIdOfic(statement.getLongInteger(index++));
		setNameOfic(statement.getLongText(index++));
		setCodeOfic(statement.getLongText(index++));

		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++,getIdMatter());
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
				logger.debug("Actualizando scr_caofic.");
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
				logger.debug("Actualizado scr_caofic.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_caofic", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CAOFIC_UPDATE,
					e);
		}
	}



	public OficinasTipoAsuntoBean getOficinasByIdMatter(int idMatter, DbConnection db) throws ISicresAdminDAOException {
		OficinasTipoAsuntoBean lista = new OficinasTipoAsuntoBean();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresOficinaTipoAsuntoTabla table = new SicresOficinaTipoAsuntoTabla();

		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_caofic");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod(SicresOficinaTipoAsuntoTabla.GET_TABLE_NAME_WITH_OFICINA_METHOD);
			tableInfo.setColumnsMethod(SicresOficinaTipoAsuntoTabla.GET_ALL_COLUMN_NAMES_WITH_OFICINA);

			rowInfoProcedure.setClassName(this.getClass().getName());
			rowInfoProcedure.setValuesMethod(LOAD_ALL_VALUES_METHOD);
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getOficinasByIdMatter(idMatter), false,tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				SicresOficinaTipoAsuntoImpl oficinaTipoAsunto = (SicresOficinaTipoAsuntoImpl) rowInfoProcedure.getRow(counter);

				OficinaTipoAsuntoBean bean = new OficinaTipoAsuntoBean();
				bean.setId(oficinaTipoAsunto.getId());
				bean.setCodigoOficina(oficinaTipoAsunto.getCodeOfic());
				bean.setIdOficina(oficinaTipoAsunto.getIdOfic());
				bean.setIdTipoAsunto(oficinaTipoAsunto.getIdMatter());
				bean.setNombreOficina(oficinaTipoAsunto.getNameOfic());

				lista.add(bean);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_caofic obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_caofic");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return lista;
	}

}
