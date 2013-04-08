/*
 * Created on 14-nov-2005
 *
 */
package ieci.tdw.ispac.ispacpublicador.business.action;

import ieci.tdw.ispac.ispacpublicador.business.exceptions.ErrorCode;
import ieci.tdw.ispac.ispacpublicador.business.rule.RuleError;

/**
 * Clase que almacenará información que describen los datos 
 * que han producido un error en la ejecución de una acción
 * al procesar una regla.
 * @author Ildefonso Noreña
 *
 */
public class ActionError extends RuleError {

    /**
     * Construye un ActionError inicializando sus atributos.
     */
    public ActionError(){
        this(-1,-1,null,null, ErrorCode.ERROR_DESCONOCIDO);
    }

    /**
     * Construye un ActionError inicializando sus atributos.
     */
    public ActionError(int idRule, int id, String name, String message, int errorCode){
        setIdRule(idRule);
        setId(id);
        setName(name);
        setMessage(message);
        setCodigoError(errorCode);
    }
}
