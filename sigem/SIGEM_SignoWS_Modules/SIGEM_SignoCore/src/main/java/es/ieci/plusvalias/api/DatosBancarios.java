package es.ieci.plusvalias.api;

public class DatosBancarios {
	private String entidad;
	private String oficina;
	private String dc;
	private String numCuenta;
	private String nifTitular;
	private String nombreTitular;
	
	public String getEntidad() {
		return entidad;
	}
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	public String getOficina() {
		return oficina;
	}
	public void setOficina(String oficina) {
		this.oficina = oficina;
	}
	public String getDc() {
		return dc;
	}
	public void setDc(String dc) {
		this.dc = dc;
	}
	public String getNumCuenta() {
		return numCuenta;
	}
	public void setNumCuenta(String numCuenta) {
		this.numCuenta = numCuenta;
	}
	public String getNifTitular() {
		return nifTitular;
	}
	public void setNifTitular(String nifTitular) {
		this.nifTitular = nifTitular;
	}
	public String getNombreTitular() {
		return nombreTitular;
	}
	public void setNombreTitular(String nombreTitular) {
		this.nombreTitular = nombreTitular;
	}
}
