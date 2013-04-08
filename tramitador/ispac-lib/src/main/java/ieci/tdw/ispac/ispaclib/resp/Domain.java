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

public class Domain extends Responsible {
	
	public Domain(String UID, String DN, String RelativeDN, String name) throws ISPACException
	{
		super(UID, DN, RelativeDN, name);
	}
	
	public Domain(String UID, String name) throws ISPACException
	{
		super(UID, name);
	}
	
	public Domain(String UID, String name, String respName) throws ISPACException
	{
		super(UID, name, respName);
	}
	
	public Domain(IDirectoryEntry entry) throws ISPACException
	{
		super(entry);
	}

	public Responsible getOrgUnit() throws ISPACException
	{
		return null;
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

	public Collection getUserGroups() throws ISPACException
	{
		return new HashSet();
	}

	public String getRespString()
	{
		String resplist = "'" + DBUtil.replaceQuotes(getUID()) + "'";
		return resplist;
	}

	public List getRespList() throws ISPACException
	{
		ArrayList resplist = new ArrayList();
		resplist.add(getUID());
		return resplist;
	}

	public boolean isInResponsibleList(String sUID)
	{
		return getUID().equalsIgnoreCase(sUID);
	}

	public String toXmlString()
	{
		return null;
	}

	public void loadObject(String xml) throws ISPACException {
	}

	public boolean isUser()
	{
		return false;
	}

	public boolean isGroup()
	{
		return false;
	}

	public boolean isOrgUnit()
	{
		return false;
	}

	public boolean isDomain() {
		return true;
	}

}