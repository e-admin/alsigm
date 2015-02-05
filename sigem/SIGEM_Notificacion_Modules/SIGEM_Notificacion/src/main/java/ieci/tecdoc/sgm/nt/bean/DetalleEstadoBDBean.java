/*
 * DetalleEstadoBean.java
 *
 * Created on 24 de mayo de 2007, 15:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.bean;

/**
 *
 * Contenerdor de valores posibles obtenidos tras la busqueda del estado de una notificacion
 */
public final class DetalleEstadoBDBean  extends GenericoBean{
    private String id;
    
    private String idSisnot;
    
    private String descripcion;
    
    /** Creates a new instance of EstadoImpl */
    public DetalleEstadoBDBean() {
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
