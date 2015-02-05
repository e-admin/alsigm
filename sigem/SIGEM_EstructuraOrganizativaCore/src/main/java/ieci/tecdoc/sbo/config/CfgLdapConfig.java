package ieci.tecdoc.sbo.config;

import ieci.tecdoc.core.textutil.UtilX;
import ieci.tecdoc.sbo.util.idoccrypto.IdocCryptoUtil;
import java.text.StringCharacterIterator;


public final class CfgLdapConfig
{

	//~ Instance fields --------------------------------------------------------

	private int     m_engine;
	private String  m_server;
	private int     m_port;
	private String  m_user;
	private String  m_pwd;
	private String  m_root;
	private boolean m_useOSAuth;

	//~ Constructors -----------------------------------------------------------

	public CfgLdapConfig()
	{

		m_engine    = CfgDefs.LDAP_ENGINE_NONE;
		m_useOSAuth = false;

	}

	//~ Methods ----------------------------------------------------------------

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the engine.
	 */
	public int getEngine()
	{

		return m_engine;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param engine The engine to set.
	 */
	public void setEngine(int engine)
	{

		m_engine = engine;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the port.
	 */
	public int getPort()
	{

		return m_port;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param port The port to set.
	 */
	public void setPort(int port)
	{

		m_port = port;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the pwd.
	 */
	public String getPwd()
	{

		return m_pwd;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param pwd The pwd to set.
	 */
	public void setPwd(String pwd)
	{

		m_pwd = pwd;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the root.
	 */
	public String getRoot()
	{

		return m_root;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param root The root to set.
	 */
	public void setRoot(String root)
	{

		m_root = root;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the server.
	 */
	public String getServer()
	{

		return m_server;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param server The server to set.
	 */
	public void setServer(String server)
	{

		m_server = server;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the useOSAuth.
	 */
	public boolean isUseOSAuth()
	{

		return m_useOSAuth;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param useOSAuth The useOSAuth to set.
	 */
	public void setUseOSAuth(boolean useOSAuth)
	{

		m_useOSAuth = useOSAuth;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the user.
	 */
	public String getUser()
	{

		return m_user;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param user The user to set.
	 */
	public void setUser(String user)
	{

		m_user = user;

	}

	protected void restore(String info)
			         throws Exception
	{

		StringCharacterIterator iterator  = new StringCharacterIterator(info);
		char				    sep		  = ',';
		String				    codePwd;
		int					    useOSAuth;

		//motor
		m_engine = UtilX.parseInteger(iterator, sep);

		if(m_engine != CfgDefs.LDAP_ENGINE_NONE)
		{
		   
			UtilX.iteratorIncrementIndex(iterator, 1);
			
			m_server = UtilX.parseString(iterator);
			
			UtilX.iteratorIncrementIndex(iterator, 1);
			
			m_port   = UtilX.parseInteger(iterator, sep);
			
			UtilX.iteratorIncrementIndex(iterator, 1);

			m_user   = UtilX.parseString(iterator);
			
			UtilX.iteratorIncrementIndex(iterator, 1);

			codePwd  = UtilX.parseString(iterator);
			m_pwd    = decodeLdapPassword(codePwd);
			
			UtilX.iteratorIncrementIndex(iterator, 1);
			
			m_root   = UtilX.parseString(iterator);
			
			if(m_engine == CfgDefs.LDAP_ENGINE_ACTIVE_DIRECTORY)
			{

			   UtilX.iteratorIncrementIndex(iterator, 1);

			   useOSAuth = UtilX.parseInteger(iterator, sep);

				if(useOSAuth == 0)
					m_useOSAuth = false;
				else
					m_useOSAuth = true;

			}

		}

	}
	
	protected String persist() throws Exception
	{
	   String misc;
	   String codePwd;
	   
	   misc = new Integer(m_engine).toString();
	   
	   if(m_engine != CfgDefs.LDAP_ENGINE_NONE)
	   {
	      misc = misc + ",";
	      misc = misc + "\"" + m_server + "\",";
	      misc = misc + new Integer(m_port).toString() + ",";
	      misc = misc + "\"" + m_user + "\",";
	      
	      codePwd = codeLdapPassword(m_pwd);
	      
	      misc = misc + "\"" + codePwd + "\",";
	      misc = misc + "\"" + m_root + "\",";
	      
	      if (m_useOSAuth)
	         misc = misc + "0";
	      else
	         misc = misc + "1";	      
	   }
	   
	   return misc;
	}

	private String decodeLdapPassword(String codePwd)
							   throws Exception
	{

		String decPwd           = null;	  
	   String passwordToGenKey = "IDOCLDAP";
	   
	   decPwd = IdocCryptoUtil.decryptPassword(codePwd, passwordToGenKey);
	      
	   return decPwd;

	}
	
	private String codeLdapPassword(String pwd)
								throws Exception
	{
	   String codePwd          = null;
	   String passwordToGenKey = "IDOCLDAP";
	   
	   codePwd = IdocCryptoUtil.encryptPassword(pwd,passwordToGenKey);
	   
	   return codePwd;
	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("CfgLdapConfig[");
		buffer.append("m_engine = ").append(m_engine);
		buffer.append(", m_server = ").append(m_server);
		buffer.append(", m_port = ").append(m_port);
		buffer.append(", m_user = ").append(m_user);
		buffer.append(", m_pwd = ").append(m_pwd);
		buffer.append(", m_root = ").append(m_root);
		buffer.append(", m_useOSAuth = ").append(m_useOSAuth);
		buffer.append("]");

		return buffer.toString();

	}

}// class
