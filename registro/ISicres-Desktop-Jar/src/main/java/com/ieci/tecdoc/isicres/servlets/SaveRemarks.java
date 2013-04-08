/**
 *
 * @author tavares
 *
 */
package com.ieci.tecdoc.isicres.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.TransformerFactory;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.distribution.DistributionUseCase;

/**
 * @author jcebrien
 *
 */
public class SaveRemarks extends HttpServlet implements Keys {

    private static Logger _logger = Logger.getLogger(DtrAcept.class);

    private DistributionUseCase distributionUseCase = null;

    private TransformerFactory factory = null;

    public void init() throws ServletException {
        super.init();

        distributionUseCase = new DistributionUseCase();
        factory = TransformerFactory.newInstance();
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
        response.setContentType("text/xml; charset=UTF-8");

        //Id. de la distribución.
        Integer distId = RequestUtils.parseRequestParameterAsInteger(request, "Id", new Integer(0));
        //Mensaje
        String Remarks = RequestUtils.parseRequestParameterAsString(request,"Remarks");
        //Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Obtenemos el objeto de configuración del servidor de aplicaciones y
        // el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
        PrintWriter writer = response.getWriter();

        try {
            writer.write(distributionUseCase.saveRemarks(useCaseConf, distId.intValue(), Remarks));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            _logger.fatal("Error modificando comentario de distribución", e);
            ResponseUtils.generateJavaScriptLogValidateSearchDist(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                    Keys.I18N_ISICRESSRV_ERR_CREATING_DTR_DIALOG));
        }
    }
}
