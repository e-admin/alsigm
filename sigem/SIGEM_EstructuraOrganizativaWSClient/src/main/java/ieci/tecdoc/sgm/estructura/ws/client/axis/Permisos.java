/**
 * Permisos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public class Permisos  extends ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.estructura.ws.client.axis.Permiso[] permisos;

    public Permisos() {
    }

    public Permisos(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.estructura.ws.client.axis.Permiso[] permisos) {
        super(
            errorCode,
            returnCode);
        this.permisos = permisos;
    }


    /**
     * Gets the permisos value for this Permisos.
     * 
     * @return permisos
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Permiso[] getPermisos() {
        return permisos;
    }


    /**
     * Sets the permisos value for this Permisos.
     * 
     * @param permisos
     */
    public void setPermisos(ieci.tecdoc.sgm.estructura.ws.client.axis.Permiso[] permisos) {
        this.permisos = permisos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Permisos)) return false;
        Permisos other = (Permisos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.permisos==null && other.getPermisos()==null) || 
             (this.permisos!=null &&
              java.util.Arrays.equals(this.permisos, other.getPermisos())));
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
        if (getPermisos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPermisos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPermisos(), i);
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
        new org.apache.axis.description.TypeDesc(Permisos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Permisos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("permisos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "permisos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Permiso"));
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
