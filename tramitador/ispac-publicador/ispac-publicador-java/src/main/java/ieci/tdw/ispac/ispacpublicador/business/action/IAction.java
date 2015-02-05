package ieci.tdw.ispac.ispacpublicador.business.action;

import ieci.tdw.ispac.ispacpublicador.business.attribute.AttributeContext;
import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ActionException;


/**
 * Interfaz que deben implementar todas las Acciones a ejecutar.
 *
 */
public interface IAction {

    /**
     * Ejecuta la acción.
     * @param rctx Contexto de ejecución de la regla
     * @param attContext Atributos con información extra, utilizados dentro de la ejecución de la regla.
     * @return true si la ejecución termina correctamente, false en caso contrario.
     * @throws ActionException
     */
    public boolean execute(RuleContext rctx, AttributeContext attContext)
    	throws ActionException;
    
    /**
     * Obtiene la información causante de que la ejecución de la acción haya 
     * dado resultado negativo.
     * @return Cadena con información.
     */
    public String getInfo();
    

    /**
     * Obtiene el código de error. Este código es un entero que indentifica al 
     * causante de que la ejecución de la acción de resultado negativo.
     * @return Código de error.
     */
    public int getErrorCode();
}
