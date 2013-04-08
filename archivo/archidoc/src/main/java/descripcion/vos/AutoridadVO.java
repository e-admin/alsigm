package descripcion.vos;

import common.vos.BaseVO;

/**
 * Clase que almacena la información de una autoridad.
 */
public class AutoridadVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del descriptor de la autoridad. */
	private String id = null;

	/** Nombre del descriptor de la autoridad. */
	private String nombre = null;

	/**
	 * Nombre de la lista descriptora a la que pertenece el descriptor de la
	 * autoridad.
	 */
	private String nombreLista = null;

	/**
	 * Constructor.
	 */
	public AutoridadVO() {
		super();
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Returns the nombreLista.
	 */
	public String getNombreLista() {
		return nombreLista;
	}

	/**
	 * @param nombreLista
	 *            The nombreLista to set.
	 */
	public void setNombreLista(String nombreLista) {
		this.nombreLista = nombreLista;
	}
}
