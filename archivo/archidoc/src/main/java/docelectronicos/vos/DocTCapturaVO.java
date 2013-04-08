package docelectronicos.vos;

import java.util.Date;

/**
 * VO de la tarea de digitalizacion
 * 
 */
public class DocTCapturaVO {
	String id;
	int tipoObj;
	String idObj;
	String idUsrCaptura;
	String codRefObj;
	String tituloObj;
	int estado;
	Date fechaEstado;
	String observaciones;
	String motivoError;
	String nombreUsuario;
	String apellidosUsuario;

	public String getApellidosUsuario() {
		return apellidosUsuario;
	}

	public void setApellidosUsuario(String apellidosUsuario) {
		this.apellidosUsuario = apellidosUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getCodRefObj() {
		return codRefObj;
	}

	public void setCodRefObj(String codRefObj) {
		this.codRefObj = codRefObj;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Date getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdObj() {
		return idObj;
	}

	public void setIdObj(String idObj) {
		this.idObj = idObj;
	}

	public String getIdUsrCaptura() {
		return idUsrCaptura;
	}

	public void setIdUsrCaptura(String idUsrCaptura) {
		this.idUsrCaptura = idUsrCaptura;
	}

	public String getMotivoError() {
		return motivoError;
	}

	public void setMotivoError(String motivoError) {
		this.motivoError = motivoError;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getTipoObj() {
		return tipoObj;
	}

	public void setTipoObj(int tipoObj) {
		this.tipoObj = tipoObj;
	}

	public String getTituloObj() {
		return tituloObj;
	}

	public void setTituloObj(String tituloObj) {
		this.tituloObj = tituloObj;
	}

}
