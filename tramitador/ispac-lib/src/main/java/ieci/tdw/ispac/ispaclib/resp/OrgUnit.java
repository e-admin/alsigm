/*
 * Created on 23-jul-2004
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

public class OrgUnit extends Responsible
{
	// Guardamos la entrada ya que es necesario xa el metodo
	// getOrgUnit que calcula la OrgUnit padre. Este calculo no
	// se hace en el constructor por motivos de eficiencia. Solo
	// calculamso el padre bajo demanda
	private IDirectoryEntry mentry;

	private OrgUnit mparent;
	
	public OrgUnit(String UID, String DN, String RelativeDN, String name)
	throws ISPACException
	{
		super(UID, DN, RelativeDN, name);
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		mentry = directory.getEntryFromUID(this.getUID());
		// TODO Probar si lo siguiente es necesario o con la llamada al constructor ya es suficiente
		// setDirectoryEntry(mentry);
	}
	
	public OrgUnit(String UID, String name)
	throws ISPACException
	{
		super(UID, name);
		
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		mentry = directory.getEntryFromUID(this.getUID());
		// TODO Probar si lo siguiente es necesario o con la llamada al constructor ya es suficiente
		// setDirectoryEntry(mentry);
	}

	public OrgUnit(String UID, String name, String respName)
	throws ISPACException
	{
		super(UID, name, respName);
		
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		mentry = directory.getEntryFromUID(this.getUID());
		// TODO Probar si lo siguiente es necesario o con la llamada al constructor ya es suficiente
		// setDirectoryEntry(mentry);
	}
	
	public OrgUnit(IDirectoryEntry entry)
	throws ISPACException
	{
		super(entry);
		mentry = entry;
	}

	// Calcula la OrgUnit padre. Este calculo no
	// se hace en el constructor por motivos de eficiencia. Solo
	// calculamso el padre bajo demanda
	public Responsible getOrgUnit() throws ISPACException
	{
		if (mparent == null)
		{
			IDirectoryEntry parent = mentry.getParentEntry();
			if (parent != null)
				mparent = new OrgUnit(parent);
		}
		return mparent;
	}

	public Collection getOrgUnits() throws ISPACException
	{
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		Set entryset = directory.getOrgUnitsFromUID(getUID());
		return RespFactory.createResponsibleSet(entryset);
	}

	public Collection getUsers() throws ISPACException
	{
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		Set entryset = directory.getUsersFromUID(getUID());
		return RespFactory.createResponsibleSet(entryset);
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
		return false;
	}
	
	public boolean isOrgUnit ()
	{
		return true;
	}
	
	public boolean isDomain ()
	{
		return false;
	}

}