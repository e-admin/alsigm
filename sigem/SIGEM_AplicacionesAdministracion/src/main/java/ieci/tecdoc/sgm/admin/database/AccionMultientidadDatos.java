package ieci.tecdoc.sgm.admin.database;

import ieci.tecdoc.sgm.admin.AdministracionException;
import ieci.tecdoc.sgm.admin.beans.AccionMultientidadImpl;
import ieci.tecdoc.sgm.admin.beans.AccionesMultientidad;
import ieci.tecdoc.sgm.admin.interfaces.AccionMultientidad;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.core.db.DataSourceManager;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class AccionMultientidadDatos extends AccionMultientidadImpl implements Serializable {

	private static final long serialVersionUID = -1751744316037097977L;
	private static final Logger logger = Logger.getLogger(AccionMultientidadDatos.class);
	protected boolean isDebugeable = true;
	
	public AccionMultientidadDatos(){
	}

	public AccionMultientidadDatos(AccionMultientidadImpl am) {
		setIdentificador(am.getIdentificador());
		setNombre(am.getNombre());
		setClaseConfiguradora(am.getClaseConfiguradora());
		setClaseEjecutora(am.getClaseEjecutora());
		setInfoAdicional(am.getInfoAdicional());
	}
	
	public Integer loadAllValues(DbOutputStatement statement, Integer idx) throws Exception {
		int index = idx.intValue();

		setIdentificador(statement.getShortText(index++));
		setNombre(statement.getShortText(index++));
		setClaseConfiguradora(statement.getShortText(index++));
		setClaseEjecutora(statement.getShortText(index++));
		setInfoAdicional(statement.getShortText(index++));
		      
		return new Integer(index);
	}

	/**
	 * Realiza la consulta de acciones de multientidad
	 *
	 * @throws AdministracionException Si se produce algún error.
	 */
	public AccionesMultientidad load() throws AdministracionException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		AccionMultientidadTabla table = new AccionMultientidadTabla();
		DbConnection dbConn = new DbConnection();
		AccionesMultientidad accionesMultientidad = new AccionesMultientidad();
		
		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo datos de accion de multientidad...");
		}

		try {
	        dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));

			tableInfo.setTableObject(table);
			tableInfo.setClassName(AccionMultientidadTabla.class.getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			//rowInfo.addRow(this);
			rowInfo.setClassName(AccionMultientidadDatos.class.getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);

			DynamicFns.selectMultiple(dbConn, null ,false, tableInfo, rowsInfo);
			
			AccionMultientidadImpl accionMultientidadImpl = null;
		    for (int i = 0; i < rowInfo.getRowCount(); i++) {
		    	accionMultientidadImpl = (AccionMultientidadDatos) rowInfo.getRow(i);
		    	if (accionMultientidadImpl != null && accionMultientidadImpl.getIdentificador() != null)
		    		accionesMultientidad.add(accionMultientidadImpl);
		    }

			if(logger.isDebugEnabled()){
				logger.debug("Datos de Acciones de Multientidad obtenidos.");
			}

		} catch (Exception e) {
			logger.error("Error recogiendo datos de Acciones de multientidad", e);
			throw new AdministracionException(AdministracionException.EC_ACC_NOT_FOUND, e.getCause());
		} finally {
			try{
				if (dbConn.existConnection()){
					dbConn.close();        	  
					if(logger.isDebugEnabled()){
						logger.debug("Sesión cerrada.");
					}
				}
			}catch(Exception e){ 
				logger.error("Error cerrando sesión.", e);
				throw new AdministracionException(AdministracionException.EC_CLOSE_CONNECTION, e.getCause());
			}
		}
		return accionesMultientidad;
	}

	/**
	 * Realiza la consulta de accion de multientidad por id.
	 *
	 * @param id identificador de la accion.
	 * @throws DbExcepcion Si se produce algún error.
	 */
	public AccionMultientidad load(String id) throws AdministracionException {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		AccionMultientidadTabla table = new AccionMultientidadTabla();
		DbConnection dbConn = new DbConnection();

		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo datos de accion de multientidad...");
		}

		try {
			dbConn.open(DBSessionManager.getSession(DataSourceManager.ADMINISTRACION_DATASOURCE_NAME));

			tableInfo.setTableObject(table);
			tableInfo.setClassName(AccionMultientidadTabla.class.getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(AccionMultientidadDatos.class.getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);

			if (!DynamicFns.select(dbConn, table.getById(id), tableInfo, rowsInfo)) {
				throw new AdministracionException(AdministracionException.EC_ACC_NOT_FOUND);
			}
						
			if(logger.isDebugEnabled()){
				logger.debug("Datos de Accion de multientidad obtenidos.");
			}
			
			return (AccionMultientidadDatos) rowInfo.getRow(0);

		} catch (AdministracionException e) {
			logger.error("Error recogiendo datos de Accion de multientidad.", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error recogiendo datos de Accion de multientidad.", e);
			throw new AdministracionException(AdministracionException.EC_ACC_NOT_FOUND, e.getCause());
		} finally {
			try{
				if (dbConn.existConnection()){
					dbConn.close();        	  
					if(logger.isDebugEnabled()){
						logger.debug("Sesión cerrada.");
					}
				}
			}catch(Exception e){ 
				logger.error("Error cerrando sesión.", e);
				throw new AdministracionException(AdministracionException.EC_CLOSE_CONNECTION, e.getCause());
			}
		}
	}
}
