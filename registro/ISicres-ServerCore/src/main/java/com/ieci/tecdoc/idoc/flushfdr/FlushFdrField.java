package com.ieci.tecdoc.idoc.flushfdr;

import java.io.Serializable;

/*
 * @author LMVICENTE @creationDate 06-sep-2004 13:47:13
 * 
 * @version @since
 */
public class FlushFdrField implements Serializable {

    private int fldid = 0;

    private String value = null;

    private int ctrlid = 0;

    public FlushFdrField() {
    }

    /**
     * @return Returns the ctrlid.
     */
    public int getCtrlid() {
        return ctrlid;
    }

    /**
     * @param ctrlid
     *            The ctrlid to set.
     */
    public void setCtrlid(int ctrlid) {
        this.ctrlid = ctrlid;
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
     * @return Returns the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            The value to set.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * toString methode: creates a String representation of the object
     * 
     * @return the String representation
     * @author info.vancauwenberge.tostring plugin
     *  
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("FlushFdrField[");
        buffer.append("fldid = ").append(fldid);
        buffer.append(", value = ").append(value);
        buffer.append(", ctrlid = ").append(ctrlid);
        buffer.append("]");
        return buffer.toString();
    }
}