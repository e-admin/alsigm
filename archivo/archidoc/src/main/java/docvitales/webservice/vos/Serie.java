package docvitales.webservice.vos;

import java.io.Serializable;

import common.view.POUtils;

import fondos.vos.SerieDocAntVO;

/**
 * Información de la serie que contiene documentos antecedentes de un tercero.
 */
public class Serie implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador de la serie. */
	private String id = null;

	/** Título de la serie. */
	private String titulo = null;

	/** Título del fondo. */
	private String fondo = null;

	/** Número de documentos antecedentes. */
	private int numUdocs = 0;

	/** Estado de la serie. */
	private int estado = 0;

	/** Descripción del estado de la serie. */
	private String descEstado = null;

	/**
	 * Constructor.
	 */
	public Serie() {
	}

	/**
	 * Constructor.
	 * 
	 * @param serie
	 *            Información de la serie.
	 */
	public Serie(SerieDocAntVO serie) {
		this();
		POUtils.copyVOProperties(this, serie);
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
