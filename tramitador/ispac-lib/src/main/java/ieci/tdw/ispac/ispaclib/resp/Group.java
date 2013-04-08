/*
 * Created on 22-jul-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaclib.resp;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.directory.DirectoryConnectorFactory;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryConnector;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * @author juanin
 *
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class Group extends Responsible
{

	private IDirectoryEntry mentry;

	public Group(String UID, String DN, String RelativeDN, String name) throws ISPACException
	{
		super(UID, DN, RelativeDN, name);
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		mentry = directory.getEntryFromUID(this.getUID());
		// TODO Probar si lo siguiente es necesario o con la llamada al constructor ya es suficiente
		// setDirectoryEntry(mentry);
	}
	
	public Group(String UID, String name) throws ISPACException
	{
		super(UID, name);
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		mentry = directory.getEntryFromUID(this.getUID());
		// TODO Probar si lo siguiente es necesario o con la llamada al constructor ya es suficiente
		// setDirectoryEntry(mentry);
	}

	
	public Group(String UID, String name, String respName) throws ISPACException
	{
		super(UID, name, respName);
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		mentry = directory.getEntryFromUID(this.getUID());
		// TODO Probar si lo siguiente es necesario o con la llamada al constructor ya es suficiente
		// setDirectoryEntry(mentry);
	}
	
	public Group(IDirectoryEntry entry) throws ISPACException
	{
		super(entry);
		mentry = entry;
	}

	public Responsible getOrgUnit()
	{
		return null;
	}

	public Collection getOrgUnits()
	{
		return new HashSet ();
	}

	public Collection getUsers() throws ISPACException
	{
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();

		Set members = directory.getMembersFromUID( mentry.getUID());
		return RespFactory.createResponsibleSet(members);
	}

	public Collection getUserGroups()
	{
		return new HashSet();
	}

	public String getRespString()
	{
		String resplist = "'" + DBUtil.replaceQuotes(getUID()) + "'";
		return resplist;
	}

	public List getRespList()
	throws ISPACException
	{
	    ArrayList resplist=new ArrayList();
		resplist.add(getUID());
		return resplist;
	}

	public boolean isInResponsibleList( String sUID)
	{
		return getUID().equalsIgnoreCase( sUID);
	}

	public String toXmlString()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispaclib.session.persistence.Persistable#loadObject(java.lang.String)
	 */
	public void loadObject(String xml) throws ISPACException
	{
		// TODO Auto-generated method stub

	}

	public boolean isUser ()
	{
		return false;
	}
	
	public boolean isGroup ()
	{
		return true;
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