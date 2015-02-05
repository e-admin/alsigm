package ieci.tecdoc.sgm.registropresencial.filters;

import ieci.tecdoc.sgm.registropresencial.utils.AuthenticationHelper;

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
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.Utils;
import com.ieci.tecdoc.isicres.filters.GZIPResponseWrapper;


/**
 * The <code>LoginCheckFilter</code> is a servlet filter that intercepts all
 * inbound request to make sure the access is authenticated.
 *
 * This work is licensed under a Creative Commons License. More information at
 * <a
 * href="http://creativecommons.org/licenses/by/1.0/">http://creativecommons.org/licenses/by/1.0/
 * </A>
 *
 * @version 1.0
 * @since 03/10/2004
 * @author ( <a href="mailto:vscarpenter@nospam.mailblocks.com">Vinny Carpenter
 *         </A>
 */
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

    /**
     * Called by the web container to indicate to a filter that it is being
     * placed into service. The servlet container calls the init method exactly
     * once after instantiating the filter. The init method must complete
     * successfully before the filter is asked to do any filtering work.
     * <P>
     * The web container cannot place the filter into service if the init method
     * either
     * <OL>
     * <LI>Throws a ServletException
     * <LI>Does not return within a time period defined by the web container
     * </OL>
     *
     * @param filterConfig
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        ctx = filterConfig.getServletContext();
        excludePaths = filterConfig.getInitParameter(PARAM_EXCLUDE_PATHS);
    }

    /**
     * The <code>doFilter()</code> method performs the actual filtering work.
     * In its doFilter() method, each filter receives the current request and
     * response, as well as a FilterChain containing the filters that still must
     * be processed.
     * <P>
     * This filter is just used to capture and log information about the user
     * being passed in to the login servlet for tracking purposes.
     *
     * @param request
     *            Servlet request object
     * @param response
     *            Servlet response object
     * @param chain
     *            Filter chain
     * @exception IOException
     * @exception ServletException
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {

        if (req != null) {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;
            //could pass in false in the getSession() to return null for new
            // session.

            //Cookie[] cokkie = request.getCookies();
            //System.out.println("cookie");
            HttpSession mySession = request.getSession();
            Object loginStatus = mySession.getAttribute(Keys.J_USECASECONF);

    		//Seteamos datos para auditoria
			Utils.setAuditContext(request);

			 // Validación para el SIGEM
			// String jndiName = Configurator.getInstance().getProperty(
			// ConfigurationKeys.KEY_DESKTOP_REPORTSDATASOURCEJNDINAME);
			// jndiName = jndiName.substring(jndiName.lastIndexOf("/") + 1,
			// jndiName.length());
			//
			// if (!jndiName.equals(Keys.KEY_BUILD_TYPE_INVESICRES)) {
			if ((request.getRequestURI().indexOf(".css") == -1)
					&& (request.getRequestURI().indexOf(".gif") == -1)
					&& (request.getRequestURI().indexOf(".png") == -1)
					&& (request.getRequestURI().indexOf(".jpg") == -1)
					&& (request.getRequestURI().indexOf(".cab") == -1)
					&& (request.getRequestURI().indexOf(".js") == -1 || request
							.getRequestURI().indexOf("jsp") != -1)
					&& (request.getRequestURI().indexOf(".htm") == -1)
					&& (request.getRequestURI().indexOf(".html") == -1)
					&& (request.getRequestURI().indexOf(".xsl") == -1)) {
				_logger.info("LoginCheckFilter mySession: " + mySession);
				_logger.info("LoginCheckFilter request.getRequestURI(): "
						+ request.getRequestURI());

				if (!AuthenticationHelper.authenticate(request)) {
					_logger.info("No hay sesion válida: ");
					_logger.info("loginStatus: " + loginStatus);
					_logger.info("ServletPath: " + request.getServletPath());
					_logger.info("RequestURI: " + request.getRequestURI());

					if (loginStatus == null
							&& !request.getServletPath().equals(
									"/Frameslogin.jsp")
							&& request.getServletPath().indexOf(
									"/FileUploadScan") == -1
							&& request.getRequestURI().indexOf(".pdf") == -1) {
						response.sendRedirect(request.getContextPath()
								+ "/Frameslogin.jsp?"
								+ request.getQueryString());
						return;
					} else if (loginStatus != null
							&& !request.getServletPath().equals("/logout.jsp")) {
						response.sendRedirect(request.getContextPath()
								+ "/logout.jsp");
						return;
					}
				}
			}
			// }
			// FIN Validación para el SIGEM

            if (loginStatus != null || request.getRequestURI().indexOf("/FileUploadScan") != -1
                    	|| request.getRequestURI().indexOf(".pdf") != -1) {

                //Logged in - Let's pass thru the user
                String ae = request.getHeader("accept-encoding");
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
                if ((request.getRequestURI().indexOf("__index") != -1) || (request.getRequestURI().equals(request.getContextPath()+"/"))
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
                    // Texto del idioma. Ej: EU_
                    String idioma = (String) mySession.getAttribute(Keys.J_IDIOMA);
                    // Número del idioma. Ej: 10
                    Long numIdioma = (Long) mySession.getAttribute(Keys.J_NUM_IDIOMA);
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
                    } else if (request.getRequestURI().indexOf("RelFilter") != -1) {
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

    /**
     * Called by the web container to indicate to a filter that it is being
     * taken out of service. This method is only called once all threads within
     * the filter's doFilter method have exited or after a timeout period has
     * passed. After the web container calls this method, it will not call the
     * doFilter method again on this instance of the filter.
     * <P>
     * This method gives the filter an opportunity to clean up any resources
     * that are being held (for example, memory, file handles, threads) and make
     * sure that any persistent state is synchronized with the filter's current
     * state in memory.
     */
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