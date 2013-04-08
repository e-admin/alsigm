package ieci.tecdoc.sgm.nt.ws.server;

public class CriterioBusquedaNotificaciones {

    private String notificacion;
    private String fechaDesde;
    private String fechaHasta;
    private String tipo;
    private String estado;
    private String nif;
    private String conDetalle;
    
	public String getConDetalle() {
		return conDetalle;
	}
	public void setConDetalle(String conDetalle) {
		this.conDetalle = conDetalle;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public String getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(String fechaHasta) {
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

    
}
