/*
 * Created on 12-jun-2006
 *
 */
package com.ieci.tecdoc.isicres.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.TransformerFactory;

import org.apache.log4j.Logger;
import org.dom4j.Document;

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.distribution.DistributionUseCase;

/**
 * @author 79426599
 *
 */
public class ValidateSearchDist extends HttpServlet {
	
    private static Logger _logger = Logger.getLogger(ValidateSearchDist.class);

    private DistributionUseCase distributionUseCase = null;

    private TransformerFactory factory = null;
    
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
        distributionUseCase = new DistributionUseCase();
        factory = TransformerFactory.newInstance();
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
        response.setContentType("text/xml; charset=UTF-8");

        //Tipo de distribucion (entrada o salida).
        Integer lnTypeDistr = RequestUtils.parseRequestParameterAsInteger(request, "TypeDist");
        // Criterio de busqueda para datos de distribución
        String distWhere = RequestUtils.parseRequestParameterAsString(request,"distWhere");
        // Criterio de busqueda para datos de registro
        String regWhere = RequestUtils.parseRequestParameterAsString(request,"regWhere");
        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Texto del idioma. Ej: EU_
        String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
        // Número del idioma. Ej: 10
        Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
        // Obtenemos el objeto de configuración del servidor de aplicaciones y
        // el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(Keys.J_USECASECONF);
        
        PrintWriter writer = response.getWriter();
        try {
            writer.write(distributionUseCase.getValidateDistributionSearch(useCaseConf, lnTypeDistr, distWhere, regWhere));
            writer.flush();
            writer.close();

        } catch (Exception e) {
            //writer.write(Keys.SEARCH_DTR);
            _logger.fatal("Error en validación de busqueda de distribucion", e);
            ResponseUtils.generateJavaScriptLogValidateSearchDist(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                    Keys.I18N_ISICRESSRV_ERR_CREATING_DTR_DIALOG));
        }

    }
    private String parseValues (String value)
    {
    	String result = value;
       if (value == null) return "";
       
       char c;
       
       result = result.replaceAll("%","%25");
       result = result.replaceAll("<","%3C"); 
       result = result.replaceAll(">","%3E");
       return result;
    }
	
}
