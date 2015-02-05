package gcontrol.actions;

import gcontrol.ControlAccesoConstants;
import gcontrol.exceptions.UsuariosNotAllowedException;
import gcontrol.forms.UsuarioForm;
import gcontrol.view.InfoOrganoPO;
import gcontrol.view.UsuarioPO;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.GrupoVO;
import gcontrol.vos.PermisoVO;
import gcontrol.vos.RolVO;
import gcontrol.vos.UsuarioOrganoVO;
import gcontrol.vos.UsuarioVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import se.NotAvailableException;
import se.autenticacion.AuthenticationConnector;
import se.autenticacion.AuthenticationConnectorFactory;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.instituciones.exceptions.GestorOrganismosException;
import se.usuarios.AppUser;
import se.usuarios.TipoUsuario;
import util.ErrorsTag;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.Usuario;
import auditoria.vos.CritUsuarioVO;

import common.Constants;
import common.Messages;
import common.MultiEntityConstants;
import common.actions.BaseAction;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionSessionBI;
import common.bi.ServiceRepository;
import common.db.DBUtils;
import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.model.UserInfo;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;
import common.session.vos.SessionVO;
import common.util.DateUtils;

public class GestionUsuariosAction extends BaseAction {
	private class FinderTipoUsuario implements Predicate {
		String tipoToFind = null;

		FinderTipoUsuario(String tipoToFind) {
			this.tipoToFind = tipoToFind;
		}

		public boolean evaluate(Object arg0) {
			return tipoToFind.equalsIgnoreCase(((Usuario) arg0).getTipo());
		}

	}

	public void buscarUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioForm frm = (UsuarioForm) form;

		frm.resetInBuscarUsuario();

		removeInTemporalSession(request,
				ControlAccesoConstants.LISTA_USUARIOS_EXTERNOS);

		saveCurrentInvocation(KeysClientsInvocations.USUARIO_BUSCAR_USUARIOS,
				request);

		setReturnActionFordward(request,
				mappings.findForward("buscar_usuarios"));

	}

	ActionErrors validateFormParaBuscar(UsuarioForm frm) {
		ActionErrors errors = null;
		if (StringUtils.isBlank(frm.getNombreUsuarioABuscar())) {
			errors = new ActionErrors();
			errors.add(
					ControlAccesoConstants.ES_NECESARIO_INTRODUCIR_UN_VALOR_PARA_EL_NOMBRE_A_BUSCAR,
					new ActionError(
							ControlAccesoConstants.ES_NECESARIO_INTRODUCIR_UN_VALOR_PARA_EL_NOMBRE_A_BUSCAR));
		}
		return errors;
	}

	ActionErrors validateFormParaSeleccionarUsuario(UsuarioForm frm) {
		ActionErrors errors = null;
		if (StringUtils.isBlank(frm.getIdUsrSisExtGestorSeleccionado())) {
			errors = new ActionErrors();
			errors.add(
					ControlAccesoConstants.ES_NECESARIO_SELECCIONAR_UN_USUARIO,
					new ActionError(
							ControlAccesoConstants.ES_NECESARIO_SELECCIONAR_UN_USUARIO));
		}
		return errors;
	}

	ActionErrors validateFormParaSeleccionarOrgano(UsuarioForm frm) {
		ActionErrors errors = null;
		if (StringUtils.isBlank(frm.getIdOrganoInterno())) {
			errors = new ActionErrors();
			errors.add(
					ControlAccesoConstants.ES_NECESARIO_SELECCIONAR_UN_ORGANO_INTERNO,
					new ActionError(
							ControlAccesoConstants.ES_NECESARIO_SELECCIONAR_UN_ORGANO_INTERNO));
		}
		return errors;
	}

	public void realizarBusquedaUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioForm frm = (UsuarioForm) form;

		ActionErrors errors = validateFormParaBuscar(frm);
		if (errors == null) {

			List tiposUsuarios = (List) getFromTemporalSession(request,
					ControlAccesoConstants.LISTA_TIPOS_USUARIOS);
			Usuario tipoUsuarioSeleccionado = (Usuario) CollectionUtils.find(
					tiposUsuarios, new FinderTipoUsuario(frm.getTipoUsuario()));
			removeInTemporalSession(request,
					ControlAccesoConstants.LISTA_USUARIOS_EXTERNOS);
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
					.getConnector(tipoUsuarioSeleccionado.getTipo(), params);
			List usuariosEncontrados = connector.findUserByName(frm
					.getNombreUsuarioABuscar());

			// //seleccionar el primero por defecto
			// if
			// (!util.CollectionUtils.isEmptyCollection(usuariosEncontrados)){
			// UserInfo userASeleccionar =
			// (UserInfo)usuariosEncontrados.iterator().next();
			// frm.setIdUsrsExtGestor(userASeleccionar.getExternalUserId());
			// }

			setInTemporalSession(request,
					ControlAccesoConstants.LISTA_USUARIOS_EXTERNOS,
					usuariosEncontrados);
		}

		if (errors != null)
			ErrorsTag.saveErrors(request, errors);

		saveCurrentInvocation(KeysClientsInvocations.USUARIOS_NUEVO_USUARIO,
				request);

		setReturnActionFordward(request,
				mappings.findForward("buscar_usuarios"));

	}

	public void cambiarTipoUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// borrar configuracion de pagina mediante objetos de session
		removeInTemporalSession(request,
				ControlAccesoConstants.SELECCIONAR_ORGANO_INTERNO_ENABLED);
		removeInTemporalSession(request,
				ControlAccesoConstants.INFO_ORGANO_EXTERNO);
		removeInTemporalSession(request,
				ControlAccesoConstants.NO_SE_DISPONE_DE_INFORMACION_DEL_ORGANO);
		removeInTemporalSession(request,
				ControlAccesoConstants.INFO_USUARIO_SELECCIONADO);
		removeInTemporalSession(request,
				ControlAccesoConstants.INFO_ORGANO_INTERNO);
		removeInTemporalSession(request,
				ControlAccesoConstants.LISTA_USUARIOS_EXTERNOS);

		setReturnActionFordward(request,
				mappings.findForward("buscar_usuarios"));
	}

	public void seleccionarUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioForm frm = (UsuarioForm) form;

		saveCurrentInvocation(KeysClientsInvocations.USUARIOS_NUEVO_USUARIO,
				request);

		ActionErrors errors = validateFormParaSeleccionarUsuario(frm);
		if (errors == null) {

			// obtencion de los datos de tipo de usuario
			List tiposUsuarios = (List) getFromTemporalSession(request,
					ControlAccesoConstants.LISTA_TIPOS_USUARIOS);
			Usuario tipoUsuario = (Usuario) CollectionUtils.find(tiposUsuarios,
					new FinderTipoUsuario(frm.getTipoUsuario()));

			// buscar el usuario seleccionado (todos son de la misma fuente de
			// usuarios bastara el idExterno para diferenciarlos)
			List usuariosEncontrados = (List) getFromTemporalSession(request,
					ControlAccesoConstants.LISTA_USUARIOS_EXTERNOS);
			UserInfo userInfo = (UserInfo) CollectionUtils.find(
					usuariosEncontrados,
					new FinderUserInfo(frm.getIdUsrSisExtGestorSeleccionado()));
			if (userInfo == null) {
				errors = new ActionErrors();
				errors.add(
						ControlAccesoConstants.ES_NECESARIO_SELECCIONAR_UN_USUARIO,
						new ActionError(
								ControlAccesoConstants.ES_NECESARIO_SELECCIONAR_UN_USUARIO));
			} else {
				setInTemporalSession(request,
						ControlAccesoConstants.INFO_USUARIO_SELECCIONADO,
						userInfo);

				GestionControlUsuariosBI gestionControl = getGestionControlUsuarios(request);
				boolean isUsuarioDeSisInternoOrganizacion = gestionControl
						.isUsuarioDeSistemaOrganizacionInterno(frm
								.getTipoUsuario());
				frm.populateByUserInfo(userInfo,
						tipoUsuario.getIdSistGestorUsr(),
						isUsuarioDeSisInternoOrganizacion);

				// obtener el organo
				try {

					if (isUsuarioDeSisInternoOrganizacion) {
						setInTemporalSession(
								request,
								ControlAccesoConstants.SELECCIONAR_ORGANO_INTERNO_ENABLED,
								Boolean.TRUE);

						removeInTemporalSession(request,
								ControlAccesoConstants.INFO_ORGANO_EXTERNO);

					} else {
						// el organo del usuario se encuentra en un sistema
						// externo (ese sistema externo puede ESTAR o NO ESTAR!
						// definido en el tipo del usuario

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
								.getConnectorByUserType(frm.getTipoUsuario(),
										params);
						if (gestorOrganismos != null) {
							InfoOrgano infoOrgano = gestorOrganismos
									.recuperarOrganodeUsuario(userInfo
											.getOrganizationUserId());
							if (infoOrgano == null) {
								errors = new ActionErrors();
								errors.add(
										ActionErrors.GLOBAL_ERROR,
										new ActionError(
												ControlAccesoConstants.ERROR_GESTION_USUARIOS_USER_SIN_ORGANO,
												userInfo.getName()));
							} else {
								setInTemporalSession(
										request,
										ControlAccesoConstants.INFO_ORGANO_EXTERNO,
										new InfoOrganoPO(
												infoOrgano,
												tipoUsuario
														.getIdSistGestorOrg(),
												infoOrgano.getId(),
												getGestionControlUsuarios(request),
												getServiceRepository(request)));

								frm.setIdOrganoExterno(infoOrgano.getId());
							}
						}

						removeInTemporalSession(request,
								ControlAccesoConstants.LISTA_USUARIOS_EXTERNOS);
					}
				} catch (GestorOrganismosException e) {
					guardarError(request, e);

				} catch (NotAvailableException e) {
					guardarError(request, e);
				}
			}
		}

		if (errors != null) {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("buscar_usuarios"));
		} else {
			// goBackExecuteLogic(mappings, form, request, response);
			setReturnActionFordward(request,
					mappings.findForward("pantallaNuevoUsuario"));
			// setReturnActionFordward(request,
			// mappings.findForward("nuevo_usuario"));
		}
	}

	/**
	 * Va a la pantalla de búsqueda de órganos para asignárselos al usuario.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void busquedaOrganoInternoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioForm frm = (UsuarioForm) form;
		frm.setCodigoOrgano(null);
		frm.setNombreOrgano(null);

		setInTemporalSession(
				request,
				ControlAccesoConstants.TIPO_USUARIO_KEY,
				ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionControlAcceso()
						.findUsuarioByTipo(frm.getTipoUsuario()));

		removeInTemporalSession(request, ControlAccesoConstants.LISTA_ORGANOS);

		saveCurrentInvocation(KeysClientsInvocations.USUARIO_BUSCAR_ORGANO,
				request);

		setReturnActionFordward(request,
				getForwardBuscarOrganoInterno(mappings));

	}

	/**
	 * Elimina la asignación del usuario actual con su organo.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void eliminarOrganoInternoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioForm frm = (UsuarioForm) form;
		frm.setIdOrganoInterno(null);

		removeInTemporalSession(request,
				ControlAccesoConstants.INFO_ORGANO_INTERNO);

		setReturnActionFordward(request, mappings.findForward("nuevo_usuario"));

	}

	/**
	 * Busca los órganos según los criterios especificados.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void buscarOrganoInternoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioForm frm = (UsuarioForm) form;
		ServiceRepository services = getServiceRepository(request);
		List organosInternos = getGestionControlUsuarios(request).findOrganos(
				frm.getCodigoOrgano(), frm.getNombreOrgano(),
				Constants.TRUE_STRING);

		if (util.CollectionUtils.isEmpty(organosInternos)) {
			ActionErrors errors = new ActionErrors();
			errors.add(
					"archigest.archivo.cacceso.msgNoOrgInternosBusqueda",
					new ActionError(
							"archigest.archivo.cacceso.msgNoOrgInternosBusqueda"));
			ErrorsTag.saveErrors(request, errors);
			setInTemporalSession(request, ControlAccesoConstants.LISTA_ORGANOS,
					null);
		} else {
			CollectionUtils.transform(organosInternos,
					new TransformerOrganoToPO(services));
			setInTemporalSession(request, ControlAccesoConstants.LISTA_ORGANOS,
					organosInternos);
		}
		setReturnActionFordward(request,
				getForwardBuscarOrganoInterno(mappings));
	}

	private class FindOrganoVOPredicate implements Predicate {
		String idOrg = null;

		FindOrganoVOPredicate(String idOrg) {
			this.idOrg = idOrg;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
		 */
		public boolean evaluate(Object arg0) {
			return ((CAOrganoVO) arg0).getIdOrg().equalsIgnoreCase(idOrg);
		}
	}

	public void seleccionarOrganoInternoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioForm frm = (UsuarioForm) form;

		// el organo del usuario se encuentra en un sistema externo

		// CAOrganoVO organoInternoSeleccionado =
		// getGestionControlUsusarios(request)
		// .getCAOrgProductorVOXId(frm.getIdOrganoInterno());

		ActionErrors errors = validateFormParaSeleccionarOrgano(frm);
		if (errors == null) {

			List organosInternos = (List) getFromTemporalSession(request,
					ControlAccesoConstants.LISTA_ORGANOS);

			OrganoPO organoInterno = (OrganoPO) CollectionUtils.find(
					organosInternos,
					new FindOrganoVOPredicate(frm.getIdOrganoInterno()));

			setInTemporalSession(request,
					ControlAccesoConstants.INFO_ORGANO_INTERNO, organoInterno);

			removeInTemporalSession(request,
					ControlAccesoConstants.LISTA_ORGANOS);

			// pantallaNuevoUsuarioExecuteLogic(mappings, form, request,
			// response);
			goBackExecuteLogic(mappings, form, request, response);
			// setReturnActionFordward(request,
			// mappings.findForward("nuevo_usuario"));

		} else {
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		}

	}

	public void nuevoUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionControlUsuariosBI controlAccesoBI = getGestionControlUsuarios(request);

		UsuarioForm frm = (UsuarioForm) form;
		frm.resetInNuevoUsuario();

		List tipoUsuarios = controlAccesoBI
				.getTiposUsuarioConSistemaOrganizacion();
		setInTemporalSession(request,
				ControlAccesoConstants.LISTA_TIPOS_USUARIOS, tipoUsuarios);

		List sistemasOrganizacion = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.getSistemasGestoresOrganismos();
		setInTemporalSession(request,
				ControlAccesoConstants.LISTA_SISTEMAS_ORGANIZACION,
				sistemasOrganizacion);

		removeInTemporalSession(request,
				ControlAccesoConstants.USUARIO_VER_USUARIO);

		removeInTemporalSession(request,
				ControlAccesoConstants.INFO_ORGANO_EXTERNO);

		// setReturnActionFordward(request,mappings.findForward("pantallaNuevoUsuario"));

		removeInTemporalSession(request,
				ControlAccesoConstants.LISTA_USUARIOS_EXTERNOS);

		saveCurrentInvocation(KeysClientsInvocations.USUARIOS_NUEVO_USUARIO,
				request);

		setReturnActionFordward(request,
				mappings.findForward("buscar_usuarios"));

	}

	public void pantallaNuevoUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.USUARIOS_NUEVO_USUARIO,
				request);

		setReturnActionFordward(request, mappings.findForward("nuevo_usuario"));

	}

	ActionForward getForwardBuscarOrganoInterno(ActionMapping mappings) {
		return mappings.findForward("buscar_organo_interno");
	}

	ActionForward getForwardNuevoUsuario(ActionMapping mappings) {
		return mappings.findForward("nuevo_usuario");
	}

	// ActionForward getForwardEditarUsuario(ActionMapping mappings){
	// return mappings.findForward("editar_usuario");
	// }
	ActionForward getForwardVerUsuario(ActionMapping mappings) {
		return mappings.findForward("ver_usuario");
	}

	private class FinderUserInfo implements Predicate {
		String externalUserId = null;

		FinderUserInfo(String externalUserId) {
			this.externalUserId = externalUserId;
		}

		public boolean evaluate(Object arg0) {
			UserInfo userToEval = ((UserInfo) arg0);
			boolean equal = false;

			if (userToEval.getExternalUserId() != null) {
				equal = userToEval.getExternalUserId().equalsIgnoreCase(
						externalUserId);
			} else
				equal = true;

			return equal;
		}

	}

	public void guardarUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ActionErrors errors = null;
		UsuarioForm frm = (UsuarioForm) form;
		Usuario tipoUsuario = null;
		UsuarioVO usuarioVO = null;

		try {
			GestionControlUsuariosBI usuariosService = getGestionControlUsuarios(request);
			usuarioVO = new UsuarioVO();
			errors = validateForm(request, frm,
					usuariosService.isUsuarioDeSistemaOrganizacionInterno(frm
							.getTipoUsuario()));
			if (errors == null) {
				populateUserVO(frm, usuarioVO);
				List tipoUsuarios = getGestionControlUsuarios(request)
						.getTiposUsuarioConSistemaOrganizacion();
				tipoUsuario = (Usuario) CollectionUtils.find(tipoUsuarios,
						new FinderTipoUsuario(frm.getTipoUsuario()));

				// usuarios interno
				if (frm.getIdOrganoInterno() != null) {
					usuariosService.insertUsuarioConSistOrgInterno(usuarioVO,
							frm.getIdOrganoInterno(),
							DateUtils.getDate(frm.getFechaIniPeriodoValidez()),
							DateUtils.getDate(frm.getFechaFinPeriodoValidez()));
				} else {
					// usuarios con sistema organizacion y sin ello(llevara en
					// iste gestor e idORganosExterno)
					usuariosService.insertUsuario(usuarioVO,
							tipoUsuario.getIdSistGestorOrg(),
							frm.getIdOrganoExterno());
				}

				// UsuarioPO usuarioPO = new
				// UsuarioPO(usuarioVO,usuariosService);
				// setInTemporalSession(request,
				// ControlAccesoConstants.USUARIO_VER_USUARIO, usuarioPO );

				frm.setIdUsuarioSeleccionado(usuarioVO.getId());

				popLastInvocation(request);

				setReturnActionFordward(request,
						mappings.findForward("ver_usuario_despues_alta"));
			}

		} catch (UsuariosNotAllowedException e) {
			errors = guardarError(request, e);
			if (e.getCodError() == UsuariosNotAllowedException.XORGANO_NO_EXISTE_EL_SISTEMA_ES_NECESARIO_CREARLO) {
				// Guardar la información del usuario en sesión
				setInTemporalSession(request,
						ControlAccesoConstants.INFO_USUARIO_EN_CREACION,
						usuarioVO);

				ActionForward ret = new ActionForward(
						"/action/gestionOrganos?method=seleccionOrganoUsuario&idOrganoSeleccionado="
								+ frm.getIdOrganoExterno() + "&sistemaExterno="
								+ tipoUsuario.getIdSistGestorOrg());
				ret.setRedirect(true);
				setReturnActionFordward(request, ret);
				return;
			}
		}
		if (errors != null) {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request, getForwardNuevoUsuario(mappings));
		}

	}

	/**
	 * @param frm
	 */
	private ActionErrors validateForm(HttpServletRequest request,
			UsuarioForm frm, boolean isUsuarioConSistOrgInterno) {
		ActionErrors errors = new ActionErrors();

		if (GenericValidator.isBlankOrNull(frm.getNombre())) {
			errors.add(
					Constants.ERROR_REQUIRED,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_NOMBRE,
									request.getLocale())));
		}

		if (GenericValidator.isBlankOrNull(frm.getIdUsrsExtGestor())) {
			errors.add(
					ControlAccesoConstants.NO_SE_HA_SELECCIONADO_USUARIO_PARA_DAR_DE_ALTA,
					new ActionError(
							ControlAccesoConstants.NO_SE_HA_SELECCIONADO_USUARIO_PARA_DAR_DE_ALTA));

		}

		if (StringUtils.isBlank(frm.getFechaIniPeriodoValidez())
				&& !TipoUsuario.INTERNO.equals(frm.getTipoUsuario())
				&& isUsuarioConSistOrgInterno) {
			errors.add(
					ControlAccesoConstants.NO_SE_HA_RELLENADO_FECHA_INI_PERIODO_VALIDEZ,
					new ActionError(
							ControlAccesoConstants.NO_SE_HA_RELLENADO_FECHA_INI_PERIODO_VALIDEZ));
		}

		if (!GenericValidator.isBlankOrNull(frm.getFechaMaxVigencia())) {
			if (!DateUtils.isDate(frm.getFechaMaxVigencia())) {
				errors.add(
						ControlAccesoConstants.FECHA_MAX_VIGENCIA_NO_ES_UNA_FECHA_VALIDA,
						new ActionError(
								ControlAccesoConstants.FECHA_MAX_VIGENCIA_NO_ES_UNA_FECHA_VALIDA));
			}
		}

		if (!(GenericValidator.isBlankOrNull(frm.getFechaIniPeriodoValidez()) && (GenericValidator
				.isBlankOrNull(frm.getFechaFinPeriodoValidez())))) {

			if (StringUtils.isBlank(frm.getFechaFinPeriodoValidez())
					&& !TipoUsuario.INTERNO.equals(frm.getTipoUsuario())) {
				errors.add(
						Constants.ERROR_REQUIRED,
						new ActionError(Constants.ERROR_REQUIRED, Messages
								.getString(Constants.ETIQUETA_FECHA_FIN,
										request.getLocale())));
			} else if (DateUtils.isDate(frm.getFechaIniPeriodoValidez())) {
				if (!StringUtils.isBlank(frm.getFechaFinPeriodoValidez())) {
					if (DateUtils.isDate(frm.getFechaFinPeriodoValidez())) {
						Date ini = DateUtils.getDate(frm
								.getFechaIniPeriodoValidez());
						Date fin = DateUtils.getDate(frm
								.getFechaFinPeriodoValidez());
						if (ini.compareTo(fin) >= 0)
							errors.add(
									ControlAccesoConstants.FECHA_DE_FIN_VALIDEZ_HA_DE_SER_MAYOR_Q_INI_VALIDEZ,
									new ActionError(
											ControlAccesoConstants.FECHA_DE_FIN_VALIDEZ_HA_DE_SER_MAYOR_Q_INI_VALIDEZ));

					} else {
						errors.add(
								ControlAccesoConstants.FECHA_FIN_VALIDEZ_NO_ES_UNA_FECHA,
								new ActionError(
										ControlAccesoConstants.FECHA_FIN_VALIDEZ_NO_ES_UNA_FECHA));
					}
				}
			} else {
				errors.add(
						ControlAccesoConstants.FECHA_INICIO_VALIDEZ_NO_ES_UNA_FECHA,
						new ActionError(
								ControlAccesoConstants.FECHA_INICIO_VALIDEZ_NO_ES_UNA_FECHA));
			}

		}

		if (isUsuarioConSistOrgInterno) {
			if (GenericValidator.isBlankOrNull(frm.getIdOrganoInterno())) {
				errors.add(
						ControlAccesoConstants.ES_NECESARIO_SELECCIONAR_UN_ORGANO_INTERNO,
						new ActionError(
								ControlAccesoConstants.ES_NECESARIO_SELECCIONAR_UN_ORGANO_INTERNO));
			}
		}
		return errors.size() > 0 ? errors : null;
	}

	public void verUsuarioExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		UsuarioForm frm = (UsuarioForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionControlUsuariosBI usuariosService = services
				.lookupGestionControlUsuariosBI();
		UsuarioVO usuario = usuariosService.getUsuario(frm
				.getIdUsuarioSeleccionado());

		UsuarioPO usuarioPO = new UsuarioPO(usuario, services);
		setInTemporalSession(request,
				ControlAccesoConstants.USUARIO_VER_USUARIO, usuarioPO);

		removeInTemporalSession(request,
				ControlAccesoConstants.INFO_ORGANO_INTERNO);

		if (usuarioPO.isUsuarioSinSistemaOrganizacion()) {
			setReturnActionFordward(request,
					mappings.findForward("tabVerGrupoUsuario"));

		} else
			pantallaVerUsuarioExecuteLogic(mappings, form, request, response);

	}

	public void pantallaVerUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// corregir problema con navegaciones circulares al mostrar información
		// de usuario.
		UsuarioPO usuario = (UsuarioPO) getFromTemporalSession(request,
				ControlAccesoConstants.USUARIO_VER_USUARIO);
		saveCurrentInvocation(KeysClientsInvocations.USUARIO_VER_USUARIO,
				request);
		setInTemporalSession(request,
				ControlAccesoConstants.USUARIO_VER_USUARIO, usuario);
		setReturnActionFordward(request, getForwardVerUsuario(mappings));
	}

	public void edicionUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioForm frm = (UsuarioForm) form;
		UsuarioPO usuarioEnEdicion = (UsuarioPO) getFromTemporalSession(
				request, ControlAccesoConstants.USUARIO_VER_USUARIO);

		populateForm(frm, usuarioEnEdicion,
				usuarioEnEdicion.getInfoUsuarioEnOrgano());

		List tipoUsuarios = getGestionControlUsuarios(request)
				.getTiposUsuarioConSistemaOrganizacion();
		setInTemporalSession(request,
				ControlAccesoConstants.LISTA_TIPOS_USUARIOS, tipoUsuarios);

		List sistemasOrganizacion = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.getSistemasGestoresOrganismos();
		setInTemporalSession(request,
				ControlAccesoConstants.LISTA_SISTEMAS_ORGANIZACION,
				sistemasOrganizacion);

		if (usuarioEnEdicion.isUsuarioConOrganoInterno()) {
			OrganoPO organo = (OrganoPO) getFromTemporalSession(request,
					ControlAccesoConstants.INFO_ORGANO_INTERNO);

			if (organo == null)
				setInTemporalSession(request,
						ControlAccesoConstants.INFO_ORGANO_INTERNO,
						usuarioEnEdicion.getOrganoEnArchivo());
		} else
			removeInTemporalSession(request,
					ControlAccesoConstants.INFO_ORGANO_INTERNO);

		saveCurrentInvocation(KeysClientsInvocations.USUARIO_EDICION_USUARIO,
				request);

		pantallaEdicionUsuarioExecuteLogic(mappings, form, request, response);
	}

	/**
	 * @param frm
	 * @param usuarioEnEdicion
	 */
	private void populateForm(UsuarioForm frm, UsuarioVO usuarioVO,
			UsuarioOrganoVO usuarioOrgano) {
		frm.setNombre(usuarioVO.getNombre());
		frm.setApellidos(usuarioVO.getApellidos());
		frm.setEmail(usuarioVO.getEmail());
		frm.setDireccion(usuarioVO.getDireccion());
		frm.setTipoUsuario(usuarioVO.getTipo());
		if (DBUtils.getBooleanValue(usuarioVO.getHabilitado())) {
			frm.setDeshabilitado(Boolean.FALSE.toString());
		} else {
			frm.setDeshabilitado(Boolean.TRUE.toString());
		}
		frm.setFechaMaxVigencia(DateUtils.formatDate(usuarioVO
				.getFMaxVigencia()));
		frm.setIdUsrsExtGestor(usuarioVO.getIdUsrsExtGestor());
		frm.setIdUsrSistOrg(usuarioVO.getIdUsrSistOrg());
		frm.setDescripcion(usuarioVO.getDescripcion());
		if (usuarioOrgano != null) {
			frm.setFechaIniPeriodoValidez(DateUtils.formatDate(usuarioOrgano
					.getFechaIni()));
			frm.setFechaFinPeriodoValidez(DateUtils.formatDate(usuarioOrgano
					.getFechaFin()));
		}
	}

	public void pantallaEdicionUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// redirigir a la edicion
		setReturnActionFordward(request, getForwardNuevoUsuario(mappings));
	}

	public void guardarEdicionUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ActionErrors errors = null;
		try {
			GestionControlUsuariosBI usuariosService = getGestionControlUsuarios(request);
			UsuarioPO usuarioVO = (UsuarioPO) getFromTemporalSession(request,
					ControlAccesoConstants.USUARIO_VER_USUARIO);
			UsuarioForm frm = (UsuarioForm) form;
			populateUserVO(frm, usuarioVO);

			errors = validateForm(request, frm,
					usuariosService
							.isUsuarioDeSistemaOrganizacionInterno(usuarioVO
									.getTipo()));
			if (errors == null) {
				// String idOrganoUsuario = null;
				// si el usuario tiene organo interno pudo haber cambiado
				if (!StringUtils.isEmpty(frm.getIdOrganoInterno())) {
					// idOrganoUsuario = frm.getIdOrganoInterno();
					// else{
					// //si no tiene organo externo o no tiene organo asociado
					// OrganoPO organo = usuarioVO.getOrganoEnArchivo();
					// if (organo!=null){
					// //en caso de organo externo obtengo su id en archivo
					// idOrganoUsuario = organo.getIdOrg();
					// }else
					// idOrganoUsuario = null;
					// }
					// if (idOrganoUsuario!=null)
					getGestionControlUsuarios(request)
							.updateUsuarioConSistOrgInterno(
									usuarioVO,
									frm.getIdOrganoInterno(),
									DateUtils.getDate(frm
											.getFechaIniPeriodoValidez()),
									DateUtils.getDate(frm
											.getFechaFinPeriodoValidez()));
				} else {
					getGestionControlUsuarios(request).updateUsuario(usuarioVO);

					if (usuarioVO.isAdministrador()) {
						GestionControlUsuariosBI cu = getGestionControlUsuarios(request);
						cu.eliminarOrganosUsuario(usuarioVO.getId());
					}

				}
				// setReturnActionFordward(request,
				// getForwardEditarUsuario(mappings));
			} else
				ErrorsTag.saveErrors(request, errors);

		} catch (UsuariosNotAllowedException e) {
			errors = guardarError(request, e);
		}

		if (errors != null) {
			goLastClientExecuteLogic(mappings, form, request, response);
			// setReturnActionFordward(request,
			// getForwardEditarUsuario(mappings));
		} else
			goBackExecuteLogic(mappings, form, request, response);

	}

	// public class ExtractIDRole implements Transformer {
	// /* (non-Javadoc)
	// * @see
	// org.apache.commons.collections.Transformer#transform(java.lang.Object)
	// */
	// public Object transform(Object arg0) {
	// return ((RolVO)arg0).getId();
	// }
	// }
	public void listadoRolesParaAnadirExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioForm frm = (UsuarioForm) form;
		UsuarioVO usuarioVO = (UsuarioVO) getFromTemporalSession(request,
				ControlAccesoConstants.USUARIO_VER_USUARIO);

		GestionControlUsuariosBI usuariosService = getGestionControlUsuarios(request);

		List roles = null;
		int module = -100;
		if (!GenericValidator.isBlankOrNull(frm.getIdModuloSeleccionado())) {
			try {
				module = Integer.parseInt(frm.getIdModuloSeleccionado());
			} catch (NumberFormatException e) {
				module = -100;
			}
		}

		if (module != -100)
			roles = usuariosService.getRoles(new int[] { module });
		else
			roles = usuariosService.getRoles(null);

		// filtrar roles
		List posiblesRoles = null;
		List rolesActualesUsuario = usuariosService.getRolesUsuario(usuarioVO
				.getId());
		if (!util.CollectionUtils.isEmptyCollection(roles)) {
			posiblesRoles = new ArrayList();
			if (!util.CollectionUtils.isEmptyCollection(rolesActualesUsuario))
				CollectionUtils.select(roles, new PredicateQuitarRoles(
						rolesActualesUsuario), posiblesRoles);
			else
				posiblesRoles.addAll(roles);
		}

		setInTemporalSession(request, ControlAccesoConstants.LISTA_ROLES,
				posiblesRoles);

		request.setAttribute(ControlAccesoConstants.LISTA_MODULOS,
				ArchivoModules.allModules());

		saveCurrentInvocation(KeysClientsInvocations.USUARIO_ADD_ROLE, request);

		setReturnActionFordward(request,
				mappings.findForward("listado_roles_anadir"));

	}

	public class PredicateQuitarRoles implements Predicate {
		List rolesAQuitar = null;

		PredicateQuitarRoles(List rolesAQuitar) {
			this.rolesAQuitar = rolesAQuitar;
		}

		public boolean evaluate(Object arg0) {
			for (Iterator itRolesAQuitar = rolesAQuitar.iterator(); itRolesAQuitar
					.hasNext();) {
				RolVO role = (RolVO) itRolesAQuitar.next();
				if (role.getId().equals(((RolVO) arg0).getId()))
					return false;
			}
			return true;
		}
	}

	public class PredicateQuitarGrupos implements Predicate {
		List gruposAQuitar = null;

		PredicateQuitarGrupos(List gruposAQuitar) {
			this.gruposAQuitar = gruposAQuitar;
		}

		public boolean evaluate(Object arg0) {
			for (Iterator itGruposAQuitar = gruposAQuitar.iterator(); itGruposAQuitar
					.hasNext();) {
				GrupoVO grupo = (GrupoVO) itGruposAQuitar.next();
				if (grupo.getId().equals(((GrupoVO) arg0).getId()))
					return false;
			}
			return true;
		}
	}

	public void listadoGruposParaAnadirExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// UsuarioForm frm = (UsuarioForm) form;
		UsuarioVO usuarioVO = (UsuarioVO) getFromTemporalSession(request,
				ControlAccesoConstants.USUARIO_VER_USUARIO);

		GestionControlUsuariosBI usuariosService = getGestionControlUsuarios(request);
		List grupos = usuariosService.getGrupos();
		grupos.remove(new GrupoVO(CritUsuarioVO.GLOBAL_GROUP));
		grupos.remove(new GrupoVO(CritUsuarioVO.GLOBAL_GROUP_ADM));

		List gruposUsuario = usuariosService
				.getGruposUsuario(usuarioVO.getId());
		List posiblesGrupos = new ArrayList();
		CollectionUtils.select(grupos,
				new PredicateQuitarGrupos(gruposUsuario), posiblesGrupos);
		request.setAttribute(ControlAccesoConstants.LISTA_GRUPOS,
				posiblesGrupos);

		saveCurrentInvocation(KeysClientsInvocations.USUARIO_ADD_GRUPO, request);

		setReturnActionFordward(request,
				mappings.findForward("listado_grupos_anadir"));

	}

	public class PredicateFindRole implements Predicate {
		String[] idsToFilter;

		public PredicateFindRole(String[] idsToFilter) {
			this.idsToFilter = idsToFilter;
		}

		public boolean evaluate(Object arg0) {
			for (int i = 0; i < idsToFilter.length; i++) {
				if (((RolVO) arg0).getId().equalsIgnoreCase(idsToFilter[i])) {
					return true;
				}
			}
			return false;
		}
	}

	public ActionErrors validateFormSeleccionarGrupo(UsuarioForm frm) {
		ActionErrors errors = new ActionErrors();
		if (frm.getGruposSeleccionados() == null) {
			errors.add(
					ControlAccesoConstants.ES_NECESARIO_SELECCIONAR_AL_MENOS_UN_GRUPO,
					new ActionError(
							ControlAccesoConstants.ES_NECESARIO_SELECCIONAR_AL_MENOS_UN_GRUPO));
		}
		return errors.size() > 0 ? errors : null;
	}

	public void anadirRoleAUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ActionErrors errors = null;
		try {
			/* UsuarioVO usuarioVO = (UsuarioVO) */getFromTemporalSession(
					request, ControlAccesoConstants.USUARIO_VER_USUARIO);
			UsuarioForm frm = (UsuarioForm) form;
			if ((errors = validateFormSeleccionarRole(frm)) == null) {
				GestionControlUsuariosBI usuariosService = getGestionControlUsuarios(request);

				List rolesPosiblesAAnadir = (List) getFromTemporalSession(
						request, ControlAccesoConstants.LISTA_ROLES);
				List rolesAAnadir = new ArrayList(CollectionUtils.select(
						rolesPosiblesAAnadir,
						new PredicateFindRole(frm.getRolesSeleccionados())));

				usuariosService.agregarRolesUsuario(rolesAAnadir,
						new String[] { frm.getIdUsuarioSeleccionado() });
			} else {
				ErrorsTag.saveErrors(request, errors);
			}

		} catch (ActionNotAllowedException e) {
			errors = guardarError(request, e);
		}

		if (errors != null) {
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}

		goBackExecuteLogic(mappings, form, request, response);

	}

	ActionErrors validateFormSeleccionarRole(UsuarioForm frm) {
		ActionErrors errors = new ActionErrors();
		if (frm.getRolesSeleccionados() == null) {
			errors.add(
					ControlAccesoConstants.ES_NECESARIO_SELECCIONAR_AL_MENOS_UN_ROLE,
					new ActionError(
							ControlAccesoConstants.ES_NECESARIO_SELECCIONAR_AL_MENOS_UN_ROLE));
		}
		return errors.size() > 0 ? errors : null;
	}

	public void quitarRoleAUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioVO usuarioVO = (UsuarioVO) getFromTemporalSession(request,
				ControlAccesoConstants.USUARIO_VER_USUARIO);
		UsuarioForm frm = (UsuarioForm) form;
		ActionErrors errors = null;
		try {
			if ((errors = validateFormSeleccionarRole(frm)) == null) {
				// String[] idRoles = frm.getRolesSeleccionados();
				GestionControlUsuariosBI usuariosService = getGestionControlUsuarios(request);
				List rolesUsuario = usuariosService.getRolesUsuario(usuarioVO
						.getId());
				List rolesAEliminar = new ArrayList(CollectionUtils.select(
						rolesUsuario,
						new PredicateFindRole(frm.getRolesSeleccionados())));

				usuariosService.quitarRolesUsuario(rolesAEliminar,
						new String[] { usuarioVO.getId() });

			} else
				ErrorsTag.saveErrors(request, errors);

		} catch (ActionNotAllowedException e) {
			errors = guardarError(request, e);
		}

		goLastClientExecuteLogic(mappings, form, request, response);

	}

	public void quitarGrupoAUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioVO usuarioVO = (UsuarioVO) getFromTemporalSession(request,
				ControlAccesoConstants.USUARIO_VER_USUARIO);
		UsuarioForm frm = (UsuarioForm) form;
		ActionErrors errors = null;
		try {

			if ((errors = validateFormSeleccionarGrupo(frm)) == null) {
				String[] idGrupos = frm.getGruposSeleccionados();
				GestionControlUsuariosBI usuariosService = getGestionControlUsuarios(request);
				for (int i = 0; i < idGrupos.length; i++) {
					usuariosService.quitarUsuariosDeGrupo(
							new String[] { idGrupos[i] },
							new String[] { usuarioVO.getId() });
				}
			} else
				ErrorsTag.saveErrors(request, errors);

		} catch (ActionNotAllowedException e) {
			errors = guardarError(request, e);
		}

		goLastClientExecuteLogic(mappings, form, request, response);

	}

	public void anadirGrupoAUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionErrors errors = null;
		try {

			UsuarioForm frm = (UsuarioForm) form;
			if ((errors = validateFormSeleccionarGrupo(frm)) == null) {
				getGestionControlUsuarios(request).agregarUsuariosAGrupo(
						frm.getGruposSeleccionados(),
						new String[] { frm.getIdUsuarioSeleccionado() });
			} else {
				ErrorsTag.saveErrors(request, errors);
			}

		} catch (ActionNotAllowedException e) {
			errors = guardarError(request, e);
		}

		if (errors != null) {
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}

		goBackExecuteLogic(mappings, form, request, response);

	}

	/**
	 * @param frm
	 * @param usuarioVO
	 */
	private void populateUserVO(UsuarioForm frm, UsuarioVO usuarioVO) {
		usuarioVO.setNombre(frm.getNombre());
		usuarioVO.setApellidos(frm.getApellidos());
		usuarioVO.setEmail(frm.getEmail());
		usuarioVO.setDireccion(frm.getDireccion());
		usuarioVO.setTipo(frm.getTipoUsuario());
		usuarioVO.setHabilitado(DBUtils.getStringValue(frm.getHabilitado()));
		usuarioVO.setFMaxVigencia(frm.getFechaMaxVigenciaDate());
		usuarioVO.setIdUsrsExtGestor(frm.getIdUsrsExtGestor());
		usuarioVO.setIdUsrSistOrg(frm.getIdUsrSistOrg());
		usuarioVO.setDescripcion(frm.getDescripcion());
	}

	public class TrasnformerUsuarioToUsuarioPO implements Transformer {
		ServiceRepository services = null;

		TrasnformerUsuarioToUsuarioPO(ServiceRepository services) {
			this.services = services;
		}

		public Object transform(Object arg0) {
			return new UsuarioPO((UsuarioVO) arg0, services);
		}
	}

	public void listadoUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionControlUsuariosBI usuariosService = services
				.lookupGestionControlUsuariosBI();

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.USUARIO_LISTADO, request);
		invocation.setAsReturnPoint(true);
		removeInTemporalSession(request, ControlAccesoConstants.LISTA_USUARIOS);

		List usuarios = null;
		try {
			usuarios = usuariosService.getUsuarios(new PageInfo(request,
					"usuario"));
			CollectionUtils.transform(usuarios,
					new TrasnformerUsuarioToUsuarioPO(services));
		} catch (TooManyResultsException e) {
			// No se va a dar nunca esta excepción
		}

		setInTemporalSession(request, ControlAccesoConstants.LISTA_USUARIOS,
				usuarios);
		setReturnActionFordward(request, mappings.findForward("lista_usuarios"));
	}

	public void verRolesUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioForm frm = (UsuarioForm) form;
		frm.resetInVerRoles();

		GestionControlUsuariosBI usuariosService = getGestionControlUsuarios(request);

		List rolesUsuario = usuariosService.getRolesUsuario(frm
				.getIdUsuarioSeleccionado());
		request.setAttribute(ControlAccesoConstants.LISTA_ROLES_USUARIO,
				rolesUsuario);

		if (!util.CollectionUtils.isEmptyCollection(rolesUsuario)) {
			List permisosTotales = new ArrayList();
			for (Iterator itRoles = rolesUsuario.iterator(); itRoles.hasNext();) {
				RolVO rolVO = (RolVO) itRoles.next();
				List permisosXRol = usuariosService.getPermisosRol(rolVO
						.getId());
				for (int i = 0; i < permisosXRol.size(); i++) {
					final PermisoVO permiso = (PermisoVO) permisosXRol.get(i);
					if (!util.CollectionUtils.contains(permisosTotales,
							new Comparable() {
								public int compareTo(Object o) {
									return (((PermisoVO) o).getPerm() - permiso
											.getPerm());
								}
							}))
						permisosTotales.add(permiso);
				}
			}

			request.setAttribute(ControlAccesoConstants.LISTA_TOTAL_PERMISOS,
					permisosTotales);
		}

		saveCurrentInvocation(KeysClientsInvocations.USUARIO_VER_USUARIO,
				request);

		pantallaVerUsuarioExecuteLogic(mappings, form, request, response);

	}

	public void verGruposUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioForm frm = (UsuarioForm) form;
		frm.resetInVerGrupos();

		GestionControlUsuariosBI usuariosService = getGestionControlUsuarios(request);
		List gruposUsuario = usuariosService.getGruposUsuario(frm
				.getIdUsuarioSeleccionado());

		request.setAttribute(ControlAccesoConstants.LISTA_GRUPOS_USUARIO,
				gruposUsuario);

		saveCurrentInvocation(KeysClientsInvocations.USUARIO_VER_USUARIO,
				request);

		pantallaVerUsuarioExecuteLogic(mappings, form, request, response);

	}

	public void eliminarUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionErrors errors = null;
		try {
			UsuarioForm frm = (UsuarioForm) form;
			if ((errors = validateFormParaEliminarUsuario(frm, request)) == null) {

				String idUsuariosABorrar[] = frm.getUsuariosABorrar();

				GestionControlUsuariosBI usuariosService = getGestionControlUsuarios(request);

				usuariosService.eliminarUsuarios(idUsuariosABorrar);

				frm.resetBeforeDelete();

				goBackExecuteLogic(mappings, form, request, response);
				return;
			} else {
				ErrorsTag.saveErrors(request, errors);
			}
		} catch (ActionNotAllowedException e) {
			guardarError(request, e);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
		// setReturnActionFordward(request,
		// mappings.findForward("ver_usuario"));
	}

	public void eliminarUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionErrors errors = null;
		try {
			UsuarioForm frm = (UsuarioForm) form;
			if ((errors = validateFormParaEliminarUsuario(frm, request)) == null) {
				String idUsuariosABorrar[] = frm.getUsuariosABorrar();

				GestionControlUsuariosBI usuariosService = getGestionControlUsuarios(request);

				usuariosService.eliminarUsuarios(idUsuariosABorrar);

				frm.resetBeforeDelete();
			} else
				ErrorsTag.saveErrors(request, errors);

		} catch (ActionNotAllowedException e) {
			guardarError(request, e);
		}

		goLastClientExecuteLogic(mappings, form, request, response);
		// setReturnActionFordward(request,
		// mappings.findForward("lista_usuarios"));
	}

	public void verBuscadorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UsuarioForm frm = (UsuarioForm) form;
		frm.clear();
		List tiposUsuario = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.getConfiguracionControlAcceso().getUsuarios();
		setInTemporalSession(request,
				ControlAccesoConstants.LISTA_TIPOS_USUARIOS, tiposUsuario);

		saveCurrentInvocation(KeysClientsInvocations.USUARIO_BUSQUEDA, request);
		setReturnActionFordward(request,
				mappings.findForward("resultado_busqueda_usuarios"));
	}

	public void buscarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UsuarioForm frm = (UsuarioForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionControlUsuariosBI usuarioBI = services
				.lookupGestionControlUsuariosBI();
		List usuarios = usuarioBI.findUsuarios(frm.getTipoUsuario(),
				frm.getSearchTokenNombre(), frm.getSearchTokenApellidos());
		CollectionUtils.transform(usuarios, new TrasnformerUsuarioToUsuarioPO(
				services));
		saveCurrentInvocation(KeysClientsInvocations.USUARIO_BUSQUEDA, request);
		request.setAttribute(ControlAccesoConstants.LISTA_USUARIOS, usuarios);
		setReturnActionFordward(request,
				mappings.findForward("resultado_busqueda_usuarios"));
	}

	private ActionErrors validateFormParaEliminarUsuario(UsuarioForm frm,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		String idUsuariosABorrar[] = frm.getUsuariosABorrar();
		if (idUsuariosABorrar == null || idUsuariosABorrar.length == 0) {
			errors.add(
					ControlAccesoConstants.ERROR_SELECCIONE_AL_MENOS_UN_USUARIO,
					new ActionError(
							ControlAccesoConstants.ERROR_SELECCIONE_AL_MENOS_UN_USUARIO));
		} else {
			// obtener los ids de los usuarios activos en la aplicacion en ese
			// momento
			ServiceRepository services = getServiceRepository(request);
			GestionSessionBI serviceSession = services.lookupGestionSessionBI();
			GestionControlUsuariosBI controlUsuariosBI = services
					.lookupGestionControlUsuariosBI();

			List listaSessionesVOActivas = serviceSession
					.getSessionesActivasActuales();
			HashMap usuariosActuales = new HashMap();
			for (Iterator it = listaSessionesVOActivas.iterator(); it.hasNext();) {
				SessionVO sesion = (SessionVO) it.next();
				usuariosActuales.put(sesion.getIdUsuario(), sesion);
			}

			List listaUsuariosTotal = (List) getFromTemporalSession(request,
					ControlAccesoConstants.LISTA_USUARIOS);
			HashMap usuariosTotales = new HashMap();
			for (Iterator it = listaUsuariosTotal.iterator(); it.hasNext();) {
				UsuarioVO user = (UsuarioVO) it.next();
				usuariosTotales.put(user.getId(), user);
			}

			boolean eliminarUsuarioActivo = false;
			boolean masDeUnoActivo = false;
			List listaNombresUsuariosNoValidos = new ArrayList();
			for (int i = 0; i < idUsuariosABorrar.length; i++) {
				if (usuariosActuales.containsKey(idUsuariosABorrar[i])) {
					if (eliminarUsuarioActivo)
						masDeUnoActivo = true;
					eliminarUsuarioActivo = true;
					UsuarioVO user = (UsuarioVO) usuariosTotales
							.get(idUsuariosABorrar[i]);
					if (user == null)
						user = controlUsuariosBI
								.getUsuario(idUsuariosABorrar[i]);
					listaNombresUsuariosNoValidos.add(user.getNombreCompleto());
				}
			}

			if (eliminarUsuarioActivo) {
				if (masDeUnoActivo) {
					Map mapElementosError = new HashMap();
					String message = ControlAccesoConstants.ERROR_ELIMINAR_USUARIOS_ACTIVOS;
					errors.add(
							ControlAccesoConstants.ERROR_ELIMINAR_USUARIOS_ACTIVOS,
							new ActionError(message));
					mapElementosError.put(message,
							listaNombresUsuariosNoValidos);
					setInTemporalSession(request,
							Constants.LISTA_ELEMENTOS_ERROR_KEY,
							mapElementosError);
				} else {
					errors.add(
							Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(
									ControlAccesoConstants.ERROR_ELIMINAR_USUARIO_ACTIVO,
									listaNombresUsuariosNoValidos.get(0)));
				}
			}

		}

		return errors.size() > 0 ? errors : null;
	}

}
