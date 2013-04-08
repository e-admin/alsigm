/*
 * Created on 22-nov-2005
 *
 */
package ieci.tdw.ispac.ispacpublicador.business.action;

import ieci.tdw.ispac.ispacpublicador.business.attribute.AttributeContext;
import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ActionException;
import ieci.tdw.ispac.ispacpublicador.business.rule.RuleInfo;
import ieci.tdw.ispac.ispacpublicador.business.rule.RuleMessage;
import ieci.tdw.ispac.ispacpublicador.business.vo.RuleVO;

import org.apache.log4j.Logger;


/**
 * Clase encargada de manejar la ejecución de Acciones.
 * @author Ildefonso Noreña
 *
 */
public class ActionHandler {

    protected Logger logger = Logger.getLogger(ActionHandler.class);
    
    public boolean execute(IAction action, RuleContext rctx, AttributeContext attCtx, RuleVO vo) throws ActionException{
        if (!action.execute(rctx, attCtx)){
            RuleMessage error = new ActionError(vo.getId(),vo.getIdAccion(), action.getClass().getName(), action.getInfo(), action.getErrorCode());
            if (logger.isInfoEnabled())
                logger.info("La ejecución de la acción da resultado negativo, motivos: "+error.toString());
            rctx.addMessage(error);
            return false;
        }else{
            //Añadimos mensaje indicando que la accion se ha ejecutado correctamente
            RuleMessage info = new RuleInfo(vo.getId(),vo.getIdAccion(), action.getClass().getName(), "La Acción se ha procesado correctamente.");
            rctx.addMessage(info);
        }
        return true;
    }
}
