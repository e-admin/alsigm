package es.ieci.tecdoc.isicres.admin.core.database;

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
import es.ieci.tecdoc.isicres.admin.beans.TipoAsunto;
import es.ieci.tecdoc.isicres.admin.beans.TiposAsuntoBean;
import es.ieci.tecdoc.isicres.admin.core.UtilsBD;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public class SicresTipoAsuntoDatos extends TipoAsunto{
	private static Logger logger = Logger.getLogger(SicresTipoAsuntoDatos.class);


	public SicresTipoAsuntoDatos() {}

	public SicresTipoAsuntoDatos(TipoAsunto bean) throws Exception {
		BeanUtils.copyProperties(this, bean);
	}

	public void add(DbConnection db) throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresTipoAsuntoTabla table = new SicresTipoAsuntoTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Añadiendo scr_ca...");
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
				logger.debug("scr_ca añadida.");
			}
		} catch (Exception e) {

			//TODO Hacer esto de los errores de foreing key de forma generica
			if(UtilsBD.isErrorDuplicateKey(e.getMessage())){
				logger.error("Error eliminando scr_ca. Error foreing key");
				throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CA_UNIQUE_KEY_ERROR);
			}
			else{
				logger.error("Error añadiendo scr_ca.", e);
				throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CA_INSERT);
			}
		}

	}

	public void delete(DbConnection db) throws ISicresAdminDAOException {
		SicresTipoAsuntoTabla table = new SicresTipoAsuntoTabla();
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando scr_ca...");
		}
		try {
			DbDeleteFns
					.delete(db, table.getTableName(), table.getById(getId()));
			if (logger.isDebugEnabled()) {
				logger.debug("Eliminado scr_ca.");
			}
		} catch (Exception e) {

			//TODO Hacer esto de los errores de foreing key de forma generica
			if(UtilsBD.isErrorForeignKey(e.getMessage())){
				logger.error("Error eliminando scr_ca. Error foreing key");
				throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CA_DELETE_FOREIGN_KEY_ERROR);
			}
			else{
				logger.error("Error eliminando scr_ca");
				throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CA_DELETE);
			}
		}
	}

	public Integer insert(DbInputStatement statement, Integer idx)
			throws Exception {

		int index = idx.intValue();
		statement.setLongInteger(index++, getId());
		statement.setLongText(index++,getCode().toUpperCase());
		statement.setLongText(index++,getMatter());
		statement.setLongInteger(index++,getForEreg());
		statement.setLongInteger(index++,getForSreg());
		statement.setLongInteger(index++,getAllOfics());
		statement.setLongInteger(index++,getIdArch());
		statement.setDateTime(index++,getCreationDate());
		statement.setDateTime(index++,getDisableDate());
		statement.setLongInteger(index++,getEnabled());
		statement.setLongInteger(index++,getIdOrg());

		return new Integer(index);
	}

	public void load(int identificador, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresTipoAsuntoTabla table = new SicresTipoAsuntoTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_ca...");
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
						ISicresAdminDAOException.SCR_CA_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_ca obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_ca");
			else
				logger.error("Error obteniendo datos de scr_ca");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public void load(String code, DbConnection db)
			throws ISicresAdminDAOException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresTipoAsuntoTabla table = new SicresTipoAsuntoTabla();

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_ca...");
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
						ISicresAdminDAOException.SCR_CA_NOT_FOUND);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_ca obtenidos.");
			}
		} catch (Exception e) {
			if (e instanceof ISicresAdminDAOException)
				logger.warn("No se ha encontrado fila en scr_ca");
			else
				logger.error("Error obteniendo datos de scr_ca");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public Integer loadAllValues(DbOutputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();

		setId(statement.getLongInteger(index++));
		setCode(statement.getLongText(index++));
		setMatter(statement.getLongText(index++));
		setForEreg(statement.getLongInteger(index++));
		setForSreg(statement.getLongInteger(index++));
		setAllOfics(statement.getLongInteger(index++));
		setIdArch(statement.getLongInteger(index++));
		setCreationDate(statement.getDateTime(index++));
		setDisableDate(statement.getDateTime(index++));
		setEnabled(statement.getLongInteger(index++));
		setIdOrg(statement.getLongInteger(index++));

		return new Integer(index);
	}

	public Integer update(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();

		statement.setLongText(index++,getCode().toUpperCase());
		statement.setLongText(index++,getMatter());
		statement.setLongInteger(index++,getForEreg());
		statement.setLongInteger(index++,getForSreg());
		statement.setLongInteger(index++,getAllOfics());
		statement.setLongInteger(index++,getIdArch());
		statement.setDateTime(index++,getCreationDate());
		statement.setDateTime(index++,getDisableDate());
		statement.setLongInteger(index++,getEnabled());
		statement.setLongInteger(index++,getIdOrg());

		return new Integer(index);
	}

	public void update(DbConnection db) throws ISicresAdminDAOException {

		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		SicresTipoAsuntoTabla table = new SicresTipoAsuntoTabla();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Actualizando scr_ca.");
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
				logger.debug("Actualizado scr_ca.");
			}
		} catch (Exception e) {
			//TODO Hacer esto de los errores de foreing key de forma generica
			if(UtilsBD.isErrorDuplicateKey(e.getMessage())){
				logger.error("Error eliminando scr_ca. Error foreing key");
				throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CA_UNIQUE_KEY_ERROR);
			}
			else{
				logger.error("Error actualizando scr_ca", e);
				throw new ISicresAdminDAOException(ISicresAdminDAOException.SCR_CA_UPDATE, e);
			}
		}
	}

	public static TiposAsuntoBean getTiposAsunto(DbConnection db)
	throws ISicresAdminDAOException {
		TiposAsuntoBean tiposAsunto = new TiposAsuntoBean();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfoProcedure = new DynamicRow();
		SicresTipoAsuntoTabla table = new SicresTipoAsuntoTabla();
		SicresTipoAsuntoDatos tipoAsuntoDAO;
		int counter, size;

		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo datos de scr_caaux");
		}

		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(table.getClass().getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfoProcedure.setClassName(SicresTipoAsuntoDatos.class.getName());
			rowInfoProcedure.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfoProcedure);

			DynamicFns.selectMultiple(db, table.getOrderByDesc(), false,
					tableInfo, rowsInfo);
			size = rowInfoProcedure.getRowCount();

			for (counter = 0; counter < size; counter++) {
				tipoAsuntoDAO = (SicresTipoAsuntoDatos) rowInfoProcedure.getRow(counter);
				tiposAsunto.add(tipoAsuntoDAO);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Datos de scr_caaux obtenidos");
			}
		} catch (Exception e) {
			logger.error("Error obteniendo datos de scr_caaux");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return tiposAsunto;
	}
}
