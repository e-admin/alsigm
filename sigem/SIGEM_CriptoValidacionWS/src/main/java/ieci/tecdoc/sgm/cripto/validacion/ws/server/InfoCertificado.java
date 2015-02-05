package ieci.tecdoc.sgm.cripto.validacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class InfoCertificado extends RetornoServicio {

    private String nombre;
    private String nombreSinApellidos;
    private String apellido1;
    private String apellido2;
    private String nif;
    private String cif;
    private String razonSocial;
    private String numeroSerie;
    private String asunto;
    private String emisor;

	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombreSinApellidos() {
		return nombreSinApellidos;
	}
	public void setNombreSinApellidos(String nombreSinApellidos) {
		this.nombreSinApellidos = nombreSinApellidos;
	}
	public String getNumeroSerie() {
		return numeroSerie;
	}
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}



}
