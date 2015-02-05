/**
 * ListaLiquidaciones.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.pe.ws.client;
/*
 *  $Id: ListaLiquidaciones.java,v 1.1.2.2 2008/02/05 13:32:49 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;

public class ListaLiquidaciones  extends ieci.tecdoc.sgm.pe.ws.client.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.pe.ws.client.Liquidacion[] liquidaciones;

    public ListaLiquidaciones() {
    }

    public ListaLiquidaciones(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.pe.ws.client.Liquidacion[] liquidaciones) {
        super(
            errorCode,
            returnCode);
        this.liquidaciones = liquidaciones;
    }


    /**
     * Gets the liquidaciones value for this ListaLiquidaciones.
     * 
     * @return liquidaciones
     */
    public ieci.tecdoc.sgm.pe.ws.client.Liquidacion[] getLiquidaciones() {
        return liquidaciones;
    }


    /**
     * Sets the liquidaciones value for this ListaLiquidaciones.
     * 
     * @param liquidaciones
     */
    public void setLiquidaciones(ieci.tecdoc.sgm.pe.ws.client.Liquidacion[] liquidaciones) {
        this.liquidaciones = liquidaciones;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListaLiquidaciones)) return false;
        ListaLiquidaciones other = (ListaLiquidaciones) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.liquidaciones==null && other.getLiquidaciones()==null) || 
             (this.liquidaciones!=null &&
              java.util.Arrays.equals(this.liquidaciones, other.getLiquidaciones())));
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
        if (getLiquidaciones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLiquidaciones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLiquidaciones(), i);
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
        new org.apache.axis.description.TypeDesc(ListaLiquidaciones.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "ListaLiquidaciones"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liquidaciones");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "liquidaciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.pe.sgm.tecdoc.ieci", "Liquidacion"));
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
