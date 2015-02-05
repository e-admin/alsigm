/**
 * ResultadoValidacionCertificado.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.validacion.ws.client;

public class ResultadoValidacionCertificado  extends ieci.tecdoc.sgm.cripto.validacion.ws.client.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.cripto.validacion.ws.client.InfoCertificado certificado;

    private java.lang.String mensajeValidacion;

    private java.lang.String resultadoValidacion;

    public ResultadoValidacionCertificado() {
    }

    public ResultadoValidacionCertificado(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.cripto.validacion.ws.client.InfoCertificado certificado,
           java.lang.String mensajeValidacion,
           java.lang.String resultadoValidacion) {
        super(
            errorCode,
            returnCode);
        this.certificado = certificado;
        this.mensajeValidacion = mensajeValidacion;
        this.resultadoValidacion = resultadoValidacion;
    }


    /**
     * Gets the certificado value for this ResultadoValidacionCertificado.
     * 
     * @return certificado
     */
    public ieci.tecdoc.sgm.cripto.validacion.ws.client.InfoCertificado getCertificado() {
        return certificado;
    }


    /**
     * Sets the certificado value for this ResultadoValidacionCertificado.
     * 
     * @param certificado
     */
    public void setCertificado(ieci.tecdoc.sgm.cripto.validacion.ws.client.InfoCertificado certificado) {
        this.certificado = certificado;
    }


    /**
     * Gets the mensajeValidacion value for this ResultadoValidacionCertificado.
     * 
     * @return mensajeValidacion
     */
    public java.lang.String getMensajeValidacion() {
        return mensajeValidacion;
    }


    /**
     * Sets the mensajeValidacion value for this ResultadoValidacionCertificado.
     * 
     * @param mensajeValidacion
     */
    public void setMensajeValidacion(java.lang.String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }


    /**
     * Gets the resultadoValidacion value for this ResultadoValidacionCertificado.
     * 
     * @return resultadoValidacion
     */
    public java.lang.String getResultadoValidacion() {
        return resultadoValidacion;
    }


    /**
     * Sets the resultadoValidacion value for this ResultadoValidacionCertificado.
     * 
     * @param resultadoValidacion
     */
    public void setResultadoValidacion(java.lang.String resultadoValidacion) {
        this.resultadoValidacion = resultadoValidacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResultadoValidacionCertificado)) return false;
        ResultadoValidacionCertificado other = (ResultadoValidacionCertificado) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.certificado==null && other.getCertificado()==null) || 
             (this.certificado!=null &&
              this.certificado.equals(other.getCertificado()))) &&
            ((this.mensajeValidacion==null && other.getMensajeValidacion()==null) || 
             (this.mensajeValidacion!=null &&
              this.mensajeValidacion.equals(other.getMensajeValidacion()))) &&
            ((this.resultadoValidacion==null && other.getResultadoValidacion()==null) || 
             (this.resultadoValidacion!=null &&
              this.resultadoValidacion.equals(other.getResultadoValidacion())));
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
        if (getCertificado() != null) {
            _hashCode += getCertificado().hashCode();
        }
        if (getMensajeValidacion() != null) {
            _hashCode += getMensajeValidacion().hashCode();
        }
        if (getResultadoValidacion() != null) {
            _hashCode += getResultadoValidacion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResultadoValidacionCertificado.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "ResultadoValidacionCertificado"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certificado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "certificado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "InfoCertificado"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensajeValidacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "mensajeValidacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultadoValidacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "resultadoValidacion"));
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
