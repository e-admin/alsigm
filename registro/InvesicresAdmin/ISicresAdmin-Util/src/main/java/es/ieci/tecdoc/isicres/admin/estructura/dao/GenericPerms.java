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
public interface GenericPerms {

	/**
	 * Elimina los permisos de un elemento.
	 *
	 * @param id
	 *            Identificador del elemento.
	 * @param dest
	 *            Destino del permiso.
	 * @throws Exception
	 *             Si se produce algún error en el borrao de los permisos del
	 *             elemento.
	 */

	void deletePerms(int id, int dest, String entidad) throws Exception;



	/**
	 * Inserta la parte de permisos de las tablas de elemento.
	 *
	 * @throws Exception
	 *             Si se produce algún error en la inserción del elemento.
	 */

	void insertPerms(String entidad) throws Exception;

	/**
	 * Actualiza un permiso.
	 *
	 * @param id
	 *            Identificador del elemento.
	 * @param productId
	 *            Identificador del producto.
	 * @param dest
	 *            Destino del permiso.
	 * @throws Exception
	 *             Si se produce algún error en la actualización.
	 */

	void updatePerm(int id, int productId, int dest, String entidad)
			throws Exception;

	/**
	 * Actualiza la parte de permisos de las tablas de elemento.
	 *
	 * @param id
	 *            Identificador del elemento.
	 * @param dest
	 *            Destino del permiso.
	 * @throws Exception
	 *             Si se produce algún error en la actualización.
	 */

	void updatePerms(int id, int dest, String entidad) throws Exception;

	/**
	 * Lee los permisos del elemento invesDoc.
	 *
	 * @param id
	 *            Identificador del elemento.
	 * @param dest
	 *            Destinatario de los permisos.
	 * @throws Exception
	 *             Si se produce algún error en la lectura de los permisos del
	 *             elemento.
	 */

	void loadPerms(int id, int dest, String entidad) throws Exception;

	/**
	 * Establece los permisos del elemento invesDoc por defecto.
	 *
	 * @param id
	 *            Identificador del elemento.
	 * @param dest
	 *            Destino del elemento.
	 * @throws Exception
	 *             Si se produce algún error en el establecimiento de los
	 *             permisos del elemento.
	 */

	void setDefaultPerms(int id, int dest) throws Exception;

	Permissions get_perms();

	Permissions getPermissions();

}
