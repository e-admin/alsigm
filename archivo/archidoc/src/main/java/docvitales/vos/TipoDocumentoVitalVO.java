package docvitales.vos;

import common.vos.BaseVO;

/**
 * Información de un tipo de documento vital.
 */
public class TipoDocumentoVitalVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del tipo de documento vital. */
	private String id = null;

	/** Nombre del tipo de documento vital. */
	private String nombre = null;

	/** Descripción del tipo de documento vital. */
	private String descripcion = null;

	/**
	 * Constructor.
	 */
	public TipoDocumentoVitalVO() {
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
}
