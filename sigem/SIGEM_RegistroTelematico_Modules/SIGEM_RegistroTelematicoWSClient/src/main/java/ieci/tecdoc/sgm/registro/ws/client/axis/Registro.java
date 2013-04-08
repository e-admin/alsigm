/**
 * Registro.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registro.ws.client.axis;

public class Registro  extends ieci.tecdoc.sgm.registro.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private java.lang.String EMail;

    private java.lang.String additionalInfo;

    private java.lang.String addressee;

    private java.lang.String adittionalInfo;

    private java.lang.String effectiveDate;

    private java.lang.String name;

    private java.lang.String numeroExpediente;

    private java.lang.String oficina;

    private java.lang.String registryDate;

    private java.lang.String registryNumber;

    private java.lang.String representedId;

    private java.lang.String representedName;

    private java.lang.String senderId;

    private java.lang.String senderIdType;

    private java.lang.String status;

    private java.lang.String topic;

    public Registro() {
    }

    public Registro(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String EMail,
           java.lang.String additionalInfo,
           java.lang.String addressee,
           java.lang.String adittionalInfo,
           java.lang.String effectiveDate,
           java.lang.String name,
           java.lang.String numeroExpediente,
           java.lang.String oficina,
           java.lang.String registryDate,
           java.lang.String registryNumber,
           java.lang.String representedId,
           java.lang.String representedName,
           java.lang.String senderId,
           java.lang.String senderIdType,
           java.lang.String status,
           java.lang.String topic) {
        super(
            errorCode,
            returnCode);
        this.EMail = EMail;
        this.additionalInfo = additionalInfo;
        this.addressee = addressee;
        this.adittionalInfo = adittionalInfo;
        this.effectiveDate = effectiveDate;
        this.name = name;
        this.numeroExpediente = numeroExpediente;
        this.oficina = oficina;
        this.registryDate = registryDate;
        this.registryNumber = registryNumber;
        this.representedId = representedId;
        this.representedName = representedName;
        this.senderId = senderId;
        this.senderIdType = senderIdType;
        this.status = status;
        this.topic = topic;
    }


    /**
     * Gets the EMail value for this Registro.
     * 
     * @return EMail
     */
    public java.lang.String getEMail() {
        return EMail;
    }


    /**
     * Sets the EMail value for this Registro.
     * 
     * @param EMail
     */
    public void setEMail(java.lang.String EMail) {
        this.EMail = EMail;
    }


    /**
     * Gets the additionalInfo value for this Registro.
     * 
     * @return additionalInfo
     */
    public java.lang.String getAdditionalInfo() {
        return additionalInfo;
    }


    /**
     * Sets the additionalInfo value for this Registro.
     * 
     * @param additionalInfo
     */
    public void setAdditionalInfo(java.lang.String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }


    /**
     * Gets the addressee value for this Registro.
     * 
     * @return addressee
     */
    public java.lang.String getAddressee() {
        return addressee;
    }


    /**
     * Sets the addressee value for this Registro.
     * 
     * @param addressee
     */
    public void setAddressee(java.lang.String addressee) {
        this.addressee = addressee;
    }


    /**
     * Gets the adittionalInfo value for this Registro.
     * 
     * @return adittionalInfo
     */
    public java.lang.String getAdittionalInfo() {
        return adittionalInfo;
    }


    /**
     * Sets the adittionalInfo value for this Registro.
     * 
     * @param adittionalInfo
     */
    public void setAdittionalInfo(java.lang.String adittionalInfo) {
        this.adittionalInfo = adittionalInfo;
    }


    /**
     * Gets the effectiveDate value for this Registro.
     * 
     * @return effectiveDate
     */
    public java.lang.String getEffectiveDate() {
        return effectiveDate;
    }


    /**
     * Sets the effectiveDate value for this Registro.
     * 
     * @param effectiveDate
     */
    public void setEffectiveDate(java.lang.String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }


    /**
     * Gets the name value for this Registro.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Registro.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the numeroExpediente value for this Registro.
     * 
     * @return numeroExpediente
     */
    public java.lang.String getNumeroExpediente() {
        return numeroExpediente;
    }


    /**
     * Sets the numeroExpediente value for this Registro.
     * 
     * @param numeroExpediente
     */
    public void setNumeroExpediente(java.lang.String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }


    /**
     * Gets the oficina value for this Registro.
     * 
     * @return oficina
     */
    public java.lang.String getOficina() {
        return oficina;
    }


    /**
     * Sets the oficina value for this Registro.
     * 
     * @param oficina
     */
    public void setOficina(java.lang.String oficina) {
        this.oficina = oficina;
    }


    /**
     * Gets the registryDate value for this Registro.
     * 
     * @return registryDate
     */
    public java.lang.String getRegistryDate() {
        return registryDate;
    }


    /**
     * Sets the registryDate value for this Registro.
     * 
     * @param registryDate
     */
    public void setRegistryDate(java.lang.String registryDate) {
        this.registryDate = registryDate;
    }


    /**
     * Gets the registryNumber value for this Registro.
     * 
     * @return registryNumber
     */
    public java.lang.String getRegistryNumber() {
        return registryNumber;
    }


    /**
     * Sets the registryNumber value for this Registro.
     * 
     * @param registryNumber
     */
    public void setRegistryNumber(java.lang.String registryNumber) {
        this.registryNumber = registryNumber;
    }


    /**
     * Gets the representedId value for this Registro.
     * 
     * @return representedId
     */
    public java.lang.String getRepresentedId() {
        return representedId;
    }


    /**
     * Sets the representedId value for this Registro.
     * 
     * @param representedId
     */
    public void setRepresentedId(java.lang.String representedId) {
        this.representedId = representedId;
    }


    /**
     * Gets the representedName value for this Registro.
     * 
     * @return representedName
     */
    public java.lang.String getRepresentedName() {
        return representedName;
    }


    /**
     * Sets the representedName value for this Registro.
     * 
     * @param representedName
     */
    public void setRepresentedName(java.lang.String representedName) {
        this.representedName = representedName;
    }


    /**
     * Gets the senderId value for this Registro.
     * 
     * @return senderId
     */
    public java.lang.String getSenderId() {
        return senderId;
    }


    /**
     * Sets the senderId value for this Registro.
     * 
     * @param senderId
     */
    public void setSenderId(java.lang.String senderId) {
        this.senderId = senderId;
    }


    /**
     * Gets the senderIdType value for this Registro.
     * 
     * @return senderIdType
     */
    public java.lang.String getSenderIdType() {
        return senderIdType;
    }


    /**
     * Sets the senderIdType value for this Registro.
     * 
     * @param senderIdType
     */
    public void setSenderIdType(java.lang.String senderIdType) {
        this.senderIdType = senderIdType;
    }


    /**
     * Gets the status value for this Registro.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Registro.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the topic value for this Registro.
     * 
     * @return topic
     */
    public java.lang.String getTopic() {
        return topic;
    }


    /**
     * Sets the topic value for this Registro.
     * 
     * @param topic
     */
    public void setTopic(java.lang.String topic) {
        this.topic = topic;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Registro)) return false;
        Registro other = (Registro) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.EMail==null && other.getEMail()==null) || 
             (this.EMail!=null &&
              this.EMail.equals(other.getEMail()))) &&
            ((this.additionalInfo==null && other.getAdditionalInfo()==null) || 
             (this.additionalInfo!=null &&
              this.additionalInfo.equals(other.getAdditionalInfo()))) &&
            ((this.addressee==null && other.getAddressee()==null) || 
             (this.addressee!=null &&
              this.addressee.equals(other.getAddressee()))) &&
            ((this.adittionalInfo==null && other.getAdittionalInfo()==null) || 
             (this.adittionalInfo!=null &&
              this.adittionalInfo.equals(other.getAdittionalInfo()))) &&
            ((this.effectiveDate==null && other.getEffectiveDate()==null) || 
             (this.effectiveDate!=null &&
              this.effectiveDate.equals(other.getEffectiveDate()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.numeroExpediente==null && other.getNumeroExpediente()==null) || 
             (this.numeroExpediente!=null &&
              this.numeroExpediente.equals(other.getNumeroExpediente()))) &&
            ((this.oficina==null && other.getOficina()==null) || 
             (this.oficina!=null &&
              this.oficina.equals(other.getOficina()))) &&
            ((this.registryDate==null && other.getRegistryDate()==null) || 
             (this.registryDate!=null &&
              this.registryDate.equals(other.getRegistryDate()))) &&
            ((this.registryNumber==null && other.getRegistryNumber()==null) || 
             (this.registryNumber!=null &&
              this.registryNumber.equals(other.getRegistryNumber()))) &&
            ((this.representedId==null && other.getRepresentedId()==null) || 
             (this.representedId!=null &&
              this.representedId.equals(other.getRepresentedId()))) &&
            ((this.representedName==null && other.getRepresentedName()==null) || 
             (this.representedName!=null &&
              this.representedName.equals(other.getRepresentedName()))) &&
            ((this.senderId==null && other.getSenderId()==null) || 
             (this.senderId!=null &&
              this.senderId.equals(other.getSenderId()))) &&
            ((this.senderIdType==null && other.getSenderIdType()==null) || 
             (this.senderIdType!=null &&
              this.senderIdType.equals(other.getSenderIdType()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.topic==null && other.getTopic()==null) || 
             (this.topic!=null &&
              this.topic.equals(other.getTopic())));
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
        if (getEMail() != null) {
            _hashCode += getEMail().hashCode();
        }
        if (getAdditionalInfo() != null) {
            _hashCode += getAdditionalInfo().hashCode();
        }
        if (getAddressee() != null) {
            _hashCode += getAddressee().hashCode();
        }
        if (getAdittionalInfo() != null) {
            _hashCode += getAdittionalInfo().hashCode();
        }
        if (getEffectiveDate() != null) {
            _hashCode += getEffectiveDate().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getNumeroExpediente() != null) {
            _hashCode += getNumeroExpediente().hashCode();
        }
        if (getOficina() != null) {
            _hashCode += getOficina().hashCode();
        }
        if (getRegistryDate() != null) {
            _hashCode += getRegistryDate().hashCode();
        }
        if (getRegistryNumber() != null) {
            _hashCode += getRegistryNumber().hashCode();
        }
        if (getRepresentedId() != null) {
            _hashCode += getRepresentedId().hashCode();
        }
        if (getRepresentedName() != null) {
            _hashCode += getRepresentedName().hashCode();
        }
        if (getSenderId() != null) {
            _hashCode += getSenderId().hashCode();
        }
        if (getSenderIdType() != null) {
            _hashCode += getSenderIdType().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getTopic() != null) {
            _hashCode += getTopic().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Registro.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "Registro"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EMail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "EMail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "additionalInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "addressee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adittionalInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "adittionalInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effectiveDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "effectiveDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroExpediente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "numeroExpediente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oficina");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "oficina"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registryDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "registryDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registryNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "registryNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("representedId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "representedId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("representedName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "representedName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("senderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "senderId"));
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
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topic");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registro.sgm.tecdoc.ieci", "topic"));
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
