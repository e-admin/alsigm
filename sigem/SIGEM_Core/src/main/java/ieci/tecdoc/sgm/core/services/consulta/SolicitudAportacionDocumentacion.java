package ieci.tecdoc.sgm.core.services.consulta;

public class SolicitudAportacionDocumentacion {

	private String identificador;
	private String idDocumento;
	private String mensajeParaElCiudadano;
	private String identificadorHito;
	private String fecha;
	private String numeroExpediente;
	
	public String getNumeroExpediente() {
		return numeroExpediente;
	}
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getIdentificadorHito() {
		return identificadorHito;
	}
	public void setIdentificadorHito(String identificadorHito) {
		this.identificadorHito = identificadorHito;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}
	public String getMensajeParaElCiudadano() {
		return mensajeParaElCiudadano;
	}
	public void setMensajeParaElCiudadano(String mensajeParaElCiudadano) {
		this.mensajeParaElCiudadano = mensajeParaElCiudadano;
	}

}
