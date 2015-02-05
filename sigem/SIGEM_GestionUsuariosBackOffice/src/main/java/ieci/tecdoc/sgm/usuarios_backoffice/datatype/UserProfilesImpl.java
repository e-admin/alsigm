package ieci.tecdoc.sgm.usuarios_backoffice.datatype;

import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.util.ArrayList;

public class UserProfilesImpl implements UserProfiles {

	private ArrayList list = new ArrayList();

	protected UserProfilesImpl() {
	}

	public UserProfile getProductProfile(int productId) throws Exception {
		UserProfile profile = null;
		boolean found = false;
		for (int counter = 0; counter < count(); counter++) {
			profile = get(counter);
			if (profile.getProduct() != productId)
				continue;
			found = true;
			break;
		}

		if (!found)
			AdminException.throwException(3001005L);
		return profile;
	}

	public String toXML(boolean header) {
		String tagName = "Profiles";
		XmlTextBuilder bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();
		bdr.addOpeningTag(tagName);
		for (int i = 0; i < count(); i++) {
			UserProfile profile = get(i);
			bdr.addFragment(profile.toXML(header));
		}

		bdr.addClosingTag(tagName);
		return bdr.getText();
	}

	public String toString() {
		return toXML(false);
	}

	public int count() {
		return list.size();
	}

	public UserProfile get(int index) {
		return (UserProfile) list.get(index);
	}

	protected void setId(int id) {
		for (int counter = 0; counter < count(); counter++)
			((UserProfileImpl) get(counter)).setId(id);

	}

	public void add(UserProfile profile) {
		list.add(profile);
	}

}
