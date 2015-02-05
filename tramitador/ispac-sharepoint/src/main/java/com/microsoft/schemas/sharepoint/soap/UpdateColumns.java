/**
 * UpdateColumns.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public class UpdateColumns  implements java.io.Serializable {
    private com.microsoft.schemas.sharepoint.soap.UpdateColumnsNewFields newFields;

    private com.microsoft.schemas.sharepoint.soap.UpdateColumnsUpdateFields updateFields;

    private com.microsoft.schemas.sharepoint.soap.UpdateColumnsDeleteFields deleteFields;

    public UpdateColumns() {
    }

    public UpdateColumns(
           com.microsoft.schemas.sharepoint.soap.UpdateColumnsNewFields newFields,
           com.microsoft.schemas.sharepoint.soap.UpdateColumnsUpdateFields updateFields,
           com.microsoft.schemas.sharepoint.soap.UpdateColumnsDeleteFields deleteFields) {
           this.newFields = newFields;
           this.updateFields = updateFields;
           this.deleteFields = deleteFields;
    }


    /**
     * Gets the newFields value for this UpdateColumns.
     * 
     * @return newFields
     */
    public com.microsoft.schemas.sharepoint.soap.UpdateColumnsNewFields getNewFields() {
        return newFields;
    }


    /**
     * Sets the newFields value for this UpdateColumns.
     * 
     * @param newFields
     */
    public void setNewFields(com.microsoft.schemas.sharepoint.soap.UpdateColumnsNewFields newFields) {
        this.newFields = newFields;
    }


    /**
     * Gets the updateFields value for this UpdateColumns.
     * 
     * @return updateFields
     */
    public com.microsoft.schemas.sharepoint.soap.UpdateColumnsUpdateFields getUpdateFields() {
        return updateFields;
    }


    /**
     * Sets the updateFields value for this UpdateColumns.
     * 
     * @param updateFields
     */
    public void setUpdateFields(com.microsoft.schemas.sharepoint.soap.UpdateColumnsUpdateFields updateFields) {
        this.updateFields = updateFields;
    }


    /**
     * Gets the deleteFields value for this UpdateColumns.
     * 
     * @return deleteFields
     */
    public com.microsoft.schemas.sharepoint.soap.UpdateColumnsDeleteFields getDeleteFields() {
        return deleteFields;
    }


    /**
     * Sets the deleteFields value for this UpdateColumns.
     * 
     * @param deleteFields
     */
    public void setDeleteFields(com.microsoft.schemas.sharepoint.soap.UpdateColumnsDeleteFields deleteFields) {
        this.deleteFields = deleteFields;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateColumns)) return false;
        UpdateColumns other = (UpdateColumns) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.newFields==null && other.getNewFields()==null) || 
             (this.newFields!=null &&
              this.newFields.equals(other.getNewFields()))) &&
            ((this.updateFields==null && other.getUpdateFields()==null) || 
             (this.updateFields!=null &&
              this.updateFields.equals(other.getUpdateFields()))) &&
            ((this.deleteFields==null && other.getDeleteFields()==null) || 
             (this.deleteFields!=null &&
              this.deleteFields.equals(other.getDeleteFields())));
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
        if (getNewFields() != null) {
            _hashCode += getNewFields().hashCode();
        }
        if (getUpdateFields() != null) {
            _hashCode += getUpdateFields().hashCode();
        }
        if (getDeleteFields() != null) {
            _hashCode += getDeleteFields().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateColumns.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">UpdateColumns"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newFields");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "newFields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">>UpdateColumns>newFields"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateFields");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "updateFields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">>UpdateColumns>updateFields"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deleteFields");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "deleteFields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">>UpdateColumns>deleteFields"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
