/*
 * Created on 22-nov-2005
 *
 */
package ieci.tdw.ispac.ispacpublicador.business.condition;

import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ConditionException;
import ieci.tdw.ispac.ispacpublicador.business.rule.RuleInfo;
import ieci.tdw.ispac.ispacpublicador.business.rule.RuleMessage;
import ieci.tdw.ispac.ispacpublicador.business.vo.RuleVO;

import org.apache.log4j.Logger;


/**
 * Clase encargada de manejar la evaluación de Condiciones.
 * @author Ildefonso Noreña
 *
 */
public class ConditionHandler {

    /**
     * Logger
     */
    protected Logger logger = Logger.getLogger(ConditionHandler.class);
    
    public boolean evaluate(ICondition condition, RuleContext rctx, RuleVO vo)throws ConditionException{
      RuleMessage info = new RuleInfo(vo.getId(),vo.getIdCondicion(), condition.getClass().getName(), null);
      //Evaluamos la condicion
      if (logger.isInfoEnabled())
          logger.info("Evaluando la condicion: "+condition.getClass().getName());
      
      if (!condition.evaluate(rctx)){
          info.setMessage("La evaluación de la condición da resultado negativo por lo que no se ejecutara la accion que tiene asociada");
          //Añadimos mensaje resultado de evalucion de la condicion
          rctx.addMessage(info);
          if (logger.isInfoEnabled())
              logger.info(info.toString());
          return false;
      }
      
      info.setMessage("La condición da resultado positivo");
      //Añadimos mensaje resultado de evalucion de la condicion
      rctx.addMessage(info);
      if (logger.isInfoEnabled())
          logger.info(info.toString());
      return true;
    }
}
