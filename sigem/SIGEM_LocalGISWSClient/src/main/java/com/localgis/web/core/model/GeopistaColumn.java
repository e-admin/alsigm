/**
 * GeopistaColumn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.web.core.model;

public class GeopistaColumn  implements java.io.Serializable {
    private java.lang.String alias;

    private java.lang.Short geometryType;

    private java.lang.Integer id;

    private java.lang.Integer idAlias;

    private java.lang.Integer idAttributeGeopista;

    private java.lang.Integer idDomain;

    private java.lang.Integer idTable;

    private java.lang.Short length;

    private java.lang.String name;

    private java.lang.Short precision;

    private java.lang.Short scale;

    private java.lang.String tablename;

    private java.lang.Short type;

    public GeopistaColumn() {
    }

    public GeopistaColumn(
           java.lang.String alias,
           java.lang.Short geometryType,
           java.lang.Integer id,
           java.lang.Integer idAlias,
           java.lang.Integer idAttributeGeopista,
           java.lang.Integer idDomain,
           java.lang.Integer idTable,
           java.lang.Short length,
           java.lang.String name,
           java.lang.Short precision,
           java.lang.Short scale,
           java.lang.String tablename,
           java.lang.Short type) {
           this.alias = alias;
           this.geometryType = geometryType;
           this.id = id;
           this.idAlias = idAlias;
           this.idAttributeGeopista = idAttributeGeopista;
           this.idDomain = idDomain;
           this.idTable = idTable;
           this.length = length;
           this.name = name;
           this.precision = precision;
           this.scale = scale;
           this.tablename = tablename;
           this.type = type;
    }


    /**
     * Gets the alias value for this GeopistaColumn.
     * 
     * @return alias
     */
    public java.lang.String getAlias() {
        return alias;
    }


    /**
     * Sets the alias value for this GeopistaColumn.
     * 
     * @param alias
     */
    public void setAlias(java.lang.String alias) {
        this.alias = alias;
    }


    /**
     * Gets the geometryType value for this GeopistaColumn.
     * 
     * @return geometryType
     */
    public java.lang.Short getGeometryType() {
        return geometryType;
    }


    /**
     * Sets the geometryType value for this GeopistaColumn.
     * 
     * @param geometryType
     */
    public void setGeometryType(java.lang.Short geometryType) {
        this.geometryType = geometryType;
    }


    /**
     * Gets the id value for this GeopistaColumn.
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }


    /**
     * Sets the id value for this GeopistaColumn.
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }


    /**
     * Gets the idAlias value for this GeopistaColumn.
     * 
     * @return idAlias
     */
    public java.lang.Integer getIdAlias() {
        return idAlias;
    }


    /**
     * Sets the idAlias value for this GeopistaColumn.
     * 
     * @param idAlias
     */
    public void setIdAlias(java.lang.Integer idAlias) {
        this.idAlias = idAlias;
    }


    /**
     * Gets the idAttributeGeopista value for this GeopistaColumn.
     * 
     * @return idAttributeGeopista
     */
    public java.lang.Integer getIdAttributeGeopista() {
        return idAttributeGeopista;
    }


    /**
     * Sets the idAttributeGeopista value for this GeopistaColumn.
     * 
     * @param idAttributeGeopista
     */
    public void setIdAttributeGeopista(java.lang.Integer idAttributeGeopista) {
        this.idAttributeGeopista = idAttributeGeopista;
    }


    /**
     * Gets the idDomain value for this GeopistaColumn.
     * 
     * @return idDomain
     */
    public java.lang.Integer getIdDomain() {
        return idDomain;
    }


    /**
     * Sets the idDomain value for this GeopistaColumn.
     * 
     * @param idDomain
     */
    public void setIdDomain(java.lang.Integer idDomain) {
        this.idDomain = idDomain;
    }


    /**
     * Gets the idTable value for this GeopistaColumn.
     * 
     * @return idTable
     */
    public java.lang.Integer getIdTable() {
        return idTable;
    }


    /**
     * Sets the idTable value for this GeopistaColumn.
     * 
     * @param idTable
     */
    public void setIdTable(java.lang.Integer idTable) {
        this.idTable = idTable;
    }


    /**
     * Gets the length value for this GeopistaColumn.
     * 
     * @return length
     */
    public java.lang.Short getLength() {
        return length;
    }


    /**
     * Sets the length value for this GeopistaColumn.
     * 
     * @param length
     */
    public void setLength(java.lang.Short length) {
        this.length = length;
    }


    /**
     * Gets the name value for this GeopistaColumn.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this GeopistaColumn.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the precision value for this GeopistaColumn.
     * 
     * @return precision
     */
    public java.lang.Short getPrecision() {
        return precision;
    }


    /**
     * Sets the precision value for this GeopistaColumn.
     * 
     * @param precision
     */
    public void setPrecision(java.lang.Short precision) {
        this.precision = precision;
    }


    /**
     * Gets the scale value for this GeopistaColumn.
     * 
     * @return scale
     */
    public java.lang.Short getScale() {
        return scale;
    }


    /**
     * Sets the scale value for this GeopistaColumn.
     * 
     * @param scale
     */
    public void setScale(java.lang.Short scale) {
        this.scale = scale;
    }


    /**
     * Gets the tablename value for this GeopistaColumn.
     * 
     * @return tablename
     */
    public java.lang.String getTablename() {
        return tablename;
    }


    /**
     * Sets the tablename value for this GeopistaColumn.
     * 
     * @param tablename
     */
    public void setTablename(java.lang.String tablename) {
        this.tablename = tablename;
    }


    /**
     * Gets the type value for this GeopistaColumn.
     * 
     * @return type
     */
    public java.lang.Short getType() {
        return type;
    }


    /**
     * Sets the type value for this GeopistaColumn.
     * 
     * @param type
     */
    public void setType(java.lang.Short type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GeopistaColumn)) return false;
        GeopistaColumn other = (GeopistaColumn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.alias==null && other.getAlias()==null) || 
             (this.alias!=null &&
              this.alias.equals(other.getAlias()))) &&
            ((this.geometryType==null && other.getGeometryType()==null) || 
             (this.geometryType!=null &&
              this.geometryType.equals(other.getGeometryType()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.idAlias==null && other.getIdAlias()==null) || 
             (this.idAlias!=null &&
              this.idAlias.equals(other.getIdAlias()))) &&
            ((this.idAttributeGeopista==null && other.getIdAttributeGeopista()==null) || 
             (this.idAttributeGeopista!=null &&
              this.idAttributeGeopista.equals(other.getIdAttributeGeopista()))) &&
            ((this.idDomain==null && other.getIdDomain()==null) || 
             (this.idDomain!=null &&
              this.idDomain.equals(other.getIdDomain()))) &&
            ((this.idTable==null && other.getIdTable()==null) || 
             (this.idTable!=null &&
              this.idTable.equals(other.getIdTable()))) &&
            ((this.length==null && other.getLength()==null) || 
             (this.length!=null &&
              this.length.equals(other.getLength()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.precision==null && other.getPrecision()==null) || 
             (this.precision!=null &&
              this.precision.equals(other.getPrecision()))) &&
            ((this.scale==null && other.getScale()==null) || 
             (this.scale!=null &&
              this.scale.equals(other.getScale()))) &&
            ((this.tablename==null && other.getTablename()==null) || 
             (this.tablename!=null &&
              this.tablename.equals(other.getTablename()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType())));
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
        if (getAlias() != null) {
            _hashCode += getAlias().hashCode();
        }
        if (getGeometryType() != null) {
            _hashCode += getGeometryType().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getIdAlias() != null) {
            _hashCode += getIdAlias().hashCode();
        }
        if (getIdAttributeGeopista() != null) {
            _hashCode += getIdAttributeGeopista().hashCode();
        }
        if (getIdDomain() != null) {
            _hashCode += getIdDomain().hashCode();
        }
        if (getIdTable() != null) {
            _hashCode += getIdTable().hashCode();
        }
        if (getLength() != null) {
            _hashCode += getLength().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getPrecision() != null) {
            _hashCode += getPrecision().hashCode();
        }
        if (getScale() != null) {
            _hashCode += getScale().hashCode();
        }
        if (getTablename() != null) {
            _hashCode += getTablename().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GeopistaColumn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "GeopistaColumn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alias");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "alias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("geometryType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "geometryType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
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
        elemField.setFieldName("idAlias");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "idAlias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAttributeGeopista");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "idAttributeGeopista"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDomain");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "idDomain"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "idTable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("length");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "length"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("precision");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "precision"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "scale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tablename");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "tablename"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
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
