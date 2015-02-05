/**
 * Provincia.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.GeoLocalizacionWebService;

public class Provincia  extends es.ieci.tecdoc.sigem.GeoLocalizacionWebService.RetornoServicio  implements java.io.Serializable {
    private int codigoINE;

    private java.lang.String comunidadAutonoma;

    private java.lang.String nombreCoOficial;

    private java.lang.String nombreOficial;

    public Provincia() {
    }

    public Provincia(
           java.lang.String errorCode,
           java.lang.String returnCode,
           int codigoINE,
           java.lang.String comunidadAutonoma,
           java.lang.String nombreCoOficial,
           java.lang.String nombreOficial) {
        super(
            errorCode,
            returnCode);
        this.codigoINE = codigoINE;
        this.comunidadAutonoma = comunidadAutonoma;
        this.nombreCoOficial = nombreCoOficial;
        this.nombreOficial = nombreOficial;
    }


    /**
     * Gets the codigoINE value for this Provincia.
     * 
     * @return codigoINE
     */
    public int getCodigoINE() {
        return codigoINE;
    }


    /**
     * Sets the codigoINE value for this Provincia.
     * 
     * @param codigoINE
     */
    public void setCodigoINE(int codigoINE) {
        this.codigoINE = codigoINE;
    }


    /**
     * Gets the comunidadAutonoma value for this Provincia.
     * 
     * @return comunidadAutonoma
     */
    public java.lang.String getComunidadAutonoma() {
        return comunidadAutonoma;
    }


    /**
     * Sets the comunidadAutonoma value for this Provincia.
     * 
     * @param comunidadAutonoma
     */
    public void setComunidadAutonoma(java.lang.String comunidadAutonoma) {
        this.comunidadAutonoma = comunidadAutonoma;
    }


    /**
     * Gets the nombreCoOficial value for this Provincia.
     * 
     * @return nombreCoOficial
     */
    public java.lang.String getNombreCoOficial() {
        return nombreCoOficial;
    }


    /**
     * Sets the nombreCoOficial value for this Provincia.
     * 
     * @param nombreCoOficial
     */
    public void setNombreCoOficial(java.lang.String nombreCoOficial) {
        this.nombreCoOficial = nombreCoOficial;
    }


    /**
     * Gets the nombreOficial value for this Provincia.
     * 
     * @return nombreOficial
     */
    public java.lang.String getNombreOficial() {
        return nombreOficial;
    }


    /**
     * Sets the nombreOficial value for this Provincia.
     * 
     * @param nombreOficial
     */
    public void setNombreOficial(java.lang.String nombreOficial) {
        this.nombreOficial = nombreOficial;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Provincia)) return false;
        Provincia other = (Provincia) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.codigoINE == other.getCodigoINE() &&
            ((this.comunidadAutonoma==null && other.getComunidadAutonoma()==null) || 
             (this.comunidadAutonoma!=null &&
              this.comunidadAutonoma.equals(other.getComunidadAutonoma()))) &&
            ((this.nombreCoOficial==null && other.getNombreCoOficial()==null) || 
             (this.nombreCoOficial!=null &&
              this.nombreCoOficial.equals(other.getNombreCoOficial()))) &&
            ((this.nombreOficial==null && other.getNombreOficial()==null) || 
             (this.nombreOficial!=null &&
              this.nombreOficial.equals(other.getNombreOficial())));
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
        _hashCode += getCodigoINE();
        if (getComunidadAutonoma() != null) {
            _hashCode += getComunidadAutonoma().hashCode();
        }
        if (getNombreCoOficial() != null) {
            _hashCode += getNombreCoOficial().hashCode();
        }
        if (getNombreOficial() != null) {
            _hashCode += getNombreOficial().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Provincia.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Provincia"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoINE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "codigoINE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comunidadAutonoma");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "comunidadAutonoma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreCoOficial");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "nombreCoOficial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreOficial");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "nombreOficial"));
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
