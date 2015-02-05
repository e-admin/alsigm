package ieci.tdw.ispac.ispacweb.util;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;
import ieci.tecdoc.sgm.core.config.ports.PortsConfig;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DocumentUtil {

	/** Clase del applet que lanza aplicaciones. */
	private static final String APP_LAUNCHER_APPLET_CODE =
		"ieci.tdw.applets.applauncher.AppLauncherApplet";

	/** Librería del applet que lanza aplicaciones. */
	private static final String APP_LAUNCHER_APPLET_ARCHIVE =
		"applets/applauncherapplet.jar";

	/**
	 * Ver un documento.
	 *
	 * @param servletCtx Contexto del servlet.
	 * @param request Petición actual.
	 * @param response Respuesta.
	 * @param servletName Nombre del servlet que se invoca para descargar el fichero.
	 * @param session Sesión del usuario.
	 * @param id Identificador de la entidad.
	 * @param docref Identificador único del documeno en el gestor documental
	 * @param mimeType Tipo del fichero.
	 * @param readonly Indicador de sólo lectura.
	 * @param defaultView Indicador para permitir ver cualquier tipo de fichero.
	 * @param reloadTopWindow Indicador para recargar la página principal.
	 * @return Cierto si se ve el documento, en caso contrario, falso.
	 * @throws Exception Si se produce algún error.
	 */
	public static boolean viewDocument(ServletContext servletCtx,
									   HttpServletRequest request,
			   						   HttpServletResponse response,
			   						   String servletName,
			   						   SessionAPI session,
			   						   String id,
			   						   String docref,
			   						   String mimeType,
			   						   String readonly,
			   						   boolean defaultView,
			   						   boolean reloadTopWindow) throws Exception {

		String url = generateURL(request, servletName, session.getTicket(), id, mimeType);

		boolean useOdtTemplantes = ConfigurationMgr.getVarGlobalBoolean(session.getClientContext(), ConfigurationMgr.USE_ODT_TEMPLATES, false);

	    if ( "application/msword".equalsIgnoreCase(mimeType)
	    		|| "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equalsIgnoreCase(mimeType)
	    		|| "application/excel".equalsIgnoreCase(mimeType)
	    		|| "application/x-excel".equalsIgnoreCase(mimeType)
				|| "application/x-msexcel".equalsIgnoreCase(mimeType)
				|| "application/vndms-excel".equalsIgnoreCase(mimeType)
				|| "application/vnd.ms-excel".equalsIgnoreCase(mimeType)
				|| "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equalsIgnoreCase(mimeType)
	    		|| "application/mspowerpoint".equalsIgnoreCase(mimeType)
				|| "application/powerpoint".equalsIgnoreCase(mimeType)
				|| "application/vndms-powerpoint".equalsIgnoreCase(mimeType)
				|| "application/vnd.ms-powerpoint".equalsIgnoreCase(mimeType)
				|| "application/x-mspowerpoint".equalsIgnoreCase(mimeType)
				|| ("application/vnd.oasis.opendocument.text".equalsIgnoreCase(mimeType) && useOdtTemplantes)
				|| "application/vnd.openxmlformats-officedocument.presentationml.presentation".equalsIgnoreCase(mimeType)
				|| "application/vnd.openxmlformats-officedocument.presentationml.slideshow".equalsIgnoreCase(mimeType)) {

	    	//String htmlPage = generateHtmlPage(servletCtx, request.getContextPath(), url, mimeType, readonly, reloadTopWindow, useOdtTemplantes);
	    	String htmlPage = generateHtmlPage(servletCtx, request, url, mimeType, readonly, reloadTopWindow, useOdtTemplantes);

	    	ServletOutputStream out = response.getOutputStream();
	    	response.setHeader("Pragma", "public");
	    	response.setHeader("Cache-Control", "max-age=0");
	    	response.setContentType("text/html");
	    	out.write(htmlPage.getBytes());
	    	out.flush();
	    	out.close();

	    	/*
	    	PrintWriter out = response.getWriter();
			out.print(generateHtmlPage(servletCtx, url, mimeType,
					readonly, reloadTopWindow));
		    out.close();
		    */

	    } else if ( defaultView
	    		|| mimeType.equalsIgnoreCase("application/pdf") ) {

		    // Para pdf se descarga como un adjunto, ya que si se intenta abrir
	    	// dentro de ActiveX de Internet Explorer no lo hace correctamente
	    	// en algunos equipos, sera por temas de configuracion

            ServletOutputStream out = response.getOutputStream();
	    	response.setContentType(mimeType);
	    	response.setHeader("Pragma", "public");
	    	response.setHeader("Cache-Control", "max-age=0");
            response.setHeader("Content-Transfer-Encoding", "binary");
        	response.setHeader("Content-Disposition", new StringBuffer()
        			.append("attachment; filename=\"").append(id).append(".")
        			.append(MimetypeMapping.getExtension(mimeType))
        			.append("\"").toString());
        	IGenDocAPI genDocAPI = session.getAPI().getGenDocAPI();
			Object connectorSession = null;
			try {
				connectorSession = genDocAPI.createConnectorSession();
	        	response.setContentLength(genDocAPI.getDocumentSize(connectorSession, docref));
	        	genDocAPI.getDocument(connectorSession, docref, out);
			}finally {
				if (connectorSession != null) {
					genDocAPI.closeConnectorSession(connectorSession);
				}
	    	}



            out.flush();
            out.close();

	    } else {
	    	return false;
	    }

	    return true;
	}

	/**
	 * Generar la URL del servlet que se invoca para descargar el fichero.
	 *
	 * @param request Petición actual.
	 * @param servletName Nombre del servlet.
	 * @param ticket Ticket de la sesión.
	 * @param id Identificador de la entidad.
	 * @param mimeType Tipo MIME del documento.
	 * @return URL del servlet que se invoca para descargar el fichero.
	 */
	public static String generateURL(HttpServletRequest request,
							   		 String servletName,
							   		 String ticket,
							   		 String id,
							   		 String mimeType) {

		OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
		String organizationId = null;
		if (info != null)
			organizationId = info.getOrganizationId();



		StringBuffer url = new StringBuffer(request.getScheme());
		url.append("://");
		url.append(request.getServerName());
		url.append(":");
		url.append(Integer.toString(request.getServerPort()));
		url.append(request.getContextPath());
		url.append("/");
		url.append(servletName);
		if (organizationId != null){
			url.append("/");
			url.append(organizationId);
		}
		url.append("/");
		url.append(ticket);
		url.append("/");
		url.append(id);

		if (StringUtils.isNotBlank(mimeType)) {
			String ext = MimetypeMapping.getExtension(mimeType);
			if (!id.endsWith("." + ext)) {
				url.append(".").append(ext);
			}
		}

		return url.toString();
	}

	/**
	 * Generar la URL para la eliminación del fichero.
	 *
	 * @param request Petición actual.
	 * @param id Identificador de la entidad.
	 * @return URL del servlet que se invoca para descargar el fichero.
	 */
	public static String generateDocumentDeleteURL(HttpServletRequest request, String id) {

		return new StringBuffer(request.getScheme())
			.append("://")
			.append(request.getServerName())
			.append(":")
			.append(request.getServerPort())
			.append(request.getContextPath())
			.append("/deleteFile.do?file=")
			.append(id)
			.toString();
	}

	/**
	 * Generar el código HTML del applet que permite abrir documentos.
	 *
	 * @param servletCtx Contexto del servlet.
	 * @param context Contexto de la aplicación.
	 * @param url URL del servlet que se invoca para descargar el fichero.
	 * @param mimeType Tipo MIME
	 * @param readOnly Indica si el documento es de solo lectura.
	 * @param reloadTopWindow Indicador para recargar la página principal.
	 * @return Código HTML del applet que permite abrir el documento.
	 * @throws Exception Si se produce algún error.
	 */
	public static String generateHtmlPage(
			ServletContext servletCtx, String context, String url, String mimeType,
			String readOnly, boolean reloadTopWindow, boolean useOdtTemplates) throws Exception {

		// "application/vnd.oasis.opendocument.text".equalsIgnoreCase(mimeType)

        return new StringBuffer()
        	.append("<html>")
        	.append("<head>")
        	.append("<script language='javascript'>")
        	.append((useOdtTemplates == false) ? getOpenDocumentFunction(url, mimeType, readOnly, reloadTopWindow) : "")
        	.append(getLaunchAppletFunction(servletCtx, context, url, reloadTopWindow))
			.append("</script>")
        	.append("</head>")
        	.append("<body onload=\"")
        	.append((useOdtTemplates == false) ? "openDocument()" : "launchApplet()")
        	.append("\">")
        	.append("</body>")
        	.append("</html>")
        	.toString();
	}

	/**
	 * Generar el código HTML del applet que permite abrir documentos.
	 *
	 * @param servletCtx Contexto del servlet.
	 * @param request Petición.
	 * @param url URL del servlet que se invoca para descargar el fichero.
	 * @param mimeType Tipo MIME
	 * @param readOnly Indica si el documento es de solo lectura.
	 * @param reloadTopWindow Indicador para recargar la página principal.
	 * @return Código HTML del applet que permite abrir el documento.
	 * @throws Exception Si se produce algún error.
	 */
	public static String generateHtmlPage(
			ServletContext servletCtx, HttpServletRequest request, String url, String mimeType,
			String readOnly, boolean reloadTopWindow, boolean useOdtTemplates) throws Exception {

		// "application/vnd.oasis.opendocument.text".equalsIgnoreCase(mimeType)

        return new StringBuffer()
        	.append("<html>")
        	.append("<head>")
        	.append("<script language='javascript'>")
        	.append((useOdtTemplates == false) ? getOpenDocumentFunction(url, mimeType, readOnly, reloadTopWindow) : "")
        	.append(getLaunchAppletFunction(servletCtx, request, url, reloadTopWindow))
			.append("</script>")
        	.append("</head>")
        	.append("<body onload=\"")
        	.append((useOdtTemplates == false) ? "openDocument()" : "launchApplet()")
        	.append("\">")
        	.append("</body>")
        	.append("</html>")
        	.toString();
	}

	/**
	 * Generar el código HTML del applet que permite abrir documentos.
	 *
	 * @param servletCtx Contexto del servlet.
	 * @param context Contexto de la aplicación.
	 * @param url URL del servlet que se invoca para descargar el fichero.
	 * @param mimeType Tipo MIME
	 * @param readOnly Indica si el documento es de solo lectura.
	 * @param reloadTopWindow Indicador para recargar la página principal.
	 * @return Código HTML del applet que permite abrir el documento.
	 * @throws Exception Si se produce algún error.
	 */
	public static String generateHtmlCode(
			ServletContext servletCtx, String context, String url, String mimeType,
			String readOnly, boolean reloadTopWindow) throws Exception {

        return new StringBuffer()
        	.append("<script language='javascript'>")
        	.append(getOpenDocumentFunction(url, mimeType, readOnly, reloadTopWindow))
			.append(getLaunchAppletFunction(servletCtx, context, url, reloadTopWindow))
        	.append("openDocument();")
			.append("</script>")
        	.toString();
	}

	/**
	 * Generar el código HTML del applet que permite abrir documentos.
	 *
	 * @param servletCtx Contexto del servlet.
	 * @param request Petición.
	 * @param url URL del servlet que se invoca para descargar el fichero.
	 * @param mimeType Tipo MIME
	 * @param readOnly Indica si el documento es de solo lectura.
	 * @param reloadTopWindow Indicador para recargar la página principal.
	 * @return Código HTML del applet que permite abrir el documento.
	 * @throws Exception Si se produce algún error.
	 */
	public static String generateHtmlCode(
			ServletContext servletCtx, HttpServletRequest request, String url, String mimeType,
			String readOnly, boolean reloadTopWindow) throws Exception {

        return new StringBuffer()
        	.append("<script language='javascript'>")
        	.append(getOpenDocumentFunction(url, mimeType, readOnly, reloadTopWindow))
			.append(getLaunchAppletFunction(servletCtx, request, url, reloadTopWindow))
        	.append("openDocument();")
			.append("</script>")
        	.toString();
	}

	private static String getOpenDocumentFunction(String url, String mimeType,
			String readOnly, boolean reloadTopWindow) {

    	StringBuffer js = new StringBuffer("function openDocument() {");

	    // Comprobar si es un documento de MSOffice
	    if (isMSOfficeDocument(mimeType)) {

			js.append("if (window.ActiveXObject) {")
	    	.append("var url='")
	    	.append(url)
	    	.append("';")
			.append(getActiveXCode(mimeType, readOnly, reloadTopWindow))
			.append("} else {")
			.append("launchApplet();")
			.append("}");

	    } else {
			js.append("launchApplet();");
	    }

		js.append("}");

		return js.toString();
	}

	private static String getLaunchAppletFunction(ServletContext servletCtx,
			String context, String url, boolean reloadTopWindow)
			throws Exception {
		return new StringBuffer("function launchApplet() {")
	    	.append("try {")
	    	.append("var code = \"")
	    	.append(getAppletCode(servletCtx, context, url))
	    	.append("\";")
	    	.append("document.write(code);")
	    	.append(reloadTopWindow ? generateJSCodeReloadTopWindowWithTimeout(5000) : "")
	    	.append("} catch (e) {")
	    	.append("alert(e.name + ': ' + e.message);")
	    	.append("}")
	    	.append("}")
			.toString();
	}

	private static String getLaunchAppletFunction(ServletContext servletCtx,
			HttpServletRequest request, String url, boolean reloadTopWindow)
			throws Exception {
		return new StringBuffer("function launchApplet() {")
	    	.append("try {")
	    	.append("var code = \"")
	    	.append(getAppletCode(servletCtx, request, url))
	    	.append("\";")
	    	.append("document.write(code);")
	    	.append(reloadTopWindow ? generateJSCodeReloadTopWindowWithTimeout(5000) : "")
	    	.append("} catch (e) {")
	    	.append("alert(e.name + ': ' + e.message);")
	    	.append("}")
	    	.append("}")
			.toString();
	}

	private static boolean isMSOfficeDocument(String mimeType) {
	    return ( "application/msword".equalsIgnoreCase(mimeType)
	    		|| "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equalsIgnoreCase(mimeType)
	    		|| "application/excel".equalsIgnoreCase(mimeType)
	    		|| "application/x-excel".equalsIgnoreCase(mimeType)
				|| "application/x-msexcel".equalsIgnoreCase(mimeType)
				|| "application/vndms-excel".equalsIgnoreCase(mimeType)
				|| "application/vnd.ms-excel".equalsIgnoreCase(mimeType)
				|| "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equalsIgnoreCase(mimeType)
	    		|| "application/mspowerpoint".equalsIgnoreCase(mimeType)
				|| "application/powerpoint".equalsIgnoreCase(mimeType)
				|| "application/vndms-powerpoint".equalsIgnoreCase(mimeType)
				|| "application/vnd.ms-powerpoint".equalsIgnoreCase(mimeType)
				|| "application/x-mspowerpoint".equalsIgnoreCase(mimeType)
				|| "application/vnd.openxmlformats-officedocument.presentationml.presentation".equalsIgnoreCase(mimeType)
				|| "application/vnd.openxmlformats-officedocument.presentationml.slideshow".equalsIgnoreCase(mimeType));
	}

	protected static String getActiveXCode(String mimeType,
			String readonly, boolean reloadTopWindow) {

		StringBuffer code = new StringBuffer();

		code.append("try {");

	    if ("application/msword".equalsIgnoreCase(mimeType)
	    		|| "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equalsIgnoreCase(mimeType)) {

	    	// ActiveX para abrir MS Word
			code.append("var application = new ActiveXObject('Word.Application');");

			if ("true".equalsIgnoreCase(readonly)) {
				code.append("application.Documents.Open(url, false, true);");
			} else {
				code.append("application.Documents.Open(url);");
			}

			if (reloadTopWindow) {
				code.append(generateJSCodeReloadTopWindow());
			}

			code.append("application.Visible = true;")
				//.append("application.WindowState = 0;") //Normal
				.append("application.WindowState = 2;") //Minimize
				.append("application.WindowState = 1;"); //Maximize

	    } else if ("application/excel".equalsIgnoreCase(mimeType)
	    		|| "application/x-excel".equalsIgnoreCase(mimeType)
				|| "application/x-msexcel".equalsIgnoreCase(mimeType)
				|| "application/vndms-excel".equalsIgnoreCase(mimeType)
				|| "application/vnd.ms-excel".equalsIgnoreCase(mimeType)
				|| "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equalsIgnoreCase(mimeType)) {

	    	// ActiveX para abrir MS Excel
			code.append("var application = new ActiveXObject('Excel.Application');");

			if ("true".equalsIgnoreCase(readonly)) {
				code.append("application.Workbooks.Open(url, false, true);");
			} else {
				code.append("application.Workbooks.Open(url);");
			}

			if (reloadTopWindow) {
				code.append(generateJSCodeReloadTopWindow());
			}

			code.append("application.Visible = true;")
				//.append("application.WindowState = 0;") //Normal
				.append("application.WindowState = 2;") //Minimize
				.append("application.WindowState = 1;"); //Maximize

	    } else if ("application/mspowerpoint".equalsIgnoreCase(mimeType)
				|| "application/powerpoint".equalsIgnoreCase(mimeType)
				|| "application/vndms-powerpoint".equalsIgnoreCase(mimeType)
				|| "application/vnd.ms-powerpoint".equalsIgnoreCase(mimeType)
				|| "application/x-mspowerpoint".equalsIgnoreCase(mimeType)
				|| "application/vnd.openxmlformats-officedocument.presentationml.presentation".equalsIgnoreCase(mimeType)
				|| "application/vnd.openxmlformats-officedocument.presentationml.slideshow".equalsIgnoreCase(mimeType)) {

	    	// ActiveX para abrir MS Excel
			code.append("var application = new ActiveXObject('PowerPoint.Application');");

			if ("true".equalsIgnoreCase(readonly)) {
				code.append("application.Presentations.Open(url, false, true);");
			} else {
				code.append("application.Presentations.Open(url);");
			}

			if (reloadTopWindow) {
				code.append(generateJSCodeReloadTopWindow());
			}

			code.append("application.Visible = true;")
				.append("application.WindowState = 2;") //Minimize
				.append("application.WindowState = 1;"); //Maximize

		} else {

	    	// ActiveX para abrir Internet Explorer
			code.append("var application = new ActiveXObject('InternetExplorer.Application');")
				.append("application.AddressBar = false;")
				.append("application.StatusBar = false;")
				.append("application.MenuBar = false;")
				.append("application.ToolBar = false;")
				.append("application.navigate(url);")
				.append("application.Width = 800;")
				.append("application.Height = 600;");

			if (reloadTopWindow) {
				code.append(generateJSCodeReloadTopWindow());
			}

			code.append("application.Visible = true;");
		}

		code.append("} catch (e) {")
			.append("launchApplet();")
			.append("}");

		return code.toString();
	}

	/**
	 * Generar el código HTML del applet que permite imprimir documentos.
	 *
	 * @param servletCtx Contexto del servlet.
	 * @param id Identificador del documento
	 * @param url URL del servlet que se invoca para descargar el fichero.
	 * @param mimeType Tipo MIME
	 * @param callbackFunction Función JavaScript de retorno.
	 * @return Código HTML del applet que permite imprimir el documento.
	 */
	public static String generateHtmlCodeForPrinting(
				ServletContext servletCtx, String id, String url, String mimeType,
				String callbackFunction) {

        return new StringBuffer()
        	.append("<script language='javascript'>")
			.append("if (window.ActiveXObject) {")
			.append(getActiveXCodeForPrinting(id, url, mimeType, callbackFunction))
			.append("}")
			.append("</script>")
        	.toString();
	}

	protected static String getActiveXCodeForPrinting(String id, String url,
			String mimeType, String callbackFunction) {

		StringBuffer code = new StringBuffer();

		code.append("var application = null;");
		code.append("try {");

	    if ("application/msword".equalsIgnoreCase(mimeType)
	    		|| "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equalsIgnoreCase(mimeType)) {

	    	// ActiveX para abrir MS Word
			code.append("application = new ActiveXObject('Word.Application');");
			code.append("var openDocument = application.Documents.Open('")
				.append(url).append("', false, true);");
			code.append("openDocument.PrintOut(false);");
			code.append("openDocument.Close(0);");

	    } else if ("application/excel".equalsIgnoreCase(mimeType)
	    		|| "application/x-excel".equalsIgnoreCase(mimeType)
				|| "application/x-msexcel".equalsIgnoreCase(mimeType)
				|| "application/vndms-excel".equalsIgnoreCase(mimeType)
				|| "application/vnd.ms-excel".equalsIgnoreCase(mimeType)
				|| "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equalsIgnoreCase(mimeType)) {

	    	// ActiveX para abrir MS Excel
			code.append("application = new ActiveXObject('Excel.Application');");
			code.append("var openDocument = application.Workbooks.Open('")
				.append(url).append("', false, true);");
			code.append("openDocument.PrintOut(false);");
			code.append("openDocument.Close(0);");

	    } else if ("application/mspowerpoint".equalsIgnoreCase(mimeType)
				|| "application/powerpoint".equalsIgnoreCase(mimeType)
				|| "application/vndms-powerpoint".equalsIgnoreCase(mimeType)
				|| "application/vnd.ms-powerpoint".equalsIgnoreCase(mimeType)
				|| "application/x-mspowerpoint".equalsIgnoreCase(mimeType)
				|| "application/vnd.openxmlformats-officedocument.presentationml.presentation".equalsIgnoreCase(mimeType)
				|| "application/vnd.openxmlformats-officedocument.presentationml.slideshow".equalsIgnoreCase(mimeType)) {

	    	// ActiveX para abrir MS Powerpoint
			code.append("application = new ActiveXObject('PowerPoint.Application');");
			code.append("var openDocument = application.Presentations.Open('")
				.append(url).append("', false, true);");
			code.append("openDocument.PrintOut(false);");
			code.append("openDocument.Close(0);");

		} else {

	    	// ActiveX para abrir Internet Explorer
			code.append("application = new ActiveXObject('InternetExplorer.Application');")
				.append("application.navigate('" + url + "');")
				.append("application.visible = true;")
				.append("application.execWB(6, true);");
		}

	    if (StringUtils.isNotBlank(callbackFunction)) {
	    	code.append(callbackFunction + "(true, '" + id + "');");
	    }

		code.append("} catch (e) {");

		if (StringUtils.isNotBlank(callbackFunction)) {
	    	code.append(callbackFunction + "(false, '" + id + "', e);");
	    }

		code.append("} finally {")
			.append("if (application != null) { application.Quit(); }")
			.append("}");

		return code.toString();
	}

	/**
	 * Generar el código HTML del applet que permite abrir documentos.
	 * @param servletCtx Contexto del servlet.
	 * @param context Contexto de la aplicación.
	 * @param docUrl URL del documento.
	 * @return Código HTML del applet que permite abrir el documento.
	 * @throws Exception si ocurre algún error.
	 */
	protected static String getAppletCode(ServletContext servletCtx, String context,
			String docUrl) throws Exception  {

        String baseurl = StaticContext.getInstance().getBaseUrl(context);
        String ispacbase = (String) servletCtx.getAttribute("ispacbase");

        return "<object classid='clsid:8AD9C840-044E-11D1-B3E9-00805F499D93'"
        	+ " codebase='http://java.sun.com/products/plugin/autodl/jinstall-1_4-windows-i586.cab#Version=1,4,0,0'"
        	+ " width='1' height='1' NAME='AppLauncherApplet' alt='AppLauncherApplet'>"
        	+ "<param name='code' value='" + APP_LAUNCHER_APPLET_CODE + "'/>"
        	+ "<param name='archive' value='" + baseurl + "/" + ispacbase + "/" + APP_LAUNCHER_APPLET_ARCHIVE + "' />"
        	+ "<param name='name' value='AppLauncherApplet'/>"
        	+ "<param name='mayscript' value='true'/>"
        	+ "<param name='type' value='application/x-java-applet'/>"
        	+ "<param name='scriptable' value='true'/>"
        	+ "<param name='downloadBase' value='" + baseurl + "/" + ispacbase + "/applets" + "'/>"
        	+ "<param name='docUrl' value='" + docUrl + "'/>"
        	+ "<comment>"
        	+ "<embed type='application/x-java-applet'"
        	+ " code='" + APP_LAUNCHER_APPLET_CODE + "'"
        	+ " archive='" + baseurl + "/" + ispacbase + "/" + APP_LAUNCHER_APPLET_ARCHIVE + "'"
        	+ " alt='AppLauncherApplet'"
        	+ " name='AppLauncherApplet'"
        	+ " width='1'"
        	+ " height='1'"
        	+ " mayscript='true'"
        	+ " scriptable='true'"
        	+ " pluginspage='http://java.sun.com/products/plugin/index.html#download'"
        	+ " downloadBase='" + baseurl + "/" + ispacbase + "/applets" + "'"
        	+ " docUrl='" + docUrl + "'>"
        	+ "</embed>"
        	+ "</comment>"
        	+ "</object>";
	}

	/**
	 * Generar el código HTML del applet que permite abrir documentos.
	 * @param servletCtx Contexto del servlet.
	 * @param request Petición.
	 * @param docUrl URL del documento.
	 * @return Código HTML del applet que permite abrir el documento.
	 * @throws Exception si ocurre algún error.
	 */
	protected static String getAppletCode(ServletContext servletCtx, HttpServletRequest request,
			String docUrl) throws Exception  {

		String serverPort = String.valueOf(request.getServerPort());

		String proxyHttpPort = PortsConfig.getHttpFrontendPort();
		String proxyHttpsNoCertPort = PortsConfig.getHttpsFrontendPort();
		String proxyHttpsSiCertPort = PortsConfig.getHttpsFrontendAuthclientPort();

		if ((proxyHttpPort != null && proxyHttpPort.equals(serverPort)) ||
			(proxyHttpsNoCertPort != null && proxyHttpsNoCertPort.equals(serverPort)) ||
			(proxyHttpsSiCertPort != null && proxyHttpsSiCertPort.equals(serverPort))) {

			// Servidor Frontend por delante del Servidor de Aplicaciones (Ej: APACHE + TOMCAT)
			// HTTP
			serverPort = proxyHttpPort;
			// HTTPs sin certificado
			// serverPort = proxyHttpsNoCertPort;
		}
		else {
			// HTTP
			serverPort = PortsConfig.getHttpPort();
			// HTTPs sin certificado
			// serverPort = PortsConfig.getHttpsPort();
		}

		String appletDownload = "http://" + request.getServerName() + ":" + serverPort;

		// URL al documento por HTTP
		docUrl = docUrl.replaceFirst("https:", "http:");
		docUrl = docUrl.replaceFirst(String.valueOf(request.getServerPort()), serverPort);

        String baseurl = StaticContext.getInstance().getBaseUrl(request.getContextPath());
        String ispacbase = (String) servletCtx.getAttribute("ispacbase");

        return "<object classid='clsid:8AD9C840-044E-11D1-B3E9-00805F499D93'"
        	+ " codebase='http://java.sun.com/products/plugin/autodl/jinstall-1_4-windows-i586.cab#Version=1,4,0,0'"
        	+ " width='1' height='1' NAME='AppLauncherApplet' alt='AppLauncherApplet'>"
        	+ "<param name='code' value='" + APP_LAUNCHER_APPLET_CODE + "'/>"
        	+ "<param name='archive' value='" + appletDownload + baseurl + "/" + ispacbase + "/" + APP_LAUNCHER_APPLET_ARCHIVE + "' />"
        	+ "<param name='name' value='AppLauncherApplet'/>"
        	+ "<param name='mayscript' value='true'/>"
        	+ "<param name='type' value='application/x-java-applet'/>"
        	+ "<param name='scriptable' value='true'/>"
        	+ "<param name='codeBase' value='" + appletDownload + baseurl + "/" + ispacbase + "/../" + "'/>"
        	+ "<param name='downloadBase' value='" + appletDownload + baseurl + "/" + ispacbase + "/applets" + "'/>"
        	+ "<param name='docUrl' value='" + docUrl + "'/>"
        	+ "<comment>"
        	+ "<embed type='application/x-java-applet'"
        	+ " code='" + APP_LAUNCHER_APPLET_CODE + "'"
        	+ " archive='" + appletDownload + baseurl + "/" + ispacbase + "/" + APP_LAUNCHER_APPLET_ARCHIVE + "'"
        	+ " alt='AppLauncherApplet'"
        	+ " name='AppLauncherApplet'"
        	+ " width='1'"
        	+ " height='1'"
        	+ " mayscript='true'"
        	+ " scriptable='true'"
        	+ " pluginspage='http://java.sun.com/products/plugin/index.html#download'"
        	+ " codeBase='" + appletDownload + baseurl + "/" + ispacbase + "/../" + "'"
        	+ " downloadBase='" + appletDownload + baseurl + "/" + ispacbase + "/applets" + "'"
        	+ " docUrl='" + docUrl + "'>"
        	+ "</embed>"
        	+ "</comment>"
        	+ "</object>";
	}

	/**
	 * Generar el código JavaScript que permite recargar la página principal.
	 * @return Código JavaScript que permite recargar la página principal.
	 */
	protected static String generateJSCodeReloadTopWindow() {

    	return "top.ispac_needToConfirm = false; " + generateJSCodeLocationHref() + " top.window.location.href = newhref;";
	}

	/**
	 * Generar el código JavaScript que permite recargar la página principal con un retardo.
	 * @param timeout retardo al recargar la página principal.
	 * @return Código JavaScript que permite recargar la página principal.
	 */
	protected static String generateJSCodeReloadTopWindowWithTimeout(int timeout) {
		return "top.ispac_needToConfirm = false; " + generateJSCodeLocationHref() + " setTimeout(top.window.location.href = newhref, " + timeout + ");";
	}

	private static String generateJSCodeLocationHref() {

		StringBuffer javascript = new StringBuffer();

		// Refrescar el top manteniendo el bloque activo
		javascript.append("var activeBlock = top.window.document.getElementById(\"block\");")
				  .append("var newhref = top.window.location.href;")
				  .append("if (newhref.indexOf(\"?\") == -1) {")
				  .append("newhref = newhref + \"?reload=true\";")
				  .append("} else if (newhref.indexOf(\"reload\") == -1) {")
				  .append("newhref = newhref + \"&reload=true\";")
				  .append("}")
				  .append("if (activeBlock) {")
				  .append("if (newhref.indexOf(\"block\") == -1) {")
				  .append("newhref = newhref + \"&block=\" + activeBlock.value;")
				  .append("} else {")
				  .append("index = newhref.indexOf(\"block\");")
				  .append("newhref1 = newhref.substring(0,index);")
				  .append("newhref1 = newhref1 + \"block=\" + activeBlock.value;")
				  .append("newhref2 = newhref.substr(index);")
				  .append("index2 = newhref2.indexOf(\"&\");")
				  .append("if (index2 != -1) {")
				  .append("newhref1 = newhref1 + newhref2.substr(index2);")
				  .append("}")
				  .append("newhref = newhref1;")
				  .append("}")
				  .append("}");

		return javascript.toString();
	}

	/**
	 * Obtiene la información de un documento.
	 * @param session Sesión de tramitación.
	 * @param documentId Identificador del documento.
	 * @return Información del documento.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static IItem getDocumentItem(SessionAPI session, int documentId) throws ISPACException {

		IInvesflowAPI invesflowAPI = session.getAPI();
        IEntitiesAPI entityAPI = invesflowAPI.getEntitiesAPI();

        // Información del documento
        return entityAPI.getDocument(documentId);
	}

	/**
	 * Obtiene el tipo MIME de un documento.
	 * @param session Sesión de tramitación.
	 * @param documentId Identificador del documento.
	 * @return Tipo MIME
	 * @throws ISPACException si ocurre algún error.
	 */
	public static String getDocumentMimeType(SessionAPI session, int documentId) throws ISPACException {

		String mimeType = null;

        // Información del documento
        IItem docItem = getDocumentItem(session, documentId);

        if (docItem != null) {

        	// GUID del documento
        	String guid = docItem.getString("INFOPAG_RDE");
        	if (StringUtils.isBlank(guid)) {
        		guid = docItem.getString("INFOPAG");
        	}

        	// Obtener el tipo MIME a partir del GUID
        	mimeType = getDocumentMimeType(session, guid);
        }

		return mimeType;
	}

	/**
	 * Obtiene el tipo MIME de un documento.
	 * @param session Sesión de tramitación.
	 * @param guid GUID del documento.
	 * @return Tipo MIME
	 * @throws ISPACException si ocurre algún error.
	 */
	public static String getDocumentMimeType(SessionAPI session, String guid) throws ISPACException {

		String mimeType = null;

    	if (StringUtils.isNotBlank(guid)) {

    		IGenDocAPI genDocAPI = session.getAPI().getGenDocAPI();
			Object connectorSession = null;

			try {

				// Iniciar conexión con el conector documental
				connectorSession = genDocAPI.createConnectorSession();

	        	// Obtener el tipo MIME del documento
				mimeType = genDocAPI.getMimeType(connectorSession, guid);

			} finally {
				if (connectorSession != null) {
					genDocAPI.closeConnectorSession(connectorSession);
				}
			}
    	}

    	return mimeType;
	}

	/**
	 * Descarga un documento.
	 * @param session Sesión de tramitación.
	 * @param documentId Identificador del documento.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static void downloadDocument(HttpServletResponse response,
			SessionAPI session, int documentId) throws ISPACException {

        // Obtener la información del documento
        IItem documentItem = DocumentUtil.getDocumentItem(session, documentId);
        if (documentItem != null) {

        	// Obtener el nombre del documento
        	String name = documentItem.getString("NOMBRE");

        	// Obtener el GUID del documento
        	String guid = documentItem.getString("INFOPAG");

    		if (StringUtils.isNotBlank(guid)) {

    			IGenDocAPI genDocAPI = session.getAPI().getGenDocAPI();
    			Object connectorSession = null;

    			try {
    				connectorSession = genDocAPI.createConnectorSession();

    				// Tipo MIME del documento
    				String mimetype = genDocAPI.getMimeType(connectorSession, guid);

    				// Información de respuesta
    				ServletOutputStream out = response.getOutputStream();
    		    	response.setHeader("Pragma", "public");
    		    	response.setHeader("Cache-Control", "max-age=0");
    				response.setHeader("Content-Transfer-Encoding", "binary");
    				response.setContentType(mimetype);

    				String extension = documentItem.getString("EXTENSION");
    	            if (StringUtils.isBlank(extension)) {
    	            	response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
    	            } else {
    	            	response.setHeader("Content-Disposition", "inline; filename=\"" + name + "."
    	            			+ extension + "\"");
    	            }

    	            // Tamaño del fichero
    	            response.setContentLength(genDocAPI.getDocumentSize(connectorSession, guid));

    				// Descarga del documento
    				genDocAPI.getDocument(connectorSession, guid, out);
    			} catch (IOException e) {
    				throw new ISPACException(e);
    			} finally {
    				if (connectorSession != null) {
    					genDocAPI.closeConnectorSession(connectorSession);
    				}
    			}
    		}
        }
	}

}