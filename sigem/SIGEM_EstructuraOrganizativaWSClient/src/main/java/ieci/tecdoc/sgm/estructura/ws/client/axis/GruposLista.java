/**
 * GruposLista.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public class GruposLista  extends ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.estructura.ws.client.axis.Grupo[] grupos;

    public GruposLista() {
    }

    public GruposLista(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.estructura.ws.client.axis.Grupo[] grupos) {
        super(
            errorCode,
            returnCode);
        this.grupos = grupos;
    }


    /**
     * Gets the grupos value for this GruposLista.
     * 
     * @return grupos
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Grupo[] getGrupos() {
        return grupos;
    }


    /**
     * Sets the grupos value for this GruposLista.
     * 
     * @param grupos
     */
    public void setGrupos(ieci.tecdoc.sgm.estructura.ws.client.axis.Grupo[] grupos) {
        this.grupos = grupos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GruposLista)) return false;
        GruposLista other = (GruposLista) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.grupos==null && other.getGrupos()==null) || 
             (this.grupos!=null &&
              java.util.Arrays.equals(this.grupos, other.getGrupos())));
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
        if (getGrupos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGrupos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGrupos(), i);
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
        new org.apache.axis.description.TypeDesc(GruposLista.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "GruposLista"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("grupos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "grupos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Grupo"));
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
