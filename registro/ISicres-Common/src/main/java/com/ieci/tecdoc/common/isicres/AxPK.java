//
// FileName: AxPK.java
//
package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;

/**
 * @author lmvicente
 * @version @since @creationDate 01-mar-2004
 */

public class AxPK implements Serializable {

    private int id;
    private String type;
    private AxSf axsf = null;

    public AxPK() {
    }

    public AxPK(String type, Integer id) {
        this.type = type;
        this.id = id.intValue();
    }

    public AxPK(String type, int id) {
        this.type = type;
        this.id = id;
    }

    public AxPK(String type, int id, AxSf axsf) {
        this.type = type;
        this.id = id;
        this.axsf = axsf;
    }

    /**
     * @return Returns the axsf.
     */
    public AxSf getAxsf() {
        return axsf;
    }

    /**
     * @param axsf
     *            The axsf to set.
     */
    public void setAxsf(AxSf axsf) {
        this.axsf = axsf;
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
        buffer.append(id);
        buffer.append(type);
        return buffer.toString().hashCode();
    }

    public boolean equals(Object other) {
        if (other instanceof AxPK) {
            return ((AxPK) other).getId() == id && ((AxPK) other).getType().equals(type);
        } else {
            return false;
        }
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Id [");
        buffer.append(id);
        buffer.append("] Type [");
        buffer.append(type);
        buffer.append("] Axsf [");
        buffer.append(axsf);
        buffer.append("]");
       return buffer.toString();
    }
}