package ieci.tecdoc.sgm.migration.mgr.dto;

public class RegisterOriginalDto {

	/**
	 * Número de registro
	 */
	private String numeroRegistro;
	
	/**
	 * Fecha del registro
	 */
	private String fechaRegistro;
	
	/**
	 * Tipo de registro
	 */
	private String tipoRegistro;
	
	/**
	 * Oficina de registro
	 */
	private String oficinaRegistro;
	
	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getTipoRegistro() {
		return tipoRegistro;
	}
	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}
	public String getOficinaRegistro() {
		return oficinaRegistro;
	}
	public void setOficinaRegistro(String oficinaRegistro) {
		this.oficinaRegistro = oficinaRegistro;
	}
	
	
}
