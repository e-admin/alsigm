/**
 * ConectorAutenticacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.catalogo.ws.client;

public class ConectorAutenticacion  extends ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String conectorId;

    private java.lang.String tramiteId;

    public ConectorAutenticacion() {
    }

    public ConectorAutenticacion(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String conectorId,
           java.lang.String tramiteId) {
        super(
            errorCode,
            returnCode);
        this.conectorId = conectorId;
        this.tramiteId = tramiteId;
    }


    /**
     * Gets the conectorId value for this ConectorAutenticacion.
     * 
     * @return conectorId
     */
    public java.lang.String getConectorId() {
        return conectorId;
    }


    /**
     * Sets the conectorId value for this ConectorAutenticacion.
     * 
     * @param conectorId
     */
    public void setConectorId(java.lang.String conectorId) {
        this.conectorId = conectorId;
    }


    /**
     * Gets the tramiteId value for this ConectorAutenticacion.
     * 
     * @return tramiteId
     */
    public java.lang.String getTramiteId() {
        return tramiteId;
    }


    /**
     * Sets the tramiteId value for this ConectorAutenticacion.
     * 
     * @param tramiteId
     */
    public void setTramiteId(java.lang.String tramiteId) {
        this.tramiteId = tramiteId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConectorAutenticacion)) return false;
        ConectorAutenticacion other = (ConectorAutenticacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.conectorId==null && other.getConectorId()==null) || 
             (this.conectorId!=null &&
              this.conectorId.equals(other.getConectorId()))) &&
            ((this.tramiteId==null && other.getTramiteId()==null) || 
             (this.tramiteId!=null &&
              this.tramiteId.equals(other.getTramiteId())));
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
        if (getConectorId() != null) {
            _hashCode += getConectorId().hashCode();
        }
        if (getTramiteId() != null) {
            _hashCode += getTramiteId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConectorAutenticacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "ConectorAutenticacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("conectorId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "conectorId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tramiteId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "tramiteId"));
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
