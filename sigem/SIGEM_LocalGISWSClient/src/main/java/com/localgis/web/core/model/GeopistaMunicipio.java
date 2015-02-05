/**
 * GeopistaMunicipio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.web.core.model;

public class GeopistaMunicipio  implements java.io.Serializable {
    private java.math.BigDecimal area;

    private java.lang.Integer id;

    private java.lang.String idCatastro;

    private java.lang.String idIne;

    private java.lang.String idMhacienda;

    private java.lang.String idProvincia;

    private java.math.BigDecimal length;

    private java.lang.String nombrecooficial;

    private java.lang.String nombreoficial;

    private java.lang.String nombreoficialcorto;

    private java.lang.String srid;

    public GeopistaMunicipio() {
    }

    public GeopistaMunicipio(
           java.math.BigDecimal area,
           java.lang.Integer id,
           java.lang.String idCatastro,
           java.lang.String idIne,
           java.lang.String idMhacienda,
           java.lang.String idProvincia,
           java.math.BigDecimal length,
           java.lang.String nombrecooficial,
           java.lang.String nombreoficial,
           java.lang.String nombreoficialcorto,
           java.lang.String srid) {
           this.area = area;
           this.id = id;
           this.idCatastro = idCatastro;
           this.idIne = idIne;
           this.idMhacienda = idMhacienda;
           this.idProvincia = idProvincia;
           this.length = length;
           this.nombrecooficial = nombrecooficial;
           this.nombreoficial = nombreoficial;
           this.nombreoficialcorto = nombreoficialcorto;
           this.srid = srid;
    }


    /**
     * Gets the area value for this GeopistaMunicipio.
     * 
     * @return area
     */
    public java.math.BigDecimal getArea() {
        return area;
    }


    /**
     * Sets the area value for this GeopistaMunicipio.
     * 
     * @param area
     */
    public void setArea(java.math.BigDecimal area) {
        this.area = area;
    }


    /**
     * Gets the id value for this GeopistaMunicipio.
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }


    /**
     * Sets the id value for this GeopistaMunicipio.
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }


    /**
     * Gets the idCatastro value for this GeopistaMunicipio.
     * 
     * @return idCatastro
     */
    public java.lang.String getIdCatastro() {
        return idCatastro;
    }


    /**
     * Sets the idCatastro value for this GeopistaMunicipio.
     * 
     * @param idCatastro
     */
    public void setIdCatastro(java.lang.String idCatastro) {
        this.idCatastro = idCatastro;
    }


    /**
     * Gets the idIne value for this GeopistaMunicipio.
     * 
     * @return idIne
     */
    public java.lang.String getIdIne() {
        return idIne;
    }


    /**
     * Sets the idIne value for this GeopistaMunicipio.
     * 
     * @param idIne
     */
    public void setIdIne(java.lang.String idIne) {
        this.idIne = idIne;
    }


    /**
     * Gets the idMhacienda value for this GeopistaMunicipio.
     * 
     * @return idMhacienda
     */
    public java.lang.String getIdMhacienda() {
        return idMhacienda;
    }


    /**
     * Sets the idMhacienda value for this GeopistaMunicipio.
     * 
     * @param idMhacienda
     */
    public void setIdMhacienda(java.lang.String idMhacienda) {
        this.idMhacienda = idMhacienda;
    }


    /**
     * Gets the idProvincia value for this GeopistaMunicipio.
     * 
     * @return idProvincia
     */
    public java.lang.String getIdProvincia() {
        return idProvincia;
    }


    /**
     * Sets the idProvincia value for this GeopistaMunicipio.
     * 
     * @param idProvincia
     */
    public void setIdProvincia(java.lang.String idProvincia) {
        this.idProvincia = idProvincia;
    }


    /**
     * Gets the length value for this GeopistaMunicipio.
     * 
     * @return length
     */
    public java.math.BigDecimal getLength() {
        return length;
    }


    /**
     * Sets the length value for this GeopistaMunicipio.
     * 
     * @param length
     */
    public void setLength(java.math.BigDecimal length) {
        this.length = length;
    }


    /**
     * Gets the nombrecooficial value for this GeopistaMunicipio.
     * 
     * @return nombrecooficial
     */
    public java.lang.String getNombrecooficial() {
        return nombrecooficial;
    }


    /**
     * Sets the nombrecooficial value for this GeopistaMunicipio.
     * 
     * @param nombrecooficial
     */
    public void setNombrecooficial(java.lang.String nombrecooficial) {
        this.nombrecooficial = nombrecooficial;
    }


    /**
     * Gets the nombreoficial value for this GeopistaMunicipio.
     * 
     * @return nombreoficial
     */
    public java.lang.String getNombreoficial() {
        return nombreoficial;
    }


    /**
     * Sets the nombreoficial value for this GeopistaMunicipio.
     * 
     * @param nombreoficial
     */
    public void setNombreoficial(java.lang.String nombreoficial) {
        this.nombreoficial = nombreoficial;
    }


    /**
     * Gets the nombreoficialcorto value for this GeopistaMunicipio.
     * 
     * @return nombreoficialcorto
     */
    public java.lang.String getNombreoficialcorto() {
        return nombreoficialcorto;
    }


    /**
     * Sets the nombreoficialcorto value for this GeopistaMunicipio.
     * 
     * @param nombreoficialcorto
     */
    public void setNombreoficialcorto(java.lang.String nombreoficialcorto) {
        this.nombreoficialcorto = nombreoficialcorto;
    }


    /**
     * Gets the srid value for this GeopistaMunicipio.
     * 
     * @return srid
     */
    public java.lang.String getSrid() {
        return srid;
    }


    /**
     * Sets the srid value for this GeopistaMunicipio.
     * 
     * @param srid
     */
    public void setSrid(java.lang.String srid) {
        this.srid = srid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GeopistaMunicipio)) return false;
        GeopistaMunicipio other = (GeopistaMunicipio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.area==null && other.getArea()==null) || 
             (this.area!=null &&
              this.area.equals(other.getArea()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.idCatastro==null && other.getIdCatastro()==null) || 
             (this.idCatastro!=null &&
              this.idCatastro.equals(other.getIdCatastro()))) &&
            ((this.idIne==null && other.getIdIne()==null) || 
             (this.idIne!=null &&
              this.idIne.equals(other.getIdIne()))) &&
            ((this.idMhacienda==null && other.getIdMhacienda()==null) || 
             (this.idMhacienda!=null &&
              this.idMhacienda.equals(other.getIdMhacienda()))) &&
            ((this.idProvincia==null && other.getIdProvincia()==null) || 
             (this.idProvincia!=null &&
              this.idProvincia.equals(other.getIdProvincia()))) &&
            ((this.length==null && other.getLength()==null) || 
             (this.length!=null &&
              this.length.equals(other.getLength()))) &&
            ((this.nombrecooficial==null && other.getNombrecooficial()==null) || 
             (this.nombrecooficial!=null &&
              this.nombrecooficial.equals(other.getNombrecooficial()))) &&
            ((this.nombreoficial==null && other.getNombreoficial()==null) || 
             (this.nombreoficial!=null &&
              this.nombreoficial.equals(other.getNombreoficial()))) &&
            ((this.nombreoficialcorto==null && other.getNombreoficialcorto()==null) || 
             (this.nombreoficialcorto!=null &&
              this.nombreoficialcorto.equals(other.getNombreoficialcorto()))) &&
            ((this.srid==null && other.getSrid()==null) || 
             (this.srid!=null &&
              this.srid.equals(other.getSrid())));
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
        if (getArea() != null) {
            _hashCode += getArea().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getIdCatastro() != null) {
            _hashCode += getIdCatastro().hashCode();
        }
        if (getIdIne() != null) {
            _hashCode += getIdIne().hashCode();
        }
        if (getIdMhacienda() != null) {
            _hashCode += getIdMhacienda().hashCode();
        }
        if (getIdProvincia() != null) {
            _hashCode += getIdProvincia().hashCode();
        }
        if (getLength() != null) {
            _hashCode += getLength().hashCode();
        }
        if (getNombrecooficial() != null) {
            _hashCode += getNombrecooficial().hashCode();
        }
        if (getNombreoficial() != null) {
            _hashCode += getNombreoficial().hashCode();
        }
        if (getNombreoficialcorto() != null) {
            _hashCode += getNombreoficialcorto().hashCode();
        }
        if (getSrid() != null) {
            _hashCode += getSrid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GeopistaMunicipio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "GeopistaMunicipio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("area");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "area"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCatastro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "idCatastro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idIne");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "idIne"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMhacienda");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "idMhacienda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idProvincia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "idProvincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("length");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "length"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombrecooficial");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "nombrecooficial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreoficial");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "nombreoficial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreoficialcorto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "nombreoficialcorto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("srid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "srid"));
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
