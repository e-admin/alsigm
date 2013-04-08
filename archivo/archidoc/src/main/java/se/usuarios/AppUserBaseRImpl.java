package se.usuarios;

import gcontrol.model.TipoDestinatario;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.DestinatarioPermisosListaVO;
import gcontrol.vos.GrupoVO;
import gcontrol.vos.PermisoVO;
import gcontrol.vos.PermisosListaVO;
import gcontrol.vos.RolVO;
import gcontrol.vos.UsuarioVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.security.auth.Subject;

import org.apache.log4j.Logger;

import salas.model.GestionSalasConsultaBI;
import salas.vos.UsuarioSalasConsultaVO;
import se.autenticacion.AuthenticationConnector;
import se.autenticacion.AuthenticationConnectorFactory;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.usuarios.exceptions.AppUserException;
import util.CollectionUtils;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.SistemaGestorOrganismos;

import common.ConfigConstants;
import common.Constants;
import common.MultiEntityConstants;
import common.TiposUsuario;
import common.authentication.ArchivoIdentifier;
import common.bi.GestionAuditoriaBI;
import common.bi.GestionControlUsuariosBI;
import common.bi.ServiceRepository;
import common.model.UserInfo;
import common.util.StringUtils;

public abstract class AppUserBaseRImpl implements AppUserRI {

	/** Logger de la clase. */
	private final static Logger logger = Logger
			.getLogger(AppUserBaseRImpl.class);

	/**
	 * Obtiene la información del usuario en la aplicación.
	 * 
	 * @param userAuthId
	 *            Identificador del usuario en el Sistema Gestor de Usuarios.
	 * @param userAuthType
	 *            Tipo de usuario.
	 * @param userName
	 *            Nombre del usuario.
	 * @param remoteIpAddress
	 *            Dirección IP del usuario.
	 * @param entity
	 *            Entidad para multientidad
	 * @param sessionAdm
	 *            Sesión de administracion
	 * @return Información del usuario en la aplicación.
	 * @throws AppUserException
	 *             si ocurre algún error en la validación
	 */
	public abstract AppUser getAppUser(String userAuthId, String userAuthType,
			String userName, String remoteIpAddress, String entity,
			String sessionAdm) throws AppUserException;

	/**
	 * Obtiene la información del usuario en la aplicación.
	 * 
	 * @param subject
	 *            Información de la autenticación del usuario.
	 * @return Información del usuario en la aplicación.
	 * @throws AppUserException
	 *             si ocurre algún error en la validación
	 */
	public abstract AppUser getAppUser(Subject subject) throws AppUserException;

	/**
	 * Obtiene la información del usuario en la aplicación.
	 * 
	 * @param subject
	 *            Información de la autenticación del usuario.
	 * @return Información del usuario en la aplicación.
	 */
	public static String getUserEntity(Subject subject) {
		String entity = null;

		// Obtener la información del usuario autenticado
		Object[] identifiers = subject.getPrincipals(ArchivoIdentifier.class)
				.toArray();
		for (int i = 0; i < identifiers.length; i++) {

			ArchivoIdentifier pi = (ArchivoIdentifier) identifiers[i];

			if (ArchivoIdentifier.ENTITY.equals(pi.getName())) {
				entity = pi.getValue();
				return entity;
			}
		}

		return null;
	}

	/**
	 * Obtiene el id del usuario en el sistema externo
	 * 
	 * @param subject
	 *            Información del id del usuario en el sistema externo
	 * @return Información del id del usuario en el sistema externo
	 */
	public static String getExternalUserId(Subject subject) {
		String externalUserId = null;

		// Obtener la información del usuario autenticado
		Object[] identifiers = subject.getPrincipals(ArchivoIdentifier.class)
				.toArray();
		for (int i = 0; i < identifiers.length; i++) {

			ArchivoIdentifier pi = (ArchivoIdentifier) identifiers[i];

			if (ArchivoIdentifier.EXTERNAL_USER_ID.equals(pi.getName())) {
				externalUserId = pi.getValue();
				return externalUserId;
			}
		}

		return null;
	}

	/**
	 * Obtiene la información de la key de sesión para multientidad.
	 * 
	 * @param subject
	 *            Información de la autenticación del usuario.
	 * @return Información del usuario en la aplicación.
	 */
	public static String getSessionKey(Subject subject) {
		String session = null;

		// Obtener la información del usuario autenticado
		Object[] identifiers = subject.getPrincipals(ArchivoIdentifier.class)
				.toArray();
		for (int i = 0; i < identifiers.length; i++) {

			ArchivoIdentifier pi = (ArchivoIdentifier) identifiers[i];

			if (ArchivoIdentifier.SESSION.equals(pi.getName())) {
				session = pi.getValue();
				return session;
			}
		}

		return null;
	}

	/**
	 * Obtiene la información de la key de sesión de administración para
	 * multientidad.
	 * 
	 * @param subject
	 *            Información de la autenticación del usuario.
	 * @return Información del usuario en la aplicación.
	 */
	public static String getSessionAdmKey(Subject subject) {
		String session = null;

		// Obtener la información del usuario autenticado
		Object[] identifiers = subject.getPrincipals(ArchivoIdentifier.class)
				.toArray();
		for (int i = 0; i < identifiers.length; i++) {

			ArchivoIdentifier pi = (ArchivoIdentifier) identifiers[i];

			if (ArchivoIdentifier.SESSION_ADM.equals(pi.getName())) {
				session = pi.getValue();
				return session;
			}
		}

		return null;
	}

	/**
	 * Obtiene la información del idioma del usuario para multientidad.
	 * 
	 * @param subject
	 *            Información del idioma del usuario.
	 * @return Información del idioma del usuario.
	 */
	public static String getIdiomaKey(Subject subject) {
		String session = null;

		// Obtener la información del usuario autenticado
		Object[] identifiers = subject.getPrincipals(ArchivoIdentifier.class)
				.toArray();
		for (int i = 0; i < identifiers.length; i++) {

			ArchivoIdentifier pi = (ArchivoIdentifier) identifiers[i];

			if (ArchivoIdentifier.IDIOMA.equals(pi.getName())) {
				session = pi.getValue();
				return session;
			}
		}

		return null;
	}

	/**
	 * Obtiene la información del usuario en la aplicación.
	 * 
	 * @param subject
	 *            Información de la autenticación del usuario.
	 * @return Información del usuario en la aplicación.
	 */
	public static String getUserLogin(Subject subject) {
		String login = null;

		// Obtener la información del usuario autenticado
		Object[] identifiers = subject.getPrincipals(ArchivoIdentifier.class)
				.toArray();
		for (int i = 0; i < identifiers.length; i++) {

			ArchivoIdentifier pi = (ArchivoIdentifier) identifiers[i];

			if (ArchivoIdentifier.USER_LOGIN.equals(pi.getName())) {
				login = pi.getValue();
				return login;
			}
		}

		return null;
	}

	/**
	 * Comprueba la existencia del usuario en el Sistema de Archivo.
	 * 
	 * @param user
	 *            Información del usuario.
	 * @param sessionAdm
	 *            Sesión de administración.
	 * @throws AppUserException
	 *             si ocurre algún error en la validación
	 */
	protected void checkUser(AppUser user, String sessionAdm)
			throws AppUserException {
		if (StringUtils.isNotEmpty(sessionAdm)
				&& (TipoUsuario.ADMINISTRADOR.equals(user.getUserType()))) {
			// Sesión de administración

			// Obtención del repositorio de servicios
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(user));

			// Interfaz de acceso a la gestión de usuarios
			GestionControlUsuariosBI gcu = services
					.lookupGestionControlUsuariosBI();

			// Obtener el nombre del superusuario

			// Obtener la información del usuario en Archivo
			UsuarioVO usuario = gcu.getSuperusuario(user.getName());

			if (usuario == null) {
				// Crear el usuario
				usuario = gcu.crearUsuarioAdministracion(user.getName());
			}

			if (logger.isDebugEnabled())
				logger.debug("UsuarioVO:" + Constants.NEWLINE + usuario);

			// Validar el usuario
			validateInternalUserInfo(usuario);

			// Completar la información del usuario.
			user.copy(usuario);

		} else {
			// Obtención del repositorio de servicios
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(user));

			// Interfaz de acceso a la gestión de usuarios
			GestionControlUsuariosBI gcu = services
					.lookupGestionControlUsuariosBI();

			// Comprobar que exista el usuario en la aplicación
			if ((user != null) && (user.getUserType() == null))
				throw new AppUserException(AppUserException.USER_TYPE_NOT_FOUND);

			// Obtener la información del usuario en Archivo
			UsuarioVO usuario = gcu.getUsuario(user.getUserType(),
					user.getExternalUserId());
			if (logger.isDebugEnabled())
				logger.debug("UsuarioVO:" + Constants.NEWLINE + usuario);

			// Si el usuario es externo y no existe, crearlo
			if ((usuario == null)
					&& TiposUsuario.ORGANO_EXTERNO_VALUE.equals(user
							.getUserType()))
				usuario = createExternalUserInfo(user);

			// Validar el usuario
			validateInternalUserInfo(usuario);

			// Completar la información del usuario.
			user.copy(usuario);

			// Cargar los datos del registro

			if (ConfigConstants.getInstance().getMostrarSalas()
					&& user.isExterno()) {
				GestionSalasConsultaBI gestionSalasConsultaBI = services
						.lookupGestionSalasBI();

				UsuarioSalasConsultaVO usuarioSalasConsultaVO = gestionSalasConsultaBI
						.getUsuarioExternoById(user.getId(), true);

				user.setUsuarioSalasConsultaVO(usuarioSalasConsultaVO);
			}

		}
	}

	/**
	 * Guarda la información del usuario externo si no existe.
	 * 
	 * @param user
	 *            Información del usuario externo.
	 * @return Información del usuario externo.
	 * @throws AppUserException
	 *             si ocurre algún error.
	 */
	protected UsuarioVO createExternalUserInfo(AppUser user)
			throws AppUserException {
		UsuarioVO usuario = null;

		try {

			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((user != null) && (user.getEntity() != null)) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM, user.getEntity());
			}

			// Instancia del conector adecuado para el tipo de usuario
			AuthenticationConnector authClient = AuthenticationConnectorFactory
					.getConnector(user.getUserType(), params);

			// Autenticación del usuario
			UserInfo userInfo = authClient
					.getUserInfo(user.getExternalUserId());
			if (userInfo == null)
				throw new AppUserException(AppUserException.USER_NOT_FOUND);

			// Crear el usuario
			usuario = new UsuarioVO();
			usuario.setNombre(userInfo.getName());
			usuario.setApellidos(userInfo.getSurname());
			usuario.setEmail(userInfo.getEmail());
			usuario.setDireccion(userInfo.getAddress());
			usuario.setTipo(user.getUserType());
			usuario.setHabilitado(Constants.TRUE_STRING);
			usuario.setFMaxVigencia(null);
			usuario.setIdUsrsExtGestor(userInfo.getExternalUserId());
			usuario.setDescripcion(userInfo.getDescription());

			// Interfaz de acceso a la gestión de usuarios
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(user));
			GestionControlUsuariosBI gcu = services
					.lookupGestionControlUsuariosBI();

			// Obtener la información del usuario en Archivo
			usuario = gcu.crearUsuarioExterno(usuario);
			if (logger.isDebugEnabled())
				logger.debug("UsuarioVO creado:" + Constants.NEWLINE + usuario);
		} catch (AppUserException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error al crear al usuario externo", e);
			throw new AppUserException(AppUserException.USER_NOT_FOUND);
		}

		return usuario;
	}

	/**
	 * Valida la información interna del usuario.
	 * 
	 * @param usuario
	 *            Información interna del usuario.
	 * @throws AppUserException
	 *             si ocurre algún error en la validación
	 */
	protected void validateInternalUserInfo(UsuarioVO usuario)
			throws AppUserException {
		// Comprobar que exista el usuario en la aplicación
		if (usuario == null)
			throw new AppUserException(AppUserException.USER_NOT_FOUND);

		// Comprobar que el usuario esté habilitado
		if (!Constants.TRUE_STRING.equals(usuario.getHabilitado()))
			throw new AppUserException(AppUserException.USER_NOT_ACTIVATED);

		// Comprobar la fecha máxima de vigencia
		if ((usuario.getFMaxVigencia() != null)
				&& usuario.getFMaxVigencia().before(new Date()))
			throw new AppUserException(AppUserException.USER_EXPIRED);
	}

	/**
	 * Añade la lista de permisos del usuario.
	 * 
	 * @param user
	 *            Información del usuario.
	 * @throws AppUserException
	 *             si ocurre algún error en la validación
	 */
	protected void addUserPermissions(AppUser user) throws AppUserException {
		if (TipoUsuario.ADMINISTRADOR.equals(user.getUserType())) {
			// Permiso de administración total del sistema
			user.setPermissions(new String[] { AppPermissions.ADMINISTRACION_TOTAL_SISTEMA });
		} else {
			// Lista de permisos
			List permissions = new ArrayList();

			// Interfaz de acceso a la gestión de usuarios
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(user));
			GestionControlUsuariosBI gcu = services
					.lookupGestionControlUsuariosBI();

			// Obtener los roles del usuario
			List roles = gcu.getRolesUsuario(user.getId());

			// Añadir los permisos de los roles
			if (roles != null) {
				for (int i = 0; i < roles.size(); i++) {
					// Recuperar el rol
					RolVO rol = (RolVO) roles.get(i);
					if (logger.isDebugEnabled())
						logger.debug("Rol:" + Constants.NEWLINE + rol);

					// Añadir el rol
					user.getRolesList().add(rol);

					// Obtener los permisos del rol
					List permisos = gcu.getPermisosRol(rol.getId());
					for (int j = 0; (permisos != null) && (j < permisos.size()); j++) {
						PermisoVO permiso = (PermisoVO) permisos.get(j);
						if (logger.isDebugEnabled())
							logger.debug("Permiso:" + Constants.NEWLINE
									+ permiso);

						// Añadir el permiso
						permissions.add("" + permiso.getPerm());
					}
				}
			}

			// Comprobar que el usuario tenga permisos
			if (permissions.size() == 0)
				throw new AppUserException(
						AppUserException.USER_WITH_NO_PERMISSIONS);

			// Ordenar la lista de permisos
			String[] permissionsArray = (String[]) permissions
					.toArray(new String[permissions.size()]);
			Arrays.sort(permissionsArray);

			user.setPermissions(permissionsArray);
		}
	}

	/**
	 * Añade la lista de grupos del usuario.
	 * 
	 * @param user
	 *            Información del usuario.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	protected void addUserGroups(AppUser user) {
		// Interfaz de acceso a la gestión de usuarios
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(user));
		GestionControlUsuariosBI gcu = services
				.lookupGestionControlUsuariosBI();

		List grupos = null;

		if (TipoUsuario.ADMINISTRADOR.equals(user.getUserType())) {

			// Obtener todos los grupos
			grupos = gcu.getGrupos();

		} else {

			// Obtener los grupos del usuario
			grupos = gcu.getGruposUsuario(user.getId());

		}

		if (grupos != null) {
			boolean mostrarPaisProvincia = true;
			boolean mostrarArchivoCodigoClasificadores = true;
			// Añadir los grupos
			for (int i = 0; i < grupos.size(); i++) {
				// Recuperar el grupo
				GrupoVO grupo = (GrupoVO) grupos.get(i);
				if (logger.isDebugEnabled())
					logger.debug("GrupoVO:" + Constants.NEWLINE + grupo);

				// Añadir el grupo
				if (!TipoUsuario.ADMINISTRADOR.equals(user.getUserType())) {
					user.getGroupList().add(grupo);
					// Comprobar que partes del codigo de referencia se muestran
					// al usuario
					if (mostrarPaisProvincia && grupo.isOcultarPaisProvincia())
						mostrarPaisProvincia = false;
					if (mostrarArchivoCodigoClasificadores
							&& grupo.isOcultarArchivoCodigoClasificadores())
						mostrarArchivoCodigoClasificadores = false;
				}

				// Añadir el identificador de archivo
				if (StringUtils.isNotBlank(grupo.getIdArchivo())) {
					user.getCustodyArchiveList().add(grupo.getIdArchivo());
					user.setUsuarioArchivo(true);
				}

				// Añadir las partes del codigo de referencia a mostrar
				user.addParteCodigoReferencia(AppUser.MOSTRAR_PAIS_PROVINCIA,
						mostrarPaisProvincia);
				user.addParteCodigoReferencia(
						AppUser.MOSTRAR_ARCHIVO_CODIGO_CLASIFICADOR,
						mostrarArchivoCodigoClasificadores);
			}
		}
	}

	/**
	 * Establece el nivel de auditoría del usuario.
	 * 
	 * @param user
	 *            Información del usuario.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	protected void setLogLevel(AppUser user) {
		// Interfaz de acceso a la gestión de auditoría
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(user));
		GestionAuditoriaBI serviceAuditoria = services
				.lookupGestionAuditoriaBI();

		// Establecer el nivel de auditoría
		user.setLogLevel(serviceAuditoria.getLogLevel(user));
	}

	/**
	 * Establece la información de la organización a la que pertenece el
	 * usuario.
	 * 
	 * @param user
	 *            Información del usuario.
	 * @throws AppUserException
	 *             si ocurre algún error en la validación
	 */
	protected void setOrganizationInfo(AppUser user, String entity)
			throws AppUserException {
		if (!TipoUsuario.ADMINISTRADOR.equals(user.getUserType())) {
			try {
				// Comprobar si el usuario pertenece a algún órgano
				if (StringUtils.isNotBlank(user.getOrganizationUserId())) {

					// Obtener la entidad para el usuario conectado
					Properties params = null;

					if (StringUtils.isNotEmpty(entity)) {
						params = new Properties();
						params.put(MultiEntityConstants.ENTITY_PARAM, entity);
					}

					// Cargar el conector con el Sistema Gestor de Organización
					GestorOrganismos oc = GestorOrganismosFactory
							.getConnectorByUserType(user.getUserType(), params);
					if (oc != null) {
						// Recuperar el órgano al que pertenece el usuario
						InfoOrgano organo = oc.recuperarOrganodeUsuario(user
								.getOrganizationUserId());
						if (organo != null) {
							// Información del Sistema Gestor de Organismos en
							// la configuración de Archivo
							ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
									.getConfiguracionSistemaArchivo();
							SistemaGestorOrganismos sgo = csa
									.findSistemaGestorOrganismosByUserType(user
											.getUserType());

							ServiceRepository services = ServiceRepository
									.getInstance(ServiceClient.create(user));
							GestionControlUsuariosBI gcu = services
									.lookupGestionControlUsuariosBI();

							if (!sgo.isInterno()) {
								// Obtener el órgano del usuario en archivo
								CAOrganoVO organoUsuario = gcu
										.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
												sgo.getId(), organo.getId(),
												Constants.TRUE_STRING);

								// Añadir el órgano del usuario en archivo
								user.setOrganization(organoUsuario);

								// Obtener los organos dependientes
								List orgDepExtList = oc
										.recuperarOrganosDependientes(
												organo.getId(), 0);
								if ((orgDepExtList != null)
										&& (orgDepExtList.size() > 0)) {
									CAOrganoVO org;
									for (int i = 0; i < orgDepExtList.size(); i++) {
										org = gcu
												.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
														sgo.getId(),
														((InfoOrgano) orgDepExtList
																.get(i))
																.getId(),
														Constants.TRUE_STRING);

										if (org != null)
											user.getDependentOrganizationList()
													.add(org);
									}
								}

								// Obtener los organos antecesores
								List orgAntExtList = oc
										.recuperarOrganosAntecesores(
												organo.getId(), 0);
								if ((orgAntExtList != null)
										&& (orgAntExtList.size() > 0)) {
									CAOrganoVO org;
									for (int i = orgAntExtList.size() - 1; i >= 0; i--) {
										org = gcu
												.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
														sgo.getId(),
														((InfoOrgano) orgAntExtList
																.get(i))
																.getId(),
														Constants.TRUE_STRING);

										if (org != null)
											user.getAncestorOrganizationList()
													.add(org);
									}
								}

								// Comprobar el nombre largo del órgano del
								// usuario
								gcu.checkNombreLargoOrgano(organoUsuario,
										organo, orgAntExtList);
							} else {
								// Añadir el órgano del usuario en archivo
								user.setOrganization(gcu
										.getCAOrgProductorVOXId(organo.getId()));
								// componer la lista de antecesores de la
								// organizacion, a partir del nombre largo
								// utilizando como caracter separador '/'
								// (SLASH)
								String[] strOrganizationAncestors = user
										.getOrganization()
										.getNombreLargo()
										.split(Constants.SEPARADOR_ANTECESORES_ORGANO);
								CAOrganoVO organoAncestor = null;
								for (int i = 1; i < strOrganizationAncestors.length; i++) {
									if (!StringUtils
											.isEmpty(strOrganizationAncestors[i])) {
										organoAncestor = new CAOrganoVO();
										organoAncestor
												.setNombre(strOrganizationAncestors[i]);
										user.getAncestorOrganizationList().add(
												organoAncestor);
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error(
						"Error al recuperar la información de organizaci\u00F3n del usuario: "
								+ user, e);
				throw new AppUserException(
						AppUserException.USER_ORGANIZATION_ERROR,
						new String[] { e.getLocalizedMessage() });
			}
		} else {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(user));
			GestionControlUsuariosBI gcu = services
					.lookupGestionControlUsuariosBI();

			// Añadir el órgano del usuario en archivo
			CAOrganoVO organoVO = gcu.getOrganoUsuarioEnArchivo(user.getId());
			if (organoVO != null) {
				user.setOrganization(organoVO);
				// componer la lista de antecesores de la organizacion, a partir
				// del nombre largo
				// utilizando como caracter separador '/' (SLASH)
				String[] strOrganizationAncestors = user.getOrganization()
						.getNombreLargo()
						.split(Constants.SEPARADOR_ANTECESORES_ORGANO);
				CAOrganoVO organoAncestor = null;
				for (int i = 1; i < strOrganizationAncestors.length; i++) {
					if (!StringUtils.isEmpty(strOrganizationAncestors[i])) {
						organoAncestor = new CAOrganoVO();
						organoAncestor.setNombre(strOrganizationAncestors[i]);
						user.getAncestorOrganizationList().add(organoAncestor);
					}
				}
			}
		}
	}

	/**
	 * Establece las listas de control de acceso del usuario.
	 * 
	 * @param user
	 *            Información del usuario.
	 * @throws AppUserException
	 *             si ocurre algún error.
	 */
	protected void setAccessControlLists(AppUser user) throws AppUserException {
		if (!TipoUsuario.ADMINISTRADOR.equals(user.getUserType())) {
			// Lista de permisos
			Map acls = new HashMap();

			// Interfaz de acceso a la gestión de usuarios
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(user));
			GestionControlUsuariosBI gcu = services
					.lookupGestionControlUsuariosBI();

			// Obtener las listas de acceso
			List listasAcceso = gcu
					.getListasAcceso(getDestinatariosPermisoLista(user));

			// Añadir las listas de acceso
			if (listasAcceso != null) {
				PermisosListaVO listaAcceso;
				List permisosLista;
				String permiso;

				for (int i = 0; i < listasAcceso.size(); i++) {
					// Recuperar el rol
					listaAcceso = (PermisosListaVO) listasAcceso.get(i);
					if (logger.isDebugEnabled())
						logger.debug("PermisosListaVO: " + Constants.NEWLINE
								+ listaAcceso);

					// Permiso de la lista
					permiso = new Integer(listaAcceso.getPerm()).toString();

					// Comprobar si ya está añadida
					permisosLista = (List) acls.get(listaAcceso.getIdListCA());
					if (CollectionUtils.isEmpty(permisosLista)) {
						// Crear lista de permisos
						permisosLista = new ArrayList();
						permisosLista.add(permiso);

						// Añadir lista ACL con su permiso
						acls.put(listaAcceso.getIdListCA(), permisosLista);
					} else {
						// Añadir permiso a lista existente
						if (!permisosLista.contains(permiso))
							permisosLista.add(permiso);
					}
				}
			}

			user.setAcls(acls);
		}
	}

	/**
	 * Obtiene la lista de destinatarios de los permisos de una lista de control
	 * de acceso.
	 * 
	 * @param user
	 *            Información del usuario.
	 * @return Lista de destinatarios.
	 */
	protected List getDestinatariosPermisoLista(AppUser user) {
		List destinatarios = new LinkedList();
		DestinatarioPermisosListaVO destinatario = null;

		// Añadir el usuario
		destinatario = new DestinatarioPermisosListaVO(TipoDestinatario.USUARIO);
		destinatario.getIdDestList().add(user.getId());
		destinatarios.add(destinatario);

		// Añadir órgano al que pertenece el usuario
		if (user.getOrganization() != null) {
			destinatario = new DestinatarioPermisosListaVO(
					TipoDestinatario.ORGANO);
			destinatario.getIdDestList().add(user.getOrganization().getIdOrg());
			destinatarios.add(destinatario);
		}

		// Añadir órganos dependientes del órgano del usuario
		if (user.getDependentOrganizationList().size() > 0) {
			destinatario = new DestinatarioPermisosListaVO(
					TipoDestinatario.ORGANO);
			for (int i = 0; i < user.getDependentOrganizationList().size(); i++) {
				CAOrganoVO organo = (CAOrganoVO) user
						.getDependentOrganizationList().get(i);
				destinatario.getIdDestList().add(organo.getIdOrg());
			}
			destinatarios.add(destinatario);
		}

		// Añadir los grupos del usuario
		if (user.getGroupList().size() > 0) {
			destinatario = new DestinatarioPermisosListaVO(
					TipoDestinatario.GRUPO);
			for (int i = 0; i < user.getGroupList().size(); i++) {
				GrupoVO grupo = (GrupoVO) user.getGroupList().get(i);
				destinatario.getIdDestList().add(grupo.getId());
			}
			destinatarios.add(destinatario);
		}

		return destinatarios;
	}
}
