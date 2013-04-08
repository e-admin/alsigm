package descripcion.model.xml.card;

import descripcion.model.xml.XmlElement;

/**
 * Clase abstracta que almacena la información de un elemento de la ficha
 * ISAD(G).
 */
public abstract class Elemento extends XmlElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Tipo de elemento. */
	protected short tipo;

	/** Etiqueta del elemento. */
	protected Etiqueta etiqueta;

	/** Mostrar Scroll. */
	protected String scroll;

	/**
	 * Constructor.
	 */
	public Elemento() {
		super();
		setTipo(TiposElemento.TIPO_ELEMENTO_DESCONOCIDO);
		etiqueta = new Etiqueta();
	}

	/**
	 * Constructor.
	 * 
	 * @param tipo
	 *            Tipo de elemento.
	 */
	public Elemento(short tipo) {
		super();
		setTipo(tipo);
		etiqueta = new Etiqueta();
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
	 * Obtiene el nombre del tipo de elemento.
	 * 
	 * @return Nombre del tipo de elemento.
	 */
	public String getNombreTipoElemento() {
		return TiposElemento.getNombreTipoElemento(tipo);
	}

	/**
	 * Obtiene la etiqueta del elemento.
	 * 
	 * @return Etiqueta del elemento.
	 */
	public Etiqueta getEtiqueta() {
		return etiqueta;
	}

	/**
	 * Establece la etiqueta del elemento.
	 * 
	 * @param etiqueta
	 *            Etiqueta del elemento.
	 */
	public void setEtiqueta(Etiqueta etiqueta) {
		this.etiqueta = etiqueta;
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