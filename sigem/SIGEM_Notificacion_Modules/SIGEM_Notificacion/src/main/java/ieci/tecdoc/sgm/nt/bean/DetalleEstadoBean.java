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
public final class DetalleEstadoBean  extends GenericoBean{
    
    private String error;
    private String motivoRechazo;
    private String estado;
    private java.util.Calendar fecha;
    
    /** Creates a new instance of DetalleEstadoBean */
    public DetalleEstadoBean() {
    }

    /** Devuelve el codigo de error
     * 
     * @return String codigo de error
     */
    public String getError() {
        return error;
    }

    /** Establece el codigo de error
     * 
     * @param error String error
     */
    public void setError(String error) {
        this.error = error;
    }

    /** Devuelve el motivo del rechazo 
     * 
     * @return String motivo
     */
    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    /** Establece el motivo de recharo
     * 
     * @param motivoRechazo String
     */
    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    /** Devuelve el estado
     * 
     * @return String
     */
    public String getEstado() {
        return estado;
    }

    /** Establece el estado
     * 
     * @param estado String
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /** Devuelve la fecha
     * 
     * @return Calendar
     */
    public java.util.Calendar getFecha() {
        return fecha;
    }
    
    /** Devuelve la fecha en formato String
     * 
     * @return String
     */
    public String getFechaToString() {
        return toString(fecha);
    }

    /** Establece la fecha
     * 
     * @param fecha Calendar
     */
    public void setFecha(java.util.Calendar fecha) {
        this.fecha = fecha;
    }

    
   
}
