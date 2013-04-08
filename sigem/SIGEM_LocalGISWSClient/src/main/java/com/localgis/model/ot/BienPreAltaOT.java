/**
 * BienPreAltaOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.model.ot;

public class BienPreAltaOT  implements java.io.Serializable {
    private java.lang.Double costeAdquisicion;

    private java.lang.String descripcion;

    private java.lang.String fechaAdquisicion;

    private java.lang.Long id;

    private java.lang.Long idMunicipio;

    private java.lang.String nombre;

    private java.lang.Integer tipo;

    public BienPreAltaOT() {
    }

    public BienPreAltaOT(
           java.lang.Double costeAdquisicion,
           java.lang.String descripcion,
           java.lang.String fechaAdquisicion,
           java.lang.Long id,
           java.lang.Long idMunicipio,
           java.lang.String nombre,
           java.lang.Integer tipo) {
           this.costeAdquisicion = costeAdquisicion;
           this.descripcion = descripcion;
           this.fechaAdquisicion = fechaAdquisicion;
           this.id = id;
           this.idMunicipio = idMunicipio;
           this.nombre = nombre;
           this.tipo = tipo;
    }


    /**
     * Gets the costeAdquisicion value for this BienPreAltaOT.
     * 
     * @return costeAdquisicion
     */
    public java.lang.Double getCosteAdquisicion() {
        return costeAdquisicion;
    }


    /**
     * Sets the costeAdquisicion value for this BienPreAltaOT.
     * 
     * @param costeAdquisicion
     */
    public void setCosteAdquisicion(java.lang.Double costeAdquisicion) {
        this.costeAdquisicion = costeAdquisicion;
    }


    /**
     * Gets the descripcion value for this BienPreAltaOT.
     * 
     * @return descripcion
     */
    public java.lang.String getDescripcion() {
        return descripcion;
    }


    /**
     * Sets the descripcion value for this BienPreAltaOT.
     * 
     * @param descripcion
     */
    public void setDescripcion(java.lang.String descripcion) {
        this.descripcion = descripcion;
    }


    /**
     * Gets the fechaAdquisicion value for this BienPreAltaOT.
     * 
     * @return fechaAdquisicion
     */
    public java.lang.String getFechaAdquisicion() {
        return fechaAdquisicion;
    }


    /**
     * Sets the fechaAdquisicion value for this BienPreAltaOT.
     * 
     * @param fechaAdquisicion
     */
    public void setFechaAdquisicion(java.lang.String fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }


    /**
     * Gets the id value for this BienPreAltaOT.
     * 
     * @return id
     */
    public java.lang.Long getId() {
        return id;
    }


    /**
     * Sets the id value for this BienPreAltaOT.
     * 
     * @param id
     */
    public void setId(java.lang.Long id) {
        this.id = id;
    }


    /**
     * Gets the idMunicipio value for this BienPreAltaOT.
     * 
     * @return idMunicipio
     */
    public java.lang.Long getIdMunicipio() {
        return idMunicipio;
    }


    /**
     * Sets the idMunicipio value for this BienPreAltaOT.
     * 
     * @param idMunicipio
     */
    public void setIdMunicipio(java.lang.Long idMunicipio) {
        this.idMunicipio = idMunicipio;
    }


    /**
     * Gets the nombre value for this BienPreAltaOT.
     * 
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this BienPreAltaOT.
     * 
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }


    /**
     * Gets the tipo value for this BienPreAltaOT.
     * 
     * @return tipo
     */
    public java.lang.Integer getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this BienPreAltaOT.
     * 
     * @param tipo
     */
    public void setTipo(java.lang.Integer tipo) {
        this.tipo = tipo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BienPreAltaOT)) return false;
        BienPreAltaOT other = (BienPreAltaOT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.costeAdquisicion==null && other.getCosteAdquisicion()==null) || 
             (this.costeAdquisicion!=null &&
              this.costeAdquisicion.equals(other.getCosteAdquisicion()))) &&
            ((this.descripcion==null && other.getDescripcion()==null) || 
             (this.descripcion!=null &&
              this.descripcion.equals(other.getDescripcion()))) &&
            ((this.fechaAdquisicion==null && other.getFechaAdquisicion()==null) || 
             (this.fechaAdquisicion!=null &&
              this.fechaAdquisicion.equals(other.getFechaAdquisicion()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.idMunicipio==null && other.getIdMunicipio()==null) || 
             (this.idMunicipio!=null &&
              this.idMunicipio.equals(other.getIdMunicipio()))) &&
            ((this.nombre==null && other.getNombre()==null) || 
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre()))) &&
            ((this.tipo==null && other.getTipo()==null) || 
             (this.tipo!=null &&
              this.tipo.equals(other.getTipo())));
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
        if (getCosteAdquisicion() != null) {
            _hashCode += getCosteAdquisicion().hashCode();
        }
        if (getDescripcion() != null) {
            _hashCode += getDescripcion().hashCode();
        }
        if (getFechaAdquisicion() != null) {
            _hashCode += getFechaAdquisicion().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getIdMunicipio() != null) {
            _hashCode += getIdMunicipio().hashCode();
        }
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BienPreAltaOT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ot.model.localgis.com", "BienPreAltaOT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("costeAdquisicion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "costeAdquisicion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "descripcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaAdquisicion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "fechaAdquisicion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMunicipio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "idMunicipio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ot.model.localgis.com", "tipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
