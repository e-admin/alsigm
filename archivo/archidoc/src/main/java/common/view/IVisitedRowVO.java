package common.view;

public interface IVisitedRowVO {

	public static final String CSS_FILA_NORMAL = "";
	public static final String CSS_FILA_CARGADA = "loaded";

	/**
	 * Establece la clase de estilo CSS para cada fila de la tabla del display
	 * tag.
	 * 
	 * @return Clase de estilo CSS.
	 */
	public String getRowStyle();

}
