package ieci.tdw.ispac.api.rule.processes;

import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;

public class SetCalendarRule implements IRule {

//	private static final Logger logger = 
//		Logger.getLogger(SetCalendarRule.class);
	
	private final static int ID_DEFAULT_CALENDAR = 1;
	
    public boolean init(IRuleContext rulectx) throws ISPACRuleException {
        return true;
    }

    public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
        return true;
    }

    public Object execute(IRuleContext rulectx) throws ISPACRuleException {
       
//        try {

        	//Asociar el calendario por defecto al proceso del expediente
	        //rulectx.getClientContext().getAPI().getTransactionAPI().setCalendarProcess(rulectx.getProcessId(), ID_DEFAULT_CALENDAR);
	        
	        return new Integer(ID_DEFAULT_CALENDAR);
	        
//        } catch(ISPACException e) {
//        	logger.error("Error en la regla " + this.getClass().getName(), e);
//            throw new ISPACRuleException(
//            		"Error Asignando Valores al Expediente.", e);
//        }
    }
   
    public void cancel(IRuleContext rulectx) throws ISPACRuleException {}
}