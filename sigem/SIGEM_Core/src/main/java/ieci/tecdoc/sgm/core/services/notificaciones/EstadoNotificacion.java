package ieci.tecdoc.sgm.core.services.notificaciones;

public class EstadoNotificacion {

    private String error;
    private String motivoRechazo;
    private String estado;
    private java.util.Calendar fecha;
    
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public java.util.Calendar getFecha() {
		return fecha;
	}
	public void setFecha(java.util.Calendar fecha) {
		this.fecha = fecha;
	}
	public String getMotivoRechazo() {
		return motivoRechazo;
	}
	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

    
}
