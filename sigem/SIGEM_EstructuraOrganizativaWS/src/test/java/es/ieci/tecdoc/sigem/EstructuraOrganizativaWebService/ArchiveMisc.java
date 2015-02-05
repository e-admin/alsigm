/**
 * ArchiveMisc.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService;

public class ArchiveMisc  extends es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio  implements java.io.Serializable {
    private java.lang.String fdrName;

    private int volListId;

    private int volListType;

    public ArchiveMisc() {
    }

    public ArchiveMisc(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String fdrName,
           int volListId,
           int volListType) {
        super(
            errorCode,
            returnCode);
        this.fdrName = fdrName;
        this.volListId = volListId;
        this.volListType = volListType;
    }


    /**
     * Gets the fdrName value for this ArchiveMisc.
     * 
     * @return fdrName
     */
    public java.lang.String getFdrName() {
        return fdrName;
    }


    /**
     * Sets the fdrName value for this ArchiveMisc.
     * 
     * @param fdrName
     */
    public void setFdrName(java.lang.String fdrName) {
        this.fdrName = fdrName;
    }


    /**
     * Gets the volListId value for this ArchiveMisc.
     * 
     * @return volListId
     */
    public int getVolListId() {
        return volListId;
    }


    /**
     * Sets the volListId value for this ArchiveMisc.
     * 
     * @param volListId
     */
    public void setVolListId(int volListId) {
        this.volListId = volListId;
    }


    /**
     * Gets the volListType value for this ArchiveMisc.
     * 
     * @return volListType
     */
    public int getVolListType() {
        return volListType;
    }


    /**
     * Sets the volListType value for this ArchiveMisc.
     * 
     * @param volListType
     */
    public void setVolListType(int volListType) {
        this.volListType = volListType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArchiveMisc)) return false;
        ArchiveMisc other = (ArchiveMisc) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.fdrName==null && other.getFdrName()==null) || 
             (this.fdrName!=null &&
              this.fdrName.equals(other.getFdrName()))) &&
            this.volListId == other.getVolListId() &&
            this.volListType == other.getVolListType();
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
        if (getFdrName() != null) {
            _hashCode += getFdrName().hashCode();
        }
        _hashCode += getVolListId();
        _hashCode += getVolListType();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ArchiveMisc.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArchiveMisc"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fdrName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "fdrName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volListId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "volListId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volListType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "volListType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
