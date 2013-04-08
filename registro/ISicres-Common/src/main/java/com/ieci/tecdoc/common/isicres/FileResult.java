package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;


/**
 * @author LMVICENTE
 * @creationDate 29-jun-2004 12:30:45
 * @version @since
 */

public class FileResult implements Serializable{

    protected String logicalFileName = null;
    
    protected String physicalFileName = null;

    public FileResult() {
    }

    /**
     * @return Returns the fileName.
     */
    public String getLogicalFileName() {
        return logicalFileName;
    }
    /**
     * @param fileName The fileName to set.
     */
    public void setLogicalFileName(String fileName) {
        this.logicalFileName = fileName;
    }
    /**
     * @return Returns the physicalFileName.
     */
    public String getPhysicalFileName() {
        return physicalFileName;
    }
    /**
     * @param physicalFileName The physicalFileName to set.
     */
    public void setPhysicalFileName(String physicalFileName) {
        this.physicalFileName = physicalFileName;
    }
    /**
     * toString methode: creates a String representation of the object
     * @return the String representation
     * @author info.vancauwenberge.tostring plugin

     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("FileResult[");
        buffer.append("logicalFileName = ").append(logicalFileName);
        buffer.append(", physicalFileName = ").append(physicalFileName);
        buffer.append("]");
        return buffer.toString();
    }
}