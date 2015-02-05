package com.ieci.tecdoc.idoc.decoder.validation.idocvtblctlg;

import java.util.StringTokenizer;

/**
 * @author LMVICENTE
 * @creationDate 03-jun-2004 17:15:07
 * @version
 * @since
 */
public class IdocvtblctlgTV {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;

    private String ver = null;
    private String logDocColName = null;
    private String docColName = null;
    private int docColFmtDef;
    private String primaryColName = null;
    private String from = null;
    private String where = null;
    private String orderBy = null;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public IdocvtblctlgTV(String sourceData) {
        setSourceData(sourceData);
        analize();
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("IdocvtblctlgTV ver [");
        buffer.append(ver);
        buffer.append("] logDocColName [");
        buffer.append(logDocColName);
        buffer.append("] docColName [");
        buffer.append(docColName);
        buffer.append("] docColFmtDef [");
        buffer.append(docColFmtDef);
        buffer.append("] primaryColName [");
        buffer.append(primaryColName);
        buffer.append("] from [");
        buffer.append(from);
        buffer.append("] where [");
        buffer.append(where);
        buffer.append("] orderBy [");
        buffer.append(orderBy);
        buffer.append("]");

        return buffer.toString();
    }

    /**
     * @return Returns the docColFmtDef.
     */
    public int getDocColFmtDef() {
        return docColFmtDef;
    }
    /**
     * @param docColFmtDef The docColFmtDef to set.
     */
    public void setDocColFmtDef(int docColFmtDef) {
        this.docColFmtDef = docColFmtDef;
    }
    /**
     * @return Returns the docColName.
     */
    public String getDocColName() {
        return docColName;
    }
    /**
     * @param docColName The docColName to set.
     */
    public void setDocColName(String docColName) {
        this.docColName = docColName;
    }
    /**
     * @return Returns the from.
     */
    public String getFrom() {
        return from;
    }
    /**
     * @param from The from to set.
     */
    public void setFrom(String from) {
        this.from = from;
    }
    /**
     * @return Returns the logDocColName.
     */
    public String getLogDocColName() {
        return logDocColName;
    }
    /**
     * @param logDocColName The logDocColName to set.
     */
    public void setLogDocColName(String logDocColName) {
        this.logDocColName = logDocColName;
    }
    /**
     * @return Returns the orderBy.
     */
    public String getOrderBy() {
        return orderBy;
    }
    /**
     * @param orderBy The orderBy to set.
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    /**
     * @return Returns the primaryColName.
     */
    public String getPrimaryColName() {
        return primaryColName;
    }
    /**
     * @param primaryColName The primaryColName to set.
     */
    public void setPrimaryColName(String primaryColName) {
        this.primaryColName = primaryColName;
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
    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
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
    /**
     * @return Returns the where.
     */
    public String getWhere() {
        return where;
    }
    /**
     * @param where The where to set.
     */
    public void setWhere(String where) {
        this.where = where;
    }
    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private void analize() {
        StringTokenizer tokenizer2 = new StringTokenizer(sourceData, "|");

        String nextToken = tokenizer2.nextToken();
        ver = nextToken.substring(1, nextToken.length() - 1);

        StringTokenizer tokenizer = new StringTokenizer(tokenizer2.nextToken(), ",");

        nextToken = tokenizer.nextToken();
        logDocColName = nextToken.substring(1, nextToken.length() - 1);

        nextToken = tokenizer.nextToken();
        docColName = nextToken.substring(1, nextToken.length() - 1);

        nextToken = tokenizer.nextToken();
        docColFmtDef = Integer.parseInt(nextToken);

        nextToken = tokenizer.nextToken();
        primaryColName = nextToken.substring(1, nextToken.length() - 1);

        nextToken = tokenizer.nextToken();
        from = nextToken.substring(1, nextToken.length() - 1);

        nextToken = tokenizer.nextToken();
        where = nextToken.substring(1, nextToken.length() - 1);

        nextToken = tokenizer.nextToken();
        orderBy = nextToken.substring(1, nextToken.length() - 1);

    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

