package gcontrol.actions;

import gcontrol.ControlAccesoConstants;
import gcontrol.forms.GrupoForm;
import gcontrol.vos.ArchivoVO;
import gcontrol.vos.GrupoVO;
import gcontrol.vos.UsuarioVO;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import util.ErrorsTag;
import xml.config.ConfiguracionSistemaArchivoFactory;
import auditoria.vos.CritUsuarioVO;

import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionArchivosBI;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.model.PaisesRI;
import common.model.PaisesRIFactory;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ListUtils;
import common.vos.ComunidadVO;
import common.vos.PaisVO;

/**
 * 
 */
public class GestionGruposAction extends BaseAction {
	public void listaGruposExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();
		List listaGrupos = userBI.getGrupos();
		listaGrupos.remove(new GrupoVO(CritUsuarioVO.GLOBAL_GROUP));
		listaGrupos.remove(new GrupoVO(CritUsuarioVO.GLOBAL_GROUP_ADM));

		request.setAttribute(ControlAccesoConstants.LISTA_GRUPOS, listaGrupos);
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.USUARIOS_LISTA_GRUPOS, request);
		invocation.setAsReturnPoint(true);
		setReturnActionFordward(request, mappings.findForward("lista_grupos"));
	}

	public void verGrupoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		GrupoForm grupoForm = (GrupoForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();
		GrupoVO grupo = userBI.getGrupo(grupoForm.getIdGrupo());
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.USUARIOS_INFO_GRUPO, request);
		invocation.setAsReturnPoint(true);
		setInTemporalSession(request, ControlAccesoConstants.INFO_GRUPO,
				GrupoToPO.getInstance(services).transform(grupo));
		request.setAttribute(ControlAccesoConstants.LISTA_USUARIOS,
				userBI.getUsuariosEnGrupo(grupo.getId()));
		setReturnActionFordward(request, mappings.findForward("info_grupo"));
	}

	public void altaGrupoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.USUARIOS_ALTA_GRUPO, request);
		invocation.setAsReturnPoint(true);

		establecerEjemplo(request, (GrupoForm) form);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();
		setInTemporalSession(request, ControlAccesoConstants.LISTA_ARCHIVOS,
				sistemaBI.getArchivos());
		setReturnActionFordward(request, mappings.findForward("edicion_grupo"));
	}

	public void edicionGrupoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GrupoForm grupoForm = (GrupoForm) form;

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();
		GrupoVO grupo = userBI.getGrupo(grupoForm.getIdGrupo());
		grupoForm.setNombre(grupo.getNombre());
		grupoForm.setDescripcion(grupo.getDescripcion());
		grupoForm.setArchivoCustodia(grupo.getIdArchivo());
		if (StringUtils.isNotBlank(grupo.getIdArchivo())
				&& !"--".equals(grupo.getIdArchivo()))
			grupoForm.setPerteneceAArchivo(true);

		grupoForm.setOcultarArchivoCodigoClasificadores(grupo
				.isOcultarArchivoCodigoClasificadores());
		grupoForm.setOcultarPaisProvincia(grupo.isOcultarPaisProvincia());

		if (grupoForm.isOcultarArchivoCodigoClasificadores()
				|| grupoForm.isOcultarPaisProvincia()) {
			grupoForm.setPersonalizarSecciones(true);
		}

		establecerEjemplo(request, grupoForm);

		// Guardamos la lista de archivos en sesión para permitir cambiar el
		// archivo al que pertenecen los usuarios del grupo
		GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();
		setInTemporalSession(request, ControlAccesoConstants.LISTA_ARCHIVOS,
				sistemaBI.getArchivos());

		saveCurrentInvocation(KeysClientsInvocations.USUARIOS_EDICION_GRUPO,
				request);
		setInTemporalSession(request, ControlAccesoConstants.INFO_GRUPO,
				GrupoToPO.getInstance(services).transform(grupo));
		setReturnActionFordward(request, mappings.findForward("edicion_grupo"));
	}

	public void guardarGrupoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GrupoForm grupoForm = (GrupoForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();

		ActionErrors errors = validateInGuardarGrupo(form);

		// Comprobar si el nombre del grupo está duplicado

		if (errors == null) {
			// Solo se comprueba que no esté duplicado si el formulario no tiene
			// errores
			if (userBI.isNombreGrupoDuplicado(grupoForm.getIdGrupo(),
					grupoForm.getNombre())) {
				errors = new ActionErrors();
				errors.add(
						Constants.ERROR_REGISTRO_DUPLICADO,
						new ActionError(Constants.ERROR_REGISTRO_DUPLICADO,
								Messages.getString(Constants.ETIQUETA_NOMBRE,
										request.getLocale())));
			}
		}
		if (errors == null) {
			GrupoVO grupo = null;
			if (grupoForm.getIdGrupo() == null) {
				grupo = new GrupoVO();
				grupo.setIdArchivo(grupoForm.getArchivoCustodia());
			} else
				grupo = userBI.getGrupo(grupoForm.getIdGrupo());
			grupo.setNombre(grupoForm.getNombre());
			grupo.setDescripcion(grupoForm.getDescripcion());
			if (grupoForm.isPerteneceAArchivo())
				grupo.setIdArchivo(grupoForm.getArchivoCustodia());
			else
				grupo.setIdArchivo(Constants.STRING_EMPTY);

			if (!grupoForm.isPersonalizarSecciones()) {
				grupoForm.setOcultarArchivoCodigoClasificadores(false);
				grupoForm.setOcultarPaisProvincia(false);
			}

			grupo.setOcultarArchivoCodigoClasificadores(grupoForm
					.isOcultarArchivoCodigoClasificadores());
			grupo.setOcultarPaisProvincia(grupoForm.isOcultarPaisProvincia());

			try {
				userBI.guardarGrupo(grupo);
				ActionRedirect vistaGrupo = new ActionRedirect(
						mappings.findForward("redirect_to_info_grupo"), true);
				vistaGrupo.addParameter("idGrupo", grupo.getId());
				popLastInvocation(request);
				setReturnActionFordward(request, vistaGrupo);

			} catch (ActionNotAllowedException anae) {
				errors = guardarError(request, anae);
			}

		} else
			ErrorsTag.saveErrors(request, errors);

		if (errors != null)
			setReturnActionFordward(request,
					mappings.findForward("edicion_grupo"));

	}

	private ActionErrors validateInGuardarGrupo(ActionForm form) {
		ActionErrors errors = null;
		GrupoForm grupoForm = (GrupoForm) form;
		if (GenericValidator.isBlankOrNull(grupoForm.getNombre())) {
			errors = new ActionErrors();
			errors.add(
					ControlAccesoConstants.NECESARIO_INTRODUCIR_UN_NOMBRE,
					new ActionError(
							ControlAccesoConstants.NECESARIO_INTRODUCIR_UN_NOMBRE));
		}

		return errors;
	}

	// private void performEliminacionGrupos(GestionControlUsuariosBI userBI,
	// String[] grupos)
	// throws ActionNotAllowedException {
	// userBI.eliminarGrupo(grupos);
	// }

	public void eliminarGrupoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GrupoForm grupoForm = (GrupoForm) form;
		ServiceRepository services = getServiceRepository(request);
		try {
			if (grupoForm.getIdGrupo() != null) {
				String[] grupoAEliminar = new String[] { grupoForm.getIdGrupo() };
				services.lookupGestionControlUsuariosBI().eliminarGrupo(
						grupoAEliminar);
				goBackExecuteLogic(mappings, form, request, response);
			} else {
				obtenerErrores(request, true)
						.add(ControlAccesoConstants.NECESARIO_SELECCIONAR_UN_GRUPO,
								new ActionError(
										ControlAccesoConstants.NECESARIO_SELECCIONAR_UN_GRUPO));
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}

		/*
		 * ActionErrors errors = validateAgregarEliminarUsuarios(form);
		 * ActionMethod actionMethod = new ActionMethod(errors, mappings, form,
		 * request, response) { public void executeInCaseNOErrors() throws
		 * ActionNotAllowedException {
		 * 
		 * GrupoForm grupoForm = (GrupoForm)getForm(); ServiceRepository
		 * services =
		 * ServiceRepository.getInstance(ServiceClient.create(getAppUser
		 * (getRequest()))); String[] grupo = new String[]
		 * {grupoForm.getIdGrupo()};
		 * performEliminacionGrupos(services.lookupGestionControlUsuariosBI(),
		 * grupo);
		 * 
		 * goBackExecuteLogic(); }
		 * 
		 * public void executeInCaseErrors() {
		 * verGrupoExecuteLogic(getMappings(), getForm(), getRequest(),
		 * getResponse()); } };
		 */
	}

	public void eliminarGruposExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GrupoForm grupoForm = (GrupoForm) form;
		String[] gruposAEliminar = grupoForm.getGruposSeleccionados();

		try {
			if (gruposAEliminar == null || gruposAEliminar.length == 0)
				obtenerErrores(request, true)
						.add(ControlAccesoConstants.NECESARIO_SELECCIONAR_UN_GRUPO,
								new ActionError(
										ControlAccesoConstants.NECESARIO_SELECCIONAR_UN_GRUPO));
			else
				getGestionControlUsuarios(request).eliminarGrupo(
						gruposAEliminar);

		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public class PredicateFiltrarUsuarios implements Predicate {
		List usuariosAQuitar = null;

		PredicateFiltrarUsuarios(List usuariosAQuitar) {
			this.usuariosAQuitar = usuariosAQuitar;
		}

		public boolean evaluate(Object arg0) {
			for (Iterator itDestinatariosAQuitar = usuariosAQuitar.iterator(); itDestinatariosAQuitar
					.hasNext();) {
				UsuarioVO usuario = (UsuarioVO) itDestinatariosAQuitar.next();
				if (usuario.getId().equals(((UsuarioVO) arg0).getId()))
					return false;
			}
			return true;
		}
	}

	public void busquedaUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GrupoForm grupoForm = (GrupoForm) form;
		String query = grupoForm.getNombreUsuario();
		if (query != null)
			if (!StringUtils.isBlank(query)) {
				ServiceRepository services = ServiceRepository
						.getInstance(ServiceClient.create(getAppUser(request)));
				GestionControlUsuariosBI userBI = services
						.lookupGestionControlUsuariosBI();

				List usuarios = userBI.findUsersByName(query);
				GrupoVO grupo = (GrupoVO) getFromTemporalSession(request,
						ControlAccesoConstants.INFO_GRUPO);
				List usuariosActualesGrupo = userBI.getUsuariosEnGrupo(grupo
						.getId());

				CollectionUtils.filter(usuarios, new PredicateFiltrarUsuarios(
						usuariosActualesGrupo));
				request.setAttribute(ControlAccesoConstants.LISTA_USUARIOS,
						usuarios);

				// saveCurrentInvocation(KeysClientsInvocations.USUARIOS_SELECCION_USUARIOS,
				// request);
			} else
				obtenerErrores(request, true).add(
						Constants.ERROR_NO_SEARCH_TOKEN,
						new ActionError(Constants.ERROR_NO_SEARCH_TOKEN));

		saveCurrentInvocation(
				KeysClientsInvocations.USUARIOS_SELECCION_USUARIOS, request);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_usuarios"));
	}

	public void quitarUsuariosDeGrupoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GrupoForm grupoForm = (GrupoForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();
		GrupoVO grupo = (GrupoVO) getFromTemporalSession(request,
				ControlAccesoConstants.INFO_GRUPO);
		String[] grupoID = { grupo.getId() };
		ActionErrors errors = validateAgregarEliminarUsuarios(form);
		try {
			if (errors == null) {
				userBI.quitarUsuariosDeGrupo(grupoID,
						grupoForm.getUsuarioSeleccionado());
			} else
				ErrorsTag.saveErrors(request, errors);

		} catch (ActionNotAllowedException anae) {
			errors = guardarError(request, anae);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void agregarUsuariosAGrupoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GrupoForm grupoForm = (GrupoForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI userBI = services
				.lookupGestionControlUsuariosBI();
		GrupoVO grupo = (GrupoVO) getFromTemporalSession(request,
				ControlAccesoConstants.INFO_GRUPO);
		String[] grupoID = { grupo.getId() };
		ActionErrors errors = validateAgregarEliminarUsuarios(form);
		try {
			if (errors == null) {
				userBI.agregarUsuariosAGrupo(grupoID,
						grupoForm.getUsuarioSeleccionado());
				goBackExecuteLogic(mappings, form, request, response);
			} else
				ErrorsTag.saveErrors(request, errors);

		} catch (ActionNotAllowedException anae) {
			errors = guardarError(request, anae);
		}
		if (errors != null) {
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/**
	 * @param form
	 * @return
	 */
	private ActionErrors validateAgregarEliminarUsuarios(ActionForm form) {
		GrupoForm grupoForm = (GrupoForm) form;
		String[] usuarios = grupoForm.getUsuarioSeleccionado();
		ActionErrors errors = null;
		if (usuarios == null || usuarios.length == 0) {
			errors = new ActionErrors();
			errors.add(
					ControlAccesoConstants.NECESARIO_SELECCIONAR_UN_USUARIO,
					new ActionError(
							ControlAccesoConstants.NECESARIO_SELECCIONAR_UN_USUARIO));
		}
		return errors;
	}

	public void verBuscadorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GrupoForm frm = (GrupoForm) form;
		frm.setNombre(null);

		saveCurrentInvocation(KeysClientsInvocations.USUARIO_BUSQUEDA_GRUPOS,
				request);
		setReturnActionFordward(request,
				mappings.findForward("busqueda_grupos"));
	}

	public void buscarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		GrupoForm frm = (GrupoForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionControlUsuariosBI usuarioBI = services
				.lookupGestionControlUsuariosBI();
		List listaGrupos = usuarioBI.findGruposByName(frm.getNombre());
		listaGrupos.remove(new GrupoVO(CritUsuarioVO.GLOBAL_GROUP));
		listaGrupos.remove(new GrupoVO(CritUsuarioVO.GLOBAL_GROUP_ADM));
		// CollectionUtils.transform(usuarios, new
		// TrasnformerUsuarioToUsuarioPO(services));
		saveCurrentInvocation(KeysClientsInvocations.USUARIO_BUSQUEDA_GRUPOS,
				request);
		request.setAttribute(ControlAccesoConstants.LISTA_GRUPOS, listaGrupos);
		setReturnActionFordward(request,
				mappings.findForward("busqueda_grupos"));
	}

	// private ActionErrors validateEliminarGrupos(ActionForm form) {
	// GrupoForm grupoForm = (GrupoForm)form;
	// String[] grupos = grupoForm.getGruposSeleccionados();
	// ActionErrors errors = null;
	// if (grupos==null || grupos.length==0){
	// errors = new ActionErrors();
	// errors.add(NECESARIO_SELECCIONAR_UN_GRUPO, new
	// ActionError(NECESARIO_SELECCIONAR_UN_GRUPO));
	// }
	// return errors;
	// }

	private void establecerEjemplo(HttpServletRequest request, GrupoForm frm) {

		// Establecer país y Provincia.
		ServiceRepository services = getServiceRepository(request);
		PaisesRI paisesRI = PaisesRIFactory.createPaisesRI(
				services.lookupInfoSistemaBI(), request.getLocale());
		List listaPaises = paisesRI.getAllPaises();
		String delimitador = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos()
				.getDelimitadorCodigoReferencia();
		if (delimitador == null)
			delimitador = Constants.SLASH;

		frm.setDelimitador(delimitador);

		String ejemploPaisProvincia = "";
		List listaComunidades = null;
		if (listaPaises != null && listaPaises.size() > 0) {
			PaisVO unPais = (PaisVO) listaPaises.get(0);

			if (unPais != null) {
				ejemploPaisProvincia = unPais.getCodigo();
				listaComunidades = unPais.getComunidades();

				if (!ListUtils.isEmpty(listaComunidades)) {
					ComunidadVO comunidad = (ComunidadVO) listaComunidades
							.get(0);
					if (comunidad != null) {
						ejemploPaisProvincia += delimitador
								+ comunidad.getCodigocomunidad();
					}
				}
			}
		}

		frm.setEjemploPaisProvincia(ejemploPaisProvincia);

		// Establecer el código de archivo y clasificadores
		GestionArchivosBI archivosBI = services.lookupGestionArchivosBI();

		String ejemploArchivoCodigo = "";

		if (frm.getArchivoCustodia() != null) {
			ArchivoVO archivo = (ArchivoVO) archivosBI.getArchivoXId(frm
					.getArchivoCustodia());

			if (archivo != null) {
				ejemploArchivoCodigo = archivo.getCodigo();
			}
		}

		if (common.util.StringUtils.isEmpty(ejemploArchivoCodigo)) {

			// Obtener un archivo
			List listaArchivos = archivosBI.getListaArchivos();
			if (!ListUtils.isEmpty(listaArchivos)) {
				ArchivoVO archivo = (ArchivoVO) listaArchivos.get(0);

				if (archivo != null) {
					ejemploArchivoCodigo = archivo.getCodigo();
				}
			}
		}

		ejemploArchivoCodigo += delimitador + "1/FONDO/CLASIFICADOR/SERIE";

		frm.setEjemploArchivoCodigo(ejemploArchivoCodigo);

		// Establecer el código de unidad documental.
		String codigoUdoc = "Expediente1";
		frm.setEjemploCodigo(codigoUdoc);

	}

}
