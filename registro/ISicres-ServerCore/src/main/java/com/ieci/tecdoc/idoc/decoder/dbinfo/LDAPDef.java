package com.ieci.tecdoc.idoc.decoder.dbinfo;

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * @author 79426599
 *
 */
public class LDAPDef implements Serializable {

    private String sourceData = null;
    private int ldapEngine = 0;
    private String ldapServer = null;
    private int ldapPort = 0;
    private String ldapUser = null;
    private String ldapPassword = null;
    private String ldapRoot = "";
    private int useOsAuthentication = 0;
    
	public LDAPDef() {
	}
	public LDAPDef(String data) {
        setSourceData(data);
	}

	/**
	 * @return Returns the ldapEngine.
	 */
	public int getLdapEngine() {
		return ldapEngine;
	}
	/**
	 * @param ldapEngine The ldapEngine to set.
	 */
	public void setLdapEngine(int ldapEngine) {
		this.ldapEngine = ldapEngine;
	}
	/**
	 * @return Returns the ldapPassword.
	 */
	public String getLdapPassword() {
		return ldapPassword;
	}
	/**
	 * @param ldapPassword The ldapPassword to set.
	 */
	public void setLdapPassword(String ldapPassword) {
		this.ldapPassword = ldapPassword;
	}
	/**
	 * @return Returns the ldapPort.
	 */
	public int getLdapPort() {
		return ldapPort;
	}
	/**
	 * @param ldapPort The ldapPort to set.
	 */
	public void setLdapPort(int ldapPort) {
		this.ldapPort = ldapPort;
	}
	/**
	 * @return Returns the ldapRoot.
	 */
	public String getLdapRoot() {
		return ldapRoot;
	}
	/**
	 * @param ldapRoot The ldapRoot to set.
	 */
	public void setLdapRoot(String ldapRoot) {
		this.ldapRoot = ldapRoot;
	}
	/**
	 * @return Returns the ldapServer.
	 */
	public String getLdapServer() {
		return ldapServer;
	}
	/**
	 * @param ldapServer The ldapServer to set.
	 */
	public void setLdapServer(String ldapServer) {
		this.ldapServer = ldapServer;
	}
	/**
	 * @return Returns the ldapUser.
	 */
	public String getLdapUser() {
		return ldapUser;
	}
	/**
	 * @param ldapUser The ldapUser to set.
	 */
	public void setLdapUser(String ldapUser) {
		this.ldapUser = ldapUser;
	}
	/**
	 * @return Returns the sourceData.
	 */
	public String getSourceData() {
		return sourceData;
	}
	/**
	 * @param sourceData The sourceData to set.
	 */
	public void setSourceData(String data) {
        if (data != null) {
            this.sourceData = data;
            analize();
        }
	}
	/**
	 * @return Returns the useOsAuthentication.
	 */
	public int getUseOsAuthentication() {
		return useOsAuthentication;
	}
	/**
	 * @param useOsAuthentication The useOsAuthentication to set.
	 */
	public void setUseOsAuthentication(int useOsAuthentication) {
		this.useOsAuthentication = useOsAuthentication;
	}
	
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("\n\t\t LDAPDef ldapEngine [");
        buffer.append(ldapEngine);
        buffer.append("] ldapServer [");
        buffer.append(ldapServer);
        buffer.append("] ldapUser [");
        buffer.append(ldapUser);
        buffer.append("] ldapRoot [");
        buffer.append(ldapRoot);
        buffer.append("] useOsAuthentication [");
        buffer.append(useOsAuthentication);
        
        return buffer.toString();
    }
	
    private void analize() {
        StringTokenizer tokenizer = new StringTokenizer(sourceData, "\"");

        String nextToken = tokenizer.nextToken();
		if (nextToken.endsWith(",")){
	        nextToken = nextToken.substring(0, nextToken.length()-1);
	        ldapEngine = Integer.parseInt(nextToken);
		} else {
	        ldapEngine = Integer.parseInt(nextToken);
		}
        if (ldapEngine != 0) {
			ldapServer = tokenizer.nextToken();
			nextToken = tokenizer.nextToken();
			nextToken = nextToken.substring(1, nextToken.length() - 1);
			ldapPort = Integer.parseInt(nextToken);
			nextToken = tokenizer.nextToken();
			if (nextToken.equals(",")){
				ldapUser = "";
			} else {
				ldapUser = nextToken;
				tokenizer.nextToken();
			}
			nextToken = tokenizer.nextToken();
			if (nextToken.equals(",")){
				ldapPassword = "";
			} else {
				ldapPassword = nextToken;
				tokenizer.nextToken();
			}
			nextToken = tokenizer.nextToken();
			if (nextToken.startsWith(",")){
				ldapRoot = "";
				nextToken = nextToken.substring(1, nextToken.length());
				useOsAuthentication = Integer.parseInt(nextToken);
			} else {
				ldapRoot = nextToken;
				nextToken = tokenizer.nextToken();
				nextToken = nextToken.substring(1, nextToken.length());
				useOsAuthentication = Integer.parseInt(nextToken);
			}
		}
    }
}
