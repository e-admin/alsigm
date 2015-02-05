package ieci.tdw.ispac.ispacpublicador.business.action;


/**
 * Acción base para la implementación de acciones.
 * 
 */
public abstract class DefaultAction implements IAction {

    /** Información sobre el resultado de la acción. */
    private String info = null;
    
    /** Código de error. */
    private int errorCode = -1;
    
    
    /**
     * Constructor.
     * 
     */
    public DefaultAction() {}
    
    /**
     * Obtiene la información causante de que la ejecución de la acción haya 
     * dado resultado negativo.
     * @return Cadena con información.
     */
    public String getInfo() {
        return info;
    }

    /**
     * Establece la información causante de que la ejecución de la acción haya 
     * dado resultado negativo.
     * @param Cadena con información.
     */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
     * Obtiene el código de error. Este código es un entero que indentifica al 
     * causante de que la ejecución de la acción de resultado negativo.
     * @return Código de error.
     */
    public int getErrorCode() {
        return errorCode;
    }

	/**
     * Establece el código de error. Este código es un entero que indentifica al 
     * causante de que la ejecución de la acción de resultado negativo.
     * @param Código de error.
     */
    public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}