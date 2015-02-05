package ieci.tecdoc.sgm.usuarios_backoffice.datatype;

import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.sgm.base.db.DbDataType;
import ieci.tecdoc.sgm.base.db.DbInputStatement;
import ieci.tecdoc.sgm.base.db.DbOutputStatement;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import org.apache.log4j.Logger;

public class UserProfileImpl implements UserProfile {

	private int _userId = DbDataType.NULL_LONG_INTEGER;
	private int _product = 3;
	private int _profile = 0;
	private static final Logger _logger = Logger
			.getLogger(UserProfileImpl.class);

	public UserProfileImpl() {
	}

	public UserProfileImpl(int userId, int product, int profile) {
		_userId = userId;
		_product = product;
		_profile = profile;
	}

	public int getUserId() {
		return _userId;
	}

	public int getProduct() {
		return _product;
	}

	public int getProfile() {
		return _profile;
	}

	public void setProfile(int profile) {
		_profile = profile;
	}

	public String toString() {
		return toXML(false);
	}

	public String toXML(boolean header) {
		String tagName = "Profile";
		XmlTextBuilder bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();
		bdr.addOpeningTag(tagName);
		bdr.addSimpleElement("ProductId", Integer.toString(_product));
		bdr.addSimpleElement("Profile", Integer.toString(_profile));
		bdr.addClosingTag(tagName);
		return bdr.getText();
	}

	public Integer insertValues(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("insertValues");
		statement.setLongInteger(index++, _userId);
		statement.setLongInteger(index++, _product);
		statement.setLongInteger(index++, _profile);
		return new Integer(index);
	}

	public Integer updateValues(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("updateValues");
		statement.setLongInteger(index++, _profile);
		return new Integer(index);
	}

	public Integer loadValues(DbOutputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("getValues");
		_product = statement.getShortInteger(index++);
		_profile = statement.getShortInteger(index++);
		return new Integer(index);
	}

	protected void setId(int id) {
		_userId = id;
	}

	public void setProduct(int product) {
		_product = product;
	}

	public boolean checkProfilesExists(String entidad) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public void insert(String entidad) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void load(int idUsuario, int idProfile, String entidad) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void store(String entidad) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void update(String entidad) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
