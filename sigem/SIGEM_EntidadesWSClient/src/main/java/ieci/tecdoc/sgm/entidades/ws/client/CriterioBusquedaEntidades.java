/**
 * CriterioBusquedaEntidades.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.entidades.ws.client;

public class CriterioBusquedaEntidades  extends ieci.tecdoc.sgm.entidades.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String nombreCorto;

    private java.lang.String nombreLargo;

    public CriterioBusquedaEntidades() {
    }

    public CriterioBusquedaEntidades(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String nombreCorto,
           java.lang.String nombreLargo) {
        super(
            errorCode,
            returnCode);
        this.nombreCorto = nombreCorto;
        this.nombreLargo = nombreLargo;
    }


    /**
     * Gets the nombreCorto value for this CriterioBusquedaEntidades.
     * 
     * @return nombreCorto
     */
    public java.lang.String getNombreCorto() {
        return nombreCorto;
    }


    /**
     * Sets the nombreCorto value for this CriterioBusquedaEntidades.
     * 
     * @param nombreCorto
     */
    public void setNombreCorto(java.lang.String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }


    /**
     * Gets the nombreLargo value for this CriterioBusquedaEntidades.
     * 
     * @return nombreLargo
     */
    public java.lang.String getNombreLargo() {
        return nombreLargo;
    }


    /**
     * Sets the nombreLargo value for this CriterioBusquedaEntidades.
     * 
     * @param nombreLargo
     */
    public void setNombreLargo(java.lang.String nombreLargo) {
        this.nombreLargo = nombreLargo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CriterioBusquedaEntidades)) return false;
        CriterioBusquedaEntidades other = (CriterioBusquedaEntidades) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.nombreCorto==null && other.getNombreCorto()==null) || 
             (this.nombreCorto!=null &&
              this.nombreCorto.equals(other.getNombreCorto()))) &&
            ((this.nombreLargo==null && other.getNombreLargo()==null) || 
             (this.nombreLargo!=null &&
              this.nombreLargo.equals(other.getNombreLargo())));
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
        if (getNombreCorto() != null) {
            _hashCode += getNombreCorto().hashCode();
        }
        if (getNombreLargo() != null) {
            _hashCode += getNombreLargo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CriterioBusquedaEntidades.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "CriterioBusquedaEntidades"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreCorto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "nombreCorto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreLargo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.entidades.sgm.tecdoc.ieci", "nombreLargo"));
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
