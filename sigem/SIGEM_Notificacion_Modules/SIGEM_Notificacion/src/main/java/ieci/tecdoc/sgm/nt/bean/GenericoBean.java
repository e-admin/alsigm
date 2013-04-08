/*
 * GenericoBean.java
 *
 * Created on 5 de junio de 2007, 17:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.bean;

/**
 *
 * @author Usuario
 */
public class GenericoBean {
   
    private Integer errorCode;
    private boolean error;
    private String informacionError;
    private String motivoError;
    
    /** Creates a new instance of GenericoBean */
    public GenericoBean() {
    }
    
    protected String toString (java.util.Date fecha_){
        java.text.Format formatter= new java.text.SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(fecha_);      
    }
    
    protected String toString (java.util.Calendar fecha_){        
        java.text.Format formatter= new java.text.SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(fecha_.getTime());      
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public boolean hayError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getInformacionError() {
        return informacionError;
    }

    public void setInformacionError(String informacionError) {
        this.informacionError = informacionError;
    }

    public String getMotivoError() {
        return motivoError;
    }

    public void setMotivoError(String motivoError) {
        this.motivoError = motivoError;
    }
 }
