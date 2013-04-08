package es.ieci.tecdoc.isicres.admin.core.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;


public class IdsGenerator {
	public static Logger logger = Logger.getLogger(IdsGenerator.class);

	public static final String SCR_BOOKOFIC = "SCR_BOOKOFIC";
	public static final String SCR_OFIC 	= "SCR_OFIC";
	public static final String SCR_ORGS 	= "SCR_ORGS";
	public static final String SCR_DISTLIST = "SCR_DISTLIST";
	public static final String SCR_REGSTATE = "SCR_REGSTATE";
	public static final String SCR_TYPEADMIN = "SCR_TYPEADM";
	public static final String SCR_USEROFIC = "SCR_USROFIC";
	public static final String SCR_TT 		= "SCR_TT";
	public static final String SCR_CA		= "SCR_CA";
	public static final String SCR_CAOFIC	= "SCR_CAOFIC";
	public static final String SCR_REPORT		= "SCR_REPORTS";
	public static final String SCR_REPORTOFIC	= "SCR_REPORTOFIC";
	public static final String SCR_REPORTPERF	= "SCR_REPORTPERF";
	public static final String SCR_REPORTARCH	= "SCR_REPORTARCH";
	public static final String SCR_CADOCS	= "SCR_CADOCS";
	public static final String SCR_CAAUX	= "SCR_CAAUX";

	public static int generateNextId(String tabla, String entidad)
			throws Exception {
		int id=0;
		DbConnection db = new DbConnection();
		ResultSet rs=null;
		PreparedStatement stmtUp=null;
		PreparedStatement stmtSel=null;
		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			Connection conn = db.getJdbcConnection();
			stmtUp = conn
					.prepareStatement("UPDATE SCR_CONTADOR SET CONTADOR=CONTADOR+1 WHERE TABLAID=?");
			stmtUp.setString(1, tabla.toUpperCase());
			stmtUp.execute();

			stmtSel = conn
					.prepareStatement("SELECT CONTADOR FROM SCR_CONTADOR WHERE TABLAID=?");
			stmtSel.setString(1, tabla.toUpperCase());
			rs = stmtSel.executeQuery();
			if (rs.next()) {
				id = rs.getInt("contador");
			} else {
				id = 1;
				logger.warn("No se ha encontrado el contador para la tabla '" + tabla + "', se inserta en scr_contador y se devuelve el valor 1");
				conn.createStatement().execute("INSERT INTO scr_contador(tablaid, contador) VALUES ('" + tabla + "', 1)");
				//throw new RPAdminDAOException(RPAdminDAOException.IDGENERATOR_TABLE_NOT_FOUND);
			}
			db.endTransaction(true);
			return id;
		} catch (Exception e) {
			if(db!=null && db.inTransaction()) db.endTransaction(false);
			logger.error("Error generando id");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if(rs!=null) rs.close();
				if(stmtUp!=null) stmtUp.close();
				if(stmtSel!=null) stmtSel.close();
				if(db!=null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}
}
