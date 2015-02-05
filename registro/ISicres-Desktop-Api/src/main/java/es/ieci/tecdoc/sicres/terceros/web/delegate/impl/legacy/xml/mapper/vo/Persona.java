package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 *
 * @author IECISA
 *
 */
public class Persona extends Entity {

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

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

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getSesionId() {
		return sesionId;
	}

	public void setSesionId(String sesionId) {
		this.sesionId = sesionId;
	}

	public String getBloqueada() {
		return bloqueada;
	}

	public void setBloqueada(String bloqueada) {
		this.bloqueada = bloqueada;
	}

	public Domicilios getDomicilios() {
		return domicilios;
	}

	public void setDomicilios(Domicilios domicilios) {
		this.domicilios = domicilios;
	}

	public EDirecciones getEdirecciones() {
		return edirecciones;
	}

	public void setEdirecciones(EDirecciones edirecciones) {
		this.edirecciones = edirecciones;
	}

	protected String sesionId;
	protected String bloqueada;
	protected String tipo;
	protected String nombre;
	protected String apellido1;
	protected String apellido2;
	protected String tipoDoc;
	protected String nif;
	protected Domicilios domicilios;
	protected EDirecciones edirecciones;
	private static final long serialVersionUID = 846606914042316691L;

}
