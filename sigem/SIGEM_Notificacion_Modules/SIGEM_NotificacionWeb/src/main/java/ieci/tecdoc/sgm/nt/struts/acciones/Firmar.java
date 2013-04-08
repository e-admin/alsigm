/*
 * Firmar.java
 *
 * Created on 15 de junio de 2007, 16:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.struts.acciones;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.notificaciones.ServicioNotificaciones;
import ieci.tecdoc.sgm.nt.database.NotificacionesDatos;
import ieci.tecdoc.sgm.nt.struts.util.Misc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author X73994NA
 */
public class Firmar  extends NotificacionWebAction {
    
    /** Creates a new instance of Firmar */
    public Firmar() {
    }
    
    public ActionForward executeAction(ActionMapping mapping, 
                                    ActionForm form,
                                    HttpServletRequest request, 
                                    HttpServletResponse response){
        String target = new String("success");
        try {
        	ServicioNotificaciones oServicio = LocalizadorServicios.getServicioNotificaciones();

        	oServicio.actualizaEstado(
        			(String)request.getParameter("expediente"),
        			new Integer(3),
        			new java.util.Date(), 
        			(String)request.getParameter("cnifDest"),
        			(String)request.getParameter("notiid"),
        			Misc.obtenerEntidad(request));	
        } catch (Exception ex){
        	request.setAttribute("error", "Fallo al actualizar el estado." + ex.toString());
        }   
        return (mapping.findForward(target));
    }
}
