package ieci.tdw.ispac.ispaclib.resp;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.directory.DirectoryConnectorFactory;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryConnector;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.w3c.dom.Document;

public class User extends Responsible
{
	private IDirectoryEntry mentry;

	private OrgUnit mdept;

	private final LinkedHashMap mgroupmap;

	private boolean mbGroupsCalculated = false;

	public User(String xml) throws ISPACException
	{
		super();
		loadObject (xml);
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		mentry = directory.getEntryFromUID(getUID());
		setDirectoryEntry(mentry);
		mdept = new OrgUnit(mentry.getParentEntry());
		mgroupmap = new LinkedHashMap();
	}

	public User(IDirectoryEntry entry) throws ISPACException
	{
		super(entry);
		mentry = entry;
		mdept = new OrgUnit(entry.getParentEntry());
		mgroupmap = new LinkedHashMap();
	}
	
	public User(String id, String name) throws ISPACException {
		super(id, name);
		mentry = null;
		mdept = null;
		mgroupmap = new LinkedHashMap();
	}

	//public Responsible getDepartament()
	public Responsible getOrgUnit() throws ISPACException
	{
		return mdept;
	}

	public Collection getOrgUnits()
	{
		return new HashSet ();
	}

	public Collection getUsers() throws ISPACException
	{
		return new HashSet();
	}

	public Collection getUserGroups() throws ISPACException
	{
		if (!mbGroupsCalculated)
		{
			Iterator it = mentry.getGroupEntries().iterator();
			while (it.hasNext())
			{
				Group group = new Group((IDirectoryEntry) it.next());
				mgroupmap.put(group.getUID(), group);
			}
			mbGroupsCalculated = true;
		}

		return mgroupmap.values();
	}

	public List getRespList()
	throws ISPACException
	{
	    ArrayList resplist=new ArrayList();
		resplist.add(getUID());
		resplist.add(getOrgUnit().getUID());
		Iterator it = getUserGroups().iterator();
		while (it.hasNext())
		{
			Responsible resp = (Responsible) it.next();
			resplist.add(resp.getUID());
		}
		return resplist;
	}

	public String getRespString()
	throws ISPACException
	{
		String resplist = "'" + DBUtil.replaceQuotes(getUID()) + "','" + DBUtil.replaceQuotes(getOrgUnit().getUID()) + "'";
		Iterator it = getUserGroups().iterator();
		while (it.hasNext())
		{
			Responsible resp = (Responsible) it.next();
			resplist += ",'" + DBUtil.replaceQuotes(resp.getUID()) + "'";
		}

		return resplist;
	}

	public boolean isInResponsibleList( String sUID)
	throws ISPACException
	{
		Responsible responsible;

		if (sUID.equalsIgnoreCase( getUID()))
			return true;
		if (sUID.equalsIgnoreCase( getOrgUnit().getUID()))
			return true;

		Iterator iterator = getUserGroups().iterator();
		while (iterator.hasNext())
		{
			responsible = (Responsible) iterator.next();
			if (sUID.equalsIgnoreCase( responsible.getUID()))
				return true;
		}

		return false;
	}

	public String toXmlString()
	{
		String userXml = getXmlValues();
		return XmlTag.newTag("user", userXml);
	}

	public void loadObject(String xml) throws ISPACException
	{
		Document xmldoc = XMLDocUtil.newDocument(xml);

		set(PROPERTY_UID, XPathUtil.get(xmldoc, "/user/value[@id =1]/text()"));
		set(PROPERTY_NAME, XPathUtil.get(xmldoc, "/user/value[@id =2]/text()"));
	}

	public boolean isUser ()
	{
		return true;
	}
	
	public boolean isGroup ()
	{
		return false;
	}
	
	public boolean isOrgUnit ()
	{
		return false;
	}
	
	public boolean isDomain ()
	{
		return false;
	}
	
}