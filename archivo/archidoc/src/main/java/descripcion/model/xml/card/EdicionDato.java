package descripcion.model.xml.card;

import descripcion.model.xml.definition.DefTipos;

/**
 * Clase abstracta que almacena la información de edición de un elememento de
 * tipo dato.
 */
public abstract class EdicionDato extends Edicion {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Tipo de dato. */
	private short tipo = DefTipos.TIPO_DATO_DESCONOCIDO;

	/** Indica si el campo es multivalor. */
	private boolean multivalor = false;

	/** Indica si el campo es obligatorio. */
	private boolean obligatorio = false;

	/** Valor inicial. */
	private ValorInicial valorInicial = null;

	/** Tiene padre y es editable */
	private boolean padreEditable = false;

	/** tipo de padre */
	private short tipoPadre = 0;

	/** Multilinea */
	private boolean multilinea = false;

	/**
	 * Constructor.
	 */
	public EdicionDato(short tipo) {
		super();
		setTipo(tipo);
	}

	/**
	 * Indica si el campo es multivalor.
	 * 
	 * @return Si el campo es multivalor.
	 */
	public boolean isMultivalor() {
		return multivalor;
	}

	/**
	 * Establece si el campo es multivalor.
	 * 
	 * @param multivalor
	 *            Si el campo es multivalor.
	 */
	public void setMultivalor(boolean multivalor) {
		this.multivalor = multivalor;
	}

	/**
	 * Indica si el campo es obligatorio.
	 * 
	 * @return Si el campo es obligatorio.
	 */
	public boolean isObligatorio() {
		return obligatorio;
	}

	/**
	 * Establece si el campo es obligatorio.
	 * 
	 * @param obligatorio
	 *            Si el campo es obligatorio.
	 */
	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	/**
	 * Obtiene el tipo de campo.
	 * 
	 * @return Tipo de campo.
	 */
	public short getTipo() {
		return tipo;
	}

	/**
	 * Establece el tipo de campo.
	 * 
	 * @param tipo
	 *            Tipo de campo.
	 */
	public void setTipo(short tipo) {
		this.tipo = tipo;
	}

	/**
	 * Obtiene el valor inicial.
	 * 
	 * @return Valor inicial.
	 */
	public ValorInicial getValorInicial() {
		return valorInicial;
	}

	/**
	 * Establece el valor inicial.
	 * 
	 * @param valorInicial
	 *            Valor inicial.
	 */
	public void setValorInicial(ValorInicial valorInicial) {
		this.valorInicial = valorInicial;
	}

	public boolean isPadreEditable() {
		return padreEditable;
	}

	public void setPadreEditable(boolean padreEditable) {
		this.padreEditable = padreEditable;
	}

	public short getTipoPadre() {
		return tipoPadre;
	}

	public void setTipoPadre(short tipoPadre) {
		this.tipoPadre = tipoPadre;
	}

	public boolean isMultilinea() {
		return multilinea;
	}

	public void setMultilinea(boolean multilinea) {
		this.multilinea = multilinea;
	}
}
