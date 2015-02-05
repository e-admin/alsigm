package valoracion.vos;

import common.vos.BaseVO;

/**
 * Información de una serie y las unidades documentales seleccionables para la
 * valoración dictaminada de la serie.
 */
public class SerieSeleccionableVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String codigo = null;
	private String titulo = null;
	private String fondo = null;
	private int numeroUdocs = 0;

	/**
	 * Constructor.
	 */
	public SerieSeleccionableVO() {
		super();
	}

	/**
	 * @return Returns the codigo.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            The codigo to set.
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return Returns the fondo.
	 */
	public String getFondo() {
		return fondo;
	}

	/**
	 * @param fondo
	 *            The fondo to set.
	 */
	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the numeroUdocs.
	 */
	public int getNumeroUdocs() {
		return numeroUdocs;
	}

	/**
	 * @param numeroUdocs
	 *            The numeroUdocs to set.
	 */
	public void setNumeroUdocs(int numeroUdocs) {
		this.numeroUdocs = numeroUdocs;
	}

	/**
	 * @return Returns the titulo.
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo
	 *            The titulo to set.
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
