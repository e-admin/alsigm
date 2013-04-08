package ieci.tdw.applets.ispacdocapplet.logging;


/**
 * Genera trazas en el log del applet.
 *
 */
public class Log {

	/**
	 * Escribe una traza en el log.
	 * @param message Mensaje de la traza.
	 */
	public static void log(String message) {
		System.out.println(message);
	}

	/**
	 * Escribe una excepción en el log.
	 * @param t Excepción capturada.
	 */
	public static void log(Throwable t) {
		t.printStackTrace();
	}

	/**
	 * Escribe una traza en el log.
	 * @param message Mensaje de la traza.
	 * @param t Excepción capturada.
	 */
	public static void log(String message, Throwable t) {
		System.out.println(message);
		t.printStackTrace();
	}

}
