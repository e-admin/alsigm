package ieci.tecdoc.sgm.registro.action;
/*
 *  $Id: RegistroWebAction.java,v 1.1.2.2 2008/03/14 11:36:02 jnogales Exp $
 */
import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;
import ieci.tecdoc.sgm.core.services.idioma.ConstantesIdioma;
import ieci.tecdoc.sgm.core.user.web.SesionUserHelper;
import ieci.tecdoc.sgm.core.user.web.WebAuthenticationHelper;
import ieci.tecdoc.sgm.registro.plugin.ConfigLoader;
import ieci.tecdoc.sgm.registro.utils.Defs;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.core.io.Resource;

public abstract class RegistroWebAction extends Action {

	private static final Logger logger = Logger.getLogger(RegistroWebAction.class);

	public static final String GLOBAL_FORWARD_ERROR = "";
	public static final String GLOBAL_FORWARD_LOGIN = "errorAutenticacion";
	public static final String ERROR_KEY 	=  "mensajeError";
	public static final String PARAM_WEB_AUTH_URL = "WEB_AUTH_URL";
	private static final String TIPO_APLICACION = "RegistroTelematico";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = null;
		try{

			if(!SesionUserHelper.authenticateUser(request)){
				response.sendRedirect(WebAuthenticationHelper.getWebAuthURL(request,TIPO_APLICACION));
				return null;
			}

			//AuthenticationHelper.saveUser(request);

	    	forward = executeAction(mapping, form, request, response);

		}catch(Throwable e){
			logger.error("Error inesperado ejecutando acción.", e);
			return mapping.findForward(GLOBAL_FORWARD_ERROR);
		}

		return forward;
	}

	/**
	 * Obtener la ruta al fichero correspondiente de recursos del trámite.
	 * @param session Sesión.
	 * @param idEntidad Identificador de la entidad.
	 * @param tramiteId Código del trámite.
	 * @param idioma Idioma para el recurso.
	 * @param fileName Nombre del fichero del recurso.
	 * @param extension Extensión del fichero del recurso.
	 *
	 * @return Ruta absoluta al fichero de recursos del trámite.
	 */
	protected String getResourceTramitePath(HttpSession session, String idEntidad, String tramiteId, Locale idioma, String fileName, String extension) {

		final String separador = System.getProperty("file.separator");

		String directoryPath = null;
		String resourcePath = null;

		SigemConfigFilePathResolver pathResolver = SigemConfigFilePathResolver.getInstance();

		String tramitesPath = (String) session.getServletContext().getAttribute(Defs.PLUGIN_TMP_PATH_TRAMITES);

		// Ruta establecida a partir de la carpeta de trámites
		StringBuffer rutaCarpetaTramite = new StringBuffer();
		rutaCarpetaTramite.append(tramitesPath)
						  .append(separador)
						  .append(idEntidad)
						  .append(separador)
						  .append(tramiteId)
						  .append(separador);

		// Obtener la ruta a partir de la configuración de la propia aplicación
		Resource directoryConfig = pathResolver.loadResource(rutaCarpetaTramite.toString(), ConfigLoader.CONFIG_SUBDIR);
		if (directoryConfig != null) {
			try {
				directoryPath = directoryConfig.getFile().getAbsolutePath() + separador;
			} catch (IOException e) {
			}
		}

		if (directoryPath == null) {
			// Obtener la ruta a partir del path del servidor
			directoryPath = session.getServletContext().getRealPath("") + separador + rutaCarpetaTramite.toString();
		}

		String strIdioma = idioma.getLanguage();
		if (ConstantesIdioma.ESPANOL.equals(strIdioma)) {

			resourcePath = directoryPath + fileName.substring(0, fileName.length() -1) + "." + extension;
			if (!existResourcePath(resourcePath)) {
				return null;
			}
		} else {
			resourcePath = directoryPath + fileName + strIdioma + "." + extension;
			if (!existResourcePath(resourcePath)) {

				resourcePath = directoryPath + fileName.substring(0, fileName.length() -1) + "." + extension;
				if (!existResourcePath(resourcePath)) {
					return null;
				}
			}
		}

		return resourcePath;
	}

	protected boolean existResourcePath(String resourcePath) {
		File resourceFile = new File(resourcePath);
		if ((resourceFile == null) ||
			(!resourceFile.exists())) {
			return false;
		}
		return true;
	}

	/**
	 * Método específico que se ejecuta en cada acción.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public abstract ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
