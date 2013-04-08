/*
 * Created on 08-jun-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.api.rule;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;
/**
 * @author   juanin  To change the template for this generated type comment go to  Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RuleContextBuilder
{
	private final IClientContext mcctx;
	private final HashMap itemmap;
	
	private IItem item;

	public RuleContextBuilder(IClientContext cctx) throws ISPACException
	{
		itemmap=new HashMap();
		initContext();
		mcctx=cctx;
		item=null;
	}

	public void addContext(IProcess process)
	throws ISPACException
	{
		itemmap.put(RuleProperties.RCTX_PROCESS,XmlTag.newTag(RuleProperties.RCTX_PROCESS,process.getKeyInt()));
		itemmap.put(RuleProperties.RCTX_PROCEDURE,XmlTag.newTag(RuleProperties.RCTX_PROCEDURE,process.getInt("ID_PCD")));
		itemmap.put(RuleProperties.RCTX_NUMEXP,XmlTag.newTag(RuleProperties.RCTX_NUMEXP,process.getString("NUMEXP")));
		itemmap.put(RuleProperties.RCTX_STAGE,XmlTag.newTag(RuleProperties.RCTX_STAGE,0));
		itemmap.put(RuleProperties.RCTX_STAGEPCD,XmlTag.newTag(RuleProperties.RCTX_STAGEPCD,0));
		itemmap.put(RuleProperties.RCTX_TASK,XmlTag.newTag(RuleProperties.RCTX_TASK,0));
		itemmap.put(RuleProperties.RCTX_TASKPCD,XmlTag.newTag(RuleProperties.RCTX_TASKPCD,0));
	}

	public void addContext(TXFaseDAO stage)
	throws ISPACException
	{
		itemmap.put(RuleProperties.RCTX_PROCESS,XmlTag.newTag(RuleProperties.RCTX_PROCESS,stage.getInt("ID_EXP")));
		itemmap.put(RuleProperties.RCTX_PROCEDURE,XmlTag.newTag(RuleProperties.RCTX_PROCEDURE,stage.getInt("ID_PCD")));
		itemmap.put(RuleProperties.RCTX_NUMEXP,XmlTag.newTag(RuleProperties.RCTX_NUMEXP,stage.getString("NUMEXP")));
		itemmap.put(RuleProperties.RCTX_STAGE,XmlTag.newTag(RuleProperties.RCTX_STAGE,stage.getKeyInt()));
		itemmap.put(RuleProperties.RCTX_STAGEPCD,XmlTag.newTag(RuleProperties.RCTX_STAGEPCD,stage.getInt("ID_FASE")));
		itemmap.put(RuleProperties.RCTX_TASK,XmlTag.newTag(RuleProperties.RCTX_TASK,0));
		itemmap.put(RuleProperties.RCTX_TASKPCD,XmlTag.newTag(RuleProperties.RCTX_TASKPCD,0));
	}

	public void addContext(TXTramiteDAO task)
	throws ISPACException
	{
		itemmap.put(RuleProperties.RCTX_PROCESS,XmlTag.newTag(RuleProperties.RCTX_PROCESS,task.getInt("ID_EXP")));
		itemmap.put(RuleProperties.RCTX_PROCEDURE,XmlTag.newTag(RuleProperties.RCTX_PROCEDURE,task.getInt("ID_PCD")));
		itemmap.put(RuleProperties.RCTX_NUMEXP,XmlTag.newTag(RuleProperties.RCTX_NUMEXP,task.getString("NUMEXP")));
		itemmap.put(RuleProperties.RCTX_STAGE,XmlTag.newTag(RuleProperties.RCTX_STAGE,task.getInt("ID_FASE_EXP")));
		itemmap.put(RuleProperties.RCTX_STAGEPCD,XmlTag.newTag(RuleProperties.RCTX_STAGEPCD,task.getInt("ID_FASE_PCD")));
		itemmap.put(RuleProperties.RCTX_TASK,XmlTag.newTag(RuleProperties.RCTX_TASK,task.getKeyInt()));
		itemmap.put(RuleProperties.RCTX_TASKPCD,XmlTag.newTag(RuleProperties.RCTX_TASKPCD,task.getInt("ID_TRAMITE")));
	}

	public void initContext()
	throws ISPACException
	{
		itemmap.put(RuleProperties.RCTX_PROCESS,XmlTag.newTag(RuleProperties.RCTX_PROCESS,0));
		itemmap.put(RuleProperties.RCTX_PROCEDURE,XmlTag.newTag(RuleProperties.RCTX_PROCEDURE,0));
		itemmap.put(RuleProperties.RCTX_NUMEXP,XmlTag.newTag(RuleProperties.RCTX_NUMEXP,0));
		itemmap.put(RuleProperties.RCTX_STAGE,XmlTag.newTag(RuleProperties.RCTX_STAGE,0));
		itemmap.put(RuleProperties.RCTX_STAGEPCD,XmlTag.newTag(RuleProperties.RCTX_STAGEPCD,0));
		itemmap.put(RuleProperties.RCTX_TASK,XmlTag.newTag(RuleProperties.RCTX_TASK,0));
		itemmap.put(RuleProperties.RCTX_TASKPCD,XmlTag.newTag(RuleProperties.RCTX_TASKPCD,0));
	}	
	
	public void addContext(String itemKey,String itemValue)
	{
		//itemmap.put(itemKey,XmlTag.newTag(itemKey,XmlTag.newCDATA(itemValue)));
	 	itemmap.put(itemKey,XmlTag.newTag(itemKey,XmlTag.newCDATA(StringEscapeUtils.escapeXml(itemValue)))); 
	}
	
	/**
	 * @return Returns the item.
	 */
	public IItem getItem() {
		return item;
	}
	/**
	 * @param item The item to set.
	 */
	public void setItem(IItem item) {
		this.item = item;
	}

	public void addContext(Map parammap)
	{
	    if (parammap==null)
	        return;

		Iterator it=parammap.entrySet().iterator();
		while (it.hasNext())
        {
		    Map.Entry var = (Map.Entry) it.next();
		    String key="";
		    String value="";

		    if (var.getKey()!=null)
		        key=var.getKey().toString();

		    if (var.getValue()!=null)
		        value=var.getValue().toString();

		    addContext(key,value);
        }
	}

	public void addContext(IRuleContextParams rctxobj)
	{
	    itemmap.put(RuleProperties.RCTX_PROCESS,XmlTag.newTag(RuleProperties.RCTX_PROCESS,rctxobj.getRuleProcId()));
		itemmap.put(RuleProperties.RCTX_PROCEDURE,XmlTag.newTag(RuleProperties.RCTX_PROCEDURE,rctxobj.getRuleProcedureId()));
		itemmap.put(RuleProperties.RCTX_NUMEXP,XmlTag.newTag(RuleProperties.RCTX_NUMEXP,rctxobj.getRuleNumexp()));
		itemmap.put(RuleProperties.RCTX_STAGE,XmlTag.newTag(RuleProperties.RCTX_STAGE,rctxobj.getRuleStageId()));
		itemmap.put(RuleProperties.RCTX_STAGEPCD,XmlTag.newTag(RuleProperties.RCTX_STAGEPCD,rctxobj.getRuleStagePCDId()));
		itemmap.put(RuleProperties.RCTX_TASK,XmlTag.newTag(RuleProperties.RCTX_TASK,rctxobj.getRuleTaskId()));
		itemmap.put(RuleProperties.RCTX_TASKPCD,XmlTag.newTag(RuleProperties.RCTX_TASKPCD,rctxobj.getRuleTaskPCDId()));
		addContext(rctxobj.getRuleParameters());
	}


	public void addContext(String sXMLTxt)
	throws ISPACException
	{
		//TODO Implementar añadir tags específicos.
	}

	public IRuleContext getRuleContext()
	throws ISPACException
	{
		String tags="";
		Iterator it=itemmap.entrySet().iterator();
		while (it.hasNext())
		{
			tags+=(String)((Map.Entry)it.next()).getValue();
		}

//		String xml="<?xml version='1.0' encoding='ISO-8859-1'?>"+
//			"<ispac_context>"+tags+"</ispac_context>";

		String xml= XmlTag.getXmlInstruction("ISO-8859-1")+
					XmlTag.newTag("ispac_context",tags);

		Document docxml=XMLDocUtil.newDocument(xml);
		
		return new RuleContext(docxml, mcctx, getItem());
	}

	public void newContext()
	{
		itemmap.clear();
	}
}
