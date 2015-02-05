/**
 * ListaTasas.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.pe.ws.client;

public class ListaTasas  extends ieci.tecdoc.sgm.pe.ws.client.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.pe.ws.client.Tasa[] tasas;

    public ListaTasas() {
    }

    public ListaTasas(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.pe.ws.client.Tasa[] tasas) {
        super(
            errorCode,
            returnCode);
        this.tasas = tasas;
    }


    /**
     * Gets the tasas value for this ListaTasas.
     * 
     * @return tasas
     */
    public ieci.tecdoc.sgm.pe.ws.client.Tasa[] getTasas() {
        return tasas;
    }


    /**
     * Sets the tasas value for this ListaTasas.
     * 
     * @param tasas
     */
    public void setTasas(ieci.tecdoc.sgm.pe.ws.client.Tasa[] tasas) {
        this.tasas = tasas;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListaTasas)) return false;
        ListaTasas other = (ListaTasas) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.tasas==null && other.getTasas()==null) || 
             (this.tasas!=null &&
              java.util.Arrays.equals(this.tasas, other.getTasas())));
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
        if (getTasas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTasas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTasas(), i);
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
        new org.apache.axis.description.TypeDesc(ListaTasas.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "ListaTasas"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tasas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "tasas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Tasa"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "item"));
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
