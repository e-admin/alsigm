/**
 * Funcionario.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.peticion;

public class Funcionario  implements java.io.Serializable {
    private java.lang.String nombreCompletoFuncionario;

    private java.lang.String nifFuncionario;

    public Funcionario() {
    }

    public Funcionario(
           java.lang.String nombreCompletoFuncionario,
           java.lang.String nifFuncionario) {
           this.nombreCompletoFuncionario = nombreCompletoFuncionario;
           this.nifFuncionario = nifFuncionario;
    }


    /**
     * Gets the nombreCompletoFuncionario value for this Funcionario.
     * 
     * @return nombreCompletoFuncionario
     */
    public java.lang.String getNombreCompletoFuncionario() {
        return nombreCompletoFuncionario;
    }


    /**
     * Sets the nombreCompletoFuncionario value for this Funcionario.
     * 
     * @param nombreCompletoFuncionario
     */
    public void setNombreCompletoFuncionario(java.lang.String nombreCompletoFuncionario) {
        this.nombreCompletoFuncionario = nombreCompletoFuncionario;
    }


    /**
     * Gets the nifFuncionario value for this Funcionario.
     * 
     * @return nifFuncionario
     */
    public java.lang.String getNifFuncionario() {
        return nifFuncionario;
    }


    /**
     * Sets the nifFuncionario value for this Funcionario.
     * 
     * @param nifFuncionario
     */
    public void setNifFuncionario(java.lang.String nifFuncionario) {
        this.nifFuncionario = nifFuncionario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Funcionario)) return false;
        Funcionario other = (Funcionario) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nombreCompletoFuncionario==null && other.getNombreCompletoFuncionario()==null) || 
             (this.nombreCompletoFuncionario!=null &&
              this.nombreCompletoFuncionario.equals(other.getNombreCompletoFuncionario()))) &&
            ((this.nifFuncionario==null && other.getNifFuncionario()==null) || 
             (this.nifFuncionario!=null &&
              this.nifFuncionario.equals(other.getNifFuncionario())));
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
        if (getNombreCompletoFuncionario() != null) {
            _hashCode += getNombreCompletoFuncionario().hashCode();
        }
        if (getNifFuncionario() != null) {
            _hashCode += getNifFuncionario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Funcionario.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">Funcionario"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreCompletoFuncionario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "NombreCompletoFuncionario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nifFuncionario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "NifFuncionario"));
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
