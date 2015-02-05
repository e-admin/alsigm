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
import es.ieci.tecdoc.isicres.admin.core.beans.IUserDeptHdrImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.IUserLdapUserHdrImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.ListaUsersLdapUserHdrImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class IUserLdapUserHdrDatos extends IUserLdapUserHdrImpl {
	private static Logger logger = Logger.getLogger(IUserLdapUserHdrDatos.class);

	public IUserLdapUserHdrDatos() {
	}

	public IUserLdapUserHdrDatos(IUserDeptHdrImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setLdapguid(statement.getShortText(index++));
		setLdapfullname(statement.getShortText(index++));
		
		return new Integer(index);
	}
	
	public Integer insert(DbInputStatement statement, Integer idx)
	throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getId());
		statement.setShortText(index++, getLdapguid());
		statement.setShortText(index++, getLdapfullname());
		return new Integer(index);
	}
	
	
	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		IUserLdapUserHdrTabla table = new IUserLdapUserHdrTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo iuserldapuserhdr...");
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
				logger.debug("iuserldapuserhdr añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo iuserldapuserhdr.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.IUSERLDAPUSERHDR_INSERT,e);
		}
	}
	
	public ListaUsersLdapUserHdrImpl load(DbConnection db) throws ISicresAdminDAOException {
		ListaUsersLdapUserHdrImpl usersLdap = new ListaUsersLdapUserHdrImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		IUserLdapUserHdrTabla table = new IUserLdapUserHdrTabla();
		IUserLdapUserHdrImpl userLdap;
		int counter, size;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de iuserldapuserhdr...");
		}
		
		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");
		
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);
		
			DynamicFns.selectMultiple(db, null, false, tableInfo, rowsInfo);
			size = rowInfo.getRowCount();
			
			for (counter = 0; counter < size; counter++) {
				userLdap = (IUserLdapUserHdrImpl) rowInfo.getRow(counter);
				usersLdap.add(userLdap);
			}
			if (logger.isDebugEnabled()) 
				logger.debug("Datos de iuserldapuserhdr obtenidos");	
		} catch (Exception e) {
			logger.error("Error obteniendo datos de iuserldapuserhdr");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usersLdap;
	}
	
	public void getUserLdapByGuid(String ldapguid, DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		IUserLdapUserHdrTabla table = new IUserLdapUserHdrTabla();

		if (logger.isDebugEnabled())
			logger.debug("Obteniendo datos de iuserldapuserhdr...");
		
		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);

			if (!DynamicFns.select(db, table.getByGuid(ldapguid), tableInfo,	rowsInfo))
				throw new ISicresAdminDAOException(ISicresAdminDAOException.IUSERLDAPUSERHDR_NOT_FOUND);
			
			if (logger.isDebugEnabled()) 
				logger.debug("Datos de iuserldapuserhdr obtenidos.");			
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en iuserldapuserhdr");
			else
				logger.error("Error obteniendo datos de iuserldapuserhdr");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}
}