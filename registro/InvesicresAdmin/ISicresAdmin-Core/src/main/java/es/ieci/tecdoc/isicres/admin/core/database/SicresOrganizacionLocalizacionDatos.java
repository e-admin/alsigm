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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionLocalizacionImpl;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;

public class SicresOrganizacionLocalizacionDatos extends SicresOrganizacionLocalizacionImpl {
	private static Logger logger = Logger.getLogger(SicresOrganizacionLocalizacionDatos.class);

	public SicresOrganizacionLocalizacionDatos(){
		
	}

	public SicresOrganizacionLocalizacionDatos(SicresOrganizacionLocalizacionImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}
	
	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setIdOrgs(statement.getLongInteger(index++));
		setAddress(statement.getLongText(index++));
		setCity(statement.getLongText(index++));
		setZip(statement.getLongText(index++));
		setCountry(statement.getLongText(index++));
		setTelephone(statement.getLongText(index++));
		setFax(statement.getLongText(index++));
		setEmail(statement.getLongText(index++));
		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getIdOrgs());
		statement.setLongText(index++, getAddress());
		statement.setLongText(index++, getCity());
		statement.setLongText(index++, getZip());
		statement.setLongText(index++, getCountry());
		statement.setLongText(index++, getTelephone());
		statement.setLongText(index++, getFax());
		statement.setLongText(index++, getEmail());		
		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongText(index++, getAddress());
		statement.setLongText(index++, getCity());
		statement.setLongText(index++, getZip());
		statement.setLongText(index++, getCountry());
		statement.setLongText(index++, getTelephone());
		statement.setLongText(index++, getFax());
		statement.setLongText(index++, getEmail());
		return new Integer(index);
	}

	public void load(int identificador, DbConnection db) throws ISicresRPAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOrganizacionLocalizacionTabla table = new SicresOrganizacionLocalizacionTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_dirorgs...");
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

			if (!DynamicFns.select(db, table.getById(identificador),
					tableInfo, rowsInfo)) {
				throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.SCR_DIRORGS_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_dirorgs obtenidos.");
			}
		} catch (Exception e) {
			if(e instanceof ISicresRPAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_dirorgs");
			else
				logger.error("Error obteniendo datos de scr_dirorgs");			
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} 
	}

	public void add(DbConnection db) throws ISicresRPAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOrganizacionLocalizacionTabla table = new SicresOrganizacionLocalizacionTabla();

		if(logger.isDebugEnabled()){
			logger.debug("Añadiendo scr_dirorgs...");
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
				logger.debug("scr_dirorgs añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_dirorgs.", e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.SCR_DIRORGS_INSERT, e);
		}
	}

	public void delete(DbConnection db) throws ISicresRPAdminDAOException {
		SicresOrganizacionLocalizacionTabla table = new SicresOrganizacionLocalizacionTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_dirorgs...");
		}
		try {
			DbDeleteFns.delete(db, table.getTableName(), table
					.getById(getIdOrgs()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_dirorgs.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_dirorgs");			
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.SCR_DIRORGS_DELETE);
		}
	}

	public void update(DbConnection db) throws ISicresRPAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOrganizacionLocalizacionTabla table = new SicresOrganizacionLocalizacionTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_dirorgs.");
			}	

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getUpdateColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("update");
			rowsInfo.add(rowInfo);

			DynamicFns.update(db, table.getById(getIdOrgs()),
					tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizado scr_dirorgs.");
			}	
		} catch (Exception e) {
			logger.error("Error actualizando scr_dirorgs", e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.SCR_DIRORGS_UPDATE);
		}
	}

}
