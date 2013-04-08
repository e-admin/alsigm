package com.ieci.tecdoc.idoc.decoder.validation.idocarchdet;

import java.util.StringTokenizer;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class MiscFormat {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;
    private String ver = null;
    private String fdrname = null;
    private int vollisttype = 0;
    private int id = 0;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public MiscFormat() {
        this(null);
    }

    public MiscFormat(String data) {
        setSourceData(data);
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("MiscFormat ver[");
        buffer.append(ver);
        buffer.append("] fdrname [");
        buffer.append(fdrname);
        buffer.append("] vollisttype [");
        buffer.append(vollisttype);
        buffer.append("] id [");
        buffer.append(id);
        buffer.append("]");

        return buffer.toString();
    }

    /**
     * @return Returns the data.
     */
    public String getSourceData() {
        return sourceData;
    }

    /**
     * @param data
     *            The data to set.
     */
    public void setSourceData(String data) {
        if (data != null) {
            this.sourceData = data;
            analize();
        }
    }

    /**
     * @return Returns the fdrname.
     */
    public String getFdrname() {
        return fdrname;
    }

    /**
     * @param fdrname
     *            The fdrname to set.
     */
    public void setFdrname(String fdrname) {
        this.fdrname = fdrname;
    }

    /**
     * @return Returns the id.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Returns the ver.
     */
    public String getVer() {
        return ver;
    }

    /**
     * @param ver
     *            The ver to set.
     */
    public void setVer(String ver) {
        this.ver = ver;
    }

    /**
     * @return Returns the vollisttype.
     */
    public int getVollisttype() {
        return vollisttype;
    }

    /**
     * @param vollisttype
     *            The vollisttype to set.
     */
    public void setVollisttype(int vollisttype) {
        this.vollisttype = vollisttype;
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private void analize() {
        StringTokenizer tokenizer = new StringTokenizer(sourceData, "|");

        String nextToken = tokenizer.nextToken();
        ver = nextToken.substring(1, nextToken.length() - 1);

        nextToken = tokenizer.nextToken();
        fdrname = nextToken.substring(1, nextToken.length() - 1);

        StringTokenizer tokenizer2 = new StringTokenizer(tokenizer.nextToken(), ",");

        vollisttype = Integer.parseInt(tokenizer2.nextToken());

        if (tokenizer2.hasMoreTokens()) {
            id = Integer.parseInt(tokenizer2.nextToken());
        }
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

