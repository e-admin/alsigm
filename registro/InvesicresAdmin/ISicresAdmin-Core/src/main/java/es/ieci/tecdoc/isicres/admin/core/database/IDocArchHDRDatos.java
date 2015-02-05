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
import es.ieci.tecdoc.isicres.admin.core.beans.IDocArchHDRImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.IDocArchsHDRImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class IDocArchHDRDatos extends IDocArchHDRImpl {
	private static Logger logger = Logger.getLogger(IDocArchHDRDatos.class);

	public IDocArchHDRDatos() {
	}

	public IDocArchHDRDatos(IDocArchHDRImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setName(statement.getShortText(index++));
		setType(statement.getLongInteger(index++));
		setAcsId(statement.getLongInteger(index++));
		setEstadoLibro(statement.getLongInteger(index++));

		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongText(index++, getName());
		return new Integer(index);
	}

	public void load(int identificador, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		IDocArchHDRTabla table = new IDocArchHDRTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de idocarchhdr...");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableNameWithBook");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);

			if (!DynamicFns.select(db, table.getByIdWithBook(identificador), tableInfo,
					rowsInfo)) {
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.IDOCARCHHDR_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de idocarchhdr obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en idocarchhdr");
			else
				logger.error("Error obteniendo datos de idocarchhdr");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		IDocArchHDRTabla table = new IDocArchHDRTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando idocarchhdr.");
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
				logger.debug("Actualizado idocarchhdr.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando idocarchhdr", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.EXC_GENERIC_EXCEPCION,
					e);
		}
	}

	public static IDocArchsHDRImpl getArchivadores(int type, DbConnection db)
			throws ISicresAdminDAOException {
		IDocArchsHDRImpl archivadores = new IDocArchsHDRImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		IDocArchHDRTabla table = new IDocArchHDRTabla();
		IDocArchHDRDatos archivador;
		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de idocarchhdr");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableNameWithBook");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfoProcedure.setClassName(IDocArchHDRDatos.class.getName());
			rowInfoProcedure.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfoProcedure);

			StringBuffer sbQual = new StringBuffer(table.getByTypeWithBook(type))
					.append(table.getOrderByDesc());

			DynamicFns.selectMultiple(db, sbQual.toString(), false, tableInfo,
					rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				archivador = (IDocArchHDRDatos) rowInfoProcedure
						.getRow(counter);
				archivadores.add(archivador);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de idocarchhdr obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de idocarchhdr");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return archivadores;
	}
}
