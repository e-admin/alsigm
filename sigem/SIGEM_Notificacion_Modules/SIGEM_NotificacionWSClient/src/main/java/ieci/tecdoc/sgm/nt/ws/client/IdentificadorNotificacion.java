/**
 * IdentificadorNotificacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.nt.ws.client;

public class IdentificadorNotificacion  extends ieci.tecdoc.sgm.nt.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String codigoDeNotificacion;

    public IdentificadorNotificacion() {
    }

    public IdentificadorNotificacion(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String codigoDeNotificacion) {
        super(
            errorCode,
            returnCode);
        this.codigoDeNotificacion = codigoDeNotificacion;
    }


    /**
     * Gets the codigoDeNotificacion value for this IdentificadorNotificacion.
     * 
     * @return codigoDeNotificacion
     */
    public java.lang.String getCodigoDeNotificacion() {
        return codigoDeNotificacion;
    }


    /**
     * Sets the codigoDeNotificacion value for this IdentificadorNotificacion.
     * 
     * @param codigoDeNotificacion
     */
    public void setCodigoDeNotificacion(java.lang.String codigoDeNotificacion) {
        this.codigoDeNotificacion = codigoDeNotificacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IdentificadorNotificacion)) return false;
        IdentificadorNotificacion other = (IdentificadorNotificacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codigoDeNotificacion==null && other.getCodigoDeNotificacion()==null) || 
             (this.codigoDeNotificacion!=null &&
              this.codigoDeNotificacion.equals(other.getCodigoDeNotificacion())));
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
        if (getCodigoDeNotificacion() != null) {
            _hashCode += getCodigoDeNotificacion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IdentificadorNotificacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "IdentificadorNotificacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoDeNotificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "codigoDeNotificacion"));
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
