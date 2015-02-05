/**
 * OrganosDestinatarios.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.catalogo.ws.client;

public class OrganosDestinatarios  extends ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.catalogo.ws.client.OrganoDestinatario[] organosDestinatarios;

    public OrganosDestinatarios() {
    }

    public OrganosDestinatarios(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.catalogo.ws.client.OrganoDestinatario[] organosDestinatarios) {
        super(
            errorCode,
            returnCode);
        this.organosDestinatarios = organosDestinatarios;
    }


    /**
     * Gets the organosDestinatarios value for this OrganosDestinatarios.
     * 
     * @return organosDestinatarios
     */
    public ieci.tecdoc.sgm.catalogo.ws.client.OrganoDestinatario[] getOrganosDestinatarios() {
        return organosDestinatarios;
    }


    /**
     * Sets the organosDestinatarios value for this OrganosDestinatarios.
     * 
     * @param organosDestinatarios
     */
    public void setOrganosDestinatarios(ieci.tecdoc.sgm.catalogo.ws.client.OrganoDestinatario[] organosDestinatarios) {
        this.organosDestinatarios = organosDestinatarios;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrganosDestinatarios)) return false;
        OrganosDestinatarios other = (OrganosDestinatarios) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.organosDestinatarios==null && other.getOrganosDestinatarios()==null) || 
             (this.organosDestinatarios!=null &&
              java.util.Arrays.equals(this.organosDestinatarios, other.getOrganosDestinatarios())));
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
        if (getOrganosDestinatarios() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOrganosDestinatarios());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOrganosDestinatarios(), i);
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
        new org.apache.axis.description.TypeDesc(OrganosDestinatarios.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "OrganosDestinatarios"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organosDestinatarios");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "organosDestinatarios"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "OrganoDestinatario"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "item"));
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
