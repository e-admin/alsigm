/**
 * FolderSearchCriteria.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registropresencial.ws.server;

public class FolderSearchCriteria  extends ieci.tecdoc.sgm.core.services.dto.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.registropresencial.ws.server.BookId bookId;

    private ieci.tecdoc.sgm.registropresencial.ws.server.FieldsSearchCriteria fields;

    private java.lang.String folderId;

    private java.lang.String folderNumber;

    public FolderSearchCriteria() {
    }

    public FolderSearchCriteria(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.registropresencial.ws.server.BookId bookId,
           ieci.tecdoc.sgm.registropresencial.ws.server.FieldsSearchCriteria fields,
           java.lang.String folderId,
           java.lang.String folderNumber) {
        super(
            errorCode,
            returnCode);
        this.bookId = bookId;
        this.fields = fields;
        this.folderId = folderId;
        this.folderNumber = folderNumber;
    }


    /**
     * Gets the bookId value for this FolderSearchCriteria.
     * 
     * @return bookId
     */
    public ieci.tecdoc.sgm.registropresencial.ws.server.BookId getBookId() {
        return bookId;
    }


    /**
     * Sets the bookId value for this FolderSearchCriteria.
     * 
     * @param bookId
     */
    public void setBookId(ieci.tecdoc.sgm.registropresencial.ws.server.BookId bookId) {
        this.bookId = bookId;
    }


    /**
     * Gets the fields value for this FolderSearchCriteria.
     * 
     * @return fields
     */
    public ieci.tecdoc.sgm.registropresencial.ws.server.FieldsSearchCriteria getFields() {
        return fields;
    }


    /**
     * Sets the fields value for this FolderSearchCriteria.
     * 
     * @param fields
     */
    public void setFields(ieci.tecdoc.sgm.registropresencial.ws.server.FieldsSearchCriteria fields) {
        this.fields = fields;
    }


    /**
     * Gets the folderId value for this FolderSearchCriteria.
     * 
     * @return folderId
     */
    public java.lang.String getFolderId() {
        return folderId;
    }


    /**
     * Sets the folderId value for this FolderSearchCriteria.
     * 
     * @param folderId
     */
    public void setFolderId(java.lang.String folderId) {
        this.folderId = folderId;
    }


    /**
     * Gets the folderNumber value for this FolderSearchCriteria.
     * 
     * @return folderNumber
     */
    public java.lang.String getFolderNumber() {
        return folderNumber;
    }


    /**
     * Sets the folderNumber value for this FolderSearchCriteria.
     * 
     * @param folderNumber
     */
    public void setFolderNumber(java.lang.String folderNumber) {
        this.folderNumber = folderNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FolderSearchCriteria)) return false;
        FolderSearchCriteria other = (FolderSearchCriteria) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.bookId==null && other.getBookId()==null) || 
             (this.bookId!=null &&
              this.bookId.equals(other.getBookId()))) &&
            ((this.fields==null && other.getFields()==null) || 
             (this.fields!=null &&
              this.fields.equals(other.getFields()))) &&
            ((this.folderId==null && other.getFolderId()==null) || 
             (this.folderId!=null &&
              this.folderId.equals(other.getFolderId()))) &&
            ((this.folderNumber==null && other.getFolderNumber()==null) || 
             (this.folderNumber!=null &&
              this.folderNumber.equals(other.getFolderNumber())));
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
        if (getBookId() != null) {
            _hashCode += getBookId().hashCode();
        }
        if (getFields() != null) {
            _hashCode += getFields().hashCode();
        }
        if (getFolderId() != null) {
            _hashCode += getFolderId().hashCode();
        }
        if (getFolderNumber() != null) {
            _hashCode += getFolderNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FolderSearchCriteria.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FolderSearchCriteria"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bookId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "bookId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "BookId"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fields");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "fields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FieldsSearchCriteria"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("folderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("folderNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folderNumber"));
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
