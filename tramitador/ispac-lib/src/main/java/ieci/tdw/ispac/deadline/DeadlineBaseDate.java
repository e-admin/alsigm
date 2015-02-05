/*
 * Created on 06-oct-2004
 *  
 */
package ieci.tdw.ispac.deadline;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;

import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * DeadlineBaseDate
 * @version   $Revision: 1.3 $ $Date: 2007/09/13 11:08:14 $
 */
public class DeadlineBaseDate
{
	private final Document mdeadlineXML;
	private final ClientContext mcontext;
	private final EventManager meventmgr;
	private final String mnumexp;

	public DeadlineBaseDate(ClientContext context,EventManager eventmgr,Document deadlineXML,String numexp) 
	{
		mdeadlineXML=deadlineXML;
		mcontext=context;
		meventmgr=eventmgr;
		mnumexp=numexp;
	}

	public Date getBaseDate() throws ISPACException
	{
		if (XPathUtil.existNode(mdeadlineXML, "/deadline/basedate/entity"))
			return getEntityBaseDate();
		
		if (XPathUtil.existNode(mdeadlineXML, "/deadline/basedate/currentdate"))
			return getCurrentBaseDate();
		
		if (XPathUtil.existNode(mdeadlineXML, "/deadline/basedate/rule"))
			return getRuleBaseDate();
		
		if (XPathUtil.existNode(mdeadlineXML, "/deadline/basedate/date"))
			return getCurrentBaseDate();
		
		throw new ISPACException("No existe plazo base");
	}
	
	protected Date getEntityBaseDate() throws ISPACException
	{
		int entityid = XPathUtil.getInt(mdeadlineXML, "/deadline/basedate/entity/@id");

		Node node = XPathUtil.selectNode(mdeadlineXML, "/deadline/basedate/entity/@field");
		if (node == null)
			throw new ISPACException("No existe plazo base");

		return getEntityDate(entityid, node.getNodeValue());
	}

	protected Date getEntityDate(int nIdEntity, String property)
	throws ISPACException
	{
		DbCnt cnt = null;
		try
		{
			cnt=mcontext.getConnection();
			CollectionDAO entities=EntityFactoryDAO.getInstance().getEntities(cnt,nIdEntity,mnumexp,null);

			if (!entities.next())
				throw new ISPACException("No existe ningun registro de la entidad asociada al"+
						" expediente para poder calcular el plazo");
			
			ObjectDAO objdao=entities.value();
			return objdao.getDate(property);
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en getEntityDate:", e);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}
	
	protected Date getCurrentBaseDate() throws ISPACException
	{
		//XPathUtil.getDate(mdeadlineXML, "/deadline/basedate/currentdate");
	    return new Date();
	}
	
	protected Date getRuleBaseDate() throws ISPACException
	{
		int ruleid = XPathUtil.getInt(mdeadlineXML, "/deadline/basedate/rule/@id");
		Date date=null;
		
		try
		{
			date=(Date)meventmgr.executeRule(ruleid);
		} catch (ClassCastException e)
		{
			throw new ISPACException("El valor obtenido por la ejecución de la regla"+
					" como fecha base del plazo no es del tipo adecuado",e);
		}
	
		return date;	
	}
}