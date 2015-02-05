/*
 * Creado el 20-jun-2006
 *
*/
package ieci.tecdoc.sgm.registro.exception;

import ieci.tecdoc.sgm.registro.exception.RegistroExcepcion;



/**
 * Excepción específica de la validación de documentos en el
 * proceso de registro.
 */
public class ValidacionDocumentoExcepcion extends RegistroExcepcion {

	private String codDoc;
	/**
	 * Constructor de excepción de registro.
	 * @param codDoc Cídgo del documento.
	 * @param errorCode Código de error.
	 * @param msg Mensaje de error.
	 */	
   public ValidacionDocumentoExcepcion(String codDoc, String errorCode, String msg)
   {
      super(errorCode);   
      this.codDoc = codDoc;
   }

   /**
    * Constructor de excepción de registro.
    * @param errorCode Código de error.
    * @param msg mensaje de error.
    * @param cause Cause del error.
    */
   public ValidacionDocumentoExcepcion(String codDoc, String errorCode, String msg, Exception cause)
   {
      super(msg, cause);
      this.codDoc = codDoc;
   }
   
    /** 
     * Devuelve el código de documento cuya validación produjo la excepción.
     * @return String Código de documento.
     */
	public String getCodDoc() {
		return codDoc;
	}
}
