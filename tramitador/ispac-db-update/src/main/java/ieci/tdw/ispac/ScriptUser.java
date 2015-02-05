package ieci.tdw.ispac;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ScriptUser extends Responsible {
	
	private static final long serialVersionUID = 1L;

	public ScriptUser(String name) throws ISPACException {
		super(null, name);
	}

	public Responsible getOrgUnit() throws ISPACException {
		return null;
	}

	public Collection getOrgUnits() throws ISPACException {
		return new ArrayList();
	}

	public List getRespList() throws ISPACException {
		return new ArrayList();
	}

	public String getRespString() throws ISPACException {
		return null;
	}

	public Collection getUserGroups() throws ISPACException {
		return new ArrayList();
	}

	public Collection getUsers() throws ISPACException {
		return new ArrayList();
	}

	public boolean isDomain() {
		return false;
	}

	public boolean isGroup() {
		return false;
	}

	public boolean isInResponsibleList(String suid) throws ISPACException {
		return false;
	}

	public boolean isOrgUnit() {
		return false;
	}

	public boolean isUser() {
		return true;
	}

	public void loadObject(String xml) throws ISPACException {
	}

	public String toXmlString() {
		String userXml = getXmlValues();
		return XmlTag.newTag("user", userXml);
	}
}
