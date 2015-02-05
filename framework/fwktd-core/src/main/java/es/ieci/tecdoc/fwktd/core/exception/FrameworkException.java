/*
 * 
 */
package es.ieci.tecdoc.fwktd.core.exception;

/**
 * Clase base para las extensiones que dispara el framework.
 *
 */
public class FrameworkException extends RuntimeException {

	
	/**
     * Constructor de la clase.
     * 
     * @param msg
     *            mensaje informativo, sugerencia o cualquier otro tipo de
     *            información auxiliar que irá asociada a la excepción
     * @param e
     *            excepción anidada que provoca el disparo de esta excepción
     * 
     */
    public FrameworkException(String msg, Exception e) {
        super(msg, e);
        
    }


    /**
     * Constructor de la clase.
     * 
     * @param msg
     *            ubicación en el código, operación o motivo que provocan el
     *            disparo de la excepción
     */
    public FrameworkException(String msg) {
        super(msg);

    }
    
    // Members
    private static final long serialVersionUID = -2456285626296760901L;
	
}