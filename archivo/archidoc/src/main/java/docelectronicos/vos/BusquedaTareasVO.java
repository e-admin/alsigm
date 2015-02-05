package docelectronicos.vos;

import java.util.Date;

import common.pagination.PageInfo;

public class BusquedaTareasVO {
	String fechaEstado = null;
	String condicion = null;
	String referencia = null;
	String titulo = null;
	String[] estados;
	Date fechaInicioEstado;
	Date fechaFinEstado;
	String nombreCompletoUsuarioCaptura = null;
	String idUsuarioCaptura;
	PageInfo pageInfo;

	public String getIdUsuarioCaptura() {
		return idUsuarioCaptura;
	}

	public void setIdUsuarioCaptura(String idUsuarioCaptura) {
		this.idUsuarioCaptura = idUsuarioCaptura;
	}

	public String getNombreCompletoUsuarioCaptura() {
		return nombreCompletoUsuarioCaptura;
	}

	public void setNombreCompletoUsuarioCaptura(String idUsuarioCaptura) {
		this.nombreCompletoUsuarioCaptura = idUsuarioCaptura;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public String[] getEstados() {
		return estados;
	}

	public void setEstados(String[] estados) {
		this.estados = estados;
	}

	public Date getFechaFinEstado() {
		return fechaFinEstado;
	}

	public void setFechaFinEstado(Date fechaFinEstado) {
		this.fechaFinEstado = fechaFinEstado;
	}

	public Date getFechaInicioEstado() {
		return fechaInicioEstado;
	}

	public void setFechaInicioEstado(Date fechaInicioEstado) {
		this.fechaInicioEstado = fechaInicioEstado;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public String getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(String fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
