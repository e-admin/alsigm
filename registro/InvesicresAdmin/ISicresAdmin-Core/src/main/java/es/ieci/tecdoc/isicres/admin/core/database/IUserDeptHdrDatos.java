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
import es.ieci.tecdoc.isicres.admin.core.beans.IUserDeptHdrImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class IUserDeptHdrDatos extends IUserDeptHdrImpl {
	private static Logger logger = Logger.getLogger(IUserDeptHdrDatos.class);

	public IUserDeptHdrDatos() {
	}

	public IUserDeptHdrDatos(IUserDeptHdrImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setName(statement.getShortText(index++));
		setParentid(statement.getLongInteger(index++));
		setMgrid(statement.getLongInteger(index++));
		setType(statement.getLongInteger(index++));
		setCrtrid(statement.getLongInteger(index++));
		setCrtndate(statement.getDateTime(index++));
		setUpdrid(statement.getLongInteger(index++));
		setUpddate(statement.getDateTime(index++));

		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
	throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getId());
		statement.setShortText(index++, getName());
		statement.setLongInteger(index++, getParentid());
		statement.setLongInteger(index++, getMgrid());
		statement.setLongInteger(index++, getType());
		statement.setLongInteger(index++, getCrtrid());
		statement.setDateTime(index++, getCrtndate());
		statement.setLongInteger(index++, getUpdrid());
		statement.setDateTime(index++, getUpddate());
		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
	throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getType());
		return new Integer(index);
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		IUserDeptHdrTabla table = new IUserDeptHdrTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo iuserdepthdr...");
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
				logger.debug("scr_tt añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo iuserdepthdr.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.IUSERDEPTHDR_INSERT,e);
		}
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		IUserDeptHdrTabla table = new IUserDeptHdrTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando iuserdepthdr.");
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
				logger.debug("Actualizado iuserdepthdr.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando iuserdepthdr", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.IUSERDEPTHDR_UPDATE, e);
		}
	}

	public void loadByTypeAndCrtId(int type, int crtid, DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		IUserDeptHdrTabla table = new IUserDeptHdrTabla();

		if (logger.isDebugEnabled())
			logger.debug("Obteniendo datos de iuserdepthdr...");

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);

			if (!DynamicFns.select(db, table.getByTypeAndCrtId(type, crtid), tableInfo, rowsInfo))
				throw new ISicresAdminDAOException(ISicresAdminDAOException.IUSERDEPTHDR_NOT_FOUND);

			if (logger.isDebugEnabled())
				logger.debug("Datos de iuserdepthdr obtenidos.");
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en iuserdepthdr");
			else
				logger.error("Error obteniendo datos de iuserdepthdr");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}


	public boolean loadByGuidsGroupAndDeptId(int deptId, String ldapGuidsGroup[], DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		IUserDeptHdrTabla table = new IUserDeptHdrTabla();
		boolean encontrado = false;

		if (logger.isDebugEnabled())
			logger.debug("Obteniendo datos de iuserdepthdr...");

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableNameWithGroup");
			tableInfo.setColumnsMethod("getQualifiedColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);

			if (DynamicFns.select(db, table.getByGuidsGroupAndDeptId(deptId, ldapGuidsGroup), tableInfo, rowsInfo))
				encontrado = true;

			if (logger.isDebugEnabled())
				logger.debug("Datos de iuserdepthdr obtenidos.");
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en iuserdepthdr");
			else
				logger.error("Error obteniendo datos de iuserdepthdr");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return encontrado;
	}

	public void deleteDepartamentoById(DbConnection db, int id) throws ISicresAdminDAOException{
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando iuserdepthdr...");
		}
		try {
			DbDeleteFns
					.delete(db, IUserDeptHdrTabla.TABLE_NAME, new IUserDeptHdrTabla().getById(id));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado iuserdepthdr.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando iuserdepthdr");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.IUSERDEPTHDR_UPDATE);
		}
	}
}