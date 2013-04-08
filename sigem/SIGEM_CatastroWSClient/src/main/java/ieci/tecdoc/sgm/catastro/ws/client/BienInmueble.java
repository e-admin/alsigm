/**
 * BienInmueble.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.catastro.ws.client;

public class BienInmueble  extends ieci.tecdoc.sgm.catastro.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String claseBienInmueble;

    private java.lang.String claseUso;

    private ieci.tecdoc.sgm.catastro.ws.client.Localizacion direccionLocalizacion;

    private ieci.tecdoc.sgm.catastro.ws.client.Construcciones lstConstrucciones;

    private ieci.tecdoc.sgm.catastro.ws.client.Cultivos lstCultivos;

    private java.lang.String referencia_catastral;

    private java.lang.Double superficie;

    public BienInmueble() {
    }

    public BienInmueble(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String claseBienInmueble,
           java.lang.String claseUso,
           ieci.tecdoc.sgm.catastro.ws.client.Localizacion direccionLocalizacion,
           ieci.tecdoc.sgm.catastro.ws.client.Construcciones lstConstrucciones,
           ieci.tecdoc.sgm.catastro.ws.client.Cultivos lstCultivos,
           java.lang.String referencia_catastral,
           java.lang.Double superficie) {
        super(
            errorCode,
            returnCode);
        this.claseBienInmueble = claseBienInmueble;
        this.claseUso = claseUso;
        this.direccionLocalizacion = direccionLocalizacion;
        this.lstConstrucciones = lstConstrucciones;
        this.lstCultivos = lstCultivos;
        this.referencia_catastral = referencia_catastral;
        this.superficie = superficie;
    }


    /**
     * Gets the claseBienInmueble value for this BienInmueble.
     * 
     * @return claseBienInmueble
     */
    public java.lang.String getClaseBienInmueble() {
        return claseBienInmueble;
    }


    /**
     * Sets the claseBienInmueble value for this BienInmueble.
     * 
     * @param claseBienInmueble
     */
    public void setClaseBienInmueble(java.lang.String claseBienInmueble) {
        this.claseBienInmueble = claseBienInmueble;
    }


    /**
     * Gets the claseUso value for this BienInmueble.
     * 
     * @return claseUso
     */
    public java.lang.String getClaseUso() {
        return claseUso;
    }


    /**
     * Sets the claseUso value for this BienInmueble.
     * 
     * @param claseUso
     */
    public void setClaseUso(java.lang.String claseUso) {
        this.claseUso = claseUso;
    }


    /**
     * Gets the direccionLocalizacion value for this BienInmueble.
     * 
     * @return direccionLocalizacion
     */
    public ieci.tecdoc.sgm.catastro.ws.client.Localizacion getDireccionLocalizacion() {
        return direccionLocalizacion;
    }


    /**
     * Sets the direccionLocalizacion value for this BienInmueble.
     * 
     * @param direccionLocalizacion
     */
    public void setDireccionLocalizacion(ieci.tecdoc.sgm.catastro.ws.client.Localizacion direccionLocalizacion) {
        this.direccionLocalizacion = direccionLocalizacion;
    }


    /**
     * Gets the lstConstrucciones value for this BienInmueble.
     * 
     * @return lstConstrucciones
     */
    public ieci.tecdoc.sgm.catastro.ws.client.Construcciones getLstConstrucciones() {
        return lstConstrucciones;
    }


    /**
     * Sets the lstConstrucciones value for this BienInmueble.
     * 
     * @param lstConstrucciones
     */
    public void setLstConstrucciones(ieci.tecdoc.sgm.catastro.ws.client.Construcciones lstConstrucciones) {
        this.lstConstrucciones = lstConstrucciones;
    }


    /**
     * Gets the lstCultivos value for this BienInmueble.
     * 
     * @return lstCultivos
     */
    public ieci.tecdoc.sgm.catastro.ws.client.Cultivos getLstCultivos() {
        return lstCultivos;
    }


    /**
     * Sets the lstCultivos value for this BienInmueble.
     * 
     * @param lstCultivos
     */
    public void setLstCultivos(ieci.tecdoc.sgm.catastro.ws.client.Cultivos lstCultivos) {
        this.lstCultivos = lstCultivos;
    }


    /**
     * Gets the referencia_catastral value for this BienInmueble.
     * 
     * @return referencia_catastral
     */
    public java.lang.String getReferencia_catastral() {
        return referencia_catastral;
    }


    /**
     * Sets the referencia_catastral value for this BienInmueble.
     * 
     * @param referencia_catastral
     */
    public void setReferencia_catastral(java.lang.String referencia_catastral) {
        this.referencia_catastral = referencia_catastral;
    }


    /**
     * Gets the superficie value for this BienInmueble.
     * 
     * @return superficie
     */
    public java.lang.Double getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this BienInmueble.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Double superficie) {
        this.superficie = superficie;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BienInmueble)) return false;
        BienInmueble other = (BienInmueble) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.claseBienInmueble==null && other.getClaseBienInmueble()==null) || 
             (this.claseBienInmueble!=null &&
              this.claseBienInmueble.equals(other.getClaseBienInmueble()))) &&
            ((this.claseUso==null && other.getClaseUso()==null) || 
             (this.claseUso!=null &&
              this.claseUso.equals(other.getClaseUso()))) &&
            ((this.direccionLocalizacion==null && other.getDireccionLocalizacion()==null) || 
             (this.direccionLocalizacion!=null &&
              this.direccionLocalizacion.equals(other.getDireccionLocalizacion()))) &&
            ((this.lstConstrucciones==null && other.getLstConstrucciones()==null) || 
             (this.lstConstrucciones!=null &&
              this.lstConstrucciones.equals(other.getLstConstrucciones()))) &&
            ((this.lstCultivos==null && other.getLstCultivos()==null) || 
             (this.lstCultivos!=null &&
              this.lstCultivos.equals(other.getLstCultivos()))) &&
            ((this.referencia_catastral==null && other.getReferencia_catastral()==null) || 
             (this.referencia_catastral!=null &&
              this.referencia_catastral.equals(other.getReferencia_catastral()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie())));
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
        if (getClaseBienInmueble() != null) {
            _hashCode += getClaseBienInmueble().hashCode();
        }
        if (getClaseUso() != null) {
            _hashCode += getClaseUso().hashCode();
        }
        if (getDireccionLocalizacion() != null) {
            _hashCode += getDireccionLocalizacion().hashCode();
        }
        if (getLstConstrucciones() != null) {
            _hashCode += getLstConstrucciones().hashCode();
        }
        if (getLstCultivos() != null) {
            _hashCode += getLstCultivos().hashCode();
        }
        if (getReferencia_catastral() != null) {
            _hashCode += getReferencia_catastral().hashCode();
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BienInmueble.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "BienInmueble"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claseBienInmueble");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "claseBienInmueble"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claseUso");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "claseUso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direccionLocalizacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "direccionLocalizacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "Localizacion"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lstConstrucciones");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "lstConstrucciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "Construcciones"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lstCultivos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "lstCultivos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "Cultivos"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referencia_catastral");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "referencia_catastral"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficie");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "superficie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
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
