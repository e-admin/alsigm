package es.ieci.tecdoc.isicres.terceros.business.vo;

import javax.validation.constraints.Size;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;

/**
 * Clase base para las direcciones de un tercero.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class BaseDireccionVO extends Entity {

	/**
	 * Constructor por defecto.
	 */
	public BaseDireccionVO() {
		tercero = new TerceroValidadoVO();
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public DireccionType getTipo() {
		return tipo;
	}

	public void setTipo(DireccionType tipo) {
		this.tipo = tipo;
	}

	public BaseTerceroVO getTercero() {
		return tercero;
	}

	public void setTercero(BaseTerceroVO tercero) {
		this.tercero = tercero;
	}

	// Members
	/**
	 * Tipo de dirección: física/telemática.
	 */
	protected DireccionType tipo;

	/**
	 * direccion completa ( se puede usar para importacion cuando no se tiene la
	 * informacion de la direccion separada)
	 */
	@Size(max = 160)
	protected String direccion;

	/**
	 * Flag que indica si esta dirección debe ser empleada como dirección
	 * principal del tercero.
	 */
	protected boolean principal;

	/**
	 * Tercero al que pertenece la direccion.
	 */
	protected BaseTerceroVO tercero;

	private static final long serialVersionUID = -7149794802390742709L;
}
