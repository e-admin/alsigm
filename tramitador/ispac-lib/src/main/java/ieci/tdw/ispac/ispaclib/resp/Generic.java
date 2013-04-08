/*
 * Created on 23-jul-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaclib.resp;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


/**
 * @author juanin
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Generic extends Responsible
{
	/**
	 * @param entry
	 * @throws ISPACException
	 */
	public Generic(IDirectoryEntry entry)
	throws ISPACException
	{
		super(entry);
	}

	public Responsible getOrgUnit()
	throws ISPACException
	{
		return null;
	}

	public Collection getOrgUnits()
	throws ISPACException
	{
		return new HashSet ();
	}

	public Collection getUsers()
	throws ISPACException
	{
		return new HashSet();
	}

	public Collection getUserGroups()
	throws ISPACException
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
		return null;
	}

	public void loadObject(String xml) throws ISPACException
	{
	}

	public boolean isUser ()
	{
		return false;
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