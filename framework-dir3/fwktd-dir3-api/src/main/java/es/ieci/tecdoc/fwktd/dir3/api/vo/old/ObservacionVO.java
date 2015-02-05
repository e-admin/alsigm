package es.ieci.tecdoc.fwktd.dir3.api.vo.old;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 * Información de una observación.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ObservacionVO extends Entity {

	private static final long serialVersionUID = -3189589176546995190L;

	private String texto;
	private String estado;

	/**
	 * Constructor.
	 */
	public ObservacionVO() {
		super();
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
