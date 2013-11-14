package es.ieci.tecdoc.isicres.admin.core.ldap;


import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public final class LdapSearchEx {

	private LdapConnection m_conn;
	private String m_start;
	private int m_scope;
	private String m_filter;
	private String[] m_retAttrs;
	private int m_maxCount;
	private NamingEnumeration m_ne;
	private SearchResult m_sr;
	private Attributes m_srAttrs;

	public LdapSearchEx() {
		m_conn = null;
		m_start = null;
		m_scope = SearchControls.OBJECT_SCOPE;
		m_filter = null;
		m_retAttrs = null;
		m_maxCount = 0;
		m_ne = null;
		m_sr = null;
		m_srAttrs = null;
	}

	public void initialize(LdapConnection conn, String start, int scope,
			String filter, String[] retAttrs, int maxCount) {
		m_conn = conn;
		m_start = start;
		m_scope = scope;
		m_filter = filter;
		m_retAttrs = retAttrs;
		m_maxCount = maxCount;
	}

	public void release() throws Exception {
		if (m_ne != null)
			m_ne.close();
	}

	public static void ensureRelease(LdapSearchEx search, Exception exc)
			throws Exception {

		try {
			if (search != null)
				search.release();
			throw exc;
		} catch (Exception e) {
			throw exc;
		}

	}

	public void execute() throws Exception {
		m_ne = search(m_conn.getInitDirContext(), m_start, m_scope, m_filter,
				m_retAttrs, m_maxCount);
	}

	public boolean next() throws Exception {

		boolean hm;

		hm = m_ne.hasMoreElements();

		if (hm) {

			m_sr = (SearchResult) m_ne.nextElement();

			m_srAttrs = m_sr.getAttributes();

		}

		return hm;

	}

	public int getAttributeValueCount(String attrName) {

		Attribute attr;

		attr = m_srAttrs.get(attrName);

		if (attr == null)
			return 0;
		else
			return attr.size();

	}

	public Object getAttributeValue(String attrName, int valIdx)
			throws Exception {
		return m_srAttrs.get(attrName).get(valIdx);
	}

	public NamingEnumeration getAttributes() throws Exception {
		return m_srAttrs.getAll();
	}

	public Object getAttributeValue(String attrName) throws Exception {
		return m_srAttrs.get(attrName).get(0);
	}

	public Attribute getAttributeValues(String attrName) throws Exception {

		return m_srAttrs.get(attrName);
	}

	public Attribute getAttributeValuesLowercase(String attrName)
			throws Exception {
		Attribute attrs = m_srAttrs.get(attrName);
		BasicAttribute attrsLowercase = new BasicAttribute(attrName);
		if ((attrs != null) && (attrs.size() > 0)) {
			for (int i = 0; i < attrs.size(); i++) {
				String attribute = (String) attrs.get(i);
				if ((attribute != null) && (!"".equals(attribute.trim()))) {
					attrsLowercase.add(attribute.toLowerCase().trim());
				}
			}
		}
		return attrsLowercase;
	}

	public String getEntryDn() throws Exception {
		// Obtenemos el DN del nodo ldap
		return m_sr.getNameInNamespace();
	}

	// **************************************************************************

	private static NamingEnumeration search(DirContext dirCtx, String start,
			int scope, String filter, String[] attrs, int maxCount)
			throws Exception {

		NamingEnumeration ne = null;
		SearchControls sc;

		sc = new SearchControls();

		sc.setSearchScope(scope);
		sc.setReturningAttributes(attrs);
		sc.setCountLimit(maxCount);

		ne = dirCtx.search(start, filter, sc);

		return ne;

	}

} // class
