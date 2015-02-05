package ieci.core.guid;

import ieci.core.db.DbConnection;
import ieci.core.db.DbEngine;

/** Agrupa funciones varias. */

public final class GuidManager {

	private GuidManager() {
	}

	/**
	 * Crea la tabla GuidGen.
	 * 
	 * @param conn
	 *            Conexión a la base de datos.
	 * @throws Exception
	 *             si se produce algún error.
	 */

	public static void createTable(DbConnection conn) throws Exception {
		GuidDaoGenTbl.createTable(conn);
	}

	/**
	 * Elimina la tabla GuidGen.
	 * 
	 * @param conn
	 *            Conexión a la base de datos.
	 * @throws Exception
	 *             si se produce algún error.
	 */

	public static void dropTable(DbConnection conn) throws Exception {
		GuidDaoGenTbl.dropTable(conn);
	}

	/**
	 * Inicializa la tabla GuidGen.
	 * 
	 * @param conn
	 *            Conexión a la base de datos.
	 * @param node
	 *            Nodo.
	 * @throws Exception
	 *             si se produce algún error.
	 */

	public static void initializeTable(DbConnection conn, String node)
			throws Exception {

		GuidDaoGenRecAc rec;

		rec = new GuidDaoGenRecAc(node, 0);

		GuidDaoGenTbl.insertRow(conn, rec);

	}

	/**
	 * Actualiza la tabla GuidGen.
	 * 
	 * @param conn
	 *            Conexión a la base de datos.
	 * @param node
	 *            Nodo.
	 * @throws Exception
	 *             si se produce algún error.
	 */

	public static void updateTable(DbConnection conn, String node)
			throws Exception {

		GuidDaoGenRecAc rec;

		rec = new GuidDaoGenRecAc(node, 0);

		GuidDaoGenTbl.updateRow(conn, rec);

	}

	public static String getNode(DbConnection conn) throws Exception {
		return GuidDaoGenTbl.selectNode(conn);
	}

	/**
	 * Genera un nuevo GUID.
	 * 
	 * @param conn
	 *            Conexión a la base de datos.
	 * @return El GUID generado.
	 * @throws Exception
	 *             si se produce algún error.
	 */

	public static String generateGuid(DbConnection conn) throws Exception {

		Guid guid;

		guid = new Guid();

		guid.create(conn);

		return guid.getText();

	}

	public static String generateGUID(DbEngine dbEngine) throws Exception {
		Guid guid = new Guid();
		DbConnection con = null;
		try {
			con = dbEngine.getConnection();
			guid.create(con);
			return guid.getText();
		} catch (Exception e) {
			throw e;
		} finally {
			dbEngine.closeConnection(con);
		}
	}
} // class
