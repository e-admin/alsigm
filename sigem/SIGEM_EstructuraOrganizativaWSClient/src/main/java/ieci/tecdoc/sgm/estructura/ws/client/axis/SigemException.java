/**
 * SigemException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public class SigemException  extends org.apache.axis.AxisFault  implements java.io.Serializable {
    private long errorCode;

    private java.lang.String message1;

    private java.lang.String messagesFile;

    public SigemException() {
    }

    public SigemException(
           long errorCode,
           java.lang.String message1,
           java.lang.String messagesFile) {
        this.errorCode = errorCode;
        this.message1 = message1;
        this.messagesFile = messagesFile;
    }


    /**
     * Gets the errorCode value for this SigemException.
     * 
     * @return errorCode
     */
    public long getErrorCode() {
        return errorCode;
    }


    /**
     * Sets the errorCode value for this SigemException.
     * 
     * @param errorCode
     */
    public void setErrorCode(long errorCode) {
        this.errorCode = errorCode;
    }


    /**
     * Gets the message1 value for this SigemException.
     * 
     * @return message1
     */
    public java.lang.String getMessage1() {
        return message1;
    }


    /**
     * Sets the message1 value for this SigemException.
     * 
     * @param message1
     */
    public void setMessage1(java.lang.String message1) {
        this.message1 = message1;
    }


    /**
     * Gets the messagesFile value for this SigemException.
     * 
     * @return messagesFile
     */
    public java.lang.String getMessagesFile() {
        return messagesFile;
    }


    /**
     * Sets the messagesFile value for this SigemException.
     * 
     * @param messagesFile
     */
    public void setMessagesFile(java.lang.String messagesFile) {
        this.messagesFile = messagesFile;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SigemException)) return false;
        SigemException other = (SigemException) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.errorCode == other.getErrorCode() &&
            ((this.message1==null && other.getMessage1()==null) || 
             (this.message1!=null &&
              this.message1.equals(other.getMessage1()))) &&
            ((this.messagesFile==null && other.getMessagesFile()==null) || 
             (this.messagesFile!=null &&
              this.messagesFile.equals(other.getMessagesFile())));
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
        _hashCode += new Long(getErrorCode()).hashCode();
        if (getMessage1() != null) {
            _hashCode += getMessage1().hashCode();
        }
        if (getMessagesFile() != null) {
            _hashCode += getMessagesFile().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SigemException.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://exception.core.sgm.tecdoc.ieci", "SigemException"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://exception.core.sgm.tecdoc.ieci", "errorCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://exception.core.sgm.tecdoc.ieci", "message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messagesFile");
        elemField.setXmlName(new javax.xml.namespace.QName("http://exception.core.sgm.tecdoc.ieci", "messagesFile"));
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


    /**
     * Writes the exception data to the faultDetails
     */
    public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
        context.serialize(qname, null, this);
    }
}
