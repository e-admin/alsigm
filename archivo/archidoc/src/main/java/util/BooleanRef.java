package util;

/**
 * Objeto Booleano para pasar por referencia
 * 
 * @author lucas
 */
public class BooleanRef {
	private boolean valorCierto = false;

	/**
	 * Constructor por Defecto. Inicializa la variable a <b>false</b>
	 */
	public BooleanRef() {
		this.valorCierto = false;
	}

	/**
	 * Constructor con Parámetros
	 * 
	 * @param valorInicial
	 *            Valor inicial. Puede ser true o false
	 */
	public BooleanRef(boolean valorInicial) {
		this.valorCierto = valorInicial;
	}

	/**
	 * Comprueba si el valorCierto es cierto
	 * 
	 * @return el valorCierto
	 */
	public boolean isValorCierto() {
		return valorCierto;
	}

	/**
	 * Establece el valorCierto del booleano
	 * 
	 * @param valorCierto
	 *            el valorCierto a establecer
	 */
	public void setValorCierto(boolean valor) {
		this.valorCierto = valor;
	}
}
