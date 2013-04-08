/**
 * UsuariosLdap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public class UsuariosLdap  extends ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap[] usuariosLdap;

    public UsuariosLdap() {
    }

    public UsuariosLdap(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap[] usuariosLdap) {
        super(
            errorCode,
            returnCode);
        this.usuariosLdap = usuariosLdap;
    }


    /**
     * Gets the usuariosLdap value for this UsuariosLdap.
     * 
     * @return usuariosLdap
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap[] getUsuariosLdap() {
        return usuariosLdap;
    }


    /**
     * Sets the usuariosLdap value for this UsuariosLdap.
     * 
     * @param usuariosLdap
     */
    public void setUsuariosLdap(ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap[] usuariosLdap) {
        this.usuariosLdap = usuariosLdap;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UsuariosLdap)) return false;
        UsuariosLdap other = (UsuariosLdap) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.usuariosLdap==null && other.getUsuariosLdap()==null) || 
             (this.usuariosLdap!=null &&
              java.util.Arrays.equals(this.usuariosLdap, other.getUsuariosLdap())));
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
        if (getUsuariosLdap() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUsuariosLdap());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUsuariosLdap(), i);
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
        new org.apache.axis.description.TypeDesc(UsuariosLdap.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuariosLdap"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuariosLdap");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "usuariosLdap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuarioLdap"));
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
