package ieci.tecdoc.sgm.registro.action;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;
import ieci.tecdoc.sgm.registro.utils.Defs;
import ieci.tecdoc.sgm.registro.utils.Misc;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DescargarJustificanteAction extends RegistroWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		ServletOutputStream out = null;

		try {
			String sessionId = (String)session.getAttribute(Defs.SESION_ID);
			String registryNumber = (String)request.getParameter("numeroRegistro");

			ServicioRegistroTelematico oServicio = LocalizadorServicios.getServicioRegistroTelematico();

           	// Obtener el justificante a mostrar
			byte[] justificante = oServicio.obtenerJustificanteRegistro(sessionId, registryNumber, Misc.obtenerEntidad(request));

			response.setHeader("Pragma", "public");
	    	response.setHeader("Cache-Control", "max-age=0");
            response.setContentType("application/pdf");
            response.setHeader("Content-Transfer-Encoding", "binary");
           	response.setHeader("Content-Disposition", "attachment; filename=\"Justificante_" + registryNumber + ".pdf\"");

           	response.setContentLength(justificante.length);
           	out = response.getOutputStream();
            out.write(justificante, 0, justificante.length);

        } catch(Exception e) {

    		request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_OBTENER_JUSTIFICANTE);
	    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());

	    	return mapping.findForward("failure");
        } finally {
        	if (out != null) {
        		out.flush();
        		out.close();
        	}
        }

	    return null;
   	}
}
