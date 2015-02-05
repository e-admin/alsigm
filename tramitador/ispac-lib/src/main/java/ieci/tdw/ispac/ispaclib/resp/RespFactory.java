package ieci.tdw.ispac.ispaclib.resp;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.directory.DirectoryConnectorFactory;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryConnector;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Node;

public class RespFactory
{
	public static Responsible createResponsible(IDirectoryEntry entry)
	throws ISPACException
	{
		switch(entry.getEntryType())
		{
		case IDirectoryEntry.ET_PERSON:
			return new User(entry);
		case IDirectoryEntry.ET_GROUP:
			return new Group(entry);
		case IDirectoryEntry.ET_UNIT:
		case IDirectoryEntry.ET_ORGANIZATION:
			return new OrgUnit(entry);
		case IDirectoryEntry.ET_DOMAIN:
			return new Domain(entry);
		default:
			return new Generic(entry);
		}
	}
	
	public static Responsible createResponsible (String uid)
	throws ISPACException
	{
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		IDirectoryEntry entry = directory.getEntryFromUID(uid);
		return createResponsible (entry);
	}
	
	public static Collection createResponsibleSet(List uids)
	throws ISPACException
	{
		Set set = new HashSet ();
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		Iterator iter = uids.iterator();
		while (iter.hasNext())
		{
			set.add(directory.getEntryFromUID((String) iter.next()));
		}
		return createResponsibleSet(set);
	}
	
	public static Collection createResponsibleSet(Set entryset)
	throws ISPACException
	{
		LinkedHashSet respset=new LinkedHashSet();
		Iterator it=entryset.iterator();
		while (it.hasNext())
		{
			respset.add(RespFactory.createResponsible((IDirectoryEntry)it.next()));
		}
		return respset;
	}
	
	public static Group createGroup(Node elem)
	throws ISPACException
	{
		String uid=XPathUtil.get(elem,"value[@id =1]/text()");
		String dn=XPathUtil.get(elem,"value[@id =2]/text()");
		String relativedn=XPathUtil.get(elem,"value[@id =3]/text()");
		String name=XPathUtil.get(elem,"value[@id =4]/text()");
		
		return new Group(uid,dn,relativedn,name);
	}
	
	public static OrgUnit createOrgUnit(Node elem)
	throws ISPACException
	{
		String uid=XPathUtil.get(elem,"value[@id =1]/text()");
		String dn=XPathUtil.get(elem,"value[@id =2]/text()");
		String relativedn=XPathUtil.get(elem,"value[@id =3]/text()");
		String name=XPathUtil.get(elem,"value[@id =4]/text()");
		
		return new OrgUnit(uid,dn,relativedn,name);
	}	
}
