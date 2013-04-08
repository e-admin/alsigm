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
import es.ieci.tecdoc.isicres.admin.core.beans.IVolArchListImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class IVolArchListDatos extends IVolArchListImpl {
	private static Logger logger = Logger.getLogger(IVolArchListDatos.class);

	public IVolArchListDatos(){
		
	}

	public IVolArchListDatos(IVolArchListImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}
	
	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setArchId(statement.getLongInteger(index++));
		setListId(statement.getLongInteger(index++));
		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getArchId());
		statement.setLongInteger(index++, getListId());
		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getListId());
		return new Integer(index);
	}

	public void load(int identificador, DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		IVolArchListTabla table = new IVolArchListTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de ivolarchlist...");
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
				throw new ISicresAdminDAOException(ISicresAdminDAOException.IVOLARCHLIST_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de ivolarchlist obtenidos.");
			}
		} catch (Exception e) {
			if(e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en ivolarchlist");
			else
				logger.error("Error obteniendo datos de ivolarchlist");			
			throw new ISicresAdminDAOException(ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} 
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		IVolArchListTabla table = new IVolArchListTabla();

		if(logger.isDebugEnabled()){
			logger.debug("Añadiendo ivolarchlist...");
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
			
			//No me molesto mucho, estos daos en teoría pasarán a mejor vida.
			StringBuffer sb = new StringBuffer("UPDATE idocarchdet SET detval='\"01.01\"|\"\"|1,")
				.append(getListId()).append("' WHERE dettype=2 AND archid=").append(getArchId());
			db.getJdbcConnection().createStatement().execute(sb.toString());
			if(logger.isDebugEnabled()){
				logger.debug("ivolarchlist añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo ivolarchlist.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.IVOLARCHLIST_INSERT, e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		IVolArchListTabla table = new IVolArchListTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando ivolarchlist...");
		}
		try {
			DbDeleteFns.delete(db, table.getTableName(), table
					.getById(getArchId()));
			//No me molesto mucho, estos daos en teoría pasarán a mejor vida.
			StringBuffer sb = new StringBuffer("UPDATE idocarchdet SET detval='\"01.01\"|\"\"|0")
				.append(" WHERE dettype=2 AND archid=").append(getArchId());
			db.getJdbcConnection().createStatement().execute(sb.toString());			
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado ivolarchlist.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando ivolarchlist");			
			throw new ISicresAdminDAOException(ISicresAdminDAOException.IVOLARCHLIST_DELETE);
		}
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		IVolArchListTabla table = new IVolArchListTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando ivolarchlist.");
			}	

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getUpdateColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("update");
			rowsInfo.add(rowInfo);

			DynamicFns.update(db, table.getById(getArchId()),
					tableInfo, rowsInfo);
			//No me molesto mucho, estos daos en teoría pasarán a mejor vida.
			StringBuffer sb = new StringBuffer("UPDATE idocarchdet SET detval='\"01.01\"|\"\"|1,")
				.append(getListId()).append("' WHERE dettype=2 AND archid=").append(getArchId());
			db.getJdbcConnection().createStatement().execute(sb.toString());
			
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizado ivolarchlist.");
			}	
		} catch (Exception e) {
			logger.error("Error actualizando ivolarchlist", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.IVOLARCHLIST_UPDATE);
		}
	}

}
