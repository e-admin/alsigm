package com.ieci.tecdoc.isicres.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.IdiomaUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.Utils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;



/**
     * Filtro que verifica que el usuario esta autenticado en las zonas necesarias y adicionalmente habilita o deshabilita la
     * respuesta comprimida mediante gzip.
     * En caso de no estar autenticado en una zona que necesita estar autenticado, muestra mensaje javascript y redirige
     * hacia /__index.jsp que se encargara de la realizacion de login
**/
public class LoginCheckFilter implements javax.servlet.Filter, Keys {

    private static Logger _logger = Logger.getLogger(LoginCheckFilter.class);
    private static final String PARAM_EXCLUDE_PATHS="excludePaths";

    /**
     * The servlet context that includes set of methods that a servlet uses to
     * communicate with its servlet container
     */
    private ServletContext ctx;

    /**
     * A filter configuration object used by the web container to pass
     * information to a filter during initialization
     */
    private FilterConfig filterConfig;

    public static long provCityDir = 0;

    //rutas a excluir de las comprobaciones del filtro. separadas por ';'.
    private String excludePaths=null;


    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        ctx = filterConfig.getServletContext();
        excludePaths = filterConfig.getInitParameter(PARAM_EXCLUDE_PATHS);
    }



    /**
     * Filtro que verifica que el usuario esta autenticado en las zonas necesarias y adicionalmente habilita o deshabilita la
     * respuesta comprimida mediante gzip.
     * En caso de no estar autenticado en una zona que necesita estar autenticado, muestra mensaje javascript y redirige
     * hacia /__index.jsp que se encargara de la realizacion de login
     *
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {

        if (req != null) {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;


            HttpSession mySession = request.getSession();

			UseCaseConf loginStatus = (UseCaseConf) mySession
					.getAttribute(Keys.J_USECASECONF);

    		//Seteamos datos para auditoria
			Utils.setAuditContext(request);

			// validamos si el objeto es distinto de null ya que se inserta en
			// session inicializado a nulo, con lo que comprobaremos uno de los
			// parametros del objeto para lanzar la validacion
			String sessionID = null;
			if (loginStatus != null){
				sessionID = loginStatus.getSessionID();
			}

            //si esta logado o la petición es de un pdf o es de subida de fichero a traves de FileUploadScan
            if (sessionID != null || request.getRequestURI().indexOf("/FileUploadScan") != -1
                    	|| request.getRequestURI().indexOf(".pdf") != -1|| request.getRequestURI().indexOf("/remoting") != -1) {

                //Logged in - Let's pass thru the user
            	String ae = request.getHeader("accept-encoding");

            	//si se acepta compresion gzip y esta configurada la compresion en la aplicacion y no es una peticion a los servlets
            	//  validatecode validateunit reportdoc updatefields comprime la respuesta , sino no la comprime
                if (ae != null && ae.indexOf("gzip") != -1
                        && Configurator.getInstance().getPropertyBoolean(ConfigurationKeys.KEY_DESKTOP_USECOMPRESSEDCONTENTS)
                        && (request.getRequestURI().indexOf("/validatecode") == -1)
                        && (request.getRequestURI().indexOf("/validateunit") == -1)
                        && (request.getRequestURI().indexOf("/reportdoc") == -1)
                        && (request.getRequestURI().indexOf("/updatefields") == -1)) {
                    GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(response);
                    chain.doFilter(req, wrappedResponse);
                    wrappedResponse.finishResponse();
                    return;
                } else {
                    chain.doFilter(req, res);
                }
            } else {
            	// no estamos logados, los recursos que no necesitan estar logado y pueden facilitar el proceso de login
                if ((request.getRequestURI().indexOf("__index") != -1)
                		|| (request.getRequestURI().equals(request.getContextPath()+"/"))
                        || (request.getRequestURI().equals(request.getContextPath()))
                        || (request.getRequestURI().indexOf("css") != -1)
                        || (request.getRequestURI().indexOf("gif") != -1)
                        || (request.getRequestURI().indexOf("png") != -1)
                        || (request.getRequestURI().indexOf("jpg") != -1)
                        || (request.getRequestURI().indexOf("logout") != -1)
                        || (request.getRequestURI().indexOf("login") != -1)
                        || (request.getRequestURI().indexOf("default") != -1)
                        || (request.getRequestURI().indexOf("chgpwdsrv") != -1)
                        || (request.getRequestURI().indexOf("cab") != -1)
                        || (request.getRequestURI().indexOf("js") != -1 && request.getRequestURI().indexOf("jsp") == -1)
                        || (request.getRequestURI().indexOf("htm") != -1)
                        || (request.getRequestURI().indexOf("html") != -1)
                        || (request.getRequestURI().indexOf("xsl") != -1)
                        || (containsPathToIgnore(request.getRequestURI()))){
                    //User is going to or being redirected to login page or
                    // loading images - Let's pass thru the user

                	// se mira si devuelve la respuesta comprimida
                    String ae = request.getHeader("accept-encoding");
                    if (ae != null && ae.indexOf("gzip") != -1
                            && Configurator.getInstance().getPropertyBoolean(ConfigurationKeys.KEY_DESKTOP_USECOMPRESSEDCONTENTS)) {
                        GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(response);
                        chain.doFilter(req, wrappedResponse);
                        wrappedResponse.finishResponse();
                        return;
                    } else {
                        chain.doFilter(req, res);
                    }
                } else {
                	//Estamos en una zona protegida y o bien no estamos logados o expiro la sesion
                	// se procede a generar el menaje de error javascript personalizado por la peticion

                    // Texto del idioma. Ej: EU_
                    String idioma = (String) mySession.getAttribute(Keys.J_IDIOMA);
                    if(idioma == null){
                    	idioma = IdiomaUtils.getInstance().getIdioma(request);
                    }
                    // Numero del idioma. Ej: 10
                    Long numIdioma = (Long) mySession.getAttribute(Keys.J_NUM_IDIOMA);
                    if(numIdioma == null){
                    	numIdioma = IdiomaUtils.getInstance().getNumIdioma(request);
                    }

                    Boolean form = RequestUtils.parseRequestParameterAsBoolean(request, "Form");
                    int enabled = RequestUtils.parseRequestParameterAsint(request, "Enabled");
                    int isDtrList = RequestUtils.parseRequestParameterAsint(request, "IsDtrList");
                    int enabledImp = 1;
                    response.setContentType("text/html; charset=UTF-8");
                    PrintWriter writer = response.getWriter();

                    if ((request.getRequestURI().indexOf("openfolder") != -1)
                            || (request.getRequestURI().indexOf("newfolder") != -1)
                            || (request.getRequestURI().indexOf("newfldbuc") != -1)
                            || (request.getRequestURI().indexOf("printsello") != -1)) {
                        ResponseUtils.generateJavaScriptErrorSessionExpiredOpenFolder(writer, new SessionException(
                                SessionException.ERROR_SESSION_EXPIRED), idioma, numIdioma, form);
                    } else if ((request.getRequestURI().indexOf("vldres") != -1)) {
                        if (isDtrList == 1) {
                            enabled = 0;
                        }
                        ResponseUtils.generateJavaScriptLogSessionExpiredVldRes(writer, new SessionException(
                                SessionException.ERROR_SESSION_EXPIRED).getMessage(), idioma, numIdioma, enabled);
                    } else if ((request.getRequestURI().indexOf("validateunit") != -1)
                    		|| (request.getRequestURI().indexOf("validatecode") != -1)
                    		|| (request.getRequestURI().indexOf("actionform") != -1)
                    		|| (request.getRequestURI().indexOf("updatefields") != -1)) {
                        ResponseUtils.generateJavaScriptLogSessionExpiredValidateUnit(writer, new SessionException(
                                SessionException.ERROR_SESSION_EXPIRED).getMessage(), idioma, numIdioma);
                    } else if ((request.getRequestURI().indexOf("report") != -1)
                            || (request.getRequestURI().indexOf("frmpersistflds") != -1)
                            || (request.getRequestURI().indexOf("reportdoc") != -1)) {
                    	if ((request.getRequestURI().indexOf("reportsfmt") != -1)) {
                            ResponseUtils.generateJavaScriptLogSessionExpiredVldRes(writer, new SessionException(
                                    SessionException.ERROR_SESSION_EXPIRED).getMessage(), idioma, numIdioma, enabled);

                        } else if ((request.getRequestURI().indexOf("reportdoc") != -1)) {
                            ResponseUtils.generateJavaScriptLogSessionExpiredReportDoc(writer, new SessionException(
                                    SessionException.ERROR_SESSION_EXPIRED).getMessage());
                        } else {
                            ResponseUtils
                                    .generateJavaScriptLogSessionExpiredVldRes(writer, new SessionException(
                                            SessionException.ERROR_SESSION_EXPIRED).getMessage(), idioma, numIdioma,
                                            enabledImp);
                        }
                    } else if ((request.getRequestURI().indexOf("closefolder") != -1)) {
                        ResponseUtils.generateJavaScriptLogSessionExpiredCloseFolder(writer, new SessionException(
                                SessionException.ERROR_SESSION_EXPIRED).getMessage());
                    } else if ((request.getRequestURI().indexOf("SetPersistFields") != -1)) {
                        ResponseUtils
                        .generateJavaScriptLogSessionExpiredSetPersistFields(writer, new SessionException(
                                SessionException.ERROR_SESSION_EXPIRED).getMessage(), idioma, numIdioma,
                                enabledImp);
                    } else if ((request.getRequestURI().indexOf("dtrfdr") != -1)
                            || (request.getRequestURI().indexOf("origdocfdr") != -1)
                            || (request.getRequestURI().indexOf("vldbuscinter") != -1)
                            || (request.getRequestURI().indexOf("asocregsfdr") != -1)
                            || (request.getRequestURI().indexOf("saveorigdoc") != -1)
                            || (request.getRequestURI().indexOf("getpage") != -1)
                            || (request.getRequestURI().indexOf("flushfdr") != -1)) {
                        ResponseUtils.generateJavaScriptLogSessionExpiredDtrfdr(writer, new SessionException(
                                SessionException.ERROR_SESSION_EXPIRED).getMessage(), idioma, numIdioma);
                    } else if ((request.getRequestURI().indexOf("vldpoblacion") != -1)) {
                        provCityDir++;
                        ResponseUtils.generateJavaScriptLogSessionExpiredProvCityDir(writer, new SessionException(
                                SessionException.ERROR_SESSION_EXPIRED).getMessage());
                    } else if ((request.getRequestURI().indexOf("vlddirinter") != -1)) {
                        ResponseUtils.generateJavaScriptLogSessionExpiredProvCityDir(writer, new SessionException(
                                SessionException.ERROR_SESSION_EXPIRED).getMessage());
                    } else if ((request.getRequestURI().indexOf("getsearchdist") != -1)
                    		|| (request.getRequestURI().indexOf("validatesearchdist") != -1)) {
                        ResponseUtils.generateJavaScriptLogSessionExpiredGetSearchDist(writer, new SessionException(
                                SessionException.ERROR_SESSION_EXPIRED).getMessage(), idioma, numIdioma);
                    } else if (((request.getRequestURI().indexOf("vlddireccion") != -1) || (request.getRequestURI()
                            .indexOf("vldciudad") != -1))) {
                        if (provCityDir > 0) {
                            provCityDir = 0;
                        }
                    } else if (request.getRequestURI().indexOf("frmdata") != -1) {
                        ResponseUtils.generateJavaScriptLogSessionExpiredFrmData(writer, new SessionException(
                                SessionException.ERROR_SESSION_EXPIRED).getMessage(), idioma, numIdioma);
                    }else if (request.getRequestURI().indexOf("RelFilter") != -1) {
    					ResponseUtils
    								.generateJavaScriptLogSessionExpiredRelation(
    										writer, idioma, numIdioma,
    										new SessionException(
    				                                SessionException.ERROR_SESSION_EXPIRED).getMessage(), 1);
                    } else {
                        ResponseUtils.generateJavaScriptErrorSessionExpired(writer, new SessionException(
                                SessionException.ERROR_SESSION_EXPIRED), idioma, numIdioma);
                    }
                }
            }
        }
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        this.filterConfig = null;
    }

    private boolean containsPathToIgnore(String requestURI){
    	java.util.StringTokenizer tokenizer=new java.util.StringTokenizer(excludePaths, ";");
    	while(tokenizer.hasMoreElements()){
    		String ignorePath=tokenizer.nextToken();
    		if(requestURI.indexOf(ignorePath)!=-1){
    			return true;
    		}
    	}
    	return false;
    }
}