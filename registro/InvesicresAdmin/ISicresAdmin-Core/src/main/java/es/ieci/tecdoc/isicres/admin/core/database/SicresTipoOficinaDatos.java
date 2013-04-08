package es.ieci.tecdoc.isicres.admin.core.database;

/*$Id*/


import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputStatement;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRow;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRows;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicTable;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresTiposOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresTipoOficinaDatos extends SicresTipoOficinaImpl {
	private static Logger logger = Logger.getLogger(SicresTipoOficinaDatos.class);

	public SicresTipoOficinaDatos() {

	}

	public SicresTipoOficinaDatos(SicresTipoOficinaImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}
	
	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setDescription(statement.getLongText(index++));

		return new Integer(index);
	}

	public static SicresTiposOficinaImpl getTiposOficina(DbConnection db)
			throws ISicresAdminDAOException {
		SicresTiposOficinaImpl tipos = new SicresTiposOficinaImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresTipoOficinaTabla table = new SicresTipoOficinaTabla();
		SicresTipoOficinaDatos tipo;
		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_type_ofic");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfoProcedure.setClassName(SicresTipoOficinaDatos.class.getName());
			rowInfoProcedure.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getOrderByDesc(), false,
					tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				tipo = (SicresTipoOficinaDatos) rowInfoProcedure.getRow(counter);
				tipos.add(tipo);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_typeofic obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_typeofic");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return tipos;
	}
}
