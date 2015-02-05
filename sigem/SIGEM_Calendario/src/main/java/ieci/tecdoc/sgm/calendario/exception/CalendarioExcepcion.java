package ieci.tecdoc.sgm.calendario.exception;


/**
 * Excepción específica del catálogo de trámites.
 * 
 * @author IECISA
 */
public class CalendarioExcepcion extends SgmExcepcion {
    
  /**
   * Construye un objeto de la clase.
   * 
   * @param errorCode
   *           Código de error.
   */

  public CalendarioExcepcion(long errorCode) {
     super(errorCode);
  }

  /**
   * Construye un objeto de la clase.
   * 
   * @param errorCode
   *           Código de error.
   * @param cause
   *           Excepción que ha causado ésta.
   */

  public CalendarioExcepcion(long errorCode, Throwable cause) {
     super(errorCode, cause);
  }

  /**
   * Devuelve la ruta del fichero de mensajes de error
   * @return Ruta del fichero de mensajes de error 
   */
  public String getMessagesFile() {
     return RESOURCE_FILE;
  }

  private static String RESOURCE_FILE = "ieci.tecdoc.sgm.calendario.resources.error";
  
}
