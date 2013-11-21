/**
 *
 * @author jcebrien
 *
 */
package com.ieci.tecdoc.isicres.servlets.core;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;

import es.ieci.tecdoc.fwktd.core.config.web.ContextUtil;
//import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;

/**
 * @author jcebrien
 *
 */
public class FileUploadScan extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(FileUploadScan.class);

	private static final String PUNTO_COMA = ";";

    private static final String BARRA = "|";

    private static final String DOSBARRA = "||";

    private static final String DOSPATH = "\\";

    private static final String PUNTO = ".";

    private static final String INTERROGACION = "?";

    private static final String ALMOHADILLA = "#";

    private static final String IGUAL = "=";

    private static final String AMPERSAN = "&";

    private static final String AMPERSANDOBLE = "&&";

    private static final String GUIONBAJO = "_";

    private static List filesInfo = new ArrayList();

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

    	String sessionId = null;           //Identificador de la session
    	String folderId = null;            //Identificador de la carpeta
    	DiskFileUpload fileUpload = null;  //Objeto con el API para procesar la subida de ficheros
    	Long maxUploadFileSize = null;     //Longitud maxima de los ficheros.
    	FileItem fileItem = null;          //Objeto para recorrer ficheros o parametros de la request
    	File newFile = null;               //Fichero destino donde se guardara el fichero subido
    	File newDir = null;                //Directorio destino donde se guardara el fichero subido
    	Properties parameters = null;      //Objeto usado para guardar los parametros de la request
    	int fileIndex = 0;                 //Indice usado para enumerar los ficheros subidos, se usa para componer el nombre del fichero.

    	//Obtenemos de la configuracion el limite de tamaño de los ficheros
    	maxUploadFileSize = new Long(Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_MAXUPLOADFILESIZE));

    	PrintWriter writer = response.getWriter(); //Usado solo para la respuesta del error en caso de producirse.

    	try {
    		if (FileUpload.isMultipartContent(request)) {
    			fileUpload = new DiskFileUpload();
			//Si el tamaño del fichero excede el limite configurado se lanzara una excepcion
    			fileUpload.setSizeMax(maxUploadFileSize);
    			//Obtener los "FileItem" de la request.
    			List fileItems = fileUpload.parseRequest(request);

			if (fileItems != null) {

	    			//Recorrer los fileItems primero para obtener solo los parametros (SessionId y FolderId)
    				parameters = new Properties();
	    			for (int i = 0; i < fileItems.size(); i++) {
	    				fileItem = (FileItem)fileItems.get(i);
	    				//Solo procesar si es un parametro
	    				if (fileItem.isFormField()) {
	    					parameters.setProperty(fileItem.getFieldName(), fileItem.getString());
	    				}
	    			}

	    			//Recoger los parametros
	    			sessionId = parameters.getProperty("SessionPId");
	    			folderId = parameters.getProperty("FolderId");

	    			//Guardar los ficheros
	    			for (int i = 0; i < fileItems.size(); i++) {
	    				fileItem = (FileItem)fileItems.get(i);

	    				//Solo procesar si es un fichero.
	    				if (!fileItem.isFormField()) {

						//obtenemos el codigo referente al fichero
						int indice = getCodigoFichero(fileItem, fileIndex);

	    					//Componer nombre del fichero
	                        String fileNameFis = getFileNameFis(sessionId, folderId.toString(), indice, fileItem.getName());
	                        fileIndex++;

	                        //Obtener directorio temporal para guardar el fichero
	                        if (Configurator.getInstance().getPropertyBoolean(ConfigurationKeys.KEY_DESKTOP_ISRELATIVE_TEMPORAL_DIR)) {
	                        	newDir = new File(ContextUtil.getRealPath(getServletContext(),
	                                    Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME)));
	                        } else {
	                            newDir = new File(Configurator.getInstance().getProperty(
	                            		ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME));
	                        }
	                        //Si el directorio temporal no existe, crearlo.
	                        if (!newDir.exists()) {
	                            newDir.mkdir();
	                        }

	                        //Obtener el fichero destino
	                        if (Configurator.getInstance().getPropertyBoolean(ConfigurationKeys.KEY_DESKTOP_ISRELATIVE_TEMPORAL_DIR)) {
	                              newFile = new File(ContextUtil.getRealPath(getServletContext(),
	                                      Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME)),
	                                      fileNameFis);
	                        } else {
	                            newFile = new File(
	                                    Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME),
	                                    fileNameFis);
	                        }
	                        //Escribir el fichero
	                        newFile.deleteOnExit();
	                        fileItem.write(newFile);
	    				}
	    			}
				}
    		}
    	} catch (FileUploadException e) {
            _logger.fatal("Error cargando ficheros", e);
            ResponseUtils.generateJavaScriptErrorForUpdateFolderNoBadCtrls(writer);
            ResponseUtils.generateJavaScriptErrorForSave(writer);
            String msg = MessageFormat.format(RBUtil.getInstance(request.getLocale()).getProperty(
                    Keys.I18N_EXCEPTION_MAXUPLOADFILESIZE), new String[] { maxUploadFileSize.toString() });
            ResponseUtils.generateJavaScriptLog(writer, msg);

        } catch (Exception e) {
            _logger.fatal("Error cargando ficheros", e);
            //ResponseUtils.generateJavaScriptLog(response, RBUtil.getInstance(useCaseConf.getLocale())
                    //.getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_UPLOAD_OBJ));
        }

    }

    /**
     * Método que obtiene el código del fichero a partir del nombre del fichero
     * @param fileItem - Información del fichero
     * @param fileIndex - Posición del fichero dentro del array de ficheros
     * @return codigo del fichero
     */
	private int getCodigoFichero(FileItem fileItem, int fileIndex) {

		int result;
		try {
			//Obtenemos el identificador del fichero a partir del nombre
			String codigoFichero = fileItem.getFieldName().substring("LI".length(),
					fileItem.getFieldName().length());
			// lo pasamos a un valor integer
			result = Integer.parseInt(codigoFichero);
		} catch (Exception e) {
			// Si se produce alguna excepción durante el proceso anterior, asignamos como
			// codigo del fichero la posición de este, dentro del array de
			// ficheros
			_logger.warn("No se ha podido obtener el identificador del documento: " + fileItem.getFieldName());
			result = fileIndex;
		}
		return result;
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
        //System.out.println("getFileName: " + buffer.toString());

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

}