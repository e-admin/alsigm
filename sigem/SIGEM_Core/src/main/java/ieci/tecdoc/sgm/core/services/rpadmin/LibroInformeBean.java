package ieci.tecdoc.sgm.core.services.rpadmin;

public class LibroInformeBean {
	private int id;
	private int idArch;
	private int idReport;
	private String codigoArchivo;
	private String nombreArchivo;
	
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
	 * @return the idArch
	 */
	public int getIdArch() {
		return idArch;
	}
	/**
	 * @param idArch the idArch to set
	 */
	public void setIdArch(int idArch) {
		this.idArch = idArch;
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
	 * @return the codigoArchivo
	 */
	public String getCodigoArchivo() {
		return codigoArchivo;
	}
	/**
	 * @param codigoArchivo the codigoArchivo to set
	 */
	public void setCodigoArchivo(String codigoArchivo) {
		this.codigoArchivo = codigoArchivo;
	}
	/**
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	/**
	 * @param nombreArchivo the nombreArchivo to set
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
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
