/*
 * Created on 07-jun-2005
 *
 */
package ieci.tecdoc.mvc.control;

import ieci.tecdoc.mvc.dto.access.UserConnectedDTO;
import ieci.tecdoc.mvc.util.Constantes;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.RequestProcessor;

// ieci.tecdoc.mvc.control.AdmRequestProcessor
/**
 * @author Antonio María
 *
 */
public class AdmRequestProcessor extends RequestProcessor {
    private static Logger logger = Logger.getLogger(AdmRequestProcessor.class);
    
    
    protected boolean processPreprocess(HttpServletRequest request,
            HttpServletResponse response)  {
        
        boolean continueProcessing = true; // assume success
        HttpSession session = null;
        // ensure that the user’s session has not timed out
        boolean isRequestedSessionIdValid = request.isRequestedSessionIdValid();
        String contextPath = request.getContextPath();
        try {
	        if(!isRequestedSessionIdValid){ // user’s session has timed out, make them login again
	            response.sendRedirect(contextPath + "/acs/logout.jsp?logout");
	            continueProcessing = false;
	        }
	        else {
	            session = request.getSession();
		        String path = processPath(request, response);
		        if (!path.equals("/html/login") && !path.equals("/html/changePwd") && !path.equals("/html/loginCert") && !path.equals("/html/invalidated"))
		        {
		            UserConnectedDTO user = (UserConnectedDTO) session.getAttribute(Constantes.TOKEN_USER_CONNECTED);
		            if (user == null) {
		                response.sendRedirect(contextPath + "/acs/logout.jsp?logout=error");
		                continueProcessing = false;
		            }
		            else{
		                String userReg = "/user/";
		                String archiveReg = "/archive/";
		                String volReg = "/volume/";
		                if (path.startsWith(userReg)){
		                    if (! user.getHasAccessUser() ){
		                        response.sendRedirect(contextPath + "/acs/logout.jsp?logout=error");
				                continueProcessing = false;
		                    }
		                }
		                else if (path.startsWith(archiveReg))
		                {
		                    if (! user.getHasAccessSys() ){
		                        response.sendRedirect(contextPath + "/acs/logout.jsp?logout=error");
				                continueProcessing = false;
		                    }
		                }
		                else if (path.startsWith(volReg))
		                {
		                    if (! user.getHasAccessVol() ){
		                        response.sendRedirect(contextPath + "/acs/logout.jsp?logout=error");
				                continueProcessing = false;
		                    }
		                }
		            }
		        }
	        }
	        
	        // don’t do any testing if user is logging on
	        
        }catch (Exception e ){
            logger.error("Fallo en redirect");
            try {
                response.sendRedirect(contextPath + "/acs/logout.jsp?logout=error");
            } catch (IOException e1) {
            }
            continueProcessing = false;
        }
        return continueProcessing;
    }

}