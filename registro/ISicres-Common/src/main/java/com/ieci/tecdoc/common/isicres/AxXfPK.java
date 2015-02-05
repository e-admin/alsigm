//
// FileName: AxPK.java
//
package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;

/**
 * @author lmvicente
 * @version @since @creationDate 01-mar-2004
 */

public class AxXfPK implements Serializable {

    private int fdrId;
    private String type;
    private int fldId;
    
    public AxXfPK() {
    }

    public AxXfPK(String type, Integer id) {
        this.type = type;
        this.fdrId = id.intValue();
    }

    public AxXfPK(String type, int id) {
        this.type = type;
        this.fdrId = id;
    }

    public AxXfPK(String type, int fdrId, int fldId) {
        this.type = type;
        this.fdrId = fdrId;
        this.fldId = fldId;
    }

    /**
     * @return Returns the axsf.
     */
    public int getFldId() {
        return fldId;
    }

    /**
     * @param axsf
     *            The axsf to set.
     */
    public void setFldId(int fldId) {
        this.fldId = fldId;
    }

    /**
     * @return Returns the id.
     */
    public int getFdrId() {
        return fdrId;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setFdrId(int id) {
        this.fdrId = id;
    }

    /**
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    public int hashCode() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(fdrId);
        buffer.append(type);
        buffer.append(fldId);
        return buffer.toString().hashCode();
    }

    public boolean equals(Object other) {
        if (other instanceof AxXfPK) {
            return ((AxXfPK) other).getFdrId() == fdrId && ((AxXfPK) other).getType().equals(type) && ((AxXfPK) other).getFldId() == (fldId);
        } else {
            return false;
        }
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("fdrId [");
        buffer.append(fdrId);
        buffer.append("] Type [");
        buffer.append(type);
        buffer.append("] fldId [");
        buffer.append(fldId);
        buffer.append("]");
       return buffer.toString();
    }
}