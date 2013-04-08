/**
 * CriterioBusquedaDocumentos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.nt.ws.client;

public class CriterioBusquedaDocumentos  implements java.io.Serializable {
    private java.lang.String codigDoc;

    private java.lang.String codigoNoti;

    private java.lang.String expediente;

    private java.lang.String nifDestinatario;

    public CriterioBusquedaDocumentos() {
    }

    public CriterioBusquedaDocumentos(
           java.lang.String codigDoc,
           java.lang.String codigoNoti,
           java.lang.String expediente,
           java.lang.String nifDestinatario) {
           this.codigDoc = codigDoc;
           this.codigoNoti = codigoNoti;
           this.expediente = expediente;
           this.nifDestinatario = nifDestinatario;
    }


    /**
     * Gets the codigDoc value for this CriterioBusquedaDocumentos.
     * 
     * @return codigDoc
     */
    public java.lang.String getCodigDoc() {
        return codigDoc;
    }


    /**
     * Sets the codigDoc value for this CriterioBusquedaDocumentos.
     * 
     * @param codigDoc
     */
    public void setCodigDoc(java.lang.String codigDoc) {
        this.codigDoc = codigDoc;
    }


    /**
     * Gets the codigoNoti value for this CriterioBusquedaDocumentos.
     * 
     * @return codigoNoti
     */
    public java.lang.String getCodigoNoti() {
        return codigoNoti;
    }


    /**
     * Sets the codigoNoti value for this CriterioBusquedaDocumentos.
     * 
     * @param codigoNoti
     */
    public void setCodigoNoti(java.lang.String codigoNoti) {
        this.codigoNoti = codigoNoti;
    }


    /**
     * Gets the expediente value for this CriterioBusquedaDocumentos.
     * 
     * @return expediente
     */
    public java.lang.String getExpediente() {
        return expediente;
    }


    /**
     * Sets the expediente value for this CriterioBusquedaDocumentos.
     * 
     * @param expediente
     */
    public void setExpediente(java.lang.String expediente) {
        this.expediente = expediente;
    }


    /**
     * Gets the nifDestinatario value for this CriterioBusquedaDocumentos.
     * 
     * @return nifDestinatario
     */
    public java.lang.String getNifDestinatario() {
        return nifDestinatario;
    }


    /**
     * Sets the nifDestinatario value for this CriterioBusquedaDocumentos.
     * 
     * @param nifDestinatario
     */
    public void setNifDestinatario(java.lang.String nifDestinatario) {
        this.nifDestinatario = nifDestinatario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CriterioBusquedaDocumentos)) return false;
        CriterioBusquedaDocumentos other = (CriterioBusquedaDocumentos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigDoc==null && other.getCodigDoc()==null) || 
             (this.codigDoc!=null &&
              this.codigDoc.equals(other.getCodigDoc()))) &&
            ((this.codigoNoti==null && other.getCodigoNoti()==null) || 
             (this.codigoNoti!=null &&
              this.codigoNoti.equals(other.getCodigoNoti()))) &&
            ((this.expediente==null && other.getExpediente()==null) || 
             (this.expediente!=null &&
              this.expediente.equals(other.getExpediente()))) &&
            ((this.nifDestinatario==null && other.getNifDestinatario()==null) || 
             (this.nifDestinatario!=null &&
              this.nifDestinatario.equals(other.getNifDestinatario())));
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
        if (getCodigDoc() != null) {
            _hashCode += getCodigDoc().hashCode();
        }
        if (getCodigoNoti() != null) {
            _hashCode += getCodigoNoti().hashCode();
        }
        if (getExpediente() != null) {
            _hashCode += getExpediente().hashCode();
        }
        if (getNifDestinatario() != null) {
            _hashCode += getNifDestinatario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CriterioBusquedaDocumentos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "CriterioBusquedaDocumentos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigDoc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "codigDoc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoNoti");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "codigoNoti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expediente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "expediente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nifDestinatario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "nifDestinatario"));
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
