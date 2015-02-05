package salas.actions;

import gcontrol.ControlAccesoConstants;
import gcontrol.vos.ArchivoVO;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import salas.SalasConsultaConstants;
import salas.exceptions.SalasConsultaException;
import salas.form.EdificioForm;
import salas.model.GestionSalasConsultaBI;
import salas.vos.EdificioVO;
import se.usuarios.AppUser;
import util.ErrorsTag;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.bi.GestionArchivosBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ListUtils;
import common.util.StringUtils;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class GestionEdificiosAction extends SalasConsultaBaseAction {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(GestionEdificiosAction.class);

	public void initExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.SALAS, request);
		invocation.setAsReturnPoint(true);
		EdificioForm edificioForm = (EdificioForm) form;
		edificioForm.reset(mappings, request);

		removeInTemporalSession(request, SalasConsultaConstants.EDIFICIO_KEY);

		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_INIT_EDIFICIOS));
	}

	public void listadoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			AppUser appUser = getAppUser(request);

			GestionSalasConsultaBI salasBI = getGestionSalasBI(request);

			List listaEdificios = salasBI.getEdificiosByIdsArchivo(appUser
					.getIdsArchivosUser());

			request.setAttribute(SalasConsultaConstants.LISTA_EDIFICIOS_KEY,
					listaEdificios);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_LISTADO_EDIFICIOS));
		} catch (SecurityException e) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void verEdificioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String idEdificio = request
				.getParameter(SalasConsultaConstants.PARAM_ID_EDIFICIO);

		ActionRedirect vistaEdificio = new ActionRedirect(
				mappings.findForward(SalasConsultaConstants.GLOBAL_FORWARD_VER_EDIFICIO),
				true);
		vistaEdificio.addParameter(SalasConsultaConstants.PARAM_ID_EDIFICIO,
				idEdificio);
		vistaEdificio.addParameter(SalasConsultaConstants.PARAM_REFRESHVIEW,
				Constants.BOOLEAN_TRUE_STRING);
		setReturnActionFordward(request, vistaEdificio);

	}

	public void verExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			EdificioForm frm = (EdificioForm) form;

			String idEdificio = request
					.getParameter(SalasConsultaConstants.PARAM_ID_EDIFICIO);

			idEdificio = URLDecoder.decode(idEdificio,
					Constants.ENCODING_ISO_8859_1);

			if (Boolean
					.valueOf(
							request.getParameter(SalasConsultaConstants.PARAM_REFRESHVIEW))
					.booleanValue()) {
				request.setAttribute(SalasConsultaConstants.REFRESH_VIEW_KEY,
						Boolean.TRUE);
			}

			if (StringUtils.isEmpty(idEdificio)) {
				idEdificio = frm.getIdEdificio();

			}

			if (StringUtils.isNotEmpty(idEdificio)) {

				GestionSalasConsultaBI salasBI = getGestionSalasBI(request);

				EdificioVO edificioVO = salasBI.getEdificioById(idEdificio);

				if (edificioVO != null) {
					saveCurrentInvocation(
							KeysClientsInvocations.SALAS_VER_EDIFICIO, request);

					EdificioForm edificioForm = (EdificioForm) form;
					setInTemporalSession(request,
							SalasConsultaConstants.EDIFICIO_KEY, edificioVO);
					edificioForm.set(edificioVO);

					verNodoTreeView(request, edificioVO);

					// Obtener la lista de Salas
					List listaSalas = salasBI.getSalas(idEdificio);

					setInTemporalSession(request,
							SalasConsultaConstants.LISTA_SALAS_KEY, listaSalas);

					setReturnActionFordward(
							request,
							mappings.findForward(SalasConsultaConstants.FORWARD_DATOS_EDIFICIO));
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("No se ha encontrado el objeto Edificio para el id:"
								+ idEdificio);
					}

					accionDatosElementoNoEncontrado(
							request,
							Messages.getString(SalasConsultaConstants.ETIQUETA_OBJETO_EDIFICIO));
					setReturnActionFordward(
							request,
							mappings.findForward(SalasConsultaConstants.FORWARD_LISTADO_EDIFICIOS));
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("No se ha recibido el parámetro idEdificio");
				}

				accionDatosElementoNoEncontrado(
						request,
						Messages.getString(SalasConsultaConstants.ETIQUETA_OBJETO_EDIFICIO));
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		} catch (SecurityException e) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void nuevoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		GestionSistemaBI sistemaBI = getGestionSistemaBI(request);
		GestionArchivosBI archivoBI = getGestionArchivosBI(request);
		ServiceRepository services = getServiceRepository(request);

		EdificioForm edificioForm = (EdificioForm) form;
		edificioForm.reset(mappings, request);

		// Comprobar que el usuario pertenece a una archivo
		if (ListUtils.isEmpty(getAppUser(request).getCustodyArchiveList())) {
			ActionErrors errores = new ActionErrors();

			errores.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_OPERACION_NO_PERMITIDA_USUARIO_SIN_ARCHIVOS));

			ErrorsTag.saveErrors(request, errores);

			goLastClientExecuteLogic(mappings, form, request, response);
		} else {
			removeInTemporalSession(request,
					ControlAccesoConstants.LISTA_ARCHIVOS);

			List archivos = null;
			if (ConfigConstants.getInstance().getMostrarTodasUbicaciones()) {
				archivos = sistemaBI.getArchivos();
			} else {
				List custodyArchiveList = services.getServiceClient()
						.getCustodyArchiveList();
				archivos = archivoBI.getArchivosXId(custodyArchiveList
						.toArray());
			}

			if (!ListUtils.isEmpty(archivos) && archivos.size() == 1) {
				ArchivoVO archivoVO = (ArchivoVO) archivos.get(0);
				edificioForm.setIdArchivo(archivoVO.getId());
				edificioForm.setNombreArchivo(archivoVO.getNombre());
			}
			setInTemporalSession(request,
					ControlAccesoConstants.LISTA_ARCHIVOS, archivos);

			saveCurrentInvocation(KeysClientsInvocations.SALAS_CREAR_EDIFICIO,
					request);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_EDICION_EDIFICIO));
		}
	}

	public void editarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		GestionSistemaBI sistemaBI = getGestionSistemaBI(request);
		GestionArchivosBI archivoBI = getGestionArchivosBI(request);
		ServiceRepository services = getServiceRepository(request);

		EdificioForm edificioForm = (EdificioForm) form;

		// Comprobar que el usuario pertenece a una archivo
		if (ListUtils.isEmpty(getAppUser(request).getCustodyArchiveList())) {
			ActionErrors errores = new ActionErrors();

			errores.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_OPERACION_NO_PERMITIDA_USUARIO_SIN_ARCHIVOS));

			ErrorsTag.saveErrors(request, errores);

			goLastClientExecuteLogic(mappings, form, request, response);
		} else {
			String idEdificio = request
					.getParameter(SalasConsultaConstants.PARAM_ID_EDIFICIO);

			if (StringUtils.isNotEmpty(idEdificio)) {

				removeInTemporalSession(request,
						ControlAccesoConstants.LISTA_ARCHIVOS);

				List archivos = null;
				if (ConfigConstants.getInstance().getMostrarTodasUbicaciones()) {
					archivos = sistemaBI.getArchivos();
				} else {
					List custodyArchiveList = services.getServiceClient()
							.getCustodyArchiveList();
					archivos = archivoBI.getArchivosXId(custodyArchiveList
							.toArray());
				}

				setInTemporalSession(request,
						ControlAccesoConstants.LISTA_ARCHIVOS, archivos);

				saveCurrentInvocation(
						KeysClientsInvocations.SALAS_EDITAR_EDIFICIO, request);

				EdificioVO edificioVO = salasBI.getEdificioById(idEdificio);

				if (edificioVO != null) {

					edificioForm.set(edificioVO);

					setReturnActionFordward(
							request,
							mappings.findForward(SalasConsultaConstants.FORWARD_EDICION_EDIFICIO));
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("No se ha encontrado el objeto Edificio para el id:"
								+ idEdificio);
					}

					accionDatosElementoNoEncontrado(
							request,
							Messages.getString(SalasConsultaConstants.ETIQUETA_OBJETO_EDIFICIO));
					goLastClientExecuteLogic(mappings, form, request, response);
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("No se ha recibido el parámetro idEdificio");
				}

				accionDatosElementoNoEncontrado(
						request,
						Messages.getString(SalasConsultaConstants.ETIQUETA_OBJETO_EDIFICIO));
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		}
	}

	public void guardarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		EdificioForm edificioForm = (EdificioForm) form;
		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);

		EdificioVO edificioVO = new EdificioVO();
		edificioForm.populate(edificioVO);

		ActionErrors errors = edificioForm.validate(mappings, request);

		if (errors.isEmpty()) {
			try {
				if (StringUtils.isNotEmpty(edificioVO.getId())) {
					salasBI.actualizarEdificio(edificioVO);
					actualizarNodoTreeView(request, edificioVO);
				} else {
					salasBI.insertarEdificio(edificioVO);
					edificioForm.setIdEdificio(edificioVO.getId());
					insertarNodoTreeView(request, edificioVO);
				}
				ActionRedirect vistaEdificio = new ActionRedirect(
						mappings.findForward(SalasConsultaConstants.GLOBAL_FORWARD_VER_EDIFICIO),
						true);
				vistaEdificio.addParameter(
						SalasConsultaConstants.PARAM_ID_EDIFICIO,
						edificioVO.getId());
				vistaEdificio.addParameter(
						SalasConsultaConstants.PARAM_REFRESHVIEW,
						Constants.BOOLEAN_TRUE_STRING);
				popLastInvocation(request);
				setReturnActionFordward(request, vistaEdificio);
			} catch (SecurityException e) {
				accionSinPermisos(request);
				setReturnActionFordward(
						request,
						mappings.findForward(SalasConsultaConstants.FORWARD_EDICION_EDIFICIO));
			} catch (SalasConsultaException ned) {
				guardarError(request, ned);
				setReturnActionFordward(
						request,
						mappings.findForward(SalasConsultaConstants.FORWARD_EDICION_EDIFICIO));
			} catch (Exception e) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
						Constants.GLOBAL_ARCHIGEST_EXCEPTION, e.getMessage()));
				ErrorsTag.saveErrors(request, errors);
				logger.error(
						"Se ha producido un error al guardar los datos edificio del edificio: "
								+ edificioVO, e);
				setReturnActionFordward(
						request,
						mappings.findForward(SalasConsultaConstants.FORWARD_EDICION_EDIFICIO));
			}
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_EDICION_EDIFICIO));
		}
	}

	public void eliminarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		EdificioForm frm = (EdificioForm) form;

		String idEdificio = frm.getIdEdificio();
		try {
			EdificioVO edificioVO = (EdificioVO) getFromTemporalSession(
					request, SalasConsultaConstants.EDIFICIO_KEY);

			edificioVO.setId(idEdificio);

			salasBI.eliminarEdificio(edificioVO);

			eliminarNodoTreeView(request);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_INIT_EDIFICIOS));
		} catch (SecurityException e) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (SalasConsultaException ecs) {
			ActionErrors errors = new ActionErrors();
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionError(
							SalasConsultaConstants.ERROR_NO_ELIMINAR_EDIFICIO_CON_SALAS));
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_DATOS_EDIFICIO));

		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					Constants.GLOBAL_ARCHIGEST_EXCEPTION, e.getMessage()));
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_DATOS_EDIFICIO));

			logger.error(
					"Se ha producido un error al eliminar el edificio con id: "
							+ idEdificio, e);
		}
	}
}
