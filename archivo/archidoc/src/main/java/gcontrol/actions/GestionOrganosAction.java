package gcontrol.actions;

import gcontrol.ControlAccesoConstants;
import gcontrol.forms.OrganoForm;
import gcontrol.model.NombreOrganoFormat;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.UsuarioVO;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.instituciones.TipoAtributo;
import se.instituciones.exceptions.GestorOrganismosException;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import util.CollectionUtils;
import util.ErrorsTag;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.Messages;
import common.MultiEntityConstants;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.UncheckedArchivoException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;

public class GestionOrganosAction extends BaseAction {
	public void listaOrganosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI controlAccesoBI = services
				.lookupGestionControlUsuariosBI();
		List organos = controlAccesoBI.getOrganos(Boolean.TRUE);
		request.setAttribute(ControlAccesoConstants.LISTA_ORGANOS, organos);
		saveCurrentInvocation(KeysClientsInvocations.USUARIOS_LISTA_ORGANOS,
				request);
		setReturnActionFordward(request, mappings.findForward("lista_organos"));
	}

	public void altaOrganoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List orgSystems = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.getSistemasGestoresOrganismos();
		setInTemporalSession(request,
				ControlAccesoConstants.LISTA_SISTEMAS_ORGANIZACION, orgSystems);
		request.setAttribute(
				ControlAccesoConstants.LISTA_SISTEMAS_ORGANIZACION, orgSystems);
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.USUARIOS_SELECCION_ORGANO, request);
		invocation.setAsReturnPoint(true);
		setReturnActionFordward(request, mappings.findForward("alta_organo"));
	}

	class OrganoEnSistema implements InfoOrgano {
		/**
	 *
	 */
		private static final long serialVersionUID = 1L;
		String id = null;
		String codigo = null;
		String vigente = null;

		OrganoEnSistema(String id, String codigo, String vigente) {
			super();
			this.id = id;
			this.codigo = codigo;
			this.vigente = vigente;
		}

		public boolean isOrganoVigente() {
			return Constants.TRUE_STRING.equals(this.vigente);
		}

		public String getCodigo() {
			return codigo;
		}

		public String getId() {
			return id;
		}

		public String getIdPadre() {
			return null;
		}

		public int getNivel() {
			return 0;
		}

		public String getNombre() {
			return null;
		}

		public boolean equals(Object obj) {
			boolean result = false;
			if (obj != null)
				if (InfoOrgano.class.isAssignableFrom(obj.getClass())) {
					InfoOrgano objAsOrgano = (InfoOrgano) obj;
					result = StringUtils.equals(this.id, objAsOrgano.getId());
				}
			return result;
		}
	}

	public void buscarEnSistemaExternoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		OrganoForm organoForm = (OrganoForm) form;
		try {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionControlUsuariosBI controlAccesoBI = services
					.lookupGestionControlUsuariosBI();
			String extSystemCode = organoForm.getSistemaExterno();
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
			GestorOrganismos gestorOrganismos = GestorOrganismosFactory
					.getConnectorById(extSystemCode, params);
			List organosEnSistema = controlAccesoBI
					.getOrganosXIdSistExtGestor(extSystemCode);
			List organos = gestorOrganismos.recuperarOrganos(organoForm
					.getNombre());
			CollectionUtils.transform(organosEnSistema, new Transformer() {
				public Object transform(Object obj) {
					CAOrganoVO organo = (CAOrganoVO) obj;
					return new OrganoEnSistema(organo.getIdOrgSExtGestor(),
							organo.getCodigo(), organo.getVigente());
				}
			});
			if (organos != null) {
				for (Iterator i = organosEnSistema.iterator(); i.hasNext();) {
					OrganoEnSistema organo = (OrganoEnSistema) i.next();
					if (organo.isOrganoVigente()) {
						organos.remove(organo);
					}
				}
			}
			request.setAttribute(ControlAccesoConstants.LISTA_ORGANOS, organos);
			List orgSystems = (List) getFromTemporalSession(request,
					ControlAccesoConstants.LISTA_SISTEMAS_ORGANIZACION);
			request.setAttribute(
					ControlAccesoConstants.LISTA_SISTEMAS_ORGANIZACION,
					orgSystems);
			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.USUARIOS_SELECCION_ORGANO, request);
			invocation.setAsReturnPoint(true);
			setReturnActionFordward(request,
					mappings.findForward("alta_organo"));
		} catch (GestorOrganismosException goe) {
			ActionErrors errors = obtenerErrores(request, true);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					Constants.ERROR_GESTOR_ORGANIZACION));
			throw new UncheckedArchivoException(
					"Error de comunicación con sistema gestor de organizacion");
		} catch (NotAvailableException nae) {
			ActionErrors errors = obtenerErrores(request, true);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					Constants.ERROR_GESTOR_ORGANIZACION));
			throw new UncheckedArchivoException(
					"Error de comunicación con sistema gestor de organizacion");
		}
	}

	public void seleccionOrganoUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.USUARIOS_ALTA_ORGANO, request);
		invocation.setAsReturnPoint(true);

		seleccionOrgano(mappings, form, request, response);
	}

	public void seleccionOrganoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(KeysClientsInvocations.USUARIOS_ALTA_ORGANO,
				request);

		OrganoForm of = (OrganoForm) form;
		if (StringUtils.isEmpty(of.getIdOrganoSeleccionado())) {
			ActionErrors errors = obtenerErrores(request, true);
			errors.add(
					ControlAccesoConstants.NECESARIO_SELECIONAR_ORGANO_MESSAGE_KEY,
					new ActionError(
							ControlAccesoConstants.NECESARIO_SELECIONAR_ORGANO_MESSAGE_KEY));
			goBackExecuteLogic(mappings, form, request, response);

			// setReturnActionFordward(request,
			// mappings.findForward("alta_organo"));
		} else {
			seleccionOrgano(mappings, form, request, response);
		}
	}

	private void seleccionOrgano(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		OrganoForm organoForm = (OrganoForm) form;

		try {

			ServiceRepository services = getServiceRepository(request);
			// GestionControlUsuariosBI controlAccesoBI =
			// services.lookupGestionControlUsuariosBI();

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
			GestorOrganismos gestorOrganismos = GestorOrganismosFactory
					.getConnectorById(organoForm.getSistemaExterno(), params);
			String idOrgano = organoForm.getIdOrganoSeleccionado();
			organoForm.setIdEnSistemaExterno(idOrgano);
			InfoOrgano infoOrgano = gestorOrganismos.recuperarOrgano(
					TipoAtributo.IDENTIFICADOR_ORGANO, idOrgano);
			organoForm.setCodigo(infoOrgano.getCodigo());
			organoForm.setNombre(infoOrgano.getNombre());
			organoForm.setNombreLargo(NombreOrganoFormat.formatearNombreLargo(
					infoOrgano,
					gestorOrganismos.recuperarOrganosAntecesores(
							infoOrgano.getId(), 0)));
			organoForm.setVigente(Constants.TRUE_STRING);
			GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();
			List archivos = sistemaBI.getArchivos();
			request.setAttribute(ControlAccesoConstants.LISTA_ARCHIVOS,
					archivos);
			setReturnActionFordward(request,
					mappings.findForward("edicion_organo"));
		} catch (GestorOrganismosException goe) {
			ActionErrors errors = obtenerErrores(request, true);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					Constants.ERROR_GESTOR_ORGANIZACION));
			throw new UncheckedArchivoException(
					"Error de comunicación con sistema gestor de organizacion");
		} catch (NotAvailableException nae) {
			ActionErrors errors = obtenerErrores(request, true);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					Constants.ERROR_GESTOR_ORGANIZACION));
			throw new UncheckedArchivoException(
					"Error de comunicación con sistema gestor de organizacion");
		}
	}

	private ActionErrors validateForm(HttpServletRequest request, OrganoForm frm) {
		ActionErrors errors = new ActionErrors();
		if (GenericValidator.isBlankOrNull(frm.getArchivoReceptor())) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					Constants.ERROR_NO_EXISTEN_ARCHIVOS));
		}

		if (StringUtils.isEmpty(frm.getNombre())) {
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_NOMBRE,
									request.getLocale())));
		}
		return errors;
	}

	public void guardarOrganoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		OrganoForm organoForm = (OrganoForm) form;

		ActionErrors errors = validateForm(request, organoForm);

		if (errors.isEmpty()) {
			if (StringUtils.isEmpty(organoForm.getIdOrgano()))
				crearOrganoExecuteLogic(mappings, form, request, response);
			else
				modificarOrganoExecuteLogic(mappings, form, request, response);
		} else {
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void crearOrganoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI controlAccesoBI = services
				.lookupGestionControlUsuariosBI();
		OrganoForm organoForm = (OrganoForm) form;
		CAOrganoVO organo = new CAOrganoVO();
		organo.setCodigo(organoForm.getCodigo());
		organo.setNombre(organoForm.getNombre());
		organo.setNombreLargo(organoForm.getNombreLargo());
		organo.setIdArchivoReceptor(organoForm.getArchivoReceptor());
		organo.setSistExtGestor(organoForm.getSistemaExterno());
		organo.setIdOrgSExtGestor(organoForm.getIdEnSistemaExterno());
		organo.setDescripcion(organoForm.getDescripcion());
		// organo.setVigente(organoForm.getVigente());
		try {
			if (StringUtils.isEmpty(organo.getCodigo())) {
				GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();
				List archivos = sistemaBI.getArchivos();
				request.setAttribute(ControlAccesoConstants.LISTA_ARCHIVOS,
						archivos);

				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								Constants.ERROR_ORGANO_EXTERNO_SIN_CODIGO));
				setReturnActionFordward(request,
						mappings.findForward("edicion_organo"));
			} else {

				controlAccesoBI.saveOrgano(organo);

				// Obtener la información del usuario en creación
				UsuarioVO usuarioVO = (UsuarioVO) getFromTemporalSession(
						request,
						ControlAccesoConstants.INFO_USUARIO_EN_CREACION);

				if (usuarioVO != null) {
					// Guardar el usuario en creación
					getGestionControlUsuarios(request).insertUsuario(usuarioVO,
							null, null);

					removeInTemporalSession(request,
							ControlAccesoConstants.INFO_USUARIO_EN_CREACION);

					getInvocationStack(request).goToLastReturnPoint(request);
					popLastInvocation(request);

					ActionRedirect forwardVistaUsuario = new ActionRedirect(
							mappings.findForward("redirect_to_info_usuario"));
					forwardVistaUsuario.addParameter("idUsuarioSeleccionado",
							usuarioVO.getId());
					setReturnActionFordward(request, forwardVistaUsuario);
				} else {
					getInvocationStack(request).goToLastReturnPoint(request);
					ActionRedirect forwardVistaOrgano = new ActionRedirect(
							mappings.findForward("redirect_to_info_organo"));
					forwardVistaOrgano.addParameter("idOrgano",
							organo.getIdOrg());
					setReturnActionFordward(request, forwardVistaOrgano);
				}
			}
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mappings.findForward("edicion_organo"));
		} catch (GestorOrganismosException goe) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_ORGANIZACION));
			setReturnActionFordward(request,
					mappings.findForward("edicion_organo"));
		} catch (NotAvailableException nae) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_ERROR,
							new ActionError(
									Constants.ERROR_FUNCIONALIDAD_NO_DISPONIBLE));
			setReturnActionFordward(request,
					mappings.findForward("edicion_organo"));
		}
	}

	public void infoOrganoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI controlAccesoBI = services
				.lookupGestionControlUsuariosBI();
		OrganoForm organoForm = (OrganoForm) form;
		String pIdOrgano = request.getParameter("idOrgano");
		if (pIdOrgano == null)
			pIdOrgano = organoForm.getIdOrgano();
		if (pIdOrgano == null)
			throw new UncheckedArchivoException(
					"Parametro idOrgano no presente en llamada a infoOrganoExecuteLogic");
		CAOrganoVO organo = controlAccesoBI.getCAOrgProductorVOXId(pIdOrgano);
		OrganoPO organoPO = (OrganoPO) TransformerOrganoToPO.getInstance(
				services).transform(organo);
		request.setAttribute(ControlAccesoConstants.INFO_ORGANO, organoPO);
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.USUARIOS_INFO_ORGANO, request);
		invocation.setAsReturnPoint(true);
		setReturnActionFordward(request, mappings.findForward("info_organo"));
	}

	public void vincularAOrganizacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI controlAccesoBI = services
				.lookupGestionControlUsuariosBI();
		OrganoForm organoForm = (OrganoForm) form;
		String pIdOrgano = request.getParameter("idOrgano");
		if (pIdOrgano == null)
			pIdOrgano = organoForm.getIdOrgano();
		if (pIdOrgano == null)
			throw new UncheckedArchivoException(
					"Parametro idOrgano no presente en llamada a infoOrganoExecuteLogic");
		CAOrganoVO organo = controlAccesoBI.getCAOrgProductorVOXId(pIdOrgano);

		OrganoPO organoPO = (OrganoPO) TransformerOrganoToPO.getInstance(
				services).transform(organo);

		if (organoPO != null) {
			InfoOrgano organoExterno = organoPO.getOrganizacionCodigo();

			if (organoExterno != null) {
				try {
					controlAccesoBI.vincularOrganizacion(organo, organoExterno);
					goLastClientExecuteLogic(mappings, organoForm, request,
							response);
				} catch (ActionNotAllowedException anae) {
					guardarError(request, anae);
				} catch (GestorOrganismosException goe) {
					obtenerErrores(request, true)
							.add(ActionErrors.GLOBAL_ERROR,
									new ActionError(
											Constants.ERROR_GESTOR_ORGANIZACION));
				} catch (NotAvailableException nae) {
					obtenerErrores(request, true)
							.add(ActionErrors.GLOBAL_ERROR,
									new ActionError(
											Constants.ERROR_FUNCIONALIDAD_NO_DISPONIBLE));
				} catch (Exception e) {
					logger.error("Error al modificar el órgano", e);
					obtenerErrores(request, true).add(
							ActionErrors.GLOBAL_ERROR,
							new ActionError(Constants.ERROR_GENERAL_MESSAGE));
				}
			}
		}

		goLastClientExecuteLogic(mappings, organoForm, request, response);
	}

	public void pasarANoVigenteExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI controlAccesoBI = services
				.lookupGestionControlUsuariosBI();
		OrganoForm organoForm = (OrganoForm) form;
		String pIdOrgano = request.getParameter("idOrgano");
		if (pIdOrgano == null)
			pIdOrgano = organoForm.getIdOrgano();
		if (pIdOrgano == null)
			throw new UncheckedArchivoException(
					"Parametro idOrgano no presente en llamada a infoOrganoExecuteLogic");
		CAOrganoVO organo = controlAccesoBI.getCAOrgProductorVOXId(pIdOrgano);

		if (organo != null && organo.isOrganoVigente()) {
			try {
				organo.setVigente(Constants.FALSE_STRING);
				modificarOrganoCodeLogic(mappings, organoForm, request,
						response, organo);
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
			} catch (GestorOrganismosException goe) {
				obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
						new ActionError(Constants.ERROR_GESTOR_ORGANIZACION));
			} catch (NotAvailableException nae) {
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								Constants.ERROR_FUNCIONALIDAD_NO_DISPONIBLE));
			} catch (Exception e) {
				logger.error("Error al modificar el órgano", e);
				obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
						new ActionError(Constants.ERROR_GENERAL_MESSAGE));
			}
		}
		goLastClientExecuteLogic(mappings, organoForm, request, response);
	}

	protected void modificarOrganoCodeLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, CAOrganoVO CAOrganoVO)
			throws Exception {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI controlAccesoBI = services
				.lookupGestionControlUsuariosBI();
		controlAccesoBI.saveOrgano(CAOrganoVO);
	}

	public void modificarOrganoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		OrganoForm organoForm = (OrganoForm) form;
		CAOrganoVO organo = (CAOrganoVO) getFromTemporalSession(request,
				ControlAccesoConstants.INFO_ORGANO);
		organo.setNombre(organoForm.getNombre());
		organo.setNombreLargo(organoForm.getNombreLargo());
		organo.setIdArchivoReceptor(organoForm.getArchivoReceptor());
		organo.setDescripcion(organoForm.getDescripcion());
		// organo.setVigente(organoForm.getVigente());

		try {
			modificarOrganoCodeLogic(mappings, organoForm, request, response,
					organo);
			goBackExecuteLogic(mappings, organoForm, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mappings.findForward("edicion_organo"));
		} catch (GestorOrganismosException goe) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_ORGANIZACION));
			setReturnActionFordward(request,
					mappings.findForward("edicion_organo"));
		} catch (NotAvailableException nae) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_ERROR,
							new ActionError(
									Constants.ERROR_FUNCIONALIDAD_NO_DISPONIBLE));
			setReturnActionFordward(request,
					mappings.findForward("edicion_organo"));
		} catch (Exception e) {
			logger.error("Error al modificar el órgano", e);
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GENERAL_MESSAGE));
			setReturnActionFordward(request,
					mappings.findForward("edicion_organo"));
		}
	}

	public void edicionOrganoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionControlUsuariosBI controlAccesoBI = services
					.lookupGestionControlUsuariosBI();
			String pIdOrgano = request.getParameter("idOrgano");
			CAOrganoVO organo = controlAccesoBI
					.getCAOrgProductorVOXId(pIdOrgano);
			OrganoForm organoForm = (OrganoForm) form;
			organoForm.setArchivoReceptor(organo.getIdArchivoReceptor());
			organoForm.setCodigo(organo.getCodigo());
			organoForm.setNombre(organo.getNombre());
			organoForm.setDescripcion(organo.getDescripcion());
			organoForm.setVigente(organo.getVigente());

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
			GestorOrganismos gestorOrganismos = GestorOrganismosFactory
					.getConnectorById(organo.getSistExtGestor(), params);
			InfoOrgano infoOrgano = gestorOrganismos.recuperarOrgano(
					TipoAtributo.IDENTIFICADOR_ORGANO,
					organo.getIdOrgSExtGestor());

			if (infoOrgano != null) {
				organoForm.setNombreLargo(NombreOrganoFormat
						.formatearNombreLargo(infoOrgano, gestorOrganismos
								.recuperarOrganosAntecesores(
										infoOrgano.getId(), 0)));
				saveCurrentInvocation(
						KeysClientsInvocations.USUARIOS_EDICION_ORGANO, request);
				setInTemporalSession(request,
						ControlAccesoConstants.INFO_ORGANO, organo);
				GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();
				List archivos = sistemaBI.getArchivos();
				request.setAttribute(ControlAccesoConstants.LISTA_ARCHIVOS,
						archivos);
				setReturnActionFordward(request,
						mappings.findForward("edicion_organo"));
			} else {
				ActionErrors errors = obtenerErrores(request, true);
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						Constants.ERROR_GESTOR_ORGANIZACION_ORGANO_INEXISTENTE));
				goLastClientExecuteLogic(mappings, organoForm, request,
						response);
			}
		} catch (GestorOrganismosException goe) {
			ActionErrors errors = obtenerErrores(request, true);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					Constants.ERROR_GESTOR_ORGANIZACION));
			throw new UncheckedArchivoException(
					"Error de comunicación con sistema gestor de organizacion");
		} catch (NotAvailableException nae) {
			ActionErrors errors = obtenerErrores(request, true);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					Constants.ERROR_GESTOR_ORGANIZACION));
			throw new UncheckedArchivoException(
					"Error de comunicación con sistema gestor de organizacion");
		}
	}

	public void expandOrganoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String idOrgDesplegado = request.getParameter("idOrgDesplegado");
			String sistemaExterno = request.getParameter("sistemaExterno");

			ClientInvocation invocation = getInvocationStack(request)
					.getLastClientInvocation();
			invocation.addParameter("idOrgDesplegado", idOrgDesplegado);
			request.setAttribute("idOrgDesplegado", idOrgDesplegado);

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

			GestorOrganismos gestorOrganismos = GestorOrganismosFactory
					.getConnectorById(sistemaExterno, params);
			request.setAttribute(
					ControlAccesoConstants.LISTA_ORGANOS_ANTECESORES,
					CollectionUtils.reverse(gestorOrganismos
							.recuperarOrganosAntecesores(idOrgDesplegado, 0)));

			setReturnActionFordward(request,
					new ActionForward(invocation.getInvocationURI()));
		} catch (GestorOrganismosException goe) {
			ActionErrors errors = obtenerErrores(request, true);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					Constants.ERROR_GESTOR_ORGANIZACION));
			throw new UncheckedArchivoException(
					"Error de comunicación con sistema gestor de organizacion");
		} catch (NotAvailableException nae) {
			ActionErrors errors = obtenerErrores(request, true);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					Constants.ERROR_GESTOR_ORGANIZACION));
			throw new UncheckedArchivoException(
					"Error de comunicación con sistema gestor de organizacion");
		}
	}

	public void verBuscadorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(KeysClientsInvocations.USUARIO_BUSQUEDA_ORGANO,
				request);

		setReturnActionFordward(request,
				mappings.findForward("resultado_busqueda_organos"));
	}

	public void buscarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveCurrentInvocation(KeysClientsInvocations.USUARIO_BUSQUEDA_ORGANO,
				request);

		// Órganos
		OrganoForm frm = (OrganoForm) form;
		request.setAttribute(
				ControlAccesoConstants.LISTA_ORGANOS,
				getGestionControlUsuarios(request).findOrganos(frm.getCodigo(),
						frm.getNombre(), frm.getVigente()));

		setReturnActionFordward(request,
				mappings.findForward("resultado_busqueda_organos"));
	}

}
