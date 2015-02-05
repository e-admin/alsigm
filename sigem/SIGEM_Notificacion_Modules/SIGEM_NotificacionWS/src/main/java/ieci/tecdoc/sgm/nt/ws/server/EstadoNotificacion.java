package ieci.tecdoc.sgm.nt.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class EstadoNotificacion extends RetornoServicio {

    private String error;
    private String motivoRechazo;
    private String estado;
    private String fecha;
    
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
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getMotivoRechazo() {
		return motivoRechazo;
	}
	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

    
    
}
