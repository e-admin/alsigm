package ieci.tdw.ispac.ispaclib.invesdoc.directory;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryConnector;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.invesdoc.InvesDocConstants;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tecdoc.sbo.uas.std.UasMisc;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class InvesDocDirectoryConnector implements IDirectoryConnector {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(InvesDocDirectoryConnector.class);
	
    private String msPoolName = null;
    
	public InvesDocDirectoryConnector() 
	throws ISPACException
	{
//		ISPACConfiguration parameters = ISPACConfiguration.getInstance();
//		msPoolName = parameters.get(InvesDocConstants.INVESDOC_DIRECTORY_CONNECTOR_POOL_NAME);

	
//		ISPACConfiguration parameters = ISPACConfiguration.getInstance();
//		msPoolName = parameters.get(ISPACConfiguration.USERS_POOL_NAME);
		if (msPoolName==null) {
			OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
			if (info != null)
				msPoolName =  info.getUsersPoolName();
			else{
				ISPACConfiguration parameters = ISPACConfiguration.getInstance();
				msPoolName = parameters.get(InvesDocConstants.INVESDOC_DIRECTORY_CONNECTOR_POOL_NAME);
			}
		}				
	
	}
	
	public IDirectoryEntry login( String user, String password) 
	throws ISPACException
	{
		InvesDocEntry entry = null;
		
		DbCnt cnt = new DbCnt(msPoolName);
		
		cnt.getConnection();
		
		try
		{
			entry = InvesDocEntry.getUserEntry(user, cnt);
			String encrypt = UasMisc.encryptPassword(password, entry.getID());
			if (!encrypt.equals(entry.getPassword()))
			{
				throw new ISPACNullObject( "Clave Errónea");
			}
		} catch (ISPACException e) {
			throw e;
		} catch (Exception e) {
			logger.warn("Error en la autenticación del usuario [" + user + "]", e);
			throw new ISPACException(e);
		} finally {
			cnt.closeConnection();
		}
		
		return entry;
	}
	
	public IDirectoryEntry getEntryFromUserName( String user) 
	throws ISPACException
	{
		DbCnt cnt = new DbCnt(msPoolName);
		
		cnt.getConnection();
		
		try
		{
			return	InvesDocEntry.getUserEntry(user, cnt);
		}
		finally
		{
			cnt.closeConnection();
		}
	}
	
	public IDirectoryEntry getEntryFromRoot() 
	throws ISPACException
	{
		DbCnt cnt = new DbCnt(msPoolName);
		
		cnt.getConnection();
		
		try
		{
			return	InvesDocEntry.getEntryFromRoot( cnt);
		}
		finally
		{
			cnt.closeConnection();
		}
	}
	
	public IDirectoryEntry getEntryFromUID( String sUID) 
	throws ISPACException
	{
		DbCnt cnt = new DbCnt(msPoolName);
		
		cnt.getConnection();

		try
		{
			return InvesDocEntry.getEntryFromUID( sUID, cnt);
		}
		finally
		{
			cnt.closeConnection();
		}
	}
	
	public Set getMembersFromUID( String sUID) 
	throws ISPACException
	{
		DbCnt cnt = new DbCnt(msPoolName);
		
		cnt.getConnection();
		
		try
		{
			return InvesDocEntry.getMembersFromUID( sUID, cnt);
		}
		finally
		{
			cnt.closeConnection();
		}
	}

	public Set getAncestorsFromUID( String sUID) 
	throws ISPACException
	{
		DbCnt cnt = new DbCnt(msPoolName);
		
		cnt.getConnection();
		
		try
		{
			return InvesDocEntry.getAncestorsFromUID( sUID, cnt);
		}
		finally
		{
			cnt.closeConnection();
		}
	}
	
	public Set getAllGroups() 
	throws ISPACException
	{
		DbCnt cnt = new DbCnt(msPoolName);
		
		cnt.getConnection();
		
		try
		{
			return InvesDocEntry.getAllGroups( cnt);
		}
		finally
		{
			cnt.closeConnection();
		}
	}

	public Set getOrgUnitsFromUID( String sUID) 
	throws ISPACException
	{
		DbCnt cnt = new DbCnt(msPoolName);
		
		cnt.getConnection();
		
		try
		{
			return InvesDocEntry.getOrgUnitsFromUID(sUID, cnt);
		}
		finally
		{
			cnt.closeConnection();
		}
	}
	
	public Set getUsersFromUID( String sUID)
	throws ISPACException
	{
		DbCnt cnt = new DbCnt(msPoolName);
		
		cnt.getConnection();
		
		try
		{
			return InvesDocEntry.getUsersFromUID(sUID, cnt);
		}
		finally
		{
			cnt.closeConnection();
		}
	}
	
	public String getContextRoot() 
	throws ISPACException
	{
		ISPACConfiguration parameters = ISPACConfiguration.getInstance();
		return parameters.get(InvesDocConstants.INVESDOC_DIRECTORY_CONNECTOR_NAME);
	}
	
	public LinkedHashMap getPropertiesFromUID( String sUID) 
	throws ISPACException
	{
		return null;
	}

	public Set getEntryFromName(String name, String []elementos) throws ISPACException {
		
		LinkedHashSet entries = new LinkedHashSet();
		DbCnt cnt = new DbCnt(msPoolName);
		cnt.getConnection();
		try
		{
			String field=ISPACConfiguration.getInstance().get("INVESDOC_USER_NAME_FIELD");
			if(StringUtils.isBlank(field) ){
				field="NAME";
			}
			if(elementos==null){
				//Buscamos los grupos, dept y usuarios que contenga el name
				entries.addAll(InvesDocEntry.searchDepartments(cnt, name, field));
				entries.addAll(InvesDocEntry.searchGroups(cnt, name, field));
				entries.addAll(InvesDocEntry.searchUsers(cnt, name, field));
				
			}
			else{
				for(int i=0; i<elementos.length; i++){
					if(StringUtils.equalsIgnoreCase(elementos[i], IResponsible.PROPERTY_GROUP)){
						entries.addAll(InvesDocEntry.searchGroups(cnt, name, field));
					}
					else if(StringUtils.equalsIgnoreCase(elementos[i], IResponsible.PROPERTY_USER)){
						entries.addAll(InvesDocEntry.searchUsers(cnt, name, field));
					}
					else if(StringUtils.equalsIgnoreCase(elementos[i], IResponsible.PROPERTY_ORGUNIT)){
						entries.addAll(InvesDocEntry.searchDepartments(cnt, name, field));
					}
				}
			}
			return entries;
			
		}
		finally
		{
			cnt.closeConnection();
		}
		
	}
}
