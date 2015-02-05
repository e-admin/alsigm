package com.ieci.tecdoc.isicres.usecase;

import java.io.Serializable;
import java.util.Locale;

import javax.naming.Context;

import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;

/**
 * @author LMVICENTE
 * @creationDate 29-abr-2004 16:37:14
 * @version
 * @since
 */
public class UseCaseConf implements Serializable{

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String initialContextFactory = null;
    private String providerURL = null;
    private String urlPkgPrefixes = null;
    private String sessionID = null;
    private Locale locale = null;
    private String userName = null;
    private String password = null;
    //LdapSSO
    private String userDn = null;
    private Boolean useLdap = Boolean.FALSE;
    private Boolean usingOSAuth = Boolean.FALSE;
    private String entidadId = null;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public UseCaseConf() {
        this(Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_INITIAL_FACTORY),
                Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_PROVIDER_URL),
                Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_URL_PKGS),
                null,
                null);
    }

    public UseCaseConf(Locale locale) {
        this(Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_INITIAL_FACTORY),
                Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_PROVIDER_URL),
                Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_URL_PKGS),
                null,
                locale);

    }
    
    public UseCaseConf(Locale locale, String userDn, Boolean useLdap, Boolean usingOSAuth) {
        this(Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_INITIAL_FACTORY),
                Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_PROVIDER_URL),
                Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_URL_PKGS),
                null,
                locale);
        this.userDn = userDn;
        this.useLdap = useLdap;
        this.usingOSAuth = usingOSAuth;

    }

    /**
     * @param initialContextFactory
     * @param providerURL
     * @param urlPkgPrefixes
     */
    public UseCaseConf(String initialContextFactory, String providerURL, String urlPkgPrefixes, Locale locale) {
        this(initialContextFactory, providerURL, urlPkgPrefixes, null, locale);
    }

    /**
     * @param initialContextFactory
     * @param providerURL
     * @param urlPkgPrefixes
     * @param sessionID
     */
    public UseCaseConf(String initialContextFactory, String providerURL, String urlPkgPrefixes, String sessionID) {
        this(initialContextFactory, providerURL, urlPkgPrefixes, sessionID, null);
    }

    public UseCaseConf(String initialContextFactory, String providerURL, String urlPkgPrefixes, String sessionID,
            Locale locale) {
        this.initialContextFactory = initialContextFactory;
        this.providerURL = providerURL;
        this.urlPkgPrefixes = urlPkgPrefixes;
        this.sessionID = sessionID;
        this.locale = locale;
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(Context.INITIAL_CONTEXT_FACTORY);
        buffer.append(" [");
        buffer.append(initialContextFactory);
        buffer.append("] ");
        buffer.append(Context.PROVIDER_URL);
        buffer.append(" [");
        buffer.append(providerURL);
        buffer.append("] ");
        buffer.append(Context.URL_PKG_PREFIXES);
        buffer.append(" [");
        buffer.append(urlPkgPrefixes);
        buffer.append("] SessionID [");
        buffer.append(sessionID);
        buffer.append("] Locale [");
        buffer.append(locale);
        buffer.append("]");

        return buffer.toString();
    }

    /**
     * @return Returns the initialFactory.
     */
    public String getInitialContextFactory() {
        return initialContextFactory;
    }

    /**
     * @param initialFactory
     *            The initialFactory to set.
     */
    public void setInitialContextFactory(String initialFactory) {
        this.initialContextFactory = initialFactory;
    }

    /**
     * @return Returns the providerURL.
     */
    public String getProviderURL() {
        return providerURL;
    }

    /**
     * @param providerURL
     *            The providerURL to set.
     */
    public void setProviderURL(String providerURL) {
        this.providerURL = providerURL;
    }

    /**
     * @return Returns the sessionID.
     */
    public String getSessionID() {
        return sessionID;
    }

    /**
     * @param sessionID
     *            The sessionID to set.
     */
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    /**
     * @return Returns the urlPkgsFactory.
     */
    public String getUrlPkgPrefixes() {
        return urlPkgPrefixes;
    }

    /**
     * @param urlPkgsFactory
     *            The urlPkgsFactory to set.
     */
    public void setUrlPkgPrefixes(String urlPkgsFactory) {
        this.urlPkgPrefixes = urlPkgsFactory;
    }

    /**
     * @return Returns the locale.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @param locale
     *            The locale to set.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    public void setUserName(String name){
    	userName = name;
    }
    
    public String getUserName(){
    	return userName;
    }

	/**
	 * @return the useLdap
	 */
	public Boolean getUseLdap() {
		return useLdap;
	}

	/**
	 * @param useLdap the useLdap to set
	 */
	public void setUseLdap(Boolean useLdap) {
		this.useLdap = useLdap;
	}

	/**
	 * @return the userDn
	 */
	public String getUserDn() {
		return userDn;
	}

	/**
	 * @param userDn the userDn to set
	 */
	public void setUserDn(String userDn) {
		this.userDn = userDn;
	}

	/**
	 * @return the usingOSAuth
	 */
	public Boolean getUsingOSAuth() {
		return usingOSAuth;
	}

	/**
	 * @param usingOSAuth the usingOSAuth to set
	 */
	public void setUsingOSAuth(Boolean usingOSAuth) {
		this.usingOSAuth = usingOSAuth;
	}

	/**
	 * @return the entidadId
	 */
	public String getEntidadId() {
		return entidadId;
	}

	/**
	 * @param entidadId the entidadId to set
	 */
	public void setEntidadId(String entidadId) {
		this.entidadId = entidadId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

