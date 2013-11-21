package com.ieci.tecdoc.isicres.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.helper.ConfigExtensionFileHelper;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.session.folder.FolderFileSession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;

/**
 * @author jcebrien
 *
 *	Servlet encargado de comprobar la forma de visualizacion de los documentos
 *
  */
public class GetPage extends HttpServlet implements Keys{
	private static Logger _logger = Logger.getLogger(GetPage.class);
    private static final String INTERROGACION = "?";
    private static final String BARRAINC = "/";
    private static final String DOSPUNTOS = ":";
    private static final String SERVLETCALL = "FileDownload";

	public void init() throws ServletException {
        super.init();
    }

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
        doWork(request, response);
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
        doWork(request, response);
	}
    private void doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("text/html; charset=UTF-8");

        // URL.
        String topURL = RequestUtils.parseRequestParameterAsString(request,"topURL");
        // Nombre del fichero.
        String fileName = RequestUtils.parseRequestParameterAsString(request,"FileName");
        // identificador de libro.
        Integer bookId  = RequestUtils.parseRequestParameterAsInteger(request, "BookId");
        // Identificador de registro.
        Integer regId = RequestUtils.parseRequestParameterAsInteger(request, "RegId");
        // Identificador de documento.
        Integer docId = RequestUtils.parseRequestParameterAsInteger(request, "DocId");
        // Identificador de página.
        Integer pageId = RequestUtils.parseRequestParameterAsInteger(request, "PageId");
        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);

        PrintWriter writer = response.getWriter();

        try{
        	//obtiene la url que se ha de invocar para visualizar el fichero
        	String URL = getUrl(request, topURL);
        	//obtenemos la extension del fichero
			String fileExt = FolderFileSession.getExtensionFile(fileName,
					bookId.toString(), regId.intValue(), pageId.intValue(),
					useCaseConf.getEntidadId());

			//si la extension retornada es distinto de null
        	if(fileExt!=null){
        		fileExt = fileExt.toUpperCase();
        	}

        	_logger.info(URL);
        	_logger.info(fileExt);

    	    writer.write("<HTML>");
    	    writer.write("<head>");
    	    writer.write("<link REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"./css/global.css\">");
    	    writer.write("<link REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"./css/font.css\">");
    	    writer.write("<script type=\"text/javascript\" language=\"javascript\" src=\"./scripts/global.js\"></script>");
    	    writer.write("<script type=\"text/javascript\" language=\"javascript\" src=\"./scripts/genmsg.js\"></script>");
    	    writer.write("<script type=\"text/javascript\" language=\"javascript\" src=\"./scripts/frmdata.js\"></script>");
    	    writer.write("<script type=\"text/javascript\" language=\"javascript\" src=\"./scripts/frmint.js\"></script>");
    	    writer.write("<script type=\"text/javascript\" language=\"javascript\" src=\"./scripts/frmt.js\"></script>");
    	    writer.write("</head>");

            //obtenemos la forma para visualizar el fichero
	    getViewFile(writer, URL, fileExt);

    	    writer.write("</BODY>");
    	    writer.write("</HTML>");
        } catch (Exception e) {
            _logger.fatal("Error al obtener el fichero", e);
            writer.write(Keys.ACTIVATE_TREE_2);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_FDRQRY_OBJ));
        }

    }

    /**
     * Método que genera la forma para visualizar el fichero
     *
     * @param writer - Objeto en el que se escribe la respuesta del servlet
     * @param URL - URL
     * @param fileExt - Extensión del fichero a mostrar
     */
	private void getViewFile(PrintWriter writer, String URL, String fileExt) {

		// Variable que indica si se debe mostrar el cuadro de dialogo de
		// abrir/guardar el fichero
		boolean showDialogSaveOpen = ConfigExtensionFileHelper
				.getShowDialogSaveOpenFile(fileExt);

		// si el fichero tiene una extension de tipo IMAGEN (JPG, JPEG, TIF,
		// TIFF O BMP) y no se ha indicado la descarga/visualizar por el
		// navegador (showDialogSaveOpen), se invoca al activeX de visualizacion
		// de ficheros
		if (!showDialogSaveOpen
				&& (fileExt.equals("JPG") || fileExt.equals("JPEG")
						|| fileExt.equals("TIF") || fileExt.equals("TIFF") || fileExt
						.equals("BMP"))) {
			writer.write("<BODY tabIndex=\"-1\" onload=\"ActivateTree();\" onunload=\"\" scroll=\"no\">");
			writer.write("<object classid=\"CLSID:24C6D59E-6D0D-11D4-8128-00C0F049167F\" width=\"100%\" height=\"100%\"");
			writer.write("codebase=\"plugins/ides.cab#version=2,2,0,0\" id=Control1>");
			writer.write("<PARAM name=\"FileName\" value=\"" + URL + "\">");
			writer.write("<PARAM name=\"FitMode\" value=0>");
			writer.write("<PARAM name=\"Enhancement\" value=2>");
			if (Configurator.getInstance().getPropertyBoolean(
					ConfigurationKeys.KEY_DESKTOP_IDOCIMGENABLESAVEAS)) {
				writer.write("<PARAM name=\"EnableSaveAs\" value=1>");
			}
			writer.write("</object>");
		} else {
			StringBuffer htmlBody = new StringBuffer();
			// si el documento tiene una extension diferente a tipo de IMAGEN,
			// se abre como una nueva ventana
			htmlBody.append("<BODY tabIndex=\"-1\" onload=\"ActivateTree();window.open('"
					+ URL + "','frmPage','location=no',true); ");

			// comprobamos si se va a mostrar el fichero o forzar la descarga
			if (showDialogSaveOpen) {
				// si se fuerza la descarga mostramos el frame con los datos del
				// registro, para que no se muestre una pantalla en blanco
				htmlBody.append("mostrarFrameFolderFormData()");
			}

			htmlBody.append(";\" scroll=\"no\">");

			writer.write(htmlBody.toString());
			writer.write("<iframe id=\"frmPage\" name=\"frmPage\" style=\"position:absolute;top:0px;left:0px;width:100%;height:100%\">");
			writer.write("</iframe>");

		}
	}

    /**
     * Funcion que compone la URL necesaria para visualizar el fichero
     *
     * @param request
     * @param topURL
     * @return String - URL necesaria para visualizacion del fichero (topURL/FileDownload?...)
     */
    private String getUrl(HttpServletRequest request, String topURL) {
    	String query = request.getQueryString();
    	String sProtocol=topURL.substring(0, topURL.indexOf(DOSPUNTOS));

        StringBuffer buffer = new StringBuffer();
        buffer.append(topURL);
        buffer.append(BARRAINC);
        buffer.append(SERVLETCALL);
        buffer.append(INTERROGACION);
        buffer.append(query);
        return escapeSpecialChars(buffer.toString());
    }

    private String escapeSpecialChars(String url){
    	String [] specialChars={"'"};
    	return url.replaceAll("'","\\\\'");
    }
}
