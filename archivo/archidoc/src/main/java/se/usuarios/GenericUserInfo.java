package se.usuarios;

import gcontrol.vos.CAOrganoVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import common.util.ArrayUtils;
import common.util.ListUtils;
import common.vos.BaseVO;

/**
 * Clase abstracta que almacena la información de un cliente genérico.
 */
public abstract class GenericUserInfo extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del cliente de servicio. */
	private String id = null;

	/** Tipo de usuario. */
	private String userType = null;

	/** Dirección IP desde la que se encuentra conectado. */
	private String ip = null;

	/** Nivel de criticidad. */
	private int logLevel = -1;

	/** Permisos del usuario. */
	private String[] permissions = null;

	/** Lista de identificadores de archivos de custodia. */
	private List custodyArchiveList = null;

	/** Listas de control de acceso. */
	private Map acls = null;

	/** Órgano al que pertenece el usuario. */
	private CAOrganoVO organization = null;

	/** Órganos dependientes al que pertenece el usuario. */
	private List dependentOrganizationList = null;

	/** Grupos del usuario. */
	private List groupList = null;

	private boolean mostrarPaisProvinciaBackUp = true;
	private boolean mostrarArchivoCodigoClasificadoresBackUp = true;

	/**
	 * Constructor.
	 */
	public GenericUserInfo() {
		super();
		this.permissions = new String[0];
		this.custodyArchiveList = new ArrayList();
		this.acls = new HashMap();
		this.dependentOrganizationList = new ArrayList();
		this.groupList = new ArrayList();
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the ip.
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            The ip to set.
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return Returns the logLevel.
	 */
	public int getLogLevel() {
		return logLevel;
	}

	/**
	 * @param logLevel
	 *            The logLevel to set.
	 */
	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	/**
	 * @return Returns the rightsMap.
	 */
	public String[] getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions
	 *            The rightsList to set.
	 */
	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	}

	/**
	 * @return Returns the userType.
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            The userType to set.
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return Returns the custodyArchiveList.
	 */
	public List getCustodyArchiveList() {
		return custodyArchiveList;
	}

	/**
	 * @param custodyArchiveList
	 *            The custodyArchiveList to set.
	 */
	public void setCustodyArchiveList(List custodyArchiveList) {
		this.custodyArchiveList = custodyArchiveList;
	}

	/**
	 * @return Returns the acls.
	 */
	public Map getAcls() {
		return acls;
	}

	/**
	 * @param acls
	 *            The acls to set.
	 */
	public void setAcls(Map acls) {
		this.acls = acls;
	}

	/**
	 * @return Returns the organization.
	 */
	public CAOrganoVO getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            The CAOrganoVO to set.
	 */
	public void setOrganization(CAOrganoVO organization) {
		this.organization = organization;
	}

	/**
	 * @return Returns the dependentOrganizationList.
	 */
	public List getDependentOrganizationList() {
		return dependentOrganizationList;
	}

	/**
	 * @param dependentOrganizationList
	 *            The dependentOrganizationList to set.
	 */
	public void setDependentOrganizationList(List dependentOrganizationList) {
		this.dependentOrganizationList = dependentOrganizationList;
	}

	/**
	 * @return Returns the groupList.
	 */
	public List getGroupList() {
		return groupList;
	}

	/**
	 * @param groupList
	 *            The groupList to set.
	 */
	public void setGroupList(List groupList) {
		this.groupList = groupList;
	}

	/**
	 * Indica si el usuario tiene el permiso especificado.
	 * 
	 * @param right
	 *            Permiso.
	 * @return true si el usuario tiene el permiso especificado.
	 */
	public boolean hasPermission(String right) {
		Arrays.sort(permissions);
		if (Arrays.binarySearch(permissions, right) >= 0
				|| Arrays.binarySearch(permissions,
						AppPermissions.ADMINISTRACION_TOTAL_SISTEMA) >= 0)
			return true;

		return false;
	}

	/**
	 * Indica si el usuario tiene alguno de los permisos especificados.
	 * 
	 * @param rights
	 *            Lista de permisos.
	 * @return true si el usuario tiene alguno de los permisos especificados.
	 */
	public boolean hasAnyPermission(String[] rights) {
		if (ArrayUtils.isEmpty(rights))
			return false;

		Arrays.sort(permissions);
		if (Arrays.binarySearch(permissions,
				AppPermissions.ADMINISTRACION_TOTAL_SISTEMA) >= 0)
			return true;

		for (int i = 0; i < rights.length; i++)
			if (Arrays.binarySearch(permissions, rights[i]) >= 0)
				return true;

		return false;
	}

	/**
	 * Indica si el usuario tiene alguno de los permisos especificados.
	 * 
	 * @param rights
	 *            Lista de permisos.
	 * @return true si el usuario tiene alguno de los permisos especificados.
	 */
	public boolean hasPermissionAdministracionTotal() {
		Arrays.sort(permissions);
		if (Arrays.binarySearch(permissions,
				AppPermissions.ADMINISTRACION_TOTAL_SISTEMA) >= 0) {
			return true;
		}

		return false;
	}

	public boolean hasPermissionEliminacionCuadro() {
		String[] rights = new String[] { AppPermissions.ELIMINACION_CUADRO_CLASIFICACION };

		if (hasAnyPermission(rights)) {
			return true;
		}

		return false;
	}

	public boolean hasPermissionEdicionDocumentos() {
		String[] rights = new String[] { AppPermissions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION };
		if (hasAnyPermission(rights)) {
			return true;
		}

		return false;
	}

	public boolean hasPermissionGestionPrestamos() {
		String[] rights = new String[] { AppPermissions.GESTION_PRESTAMOS_ARCHIVO };
		if (hasAnyPermission(rights)) {
			return true;
		}

		return false;
	}

	/**
	 * Indica si el usuario tiene alguno de los permisos especificados.
	 * 
	 * @param rights
	 *            Lista de permisos.
	 * @return true si el usuario tiene alguno de los permisos especificados.
	 */
	public boolean hasAnyPermissionConsulta(String[] rights) {
		if (ArrayUtils.isEmpty(rights))
			return false;

		Arrays.sort(permissions);
		if (Arrays.binarySearch(permissions,
				AppPermissions.CONSULTA_TOTAL_SISTEMA) >= 0)
			return true;

		for (int i = 0; i < rights.length; i++)
			if (Arrays.binarySearch(permissions, rights[i]) >= 0)
				return true;

		return false;
	}

	/**
	 * Indica si el usuario está incluido en la lista de control de acceso.
	 * 
	 * @param aclId
	 *            Identificador de la lista de control de acceso.
	 * @return true si el usuario está incluido en la ACL.
	 */
	public boolean hasAccessControlList(String aclId) {
		return acls.containsKey(aclId);
	}

	/**
	 * Indica si el usuario pertenece al archivo de custodia.
	 * 
	 * @param custodyArchiveId
	 *            Identificador del archivo de custodia.
	 * @return true si el usuario pertenece al archivo de custodia.
	 */
	public boolean belongsToCustodyArchive(String custodyArchiveId) {
		Object[] arrayOrdenado = custodyArchiveList.toArray();
		Arrays.sort(arrayOrdenado);
		// if (Arrays.binarySearch(custodyArchiveList.toArray(),
		// custodyArchiveId) >= 0)
		if (Arrays.binarySearch(arrayOrdenado, custodyArchiveId) >= 0)
			return true;

		return false;
	}

	public String[] getAllDependentOrganizationIds() {
		List ids = new ArrayList();

		// Identificador del órgano del usuario
		if (organization != null)
			ids.add(organization.getIdOrg());

		// Identificadores de los órganos dependientes
		CAOrganoVO organo;
		for (int i = 0; i < dependentOrganizationList.size(); i++) {
			organo = (CAOrganoVO) dependentOrganizationList.get(i);
			ids.add(organo.getIdOrg());
		}

		return (String[]) ids.toArray(new String[ids.size()]);
	}

	public boolean isMostrarPaisProvinciaBackUp() {
		return mostrarPaisProvinciaBackUp;
	}

	public void setMostrarPaisProvinciaBackUp(boolean mostrarPaisProvinciaBackUp) {
		this.mostrarPaisProvinciaBackUp = mostrarPaisProvinciaBackUp;
	}

	public boolean isMostrarArchivoCodigoClasificadoresBackUp() {
		return mostrarArchivoCodigoClasificadoresBackUp;
	}

	public void setMostrarArchivoCodigoClasificadoresBackUp(
			boolean mostrarArchivoCodigoClasificadoresBackUp) {
		this.mostrarArchivoCodigoClasificadoresBackUp = mostrarArchivoCodigoClasificadoresBackUp;
	}

	/**
	 * Obtiene los Ids de Archivo a los que pertenece el usuario
	 * 
	 * @return Array de Strings con los Archivos del Usuario.
	 */
	public String[] getIdsArchivosUser() {
		List listaArchivosCustodia = getCustodyArchiveList();

		if (!ListUtils.isEmpty(listaArchivosCustodia)) {
			String[] retorno = new String[listaArchivosCustodia.size()];
			Iterator it = listaArchivosCustodia.iterator();
			int posicion = 0;
			while (it.hasNext()) {
				String idArchivo = (String) it.next();
				retorno[posicion] = idArchivo;
				posicion++;
			}
			return retorno;
		}
		return null;
	}

	/**
	 * Compruba si el usuario es Personal de Archivo
	 * 
	 * @return
	 */
	public boolean isPersonalArchivo() {
		if (ListUtils.isNotEmpty(getCustodyArchiveList())) {
			return true;
		}
		return false;
	}

	public boolean hasPermissionGestionSolicitudesConsultas() {
		String[] rights = new String[] { AppPermissions.GESTION_SOLICITUDES_CONSULTAS };
		if (hasAnyPermission(rights)) {
			return true;
		}
		return false;
	}
}
