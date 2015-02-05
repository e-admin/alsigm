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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaLocalizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresOficinaLocalizacionDatos extends SicresOficinaLocalizacionImpl {
	private static Logger logger = Logger.getLogger(SicresOficinaLocalizacionDatos.class);

	public SicresOficinaLocalizacionDatos(){
		
	}

	public SicresOficinaLocalizacionDatos(SicresOficinaLocalizacionImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}
	
	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setIdOfic(statement.getLongInteger(index++));
		setAddress(statement.getLongText(index++));
		setCity(statement.getLongText(index++));
		setZip(statement.getLongText(index++));
		setCountry(statement.getLongText(index++));
		setTelephone(statement.getLongText(index++));
		setFax(statement.getLongText(index++));
		setEmail(statement.getLongText(index++));
		setSigner(statement.getLongText(index++));		
		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getIdOfic());
		statement.setLongText(index++, getAddress());
		statement.setLongText(index++, getCity());
		statement.setLongText(index++, getZip());
		statement.setLongText(index++, getCountry());
		statement.setLongText(index++, getTelephone());
		statement.setLongText(index++, getFax());
		statement.setLongText(index++, getEmail());
		statement.setLongText(index++, getSigner());		
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
		statement.setLongText(index++, getSigner());		
		return new Integer(index);
	}

	public void load(int identificador, DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOficinaLocalizacionTabla table = new SicresOficinaLocalizacionTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_dirofic...");
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
				throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_DIROFIC_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_dirofic obtenidos.");
			}
		} catch (Exception e) {
			if(e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_dirofic");
			else
				logger.error("Error obteniendo datos de scr_dirofic");			
			throw new ISicresAdminDAOException(ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} 
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOficinaLocalizacionTabla table = new SicresOficinaLocalizacionTabla();

		if(logger.isDebugEnabled()){
			logger.debug("Añadiendo scr_dirofic...");
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
				logger.debug("scr_dirofic añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_dirofic.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_DIROFIC_INSERT, e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresOficinaLocalizacionTabla table = new SicresOficinaLocalizacionTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_dirofic...");
		}
		try {
			DbDeleteFns.delete(db, table.getTableName(), table
					.getById(getIdOfic()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_dirofic.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_dirofic");			
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_DIROFIC_DELETE);
		}
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOficinaLocalizacionTabla table = new SicresOficinaLocalizacionTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_dirofic.");
			}	

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getUpdateColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("update");
			rowsInfo.add(rowInfo);

			DynamicFns.update(db, table.getById(getIdOfic()),
					tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizado scr_dirofic.");
			}	
		} catch (Exception e) {
			logger.error("Error actualizando scr_dirofic", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_DIROFIC_UPDATE);
		}
	}

}
