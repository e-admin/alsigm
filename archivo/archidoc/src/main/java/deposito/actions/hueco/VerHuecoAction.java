package deposito.actions.hueco;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import util.ParamsSet;
import util.StrutsUtil;
import util.TreeView;

import common.Constants;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionArchivosBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.TypeConverter;

import deposito.DepositoConstants;
import deposito.actions.asignable.ElementoAsignablePO;
import deposito.forms.HuecoForm;
import deposito.forms.VerHuecoForm;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.DepositoVO;
import deposito.vos.ElementoAsignableVO;
import deposito.vos.ElementoVO;
import deposito.vos.HuecoID;
import deposito.vos.HuecoVO;
import deposito.vos.UDocEnUiDepositoVO;
import deposito.vos.UInsDepositoVO;
import fondos.vos.UnidadDocumentalVO;
import gcontrol.vos.ArchivoVO;

public class VerHuecoAction extends BaseAction {

	protected void listarudocs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request) throws Exception {

		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoService = services
				.lookupGestorEstructuraDepositoBI();

		VerHuecoForm frm = (VerHuecoForm) form;
		String idHueco = frm.getIdHueco();

		HuecoID huecoID = HuecoForm.getHuecoID(idHueco);
		HuecoVO huecoVO = depositoService.getInfoHueco(huecoID);


		TreeView treeView = (TreeView) getFromTemporalSession(request,
				DepositoConstants.DEPOSITO_VIEW_NAME);
		ElementoAsignableVO asig = depositoService.getElementoAsignable(huecoVO
				.getIdElemAPadre());
		ElementoVO elementoVO = depositoService.getInfoElemento(
				huecoVO.getIdElemAPadre(), asig.getIdTipoElemento());

		if(treeView != null){
			treeView.selectTreeNode(elementoVO);
		}

		removeInTemporalSession(request, DepositoConstants.LISTA_UDOCS_KEY);

		List udocsHueco = depositoService.getUDocsHueco(huecoID);

		HuecoPO huecoEntity = new HuecoPO(huecoVO, services);
		CollectionUtils.transform(udocsHueco,
				UdocEnUIToPO.getInstance(services));
		setInTemporalSession(request, DepositoConstants.HUECO_KEY, huecoEntity);
		setInTemporalSession(request, DepositoConstants.LISTA_UDOCS_KEY,
				udocsHueco);

		ElementoAsignableVO asignable = depositoService
				.getElementoAsignable(huecoVO.getIdElemAPadre());
		setInTemporalSession(request,
				DepositoConstants.TIPO_ELEMENTO_PADRE_HUECO,
				asignable.getIdTipoElemento());
		setInTemporalSession(request, DepositoConstants.ELEMENTO_ASIGNABLE_KEY,
				new ElementoAsignablePO(asignable, services));

		GestorEstructuraDepositoBI serviceEstructura = getGestorEstructuraDepositoBI(request);
		UInsDepositoVO uinsDepositoVO = serviceEstructura
				.getUinsEnDeposito(huecoVO.getIduinstalacion());

		UInsDepositoPO uinsDepositoPO = new UInsDepositoPO(services);

		if (uinsDepositoVO != null) {
			PropertyUtils.copyProperties(uinsDepositoPO, uinsDepositoVO);
			setInTemporalSession(request,
					DepositoConstants.UNIDAD_INSTALACION_DEPOSITO_KEY,
					uinsDepositoPO);
		}

		setReturnActionFordward(request, mapping.findForward("listado_udocs"));
	}

	protected void listadoudocsExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String reloadTreeView = request
				.getParameter(Constants.TREE_VIEW_RELOAD);
		if (reloadTreeView != null) {
			request.setAttribute(Constants.TREE_VIEW_RELOAD, Boolean.TRUE);
		}

		// if
		// (Boolean.valueOf(request.getParameter("refreshView")).booleanValue())
		request.setAttribute(DepositoConstants.REFRESH_VIEW_KEY, Boolean.TRUE);

		TreeView treeView = (TreeView) getFromTemporalSession(request,
				DepositoConstants.DEPOSITO_VIEW_NAME);

		String viewAction = (String) request.getAttribute("viewAction");
		String viewName = (String) request.getAttribute("viewName");

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_LISTADO_UDOCS, request,
				treeView, viewAction, viewName);
		invocation.setAsReturnPoint(true);

		try {
			listarudocs(mapping, form, request);
		} catch (Exception e) {
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							DepositoConstants.ERROR_HUECO_LISTAR_UNIDADES));
			logger.error(
					"Error al obtener la lista de unidades documentales del hueco",
					e);
			goLastClientExecuteLogic(mapping, form, request, response);

		}

	}

	protected void listadoudocsDesdeReubicacionExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_LISTADO_UDOCS_REUBICACION,
				request);
		invocation.setAsReturnPoint(true);

		listarudocs(mapping, form, request);
	}

	public void verrelacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		GestorEstructuraDepositoBI depositoService = getGestorEstructuraDepositoBI(request);

		VerHuecoForm frm = (VerHuecoForm) form;
		String idHueco = frm.getIdHueco();

		HuecoID huecoID = HuecoForm.getHuecoID(idHueco);
		HuecoVO huecoVO = depositoService.getInfoHueco(huecoID);

		// redirigir a ver relacion
		ActionForward forward = redirectForwardMethod(request,
				"/gestionRelaciones", "method", "verrelaciondesdedeposito");
		ParamsSet params = new ParamsSet().append("idrelacionseleccionada",
				huecoVO.getIdRelEntrega());
		StrutsUtil.setParamsToForward(forward, params);

		setReturnActionFordward(request, forward);
	}

	public void verUdocEnUIExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pIdUInstalacion = request.getParameter("idUInstalacion");
		String pPosUdoc = request.getParameter("posUDoc");
		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		try {
			UDocEnUiDepositoVO udocEnUI = depositoBI.abrirUdocEnUI(
					pIdUInstalacion, TypeConverter.toInt(pPosUdoc));
			ActionRedirect vistaUdoc = null;
			if (!puedeAccederUsuarioAElemento(request,
					udocEnUI.getIdunidaddoc())) {
				goLastClientExecuteLogic(mappings, form, request, response);
				return;
			}

			/*
			 * TODO: Volver a descomentar si se detecta fallo
			*
			 * getInvocationStack(request).getLastClientInvocation().setPath(
			 * "/action/manageVistaDeposito?actionToPerform=goHome"); String
			 * tipoPadre
			 * =(String)getFromTemporalSession(request,DepositoConstants
			 * .TIPO_ELEMENTO_PADRE_HUECO);
			 * getInvocationStack(request).getLastClientInvocation
			 * ().addParameter("itemPadreTipo", tipoPadre); HuecoPO
			 * huecoPO=(HuecoPO
			 * )getFromTemporalSession(request,DepositoConstants.HUECO_KEY);
			 * getInvocationStack
			 * (request).getLastClientInvocation().addParameter("itemID",
			 * huecoPO.getIdHueco());
			 * getInvocationStack(request).getLastClientInvocation
			 * ().addParameter("itemTipo", DepositoVO.ID_TIPO_HUECO);
			 */

			if (udocEnUI.isValidada()) {
				vistaUdoc = new ActionRedirect(
						mappings.findForward("ver_en_cuadro_clasificacion"));
				vistaUdoc.addParameter(Constants.ID, udocEnUI.getIdunidaddoc());
				// vistaUdoc.addParameter("itemID", udocEnUI.getIdunidaddoc());

				UdocEnUIPO po = null;
				UnidadDocumentalVO udoc = null;
				List listaUdocEnUIPOs = (List) getFromTemporalSession(request,
						DepositoConstants.LISTA_UDOCS_KEY);
				for (Iterator it = listaUdocEnUIPOs.iterator(); it.hasNext();) {
					po = (UdocEnUIPO) it.next();
					if (po.getIdunidaddoc().equals(udocEnUI.getIdunidaddoc())) {
						udoc = po.getUnidadDocumental();
						break;
					}
				}

				/*
				 * if (udoc!=null &&
				 * udoc.getIdNivel().equals(ConfiguracionSistemaArchivoFactory
				 * .getConfiguracionSistemaArchivo() .getConfiguracionGeneral()
				 * .getIdNivelFraccionSerie()))
				 */
				if (udoc != null && udoc.isSubtipoCaja()) {
					saveCurrentInvocation(
							KeysClientsInvocations.CUADRO_VER_FRACCION_SERIE,
							request);
				} else {
					saveCurrentInvocation(
							KeysClientsInvocations.CUADRO_VER_UNIDAD_DOCUMENTAL,
							request);
				}
			} else {
				vistaUdoc = new ActionRedirect(
						mappings.findForward("ver_en_relacion_entrega"));
				vistaUdoc.addParameter("udocID", udocEnUI.getIdudocre());
			}
			vistaUdoc.setRedirect(true);
			setReturnActionFordward(request, vistaUdoc);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void verPadreExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String itemID = request.getParameter("itemID");
		String itemTipo = request.getParameter("itemTipo");
		String refreshView = request.getParameter("refreshView");

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_VER_ELEMENTO, request);
		invocation.setAsReturnPoint(true);

		ActionRedirect ret = new ActionRedirect(
				mappings.findForward("ver_padre"));
		ret.addParameter("itemID", itemID, false);
		ret.addParameter("itemTipo", itemTipo, false);
		ret.addParameter("refreshView", refreshView, false);
		setReturnActionFordward(request, ret);
	}

	public void actualizarCampoSignaturaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		GestorEstructuraDepositoBI depositoBI = getGestionDespositoBI(request);
		GestionArchivosBI archivoBI = getGestionArchivosBI(request);

		String valorSignatura = request.getParameter("valorSignatura");
		UInsDepositoPO uinsDepositoPO = (UInsDepositoPO) getFromTemporalSession(
				request, DepositoConstants.UNIDAD_INSTALACION_DEPOSITO_KEY);
		HuecoVO huecoVO = depositoBI.getHuecoUInstalacion(uinsDepositoPO
				.getId());
		if (huecoVO != null) {
			DepositoVO ubicacion = depositoBI.getUbicacion(huecoVO
					.getIddeposito());
			if (ubicacion != null) {
				try {
					ArchivoVO archivoVO = archivoBI.getArchivoXId(ubicacion
							.getIdArchivo());
					depositoBI.actualizarSignaturaHueco(archivoVO, huecoVO,
							valorSignatura);
				} catch (ActionNotAllowedException e) {
					guardarError(request, e);
					goLastClientExecuteLogic(mappings, form, request, response);
				}
			}
		}
		ActionRedirect ret = new ActionRedirect(
				mappings.findForward("redirect_lista_udocs"));
		ret.addParameter("idHueco", uinsDepositoPO.getHuecoID(), false);
		setReturnActionFordward(request, ret);
	}
}