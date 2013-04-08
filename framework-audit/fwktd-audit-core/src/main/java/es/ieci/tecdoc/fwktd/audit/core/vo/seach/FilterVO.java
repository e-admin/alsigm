package es.ieci.tecdoc.fwktd.audit.core.vo.seach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.ieci.tecdoc.fwktd.core.model.Entity;

public class FilterVO extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2058464815261427741L;

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

	/**
	 * Tipo de Base de Datos
	 */
	protected JdbcTypesEnum jdbcType;

	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"dd/MM/yyyy hh:mm:ss");

	/**
	 * Constructor por defecto
	 */
	public FilterVO() {
		jdbcType = JdbcTypesEnum.VARCHAR;
	}

	/**
	 * Constructor
	 * 
	 * @param field
	 *            Campo por el que se quiere buscar
	 * @param value
	 *            Valor que se aplicará en el filtro
	 * @param operator
	 *            Operador. Objeto de la clase {@link OperatorEnum}
	 */
	public FilterVO(String field, String value, OperatorEnum operator) {
		this.field = field;
		this.value = value;
		this.operator = operator;
		this.jdbcType = JdbcTypesEnum.VARCHAR;
	}

	/**
	 * Constructor
	 * 
	 * @param field
	 *            Campo por el que se quiere buscar
	 * @param value
	 *            Valor que se aplicará en el filtro
	 * @param operator
	 *            Operador. Objeto de la clase {@link OperatorEnum}
	 * @param jdbcType
	 *            Tipo del campo en base de datos. Objeto de la clase
	 *            {@link JdbcTypesEnum}
	 */
	public FilterVO(String field, String value, OperatorEnum operator,
			JdbcTypesEnum jdbcType) {
		this.field = field;
		this.value = value;
		this.operator = operator;
		this.jdbcType = jdbcType;
	}

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

	public Date getValueAsDate() throws ParseException {

		return formatter.parse(value);

	}

	public void setValue(Date value) {
		this.value = formatter.format(value);
	}

	public void setValue(String value) {
		this.value = value;
	}

	public JdbcTypesEnum getJdbcType() {
		return jdbcType;
	}

	public int getJdbcTypeValue() {
		return jdbcType.getValue();
	}

	public void setJdbcType(JdbcTypesEnum jdbcType) {
		this.jdbcType = jdbcType;
	}

}
