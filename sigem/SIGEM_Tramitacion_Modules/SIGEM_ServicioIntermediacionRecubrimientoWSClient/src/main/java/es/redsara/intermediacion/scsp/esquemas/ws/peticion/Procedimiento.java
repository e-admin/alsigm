/**
 * Procedimiento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.peticion;

public class Procedimiento  implements java.io.Serializable {
    private java.lang.String codProcedimiento;

    private java.lang.String nombreProcedimiento;

    public Procedimiento() {
    }

    public Procedimiento(
           java.lang.String codProcedimiento,
           java.lang.String nombreProcedimiento) {
           this.codProcedimiento = codProcedimiento;
           this.nombreProcedimiento = nombreProcedimiento;
    }


    /**
     * Gets the codProcedimiento value for this Procedimiento.
     * 
     * @return codProcedimiento
     */
    public java.lang.String getCodProcedimiento() {
        return codProcedimiento;
    }


    /**
     * Sets the codProcedimiento value for this Procedimiento.
     * 
     * @param codProcedimiento
     */
    public void setCodProcedimiento(java.lang.String codProcedimiento) {
        this.codProcedimiento = codProcedimiento;
    }


    /**
     * Gets the nombreProcedimiento value for this Procedimiento.
     * 
     * @return nombreProcedimiento
     */
    public java.lang.String getNombreProcedimiento() {
        return nombreProcedimiento;
    }


    /**
     * Sets the nombreProcedimiento value for this Procedimiento.
     * 
     * @param nombreProcedimiento
     */
    public void setNombreProcedimiento(java.lang.String nombreProcedimiento) {
        this.nombreProcedimiento = nombreProcedimiento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Procedimiento)) return false;
        Procedimiento other = (Procedimiento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codProcedimiento==null && other.getCodProcedimiento()==null) || 
             (this.codProcedimiento!=null &&
              this.codProcedimiento.equals(other.getCodProcedimiento()))) &&
            ((this.nombreProcedimiento==null && other.getNombreProcedimiento()==null) || 
             (this.nombreProcedimiento!=null &&
              this.nombreProcedimiento.equals(other.getNombreProcedimiento())));
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
        if (getCodProcedimiento() != null) {
            _hashCode += getCodProcedimiento().hashCode();
        }
        if (getNombreProcedimiento() != null) {
            _hashCode += getNombreProcedimiento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Procedimiento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">Procedimiento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codProcedimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "CodProcedimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreProcedimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "NombreProcedimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
