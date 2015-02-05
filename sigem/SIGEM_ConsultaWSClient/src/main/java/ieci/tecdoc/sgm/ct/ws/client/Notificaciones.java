/**
 * Notificaciones.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.ct.ws.client;

public class Notificaciones  extends ieci.tecdoc.sgm.ct.ws.client.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.ct.ws.client.Notificacion[] notificaciones;

    public Notificaciones() {
    }

    public Notificaciones(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.ct.ws.client.Notificacion[] notificaciones) {
        super(
            errorCode,
            returnCode);
        this.notificaciones = notificaciones;
    }


    /**
     * Gets the notificaciones value for this Notificaciones.
     * 
     * @return notificaciones
     */
    public ieci.tecdoc.sgm.ct.ws.client.Notificacion[] getNotificaciones() {
        return notificaciones;
    }


    /**
     * Sets the notificaciones value for this Notificaciones.
     * 
     * @param notificaciones
     */
    public void setNotificaciones(ieci.tecdoc.sgm.ct.ws.client.Notificacion[] notificaciones) {
        this.notificaciones = notificaciones;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Notificaciones)) return false;
        Notificaciones other = (Notificaciones) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.notificaciones==null && other.getNotificaciones()==null) || 
             (this.notificaciones!=null &&
              java.util.Arrays.equals(this.notificaciones, other.getNotificaciones())));
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
        if (getNotificaciones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNotificaciones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNotificaciones(), i);
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
        new org.apache.axis.description.TypeDesc(Notificaciones.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Notificaciones"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notificaciones");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "notificaciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Notificacion"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "item"));
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
