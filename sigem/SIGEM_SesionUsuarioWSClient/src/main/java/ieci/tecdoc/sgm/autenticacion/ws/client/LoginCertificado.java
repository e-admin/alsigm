/**
 * LoginCertificado.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.autenticacion.ws.client;

public class LoginCertificado  implements java.io.Serializable {
    private java.lang.String b64Certificate;

    private java.lang.String procedureId;

    public LoginCertificado() {
    }

    public LoginCertificado(
           java.lang.String b64Certificate,
           java.lang.String procedureId) {
           this.b64Certificate = b64Certificate;
           this.procedureId = procedureId;
    }


    /**
     * Gets the b64Certificate value for this LoginCertificado.
     * 
     * @return b64Certificate
     */
    public java.lang.String getB64Certificate() {
        return b64Certificate;
    }


    /**
     * Sets the b64Certificate value for this LoginCertificado.
     * 
     * @param b64Certificate
     */
    public void setB64Certificate(java.lang.String b64Certificate) {
        this.b64Certificate = b64Certificate;
    }


    /**
     * Gets the procedureId value for this LoginCertificado.
     * 
     * @return procedureId
     */
    public java.lang.String getProcedureId() {
        return procedureId;
    }


    /**
     * Sets the procedureId value for this LoginCertificado.
     * 
     * @param procedureId
     */
    public void setProcedureId(java.lang.String procedureId) {
        this.procedureId = procedureId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoginCertificado)) return false;
        LoginCertificado other = (LoginCertificado) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.b64Certificate==null && other.getB64Certificate()==null) || 
             (this.b64Certificate!=null &&
              this.b64Certificate.equals(other.getB64Certificate()))) &&
            ((this.procedureId==null && other.getProcedureId()==null) || 
             (this.procedureId!=null &&
              this.procedureId.equals(other.getProcedureId())));
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
        if (getB64Certificate() != null) {
            _hashCode += getB64Certificate().hashCode();
        }
        if (getProcedureId() != null) {
            _hashCode += getProcedureId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LoginCertificado.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "LoginCertificado"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("b64Certificate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "b64certificate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("procedureId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "procedureId"));
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
