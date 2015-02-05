package es.ieci.tecdoc.isicres.terceros.business.vo;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 *
 * @author IECISA
 *
 */
public class RepresentanteInteresadoVO extends Entity {

	public BaseTerceroVO getRepresentante() {
		return representante;
	}

	public void setRepresentante(BaseTerceroVO representante) {
		this.representante = representante;
	}

	public InteresadoVO getInteresado() {
		return interesado;
	}

	public void setInteresado(InteresadoVO interesado) {
		this.interesado = interesado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BaseDireccionVO getDireccionNotificacion() {
		return direccionNotificacion;
	}

	public void setDireccionNotificacion(BaseDireccionVO direccionNotificacion) {
		this.direccionNotificacion = direccionNotificacion;
	}

	// Members
	protected BaseTerceroVO representante;

	/**
	 * Tercero al que representa.
	 */
	protected InteresadoVO interesado;

	protected String nombre;

	/**
	 * Dirección de notificación del representante. Será una de las que tenga
	 * como tercero físico o jurídico.
	 */
	protected BaseDireccionVO direccionNotificacion;

	private static final long serialVersionUID = -8348676068424793183L;

}
