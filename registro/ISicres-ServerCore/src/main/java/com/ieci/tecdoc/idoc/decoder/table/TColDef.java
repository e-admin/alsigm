package com.ieci.tecdoc.idoc.decoder.table;

import java.util.StringTokenizer;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class TColDef {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;
    private int w = 0;
    private String hdrText = null;
    private int just = 0;
    private int fldId = 0;
    private int role = 0;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public TColDef() {
        this(null);
    }

    public TColDef(String data) {
        setSourceData(data);
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

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

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(" ColDef w[");
        buffer.append(w);
        buffer.append("] hdrText [");
        buffer.append(hdrText);
        buffer.append("] just [");
        buffer.append(just);
        buffer.append("] fldId [");
        buffer.append(fldId);
        buffer.append("] role [");
        buffer.append(role);
        buffer.append("]");

        return buffer.toString();
    }

    /**
     * @return Returns the fldId.
     */
    public int getFldId() {
        return fldId;
    }
    /**
     * @param fldId The fldId to set.
     */
    public void setFldId(int fldId) {
        this.fldId = fldId;
    }
    /**
     * @return Returns the hdrText.
     */
    public String getHdrText() {
        return hdrText;
    }
    /**
     * @param hdrText The hdrText to set.
     */
    public void setHdrText(String hdrText) {
        this.hdrText = hdrText;
    }
    /**
     * @return Returns the just.
     */
    public int getJust() {
        return just;
    }
    /**
     * @param just The just to set.
     */
    public void setJust(int just) {
        this.just = just;
    }
    /**
     * @return Returns the role.
     */
    public int getRole() {
        return role;
    }
    /**
     * @param role The role to set.
     */
    public void setRole(int role) {
        this.role = role;
    }
    /**
     * @return Returns the w.
     */
    public int getW() {
        return w;
    }
    /**
     * @param w The w to set.
     */
    public void setW(int w) {
        this.w = w;
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private void analize() {
        StringTokenizer tokenizer = new StringTokenizer(sourceData, ",");

        String nextToken = null;

        w = Integer.parseInt(tokenizer.nextToken());
        
        hdrText = "";
        boolean include = false;
        do {
            nextToken = tokenizer.nextToken();
            if (nextToken.startsWith("\"") && nextToken.endsWith("\"")) {
                hdrText = hdrText + nextToken.substring(1, nextToken.length() - 1);
            } else if (nextToken.startsWith("\"")) {
                hdrText = hdrText + nextToken.substring(1, nextToken.length());
                include = true;
            } else if (nextToken.endsWith("\"")) {
                hdrText = hdrText + nextToken.substring(0, nextToken.length()-1);
            } else {
                hdrText = hdrText + nextToken;
                include = true;
            }
            
            if (include) {
                hdrText = hdrText + ",";
                include = false;
            }
        } while (!nextToken.endsWith("\""));
        
//        nextToken = tokenizer.nextToken();
//        hdrText = nextToken.substring(1, nextToken.length() - 1);
        
        just = Integer.parseInt(tokenizer.nextToken());
        fldId = Integer.parseInt(tokenizer.nextToken());
        role = Integer.parseInt(tokenizer.nextToken());
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

