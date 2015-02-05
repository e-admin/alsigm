package descripcion.vos;

/**
 * Clase que contiene la información de la definición de una ficha.
 */
public class FichaVO extends InfoBFichaVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String definicion = null;
	private String tipoNormaText = null;

	/**
	 * Constructor.
	 */
	public FichaVO() {

	}

	/**
	 * @return Returns the definicion.
	 */
	public String getDefinicion() {
		return definicion;
	}

	/**
	 * @param definicion
	 *            The definicion to set.
	 */
	public void setDefinicion(String definicion) {
		this.definicion = definicion;
	}

	public String getTipoNormaText() {
		return tipoNormaText;
	}

	public void setTipoNormaText(String tipoNormaText) {
		this.tipoNormaText = tipoNormaText;
	}

}
