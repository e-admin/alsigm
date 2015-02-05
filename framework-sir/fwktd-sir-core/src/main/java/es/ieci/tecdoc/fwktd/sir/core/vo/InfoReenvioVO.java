package es.ieci.tecdoc.fwktd.sir.core.vo;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;

/**
 * Información de reenvío de un asiento registral.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class InfoReenvioVO extends BaseValueObject {

	private static final long serialVersionUID = 5573982526583519248L;

	/**
	 * Código único de la entidad registral de destino obtenido del directorio
	 * común.
	 */
	private String codigoEntidadRegistralDestino;

	/**
	 * Descripción de la entidad registral de destino.
	 */
	private String descripcionEntidadRegistralDestino;
	
	/**
	 * Código de la unidad de tramitacion destino
	 */
	private String codigoUnidadTramitacionDestino;
	
	/**
	 * descripcion de la unidad de tramitacion destino
	 */
	private String descripcionUnidadTramitacionDestino;
	
	

	/**
	 * Descripción de los motivos de reenvío.
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
	public InfoReenvioVO() {
		super();
	}

	public String getCodigoEntidadRegistralDestino() {
		return codigoEntidadRegistralDestino;
	}

	public void setCodigoEntidadRegistralDestino(
			String codigoEntidadRegistralDestino) {
		this.codigoEntidadRegistralDestino = codigoEntidadRegistralDestino;
	}

	public String getDescripcionEntidadRegistralDestino() {
		return descripcionEntidadRegistralDestino;
	}

	public void setDescripcionEntidadRegistralDestino(
			String descripcionEntidadRegistralDestino) {
		this.descripcionEntidadRegistralDestino = descripcionEntidadRegistralDestino;
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

	public String getCodigoUnidadTramitacionDestino() {
		return codigoUnidadTramitacionDestino;
	}

	public void setCodigoUnidadTramitacionDestino(
			String codigoUnidadTramitacionDestino) {
		this.codigoUnidadTramitacionDestino = codigoUnidadTramitacionDestino;
	}

	public String getDescripcionUnidadTramitacionDestino() {
		return descripcionUnidadTramitacionDestino;
	}

	public void setDescripcionUnidadTramitacionDestino(
			String descripcionUnidadTramitacionDestino) {
		this.descripcionUnidadTramitacionDestino = descripcionUnidadTramitacionDestino;
	}
}
