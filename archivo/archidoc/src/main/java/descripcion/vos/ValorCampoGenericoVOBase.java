package descripcion.vos;

import java.util.Date;

import common.vos.BaseVO;

import descripcion.model.xml.card.Valor;

/**
 * VO genérico para el almacenamiento de información de un campo.
 */
public abstract class ValorCampoGenericoVOBase extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// Tipos de Campos
	public static final short TIPO_INDEFINIDO = (short) 0;
	public static final short TIPO_TEXTO_CORTO = (short) 1;
	public static final short TIPO_TEXTO_LARGO = (short) 2;
	public static final short TIPO_FECHA = (short) 3;
	public static final short TIPO_NUMERICO = (short) 4;
	public static final short TIPO_REFERENCIA = (short) 5;

	// Tipos de Elementos (elemento en cuadro, udoc en relación o en división de
	// fracción de serie)
	public static final short TIPO_ELEMENTO_INDEFINIDO = (short) 0;
	public static final short TIPO_ELEMENTO_UDOC_EN_RE = (short) 1;
	public static final short TIPO_ELEMENTO_UDOC_EN_FS = (short) 2;

	// private String idElementoCF = null;
	private String idCampo = null;
	private int orden;
	// private Date timestamp = null;
	private String etiquetaXml;


	private int tipo = TIPO_INDEFINIDO;
	private int tipoElemento = TIPO_ELEMENTO_INDEFINIDO;

	protected ValorCampoGenericoVOBase(String idCampo, int orden,
			int tipoElemento) {
		super();
		this.idCampo = idCampo;
		this.orden = orden;
		this.tipoElemento = tipoElemento;
	}

	/**
	 * Constructor.
	 */
	public ValorCampoGenericoVOBase() {
		// this.timestamp = new Date();
	}

	/**
	 * Constructor.
	 */
	public ValorCampoGenericoVOBase(short tipo) {
		this();
		setTipo(tipo);
	}

	/**
	 * @return Returns the idCampo.
	 */
	public String getIdCampo() {
		return idCampo;
	}

	/**
	 * @param idCampo
	 *            The idCampo to set.
	 */
	public void setIdCampo(String idCampo) {
		this.idCampo = idCampo;
	}

	/**
	 * @return Returns the idElementoCF.
	 */
	/*
	 * public String getIdElementoCF() { return idElementoCF; }
	 */
	/**
	 * @param idElementoCF
	 *            The idElementoCF to set.
	 */
	/*
	 * public void setIdElementoCF(String idElementoCF) { this.idElementoCF =
	 * idElementoCF; }
	 */
	/**
	 * @return Returns the orden.
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            The orden to set.
	 */
	public void setOrden(int orden) {
		this.orden = orden;
	}

	/**
	 * @return Returns the timestamp.
	 */
	/*
	 * public Date getTimestamp() { return timestamp; }
	 */
	/**
	 * @param timestamp
	 *            The timestamp to set.
	 */
	/*
	 * public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
	 */
	/**
	 * @return Returns the tipo.
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            The tipo to set.
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getTipoElemento() {
		return tipoElemento;
	}

	public void setTipoElemento(int tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

	public int getTipoUDoc() {
		return tipoElemento;
	}

	public void setTipoUDoc(int tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

	public abstract void setValue(String value);

	/**
	 * Obtiene la información del Valor.
	 *
	 * @return Valor.
	 */
	public abstract Valor getValorInfo();

	public abstract Date getTimestamp();

	public void setEtiquetaXml(String etiquetaXml) {
		this.etiquetaXml = etiquetaXml;
	}

	public String getEtiquetaXml() {
		return etiquetaXml;
	}

}
