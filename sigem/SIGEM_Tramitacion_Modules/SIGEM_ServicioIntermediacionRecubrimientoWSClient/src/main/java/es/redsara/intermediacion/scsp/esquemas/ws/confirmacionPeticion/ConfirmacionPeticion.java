/**
 * ConfirmacionPeticion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion;

public class ConfirmacionPeticion  implements java.io.Serializable {
    private es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.Atributos atributos;

    public ConfirmacionPeticion() {
    }

    public ConfirmacionPeticion(
           es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.Atributos atributos) {
           this.atributos = atributos;
    }


    /**
     * Gets the atributos value for this ConfirmacionPeticion.
     * 
     * @return atributos
     */
    public es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.Atributos getAtributos() {
        return atributos;
    }


    /**
     * Sets the atributos value for this ConfirmacionPeticion.
     * 
     * @param atributos
     */
    public void setAtributos(es.redsara.intermediacion.scsp.esquemas.ws.confirmacionPeticion.Atributos atributos) {
        this.atributos = atributos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConfirmacionPeticion)) return false;
        ConfirmacionPeticion other = (ConfirmacionPeticion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.atributos==null && other.getAtributos()==null) || 
             (this.atributos!=null &&
              this.atributos.equals(other.getAtributos())));
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
        if (getAtributos() != null) {
            _hashCode += getAtributos().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConfirmacionPeticion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">ConfirmacionPeticion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atributos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", "Atributos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/confirmacionPeticion", ">Atributos"));
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
