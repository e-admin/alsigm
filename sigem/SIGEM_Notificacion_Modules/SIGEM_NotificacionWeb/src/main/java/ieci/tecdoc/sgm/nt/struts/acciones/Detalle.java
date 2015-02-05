/*
 * Detalle.java
 *
 * Created on 14 de junio de 2007, 10:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.struts.acciones;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.notificaciones.Notificacion;
import ieci.tecdoc.sgm.core.services.notificaciones.ServicioNotificaciones;
import ieci.tecdoc.sgm.nt.struts.util.Misc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
/**
 *
 * @author X73994NA
 */
public class Detalle  extends NotificacionWebAction {
	
    public ActionForward executeAction(   ActionMapping mapping, 
                                    ActionForm form,
                                    HttpServletRequest request, 
                                    HttpServletResponse response) {
        
         String target = new String("success");
         
         String notiid = (String)request.getParameter("notiid");
         //String expediente = (String)request.getParameter("expediente");
         //String cnif = (String)request.getParameter("cnifDest");
         
         
         try {
         	ServicioNotificaciones oServicio = LocalizadorServicios.getServicioNotificaciones();
         	//CriterioBusquedaNotificaciones criteriosBusqueda = new CriterioBusquedaNotificaciones();
         	//criteriosBusqueda.setNumExp(expediente);
         	//criteriosBusqueda.setNif(cnif);
         	//Notificaciones notificaciones = oServicio.consultarNotificaciones(
         	//		criteriosBusqueda,
         	//		true,
         	//		Misc.obtenerEntidad(request));	

         	//Notificacion detalle = null;
         	//String descripcion = null;
         	//if(notificaciones.getNotificaciones().size()>0) {
         	//	detalle = (Notificacion)notificaciones.getNotificaciones().get(0);
//         		EstadoNotificacionBD estado = oServicio.obtenerEstadoBD(
//         												detalle.getEstado(),
//         												Misc.obtenerEntidad(request));
//        		descripcion = estado.getDescripcion();
         	//}
//            request.setAttribute("estado",descripcion);
         	
         	String CONST_PREFFIX_NOTIFY_CONNECTOR="ieci.tecdoc.sgm.nt.notificacion.conector.";
    		String CONST_PREFFIX_NOTIFY_STATE="estado.";
    		String CONST_MINUS="minus";
    		String CONST_SISNOT="sisnot";
         	
         	Notificacion detalle = oServicio.detalleNotificacionByNotiId(notiid, Misc.obtenerEntidad(request));
         	//traducir el estado obtenido de BD a la descripcion asociada.
         	String mensajeEstado=CONST_PREFFIX_NOTIFY_CONNECTOR+
         		(detalle.getSistemaId()==null?CONST_SISNOT:detalle.getSistemaId().toLowerCase())+
         		"."+CONST_PREFFIX_NOTIFY_STATE+(detalle.getEstado().toString());
         	
         	MessageResources messages=getResources(request);
         	detalle.setDescripcionEstado(messages.getMessage(request.getLocale(), mensajeEstado));
         	         	
         	//EstadoNotificacionBD estado = oServicio.obtenerEstadoBD(detalle.getEstado(),Misc.obtenerEntidad(request));
         	
         			
         	request.setAttribute("detalle",detalle);

         }catch (Exception e){
             target = new String("failure");
             request.setAttribute("error", "No se encontro el detalle " +e);
         }
         
        return (mapping.findForward(target));

    }
    
}
