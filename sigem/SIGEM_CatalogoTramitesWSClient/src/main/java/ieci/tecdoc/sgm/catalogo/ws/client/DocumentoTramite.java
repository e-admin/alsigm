/**
 * DocumentoTramite.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.catalogo.ws.client;

public class DocumentoTramite  extends ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String code;

    private java.lang.String documentId;

    private java.lang.String mandatory;

    private java.lang.String procedureId;

    public DocumentoTramite() {
    }

    public DocumentoTramite(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String code,
           java.lang.String documentId,
           java.lang.String mandatory,
           java.lang.String procedureId) {
        super(
            errorCode,
            returnCode);
        this.code = code;
        this.documentId = documentId;
        this.mandatory = mandatory;
        this.procedureId = procedureId;
    }


    /**
     * Gets the code value for this DocumentoTramite.
     * 
     * @return code
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this DocumentoTramite.
     * 
     * @param code
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the documentId value for this DocumentoTramite.
     * 
     * @return documentId
     */
    public java.lang.String getDocumentId() {
        return documentId;
    }


    /**
     * Sets the documentId value for this DocumentoTramite.
     * 
     * @param documentId
     */
    public void setDocumentId(java.lang.String documentId) {
        this.documentId = documentId;
    }


    /**
     * Gets the mandatory value for this DocumentoTramite.
     * 
     * @return mandatory
     */
    public java.lang.String getMandatory() {
        return mandatory;
    }


    /**
     * Sets the mandatory value for this DocumentoTramite.
     * 
     * @param mandatory
     */
    public void setMandatory(java.lang.String mandatory) {
        this.mandatory = mandatory;
    }


    /**
     * Gets the procedureId value for this DocumentoTramite.
     * 
     * @return procedureId
     */
    public java.lang.String getProcedureId() {
        return procedureId;
    }


    /**
     * Sets the procedureId value for this DocumentoTramite.
     * 
     * @param procedureId
     */
    public void setProcedureId(java.lang.String procedureId) {
        this.procedureId = procedureId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocumentoTramite)) return false;
        DocumentoTramite other = (DocumentoTramite) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            ((this.documentId==null && other.getDocumentId()==null) || 
             (this.documentId!=null &&
              this.documentId.equals(other.getDocumentId()))) &&
            ((this.mandatory==null && other.getMandatory()==null) || 
             (this.mandatory!=null &&
              this.mandatory.equals(other.getMandatory()))) &&
            ((this.procedureId==null && other.getProcedureId()==null) || 
             (this.procedureId!=null &&
              this.procedureId.equals(other.getProcedureId())));
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
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getDocumentId() != null) {
            _hashCode += getDocumentId().hashCode();
        }
        if (getMandatory() != null) {
            _hashCode += getMandatory().hashCode();
        }
        if (getProcedureId() != null) {
            _hashCode += getProcedureId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocumentoTramite.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "DocumentoTramite"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "documentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mandatory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "mandatory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("procedureId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "procedureId"));
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
