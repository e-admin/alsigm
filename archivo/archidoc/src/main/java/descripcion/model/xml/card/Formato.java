package descripcion.model.xml.card;

import org.apache.commons.lang.StringUtils;

import common.Constants;

import descripcion.model.xml.XmlElement;

/**
 * Clase que almacena la información de un formato de fecha.
 */
public class Formato extends XmlElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Literal del tipo de formato. */
	private String literal = null;

	/** Tipo de formato. */
	private String tipo = null;

	/** Separador de componentes de la fecha. */
	private String separador = null;

	/** Indica si es el valor por defecto. */
	private boolean valorPorDefecto = false;

	/**
	 * Constructor.
	 */
	public Formato() {
		this(null, null, null, false);
	}

	/**
	 * Constructor.
	 * 
	 * @param literal
	 *            Literal del tipo de formato.
	 * @param tipo
	 *            Tipo de formato.
	 * @param separador
	 *            Separador de componentes de la fecha.
	 */
	public Formato(String literal, String tipo, String separador) {
		this(literal, tipo, separador, false);
	}

	/**
	 * Constructor.
	 * 
	 * @param literal
	 *            Literal del tipo de formato.
	 * @param tipo
	 *            Tipo de formato.
	 * @param separador
	 *            Separador de componentes de la fecha.
	 * @param valorPorDefecto
	 *            Indica si es el valor por defecto.
	 */
	public Formato(String literal, String tipo, String separador,
			boolean valorPorDefecto) {
		super();

		setLiteral(literal);
		setTipo(tipo);
		setSeparador(separador);
		setValorPorDefecto(valorPorDefecto);
	}

	/**
	 * Indica si es el valor por defecto.
	 * 
	 * @return Si es el valor por defecto.
	 */
	public boolean isValorPorDefecto() {
		return valorPorDefecto;
	}

	/**
	 * Establece si es el valor por defecto.
	 * 
	 * @param valorPorDefecto
	 *            Si es el valor por defecto.
	 */
	public void setValorPorDefecto(boolean valorPorDefecto) {
		this.valorPorDefecto = valorPorDefecto;
	}

	/**
	 * Obtiene el separador de componentes de la fecha.
	 * 
	 * @return Separador de componentes de la fecha.
	 */
	public String getSeparador() {
		return separador;
	}

	/**
	 * Establece el separador de componentes de la fecha.
	 * 
	 * @param separador
	 *            Separador de componentes de la fecha.
	 */
	public void setSeparador(String separador) {
		this.separador = separador;
	}

	/**
	 * Obtiene el tipo de formato.
	 * 
	 * @return Tipo de formato.
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Establece el tipo de formato.
	 * 
	 * @param tipo
	 *            Tipo de formato.
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Obtiene el literal del tipo de formato.
	 * 
	 * @return Literal del tipo de formato.
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Establece el literal del tipo de formato.
	 * 
	 * @param literal
	 *            Literal del tipo de formato.
	 */
	public void setLiteral(String literal) {
		this.literal = literal;
	}

	/**
	 * Obtiene una representación XML del objeto.
	 * 
	 * @param indent
	 *            Número de espacios de tabulación.
	 * @return Representación del objeto.
	 */
	public String toXML(int indent) {
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Formato
		xml.append(tabs + "<" + TagsFicha.TAG_FORMATO);

		// Tipo
		xml.append(" " + TagsFicha.ATTR_TIPO_FORMATO + "=\"");
		if (tipo != null)
			xml.append(tipo);

		// Separador
		xml.append("\" " + TagsFicha.ATTR_SEPARADOR_FORMATO + "=\"");
		if (separador != null)
			xml.append(separador);
		xml.append("\"");

		// Valor por defecto
		if (valorPorDefecto)
			xml.append(" " + TagsFicha.TAG_VALOR_POR_DEFECTO + "=\"true\"");

		xml.append(">");

		xml.append(XmlElement.getCDataContent(literal));

		// Cierre
		xml.append("</" + TagsFicha.TAG_FORMATO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Formato))
			return false;

		Formato otroFormato = (Formato) obj;

		if ((otroFormato.getTipo() != null) && (this.getTipo() != null)
				&& (this.getTipo().equals(otroFormato.getTipo())))
			return true;
		else
			return false;
	}

}
