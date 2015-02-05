package es.ieci.tecdoc.isicres.admin.core.beans;

public class SicresLibroInformeImpl {
	//Campo de la Tabla
	private int id;
	private int idReport;
	private int idArch;
	
	//Campos Axiliares
	private String nameLibro;
	private String idLibro;
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
	 * @return the idarch
	 */
	public int getIdArch() {
		return idArch;
	}
	/**
	 * @param idarch the idarch to set
	 */
	public void setIdArch(int idArch) {
		this.idArch = idArch;
	}
	/**
	 * @return the nameLibro
	 */
	public String getNameLibro() {
		return nameLibro;
	}
	/**
	 * @param nameLibro the nameLibro to set
	 */
	public void setNameLibro(String nameLibro) {
		this.nameLibro = nameLibro;
	}
	/**
	 * @return the idLibro
	 */
	public String getIdLibro() {
		return idLibro;
	}
	/**
	 * @param idLibro the idLibro to set
	 */
	public void setIdLibro(String idLibro) {
		this.idLibro = idLibro;
	}
	
	
}
