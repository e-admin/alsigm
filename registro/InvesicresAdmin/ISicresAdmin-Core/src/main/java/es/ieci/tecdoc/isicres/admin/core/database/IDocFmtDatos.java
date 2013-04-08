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
import es.ieci.tecdoc.isicres.admin.core.beans.IDocFmtImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinasImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class IDocFmtDatos extends IDocFmtImpl {
	private static Logger logger = Logger.getLogger(IDocFmtDatos.class);

	public IDocFmtDatos() {

	}

	public IDocFmtDatos(IDocFmtImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getId());
		statement.setLongText(index++, getName());
		statement.setLongInteger(index++, getArchId());
		statement.setLongInteger(index++, getType());
		statement.setLongInteger(index++, getSubtype());		
		statement.setLongText(index++, getData());
		statement.setLongText(index++, getRemarks());		
		statement.setLongInteger(index++, getAccesstype());
		statement.setLongInteger(index++, getAcsid());
		statement.setLongInteger(index++, getCrtrid());		
		statement.setDateTime(index++, getCrtnDate());
		return new Integer(index);
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		IDocFmtTabla table = new IDocFmtTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo idocfmt...");
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
				logger.debug("idocfmt añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo idocfmt.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.EXC_GENERIC_EXCEPCION,
					e);
		}
	}
}
