/**
 * Portales.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.GeoLocalizacionWebService;

public class Portales  extends es.ieci.tecdoc.sigem.GeoLocalizacionWebService.RetornoServicio  implements java.io.Serializable {
    private es.ieci.tecdoc.sigem.GeoLocalizacionWebService.ArrayOfPortal portales;

    public Portales() {
    }

    public Portales(
           java.lang.String errorCode,
           java.lang.String returnCode,
           es.ieci.tecdoc.sigem.GeoLocalizacionWebService.ArrayOfPortal portales) {
        super(
            errorCode,
            returnCode);
        this.portales = portales;
    }


    /**
     * Gets the portales value for this Portales.
     * 
     * @return portales
     */
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.ArrayOfPortal getPortales() {
        return portales;
    }


    /**
     * Sets the portales value for this Portales.
     * 
     * @param portales
     */
    public void setPortales(es.ieci.tecdoc.sigem.GeoLocalizacionWebService.ArrayOfPortal portales) {
        this.portales = portales;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Portales)) return false;
        Portales other = (Portales) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.portales==null && other.getPortales()==null) || 
             (this.portales!=null &&
              this.portales.equals(other.getPortales())));
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
        if (getPortales() != null) {
            _hashCode += getPortales().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Portales.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Portales"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portales");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "portales"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "ArrayOfPortal"));
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
