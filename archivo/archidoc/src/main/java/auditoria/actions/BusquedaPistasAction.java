package auditoria.actions;

import gcontrol.model.TipoAcceso;
import gcontrol.vos.GrupoVO;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.displaytag.properties.SortOrderEnum;

import se.usuarios.ServiceClient;
import xml.config.ConfiguracionArchivoManager;
import auditoria.AuditoriaConstants;
import auditoria.forms.BusquedaPistasForm;
import auditoria.vos.BusquedaPistasVO;
import auditoria.vos.CritUsuarioVO;
import auditoria.vos.TrazaVO;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionAuditoriaBI;
import common.bi.ServiceRepository;
import common.definitions.ArchivoModules;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;
import common.util.FileHelper;

/**
 * Action para las búsquedas de las pistas de auditoría.
 */
public class BusquedaPistasAction extends BaseAction {

	/** Metodo del action que se llama para recarga el display */
	private final static String ACTION = "auditoriaBuscar?method=buscar";

	/**
	 * Muestra el formulario para realizar búsquedas.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void formBusquedaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de formBusqExecuteLogic");

		// Obtenemos el servicio para el usuario conectado
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionAuditoriaBI service = services.lookupGestionAuditoriaBI();

		// Establecemos los elementos de la vista
		this.establecerElementosVista(request, service, null);

		// Guardar el enlace a la página
		saveCurrentInvocation(KeysClientsInvocations.AUDITORIA_FORMBUSQUEDA,
				request);

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("formulario_busqueda"));
	}

	/**
	 * Muestra el formulario con las acciones del modulo seleccionado.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void loadAccionesExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de formBusqExecuteLogic");

		// Obtenemos el servicio para el usuario conectado
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionAuditoriaBI service = services.lookupGestionAuditoriaBI();

		// Obtenemos el modulo por el que deseamos filtrar
		String modulo = request.getParameter("modulo");
		// Establecemos los elementos de la vista
		this.establecerElementosVista(request, service, modulo);

		// Guardar el enlace a la página
		saveCurrentInvocation(KeysClientsInvocations.AUDITORIA_FORMBUSQUEDA,
				request);

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("formulario_busqueda"));
	}

	/**
	 * Establece los elementos necesarios para mostrar en la vista
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param service
	 *            Servicio de acceso a los metodos de auditoria
	 * @param module
	 *            Modulo del que deseamos obtener las acciones o null si
	 *            queremos todas
	 */
	private void establecerElementosVista(HttpServletRequest request,
			GestionAuditoriaBI service, String module) {
		// Obtenemos los modulos existentes
		List modulos = (List) service.getModules();
		request.setAttribute(AuditoriaConstants.LISTA_MODULOS_KEY, modulos);

		// Obtenemos la lista de acciones
		List acciones = null;
		if (module == null)
			acciones = (List) service.getActions(ArchivoModules.NONE_MODULE);
		else
			acciones = (List) service.getActions(Integer.parseInt(module));
		request.setAttribute(AuditoriaConstants.LISTA_ACCIONES_KEY, acciones);

		// Obtenemos la lista de los grupos
		List listaGrupos = getGestionControlUsuarios(request).getGrupos();
		listaGrupos.remove(new GrupoVO(CritUsuarioVO.GLOBAL_GROUP));
		listaGrupos.remove(new GrupoVO(CritUsuarioVO.GLOBAL_GROUP_ADM));
		request.setAttribute(AuditoriaConstants.LISTA_GRUPOS_KEY, listaGrupos);
	}

	/**
	 * Muestra el listado de las pistas de auditoria según los filtros
	 * aplicados.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void buscarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de busqExecuteLogic");

		// Obtenemos el servicio para el usuario conectado
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionAuditoriaBI service = services.lookupGestionAuditoriaBI();

		// Añadir los parámetros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((BusquedaPistasForm) form).getMap());

		// Validar el formulario
		ActionErrors errores = form.validate(mapping, request);
		if ((errores == null) || errores.isEmpty()) {
			logger.info("Formulario validado");

			// Guardar el enlace a la página
			saveCurrentInvocation(KeysClientsInvocations.AUDITORIA_BUSCAR,
					request);

			// Recuperar la información del formulario
			BusquedaPistasVO busquedaPistasVO = new BusquedaPistasVO();
			((BusquedaPistasForm) form).populate(busquedaPistasVO);
			if (logger.isDebugEnabled())
				logger.debug("Busqueda: " + busquedaPistasVO.toString());

			try {
				// Realizar la búsqueda
				request.setAttribute(AuditoriaConstants.LISTA_PISTAS_KEY,
						service.getPistas(busquedaPistasVO, new PageInfo(
								request, "accion", SortOrderEnum.DESCENDING)));
			} catch (TooManyResultsException e) {
				// No se va a lanzar nunca esta excepción
			}

			// Establecemos el action para la recarga del display
			request.setAttribute(AuditoriaConstants.ACTION, ACTION);

			setReturnActionFordward(request,
					mapping.findForward("listado_pistas"));
		} else {
			logger.info("Formulario inv\u00E1lido");

			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);

			// Establecemos los elementos de la vista
			this.establecerElementosVista(request, service, null);

			setReturnActionFordward(request,
					mapping.findForward("formulario_busqueda"));
		}
	}

	/**
	 * Muestra el detalle de una pista pistas de auditoria.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void detailExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de detailExecuteLogic");

		// Obtenemos el servicio para el usuario conectado
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionAuditoriaBI service = services.lookupGestionAuditoriaBI();

		// Obtenemos el identificador de la pista
		String idPista = request.getParameter(Constants.ID);
		if (idPista != null && idPista.trim().length() > 0) {
			// Obtenemos la pista y lo metemos para la vista
			TrazaVO pista = service.getPista(idPista);
			request.setAttribute(AuditoriaConstants.PISTA_KEY, pista);

			// Obtenemos el detalle y lo metemos para la vista
			List datos = service.getDatosPista(idPista);
			request.setAttribute(AuditoriaConstants.DETALLE_PISTA_KEY, datos);

			// Metemos la hoja de transformacion de los detalles
			request.setAttribute(AuditoriaConstants.FICHA_XSL_KEY,
					getDefaultTemplate());
		}

		// Guardar el enlace a la página
		saveCurrentInvocation(KeysClientsInvocations.AUDITORIA_DETALLEPISTA,
				request);
		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward(Constants.FORWARD_DETALLE_PISTA));
	}

	/**
	 * Obtiene la plantilla XSL para transformar los detalles de log.
	 * 
	 * @return Plantilla XSL.
	 */
	private String getDefaultTemplate() {

		// ConfiguracionAuditoria cfgAudit = ConfiguracionSistemaArchivoFactory
		// .getConfiguracionSistemaArchivo().getConfiguracionAuditoria();

		final String DEFAULT_TEMPLATE_FILENAME = ConfiguracionArchivoManager
				.getInstance().getRutaPlantillaXSL(TipoAcceso.AUDITORIA);
		String template = null;

		try {
			template = FileHelper.getTextFileContent(DEFAULT_TEMPLATE_FILENAME);
		} catch (IOException e) {
			logger.error("Error al leer la plantilla XSL ["
					+ DEFAULT_TEMPLATE_FILENAME + "]", e);
		}

		return template;
	}
}
