package descripcion.model.xml.format;

import descripcion.model.xml.XmlElement;

/**
 * Clase abstracta que almacena la información de un elemento de la definición
 * del formato de la ficha ISAD(G).
 */
public abstract class DefFmtElemento extends XmlElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Tipo de elemento. */
	protected short tipo;

	/** Etiqueta del elemento. */
	protected DefFmtEtiqueta etiqueta;

	/** Mostrar Scroll. */
	protected String scroll;

	/**
	 * Constructor.
	 */
	public DefFmtElemento() {
		super();
		setTipo(DefFmtTiposElemento.TIPO_ELEMENTO_DESCONOCIDO);
		etiqueta = new DefFmtEtiqueta();
		setScroll("N");
	}

	/**
	 * Constructor.
	 * 
	 * @param tipo
	 *            Tipo de elemento.
	 */
	public DefFmtElemento(short tipo) {
		super();
		setTipo(tipo);
		etiqueta = new DefFmtEtiqueta();
		setScroll("N");
	}

	/**
	 * Constructor.
	 * 
	 * @param tipo
	 *            Tipo de elemento.
	 * @param scroll
	 *            mostrar Scroll para el elemento.
	 */
	public DefFmtElemento(short tipo, String mostrarScroll) {
		super();
		setTipo(tipo);
		etiqueta = new DefFmtEtiqueta();
		setScroll(mostrarScroll);
	}

	/**
	 * Obtiene el tipo de elemento.
	 * 
	 * @return Tipo de elemento.
	 */
	public short getTipo() {
		return tipo;
	}

	/**
	 * Establece el tipo de elemento.
	 * 
	 * @param tipo
	 *            Tipo de elemento.
	 */
	public void setTipo(short tipo) {
		this.tipo = tipo;
	}

	/**
	 * Obtiene la etiqueta del elemento.
	 * 
	 * @return Etiqueta del elemento.
	 */
	public DefFmtEtiqueta getEtiqueta() {
		return etiqueta;
	}

	/**
	 * Establece la etiqueta del elemento.
	 * 
	 * @param etiqueta
	 *            Etiqueta del elemento.
	 */
	public void setEtiqueta(DefFmtEtiqueta etiqueta) {
		this.etiqueta = etiqueta;
	}

	/**
	 * Obtiene el nombre del tipo de elemento.
	 * 
	 * @return Nombre del tipo de elemento.
	 */
	public String getNombreTipoElemento() {
		return DefFmtTiposElemento.getNombreTipoElemento(tipo);
	}

	/**
	 * Obtiene el atributo que indica si es necesario mostrar el scroll
	 * 
	 * @return
	 */
	public String getScroll() {
		return scroll;
	}

	/**
	 * Establece el atributo del elemento
	 * 
	 * @param scroll
	 */
	public void setScroll(String scroll) {
		this.scroll = scroll;
	}
}
