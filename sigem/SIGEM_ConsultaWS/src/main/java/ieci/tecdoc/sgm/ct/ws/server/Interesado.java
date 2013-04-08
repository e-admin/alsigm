package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: Interesado.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Interesado extends RetornoServicio {

	private String numeroExpediente;

	private String NIF;

	private String nombre;

	private String principal;

	private String informacionAuxiliar;
	
	private Expedientes expedientes;

	public Expedientes getExpedientes() {
		return expedientes;
	}

	public void setExpedientes(Expedientes expedientes) {
		this.expedientes = expedientes;
	}

	public String getInformacionAuxiliar() {
		return informacionAuxiliar;
	}

	public void setInformacionAuxiliar(String informacionAuxiliar) {
		this.informacionAuxiliar = informacionAuxiliar;
	}

	public String getNIF() {
		return NIF;
	}

	public void setNIF(String nif) {
		NIF = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

}
