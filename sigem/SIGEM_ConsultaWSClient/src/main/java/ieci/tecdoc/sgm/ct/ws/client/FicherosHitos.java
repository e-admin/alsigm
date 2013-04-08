/**
 * FicherosHitos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.ct.ws.client;

public class FicherosHitos  extends ieci.tecdoc.sgm.ct.ws.client.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.ct.ws.client.FicherosHito[] ficherosHitos;

    public FicherosHitos() {
    }

    public FicherosHitos(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.ct.ws.client.FicherosHito[] ficherosHitos) {
        super(
            errorCode,
            returnCode);
        this.ficherosHitos = ficherosHitos;
    }


    /**
     * Gets the ficherosHitos value for this FicherosHitos.
     * 
     * @return ficherosHitos
     */
    public ieci.tecdoc.sgm.ct.ws.client.FicherosHito[] getFicherosHitos() {
        return ficherosHitos;
    }


    /**
     * Sets the ficherosHitos value for this FicherosHitos.
     * 
     * @param ficherosHitos
     */
    public void setFicherosHitos(ieci.tecdoc.sgm.ct.ws.client.FicherosHito[] ficherosHitos) {
        this.ficherosHitos = ficherosHitos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FicherosHitos)) return false;
        FicherosHitos other = (FicherosHitos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.ficherosHitos==null && other.getFicherosHitos()==null) || 
             (this.ficherosHitos!=null &&
              java.util.Arrays.equals(this.ficherosHitos, other.getFicherosHitos())));
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
        if (getFicherosHitos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFicherosHitos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFicherosHitos(), i);
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
        new org.apache.axis.description.TypeDesc(FicherosHitos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "FicherosHitos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ficherosHitos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "ficherosHitos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "FicherosHito"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "item"));
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
