package se.autenticacion.idoc.api;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbConnectionConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Métodos para el acceso a la información de usuarios.
 */
public class UasBnoUser {

	/**
	 * Obtiene un usuario a partir del identificador.
	 * 
	 * @param dbConConfig
	 *            Configuración de base de datos.
	 * @param userId
	 *            Identificador del usuario.
	 * @return Información del usuario.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public static UasDaoUserRecO findUserById(DbConnectionConfig dbConConfig,
			int userId) throws Exception {
		UasDaoUserRecO userRecO = null;

		try {
			DbConnection.open(dbConConfig);
			userRecO = UasDaoUserTblEx.selectRecO(userId);
			DbConnection.close();
		} catch (Exception e) {
			DbConnection.ensureClose(e);
		}

		return userRecO;
	}

	/**
	 * Obtiene una lista de usuarios a partir de un nombre.
	 * 
	 * @param dbConConfig
	 *            Configuración de base de datos.
	 * @param username
	 *            Nombre del usuario.
	 * @return Lista de usuarios.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public static List findUsersByName(DbConnectionConfig dbConConfig,
			String username) throws Exception {
		List users = new ArrayList();

		try {
			DbConnection.open(dbConConfig);
			users.addAll(UasDaoUserTblEx.selectRecOList(username));
			DbConnection.close();
		} catch (Exception e) {
			DbConnection.ensureClose(e);
		}

		return users;
	}

}
