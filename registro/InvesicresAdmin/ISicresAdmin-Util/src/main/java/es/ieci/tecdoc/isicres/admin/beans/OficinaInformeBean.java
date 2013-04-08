package es.ieci.tecdoc.isicres.admin.beans;

public class OficinaInformeBean {
	private int id;
	private int idInforme;
	private int idOficina;
	private String codigoOficina;
	private String nombreOficina;
	
	// Auxiliar
	private int estado = Estados.SIN_CAMBIOS;
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the idInforme
	 */
	public int getIdInforme() {
		return idInforme;
	}
	/**
	 * @param idInforme the idInforme to set
	 */
	public void setIdInforme(int idInforme) {
		this.idInforme = idInforme;
	}
	/**
	 * @return the idOficina
	 */
	public int getIdOficina() {
		return idOficina;
	}
	/**
	 * @param idOficina the idOficina to set
	 */
	public void setIdOficina(int idOficina) {
		this.idOficina = idOficina;
	}
	/**
	 * @return the codigoOficina
	 */
	public String getCodigoOficina() {
		return codigoOficina;
	}
	/**
	 * @param codigoOficina the codigoOficina to set
	 */
	public void setCodigoOficina(String codigoOficina) {
		this.codigoOficina = codigoOficina;
	}
	/**
	 * @return the nombreOficina
	 */
	public String getNombreOficina() {
		return nombreOficina;
	}
	/**
	 * @param nombreOficina the nombreOficina to set
	 */
	public void setNombreOficina(String nombreOficina) {
		this.nombreOficina = nombreOficina;
	}
	/**
	 * @return the estado
	 */
	public int getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	
}
