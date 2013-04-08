package common.actions;

import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppUser;
import xml.config.Busqueda;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.vos.BandejaActividadesVO;

import descripcion.DescripcionConstants;
import fondos.FondosConstants;
import fondos.forms.BusquedaElementosForm;
import fondos.utils.BusquedasHelper;

public class HomeAction extends BaseAction {

	private Busqueda getCfgBusquedaBandejaSimple(HttpServletRequest request)
			throws Exception {

		String key = getKeyCfgBusquedas(request,
				ConfiguracionArchivoManager.BANDEJA_SIMPLE);
		Busqueda busqueda = getCfgBusqueda(request, key);
		return busqueda;
	}

	protected void verBandejaExecuteLogic(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setReturnActionFordward(request,
				actionMapping.findForward("ver_bandeja"));
	}

	protected void loadBandejaExecuteLogic(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser userVO = getAppUser(request);
		ServiceRepository services = getServiceRepository(request);
		GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();

		BandejaActividadesVO actividadesUsuario = sistemaBI
				.getActividadesUsuario(userVO);
		request.setAttribute(Constants.BANDEJA_KEY, actividadesUsuario);

		// Crear un map de elementos visitados nuevo
		setInTemporalSession(request,
				DescripcionConstants.MAP_ELEMENTOS_CF_VISITADOS, new HashMap());

		// Establecer la configuración de la búsqueda particular y cargar las
		// listas necesarias para la búsqueda
		removeInTemporalSession(request, FondosConstants.CFG_BUSQUEDA_KEY);
		Busqueda busqueda;
		try {
			busqueda = getCfgBusquedaBandejaSimple(request);
			setInTemporalSession(request, FondosConstants.CFG_BUSQUEDA_KEY,
					busqueda);
			BusquedasHelper.loadListasBusqueda(busqueda,
					(BusquedaElementosForm) form, request, null);

			setReturnActionFordward(request,
					actionMapping.findForward("load_bandeja"));
		} catch (FileNotFoundException flne) {
			logger.error(
					"NO SE HA ENCONTRADO EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					flne);
			getErrors(request, false).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_CONFIGURACION_FILE_NOT_FOUND));
			goLastClientExecuteLogic(actionMapping, form, request, response);
		} catch (Exception e) {
			logger.error(
					"ERROR AL OBTENER EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					e);
			getErrors(request, false).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_CONFIGURACION_FILE));
			goLastClientExecuteLogic(actionMapping, form, request, response);
		}
	}

	protected void verListadoExecuteLogic(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String tipoListado = request.getParameter("tipoListado");
		setReturnActionFordward(request, actionMapping.findForward(tipoListado));
	}
}