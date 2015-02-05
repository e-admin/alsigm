/**
 * FirmarByEntidad.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.ws.client;

public class FirmarByEntidad  implements java.io.Serializable {
    private ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido poContenido;

    private java.lang.String idEntidad;

    public FirmarByEntidad() {
    }

    public FirmarByEntidad(
           ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido poContenido,
           java.lang.String idEntidad) {
           this.poContenido = poContenido;
           this.idEntidad = idEntidad;
    }


    /**
     * Gets the poContenido value for this FirmarByEntidad.
     * 
     * @return poContenido
     */
    public ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido getPoContenido() {
        return poContenido;
    }


    /**
     * Sets the poContenido value for this FirmarByEntidad.
     * 
     * @param poContenido
     */
    public void setPoContenido(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido poContenido) {
        this.poContenido = poContenido;
    }


    /**
     * Gets the idEntidad value for this FirmarByEntidad.
     * 
     * @return idEntidad
     */
    public java.lang.String getIdEntidad() {
        return idEntidad;
    }


    /**
     * Sets the idEntidad value for this FirmarByEntidad.
     * 
     * @param idEntidad
     */
    public void setIdEntidad(java.lang.String idEntidad) {
        this.idEntidad = idEntidad;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FirmarByEntidad)) return false;
        FirmarByEntidad other = (FirmarByEntidad) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.poContenido==null && other.getPoContenido()==null) || 
             (this.poContenido!=null &&
              this.poContenido.equals(other.getPoContenido()))) &&
            ((this.idEntidad==null && other.getIdEntidad()==null) || 
             (this.idEntidad!=null &&
              this.idEntidad.equals(other.getIdEntidad())));
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
        if (getPoContenido() != null) {
            _hashCode += getPoContenido().hashCode();
        }
        if (getIdEntidad() != null) {
            _hashCode += getIdEntidad().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FirmarByEntidad.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", ">firmarByEntidad"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("poContenido");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "poContenido"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Contenido"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEntidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "idEntidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
