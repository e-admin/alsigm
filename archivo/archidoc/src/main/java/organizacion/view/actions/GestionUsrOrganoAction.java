package organizacion.view.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import organizacion.OrganizacionConstants;
import organizacion.model.bi.GestionOrganizacionBI;
import organizacion.model.vo.UserOrganoVO;
import organizacion.view.form.UsuarioOrganoForm;

import se.autenticacion.AuthenticationConnector;
import se.autenticacion.AuthenticationConnectorException;
import se.autenticacion.AuthenticationConnectorFactory;
import se.usuarios.AppUser;
import se.usuarios.TipoUsuario;
import util.ErrorsTag;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.Sistema;
import xml.config.Usuario;

import common.Constants;
import common.Messages;
import common.MultiEntityConstants;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.ServiceRepository;
import common.model.UserInfo;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;

public class GestionUsrOrganoAction extends BaseAction {

	public void listadoUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI usuariosService = services
				.lookupGestionOrganizacionBI();
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.USUARIOS_LISTA_USUARIOS, request);
		invocation.setAsReturnPoint(true);
		removeInTemporalSession(request,
				OrganizacionConstants.LISTA_USUARIOS_KEY);

		List usuarios = usuariosService.getUsuarios();
		CollectionUtils.transform(usuarios, new TrasnformerUsuarioToUsuarioPO(
				services));
		setInTemporalSession(request, OrganizacionConstants.LISTA_USUARIOS_KEY,
				usuarios);
		setReturnActionFordward(request, mappings.findForward("lista_usuarios"));
	}

	public class TrasnformerUsuarioToUsuarioPO implements Transformer {
		ServiceRepository services = null;

		TrasnformerUsuarioToUsuarioPO(ServiceRepository services) {
			this.services = services;
		}

		public Object transform(Object arg0) {
			return new UserOrganoPO((UserOrganoVO) arg0, services);
		}
	}

	public void seleccionarUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioOrganoForm usuarioOrganoForm = (UsuarioOrganoForm) form;
		saveCurrentInvocation(KeysClientsInvocations.USUARIOS_NUEVO_USUARIO,
				request);

		ActionErrors errors = validateFormParaSeleccionarUsuario(usuarioOrganoForm);
		if (errors == null) {
			List usuariosEncontrados = (List) getFromTemporalSession(request,
					OrganizacionConstants.LISTA_USUARIOS_EXTERNOS);
			UserInfo userInfo = (UserInfo) CollectionUtils.find(
					usuariosEncontrados,
					new FinderUserInfo(usuarioOrganoForm
							.getIdUsrSisExtGestorSeleccionado()));
			if (userInfo == null) {
				errors = new ActionErrors();
				errors.add(
						OrganizacionConstants.ES_NECESARIO_SELECCIONAR_UN_USUARIO,
						new ActionError(
								OrganizacionConstants.ES_NECESARIO_SELECCIONAR_UN_USUARIO));
			} else {
				ServiceRepository services = getServiceRepository(request);
				GestionOrganizacionBI organizacionService = services
						.lookupGestionOrganizacionBI();
				if (organizacionService.getUsuario(
						userInfo.getExternalUserId(),
						usuarioOrganoForm.getIdOrgano()) != null) {
					errors = new ActionErrors();
					errors.add(
							OrganizacionConstants.ERRORS_NUEVO_USUARIO_YA_EXISTE,
							new ActionError(
									OrganizacionConstants.ERRORS_NUEVO_USUARIO_YA_EXISTE,
									userInfo.getName()));
				} else {
					setInTemporalSession(request,
							OrganizacionConstants.INFO_USUARIO_SELECCIONADO,
							userInfo);
					usuarioOrganoForm.setNombreUsuario(userInfo.getName());
					usuarioOrganoForm.setTipoSistema(usuarioOrganoForm
							.getTipoSistema());
				}
			}
		}

		if (errors != null) {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("buscar_usuarios"));
		} else {
			saveCurrentInvocation(
					KeysClientsInvocations.USUARIOS_NUEVO_USUARIO, request);
			setReturnActionFordward(request,
					mappings.findForward("nuevo_usuario"));
		}
	}

	public void nuevoUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioOrganoForm usuarioOrganoForm = (UsuarioOrganoForm) form;
		usuarioOrganoForm.reset(mappings, request);

		List sistemasExternos = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getSistemasGestoresUsuarios();
		setInTemporalSession(request,
				OrganizacionConstants.LISTA_SISTEMAS_EXTERNOS, sistemasExternos);

		removeInTemporalSession(request,
				OrganizacionConstants.USUARIO_VER_USUARIO);
		removeInTemporalSession(request,
				OrganizacionConstants.LISTA_USUARIOS_EXTERNOS);

		saveCurrentInvocation(
				KeysClientsInvocations.USUARIOS_SELECCION_USUARIOS, request);
		setReturnActionFordward(request,
				mappings.findForward("buscar_usuarios"));
	}

	public void realizarBusquedaUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioOrganoForm usuarioOrganoForm = (UsuarioOrganoForm) form;
		removeInTemporalSession(request,
				OrganizacionConstants.LISTA_USUARIOS_EXTERNOS);
		ActionErrors errors = validateFormParaBuscar(usuarioOrganoForm);
		if (errors == null) {

			List sistemas = (List) getFromTemporalSession(request,
					OrganizacionConstants.LISTA_SISTEMAS_EXTERNOS);
			Sistema sistemaSeleccionado = (Sistema) CollectionUtils.find(
					sistemas,
					new FinderTipoSistema(usuarioOrganoForm.getTipoSistema()));

			// Obtener información del usuario conectado
			AppUser appUser = getAppUser(request);

			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((appUser != null)
					&& (StringUtils.isNotEmpty(appUser.getEntity()))) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM,
						appUser.getEntity());
			}

			AuthenticationConnector connector = AuthenticationConnectorFactory
					.getConnectorById(sistemaSeleccionado.getId(), params);
			List usuariosEncontrados = connector
					.findUserByName(usuarioOrganoForm.getNombreUsuarioABuscar());
			if (usuariosEncontrados == null || usuariosEncontrados.isEmpty()) {
				errors = new ActionErrors();
				errors.add(OrganizacionConstants.USUARIOS_NO_ENCONTRADOS,
						new ActionError(
								OrganizacionConstants.USUARIOS_NO_ENCONTRADOS));
			}

			usuarioOrganoForm.setTipoSistema(sistemaSeleccionado.getId());
			setInTemporalSession(request,
					OrganizacionConstants.LISTA_USUARIOS_EXTERNOS,
					usuariosEncontrados);
		}

		if (errors != null)
			ErrorsTag.saveErrors(request, errors);

		setReturnActionFordward(request,
				mappings.findForward("buscar_usuarios"));
	}

	ActionErrors validateFormParaBuscar(UsuarioOrganoForm usuarioOrganoForm) {
		ActionErrors errors = null;
		if (StringUtils.isBlank(usuarioOrganoForm.getNombreUsuarioABuscar())) {
			errors = new ActionErrors();
			errors.add(
					OrganizacionConstants.ES_NECESARIO_INTRODUCIR_UN_VALOR_PARA_EL_NOMBRE_A_BUSCAR,
					new ActionError(
							OrganizacionConstants.ES_NECESARIO_INTRODUCIR_UN_VALOR_PARA_EL_NOMBRE_A_BUSCAR));
		}
		return errors;
	}

	ActionErrors validateFormParaSeleccionarUsuario(
			UsuarioOrganoForm UsuarioOrganoForm) {
		ActionErrors errors = null;
		if (StringUtils.isBlank(UsuarioOrganoForm
				.getIdUsrSisExtGestorSeleccionado())) {
			errors = new ActionErrors();
			errors.add(
					OrganizacionConstants.ES_NECESARIO_SELECCIONAR_UN_USUARIO,
					new ActionError(
							OrganizacionConstants.ES_NECESARIO_SELECCIONAR_UN_USUARIO));
		}
		return errors;
	}

	private ActionErrors validateForm(UsuarioOrganoForm usuarioOrganoForm) {
		ActionErrors errors = new ActionErrors();

		if (GenericValidator
				.isBlankOrNull(usuarioOrganoForm.getNombreUsuario())) {
			errors.add(
					Constants.ERROR_REQUIRED,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(OrganizacionConstants.ETIQUETA_USUARIO)));
		}

		if (GenericValidator.isBlankOrNull(usuarioOrganoForm.getIdOrgano())) {
			errors.add(
					Constants.ERROR_REQUIRED,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(OrganizacionConstants.ETIQUETA_ORGANO)));

		}
		return errors.size() > 0 ? errors : null;
	}

	private class FinderTipoSistema implements Predicate {
		String tipoToFind = null;

		FinderTipoSistema(String tipoToFind) {
			this.tipoToFind = tipoToFind;
		}

		public boolean evaluate(Object arg0) {
			return tipoToFind.equalsIgnoreCase(((Sistema) arg0).getId());
		}
	}

	private class FinderUserInfo implements Predicate {
		String externalUserId = null;

		FinderUserInfo(String externalUserId) {
			this.externalUserId = externalUserId;
		}

		public boolean evaluate(Object arg0) {
			UserInfo userToEval = ((UserInfo) arg0);
			boolean equal = false;

			if (userToEval.getExternalUserId() != null)
				equal = userToEval.getExternalUserId().equalsIgnoreCase(
						externalUserId);
			else
				equal = true;

			return equal;
		}
	}

	public void guardarUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ActionErrors errors = null;
		String editando = request.getParameter("editando");
		UsuarioOrganoForm usuarioOrganoForm = (UsuarioOrganoForm) form;
		UserOrganoVO userOrganoVO = null;

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI usuariosService = services
				.lookupGestionOrganizacionBI();
		userOrganoVO = new UserOrganoVO();
		errors = validateForm(usuarioOrganoForm);
		if (errors == null) {
			userOrganoVO = new UserOrganoVO();
			userOrganoVO.setNombreUsuario(usuarioOrganoForm.getNombreUsuario());
			userOrganoVO.setIdOrgano(usuarioOrganoForm.getIdOrgano());

			UserInfo userInfo = (UserInfo) getFromTemporalSession(request,
					OrganizacionConstants.INFO_USUARIO_SELECCIONADO);
			if (userInfo != null)
				userOrganoVO.setIdUsuario(userInfo.getExternalUserId());
			else
				userOrganoVO.setIdUsuario(usuarioOrganoForm
						.getIdUsrSisExtGestorSeleccionado());

			if (editando == null)
				usuariosService.insertarUsuarioOrgano(userOrganoVO);
			else {
				userOrganoVO.setIdUsuario(usuarioOrganoForm.getIdUsuario());
				usuariosService.actualizarUsuarioOrgano(userOrganoVO);
			}

			popLastInvocation(request);
			ActionRedirect forward = new ActionRedirect(
					mappings.findForward("ver_usuario_despues_alta"));
			forward.addParameter("idUsuarioSeleccionado",
					userOrganoVO.getIdUsuario());
			setReturnActionFordward(request, forward);
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("nuevo_usuario"));
		}
	}

	public void eliminarUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ActionErrors errors = new ActionErrors();
		UsuarioOrganoForm usuarioOrganoForm = (UsuarioOrganoForm) form;
		String idUsuariosABorrar[] = usuarioOrganoForm.getUsuariosABorrar();

		if (idUsuariosABorrar == null || idUsuariosABorrar.length == 0)
			errors.add(
					OrganizacionConstants.ERROR_SELECCIONE_AL_MENOS_UN_USUARIO,
					new ActionError(
							OrganizacionConstants.ERROR_SELECCIONE_AL_MENOS_UN_USUARIO));
		else {
			ServiceRepository services = getServiceRepository(request);
			GestionOrganizacionBI organizacionBI = services
					.lookupGestionOrganizacionBI();

			for (int i = 0; i < idUsuariosABorrar.length; i++)
				organizacionBI.eliminarUsuarioOrgano(idUsuariosABorrar[i]);

			usuarioOrganoForm.setUsuariosABorrar(null);
		}
		goBackExecuteLogic(mappings, form, request, response);
	}

	public void eliminarUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ActionErrors errors = new ActionErrors();
		UsuarioOrganoForm usuarioOrganoForm = (UsuarioOrganoForm) form;
		String idUsuariosABorrar[] = usuarioOrganoForm.getUsuariosABorrar();

		if (idUsuariosABorrar == null || idUsuariosABorrar.length == 0)
			errors.add(
					OrganizacionConstants.ERROR_SELECCIONE_AL_MENOS_UN_USUARIO,
					new ActionError(
							OrganizacionConstants.ERROR_SELECCIONE_AL_MENOS_UN_USUARIO));
		else {
			ServiceRepository services = getServiceRepository(request);
			GestionOrganizacionBI organizacionBI = services
					.lookupGestionOrganizacionBI();

			for (int i = 0; i < idUsuariosABorrar.length; i++)
				organizacionBI.eliminarUsuarioOrgano(idUsuariosABorrar[i]);

			usuarioOrganoForm.setUsuariosABorrar(null);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void verUsuarioExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionService = services
				.lookupGestionOrganizacionBI();

		String idUsuarioSeleccionado = request
				.getParameter("idUsuarioSeleccionado");
		UserOrganoVO userOrganoVO = organizacionService
				.getUsuarioById(idUsuarioSeleccionado);

		UserOrganoPO userOrganoPO = new UserOrganoPO(userOrganoVO, services);
		setInTemporalSession(request,
				OrganizacionConstants.USUARIO_VER_USUARIO, userOrganoPO);

		saveCurrentInvocation(KeysClientsInvocations.USUARIO_VER_USUARIO,
				request);
		setReturnActionFordward(request, mappings.findForward("ver_usuario"));
	}

	public void edicionUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioOrganoForm usuarioOrganoForm = (UsuarioOrganoForm) form;
		UserOrganoPO userOrganoPO = (UserOrganoPO) getFromTemporalSession(
				request, OrganizacionConstants.USUARIO_VER_USUARIO);

		populateForm(usuarioOrganoForm, userOrganoPO);

		saveCurrentInvocation(KeysClientsInvocations.USUARIO_EDICION_USUARIO,
				request);

		setReturnActionFordward(request, mappings.findForward("nuevo_usuario"));
	}

	/**
	 * @param frm
	 * @param usuarioEnEdicion
	 */
	private void populateForm(UsuarioOrganoForm usuarioOrganoForm,
			UserOrganoPO userOrganoPO) {

		usuarioOrganoForm.setIdUsuario(userOrganoPO.getIdUsuario());
		usuarioOrganoForm.setNombreUsuario(userOrganoPO.getNombreUsuario());
		usuarioOrganoForm.setIdOrgano(userOrganoPO.getIdOrgano());
		usuarioOrganoForm.setNombreOrgano(userOrganoPO.getNombreOrgano());
	}

	public void verBuscadorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioOrganoForm usuarioOrganoForm = (UsuarioOrganoForm) form;
		usuarioOrganoForm.clear();

		List sistemasExternos = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getSistemasGestoresUsuarios();
		setInTemporalSession(request,
				OrganizacionConstants.LISTA_SISTEMAS_EXTERNOS, sistemasExternos);

		saveCurrentInvocation(KeysClientsInvocations.USUARIO_BUSQUEDA, request);
		setReturnActionFordward(request,
				mappings.findForward("resultado_busqueda_usuarios"));
	}

	/***************************************************************/

	public void busquedaUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioOrganoForm usuarioOrganoForm = (UsuarioOrganoForm) form;
		UserOrganoVO userOrganoVO = new UserOrganoVO();
		userOrganoVO.setNombreUsuario(usuarioOrganoForm.getNombreUsuario());

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();

		removeInTemporalSession(request,
				OrganizacionConstants.LISTA_USUARIOS_EXTERNOS);
		removeInTemporalSession(request,
				OrganizacionConstants.LISTA_SISTEMAS_EXTERNOS);

		// Obtener sólo el sistema de autenticación de usuarios que tenga el
		// usuario interno
		ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		Usuario usuario = csa.getConfiguracionControlAcceso()
				.findUsuarioByTipo(TipoUsuario.INTERNO);
		if (usuario == null)
			throw new AuthenticationConnectorException(
					"El tipo de usuario no es v\u00E1lido");

		Sistema sistGestorUsuarios = csa.findSistemaGestorUsuariosById(usuario
				.getIdSistGestorUsr());

		// List sistemasExternos =
		// ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().getSistemasGestoresUsuarios();
		List sistemasExternos = new ArrayList();
		sistemasExternos.add(sistGestorUsuarios);

		setInTemporalSession(request,
				OrganizacionConstants.LISTA_SISTEMAS_EXTERNOS, sistemasExternos);

		List usuarios = organizacionBI.buscarUsuariosOrganos(userOrganoVO);
		List usuariosActualesOrgano = organizacionBI
				.getUsuariosByOrgano(usuarioOrganoForm.getIdOrgano());
		CollectionUtils.filter(usuarios, new PredicateFiltrarUsuarios(
				usuariosActualesOrgano));
		request.setAttribute(OrganizacionConstants.LISTA_USUARIOS_KEY, usuarios);

		String idOrgano = request.getParameter("idOrgano");
		usuarioOrganoForm.setIdOrgano(idOrgano);

		saveCurrentInvocation(
				KeysClientsInvocations.USUARIOS_SELECCION_USUARIOS, request);
		setReturnActionFordward(request,
				mappings.findForward("buscar_usuarios"));
	}

	public class PredicateFiltrarUsuarios implements Predicate {
		List usuariosAQuitar = null;

		PredicateFiltrarUsuarios(List usuariosAQuitar) {
			this.usuariosAQuitar = usuariosAQuitar;
		}

		public boolean evaluate(Object arg0) {
			for (Iterator itDestinatariosAQuitar = usuariosAQuitar.iterator(); itDestinatariosAQuitar
					.hasNext();) {
				UserOrganoVO usuario = (UserOrganoVO) itDestinatariosAQuitar
						.next();
				if (usuario.getIdUsuario().equals(
						((UserOrganoVO) arg0).getIdUsuario()))
					return false;
			}
			return true;
		}
	}

	public void agregarUsuariosAOrganoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioOrganoForm usuarioOrganoForm = (UsuarioOrganoForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();

		ActionErrors errors = validateAgregarUsuarios(form);
		if (errors == null) {
			errors = new ActionErrors();
			List usuariosEncontrados = (List) getFromTemporalSession(request,
					OrganizacionConstants.LISTA_USUARIOS_EXTERNOS);
			List users = new ArrayList();
			String[] usuariosSeleccionados = usuarioOrganoForm
					.getUsuariosSeleccionados();
			for (int j = 0; j < usuariosSeleccionados.length; j++)
				users.add(CollectionUtils.find(usuariosEncontrados,
						new FinderUserInfo(usuariosSeleccionados[j])));

			for (int i = 0; i < users.size(); i++) {
				UserInfo userInfo = (UserInfo) users.get(i);
				UserOrganoVO userOrganoVO = new UserOrganoVO();
				userOrganoVO.setIdUsuario(userInfo.getExternalUserId());
				userOrganoVO.setIdOrgano(usuarioOrganoForm.getIdOrgano());
				String nombreUsuario = userInfo.getName();

				if (StringUtils.isNotEmpty(userInfo.getSurname())) {
					nombreUsuario += Constants.STRING_SPACE
							+ userInfo.getSurname();
				}

				userOrganoVO.setNombreUsuario(nombreUsuario);

				// if(organizacionBI.getUsuario(userInfo.getExternalUserId(),
				// usuarioOrganoForm.getIdOrgano())!=null){
				if (organizacionBI.getUsuarioById(userInfo.getExternalUserId()) != null) {
					errors.add(
							OrganizacionConstants.ERRORS_USUARIO_YA_ASOCIADO,
							new ActionError(
									OrganizacionConstants.ERRORS_USUARIO_YA_ASOCIADO,
									userInfo.getName()));
					ErrorsTag.saveErrors(request, errors);
				} else
					organizacionBI.insertarUsuarioOrgano(userOrganoVO);
			}
			if (!errors.isEmpty()) {
				removeInTemporalSession(request,
						OrganizacionConstants.LISTA_USUARIOS_EXTERNOS);
				setReturnActionFordward(request,
						mappings.findForward("buscar_usuarios"));
			} else
				goBackExecuteLogic(mappings, form, request, response);
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("buscar_usuarios"));
			// goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	private ActionErrors validateAgregarUsuarios(ActionForm form) {
		UsuarioOrganoForm usuarioOrganoForm = (UsuarioOrganoForm) form;
		String[] usuarios = usuarioOrganoForm.getUsuariosSeleccionados();
		ActionErrors errors = null;
		if (usuarios == null || usuarios.length == 0) {
			errors = new ActionErrors();
			errors.add(
					OrganizacionConstants.NECESARIO_SELECCIONAR_UN_USUARIO,
					new ActionError(
							OrganizacionConstants.NECESARIO_SELECCIONAR_UN_USUARIO));
		}
		return errors;
	}

	public void agregarUsuarioABusquedaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ActionErrors errors = validateSeleccionarUsuario(form);
		if (errors == null) {
			ActionRedirect buscadorUsuario = new ActionRedirect(
					mappings.findForward("redirect_to_busqueda_organizacion"),
					false);
			setReturnActionFordward(request, buscadorUsuario);
		} else {
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	private ActionErrors validateSeleccionarUsuario(ActionForm form) {
		UsuarioOrganoForm usuarioOrganoForm = (UsuarioOrganoForm) form;
		String usuario = usuarioOrganoForm.getIdUsrSisExtGestorSeleccionado();
		ActionErrors errors = null;
		if (StringUtils.isEmpty(usuario)) {
			errors = new ActionErrors();
			errors.add(
					OrganizacionConstants.NECESARIO_SELECCIONAR_UN_USUARIO,
					new ActionError(
							OrganizacionConstants.NECESARIO_SELECCIONAR_UN_USUARIO));
		}
		return errors;
	}
}