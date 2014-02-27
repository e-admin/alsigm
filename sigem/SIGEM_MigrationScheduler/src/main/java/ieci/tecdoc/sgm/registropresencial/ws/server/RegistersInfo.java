/**
 * RegistersInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registropresencial.ws.server;

public class RegistersInfo  extends ieci.tecdoc.sgm.core.services.dto.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo[] registers;

    public RegistersInfo() {
    }

    public RegistersInfo(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo[] registers) {
        super(
            errorCode,
            returnCode);
        this.registers = registers;
    }


    /**
     * Gets the registers value for this RegistersInfo.
     * 
     * @return registers
     */
    public ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo[] getRegisters() {
        return registers;
    }


    /**
     * Sets the registers value for this RegistersInfo.
     * 
     * @param registers
     */
    public void setRegisters(ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo[] registers) {
        this.registers = registers;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegistersInfo)) return false;
        RegistersInfo other = (RegistersInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.registers==null && other.getRegisters()==null) || 
             (this.registers!=null &&
              java.util.Arrays.equals(this.registers, other.getRegisters())));
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
        if (getRegisters() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRegisters());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRegisters(), i);
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
        new org.apache.axis.description.TypeDesc(RegistersInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegistersInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "registers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegisterInfo"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "item"));
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
