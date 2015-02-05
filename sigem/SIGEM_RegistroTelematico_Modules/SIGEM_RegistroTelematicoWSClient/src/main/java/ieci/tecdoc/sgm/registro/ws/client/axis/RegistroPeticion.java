/**
 * RegistroPeticion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registro.ws.client.axis;

public class RegistroPeticion  extends ieci.tecdoc.sgm.registro.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private java.lang.String addressee;

    private ieci.tecdoc.sgm.registro.ws.client.axis.PeticionDocumentos documents;

    private java.lang.String email;

    private java.lang.String folderId;

    private java.lang.String procedureId;

    private java.lang.String senderIdType;

    private java.lang.String specificData;

    public RegistroPeticion() {
    }

    public RegistroPeticion(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String addressee,
           ieci.tecdoc.sgm.registro.ws.client.axis.PeticionDocumentos documents,
           java.lang.String email,
           java.lang.String folderId,
           java.lang.String procedureId,
           java.lang.String senderIdType,
           java.lang.String specificData) {
        super(
            errorCode,
            returnCode);
        this.addressee = addressee;
        this.documents = documents;
        this.email = email;
        this.folderId = folderId;
        this.procedureId = procedureId;
        this.senderIdType = senderIdType;
        this.specificData = specificData;
    }


    /**
     * Gets the addressee value for this RegistroPeticion.
     * 
     * @return addressee
     */
    public java.lang.String getAddressee() {
        return addressee;
    }


    /**
     * Sets the addressee value for this RegistroPeticion.
     * 
     * @param addressee
     */
    public void setAddressee(java.lang.String addressee) {
        this.addressee = addressee;
    }


    /**
     * Gets the documents value for this RegistroPeticion.
     * 
     * @return documents
     */
    public ieci.tecdoc.sgm.registro.ws.client.axis.PeticionDocumentos getDocuments() {
        return documents;
    }


    /**
     * Sets the documents value for this RegistroPeticion.
     * 
     * @param documents
     */
    public void setDocuments(ieci.tecdoc.sgm.registro.ws.client.axis.PeticionDocumentos documents) {
        this.documents = documents;
    }


    /**
     * Gets the email value for this RegistroPeticion.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this RegistroPeticion.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }


    /**
     * Gets the folderId value for this RegistroPeticion.
     * 
     * @return folderId
     */
    public java.lang.String getFolderId() {
        return folderId;
    }


    /**
     * Sets the folderId value for this RegistroPeticion.
     * 
     * @param folderId
     */
    public void setFolderId(java.lang.String folderId) {
        this.folderId = folderId;
    }


    /**
     * Gets the procedureId value for this RegistroPeticion.
     * 
     * @return procedureId
     */
    public java.lang.String getProcedureId() {
        return procedureId;
    }


    /**
     * Sets the procedureId value for this RegistroPeticion.
     * 
     * @param procedureId
     */
    public void setProcedureId(java.lang.String procedureId) {
        this.procedureId = procedureId;
    }


    /**
     * Gets the senderIdType value for this RegistroPeticion.
     * 
     * @return senderIdType
     */
    public java.lang.String getSenderIdType() {
        return senderIdType;
    }


    /**
     * Sets the senderIdType value for this RegistroPeticion.
     * 
     * @param senderIdType
     */
    public void setSenderIdType(java.lang.String senderIdType) {
        this.senderIdType = senderIdType;
    }


    /**
     * Gets the specificData value for this RegistroPeticion.
     * 
     * @return specificData
     */
    public java.lang.String getSpecificData() {
        return specificData;
    }


    /**
     * Sets the specificData value for this RegistroPeticion.
     * 
     * @param specificData
     */
    public void setSpecificData(java.lang.String specificData) {
        this.specificData = specificData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegistroPeticion)) return false;
        RegistroPeticion other = (RegistroPeticion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.addressee==null && other.getAddressee()==null) || 
             (this.addressee!=null &&
              this.addressee.equals(other.getAddressee()))) &&
            ((this.documents==null && other.getDocuments()==null) || 
             (this.documents!=null &&
              this.documents.equals(other.getDocuments()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.folderId==null && other.getFolderId()==null) || 
             (this.folderId!=null &&
              this.folderId.equals(other.getFolderId()))) &&
            ((this.procedureId==null && other.getProcedureId()==null) || 
             (this.procedureId!=null &&
              this.procedureId.equals(other.getProcedureId()))) &&
            ((this.senderIdType==null && other.getSenderIdType()==null) || 
             (this.senderIdType!=null &&
              this.senderIdType.equals(other.getSenderIdType()))) &&
            ((this.specificData==null && other.getSpecificData()==null) || 
             (this.specificData!=null &&
              this.specificData.equals(other.getSpecificData())));
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
        if (getAddressee() != null) {
            _hashCode += getAddressee().hashCode();
        }
        if (getDocuments() != null) {
            _hashCode += getDocuments().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getFolderId() != null) {
            _hashCode += getFolderId().hashCode();
        }
        if (getProcedureId() != null) {
            _hashCode += getProcedureId().hashCode();
        }
        if (getSenderIdType() != null) {
            _hashCode += getSenderIdType().hashCode();
        }
        if (getSpecificData() != null) {
            _hashCode += getSpecificData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegistroPeticion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "RegistroPeticion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "addressee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documents");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "documents"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "PeticionDocumentos"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("folderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "folderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("procedureId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "procedureId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("senderIdType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "senderIdType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specificData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "specificData"));
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
