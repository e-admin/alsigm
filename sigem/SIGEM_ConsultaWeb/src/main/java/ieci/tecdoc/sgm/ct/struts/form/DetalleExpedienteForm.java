package ieci.tecdoc.sgm.ct.struts.form;

import ieci.tecdoc.sgm.core.services.consulta.FicherosHito;
import ieci.tecdoc.sgm.core.services.consulta.HitoExpediente;
import ieci.tecdoc.sgm.core.services.consulta.Notificaciones;
import ieci.tecdoc.sgm.core.services.consulta.Pagos;
import ieci.tecdoc.sgm.core.services.consulta.Subsanaciones;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Clase ActionForm representativa del listado de DetalleExpediente.jsp 
 */
public class DetalleExpedienteForm extends ActionForm {
	
	private List hitos;
	private HitoExpediente hitoEstado;
	private Subsanaciones subsActual;
	private Notificaciones notifsActual;
	private Pagos pagosActual;
	private Hashtable ficherosHashTable;
	private List ficherosEstado;
	private List ficherosHitoHist;
	private List subsHistoricas;
	private List notifsHistoricas;
	private List pagosHistoricas;
	private String URLNotificacion;
	private String URLAportacion;
	private String URLPago;
	private String numeroExpediente;
	private String numeroRegistro;
	
	/******** TRABAJAN CON HITOS COMO ARRAYLIST ********/
   
    public List getHitos() {
            return hitos;
    }
    
    public void setHitos(List hitos) {
            this.hitos = hitos;
    }
    
    public void reset(ActionMapping arg0, HttpServletRequest arg1) {
    	hitos = new ArrayList();
    }
    
    /******** TRABAJAN CON EL HITO DE ESTADO ********/
    
    public HitoExpediente getHitoEstado() {
        return hitoEstado;
    }

    public void setHitoEstado(HitoExpediente hitoEstado) {
        this.hitoEstado = hitoEstado;
    }
    
    public void resetHitoEstado(ActionMapping arg0, HttpServletRequest arg1) {
    	hitoEstado = new HitoExpediente();
    }
    
    public String getHitoEstadoNumeroExpediente() {
    	return hitoEstado.getNumeroExpediente();
    }
    
    public void setHitoEstadoNumeroExpediente(String numeroExpediente) {
    	hitoEstado.setNumeroExpediente(numeroExpediente);
    }
    
    public String getHitoEstadoGuid() {
    	return hitoEstado.getGuid();
    }
    
    public void setHitoEstadoGuid(String guid) {
    	hitoEstado.setGuid(guid);
    }
    
    public String getHitoEstadoCodigo() {
    	return hitoEstado.getCodigo();
    }
    
    public String getHitoEstadoFecha() {
    	return hitoEstado.getFecha();
    }
    
    public String getHitoEstadoDescripcion() {
    	return hitoEstado.getDescripcion();
    }
    
    public String getHitoEstadoInformacionAuxiliar() {
    	return hitoEstado.getInformacionAuxiliar();
    }
    
    /******** TRABAJAN CON FICHEROS DE UN HITO ********/
    public Hashtable getFicherosHashtable() {
        return ficherosHashTable;
    }
    
    public void setFicherosHashtable(Hashtable ficherosHashTable) {
        this.ficherosHashTable = ficherosHashTable;
    }
    
    public void setFicheros (String guidHito){
    	this.ficherosHitoHist = ((FicherosHito)getFicherosHashtable().get(guidHito)).getFicheros();   	
    }
    
    public List getFicheros(){
    	
    	return ficherosHitoHist;
    }
    
    public void setDocumentos(String guidHito, FicherosHito ficherosHito){
    	this.getFicherosHashtable().put(guidHito, ficherosHito); 
    }
    
    public List getFicherosEstado(){
    	return ficherosEstado;
    }
    
    public void setFicherosEstado(List ficherosEstado){
    	this.ficherosEstado = ficherosEstado;
    }

    /*********** SUBSANACIONES ******************/
	public Subsanaciones getSubsActual() {
		return subsActual;
	}

	public void setSubsActual(Subsanaciones subsActual) {
		this.subsActual = subsActual;
	}

	public List getSubsHistoricas() {
		return subsHistoricas;
	}

	public void setSubsHistoricas(List subsHistoricas) {
		this.subsHistoricas = subsHistoricas;
	}
	
	/*************** NOTIFICACIONES *********************/
	public Notificaciones getNotifsActual() {
		return notifsActual;
	}

	public void setNotifsActual(Notificaciones notifsActual) {
		this.notifsActual = notifsActual;
	}

	public List getNotifsHistoricas() {
		return notifsHistoricas;
	}

	public void setNotifsHistoricas(List notifsHistoricas) {
		this.notifsHistoricas = notifsHistoricas;
	}
	
	/**************** PAGOS *********************/
	public Pagos getPagosActual() {
		return pagosActual;
	}

	public void setPagosActual(Pagos pagosActual) {
		this.pagosActual = pagosActual;
	}

	public List getPagosHistoricas() {
		return pagosHistoricas;
	}

	public void setPagosHistoricas(List pagosHistoricas) {
		this.pagosHistoricas = pagosHistoricas;
	}

	
	/**************** DATOS EXPEDIENTE ****************/
	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public String getURLAportacion() {
		return URLAportacion;
	}

	public void setURLAportacion(String aportacion) {
		URLAportacion = aportacion;
	}

	public String getURLNotificacion() {
		return URLNotificacion;
	}

	public void setURLNotificacion(String notificacion) {
		URLNotificacion = notificacion;
	}
	
	public String getURLPago() {
		return URLPago;
	}

	public void setURLPago(String pago) {
		URLPago = pago;
	}
	
	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
}
