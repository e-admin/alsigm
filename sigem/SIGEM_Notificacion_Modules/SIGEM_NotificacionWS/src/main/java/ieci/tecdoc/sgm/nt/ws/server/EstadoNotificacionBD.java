/*
 * EstadoImpl.java
 *
 * Created on 15 de junio de 2007, 15:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 *
 * @author X73994NA
 */
public class EstadoNotificacionBD extends RetornoServicio {
    
    private String id;
    
    private String idSisnot;
    
    private String descripcion;
    
    /** Creates a new instance of EstadoImpl */
    public EstadoNotificacionBD() {
    }
    
     /**
     * Devuelve el id del estado
     * @return String error
     */       
    public String getId (){
        return id;
    }
    
    /**
     * Establece el id del estado
     * @return String codigo de error.
     */       
    public void setId (String nuevoValor_){
        id = nuevoValor_;
    }
    
     /**
     * Devuelve la descripcion del sisnot
     * @return String error
     */       
    public String getIdSisnot (){
        return idSisnot;
        
    }
    
    /**
     * Establece la descripcion del sisnot
     * @return String codigo de error.
     */       
    public void setIdSisnot (String nuevoValor_){
        idSisnot = nuevoValor_;
    }
    
     /**
     * Devuelve la descripcion 
     * @return String error
     */       
    public String getDescripcion (){
        return descripcion;
    }
    
    /**
     * Establece el id del estado
     * @return String codigo de error.
     */       
    public void setDescripcion (String nuevoValor_){
        descripcion = nuevoValor_;
    }
    
    
}
