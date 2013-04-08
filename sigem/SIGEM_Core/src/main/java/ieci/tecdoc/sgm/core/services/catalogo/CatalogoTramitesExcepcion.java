package ieci.tecdoc.sgm.core.services.catalogo;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;

/**
 * Excepción específica del catálogo de trámites.
 * 
 * @author IECISA
 */
public class CatalogoTramitesExcepcion extends SigemException {

	private static String RESOURCE_FILE = "ieci.tecdoc.sgm.core.services.catalogo.CatalogoTramitesExceptionMessages";
	
	public static final long EC_PREFIX = 2000050000;
	public static final long EXC_GENERIC_EXCEPCION	= EC_PREFIX; 
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

  public CatalogoTramitesExcepcion(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}
  
  /**
   * Devuelve la ruta del fichero de mensajes de error
   * @return Ruta del fichero de mensajes de error 
   */
  public String getMessagesFile() {
     return RESOURCE_FILE;
  }
  
  private static final long serialVersionUID = 1L;
}
