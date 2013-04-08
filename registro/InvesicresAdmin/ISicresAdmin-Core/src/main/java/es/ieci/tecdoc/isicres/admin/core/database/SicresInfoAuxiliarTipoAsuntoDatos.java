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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresInfoAuxiliarTipoAsuntoImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresInfoAuxiliarTipoAsuntoDatos extends SicresInfoAuxiliarTipoAsuntoImpl implements ISicresDatos {
	private static Logger logger = Logger.getLogger(SicresInfoAuxiliarTipoAsuntoDatos.class);

	public SicresInfoAuxiliarTipoAsuntoDatos() {

	}

	public SicresInfoAuxiliarTipoAsuntoDatos(SicresInfoAuxiliarTipoAsuntoImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}
	
	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresInfoAuxiliarTipoAsuntoTabla table = new SicresInfoAuxiliarTipoAsuntoTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_caux...");
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
				logger.debug("scr_caaux añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_caaux.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CAAUX_INSERT,e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresInfoAuxiliarTipoAsuntoTabla table = new SicresInfoAuxiliarTipoAsuntoTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_caaux...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getById(getId()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_caaux.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_caaux");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CAAUX_DELETE);
		}
	}
	
	public void deleteByIdMatter(DbConnection db) throws ISicresAdminDAOException {
		SicresInfoAuxiliarTipoAsuntoTabla table = new SicresInfoAuxiliarTipoAsuntoTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_caaux con idAsunto="+ getIdMatter() +"...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getByIdMatter(getIdMatter()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_caaux.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_caaux");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CAAUX_DELETE);
		}
	}
	
	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();

		statement.setLongInteger(index++, getId());
		statement.setLongInteger(index++,getIdMatter());
		statement.setLongText(index++,getDatosAux());

		return new Integer(index);
	}

	public void load(int identificador, DbConnection db) throws ISicresAdminDAOException{
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresInfoAuxiliarTipoAsuntoTabla table = new SicresInfoAuxiliarTipoAsuntoTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_caaux...");
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
					rowsInfo, false)) {
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_CAAUX_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_caaux obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_caaux");
			else
				logger.error("Error obteniendo datos de scr_caaux");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}		
	}
	
	public void loadByIdMatter(int idMatter, DbConnection db)throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresInfoAuxiliarTipoAsuntoTabla table = new SicresInfoAuxiliarTipoAsuntoTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_caaux...");
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

			if (!DynamicFns.select(db, table.getByIdMatter(idMatter), tableInfo,
					rowsInfo, false)) {
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_CAAUX_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_caaux obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.debug("No se ha encontrado fila en scr_caaux");
			else{
				logger.error("Error obteniendo datos de scr_caaux");
				throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
			}
		}
	}	

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setIdMatter(statement.getLongInteger(index++));
		setDatosAux(statement.getLongText(index++));
		
		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {
		
		int index = idx.intValue();
		statement.setLongInteger(index++, getId());
		statement.setLongInteger(index++,getIdMatter());
		statement.setLongText(index++,getDatosAux());
		
		return new Integer(index);
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresInfoAuxiliarTipoAsuntoTabla table = new SicresInfoAuxiliarTipoAsuntoTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_caaux.");
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
				logger.debug("Actualizado scr_caaux.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_caaux", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CAAUX_UPDATE,
					e);
		}
	}


}
