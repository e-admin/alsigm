package ieci.tdw.ispac.ispaclib.ldap.directory;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryConnector;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;

import java.util.LinkedHashMap;
import java.util.Set;

public class LdapDirectoryConnector implements IDirectoryConnector {

	/**
	 * Clase de utilidad.
	 */
	private LdapDirectoryConnectorHelper helper = null;

	/**
	 * Contructor de la clase. Establece conexion con directorio como
	 * administrador
	 * 
	 * @throws ISPACException
	 *             si se produce error en la conexion
	 */
	public LdapDirectoryConnector() throws ISPACException {
		helper = LdapDirectoryConnectorHelper.getInstance();
	}

	public Set getAllGroups() throws ISPACException {
		return helper.getAllGroups();
	}

	public Set getAncestorsFromUID(String uid) throws ISPACException {
		return helper.getAncestorsFromUID(uid);
	}

	public String getContextRoot() throws ISPACException {
		return helper.getContextRoot();
	}

	public IDirectoryEntry getEntryFromRoot() throws ISPACException {
		return helper.getEntryFromRoot();
	}

	public IDirectoryEntry getEntryFromUID(String uid) throws ISPACException {
		return helper.getEntryFromUID(uid);
	}

	public IDirectoryEntry getEntryFromUserName(String user)
			throws ISPACException {
		return helper.getEntryFromUserName(user);
	}

	public Set getMembersFromUID(String uid) throws ISPACException {
		return helper.getMembersFromUID(uid);
	}

	public Set getOrgUnitsFromUID(String uid) throws ISPACException {
		return helper.getOrgUnitsFromUID(uid);
	}

	public LinkedHashMap getPropertiesFromUID(String uid) throws ISPACException {
		return helper.getPropertiesFromUID(uid);
	}

	public Set getUsersFromUID(String uid) throws ISPACException {
		return helper.getUsersFromUID(uid);
	}

	public IDirectoryEntry login(String user, String password)
			throws ISPACException {
		return helper.login(user, password);
	}

	public Set getEntryFromName(String name, String[] elementos)
			throws ISPACException {
		return helper.getEntryFromName(name, elementos);
	}

}
