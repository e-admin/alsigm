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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresLibrosOficinasImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresLibroOficinaDatos extends SicresLibroOficinaImpl {
	private static Logger logger = Logger
			.getLogger(SicresLibroOficinaDatos.class);

	public SicresLibroOficinaDatos() {

	}

	public SicresLibroOficinaDatos(SicresLibroOficinaImpl bean)
			throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setIdBook(statement.getLongInteger(index++));
		setIdOfic(statement.getLongInteger(index++));
		setNumeration(statement.getLongInteger(index++));
		return new Integer(index);
	}

	public Integer loadAllValuesWithOficina(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setIdBook(statement.getLongInteger(index++));
		setIdOfic(statement.getLongInteger(index++));
		setNumeration(statement.getLongInteger(index++));
		setNameOfic(statement.getShortText(index++));
		setDeptId(statement.getLongInteger(index++));
		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		statement.setLongInteger(index++, getId());
		statement.setLongInteger(index++, getIdBook());
		statement.setLongInteger(index++, getIdOfic());
		statement.setLongInteger(index++, getNumeration());
		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getNumeration());
		return new Integer(index);
	}

	public void load(int bookId, int oficId, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresLibroOficinaTabla table = new SicresLibroOficinaTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_bookofic...");
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

			if (!DynamicFns.select(db, table.getByIds(bookId, oficId),
					tableInfo, rowsInfo)) {
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_BOOKOFIC_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_bookofic obtenidos.");
			}
		} catch (Exception e) {
			if(e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_bookofic");
			else
				logger.error("Error obteniendo datos de scr_bookofic");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresLibroOficinaTabla table = new SicresLibroOficinaTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_bookofic...");
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
				logger.debug("scr_bookofic añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_bookofic.", e);
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.SCR_BOOKOFIC_INSERT, e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresLibroOficinaTabla table = new SicresLibroOficinaTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_bookofic...");
		}
		try {
			DbDeleteFns.delete(db, table.getTableName(), table.getByIds(
					getIdBook(), getIdOfic()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_bookofic.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_bookofic");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.SCR_BOOKOFIC_DELETE);
		}
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresLibroOficinaTabla table = new SicresLibroOficinaTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_bookofic.");
			}

			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getUpdateColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("update");
			rowsInfo.add(rowInfo);

			DynamicFns.update(db, table.getByIds(getIdBook(), getIdOfic()),
					tableInfo, rowsInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizado scr_bookofic.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_bookofic", e);
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.SCR_BOOKOFIC_UPDATE, e);
		}
	}

	public static void deleteOficina(int idOfic, DbConnection db) throws ISicresAdminDAOException {
		SicresLibroOficinaTabla table = new SicresLibroOficinaTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_bookofic...");
		}
		try {
			DbDeleteFns.delete(db, table.getTableName(), table.getByOficina(idOfic));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_bookofic.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_bookofic");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.SCR_BOOKOFIC_DELETE);
		}
	}

	public static void deleteLibro(int idLibro, DbConnection db) throws ISicresAdminDAOException {
		SicresLibroOficinaTabla table = new SicresLibroOficinaTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_bookofic...");
		}
		try {
			DbDeleteFns.delete(db, table.getTableName(), table.getByLibro(idLibro));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_bookofic.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_bookofic");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.SCR_BOOKOFIC_DELETE);
		}
	}

	public static SicresLibrosOficinasImpl getOficinas(int bookId,
			DbConnection db) throws ISicresAdminDAOException {
		SicresLibrosOficinasImpl lista = new SicresLibrosOficinasImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresLibroOficinaTabla table = new SicresLibroOficinaTabla();
		SicresLibroOficinaDatos bean;
		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_bookofic");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableNameWithOficina");
			tableInfo.setColumnsMethod("getAllColumnNamesWithOficina");

			rowInfoProcedure.setClassName(SicresLibroOficinaDatos.class
					.getName());
			rowInfoProcedure.setValuesMethod("loadAllValuesWithOficina");
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getOficinaByBook(bookId), false,
					tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				bean = (SicresLibroOficinaDatos) rowInfoProcedure
						.getRow(counter);
				lista.add(bean);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_bookofic obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_bookofic");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return lista;
	}

}
