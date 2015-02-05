/**
 * Registros.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registro.ws.client.axis;

public class Registros  extends ieci.tecdoc.sgm.registro.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.registro.ws.client.axis.Registro[] registros;

    public Registros() {
    }

    public Registros(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.registro.ws.client.axis.Registro[] registros) {
        super(
            errorCode,
            returnCode);
        this.registros = registros;
    }


    /**
     * Gets the registros value for this Registros.
     * 
     * @return registros
     */
    public ieci.tecdoc.sgm.registro.ws.client.axis.Registro[] getRegistros() {
        return registros;
    }


    /**
     * Sets the registros value for this Registros.
     * 
     * @param registros
     */
    public void setRegistros(ieci.tecdoc.sgm.registro.ws.client.axis.Registro[] registros) {
        this.registros = registros;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Registros)) return false;
        Registros other = (Registros) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.registros==null && other.getRegistros()==null) || 
             (this.registros!=null &&
              java.util.Arrays.equals(this.registros, other.getRegistros())));
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
        if (getRegistros() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRegistros());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRegistros(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Registros.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "Registros"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registros");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "registros"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "Registro"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "item"));
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
