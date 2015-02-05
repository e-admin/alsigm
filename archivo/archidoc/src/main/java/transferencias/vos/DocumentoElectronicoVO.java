package transferencias.vos;

/**
 * Value Object con la informacion referente a cada uno de los documentos
 * electronicos que pueden formar parte de la lista de documentos que contiene
 * una unidad documental
 * 
 */
public class DocumentoElectronicoVO extends DocumentoVO {

	/** Repositorio del documento. */
	protected String repositorio = null;

	/** Localizador del documento. */
	protected String localizador = null;

	/** Extensión del fichero. */
	protected String extension = null;

	public DocumentoElectronicoVO() {
	}

	public DocumentoElectronicoVO(String nombre, String descripcion,
			String repositorio, String localizador, String extFich) {
		super(nombre, descripcion);
		this.repositorio = repositorio;
		this.localizador = localizador;
		this.extension = extFich;
	}

	public String getLocalizador() {
		return localizador;
	}

	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}

	public String getRepositorio() {
		return repositorio;
	}

	public void setRepositorio(String repositorio) {
		this.repositorio = repositorio;
	}

	public boolean isElectronico() {
		return true;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extensionFichero) {
		this.extension = extensionFichero;
	}
}