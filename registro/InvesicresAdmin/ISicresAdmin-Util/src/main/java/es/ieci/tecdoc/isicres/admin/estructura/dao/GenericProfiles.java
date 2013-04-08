/**
 *
 */
package es.ieci.tecdoc.isicres.admin.estructura.dao;



/**
 * @author Iecisa
 * @version $Revision$
 *
 */

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 */
public interface GenericProfiles {


	UserProfiles getProfiles();

	/**
	 * Inserta la parte de perfiles de las tablas de elemento.
	 *
	 * @throws Exception
	 *             Si se produce algún error en la inserción del elemento.
	 */

	void insertProfiles(String entidad) throws Exception;

	/**
	 * Actualiza un perfil.
	 *
	 * @param id
	 *            Identificador del elemento.
	 * @param productId
	 *            Identificador del producto.
	 * @throws Exception
	 *             Si se produce algún error en la actualización.
	 */
	void updateProfile(int id, int productId, String entidad)
			throws Exception;

	/**
	 * Actualiza la parte de perfiles de las tablas de elemento.
	 *
	 * @param id
	 *            Identificador del elemento.
	 * @throws Exception
	 *             Si se produce algún error en la actualización.
	 */
	void updateProfiles(int id, String entidad) throws Exception;

	/**
	 * Lee los perfiles del elemento invesDoc.
	 *
	 * @param id
	 *            Identificador del elemento.
	 * @return Si el elemento es administrador o no.
	 * @throws Exception
	 *             Si se produce algún error en la lectura de los perfiles del
	 *             elemento.
	 */
	boolean loadProfiles(int id, String entidad) throws Exception;

	/**
	 * Establece los perfiles del elemento invesDoc por defecto.
	 *
	 * @param id
	 *            Identificador del elemento.
	 * @throws Exception
	 *             Si se produce algún error en el establecimiento de los
	 *             perfiles del elemento.
	 */
	void setDefaultProfiles(int id) throws Exception;

	void setProfiles(UserProfiles userProfilesImpl)
			throws Exception;

	/**
	 * Elimina los permisos de un elemento.
	 *
	 * @param id
	 *            Identificador del elemento.
	 * @throws Exception
	 *             Si se produce algún error en el borrado de los perfiles del
	 *             elemento.
	 */
	void deleteProfiles(int id, String entidad) throws Exception;

	void resetProfiles();

	boolean existProfile(int idUsuario, int idAplicacion, String entidad);

	void insertProfile(int idUsuario, int idAplicacion, int profile);

	/**
	 * Metodo que comprueba si el usuario tiene un permiso de manager o
	 * superusario de invesdoc
	 *
	 * @return
	 */
	boolean isIdocAdmin();

}
