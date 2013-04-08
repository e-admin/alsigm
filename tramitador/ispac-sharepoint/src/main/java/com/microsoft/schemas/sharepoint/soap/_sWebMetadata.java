/**
 * _sWebMetadata.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public class _sWebMetadata  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private java.lang.String webID;

    private java.lang.String title;

    private java.lang.String description;

    private java.lang.String author;

    private org.apache.axis.types.UnsignedInt language;

    private java.util.Calendar lastModified;

    private java.util.Calendar lastModifiedForceRecrawl;

    private java.lang.String noIndex;

    private boolean validSecurityInfo;

    private boolean inheritedSecurity;

    private boolean allowAnonymousAccess;

    private boolean anonymousViewListItems;

    private java.lang.String permissions;

    private boolean externalSecurity;

    private java.lang.String categoryId;

    private java.lang.String categoryName;

    private java.lang.String categoryIdPath;

    private boolean isBucketWeb;

    private boolean usedInAutocat;

    private java.lang.String categoryBucketID;

    public _sWebMetadata() {
    }

    public _sWebMetadata(
           java.lang.String webID,
           java.lang.String title,
           java.lang.String description,
           java.lang.String author,
           org.apache.axis.types.UnsignedInt language,
           java.util.Calendar lastModified,
           java.util.Calendar lastModifiedForceRecrawl,
           java.lang.String noIndex,
           boolean validSecurityInfo,
           boolean inheritedSecurity,
           boolean allowAnonymousAccess,
           boolean anonymousViewListItems,
           java.lang.String permissions,
           boolean externalSecurity,
           java.lang.String categoryId,
           java.lang.String categoryName,
           java.lang.String categoryIdPath,
           boolean isBucketWeb,
           boolean usedInAutocat,
           java.lang.String categoryBucketID) {
           this.webID = webID;
           this.title = title;
           this.description = description;
           this.author = author;
           this.language = language;
           this.lastModified = lastModified;
           this.lastModifiedForceRecrawl = lastModifiedForceRecrawl;
           this.noIndex = noIndex;
           this.validSecurityInfo = validSecurityInfo;
           this.inheritedSecurity = inheritedSecurity;
           this.allowAnonymousAccess = allowAnonymousAccess;
           this.anonymousViewListItems = anonymousViewListItems;
           this.permissions = permissions;
           this.externalSecurity = externalSecurity;
           this.categoryId = categoryId;
           this.categoryName = categoryName;
           this.categoryIdPath = categoryIdPath;
           this.isBucketWeb = isBucketWeb;
           this.usedInAutocat = usedInAutocat;
           this.categoryBucketID = categoryBucketID;
    }


    /**
     * Gets the webID value for this _sWebMetadata.
     * 
     * @return webID
     */
    public java.lang.String getWebID() {
        return webID;
    }


    /**
     * Sets the webID value for this _sWebMetadata.
     * 
     * @param webID
     */
    public void setWebID(java.lang.String webID) {
        this.webID = webID;
    }


    /**
     * Gets the title value for this _sWebMetadata.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this _sWebMetadata.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }


    /**
     * Gets the description value for this _sWebMetadata.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this _sWebMetadata.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the author value for this _sWebMetadata.
     * 
     * @return author
     */
    public java.lang.String getAuthor() {
        return author;
    }


    /**
     * Sets the author value for this _sWebMetadata.
     * 
     * @param author
     */
    public void setAuthor(java.lang.String author) {
        this.author = author;
    }


    /**
     * Gets the language value for this _sWebMetadata.
     * 
     * @return language
     */
    public org.apache.axis.types.UnsignedInt getLanguage() {
        return language;
    }


    /**
     * Sets the language value for this _sWebMetadata.
     * 
     * @param language
     */
    public void setLanguage(org.apache.axis.types.UnsignedInt language) {
        this.language = language;
    }


    /**
     * Gets the lastModified value for this _sWebMetadata.
     * 
     * @return lastModified
     */
    public java.util.Calendar getLastModified() {
        return lastModified;
    }


    /**
     * Sets the lastModified value for this _sWebMetadata.
     * 
     * @param lastModified
     */
    public void setLastModified(java.util.Calendar lastModified) {
        this.lastModified = lastModified;
    }


    /**
     * Gets the lastModifiedForceRecrawl value for this _sWebMetadata.
     * 
     * @return lastModifiedForceRecrawl
     */
    public java.util.Calendar getLastModifiedForceRecrawl() {
        return lastModifiedForceRecrawl;
    }


    /**
     * Sets the lastModifiedForceRecrawl value for this _sWebMetadata.
     * 
     * @param lastModifiedForceRecrawl
     */
    public void setLastModifiedForceRecrawl(java.util.Calendar lastModifiedForceRecrawl) {
        this.lastModifiedForceRecrawl = lastModifiedForceRecrawl;
    }


    /**
     * Gets the noIndex value for this _sWebMetadata.
     * 
     * @return noIndex
     */
    public java.lang.String getNoIndex() {
        return noIndex;
    }


    /**
     * Sets the noIndex value for this _sWebMetadata.
     * 
     * @param noIndex
     */
    public void setNoIndex(java.lang.String noIndex) {
        this.noIndex = noIndex;
    }


    /**
     * Gets the validSecurityInfo value for this _sWebMetadata.
     * 
     * @return validSecurityInfo
     */
    public boolean isValidSecurityInfo() {
        return validSecurityInfo;
    }


    /**
     * Sets the validSecurityInfo value for this _sWebMetadata.
     * 
     * @param validSecurityInfo
     */
    public void setValidSecurityInfo(boolean validSecurityInfo) {
        this.validSecurityInfo = validSecurityInfo;
    }


    /**
     * Gets the inheritedSecurity value for this _sWebMetadata.
     * 
     * @return inheritedSecurity
     */
    public boolean isInheritedSecurity() {
        return inheritedSecurity;
    }


    /**
     * Sets the inheritedSecurity value for this _sWebMetadata.
     * 
     * @param inheritedSecurity
     */
    public void setInheritedSecurity(boolean inheritedSecurity) {
        this.inheritedSecurity = inheritedSecurity;
    }


    /**
     * Gets the allowAnonymousAccess value for this _sWebMetadata.
     * 
     * @return allowAnonymousAccess
     */
    public boolean isAllowAnonymousAccess() {
        return allowAnonymousAccess;
    }


    /**
     * Sets the allowAnonymousAccess value for this _sWebMetadata.
     * 
     * @param allowAnonymousAccess
     */
    public void setAllowAnonymousAccess(boolean allowAnonymousAccess) {
        this.allowAnonymousAccess = allowAnonymousAccess;
    }


    /**
     * Gets the anonymousViewListItems value for this _sWebMetadata.
     * 
     * @return anonymousViewListItems
     */
    public boolean isAnonymousViewListItems() {
        return anonymousViewListItems;
    }


    /**
     * Sets the anonymousViewListItems value for this _sWebMetadata.
     * 
     * @param anonymousViewListItems
     */
    public void setAnonymousViewListItems(boolean anonymousViewListItems) {
        this.anonymousViewListItems = anonymousViewListItems;
    }


    /**
     * Gets the permissions value for this _sWebMetadata.
     * 
     * @return permissions
     */
    public java.lang.String getPermissions() {
        return permissions;
    }


    /**
     * Sets the permissions value for this _sWebMetadata.
     * 
     * @param permissions
     */
    public void setPermissions(java.lang.String permissions) {
        this.permissions = permissions;
    }


    /**
     * Gets the externalSecurity value for this _sWebMetadata.
     * 
     * @return externalSecurity
     */
    public boolean isExternalSecurity() {
        return externalSecurity;
    }


    /**
     * Sets the externalSecurity value for this _sWebMetadata.
     * 
     * @param externalSecurity
     */
    public void setExternalSecurity(boolean externalSecurity) {
        this.externalSecurity = externalSecurity;
    }


    /**
     * Gets the categoryId value for this _sWebMetadata.
     * 
     * @return categoryId
     */
    public java.lang.String getCategoryId() {
        return categoryId;
    }


    /**
     * Sets the categoryId value for this _sWebMetadata.
     * 
     * @param categoryId
     */
    public void setCategoryId(java.lang.String categoryId) {
        this.categoryId = categoryId;
    }


    /**
     * Gets the categoryName value for this _sWebMetadata.
     * 
     * @return categoryName
     */
    public java.lang.String getCategoryName() {
        return categoryName;
    }


    /**
     * Sets the categoryName value for this _sWebMetadata.
     * 
     * @param categoryName
     */
    public void setCategoryName(java.lang.String categoryName) {
        this.categoryName = categoryName;
    }


    /**
     * Gets the categoryIdPath value for this _sWebMetadata.
     * 
     * @return categoryIdPath
     */
    public java.lang.String getCategoryIdPath() {
        return categoryIdPath;
    }


    /**
     * Sets the categoryIdPath value for this _sWebMetadata.
     * 
     * @param categoryIdPath
     */
    public void setCategoryIdPath(java.lang.String categoryIdPath) {
        this.categoryIdPath = categoryIdPath;
    }


    /**
     * Gets the isBucketWeb value for this _sWebMetadata.
     * 
     * @return isBucketWeb
     */
    public boolean isIsBucketWeb() {
        return isBucketWeb;
    }


    /**
     * Sets the isBucketWeb value for this _sWebMetadata.
     * 
     * @param isBucketWeb
     */
    public void setIsBucketWeb(boolean isBucketWeb) {
        this.isBucketWeb = isBucketWeb;
    }


    /**
     * Gets the usedInAutocat value for this _sWebMetadata.
     * 
     * @return usedInAutocat
     */
    public boolean isUsedInAutocat() {
        return usedInAutocat;
    }


    /**
     * Sets the usedInAutocat value for this _sWebMetadata.
     * 
     * @param usedInAutocat
     */
    public void setUsedInAutocat(boolean usedInAutocat) {
        this.usedInAutocat = usedInAutocat;
    }


    /**
     * Gets the categoryBucketID value for this _sWebMetadata.
     * 
     * @return categoryBucketID
     */
    public java.lang.String getCategoryBucketID() {
        return categoryBucketID;
    }


    /**
     * Sets the categoryBucketID value for this _sWebMetadata.
     * 
     * @param categoryBucketID
     */
    public void setCategoryBucketID(java.lang.String categoryBucketID) {
        this.categoryBucketID = categoryBucketID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof _sWebMetadata)) return false;
        _sWebMetadata other = (_sWebMetadata) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.webID==null && other.getWebID()==null) || 
             (this.webID!=null &&
              this.webID.equals(other.getWebID()))) &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.author==null && other.getAuthor()==null) || 
             (this.author!=null &&
              this.author.equals(other.getAuthor()))) &&
            ((this.language==null && other.getLanguage()==null) || 
             (this.language!=null &&
              this.language.equals(other.getLanguage()))) &&
            ((this.lastModified==null && other.getLastModified()==null) || 
             (this.lastModified!=null &&
              this.lastModified.equals(other.getLastModified()))) &&
            ((this.lastModifiedForceRecrawl==null && other.getLastModifiedForceRecrawl()==null) || 
             (this.lastModifiedForceRecrawl!=null &&
              this.lastModifiedForceRecrawl.equals(other.getLastModifiedForceRecrawl()))) &&
            ((this.noIndex==null && other.getNoIndex()==null) || 
             (this.noIndex!=null &&
              this.noIndex.equals(other.getNoIndex()))) &&
            this.validSecurityInfo == other.isValidSecurityInfo() &&
            this.inheritedSecurity == other.isInheritedSecurity() &&
            this.allowAnonymousAccess == other.isAllowAnonymousAccess() &&
            this.anonymousViewListItems == other.isAnonymousViewListItems() &&
            ((this.permissions==null && other.getPermissions()==null) || 
             (this.permissions!=null &&
              this.permissions.equals(other.getPermissions()))) &&
            this.externalSecurity == other.isExternalSecurity() &&
            ((this.categoryId==null && other.getCategoryId()==null) || 
             (this.categoryId!=null &&
              this.categoryId.equals(other.getCategoryId()))) &&
            ((this.categoryName==null && other.getCategoryName()==null) || 
             (this.categoryName!=null &&
              this.categoryName.equals(other.getCategoryName()))) &&
            ((this.categoryIdPath==null && other.getCategoryIdPath()==null) || 
             (this.categoryIdPath!=null &&
              this.categoryIdPath.equals(other.getCategoryIdPath()))) &&
            this.isBucketWeb == other.isIsBucketWeb() &&
            this.usedInAutocat == other.isUsedInAutocat() &&
            ((this.categoryBucketID==null && other.getCategoryBucketID()==null) || 
             (this.categoryBucketID!=null &&
              this.categoryBucketID.equals(other.getCategoryBucketID())));
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
        if (getWebID() != null) {
            _hashCode += getWebID().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getAuthor() != null) {
            _hashCode += getAuthor().hashCode();
        }
        if (getLanguage() != null) {
            _hashCode += getLanguage().hashCode();
        }
        if (getLastModified() != null) {
            _hashCode += getLastModified().hashCode();
        }
        if (getLastModifiedForceRecrawl() != null) {
            _hashCode += getLastModifiedForceRecrawl().hashCode();
        }
        if (getNoIndex() != null) {
            _hashCode += getNoIndex().hashCode();
        }
        _hashCode += (isValidSecurityInfo() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isInheritedSecurity() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isAllowAnonymousAccess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isAnonymousViewListItems() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getPermissions() != null) {
            _hashCode += getPermissions().hashCode();
        }
        _hashCode += (isExternalSecurity() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getCategoryId() != null) {
            _hashCode += getCategoryId().hashCode();
        }
        if (getCategoryName() != null) {
            _hashCode += getCategoryName().hashCode();
        }
        if (getCategoryIdPath() != null) {
            _hashCode += getCategoryIdPath().hashCode();
        }
        _hashCode += (isIsBucketWeb() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isUsedInAutocat() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getCategoryBucketID() != null) {
            _hashCode += getCategoryBucketID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(_sWebMetadata.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sWebMetadata"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("webID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "WebID"));
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
        elemField.setFieldName("author");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Author"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("language");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Language"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"));
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
        elemField.setFieldName("noIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "NoIndex"));
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
        elemField.setFieldName("permissions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "Permissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalSecurity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ExternalSecurity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoryId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CategoryId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoryName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CategoryName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoryIdPath");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CategoryIdPath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isBucketWeb");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "IsBucketWeb"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usedInAutocat");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "UsedInAutocat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoryBucketID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CategoryBucketID"));
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
