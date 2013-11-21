package com.ieci.tecdoc.isicres.servlets.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.ISicresGenPerms;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.session.folder.FolderFileSession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;
/**
 * Servlet encargado de borrar los ficheros adjuntados a un registro
 *
 * @author IECISA
 *
 */

public class FileDelete extends HttpServlet {

	private static Logger _logger = Logger.getLogger(FileDelete.class);

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doWork(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doWork(req, resp);
	}


	private void doWork(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // identificador de libro.
        Integer bookId  = RequestUtils.parseRequestParameterAsInteger(req, "BookId");
        // Identificador de registro.
        Integer regId = RequestUtils.parseRequestParameterAsInteger(req, "RegId");
        // Identificador de documento.
        Integer docId = RequestUtils.parseRequestParameterAsInteger(req, "DocId");
        // Identificador de página.
        Integer pageId = RequestUtils.parseRequestParameterAsInteger(req, "PageId");
        // Obtenemos la sesión asociada al usuario.
        HttpSession session = req.getSession();
        // Texto del idioma. Ej: EU_
        String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
        // Número del idioma. Ej: 10
        Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
        // Obtenemos el objeto de configuración del servidor de aplicaciones y
        // el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(Keys.J_USECASECONF);

        PrintWriter writer = resp.getWriter();
        try {
		//Obtenemos la cache
		CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
				useCaseConf.getSessionID());
		//Obtenemos de la cache los permisos del usuario
		ISicresGenPerms permisos = (ISicresGenPerms) cacheBag.get(ServerKeys.GENPERMS_USER);

		//comprobamos si el usuario tiene permisos para realizar la operativa
		if(permisos.isCanDeleteDocuments()){
			FolderFileSession.deleteFileOfRegister(useCaseConf.getEntidadId(), regId, docId, pageId, bookId);
		}else{
			StringBuffer sb = new StringBuffer();
				sb.append("El usuario [").append(useCaseConf.getUserName())
						.append("] no posee permisos para borrar ficheros CanDeleteDocuments[")
						.append(permisos.isCanDeleteDocuments()).append("]");
				_logger.warn(sb.toString());

			throw new SecurityException(SecurityException.ERROR_USER_NOT_PERMS_NECESSARY);
		}

		} catch (Exception e) {
			StringBuffer sb = new StringBuffer();
			sb.append(
					"No se ha podido concluir el borrado del fichero con los datos: FILEID[")
					.append(pageId).append("] DOCID [").append(docId)
					.append("] REGID [").append(regId).append("]");

            _logger.fatal(sb.toString(), e);
			ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_ISICRESSRV_ERR_DELETE_OBJ));
        }

	}
}