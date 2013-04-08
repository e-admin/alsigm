/**
 * PeticionPlanoReferenciaCatastral.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.geolocalizacion.ws.client;

public class PeticionPlanoReferenciaCatastral  extends ieci.tecdoc.sgm.geolocalizacion.ws.client.PeticionPlano  implements java.io.Serializable {
    private java.lang.String referenciaCatastral;

    public PeticionPlanoReferenciaCatastral() {
    }

    public PeticionPlanoReferenciaCatastral(
           java.lang.String errorCode,
           java.lang.String returnCode,
           int alto,
           int ancho,
           int codigoINEMunicipio,
           int escala,
           int idMapa,
           java.lang.String referenciaCatastral) {
        super(
            errorCode,
            returnCode,
            alto,
            ancho,
            codigoINEMunicipio,
            escala,
            idMapa);
        this.referenciaCatastral = referenciaCatastral;
    }


    /**
     * Gets the referenciaCatastral value for this PeticionPlanoReferenciaCatastral.
     * 
     * @return referenciaCatastral
     */
    public java.lang.String getReferenciaCatastral() {
        return referenciaCatastral;
    }


    /**
     * Sets the referenciaCatastral value for this PeticionPlanoReferenciaCatastral.
     * 
     * @param referenciaCatastral
     */
    public void setReferenciaCatastral(java.lang.String referenciaCatastral) {
        this.referenciaCatastral = referenciaCatastral;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PeticionPlanoReferenciaCatastral)) return false;
        PeticionPlanoReferenciaCatastral other = (PeticionPlanoReferenciaCatastral) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.referenciaCatastral==null && other.getReferenciaCatastral()==null) || 
             (this.referenciaCatastral!=null &&
              this.referenciaCatastral.equals(other.getReferenciaCatastral())));
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
        if (getReferenciaCatastral() != null) {
            _hashCode += getReferenciaCatastral().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PeticionPlanoReferenciaCatastral.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "PeticionPlanoReferenciaCatastral"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referenciaCatastral");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "referenciaCatastral"));
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
