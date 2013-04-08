/*
 * Created on 08-jun-2004
 *
 */
package ieci.tdw.ispac.api.rule;

import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.IClientContext;

import java.util.Date;

import org.w3c.dom.Document;

/**
 * @author juanin
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public interface IRuleContext
{

	public Document getDocContext() throws ISPACRuleException;

	public String getInfoMessage();

	public IClientContext getClientContext();

	public void setInfoMessage(String message);

	public int getProcedureId() throws ISPACRuleException;

	public int getStageProcedureId() throws ISPACRuleException;

	public int getTaskProcedureId() throws ISPACRuleException;

	public int getProcessId() throws ISPACRuleException;

	public int getSubProcessId() throws ISPACRuleException;
	
	public String getNumExp() throws ISPACRuleException;

	public int getStageId() throws ISPACRuleException;

	public int getTaskId() throws ISPACRuleException;

	public int getInt(String property) throws ISPACRuleException;

	public Date getDate(String property) throws ISPACRuleException;

	public String get(String property) throws ISPACRuleException;
	
	public IItem getItem();
	
	public int getObjectId()  throws ISPACRuleException;
	
	public int getObjectType()  throws ISPACRuleException;
	
	public int getEvent()  throws ISPACRuleException;
	
}