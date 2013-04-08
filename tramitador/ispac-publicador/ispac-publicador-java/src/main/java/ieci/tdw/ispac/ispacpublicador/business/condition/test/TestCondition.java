package ieci.tdw.ispac.ispacpublicador.business.condition.test;

import ieci.tdw.ispac.ispacpublicador.business.condition.ICondition;
import ieci.tdw.ispac.ispacpublicador.business.context.MilestoneContext;
import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ConditionException;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * Condición para pruebas.
 *
 */
public class TestCondition implements ICondition {

	/** Logger de la clase. */
    protected static final Logger logger = 
    	Logger.getLogger(TestCondition.class);
    
    
    public boolean evaluate(RuleContext rctx) throws ConditionException {
    	
        if (logger.isInfoEnabled()) {
            logger.info("Evaluación de la Condición: " 
            		+ this.getClass().getName());
        }

        if (logger.isDebugEnabled()){
            Map pps = rctx.getProperties();
            Map.Entry entrada;
	        for (Iterator iter = pps.entrySet().iterator(); iter.hasNext();) {
	            entrada =  (Map.Entry) iter.next();
	            logger.debug(entrada.getKey() + ": " + entrada.getValue());
	        }
        }
        
        return true;
    }

    public boolean matchContext(MilestoneContext ctx, MilestoneContext ctxComp) 
    		throws ConditionException {

    	if (logger.isDebugEnabled()) {
            logger.debug("Unificación de la Condición ["
            		+ this.getClass().getName() + "] ejecutada");
        }

        return true;
    }
}