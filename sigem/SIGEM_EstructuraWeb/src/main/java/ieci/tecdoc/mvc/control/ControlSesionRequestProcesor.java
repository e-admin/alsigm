package ieci.tecdoc.mvc.control;

import java.io.IOException;

import ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.RequestProcessor;

public class ControlSesionRequestProcesor extends RequestProcessor{
	
	private static Logger logger = Logger.getLogger(AdmRequestProcessor.class);

    protected boolean processPreprocess(HttpServletRequest request,
            HttpServletResponse response)  {
    	
        boolean continueProcessing = true; // assume success
        String contextPath = request.getContextPath();

        
		try {
			if (!AutenticacionAdministracion.autenticarEntidad(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA)) {
				String Url = AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA);
				request.setAttribute("urlRedireccion", Url);
				response.sendRedirect(Url);
				continueProcessing = false;
			}
		} catch (IOException e) {
            logger.error("Fallo en redirect");
            try {
                response.sendRedirect(contextPath + "/acs/redireccion.jsp");
            } catch (IOException e1) {
            }
            continueProcessing = false;
		}
    	
    	return continueProcessing;
    }
	
}
