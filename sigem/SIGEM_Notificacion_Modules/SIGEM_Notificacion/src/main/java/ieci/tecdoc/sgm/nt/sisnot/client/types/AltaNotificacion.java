/**
 * AltaNotificacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.nt.sisnot.client.types;

public class AltaNotificacion  extends InfoAltaNotificacion  implements java.io.Serializable {
    private java.lang.String idUsuario;

    public AltaNotificacion() {
    }

    public AltaNotificacion(
            java.lang.String asunto,
            java.lang.String codProcedimiento,
            java.lang.String codSistemaEmisor,
            java.lang.String cuerpo,
            java.lang.String fechaRegistro,
            java.lang.String nombreArchivo,
            java.lang.String notificacion64,
            java.lang.String numExpediente,
            java.lang.String numRegistro,
            java.lang.String tipoCorrespondencia,
            java.lang.String idUsuario) {
         super(
             asunto,
             codProcedimiento,
             codSistemaEmisor,
             cuerpo,
             fechaRegistro,
             nombreArchivo,
             notificacion64,
             numExpediente,
             numRegistro,
             tipoCorrespondencia);
         this.idUsuario = idUsuario;
     }

    /**
     * Gets the idUsuario value for this AltaNotificacion.
     * 
     * @return idUsuario
     */
    public java.lang.String getIdUsuario() {
        return idUsuario;
    }


    /**
     * Sets the idUsuario value for this AltaNotificacion.
     * 
     * @param idUsuario
     */
    public void setIdUsuario(java.lang.String idUsuario) {
        this.idUsuario = idUsuario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AltaNotificacion)) return false;
        AltaNotificacion other = (AltaNotificacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
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
        if (getIdUsuario() != null) {
            _hashCode += getIdUsuario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AltaNotificacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "AltaNotificacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUsuario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idUsuario"));
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
