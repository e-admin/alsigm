//
// FileName: AxPK.java
//
package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;

/**
 * @author lmvicente
 * @version @since @creationDate 01-mar-2004
 */

public class AxPKById implements Serializable {

    private int fdrId;
    private String type;
    private int id;
    
    public AxPKById() {
    }

    public AxPKById(String type, Integer id) {
        this.type = type;
        this.fdrId = id.intValue();
    }

    public AxPKById(String type, int id) {
        this.type = type;
        this.fdrId = id;
    }

    public AxPKById(String type, int fdrId, int id) {
        this.type = type;
        this.fdrId = fdrId;
        this.id = id;
    }

    /**
     * @return Returns the axsf.
     */
    public int getId() {
        return id;
    }

    /**
     * @param axsf
     *            The axsf to set.
     */
    public void setId(int fldId) {
        this.id = fldId;
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
        buffer.append(id);
        return buffer.toString().hashCode();
    }

    public boolean equals(Object other) {
        if (other instanceof AxPKById) {
            return ((AxPKById) other).getFdrId() == fdrId && ((AxPKById) other).getType().equals(type) && ((AxPKById) other).getId() == (id);
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
        buffer.append("] id [");
        buffer.append(id);
        buffer.append("]");
       return buffer.toString();
    }
}