/**
 * InfoDocumentos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.rde.ws.client;

public class InfoDocumentos  implements java.io.Serializable {
    private ieci.tecdoc.sgm.rde.ws.client.InfoDocumento[] documentos;

    public InfoDocumentos() {
    }

    public InfoDocumentos(
           ieci.tecdoc.sgm.rde.ws.client.InfoDocumento[] documentos) {
           this.documentos = documentos;
    }


    /**
     * Gets the documentos value for this InfoDocumentos.
     * 
     * @return documentos
     */
    public ieci.tecdoc.sgm.rde.ws.client.InfoDocumento[] getDocumentos() {
        return documentos;
    }


    /**
     * Sets the documentos value for this InfoDocumentos.
     * 
     * @param documentos
     */
    public void setDocumentos(ieci.tecdoc.sgm.rde.ws.client.InfoDocumento[] documentos) {
        this.documentos = documentos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InfoDocumentos)) return false;
        InfoDocumentos other = (InfoDocumentos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.documentos==null && other.getDocumentos()==null) || 
             (this.documentos!=null &&
              java.util.Arrays.equals(this.documentos, other.getDocumentos())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDocumentos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDocumentos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDocumentos(), i);
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
        new org.apache.axis.description.TypeDesc(InfoDocumentos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.rde.sgm.tecdoc.ieci", "InfoDocumentos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.rde.sgm.tecdoc.ieci", "documentos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.rde.sgm.tecdoc.ieci", "InfoDocumento"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.rde.sgm.tecdoc.ieci", "item"));
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
