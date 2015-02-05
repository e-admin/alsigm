/**
 * 
 * @author jcebrien
 * 
 */
package com.ieci.tecdoc.isicres.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;


/**
 * Este servlet se invoca cuando se cierra la ventana de información de un registro.
 * 
 * @author jcebrien
 *  
 */
public class CloseFolder extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(CloseFolder.class);
    private static final String PUNTO_COMA = ";";
    private static final String BARRA = "|";

    private BookUseCase bookUseCase = null;

    public void init() throws ServletException {
        super.init();

        bookUseCase = new BookUseCase();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doWork(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doWork(request, response);
    }

    private void doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("text/html; charset=UTF-8");
        
        // identificador de archivo de usuario.
        Integer archiveId = RequestUtils.parseRequestParameterAsInteger(request, "ArchiveId");
        // Identificador de registro a copiar.
        Integer archivePId = RequestUtils.parseRequestParameterAsInteger(request, "ArchivePId");
        // Identificador de carpeta.
        int folderId = RequestUtils.parseRequestParameterAsint(request, "FolderId");
        // Identificador de la carpeta de consulta.
        Integer folderPId = RequestUtils.parseRequestParameterAsInteger(request, "FolderPId");
        // Identificador de la carpeta de consulta.
        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
        PrintWriter writer = response.getWriter(); 
        
        try {
        	if (folderId !=-1){
	            bookUseCase.closeFolder(useCaseConf, archiveId, folderId);
	            setStrCarpeta(session, archiveId, folderId);
        	}
            session.removeAttribute(Keys.J_REGISTER);
            session.removeAttribute(Keys.J_OPENFOLDER_FORM);
            session.removeAttribute(Keys.J_ROW);
        } catch (ValidationException e) {
            _logger.fatal("Error de validacion", e);
            ResponseUtils.ResponseErrorMsg(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
        } catch (BookException e) {
            _logger.fatal("Error en el libro", e);
            ResponseUtils.ResponseErrorMsg(writer, e.getMessage());
        } catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
            ResponseUtils.ResponseErrorMsg(writer, e.getMessage());
        } catch (Exception e) {
            _logger.fatal("Error al cerrar el registro", e);
            ResponseUtils.ResponseErrorMsg(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_FDRQRY_OBJ));
        }
     }

    // Este método quita archiveId y folderId de la cadena
    private void setStrCarpeta(HttpSession session, Integer archiveId, int folderId) {
        StringBuffer buffer = new StringBuffer();
        String archFolder = (String) session.getAttribute(Keys.J_STRCARPETA);
        if (archFolder != null) {
            StringTokenizer tokens = new StringTokenizer(archFolder, PUNTO_COMA);
            String aux = null;
            String auxArch = null;
            String auxFolder = null;
            while (tokens.hasMoreTokens()) {
                aux = tokens.nextToken();
                auxArch = aux.substring(0, aux.indexOf(BARRA));
                auxFolder = aux.substring(aux.indexOf(BARRA) + 1, aux.length());
                if (!auxArch.equals(archiveId.toString()) || !auxFolder.equals(Integer.toString(folderId))) {
                    buffer.append(aux);
                    buffer.append(PUNTO_COMA);
                }
            }
        }
        session.setAttribute(Keys.J_STRCARPETA, buffer.toString());
    }

}