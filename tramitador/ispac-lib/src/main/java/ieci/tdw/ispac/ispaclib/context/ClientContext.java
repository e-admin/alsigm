package ieci.tdw.ispac.ispaclib.context;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.dao.session.SessionDAO;
import ieci.tdw.ispac.ispaclib.dao.session.SsVariableDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.resp.User;
import ieci.tdw.ispac.ispaclib.session.persistence.Persistable;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Implementación del contexto de cliente.
 * 
 */
public class ClientContext extends HashMap implements IClientContext, Persistable, Serializable {
	
	protected DbCnt mTXConnection;
	//protected HashSet cntset;
	protected DbCnt mConnection;
	protected int cntCount;
	protected String appLanguage;
	protected StateContext mStateContext;
	

	public ClientContext()
	{
		super();
		mTXConnection = null;
		//cntset = new HashSet();
		mConnection = null;
		cntCount = 0;
		//IInvesflowAPI api=new InvesflowAPI(this);
		//this.put(MBR_INVESFLOWAPI,api);
	}
	
	public ClientContext(DbCnt cnt)
	{
		super();
		mTXConnection = cnt;
		mConnection = null;
		cntCount = 0;
	}

	public void finalize()
	{
		if (mTXConnection!=null)
		{
			try
			{
				releaseTX();
			} catch (ISPACException e)
			{
				//TODO Añadir al log que no se ha podido liberar una transacción de BBDD
			}
			//TODO Añadir al log que no se ha liberado una transacción de BBDD
		}
		
		//if (cntset.size()>0)
		if (cntCount>0)
		{
			//TODO Añadir al log que no se ha liberado debidamente conexiones de BBDD
		}
	}


	public IInvesflowAPI getAPI()
	{
		return (IInvesflowAPI)get(ClientCtxConstants.MBR_INVESFLOWAPI);
	}

	public void setAPI(IInvesflowAPI api)
	{
		put(ClientCtxConstants.MBR_INVESFLOWAPI, api);
	}


	public DbCnt getConnection() throws ISPACException
	{
		if (mTXConnection != null)
			return mTXConnection;

		/*
		DbCnt cnt = new DbCnt();
		cnt.getConnection();
		cntset.add(cnt);
		return cnt;
		*/
		
		if (cntCount == 0) {
			
			mConnection = new DbCnt();
			mConnection.getConnection();
		}
		cntCount++;
		
		return mConnection;
	}
	public void releaseConnection(DbCnt cnt)
	{
		if (mTXConnection == cnt)
			return;

		cntCount--;
		if (cntCount == 0) {
		
			releaseInternalCnt(cnt);
		}
	}

	private void releaseInternalCnt(DbCnt cnt)
	{
		if (cnt != null)
		{
			cnt.closeConnection();
			//cntset.remove(cnt);
		}
	}

	public void releaseAllConnections()
	{
		/*
		Iterator it=cntset.iterator();
		while (it.hasNext())
		{
			DbCnt cnt = (DbCnt) it.next();
			cnt.closeConnection();
		}
		cntset.clear();
		*/
		
		releaseInternalCnt(mConnection);
		mConnection = null;
		releaseInternalCnt(mTXConnection);
		mTXConnection = null;
	}

	public DbCnt beginTX() throws ISPACException
	{
		if (mTXConnection == null) {
			
			//mTXConnection = getConnection();
			mTXConnection = new DbCnt();
			mTXConnection.getConnection();
		}

		mTXConnection.openTX();

		return mTXConnection;
	}

	public boolean ongoingTX() throws ISPACException
	{
		if (mTXConnection == null)
			return false;

		return mTXConnection.ongoingTX();
	}


	public void endTX(boolean bCommit) throws ISPACException
	{
		if (mTXConnection == null)
			return;
		//throw new ISPACException("No existe ninguna transacción en curso");

		mTXConnection.closeTX(bCommit);
		releaseInternalCnt(mTXConnection);
		mTXConnection = null;
	}

	public TransactionContainer getTXContainer()
	{
	    return new TransactionContainer(this);
	}

	public void releaseTX() throws ISPACException
	{
		if (mTXConnection != null)
		{
			mTXConnection.closeTX(false);
			releaseInternalCnt(mTXConnection);
			mTXConnection = null;
		}
	}

	public String getRespId()
	{
		Responsible resp = getUser();
		return (resp != null ? resp.getUID() : null);
	}

	public String getTicket()
	{
		return (String) get(ClientCtxConstants.MBR_TICKET);
	}

	public void setTicket(String ticket)
	{
		put(ClientCtxConstants.MBR_TICKET, ticket);
	}

	public void setRemoteHost(String remoteHost)
	{
		put(ClientCtxConstants.MBR_HOSTNAME, remoteHost);
	}
	
	public void setUserName(String userName)
	{
		put(ClientCtxConstants.MBR_USERNAME, userName);
	}
	
	public void setRespName(String respName)
	{
		put(ClientCtxConstants.MBR_RESPNAME, respName);
	}

	public String getSsVariable(String name) throws ISPACException
	{
		if (containsKey(name))
			return (String)get(name);

		DbCnt cnt = getConnection();
		try
		{
			SessionDAO ss = new SessionDAO(cnt, getTicket());
			SsVariableDAO var = ss.getVariable(cnt, name);
			if (var==null)
			    return null;
			return (String) var.get("VALOR");
		}finally
		{
		    releaseConnection(cnt);
		}
	}

	public void setSsVariable(String name, String value) throws ISPACException
	{
		DbCnt cnt = getConnection();
		try
		{
			SessionDAO session = new SessionDAO(cnt, getTicket());
			SsVariableDAO var = session.getVariable(cnt, name);
			if (var != null)
			{
				var.set("VALOR", value);
			} else
			{
				var = new SsVariableDAO(cnt);
				var.createNew(cnt);
				var.set("ID_SES", getTicket());
				var.set("NOMBRE", name);
				var.set("VALOR", value);
			}
			var.store(cnt);
			put(name, value);
		} finally
		{
		    releaseConnection(cnt);
		}
	}

	public void deleteSsVariable(String name) throws ISPACException
	{
		DbCnt cnt = getConnection();
		try
		{
			SessionDAO ss = new SessionDAO(cnt, getTicket());
			SsVariableDAO var = ss.getVariable(cnt, name);
			if (var != null)
				var.delete(cnt);
		}finally
		{
		    releaseConnection(cnt);
		}
		this.remove(name);
	}

	public String toXmlString()
	{
		String xml = "";
		Collection attrs = values();
		Iterator iter = attrs.iterator();
		while (iter.hasNext())
		{
			Object attr = iter.next();
			if (Persistable.class.isInstance(attr))
			{
				xml += ((Persistable) attr).toXmlString();
			}
		}
		xml = XmlTag.newTag("clientcontext", xml);
		return xml;
	}

	public void loadObject(String xml) throws ISPACException
	{
		Document doc = XMLDocUtil.newDocument(xml);
		Node userInfoNode = XPathUtil.selectNode(doc, "/clientcontext/user");
		User responsible = new User(XMLDocUtil.toString(userInfoNode));
		setUser(responsible);
	}

	public void setUser(Responsible responsible)
	{
		put(ClientCtxConstants.MBR_USER, responsible);
		put(ClientCtxConstants.MBR_USERNAME, responsible.getName());
		put(ClientCtxConstants.MBR_RESPNAME, responsible.getRespName());
	}

	public Responsible getUser()
	{
		return (Responsible)get(ClientCtxConstants.MBR_USER);
	}
	
	public IResponsible getResponsible()
	{
		return (IResponsible)get(ClientCtxConstants.MBR_USER);
	}

    public Locale getLocale()
    {
        return (Locale)get(ClientCtxConstants.MBR_LOCALE);
    }
    
    public String getAppLanguage()
    throws ISPACException
    {	
    	if (appLanguage == null) {
    		
			String clientLanguage = getLocale().getLanguage();
			
			String varLanguages = ConfigurationMgr.getVarGlobal(this, ConfigurationMgr.LANGUAGES);
			if (StringUtils.isEmpty(varLanguages)) {
				appLanguage = "es";
			}
			else {
				String[] languages = varLanguages.split(";");
				String defaultAppLanguage = languages[0];
				
				for (int i = 0; i < languages.length; i++) {
					if (clientLanguage.equalsIgnoreCase(languages[i])) {
						
						appLanguage = clientLanguage;
						return appLanguage;
					}
				}
				
				appLanguage = defaultAppLanguage.toLowerCase();
			}
    	}
    	
    	return appLanguage;
    }

    public void setLocale(Locale locale)
    {
        put(ClientCtxConstants.MBR_LOCALE,locale);
    }

	/**
	 * @return El contexto del estado actual de tramitación.
	 */
	public StateContext getStateContext() {
		return mStateContext;
	}

	/**
	 * @param stateContext Contexto del estado actual de tramitación
	 */
	public void setStateContext(StateContext stateContext) {
		mStateContext = stateContext;
	}
       
}