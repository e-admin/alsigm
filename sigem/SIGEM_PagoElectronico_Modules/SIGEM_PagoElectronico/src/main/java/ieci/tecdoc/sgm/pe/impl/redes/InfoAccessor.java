/**
 * InfoAccessor.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.pe.impl.redes;

import ieci.tecdoc.sgm.pe.impl.redes.InfoAccessor;

public class InfoAccessor  implements java.io.Serializable {
    private java.lang.String[] columsInfoOrder;

    private int[] typesInfoOrder;

    private java.lang.String usuarioPeticion;

    public InfoAccessor() {
    }

    public InfoAccessor(
           java.lang.String[] columsInfoOrder,
           int[] typesInfoOrder,
           java.lang.String usuarioPeticion) {
           this.columsInfoOrder = columsInfoOrder;
           this.typesInfoOrder = typesInfoOrder;
           this.usuarioPeticion = usuarioPeticion;
    }


    /**
     * Gets the columsInfoOrder value for this InfoAccessor.
     * 
     * @return columsInfoOrder
     */
    public java.lang.String[] getColumsInfoOrder() {
        return columsInfoOrder;
    }


    /**
     * Sets the columsInfoOrder value for this InfoAccessor.
     * 
     * @param columsInfoOrder
     */
    public void setColumsInfoOrder(java.lang.String[] columsInfoOrder) {
        this.columsInfoOrder = columsInfoOrder;
    }


    /**
     * Gets the typesInfoOrder value for this InfoAccessor.
     * 
     * @return typesInfoOrder
     */
    public int[] getTypesInfoOrder() {
        return typesInfoOrder;
    }


    /**
     * Sets the typesInfoOrder value for this InfoAccessor.
     * 
     * @param typesInfoOrder
     */
    public void setTypesInfoOrder(int[] typesInfoOrder) {
        this.typesInfoOrder = typesInfoOrder;
    }


    /**
     * Gets the usuarioPeticion value for this InfoAccessor.
     * 
     * @return usuarioPeticion
     */
    public java.lang.String getUsuarioPeticion() {
        return usuarioPeticion;
    }


    /**
     * Sets the usuarioPeticion value for this InfoAccessor.
     * 
     * @param usuarioPeticion
     */
    public void setUsuarioPeticion(java.lang.String usuarioPeticion) {
        this.usuarioPeticion = usuarioPeticion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InfoAccessor)) return false;
        InfoAccessor other = (InfoAccessor) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.columsInfoOrder==null && other.getColumsInfoOrder()==null) || 
             (this.columsInfoOrder!=null &&
              java.util.Arrays.equals(this.columsInfoOrder, other.getColumsInfoOrder()))) &&
            ((this.typesInfoOrder==null && other.getTypesInfoOrder()==null) || 
             (this.typesInfoOrder!=null &&
              java.util.Arrays.equals(this.typesInfoOrder, other.getTypesInfoOrder()))) &&
            ((this.usuarioPeticion==null && other.getUsuarioPeticion()==null) || 
             (this.usuarioPeticion!=null &&
              this.usuarioPeticion.equals(other.getUsuarioPeticion())));
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
        if (getColumsInfoOrder() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getColumsInfoOrder());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getColumsInfoOrder(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTypesInfoOrder() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTypesInfoOrder());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTypesInfoOrder(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUsuarioPeticion() != null) {
            _hashCode += getUsuarioPeticion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InfoAccessor.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:ServicioOrganismoWS", "InfoAccessor"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("columsInfoOrder");
        elemField.setXmlName(new javax.xml.namespace.QName("", "columsInfoOrder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("typesInfoOrder");
        elemField.setXmlName(new javax.xml.namespace.QName("", "typesInfoOrder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuarioPeticion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "usuarioPeticion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
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
