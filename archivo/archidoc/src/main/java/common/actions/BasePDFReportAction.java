package common.actions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import solicitudes.prestamos.PrestamosConstants;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.Messages;
import common.MultiEntityConstants;
import common.OrganizationMessages;
import common.bi.ServiceRepository;
import common.navigation.ClientInvocation;
import common.navigation.InvocationStack;
import common.navigation.KeysClientsInvocations;
import common.navigation.TitlesToolBar;
import common.util.DateUtils;
import common.util.StringUtils;

import es.archigest.framework.web.action.ArchigestActionProcessException;
import es.archigest.framework.web.action.ArchigestPDFReportAction;

/**
 * Action del que heredan los action de los informes de la aplicación, y que
 * define un conjunto de métodos base utilizados en la capa de vista de la
 * aplicación.
 * 
 */
public abstract class BasePDFReportAction extends ArchigestPDFReportAction {
	// PARAMETROS DEL INFORME
	private static final String REPORT_DIR = "REPORT_DIR";
	private static final String LABEL_TITULO_INFORME = "LABEL_TITULO_INFORME";
	protected static final String PARAM_FECHA_OPERACION = "PARAM_FECHA_OPERACION";
	protected static final String LABEL_DE = "LABEL_DE";
	protected static final String LABEL_PAGINA = "LABEL_PAGINA";
	protected static final String LABEL_DIRECCION_POSTAL = "LABEL_DIRECCION_POSTAL";
	protected static final String LABEL_SUBTITULO3 = "LABEL_SUBTITULO3";
	protected static final String LABEL_SUBTITULO2 = "LABEL_SUBTITULO2";
	protected static final String LABEL_SUBTITULO1 = "LABEL_SUBTITULO1";
	protected static final String LABEL_TITULO = "LABEL_TITULO";
	protected static final String LABEL_ORGANIZATION = "LABEL_ORGANIZATION";
	protected static final String LABEL_CODIGO_ARCHIVO = "LABEL_CODIGO_ARCHIVO";
	protected static final String LABEL_CAJA = "LABEL_CAJA";
	protected static final String LABEL_RELACION = "LABEL_RELACION";
	protected static final String CODIGO_RELACION = "CODIGO_RELACION";

	protected final static String RETURN_FORWARD_KEY = "RETURN_FORWARD_KEY";
	protected final static String REPORT_NAMES = "REPORT_NAMES_KEY";
	protected final static String REPORT_PARAMETERS = "REPORT_PARAMETERS_KEY";
	protected final static String DATA_SOURCES = "DATA_SOURCES_KEY";

	/**
	 * Establece una actionForward de retorno q mas tarde sera tratada por el
	 * find q corresponda: finsuccess en caso de q todo la eejecucion haya sido
	 * correcta findfailure en caso de q se hay producido un error
	 * 
	 * @param request
	 * @param actionForward
	 */
	protected void setReturnActionFordward(HttpServletRequest request,
			ActionForward actionForward) {
		request.setAttribute(RETURN_FORWARD_KEY, actionForward);
	}

	/**
	 * Captura de y muestra de una excepcion
	 */
	protected void catchException(Exception exception, ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		super.catchException(exception, mapping, form, request, response);

		request.setAttribute(Constants.EXCEPCION_KEY,
				((ArchigestActionProcessException) exception).getNested());

		setReturnActionFordward(request, mapping.findForward("globalerror"));
	}

	public ServiceRepository getServiceRepository(HttpServletRequest request) {
		return ServiceRepository.getInstance(ServiceClient
				.create(getAppUser(request)));
	}

	/*
	 * Obtención de una instacia de la pila de invocaciones
	 * 
	 * @param request
	 * 
	 * @return
	 */
	protected InvocationStack getInvocationStack(HttpServletRequest request) {
		InvocationStack invStack = (InvocationStack) request.getSession()
				.getAttribute(Constants.INVOCATION_STACK_KEY);
		if (invStack == null) {
			Map parameters = null;// new HashMap();
			// parameters.put("method", "goHome");
			ClientInvocation cli = ClientInvocation.getInstance(
					KeysClientsInvocations.KEY_HOME, request.getContextPath(),
					Constants.HOME_URL, parameters, true);

			cli.setTitleNavigationToolBar(TitlesToolBar
					.getATitleForClientInvocation(cli));

			request.getSession().setAttribute(Constants.INVOCATION_STACK_KEY,
					invStack = new InvocationStack(cli, request));

		}
		return invStack;
	}

	/**
	 * Coloca sobre el objeto temporal session un objeto
	 * 
	 * @param request
	 * @param nameObject
	 * @param value
	 */
	public void setInTemporalSession(HttpServletRequest request,
			String nameObject, Object value) {
		getInvocationStack(request).setInSession(request, nameObject, value);
	}

	/**
	 * Obtiene de session temporal un objeto
	 * 
	 * @param request
	 * @param nameObject
	 * @return
	 */
	public Object getFromTemporalSession(HttpServletRequest request,
			String nameObject) {
		return getInvocationStack(request).getFromSession(request, nameObject);
	}

	/**
	 * Borra la session temporal
	 * 
	 * @param request
	 */
	public void cleanTemporalSession(HttpServletRequest request) {
		getInvocationStack(request).cleanSession(request);
	}

	public void removeInTemporalSession(HttpServletRequest request,
			String nameAttribute) {
		getInvocationStack(request).removeInTemporalSession(request,
				nameAttribute);
	}

	public AppUser getAppUser(HttpServletRequest request) {
		return (AppUser) request.getSession()
				.getAttribute(Constants.USUARIOKEY);
	}

	public ServiceClient getServiceClient(HttpServletRequest request) {
		AppUser appUser = getAppUser(request);
		return (appUser != null ? ServiceClient.create(appUser) : null);
	}

	/**
	 * Indica si es un report multiple. False siempre.
	 * 
	 * @param javax
	 *            .servlet.http.HttpServletRequest
	 * @param javax
	 *            .servlet.http.HttpServletResponse
	 * @param org
	 *            .apache.struts.action.ActionForm
	 * @param org
	 *            .apache.struts.action.ActionMapping
	 */
	protected boolean isMultiReport(HttpServletRequest request,
			HttpServletResponse response, ActionForm form, ActionMapping mapping) {
		return false;
	}

	/**
	 * Obtiene los parámetros básicos del informe
	 * 
	 * @param request
	 *            Request
	 * @return Parámetros básicos del informe
	 */
	protected Map getParametrosBasicos(HttpServletRequest request,
			String tituloInforme) {
		Map parametros = getParametrosBasicos(request);

		parametros.put(LABEL_TITULO_INFORME, tituloInforme);
		return parametros;
	}

	/**
	 * Obtiene los parámetros básicos del informe
	 * 
	 * @param request
	 *            Request
	 * @return Parámetros básicos del informe
	 */
	protected Map getParametrosBasicos(HttpServletRequest request) {
		Map parametros = new HashMap();

		// Etiquetas
		final MessageResources messages = (MessageResources) request
				.getAttribute(Globals.MESSAGES_KEY);
		parametros.put(LABEL_TITULO, messages.getMessage(
				Constants.TITULO_INFORME,
				OrganizationMessages.getString(OrganizationMessages.TITLE)));
		parametros
				.put(LABEL_SUBTITULO1, messages.getMessage(
						Constants.INFORME_SUBTITULO_1, OrganizationMessages
								.getString(OrganizationMessages.SUBTITLE1)));
		parametros
				.put(LABEL_SUBTITULO2, messages.getMessage(
						Constants.INFORME_SUBTITULO_2, OrganizationMessages
								.getString(OrganizationMessages.SUBTITLE2)));
		parametros
				.put(LABEL_SUBTITULO3, messages.getMessage(
						Constants.INFORME_SUBTITULO_3, OrganizationMessages
								.getString(OrganizationMessages.SUBTITLE3)));
		parametros.put(LABEL_DIRECCION_POSTAL, messages.getMessage(
				Constants.INFORME_DIRECCION, OrganizationMessages
						.getString(OrganizationMessages.POSTAL_ADDRESS)));
		parametros.put(LABEL_PAGINA, Messages.getString(
				PrestamosConstants.LABEL_INFORMES_PAGINA, request.getLocale()));
		parametros.put(LABEL_DE, Messages.getString(
				PrestamosConstants.LABEL_INFORMES_DE, request.getLocale()));
		String fechaActual = DateUtils.formatDate(DateUtils.getFechaActual());
		parametros.put(
				PARAM_FECHA_OPERACION,
				Messages.getString(
						"archigest.archivo.eliminacion.fechaOperacion",
						request.getLocale())
						+ ":" + fechaActual);

		AppUser appUser = getAppUser(request);
		parametros.put(MultiEntityConstants.ENTITY_PARAM, appUser.getEntity());
		addReportDir(parametros, request);

		return parametros;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.archigest.framework.web.action.ArchigestPDFReportAction#loadReport(java.lang.String,
	 *      java.util.Map)
	 */
	protected InputStream loadReport(String reportName, Map params) {
		InputStream is = null;
		String pathInforme = null;
		try {
			pathInforme = ConfiguracionArchivoManager.getInstance()
					.getPathInforme(reportName, getReportEntity(params));
			is = new FileInputStream(pathInforme);
		} catch (FileNotFoundException fnfe) {
			params.put(LABEL_TITULO, pathInforme + ": No encontrado");

			pathInforme = ConfiguracionArchivoManager.getInstance()
					.getPathInforme(ConfiguracionArchivoManager.CABECERA,
							getReportEntity(params));
			try {
				is = new FileInputStream(pathInforme);
			} catch (FileNotFoundException e1) {
				logger.error("Error Al Obtener el Informe: " + pathInforme,
						fnfe);
				return null;
			}
		} catch (Exception e) {
			logger.error("Error Al Obtener el Informe: " + pathInforme, e);
		}
		return is;
	}

	public void addReportDir(Map parametros, HttpServletRequest request) {
		parametros.put(MultiEntityConstants.ENTITY_PARAM, getAppUser(request)
				.getEntity());
		parametros.put(REPORT_DIR, ConfiguracionArchivoManager.getInstance()
				.getPathInformes(getReportEntity(request)));
	}

	private String getReportEntity(HttpServletRequest request) {
		// Obtener información del usuario conectado
		AppUser appUser = getAppUser(request);

		// Obtener la entidad para el usuario conectado
		String entity = null;

		if ((appUser != null) && (StringUtils.isNotEmpty(appUser.getEntity()))) {
			entity = appUser.getEntity();
		}

		return entity;
	}

	private String getReportEntity(Map params) {

		// Obtener la entidad para el usuario conectado
		String entity = null;

		if (params != null) {
			entity = (String) params.get(MultiEntityConstants.ENTITY_PARAM);
		}

		return entity;
	}

	protected String getNombreFormato(String formato) {
		String nombreFormato = "";
		if (Constants.EQUAL.equals(formato))
			nombreFormato = Constants.STRING_EQUAL;
		else if (Constants.MAYOR.equals(formato))
			nombreFormato = Constants.STRING_MAYOR;
		else if (Constants.MAYOR_IGUAL.equals(formato))
			nombreFormato = Constants.STRING_MAYOR_IGUAL;
		else if (Constants.MENOR.equals(formato))
			nombreFormato = Constants.STRING_MENOR;
		else if (Constants.MENOR_IGUAL.equals(formato))
			nombreFormato = Constants.STRING_MENOR_IGUAL;
		return nombreFormato;
	}
}