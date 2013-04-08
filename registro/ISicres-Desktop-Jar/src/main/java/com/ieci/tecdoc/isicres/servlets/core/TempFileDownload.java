package com.ieci.tecdoc.isicres.servlets.core;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrFile;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.IOUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;

/**
 * Este servlet se invoca para mostrar los ficheros temporales escaneados antes de compulsar cuando
 * la ruta temporal se configura como absoluta.
 *
 *
 * @author jcebrien
 *
 */
public class TempFileDownload extends HttpServlet {

	private static Logger _logger = Logger.getLogger(TempFileDownload.class);

    public void init() throws ServletException {
        super.init();
    }

    protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException,
            IOException {
        doWork(arg0, arg1);
    }

    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException,
            IOException {
        doWork(arg0, arg1);
    }

    private void doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        HttpSession session = request.getSession();
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(Keys.J_USECASECONF);
        OutputStream output = response.getOutputStream();

    	// Nombre del fichero.
        String fileName = RequestUtils.parseRequestParameterAsString(request,"FileName");
    	String fileExt = fileName.substring(fileName.indexOf(".")+1, fileName.length()).toLowerCase();

        try {
        	FlushFdrFile flushFdrFile = new FlushFdrFile();

    		flushFdrFile.setExtension(fileExt);
    		String beginPath = getBeginPath(request);
    		flushFdrFile.setFileNameFis(beginPath + File.separator + fileName);
    		flushFdrFile.loadFile();

    		InputStream inputStream = null;
    		if(flushFdrFile.getBuffer() == null){
    			inputStream = new BufferedInputStream(new FileInputStream(flushFdrFile.getFileNameFis()));
    		}else{
    			inputStream = new BufferedInputStream(new ByteArrayInputStream(flushFdrFile.getBuffer()));
    		}

    		IOUtils.copy(inputStream, output);

        } catch (Exception e) {
            _logger.fatal("Error en la descarga de fichero temporal", e);
            showError (RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_DOWNLOAD_OBJ), output);
        }
    }

    private String getBeginPath(HttpServletRequest request) throws Exception{
    	String beginPath = null;
		if (Configurator.getInstance().getPropertyBoolean(ConfigurationKeys.KEY_DESKTOP_ISRELATIVE_TEMPORAL_DIR)) {
			beginPath = request.getContextPath() +
							Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME);
		} else {
			beginPath = Configurator.getInstance().getProperty(
					ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME);

		}
		return beginPath;

    }

    private void showError (String message, OutputStream output){
        try {
			byte[] buffer = ResponseUtils.generateJavaScriptLogFileDownLoad(message).getBytes("UTF-8");
			IOUtils.copy(new BufferedInputStream(new ByteArrayInputStream(buffer)), output );
		} catch (IOException e) {
            _logger.fatal("Error en ShowError", e);
		}

    }
}