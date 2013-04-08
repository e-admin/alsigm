package ieci.tecdoc.sgm.usuarios_backoffice.database;

import ieci.tecdoc.sgm.base.dbex.DbConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Métodos para el acceso a la información de usuarios.
 */
public class UasBnoUser {

	/**
	 * Obtiene la información de un usuario a partir del identificador.
	 * @param conn Conexión a la base de datos.
	 * @param userId Identificador del usuario.
	 * @return Información del usuario.
	 * @throws Exception si ocurre algún error.
	 */
	public static UasDaoUserRecO findUserById(int userId, String entidad)
			throws Exception {
		
		UasDaoUserRecO userRecO = null;

		try {
			userRecO = UasDaoUserTblEx.selectRecO(userId, entidad);
		} catch (Exception e) {
			throw e;
		}

		return userRecO;
	}

	/**
	 * Obtiene una lista de usuarios a partir de un nombre.
	 * @param conn Conexión a la base de datos.
	 * @param username Nombre del usuario.
	 * @return Lista de usuarios.
	 * @throws Exception si ocurre algún error.
	 */
	public static List findUsersByName(String username, String entidad)
			throws Exception {
		List users = new ArrayList();

		try {
			users.addAll(UasDaoUserTblEx.selectRecOList(username, entidad));
		} catch (Exception e) {
			throw e;
		}

		return users;
	}

}
