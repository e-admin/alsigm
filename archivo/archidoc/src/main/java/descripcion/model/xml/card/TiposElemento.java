package descripcion.model.xml.card;

/**
 * Clase que define los tipos de elementos de una ficha.
 */
public class TiposElemento {

	/** Constante para el tipo de elemento desconocido. */
	public static final short TIPO_ELEMENTO_DESCONOCIDO = 0;

	/** Constante para el tipo de elemento ETIQUETA DATO. */
	public static final short TIPO_ELEMENTO_ETIQUETA_DATO = 1;

	/** Constante para el tipo de elemento TABLA. */
	public static final short TIPO_ELEMENTO_TABLA = 2;

	/** Constante para el tipo de elemento TABLA TEXTUAL. */
	public static final short TIPO_ELEMENTO_TABLA_TEXTUAL = 3;

	/** Constante para el tipo de elemento HIPERVINCULO. */
	public static final short TIPO_ELEMENTO_HIPERVINCULO = 4;

	/** Constante para el tipo de elemento CABECERA. */
	public static final short TIPO_ELEMENTO_CABECERA = 5;

	/** Constante para el tipo de elemento AREA. */
	public static final short TIPO_ELEMENTO_AREA = 6;

	/** Literales de los tipos de elementos posibles. */
	protected static final String[] listaTiposElemento = new String[] {
			"Desconocido", "EtiquetaDato", "Tabla", "TablaTextual",
			"Hipervinculo", "Cabecera", "Area" };

	/**
	 * Obtiene el nombre del tipo de elemento.
	 * 
	 * @param tipo
	 *            Tipo de elemento.
	 * @return Nombre del tipo de elemento.
	 */
	public static String getNombreTipoElemento(short tipo) {
		if ((tipo >= 0) && (tipo < listaTiposElemento.length))
			return listaTiposElemento[tipo];
		else
			return null;
	}
}
