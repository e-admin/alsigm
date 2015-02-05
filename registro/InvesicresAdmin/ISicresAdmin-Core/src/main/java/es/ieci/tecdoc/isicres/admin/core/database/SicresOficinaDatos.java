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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinasImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresOficinaDatos extends SicresOficinaImpl {
	private static Logger logger = Logger.getLogger(SicresOficinaDatos.class);

	public SicresOficinaDatos() {

	}

	public SicresOficinaDatos(SicresOficinaImpl bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setCode(statement.getLongText(index++));
		setAcron(statement.getLongText(index++));
		setName(statement.getLongText(index++));
		setCreationDate(statement.getDateTime(index++));
		setDisableDate(statement.getDateTime(index++));
		setIdOrgs(statement.getLongInteger(index++));
		setStamp(statement.getLongText(index++));
		setDeptId(statement.getLongInteger(index++));
		setType(statement.getLongInteger(index++));
		return new Integer(index);
	}
	
	public Integer loadGroupDnValue(DbOutputStatement statement, Integer idx)	throws Exception {
		
		int index = idx.intValue();		
		setGroupDn(statement.getShortText(index++));
		return new Integer(index);
	}
	
	public Integer loadAllValuesLdap(DbOutputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setCode(statement.getShortText(index++));
		setName(statement.getLongText(index++));
		setAcron(statement.getShortText(index++));
		setCreationDate(statement.getDateTime(index++));
		setDisableDate(statement.getDateTime(index++));

		return new Integer(index);
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();

		statement.setLongInteger(index++, getId());
		statement.setLongText(index++, getCode());
		statement.setLongText(index++, getAcron());
		statement.setLongText(index++, getName());
		statement.setDateTime(index++, getCreationDate());
		statement.setDateTime(index++, getDisableDate());
		statement.setLongInteger(index++, getIdOrgs());
		statement.setLongText(index++, getStamp());
		statement.setLongInteger(index++, getDeptId());
		statement.setLongInteger(index++, getType());
		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongText(index++, getCode());
		statement.setLongText(index++, getAcron());
		statement.setLongText(index++, getName());
		statement.setDateTime(index++, getCreationDate());
		statement.setDateTime(index++, getDisableDate());
		statement.setLongInteger(index++, getIdOrgs());
		statement.setLongText(index++, getStamp());
		statement.setLongInteger(index++, getDeptId());
		statement.setLongInteger(index++, getType());
		return new Integer(index);
	}

	public void load(int identificador, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOficinaTabla table = new SicresOficinaTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_ofic...");
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

			if (!DynamicFns.select(db, table.getById(identificador), tableInfo,
					rowsInfo)) {
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_OFIC_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_ofic obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_ofic");
			else
				logger.error("Error obteniendo datos de scr_ofic");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void load(String code, DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOficinaTabla table = new SicresOficinaTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_ofic...");
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

			if (!DynamicFns.select(db, table.getByCode(code), tableInfo,
					rowsInfo)) {
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_OFIC_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_ofic obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_ofic");
			else
				logger.error("Error obteniendo datos de scr_ofic");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void loadDeptLdap(String ldapGuid, DbConnection db)
	throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOficinaTabla table = new SicresOficinaTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_ofic...");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getQualifiedTableName");
			tableInfo.setColumnsMethod("getQualifiedColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);

			if (!DynamicFns.select(db, table.getByLdapGuid(ldapGuid),
					tableInfo, rowsInfo)) {
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_OFIC_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_ofic obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_ofic");
			else
				logger.error("Error obteniendo datos de scr_ofic");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	public void loadDeptId(int identificador, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOficinaTabla table = new SicresOficinaTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_ofic...");
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

			if (!DynamicFns.select(db, table.getByDeptId(identificador),
					tableInfo, rowsInfo)) {
				throw new ISicresAdminDAOException(
						ISicresAdminDAOException.SCR_OFIC_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_ofic obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_ofic");
			else
				logger.error("Error obteniendo datos de scr_ofic");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	
	public SicresOficinasImpl loadDeptsId(int ids[], DbConnection db) throws ISicresAdminDAOException {
		SicresOficinasImpl usersOfic = new SicresOficinasImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOficinaTabla table = new SicresOficinaTabla();
		SicresOficinaImpl userOfic;
		int counter, size;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_ofic...");
		}
		
		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");
		
			rowInfo.setClassName(this.getClass().getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);
		
			DynamicFns.selectMultiple(db, table.getByDeptsId(ids),false, tableInfo, rowsInfo);
			size = rowInfo.getRowCount();
			
			for (counter = 0; counter < size; counter++) {
				userOfic = (SicresOficinaImpl) rowInfo.getRow(counter);
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
		SicresOficinaTabla table = new SicresOficinaTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_ofic...");
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
				logger.debug("scr_ofic añadida.");
			}
		} catch (Exception e) {
			logger.error("Error añadiendo scr_ofic.", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_OFIC_INSERT,
					e);
		}
	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresOficinaTabla table = new SicresOficinaTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_ofic...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getById(getId()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_ofic.");
			}
		} catch (Exception e) {
			logger.error("Error eliminando scr_ofic");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_OFIC_DELETE);
		}
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresOficinaTabla table = new SicresOficinaTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_ofic.");
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
				logger.debug("Actualizado scr_ofic.");
			}
		} catch (Exception e) {
			logger.error("Error actualizando scr_ofic", e);
			throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_OFIC_UPDATE,
					e);
		}
	}

	public static SicresOficinasImpl getOficinas(DbConnection db)
			throws ISicresAdminDAOException {
		SicresOficinasImpl oficinas = new SicresOficinasImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresOficinaTabla table = new SicresOficinaTabla();
		SicresOficinaDatos oficina;
		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_ofic");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfoProcedure.setClassName(SicresOficinaDatos.class.getName());
			rowInfoProcedure.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getOrderByDesc(), false,
					tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				oficina = (SicresOficinaDatos) rowInfoProcedure.getRow(counter);
				oficinas.add(oficina);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_ofic obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_ofic");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return oficinas;
	}
	
	public static SicresOficinasImpl getOficinasDesasociadasALibro(int idBook,
			DbConnection db) throws ISicresAdminDAOException {
		SicresOficinasImpl oficinas = new SicresOficinasImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresOficinaTabla table = new SicresOficinaTabla();
		SicresOficinaDatos oficina;
		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_lista_oficinas");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfoProcedure.setClassName(SicresOficinaDatos.class.getName());
			rowInfoProcedure.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfoProcedure);

			StringBuffer sb = new StringBuffer(table
					.getByDesasociadasALibro(idBook));
			sb.append(table.getOrderByDesc());
			DynamicFns.selectMultiple(db, sb.toString(), false, tableInfo,
					rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				oficina = (SicresOficinaDatos) rowInfoProcedure.getRow(counter);
				oficinas.add(oficina);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_lista_oficinas obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_lista_oficinas");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return oficinas;
	}
	
	public static SicresOficinasImpl getOficinasDesagregadasAUsuario(int idUser,
			DbConnection db) throws ISicresAdminDAOException {
		SicresOficinasImpl oficinas = new SicresOficinasImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresOficinaTabla table = new SicresOficinaTabla();
		SicresOficinaDatos oficina;
		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_lista_oficinas");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfoProcedure.setClassName(SicresOficinaDatos.class.getName());
			rowInfoProcedure.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfoProcedure);

			StringBuffer sb = new StringBuffer(table
					.getOficinasDesasociadasByAgregateUser(idUser));
			sb.append(table.getOrderByDesc());
			DynamicFns.selectMultiple(db, sb.toString(), false, tableInfo,
					rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				oficina = (SicresOficinaDatos) rowInfoProcedure.getRow(counter);
				oficinas.add(oficina);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_lista_oficinas obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_lista_oficinas");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return oficinas;
	}
	
	public static SicresOficinasImpl getOficinasAgregadasAUsuario(int idUser,
			DbConnection db) throws ISicresAdminDAOException {
		SicresOficinasImpl oficinas = new SicresOficinasImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresOficinaTabla table = new SicresOficinaTabla();
		SicresOficinaDatos oficina;
		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_lista_oficinas");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableNameWithAgregateUsers");
			tableInfo.setColumnsMethod("getQualifiedColumnNames");

			rowInfoProcedure.setClassName(SicresOficinaDatos.class.getName());
			rowInfoProcedure.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfoProcedure);

			StringBuffer sb = new StringBuffer(table
					.getByAgregateUser(idUser));
			sb.append(table.getOrderByDesc());
			DynamicFns.selectMultiple(db, sb.toString(), false, tableInfo,
					rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				oficina = (SicresOficinaDatos) rowInfoProcedure.getRow(counter);
				oficinas.add(oficina);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_lista_oficinas obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_lista_oficinas");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return oficinas;
	}
	
	public static SicresOficinasImpl getOficinasDesasociadasUsuarioLdap(int idUser, 
			int idsGroup[], DbConnection db) throws ISicresAdminDAOException {
		SicresOficinasImpl oficinas = new SicresOficinasImpl();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresOficinaTabla table = new SicresOficinaTabla();
		SicresOficinaDatos oficina;
		int counter, size;

		if (logger.isDebugEnabled())
			logger.debug("Obteniendo datos de scr_lista_oficinas");
		
		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableNameWithGroupLdap");
			tableInfo.setColumnsMethod("getAllColumnNamesLdap");

			rowInfoProcedure.setClassName(SicresOficinaDatos.class.getName());
			rowInfoProcedure.setValuesMethod("loadAllValuesLdap");
			rowsInfo.add(rowInfoProcedure);

			StringBuffer sb = new StringBuffer(table.getOficinasDesasociadasUsuario(idUser, idsGroup));
			sb.append(table.getOrderByDesc());
			DynamicFns.selectMultiple(db, sb.toString(), false, tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				oficina = (SicresOficinaDatos) rowInfoProcedure.getRow(counter);
				oficinas.add(oficina);
			}
			if (logger.isDebugEnabled()) 
				logger.debug("Datos de scr_lista_oficinas obtenidos");			
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_lista_oficinas");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinas;
	}
	
	
}
