/**
 * Parcela.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.catastro.ws.client;

public class Parcela  extends ieci.tecdoc.sgm.catastro.ws.client.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.catastro.ws.client.Localizacion direccion;

    private ieci.tecdoc.sgm.catastro.ws.client.BienesInmuebles lstBienesInmuebles;

    private java.lang.String refCatastral;

    private java.lang.Double superficie;

    private java.lang.Double superficieConstruida;

    public Parcela() {
    }

    public Parcela(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.catastro.ws.client.Localizacion direccion,
           ieci.tecdoc.sgm.catastro.ws.client.BienesInmuebles lstBienesInmuebles,
           java.lang.String refCatastral,
           java.lang.Double superficie,
           java.lang.Double superficieConstruida) {
        super(
            errorCode,
            returnCode);
        this.direccion = direccion;
        this.lstBienesInmuebles = lstBienesInmuebles;
        this.refCatastral = refCatastral;
        this.superficie = superficie;
        this.superficieConstruida = superficieConstruida;
    }


    /**
     * Gets the direccion value for this Parcela.
     * 
     * @return direccion
     */
    public ieci.tecdoc.sgm.catastro.ws.client.Localizacion getDireccion() {
        return direccion;
    }


    /**
     * Sets the direccion value for this Parcela.
     * 
     * @param direccion
     */
    public void setDireccion(ieci.tecdoc.sgm.catastro.ws.client.Localizacion direccion) {
        this.direccion = direccion;
    }


    /**
     * Gets the lstBienesInmuebles value for this Parcela.
     * 
     * @return lstBienesInmuebles
     */
    public ieci.tecdoc.sgm.catastro.ws.client.BienesInmuebles getLstBienesInmuebles() {
        return lstBienesInmuebles;
    }


    /**
     * Sets the lstBienesInmuebles value for this Parcela.
     * 
     * @param lstBienesInmuebles
     */
    public void setLstBienesInmuebles(ieci.tecdoc.sgm.catastro.ws.client.BienesInmuebles lstBienesInmuebles) {
        this.lstBienesInmuebles = lstBienesInmuebles;
    }


    /**
     * Gets the refCatastral value for this Parcela.
     * 
     * @return refCatastral
     */
    public java.lang.String getRefCatastral() {
        return refCatastral;
    }


    /**
     * Sets the refCatastral value for this Parcela.
     * 
     * @param refCatastral
     */
    public void setRefCatastral(java.lang.String refCatastral) {
        this.refCatastral = refCatastral;
    }


    /**
     * Gets the superficie value for this Parcela.
     * 
     * @return superficie
     */
    public java.lang.Double getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this Parcela.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Double superficie) {
        this.superficie = superficie;
    }


    /**
     * Gets the superficieConstruida value for this Parcela.
     * 
     * @return superficieConstruida
     */
    public java.lang.Double getSuperficieConstruida() {
        return superficieConstruida;
    }


    /**
     * Sets the superficieConstruida value for this Parcela.
     * 
     * @param superficieConstruida
     */
    public void setSuperficieConstruida(java.lang.Double superficieConstruida) {
        this.superficieConstruida = superficieConstruida;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Parcela)) return false;
        Parcela other = (Parcela) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.direccion==null && other.getDireccion()==null) || 
             (this.direccion!=null &&
              this.direccion.equals(other.getDireccion()))) &&
            ((this.lstBienesInmuebles==null && other.getLstBienesInmuebles()==null) || 
             (this.lstBienesInmuebles!=null &&
              this.lstBienesInmuebles.equals(other.getLstBienesInmuebles()))) &&
            ((this.refCatastral==null && other.getRefCatastral()==null) || 
             (this.refCatastral!=null &&
              this.refCatastral.equals(other.getRefCatastral()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie()))) &&
            ((this.superficieConstruida==null && other.getSuperficieConstruida()==null) || 
             (this.superficieConstruida!=null &&
              this.superficieConstruida.equals(other.getSuperficieConstruida())));
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
        if (getDireccion() != null) {
            _hashCode += getDireccion().hashCode();
        }
        if (getLstBienesInmuebles() != null) {
            _hashCode += getLstBienesInmuebles().hashCode();
        }
        if (getRefCatastral() != null) {
            _hashCode += getRefCatastral().hashCode();
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        if (getSuperficieConstruida() != null) {
            _hashCode += getSuperficieConstruida().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Parcela.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "Parcela"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "direccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "Localizacion"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lstBienesInmuebles");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "lstBienesInmuebles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "BienesInmuebles"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refCatastral");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "refCatastral"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficie");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "superficie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieConstruida");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "superficieConstruida"));
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
