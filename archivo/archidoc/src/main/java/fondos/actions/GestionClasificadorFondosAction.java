package fondos.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import util.ErrorsTag;
import util.TreeNode;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionFondosBI;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.navigation.TitlesToolBar;

import fondos.FondosConstants;
import fondos.exceptions.FondosOperacionNoPermitidaException;
import fondos.forms.ClasificadorFondosForm;
import fondos.forms.ClasificadorSeriesForm;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.TipoNivelCF;
import fondos.vos.ElementoCuadroClasificacionVO;
import gcontrol.model.TipoListaControlAcceso;
import gcontrol.vos.ListaAccesoVO;

/**
 * Action con las diferentes acciones que pueden ser invocadas por un usuario
 * referentes a los elementos del cuadro de clasificación de tipo clasificador
 * de series - Visualización del elemento - Alta de un clasificador de series
 * Recogida de la información de alta necesaria para la creacion que debe ser
 * suministrada por el usuario Ejecucion del alta. Incorporación al sistema de
 * un nuevo elemento con los datos suministrados - Edición de clasificador de
 * series Presentación de los datos actuales para su modificación por parte del
 * usuario Actualización del clasificador en el sistema con la información
 * modificada por el usuario - Eliminar clasificador de series - Cambio del
 * estado de vigencia de clasificador de series. Paso a vigente cuando se
 * encuentra no vigente y paso a no vigente cuando su estado es vigente - Mover
 * el clasificador de series dentro del cuadro de clasificación
 */
public class GestionClasificadorFondosAction extends BaseAction {

	// private static final String NECESARIO_SELECCIONAR_PADRE_MESSAGE_KEY =
	// "archigest.archivo.cf.necesarioSeleccionarUnaNuevaUbicacion";

	public void altaClasificadorFondosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ClasificadorFondosForm clfFondosForm = (ClasificadorFondosForm) form;

		GestionCuadroClasificacionBI bi = getGestionCuadroClasificacionBI(request);

		if (StringUtils.isEmpty(clfFondosForm.getNivelPadre())) {
			clfFondosForm.setNivelPadre(TipoNivelCF.ID_NIVEL_RAIZ);
		}
		List subNiveles = bi.getNivelesByTipo(TipoNivelCF.CLASIFICADOR_FONDOS,
				clfFondosForm.getNivelPadre());

		CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView) getFromTemporalSession(
				request, FondosConstants.CUADRO_CLF_VIEW_NAME);
		TreeNode selectedNode = treeView.getSelectedNode();

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_CREAR_CLFONDOS, request);
		invocation.setAsReturnPoint(true);

		setInTemporalSession(request,
				FondosConstants.TIPOS_CLASIFICADOR_FONDOS, subNiveles);
		if (selectedNode != null) {
			ElementoCuadroClasificacionVO padreClasificador = bi
					.getElementoCuadroClasificacion(clfFondosForm.getIdPadre());
			if (padreClasificador != null) {
				List jerarquiaElementoCF = bi.getAncestors(padreClasificador
						.getId());
				jerarquiaElementoCF.add(padreClasificador);
				setInTemporalSession(
						request,
						FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION,
						jerarquiaElementoCF);
			}
		}

		setReturnActionFordward(request,
				mappings.findForward("edicion_clasificador_fondos"));
	}

	public ActionErrors validateForm(HttpServletRequest request,
			ClasificadorSeriesForm clfFondosForm) {
		ActionErrors validationErrors = new ActionErrors();

		if (clfFondosForm.getTipoClasificador() == null)
			validationErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					Constants.TIPO_CLASIFICADOR_ES_NECESARIO));

		if (GenericValidator.isBlankOrNull(clfFondosForm.getCodigo())) {
			// Comprobar si se permite código nulo
			if (!ConfigConstants.getInstance()
					.getPermitirCodigoClasificadorNulo()) {
				validationErrors.add(Constants.CODIGO_ES_NECESARIO,
						new ActionError(Constants.CODIGO_ES_NECESARIO));
			}
		}

		if (GenericValidator.isBlankOrNull(clfFondosForm.getDenominacion()))
			validationErrors.add(Constants.DENOMINACION_ES_NECESARIO,
					new ActionError(Constants.DENOMINACION_ES_NECESARIO));

		String delimitadorCodigoReferencia = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos()
				.getDelimitadorCodigoReferencia();

		if (Constants.hasForbidenChars(clfFondosForm.getCodigo(),
				delimitadorCodigoReferencia)) {
			validationErrors.add(Constants.ERROR_INVALID_CHARACTERS,
					new ActionError(Constants.ERROR_INVALID_CHARACTERS,
							"Codigo", delimitadorCodigoReferencia));
		}

		if (ConfigConstants.getInstance().getMostrarCampoOrdenacionCuadro()) {
			if (StringUtils.isEmpty(clfFondosForm.getCodOrdenacion())) {
				validationErrors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(Constants.ERROR_REQUIRED, Messages
								.getString(
										Constants.ETIQUETA_CODIGO_ORDENACION,
										request.getLocale())));
			}
		}
		return validationErrors.size() > 0 ? validationErrors : null;
	}

	public void editarClasificadorFondosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ElementoCuadroClasificacionVO clfFondos = (ElementoCuadroClasificacionVO) getFromTemporalSession(
				request, FondosConstants.ELEMENTO_CF_KEY);

		if (!checkPermisosSobreElemento(request, clfFondos.getId(),
				FondosConstants.PERMISOS_EDICION_ELEMENTO_CUADRO)) {
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}

		GestionCuadroClasificacionBI cfBI = getGestionCuadroClasificacionBI(request);
		ElementoCuadroClasificacionVO padreClasificador = cfBI
				.getElementoCuadroClasificacion(clfFondos.getIdPadre());

		// NivelCFVO nivelPadre =
		// cfBI.getNivelCF(padreClasificador.getIdNivel());
		// List subNiveles = null;
		// if(padreClasificador!=null) subNiveles =
		// cfBI.getNivelesCF(clfFondos.getIdNivel());

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_EDITAR_CLFONDOS, request);
		invocation.setAsReturnPoint(true);

		List jerarquiaElementoCF = null;
		if (padreClasificador != null)
			jerarquiaElementoCF = cfBI.getAncestors(clfFondos.getId());
		setInTemporalSession(request,
				FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION,
				jerarquiaElementoCF);

		ClasificadorFondosForm clfFondosForm = (ClasificadorFondosForm) form;
		clfFondosForm.setId(clfFondos.getId());
		clfFondosForm.setDenominacion(clfFondos.getTitulo());
		clfFondosForm.setCodigo(clfFondos.getCodigo());
		clfFondosForm.setTipoClasificador(clfFondos.getIdNivel());
		clfFondosForm.setNombreNivel(cfBI.getNivelCF(clfFondos.getIdNivel())
				.getNombre());
		clfFondosForm.setCodOrdenacion(clfFondos.getOrdPos());
		clfFondosForm.setCodigoOld(clfFondos.getCodigo());
		setReturnActionFordward(request,
				mappings.findForward("edicion_clasificador_fondos"));
	}

	public void guardarClasificadorFondosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			ClasificadorFondosForm clfFondosForm = (ClasificadorFondosForm) form;
			GestionCuadroClasificacionBI bi = getGestionCuadroClasificacionBI(request);
			CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView) getFromTemporalSession(
					request, FondosConstants.CUADRO_CLF_VIEW_NAME);
			TreeNode nodoSeleccionado = treeView.getSelectedNode();

			ActionErrors errors = validateForm(request, clfFondosForm);
			if (errors == null) {
				// boolean sinPadre=false;
				if (StringUtils.isEmpty(clfFondosForm.getId())) {
					ElementoCuadroClasificacionVO elementoPadre = null;
					if (nodoSeleccionado != null) {
						elementoPadre = (ElementoCuadroClasificacionVO) nodoSeleccionado
								.getModelItem();
					} else {
						elementoPadre = new ElementoCuadroClasificacion();
						// sinPadre=true;
					}
					ElementoCuadroClasificacionVO clfFondos = bi
							.crearClasificadorFondos(elementoPadre,
									clfFondosForm.getTipoClasificador(),
									clfFondosForm.getCodigo(),
									clfFondosForm.getDenominacion(),
									clfFondosForm.getCodOrdenacion());
					// if(sinPadre) bi.setEstadoVigencia(clfFondos,
					// !clfFondos.isVigente());
					TreeNode addedNode = treeView.itemAdded(elementoPadre,
							clfFondos);
					nodoSeleccionado = treeView.setSelectedNode(addedNode);

				} else {
					ElementoCuadroClasificacionVO clfFondos = (ElementoCuadroClasificacionVO) nodoSeleccionado
							.getModelItem();
					clfFondos.setOrdPos(clfFondosForm.getCodOrdenacion());
					bi.updateClasificadorFondos(clfFondos,
							clfFondosForm.getTipoClasificador(),
							clfFondosForm.getCodigo(),
							clfFondosForm.getDenominacion(),
							clfFondosForm.getCodigoOld());
				}
				popLastInvocation(request);
				ActionRedirect result = new ActionRedirect(
						mappings.findForward("refreshView"));
				result.addParameter("node", nodoSeleccionado.getNodePath(),
						false);
				result.addParameter("refreshView", "true");
				result.setRedirect(true);
				setReturnActionFordward(request, result);
			} else {
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("edicion_clasificador_fondos"));
			}

		} catch (FondosOperacionNoPermitidaException e) {
			guardarError(request, e);
			setReturnActionFordward(request,
					mappings.findForward("edicion_clasificador_fondos"));
		}
	}

	public void verClasificadorFondosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionCuadroClasificacionBI bi = getGestionCuadroClasificacionBI(request);
		String idClasificadorFondos = request.getParameter("idelementocf");

		if (!puedeAccederUsuarioAElemento(request, idClasificadorFondos)) {
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}

		ElementoCuadroClasificacionVO elementoCF = bi
				.getElementoCuadroClasificacion(idClasificadorFondos);

		request.setAttribute(FondosConstants.CHILD_TYPES_ELEMENTO_CF_KEY,
				bi.getTiposSubniveles(elementoCF.getIdNivel()));

		if (Boolean.valueOf(request.getParameter("refreshView")).booleanValue())
			request.setAttribute(FondosConstants.REFRESH_VIEW_KEY, Boolean.TRUE);

		setInTemporalSession(request, FondosConstants.LISTA_SUBNIVELES_KEY,
				bi.getNivelesCF(elementoCF.getIdNivel()));

		setReturnActionFordward(request,
				mappings.findForward("view_clasificadorFondos"));

		ElementoCuadroClasificacionVO padreClasificador = bi
				.getElementoCuadroClasificacion(elementoCF.getIdPadre());
		List jerarquiaElementoCF = null;
		if (padreClasificador != null)
			jerarquiaElementoCF = bi.getAncestors(elementoCF.getId());
		setInTemporalSession(request,
				FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION,
				jerarquiaElementoCF);

		List listaDescendientes = bi
				.getHijosElementoCuadroClasificacion(elementoCF.getId());
		request.setAttribute(FondosConstants.LISTA_ELEMENTOS_CF,
				listaDescendientes);
		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_ELEMENTO_DEL_CUADRO, request)
				.setTitleNavigationToolBar(
						TitlesToolBar.CUADRO_VER_CLASIFICADOR_FONDO);

		if (StringUtils.isNotEmpty(elementoCF.getIdLCA())) {
			ListaAccesoVO listaAcceso = getGestionControlUsuarios(request)
					.getListaAcceso(elementoCF.getIdLCA());
			setInTemporalSession(request,
					FondosConstants.LISTAS_CONTROL_ACCESO_KEY, listaAcceso);
		} else {
			removeInTemporalSession(request,
					FondosConstants.LISTAS_CONTROL_ACCESO_KEY);
		}

		setInTemporalSession(request, FondosConstants.ELEMENTO_CF_KEY,
				elementoCF);
	}

	public void eliminarClasificadorFondosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionCuadroClasificacionBI bi = getGestionCuadroClasificacionBI(request);
		/*
		 * ElementoCuadroClasificacionVO clfSeries =
		 * bi.getElementoCuadroClasificacion(clfSeriesForm.getId());
		 */
		CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView) getFromTemporalSession(
				request, FondosConstants.CUADRO_CLF_VIEW_NAME);
		TreeNode nodoSeleccionado = treeView.getSelectedNode();
		ElementoCuadroClasificacionVO clfFondos = (ElementoCuadroClasificacionVO) nodoSeleccionado
				.getModelItem();
		ActionRedirect result = null;

		try {
			bi.removeElementoCF(clfFondos);
			treeView.removeNode(nodoSeleccionado);
			result = new ActionRedirect(mappings.findForward("refreshView"),
					true);

			// Añadido para el caso en que se borre un clasificador de fondo
			// pero cuyo idPadre es nulo
			if (nodoSeleccionado.getParent() != null) {
				treeView.setSelectedNode(nodoSeleccionado = nodoSeleccionado
						.getParent());
				result.addParameter("node", nodoSeleccionado.getNodePath(),
						false);
				result.addParameter("refreshView", "true");
			} else {
				result.addParameter("refreshView", "true");
				result.addParameter("node", Constants.TREE_VIEW_ROOT_NODE,
						false);
			}

			setReturnActionFordward(request, result);

		} catch (ActionNotAllowedException nee) {
			guardarError(request, nee);
			verClasificadorFondosExecuteLogic(mappings, form, request, response);
		}

	}

	public void cambiarEstadoVigenciaClasificadorFondosExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView) getFromTemporalSession(
				request, FondosConstants.CUADRO_CLF_VIEW_NAME);

		TreeNode nodoSeleccionado = treeView.getSelectedNode();
		ElementoCuadroClasificacionVO clfFondos = (ElementoCuadroClasificacionVO) nodoSeleccionado
				.getModelItem();
		try {
			GestionCuadroClasificacionBI bi = getGestionCuadroClasificacionBI(request);
			bi.setEstadoVigencia(clfFondos, !clfFondos.isVigente());

		} catch (FondosOperacionNoPermitidaException cnve) {
			guardarError(request, cnve);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void cancelarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		goBackExecuteLogic(mappings, form, request, response);
	}

	public void editarInfoClasificadorFondoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionControlUsuariosBI usuariosBI = getGestionControlUsuarios(request);
		ElementoCuadroClasificacion elementoCF = (ElementoCuadroClasificacion) getFromTemporalSession(
				request, FondosConstants.ELEMENTO_CF_KEY);

		if (!checkPermisosSobreElemento(request, elementoCF.getId(),
				FondosConstants.PERMISOS_EDICION_ELEMENTO_CUADRO)) {
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}

		if (elementoCF != null) {
			ClasificadorFondosForm clFondoForm = (ClasificadorFondosForm) form;
			clFondoForm.setNivelAcceso(elementoCF.getNivelAcceso());
			clFondoForm.setIdLCA(elementoCF.getIdLCA());

			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_EDITAR_CLFONDOS, request);
			invocation.setAsReturnPoint(true);

			request.setAttribute(
					FondosConstants.LISTAS_CONTROL_ACCESO_KEY,
					usuariosBI
							.getListasControlAccesoByTipo(TipoListaControlAcceso.ELEMENTO_CUADRO_CLASIFICACION));

			request.setAttribute(FondosConstants.ELEMENTO_CF_KEY, elementoCF);
			setReturnActionFordward(request,
					mappings.findForward("edicion_info_clfondo"));
		} else {
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void guardarInfoCLFondoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionFondosBI fondosService = getGestionFondosBI(request);
		ClasificadorFondosForm clFondoForm = (ClasificadorFondosForm) form;
		ElementoCuadroClasificacion elementoCF = (ElementoCuadroClasificacion) getFromTemporalSession(
				request, FondosConstants.ELEMENTO_CF_KEY);
		if (elementoCF != null) {
			elementoCF.setNivelAcceso(clFondoForm.getNivelAcceso());
			elementoCF.setIdLCA(clFondoForm.getIdLCA());
			fondosService.updateInfoAccesoElemento(elementoCF.getId(),
					elementoCF.getNivelAcceso(), elementoCF.getIdLCA());
		}
		goBackExecuteLogic(mappings, form, request, response);
	}
}