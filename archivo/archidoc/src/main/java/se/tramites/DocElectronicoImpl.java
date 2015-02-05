package se.tramites;

public class DocElectronicoImpl extends DocFisicoImpl implements DocElectronico {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Repositorio del documento. */
	protected String repositorio = null;

	/** Localizador del documento. */
	protected String localizador = null;

	/** Extensión del fichero. */
	protected String extension = null;

	/**
	 * Constructor.
	 */
	public DocElectronicoImpl() {
	}

	/**
	 * @return Returns the localizador.
	 */
	public String getLocalizador() {
		return localizador;
	}

	/**
	 * @param localizador
	 *            The localizador to set.
	 */
	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}

	/**
	 * @return Returns the repositorio.
	 */
	public String getRepositorio() {
		return repositorio;
	}

	/**
	 * @param repositorio
	 *            The repositorio to set.
	 */
	public void setRepositorio(String repositorio) {
		this.repositorio = repositorio;
	}

	/**
	 * @return Returns the extensionFichero.
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extensionFichero
	 *            The extensionFichero to set.
	 */
	public void setExtension(String extensionFichero) {
		this.extension = extensionFichero;
	}
}
