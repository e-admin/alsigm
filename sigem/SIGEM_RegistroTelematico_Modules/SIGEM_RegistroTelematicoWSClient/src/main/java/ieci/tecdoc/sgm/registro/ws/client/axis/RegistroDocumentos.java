/**
 * RegistroDocumentos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registro.ws.client.axis;

public class RegistroDocumentos  extends ieci.tecdoc.sgm.registro.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.registro.ws.client.axis.RegistroDocumento[] registroDocumentos;

    public RegistroDocumentos() {
    }

    public RegistroDocumentos(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.registro.ws.client.axis.RegistroDocumento[] registroDocumentos) {
        super(
            errorCode,
            returnCode);
        this.registroDocumentos = registroDocumentos;
    }


    /**
     * Gets the registroDocumentos value for this RegistroDocumentos.
     * 
     * @return registroDocumentos
     */
    public ieci.tecdoc.sgm.registro.ws.client.axis.RegistroDocumento[] getRegistroDocumentos() {
        return registroDocumentos;
    }


    /**
     * Sets the registroDocumentos value for this RegistroDocumentos.
     * 
     * @param registroDocumentos
     */
    public void setRegistroDocumentos(ieci.tecdoc.sgm.registro.ws.client.axis.RegistroDocumento[] registroDocumentos) {
        this.registroDocumentos = registroDocumentos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegistroDocumentos)) return false;
        RegistroDocumentos other = (RegistroDocumentos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.registroDocumentos==null && other.getRegistroDocumentos()==null) || 
             (this.registroDocumentos!=null &&
              java.util.Arrays.equals(this.registroDocumentos, other.getRegistroDocumentos())));
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
        if (getRegistroDocumentos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRegistroDocumentos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRegistroDocumentos(), i);
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
        new org.apache.axis.description.TypeDesc(RegistroDocumentos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "RegistroDocumentos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registroDocumentos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "registroDocumentos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "RegistroDocumento"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "item"));
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
