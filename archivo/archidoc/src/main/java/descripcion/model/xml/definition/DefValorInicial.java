package descripcion.model.xml.definition;

import common.Constants;
import common.util.StringUtils;

import descripcion.model.xml.XmlElement;

/**
 * Clave que almacena el valor inicial de un campo.
 */
public class DefValorInicial extends XmlElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String valor = null;

	private String idRef = null;
	private int tipoRef = 0;

	private String anio = null;
	private String mes = null;
	private String dia = null;
	private String siglo = null;
	private String formato = null;
	private String separador = null;
	private String calificador = null;

	private int tipoMedida = 0;
	private String unidadMedida = null;

	/**
	 * Constructor.
	 */
	public DefValorInicial() {
		super();
	}

	/**
	 * @return Returns the dia.
	 */
	public String getDia() {
		return dia;
	}

	/**
	 * @param dia
	 *            The dia to set.
	 */
	public void setDia(String dia) {
		this.dia = dia;
	}

	/**
	 * @return Returns the formato.
	 */
	public String getFormato() {
		return formato;
	}

	/**
	 * @param formato
	 *            The formato to set.
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}

	/**
	 * @return Returns the idRef.
	 */
	public String getIdRef() {
		return idRef;
	}

	/**
	 * @param idRef
	 *            The idRef to set.
	 */
	public void setIdRef(String idRef) {
		this.idRef = idRef;
	}

	/**
	 * @return Returns the tipoRef.
	 */
	public int getTipoRef() {
		return tipoRef;
	}

	/**
	 * @param tipoRef
	 *            The tipoRef to set.
	 */
	public void setTipoRef(int tipoRef) {
		this.tipoRef = tipoRef;
	}

	/**
	 * @return Returns the mes.
	 */
	public String getMes() {
		return mes;
	}

	/**
	 * @param mes
	 *            The mes to set.
	 */
	public void setMes(String mes) {
		this.mes = mes;
	}

	/**
	 * @return Returns the calificador.
	 */
	public String getCalificador() {
		return calificador;
	}

	/**
	 * @param calificador
	 *            The calificador to set.
	 */
	public void setCalificador(String calificador) {
		this.calificador = calificador;
	}

	/**
	 * @return Returns the separador.
	 */
	public String getSeparador() {
		return separador;
	}

	/**
	 * @param separador
	 *            The separador to set.
	 */
	public void setSeparador(String separador) {
		this.separador = separador;
	}

	/**
	 * @return Returns the valor.
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            The valor to set.
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return Returns the anio.
	 */
	public String getAnio() {
		return anio;
	}

	/**
	 * @param anio
	 *            The anio to set.
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}

	/**
	 * @return Returns the siglo.
	 */
	public String getSiglo() {
		return siglo;
	}

	/**
	 * @param siglo
	 *            The siglo to set.
	 */
	public void setSiglo(String siglo) {
		this.siglo = siglo;
	}

	/**
	 * @return Returns the tipoMedida.
	 */
	public int getTipoMedida() {
		return tipoMedida;
	}

	/**
	 * @param tipoMedida
	 *            The tipoMedida to set.
	 */
	public void setTipoMedida(int tipoMedida) {
		this.tipoMedida = tipoMedida;
	}

	/**
	 * @return Returns the unidadMedida.
	 */
	public String getUnidadMedida() {
		return unidadMedida;
	}

	/**
	 * @param unidadMedida
	 *            The unidadMedida to set.
	 */
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
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

		// Tag de inicio
		xml.append(tabs + "<" + DefTags.TAG_VALOR_INICIAL_CAMPO_DATO);

		// IdRef
		if (idRef != null) {
			xml.append(" " + DefTags.ATTR_IDREF + "=\"");
			xml.append(idRef);
			xml.append("\"");
		}

		// TipoRef
		if (tipoRef > 0) {
			xml.append(" " + DefTags.ATTR_TIPOREF + "=\"");
			xml.append(tipoRef);
			xml.append("\"");
		}

		// Anio
		if (anio != null) {
			xml.append(" " + DefTags.ATTR_YEAR + "=\"");
			xml.append(anio);
			xml.append("\"");
		}

		// Mes
		if (mes != null) {
			xml.append(" " + DefTags.ATTR_MONTH + "=\"");
			xml.append(mes);
			xml.append("\"");
		}

		// Dia
		if (dia != null) {
			xml.append(" " + DefTags.ATTR_DAY + "=\"");
			xml.append(dia);
			xml.append("\"");
		}

		// Siglo
		if (siglo != null) {
			xml.append(" " + DefTags.ATTR_CENTURY + "=\"");
			xml.append(siglo);
			xml.append("\"");
		}

		// Formato
		if (formato != null) {
			xml.append(" " + DefTags.ATTR_FORMAT + "=\"");
			xml.append(formato);
			xml.append("\"");
		}

		// Separador
		if (separador != null) {
			xml.append(" " + DefTags.ATTR_SEPARATOR + "=\"");
			xml.append(separador);
			xml.append("\"");
		}

		// Calificador
		if (calificador != null) {
			xml.append(" " + DefTags.ATTR_QUALIFIER + "=\"");
			xml.append(calificador);
			xml.append("\"");
		}

		// Tipo de medida
		if (tipoMedida > 0) {
			xml.append(" " + DefTags.ATTR_MEASURE_TYPE + "=\"");
			xml.append(tipoMedida);
			xml.append("\"");
		}

		// Unidad de medida
		if (unidadMedida != null) {
			xml.append(" " + DefTags.ATTR_MEASURE_UNIT + "=\"");
			xml.append(unidadMedida);
			xml.append("\"");
		}

		xml.append(">");

		// Valor
		if (valor != null)
			xml.append(valor);

		// Tag de cierre
		xml.append("</" + DefTags.TAG_VALOR_INICIAL_CAMPO_DATO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
