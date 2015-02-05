package ieci.tecdoc.sgm.tram.ws.server.dto;


/**
 * Información de un documento electrónico.
 */
public class DocElectronico extends DocFisico {

	/** Repositorio del documento. */
	protected String repositorio = null;

	/** Localizador del documento. */
	protected String localizador = null;

	/** Extensión del fichero. */
	protected String extension = null;

	/**
	 * Constructor.
	 */
	public DocElectronico() {
		super();
	}

	/**
	 * Obtiene el localizador del documento.
	 * @return Localizador del documento.
	 */
	public String getLocalizador() {
		return localizador;
	}

	/**
	 * Establece el localizador del documento.
	 * @param localizador Localizador del documento.
	 */
	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}

	/**
	 * Obtiene el repositorio del documento.
	 * @return Repositorio del documento.
	 */
	public String getRepositorio() {
		return repositorio;
	}

	/**
	 * Establece el tepositorio del documento.
	 * @param repositorio Repositorio del documento.
	 */
	public void setRepositorio(String repositorio) {
		this.repositorio = repositorio;
	}

	/**
	 * Obtiene la extensión del fichero.
	 * @return Extensión del fichero.
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * Establece la extensión del fichero.
	 * @param extensionFichero Extensión del fichero.
	 */
	public void setExtension(String extensionFichero) {
		this.extension = extensionFichero;
	}

}
