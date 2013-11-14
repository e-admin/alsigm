package es.ieci.tecdoc.isicres.api.intercambioregistral.business.types;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes de los operadores de los criterios de las
 * búsquedas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class OperadorCriterioBusquedaIREnum extends StringValuedEnum {

	private static final long serialVersionUID = -8272936321705461648L;

	public static final OperadorCriterioBusquedaIREnum EQUAL = new OperadorCriterioBusquedaIREnum("==", "=");
	public static final OperadorCriterioBusquedaIREnum NOT_EQUAL = new OperadorCriterioBusquedaIREnum("!=", "!=");
	public static final OperadorCriterioBusquedaIREnum LIKE = new OperadorCriterioBusquedaIREnum("=", "like");
	public static final OperadorCriterioBusquedaIREnum LESS_THAN = new OperadorCriterioBusquedaIREnum("<", "<");
	public static final OperadorCriterioBusquedaIREnum EQUAL_OR_LESS_THAN = new OperadorCriterioBusquedaIREnum("<=", "<=");
	public static final OperadorCriterioBusquedaIREnum GREATER_THAN = new OperadorCriterioBusquedaIREnum(">", ">");
	public static final OperadorCriterioBusquedaIREnum EQUAL_OR_GREATER_THAN = new OperadorCriterioBusquedaIREnum(">=", ">=");
	public static final OperadorCriterioBusquedaIREnum IN = new OperadorCriterioBusquedaIREnum("in", "in");

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected OperadorCriterioBusquedaIREnum(String name, String value) {
		super(name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static OperadorCriterioBusquedaIREnum getOperadorCriterio(String value) {
		return (OperadorCriterioBusquedaIREnum) StringValuedEnum.getEnum(OperadorCriterioBusquedaIREnum.class, value);
	}
}
