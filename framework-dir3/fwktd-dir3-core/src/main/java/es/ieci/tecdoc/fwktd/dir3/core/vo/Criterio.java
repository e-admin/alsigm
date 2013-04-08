package es.ieci.tecdoc.fwktd.dir3.core.vo;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.OperadorCriterioEnum;

/**
 * Información de un criterio en las búsquedas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class Criterio<ExtendedCriterioEnum extends CriterioEnum> extends BaseValueObject {

	private static final long serialVersionUID = 5164807099767853323L;

	/**
	 * Nombre del criterio
	 */
	private ExtendedCriterioEnum nombre = null;

	/**
	 * Operador
	 */
	private OperadorCriterioEnum operador = OperadorCriterioEnum.EQUAL;

	/**
	 * Valor
	 */
	private Object valor = null;

	/**
	 * Constructor.
	 */
	public Criterio() {
		super();
	}

	/**
	 * Constructor.
	 * @param nombre Nombre del criterio.
	 * @param valor Valor.
	 */
	public Criterio(ExtendedCriterioEnum nombre, Object valor) {
		this(nombre, OperadorCriterioEnum.EQUAL, valor);
	}

	/**
	 * Constructor.
	 * @param nombre Nombre del criterio.
	 * @param operador Operador.
	 * @param valor Valor.
	 */
	public Criterio(ExtendedCriterioEnum nombre, OperadorCriterioEnum operador, Object valor) {
		this();
		setNombre(nombre);
		setOperador(operador);
		setValor(valor);
	}

	public ExtendedCriterioEnum getNombre() {
		return nombre;
	}

	public void setNombre(ExtendedCriterioEnum nombre) {
		this.nombre = nombre;
	}

	public OperadorCriterioEnum getOperador() {
		return operador;
	}

	public void setOperador(OperadorCriterioEnum operador) {
		this.operador = operador;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}
}
