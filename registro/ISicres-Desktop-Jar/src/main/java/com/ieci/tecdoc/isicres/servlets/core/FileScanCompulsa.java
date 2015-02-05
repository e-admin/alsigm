package com.ieci.tecdoc.isicres.servlets.core;

import java.io.File;
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
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;

import es.ieci.tecdoc.fwktd.core.config.web.ContextUtil;

public class FileScanCompulsa extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(FileScanCompulsa.class);


    private static final String DOSPATH = "\\";

    private static final String PUNTO = ".";

    private static final String GUIONBAJO = "_";

    //public static int order = 1;

    public void init() throws ServletException {
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doWork(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doWork(request, response);
    }

    private void doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        // Identificador de sesion.
        String sessionId = RequestUtils.parseRequestParameterAsString(request, "SessionPId");
        // Identificador de orden.
        String idFile = RequestUtils.parseRequestParameterAsString(request, "IdFile");
        // Se ha cancelado la compulsa cancel=1 si, cancel=0 no.
        Integer cancel = RequestUtils.parseRequestParameterAsInteger(request, "Cancel");
        // Pagina "blank" a cargar.
        String blankPage = RequestUtils.parseRequestParameterAsString(request, "blankPage");        
        // Nombre de fichero.        
        String fileName = RequestUtils.parseRequestParameterAsString(request, "fileName");
        // Identificador de carpeta.
        Integer folderId = RequestUtils.parseRequestParameterAsInteger(request, "FolderId");
        String orderFScan = idFile.substring(idFile.length() - 1, idFile.length());
        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession(false);

        PrintWriter writer = response.getWriter();
        try {
            Integer order = new Integer(orderFScan);
            String fileNameFis = getFileNameFis(sessionId, folderId.toString(), order.intValue(), fileName);
            String pathBlank = request.getContextPath() + "/" + blankPage;

            String pathBack = getBeginPath(request) + fileNameFis;

            if (cancel.intValue() == 1){
                File fNew = null;
                if (Configurator.getInstance().getPropertyBoolean(ConfigurationKeys.KEY_DESKTOP_ISRELATIVE_TEMPORAL_DIR)) {
                    fNew = new File(ContextUtil.getRealPath(session.getServletContext(),Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME)),
                            fileNameFis);
                } else {
                    fNew = new File(
                            Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME),
                            fileNameFis);
                }
                if (fNew.exists()) {
                	fNew.delete();
                }
            }
        	ResponseUtils.generateJavaScriptCompulsaBackUploadFile(writer, pathBack, pathBlank, cancel.intValue());
        } catch (Exception e) {
            _logger.fatal("Error cargando ficheros", e);
            //ResponseUtils.generateJavaScriptLog(response, RBUtil.getInstance(useCaseConf.getLocale())
                    //.getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_UPLOAD_OBJ));
        }

    }

    private String getFileNameFis(String sessionId, String folderId, int order, String name) {
    	String extension = getExtension(name);
        StringBuffer buffer = new StringBuffer();
        buffer.append(sessionId);
        buffer.append(GUIONBAJO);
        buffer.append(folderId);
        buffer.append(GUIONBAJO);
        buffer.append(order);
        if (!extension.equals("-")){
        	buffer.append(PUNTO);
        	buffer.append(extension);
        }

        return buffer.toString();
    }

    private String getExtension(String name) {
        String extension = name.substring(name.lastIndexOf(DOSPATH) + 1, name.length());
        if (extension.indexOf(PUNTO) == -1){
        	extension = "-";
        } else {
        	extension = extension.substring(extension.lastIndexOf(PUNTO) + 1, extension.length());
        }
        return extension;
    }

    private static String getBeginPath(HttpServletRequest request) throws Exception{
    	String beginPath = null;

    	if (Configurator.getInstance().getPropertyBoolean(ConfigurationKeys.KEY_DESKTOP_ISRELATIVE_TEMPORAL_DIR)) {
			beginPath = request.getContextPath() +
							Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME)	+ "/";
		} else {
			// Esta cadena va a ir como src de un iframe, por lo que no se puede poner directamente la ruta
			// física del filesystem en ella, sino la ruta dentro del servidor a un servlet que va a generar
			// el stream de bytes que representan al fichero
//			beginPath = Configurator.getInstance().getProperty(
//							ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME);
			String serverName = request.getServerName();     // hostname.com
		    int serverPort = request.getServerPort();        // 80
		    String contextPath = request.getContextPath();   // mywebapp
		    String protocol = request.getScheme(); 			 //http, https o ftp

		    if(request.isSecure() && !protocol.equalsIgnoreCase("https")){
		    	protocol = "https";
		    }

		    beginPath = protocol + "://" + serverName +":"+serverPort + contextPath + "/TempFileDownload?FileName=";

		    if (_logger.isDebugEnabled()) {
		    	_logger.debug("Compulsa beginPath: " + beginPath);
		    }
		}

		return beginPath;
    }

}