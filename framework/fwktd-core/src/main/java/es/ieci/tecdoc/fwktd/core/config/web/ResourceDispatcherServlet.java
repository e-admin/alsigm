package es.ieci.tecdoc.fwktd.core.config.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolver;

/**
 *** 
 * Servlet abstracto para la inicializacion de carga de recursos de skins en las
 * aplicaciones de sigem Deberemos sobrescribir los metodos abstractos
 * <code>initConfigFilePathResolver</code> y <code>getSkinName</code>
 * 
 * 
 * Tambiem deberemos tener en el web.xml una entrada del tipo:
 * 
 * 
 * 
 * <servlet> <servlet-name>ResourceDispatcherServlet</servlet-name>
 * <servlet-class
 * >ieci.tecdoc.sgm.core.web.ResourceDispatcherServlet</servlet-class>
 * <init-param> <param-name>subdir</param-name>
 * <param-value>/NombreDeDespliegueAplicacioneSigem</param-value> </init-param>
 * 
 * <init-param> <param-name>defaultSkin</param-name>
 * <param-value>nombreSkinDefecto</param-value> </init-param>
 * 
 * <servlet-mapping> <servlet-name>ResourceDispatcherServlet</servlet-name>
 * <url-pattern>/resourceServlet/*</url-pattern> </servlet-mapping>
 * 
 * </servlet>
 * 
 * NOTA: el pararámetro del servlet "subdir" deberá ser el nombre de la
 * aplicacion ejemplo: /AplicacionWeb por lo que el archivo en el proyecto war
 * deberá ir bajo la ruta
 * 
 * /src/main/resources/AplicacionWeb/skinEntidad_numeroEntidad
 * 
 * La aplicacion resolvera el fichero concatenando
 * subdirectorio/skinActual/nombreDeFichero para ello usara la clase
 * <code>ConfigFilePathResolver</code>
 * 
 * Si La petición será algo asi:
 * http://localhost:8080/AplicacionWeb/resourceServlet/logos/logo.jpg y el skin
 * en el que estamos logados es la "skin1" ira a buscar la ruta en:
 * SIGEM_AplicacionWeb/skin1/logos/logo.jpg
 * 
 * En caso de no existir el skin1 la ruta a buscar sera:
 * AplicacionWeb/skinDefecto/logos/logo.jpg
 * 
 * 
 * 
 * 
 */
public abstract class ResourceDispatcherServlet extends HttpServlet {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ResourceDispatcherServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String SUBDIR_PARAM_NAME = "subdir";
	public final static String DEFAULT_SKIN = "defaultSkin";

	protected String subdir = "";

	protected String defaultSkin;

	protected ConfigFilePathResolver pathResolver;

	public void init() throws ServletException {

		this.subdir = this.getInitParameter(SUBDIR_PARAM_NAME);
		this.defaultSkin = this.getInitParameter(DEFAULT_SKIN);
		this.initConfigFilePathResolver();

	}

	public abstract void initConfigFilePathResolver();

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doWork(req, resp);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doWork(req, resp);

	}

	protected void doWork(HttpServletRequest req, HttpServletResponse resp) {

		String logoLocation = this.getLocation(req, resp);
		try {
			InputStream input = new FileInputStream(logoLocation);
			OutputStream output = resp.getOutputStream();
			IOUtils.copy(input, output);
			input.close();
			output.flush();
		} catch (Exception ex) {
			logger
					.error(
							"Error cargando recurso a traves de ResourceDispatcherServlet",
							ex);
		}
	}

	/**
	 * Obtiene la localizacion fisica del recurso lo buscará en el skin
	 * correspondiente o sino en el skin por defecto
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	protected String getLocation(HttpServletRequest req,
			HttpServletResponse resp) {
		String result = "";

		String fileName = getFileName(req);

		// /SIGEM_AplicacionWeb/skinEntidad_numeroEntidad/ficheroLogo
		String skinName = getSkinName(req);
		String fullFileName = skinName + File.separator + fileName;

		result = pathResolver.resolveFullPath(fullFileName, subdir);

		// si el fichero no existe devolver fichero con skin por defecto
		if (StringUtils.isEmpty(result)) {
			fullFileName = getDefaultSkin() + File.separator + fileName;
			result = pathResolver.resolveFullPath(fullFileName, subdir);
		}

		if (StringUtils.isEmpty(result)) {
			logger.warn("No se ha podido cargar el recurso solicitado:"
					+ getFileName(req) + " a traves de "
					+ this.getClass().getName());
		}

		return result;
	}

	/**
	 * Obtiene el filename a partir de lo que se concatena a la peticion del
	 * servlet ejemplo: Si la peticion es
	 * /pruebaWar/logoServlet/images/logo.jpg" devolveria como filename
	 * "images/logo.jpg"
	 * 
	 * @param req
	 * @return
	 */
	protected String getFileName(HttpServletRequest req) {

		String result = "";

		// "req.getServletPath()"= "/logoServlet" /"req.getContextPath()"=
		// "/pruebaWar" "req.getRequestURI()"=
		// "/pruebaWar/logoServlet/images/logo.jpg"
		result = req.getRequestURI();
		// result=StringUtils.remove(result,
		// req.getContextPath()+req.getServletPath());
		result = StringUtils.replace(result, req.getContextPath()
				+ req.getServletPath(), "");

		return result;
	}

	/**
	 * Obtiene el nombre del skin a usar
	 * 
	 * @param req
	 * @return
	 */
	abstract protected String getSkinName(HttpServletRequest req);

	/**
	 * Obtiene el skin por defecto
	 * 
	 * @return
	 */
	public String getDefaultSkin() {
		return defaultSkin;
	}

	public ConfigFilePathResolver getPathResolver() {
		return pathResolver;
	}

	public void setPathResolver(ConfigFilePathResolver pathResolver) {
		this.pathResolver = pathResolver;
	}

}
