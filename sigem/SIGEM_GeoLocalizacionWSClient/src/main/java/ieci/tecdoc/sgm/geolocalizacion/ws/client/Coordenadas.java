/**
 * Coordenadas.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.geolocalizacion.ws.client;

public class Coordenadas  extends ieci.tecdoc.sgm.geolocalizacion.ws.client.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.geolocalizacion.ws.client.Coordenada[] coordenadas;

    public Coordenadas() {
    }

    public Coordenadas(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.geolocalizacion.ws.client.Coordenada[] coordenadas) {
        super(
            errorCode,
            returnCode);
        this.coordenadas = coordenadas;
    }


    /**
     * Gets the coordenadas value for this Coordenadas.
     * 
     * @return coordenadas
     */
    public ieci.tecdoc.sgm.geolocalizacion.ws.client.Coordenada[] getCoordenadas() {
        return coordenadas;
    }


    /**
     * Sets the coordenadas value for this Coordenadas.
     * 
     * @param coordenadas
     */
    public void setCoordenadas(ieci.tecdoc.sgm.geolocalizacion.ws.client.Coordenada[] coordenadas) {
        this.coordenadas = coordenadas;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Coordenadas)) return false;
        Coordenadas other = (Coordenadas) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.coordenadas==null && other.getCoordenadas()==null) || 
             (this.coordenadas!=null &&
              java.util.Arrays.equals(this.coordenadas, other.getCoordenadas())));
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
        if (getCoordenadas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCoordenadas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCoordenadas(), i);
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
        new org.apache.axis.description.TypeDesc(Coordenadas.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Coordenadas"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coordenadas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "coordenadas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Coordenada"));
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
