/**
 * InfoUsuario.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.autenticacion.ws.client;

public class InfoUsuario  extends ieci.tecdoc.sgm.autenticacion.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String CIF;

    private java.lang.String certificateIssuer;

    private java.lang.String certificateSN;

    private java.lang.String email;

    private java.lang.String id;

    private java.lang.String inQuality;

    private java.lang.String name;

    private java.lang.String socialName;

    private java.lang.String firstName;

    private java.lang.String surName;

    private java.lang.String surName2;

    public InfoUsuario() {
    }

    public InfoUsuario(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String CIF,
           java.lang.String certificateIssuer,
           java.lang.String certificateSN,
           java.lang.String email,
           java.lang.String id,
           java.lang.String inQuality,
           java.lang.String name,
           java.lang.String socialName,
           java.lang.String firstName,
           java.lang.String surName,
           java.lang.String surName2) {
        super(
            errorCode,
            returnCode);
        this.CIF = CIF;
        this.certificateIssuer = certificateIssuer;
        this.certificateSN = certificateSN;
        this.email = email;
        this.id = id;
        this.inQuality = inQuality;
        this.name = name;
        this.socialName = socialName;
        this.firstName = firstName;
        this.surName = surName;
        this.surName2 = surName2;
    }


    /**
     * Gets the CIF value for this InfoUsuario.
     *
     * @return CIF
     */
    public java.lang.String getCIF() {
        return CIF;
    }


    /**
     * Sets the CIF value for this InfoUsuario.
     *
     * @param CIF
     */
    public void setCIF(java.lang.String CIF) {
        this.CIF = CIF;
    }


    /**
     * Gets the certificateIssuer value for this InfoUsuario.
     *
     * @return certificateIssuer
     */
    public java.lang.String getCertificateIssuer() {
        return certificateIssuer;
    }


    /**
     * Sets the certificateIssuer value for this InfoUsuario.
     *
     * @param certificateIssuer
     */
    public void setCertificateIssuer(java.lang.String certificateIssuer) {
        this.certificateIssuer = certificateIssuer;
    }


    /**
     * Gets the certificateSN value for this InfoUsuario.
     *
     * @return certificateSN
     */
    public java.lang.String getCertificateSN() {
        return certificateSN;
    }


    /**
     * Sets the certificateSN value for this InfoUsuario.
     *
     * @param certificateSN
     */
    public void setCertificateSN(java.lang.String certificateSN) {
        this.certificateSN = certificateSN;
    }


    /**
     * Gets the email value for this InfoUsuario.
     *
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this InfoUsuario.
     *
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }


    /**
     * Gets the id value for this InfoUsuario.
     *
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this InfoUsuario.
     *
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the inQuality value for this InfoUsuario.
     *
     * @return inQuality
     */
    public java.lang.String getInQuality() {
        return inQuality;
    }


    /**
     * Sets the inQuality value for this InfoUsuario.
     *
     * @param inQuality
     */
    public void setInQuality(java.lang.String inQuality) {
        this.inQuality = inQuality;
    }


    /**
     * Gets the name value for this InfoUsuario.
     *
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this InfoUsuario.
     *
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the socialName value for this InfoUsuario.
     *
     * @return socialName
     */
    public java.lang.String getSocialName() {
        return socialName;
    }


    /**
     * Sets the socialName value for this InfoUsuario.
     *
     * @param socialName
     */
    public void setSocialName(java.lang.String socialName) {
        this.socialName = socialName;
    }


    /**
     * Gets the firstName value for this InfoUsuario.
     *
     * @return firstName
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this InfoUsuario.
     *
     * @param firstName
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the surName value for this InfoUsuario.
     *
     * @return surName
     */
    public java.lang.String getSurName() {
        return surName;
    }


    /**
     * Sets the surName value for this InfoUsuario.
     *
     * @param surName
     */
    public void setSurName(java.lang.String surName) {
        this.surName = surName;
    }


    /**
     * Gets the surName2 value for this InfoUsuario.
     *
     * @return surName2
     */
    public java.lang.String getSurName2() {
        return surName2;
    }


    /**
     * Sets the surName2 value for this InfoUsuario.
     *
     * @param surName2
     */
    public void setSurName2(java.lang.String surName2) {
        this.surName2 = surName2;
    }


    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InfoUsuario)) return false;
        InfoUsuario other = (InfoUsuario) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) &&
            ((this.CIF==null && other.getCIF()==null) ||
             (this.CIF!=null &&
              this.CIF.equals(other.getCIF()))) &&
            ((this.certificateIssuer==null && other.getCertificateIssuer()==null) ||
             (this.certificateIssuer!=null &&
              this.certificateIssuer.equals(other.getCertificateIssuer()))) &&
            ((this.certificateSN==null && other.getCertificateSN()==null) ||
             (this.certificateSN!=null &&
              this.certificateSN.equals(other.getCertificateSN()))) &&
            ((this.email==null && other.getEmail()==null) ||
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.id==null && other.getId()==null) ||
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.inQuality==null && other.getInQuality()==null) ||
             (this.inQuality!=null &&
              this.inQuality.equals(other.getInQuality()))) &&
            ((this.name==null && other.getName()==null) ||
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.socialName==null && other.getSocialName()==null) ||
             (this.socialName!=null &&
              this.socialName.equals(other.getSocialName()))) &&
            ((this.firstName==null && other.getFirstName()==null) ||
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            ((this.surName==null && other.getSurName()==null) ||
             (this.surName!=null &&
              this.surName.equals(other.getSurName()))) &&
            ((this.surName2==null && other.getSurName2()==null) ||
             (this.surName2!=null &&
              this.surName2.equals(other.getSurName2())));

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
        if (getCIF() != null) {
            _hashCode += getCIF().hashCode();
        }
        if (getCertificateIssuer() != null) {
            _hashCode += getCertificateIssuer().hashCode();
        }
        if (getCertificateSN() != null) {
            _hashCode += getCertificateSN().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getInQuality() != null) {
            _hashCode += getInQuality().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getSocialName() != null) {
            _hashCode += getSocialName().hashCode();
        }
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        if (getSurName() != null) {
            _hashCode += getSurName().hashCode();
        }
        if (getSurName2() != null) {
            _hashCode += getSurName2().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InfoUsuario.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "InfoUsuario"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CIF");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "CIF"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certificateIssuer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "certificateIssuer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certificateSN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "certificateSN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inQuality");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "inQuality"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("socialName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "socialName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "firstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("surName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "surName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("surName2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "surName2"));
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
