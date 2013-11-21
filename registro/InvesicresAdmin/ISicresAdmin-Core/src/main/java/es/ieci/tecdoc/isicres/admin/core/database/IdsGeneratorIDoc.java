package es.ieci.tecdoc.isicres.admin.core.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;

public class IdsGeneratorIDoc {
	public static Logger logger = Logger.getLogger(IdsGeneratorIDoc.class);

	public static final int IDOCFMT = 10;

	public static int generateNextId(int tabla, String entidad)
			throws Exception {
		int id = 0;
		DbConnection db = new DbConnection();
		ResultSet rs = null;
		PreparedStatement stmtUp = null;
		PreparedStatement stmtSel = null;
		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			Connection conn = db.getJdbcConnection();

			//Obtenemos el ID
			stmtSel = conn
					.prepareStatement("SELECT ID FROM IDOCNEXTID WHERE TYPE=?");
			stmtSel.setInt(1, tabla);
			rs = stmtSel.executeQuery();
			if (rs.next()) {
				id = rs.getInt("ID");
			} else {
				id = 1;
				logger.warn("No se ha encontrado el contador para la tabla '"
						+ tabla
						+ "', se inserta en IDOCNEXTID y se devuelve el valor 1");
				conn.createStatement().execute(
						"INSERT INTO IDOCNEXTID(TYPE, ID) VALUES (" + tabla
								+ ", 1)");
				// throw new
				// RPAdminDAOException(RPAdminDAOException.IDGENERATOR_TABLE_NOT_FOUND);
			}

			// Actualizamos el contador
			stmtUp = conn
					.prepareStatement("UPDATE IDOCNEXTID SET ID=ID+1 WHERE TYPE=?");
			stmtUp.setInt(1, tabla);
			stmtUp.execute();

			db.endTransaction(true);
			return id;
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				db.endTransaction(false);
			logger.error("Error generando id");
			throw new ISicresAdminDAOException(
					ISicresAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmtUp != null)
					stmtUp.close();
				if (stmtSel != null)
					stmtSel.close();
				if (db != null && db.existConnection())
					db.close();
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}
}
