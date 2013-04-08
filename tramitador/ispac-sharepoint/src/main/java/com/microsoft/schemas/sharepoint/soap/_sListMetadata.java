/**
 * _sListMetadata.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public class _sListMetadata  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private java.lang.String title;

    private java.lang.String description;

    private java.lang.String baseType;

    private java.lang.String baseTemplate;

    private java.lang.String defaultViewUrl;

    private java.util.Calendar lastModified;

    private java.util.Calendar lastModifiedForceRecrawl;

    private java.lang.String author;

    private boolean validSecurityInfo;

    private boolean inheritedSecurity;

    private boolean allowAnonymousAccess;

    private boolean anonymousViewListItems;

    private int readSecurity;

    private java.lang.String permissions;

    public _sListMetadata() {
    }

    public _sListMetadata(
           java.lang.String title,
           java.lang.String description,
           java.lang.String baseType,
           java.lang.String baseTemplate,
           java.lang.String defaultViewUrl,
           java.util.Calendar lastModified,
           java.util.Calendar lastModifiedForceRecrawl,
           java.lang.String author,
           boolean validSecurityInfo,
           boolean inheritedSecurity,
           boolean allowAnonymousAccess,
           boolean anonymousViewListItems,
           int readSecurity,
           java.lang.String permissions) {
           this.title = title;
           this.description = description;
           this.baseType = baseType;
           this.baseTemplate = baseTemplate;
           this.defaultViewUrl = defaultViewUrl;
           this.lastModified = lastModified;
           this.lastModifiedForceRecrawl = lastModifiedForceRecrawl;
           this.author = author;
           this.validSecurityInfo = validSecurityInfo;
           this.inheritedSecurity = inheritedSecurity;
           this.allowAnonymousAccess = allowAnonymousAccess;
           this.anonymousViewListItems = anonymousViewListItems;
           this.readSecurity = readSecurity;
           this.permissions = permissions;
    }


    /**
     * Gets the title value for this _sListMetadata.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this _sListMetadata.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }


    /**
     * Gets the description value for this _sListMetadata.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this _sListMetadata.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the baseType value for this _sListMetadata.
     * 
     * @return baseType
     */
    public java.lang.String getBaseType() {
        return baseType;
    }


    /**
     * Sets the baseType value for this _sListMetadata.
     * 
     * @param baseType
     */
    public void setBaseType(java.lang.String baseType) {
        this.baseType = baseType;
    }


    /**
     * Gets the baseTemplate value for this _sListMetadata.
     * 
     * @return baseTemplate
     */
    public java.lang.String getBaseTemplate() {
        return baseTemplate;
    }


    /**
     * Sets the baseTemplate value for this _sListMetadata.
     * 
     * @param baseTemplate
     */
    public void setBaseTemplate(java.lang.String baseTemplate) {
        this.baseTemplate = baseTemplate;
    }


    /**
     * Gets the defaultViewUrl value for this _sListMetadata.
     * 
     * @return defaultViewUrl
     */
    public java.lang.String getDefaultViewUrl() {
        return defaultViewUrl;
    }


    /**
     * Sets the defaultViewUrl value for this _sListMetadata.
     * 
     * @param defaultViewUrl
     */
    public void setDefaultViewUrl(java.lang.String defaultViewUrl) {
        this.defaultViewUrl = defaultViewUrl;
    }


    /**
     * Gets the lastModified value for this _sListMetadata.
     * 
     * @return lastModified
     */
    public java.util.Calendar getLastModified() {
        return lastModified;
    }


    /**
     * Sets the lastModified value for this _sListMetadata.
     * 
     * @param lastModified
     */
    public void setLastModified(java.util.Calendar lastModified) {
        this.lastModified = lastModified;
    }


    /**
     * Gets the lastModifiedForceRecrawl value for this _sListMetadata.
     * 
     * @return lastModifiedForceRecrawl
     */
    public java.util.Calendar getLastModifiedForceRecrawl() {
        return lastModifiedForceRecrawl;
    }


    /**
     * Sets the lastModifiedForceRecrawl value for this _sListMetadata.
     * 
     * @param lastModifiedForceRecrawl
     */
    public void setLastModifiedForceRecrawl(java.util.Calendar lastModifiedForceRecrawl) {
        this.lastModifiedForceRecrawl = lastModifiedForceRecrawl;
    }


    /**
     * Gets the author value for this _sListMetadata.
     * 
     * @return author
     */
    public java.lang.String getAuthor() {
        return author;
    }


    /**
     * Sets the author value for this _sListMetadata.
     * 
     * @param author
     */
    public void setAuthor(java.lang.String author) {
        this.author = author;
    }


    /**
     * Gets the validSecurityInfo value for this _sListMetadata.
     * 
     * @return validSecurityInfo
     */
    public boolean isValidSecurityInfo() {
        return validSecurityInfo;
    }


    /**
     * Sets the validSecurityInfo value for this _sListMetadata.
     * 
     * @param validSecurityInfo
     */
    public void setValidSecurityInfo(boolean validSecurityInfo) {
        this.validSecurityInfo = validSecurityInfo;
    }


    /**
     * Gets the inheritedSecurity value for this _sListMetadata.
     * 
     * @return inheritedSecurity
     */
    public boolean isInheritedSecurity() {
        return inheritedSecurity;
    }


    /**
     * Sets the inheritedSecurity value for this _sListMetadata.
     * 
     * @param inheritedSecurity
     */
    public void setInheritedSecurity(boolean inheritedSecurity) {
        this.inheritedSecurity = inheritedSecurity;
    }


    /**
     * Gets the allowAnonymousAccess value for this _sListMetadata.
     * 
     * @return allowAnonymousAccess
     */
    public boolean isAllowAnonymousAccess() {
        return allowAnonymousAccess;
    }


    /**
     * Sets the allowAnonymousAccess value for this _sListMetadata.
     * 
     * @param allowAnonymousAccess
     */
    public void setAllowAnonymousAccess(boolean allowAnonymousAccess) {
        this.allowAnonymousAccess = allowAnonymousAccess;
    }


    /**
     * Gets the anonymousViewListItems value for this _sListMetadata.
     * 
     * @return anonymousViewListItems
     */
    public boolean isAnonymousViewListItems() {
        return anonymousViewListItems;
    }


    /**
     * Sets the anonymousViewListItems value for this _sListMetadata.
     * 
     * @param anonymousViewListItems
     */
    public void setAnonymousViewListItems(boolean anonymousViewListItems) {
        this.anonymousViewListItems = anonymousViewListItems;
    }


    /**
     * Gets the readSecurity value for this _sListMetadata.
     * 
     * @return readSecurity
     */
    public int getReadSecurity() {
        return readSecurity;
    }


    /**
     * Sets the readSecurity value for this _sListMetadata.
     * 
     * @param readSecurity
     */
    public void setReadSecurity(int readSecurity) {
        this.readSecurity = readSecurity;
    }


    /**
     * Gets the permissions value for this _sListMetadata.
     * 
     * @return permissions
     */
    public java.lang.String getPermissions() {
        return permissions;
    }


    /**
     * Sets the permissions value for this _sListMetadata.
     * 
     * @param permissions
     */
    public void setPermissions(java.lang.String permissions) {
        this.permissions = permissions;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof _sListMetadata)) return false;
        _sListMetadata other = (_sListMetadata) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
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
            ((this.lastModifiedForceRecrawl==null && other.getLastModifiedForceRecrawl()==null) || 
             (this.lastModifiedForceRecrawl!=null &&
              this.lastModifiedForceRecrawl.equals(other.getLastModifiedForceRecrawl()))) &&
            ((this.author==null && other.getAuthor()==null) || 
             (this.author!=null &&
              this.author.equals(other.getAuthor()))) &&
            this.validSecurityInfo == other.isValidSecurityInfo() &&
            this.inheritedSecurity == other.isInheritedSecurity() &&
            this.allowAnonymousAccess == other.isAllowAnonymousAccess() &&
            this.anonymousViewListItems == other.isAnonymousViewListItems() &&
            this.readSecurity == other.getReadSecurity() &&
            ((this.permissions==null && other.getPermissions()==null) || 
             (this.permissions!=null &&
              this.permissions.equals(other.getPermissions())));
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
        if (getLastModifiedForceRecrawl() != null) {
            _hashCode += getLastModifiedForceRecrawl().hashCode();
        }
        if (getAuthor() != null) {
            _hashCode += getAuthor().hashCode();
        }
        _hashCode += (isValidSecurityInfo() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isInheritedSecurity() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isAllowAnonymousAccess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isAnonymousViewListItems() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getReadSecurity();
        if (getPermissions() != null) {
            _hashCode += getPermissions().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(_sListMetadata.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sListMetadata"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastModifiedForceRecrawl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "LastModifiedForceRecrawl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("author");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Author"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validSecurityInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ValidSecurityInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("permissions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Permissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
