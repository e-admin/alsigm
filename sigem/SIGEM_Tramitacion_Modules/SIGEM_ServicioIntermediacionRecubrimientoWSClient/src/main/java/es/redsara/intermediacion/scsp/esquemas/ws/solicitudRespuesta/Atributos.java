/**
 * Atributos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.solicitudRespuesta;

public class Atributos  implements java.io.Serializable {
    private java.lang.String idPeticion;

    private java.lang.String codigoCertificado;

    private long numElementos;

    public Atributos() {
    }

    public Atributos(
           java.lang.String idPeticion,
           java.lang.String codigoCertificado,
           long numElementos) {
           this.idPeticion = idPeticion;
           this.codigoCertificado = codigoCertificado;
           this.numElementos = numElementos;
    }


    /**
     * Gets the idPeticion value for this Atributos.
     * 
     * @return idPeticion
     */
    public java.lang.String getIdPeticion() {
        return idPeticion;
    }


    /**
     * Sets the idPeticion value for this Atributos.
     * 
     * @param idPeticion
     */
    public void setIdPeticion(java.lang.String idPeticion) {
        this.idPeticion = idPeticion;
    }


    /**
     * Gets the codigoCertificado value for this Atributos.
     * 
     * @return codigoCertificado
     */
    public java.lang.String getCodigoCertificado() {
        return codigoCertificado;
    }


    /**
     * Sets the codigoCertificado value for this Atributos.
     * 
     * @param codigoCertificado
     */
    public void setCodigoCertificado(java.lang.String codigoCertificado) {
        this.codigoCertificado = codigoCertificado;
    }


    /**
     * Gets the numElementos value for this Atributos.
     * 
     * @return numElementos
     */
    public long getNumElementos() {
        return numElementos;
    }


    /**
     * Sets the numElementos value for this Atributos.
     * 
     * @param numElementos
     */
    public void setNumElementos(long numElementos) {
        this.numElementos = numElementos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Atributos)) return false;
        Atributos other = (Atributos) obj;
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
            ((this.codigoCertificado==null && other.getCodigoCertificado()==null) || 
             (this.codigoCertificado!=null &&
              this.codigoCertificado.equals(other.getCodigoCertificado()))) &&
            this.numElementos == other.getNumElementos();
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
        if (getCodigoCertificado() != null) {
            _hashCode += getCodigoCertificado().hashCode();
        }
        _hashCode += new Long(getNumElementos()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Atributos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/solicitudRespuesta", ">Atributos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPeticion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/solicitudRespuesta", "IdPeticion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/solicitudRespuesta", ">IdPeticion"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoCertificado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/solicitudRespuesta", "CodigoCertificado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/solicitudRespuesta", ">CodigoCertificado"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numElementos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/solicitudRespuesta", "NumElementos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/solicitudRespuesta", ">NumElementos"));
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
