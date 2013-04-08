/**
 * RetornoCalendario.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.calendario.ws.client;

public class RetornoCalendario  extends ieci.tecdoc.sgm.calendario.ws.client.RetornoServicio  implements java.io.Serializable {
    private boolean laborable;

    private java.lang.String proximo;

    public RetornoCalendario() {
    }

    public RetornoCalendario(
           java.lang.String errorCode,
           java.lang.String returnCode,
           boolean laborable,
           java.lang.String proximo) {
        super(
            errorCode,
            returnCode);
        this.laborable = laborable;
        this.proximo = proximo;
    }


    /**
     * Gets the laborable value for this RetornoCalendario.
     * 
     * @return laborable
     */
    public boolean isLaborable() {
        return laborable;
    }


    /**
     * Sets the laborable value for this RetornoCalendario.
     * 
     * @param laborable
     */
    public void setLaborable(boolean laborable) {
        this.laborable = laborable;
    }


    /**
     * Gets the proximo value for this RetornoCalendario.
     * 
     * @return proximo
     */
    public java.lang.String getProximo() {
        return proximo;
    }


    /**
     * Sets the proximo value for this RetornoCalendario.
     * 
     * @param proximo
     */
    public void setProximo(java.lang.String proximo) {
        this.proximo = proximo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetornoCalendario)) return false;
        RetornoCalendario other = (RetornoCalendario) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.laborable == other.isLaborable() &&
            ((this.proximo==null && other.getProximo()==null) || 
             (this.proximo!=null &&
              this.proximo.equals(other.getProximo())));
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
        _hashCode += (isLaborable() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getProximo() != null) {
            _hashCode += getProximo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RetornoCalendario.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "RetornoCalendario"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("laborable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "laborable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("proximo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "proximo"));
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
