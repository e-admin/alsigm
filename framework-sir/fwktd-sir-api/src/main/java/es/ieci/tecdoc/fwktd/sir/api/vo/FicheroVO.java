package es.ieci.tecdoc.fwktd.sir.api.vo;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;

/**
 * Información de un fichero.
 *
 * @author Iecisa
 * @version $Revision$
 */
public class FicheroVO extends BaseValueObject {

	private static final long serialVersionUID = -8817361745788252613L;

	/**
	 * Nombre del fichero.
	 */
	private String nombre = null;

	/**
	 * Contenido del fichero.
	 */
	private byte[] contenido = null;

	/**
	 * Constructor.
	 */
	public FicheroVO() {
		super();
	}

	/**
	 * Constructor
	 * @param nombre Nombre del fichero.
	 * @param contenido Contenido del fichero.
	 */
	public FicheroVO(String nombre, byte[] contenido) {
		super();
		setNombre(nombre);
		setContenido(contenido);
	}

	public byte[] getContenido() {
		return contenido;
	}

	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
