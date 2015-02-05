/**
 * PerfilesUsuario.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public class PerfilesUsuario  extends ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.estructura.ws.client.axis.PerfilUsuario[] perfilesUsuario;

    public PerfilesUsuario() {
    }

    public PerfilesUsuario(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.estructura.ws.client.axis.PerfilUsuario[] perfilesUsuario) {
        super(
            errorCode,
            returnCode);
        this.perfilesUsuario = perfilesUsuario;
    }


    /**
     * Gets the perfilesUsuario value for this PerfilesUsuario.
     * 
     * @return perfilesUsuario
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.PerfilUsuario[] getPerfilesUsuario() {
        return perfilesUsuario;
    }


    /**
     * Sets the perfilesUsuario value for this PerfilesUsuario.
     * 
     * @param perfilesUsuario
     */
    public void setPerfilesUsuario(ieci.tecdoc.sgm.estructura.ws.client.axis.PerfilUsuario[] perfilesUsuario) {
        this.perfilesUsuario = perfilesUsuario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PerfilesUsuario)) return false;
        PerfilesUsuario other = (PerfilesUsuario) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.perfilesUsuario==null && other.getPerfilesUsuario()==null) || 
             (this.perfilesUsuario!=null &&
              java.util.Arrays.equals(this.perfilesUsuario, other.getPerfilesUsuario())));
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
        if (getPerfilesUsuario() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPerfilesUsuario());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPerfilesUsuario(), i);
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
        new org.apache.axis.description.TypeDesc(PerfilesUsuario.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "PerfilesUsuario"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("perfilesUsuario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "perfilesUsuario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "PerfilUsuario"));
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
