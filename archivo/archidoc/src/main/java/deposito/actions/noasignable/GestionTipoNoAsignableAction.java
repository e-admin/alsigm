package deposito.actions.noasignable;

import ieci.core.exception.IeciTdException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import util.CollectionUtils;
import util.TreeNode;
import util.TreeView;

import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.navigation.TitlesToolBar;
import common.util.IntervalOptions;
import common.util.ListUtils;
import common.util.TypeConverter;

import deposito.CartelasDepositoHelper;
import deposito.DepositoConstants;
import deposito.actions.ErrorKeys;
import deposito.actions.asignable.ElementoAsignablePO;
import deposito.actions.asignable.ElementoAsignableToPO;
import deposito.actions.hueco.HuecoToPO;
import deposito.forms.ElementoNoAsignableForm;
import deposito.model.DepositoException;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.ElementoNoAsignableVO;
import deposito.vos.ElementoVO;
import deposito.vos.HuecoVO;
import deposito.vos.InformeOcupacion;
import deposito.vos.TipoElementoVO;

public class GestionTipoNoAsignableAction extends BaseAction {

	public void altaDescendientesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ElementoNoAsignableForm noAsignableForm = (ElementoNoAsignableForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		ElementoVO elementoPadre = null;

		String pTipoElementoPadre = request.getParameter("tipoElementoPadre");
		if (StringUtils.equals(pTipoElementoPadre,
				DepositoConstants.ID_TIPO_ELEMENTO_UBICACION))
			elementoPadre = depositoBI.getUbicacion(noAsignableForm
					.getIdUbicacion());
		else
			elementoPadre = depositoBI.getElementoNoAsignable(noAsignableForm
					.getIdPadre());
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_CREAR_ELEMENTO, request);
		invocation.setAsReturnPoint(true);
		removeInTemporalSession(request,
				DepositoConstants.LISTA_TIPO_ELEMENTO_KEY);
		List subtipos = depositoBI.getSubtiposTipoElemento(pTipoElementoPadre,
				null);

		if (ListUtils.isNotEmpty(subtipos)) {
			setInTemporalSession(request,
					DepositoConstants.LISTA_TIPO_ELEMENTO_KEY, subtipos);
			setInTemporalSession(request,
					DepositoConstants.ELEMENTO_DEPOSITO_KEY, elementoPadre);
			setReturnActionFordward(request,
					mappings.findForward("alta_no_asignable"));
		} else {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_ERROR,
							new ActionError(
									DepositoConstants.ERROR_ELEMENTO_DEPOSITO_SIN_DESCENDIENTES));
			goBackExecuteLogic(mappings, noAsignableForm, request, response);
		}

	}

	ActionErrors validarFormularioAltaNoAsignables(ElementoNoAsignableForm form) {
		ActionErrors errors = new ActionErrors();
		if (TypeConverter.toInt(form.getNumACrear(), -1) <= 0)
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					ErrorKeys.NUMERO_ELEMENTOS_INCORRECTO));
		return errors.size() > 0 ? errors : null;
	}

	public void crearDescendientesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ElementoNoAsignableForm noAsignableForm = (ElementoNoAsignableForm) form;
		ActionErrors errors = validarFormularioAltaNoAsignables(noAsignableForm);
		if (errors == null) {
			ServiceRepository services = getServiceRepository(request);
			GestorEstructuraDepositoBI depositoBI = services
					.lookupGestorEstructuraDepositoBI();
			ElementoNoAsignableVO elementoPadre = null;
			boolean isUbicacion = StringUtils.isBlank(noAsignableForm
					.getIdPadre());
			if (isUbicacion) {
				elementoPadre = depositoBI.getUbicacion(noAsignableForm
						.getIdUbicacion());
			} else {
				elementoPadre = depositoBI
						.getElementoNoAsignable(noAsignableForm.getIdPadre());
			}

			try {
				TipoElementoVO tipoElemento = depositoBI
						.getTipoElementoSingleton(noAsignableForm
								.getTipoElemento());
				if (tipoElemento.isTipoAsignable()) {
					ActionRedirect forwardAltaAsignable = new ActionRedirect(
							mappings.findForward("alta_asignable"));
					setReturnActionFordward(request, forwardAltaAsignable);
				} else {
					List elementosCreados = depositoBI
							.crearElementosNoAsignables(isUbicacion,
									elementoPadre, tipoElemento, TypeConverter
											.toInt(noAsignableForm
													.getNumACrear()));
					TreeView depositoTreeView = (TreeView) getFromTemporalSession(
							request, DepositoConstants.DEPOSITO_VIEW_NAME);
					if (depositoTreeView != null) {
						TreeNode parentNode = depositoTreeView
								.findNode(elementoPadre);
						for (Iterator i = elementosCreados.iterator(); i
								.hasNext();)
							depositoTreeView.insertNode(parentNode,
									(ElementoVO) i.next());
					}
					popLastInvocation(request);
					ActionRedirect view = new ActionRedirect(
							mappings.findForward("ver_elemento"));
					view.addParameter("refreshView", "true");
					view.addParameter("node", elementoPadre.getItemPath(),
							false);
					setReturnActionFordward(request, view);
				}
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
			}
		} else {
			obtenerErrores(request, true).add(errors);
			setReturnActionFordward(request,
					mappings.findForward("alta_no_asignable"));
		}
	}

	public void verNoAsignableExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);

		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		String reloadTreeView = request
				.getParameter(Constants.TREE_VIEW_RELOAD);
		if (reloadTreeView != null) {
			request.setAttribute(Constants.TREE_VIEW_RELOAD, Boolean.TRUE);
		}

		if (Boolean.valueOf(request.getParameter("refreshView")).booleanValue())
			request.setAttribute(DepositoConstants.REFRESH_VIEW_KEY,
					Boolean.TRUE);

		String pIdNoAsignable = request.getParameter("idNoAsignable");
		ElementoNoAsignableVO noAsignable = depositoBI
				.getElementoNoAsignable(pIdNoAsignable);
		List hijos = depositoBI.getHijosElemento(noAsignable.getId(),
				noAsignable.getIdTipoElemento());

		// Transformar a PO solamente aquellos elementos Asignables para obtener
		// la menor y mayor numeracion
		List descendientes = new ArrayList();
		for (Iterator iter = hijos.iterator(); iter.hasNext();) {
			ElementoVO elemento = (ElementoVO) iter.next();
			if (elemento.isAsignable()) {
				ElementoAsignablePO asignablePO = (ElementoAsignablePO) ElementoAsignableToPO
						.getInstance(services).transform(elemento);
				descendientes.add(asignablePO);
			} else
				descendientes.add(elemento);
		}

		TreeView treeView = (TreeView) getFromTemporalSession(request,
				DepositoConstants.DEPOSITO_VIEW_NAME);
		String viewAction = (String) request.getAttribute("viewAction");
		String viewName = (String) request.getAttribute("viewName");

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_VER_ELEMENTO, request,
				treeView, viewAction, viewName);
		invocation
				.setTitleNavigationToolBar(TitlesToolBar.DEPOSITO_VER_ELEMENTO
						+ noAsignable.getIdTipoElemento());

		setInTemporalSession(request,
				DepositoConstants.ELEMENTO_NO_ASIGNABLE_KEY, noAsignable);
		request.setAttribute(DepositoConstants.LISTA_DESCENDIENTES_KEY,
				descendientes);

		setReturnActionFordward(request,
				mappings.findForward("info_no_asignable"));
	}

	public void verOcupacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);

		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		String pIdNoAsignable = request.getParameter("idNoAsignable");
		ElementoNoAsignableVO noAsignable = depositoBI
				.getElementoNoAsignable(pIdNoAsignable);
		request.setAttribute(DepositoConstants.ELEMENTO_DEPOSITO_KEY,
				noAsignable);

		InformeOcupacion informeOcupacion = depositoBI
				.getInformeOcupacionElementoNoAsignable(pIdNoAsignable);

		request.setAttribute("__INFORME_OCUPACION", informeOcupacion);

		saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_INFORME_OCUPACION, request);
		setReturnActionFordward(request,
				mappings.findForward("informe_ocupacion"));
	}

	public void eliminarNoAsignableExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		String pIdNoAsignable = request.getParameter("idNoAsignable");
		String pIdTipoElemento = request.getParameter("idTipoElemento");
		try {
			ElementoVO elementoEliminado = depositoBI.eliminarElemento(
					pIdNoAsignable, pIdTipoElemento);
			TreeView depositoTreeView = (TreeView) getFromTemporalSession(
					request, DepositoConstants.DEPOSITO_VIEW_NAME);
			if (depositoTreeView != null) {
				TreeNode nodoEliminado = depositoTreeView
						.findNode(elementoEliminado);
				depositoTreeView.setSelectedNode(nodoEliminado.getParent());
				depositoTreeView.removeNode(nodoEliminado);
			}
			popLastInvocation(request);
			ClientInvocation lastInvocation = getInvocationStack(request)
					.getLastClientInvocation();
			ActionRedirect forward = new ActionRedirect(new ActionForward(
					lastInvocation.getInvocationURI()));
			forward.addParameter("refreshView", "true");
			setReturnActionFordward(request, forward);
		} catch (DepositoException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void selCartelasExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_SELECCION_CARTELAS, request);

		GestorEstructuraDepositoBI depositoBI = getGestionDespositoBI(request);
		List listaFormatos = depositoBI.getFormatosVigentes();
		setInTemporalSession(request, DepositoConstants.LISTA_FORMATOS,
				listaFormatos);

		String idUbicacion = request.getParameter("idUbicacion");
		ElementoNoAsignableVO elementoNoAsignable = null;
		if (StringUtils.isEmpty(idUbicacion)) {
			elementoNoAsignable = (ElementoNoAsignableVO) getFromTemporalSession(
					request, DepositoConstants.ELEMENTO_NO_ASIGNABLE_KEY);
			removeInTemporalSession(request, DepositoConstants.EDIFICIO_KEY);
		} else {
			elementoNoAsignable = (ElementoNoAsignableVO) getFromTemporalSession(
					request, DepositoConstants.EDIFICIO_KEY);
			((ElementoNoAsignableForm) form).setIdUbicacion(elementoNoAsignable
					.getIddeposito());
			removeInTemporalSession(request,
					DepositoConstants.ELEMENTO_NO_ASIGNABLE_KEY);
		}
		((ElementoNoAsignableForm) form).setId(elementoNoAsignable.getId());
		if (StringUtils
				.isEmpty(((ElementoNoAsignableForm) form).getSelHuecos())) {
			((ElementoNoAsignableForm) form).setSelHuecos("1");
		}

		setReturnActionFordward(request,
				mapping.findForward("seleccionar_cartelas"));
	}

	public void verCartelasExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ElementoNoAsignableForm noAsignableForm = ((ElementoNoAsignableForm) form);
		String strSignaturas = noAsignableForm.getTextSignaturas();
		if (((ElementoNoAsignableForm) form).getSelHuecos().equals("2")
				&& !(Pattern
						.matches(
								Constants.EXPRESION_REGULAR_VAL_SEL_CARTELAS_NO_ASIGNABLE,
								strSignaturas))) {

			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_VALIDACION_SIGNATURAS,
							request.getLocale()));
			setReturnActionFordward(request,
					mapping.findForward("seleccionar_cartelas"));
			return;
		}

		ClientInvocation invocation = getInvocationStack(request)
				.getLastClientInvocation();
		invocation.addParameters(noAsignableForm.getMap());

		saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_IMPRESION_CARTELAS, request);

		IntervalOptions options = IntervalOptions.parse(strSignaturas);
		List cartelas = new ArrayList();
		HashMap mapUDocsHueco = new HashMap();
		GestorEstructuraDepositoBI depositoBI = getGestionDespositoBI(request);
		try {
			List huecos = depositoBI.getHuecosPorSignaturas(
					noAsignableForm.getId(), noAsignableForm.getIdUbicacion(),
					noAsignableForm.getIdFormato(), options);
			if (!CollectionUtils.isEmpty(huecos)) {
				HuecoVO hueco = null;
				for (int i = 0; i < huecos.size(); i++) {
					hueco = (HuecoVO) huecos.get(i);
					if (HuecoVO.OCUPADO_STATE.equals(hueco.getEstado())) {
						// Obtener su lista de padres y generar los valores
						// necesarios para el informe
						LinkedList ltPadres = depositoBI
								.getListaPadresHueco(hueco);
						HashMap mapElementosDepositoNombresElemento = CartelasDepositoHelper
								.getMapElementosDepositoNombresElemento(
										ltPadres, depositoBI);
						hueco.setMapElementosDepositoNombresElemento(mapElementosDepositoNombresElemento);
						// Añadir el hueco
						cartelas.add(hueco);
						// Obtener la lista de unidades documentales del hueco
						List udocsHueco = depositoBI.getUDocsHueco(hueco
								.getHuecoID());
						mapUDocsHueco.put(hueco.getHuecoID().getIdpadre()
								+ Constants.UNDERSCORE
								+ hueco.getHuecoID().getNumorden(), udocsHueco);
					}
				}
				if (hueco != null) {
					String idArchivo = depositoBI.getUbicacion(
							hueco.getIddeposito()).getIdArchivo();
					setInTemporalSession(request,
							DepositoConstants.ID_ARCHIVO_KEY, idArchivo);
				}
			}
		} catch (IeciTdException e) {
			logger.error(e);
		} catch (TooManyResultsException t) {
			logger.debug(t);
		}

		if (cartelas != null && cartelas.isEmpty()) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									DepositoConstants.ERROR_NO_HAY_HUECOS_PARA_LAS_CONDICIONES,
									request.getLocale()));
			setReturnActionFordward(request,
					mapping.findForward("seleccionar_cartelas"));
		} else {
			ServiceRepository services = getServiceRepository(request);
			CollectionUtils
					.transform(cartelas, HuecoToPO.getInstance(services));
			setInTemporalSession(request, DepositoConstants.LISTA_CARTELAS_KEY,
					cartelas);
			setInTemporalSession(request,
					DepositoConstants.LISTA_UDOCS_CARTELAS_KEY, mapUDocsHueco);

			setReturnActionFordward(request,
					mapping.findForward("ver_cartelas"));
		}
	}

	public void editarNoAsignableExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestorEstructuraDepositoBI depositoBI = getGestionDespositoBI(request);
		String pIdNoAsignable = request.getParameter("idNoAsignable");

		ElementoNoAsignableForm elementoNoAsignableForm = (ElementoNoAsignableForm) form;

		ElementoNoAsignableVO elemNoAsig = depositoBI
				.getElementoNoAsignable(pIdNoAsignable);
		elementoNoAsignableForm.setId(elemNoAsig.getId());
		elementoNoAsignableForm.setPathPadre(depositoBI.getPathPadre(elemNoAsig
				.getId()));
		elementoNoAsignableForm.setNombre(elemNoAsig.getNombre());
		elementoNoAsignableForm.setIdPadre(elemNoAsig.getIdpadre());
		elementoNoAsignableForm.setIdUbicacion(elemNoAsig.getIddeposito());

		saveCurrentInvocation(KeysClientsInvocations.DEPOSITO_EDITAR_ELEMENTO,
				request);
		setReturnActionFordward(request,
				mappings.findForward("edicion_no_asignable"));
	}

	public void guardarNoAsignableExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestorEstructuraDepositoBI depositoBI = getGestionDespositoBI(request);
		ElementoNoAsignableForm elementoNoAsignableForm = (ElementoNoAsignableForm) form;

		ActionErrors errores = validarFormularioEdicion(request,
				elementoNoAsignableForm);
		if (errores.size() > 0) {
			obtenerErrores(request, true).add(errores);
			setReturnActionFordward(request,
					mappings.findForward("edicion_no_asignable"));
		} else {
			TreeView depositoTreeView = (TreeView) getFromTemporalSession(
					request, DepositoConstants.DEPOSITO_VIEW_NAME);
			ElementoNoAsignableVO noAsignable = null;
			try {
				noAsignable = depositoBI
						.getElementoNoAsignable(elementoNoAsignableForm.getId());
				// Si se modifico el nombre del elemento actualizar el nombre y
				// el path de los huecos y unidades de instalacion
				if (StringUtils.isNotEmpty(elementoNoAsignableForm.getNombre())
						&& StringUtils.isNotEmpty(noAsignable.getNombre())
						&& !elementoNoAsignableForm.getNombre().equals(
								noAsignable.getNombre())) {

					noAsignable.setNombre(elementoNoAsignableForm.getNombre());
					depositoBI.guardarElementoNoAsignable(noAsignable);
					if (depositoTreeView != null) {
						TreeNode nodoModificado = depositoTreeView
								.findNode(noAsignable);
						nodoModificado.setTreeModelItem(noAsignable);
					}
				}

				ActionRedirect verNoAsignable = new ActionRedirect(
						mappings.findForward("redirect_to_info_no_asignable"),
						true);
				verNoAsignable.addParameter("idNoAsignable",
						noAsignable.getId());
				verNoAsignable.addParameter("refreshView", "true");
				popLastInvocation(request);
				setReturnActionFordward(request, verNoAsignable);
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				setReturnActionFordward(request,
						mappings.findForward("edicion_no_asignable"));
			}
		}
	}

	private ActionErrors validarFormularioEdicion(HttpServletRequest request,
			ElementoNoAsignableForm form) {
		ActionErrors errores = new ActionErrors();
		if (StringUtils.isBlank(form.getNombre())) {
			errores.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_NOMBRE,
									request.getLocale())));
		} else {
			GestorEstructuraDepositoBI depositoBI = getGestionDespositoBI(request);
			Collection noAsignables = depositoBI.getElementosNoAsignables(
					form.getIdPadre(), form.getIdUbicacion());
			if (noAsignables != null && noAsignables.size() > 0) {
				for (Iterator iter = noAsignables.iterator(); iter.hasNext();) {
					ElementoNoAsignableVO noAsignable = (ElementoNoAsignableVO) iter
							.next();
					if (!form.getId().equals(noAsignable.getId())
							&& noAsignable.getNombre().toLowerCase()
									.equals(form.getNombre().toLowerCase())) {
						errores.add(
								ActionErrors.GLOBAL_ERROR,
								new ActionError(
										ErrorKeys.NOMBRE_ELEMENTO_NO_ASIGNABLE_REPETIDO));
						break;
					}
				}
			}
		}

		String caracteresInvalidos = Constants.SLASH;
		if (Constants.hasForbidenChars(form.getNombre(), caracteresInvalidos)) {
			errores.add(
					Constants.ERROR_INVALID_CHARACTERS,
					new ActionError(Constants.ERROR_INVALID_CHARACTERS,
							Messages.getString(Constants.ETIQUETA_NOMBRE,
									request.getLocale()), caracteresInvalidos,
							request.getLocale()));

		}
		return errores;
	}
}