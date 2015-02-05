/**
 * NotificacionesPendientes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.ct.ws.client;

public class NotificacionesPendientes  extends ieci.tecdoc.sgm.ct.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String notificacionesPendientes;

    public NotificacionesPendientes() {
    }

    public NotificacionesPendientes(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String notificacionesPendientes) {
        super(
            errorCode,
            returnCode);
        this.notificacionesPendientes = notificacionesPendientes;
    }


    /**
     * Gets the notificacionesPendientes value for this NotificacionesPendientes.
     * 
     * @return notificacionesPendientes
     */
    public java.lang.String getNotificacionesPendientes() {
        return notificacionesPendientes;
    }


    /**
     * Sets the notificacionesPendientes value for this NotificacionesPendientes.
     * 
     * @param notificacionesPendientes
     */
    public void setNotificacionesPendientes(java.lang.String notificacionesPendientes) {
        this.notificacionesPendientes = notificacionesPendientes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NotificacionesPendientes)) return false;
        NotificacionesPendientes other = (NotificacionesPendientes) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.notificacionesPendientes==null && other.getNotificacionesPendientes()==null) || 
             (this.notificacionesPendientes!=null &&
              this.notificacionesPendientes.equals(other.getNotificacionesPendientes())));
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
        if (getNotificacionesPendientes() != null) {
            _hashCode += getNotificacionesPendientes().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NotificacionesPendientes.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "NotificacionesPendientes"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notificacionesPendientes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "notificacionesPendientes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
