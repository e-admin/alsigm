package es.ieci.tecdoc.isicres.api.audit.business.vo.events;

public class IsicresAuditAbstractBasicRegistroUserEventVO extends IsicresAuditAbstractBasicUserEventVO{

	/**
	 *
	 */
	private static final long serialVersionUID = -8904177165387978563L;

	private String oficina;

	/**
	 * literal del libro actual sobre el que se esta trabajando
	 */
	private String libro;

	/**
	 * identificador del libro sobre el ques e esta trabajando
	 */
	private String idLibro;

	/**
	 * tipo de libro sobre el que se esta trabajando
	 */
	private String idTipoLibro;

	/**
	 * numero de registro sobre el que se esta trabajando
	 */
	protected String numRegistro;

	/**
	 * identificador del registro sobre el que se esta trabajando
	 */
	protected String idRegistro;

	public String getOficina() {
		return oficina;
	}

	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	public String getIdLibro() {
		return idLibro;
	}

	public String getNumRegistro() {
		return numRegistro;
	}

	public void setIdLibro(String idLibro) {
		this.idLibro = idLibro;
	}

	public void setNumRegistro(String numRegistro) {
		this.numRegistro = numRegistro;
	}

	public String getLibro() {
		return libro;
	}

	public void setLibro(String libro) {
		this.libro = libro;
	}

	public String getIdTipoLibro() {
		return idTipoLibro;
	}

	public void setIdTipoLibro(String idTipoLibro) {
		this.idTipoLibro = idTipoLibro;
	}

	public String getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
	}



}
