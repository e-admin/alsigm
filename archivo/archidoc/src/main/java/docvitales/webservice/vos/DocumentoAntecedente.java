package docvitales.webservice.vos;

import java.io.Serializable;

import common.view.POUtils;

import fondos.vos.DocumentoAntecedenteVO;

/**
 * Información de un documento antecedente de un tercero.
 */
public class DocumentoAntecedente implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador de la unidad documental. */
	private String id = null;

	/** Número de expediente. */
	private String numExp = null;

	/** Código del sistema productor. */
	private String codSistProductor = null;

	/** Tipo documental. */
	private String tipoDocumental = null;

	/** Título de la unidad documental. */
	private String titulo = null;

	/** Título de la serie. */
	private String serie = null;

	/** Título del fondo. */
	private String fondo = null;

	/** Código de referencia de la unidad documental. */
	private String codReferencia = null;

	/** Estado de la unidad documental. */
	private int estado = 0;

	/** Descripción del Estado de la unidad documental. */
	private String descEstado = null;

	/** Fecha de inicio de la unidad documental. */
	private String fechaInicial = null;

	/** Fecha de fin de la unidad documental. */
	private String fechaFinal = null;

	/**
	 * Constructor.
	 */
	public DocumentoAntecedente() {
	}

	/**
	 * Constructor.
	 * 
	 * @param doc
	 *            Información del documento antecedente.
	 */
	public DocumentoAntecedente(DocumentoAntecedenteVO doc) {
		this();
		POUtils.copyVOProperties(this, doc);
	}

	public String getCodReferencia() {
		return codReferencia;
	}

	public void setCodReferencia(String codReferencia) {
		this.codReferencia = codReferencia;
	}

	public String getCodSistProductor() {
		return codSistProductor;
	}

	public void setCodSistProductor(String codSistProductor) {
		this.codSistProductor = codSistProductor;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public String getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getFondo() {
		return fondo;
	}

	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumExp() {
		return numExp;
	}

	public void setNumExp(String numExp) {
		this.numExp = numExp;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getTipoDocumental() {
		return tipoDocumental;
	}

	public void setTipoDocumental(String tipoDocumental) {
		this.tipoDocumental = tipoDocumental;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescEstado() {
		return descEstado;
	}

	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}
}
