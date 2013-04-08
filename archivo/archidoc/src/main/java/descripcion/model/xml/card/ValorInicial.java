package descripcion.model.xml.card;

import common.Constants;
import common.util.StringUtils;

import descripcion.model.xml.XmlElement;
import descripcion.model.xml.definition.DefValorInicial;

/**
 * Clave que almacena el valor inicial de un campo.
 */
public class ValorInicial extends XmlElement {

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
	private String descripcion = null;

	/**
	 * Constructor.
	 */
	public ValorInicial() {
		super();
	}

	/**
	 * Constructor.
	 */
	public ValorInicial(DefValorInicial valorInicial) {
		super();

		if (valorInicial != null) {
			setValor(valorInicial.getValor());
			setIdRef(valorInicial.getIdRef());
			setTipoRef(valorInicial.getTipoRef());
			setAnio(valorInicial.getAnio());
			setMes(valorInicial.getMes());
			setDia(valorInicial.getDia());
			setSiglo(valorInicial.getSiglo());
			setFormato(valorInicial.getFormato());
			setSeparador(valorInicial.getSeparador());
			setCalificador(valorInicial.getCalificador());
			setTipoMedida(valorInicial.getTipoMedida());
			setUnidadMedida(valorInicial.getUnidadMedida());
		}
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
	 * @return Returns the descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            The descripcion to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
		xml.append(tabs + "<" + TagsFicha.TAG_EDICION_VALOR_INICIAL);

		// IdRef
		if (getIdRef() != null) {
			xml.append(" " + TagsFicha.ATTR_IDREF_VALOR_DATO + "=\"");
			xml.append(getIdRef());
			xml.append("\"");
		}

		// TipoRef
		if (getTipoRef() > 0) {
			xml.append(" " + TagsFicha.ATTR_TIPOREF_VALOR_DATO + "=\"");
			xml.append(getTipoRef());
			xml.append("\"");
		}

		// Anio
		if (getAnio() != null) {
			xml.append(" " + TagsFicha.ATTR_YEAR_VALOR_DATO + "=\"");
			xml.append(getAnio());
			xml.append("\"");
		}

		// Mes
		if (getMes() != null) {
			xml.append(" " + TagsFicha.ATTR_MONTH_VALOR_DATO + "=\"");
			xml.append(getMes());
			xml.append("\"");
		}

		// Dia
		if (getDia() != null) {
			xml.append(" " + TagsFicha.ATTR_DAY_VALOR_DATO + "=\"");
			xml.append(getDia());
			xml.append("\"");
		}

		// Siglo
		if (getSiglo() != null) {
			xml.append(" " + TagsFicha.ATTR_CENTURY_VALOR_DATO + "=\"");
			xml.append(getSiglo());
			xml.append("\"");
		}

		// Formato
		if (getFormato() != null) {
			xml.append(" " + TagsFicha.ATTR_FORMAT_VALOR_DATO + "=\"");
			xml.append(getFormato());
			xml.append("\"");
		}

		// Separador
		if (getSeparador() != null) {
			xml.append(" " + TagsFicha.ATTR_SEPARATOR_VALOR_DATO + "=\"");
			xml.append(getSeparador());
			xml.append("\"");
		}

		// Calificador
		if (getCalificador() != null) {
			xml.append(" " + TagsFicha.ATTR_QUALIFIER_VALOR_DATO + "=\"");
			xml.append(getCalificador());
			xml.append("\"");
		}

		// Tipo de medida
		if (tipoMedida > 0) {
			xml.append(" " + TagsFicha.ATTR_MEASURE_TYPE_VALOR_DATO + "=\"");
			xml.append(tipoMedida);
			xml.append("\"");
		}

		// Unidad de medida
		if (unidadMedida != null) {
			xml.append(" " + TagsFicha.ATTR_MEASURE_UNIT_VALOR_DATO + "=\"");
			xml.append(unidadMedida);
			xml.append("\"");
		}

		xml.append(">");

		// Valor
		xml.append(XmlElement.getCDataContent(getValor()));

		// Tag de cierre
		xml.append("</" + TagsFicha.TAG_EDICION_VALOR_INICIAL + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
