package descripcion.model.xml.card;

import common.Constants;
import common.util.CustomDate;
import common.util.StringUtils;

import descripcion.model.xml.XmlElement;
import descripcion.vos.ValorCampoGenericoVOBase;

/**
 * Clave que almacena el valor de un campo.
 */
public class Valor extends ValorInicial {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int orden = 1;
	private String accion = null;
	private Valor valorAnterior = null;
	// private boolean descrFichaAsociada = false;
	private boolean tieneFichaAsociada = false;
	private int tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO;

	/**
	 * Constructor.
	 */
	public Valor() {
		super();
	}

	/**
	 * Constructor.
	 */
	public Valor(ValorInicial valorInicial) {
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
			setDescripcion(valorInicial.getDescripcion());
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param orden
	 *            Orden.
	 */
	public Valor(int orden) {
		super();
		setOrden(orden);
	}

	/**
	 * Constructor.
	 * 
	 * @param orden
	 *            Orden.
	 * @param valor
	 *            Valor.
	 */
	public Valor(int orden, String valor) {
		super();
		setOrden(orden);
		setValor(valor);
	}

	/**
	 * Constructor.
	 */
	public Valor(int orden, CustomDate customDate) {
		super();
		setOrden(orden);
		setValor(customDate.getValue());
		setAnio(customDate.getYear());
		setMes(customDate.getMonth());
		setDia(customDate.getDay());
		setSiglo(customDate.getCentury());
		setFormato(customDate.getFormat());
		setSeparador(customDate.getSeparator());
		setCalificador(customDate.getQualifier());
	}

	/**
	 * Constructor.
	 * 
	 * @param valor
	 *            Valor.
	 * @param idRef
	 *            Identificador.
	 */
	public Valor(int orden, String valor, String idRef, int tipoRef) {
		super();
		setOrden(orden);
		setValor(valor);
		setIdRef(idRef);
		setTipoRef(tipoRef);
	}

	/**
	 * Constructor.
	 * 
	 * @param valor
	 *            Valor.
	 * @param idRef
	 *            Identificador.
	 */
	public Valor(int orden, String valor, String idRef, int tipoRef,
			String descripcion) {
		super();
		setOrden(orden);
		setValor(valor);
		setIdRef(idRef);
		setTipoRef(tipoRef);
		setDescripcion(descripcion);
	}

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
	 * @return Returns the accion.
	 */
	public String getAccion() {
		return accion;
	}

	/**
	 * @param accion
	 *            The accion to set.
	 */
	public void setAccion(String accion) {
		this.accion = accion;
	}

	/**
	 * @return Returns the valorAnterior.
	 */
	public Valor getValorAnterior() {
		return valorAnterior;
	}

	/**
	 * @param valorAnterior
	 *            The valorAnterior to set.
	 */
	public void setValorAnterior(Valor valorAnterior) {
		this.valorAnterior = valorAnterior;
	}

	/**
	 * Indica si el objeto pasado por parámetro es igual a éste.
	 * 
	 * @param valor
	 *            Valor.
	 * @return si son iguales.
	 */
	public boolean equals(Valor obj) {
		return
		// (orden == obj.getOrden()) &&
		StringUtils.equalsNullEmpty(getValor(), obj.getValor())
				&& StringUtils.equalsNullEmpty(getIdRef(), obj.getIdRef())
				&&
				// (tipoRef == obj.getTipoRef()) &&
				StringUtils.equalsNullEmpty(getAnio(), obj.getAnio())
				&& StringUtils.equalsNullEmpty(getMes(), obj.getMes())
				&& StringUtils.equalsNullEmpty(getDia(), obj.getDia())
				&& StringUtils.equalsNullEmpty(getSiglo(), obj.getSiglo())
				&& StringUtils.equalsNullEmpty(getFormato(), obj.getFormato())
				&& StringUtils.equalsNullEmpty(getSeparador(),
						obj.getSeparador())
				&& StringUtils.equalsNullEmpty(getCalificador(),
						obj.getCalificador())
				&& (getTipoMedida() == obj.getTipoMedida())
				&& StringUtils.equalsNullEmpty(getUnidadMedida(),
						obj.getUnidadMedida());
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
		xml.append(tabs + "<" + TagsFicha.TAG_VALOR_DATO);

		// Orden
		xml.append(" " + TagsFicha.ATTR_ORDEN_VALOR_DATO + "=\"");
		xml.append(orden);
		xml.append("\"");

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
		if (getTipoMedida() > 0) {
			xml.append(" " + TagsFicha.ATTR_MEASURE_TYPE_VALOR_DATO + "=\"");
			xml.append(getTipoMedida());
			xml.append("\"");
		}

		// Unidad de medida
		if (getUnidadMedida() != null) {
			xml.append(" " + TagsFicha.ATTR_MEASURE_UNIT_VALOR_DATO + "=\"");
			xml.append(getUnidadMedida());
			xml.append("\"");
		}

		// Accion
		if (accion != null) {
			xml.append(" " + TagsFicha.ATTR_ACCION_VALOR_DATO + "=\"");
			xml.append(accion);
			xml.append("\"");
		}

		// Descripcion
		if (StringUtils.isNotBlank(getDescripcion())) {
			xml.append(" " + TagsFicha.ATTR_DESCRIPCION_VALOR_DATO + "=\"");
			xml.append(getDescripcion());
			xml.append("\"");
		}

		// es un dato referencia con ficha asociada
		if (tieneFichaAsociada == true) {
			xml.append(" " + TagsFicha.TAG_TIENE_FICHA_ASOCIADA + "=\"");
			xml.append(Constants.TRUE_STRING);
			xml.append("\"");
		}

		// es descriptor con ficha asociada
		/*
		 * if (descrFichaAsociada ==true) { xml.append(" " +
		 * TagsFicha.ATTR_DESCR_FICHA_ASOCIADA + "=\"");
		 * xml.append(Constants.TRUE_STRING); xml.append("\""); }
		 */

		xml.append(">");

		// Valor
		xml.append(XmlElement.getCDataContent(getValor()));

		// Tag de cierre
		xml.append("</" + TagsFicha.TAG_VALOR_DATO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

	public boolean isTieneFichaAsociada() {
		return tieneFichaAsociada;
	}

	public void setTieneFichaAsociada(boolean tieneFichaAsociada) {
		this.tieneFichaAsociada = tieneFichaAsociada;
	}

	public int getTipoElemento() {
		return tipoElemento;
	}

	public void setTipoElemento(int tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

}
