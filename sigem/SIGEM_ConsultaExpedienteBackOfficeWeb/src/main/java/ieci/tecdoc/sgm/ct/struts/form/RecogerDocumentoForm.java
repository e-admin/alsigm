package ieci.tecdoc.sgm.ct.struts.form;

import ieci.tecdoc.sgm.core.services.consulta.FicherosHito;
import ieci.tecdoc.sgm.core.services.consulta.HitoExpediente;
import ieci.tecdoc.sgm.core.services.consulta.HitosExpediente;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class RecogerDocumentoForm extends ActionForm {
	
	private HitosExpediente hitos;
	private HitoExpediente hitoEstado;
	private Hashtable ficherosHashTable;
	private FicherosHito ficherosEstado;
	private FicherosHito ficherosHitoHist;
	
	/******** TRABAJAN CON HITOS COMO ARRAYLIST ********/
    
    public HitosExpediente getHitos() {
            return hitos;
    }
    
    public void setHitos(HitosExpediente hitos) {
            this.hitos = hitos;
    }
    
    public void reset(ActionMapping arg0, HttpServletRequest arg1) {
    	hitos = new HitosExpediente();
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
    
    public String getHitoEstadoGuid() {
    	return hitoEstado.getGuid();
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
    	this.ficherosHitoHist = (FicherosHito)getFicherosHashtable().get(guidHito);   	
    }
    
    public FicherosHito getFicheros(){
    	
    	return ficherosHitoHist;
    }
    
    public void setDocumentos(String guidHito, FicherosHito ficherosHito){
    	this.getFicherosHashtable().put(guidHito, ficherosHito); 
    }
    
    public FicherosHito getFicherosEstado(){
    	return ficherosEstado;
    }
    
    public void setFicherosEstado(FicherosHito ficherosEstado){
    	this.ficherosEstado = ficherosEstado;
    }
    
}
