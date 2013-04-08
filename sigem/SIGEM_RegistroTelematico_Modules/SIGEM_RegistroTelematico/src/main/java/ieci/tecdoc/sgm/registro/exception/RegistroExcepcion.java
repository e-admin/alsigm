package ieci.tecdoc.sgm.registro.exception;

/**
 * Excepción específica de registro.
 * 
 * @author IECISA
 *
 */
public class RegistroExcepcion extends SgmExcepcion
{
	
	/**
	 * Constructor de excepción de registro.
	 * @param errorCode Código de error.
	 */	
   public RegistroExcepcion(String errorCode)
   {
      super(errorCode);   
   }

   /**
     * Constructor de excepción de registro.
     * @param errorCode Código de error.
     */ 
  public RegistroExcepcion(long errorCode)
  {
     super(errorCode);   
  }
   
   /**
    * Constructor de excepción de registro.
    * @param msg mensaje de error.
    * @param cause Cause del error.
    */
   public RegistroExcepcion(String msg, Exception cause)
   {
      super(msg, cause);
   }
   
   /**
    * Devuelve la localización del fichero de mensajes de error
    * @return String Fichero de mensajes de error
    */
   public String getMessagesFile() {

     return RESOURCE_FILE;
  }

  private static String RESOURCE_FILE = "ieci.tecdoc.sgm.registro.resources.error";
}
