/**
 * FicherosHito.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.ct.ws.client;

public class FicherosHito  extends ieci.tecdoc.sgm.ct.ws.client.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.ct.ws.client.FicheroHito[] ficherosHito;

    private java.lang.String guid;

    public FicherosHito() {
    }

    public FicherosHito(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.ct.ws.client.FicheroHito[] ficherosHito,
           java.lang.String guid) {
        super(
            errorCode,
            returnCode);
        this.ficherosHito = ficherosHito;
        this.guid = guid;
    }


    /**
     * Gets the ficherosHito value for this FicherosHito.
     * 
     * @return ficherosHito
     */
    public ieci.tecdoc.sgm.ct.ws.client.FicheroHito[] getFicherosHito() {
        return ficherosHito;
    }


    /**
     * Sets the ficherosHito value for this FicherosHito.
     * 
     * @param ficherosHito
     */
    public void setFicherosHito(ieci.tecdoc.sgm.ct.ws.client.FicheroHito[] ficherosHito) {
        this.ficherosHito = ficherosHito;
    }


    /**
     * Gets the guid value for this FicherosHito.
     * 
     * @return guid
     */
    public java.lang.String getGuid() {
        return guid;
    }


    /**
     * Sets the guid value for this FicherosHito.
     * 
     * @param guid
     */
    public void setGuid(java.lang.String guid) {
        this.guid = guid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FicherosHito)) return false;
        FicherosHito other = (FicherosHito) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.ficherosHito==null && other.getFicherosHito()==null) || 
             (this.ficherosHito!=null &&
              java.util.Arrays.equals(this.ficherosHito, other.getFicherosHito()))) &&
            ((this.guid==null && other.getGuid()==null) || 
             (this.guid!=null &&
              this.guid.equals(other.getGuid())));
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
        if (getFicherosHito() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFicherosHito());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFicherosHito(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGuid() != null) {
            _hashCode += getGuid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FicherosHito.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "FicherosHito"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ficherosHito");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "ficherosHito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "FicheroHito"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("guid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "guid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
