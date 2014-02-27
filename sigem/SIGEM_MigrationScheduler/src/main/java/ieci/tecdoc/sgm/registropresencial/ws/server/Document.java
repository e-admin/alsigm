/**
 * Document.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registropresencial.ws.server;

public class Document  extends ieci.tecdoc.sgm.core.services.dto.RetornoServicio  implements java.io.Serializable {
    private java.lang.String docID;

    private java.lang.String documentContentB64;

    private java.lang.String documentName;

    private java.lang.String extension;

    private java.lang.String fileName;

    private ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder;

    private java.lang.String pageID;

    private java.lang.String pageName;

    public Document() {
    }

    public Document(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String docID,
           java.lang.String documentContentB64,
           java.lang.String documentName,
           java.lang.String extension,
           java.lang.String fileName,
           ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder,
           java.lang.String pageID,
           java.lang.String pageName) {
        super(
            errorCode,
            returnCode);
        this.docID = docID;
        this.documentContentB64 = documentContentB64;
        this.documentName = documentName;
        this.extension = extension;
        this.fileName = fileName;
        this.folder = folder;
        this.pageID = pageID;
        this.pageName = pageName;
    }


    /**
     * Gets the docID value for this Document.
     * 
     * @return docID
     */
    public java.lang.String getDocID() {
        return docID;
    }


    /**
     * Sets the docID value for this Document.
     * 
     * @param docID
     */
    public void setDocID(java.lang.String docID) {
        this.docID = docID;
    }


    /**
     * Gets the documentContentB64 value for this Document.
     * 
     * @return documentContentB64
     */
    public java.lang.String getDocumentContentB64() {
        return documentContentB64;
    }


    /**
     * Sets the documentContentB64 value for this Document.
     * 
     * @param documentContentB64
     */
    public void setDocumentContentB64(java.lang.String documentContentB64) {
        this.documentContentB64 = documentContentB64;
    }


    /**
     * Gets the documentName value for this Document.
     * 
     * @return documentName
     */
    public java.lang.String getDocumentName() {
        return documentName;
    }


    /**
     * Sets the documentName value for this Document.
     * 
     * @param documentName
     */
    public void setDocumentName(java.lang.String documentName) {
        this.documentName = documentName;
    }


    /**
     * Gets the extension value for this Document.
     * 
     * @return extension
     */
    public java.lang.String getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this Document.
     * 
     * @param extension
     */
    public void setExtension(java.lang.String extension) {
        this.extension = extension;
    }


    /**
     * Gets the fileName value for this Document.
     * 
     * @return fileName
     */
    public java.lang.String getFileName() {
        return fileName;
    }


    /**
     * Sets the fileName value for this Document.
     * 
     * @param fileName
     */
    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }


    /**
     * Gets the folder value for this Document.
     * 
     * @return folder
     */
    public ieci.tecdoc.sgm.registropresencial.ws.server.Folder getFolder() {
        return folder;
    }


    /**
     * Sets the folder value for this Document.
     * 
     * @param folder
     */
    public void setFolder(ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder) {
        this.folder = folder;
    }


    /**
     * Gets the pageID value for this Document.
     * 
     * @return pageID
     */
    public java.lang.String getPageID() {
        return pageID;
    }


    /**
     * Sets the pageID value for this Document.
     * 
     * @param pageID
     */
    public void setPageID(java.lang.String pageID) {
        this.pageID = pageID;
    }


    /**
     * Gets the pageName value for this Document.
     * 
     * @return pageName
     */
    public java.lang.String getPageName() {
        return pageName;
    }


    /**
     * Sets the pageName value for this Document.
     * 
     * @param pageName
     */
    public void setPageName(java.lang.String pageName) {
        this.pageName = pageName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Document)) return false;
        Document other = (Document) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.docID==null && other.getDocID()==null) || 
             (this.docID!=null &&
              this.docID.equals(other.getDocID()))) &&
            ((this.documentContentB64==null && other.getDocumentContentB64()==null) || 
             (this.documentContentB64!=null &&
              this.documentContentB64.equals(other.getDocumentContentB64()))) &&
            ((this.documentName==null && other.getDocumentName()==null) || 
             (this.documentName!=null &&
              this.documentName.equals(other.getDocumentName()))) &&
            ((this.extension==null && other.getExtension()==null) || 
             (this.extension!=null &&
              this.extension.equals(other.getExtension()))) &&
            ((this.fileName==null && other.getFileName()==null) || 
             (this.fileName!=null &&
              this.fileName.equals(other.getFileName()))) &&
            ((this.folder==null && other.getFolder()==null) || 
             (this.folder!=null &&
              this.folder.equals(other.getFolder()))) &&
            ((this.pageID==null && other.getPageID()==null) || 
             (this.pageID!=null &&
              this.pageID.equals(other.getPageID()))) &&
            ((this.pageName==null && other.getPageName()==null) || 
             (this.pageName!=null &&
              this.pageName.equals(other.getPageName())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getDocID() != null) {
            _hashCode += getDocID().hashCode();
        }
        if (getDocumentContentB64() != null) {
            _hashCode += getDocumentContentB64().hashCode();
        }
        if (getDocumentName() != null) {
            _hashCode += getDocumentName().hashCode();
        }
        if (getExtension() != null) {
            _hashCode += getExtension().hashCode();
        }
        if (getFileName() != null) {
            _hashCode += getFileName().hashCode();
        }
        if (getFolder() != null) {
            _hashCode += getFolder().hashCode();
        }
        if (getPageID() != null) {
            _hashCode += getPageID().hashCode();
        }
        if (getPageName() != null) {
            _hashCode += getPageName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Document.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Document"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "docID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentContentB64");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "documentContentB64"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "documentName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "extension"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "fileName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("folder");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "Folder"));
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
