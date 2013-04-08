/**
 * ConjuntoCoordenadas.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.geolocalizacion.ws.client;

public class ConjuntoCoordenadas  extends ieci.tecdoc.sgm.geolocalizacion.ws.client.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.geolocalizacion.ws.client.Coordenadas[] conjuntoCoordenadas;

    public ConjuntoCoordenadas() {
    }

    public ConjuntoCoordenadas(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.geolocalizacion.ws.client.Coordenadas[] conjuntoCoordenadas) {
        super(
            errorCode,
            returnCode);
        this.conjuntoCoordenadas = conjuntoCoordenadas;
    }


    /**
     * Gets the conjuntoCoordenadas value for this ConjuntoCoordenadas.
     * 
     * @return conjuntoCoordenadas
     */
    public ieci.tecdoc.sgm.geolocalizacion.ws.client.Coordenadas[] getConjuntoCoordenadas() {
        return conjuntoCoordenadas;
    }


    /**
     * Sets the conjuntoCoordenadas value for this ConjuntoCoordenadas.
     * 
     * @param conjuntoCoordenadas
     */
    public void setConjuntoCoordenadas(ieci.tecdoc.sgm.geolocalizacion.ws.client.Coordenadas[] conjuntoCoordenadas) {
        this.conjuntoCoordenadas = conjuntoCoordenadas;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConjuntoCoordenadas)) return false;
        ConjuntoCoordenadas other = (ConjuntoCoordenadas) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.conjuntoCoordenadas==null && other.getConjuntoCoordenadas()==null) || 
             (this.conjuntoCoordenadas!=null &&
              java.util.Arrays.equals(this.conjuntoCoordenadas, other.getConjuntoCoordenadas())));
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
        if (getConjuntoCoordenadas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConjuntoCoordenadas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConjuntoCoordenadas(), i);
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
        new org.apache.axis.description.TypeDesc(ConjuntoCoordenadas.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "ConjuntoCoordenadas"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("conjuntoCoordenadas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "conjuntoCoordenadas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Coordenadas"));
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
