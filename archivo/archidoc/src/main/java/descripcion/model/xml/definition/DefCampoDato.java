package descripcion.model.xml.definition;

import org.apache.commons.lang.StringUtils;

import common.Constants;

/**
 * Clase que almacena la información de un campo de tipo dato de la definición
 * de la ficha ISAD(G).
 */
public class DefCampoDato extends DefCampo {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Tipo de dato. */
	private short tipoDato = DefTipos.TIPO_DATO_TEXTO_CORTO;

	/** Si es multivalor. */
	private boolean multivalor = false;

	/** Si es editable. */
	private boolean editable = false;

	/** Si es obligatorio. */
	private boolean obligatorio = false;

	/** Valor inicial del campo. */
	private DefValorInicial valorInicial = null;

	/** Información específica del dato. */
	private DefInformacionEspecifica informacionEspecifica = new DefInformacionEspecifica();

	/**
	 * Constructor.
	 */
	public DefCampoDato() {
		super(DefTipos.TIPO_CAMPO_DATO);
	}

	/**
	 * Obtiene la información específica del dato.
	 * 
	 * @return Información específica del dato.
	 */
	public DefInformacionEspecifica getInformacionEspecifica() {
		return informacionEspecifica;
	}

	/**
	 * Establece la información específica del dato.
	 * 
	 * @param informacionEspecifica
	 *            Información específica del dato.
	 */
	public void setInformacionEspecifica(
			DefInformacionEspecifica informacionEspecifica) {
		this.informacionEspecifica = informacionEspecifica;
	}

	/**
	 * Indica si es multivalor.
	 * 
	 * @return Si es multivalor.
	 */
	public boolean isMultivalor() {
		return multivalor;
	}

	/**
	 * Establece si es multivalor.
	 * 
	 * @param multivalor
	 *            Si es multivalor.
	 */
	public void setMultivalor(boolean multivalor) {
		this.multivalor = multivalor;
	}

	/**
	 * Indica si es obligatorio.
	 * 
	 * @return Si es obligatorio.
	 */
	public boolean isObligatorio() {
		return obligatorio;
	}

	/**
	 * Establece si es obligatorio.
	 * 
	 * @param obligatorio
	 *            Si es obligatorio.
	 */
	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	/**
	 * Obtiene el tipo de dato.
	 * 
	 * @return Tipo de dato.
	 */
	public short getTipoDato() {
		return tipoDato;
	}

	/**
	 * Establece el tipo de dato.
	 * 
	 * @param tipoDato
	 *            Tipo de dato.
	 */
	public void setTipoDato(short tipoDato) {
		this.tipoDato = tipoDato;
	}

	/**
	 * Obtiene el valor inicial del campo.
	 * 
	 * @return Valor inicial del campo.
	 */
	public DefValorInicial getValorInicial() {
		return valorInicial;
	}

	/**
	 * Establece el valor inicial del campo.
	 * 
	 * @param valorInicial
	 *            Valor inicial del campo.
	 */
	public void setValorInicial(DefValorInicial valorInicial) {
		this.valorInicial = valorInicial;
	}

	/**
	 * Indica si es editable.
	 * 
	 * @return Si es editable.
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Establece si es editable.
	 * 
	 * @param editable
	 *            Si es editable.
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
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

		// Campo
		xml.append(tabs + "<" + DefTags.TAG_CAMPO + " "
				+ DefTags.ATTR_TIPO_CAMPO + "=\"" + getNombreTipoCampo()
				+ "\">");
		xml.append(Constants.NEWLINE);

		// Identificador
		xml.append(tabs + "  <" + DefTags.TAG_ID_CAMPO + ">");
		xml.append(id != null ? id : "");
		xml.append("</" + DefTags.TAG_ID_CAMPO + ">");
		xml.append(Constants.NEWLINE);

		// Tipo de dato
		xml.append(tabs + "  <" + DefTags.TAG_TIPO_CAMPO_DATO + ">");
		xml.append(tipoDato);
		xml.append("</" + DefTags.TAG_TIPO_CAMPO_DATO + ">");
		xml.append(Constants.NEWLINE);

		// Multivalor
		xml.append(tabs + "  <" + DefTags.TAG_MULTIVALOR_CAMPO_DATO + ">");
		xml.append(multivalor ? Constants.TRUE_STRING : Constants.FALSE_STRING);
		xml.append("</" + DefTags.TAG_MULTIVALOR_CAMPO_DATO + ">");
		xml.append(Constants.NEWLINE);

		// Editable
		xml.append(tabs + "  <" + DefTags.TAG_EDITABLE_CAMPO_DATO + ">");
		xml.append(editable ? Constants.TRUE_STRING : Constants.FALSE_STRING);
		xml.append("</" + DefTags.TAG_EDITABLE_CAMPO_DATO + ">");
		xml.append(Constants.NEWLINE);

		// Obligatorio
		xml.append(tabs + "  <" + DefTags.TAG_OBLIGATORIO_CAMPO_DATO + ">");
		xml.append(obligatorio ? Constants.TRUE_STRING : Constants.FALSE_STRING);
		xml.append("</" + DefTags.TAG_OBLIGATORIO_CAMPO_DATO + ">");
		xml.append(Constants.NEWLINE);

		// Valor Inicial
		if (valorInicial != null)
			xml.append(valorInicial.toXML(indent + 2));

		// Información Específica
		xml.append(informacionEspecifica.toXML(indent + 2));

		// Descripción
		xml.append(tabs + "  <" + DefTags.TAG_DESCRIPCION_CAMPO + ">");
		xml.append(descripcion != null ? descripcion : "");
		xml.append("</" + DefTags.TAG_DESCRIPCION_CAMPO + ">");
		xml.append(Constants.NEWLINE);

		xml.append(tabs + "</" + DefTags.TAG_CAMPO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
