/**
 * _sList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public class _sList  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private java.lang.String internalName;

    private java.lang.String title;

    private java.lang.String description;

    private java.lang.String baseType;

    private java.lang.String baseTemplate;

    private java.lang.String defaultViewUrl;

    private java.lang.String lastModified;

    private java.lang.String permId;

    private boolean inheritedSecurity;

    private boolean allowAnonymousAccess;

    private boolean anonymousViewListItems;

    private int readSecurity;

    public _sList() {
    }

    public _sList(
           java.lang.String internalName,
           java.lang.String title,
           java.lang.String description,
           java.lang.String baseType,
           java.lang.String baseTemplate,
           java.lang.String defaultViewUrl,
           java.lang.String lastModified,
           java.lang.String permId,
           boolean inheritedSecurity,
           boolean allowAnonymousAccess,
           boolean anonymousViewListItems,
           int readSecurity) {
           this.internalName = internalName;
           this.title = title;
           this.description = description;
           this.baseType = baseType;
           this.baseTemplate = baseTemplate;
           this.defaultViewUrl = defaultViewUrl;
           this.lastModified = lastModified;
           this.permId = permId;
           this.inheritedSecurity = inheritedSecurity;
           this.allowAnonymousAccess = allowAnonymousAccess;
           this.anonymousViewListItems = anonymousViewListItems;
           this.readSecurity = readSecurity;
    }


    /**
     * Gets the internalName value for this _sList.
     * 
     * @return internalName
     */
    public java.lang.String getInternalName() {
        return internalName;
    }


    /**
     * Sets the internalName value for this _sList.
     * 
     * @param internalName
     */
    public void setInternalName(java.lang.String internalName) {
        this.internalName = internalName;
    }


    /**
     * Gets the title value for this _sList.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this _sList.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }


    /**
     * Gets the description value for this _sList.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this _sList.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the baseType value for this _sList.
     * 
     * @return baseType
     */
    public java.lang.String getBaseType() {
        return baseType;
    }


    /**
     * Sets the baseType value for this _sList.
     * 
     * @param baseType
     */
    public void setBaseType(java.lang.String baseType) {
        this.baseType = baseType;
    }


    /**
     * Gets the baseTemplate value for this _sList.
     * 
     * @return baseTemplate
     */
    public java.lang.String getBaseTemplate() {
        return baseTemplate;
    }


    /**
     * Sets the baseTemplate value for this _sList.
     * 
     * @param baseTemplate
     */
    public void setBaseTemplate(java.lang.String baseTemplate) {
        this.baseTemplate = baseTemplate;
    }


    /**
     * Gets the defaultViewUrl value for this _sList.
     * 
     * @return defaultViewUrl
     */
    public java.lang.String getDefaultViewUrl() {
        return defaultViewUrl;
    }


    /**
     * Sets the defaultViewUrl value for this _sList.
     * 
     * @param defaultViewUrl
     */
    public void setDefaultViewUrl(java.lang.String defaultViewUrl) {
        this.defaultViewUrl = defaultViewUrl;
    }


    /**
     * Gets the lastModified value for this _sList.
     * 
     * @return lastModified
     */
    public java.lang.String getLastModified() {
        return lastModified;
    }


    /**
     * Sets the lastModified value for this _sList.
     * 
     * @param lastModified
     */
    public void setLastModified(java.lang.String lastModified) {
        this.lastModified = lastModified;
    }


    /**
     * Gets the permId value for this _sList.
     * 
     * @return permId
     */
    public java.lang.String getPermId() {
        return permId;
    }


    /**
     * Sets the permId value for this _sList.
     * 
     * @param permId
     */
    public void setPermId(java.lang.String permId) {
        this.permId = permId;
    }


    /**
     * Gets the inheritedSecurity value for this _sList.
     * 
     * @return inheritedSecurity
     */
    public boolean isInheritedSecurity() {
        return inheritedSecurity;
    }


    /**
     * Sets the inheritedSecurity value for this _sList.
     * 
     * @param inheritedSecurity
     */
    public void setInheritedSecurity(boolean inheritedSecurity) {
        this.inheritedSecurity = inheritedSecurity;
    }


    /**
     * Gets the allowAnonymousAccess value for this _sList.
     * 
     * @return allowAnonymousAccess
     */
    public boolean isAllowAnonymousAccess() {
        return allowAnonymousAccess;
    }


    /**
     * Sets the allowAnonymousAccess value for this _sList.
     * 
     * @param allowAnonymousAccess
     */
    public void setAllowAnonymousAccess(boolean allowAnonymousAccess) {
        this.allowAnonymousAccess = allowAnonymousAccess;
    }


    /**
     * Gets the anonymousViewListItems value for this _sList.
     * 
     * @return anonymousViewListItems
     */
    public boolean isAnonymousViewListItems() {
        return anonymousViewListItems;
    }


    /**
     * Sets the anonymousViewListItems value for this _sList.
     * 
     * @param anonymousViewListItems
     */
    public void setAnonymousViewListItems(boolean anonymousViewListItems) {
        this.anonymousViewListItems = anonymousViewListItems;
    }


    /**
     * Gets the readSecurity value for this _sList.
     * 
     * @return readSecurity
     */
    public int getReadSecurity() {
        return readSecurity;
    }


    /**
     * Sets the readSecurity value for this _sList.
     * 
     * @param readSecurity
     */
    public void setReadSecurity(int readSecurity) {
        this.readSecurity = readSecurity;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof _sList)) return false;
        _sList other = (_sList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.internalName==null && other.getInternalName()==null) || 
             (this.internalName!=null &&
              this.internalName.equals(other.getInternalName()))) &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.baseType==null && other.getBaseType()==null) || 
             (this.baseType!=null &&
              this.baseType.equals(other.getBaseType()))) &&
            ((this.baseTemplate==null && other.getBaseTemplate()==null) || 
             (this.baseTemplate!=null &&
              this.baseTemplate.equals(other.getBaseTemplate()))) &&
            ((this.defaultViewUrl==null && other.getDefaultViewUrl()==null) || 
             (this.defaultViewUrl!=null &&
              this.defaultViewUrl.equals(other.getDefaultViewUrl()))) &&
            ((this.lastModified==null && other.getLastModified()==null) || 
             (this.lastModified!=null &&
              this.lastModified.equals(other.getLastModified()))) &&
            ((this.permId==null && other.getPermId()==null) || 
             (this.permId!=null &&
              this.permId.equals(other.getPermId()))) &&
            this.inheritedSecurity == other.isInheritedSecurity() &&
            this.allowAnonymousAccess == other.isAllowAnonymousAccess() &&
            this.anonymousViewListItems == other.isAnonymousViewListItems() &&
            this.readSecurity == other.getReadSecurity();
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
        if (getInternalName() != null) {
            _hashCode += getInternalName().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getBaseType() != null) {
            _hashCode += getBaseType().hashCode();
        }
        if (getBaseTemplate() != null) {
            _hashCode += getBaseTemplate().hashCode();
        }
        if (getDefaultViewUrl() != null) {
            _hashCode += getDefaultViewUrl().hashCode();
        }
        if (getLastModified() != null) {
            _hashCode += getLastModified().hashCode();
        }
        if (getPermId() != null) {
            _hashCode += getPermId().hashCode();
        }
        _hashCode += (isInheritedSecurity() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isAllowAnonymousAccess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isAnonymousViewListItems() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getReadSecurity();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(_sList.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sList"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("internalName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "InternalName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Title"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("baseType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "BaseType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("baseTemplate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "BaseTemplate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultViewUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "DefaultViewUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastModified");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "LastModified"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("permId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "PermId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inheritedSecurity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "InheritedSecurity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allowAnonymousAccess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "AllowAnonymousAccess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anonymousViewListItems");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "AnonymousViewListItems"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("readSecurity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ReadSecurity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
