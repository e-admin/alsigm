/**
 * Certificacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.certificacion.ws.client;

public class Certificacion  extends ieci.tecdoc.sgm.certificacion.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String idFichero;

    private java.lang.String idUsuario;

    public Certificacion() {
    }

    public Certificacion(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String idFichero,
           java.lang.String idUsuario) {
        super(
            errorCode,
            returnCode);
        this.idFichero = idFichero;
        this.idUsuario = idUsuario;
    }


    /**
     * Gets the idFichero value for this Certificacion.
     * 
     * @return idFichero
     */
    public java.lang.String getIdFichero() {
        return idFichero;
    }


    /**
     * Sets the idFichero value for this Certificacion.
     * 
     * @param idFichero
     */
    public void setIdFichero(java.lang.String idFichero) {
        this.idFichero = idFichero;
    }


    /**
     * Gets the idUsuario value for this Certificacion.
     * 
     * @return idUsuario
     */
    public java.lang.String getIdUsuario() {
        return idUsuario;
    }


    /**
     * Sets the idUsuario value for this Certificacion.
     * 
     * @param idUsuario
     */
    public void setIdUsuario(java.lang.String idUsuario) {
        this.idUsuario = idUsuario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Certificacion)) return false;
        Certificacion other = (Certificacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idFichero==null && other.getIdFichero()==null) || 
             (this.idFichero!=null &&
              this.idFichero.equals(other.getIdFichero()))) &&
            ((this.idUsuario==null && other.getIdUsuario()==null) || 
             (this.idUsuario!=null &&
              this.idUsuario.equals(other.getIdUsuario())));
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
        if (getIdFichero() != null) {
            _hashCode += getIdFichero().hashCode();
        }
        if (getIdUsuario() != null) {
            _hashCode += getIdUsuario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Certificacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.certificacion.sgm.tecdoc.ieci", "Certificacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFichero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.certificacion.sgm.tecdoc.ieci", "idFichero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUsuario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.certificacion.sgm.tecdoc.ieci", "idUsuario"));
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
