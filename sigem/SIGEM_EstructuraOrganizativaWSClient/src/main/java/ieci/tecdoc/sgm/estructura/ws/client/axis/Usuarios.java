/**
 * Usuarios.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public class Usuarios  extends ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.estructura.ws.client.axis.Usuario[] usuarios;

    public Usuarios() {
    }

    public Usuarios(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.estructura.ws.client.axis.Usuario[] usuarios) {
        super(
            errorCode,
            returnCode);
        this.usuarios = usuarios;
    }


    /**
     * Gets the usuarios value for this Usuarios.
     * 
     * @return usuarios
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuario[] getUsuarios() {
        return usuarios;
    }


    /**
     * Sets the usuarios value for this Usuarios.
     * 
     * @param usuarios
     */
    public void setUsuarios(ieci.tecdoc.sgm.estructura.ws.client.axis.Usuario[] usuarios) {
        this.usuarios = usuarios;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Usuarios)) return false;
        Usuarios other = (Usuarios) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.usuarios==null && other.getUsuarios()==null) || 
             (this.usuarios!=null &&
              java.util.Arrays.equals(this.usuarios, other.getUsuarios())));
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
        if (getUsuarios() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUsuarios());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUsuarios(), i);
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
        new org.apache.axis.description.TypeDesc(Usuarios.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuarios"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuarios");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "usuarios"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuario"));
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
