package fondos.vos;

import common.vos.BaseVO;

/**
 * Información de la serie que contiene documentos antecedentes de un tercero.
 */
public class SerieDocAntVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String titulo = null;
	private String fondo = null;
	private int numUdocs = 0;
	private int estado = 0;
	private String descEstado = null;

	/**
	 * Constructor.
	 */
	public SerieDocAntVO() {
		super();
	}

	/**
	 * @return Returns the descEstado.
	 */
	public String getDescEstado() {
		return descEstado;
	}

	/**
	 * @param descEstado
	 *            The descEstado to set.
	 */
	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}

	/**
	 * @return Returns the estado.
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            The estado to set.
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}

	/**
	 * @return Returns the idSerie.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param idSerie
	 *            The idSerie to set.
	 */
	public void setId(String idSerie) {
		this.id = idSerie;
	}

	/**
	 * @return Returns the numUdocs.
	 */
	public int getNumUdocs() {
		return numUdocs;
	}

	/**
	 * @param numUdocs
	 *            The numUdocs to set.
	 */
	public void setNumUdocs(int numUdocs) {
		this.numUdocs = numUdocs;
	}

	/**
	 * @return Returns the tituloFondo.
	 */
	public String getFondo() {
		return fondo;
	}

	/**
	 * @param tituloFondo
	 *            The tituloFondo to set.
	 */
	public void setFondo(String tituloFondo) {
		this.fondo = tituloFondo;
	}

	/**
	 * @return Returns the tituloSerie.
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param tituloSerie
	 *            The tituloSerie to set.
	 */
	public void setTitulo(String tituloSerie) {
		this.titulo = tituloSerie;
	}
}
