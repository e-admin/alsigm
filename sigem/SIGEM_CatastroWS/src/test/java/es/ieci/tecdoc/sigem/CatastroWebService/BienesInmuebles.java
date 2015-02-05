/**
 * BienesInmuebles.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.CatastroWebService;

public class BienesInmuebles  extends es.ieci.tecdoc.sigem.CatastroWebService.RetornoServicio  implements java.io.Serializable {
    private es.ieci.tecdoc.sigem.CatastroWebService.ArrayOfBienInmueble bienesInmuebles;

    public BienesInmuebles() {
    }

    public BienesInmuebles(
           java.lang.String errorCode,
           java.lang.String returnCode,
           es.ieci.tecdoc.sigem.CatastroWebService.ArrayOfBienInmueble bienesInmuebles) {
        super(
            errorCode,
            returnCode);
        this.bienesInmuebles = bienesInmuebles;
    }


    /**
     * Gets the bienesInmuebles value for this BienesInmuebles.
     * 
     * @return bienesInmuebles
     */
    public es.ieci.tecdoc.sigem.CatastroWebService.ArrayOfBienInmueble getBienesInmuebles() {
        return bienesInmuebles;
    }


    /**
     * Sets the bienesInmuebles value for this BienesInmuebles.
     * 
     * @param bienesInmuebles
     */
    public void setBienesInmuebles(es.ieci.tecdoc.sigem.CatastroWebService.ArrayOfBienInmueble bienesInmuebles) {
        this.bienesInmuebles = bienesInmuebles;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BienesInmuebles)) return false;
        BienesInmuebles other = (BienesInmuebles) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.bienesInmuebles==null && other.getBienesInmuebles()==null) || 
             (this.bienesInmuebles!=null &&
              this.bienesInmuebles.equals(other.getBienesInmuebles())));
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
        if (getBienesInmuebles() != null) {
            _hashCode += getBienesInmuebles().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BienesInmuebles.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "BienesInmuebles"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bienesInmuebles");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "bienesInmuebles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "ArrayOfBienInmueble"));
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
