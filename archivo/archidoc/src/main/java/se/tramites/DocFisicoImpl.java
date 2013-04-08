package se.tramites;

public class DocFisicoImpl implements DocFisico {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String tipoDocumento = null;
	String asunto = null;

	public DocFisicoImpl() {
	}

	public DocFisicoImpl(String tipoDocumento, String asunto) {
		super();
		this.tipoDocumento = tipoDocumento;
		this.asunto = asunto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see se.tramites.DocFisico#getTipoDocumento()
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see se.tramites.DocFisico#getAsunto()
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * @param asunto
	 *            The asunto to set.
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	/**
	 * @param tipoDocumento
	 *            The tipoDocumento to set.
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
}
