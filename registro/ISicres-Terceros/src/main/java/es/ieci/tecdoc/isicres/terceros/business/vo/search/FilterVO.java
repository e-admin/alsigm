package es.ieci.tecdoc.isicres.terceros.business.vo.search;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 *
 * @author IECISA
 *
 */
public class FilterVO extends Entity {

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public OperatorEnum getOperator() {
		return operator;
	}

	public void setOperator(OperatorEnum operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private static final long serialVersionUID = -612910552128958436L;

	/**
	 * Campo
	 */
	protected String field;

	/**
	 * Operador
	 */
	protected OperatorEnum operator;

	/**
	 * Valor
	 */
	protected String value;

}
