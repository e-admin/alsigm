package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: Pago.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
 */
public class Pago {

	private String entidadEmisoraId;
	private String autoliquidacionId;
	private String importe;
	private String identificador;
	private String idDocumento;
	private String mensajeParaElCiudadano;
	private String identificadorHito;
	private String fecha;
	private String numeroExpediente;
	
	public String getAutoliquidacionId() {
		return autoliquidacionId;
	}
	public void setAutoliquidacionId(String autoliquidacionId) {
		this.autoliquidacionId = autoliquidacionId;
	}
	public String getEntidadEmisoraId() {
		return entidadEmisoraId;
	}
	public void setEntidadEmisoraId(String entidadEmisoraId) {
		this.entidadEmisoraId = entidadEmisoraId;
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
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getMensajeParaElCiudadano() {
		return mensajeParaElCiudadano;
	}
	public void setMensajeParaElCiudadano(String mensajeParaElCiudadano) {
		this.mensajeParaElCiudadano = mensajeParaElCiudadano;
	}
	public String getNumeroExpediente() {
		return numeroExpediente;
	}
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

}
