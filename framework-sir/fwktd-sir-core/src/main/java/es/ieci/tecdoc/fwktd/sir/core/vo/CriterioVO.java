package es.ieci.tecdoc.fwktd.sir.core.vo;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;
import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.OperadorCriterioEnum;

/**
 * Información de un criterio en las búsquedas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CriterioVO extends BaseValueObject {

	private static final long serialVersionUID = 1336948933110953963L;

	/**
	 * Nombre del criterio
	 */
	private CriterioEnum nombre = null;

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
	public CriterioVO() {
		super();
	}

	/**
	 * Constructor.
	 * @param nombre Nombre del criterio.
	 * @param valor Valor.
	 */
	public CriterioVO(CriterioEnum nombre, Object valor) {
		this(nombre, OperadorCriterioEnum.EQUAL, valor);
	}

	/**
	 * Constructor.
	 * @param nombre Nombre del criterio.
	 * @param operador Operador.
	 * @param valor Valor.
	 */
	public CriterioVO(CriterioEnum nombre, OperadorCriterioEnum operador, Object valor) {
		this();
		setNombre(nombre);
		setOperador(operador);
		setValor(valor);
	}

	public CriterioEnum getNombre() {
		return nombre;
	}

	public void setNombre(CriterioEnum nombre) {
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
