package se.usuarios;

import gcontrol.vos.UsuarioVO;
import ieci.core.db.DbEngine;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import salas.vos.UsuarioSalasConsultaVO;

import common.Constants;
import common.model.UserInfo;
import common.util.ListUtils;
import common.util.StringUtils;

/**
 * Clase que almacena información sobre el usuario que se ha conectado a la
 * aplicación.
 */
public class AppUser extends GenericUserInfo implements UserInfo {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Nombre del usuario. */
	private String name = null;

	/** Apellidos del usuario. */
	private String surname = null;

	/** Dirección de correo electrónico del usuario. */
	private String email = null;

	/** Dirección del usuario. */
	private String address = null;

	/** Indica si el usuario está habilitado. */
	private boolean activated = false;

	/** Fecha máxima de vigencia del usuario. */
	private Date maxDate = null;

	/** Identificador del usuario en el Sistema Gestor de Usuarios. */
	private String externalUserId = null;

	/** Identificador del usuario en el Sistema Gestor de Organización. */
	private String organizationUserId = null;

	/** Descripción del usuario. */
	private String description = null;

	/** Lista de roles del usuario. */
	private List rolesList = null;

	/** Organismos antecesores del órgano al que pertenece el usuario. */
	private List ancestorOrganizationList = null;

	/** Entidad del usuario */
	private String entity = null;

	private boolean usuarioArchivo = false;

	private DbEngine engine = null;

	private Locale locale = null;

	private UsuarioSalasConsultaVO usuarioSalasConsultaVO = null;

	/*
	 * Map que nos indica las partes del codigo de referencia a mostrar al
	 * usuario
	 */
	private Map partesCodigoReferencia = new HashMap();

	/* Constantes para la spartes del codigo de referencia */
	public static final int MOSTRAR_PAIS_PROVINCIA = 0;
	public static final int MOSTRAR_ARCHIVO_CODIGO_CLASIFICADOR = 1;
	private static final int partes[] = new int[] { MOSTRAR_PAIS_PROVINCIA,
			MOSTRAR_ARCHIVO_CODIGO_CLASIFICADOR };

	/**
	 * Constructor.
	 */
	public AppUser() {
		super();

		this.rolesList = new ArrayList();
		this.ancestorOrganizationList = new ArrayList();
	}

	/**
	 * @return Returns the activated.
	 */
	public boolean isActivated() {
		return activated;
	}

	/**
	 * @param activated
	 *            The activated to set.
	 */
	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	/**
	 * @return Returns the address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Returns the externalId.
	 */
	public String getExternalUserId() {
		return externalUserId;
	}

	/**
	 * @param externalId
	 *            The externalId to set.
	 */
	public void setExternalUserId(String externalId) {
		this.externalUserId = externalId;
	}

	/**
	 * @return Returns the maxDate.
	 */
	public Date getMaxDate() {
		return maxDate;
	}

	/**
	 * @param maxDate
	 *            The maxDate to set.
	 */
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the organizationId.
	 */
	public String getOrganizationUserId() {
		return organizationUserId;
	}

	/**
	 * @param organizationId
	 *            The organizationId to set.
	 */
	public void setOrganizationUserId(String organizationId) {
		this.organizationUserId = organizationId;
	}

	/**
	 * @return Returns the rolesList.
	 */
	public List getRolesList() {
		return rolesList;
	}

	/**
	 * @param rolesList
	 *            The rolesList to set.
	 */
	public void setRolesList(List rolesList) {
		this.rolesList = rolesList;
	}

	/**
	 * @return Returns the surname.
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname
	 *            The surname to set.
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getNombreCompleto() {
		StringBuffer nombreCompleto = new StringBuffer();
		if (StringUtils.isNotBlank(surname))
			nombreCompleto.append(getSurname()).append(", ");
		nombreCompleto.append(getName());
		return nombreCompleto.toString();
	}

	public String getNombreCompletoYApellidos() {
		StringBuffer nombreCompleto = new StringBuffer();
		nombreCompleto.append(getName());
		if (StringUtils.isNotBlank(surname)) {
			nombreCompleto.append(Constants.STRING_SPACE).append(getSurname());
		}

		return nombreCompleto.toString();
	}

	/**
	 * @return Returns the ancestorOrganizationList.
	 */
	public List getAncestorOrganizationList() {
		return ancestorOrganizationList;
	}

	/**
	 * @param ancestorOrganizationList
	 *            The organizationHierarchy to set.
	 */
	public void setAncestorOrganizationList(List ancestorOrganizationList) {
		this.ancestorOrganizationList = ancestorOrganizationList;
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
	 * @return el usuarioArchivo
	 */
	public boolean isUsuarioArchivo() {
		return usuarioArchivo;
	}

	/**
	 * @param usuarioArchivo
	 *            el usuarioArchivo a establecer
	 */
	public void setUsuarioArchivo(boolean usuarioArchivo) {
		this.usuarioArchivo = usuarioArchivo;
	}

	/**
	 * @return el engine
	 */
	public DbEngine getEngine() {
		return engine;
	}

	/**
	 * @param engine
	 *            el engine a establecer
	 */
	public void setEngine(DbEngine engine) {
		this.engine = engine;
	}

	/**
	 * Copia la información del usuario.
	 * 
	 * @param usuario
	 *            Información del usuario.
	 */
	public void copy(UsuarioVO usuario) {
		setId(usuario.getId());
		setName(usuario.getNombre());
		setSurname(usuario.getApellidos());
		setEmail(usuario.getEmail());
		setAddress(usuario.getDireccion());
		setUserType(usuario.getTipo());
		setActivated(Constants.TRUE_STRING.equals(usuario.getHabilitado()));
		setMaxDate(usuario.getFMaxVigencia());
		setExternalUserId(usuario.getIdUsrsExtGestor());
		setOrganizationUserId(usuario.getIdUsrSistOrg());
		setDescription(usuario.getDescripcion());
	}

	/**
	 * @return el entity
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 *            el entity a establecer
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Map getPartesCodigoReferencia() {
		return partesCodigoReferencia;
	}

	public void setPartesCodigoReferencia(Map partesCodigoReferencia) {
		this.partesCodigoReferencia = partesCodigoReferencia;
	}

	public void addParteCodigoReferencia(int key, boolean value) {
		this.partesCodigoReferencia.put(new Integer(partes[key]), new Boolean(
				value));
	}

	public boolean isMostrarPaisProvincia() {
		if (partesCodigoReferencia.get(new Integer(MOSTRAR_PAIS_PROVINCIA)) == null)
			return true;
		return ((Boolean) partesCodigoReferencia.get(new Integer(
				MOSTRAR_PAIS_PROVINCIA))).booleanValue();
	}

	public boolean isMostrarArchivoCodigoClasificadores() {
		if (partesCodigoReferencia.get(new Integer(
				MOSTRAR_ARCHIVO_CODIGO_CLASIFICADOR)) == null)
			return true;
		return ((Boolean) partesCodigoReferencia.get(new Integer(
				MOSTRAR_ARCHIVO_CODIGO_CLASIFICADOR))).booleanValue();
	}

	public boolean isExterno() {
		if (StringUtils.isBlank(this.organizationUserId)) {
			return true;
		}
		return false;
	}

	public void setUsuarioSalasConsultaVO(
			UsuarioSalasConsultaVO usuarioSalasConsultaVO) {
		this.usuarioSalasConsultaVO = usuarioSalasConsultaVO;
	}

	public UsuarioSalasConsultaVO getUsuarioSalasConsultaVO() {
		return usuarioSalasConsultaVO;
	}
}
