package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.types.CriterioBusquedaIREntradaEnum;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.types.OperadorCriterioBusquedaIREnum;


/**
 * Información de un criterio en las búsquedas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CriterioBusquedaIREntradaVO extends BaseValueObject {

	private static final long serialVersionUID = 1336948933110953963L;

	/**
	 * Nombre del criterio
	 */
	private CriterioBusquedaIREntradaEnum nombre = null;

	/**
	 * Operador
	 */
	private OperadorCriterioBusquedaIREnum operador = OperadorCriterioBusquedaIREnum.EQUAL;

	/**
	 * Valor
	 */
	private Object valor = null;

	/**
	 * Constructor.
	 */
	public CriterioBusquedaIREntradaVO() {
		super();
	}

	/**
	 * Constructor.
	 * @param nombre Nombre del criterio.
	 * @param valor Valor.
	 */
	public CriterioBusquedaIREntradaVO(CriterioBusquedaIREntradaEnum nombre, Object valor) {
		this(nombre, OperadorCriterioBusquedaIREnum.EQUAL, valor);
	}

	/**
	 * Constructor.
	 * @param nombre Nombre del criterio.
	 * @param operador Operador.
	 * @param valor Valor.
	 */
	public CriterioBusquedaIREntradaVO(CriterioBusquedaIREntradaEnum nombre, OperadorCriterioBusquedaIREnum operador, Object valor) {
		this();
		setNombre(nombre);
		setOperador(operador);
		setValor(valor);
	}

	public CriterioBusquedaIREntradaEnum getNombre() {
		return nombre;
	}

	public void setNombre(CriterioBusquedaIREntradaEnum nombre) {
		this.nombre = nombre;
	}

	public OperadorCriterioBusquedaIREnum getOperador() {
		return operador;
	}

	public void setOperador(OperadorCriterioBusquedaIREnum operador) {
		this.operador = operador;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}
}
