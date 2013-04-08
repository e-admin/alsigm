/**
 * TiposVia.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.geolocalizacion.ws.client;

public class TiposVia  extends ieci.tecdoc.sgm.geolocalizacion.ws.client.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.geolocalizacion.ws.client.TipoVia[] tiposVia;

    public TiposVia() {
    }

    public TiposVia(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.geolocalizacion.ws.client.TipoVia[] tiposVia) {
        super(
            errorCode,
            returnCode);
        this.tiposVia = tiposVia;
    }


    /**
     * Gets the tiposVia value for this TiposVia.
     * 
     * @return tiposVia
     */
    public ieci.tecdoc.sgm.geolocalizacion.ws.client.TipoVia[] getTiposVia() {
        return tiposVia;
    }


    /**
     * Sets the tiposVia value for this TiposVia.
     * 
     * @param tiposVia
     */
    public void setTiposVia(ieci.tecdoc.sgm.geolocalizacion.ws.client.TipoVia[] tiposVia) {
        this.tiposVia = tiposVia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TiposVia)) return false;
        TiposVia other = (TiposVia) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.tiposVia==null && other.getTiposVia()==null) || 
             (this.tiposVia!=null &&
              java.util.Arrays.equals(this.tiposVia, other.getTiposVia())));
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
        if (getTiposVia() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTiposVia());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTiposVia(), i);
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
        new org.apache.axis.description.TypeDesc(TiposVia.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "TiposVia"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tiposVia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "tiposVia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "TipoVia"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "item"));
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
