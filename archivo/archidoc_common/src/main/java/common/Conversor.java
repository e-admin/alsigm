package common;



/**
 * Clase para Convertir tipos de Datos
 * @author lucas
 *
 */
public class Conversor {

	/**
	 * Convierte el número especificado
	 * @param numero Número a Convertir
	 * @return Número convertido a int
	 */
	public static int toInt(String numero) throws Exception {
		try {
			int retorno = Integer.parseInt(numero);
			return retorno;
		}
		catch(Exception e) {
			throw e;
		}
	}

}
