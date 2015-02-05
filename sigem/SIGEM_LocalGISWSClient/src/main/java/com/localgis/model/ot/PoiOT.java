/**
 * PoiOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.model.ot;

public class PoiOT  implements java.io.Serializable {
    private java.lang.Double coordX;

    private java.lang.Double coordY;

    private java.lang.String direccion;

    private java.lang.Integer idContenido;

    private java.lang.Integer idMunicpio;

    private java.lang.String subtipo;

    private java.lang.String tipo;

    private java.lang.String urlContenido;

    public PoiOT() {
    }

    public PoiOT(
           java.lang.Double coordX,
           java.lang.Double coordY,
           java.lang.String direccion,
           java.lang.Integer idContenido,
           java.lang.Integer idMunicpio,
           java.lang.String subtipo,
           java.lang.String tipo,
           java.lang.String urlContenido) {
           this.coordX = coordX;
           this.coordY = coordY;
           this.direccion = direccion;
           this.idContenido = idContenido;
           this.idMunicpio = idMunicpio;
           this.subtipo = subtipo;
           this.tipo = tipo;
           this.urlContenido = urlContenido;
    }


    /**
     * Gets the coordX value for this PoiOT.
     * 
     * @return coordX
     */
    public java.lang.Double getCoordX() {
        return coordX;
    }


    /**
     * Sets the coordX value for this PoiOT.
     * 
     * @param coordX
     */
    public void setCoordX(java.lang.Double coordX) {
        this.coordX = coordX;
    }


    /**
     * Gets the coordY value for this PoiOT.
     * 
     * @return coordY
     */
    public java.lang.Double getCoordY() {
        return coordY;
    }


    /**
     * Sets the coordY value for this PoiOT.
     * 
     * @param coordY
     */
    public void setCoordY(java.lang.Double coordY) {
        this.coordY = coordY;
    }


    /**
     * Gets the direccion value for this PoiOT.
     * 
     * @return direccion
     */
    public java.lang.String getDireccion() {
        return direccion;
    }


    /**
     * Sets the direccion value for this PoiOT.
     * 
     * @param direccion
     */
    public void setDireccion(java.lang.String direccion) {
        this.direccion = direccion;
    }


    /**
     * Gets the idContenido value for this PoiOT.
     * 
     * @return idContenido
     */
    public java.lang.Integer getIdContenido() {
        return idContenido;
    }


    /**
     * Sets the idContenido value for this PoiOT.
     * 
     * @param idContenido
     */
    public void setIdContenido(java.lang.Integer idContenido) {
        this.idContenido = idContenido;
    }


    /**
     * Gets the idMunicpio value for this PoiOT.
     * 
     * @return idMunicpio
     */
    public java.lang.Integer getIdMunicpio() {
        return idMunicpio;
    }


    /**
     * Sets the idMunicpio value for this PoiOT.
     * 
     * @param idMunicpio
     */
    public void setIdMunicpio(java.lang.Integer idMunicpio) {
        this.idMunicpio = idMunicpio;
    }


    /**
     * Gets the subtipo value for this PoiOT.
     * 
     * @return subtipo
     */
    public java.lang.String getSubtipo() {
        return subtipo;
    }


    /**
     * Sets the subtipo value for this PoiOT.
     * 
     * @param subtipo
     */
    public void setSubtipo(java.lang.String subtipo) {
        this.subtipo = subtipo;
    }


    /**
     * Gets the tipo value for this PoiOT.
     * 
     * @return tipo
     */
    public java.lang.String getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this PoiOT.
     * 
     * @param tipo
     */
    public void setTipo(java.lang.String tipo) {
        this.tipo = tipo;
    }


    /**
     * Gets the urlContenido value for this PoiOT.
     * 
     * @return urlContenido
     */
    public java.lang.String getUrlContenido() {
        return urlContenido;
    }


    /**
     * Sets the urlContenido value for this PoiOT.
     * 
     * @param urlContenido
     */
    public void setUrlContenido(java.lang.String urlContenido) {
        this.urlContenido = urlContenido;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PoiOT)) return false;
        PoiOT other = (PoiOT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.coordX==null && other.getCoordX()==null) || 
             (this.coordX!=null &&
              this.coordX.equals(other.getCoordX()))) &&
            ((this.coordY==null && other.getCoordY()==null) || 
             (this.coordY!=null &&
              this.coordY.equals(other.getCoordY()))) &&
            ((this.direccion==null && other.getDireccion()==null) || 
             (this.direccion!=null &&
              this.direccion.equals(other.getDireccion()))) &&
            ((this.idContenido==null && other.getIdContenido()==null) || 
             (this.idContenido!=null &&
              this.idContenido.equals(other.getIdContenido()))) &&
            ((this.idMunicpio==null && other.getIdMunicpio()==null) || 
             (this.idMunicpio!=null &&
              this.idMunicpio.equals(other.getIdMunicpio()))) &&
            ((this.subtipo==null && other.getSubtipo()==null) || 
             (this.subtipo!=null &&
              this.subtipo.equals(other.getSubtipo()))) &&
            ((this.tipo==null && other.getTipo()==null) || 
             (this.tipo!=null &&
              this.tipo.equals(other.getTipo()))) &&
            ((this.urlContenido==null && other.getUrlContenido()==null) || 
             (this.urlContenido!=null &&
              this.urlContenido.equals(other.getUrlContenido())));
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
        if (getCoordX() != null) {
            _hashCode += getCoordX().hashCode();
        }
        if (getCoordY() != null) {
            _hashCode += getCoordY().hashCode();
        }
        if (getDireccion() != null) {
            _hashCode += getDireccion().hashCode();
        }
        if (getIdContenido() != null) {
            _hashCode += getIdContenido().hashCode();
        }
        if (getIdMunicpio() != null) {
            _hashCode += getIdMunicpio().hashCode();
        }
        if (getSubtipo() != null) {
            _hashCode += getSubtipo().hashCode();
        }
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        if (getUrlContenido() != null) {
            _hashCode += getUrlContenido().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PoiOT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "PoiOT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coordX");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "coordX"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coordY");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "coordY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "direccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idContenido");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "idContenido"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMunicpio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "idMunicpio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subtipo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "subtipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "tipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("urlContenido");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "urlContenido"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
