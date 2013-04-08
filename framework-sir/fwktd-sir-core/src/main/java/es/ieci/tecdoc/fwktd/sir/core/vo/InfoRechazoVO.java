package es.ieci.tecdoc.fwktd.sir.core.vo;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;

/**
 * Información de rechazo de un asiento registral.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class InfoRechazoVO extends BaseValueObject {

	private static final long serialVersionUID = -4427400730809870402L;

	/**
	 * Tipo de rechazo.
	 */
	private TipoRechazoEnum tipoRechazo;

	/**
	 * Descripción de los motivos de rechazo.
	 */
	private String descripcion;

	/**
	 * Nombre del usuario de origen.
	 */
	private String usuario;

	/**
	 * Contacto del usuario de origen (teléfono o dirección de correo
	 * electrónico).
	 */
	private String contacto;

	/**
	 * Aplicación y versión emisora.
	 */
	private String aplicacion;

	/**
	 * Constructor.
	 */
	public InfoRechazoVO() {
		super();
	}

	public TipoRechazoEnum getTipoRechazo() {
		return tipoRechazo;
	}

	public void setTipoRechazo(TipoRechazoEnum tipoRechazo) {
		this.tipoRechazo = tipoRechazo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}
}
