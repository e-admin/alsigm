package ieci.tecdoc.sgm.pe.ws.server;
/*
 * $Id: Tasa.java,v 1.1.4.1 2008/01/25 12:30:46 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Tasa extends RetornoServicio {

	private String codigo;
	private String idEntidadEmisora;
	private String nombre;
	private String tipo;
	private String modelo;
	private String datosEspecificos;
	private String captura;
	
	public String getCaptura() {
		return captura;
	}
	public void setCaptura(String captura) {
		this.captura = captura;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDatosEspecificos() {
		return datosEspecificos;
	}
	public void setDatosEspecificos(String datosEspecificos) {
		this.datosEspecificos = datosEspecificos;
	}
	public String getIdEntidadEmisora() {
		return idEntidadEmisora;
	}
	public void setIdEntidadEmisora(String idEntidadEmisora) {
		this.idEntidadEmisora = idEntidadEmisora;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	
}
