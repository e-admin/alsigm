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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUserFilterImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUserLocalizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresUserFilterDatos extends SicresUserFilterImpl {
	private static Logger logger = Logger.getLogger(SicresUserFilterDatos.class);

	public SicresUserFilterDatos(){
		
	}

	public SicresUserFilterDatos(SicresUserLocalizacionImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}
	
	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setIdArch(statement.getLongInteger(index++));
		setIdUser(statement.getLongInteger(index++));
		setFilterDef(statement.getLongText(index++));
		setTypeObj(statement.getLongInteger(index++));
		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getIdArch());
		statement.setLongInteger(index++, getIdUser());
		statement.setLongText(index++, getFilterDef());
		statement.setLongInteger(index++, getTypeObj());
		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongText(index++, getFilterDef());
		return new Integer(index);
	}

	public void load(int idArch, int idUser, int typeObj, DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUserFilterTabla table = new SicresUserFilterTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_userfilter...");
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

			if (!DynamicFns.select(db, table.getByIds(idArch, idUser, typeObj),
					tableInfo, rowsInfo,false)) {
				throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_USERFILTER_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_userfilter obtenidos.");
			}
		} catch (Exception e) {
			if(e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_userfilter");
			else
				logger.error("Error obteniendo datos de scr_userfilter");			
			throw new ISicresAdminDAOException(ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} 
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUserFilterTabla table = new SicresUserFilterTabla();

		if(logger.isDebugEnabled()){
			logger.debug("Añadiendo scr_userfilter...");
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
			if(logger.isDebugEnabled()){
				logger.debug("scr_userfilter añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_userfilter.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_USERFILTER_INSERT, e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresUserFilterTabla table = new SicresUserFilterTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_userfilter...");
		}
		try {
			DbDeleteFns.delete(db, table.getTableName(), table
					.getByIds(getIdArch(), getIdUser(), getTypeObj()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_userfilter.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_userfilter");			
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_USERFILTER_DELETE);
		}
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUserFilterTabla table = new SicresUserFilterTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_userfilter.");
			}	

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getUpdateColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("update");
			rowsInfo.add(rowInfo);

			DynamicFns.update(db, table.getByIds(getIdArch(), getIdUser(), getTypeObj()),
					tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizado scr_userfilter.");
			}	
		} catch (Exception e) {
			logger.error("Error actualizando scr_userfilter", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_USERFILTER_UPDATE);
		}
	}

}
