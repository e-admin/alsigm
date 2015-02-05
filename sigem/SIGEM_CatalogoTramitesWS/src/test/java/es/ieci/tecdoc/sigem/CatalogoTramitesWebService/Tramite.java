/**
 * Tramite.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.CatalogoTramitesWebService;

public class Tramite  extends es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio  implements java.io.Serializable {
    private java.lang.String addressee;

    private java.lang.String description;

    private es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Documentos documents;

    private java.lang.String firma;

    private java.lang.String id;

    private java.lang.String idProcedimiento;

    private java.lang.String limitDocs;

    private java.lang.String oficina;

    private java.lang.String topic;

    public Tramite() {
    }

    public Tramite(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String addressee,
           java.lang.String description,
           es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Documentos documents,
           java.lang.String firma,
           java.lang.String id,
           java.lang.String idProcedimiento,
           java.lang.String limitDocs,
           java.lang.String oficina,
           java.lang.String topic) {
        super(
            errorCode,
            returnCode);
        this.addressee = addressee;
        this.description = description;
        this.documents = documents;
        this.firma = firma;
        this.id = id;
        this.idProcedimiento = idProcedimiento;
        this.limitDocs = limitDocs;
        this.oficina = oficina;
        this.topic = topic;
    }


    /**
     * Gets the addressee value for this Tramite.
     * 
     * @return addressee
     */
    public java.lang.String getAddressee() {
        return addressee;
    }


    /**
     * Sets the addressee value for this Tramite.
     * 
     * @param addressee
     */
    public void setAddressee(java.lang.String addressee) {
        this.addressee = addressee;
    }


    /**
     * Gets the description value for this Tramite.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Tramite.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the documents value for this Tramite.
     * 
     * @return documents
     */
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Documentos getDocuments() {
        return documents;
    }


    /**
     * Sets the documents value for this Tramite.
     * 
     * @param documents
     */
    public void setDocuments(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Documentos documents) {
        this.documents = documents;
    }


    /**
     * Gets the firma value for this Tramite.
     * 
     * @return firma
     */
    public java.lang.String getFirma() {
        return firma;
    }


    /**
     * Sets the firma value for this Tramite.
     * 
     * @param firma
     */
    public void setFirma(java.lang.String firma) {
        this.firma = firma;
    }


    /**
     * Gets the id value for this Tramite.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this Tramite.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the idProcedimiento value for this Tramite.
     * 
     * @return idProcedimiento
     */
    public java.lang.String getIdProcedimiento() {
        return idProcedimiento;
    }


    /**
     * Sets the idProcedimiento value for this Tramite.
     * 
     * @param idProcedimiento
     */
    public void setIdProcedimiento(java.lang.String idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }


    /**
     * Gets the limitDocs value for this Tramite.
     * 
     * @return limitDocs
     */
    public java.lang.String getLimitDocs() {
        return limitDocs;
    }


    /**
     * Sets the limitDocs value for this Tramite.
     * 
     * @param limitDocs
     */
    public void setLimitDocs(java.lang.String limitDocs) {
        this.limitDocs = limitDocs;
    }


    /**
     * Gets the oficina value for this Tramite.
     * 
     * @return oficina
     */
    public java.lang.String getOficina() {
        return oficina;
    }


    /**
     * Sets the oficina value for this Tramite.
     * 
     * @param oficina
     */
    public void setOficina(java.lang.String oficina) {
        this.oficina = oficina;
    }


    /**
     * Gets the topic value for this Tramite.
     * 
     * @return topic
     */
    public java.lang.String getTopic() {
        return topic;
    }


    /**
     * Sets the topic value for this Tramite.
     * 
     * @param topic
     */
    public void setTopic(java.lang.String topic) {
        this.topic = topic;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Tramite)) return false;
        Tramite other = (Tramite) obj;
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
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.documents==null && other.getDocuments()==null) || 
             (this.documents!=null &&
              this.documents.equals(other.getDocuments()))) &&
            ((this.firma==null && other.getFirma()==null) || 
             (this.firma!=null &&
              this.firma.equals(other.getFirma()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.idProcedimiento==null && other.getIdProcedimiento()==null) || 
             (this.idProcedimiento!=null &&
              this.idProcedimiento.equals(other.getIdProcedimiento()))) &&
            ((this.limitDocs==null && other.getLimitDocs()==null) || 
             (this.limitDocs!=null &&
              this.limitDocs.equals(other.getLimitDocs()))) &&
            ((this.oficina==null && other.getOficina()==null) || 
             (this.oficina!=null &&
              this.oficina.equals(other.getOficina()))) &&
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
        if (getAddressee() != null) {
            _hashCode += getAddressee().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getDocuments() != null) {
            _hashCode += getDocuments().hashCode();
        }
        if (getFirma() != null) {
            _hashCode += getFirma().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getIdProcedimiento() != null) {
            _hashCode += getIdProcedimiento().hashCode();
        }
        if (getLimitDocs() != null) {
            _hashCode += getLimitDocs().hashCode();
        }
        if (getOficina() != null) {
            _hashCode += getOficina().hashCode();
        }
        if (getTopic() != null) {
            _hashCode += getTopic().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Tramite.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "Tramite"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "addressee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documents");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "documents"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "Documentos"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firma");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "firma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idProcedimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "idProcedimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limitDocs");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "limitDocs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oficina");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "oficina"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topic");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "topic"));
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
