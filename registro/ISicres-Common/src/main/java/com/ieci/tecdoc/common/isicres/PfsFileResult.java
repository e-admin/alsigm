package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;

/*
 * @author LMVICENTE @creationDate 21-jul-2004 16:14:08
 * 
 * @version @since
 */
public class PfsFileResult extends FileResult implements Serializable{

    private String path = null;

    public PfsFileResult() {

    }

    /**
     * @return Returns the path.
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     *            The path to set.
     */
    public void setPath(String path) {
        this.path = path;
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
        buffer.append("PfdFileResult[");
        buffer.append("path = ").append(path);
        buffer.append(", logicalfilename = ").append(logicalFileName);
        buffer.append(", physicalfilename = ").append(physicalFileName);
        buffer.append("]");
        return buffer.toString();
    }
}