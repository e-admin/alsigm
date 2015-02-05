package descripcion.vos;

import common.vos.BaseVO;
import common.vos.IKeyValue;

/**
 * Clase que contiene la información básica de la definición de una ficha.
 */
public class InfoBFichaVO extends BaseVO implements IKeyValue {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String nombre = null;
	private int tipoNorma;
	private int tipoNivel;
	private int subTipoNivel;
	private String descripcion = null;

	/**
	 * Constructor.
	 */
	public InfoBFichaVO() {

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

	/**
	 * @return Returns the tipoNivel.
	 */
	public int getTipoNivel() {
		return tipoNivel;
	}

	/**
	 * @param tipoNIvel
	 *            The tipoNivel to set.
	 */
	public void setTipoNivel(int tipoNivel) {
		this.tipoNivel = tipoNivel;
	}

	/**
	 * @return Returns the tipoNorma.
	 */
	public int getTipoNorma() {
		return tipoNorma;
	}

	/**
	 * @param tipoNorma
	 *            The tipoNorma to set.
	 */
	public void setTipoNorma(int tipoNorma) {
		this.tipoNorma = tipoNorma;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.vos.IKeyValue#getKey()
	 */
	public String getKey() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.vos.IKeyValue#getValue()
	 */
	public String getValue() {
		return this.nombre;
	}

	public void setSubTipoNivel(int subTipoNivel) {
		this.subTipoNivel = subTipoNivel;
	}

	public int getSubTipoNivel() {
		return subTipoNivel;
	}
}
