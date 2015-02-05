package ieci.tdw.ispac.ispacpublicador.business.action.test;

import ieci.tdw.ispac.ispacpublicador.business.action.DefaultAction;
import ieci.tdw.ispac.ispacpublicador.business.attribute.AttributeContext;
import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ActionException;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Acción de pruebas.
 * 
 */
public class TestAction extends DefaultAction {

	/** Logger de la clase. */
    private static final Logger logger = Logger.getLogger(TestAction.class);

    
    /**
     * Constructor.
     * 
     */
    public TestAction() {
    	super();
    }

    /**
     * Ejecuta la acción.
     * @param rctx Contexto de ejecución de la regla
     * @param attContext Atributos con información extra, utilizados dentro de la ejecución de la regla.
     * @return true si la ejecución termina correctamente, false en caso contrario.
     * @throws ActionException
     */
    public boolean execute(RuleContext rctx, AttributeContext attContext) 
    		throws ActionException {
    	
        if (logger.isInfoEnabled()) {
            logger.info("Acción [" + this.getClass().getName() + "] ejecutada");
        }

        if (logger.isDebugEnabled()){
            Map pps = rctx.getProperties();
            logger.info("-----------Propiedades----------------------");
            Map.Entry entrada;
	        for (Iterator iter = pps.entrySet().iterator(); iter.hasNext();) {
	            entrada =  (Map.Entry) iter.next();
	            logger.debug(entrada.getKey() + ": " + entrada.getValue());
	        }

	        Map atts = attContext.getProperties();
            logger.info("-----------Atributos------------------------");
            logger.info("at01: "+attContext.getAttribute("at01"));
            for (Iterator iter = atts.entrySet().iterator(); iter.hasNext();) {
	            entrada = (Map.Entry) iter.next();
	                logger.info(entrada.getKey()+":"+entrada.getValue());
	        }
        }
        
        return true;
    }
}