package es.ieci.tecdoc.isicres.admin.estructura.dao;


/**
 * Maneja la lista de usuarios invesDoc.
 */
public interface Users {

	/**
	 * Devuelve el número de usuarios.
	 *
	 * @return El número de usuarios mencionado.
	 */

	public int count();

	/**
	 * Carga la lista de usuarios con su información básica, según un
	 * departamento.
	 *
	 * @param deptId
	 *            Identificador del departamento.
	 * @throws Exception
	 *             Si se produce algún error en la carga de los usuarios.
	 */

	public void loadByDept(int deptId, String entidad) throws Exception;

	public void loadByIdsUser(int aplicacion, int idsUser[],
			boolean superusers, String entidad) throws Exception;

	public void loadUsersAsociation(int aplicacion, int deptId,
			boolean superusers, String entidad) throws Exception;

	public void loadUsersAssociated(int[] idsUser, int idOfic, String entidad)
			throws Exception;

	/**
	 * Carga la lista de usuarios con su información básica, según un grupo.
	 *
	 * @param groupId
	 *            Identificador del grupo.
	 * @throws Exception
	 *             Si se produce algún error en la carga de los usuarios.
	 */

	public void loadByGroup(int groupId, String entidad) throws Exception;

	/**
	 * Carga la lista de usuarios con su información básica, cuyo nombre
	 * contenga el texto del parámetro subName.
	 *
	 * @param subName
	 *            texto que tiene que contener el nombre de usuario.
	 * @throws Exception
	 *             Si se produce algún error en la carga de los usuarios.
	 */

	public void loadBySubName(String subName, String entidad) throws Exception;

	public void loadByAplicacion(int aplicacion, int deptId[],
			boolean sinPermisos, boolean usuarios, boolean superusuarios,
			String entidad) throws Exception;

	/**
	 * Devuelve un usuario de la lista.
	 *
	 * @param index
	 *            Indice del usuario que se desea recuperar.
	 *
	 * @return El usuario mencionado.
	 */

	public User getUser(int index);

	/**
	 * Obtiene la información de la lista de usuarios en formato XML.
	 *
	 * @return La lista de usuarios mencionada.
	 */

	/**
	 * mueve el usuario al departamento indicado.
	 *
	 * @param userId
	 *            Identificador de usuario.
	 * @param dstDeptId
	 *            Identificador del departamento destino.
	 */
	public void moveUser(int userId, int dstDeptId, String entidad)
			throws Exception;

	public String toXML();

	/**
	 * Muestra una representación de los valores de la clase en formato XML.
	 *
	 * @return La representación mencionada.
	 */

	public String toString();

}
