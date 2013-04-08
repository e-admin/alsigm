package com.ieci.tecdoc.isicres.servlets.core;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ieci.tecdoc.isicres.desktopweb.utils.IdiomaUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;

/**
 * Clase de ayuda para la jsp __index.jsp
 * @author Iecisa
 * @version $Revision$
 *
 */
public class IndexJspHelper {

	protected LoginJspHelper loginJspHelper= new LoginJspHelper();

public void doWork(HttpServletRequest request, HttpServletResponse response) throws IOException{

		IdiomaUtils idiomaUtils = IdiomaUtils.getInstance();
		String numIdioma="";
		String idioma="";

		 String idiomaAuth = RequestUtils.parseRequestParameterAsString(request, "idioma");

		// Prioridad 1: Idioma establecido desde la autenticación
		if (idiomaAuth != null && idiomaAuth != "") {
			String language = idiomaAuth.substring(0, idiomaAuth.indexOf("_"));
			if (language != null) {
	    		if (language.equalsIgnoreCase("ca")) {
	    			idioma = idiomaUtils.getMicrosoftLocales()[2][0].toString();
	    			numIdioma = idiomaUtils.getMicrosoftLocales()[2][2].toString();
	    		} else if (language.equalsIgnoreCase("eu")) {
	    			idioma = idiomaUtils.getMicrosoftLocales()[1][0].toString();
	    			numIdioma = idiomaUtils.getMicrosoftLocales()[1][2].toString();
	    		} else if (language.equalsIgnoreCase("gl")) {
	    			idioma = idiomaUtils.getMicrosoftLocales()[3][0].toString();
	    			numIdioma = idiomaUtils.getMicrosoftLocales()[3][2].toString();
	    		} else {
	    			idioma = idiomaUtils.getMicrosoftLocales()[0][0].toString();
	    			numIdioma = idiomaUtils.getMicrosoftLocales()[0][2].toString();
	    		}
			}
		}else{
			 numIdioma=idiomaUtils.getNumIdioma(request).toString();
			idioma=idiomaUtils.getIdioma(request);
		}


		if (loginJspHelper.isOpenDistribucion(request)){
			response.sendRedirect("default.jsp?Idioma=" + idioma + "&numIdioma=" + numIdioma);
		} else {

			String folderId=loginJspHelper.getFolderId(request).toString();
			String distId=loginJspHelper.getDistId(request).toString();
			String archiveId=loginJspHelper.getArchiveId(request).toString();
			response.sendRedirect("default.jsp?Idioma=" + idioma + "&numIdioma=" + numIdioma + "&ArchiveId=" + archiveId  + "&FolderId=" + folderId  + "&DistId=" + distId);
		}
	}

}
