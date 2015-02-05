package com.ieci.tecdoc.idoc.flushfdr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * @author LMVICENTE
 * @creationDate 07-sep-2004 16:42:04
 * @version
 * @since
 */
public class FlushFdrDocument implements Serializable {

    private int fixedValue = 1;
    private String className = "CL2";
    private String treeId = null;
    private String documentName = null;
    private String fatherId = null;
    private String fatherClassName = null;
    private List pages = new ArrayList();
    private int docID = 0;
    
    /**
     * @return Returns the docID.
     */
    public int getDocID() {
        return docID;
    }
    /**
     * @param docID The docID to set.
     */
    public void setDocID(int docID) {
        this.docID = docID;
    }
    public FlushFdrDocument() {
        super();
     }
    
    public void addPage(FlushFdrPage page) {
        pages.add(page);
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
     * @return Returns the documentName.
     */
    public String getDocumentName() {
        return documentName;
    }
    /**
     * @param documentName The documentName to set.
     */
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
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
        buffer.append("FlushFdrDocument[");
        buffer.append("fixedValue = ").append(fixedValue);
        buffer.append(", className = ").append(className);
        buffer.append(", treeId = ").append(treeId);
        buffer.append(", documentName = ").append(documentName);
        buffer.append(", fatherId = ").append(fatherId);
        buffer.append(", fatherClassName = ").append(fatherClassName);
        buffer.append("]");
        return buffer.toString();
    }
    /**
     * @return Returns the pages.
     */
    public List getPages() {
        return pages;
    }
    /**
     * @param pages The pages to set.
     */
    public void setPages(List pages) {
        this.pages = pages;
    }
}
