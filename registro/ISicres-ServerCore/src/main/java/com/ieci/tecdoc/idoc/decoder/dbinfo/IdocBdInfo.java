package com.ieci.tecdoc.idoc.decoder.dbinfo;

import java.util.StringTokenizer;

/**
 * @author 79426599
 *
 */
public class IdocBdInfo {

    private String sourceData = null;
    private String ver = null;
    private String dbVer = null;
    private int numFtsExts = 0;
    private String extN = null;
    private String fts = null;
    private LDAPDef ldapDef = null;
    
    public IdocBdInfo(String data) {
        setSourceData(data);
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
	 * @return Returns the numFtsExts.
	 */
	public int getNumFtsExts() {
		return numFtsExts;
	}
	/**
	 * @param numFtsExts The numFtsExts to set.
	 */
	public void setNumFtsExts(int numFtsExts) {
		this.numFtsExts = numFtsExts;
	}
	/**
	 * @return Returns the dbVer.
	 */
	public String getDbVer() {
		return dbVer;
	}
	/**
	 * @param dbVer The dbVer to set.
	 */
	public void setDbVer(String dbVer) {
		this.dbVer = dbVer;
	}
	/**
	 * @return Returns the extN.
	 */
	public String getExtN() {
		return extN;
	}
	/**
	 * @param extN The extN to set.
	 */
	public void setExtN(String extN) {
		this.extN = extN;
	}
	/**
	 * @return Returns the fts.
	 */
	public String getFts() {
		return fts;
	}
	/**
	 * @param fts The fts to set.
	 */
	public void setFts(String fts) {
		this.fts = fts;
	}
	/**
	 * @return Returns the ldap.
	 */
	public LDAPDef getLdap() {
		return ldapDef;
	}
	/**
	 * @param ldap The ldap to set.
	 */
	public void setLdap(LDAPDef ldapDef) {
		this.ldapDef = ldapDef;
	}
	/**
	 * @return Returns the ver.
	 */
	public String getVer() {
		return ver;
	}
	/**
	 * @param ver The ver to set.
	 */
	public void setVer(String ver) {
		this.ver = ver;
	}
	
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("\n\t\t IdocBdInfo ver [");
        buffer.append(ver);
        buffer.append("] dbVer [");
        buffer.append(dbVer);
        buffer.append("] numFtsExts [");
        buffer.append(numFtsExts);
        buffer.append("] extN [");
        buffer.append(extN);
        buffer.append("] fts [");
        buffer.append(fts);
        
        buffer.append("] \n\t\t LDAPDef [");
        buffer.append(ldapDef);


        return buffer.toString();
    }
	
    private void analize() {
        StringTokenizer tokenizer = new StringTokenizer(sourceData, "|");

        String nextToken = tokenizer.nextToken();
        ver = nextToken.substring(1, nextToken.length() - 1);
        nextToken = tokenizer.nextToken();
        dbVer = nextToken.substring(1, nextToken.length() - 1); 
        numFtsExts = Integer.parseInt(tokenizer.nextToken());
        extN = tokenizer.nextToken();; 
        fts = tokenizer.nextToken();;
        ldapDef = new LDAPDef(tokenizer.nextToken());
        
    }
}
