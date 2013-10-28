package gcontrol.actions;

import gcontrol.ControlAccesoConstants;
import gcontrol.exceptions.UsuariosNotAllowedException;
import gcontrol.forms.ListaAccesoForm;
import gcontrol.model.TipoDestinatario;
import gcontrol.view.TransformerUsuariosVOToUsuariosPO;
import gcontrol.view.UsuarioPO;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.DestinatarioPermisosListaVO;
import gcontrol.vos.GrupoVO;
import gcontrol.vos.ListaAccesoVO;
import gcontrol.vos.PermisosListaVO;
import gcontrol.vos.UsuarioVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import util.CollectionUtils;
import util.ErrorsTag;
import auditoria.vos.CritUsuarioVO;

import common.Constants;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionControlUsuariosBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;

public class GestionListasAccesoAction extends BaseAction {
	public class TransformerListaAccesoPO implements Transformer {
		ServiceRepository serviceRepository = null;

		public TransformerListaAccesoPO(ServiceRepository serviceRepository) {
			this.serviceRepository = serviceRepository;
		}

		public Object transform(Object arg0) {
			return new ListaAccesoPO(serviceRepository, (ListaAccesoVO) arg0);
		}
	};

	public void listaListasAccesoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// ListaAccesoForm listaAccesoForm = (ListaAccesoForm) form;
		GestionControlUsuariosBI userBI = getGestionControlUsuarios(request);
		List listasAcceso = userBI.getListasControlAcceso();
		org.apache.commons.collections.CollectionUtils.transform(listasAcceso,
				new TransformerListaAccesoPO(getServiceRepository(request)));
		request.setAttribute(ControlAccesoConstants.LISTA_LISTAS_ACCESO,
				listasAcceso);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.USUARIO_LISTA_LISTAS_ACCESO, request);
		invocation.setAsReturnPoint(true);

		setReturnActionFordward(request,
				mappings.findForward("lista_listas_acceso"));
	}

	// public void listadoGruposParaAnadirExecuteLogic(ActionMapping mappings,
	// ActionForm form,
	// HttpServletRequest request, HttpServletResponse response) {
	//
	// GestionControlUsuariosBI usuariosService =
	// getGestionControlUsuarios(request);
	//
	// setInTemporalSession(request, ControlAccesoConstants.LISTA_GRUPOS ,
	// usuariosService.getGrupos());
	//
	// setReturnActionFordward(request,
	// mappings.findForward("listado_grupos_anadir"));
	//
	// saveCurrentInvocation(KeysClientsInvocations.USUARIO_ADD_GRUPO_A_LISTA,
	// request);
	// }

	// public void buscarGruposParaAnadirExecuteLogic(ActionMapping mappings,
	// ActionForm form,
	// HttpServletRequest request, HttpServletResponse response) {
	//
	// GestionControlUsuariosBI usuariosService =
	// getGestionControlUsuarios(request);
	// ListaAccesoForm listaAccesoForm = (ListaAccesoForm) form;
	// String query = listaAccesoForm.getNombreGrupo();
	// if (query != null)
	// if (!StringUtils.isBlank(query)) {
	// ServiceRepository services =
	// ServiceRepository.getInstance(ServiceClient.create(getAppUser(request)));
	// GestionControlUsuariosBI userBI =
	// services.lookupGestionControlUsuariosBI();
	// List grupos = userBI.findGruposByName(query);
	// request.setAttribute(ControlAccesoConstants.LISTA_USUARIOS, usuarios);
	// saveCurrentInvocation(KeysClientsInvocations.SELECCION_USUARIOS,
	// request);
	// } else
	// getErrors(request, true).add(Constants.ERROR_NO_SEARCH_TOKEN, new
	// ActionError(Constants.ERROR_NO_SEARCH_TOKEN));
	//
	// setInTemporalSession(request, ControlAccesoConstants.LISTA_GRUPOS ,
	// usuariosService.getGrupos());
	//
	// setReturnActionFordward(request,
	// mappings.findForward("listado_grupos_anadir"));
	//
	// saveCurrentInvocation(KeysClientsInvocations.USUARIO_ADD_GRUPO_A_LISTA,
	// request);
	// }
	public void inicioBusquedaDestinatariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ListaAccesoForm listaAccesoForm = (ListaAccesoForm) form;
		listaAccesoForm.resetParaNuevoDestinatario();
		if (listaAccesoForm.isTipoElementoUsuario()) {
			saveCurrentInvocation(
					KeysClientsInvocations.USUARIOS_SELECCION_USUARIOS, request);
		} else if (listaAccesoForm.isTipoElementoGrupo()) {
			saveCurrentInvocation(
					KeysClientsInvocations.USUARIOS_SELECCION_GRUPOS, request);
		} else if (listaAccesoForm.isTipoElementoOrgano()) {
			saveCurrentInvocation(
					KeysClientsInvocations.USUARIOS_SELECCION_ORGANOS, request);
		}

		setReturnActionFordward(request,
				mappings.findForward("listado_destinatarios_anadir"));

	}

	public class PredicateFiltrarDestinatario implements Predicate {
		List destinatariosAQuitar = null;
		int tipoDestinatario;

		PredicateFiltrarDestinatario(List destinatariosAQuitar,
				int tipoDestinatario) {
			this.destinatariosAQuitar = destinatariosAQuitar;
			this.tipoDestinatario = tipoDestinatario;
		}

		public boolean evaluate(Object arg0) {
			for (Iterator itDestinatariosAQuitar = destinatariosAQuitar
					.iterator(); itDestinatariosAQuitar.hasNext();) {
				if (tipoDestinatario == TipoDestinatario.GRUPO) {
					GrupoVO grupo = (GrupoVO) itDestinatariosAQuitar.next();
					if (grupo.getId().equals(((GrupoVO) arg0).getId()))
						return false;

				} else if (tipoDestinatario == TipoDestinatario.ORGANO) {
					CAOrganoVO organo = (CAOrganoVO) itDestinatariosAQuitar
							.next();
					if (organo.getIdOrg()
							.equals(((CAOrganoVO) arg0).getIdOrg()))
						return false;

				} else if (tipoDestinatario == TipoDestinatario.USUARIO) {
					UsuarioVO usuario = (UsuarioVO) itDestinatariosAQuitar
							.next();
					if (usuario.getId().equals(((UsuarioVO) arg0).getId()))
						return false;
				}
			}
			return true;
		}
	}

	public void busquedaDestinatariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		ListaAccesoForm listaAccesoForm = (ListaAccesoForm) form;
		String query = listaAccesoForm.getNombreABuscar();
		if (!StringUtils.isBlank(query)) {
			GestionControlUsuariosBI userBI = services
					.lookupGestionControlUsuariosBI();
			// obtencion de elementos por criterio de busq
			List elementos = null;
			List elementosAQuitar = null;
			if (listaAccesoForm.isTipoElementoUsuario()) {
				elementos = userBI.findUsersByName(query);
				elementosAQuitar = userBI
						.getUsuariosEnListaAcceso(listaAccesoForm
								.getIdListaAcceso());
				saveCurrentInvocation(
						KeysClientsInvocations.USUARIOS_SELECCION_USUARIOS,
						request);

			} else if (listaAccesoForm.isTipoElementoGrupo()) {
				elementos = userBI.findGruposByName(query);
				elementos.remove(new GrupoVO(CritUsuarioVO.GLOBAL_GROUP));
				elementos.remove(new GrupoVO(CritUsuarioVO.GLOBAL_GROUP_ADM));
				elementosAQuitar = userBI
						.getGruposEnListaAcceso(listaAccesoForm
								.getIdListaAcceso());
				saveCurrentInvocation(
						KeysClientsInvocations.USUARIOS_SELECCION_GRUPOS,
						request);
			} else if (listaAccesoForm.isTipoElementoOrgano()) {
				elementos = userBI.findOrganosByName(query,false);
				elementosAQuitar = userBI
						.getOrganosEnListaAcceso(listaAccesoForm
								.getIdListaAcceso());
				saveCurrentInvocation(
						KeysClientsInvocations.USUARIOS_SELECCION_ORGANOS,
						request);
			}

			// es necesario eliminar de la lista de destinatarios aquellos que
			// ya estan
			// en la lista de acceso)
			if (!CollectionUtils.isEmptyCollection(elementos)
					&& !CollectionUtils.isEmptyCollection(elementosAQuitar)) {
				org.apache.commons.collections.CollectionUtils.filter(
						elementos,
						new PredicateFiltrarDestinatario(elementosAQuitar,
								listaAccesoForm.getTipoElementoSeleccionado()));
			}

			if (listaAccesoForm.isTipoElementoUsuario()) {
				org.apache.commons.collections.CollectionUtils.transform(
						elementos, new TransformerUsuariosVOToUsuariosPO(
								services));
			}

			request.setAttribute(
					ControlAccesoConstants.LISTA_DESTINATARIOS_LISTA, elementos);

		} else
			obtenerErrores(request, true).add(Constants.ERROR_NO_SEARCH_TOKEN,
					new ActionError(Constants.ERROR_NO_SEARCH_TOKEN));

		setReturnActionFordward(request,
				mappings.findForward("listado_destinatarios_anadir"));

	}

	public void verGruposExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ListaAccesoForm listaAccesoForm = (ListaAccesoForm) form;
		listaAccesoForm.setTipoElementoSeleccionado(TipoDestinatario.GRUPO);
		listaAccesoForm.setNombreABuscar(null);

		setReturnActionFordward(request,
				mappings.findForward("info_lista_acceso"));
		saveCurrentInvocation(KeysClientsInvocations.USUARIO_INFO_LISTA_ACCESO,
				request).setAsReturnPoint(true);
	}

	public void verUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ListaAccesoForm listaAccesoForm = (ListaAccesoForm) form;
		listaAccesoForm.setTipoElementoSeleccionado(TipoDestinatario.USUARIO);
		listaAccesoForm.setNombreABuscar(null);

		setReturnActionFordward(request,
				mappings.findForward("info_lista_acceso"));
		saveCurrentInvocation(KeysClientsInvocations.USUARIO_INFO_LISTA_ACCESO,
				request).setAsReturnPoint(true);
	}

	public void verOrganosExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ListaAccesoForm listaAccesoForm = (ListaAccesoForm) form;
		listaAccesoForm.setTipoElementoSeleccionado(TipoDestinatario.ORGANO);
		listaAccesoForm.setNombreABuscar(null);

		setReturnActionFordward(request,
				mappings.findForward("info_lista_acceso"));
		saveCurrentInvocation(KeysClientsInvocations.USUARIO_INFO_LISTA_ACCESO,
				request).setAsReturnPoint(true);
	}

	public void verListaAccesoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ListaAccesoForm listaAccesoForm = (ListaAccesoForm) form;
		listaAccesoForm.setTipoElementoSeleccionado(TipoDestinatario.ORGANO);
		listaAccesoForm.resetVerElemento();

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));

		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();

		ListaAccesoVO listaAcceso = userBI.getListaAcceso(listaAccesoForm
				.getIdListaAcceso());
		getInvocationStack(request).goBackUntilPreviousKeyClient(request,
				KeysClientsInvocations.USUARIO_INFO_LISTA_ACCESO);
		// goReturnPointExecuteLogic(mappings, listaAccesoForm, request,
		// response);
		setInTemporalSession(request, ControlAccesoConstants.INFO_LISTA_ACCESO,
				new ListaAccesoPO(services, listaAcceso));
		saveCurrentInvocation(KeysClientsInvocations.USUARIO_INFO_LISTA_ACCESO,
				request).setAsReturnPoint(true);

		setReturnActionFordward(request,
				mappings.findForward("info_lista_acceso"));

	}

	public void altaListaAccesoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.USUARIO_ALTA_LISTA_ACCESO, request);
		ListaAccesoForm listaAccesoForm = (ListaAccesoForm) form;
		listaAccesoForm.resetParaNuevaLista();
		invocation.setAsReturnPoint(true);
		setReturnActionFordward(request,
				mappings.findForward("edicion_lista_acceso"));
	}

	public void edicionListaAccesoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ListaAccesoForm listaAccesoForm = (ListaAccesoForm) form;
		GestionControlUsuariosBI userBI = getGestionControlUsuarios(request);

		ListaAccesoVO listaVO = userBI.getListaAcceso(listaAccesoForm
				.getIdListaAcceso());

		listaAccesoForm.populateByLista(listaVO);

		saveCurrentInvocation(
				KeysClientsInvocations.USUARIO_EDICION_LISTA_ACCESO, request);

		request.setAttribute(ControlAccesoConstants.INFO_LISTA_ACCESO,
				new ListaAccesoPO(getServiceRepository(request), listaVO));
		setReturnActionFordward(request,
				mappings.findForward("edicion_lista_acceso"));
	}

	public ActionErrors validateInGuardar(ActionForm form, boolean isEditando,
			GestionControlUsuariosBI userBI) {
		ActionErrors errors = new ActionErrors();
		ListaAccesoForm listaAccesoForm = (ListaAccesoForm) form;
		if (GenericValidator.isBlankOrNull(listaAccesoForm.getNombre())) {
			errors.add(ControlAccesoConstants.NECESARIO_NOMBRE,
					new ActionError(ControlAccesoConstants.NECESARIO_NOMBRE));
		}
		int tipoLista = listaAccesoForm.getTipoLista();
		if (tipoLista < 0) {
			errors.add(ControlAccesoConstants.NECESARIO_TIPO, new ActionError(
					ControlAccesoConstants.NECESARIO_TIPO));
		}

		// comprobar si ya existe una lista con ese nombre y tipo
		if (userBI != null) {
			ListaAccesoVO lista = userBI
					.getListaControlAccesoByNombre(listaAccesoForm.getNombre());
			if (lista != null
					&& (!isEditando || !listaAccesoForm.getIdListaAcceso()
							.equals(lista.getId()))) {
				errors.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(
						Constants.ERROR_CREAR_ELEMENTO_YA_EXISTE));
			}
		}

		return errors.size() > 0 ? errors : null;
	}

	public void guardarListaAccesoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ListaAccesoForm listaAccesoForm = (ListaAccesoForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();
		ListaAccesoVO listaAcceso = null;
		boolean isEditando = (listaAccesoForm.getIdListaAcceso() != null);
		ActionErrors errors = validateInGuardar(listaAccesoForm, isEditando,
				userBI);
		if (errors == null) {
			if (isEditando)
				listaAcceso = userBI.getListaAcceso(listaAccesoForm
						.getIdListaAcceso());
			else
				listaAcceso = new ListaAccesoVO();

			listaAcceso.setTipo(listaAccesoForm.getTipoLista());
			listaAcceso.setNombre(listaAccesoForm.getNombre());
			listaAcceso.setDescripcion(listaAccesoForm.getDescripcion());
			try {
				userBI.guardarListaAcceso(listaAcceso);
				ActionRedirect vistaGrupo = new ActionRedirect(
						mappings.findForward("redirect_to_info_lista_acceso"),
						true);
				// vistaGrupo.addParameter("idListaAcceso",
				// listaAcceso.getId());

				listaAccesoForm.setIdListaAcceso(listaAcceso.getId());

				// popLastInvocation(request); //popLastInvocation(request);
				setReturnActionFordward(request, vistaGrupo);

			} catch (ActionNotAllowedException anae) {
				errors = guardarError(request, anae);
			}

		} else {
			ErrorsTag.saveErrors(request, errors);
		}
		if (errors != null)
			setReturnActionFordward(request,
					mappings.findForward("edicion_lista_acceso"));
	}

	private void performEliminacionListaAcceso(GestionControlUsuariosBI userBI,
			String[] listasAcceso) throws ActionNotAllowedException {
		userBI.eliminarListasAcceso(listasAcceso);
	}

	public void eliminarListaAccesoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ListaAccesoForm listaAccesoForm = (ListaAccesoForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		String[] listaAcceso = new String[] { listaAccesoForm
				.getIdListaAcceso() };
		try {
			performEliminacionListaAcceso(
					services.lookupGestionControlUsuariosBI(), listaAcceso);
			goBackExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void eliminarListasAccesoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ListaAccesoForm listaAccesoForm = (ListaAccesoForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		String[] listasAcceso = listaAccesoForm.getListasAccesoSeleccionadas();
		ActionErrors errors = null;
		try {
			errors = validateInEliminarListas(form);
			if (errors == null)
				performEliminacionListaAcceso(
						services.lookupGestionControlUsuariosBI(), listasAcceso);
			else
				ErrorsTag.saveErrors(request, errors);

		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	// public void busquedaUsuarioExecuteLogic(ActionMapping mappings,
	// ActionForm form,
	// HttpServletRequest request, HttpServletResponse response) {
	// GrupoForm grupoForm = (GrupoForm)form;
	// String query = grupoForm.getNombreUsuario();
	// if (query != null)
	// if (!StringUtils.isBlank(query)) {
	// ServiceRepository services =
	// ServiceRepository.getInstance(ServiceClient.create(getAppUser(request)));
	// GestionControlUsuariosBI userBI =
	// services.lookupGestionControlUsuariosBI();
	// List usuarios = userBI.findUsersByName(query);
	// request.setAttribute(ControlAccesoConstants.LISTA_USUARIOS, usuarios);
	// saveCurrentInvocation(KeysClientsInvocations.USUARIOS_SELECCION_USUARIOS,
	// request);
	// } else
	// getErrors(request, true).add(Constants.ERROR_NO_SEARCH_TOKEN, new
	// ActionError(Constants.ERROR_NO_SEARCH_TOKEN));
	//
	// setReturnActionFordward(request,
	// mappings.findForward("seleccion_usuarios"));
	// }

	/**
	 * @param form
	 */
	private ActionErrors validateInEliminarListas(ActionForm form) {
		ActionErrors errors = new ActionErrors();
		ListaAccesoForm frm = (ListaAccesoForm) form;
		String[] listasAEliminar = frm.getListasAccesoSeleccionadas();
		if (!(listasAEliminar != null && listasAEliminar.length > 0))
			errors.add(
					ControlAccesoConstants.SELECCIONE_AL_MENOS_UNA_LISTA_PARA_ELIMINAR,
					new ActionError(
							ControlAccesoConstants.SELECCIONE_AL_MENOS_UNA_LISTA_PARA_ELIMINAR));

		return errors.size() > 0 ? errors : null;
	}

	public void verElementoListaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ListaAccesoForm frm = (ListaAccesoForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();
		ListaAccesoVO listaVO = (ListaAccesoVO) getFromTemporalSession(request,
				ControlAccesoConstants.INFO_LISTA_ACCESO);

		resetInVerElemento(form);

		ActionErrors errors = validateFormInSeleccionarElemento((ListaAccesoForm) form);
		if (errors == null) {

			// objeto destinataio para realziar la busqueda
			DestinatarioPermisosListaVO destinatario = new DestinatarioPermisosListaVO(
					frm.getTipoElementoSeleccionado());
			List idsDestinatarios = new ArrayList();
			idsDestinatarios.add(frm.getIdElementoSeleccionado());
			destinatario.setIdDestList(idsDestinatarios);
			List listDestinatarios = new ArrayList();
			listDestinatarios.add(destinatario);

			List permisosDeElemento = userBI.getPermisosListaAcceso(
					frm.getIdListaAcceso(), listDestinatarios);
			// List permisosDeElemento =
			// userBI.getListasAcceso(listDestinatarios);
			List permisosPosible = userBI.getPermisosListaAcceso(listaVO
					.getTipo());
			frm.resetPermisos();
			for (Iterator itPermisos = permisosPosible.iterator(); itPermisos
					.hasNext();) {
				final Integer permisoPosible = ((Integer) itPermisos.next());
				int permisoEncontrado = Integer.MIN_VALUE;
				PermisosListaVO permisoListaEncontrado = null;
				if (!CollectionUtils.isEmptyCollection(permisosDeElemento)) {
					permisoListaEncontrado = ((PermisosListaVO) org.apache.commons.collections.CollectionUtils
							.find(permisosDeElemento, new Predicate() {
								public boolean evaluate(Object arg0) {
									PermisosListaVO permiLista = ((PermisosListaVO) arg0);
									return permiLista.getPerm() == permisoPosible
											.intValue();
								}
							}));
					if (permisoListaEncontrado != null)
						permisoEncontrado = permisoListaEncontrado.getPerm();
				}

				if (permisoEncontrado != Integer.MIN_VALUE)
					frm.setPermiso(permisoPosible.toString(),
							Boolean.TRUE.toString());
				else
					frm.setPermiso(permisoPosible.toString(),
							Boolean.FALSE.toString());
			}

			// obtener el vo del destinatario
			if (frm.isTipoElementoGrupo()) {
				setInTemporalSession(request,
						ControlAccesoConstants.DESTINATARIO_LISTA,
						userBI.getGrupo(frm.getIdElementoSeleccionado()));

			} else if (frm.isTipoElementoUsuario()) {

				setInTemporalSession(
						request,
						ControlAccesoConstants.DESTINATARIO_LISTA,
						new UsuarioPO(userBI.getUsuario(frm
								.getIdElementoSeleccionado()), services));

			} else if (frm.isTipoElementoOrgano()) {
				setInTemporalSession(request,
						ControlAccesoConstants.DESTINATARIO_LISTA,
						userBI.getOrganosXIdEnArchivo(frm
								.getIdElementoSeleccionado()));
			}

			saveCurrentInvocation(
					KeysClientsInvocations.USUARIOS_SELECCION_PERMISOS_EN_LISTA,
					request);

			setReturnActionFordward(request,
					mappings.findForward("seleccionar_permisos"));

		} else {
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		}

	}

	/**
	 * @param form
	 */
	private void resetInVerElemento(ActionForm form) {
		ListaAccesoForm frm = (ListaAccesoForm) form;
		frm.setPermisosSeleccionados(null);

	}

	/**
	 * @param form
	 */
	private ActionErrors validateFormInSeleccionarElemento(ListaAccesoForm form) {
		ActionErrors errors = new ActionErrors();
		if (GenericValidator.isBlankOrNull(form.getIdElementoSeleccionado())) {
			errors.add(
					ControlAccesoConstants.ES_NECESARIO_SELECCIONAR_DESTINATARIO_DE_LA_LISTA,
					new ActionError(
							ControlAccesoConstants.ES_NECESARIO_SELECCIONAR_DESTINATARIO_DE_LA_LISTA));

		}
		return (errors.size() > 0) ? errors : null;
	}

	public void agregarElementoAListaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ListaAccesoForm listaForm = (ListaAccesoForm) form;
		GestionControlUsuariosBI userBI = getGestionControlUsuarios(request);
		ListaAccesoVO listaVO = (ListaAccesoVO) getFromTemporalSession(request,
				ControlAccesoConstants.INFO_LISTA_ACCESO);
		ActionErrors errors = null;
		try {
			errors = validateFormInAgregarDestinatario(form);
			if (errors == null) {
				// if (listaForm.isTipoElementoGrupo()){
				userBI.agregarPermisosALista(listaVO.getId(),
						listaForm.getTipoElementoSeleccionado(),
						new String[] { listaForm.getIdElementoSeleccionado() },
						listaForm.getPermisosSeleccionados());
				//
				// }else if(listaForm.isTipoElementoOrgano()){
				// userBI.agregarOrganosALista(listaVO.getId(), new String[] {
				// listaForm
				// .getIdElementoSeleccionado() },
				// listaForm.getPermisosSeleccionados());
				//
				// }if(listaForm.isTipoElementoUsuario()){
				// userBI.agregarUsuariosALista(listaVO.getId(), new String[] {
				// listaForm
				// .getIdElementoSeleccionado() },
				// listaForm.getPermisosSeleccionados());
				// }

				goReturnPointExecuteLogic(mappings, form, request, response);

			} else
				ErrorsTag.saveErrors(request, errors);

		} catch (ActionNotAllowedException anae) {
			errors = guardarError(request, anae);
		}

		if (errors != null)
			goLastClientExecuteLogic(mappings, form, request, response);

	}

	public void eliminarElementoDeListaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ListaAccesoVO listaVO = (ListaAccesoVO) getFromTemporalSession(request,
				ControlAccesoConstants.INFO_LISTA_ACCESO);
		ListaAccesoForm listaForm = (ListaAccesoForm) form;
		GestionControlUsuariosBI userBI = getGestionControlUsuarios(request);
		ActionErrors errors = null;
		try {
			errors = validateFormInEliminarDestinatarios(form);
			if (errors == null) {
				userBI.eliminarElementosDeLista(listaVO.getId(),
						listaForm.getTipoElementoSeleccionado(),
						listaForm.getDestinatariosSeleccionadosABorrar());
				listaForm.resetVerElemento();
			} else
				ErrorsTag.saveErrors(request, errors);

		} catch (UsuariosNotAllowedException e) {
			errors = guardarError(request, e);
		}

		goLastClientExecuteLogic(mappings, form, request, response);
	}

	/**
	 * @param form
	 * @return
	 */
	private ActionErrors validateFormInEliminarDestinatarios(ActionForm form) {
		ActionErrors errors = new ActionErrors();
		ListaAccesoForm listaForm = (ListaAccesoForm) form;
		if (!(listaForm.getDestinatariosSeleccionadosABorrar() != null && listaForm
				.getDestinatariosSeleccionadosABorrar().length > 0)) {
			errors.add(
					ControlAccesoConstants.ERROR_NECESARIO_SELECCIONAR_AL_MENOS_UN_ELEMENTO,
					new ActionError(
							ControlAccesoConstants.ERROR_NECESARIO_SELECCIONAR_AL_MENOS_UN_ELEMENTO));
		}
		return errors.size() > 0 ? errors : null;
	}

	private ActionErrors validateFormInAgregarDestinatario(ActionForm form) {
		ActionErrors errors = new ActionErrors();
		ListaAccesoForm listaForm = (ListaAccesoForm) form;
		if (listaForm.getPermisosSeleccionados() != null) {
			if (listaForm.getPermisosSeleccionados().length == 0) {
				errors.add(
						ControlAccesoConstants.ERROR_NECESARIO_SELECCIONAR_AGUN_PERMISO,
						new ActionError(
								ControlAccesoConstants.ERROR_NECESARIO_SELECCIONAR_AGUN_PERMISO));
			}
		} else {
			errors.add(
					ControlAccesoConstants.ERROR_NECESARIO_SELECCIONAR_AGUN_PERMISO,
					new ActionError(
							ControlAccesoConstants.ERROR_NECESARIO_SELECCIONAR_AGUN_PERMISO));

		}
		return errors.size() > 0 ? errors : null;
	}

	public void verBuscadorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ListaAccesoForm frm = (ListaAccesoForm) form;
		frm.setNombre(null);

		saveCurrentInvocation(
				KeysClientsInvocations.USUARIO_BUSQUEDA_LISTAS_ACCESO, request);
		setReturnActionFordward(request,
				mappings.findForward("busqueda_listas_acceso"));
	}

	public void buscarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ListaAccesoForm frm = (ListaAccesoForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionControlUsuariosBI usuarioBI = services
				.lookupGestionControlUsuariosBI();
		List listaListasAcceso = usuarioBI.findListasAccesoByNombreYTipos(
				frm.getNombre(), frm.getTiposLista());
		// CollectionUtils.transform(usuarios, new
		// TrasnformerUsuarioToUsuarioPO(services));
		saveCurrentInvocation(
				KeysClientsInvocations.USUARIO_BUSQUEDA_LISTAS_ACCESO, request);
		request.setAttribute(ControlAccesoConstants.LISTA_LISTAS_ACCESO,
				listaListasAcceso);
		setReturnActionFordward(request,
				mappings.findForward("busqueda_listas_acceso"));
	}

}
