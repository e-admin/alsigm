package docelectronicos.vos;

import common.vos.BaseVO;

/**
 * Información básica de una ficha de clasificadores documentales.
 */
public class InfoBFichaClfVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador de la ficha. */
	private String id = null;

	/** Nombre de la ficha. */
	private String nombre = null;

	/** Descripción de la ficha. */
	private String descripcion = null;

	/**
	 * Constructor.
	 */
	public InfoBFichaClfVO() {
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
	 * @return Returns the descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            The descripcion to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
