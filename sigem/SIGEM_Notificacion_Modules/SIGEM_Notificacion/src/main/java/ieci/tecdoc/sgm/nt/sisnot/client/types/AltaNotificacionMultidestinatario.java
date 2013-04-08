/**
 * AltaNotificacionMultidestinatario.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.nt.sisnot.client.types;

public class AltaNotificacionMultidestinatario  extends InfoAltaNotificacion  implements java.io.Serializable {
    private java.lang.String[] idUsuarios;

    public AltaNotificacionMultidestinatario() {
    }

    public AltaNotificacionMultidestinatario(
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
           java.lang.String[] idUsuarios) {
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
        this.idUsuarios = idUsuarios;
    }


    /**
     * Gets the idUsuarios value for this AltaNotificacionMultidestinatario.
     * 
     * @return idUsuarios
     */
    public java.lang.String[] getIdUsuarios() {
        return idUsuarios;
    }


    /**
     * Sets the idUsuarios value for this AltaNotificacionMultidestinatario.
     * 
     * @param idUsuarios
     */
    public void setIdUsuarios(java.lang.String[] idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public java.lang.String getIdUsuarios(int i) {
        return this.idUsuarios[i];
    }

    public void setIdUsuarios(int i, java.lang.String _value) {
        this.idUsuarios[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AltaNotificacionMultidestinatario)) return false;
        AltaNotificacionMultidestinatario other = (AltaNotificacionMultidestinatario) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idUsuarios==null && other.getIdUsuarios()==null) || 
             (this.idUsuarios!=null &&
              java.util.Arrays.equals(this.idUsuarios, other.getIdUsuarios())));
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
        if (getIdUsuarios() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIdUsuarios());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIdUsuarios(), i);
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
        new org.apache.axis.description.TypeDesc(AltaNotificacionMultidestinatario.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "AltaNotificacionMultidestinatario"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUsuarios");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idUsuarios"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
