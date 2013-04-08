/*
 * Created on 21-mar-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.exception.UserErrorCodes;
import ieci.tecdoc.idoc.admin.api.user.AplicacionPerfil;
import ieci.tecdoc.idoc.admin.api.user.Group;
import ieci.tecdoc.idoc.admin.api.user.GroupUserRel;
import ieci.tecdoc.idoc.admin.api.user.Groups;
import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.api.user.UserAccess;
import ieci.tecdoc.idoc.admin.api.user.UserData;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.idoc.admin.internal.GroupImpl;
import ieci.tecdoc.idoc.admin.internal.GrpUsrRelImpl;
import ieci.tecdoc.idoc.admin.internal.UserProfileImpl;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.access.UserConnectedDTO;
import ieci.tecdoc.mvc.dto.adminUser.bd.GroupDTO;
import ieci.tecdoc.mvc.dto.adminUser.bd.GroupListDTO;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.adminUser.bd.UserForm;
import ieci.tecdoc.mvc.service.adminUser.ServiceCertificate;
import ieci.tecdoc.mvc.service.adminUser.ServiceCommon;
import ieci.tecdoc.mvc.util.SessionHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio Mar�a
 * 
 */
public class UserEdit extends BaseAction {

	private static Logger logger = Logger.getLogger(UserEdit.class);

	public ActionForward executeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String entidad = SessionHelper.getEntidad(request);

		HttpSession session = request.getSession(false);
		UserForm userForm = (UserForm) form;

		int userId = getUserId(request);
		String objId = request.getParameter("id");
		int idUsuario = Integer.parseInt(objId);
		String submited = request.getParameter("submitted");

		List grupos = getGroupList(userId, idUsuario, entidad);

		GroupListDTO groupList = new GroupListDTO();
		groupList.setGrupos(grupos);
		request.setAttribute("gruposListDTO", groupList);

		if (submited == null) {
			UserConnectedDTO userDTO = new UserConnectedDTO();
			cargarUsuario(session, userForm, userDTO, userId, idUsuario,
					entidad);
			request.setAttribute("userDTO", userDTO);
			return mapping.findForward("success");
		} else {
			UserAccess userAccess = ObjFactory.createUserAccess();
			boolean userCanEditUser = userAccess.userCanEditUser(userId,
					idUsuario, entidad);
			if (!userCanEditUser)
				throw new IeciTdException(
						String.valueOf(MvcError.EC_NOT_CAN_EDIT_USER), null);
			guardarDatos(session, userForm, userId, idUsuario, entidad);

			request.setAttribute("tipo", "Usuario");

			User user = ObjFactory.createUser();
			user.load(idUsuario, false, entidad);
			String idDept = Integer.toString(user.getDeptId());

			request.setAttribute("id", idDept);

			return mapping.findForward("ok");
		}

	}

	public void guardarDatos(HttpSession session, UserForm userForm,
			int userId, int idUsuario, String entidad) throws Exception {

		// General
		User user = ObjFactory.createUser(userId, Defs.NULL_ID);
		user.load(idUsuario, entidad);
		user.setPwdmbc(userForm.isPwdmbc());
		user.setPwdvpcheck(userForm.isPwdvpcheck());

		user.setName(userForm.getNombre());
		user.setDescription(userForm.getDescripcion());
		String pwd = userForm.getPwd();
		if (pwd.indexOf("*") == -1) // Cambio la contrase�a, si se ha
									// modificado
									// el <input type="passwd"
			user.setPassword(pwd);

		// Guardar grupos asociados
		String[] gruposAsignados = (String[]) session
				.getAttribute("gruposAsignados");
		String[] gruposSeleccionados = userForm.getGruposAsignados();
		String[] gruposParaAsignar = diferencia(gruposSeleccionados,
				gruposAsignados);
		String[] gruposParaEliminar = diferencia(gruposAsignados,
				gruposSeleccionados);

		// session.removeAttribute("gruposAsignados");
		session.setAttribute("gruposAsignados", gruposSeleccionados);
		asignarUsuarioAGrupo(userId, idUsuario, gruposParaAsignar, entidad);
		quitarUsuarioDeGrupos(userId, idUsuario, gruposParaEliminar, entidad);

		// Guardar datos personales
		user.getUserDataImpl().setId(user.getId());
		user.getUserDataImpl().setCargo(userForm.getCargo());
		user.getUserDataImpl().setEmail(userForm.getEmail());
		user.getUserDataImpl().setTfnoMovil(userForm.getTfnoMovil());
		user.getUserDataImpl().setIdCert(userForm.getIdCert());
		user.getUserDataImpl().setApellidos(userForm.getApellidos());
		user.getUserDataImpl().setNombre(userForm.getNombrePersonal());

		// Perfiles del usuario
		// user.resetProfiles();
		Map profilesMap = new HashMap();
		UserProfiles profiles = user.getProfiles();
		if (profiles != null) {

			for (int i = 0; i < profiles.count(); i++) {

				UserProfile userProfile = profiles.get(i);
				profilesMap.put(new Integer(userProfile.getProduct()),
						userProfile);
			}
		}

		// Perfiles modificables en el formulario
		Collection listaPerfilesAplicacion = userForm.getListaAplicaciones();
		for (Iterator iter = listaPerfilesAplicacion.iterator(); iter.hasNext();) {

			AplicacionPerfil element = (AplicacionPerfil) iter.next();
			if ((element.isEditableSinPermisos())
					|| (element.isEditableUsuarioEstandar())
					|| (element.isEditableUsuarioAdministrador())) {

				int idAplicacion = Integer.parseInt(element.getIdetificador());
				profilesMap.put(new Integer(idAplicacion), new UserProfileImpl(
						idUsuario, idAplicacion, element.getPerfil()));
			}
		}

		// Actualizar perfiles
		user.resetProfiles();
		profiles = user.getProfiles();
		Iterator it = profilesMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry e = (Entry) it.next();
			profiles.add((UserProfile) e.getValue());
		}

		/*
		 * UserProfiles profiles = user.getProfiles();
		 * 
		 * // Sistema UserProfile profile =
		 * profiles.getProductProfile(UserDefs.PRODUCT_SYSTEM); if
		 * (userForm.isSystemSuperuser())
		 * profile.setProfile(UserDefs.PROFILE_SUPERUSER); else
		 * profile.setProfile(UserDefs.PROFILE_NONE);
		 * 
		 * // Administrador de Usuarios profile =
		 * profiles.getProductProfile(UserDefs.PRODUCT_USER); String perfiles =
		 * userForm.getUser(); if (perfiles.equals("superuser"))
		 * profile.setProfile(UserDefs.PROFILE_SUPERUSER); else if
		 * (perfiles.equals("manager"))
		 * profile.setProfile(UserDefs.PROFILE_MANAGER); else if
		 * (perfiles.equals("standard"))
		 * profile.setProfile(UserDefs.PROFILE_STANDARD); else if
		 * (perfiles.equals("none")) profile.setProfile(UserDefs.PROFILE_NONE);
		 * 
		 * // InvesDoc profile =
		 * profiles.getProductProfile(UserDefs.PRODUCT_IDOC); perfiles =
		 * userForm.getIdoc(); if (perfiles.equals("superuser"))
		 * profile.setProfile(UserDefs.PROFILE_SUPERUSER); else if
		 * (perfiles.equals("manager"))
		 * profile.setProfile(UserDefs.PROFILE_MANAGER); else if
		 * (perfiles.equals("standard"))
		 * profile.setProfile(UserDefs.PROFILE_STANDARD); else if
		 * (perfiles.equals("none")) profile.setProfile(UserDefs.PROFILE_NONE);
		 * 
		 * // Administrador de vol�menes profile =
		 * profiles.getProductProfile(UserDefs.PRODUCT_VOLUME); if
		 * (userForm.isVolumeSuperuser())
		 * profile.setProfile(UserDefs.PROFILE_SUPERUSER); else
		 * profile.setProfile(UserDefs.PROFILE_NONE);
		 */

		// Permisos
		int permisos = 0;

		if (userForm.isIdocConsulta())
			permisos += UserDefs.PERMISSION_QUERY;
		if (userForm.isIdocModificacion())
			permisos += UserDefs.PERMISSION_UPDATE;
		if (userForm.isIdocCreacion())
			permisos += UserDefs.PERMISSION_CREATION;
		if (userForm.isIdocBorrado())
			permisos += UserDefs.PERMISSION_DELETION;
		if (userForm.isIdocImpresion())
			permisos += UserDefs.PERMISSION_PRINTING;

		Permissions perms = user.getPermissions();
		Permission perm = perms.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos
																				// sobre
																				// InvesDoc
		perm.setPermission(permisos);

		/*
		 * // Certificado if (useCertificate()){ ServiceCertificate service =
		 * ServiceCertificate.getInstance(); service.updateIdCert(user.getId(),
		 * userForm.getIdCert(), null, entidad); }
		 */

		user.store(entidad); // Guardar cambios

		if (logger.isDebugEnabled())
			logger.debug("Datos guardados");

	}

	public void cargarUsuario(HttpSession session, UserForm userForm,
			UserConnectedDTO userDTO, int userId, int idUsuario, String entidad)
			throws Exception {
		User user = ObjFactory.createUser(userId, Defs.NULL_ID);
		user.load(idUsuario, entidad);

		UserProfiles profiles = null;
		UserProfile profile = null;

		// General
		userForm.setNombre(user.getName());
		userForm.setId(user.getId());
		userForm.setPwd("****");
		userForm.setRepwd("****");
		userForm.setPwdvpcheck(user.getPwdvpcheck());
		userForm.setPwdmbc(user.getPwdmbc());
		userForm.setDescripcion(user.getDescription());

		// Datos Personales
		UserData userData = user.getUserDataImpl();
		if (userData != null) {
			userForm.setCargo(userData.getCargo());
			userForm.setEmail(userData.getEmail());
			userForm.setTfnoMovil(userData.getTfnoMovil());
			userForm.setIdCert(userData.getIdCert());
			userForm.setApellidos(userData.getApelliidos());
			userForm.setNombrePersonal(userData.getNombre());

		}

		UserProfiles userProfiles = user.getProfiles();
		Collection listaAplicacionesModificada = new ArrayList();
		Collection listaAplicacionesDefecto = userForm.getListaAplicaciones();
		for (Iterator iter = listaAplicacionesDefecto.iterator(); iter
				.hasNext();) {
			AplicacionPerfil aplicacionPerfil = (AplicacionPerfil) iter.next();
			try {
				UserProfile userProfile = userProfiles
						.getProductProfile(Integer.parseInt(aplicacionPerfil
								.getIdetificador()));
				aplicacionPerfil.setPerfil(userProfile.getProfile());
			} catch (IeciTdException ieciEx) {
				if (!ieciEx
						.getErrorCode()
						.equals(Long
								.toString(UserErrorCodes.EC_USER_NOT_PROFILE_FOR_PRODUCT))) {
					throw ieciEx;
				}
			}
			listaAplicacionesModificada.add(aplicacionPerfil);
		}
		userForm.setListaAplicaciones(listaAplicacionesModificada);

		userDTO.setId(idUsuario);
		userDTO.setUserName(user.getName());

		// Cargar datos de grupos

		String[] gruposAsignados = getGruposAsignados(idUsuario, entidad);
		session.setAttribute("gruposAsignados", gruposAsignados);
		userForm.setGruposAsignados(gruposAsignados);

		// Perfiles
		profiles = user.getProfiles();

		// Sistema

		profile = profiles.getProductProfile(UserDefs.PRODUCT_SYSTEM);
		Map _profiles = new HashMap();

		if (profile.getProfile() == UserDefs.PROFILE_SUPERUSER) { // Si es super
																	// usuario
																	// de
																	// sistema
																	// activar
																	// todos los
																	// permisos
																	// y listo

			userDTO.setIsSystemSuperUser(true);
			userDTO.setHasAccessSys(true);
			userDTO.setHasAccessUser(true);
			userDTO.setHasAccessVol(true);

			_profiles.put(String.valueOf(UserDefs.PRODUCT_IDOC), new Integer(
					UserDefs.PROFILE_SUPERUSER));
			_profiles.put(String.valueOf(UserDefs.PRODUCT_VOLUME), new Integer(
					UserDefs.PROFILE_SUPERUSER));
			_profiles.put(String.valueOf(UserDefs.PRODUCT_USER), new Integer(
					UserDefs.PROFILE_SUPERUSER));

			userForm.setSystemSuperuser(true);
			userForm.setUser("superuser");
			userForm.setIdoc("superuser");
			userForm.setVolumeSuperuser(true);

			userForm.setIdocConsulta(true);
			userForm.setIdocModificacion(true);
			userForm.setIdocCreacion(true);
			userForm.setIdocBorrado(true);
			userForm.setIdocImpresion(true);
		} else {
			// User
			profile = profiles.getProductProfile(UserDefs.PRODUCT_USER);
			_profiles.put(String.valueOf(UserDefs.PRODUCT_USER), new Integer(
					profile.getProfile()));

			if (profile.getProfile() == UserDefs.PROFILE_NONE) {
				userForm.setUser("none");
			} else {
				if (profile.getProfile() == UserDefs.PROFILE_SUPERUSER) {
					userForm.setUser("superuser");
				} else if (profile.getProfile() == UserDefs.PROFILE_MANAGER) {
					userForm.setUser("manager");
				} else if (profile.getProfile() == UserDefs.PROFILE_STANDARD) {
					userForm.setUser("standard");
				}
			}
			// Invesdoc
			profile = profiles.getProductProfile(UserDefs.PRODUCT_IDOC);
			_profiles.put(String.valueOf(UserDefs.PRODUCT_IDOC), new Integer(
					profile.getProfile()));

			if (profile.getProfile() == UserDefs.PROFILE_NONE) {
				userForm.setIdoc("none"); // Por defecto el formulario se crea
											// con los permisos a false
			} else {
				if (profile.getProfile() == UserDefs.PROFILE_SUPERUSER) { // Si
																			// es
																			// manager
																			// o
																			// administrador
																			// no
																			// leo
																			// los
																			// permisos
																			// del
																			// ldap,
																			// deben
																			// estar
																			// todos
																			// activos
					userForm.setIdoc("superuser");
					userForm.setIdocConsulta(true);
					userForm.setIdocModificacion(true);
					userForm.setIdocCreacion(true);
					userForm.setIdocBorrado(true);
					userForm.setIdocImpresion(true);
				} else if (profile.getProfile() == UserDefs.PROFILE_MANAGER) {
					userForm.setIdoc("manager");
					userForm.setIdocConsulta(true);
					userForm.setIdocModificacion(true);
					userForm.setIdocCreacion(true);
					userForm.setIdocBorrado(true);
					userForm.setIdocImpresion(true);
				} else if (profile.getProfile() == UserDefs.PROFILE_STANDARD) {
					userForm.setIdoc("standard");
					// Tengo que leer los permisos asociados a invesdoc, pero se
					// que al menos puede consultar
					userForm.setIdocConsulta(true);
					Permissions perms = user.getPermissions();
					Permission perm = perms
							.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos
																			// sobre
																			// InvesDoc
					int permisos = perm.getPermission();
					if ((permisos & UserDefs.PERMISSION_UPDATE) == UserDefs.PERMISSION_UPDATE) {
						userForm.setIdocModificacion(true);
					}
					if ((permisos & UserDefs.PERMISSION_CREATION) == UserDefs.PERMISSION_CREATION) {
						userForm.setIdocCreacion(true);
					}
					if ((permisos & UserDefs.PERMISSION_DELETION) == UserDefs.PERMISSION_DELETION) {
						userForm.setIdocBorrado(true);
					}
					if ((permisos & UserDefs.PERMISSION_PRINTING) == UserDefs.PERMISSION_PRINTING) {
						userForm.setIdocImpresion(true);
					}
				}
			}
			// Administrador de vol�menes
			profile = profiles.getProductProfile(UserDefs.PRODUCT_VOLUME);
			_profiles.put(String.valueOf(UserDefs.PRODUCT_VOLUME), new Integer(
					profile.getProfile()));
			if (profile.getProfile() == UserDefs.PROFILE_SUPERUSER) {
				userForm.setVolumeSuperuser(true);
			}
		}

		// Certificado
		if (useCertificate()) {
			ServiceCertificate service = ServiceCertificate.getInstance();
			userForm.setIdCert(service.getIdCert(user.getId(), entidad));
		}

		userDTO.setProfiles(_profiles);
	}

	/**
	 * @param id
	 * @return
	 */
	private String[] getGruposAsignados(int id, String entidad)
			throws Exception {

		GroupUserRel gur = new GrpUsrRelImpl();
		List gruposList = gur.loadUserGroupIds(id, entidad);
		Iterator it = gruposList.iterator();
		int n = gruposList.size();
		String s[] = new String[n];
		int i = 0;
		while (it.hasNext()) {
			String groupId = it.next().toString();
			Group group = new GroupImpl();
			try {
				// Es posible que el grupo asociado al usuario no exista
				group.load(Integer.valueOf(groupId).intValue(), entidad);
				s[i++] = groupId;
			} catch (Exception e) {
				logger.warn("El grupo ["
						+ groupId
						+ "] ya no existe pero esta presente en la tabla de relacion usuarios-grupos");
			}			
		}
		return s;
	}

	public List getGroupList(int userId, int idUsuario, String entidad)
			throws Exception {

		User user = ObjFactory.createUser();
		List list = user.getGroupsIdMgr(userId, entidad); // Lista de grupos de
															// los cuales el
															// user connected es
															// manager

		Groups grupos = new Groups();
		grupos.loadLite(entidad);
		int n = grupos.count();
		Group grupo;
		List groupList = new ArrayList();
		for (int i = 0; i < n; i++) {
			grupo = grupos.getGroup(i);
			int id = grupo.getId();
			String name = grupo.getName();
			GroupDTO groupDTO = new GroupDTO(id, name);
			if (!list.contains(new Integer(id))) {
				groupDTO.setIsDisabled(true);
			}
			groupList.add(groupDTO);
		}
		return groupList;
	}

	public List getListaGrupos(int idUsuario, String entidad) throws Exception {
		List listaGrupos = new ArrayList();
		User u = ObjFactory.createUser();
		List groupsIdList = u.getGroupsIdMgr(idUsuario, entidad);

		Iterator it = groupsIdList.iterator();

		while (it.hasNext()) {
			Integer id = (Integer) it.next();
			String name = ServiceCommon
					.getNameGroupById(id.intValue(), entidad);
			GroupDTO g = new GroupDTO(id.intValue(), name);
			listaGrupos.add(g);
		}
		return listaGrupos;
	}

	public void quitarUsuarioDeGrupos(int userId, int idUsuario,
			String[] grupos, String entidad) throws Exception {
		GroupUserRel gur = new GrpUsrRelImpl();
		int n = grupos.length;
		UserAccess userAccess = ObjFactory.createUserAccess();
		for (int i = 0; i < n; i++) {
			int groupId = Integer.parseInt(grupos[i]);

			boolean userCanDeleteGroupUser = userAccess.userCanDeleteGroupUser(
					userId, groupId, entidad);
			if (!userCanDeleteGroupUser)
				throw new IeciTdException(
						String.valueOf(MvcError.EC_NOT_CAN_DELETE_GROUP_USER),
						null);

			gur.deleteGroupUser(groupId, idUsuario, entidad);
		}
	}

	public void asignarUsuarioAGrupo(int userId, int idUsuario,
			String[] grupos, String entidad) throws Exception {
		GroupUserRel gur = new GrpUsrRelImpl();
		int n = grupos.length;
		UserAccess userAccess = ObjFactory.createUserAccess();
		for (int i = 0; i < n; i++) {
			int groupId = Integer.parseInt(grupos[i]);
			boolean userCanAssingUserToGroup = userAccess
					.userCanAssingUserToGroup(userId, idUsuario, groupId,
							entidad);
			if (!userCanAssingUserToGroup)
				throw new IeciTdException(
						String.valueOf(MvcError.EC_NOT_CAN_ASSING_USER_TO_GROUP),
						null);
			gur.assignUserToGroup(groupId, idUsuario, entidad);
		}
	}

	private String[] diferencia(String[] v1, String[] v2) {
		TreeSet resul = new TreeSet();
		int n = v1.length;
		int i;
		for (i = 0; i < n; i++) {
			String s = v1[i];
			if (s != null && !busca(s, v2))
				resul.add(s);

		}
		int nresul = resul.size();

		String r[] = new String[nresul];
		Iterator it = resul.iterator();
		i = 0;
		while (it.hasNext()) {
			r[i] = (String) it.next();
			i++;
		}

		return r;

	}

	public boolean busca(String obj, String[] vector) {
		boolean enc = false;
		int n = vector.length;
		int i = 0;
		while (i < n && !enc) {
			if (vector[i] != null && vector[i].equals(obj))
				enc = true;
			i++;
		}
		return enc;
	}

}