package ieci.tecdoc.sgm.nt.conectores.types;

public class ResultadoAltaNotificacion {
	private String idNotificacionSistemaExterno;
	private String errorSistemaExterno;
	
	public String getIdNotificacionSistemaExterno() {
		return idNotificacionSistemaExterno;
	}
	public void setIdNotificacionSistemaExterno(String idNotificacionSistemaExterno) {
		this.idNotificacionSistemaExterno = idNotificacionSistemaExterno;
	}
	public String getErrorSistemaExterno() {
		return errorSistemaExterno;
	}
	public void setErrorSistemaExterno(String errorSistemaExterno) {
		this.errorSistemaExterno = errorSistemaExterno;
	}
}
