package fondos.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;
import util.ErrorsTag;
import util.TreeModelItem;
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
import common.bi.GestionSeriesBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.navigation.TitlesToolBar;
import common.view.POUtils;

import fondos.FondosConstants;
import fondos.exceptions.FondosOperacionNoPermitidaException;
import fondos.forms.ClasificadorSeriesForm;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.IElementoCuadroClasificacion;
import fondos.model.TipoNivelCF;
import fondos.utils.FondosUtils;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.INivelCFVO;
import fondos.vos.SerieVO;
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
public class GestionClasificadorSeriesAction extends BaseAction {

	public class ElementoCFPO extends ElementoCuadroClasificacion {
		GestionSeriesBI seriesServices = null;

		public ElementoCFPO(ElementoCuadroClasificacionVO elemento,
				GestionSeriesBI seriesServices) {
			POUtils.copyVOProperties(this, elemento);
			this.seriesServices = seriesServices;
		}

		public int getEstado() {
			if (isTipoSerie()
					&& super.getEstado() != IElementoCuadroClasificacion.TEMPORAL) {
				SerieVO serie = seriesServices.getSerie(getId());
				if (serie != null)// aunq sea de tipo serie puede no estar aun
									// creada (solitud de alta)
					return serie.getEstadoserie();
			}
			return super.getEstado();
		}
	};

	public class TransformToElemtoCFPO implements Transformer {
		GestionSeriesBI seriesServices = null;

		public TransformToElemtoCFPO(GestionSeriesBI seriesServices) {
			this.seriesServices = seriesServices;
		}

		public Object transform(Object arg0) {
			return new ElementoCFPO((ElementoCuadroClasificacionVO) arg0,
					seriesServices);
		}
	};

	public void verClasificadorSeriesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionCuadroClasificacionBI bi = services
				.lookupGestionCuadroClasificacionBI();
		String idClasificador = request.getParameter("idelementocf");

		if (!puedeAccederUsuarioAElemento(request, idClasificador)) {
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}

		ElementoCuadroClasificacionVO elementoCF = bi
				.getElementoCuadroClasificacion(idClasificador);

		// List childs = elementoCF.getChilds();
		try {
			elementoCF.setCodReferenciaPersonalizado(FondosUtils
					.getCodReferenciaPorSecciones(getAppUser(request),
							elementoCF.getIdFondo(),
							elementoCF.getCodReferencia(), services));
		} catch (Exception e) {
			logger.error(
					"Error al obtener el código de referencia personalizado.",
					e);
		}

		List childs = null;

		Collection listaTiposSubniveles = bi.getTiposSubniveles(elementoCF
				.getIdNivel());

		request.setAttribute(FondosConstants.CHILD_TYPES_ELEMENTO_CF_KEY,
				listaTiposSubniveles);

		boolean childsAreSerie = false;
		if (!util.CollectionUtils.isEmpty(listaTiposSubniveles)) {
			Iterator it = listaTiposSubniveles.iterator();
			while (it.hasNext()) {
				TipoNivelCF tipoNivelCF = (TipoNivelCF) it.next();
				if (tipoNivelCF.equals(TipoNivelCF.SERIE)) {
					childsAreSerie = true;
				}

			}
		}

		if (childsAreSerie) {// Solo para las series
			childs = bi.getHijosElementoCuadroClasificacion(elementoCF.getId(),
					false);
		} else {
			childs = bi.getHijosElementoCuadroClasificacion(elementoCF.getId());
		}

		CollectionUtils.transform(childs, new TransformToElemtoCFPO(
				getGestionSeriesBI(request)));

		if (Boolean.valueOf(request.getParameter("refreshView")).booleanValue())
			request.setAttribute(FondosConstants.REFRESH_VIEW_KEY, Boolean.TRUE);

		setInTemporalSession(request, FondosConstants.LISTA_SUBNIVELES_KEY,
				bi.getNivelesCF(elementoCF.getIdNivel()));

		setReturnActionFordward(request,
				mappings.findForward("view_clasificadorSeries"));

		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_ELEMENTO_DEL_CUADRO, request)
				.setTitleNavigationToolBar(
						new StringBuffer(
								TitlesToolBar.CUADRO_VER_CLASIFICADOR_SERIES)
								.append("_").append(elementoCF.getIdNivel())
								.toString());

		// esto al final pq sino si existe un punto de retorno por
		// encima(identificacion), estamos asociando todo a ese nodo
		// y a liberarlo(al hacer este save) perdemos los objetos de session
		setInTemporalSession(request, FondosConstants.ELEMENTO_CF_KEY,
				elementoCF);
		setInTemporalSession(request, FondosConstants.NIVEL_CF_KEY,
				bi.getNivelCF(elementoCF.getIdNivel()));
		setInTemporalSession(request, FondosConstants.CHILDS_ELEMENTO_CF_KEY,
				childs);

		List jerarquiaElementoCF = null;
		jerarquiaElementoCF = bi.getAncestors(elementoCF.getId());
		setInTemporalSession(request,
				FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION,
				jerarquiaElementoCF);

		ElementoCuadroClasificacionVO padre = bi
				.getElementoCuadroClasificacion(elementoCF.getIdPadre());
		if (padre != null)
			setInTemporalSession(request, FondosConstants.NIVEL_CF_PADRE_KEY,
					bi.getNivelCF(padre.getIdNivel()));

		if (StringUtils.isNotEmpty(elementoCF.getIdLCA())) {
			ListaAccesoVO listaAcceso = getGestionControlUsuarios(request)
					.getListaAcceso(elementoCF.getIdLCA());
			setInTemporalSession(request,
					FondosConstants.LISTAS_CONTROL_ACCESO_KEY, listaAcceso);
		} else {
			removeInTemporalSession(request,
					FondosConstants.LISTAS_CONTROL_ACCESO_KEY);
		}
	}

	public void altaClasificadorSeriesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ClasificadorSeriesForm clfSeriesForm = (ClasificadorSeriesForm) form;
		GestionCuadroClasificacionBI bi = getGestionCuadroClasificacionBI(request);

		List subNiveles = bi.getNivelesNoSerieCF(clfSeriesForm.getNivelPadre());

		CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView) getFromTemporalSession(
				request, FondosConstants.CUADRO_CLF_VIEW_NAME);
		TreeNode selectedNode = treeView.getSelectedNode();

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_CREAR_CLSERIES, request);
		invocation.setAsReturnPoint(true);

		setInTemporalSession(request,
				FondosConstants.TIPOS_CLASIFICADOR_SERIES, subNiveles);
		if (selectedNode != null) {
			ElementoCuadroClasificacionVO padreClasificador = bi
					.getElementoCuadroClasificacion(clfSeriesForm.getIdPadre());
			List jerarquiaElementoCF = bi.getAncestors(padreClasificador
					.getId());
			jerarquiaElementoCF.add(padreClasificador);
			setInTemporalSession(request,
					FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION,
					jerarquiaElementoCF);
		}

		setReturnActionFordward(request,
				mappings.findForward("edicion_clasificador_series"));
	}

	public ActionErrors validateForm(HttpServletRequest request,
			ClasificadorSeriesForm clfSeriesForm) {
		ActionErrors validationErrors = new ActionErrors();

		if (clfSeriesForm.getTipoClasificador() == null)
			validationErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					Constants.TIPO_CLASIFICADOR_ES_NECESARIO));
		if (GenericValidator.isBlankOrNull(clfSeriesForm.getCodigo())) {
			// Comprobar si se permite código nulo
			if (!ConfigConstants.getInstance()
					.getPermitirCodigoClasificadorNulo()) {
				validationErrors.add(Constants.CODIGO_ES_NECESARIO,
						new ActionError(Constants.CODIGO_ES_NECESARIO));
			}
		}
		if (GenericValidator.isBlankOrNull(clfSeriesForm.getDenominacion()))
			validationErrors.add(Constants.DENOMINACION_ES_NECESARIO,
					new ActionError(Constants.DENOMINACION_ES_NECESARIO));

		String delimitadorCodigoReferencia = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos()
				.getDelimitadorCodigoReferencia();

		if (Constants.hasForbidenChars(clfSeriesForm.getCodigo(),
				delimitadorCodigoReferencia)) {
			validationErrors.add(Constants.ERROR_INVALID_CHARACTERS,
					new ActionError(Constants.ERROR_INVALID_CHARACTERS,
							"Codigo", delimitadorCodigoReferencia));
		}

		if (ConfigConstants.getInstance().getMostrarCampoOrdenacionCuadro()) {
			if (StringUtils.isEmpty(clfSeriesForm.getCodOrdenacion())) {
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

	// public void crearClasificadorSeriesExecuteLogic(ActionMapping mappings,
	// ActionForm form,
	// HttpServletRequest request, HttpServletResponse response) {
	//
	// ClasificadorSeriesForm clfSeriesForm = (ClasificadorSeriesForm) form;
	// ActionErrors errors = validateForm(request, clfSeriesForm);
	// if (errors == null) {
	// try {
	//
	// GestionCuadroClasificacionBI gestionCuadroClasificacionBI =
	// getGestionCuadroClasificacionBI(request);
	// String idPadre = clfSeriesForm.getIdPadre();
	// String nivelPadre = clfSeriesForm.getNivelPadre();
	//
	// CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView)
	// getFromTemporalSession(
	// request, FondosConstants.CUADRO_CLF_VIEW_NAME);
	//
	// //String parentNodePath = request.getParameter("parentNode");
	// //TreeNode parentNode = treeView.getNode(parentNodePath);
	// TreeNode parentNode = treeView.getSelectedNode();
	// ElementoCuadroClasificacionVO clfSeries = gestionCuadroClasificacionBI
	// .crearClasificadorSeries(
	// (ElementoCuadroClasificacionVO) parentNode.getModelItem(),
	// clfSeriesForm.getTipoClasificador(),
	// clfSeriesForm.getCodigo(),
	// clfSeriesForm.getDenominacion());
	//
	// TreeNode addedNode = treeView.itemAdded((TreeModelItem) clfSeries);
	// treeView.setSelectedNode(addedNode);
	//
	// /*
	// * CuadroClasificacionTreeView treeView =
	// *
	// (CuadroClasificacionTreeView)request.getSession().getAttribute(FondosConstants.CUADRO_CLF_VIEW_NAME);
	// * treeView.itemAdded((TreeModelItem)clfSeries);
	// */
	//
	// ActionRedirect result = new ActionRedirect(mappings
	// .findForward("refreshView"));
	// //result.addParameter("idelementocf", clfSeries.getId());
	// result.addParameter("node", addedNode.getNodePath());
	// result.addParameter("refreshView", "true");
	// result.setRedirect(true);
	//
	// //request.setAttribute(FondosConstants.ELEMENTO_CF_KEY, clfSeries);
	// setReturnActionFordward(request, result);
	//
	// } catch (FondosOperacionNoPermitidaException nuce) {
	// errors = guardarError(request, nuce);
	// //errors = manageFondosOperacionNoPermitida(request, nuce);
	// }
	// }
	//
	// if (errors!=null){
	// ErrorsTag.saveErrors(request, errors);
	// altaClasificadorSeriesExecuteLogic(mappings, form, request,
	// response);
	// return;
	// }
	// }

	protected ActionErrors manageFondosOperacionNoPermitida(
			HttpServletRequest request, FondosOperacionNoPermitidaException e) {
		ActionErrors errors = null;
		if (e.getCodError() == FondosOperacionNoPermitidaException.XCODIGO_YA_EXISTE) {
			errors = obtenerErrores(request, true);
			errors.add(Constants.ERROR_CODIGO_CLFSERIE_DUPLICADO,
					new ActionError(Constants.ERROR_CODIGO_CLFSERIE_DUPLICADO));
		}
		return errors;
	}

	public void editarClasificadorSeriesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ElementoCuadroClasificacionVO clfSeries = (ElementoCuadroClasificacionVO) getFromTemporalSession(
				request, FondosConstants.ELEMENTO_CF_KEY);

		if (!checkPermisosSobreElemento(request, clfSeries.getId(),
				FondosConstants.PERMISOS_EDICION_ELEMENTO_CUADRO)) {
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}

		GestionCuadroClasificacionBI cfBI = getGestionCuadroClasificacionBI(request);
		ElementoCuadroClasificacionVO padreClasificador = cfBI
				.getElementoCuadroClasificacion(clfSeries.getIdPadre());

		// NivelCFVO nivelPadre =
		// cfBI.getNivelCF(padreClasificador.getIdNivel());
		// List subNiveles = cfBI.getNivelesCF(padreClasificador.getIdNivel());

		// NivelCFVO nivelPadre =
		// cfBI.getNivelCF(padreClasificador.getIdNivel());
		List subNiveles = new ArrayList();

		INivelCFVO nivel = cfBI.getNivelCF(clfSeries.getIdNivel());
		if (nivel != null)
			subNiveles.add(nivel);
		// cfBI.getNivelesCF(padreClasificador.getIdNivel());

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_EDITAR_CLSERIES, request);
		invocation.setAsReturnPoint(true);

		setInTemporalSession(request,
				FondosConstants.TIPOS_CLASIFICADOR_SERIES, subNiveles);
		List jerarquiaElementoCF = cfBI.getAncestors(padreClasificador.getId());
		// ArrayList jerarquiaElementoCF = new
		// ArrayList(padreClasificador.getAncestors());
		// jerarquiaElementoCF.add(padreClasificador);
		setInTemporalSession(request,
				FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION,
				jerarquiaElementoCF);

		ClasificadorSeriesForm clfSeriesForm = (ClasificadorSeriesForm) form;
		clfSeriesForm.setId(clfSeries.getId());
		clfSeriesForm.setDenominacion(clfSeries.getTitulo());
		clfSeriesForm.setCodigo(clfSeries.getCodigo());
		clfSeriesForm.setTipoClasificador(clfSeries.getIdNivel());
		clfSeriesForm.setCodOrdenacion(clfSeries.getOrdPos());
		clfSeriesForm.setCodigoOld(clfSeries.getCodigo());
		setReturnActionFordward(request,
				mappings.findForward("edicion_clasificador_series"));
	}

	// ClasificadorSeriesForm clfSeriesForm = (ClasificadorSeriesForm) form;
	// ActionErrors errors = validateForm(request, clfSeriesForm);
	// if (errors == null) {
	// try {
	//
	// GestionCuadroClasificacionBI gestionCuadroClasificacionBI =
	// getGestionCuadroClasificacionBI(request);
	// String idPadre = clfSeriesForm.getIdPadre();
	// String nivelPadre = clfSeriesForm.getNivelPadre();
	//
	// CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView)
	// getFromTemporalSession(
	// request, FondosConstants.CUADRO_CLF_VIEW_NAME);
	//
	// //String parentNodePath = request.getParameter("parentNode");
	// //TreeNode parentNode = treeView.getNode(parentNodePath);
	// TreeNode parentNode = treeView.getSelectedNode();
	// ElementoCuadroClasificacionVO clfSeries = gestionCuadroClasificacionBI
	// .crearClasificadorSeries(
	// (ElementoCuadroClasificacionVO) parentNode.getModelItem(),
	// clfSeriesForm.getTipoClasificador(),
	// clfSeriesForm.getCodigo(),
	// clfSeriesForm.getDenominacion());
	//
	// TreeNode addedNode = treeView.itemAdded((TreeModelItem) clfSeries);
	// treeView.setSelectedNode(addedNode);
	//
	// /*
	// * CuadroClasificacionTreeView treeView =
	// *
	// (CuadroClasificacionTreeView)request.getSession().getAttribute(FondosConstants.CUADRO_CLF_VIEW_NAME);
	// * treeView.itemAdded((TreeModelItem)clfSeries);
	// */
	//
	// ActionRedirect result = new ActionRedirect(mappings
	// .findForward("refreshView"));
	// //result.addParameter("idelementocf", clfSeries.getId());
	// result.addParameter("node", addedNode.getNodePath());
	// result.addParameter("refreshView", "true");
	// result.setRedirect(true);
	//
	// //request.setAttribute(FondosConstants.ELEMENTO_CF_KEY, clfSeries);
	// setReturnActionFordward(request, result);
	//
	// } catch (FondosOperacionNoPermitidaException nuce) {
	// errors = guardarError(request, nuce);
	// //errors = manageFondosOperacionNoPermitida(request, nuce);
	// }
	// }
	//
	// if (errors!=null){
	// ErrorsTag.saveErrors(request, errors);
	// altaClasificadorSeriesExecuteLogic(mappings, form, request,
	// response);
	// return;
	// }
	//

	public void guardarClasificadorSeriesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			ClasificadorSeriesForm clfSeriesForm = (ClasificadorSeriesForm) form;
			GestionCuadroClasificacionBI bi = getGestionCuadroClasificacionBI(request);
			CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView) getFromTemporalSession(
					request, FondosConstants.CUADRO_CLF_VIEW_NAME);
			TreeNode nodoSeleccionado = treeView.getSelectedNode();

			ActionErrors errors = validateForm(request, clfSeriesForm);
			if (errors == null) {
				if (StringUtils.isEmpty(clfSeriesForm.getId())) {
					ElementoCuadroClasificacionVO elementoPadre = (ElementoCuadroClasificacionVO) nodoSeleccionado
							.getModelItem();
					ElementoCuadroClasificacionVO clfSeries = bi
							.crearClasificadorSeries(elementoPadre,
									clfSeriesForm.getTipoClasificador(),
									clfSeriesForm.getCodigo(),
									clfSeriesForm.getDenominacion(),
									clfSeriesForm.getCodOrdenacion());
					TreeNode addedNode = treeView.itemAdded(elementoPadre,
							clfSeries);
					nodoSeleccionado = treeView.setSelectedNode(addedNode);

				} else {
					ElementoCuadroClasificacionVO clfSeries = (ElementoCuadroClasificacionVO) nodoSeleccionado
							.getModelItem();
					clfSeries.setOrdPos(clfSeriesForm.getCodOrdenacion());
					bi.updateClasificadorSeries(clfSeries,
							clfSeriesForm.getTipoClasificador(),
							clfSeriesForm.getCodigo(),
							clfSeriesForm.getDenominacion(),
							clfSeriesForm.getCodigoOld());
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
						mappings.findForward("edicion_clasificador_series"));
			}

		} catch (FondosOperacionNoPermitidaException e) {
			guardarError(request, e);
			setReturnActionFordward(request,
					mappings.findForward("edicion_clasificador_series"));
		}
	}

	public void eliminarClasificadorSeriesExecuteLogic(ActionMapping mappings,
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
		ElementoCuadroClasificacionVO clfSeries = (ElementoCuadroClasificacionVO) nodoSeleccionado
				.getModelItem();
		ActionRedirect result = null;
		try {
			bi.removeElementoCF(clfSeries);
			treeView.removeNode(nodoSeleccionado);
			treeView.setSelectedNode(nodoSeleccionado = nodoSeleccionado
					.getParent());

			/*
			 * ElementoCuadroClasificacionVO newSelectedElement =
			 * (ElementoCuadroClasificacionVO)
			 */treeView.getSelectedNode().getModelItem();
			// request.setAttribute(FondosConstants.ELEMENTO_CF_KEY,
			// newSelectedElement);
			// result.addParameter("idelementocf", newSelectedElement.getId());
			result = new ActionRedirect(mappings.findForward("refreshView"),
					true);
			result.addParameter("node", nodoSeleccionado.getNodePath(), false);
			result.addParameter("refreshView", "true");
			setReturnActionFordward(request, result);

			// } catch (NotEmptyElementException nee) {
			// ActionErrors errors = getErrors(request, true);
			// errors.add(Constants.ERROR_CLF_SERIES_NO_VACIO, new ActionError(
			// Constants.ERROR_CLF_SERIES_NO_VACIO));
			// ErrorsTag.saveErrors(request, errors);
			// verClasificadorSeriesExecuteLogic(mappings, form, request,
			// response);
			// }
		} catch (ActionNotAllowedException nee) {
			guardarError(request, nee);
			// ActionErrors errors = getErrors(request, true);
			// errors.add(Constants.ERROR_CLF_SERIES_NO_VACIO, new ActionError(
			// Constants.ERROR_CLF_SERIES_NO_VACIO));
			// ErrorsTag.saveErrors(request, errors);
			verClasificadorSeriesExecuteLogic(mappings, form, request, response);
		}

	}

	public void cambiarEstadoVigenciaClasificadorSeriesExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView) getFromTemporalSession(
				request, FondosConstants.CUADRO_CLF_VIEW_NAME);

		TreeNode nodoSeleccionado = treeView.getSelectedNode();
		ElementoCuadroClasificacionVO clfSeries = (ElementoCuadroClasificacionVO) nodoSeleccionado
				.getModelItem();
		try {
			GestionCuadroClasificacionBI bi = getGestionCuadroClasificacionBI(request);
			bi.setEstadoVigencia(clfSeries, !clfSeries.isVigente());

		} catch (FondosOperacionNoPermitidaException cnve) {
			guardarError(request, cnve);
			// getErrors(request,
			// true).add(Constants.ERROR_CLF_SERIES_HAS_ACTIVE_CHILDS,
			// new ActionError(Constants.ERROR_CLF_SERIES_HAS_ACTIVE_CHILDS));
		}
		// request.setAttribute(FondosConstants.ELEMENTO_CF_KEY, clfSeries);
		// setReturnActionFordward(request,
		// mappings.findForward("view_clasificadorSeries"));
		// verClasificadorSeriesExecuteLogic(mappings, form, request, response);
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	/**
	 * Accion para mover el elemento desde la pantalla de Clasificadores.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void moveExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String idClasificador = request.getParameter("idClasificador");

		moveCodeLogic(mappings, form, request, response, idClasificador);
	}

	/**
	 * Accion para mover el elemento desde el resultado de una búsqueda.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void accionMoveExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		if (getServiceClient(request).hasPermission(
				AppPermissions.MODIFICACION_CUADRO_CLASIFICACION)) {
			String[] ids = (String[]) getFromTemporalSession(request,
					FondosConstants.ACCION_ELEMENTOS_KEY);

			ElementoCuadroClasificacionVO elementoCF = getGestionCuadroClasificacionBI(
					request).getElementoCuadroClasificacion(ids[0]);

			setInTemporalSession(request, FondosConstants.ELEMENTO_CF_KEY,
					elementoCF);
			setInTemporalSession(
					request,
					FondosConstants.NIVEL_CF_KEY,
					getGestionCuadroClasificacionBI(request).getNivelCF(
							elementoCF.getIdNivel()));

			moveCodeLogic(mappings, form, request, response, ids[0]);
		} else {
			ActionErrors errors = getErrors(request, true);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					Constants.ERROR_SIN_PERMISOS));
			ErrorsTag.saveErrors(request, errors);
			setInTemporalSession(request, "usarCache", Boolean.TRUE);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	private void moveCodeLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String idClasificador) {

		removeInTemporalSession(request,
				FondosConstants.MOVIMIENTO_FINALIZADO_KEY);

		ClasificadorSeriesForm clfSeriesForm = (ClasificadorSeriesForm) form;
		clfSeriesForm.setId(idClasificador);

		ServiceRepository services = getServiceRepository(request);
		GestionCuadroClasificacionBI cfBI = services
				.lookupGestionCuadroClasificacionBI();

		ElementoCuadroClasificacionVO elementoCuadroClasificacionVO = cfBI
				.getElementoCuadroClasificacion(idClasificador);
		clfSeriesForm.setFondoSerie(elementoCuadroClasificacionVO.getIdFondo());

		// Establecemos los fondos vigentes
		setInTemporalSession(request, FondosConstants.LISTA_FONDOS_KEY,
				getGestionFondosBI(request).getFondosVigentes());

		setReturnActionFordward(request,
				mappings.findForward("seleccion_padre"));
	}

	public void seleccionarNuevoPadreExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ClasificadorSeriesForm clfSeriesForm = (ClasificadorSeriesForm) form;
		GestionCuadroClasificacionBI cfBI = getGestionCuadroClasificacionBI(request);
		final ElementoCuadroClasificacionVO clasificador = cfBI
				.getElementoCuadroClasificacion(clfSeriesForm.getId());
		INivelCFVO nivelClasificador = cfBI.getNivelCF(clasificador
				.getIdNivel());
		List posiblesNivelesPadre = cfBI.getNivelesPadre(
				nivelClasificador.getId(), nivelClasificador.getTipo());
		List idsPosiblesNivelesPadre = new ArrayList(posiblesNivelesPadre);
		CollectionUtils.transform(idsPosiblesNivelesPadre, new Transformer() {
			public Object transform(Object arg0) {
				return ((INivelCFVO) arg0).getId();
			}
		});
		String[] nivelesID = (String[]) idsPosiblesNivelesPadre
				.toArray(new String[0]);
		// List clasificadoresPadre =
		// cfBI.getElementosCuadroClasificacionXNivel(nivelesID,
		// clasificador.getIdFondo());
		List clasificadoresPadre = cfBI
				.getElementosCFXNivelYFondoYCodigoYTitulo(nivelesID,
						clfSeriesForm.getFondoSerie(),
						clfSeriesForm.getCodigo(),
						clfSeriesForm.getTituloSerie());
		CollectionUtils.filter(clasificadoresPadre, new Predicate() {
			public boolean evaluate(Object arg0) {
				return !((ElementoCuadroClasificacionVO) arg0).getId().equals(
						clasificador.getIdPadre());
			}
		});

		// setInTemporalSession(request, FondosConstants.ELEMENTO_CF_KEY,
		// clasificador);
		request.setAttribute(FondosConstants.LISTA_ELEMENTOS_CF,
				clasificadoresPadre);
		request.setAttribute(FondosConstants.NIVEL_ELEMENTO_CF,
				nivelClasificador);
		request.setAttribute(FondosConstants.LISTA_NIVELES_KEY,
				posiblesNivelesPadre);
		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_MOVER_CLASIFICADORSERIE, request);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_padre"));
	}

	public void moverClasificadorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		Boolean movimientoFinalizado = (Boolean) getFromTemporalSession(
				request, FondosConstants.MOVIMIENTO_FINALIZADO_KEY);

		if (movimientoFinalizado == null) {
			movimientoFinalizado = new Boolean(false);
		}

		if (!movimientoFinalizado.booleanValue()) {

			String pClasificador = request.getParameter("idClasificador");
			String pNuevoPadre = request.getParameter("nuevoPadre");

			if (pNuevoPadre != null && StringUtils.isNotBlank(pNuevoPadre)) {
				GestionCuadroClasificacionBI cfBI = getGestionCuadroClasificacionBI(request);
				ElementoCuadroClasificacionVO clasificador = cfBI
						.getElementoCuadroClasificacion(pClasificador);

				try {
					cfBI.moverElemento(clasificador, pNuevoPadre);

					setInTemporalSession(request,
							FondosConstants.MOVIMIENTO_FINALIZADO_KEY,
							new Boolean(true));

					setInTemporalSession(request,
							FondosConstants.CLASIFICADOR_ORIGEN_KEY,
							clasificador);

					ElementoCuadroClasificacionVO clasificadorDestino = cfBI
							.getElementoCuadroClasificacion(pClasificador);
					setInTemporalSession(request,
							FondosConstants.CLASIFICADOR_DESTINO_KEY,
							clasificadorDestino);

					CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView) getFromTemporalSession(
							request, FondosConstants.CUADRO_CLF_VIEW_NAME);

					if (treeView != null) {
						TreeModelItem nuevoPadre = (TreeModelItem) cfBI
								.getElementoCuadroClasificacion(pNuevoPadre);

						// Corregido - Alicia
						/*********************************/
						// TreeNode nodeNuevoPadre =
						// treeView.findNode(nuevoPadre);
						TreeNode nodeNuevoPadre = treeView.getNode(treeView
								.getTreeModel().calculateItemPath(nuevoPadre));
						/*********************************/

						TreeNode nodeClasificador = treeView.getSelectedNode();
						treeView.removeNode(nodeClasificador);
						nodeClasificador = treeView.insertNode(nodeNuevoPadre,
								(TreeModelItem) clasificador);
						treeView.setSelectedNode(nodeClasificador);

						/*
						 * ActionRedirect result = new ActionRedirect(mappings
						 * .findForward("refreshView"), true);
						 * result.addParameter("node",
						 * nodeClasificador.getNodePath(), false);
						 * result.addParameter("refreshView", "true");
						 */

						// setReturnActionFordward(request, result);
					}
					request.setAttribute(FondosConstants.REFRESH_VIEW_KEY,
							Boolean.TRUE);
					setReturnActionFordward(request,
							mappings.findForward("informe_mover"));

				} catch (FondosOperacionNoPermitidaException e) {
					guardarError(request, e);
					if (e.getCodError() == FondosOperacionNoPermitidaException.XNO_SE_PUEDE_MOVER_PROVOCA_FONDOS_DISTINTOS_EN_MISMA_CAJA) {
						request.setAttribute(
								Constants.LABEL_MENSAJE_MULTILINEA,
								Constants.MULTILINEA_ERROR_UIs_AFECTADAS);
						request.setAttribute(
								Constants.LINEAS_MENSAJE_MULTILINEA,
								e.getIdsProblematicos());
						setReturnActionFordward(
								request,
								mappings.findForward("back_and_error_multilinea"));
						return;
					}
					seleccionarNuevoPadreExecuteLogic(mappings, form, request,
							response);

				}
			} else {

				ActionErrors errors = obtenerErrores(request, true);
				errors.add(
						FondosConstants.NECESARIO_SELECCIONAR_PADRE_MESSAGE_KEY,
						new ActionError(
								FondosConstants.NECESARIO_SELECCIONAR_PADRE_MESSAGE_KEY));

				seleccionarNuevoPadreExecuteLogic(mappings, form, request,
						response);
			}

		} else {
			setReturnActionFordward(request,
					mappings.findForward("informe_mover"));
		}
	}

	public void cancelarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		goBackExecuteLogic(mappings, form, request, response);
	}

	public void editarInfoClasificadorSerieExecuteLogic(ActionMapping mappings,
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
			ClasificadorSeriesForm clSerieForm = (ClasificadorSeriesForm) form;
			clSerieForm.setNivelAcceso(elementoCF.getNivelAcceso());
			clSerieForm.setIdLCA(elementoCF.getIdLCA());

			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_EDITAR_CLFONDOS, request);
			invocation.setAsReturnPoint(true);

			request.setAttribute(
					FondosConstants.LISTAS_CONTROL_ACCESO_KEY,
					usuariosBI
							.getListasControlAccesoByTipo(TipoListaControlAcceso.ELEMENTO_CUADRO_CLASIFICACION));

			request.setAttribute(FondosConstants.ELEMENTO_CF_KEY, elementoCF);
			setReturnActionFordward(request,
					mappings.findForward("edicion_info_clserie"));
		} else {
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void guardarInfoCLSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionFondosBI fondosService = getGestionFondosBI(request);
		ClasificadorSeriesForm clSerieForm = (ClasificadorSeriesForm) form;
		ElementoCuadroClasificacion elementoCF = (ElementoCuadroClasificacion) getFromTemporalSession(
				request, FondosConstants.ELEMENTO_CF_KEY);
		if (elementoCF != null) {
			elementoCF.setNivelAcceso(clSerieForm.getNivelAcceso());
			elementoCF.setIdLCA(clSerieForm.getIdLCA());
			fondosService.updateInfoAccesoElemento(elementoCF.getId(),
					elementoCF.getNivelAcceso(), elementoCF.getIdLCA());
		}
		goBackExecuteLogic(mappings, form, request, response);
	}
}