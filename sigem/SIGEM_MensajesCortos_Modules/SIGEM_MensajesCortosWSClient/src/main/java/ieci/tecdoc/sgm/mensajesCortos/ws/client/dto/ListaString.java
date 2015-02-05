/**
 * ListaString.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.mensajesCortos.ws.client.dto;

public class ListaString  extends ieci.tecdoc.sgm.core.services.dto.RetornoServicio  implements java.io.Serializable {
    private java.lang.String[] array;

    public ListaString() {
    }

    public ListaString(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String[] array) {
        this.errorCode=errorCode;
        this.returnCode=returnCode;
        this.array = array;
    }


    /**
     * Gets the array value for this ListaString.
     * 
     * @return array
     */
    public java.lang.String[] getArray() {
        return array;
    }


    /**
     * Sets the array value for this ListaString.
     * 
     * @param array
     */
    public void setArray(java.lang.String[] array) {
        this.array = array;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListaString)) return false;
        ListaString other = (ListaString) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.array==null && other.getArray()==null) || 
             (this.array!=null &&
              java.util.Arrays.equals(this.array, other.getArray())));
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
        if (getArray() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getArray());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getArray(), i);
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
        new org.apache.axis.description.TypeDesc(ListaString.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.server.ws.mensajesCortos.sgm.tecdoc.ieci", "ListaString"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("array");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.server.ws.mensajesCortos.sgm.tecdoc.ieci", "array"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.mensajesCortos.sgm.tecdoc.ieci", "item"));
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
