package ieci.tecdoc.sgm.core.services.notificaciones;

public class CriterioBusquedaNotificaciones {

    private String notificacion;
    private java.util.Date fechaDesde;
    private java.util.Date fechaHasta;
    private String tipo;
    private Integer estado;
    private String nif;
    private String numExp;
    
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public java.util.Date getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(java.util.Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public java.util.Date getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(java.util.Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNotificacion() {
		return notificacion;
	}
	public void setNotificacion(String notificacion) {
		this.notificacion = notificacion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
