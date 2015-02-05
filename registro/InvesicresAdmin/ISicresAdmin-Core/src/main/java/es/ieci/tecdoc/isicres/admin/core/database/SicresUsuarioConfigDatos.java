package es.ieci.tecdoc.isicres.admin.core.database;

/*$Id*/


import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputStatement;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputStatement;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRow;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRows;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicTable;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUsuarioConfigImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresUsuarioConfigDatos extends SicresUsuarioConfigImpl {
	
	private static Logger logger = Logger
			.getLogger(SicresUsuarioConfigDatos.class);

	public SicresUsuarioConfigDatos() {

	}

	public SicresUsuarioConfigDatos(SicresUsuarioConfigImpl bean)
			throws Exception {
		BeanUtils.copyProperties(this, bean);
	}
	
	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
	throws Exception {

		int index = idx.intValue();
		
		setUserId(statement.getLongInteger(index++));
		setData(statement.getLongText(index++));
		setIdOficPref(statement.getLongInteger(index++));
		
		return new Integer(index);
	}	
	
	public Integer loadByIdValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setUserId(statement.getLongInteger(index++));
		setIdOficPref(statement.getLongInteger(index++));

		return new Integer(index);
	}  


	public void loadById(int identificador, DbConnection db)
	throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUsuarioConfigTabla table = new SicresUsuarioConfigTabla();
		
		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_userconfig...");
		}
		
		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getLoadByIdColumnNames");
		
			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("loadByIdValues");
			rowsInfo.add(rowInfo);
		
			if (!DynamicFns.select(db, table.getById(identificador),
					tableInfo, rowsInfo)) {
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_USERCONFIG_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_userconfig obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_userconfig");
			else
				logger.error("Error obteniendo datos de scr_userconfig");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Integer insert(DbInputStatement statement, Integer idx)
	throws Exception {

		int index = idx.intValue();

		//Establecer el valor por defecto de la columna Data
		setData(DEFAULT_VALUE_CN_DATA);
		
		
		statement.setLongInteger(index++, getUserId());
		statement.setLongText(index++, getData());
		statement.setLongInteger(index++, getIdOficPref());
		return new Integer(index);
	}	
	
	public Integer update(DbInputStatement statement, Integer idx)
	throws Exception {
		int index = idx.intValue();
		statement.setLongInteger(index++, getIdOficPref());
		return new Integer(index);
	}	
	
	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUsuarioConfigTabla table = new SicresUsuarioConfigTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_userconfig...");
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
				logger.debug("scr_userconfig añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_userconfig.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_USERCONFIG_INSERT,
					e);
		}
	}	
	
	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUsuarioConfigTabla table = new SicresUsuarioConfigTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_userconfig.");
			}

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getUpdateColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("update");
			rowsInfo.add(rowInfo);

			DynamicFns.update(db, table.getById(getUserId()), tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizado scr_userconfig.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_orgs", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_USERCONFIG_UPDATE,
					e);
		}
	}	
	
	
	public void updateByIdOficPref(DbConnection db, int idOficPref) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUsuarioConfigTabla table = new SicresUsuarioConfigTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_userconfig.");
			}

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getUpdateColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("update");
			rowsInfo.add(rowInfo);

			DynamicFns.update(db, table.getByIdOficPref(idOficPref), tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizado scr_userconfig.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_orgs", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_USERCONFIG_UPDATE,
					e);
		}
	}	
}