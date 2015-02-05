/**
 * ContenedorDocumentos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registro.ws.client.axis;

public class ContenedorDocumentos  extends ieci.tecdoc.sgm.registro.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.registro.ws.client.axis.ContenedorDocumento[] contenedorDocumentos;

    public ContenedorDocumentos() {
    }

    public ContenedorDocumentos(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.registro.ws.client.axis.ContenedorDocumento[] contenedorDocumentos) {
        super(
            errorCode,
            returnCode);
        this.contenedorDocumentos = contenedorDocumentos;
    }


    /**
     * Gets the contenedorDocumentos value for this ContenedorDocumentos.
     * 
     * @return contenedorDocumentos
     */
    public ieci.tecdoc.sgm.registro.ws.client.axis.ContenedorDocumento[] getContenedorDocumentos() {
        return contenedorDocumentos;
    }


    /**
     * Sets the contenedorDocumentos value for this ContenedorDocumentos.
     * 
     * @param contenedorDocumentos
     */
    public void setContenedorDocumentos(ieci.tecdoc.sgm.registro.ws.client.axis.ContenedorDocumento[] contenedorDocumentos) {
        this.contenedorDocumentos = contenedorDocumentos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContenedorDocumentos)) return false;
        ContenedorDocumentos other = (ContenedorDocumentos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.contenedorDocumentos==null && other.getContenedorDocumentos()==null) || 
             (this.contenedorDocumentos!=null &&
              java.util.Arrays.equals(this.contenedorDocumentos, other.getContenedorDocumentos())));
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
        if (getContenedorDocumentos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getContenedorDocumentos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getContenedorDocumentos(), i);
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
        new org.apache.axis.description.TypeDesc(ContenedorDocumentos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "ContenedorDocumentos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contenedorDocumentos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "contenedorDocumentos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "ContenedorDocumento"));
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
