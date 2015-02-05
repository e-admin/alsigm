/**
 * Fault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.fault;

public class Fault  extends org.apache.axis.AxisFault  implements java.io.Serializable {
    private java.lang.String idPeticion;

    private java.lang.String timeStamp;

    private java.lang.String codigoCertificado;

    private java.lang.String codigoEstado;

    private java.lang.String literalError;

    public Fault() {
    }

    public Fault(
           java.lang.String idPeticion,
           java.lang.String timeStamp,
           java.lang.String codigoCertificado,
           java.lang.String codigoEstado,
           java.lang.String literalError) {
        this.idPeticion = idPeticion;
        this.timeStamp = timeStamp;
        this.codigoCertificado = codigoCertificado;
        this.codigoEstado = codigoEstado;
        this.literalError = literalError;
    }


    /**
     * Gets the idPeticion value for this Fault.
     * 
     * @return idPeticion
     */
    public java.lang.String getIdPeticion() {
        return idPeticion;
    }


    /**
     * Sets the idPeticion value for this Fault.
     * 
     * @param idPeticion
     */
    public void setIdPeticion(java.lang.String idPeticion) {
        this.idPeticion = idPeticion;
    }


    /**
     * Gets the timeStamp value for this Fault.
     * 
     * @return timeStamp
     */
    public java.lang.String getTimeStamp() {
        return timeStamp;
    }


    /**
     * Sets the timeStamp value for this Fault.
     * 
     * @param timeStamp
     */
    public void setTimeStamp(java.lang.String timeStamp) {
        this.timeStamp = timeStamp;
    }


    /**
     * Gets the codigoCertificado value for this Fault.
     * 
     * @return codigoCertificado
     */
    public java.lang.String getCodigoCertificado() {
        return codigoCertificado;
    }


    /**
     * Sets the codigoCertificado value for this Fault.
     * 
     * @param codigoCertificado
     */
    public void setCodigoCertificado(java.lang.String codigoCertificado) {
        this.codigoCertificado = codigoCertificado;
    }


    /**
     * Gets the codigoEstado value for this Fault.
     * 
     * @return codigoEstado
     */
    public java.lang.String getCodigoEstado() {
        return codigoEstado;
    }


    /**
     * Sets the codigoEstado value for this Fault.
     * 
     * @param codigoEstado
     */
    public void setCodigoEstado(java.lang.String codigoEstado) {
        this.codigoEstado = codigoEstado;
    }


    /**
     * Gets the literalError value for this Fault.
     * 
     * @return literalError
     */
    public java.lang.String getLiteralError() {
        return literalError;
    }


    /**
     * Sets the literalError value for this Fault.
     * 
     * @param literalError
     */
    public void setLiteralError(java.lang.String literalError) {
        this.literalError = literalError;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Fault)) return false;
        Fault other = (Fault) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idPeticion==null && other.getIdPeticion()==null) || 
             (this.idPeticion!=null &&
              this.idPeticion.equals(other.getIdPeticion()))) &&
            ((this.timeStamp==null && other.getTimeStamp()==null) || 
             (this.timeStamp!=null &&
              this.timeStamp.equals(other.getTimeStamp()))) &&
            ((this.codigoCertificado==null && other.getCodigoCertificado()==null) || 
             (this.codigoCertificado!=null &&
              this.codigoCertificado.equals(other.getCodigoCertificado()))) &&
            ((this.codigoEstado==null && other.getCodigoEstado()==null) || 
             (this.codigoEstado!=null &&
              this.codigoEstado.equals(other.getCodigoEstado()))) &&
            ((this.literalError==null && other.getLiteralError()==null) || 
             (this.literalError!=null &&
              this.literalError.equals(other.getLiteralError())));
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
        if (getIdPeticion() != null) {
            _hashCode += getIdPeticion().hashCode();
        }
        if (getTimeStamp() != null) {
            _hashCode += getTimeStamp().hashCode();
        }
        if (getCodigoCertificado() != null) {
            _hashCode += getCodigoCertificado().hashCode();
        }
        if (getCodigoEstado() != null) {
            _hashCode += getCodigoEstado().hashCode();
        }
        if (getLiteralError() != null) {
            _hashCode += getLiteralError().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Fault.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", ">fault"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPeticion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", "IdPeticion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeStamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", "TimeStamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoCertificado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", "CodigoCertificado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoEstado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", "CodigoEstado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("literalError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/fault", "LiteralError"));
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


    /**
     * Writes the exception data to the faultDetails
     */
    public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
        context.serialize(qname, null, this);
    }
}
