/**
 * Conectores.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.catalogo.ws.client;

public class Conectores  extends ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.catalogo.ws.client.Conector[] conectores;

    public Conectores() {
    }

    public Conectores(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.catalogo.ws.client.Conector[] conectores) {
        super(
            errorCode,
            returnCode);
        this.conectores = conectores;
    }


    /**
     * Gets the conectores value for this Conectores.
     * 
     * @return conectores
     */
    public ieci.tecdoc.sgm.catalogo.ws.client.Conector[] getConectores() {
        return conectores;
    }


    /**
     * Sets the conectores value for this Conectores.
     * 
     * @param conectores
     */
    public void setConectores(ieci.tecdoc.sgm.catalogo.ws.client.Conector[] conectores) {
        this.conectores = conectores;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Conectores)) return false;
        Conectores other = (Conectores) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.conectores==null && other.getConectores()==null) || 
             (this.conectores!=null &&
              java.util.Arrays.equals(this.conectores, other.getConectores())));
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
        if (getConectores() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConectores());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConectores(), i);
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
        new org.apache.axis.description.TypeDesc(Conectores.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "Conectores"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("conectores");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "conectores"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "Conector"));
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
