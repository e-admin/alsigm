/**
 * Documento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.CatalogoTramitesWebService;

public class Documento  extends es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio  implements java.io.Serializable {
    private java.lang.String description;

    private java.lang.String extension;

    private java.lang.String id;

    private java.lang.String signatureHook;

    private java.lang.String validationHook;

    public Documento() {
    }

    public Documento(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String description,
           java.lang.String extension,
           java.lang.String id,
           java.lang.String signatureHook,
           java.lang.String validationHook) {
        super(
            errorCode,
            returnCode);
        this.description = description;
        this.extension = extension;
        this.id = id;
        this.signatureHook = signatureHook;
        this.validationHook = validationHook;
    }


    /**
     * Gets the description value for this Documento.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Documento.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the extension value for this Documento.
     * 
     * @return extension
     */
    public java.lang.String getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this Documento.
     * 
     * @param extension
     */
    public void setExtension(java.lang.String extension) {
        this.extension = extension;
    }


    /**
     * Gets the id value for this Documento.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this Documento.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the signatureHook value for this Documento.
     * 
     * @return signatureHook
     */
    public java.lang.String getSignatureHook() {
        return signatureHook;
    }


    /**
     * Sets the signatureHook value for this Documento.
     * 
     * @param signatureHook
     */
    public void setSignatureHook(java.lang.String signatureHook) {
        this.signatureHook = signatureHook;
    }


    /**
     * Gets the validationHook value for this Documento.
     * 
     * @return validationHook
     */
    public java.lang.String getValidationHook() {
        return validationHook;
    }


    /**
     * Sets the validationHook value for this Documento.
     * 
     * @param validationHook
     */
    public void setValidationHook(java.lang.String validationHook) {
        this.validationHook = validationHook;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Documento)) return false;
        Documento other = (Documento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.extension==null && other.getExtension()==null) || 
             (this.extension!=null &&
              this.extension.equals(other.getExtension()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.signatureHook==null && other.getSignatureHook()==null) || 
             (this.signatureHook!=null &&
              this.signatureHook.equals(other.getSignatureHook()))) &&
            ((this.validationHook==null && other.getValidationHook()==null) || 
             (this.validationHook!=null &&
              this.validationHook.equals(other.getValidationHook())));
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
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getExtension() != null) {
            _hashCode += getExtension().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getSignatureHook() != null) {
            _hashCode += getSignatureHook().hashCode();
        }
        if (getValidationHook() != null) {
            _hashCode += getValidationHook().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Documento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "Documento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "extension"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("signatureHook");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "signatureHook"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validationHook");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "validationHook"));
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
