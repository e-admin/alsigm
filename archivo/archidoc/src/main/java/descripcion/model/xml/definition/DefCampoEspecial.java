package descripcion.model.xml.definition;

import org.apache.commons.lang.StringUtils;

import common.Constants;

/**
 * Clase que almacena la información de un campo especial de la definición de la
 * ficha ISAD(G).
 */
public class DefCampoEspecial extends DefCampo {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Si es editable. */
	private boolean editable = false;

	/** Valor inicial del campo. */
	private DefValorInicial valorInicial = null;

	/** Tipo de dato. */
	private short tipoDato = DefTipos.TIPO_DATO_TEXTO_CORTO;

	/** Si es obligatorio. */
	private boolean obligatorio = false;

	/**
	 * Constructor.
	 */
	public DefCampoEspecial() {
		super(DefTipos.TIPO_CAMPO_ESPECIAL);
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

		// Descripción
		xml.append(tabs + "  <" + DefTags.TAG_DESCRIPCION_CAMPO + ">");
		xml.append(descripcion != null ? descripcion : "");
		xml.append("</" + DefTags.TAG_DESCRIPCION_CAMPO + ">");
		xml.append(Constants.NEWLINE);

		// Tipo de dato
		xml.append(tabs + "  <" + DefTags.TAG_TIPO_CAMPO_DATO + ">");
		xml.append(tipoDato);
		xml.append("</" + DefTags.TAG_TIPO_CAMPO_DATO + ">");
		xml.append(Constants.NEWLINE);

		// Editable
		xml.append(tabs + "  <" + DefTags.TAG_EDITABLE_CAMPO_DATO + ">");
		xml.append(editable ? Constants.TRUE_STRING : Constants.FALSE_STRING);
		xml.append("</" + DefTags.TAG_EDITABLE_CAMPO_DATO + ">");
		xml.append(Constants.NEWLINE);

		// Obligatorio
		xml.append(tabs + "  <" + DefTags.TAG_OBLIGATORIO_CAMPO_DATO + ">");
		xml.append(editable ? Constants.TRUE_STRING : Constants.FALSE_STRING);
		xml.append("</" + DefTags.TAG_OBLIGATORIO_CAMPO_DATO + ">");
		xml.append(Constants.NEWLINE);

		xml.append(tabs + "</" + DefTags.TAG_CAMPO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public short getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(short tipoDato) {
		this.tipoDato = tipoDato;
	}

	public DefValorInicial getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(DefValorInicial valorInicial) {
		this.valorInicial = valorInicial;
	}

	public boolean isObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}
}
