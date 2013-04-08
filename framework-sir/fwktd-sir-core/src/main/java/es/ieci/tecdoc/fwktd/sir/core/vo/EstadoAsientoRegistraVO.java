package es.ieci.tecdoc.fwktd.sir.core.vo;

import java.util.Date;
import java.util.Map;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;

public class EstadoAsientoRegistraVO extends BaseValueObject {
	
	private static final long serialVersionUID = -4010540666604455571L;

	private EstadoAsientoRegistralEnum estado;
	
	private Date fechaEstado;
	
	private String nombreUsuario;
	
	private String contactoUsuario;
	
	private String observaciones;
	
	private Map <String, String> datosAdicionales;
	
	public Date getFechaEstado() {
		return fechaEstado;
		
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public String getContactoUsuario() {
		return contactoUsuario;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public void setContactoUsuario(String contactoUsuario) {
		this.contactoUsuario = contactoUsuario;
	}

	
	
	

	public EstadoAsientoRegistralEnum getEstado() {
		return estado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public Map<String, String> getDatosAdicionales() {
		return datosAdicionales;
	}

	public void setEstado(EstadoAsientoRegistralEnum estado) {
		this.estado = estado;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public void setDatosAdicionales(Map<String, String> datosAdicionales) {
		this.datosAdicionales = datosAdicionales;
	}
	
	
	

}
