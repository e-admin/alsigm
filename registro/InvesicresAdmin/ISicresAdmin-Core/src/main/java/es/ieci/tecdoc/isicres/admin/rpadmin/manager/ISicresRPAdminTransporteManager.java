package es.ieci.tecdoc.isicres.admin.rpadmin.manager;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.beans.Transporte;
import es.ieci.tecdoc.isicres.admin.beans.Transportes;
import es.ieci.tecdoc.isicres.admin.core.database.IdsGenerator;
import es.ieci.tecdoc.isicres.admin.core.database.SicresTransporteDatos;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;

public class ISicresRPAdminTransporteManager{
	public static final Logger logger = Logger
			.getLogger(ISicresRPAdminTransporteManager.class);

	public static Transportes obtenerTransportes(String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		Transportes transportes = null;
		try {
			db.open(DBSessionManager.getSession());
			transportes = SicresTransporteDatos.getTransportes(db);
		} catch (Exception e) {
			logger.error("Error obteniendo transportes");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection())
					db.close();
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return transportes;
	}

	public static void crearTransporte(Transporte transporte, String entidad)
			throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();

		try {
			transporte.setId(IdsGenerator.generateNextId(IdsGenerator.SCR_TT,
					entidad));

			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			new SicresTransporteDatos(transporte).add(db);
			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error creando el tranporte");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	public static void editarTransporte(Transporte transporte,String entidad)
			throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			new SicresTransporteDatos(transporte).update(db);
			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error editando el Transporte");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	public static void eliminarTransporte(int id, String entidad)
			throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();

		try {
			db.open(DBSessionManager.getSession());
			SicresTransporteDatos transporte = new SicresTransporteDatos();
			transporte.load(id, db);
			
			db.beginTransaction();
			transporte.delete(db);
			db.endTransaction(true);
			if(logger.isDebugEnabled())
				logger.error("Transporte eliminado");
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error eliminando el transporte");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}


	public static Transporte obtenerTransporte(int id, String entidad)
			throws Exception {
		DbConnection db = new DbConnection();
		SicresTransporteDatos transporte = new SicresTransporteDatos();
		try {
			db.open(DBSessionManager.getSession());
			transporte.load(id, db);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return transporte;
	}

}
