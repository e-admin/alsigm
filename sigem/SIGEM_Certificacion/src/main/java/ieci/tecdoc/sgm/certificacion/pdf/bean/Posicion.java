package ieci.tecdoc.sgm.certificacion.pdf.bean;

/**
 * Clase que almacena la posición de un dato dentro de un elemento
 * @author José Antonio Nogales
 */
public class Posicion {
	int ordenRuta;
	int tipo;
	
	/**
	 * Constructor de la clase
	 * @param ordenRuta Orden de la ruta (en caso de ser el dato de tipo path.1, path.2, ...)
	 * @param tipo Tipo de dato (imagen, cabecera, ...)
	 */
	public Posicion(int ordenRuta, int tipo) {
		this.ordenRuta = ordenRuta;
		this.tipo = tipo;
	}

	/**
	 * Métodos get y set de la clase
	 */
	public int getOrdenRuta() {
		return ordenRuta;
	}

	public void setOrdenRuta(int ordenRuta) {
		this.ordenRuta = ordenRuta;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
}
