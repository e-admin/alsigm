/*
 * IdentificadorBean.java
 *
 * Created on 24 de mayo de 2007, 15:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.bean;

/**
 *
 * Contenedor de atributos necesarios para identificar una notificacion
 */
public final class IdentificadorBean  extends GenericoBean{
    
     private String codigoDeNotificacion;
    
    /** Creates a new instance of IdentificadorBean */
    public IdentificadorBean() {
    }
    
   
    /**
     * Constructor que permite inicializar el objeto con un valor en todos sus campos
     * @param init_ String codigo de la notificaion
     * 
     */
    public IdentificadorBean(String init_) {
        codigoDeNotificacion = init_;
    }

    /**
     * Devuelve el valor del codigo de notificacion
     * @return valor del codigo de notificaicon
     */
    public String getCodigoDeNotificacion() {
        return codigoDeNotificacion;
    }

    /**
     * 
     * @param codigoDeNotificacion 
     */
    public void setCodigoDeNotificacion(String codigoDeNotificacion) {
        this.codigoDeNotificacion = codigoDeNotificacion;
    }
    
}
