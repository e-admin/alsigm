/*
 * Created on 14-nov-2005
 *
 */
package ieci.tdw.ispac.ispacpublicador.business.context;

import ieci.tdw.ispac.ispacpublicador.business.rule.RuleError;
import ieci.tdw.ispac.ispacpublicador.business.rule.RuleMessage;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * Contexto de procesamiento de una regla.
 * @author Ildefonso Noreña
 *
 */

//TODO Estudiar: No seria mejor llamar esta clase ActiveMilestoneContext, ya que mantiene informacion del Hito Activo, tanto del contexto como del procesamiento de las reglas a él asociadas.
public class RuleContext extends MilestoneContext {

    
    /**
     * mantendrá la lista de mensajes que se producen durante el procesamiento de una regla. 
     */
    private List messages;
    
    
    /**
     * Almacenará un error si es que se produce en el procesamiento de una regla.
     */
    private RuleError error;
    
    /**
     * @param xmlContext
     */
    public RuleContext(String xmlContext) {
        super(xmlContext,ContextProperties.CTX_ROOT);
        error = null;
        messages = new LinkedList();        
    }
    
    public void addMessage(RuleMessage e){
        if (e instanceof RuleError)
            setError((RuleError)e);
        messages.add(e);
    }

    /**
     * Limpia la lista de mensajes producidos 
     */
    public void clearMessages(){
        messages.clear();
    }
    

    
    public String toString(){
        StringBuffer buf = new StringBuffer("\nRESULTADO DEL PROCESAMIENTO DE REGLAS PARA HITO [ID_HITO:'"+ getIdHito()+"', ID_APLICACION:'"+getIdAplicacion()+"', ID_SISTEMA:'"+getIdSistema()+"']\n");
        for (Iterator iter = messages.iterator(); iter.hasNext();) {
            RuleMessage message = (RuleMessage) iter.next();
            buf.append(message.toString()+"\n");
        }
        return buf.toString();
    }

    /**
     * @return una lista con todos los mensajes producidos durante el procesamiento de una regla.
     */
    public List getMessages(){
        return messages;
    }

    /**
     * @return Returns the error.
     */
    public RuleError getError() {
        return error;
    }
    /**
     * @param error The error to set.
     */
    public void setError(RuleError error) {
        this.error = error;
    }
    
    

	public int getIdAplicacion(){
	    return getInt(ContextProperties.CTX_APPLICATION);
	}
	public String getIdSistema(){
	    return get(ContextProperties.CTX_SYSTEM);
	}	
	    
    
}
