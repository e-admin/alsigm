/*
 * Buscar.java
 *
 * Created on 13 de junio de 2007, 12:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.struts.acciones;


import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaDocumentos;
import ieci.tecdoc.sgm.core.services.notificaciones.InfoDocumento;
import ieci.tecdoc.sgm.core.services.notificaciones.ServicioNotificaciones;
import ieci.tecdoc.sgm.nt.struts.util.Misc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
/**
 *
 * @author X73994NA
 */
public class Descargar extends NotificacionWebAction {
    
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       
        String idDoc = (String)request.getParameter("idFichero");
        String nameDoc = (String)request.getParameter("nameFichero");
        InfoDocumento doc = null;                    
  
        try{
            //doc = new ieci.tecdoc.sgm.rde.ContenedorDocumentosManager().retrieveDocument(null,idDoc);
        	ServicioNotificaciones oServicio = LocalizadorServicios.getServicioNotificaciones();
        	 
        	CriterioBusquedaDocumentos criterios = 
                    new CriterioBusquedaDocumentos();
            
            criterios.setCodigoNoti(idDoc);
            criterios.setCodigDoc(nameDoc );
            
            doc = oServicio.recuperaDocumento(criterios, Misc.obtenerEntidad(request));
        }catch (Exception e){
            request.setAttribute("error", "Fallo conseguir el fichero de base de datos:" + e.toString());
            return mapping.findForward("failure");
        }
       
        response.setHeader("Content-disposition","attachment; filename=" + nameDoc);
        response.setContentType(doc.getMimeType());
        OutputStream writer;
		try {
			writer = response.getOutputStream();
	        writer.write(doc.getContent());
	        writer.flush();
	        writer.close();			
		} catch (IOException e) {
            request.setAttribute("error", "Fallo al enviar el fichero al cliente:" + e.toString());
            return mapping.findForward("failure");
		}

        return null;
    }
}

  
