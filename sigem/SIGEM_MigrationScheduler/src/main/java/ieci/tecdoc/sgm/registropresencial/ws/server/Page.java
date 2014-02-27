/**
 * Page.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registropresencial.ws.server;

public class Page  implements java.io.Serializable {
    private java.lang.String docID;

    private java.lang.String fileID;

    private java.lang.String folderId;

    private java.lang.String loc;

    private java.lang.String pageID;

    private java.lang.String pageName;

    private java.lang.String volidID;

    public Page() {
    }

    public Page(
           java.lang.String docID,
           java.lang.String fileID,
           java.lang.String folderId,
           java.lang.String loc,
           java.lang.String pageID,
           java.lang.String pageName,
           java.lang.String volidID) {
           this.docID = docID;
           this.fileID = fileID;
           this.folderId = folderId;
           this.loc = loc;
           this.pageID = pageID;
           this.pageName = pageName;
           this.volidID = volidID;
    }


    /**
     * Gets the docID value for this Page.
     * 
     * @return docID
     */
    public java.lang.String getDocID() {
        return docID;
    }


    /**
     * Sets the docID value for this Page.
     * 
     * @param docID
     */
    public void setDocID(java.lang.String docID) {
        this.docID = docID;
    }


    /**
     * Gets the fileID value for this Page.
     * 
     * @return fileID
     */
    public java.lang.String getFileID() {
        return fileID;
    }


    /**
     * Sets the fileID value for this Page.
     * 
     * @param fileID
     */
    public void setFileID(java.lang.String fileID) {
        this.fileID = fileID;
    }


    /**
     * Gets the folderId value for this Page.
     * 
     * @return folderId
     */
    public java.lang.String getFolderId() {
        return folderId;
    }


    /**
     * Sets the folderId value for this Page.
     * 
     * @param folderId
     */
    public void setFolderId(java.lang.String folderId) {
        this.folderId = folderId;
    }


    /**
     * Gets the loc value for this Page.
     * 
     * @return loc
     */
    public java.lang.String getLoc() {
        return loc;
    }


    /**
     * Sets the loc value for this Page.
     * 
     * @param loc
     */
    public void setLoc(java.lang.String loc) {
        this.loc = loc;
    }


    /**
     * Gets the pageID value for this Page.
     * 
     * @return pageID
     */
    public java.lang.String getPageID() {
        return pageID;
    }


    /**
     * Sets the pageID value for this Page.
     * 
     * @param pageID
     */
    public void setPageID(java.lang.String pageID) {
        this.pageID = pageID;
    }


    /**
     * Gets the pageName value for this Page.
     * 
     * @return pageName
     */
    public java.lang.String getPageName() {
        return pageName;
    }


    /**
     * Sets the pageName value for this Page.
     * 
     * @param pageName
     */
    public void setPageName(java.lang.String pageName) {
        this.pageName = pageName;
    }


    /**
     * Gets the volidID value for this Page.
     * 
     * @return volidID
     */
    public java.lang.String getVolidID() {
        return volidID;
    }


    /**
     * Sets the volidID value for this Page.
     * 
     * @param volidID
     */
    public void setVolidID(java.lang.String volidID) {
        this.volidID = volidID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Page)) return false;
        Page other = (Page) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.docID==null && other.getDocID()==null) || 
             (this.docID!=null &&
              this.docID.equals(other.getDocID()))) &&
            ((this.fileID==null && other.getFileID()==null) || 
             (this.fileID!=null &&
              this.fileID.equals(other.getFileID()))) &&
            ((this.folderId==null && other.getFolderId()==null) || 
             (this.folderId!=null &&
              this.folderId.equals(other.getFolderId()))) &&
            ((this.loc==null && other.getLoc()==null) || 
             (this.loc!=null &&
              this.loc.equals(other.getLoc()))) &&
            ((this.pageID==null && other.getPageID()==null) || 
             (this.pageID!=null &&
              this.pageID.equals(other.getPageID()))) &&
            ((this.pageName==null && other.getPageName()==null) || 
             (this.pageName!=null &&
              this.pageName.equals(other.getPageName()))) &&
            ((this.volidID==null && other.getVolidID()==null) || 
             (this.volidID!=null &&
              this.volidID.equals(other.getVolidID())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDocID() != null) {
            _hashCode += getDocID().hashCode();
        }
        if (getFileID() != null) {
            _hashCode += getFileID().hashCode();
        }
        if (getFolderId() != null) {
            _hashCode += getFolderId().hashCode();
        }
        if (getLoc() != null) {
            _hashCode += getLoc().hashCode();
        }
        if (getPageID() != null) {
            _hashCode += getPageID().hashCode();
        }
        if (getPageName() != null) {
            _hashCode += getPageName().hashCode();
        }
        if (getVolidID() != null) {
            _hashCode += getVolidID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Page.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Page"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "docID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "fileID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("folderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "loc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "pageID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "pageName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volidID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "volidID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
