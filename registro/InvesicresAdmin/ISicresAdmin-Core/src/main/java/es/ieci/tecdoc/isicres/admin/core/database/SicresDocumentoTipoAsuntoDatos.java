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
import es.ieci.tecdoc.isicres.admin.beans.DocumentoTipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.DocumentosTipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresDocumentoTipoAsuntoImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;


public class SicresDocumentoTipoAsuntoDatos extends SicresDocumentoTipoAsuntoImpl
		implements ISicresDatos {

	private static Logger logger = Logger.getLogger(SicresDocumentoTipoAsuntoDatos.class);

	public SicresDocumentoTipoAsuntoDatos(){

	}

	public SicresDocumentoTipoAsuntoDatos(SicresDocumentoTipoAsuntoImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresDocumentoTipoAsuntoTabla table = new SicresDocumentoTipoAsuntoTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_cadocs...");
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
				logger.debug("scr_cadocs añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_caofic.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CADOCS_INSERT,e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresDocumentoTipoAsuntoTabla table = new SicresDocumentoTipoAsuntoTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_cadocs...");
		}
		try {
			DbDeleteFns.delete(db, table.getTableName(), table.getById(getId()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_cadocs.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_cadocs");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CADOCS_DELETE);
		}
	}

	public void deleteByIdMatter(DbConnection db) throws ISicresAdminDAOException {
		SicresDocumentoTipoAsuntoTabla table = new SicresDocumentoTipoAsuntoTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_cadocs...");
		}
		try {
			DbDeleteFns.delete(db, table.getTableName(), table.getByIdMatter(getIdMatter()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_cadocs.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_cadocs");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CADOCS_DELETE);
		}
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();

		statement.setLongInteger(index++, getId());
		statement.setLongInteger(index++,getIdMatter());
		statement.setLongText(index++,getDescription());
		statement.setLongInteger(index, getMandatory());

		return new Integer(index);
	}

	public void load(int identificador, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresDocumentoTipoAsuntoTabla table = new SicresDocumentoTipoAsuntoTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_cadocs...");
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
						ISicresAdminDAOException.SCR_CADOCS_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_cadocs obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_cadocs");
			else
				logger.error("Error obteniendo datos de scr_cadocs");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setIdMatter(statement.getLongInteger(index++));
		setDescription(statement.getLongText(index++));
		setMandatory(statement.getLongInteger(index++));

		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();

		statement.setLongText(index++,getDescription());

		return new Integer(index);
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresDocumentoTipoAsuntoTabla table = new SicresDocumentoTipoAsuntoTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_cadocs.");
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
				logger.debug("Actualizado scr_cadocs.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_caofic", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CADOCS_UPDATE,
					e);
		}
	}

	public DocumentosTipoAsuntoBean getDocumentosByIdMatter(int idMatter, DbConnection db) throws ISicresAdminDAOException {
		DocumentosTipoAsuntoBean lista = new DocumentosTipoAsuntoBean();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresDocumentoTipoAsuntoTabla table = new SicresDocumentoTipoAsuntoTabla();


		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_caofic");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod(ISicresTabla.GET_TABLES_NAME_METHOD);
			tableInfo.setColumnsMethod(ISicresTabla.GET_COLUMN_NAMES_METHOD);

			rowInfoProcedure.setClassName(SicresDocumentoTipoAsuntoDatos.class
					.getName());
			rowInfoProcedure.setValuesMethod(LOAD_ALL_VALUES_METHOD);
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getByIdMatter(idMatter), false,tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				SicresDocumentoTipoAsuntoDatos documento = (SicresDocumentoTipoAsuntoDatos) rowInfoProcedure.getRow(counter);

				DocumentoTipoAsuntoBean bean = new DocumentoTipoAsuntoBean();
				bean.setId(documento.getId());
				bean.setDescription(documento.getDescription());
				bean.setIdMatter(documento.getIdMatter());
				bean.setMandatory(documento.getMandatory());

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
