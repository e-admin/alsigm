package ieci.tecdoc.sbo.config;

//import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.core.textutil.UtilX;

import java.util.StringTokenizer;


public final class CfgDbInfoMisc
{

	//~ Instance fields --------------------------------------------------------

	private String		    m_vers    = "01.04";
	private String		    m_dbVers  = "9.0.0";
	private CfgFtsConfig  m_ftsCfg  = null;
	private CfgLdapConfig m_ldapCfg = null;

	//~ Constructors -----------------------------------------------------------

	public CfgDbInfoMisc(){}

	//~ Methods ----------------------------------------------------------------

	protected String initInfoMisc() 
	{
	   String misc;
	   
	   misc = "\"" + m_vers + "\"|\"" + m_dbVers + "\"|";
	   
      if (m_ftsCfg == null)
         misc = misc + "2|\"doc\",\"txt\"|0,0,\"\"|";
      
      if (m_ldapCfg == null)
         misc = misc + "0";
            
	   return misc;	
	}
	
		
   public void restore(String misc)
			  throws Exception
	{

		StringTokenizer tokens = new StringTokenizer(misc, "|");

		//Versión
		m_vers   = UtilX.parseStringNextToken(tokens, true);

		//Versión de base de datos
		m_dbVers = UtilX.parseStringNextToken(tokens, true);
		
		restoreFtsCfg(tokens);
		restoreLdapCfg(tokens);

	}
   
   protected String persist() throws Exception
	{
	   String misc;
	   
	   misc = "\"" + m_vers + "\"|\"" + m_dbVers + "\"|";
	   
	   if (m_ftsCfg == null)
         misc = misc + "2|\"doc\",\"txt\"|0,0,\"\"";
	   else
	      misc = misc + m_ftsCfg.persist();
      
	   misc = misc + "|";
	   
      if (m_ldapCfg == null)
         misc = misc + "0";
      else
         misc = misc + m_ldapCfg.persist();
            
	   return misc;
	   
	}

	private void restoreFtsCfg(StringTokenizer tokens)
						throws Exception
	{

		m_ftsCfg = new CfgFtsConfig();
		
		m_ftsCfg.restoreFromTokenizer(tokens);

	}

	private void restoreLdapCfg(StringTokenizer tokens)
						 throws Exception
	{

		String ldapInfo;
		int    i = tokens.countTokens();
		
		ldapInfo  = UtilX.parseStringNextToken(tokens);
		m_ldapCfg = new CfgLdapConfig();
		
		m_ldapCfg.restore(ldapInfo);

	}

	public CfgLdapConfig getLdapConfig()
	{

		return m_ldapCfg;

	}

   public CfgFtsConfig getFtsConfig()
	{

		return m_ftsCfg;

	}

   protected CfgFtsConfig restoreFtsInfo(String misc)
			                 throws Exception
	{

		StringTokenizer tokens    = new StringTokenizer(misc, "|");
      CfgFtsConfig    ftsConfig = null;

		//Versión
		m_vers   = UtilX.parseStringNextToken(tokens, true);

		//Versión de base de datos
		m_dbVers = UtilX.parseStringNextToken(tokens, true);
		
		restoreFtsCfg(tokens);	

      ftsConfig = m_ftsCfg;

      return ftsConfig;	
	}

}// class
