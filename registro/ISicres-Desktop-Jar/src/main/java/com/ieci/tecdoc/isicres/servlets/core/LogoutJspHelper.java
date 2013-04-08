package com.ieci.tecdoc.isicres.servlets.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.IdiomaUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.security.SecurityUseCase;

public class LogoutJspHelper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(LogoutJspHelper.class);

	protected LogoutJspHelper(){
		super();
	}

	/**
	 * Metodo invocado desde logout.jsp para realizar el proceso de login
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	public  void doWork(ServletRequest req, ServletResponse res) throws IOException {

		HttpServletRequest request= (HttpServletRequest) req;
		HttpServletResponse response= (HttpServletResponse )res;
		HttpSession session = request.getSession();


		// Texto del idioma. Ej: EU_
		String idioma = getIdioma(request);
		if (idioma == null){
			idioma = IdiomaUtils.getInstance().getIdioma(request);
		}

		// Numero del idioma. Ej: 10
		Long numIdioma = getNumIdioma(request);
        if (numIdioma == null){
        	numIdioma = IdiomaUtils.getInstance().getNumIdioma(request);
        }

		try {
			if (logger.isDebugEnabled()){
				logger.debug("Se procede a hacer logout desde la jsp"+ request.getRequestURI());
			}

			doLogout(request.getSession());

		} catch (Exception e) {
			logger.error("Error haciendo logout:",e);
		}finally{
			// Se elimina la sesión en el servidor.
			session.invalidate();
		}


		success(request,response,idioma,numIdioma.toString());

	}

	public static  void doLogout(HttpSession session) throws SecurityException{
		UseCaseConf useCaseConf = (UseCaseConf) session	.getAttribute(Keys.J_USECASECONF);
		SecurityUseCase securityUseCase = new SecurityUseCase();

		// Salida de la session.
		if (useCaseConf != null) {
			securityUseCase.logout(useCaseConf);
		}
	}

	protected Long getNumIdioma(HttpServletRequest request){
		HttpSession session = request.getSession();
		Long result = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
		return result;
	}

	protected String getIdioma(HttpServletRequest request){
		HttpSession session = request.getSession();
		String result = (String) session.getAttribute(Keys.J_IDIOMA);
		return result;

	}
	protected boolean getGoToBlank(HttpServletRequest request, HttpServletResponse response){

		boolean result= RequestUtils.parseRequestParameterAsBoolean(request, "GotoBlank", Boolean.FALSE).booleanValue();
		return result;

	}

	protected  void success(HttpServletRequest request, HttpServletResponse response,String idioma, String numIdioma) throws IOException{

		PrintWriter out = response.getWriter();
		// Redirecciona a la página de entrada a la aplicación.
		if (!getGoToBlank(request,response)) {
			out.write("<script language=\"javascript\">");
			out.write("window.open(\"__index.jsp?Idioma=" + idioma
					+ "&numIdioma=" + numIdioma
					+ "\" , \"_top\",\"location=no\",true);");
			out.write("</script>");
		} else {
			out.write("<script language=\"javascript\">");
			out
					.write("window.open(\"about:blank\", \"_top\",\"location=no\",true);");
			out.write("</script>");
		}

	}

}
