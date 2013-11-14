package ieci.tecdoc.isicres.rpadmin.struts.filters;

import ieci.tecdoc.isicres.rpadmin.struts.filters.config.RedirectLoginFilterConfig;
import ieci.tecdoc.isicres.rpadmin.struts.util.AutenticacionAdministracion;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;

public class RedirectLoginFilter implements Filter{

	private static Logger logger = Logger.getLogger(RedirectLoginFilter.class);

	private static final String PARAM_FILE_EXCLUDE_PATHS="fileExcludePaths";

	private static final String PUNTO_Y_COMA = ";";

    //ruta del fichero que contiene los path a excluir
    private String fileExcludePaths=null;

    //rutas a excluir de las comprobaciones del filtro. separadas por ';'.
    private String excludePaths=null;

    /**
     * A filter configuration object used by the web container to pass
     * information to a filter during initialization
     */
    private FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		//obtenemos el path del fichero
		fileExcludePaths = filterConfig.getInitParameter(PARAM_FILE_EXCLUDE_PATHS);
		//obtenemos la config indicada en el fichero
		excludePaths = getConfigFicheroExcludePaths();

	}

	public void destroy() {
	    this.filterConfig = null;
	}

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		//Obtenemos el id de sesion
		String sessionID = obtenerSesionID(request);

		// Comprobamos si la sesion es valida
		if (sessionID == null) {
			// validamos si se permite el acceso al recurso solicitado sin
			// necesidad de sesion
			if (validarAccesoRecursoSinSesion(request)) {
				// Ejecutamos la aplicación de forma correcta
				chain.doFilter(request, response);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("La sesión ha caducado");
				}

				// Redireccionamos a la pantalla de login de la aplicación
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter writer = response.getWriter();
				// Generamos alert en pantalla y redireccionamos
				generateJavaScriptLogSessionExpired(writer,
						AutenticacionAdministracion.obtenerUrlLogout(request),
						new SessionException(
								SessionException.ERROR_SESSION_EXPIRED)
								.getMessage());
			}
		} else {
			// Ejecutamos la aplicación de forma correcta
			chain.doFilter(request, response);
		}
	}


	/**
	 * Método que lee el fichero indicado como parámetro del filtro:
	 * fileExcludePaths
	 *
	 * @return {@link String} - rutas a excluir de las comprobaciones del
	 *         filtro. separadas por ';'.
	 */
	private String getConfigFicheroExcludePaths() {
		String result = null;
		if (StringUtils.isNotBlank(fileExcludePaths)) {
			result = RedirectLoginFilterConfig.getInstance(fileExcludePaths)
					.getExcludePaths();
		}
		return result;
	}


	/**
	 * Método que recupara el ID de sesion de un usuario autenticado en la aplicación
	 *
	 * @param request - {@link HttpServletRequest}
	 * @return {@link String} - SessionID
	 */
	private String obtenerSesionID(HttpServletRequest request) {
		String sessionID = null;
		UseCaseConf loginStatus = null;

		// Comprobamos la sesion
		HttpSession session = request.getSession(false);

		//si la sesion es distinta de nulo
		if(session != null){
			// Obtenemos un objeto de sesion que se crea una vez que el usuario se
			// ha logeado
			loginStatus = (UseCaseConf) session.getAttribute(Keys.J_USECASECONF);
			// si el objeto que recuparamos de sesion es distinto a nulo,
			// buscamos algún atributo que valide que la sesion es válida
			if (loginStatus != null) {
				// Obtenemos el id de la session almacenada en el objeto
				sessionID = loginStatus.getSessionID();
			}
		}

		return sessionID;
	}

	/**
	 * Método que valida si el acceso a los recursos indicados puede realizarse sin sesion
	 * @param request
	 * @return {@link boolean} - true: Se permite el acceso / false: no se permite el acceso sin sesion
	 */
	private boolean validarAccesoRecursoSinSesion(HttpServletRequest request) {
		boolean result = false;

		if ((request.getRequestURI().equals(request.getContextPath() + "/"))
				|| (request.getRequestURI().equals(request.getContextPath()))
				|| (request.getRequestURI().indexOf("css") != -1)
				|| (request.getRequestURI().indexOf("gif") != -1)
				|| (request.getRequestURI().indexOf("png") != -1)
				|| (request.getRequestURI().indexOf("jpg") != -1)
				|| (request.getRequestURI().indexOf("logout") != -1)
				|| (request.getRequestURI().indexOf("login") != -1)
				|| (request.getRequestURI().indexOf("js") != -1 && request
						.getRequestURI().indexOf("jsp") == -1)
				|| (request.getRequestURI().indexOf("htm") != -1)
				|| (request.getRequestURI().indexOf("html") != -1)
				|| (containsPathToIgnore(request.getRequestURI()))){
			// se permite el acceso a los recursos
			result = true;
		}

		return result;
	}



	/**
	 * Método que genera el alert con el mensaje indicado y redirecciona a la URL indicada
	 * @param writer
	 * @param url - URL a la que se redirecciona
	 * @param message - Mensaje del alert
	 * @throws IOException
	 */
	private void generateJavaScriptLogSessionExpired(Writer writer, String url,
			String message) throws IOException {
		writer.write("<script language=\"javascript\">alert(\"" + message
				+ "\");");

		writer.write("window.open(\""+ url + "\" , \"_top\",\"location=no\",true);");
		writer.write("</script>");
	}

	/**
	 * Método que comprueba si los paths indicados por el fichero de
	 * configuración corresponden con el path que se intenta acceder
	 *
	 * @param requestURI
	 *            - Path al que se intenta acceder
	 * @return {@link boolean} - true: el path al que se intenta acceder
	 *         corresponde con el path excluido/ false: los paths no se
	 *         corresponden
	 */
    private boolean containsPathToIgnore(String requestURI){
	if(StringUtils.isNotBlank(excludePaths)){
		//Descomponemos la cadena por el separador punto y compa
		StringTokenizer tokenizer=new StringTokenizer(excludePaths, PUNTO_Y_COMA);
		while(tokenizer.hasMoreElements()){
			String ignorePath=tokenizer.nextToken();
			//comprobamos si la URL de acceso corresponde con el token de URL excluida
			if(requestURI.indexOf(ignorePath)!=-1){
				return true;
			}
		}
	}
	return false;
    }


}
