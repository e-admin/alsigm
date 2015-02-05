/**
 * FicheroHito.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.ConsultaExpedientesWebService;

public class FicheroHito  implements java.io.Serializable {
    private java.lang.String guid;

    private java.lang.String guidHito;

    private java.lang.String titulo;

    public FicheroHito() {
    }

    public FicheroHito(
           java.lang.String guid,
           java.lang.String guidHito,
           java.lang.String titulo) {
           this.guid = guid;
           this.guidHito = guidHito;
           this.titulo = titulo;
    }


    /**
     * Gets the guid value for this FicheroHito.
     * 
     * @return guid
     */
    public java.lang.String getGuid() {
        return guid;
    }


    /**
     * Sets the guid value for this FicheroHito.
     * 
     * @param guid
     */
    public void setGuid(java.lang.String guid) {
        this.guid = guid;
    }


    /**
     * Gets the guidHito value for this FicheroHito.
     * 
     * @return guidHito
     */
    public java.lang.String getGuidHito() {
        return guidHito;
    }


    /**
     * Sets the guidHito value for this FicheroHito.
     * 
     * @param guidHito
     */
    public void setGuidHito(java.lang.String guidHito) {
        this.guidHito = guidHito;
    }


    /**
     * Gets the titulo value for this FicheroHito.
     * 
     * @return titulo
     */
    public java.lang.String getTitulo() {
        return titulo;
    }


    /**
     * Sets the titulo value for this FicheroHito.
     * 
     * @param titulo
     */
    public void setTitulo(java.lang.String titulo) {
        this.titulo = titulo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FicheroHito)) return false;
        FicheroHito other = (FicheroHito) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.guid==null && other.getGuid()==null) || 
             (this.guid!=null &&
              this.guid.equals(other.getGuid()))) &&
            ((this.guidHito==null && other.getGuidHito()==null) || 
             (this.guidHito!=null &&
              this.guidHito.equals(other.getGuidHito()))) &&
            ((this.titulo==null && other.getTitulo()==null) || 
             (this.titulo!=null &&
              this.titulo.equals(other.getTitulo())));
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
        if (getGuid() != null) {
            _hashCode += getGuid().hashCode();
        }
        if (getGuidHito() != null) {
            _hashCode += getGuidHito().hashCode();
        }
        if (getTitulo() != null) {
            _hashCode += getTitulo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FicheroHito.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "FicheroHito"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("guid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "guid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("guidHito");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "guidHito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titulo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "titulo"));
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
