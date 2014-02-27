/**
 * DistributionCountInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registropresencial.ws.server;

public class DistributionCountInfo  extends ieci.tecdoc.sgm.core.services.dto.RetornoServicio  implements java.io.Serializable {
    private java.lang.Integer count;

    private java.lang.Boolean oficAsoc;

    private java.lang.Integer state;

    private java.lang.Integer typeBookRegisterDist;

    public DistributionCountInfo() {
    }

    public DistributionCountInfo(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.Integer count,
           java.lang.Boolean oficAsoc,
           java.lang.Integer state,
           java.lang.Integer typeBookRegisterDist) {
        super(
            errorCode,
            returnCode);
        this.count = count;
        this.oficAsoc = oficAsoc;
        this.state = state;
        this.typeBookRegisterDist = typeBookRegisterDist;
    }


    /**
     * Gets the count value for this DistributionCountInfo.
     * 
     * @return count
     */
    public java.lang.Integer getCount() {
        return count;
    }


    /**
     * Sets the count value for this DistributionCountInfo.
     * 
     * @param count
     */
    public void setCount(java.lang.Integer count) {
        this.count = count;
    }


    /**
     * Gets the oficAsoc value for this DistributionCountInfo.
     * 
     * @return oficAsoc
     */
    public java.lang.Boolean getOficAsoc() {
        return oficAsoc;
    }


    /**
     * Sets the oficAsoc value for this DistributionCountInfo.
     * 
     * @param oficAsoc
     */
    public void setOficAsoc(java.lang.Boolean oficAsoc) {
        this.oficAsoc = oficAsoc;
    }


    /**
     * Gets the state value for this DistributionCountInfo.
     * 
     * @return state
     */
    public java.lang.Integer getState() {
        return state;
    }


    /**
     * Sets the state value for this DistributionCountInfo.
     * 
     * @param state
     */
    public void setState(java.lang.Integer state) {
        this.state = state;
    }


    /**
     * Gets the typeBookRegisterDist value for this DistributionCountInfo.
     * 
     * @return typeBookRegisterDist
     */
    public java.lang.Integer getTypeBookRegisterDist() {
        return typeBookRegisterDist;
    }


    /**
     * Sets the typeBookRegisterDist value for this DistributionCountInfo.
     * 
     * @param typeBookRegisterDist
     */
    public void setTypeBookRegisterDist(java.lang.Integer typeBookRegisterDist) {
        this.typeBookRegisterDist = typeBookRegisterDist;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DistributionCountInfo)) return false;
        DistributionCountInfo other = (DistributionCountInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.count==null && other.getCount()==null) || 
             (this.count!=null &&
              this.count.equals(other.getCount()))) &&
            ((this.oficAsoc==null && other.getOficAsoc()==null) || 
             (this.oficAsoc!=null &&
              this.oficAsoc.equals(other.getOficAsoc()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.typeBookRegisterDist==null && other.getTypeBookRegisterDist()==null) || 
             (this.typeBookRegisterDist!=null &&
              this.typeBookRegisterDist.equals(other.getTypeBookRegisterDist())));
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
        if (getCount() != null) {
            _hashCode += getCount().hashCode();
        }
        if (getOficAsoc() != null) {
            _hashCode += getOficAsoc().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getTypeBookRegisterDist() != null) {
            _hashCode += getTypeBookRegisterDist().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DistributionCountInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionCountInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("count");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "count"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oficAsoc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "oficAsoc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "state"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("typeBookRegisterDist");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "typeBookRegisterDist"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
