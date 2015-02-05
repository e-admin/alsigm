/**
 * Parcelas.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.catastro.ws.client;

public class Parcelas  extends ieci.tecdoc.sgm.catastro.ws.client.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.catastro.ws.client.Parcela[] parcelas;

    public Parcelas() {
    }

    public Parcelas(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.catastro.ws.client.Parcela[] parcelas) {
        super(
            errorCode,
            returnCode);
        this.parcelas = parcelas;
    }


    /**
     * Gets the parcelas value for this Parcelas.
     * 
     * @return parcelas
     */
    public ieci.tecdoc.sgm.catastro.ws.client.Parcela[] getParcelas() {
        return parcelas;
    }


    /**
     * Sets the parcelas value for this Parcelas.
     * 
     * @param parcelas
     */
    public void setParcelas(ieci.tecdoc.sgm.catastro.ws.client.Parcela[] parcelas) {
        this.parcelas = parcelas;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Parcelas)) return false;
        Parcelas other = (Parcelas) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.parcelas==null && other.getParcelas()==null) || 
             (this.parcelas!=null &&
              java.util.Arrays.equals(this.parcelas, other.getParcelas())));
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
        if (getParcelas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getParcelas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getParcelas(), i);
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
        new org.apache.axis.description.TypeDesc(Parcelas.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "Parcelas"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parcelas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "parcelas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "Parcela"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "item"));
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
