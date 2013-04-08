package ieci.tecdoc.idoc.admin.api.user;

import ieci.tecdoc.idoc.admin.internal.GenericPermsImpl;
import ieci.tecdoc.idoc.admin.internal.GenericProfilesImpl;
import ieci.tecdoc.idoc.admin.internal.UserDataImpl;

import java.util.ArrayList;
import java.util.Date;

/**
 * Proporciona toda la funcionalidad necesaria para manejar usuarios invesDoc.
 */
public interface User {
	/**
	 * Carga un usuario.
	 * 
	 * @param userId
	 *            Identificador del usuario.
	 * @throws Exception
	 *             Si se produce algún error al leer la información del usuario.
	 */

	public void load(int userId, String entidad) throws Exception;

	/**
	 * Carga un usuario.
	 * 
	 * @param userId
	 *            Identificador del usuario.
	 * @param full
	 *            Indica si comprueba perfiles y permisos
	 * @param entidad
	 *            Identificador de la entidad.
	 * @throws Exception
	 *             Si se produce algún error al leer la información del usuario.
	 */

	public void load(int userId, boolean full, String entidad) throws Exception;

	/**
	 * Carga un usuario.
	 * 
	 * @param userName
	 *            Nombre del usuario.
	 * @throws Exception
	 *             Si se produce algún error al leer la información del usuario.
	 */

	public void load(String userName, String entidad) throws Exception;

	/**
	 * Guarda el usuario. Se utiliza tanto para inserciones como para
	 * actualizaciones.
	 * 
	 * @throws Exception
	 *             Si se produce algún error al guardar. Por ejemplo, el usuario
	 *             ya existe.
	 */
	public void store(String entidad) throws Exception;

	/**
	 * Carga un usuario a partir del identificador del certificado digital
	 * 
	 * @param idCert
	 *            Identificador del certificado digital
	 * @param entidad
	 *            Identificador de la entidad
	 * @throws Exception
	 *             Si se produce algún error al leer la información del usuario.
	 */
	public void loadFromIdCert(String idCert, String entidad) throws Exception;

	/**
	 * Elimina el usuario.
	 * 
	 * @throws Exception
	 *             Si se produce algún error al eliminar.
	 */

	public void delete(String entidad) throws Exception;

	/**
	 * Obtiene los identificadores de los departamentos a los que administra el
	 * usurario indicado.
	 * 
	 * @param userId
	 *            Identificador de usuario
	 * @return Lista de enteros con los identificadores de departamento
	 * @throws Exception
	 *             Si se produce algún error.
	 */
	public ArrayList getDeptsIdMgr(int userId, String entidad) throws Exception;

	/**
	 * Otiene los identificadores de los grupos a los que administra el usuario
	 * especificado.
	 * 
	 * @param userId
	 *            Identificador de usuario
	 * @return Lista de enteros con los identificadores de grupo
	 * @throws Exception
	 *             Si se produce algún error.
	 */
	public ArrayList getGroupsIdMgr(int userId, String entidad)
			throws Exception;

	/**
	 * Obtiene el identificador del usuario.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getId();

	/**
	 * Obtiene el nombre del usuario.
	 * 
	 * @return El nombre mencionado.
	 */
	public String getName();

	/**
	 * Establece el nombre del usuario.
	 * 
	 * @param name
	 *            El nombre del usuario.
	 */
	public void setName(String name);

	/**
	 * Establece la password del usuario.
	 * 
	 * @param password
	 *            La password del usuario.
	 */
	public void setPassword(String password);

	/**
	 * Obtiene la obligatoriedad sobre la inicialización de la contraseña.
	 * 
	 * @return <code>true</code> si es obligatoria la inicialización de la
	 *         contraseña. <code>false</code> si no es obligatoria la
	 *         inicialización de la contraseña.
	 */
	public boolean getPwdmbc();

	/**
	 * Establece la obligatoriedad de la inicialización de la contraseña.
	 * 
	 * @param pwdmbc
	 *            <code>true</code> si es obligatoria la inicialización de la
	 *            contraseña. <code>false</code> si no es obligatoria la
	 *            inicialización de la contraseña.
	 */
	public void setPwdmbc(boolean pwdmbc);

	/**
	 * Obtiene la información de comprobar la caducidad de la contraseña.
	 * 
	 * @return <code>true</code> si se comprueba la caducidad de la contraseña.
	 *         <code>false</code> si no se comprueba.
	 */
	public boolean getPwdvpcheck();

	/**
	 * Establece la información de comprobar la caducidad de la contraseña.
	 * 
	 * @param pwdvpcheck
	 *            <code>true</code> si se comprueba la caducidad de la
	 *            contraseña. <code>false</code> si no se comprueba.
	 */
	public void setPwdvpcheck(boolean pwdvpcheck);

	/**
	 * Obtiene la descripción del usuario.
	 * 
	 * @return El dato mencionado.
	 */
	public String getDescription();

	/**
	 * Establece la descripción del usuario.
	 * 
	 * @param description
	 *            La descripción del usuario.
	 */
	public void setDescription(String description);

	/**
	 * Obtiene el identificador del departamento al que pertenece el usuario.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getDeptId();

	/**
	 * Establece el identificador del departamento del usuario.
	 * 
	 * @param deptId
	 *            El identificador del departamento.
	 */
	public void setDeptId(int deptId);

	/**
	 * Obtiene el estado del usuario.
	 * 
	 * @return El dato mencionado.
	 */
	public int getState();

	/**
	 * Establece el estado del usuario.
	 * 
	 * @param state
	 *            El estado del usuario.
	 */
	public void setState(int state);

	/**
	 * Obtiene la lista de perfiles del usuario.
	 * 
	 * @return La lista mencionada.
	 */
	public UserProfiles getProfiles();

	/**
	 * Obtiene la lista de permisos del usuario.
	 * 
	 * @return La lista mencionada.
	 */
	public Permissions getPermissions();

	/**
	 * Obtiene el identificador del usuario que ha creado el usuario.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getCreatorId();

	/**
	 * Obtiene la fecha de creación del usuario.
	 * 
	 * @return La fecha mencionada.
	 */
	public Date getCreationDate();

	/**
	 * Obtiene el identificador del usuario que ha actualizado el usuario.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getUpdaterId();

	public int getUserConnected();

	public String getPassword();

	public String getOldPassword();

	public boolean getIsChange();

	public String get_pwdmbc();

	public String get_pwdvpcheck();

	public int get_pwdminlen();

	public long get_pwdLastUpdTs();

	public void set_pwdLastUpdTs(long _pwdLastUpdTs);

	public void set_pwdminlen(int _pwdminlen);

	public boolean get_wasAdmin();

	public GenericPermsImpl get_permsImpl();

	public GenericProfilesImpl get_profilesImpl();

	public UserDataImpl getUserDataImpl();

	public void setUserDataImpl(UserDataImpl userDataImpl);

	/**
	 * Obtiene la fecha de actualización del usuario.
	 * 
	 * @return La fecha mencionada.
	 */
	public Date getUpdateDate();

	/**
	 * Obtiene la fecha de la última modificación de la contraseña. Horas
	 * transcurridas desde 01-01-1971.
	 * 
	 * @return La fecha mencionada.
	 */
	public double getPwdLastUpdTs();

	/**
	 * Inicializa datos del Syssuperuser
	 * 
	 * @throws Exception
	 */
	public void initSysSuperUser(String entidad) throws Exception;

	/**
	 * Obtiene la información del usuario en formato XML.
	 * 
	 * @return La información mencionada.
	 */

	public String toXML();

	/**
	 * Muestra una representación de los valores de la clase en formato XML.
	 * 
	 * @return La representación mencionada.
	 */

	public String toString();

	public void resetProfiles();

}
