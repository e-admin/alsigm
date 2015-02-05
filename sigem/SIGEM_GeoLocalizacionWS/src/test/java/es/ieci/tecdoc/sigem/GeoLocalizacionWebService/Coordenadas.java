/**
 * Coordenadas.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.GeoLocalizacionWebService;

public class Coordenadas  extends es.ieci.tecdoc.sigem.GeoLocalizacionWebService.RetornoServicio  implements java.io.Serializable {
    private es.ieci.tecdoc.sigem.GeoLocalizacionWebService.ArrayOfCoordenada coordenadas;

    public Coordenadas() {
    }

    public Coordenadas(
           java.lang.String errorCode,
           java.lang.String returnCode,
           es.ieci.tecdoc.sigem.GeoLocalizacionWebService.ArrayOfCoordenada coordenadas) {
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
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.ArrayOfCoordenada getCoordenadas() {
        return coordenadas;
    }


    /**
     * Sets the coordenadas value for this Coordenadas.
     * 
     * @param coordenadas
     */
    public void setCoordenadas(es.ieci.tecdoc.sigem.GeoLocalizacionWebService.ArrayOfCoordenada coordenadas) {
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
              this.coordenadas.equals(other.getCoordenadas())));
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
            _hashCode += getCoordenadas().hashCode();
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "ArrayOfCoordenada"));
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
