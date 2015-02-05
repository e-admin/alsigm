package descripcion.model.xml.definition;

import descripcion.model.xml.XmlElement;

/**
 * Clase abstracta que almacena la información de un campo de la definición de
 * la ficha ISAD(G).
 */
public abstract class DefCampo extends XmlElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Tipo de elemento. */
	protected short tipo;

	/** Identificador del campo. */
	protected String id = null;

	/** Descripción del elemento. */
	protected String descripcion = null;

	/**
	 * Constructor.
	 */
	public DefCampo() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param tipo
	 *            Tipo de campo.
	 */
	public DefCampo(short tipo) {
		super();
		setTipo(tipo);
	}

	/**
	 * Obtiene el identificador del campo.
	 * 
	 * @return Identificador del campo.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador del campo.
	 * 
	 * @param id
	 *            Identificador del campo.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Obtiene la descripción del campo.
	 * 
	 * @return Descripción del campo.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción del campo.
	 * 
	 * @param descripcion
	 *            Descripción del campo.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el tipo de campo.
	 * 
	 * @return Tipo de campo.
	 */
	public short getTipo() {
		return tipo;
	}

	/**
	 * Establece el tipo de campo.
	 * 
	 * @param tipo
	 *            Tipo de campo.
	 */
	public void setTipo(short tipo) {
		this.tipo = tipo;
	}

	/**
	 * Obtiene el nombre del tipo de campo.
	 * 
	 * @return Nombre del tipo de campo.
	 */
	public String getNombreTipoCampo() {
		return DefTipos.getNombreTipoCampo(tipo);
	}

}
