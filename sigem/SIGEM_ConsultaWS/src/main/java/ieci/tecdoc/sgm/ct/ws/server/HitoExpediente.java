package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: HitoExpediente.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class HitoExpediente extends RetornoServicio {

	private String numeroExpediente;

	private String guid;

	private String codigo;

	private String fecha;

	private String descripcion;
	
	private String informacionAuxiliar;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getInformacionAuxiliar() {
		return informacionAuxiliar;
	}

	public void setInformacionAuxiliar(String informacionAuxiliar) {
		this.informacionAuxiliar = informacionAuxiliar;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	
}
