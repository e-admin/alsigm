/**
 * ListaHitos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.publicador.ws.client.dto;

public class ListaHitos  extends ieci.tecdoc.sgm.core.services.dto.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.publicador.ws.client.dto.Hito[] hitos;

    public ListaHitos() {
    }

    public ListaHitos(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.publicador.ws.client.dto.Hito[] hitos) {
    	 this.errorCode=errorCode;
         this.returnCode=returnCode;
        this.hitos = hitos;
    }


    /**
     * Gets the hitos value for this ListaHitos.
     * 
     * @return hitos
     */
    public ieci.tecdoc.sgm.publicador.ws.client.dto.Hito[] getHitos() {
        return hitos;
    }


    /**
     * Sets the hitos value for this ListaHitos.
     * 
     * @param hitos
     */
    public void setHitos(ieci.tecdoc.sgm.publicador.ws.client.dto.Hito[] hitos) {
        this.hitos = hitos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListaHitos)) return false;
        ListaHitos other = (ListaHitos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.hitos==null && other.getHitos()==null) || 
             (this.hitos!=null &&
              java.util.Arrays.equals(this.hitos, other.getHitos())));
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
        if (getHitos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getHitos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getHitos(), i);
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
        new org.apache.axis.description.TypeDesc(ListaHitos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "ListaHitos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hitos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "hitos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "Hito"));
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
