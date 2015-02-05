package ieci.tecdoc.sgm.usuarios_backoffice.datatype;

import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.sgm.base.db.DbDataType;
import ieci.tecdoc.sgm.base.db.DbInputStatement;
import ieci.tecdoc.sgm.base.db.DbOutputStatement;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import org.apache.log4j.Logger;

public class PermImpl implements Permission {

	private int _id = DbDataType.NULL_LONG_INTEGER;
	private int _dest = 1;
	private int _product = 3;
	private int _perm = 0;
	private static final Logger _logger = Logger.getLogger(PermImpl.class);

	public PermImpl() {
	}

	public PermImpl(int id, int dest, int product, int perm) {
		_id = id;
		_dest = dest;
		_product = product;
		_perm = perm;
	}

	public int getId() {
		return _id;
	}

	public int getDestination() {
		return _dest;
	}

	public int getProduct() {
		return _product;
	}

	public int getPermission() {
		return _perm;
	}

	public void setPermission(int perm) {
		_perm = perm;
	}

	public String toString() {
		return toXML(false);
	}

	public String toXML(boolean header) {
		String tagName = "Permission";
		XmlTextBuilder bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();
		bdr.addOpeningTag(tagName);
		bdr.addSimpleElement("ProductId", Integer.toString(_product));
		bdr.addSimpleElement("Permission", Integer.toString(_perm));
		bdr.addClosingTag(tagName);
		return bdr.getText();
	}

	public Integer insertValues(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("insertValues");
		statement.setLongInteger(index++, _dest);
		statement.setLongInteger(index++, _id);
		statement.setLongInteger(index++, _product);
		statement.setLongInteger(index++, _perm);
		return new Integer(index);
	}

	public Integer updateValues(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("updateValues");
		statement.setLongInteger(index++, _perm);
		return new Integer(index);
	}

	public Integer loadValues(DbOutputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("getValues");
		_dest = statement.getShortInteger(index++);
		_product = statement.getShortInteger(index++);
		_perm = statement.getShortInteger(index++);
		return new Integer(index);
	}

	protected void setId(int id) {
		_id = id;
	}

	protected void setDestination(int dest) {
		_dest = dest;
	}

	protected void setProduct(int product) {
		_product = product;
	}

}
