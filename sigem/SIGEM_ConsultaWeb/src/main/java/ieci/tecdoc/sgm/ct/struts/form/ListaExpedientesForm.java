package ieci.tecdoc.sgm.ct.struts.form;

import ieci.tecdoc.sgm.core.services.consulta.Expedientes;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ListaExpedientesForm extends ActionForm {
	
	private Expedientes expedientes;
	private String URLNotificacion;
	private String URLAportacion;
    
    public Expedientes getExpedientes() {
            return expedientes;
    }
    
    public void setExpedientes(Expedientes expedientes) {
            this.expedientes = expedientes;
    }
    
    public void reset(ActionMapping arg0, HttpServletRequest arg1) {
            expedientes = new Expedientes();
    }
    
    public String getURLNotificacion() {
        return URLNotificacion;
    }
    
    public void setURLNotificacion(String URLNotificacion) {
        this.URLNotificacion = URLNotificacion;
    }
    
    public String getURLAportacion() {
        return URLAportacion;
    }
    
    public void setURLAportacion(String URLAportacion) {
        this.URLAportacion = URLAportacion;
    }

}
