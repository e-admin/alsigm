/**
 * _sSiteMetadata.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public class _sSiteMetadata  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private java.util.Calendar lastModified;

    private java.util.Calendar lastModifiedForceRecrawl;

    private boolean smallSite;

    private java.lang.String portalUrl;

    private java.lang.String userProfileGUID;

    private boolean validSecurityInfo;

    public _sSiteMetadata() {
    }

    public _sSiteMetadata(
           java.util.Calendar lastModified,
           java.util.Calendar lastModifiedForceRecrawl,
           boolean smallSite,
           java.lang.String portalUrl,
           java.lang.String userProfileGUID,
           boolean validSecurityInfo) {
           this.lastModified = lastModified;
           this.lastModifiedForceRecrawl = lastModifiedForceRecrawl;
           this.smallSite = smallSite;
           this.portalUrl = portalUrl;
           this.userProfileGUID = userProfileGUID;
           this.validSecurityInfo = validSecurityInfo;
    }


    /**
     * Gets the lastModified value for this _sSiteMetadata.
     * 
     * @return lastModified
     */
    public java.util.Calendar getLastModified() {
        return lastModified;
    }


    /**
     * Sets the lastModified value for this _sSiteMetadata.
     * 
     * @param lastModified
     */
    public void setLastModified(java.util.Calendar lastModified) {
        this.lastModified = lastModified;
    }


    /**
     * Gets the lastModifiedForceRecrawl value for this _sSiteMetadata.
     * 
     * @return lastModifiedForceRecrawl
     */
    public java.util.Calendar getLastModifiedForceRecrawl() {
        return lastModifiedForceRecrawl;
    }


    /**
     * Sets the lastModifiedForceRecrawl value for this _sSiteMetadata.
     * 
     * @param lastModifiedForceRecrawl
     */
    public void setLastModifiedForceRecrawl(java.util.Calendar lastModifiedForceRecrawl) {
        this.lastModifiedForceRecrawl = lastModifiedForceRecrawl;
    }


    /**
     * Gets the smallSite value for this _sSiteMetadata.
     * 
     * @return smallSite
     */
    public boolean isSmallSite() {
        return smallSite;
    }


    /**
     * Sets the smallSite value for this _sSiteMetadata.
     * 
     * @param smallSite
     */
    public void setSmallSite(boolean smallSite) {
        this.smallSite = smallSite;
    }


    /**
     * Gets the portalUrl value for this _sSiteMetadata.
     * 
     * @return portalUrl
     */
    public java.lang.String getPortalUrl() {
        return portalUrl;
    }


    /**
     * Sets the portalUrl value for this _sSiteMetadata.
     * 
     * @param portalUrl
     */
    public void setPortalUrl(java.lang.String portalUrl) {
        this.portalUrl = portalUrl;
    }


    /**
     * Gets the userProfileGUID value for this _sSiteMetadata.
     * 
     * @return userProfileGUID
     */
    public java.lang.String getUserProfileGUID() {
        return userProfileGUID;
    }


    /**
     * Sets the userProfileGUID value for this _sSiteMetadata.
     * 
     * @param userProfileGUID
     */
    public void setUserProfileGUID(java.lang.String userProfileGUID) {
        this.userProfileGUID = userProfileGUID;
    }


    /**
     * Gets the validSecurityInfo value for this _sSiteMetadata.
     * 
     * @return validSecurityInfo
     */
    public boolean isValidSecurityInfo() {
        return validSecurityInfo;
    }


    /**
     * Sets the validSecurityInfo value for this _sSiteMetadata.
     * 
     * @param validSecurityInfo
     */
    public void setValidSecurityInfo(boolean validSecurityInfo) {
        this.validSecurityInfo = validSecurityInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof _sSiteMetadata)) return false;
        _sSiteMetadata other = (_sSiteMetadata) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.lastModified==null && other.getLastModified()==null) || 
             (this.lastModified!=null &&
              this.lastModified.equals(other.getLastModified()))) &&
            ((this.lastModifiedForceRecrawl==null && other.getLastModifiedForceRecrawl()==null) || 
             (this.lastModifiedForceRecrawl!=null &&
              this.lastModifiedForceRecrawl.equals(other.getLastModifiedForceRecrawl()))) &&
            this.smallSite == other.isSmallSite() &&
            ((this.portalUrl==null && other.getPortalUrl()==null) || 
             (this.portalUrl!=null &&
              this.portalUrl.equals(other.getPortalUrl()))) &&
            ((this.userProfileGUID==null && other.getUserProfileGUID()==null) || 
             (this.userProfileGUID!=null &&
              this.userProfileGUID.equals(other.getUserProfileGUID()))) &&
            this.validSecurityInfo == other.isValidSecurityInfo();
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
        if (getLastModified() != null) {
            _hashCode += getLastModified().hashCode();
        }
        if (getLastModifiedForceRecrawl() != null) {
            _hashCode += getLastModifiedForceRecrawl().hashCode();
        }
        _hashCode += (isSmallSite() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getPortalUrl() != null) {
            _hashCode += getPortalUrl().hashCode();
        }
        if (getUserProfileGUID() != null) {
            _hashCode += getUserProfileGUID().hashCode();
        }
        _hashCode += (isValidSecurityInfo() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(_sSiteMetadata.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "_sSiteMetadata"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("smallSite");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "SmallSite"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portalUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "PortalUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userProfileGUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "UserProfileGUID"));
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
