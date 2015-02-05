/**
 *
 * @author jcebrien
 *
 */
package com.ieci.tecdoc.isicres.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import com.ieci.tecdoc.isicres.events.exception.EventException;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;
/**
 * Este servlet se invoca cuando se pulsa aceptar en la ventana modal de editar registro..
 *
 *
 * @author jcebrien
 *
 */
public class UpdateFields extends HttpServlet implements Keys{

	private static Logger _logger = Logger.getLogger(UpdateFields.class);
    private static final String GUIONBAJO = "_";
    private BookUseCase bookUseCase = null;

    public void init() throws ServletException {
        super.init();

        bookUseCase = new BookUseCase();
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

        // identificador de archivo de usuario.
        Integer archivePId  = RequestUtils.parseRequestParameterAsInteger(request, "ArchivePId ", new Integer(0));
        // Identificador de registro a copiar.
        Integer fldId = RequestUtils.parseRequestParameterAsInteger(request, "FldId", new Integer(0));
        // Código de unidad.
        String code = RequestUtils.parseRequestParameterAsString(request,"Code");
        // lista de registors seleccionados.
        String list = RequestUtils.parseRequestParameterAsString(request,"List");
        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Texto del idioma. Ej: EU_
        String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
        // Número del idioma. Ej: 10
        Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
        // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
        // Recuperamos el id de libro
        Integer bookID = (Integer) session.getAttribute(Keys.J_BOOK);
        String resp=null;
		PrintWriter writer = response.getWriter ();
        try{
	        List listIdsRegister = parseList(list);
	        if (_logger.isDebugEnabled()) {
	            for (Iterator it = listIdsRegister.iterator(); it.hasNext();) {
	                _logger.debug("===================> listIdsRegister " +  it.next());
	            }
	        }
	        if (!listIdsRegister.isEmpty()){
	        	// Lo único que se permite editar/actualizar de los registros desde la opción de menú
	        	// que va a este servlet es la unidad origen o destino
	        	resp = bookUseCase.updateFieldOrg(useCaseConf, bookID, fldId, code, listIdsRegister);
		        if (_logger.isDebugEnabled()) {
		        	_logger.debug("************* resp :" + resp);
		        }
	            writer .write(resp);
	        }
	        else{
	            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
	                    .getProperty(Keys.I18N_ISICRESSRV_ERR_UPDATEFOLDERS_OBJ));
	        }
        } catch (RemoteException e) {
            _logger.fatal("Error de comunicaciones", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_REMOTEEXCEPTION));
        } catch (ValidationException e) {
            _logger.fatal("Error de validacion", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
        } catch (BookException e) {
            writer .write("alert");
            _logger.fatal(e.getMessage(), e);
            ResponseUtils.generateJavaScriptError(writer , e);
        } catch (EventException eE) {
            writer .write("alert");
            _logger.fatal(eE.getMessage(), eE);
            ResponseUtils.generateJavaScriptError(writer , eE);
        } catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
            ResponseUtils.generateJavaScriptErrorSessionExpiredOpenFolder(writer , e, idioma, numIdioma, Boolean.FALSE);
        } catch (Exception e) {
            _logger.fatal("Error al actualizar", e);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_UPDATEFIELDS_OBJ));
        }


    }
    private List parseList(String listIds)  {
    	List result = new ArrayList();
        StringTokenizer tokens = new StringTokenizer(listIds, GUIONBAJO);
        String token = null;
        int i=0;
        while (tokens.hasMoreTokens()) {
	        if (_logger.isDebugEnabled()) {
	        	_logger.debug("i: " + i);
	        }
        	if (i == 0){
        		tokens.nextToken();
        	}
        	token = tokens.nextToken();
	        if (_logger.isDebugEnabled()) {
	        	_logger.debug("Ids : " + token);
	        }
            result.add(new Integer(token));
            i++;
        }
    	return result;
    }

}
