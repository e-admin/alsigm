package com.ieci.tecdoc.idoc.flushfdr;

import java.io.Serializable;

/*
 * @author LMVICENTE
 * @creationDate 07-sep-2004 16:44:15
 * @version
 * @since
 */
public class FlushFdrPage implements Serializable {

    private int fixedValue = 1;
    private String className = "CL3";
    private String treeId = null;
    private String pageName = null;
    private String fatherId = null;
    private String fatherClassName = null;
    private FlushFdrFile file = null;
  
    public FlushFdrPage() {
    }
    
    /**
     * @return Returns the file.
     */
    public FlushFdrFile getFile() {
        return file;
    }
    /**
     * @param file The file to set.
     */
    public void setFile(FlushFdrFile file) {
        this.file = file;
    }
    
    /**
     * @return Returns the className.
     */
    public String getClassName() {
        return className;
    }
    /**
     * @param className The className to set.
     */
    public void setClassName(String className) {
        this.className = className;
    }
    /**
     * @return Returns the fatherClassName.
     */
    public String getFatherClassName() {
        return fatherClassName;
    }
    /**
     * @param fatherClassName The fatherClassName to set.
     */
    public void setFatherClassName(String fatherClassName) {
        this.fatherClassName = fatherClassName;
    }
    /**
     * @return Returns the fatherId.
     */
    public String getFatherId() {
        return fatherId;
    }
    /**
     * @param fatherId The fatherId to set.
     */
    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }
    /**
     * @return Returns the fixedValue.
     */
    public int getFixedValue() {
        return fixedValue;
    }
    /**
     * @param fixedValue The fixedValue to set.
     */
    public void setFixedValue(int fixedValue) {
        this.fixedValue = fixedValue;
    }
    /**
     * @return Returns the pageName.
     */
    public String getPageName() {
        return pageName;
    }
    /**
     * @param pageName The pageName to set.
     */
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
    /**
     * @return Returns the treeId.
     */
    public String getTreeId() {
        return treeId;
    }
    /**
     * @param treeId The treeId to set.
     */
    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }
    /**
     * toString methode: creates a String representation of the object
     * @return the String representation
     * @author info.vancauwenberge.tostring plugin

     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("FlushFdrPage[");
        buffer.append("fixedValue = ").append(fixedValue);
        buffer.append(", className = ").append(className);
        buffer.append(", treeId = ").append(treeId);
        buffer.append(", pageName = ").append(pageName);
        buffer.append(", fatherId = ").append(fatherId);
        buffer.append(", fatherClassName = ").append(fatherClassName);
        buffer.append("]");
        return buffer.toString();
    }
}
