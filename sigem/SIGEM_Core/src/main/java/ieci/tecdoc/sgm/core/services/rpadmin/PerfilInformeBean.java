package ieci.tecdoc.sgm.core.services.rpadmin;

public class PerfilInformeBean {
	private int id;
	private int idPerfil;
	private int idReport;
	private String codigoPerfil;
	private String nombrePerfil;
	
	
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
	 * @return the idPerfil
	 */
	public int getIdPerfil() {
		return idPerfil;
	}


	/**
	 * @param idPerfil the idPerfil to set
	 */
	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}


	/**
	 * @return the idReport
	 */
	public int getIdReport() {
		return idReport;
	}


	/**
	 * @param idReport the idReport to set
	 */
	public void setIdReport(int idReport) {
		this.idReport = idReport;
	}


	/**
	 * @return the codigoPerfil
	 */
	public String getCodigoPerfil() {
		return codigoPerfil;
	}


	/**
	 * @param codigoPerfil the codigoPerfil to set
	 */
	public void setCodigoPerfil(String codigoPerfil) {
		this.codigoPerfil = codigoPerfil;
	}


	/**
	 * @return the nombrePerfil
	 */
	public String getNombrePerfil() {
		return nombrePerfil;
	}


	/**
	 * @param nombrePerfil the nombrePerfil to set
	 */
	public void setNombrePerfil(String nombrePerfil) {
		this.nombrePerfil = nombrePerfil;
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
