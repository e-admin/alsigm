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
import es.ieci.tecdoc.isicres.admin.core.beans.ListaUsuariosOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUsuarioOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresUsuarioOficinaDatos extends SicresUsuarioOficinaImpl{
	private static Logger logger = Logger.getLogger(SicresUsuarioOficinaDatos.class);

	public SicresUsuarioOficinaDatos() {

	}

	public SicresUsuarioOficinaDatos(SicresUsuarioOficinaImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}
	
	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setIdUser(statement.getLongInteger(index++));
		setIdOfic(statement.getLongInteger(index++));
		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		statement.setLongInteger(index++, getId());
		statement.setLongInteger(index++, getIdUser());
		statement.setLongInteger(index++, getIdOfic());
		return new Integer(index);
	}

	public void load(int idUser, int idOfic, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUsuarioOficinaTabla table = new SicresUsuarioOficinaTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_usrofic...");
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

			if (!DynamicFns.select(db, table.getByIds(idUser, idOfic), tableInfo,
					rowsInfo)) {
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_USEROFIC_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_usrofic obtenidos.");
			}
		} catch (Exception e) {
			if(e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_usrofic");
			else
				logger.error("Error obteniendo datos de scr_usrofic");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public ListaUsuariosOficinaImpl getUsuariosOficina(int idOfic, DbConnection db) throws ISicresAdminDAOException {
		ListaUsuariosOficinaImpl usersOfic = new ListaUsuariosOficinaImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUsuarioOficinaTabla table = new SicresUsuarioOficinaTabla();
		SicresUsuarioOficinaDatos userOfic;
		int counter, size;
		
		if (logger.isDebugEnabled())
			logger.debug("Obteniendo datos de scr_usrofic...");
				
		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");
		
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);
		
			DynamicFns.selectMultiple(db, table.getByOficina(idOfic), false, tableInfo, rowsInfo);
			size = rowInfo.getRowCount();
		
			for (counter = 0; counter < size; counter++) {
				userOfic = (SicresUsuarioOficinaDatos) rowInfo.getRow(counter);
				usersOfic.add(userOfic);
			}
			if (logger.isDebugEnabled()) 
				logger.debug("Datos de scr_ofic obtenidos");			
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_ofic");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}		
		return usersOfic;
	}
	
	/**
	 * Obtiene usuarios agregados que pertenecen a alguna de las oficinas pasadas como parametro y que son diferentes
	 * a los distintos usuarios que se pasan (que ya son parte de la oficina sin estar agregados).
	 */
	public ListaUsuariosOficinaImpl getAgregadosOficinas(int idsOfic[], int idsUser[], DbConnection db) throws ISicresAdminDAOException {
		ListaUsuariosOficinaImpl usersOfic = new ListaUsuariosOficinaImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUsuarioOficinaTabla table = new SicresUsuarioOficinaTabla();
		SicresUsuarioOficinaDatos userOfic;
		int counter, size;
		
		if (logger.isDebugEnabled())
			logger.debug("Obteniendo datos de scr_usrofic...");
				
		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");
		
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);
		
			DynamicFns.selectMultiple(db, table.getByIdsOficAndNotIdsUser(idsOfic, idsUser), false, tableInfo, rowsInfo);
			size = rowInfo.getRowCount();
		
			for (counter = 0; counter < size; counter++) {
				userOfic = (SicresUsuarioOficinaDatos) rowInfo.getRow(counter);
				usersOfic.add(userOfic);
			}
			if (logger.isDebugEnabled()) 
				logger.debug("Datos de scr_ofic obtenidos");			
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_ofic");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}		
		return usersOfic;
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresUsuarioOficinaTabla table = new SicresUsuarioOficinaTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_usrofic...");
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
				logger.debug("scr_usrofic añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_usrofic.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_USEROFIC_INSERT,
					e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresUsuarioOficinaTabla table = new SicresUsuarioOficinaTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_usrofic...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getByIds(getIdUser(), getIdOfic()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_usrofic.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_usrofic");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_USEROFIC_DELETE);
		}
	}

	public static void deleteUser(int idUser, DbConnection db) throws ISicresAdminDAOException {
		SicresUsuarioOficinaTabla table = new SicresUsuarioOficinaTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_usrofic...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getByUser(idUser));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_usrofic.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_usrofic");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_USEROFIC_DELETE);
		}
	}

	public static void deleteOficina(int idOfic, DbConnection db) throws ISicresAdminDAOException {
		SicresUsuarioOficinaTabla table = new SicresUsuarioOficinaTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_usrofic...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getByOficina(idOfic));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_usrofic.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_usrofic");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_USEROFIC_DELETE);
		}
	}
}
