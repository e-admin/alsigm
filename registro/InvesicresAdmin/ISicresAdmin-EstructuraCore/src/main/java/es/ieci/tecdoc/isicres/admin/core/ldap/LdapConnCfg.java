package es.ieci.tecdoc.isicres.admin.core.ldap;

public final class LdapConnCfg {

	private int m_engine;
	private int m_provider;
	private String m_url;
	private String m_user;
	private String m_pwd;
	private boolean m_pool;
	private int m_poolTO;
	private String ldapVersion;

	public LdapConnCfg(int engine, int provider, String url, String user,
			String pwd, boolean pool, int poolTimeOut) {
		this.m_engine = engine;
		this.m_provider = provider;
		this.m_url = url;
		this.m_user = user;
		this.m_pwd = pwd;
		this.m_pool = pool;
		this.m_poolTO = poolTimeOut;
	}

	private LdapConnCfg() {
	}

	public int getEngine() {
		return m_engine;
	}

	public void setEngine(int engine) {
		m_engine = engine;
	}

	public int getProvider() {
		return m_provider;
	}

	public void setProvider(int Provider) {
		m_provider = Provider;
	}

	public String getUrl() {
		return m_url;
	}

	public void setUrl(String url) {
		m_url = url;
	}

	public String getUser() {
		return m_user;
	}

	public void setUser(String user) {
		m_user = user;
	}

	public String getPwd() {
		return m_pwd;
	}

	public void setPwd(String pwd) {
		m_pwd = pwd;
	}

	public boolean getPool() {
		return m_pool;
	}

	public void setPool(boolean pool) {
		m_pool = pool;
	}

	public int getPoolTimeOut() {
		return m_poolTO;
	}

	public void setPoolTimeOut(int poolTO) {
		m_poolTO = poolTO;
	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("LdapConnCfg[");
		buffer.append("engine = ").append(m_engine);
		buffer.append(", provider = ").append(m_provider);
		buffer.append(", url = ").append(m_url);
		buffer.append(", user = ").append(m_user);
		buffer.append(", pwd = ").append(m_pwd);
		buffer.append(", pool = ").append(m_pool);
		buffer.append(", poolTimeOut = ").append(m_poolTO);
		buffer.append("]");

		return buffer.toString();
	}

	public String getLdapVersion() {
		return ldapVersion;
	}

	public void setLdapVersion(String ldapVersion) {
		this.ldapVersion = ldapVersion;
	}

} // class
