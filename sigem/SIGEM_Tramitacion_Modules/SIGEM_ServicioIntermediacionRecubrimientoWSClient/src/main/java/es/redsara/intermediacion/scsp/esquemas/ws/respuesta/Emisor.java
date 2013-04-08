/**
 * Emisor.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.respuesta;

public class Emisor  implements java.io.Serializable {
    private java.lang.String nifEmisor;

    private java.lang.String nombreEmisor;

    public Emisor() {
    }

    public Emisor(
           java.lang.String nifEmisor,
           java.lang.String nombreEmisor) {
           this.nifEmisor = nifEmisor;
           this.nombreEmisor = nombreEmisor;
    }


    /**
     * Gets the nifEmisor value for this Emisor.
     * 
     * @return nifEmisor
     */
    public java.lang.String getNifEmisor() {
        return nifEmisor;
    }


    /**
     * Sets the nifEmisor value for this Emisor.
     * 
     * @param nifEmisor
     */
    public void setNifEmisor(java.lang.String nifEmisor) {
        this.nifEmisor = nifEmisor;
    }


    /**
     * Gets the nombreEmisor value for this Emisor.
     * 
     * @return nombreEmisor
     */
    public java.lang.String getNombreEmisor() {
        return nombreEmisor;
    }


    /**
     * Sets the nombreEmisor value for this Emisor.
     * 
     * @param nombreEmisor
     */
    public void setNombreEmisor(java.lang.String nombreEmisor) {
        this.nombreEmisor = nombreEmisor;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Emisor)) return false;
        Emisor other = (Emisor) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nifEmisor==null && other.getNifEmisor()==null) || 
             (this.nifEmisor!=null &&
              this.nifEmisor.equals(other.getNifEmisor()))) &&
            ((this.nombreEmisor==null && other.getNombreEmisor()==null) || 
             (this.nombreEmisor!=null &&
              this.nombreEmisor.equals(other.getNombreEmisor())));
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
        if (getNifEmisor() != null) {
            _hashCode += getNifEmisor().hashCode();
        }
        if (getNombreEmisor() != null) {
            _hashCode += getNombreEmisor().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Emisor.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Emisor"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nifEmisor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "NifEmisor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreEmisor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "NombreEmisor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
