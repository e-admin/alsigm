package com.ieci.tecdoc.idoc.decoder.validation.idocarchdet;

import java.util.StringTokenizer;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class VFldVldDef {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private StringTokenizer tokenizer = null;
    private int fldid = 0;
    private int vldtype = 0;
    private int id1 = 0;
    
    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public VFldVldDef() {
        this(null);
    }

    public VFldVldDef(StringTokenizer tokenizer) {
        setTokenizer(tokenizer);
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("VFldVldDef fldid [");
        buffer.append(fldid);
        buffer.append("] vldtype [");
        buffer.append(vldtype);
        buffer.append("] id1 [");
        buffer.append(id1);
        buffer.append("]");

        return buffer.toString();
    }

    /**
     * @return Returns the fldid.
     */
    public int getFldid() {
        return fldid;
    }

    /**
     * @param fldid
     *            The fldid to set.
     */
    public void setFldid(int fldid) {
        this.fldid = fldid;
    }

   
    /**
     * @return Returns the id1.
     */
    public int getId1() {
        return id1;
    }

    /**
     * @param id1
     *            The id1 to set.
     */
    public void setId1(int id1) {
        this.id1 = id1;
    }

    
    /**
     * @return Returns the tokenizer.
     */
    public StringTokenizer getTokenizer() {
        return tokenizer;
    }

    /**
     * @param tokenizer
     *            The tokenizer to set.
     */
    public void setTokenizer(StringTokenizer tokenizer) {
        if (tokenizer != null) {
            this.tokenizer = tokenizer;
            analize();
        }
    }

    /**
     * @return Returns the vldtype.
     */
    public int getVldtype() {
        return vldtype;
    }

    /**
     * @param vldtype
     *            The vldtype to set.
     */
    public void setVldtype(int vldtype) {
        this.vldtype = vldtype;
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private void analize() {
        fldid = Integer.parseInt(tokenizer.nextToken());
        tokenizer.nextToken();

        String nextToken = tokenizer.nextToken();

        StringTokenizer tokenizer2 = new StringTokenizer(nextToken, ",");

        vldtype = Integer.parseInt(tokenizer2.nextToken());
        id1 = Integer.parseInt(tokenizer2.nextToken());

    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

