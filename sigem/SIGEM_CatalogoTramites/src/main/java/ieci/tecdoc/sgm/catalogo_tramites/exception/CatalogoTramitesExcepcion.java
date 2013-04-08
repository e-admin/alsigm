package ieci.tecdoc.sgm.catalogo_tramites.exception;


/**
 * Excepción específica del catálogo de trámites.
 * 
 * @author IECISA
 */
public class CatalogoTramitesExcepcion extends SgmExcepcion {
    
  /**
   * Construye un objeto de la clase.
   * 
   * @param errorCode
   *           Código de error.
   */

  public CatalogoTramitesExcepcion(long errorCode) {
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

  public CatalogoTramitesExcepcion(long errorCode, Throwable cause) {
     super(errorCode, cause);
  }

  /**
   * Devuelve la ruta del fichero de mensajes de error
   * @return Ruta del fichero de mensajes de error 
   */
  public String getMessagesFile() {
     return RESOURCE_FILE;
  }

  private static String RESOURCE_FILE = "ieci.tecdoc.sgm.catalogo_tramites.resources.error";
  
}
