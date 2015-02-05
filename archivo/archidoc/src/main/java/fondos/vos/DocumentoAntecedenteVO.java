package fondos.vos;

import common.vos.BaseVO;

/**
 * Información de un documento antecedente de un tercero.
 */
public class DocumentoAntecedenteVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String numExp = null;
	private String codSistProductor = null;
	private String tipoDocumental = null;
	private String titulo = null;
	private String serie = null;
	private String fondo = null;
	private String codReferencia = null;
	private int estado = 0;
	private String descEstado = null;
	private String fechaInicial = null;
	private String fechaFinal = null;

	/**
	 * Constructor.
	 */
	public DocumentoAntecedenteVO() {
		super();
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
