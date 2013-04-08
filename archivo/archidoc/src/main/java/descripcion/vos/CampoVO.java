package descripcion.vos;

/**
 * VO que almacena los valores de tablas auxiliares del campo.
 */
public class CampoVO {

	/**
	 * Cadena que contiene la etiquetXml de la Tabla a la que pertenece
	 */
	private String etiquetaXmlTabla;

	/**
	 * Cadena que contiene la etiquetaXml de la fila de la tabla
	 */
	private String etiquetaXmlFila;

	public CampoVO() {
		super();
	}

	/**
	 * @return el etiquetaXmlTabla
	 */
	public String getEtiquetaXmlTabla() {
		return etiquetaXmlTabla;
	}

	/**
	 * @param etiquetaXmlTabla
	 *            el etiquetaXmlTabla a fijar
	 */
	public void setEtiquetaXmlTabla(String etiquetaXmlTabla) {
		this.etiquetaXmlTabla = etiquetaXmlTabla;
	}

	/**
	 * @return el etiquetaXmlFila
	 */
	public String getEtiquetaXmlFila() {
		return etiquetaXmlFila;
	}

	/**
	 * @param etiquetaXmlFila
	 *            el etiquetaXmlFila a fijar
	 */
	public void setEtiquetaXmlFila(String etiquetaXmlFila) {
		this.etiquetaXmlFila = etiquetaXmlFila;
	}

}
