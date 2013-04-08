/*
 * ResultadoBusquedaBean.java
 *
 * Created on 24 de mayo de 2007, 15:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.bean;

import java.util.Date;

/**
 *
 * @author Usuario
 */
public final class ResultadoBusquedaBean extends GenericoBean {
    
    private String identificadorNotificacion;
    private NotificacionBean detalle;
    
    
    /** Creates a new instance of ResultadoBusquedaBean */
    public ResultadoBusquedaBean() {
    }

    /**
     * Devuelve el valor del identificador de notificacion
     * @return String identificador de notificacion
     */
    public String getIdentificadorNotificacion() {
        return identificadorNotificacion;
    }

    /**
     * Establece el valor del identificador de notificacion
     * @param identificadorNotificacion String nuevo valor del identificador de notificacion
     */
    public void setIdentificadorNotificacion(String identificadorNotificacion) {
        this.identificadorNotificacion = identificadorNotificacion;
    }

    /**
     * Devuelve el detalle de la notificacion
     * @return NotificacionBean detalle
     */
    public NotificacionBean getDetalle() {
        return detalle;
    }

    /**
     * Establece el valor del detalle de la notificacion
     * @param detalle NotificacionBean detalle
     */
    public void setDetalle(NotificacionBean detalle) {
        this.detalle = detalle;
    }
   
}
