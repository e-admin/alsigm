package es.ieci.tecdoc.isicres.admin.core.beans;

public class SicresPerfilInformeImpl {

	// Campo de la Tabla
	private int id;
	private int idReport;
	private int idPerf;

	// Campos Axiliares
	private String namePerf;
	private String codePerf;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param idReport
	 *            the idReport to set
	 */
	public void setIdReport(int idReport) {
		this.idReport = idReport;
	}

	/**
	 * @return the idPerf
	 */
	public int getIdPerf() {
		return idPerf;
	}

	/**
	 * @param idPerf
	 *            the idPerf to set
	 */
	public void setIdPerf(int idPerf) {
		this.idPerf = idPerf;
	}

	/**
	 * @return the namePerf
	 */
	public String getNamePerf() {
		return namePerf;
	}

	/**
	 * @param namePerf
	 *            the namePerf to set
	 */
	public void setNamePerf(String namePerf) {
		this.namePerf = namePerf;
	}

	/**
	 * @return the codePerf
	 */
	public String getCodePerf() {
		return codePerf;
	}

	/**
	 * @param codePerf
	 *            the codePerf to set
	 */
	public void setCodePerf(String codePerf) {
		this.codePerf = codePerf;
	}

}
