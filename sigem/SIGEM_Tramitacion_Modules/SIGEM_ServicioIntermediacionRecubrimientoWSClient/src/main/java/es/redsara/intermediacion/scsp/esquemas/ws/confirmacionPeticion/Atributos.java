/**
 * Atributos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion;

public class Atributos  implements java.io.Serializable {
    private java.lang.String idPeticion;

    private long numElementos;

    private java.lang.String timeStamp;

    private es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.Estado estado;

    private java.lang.String codigoCertificado;

    public Atributos() {
    }

    public Atributos(
           java.lang.String idPeticion,
           long numElementos,
           java.lang.String timeStamp,
           es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.Estado estado,
           java.lang.String codigoCertificado) {
           this.idPeticion = idPeticion;
           this.numElementos = numElementos;
           this.timeStamp = timeStamp;
           this.estado = estado;
           this.codigoCertificado = codigoCertificado;
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


    /**
     * Gets the timeStamp value for this Atributos.
     * 
     * @return timeStamp
     */
    public java.lang.String getTimeStamp() {
        return timeStamp;
    }


    /**
     * Sets the timeStamp value for this Atributos.
     * 
     * @param timeStamp
     */
    public void setTimeStamp(java.lang.String timeStamp) {
        this.timeStamp = timeStamp;
    }


    /**
     * Gets the estado value for this Atributos.
     * 
     * @return estado
     */
    public es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.Estado getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this Atributos.
     * 
     * @param estado
     */
    public void setEstado(es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.Estado estado) {
        this.estado = estado;
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
            this.numElementos == other.getNumElementos() &&
            ((this.timeStamp==null && other.getTimeStamp()==null) || 
             (this.timeStamp!=null &&
              this.timeStamp.equals(other.getTimeStamp()))) &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.codigoCertificado==null && other.getCodigoCertificado()==null) || 
             (this.codigoCertificado!=null &&
              this.codigoCertificado.equals(other.getCodigoCertificado())));
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
        _hashCode += new Long(getNumElementos()).hashCode();
        if (getTimeStamp() != null) {
            _hashCode += getTimeStamp().hashCode();
        }
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getCodigoCertificado() != null) {
            _hashCode += getCodigoCertificado().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Atributos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">Atributos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPeticion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", "IdPeticion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">IdPeticion"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numElementos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", "NumElementos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">NumElementos"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeStamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", "TimeStamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">TimeStamp"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", "Estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">Estado"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoCertificado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", "CodigoCertificado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">CodigoCertificado"));
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
