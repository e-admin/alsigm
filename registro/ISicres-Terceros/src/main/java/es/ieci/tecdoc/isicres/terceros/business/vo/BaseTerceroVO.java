package es.ieci.tecdoc.isicres.terceros.business.vo;

import javax.validation.constraints.Size;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 * Clase base para los terceros. Mantiene los atributos comunes para terceros,
 * con independencia de su naturaleza (física o jurídica).
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class BaseTerceroVO extends Entity {

	private static final long serialVersionUID = 3493266506908475595L;

	/**
	 * Nombre del tercero.
	 */
	@Size(max = 30)
	protected String nombre;

	/**
	 * Constructor por defecto.
	 */
	public BaseTerceroVO() {
		// nothing to do
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 *
	 * @return Descripción del tercero. Depende del tipo de tercero: Nombre y
	 *         apellidos, razón social, etc..
	 */
	public String getDescripcion() {
		return getNombre();
	}

}
