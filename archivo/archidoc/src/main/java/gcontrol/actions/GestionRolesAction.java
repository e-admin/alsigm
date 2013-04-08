package gcontrol.actions;

import gcontrol.ControlAccesoConstants;
import gcontrol.forms.RolForm;
import gcontrol.vos.PermisoVO;
import gcontrol.vos.RolVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;
import util.ErrorsTag;

import common.Constants;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionControlUsuariosBI;
import common.bi.ServiceRepository;
import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.view.ErrorKeys;

/**
 * Controlador de las acciones incluidas en la gestión de perfiles
 */
public class GestionRolesAction extends BaseAction {

	public void listaRolesExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RolForm rolForm = (RolForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();
		int[] modules = null;
		if (rolForm.getModulo() > 0)
			modules = new int[] { rolForm.getModulo() };
		List listaRoles = userBI.getRoles(modules);
		request.setAttribute(ControlAccesoConstants.LISTA_ROLES, listaRoles);
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.USUARIOS_LISTA_ROLES, request);
		invocation.setAsReturnPoint(true);
		request.setAttribute(ControlAccesoConstants.LISTA_MODULOS,
				ArchivoModules.allModules());
		setReturnActionFordward(request, mappings.findForward("lista_roles"));
	}

	public void verRolExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RolForm rolForm = (RolForm) form;
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.USUARIOS_INFO_ROL, request);
		invocation.setAsReturnPoint(true);
		removeInTemporalSession(request, ControlAccesoConstants.INFO_ROL);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();
		RolVO rol = (RolPO) RolToPO.getInstance(services).transform(
				userBI.getRol(rolForm.getIdRol()));
		setInTemporalSession(request, ControlAccesoConstants.INFO_ROL, rol);

		setReturnActionFordward(request, mappings.findForward("info_rol"));
	}

	public void altaRolExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.USUARIOS_ALTA_ROL, request);
		invocation.setAsReturnPoint(true);
		setInTemporalSession(request, ControlAccesoConstants.LISTA_MODULOS,
				ArchivoModules.allModules());
		setReturnActionFordward(request, mappings.findForward("edicion_rol"));
	}

	public void edicionRolExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RolForm rolForm = (RolForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();
		RolPO rol = (RolPO) RolToPO.getInstance(services).transform(
				userBI.abrirRol(rolForm.getIdRol()));
		rolForm.setIdRol(rol.getId());
		rolForm.setModulo(rol.getTipoModulo());
		rolForm.setNombre(rol.getNombre());
		rolForm.setDescripcion(rol.getDescripcion());
		saveCurrentInvocation(KeysClientsInvocations.USUARIOS_EDICION_ROL,
				request);
		request.setAttribute(ControlAccesoConstants.INFO_ROL, rol);
		setReturnActionFordward(request, mappings.findForward("edicion_rol"));
	}

	public void seleccionPermisosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RolForm rolForm = (RolForm) form;
		request.setAttribute(ControlAccesoConstants.LISTA_PERMISOS,
				AppPermissions.getPermisosModulo(rolForm.getModulo()));
		ClientInvocation invocation = getInvocationStack(request)
				.getLastClientInvocation();
		saveCurrentInvocation(invocation.getKeyClient(), request);
		setReturnActionFordward(request, mappings.findForward("edicion_rol"));
	}

	ActionErrors validateForm(ActionForm form) {
		RolForm rolForm = (RolForm) form;
		ActionErrors errors = new ActionErrors();
		if (GenericValidator.isBlankOrNull(rolForm.getNombre())) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					ErrorKeys.NECESARIO_NOMBRE_ROL));
		}
		return errors.size() > 0 ? errors : null;
	}

	public void guardarRolExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RolForm rolForm = (RolForm) form;
		ActionErrors errors = null;
		errors = validateForm(form);
		if (errors == null) {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionControlUsuariosBI userBI = services
					.lookupGestionControlUsuariosBI();
			RolVO rol = null;
			if (rolForm.getIdRol() == null) {
				rol = new RolVO();
				rol.setTipoModulo(rolForm.getModulo());
			} else
				rol = userBI.getRol(rolForm.getIdRol());
			rol.setNombre(rolForm.getNombre());
			rol.setDescripcion(rolForm.getDescripcion());
			try {
				userBI.guardarRol(rol, rolForm.getPermisoRol());
				ActionRedirect vistaRol = new ActionRedirect(
						mappings.findForward("redirect_to_info_rol"), true);
				vistaRol.addParameter("idRol", rol.getId());
				popLastInvocation(request);
				setReturnActionFordward(request, vistaRol);
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				setReturnActionFordward(request,
						mappings.findForward("edicion_rol"));
			}
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("edicion_rol"));
		}
	}

	public void quitarPermisosRolExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RolForm rolForm = (RolForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();
		userBI.quitarPermisosRol(rolForm.getIdRol(), rolForm.getPermisoRol());
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void agregarPermisosRolExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RolForm rolForm = (RolForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();
		userBI.agregarPermisosRol(rolForm.getIdRol(), rolForm.getPermisoRol());
		goBackExecuteLogic(mappings, form, request, response);
	}

	public void mostrarSeleccionPermisosRolExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RolPO rol = (RolPO) getFromTemporalSession(request,
				ControlAccesoConstants.INFO_ROL);
		int modulo = rol.getTipoModulo();
		String pModulo = request.getParameter("modulo");
		if (pModulo != null && !common.util.StringUtils.isBlank(pModulo))
			modulo = Integer.parseInt(pModulo);
		RolForm rolForm = (RolForm) form;
		rolForm.setModulo(modulo);
		Collection permisos = Arrays.asList(AppPermissions
				.getPermisosModulo(modulo));
		List permisosEnRol = null;
		if (rol.getPermisos() != null)
			permisosEnRol = new ArrayList(rol.getPermisos());
		else
			permisosEnRol = new ArrayList();
		CollectionUtils.transform(permisosEnRol, new Transformer() {
			public Object transform(Object obj) {
				return String.valueOf(((PermisoVO) obj).getPerm());
			}
		});
		permisos = CollectionUtils.subtract(permisos, permisosEnRol);
		request.setAttribute(ControlAccesoConstants.LISTA_PERMISOS, permisos);
		setInTemporalSession(request, ControlAccesoConstants.LISTA_MODULOS,
				ArchivoModules.allModules());
		saveCurrentInvocation(
				KeysClientsInvocations.USUARIOS_SELECCION_PERMISOS, request);
		setReturnActionFordward(request,
				mappings.findForward("seleccion_permisos"));
	}

	private void performEliminacionRoles(GestionControlUsuariosBI userBI,
			String[] roles) throws ActionNotAllowedException {
		userBI.eliminarRol(roles);
	}

	public void eliminarRolExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RolForm rolForm = (RolForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		String[] rol = new String[] { rolForm.getIdRol() };
		try {
			performEliminacionRoles(services.lookupGestionControlUsuariosBI(),
					rol);
			goBackExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			verRolExecuteLogic(mappings, form, request, response);
		}
	}

	public void eliminarRolesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RolForm rolForm = (RolForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		String[] rol = rolForm.getRolesSeleccionados();
		try {
			performEliminacionRoles(services.lookupGestionControlUsuariosBI(),
					rol);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void usuariosEnRolExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RolForm rolForm = (RolForm) form;
		saveCurrentInvocation(KeysClientsInvocations.USUARIOS_INFO_ROL, request);
		if (getFromTemporalSession(request, ControlAccesoConstants.INFO_ROL) == null) {
			removeInTemporalSession(request, ControlAccesoConstants.INFO_ROL);
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionControlUsuariosBI userBI = services
					.lookupGestionControlUsuariosBI();
			RolVO rol = (RolPO) RolToPO.getInstance(services).transform(
					userBI.getRol(rolForm.getIdRol()));
			setInTemporalSession(request, ControlAccesoConstants.INFO_ROL, rol);
		}

		removeInTemporalSession(request,
				ControlAccesoConstants.LISTA_USUARIOS_EN_ROL);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();
		List users = userBI.getUsuariosConRol(rolForm.getIdRol());
		setInTemporalSession(request,
				ControlAccesoConstants.LISTA_USUARIOS_EN_ROL, users);

		setReturnActionFordward(request, mappings.findForward("info_rol"));
	}

	public void busquedaUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RolForm rolForm = (RolForm) form;
		String query = rolForm.getNombreUsuario();
		if (query != null)
			if (!StringUtils.isBlank(query)) {
				ServiceRepository services = ServiceRepository
						.getInstance(ServiceClient.create(getAppUser(request)));
				GestionControlUsuariosBI userBI = services
						.lookupGestionControlUsuariosBI();
				List usuarios = userBI.findUsersByName(query);
				List usuariosConRol = (List) getFromTemporalSession(request,
						ControlAccesoConstants.LISTA_USUARIOS_EN_ROL);
				if (usuariosConRol != null)
					usuarios.removeAll(usuariosConRol);
				request.setAttribute(ControlAccesoConstants.LISTA_USUARIOS,
						usuarios);
			} else
				obtenerErrores(request, true).add(
						Constants.ERROR_NO_SEARCH_TOKEN,
						new ActionError(Constants.ERROR_NO_SEARCH_TOKEN));
		saveCurrentInvocation(
				KeysClientsInvocations.USUARIOS_SELECCION_USUARIOS, request);
		setReturnActionFordward(request,
				mappings.findForward("seleccion_usuarios"));
	}

	public void quitarRolAUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RolForm rolForm = (RolForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();
		RolVO rol = (RolVO) getFromTemporalSession(request,
				ControlAccesoConstants.INFO_ROL);
		try {
			userBI.quitarRolesUsuario(Collections.singletonList(rol),
					rolForm.getUsuarioSeleccionado());
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void agregarRolAUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RolForm rolForm = (RolForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();
		RolVO rol = (RolVO) getFromTemporalSession(request,
				ControlAccesoConstants.INFO_ROL);
		try {
			userBI.agregarRolesUsuario(Collections.singletonList(rol),
					rolForm.getUsuarioSeleccionado());
			goBackExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}
}