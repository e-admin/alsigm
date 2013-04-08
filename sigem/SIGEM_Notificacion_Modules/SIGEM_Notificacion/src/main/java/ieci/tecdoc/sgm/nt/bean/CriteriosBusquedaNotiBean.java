/*
 * CriteriosBusquedaNotiBean.java
 *
 * Created on 24 de mayo de 2007, 15:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.bean;

/**
 *
 * Contenedor de atributos posibles para establecer criterios de busqueda de notificaciones
 */
public final class CriteriosBusquedaNotiBean  extends GenericoBean{
    
    /**
     * Creates a new instance of CriteriosBusquedaNotiBean
     */
    public CriteriosBusquedaNotiBean() {
    }
    
    private String notificacion;
    private java.util.Date fechaDesde;
    private java.util.Date fechaHasta;
    private String tipo;
    private Integer estado;
    private String nif;
    private String numExp;    
    

    /** Devuelve el codigo de notificacion
     * 
     * @return String codigo notificacion
     */
    public String getNotificacion() {
        return notificacion;
    }

    /** Establece el codigo de notificacion
     * 
     * @param notificacion String codigo de notificacion
     */
    public void setNotificacion(String notificacion) {
        this.notificacion = notificacion;
    }

    /** Devuelve el falor de la fecha inferior para la busqueda
     * 
     * @return Date fecha de busqueda inferior
     */
    public java.util.Date getFechaDesde() {
        return fechaDesde;
    }

    /** Establece el valor de la fecha inferior de busqueda
     * 
     * @param fechaDesde Date valor de fecha de busqueda inferior
     */
    public void setFechaDesde(java.util.Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    /** Devuelve el valor de la fecha tope para la busqueda
     * 
     * @return Date valor de fecha tope
     */
    public java.util.Date getFechaHasta() {
        return fechaHasta;
    }  

    /** Establece la fecha tope para la busqueda
     * 
     * @param fechaHasta Date fecha tope
     */
    public void setFechaHasta(java.util.Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    /** Devuelve el top de notificacion
     * 
     * @return String tipo
     */
    public String getTipo() {
        return tipo;
    }

    /** Establece el tipo de notificacion
     * 
     * @param tipo String tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /** Devuelve el estado de la notificacion
     * 
     * @return Integer estado
     */
    public Integer getEstado() {
        return estado;
    }

    /**Establece el estado de la notificacion
     * 
     * @param estado Integer estado
     */
    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    /** DEvueleve le valor del nif de la notificacion
     * 
     * @return String nif
     */
    public String getNif() {
        return nif;
    }

    /** Establece el valor del nif de la notificacion
     * 
     * @param nif String nif
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /** DEvueleve le valor del número de expediente de la notificacion
     * 
     * @return String numExp
     */
    public String getNumExp() {
        return numExp;
    }

    /** Establece el valor del número de expediente de la notificacion
     * 
     * @param numExp String Número de expediente
     */
    public void setNumExp(String numExp) {
        this.numExp = numExp;
    }
    
}
