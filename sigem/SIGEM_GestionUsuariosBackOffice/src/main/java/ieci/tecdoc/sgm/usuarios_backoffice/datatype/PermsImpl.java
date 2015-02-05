package ieci.tecdoc.sgm.usuarios_backoffice.datatype;

import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.util.ArrayList;

public class PermsImpl implements Permissions {

	private ArrayList list = new ArrayList();

	protected PermsImpl() {
	}

	public String toXML(boolean header) {

		String tagName = "Permissions";
		XmlTextBuilder bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();
		bdr.addOpeningTag(tagName);
		for (int i = 0; i < count(); i++) {
			Permission perm = get(i);
			bdr.addFragment(perm.toXML(header));
		}

		bdr.addClosingTag(tagName);
		return bdr.getText();
	}

	public String toString() {
		return toXML(false);
	}

	public Permission getProductPermission(int productId) throws Exception {
		Permission perm = null;
		boolean found = false;
		for (int counter = 0; counter < count(); counter++) {
			perm = get(counter);
			if (perm.getProduct() != productId)
				continue;
			found = true;
			break;
		}

		if (!found)
			AdminException.throwException(3001006L);
		return perm;
	}

	protected int count() {
		return list.size();
	}

	protected Permission get(int index) {
		return (Permission) list.get(index);
	}

	protected void setId(int id) {
		for (int counter = 0; counter < count(); counter++)
			((PermImpl) get(counter)).setId(id);

	}

	protected void add(Permission perm) {
		list.add(perm);
	}

}
