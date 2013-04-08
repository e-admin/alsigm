/**
 * ListaReglas.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.publicador.ws.client.dto;



public class ListaReglas  extends ieci.tecdoc.sgm.core.services.dto.RetornoServicio  implements java.io.Serializable {
    private Regla[] reglas;

    public ListaReglas() {
    }

    public ListaReglas(
           java.lang.String errorCode,
           java.lang.String returnCode,
           Regla[] reglas) {
       this.errorCode=errorCode;
       this.returnCode=returnCode;
        
        this.reglas = reglas;
    }


    /**
     * Gets the reglas value for this ListaReglas.
     * 
     * @return reglas
     */
    public Regla[] getReglas() {
        return reglas;
    }


    /**
     * Sets the reglas value for this ListaReglas.
     * 
     * @param reglas
     */
    public void setReglas(Regla[] reglas) {
        this.reglas = reglas;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListaReglas)) return false;
        ListaReglas other = (ListaReglas) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.reglas==null && other.getReglas()==null) || 
             (this.reglas!=null &&
              java.util.Arrays.equals(this.reglas, other.getReglas())));
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
        if (getReglas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getReglas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getReglas(), i);
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
        new org.apache.axis.description.TypeDesc(ListaReglas.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "ListaReglas"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reglas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "reglas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "Regla"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "item"));
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
