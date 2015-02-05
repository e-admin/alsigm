/**
 * UsuariosBasicos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public class UsuariosBasicos  extends ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioBasico[] usuariosBasicos;

    public UsuariosBasicos() {
    }

    public UsuariosBasicos(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioBasico[] usuariosBasicos) {
        super(
            errorCode,
            returnCode);
        this.usuariosBasicos = usuariosBasicos;
    }


    /**
     * Gets the usuariosBasicos value for this UsuariosBasicos.
     * 
     * @return usuariosBasicos
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioBasico[] getUsuariosBasicos() {
        return usuariosBasicos;
    }


    /**
     * Sets the usuariosBasicos value for this UsuariosBasicos.
     * 
     * @param usuariosBasicos
     */
    public void setUsuariosBasicos(ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioBasico[] usuariosBasicos) {
        this.usuariosBasicos = usuariosBasicos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UsuariosBasicos)) return false;
        UsuariosBasicos other = (UsuariosBasicos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.usuariosBasicos==null && other.getUsuariosBasicos()==null) || 
             (this.usuariosBasicos!=null &&
              java.util.Arrays.equals(this.usuariosBasicos, other.getUsuariosBasicos())));
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
        if (getUsuariosBasicos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUsuariosBasicos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUsuariosBasicos(), i);
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
        new org.apache.axis.description.TypeDesc(UsuariosBasicos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuariosBasicos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuariosBasicos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "usuariosBasicos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuarioBasico"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "item"));
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
