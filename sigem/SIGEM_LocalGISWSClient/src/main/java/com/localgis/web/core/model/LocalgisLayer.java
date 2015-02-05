/**
 * LocalgisLayer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.web.core.model;

public class LocalgisLayer  implements java.io.Serializable {
    private java.lang.String columnTime;

    private java.lang.String geometrytype;

    private java.lang.Integer layerid;

    private java.lang.Integer layeridgeopista;

    private java.lang.String layername;

    private java.lang.String layerquery;

    private java.lang.String time;

    private java.lang.Boolean versionable;

    private java.lang.Boolean versionable2;

    public LocalgisLayer() {
    }

    public LocalgisLayer(
           java.lang.String columnTime,
           java.lang.String geometrytype,
           java.lang.Integer layerid,
           java.lang.Integer layeridgeopista,
           java.lang.String layername,
           java.lang.String layerquery,
           java.lang.String time,
           java.lang.Boolean versionable,
           java.lang.Boolean versionable2) {
           this.columnTime = columnTime;
           this.geometrytype = geometrytype;
           this.layerid = layerid;
           this.layeridgeopista = layeridgeopista;
           this.layername = layername;
           this.layerquery = layerquery;
           this.time = time;
           this.versionable = versionable;
           this.versionable2 = versionable2;
    }


    /**
     * Gets the columnTime value for this LocalgisLayer.
     * 
     * @return columnTime
     */
    public java.lang.String getColumnTime() {
        return columnTime;
    }


    /**
     * Sets the columnTime value for this LocalgisLayer.
     * 
     * @param columnTime
     */
    public void setColumnTime(java.lang.String columnTime) {
        this.columnTime = columnTime;
    }


    /**
     * Gets the geometrytype value for this LocalgisLayer.
     * 
     * @return geometrytype
     */
    public java.lang.String getGeometrytype() {
        return geometrytype;
    }


    /**
     * Sets the geometrytype value for this LocalgisLayer.
     * 
     * @param geometrytype
     */
    public void setGeometrytype(java.lang.String geometrytype) {
        this.geometrytype = geometrytype;
    }


    /**
     * Gets the layerid value for this LocalgisLayer.
     * 
     * @return layerid
     */
    public java.lang.Integer getLayerid() {
        return layerid;
    }


    /**
     * Sets the layerid value for this LocalgisLayer.
     * 
     * @param layerid
     */
    public void setLayerid(java.lang.Integer layerid) {
        this.layerid = layerid;
    }


    /**
     * Gets the layeridgeopista value for this LocalgisLayer.
     * 
     * @return layeridgeopista
     */
    public java.lang.Integer getLayeridgeopista() {
        return layeridgeopista;
    }


    /**
     * Sets the layeridgeopista value for this LocalgisLayer.
     * 
     * @param layeridgeopista
     */
    public void setLayeridgeopista(java.lang.Integer layeridgeopista) {
        this.layeridgeopista = layeridgeopista;
    }


    /**
     * Gets the layername value for this LocalgisLayer.
     * 
     * @return layername
     */
    public java.lang.String getLayername() {
        return layername;
    }


    /**
     * Sets the layername value for this LocalgisLayer.
     * 
     * @param layername
     */
    public void setLayername(java.lang.String layername) {
        this.layername = layername;
    }


    /**
     * Gets the layerquery value for this LocalgisLayer.
     * 
     * @return layerquery
     */
    public java.lang.String getLayerquery() {
        return layerquery;
    }


    /**
     * Sets the layerquery value for this LocalgisLayer.
     * 
     * @param layerquery
     */
    public void setLayerquery(java.lang.String layerquery) {
        this.layerquery = layerquery;
    }


    /**
     * Gets the time value for this LocalgisLayer.
     * 
     * @return time
     */
    public java.lang.String getTime() {
        return time;
    }


    /**
     * Sets the time value for this LocalgisLayer.
     * 
     * @param time
     */
    public void setTime(java.lang.String time) {
        this.time = time;
    }


    /**
     * Gets the versionable value for this LocalgisLayer.
     * 
     * @return versionable
     */
    public java.lang.Boolean getVersionable() {
        return versionable;
    }


    /**
     * Sets the versionable value for this LocalgisLayer.
     * 
     * @param versionable
     */
    public void setVersionable(java.lang.Boolean versionable) {
        this.versionable = versionable;
    }


    /**
     * Gets the versionable2 value for this LocalgisLayer.
     * 
     * @return versionable2
     */
    public java.lang.Boolean getVersionable2() {
        return versionable2;
    }


    /**
     * Sets the versionable2 value for this LocalgisLayer.
     * 
     * @param versionable2
     */
    public void setVersionable2(java.lang.Boolean versionable2) {
        this.versionable2 = versionable2;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LocalgisLayer)) return false;
        LocalgisLayer other = (LocalgisLayer) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.columnTime==null && other.getColumnTime()==null) || 
             (this.columnTime!=null &&
              this.columnTime.equals(other.getColumnTime()))) &&
            ((this.geometrytype==null && other.getGeometrytype()==null) || 
             (this.geometrytype!=null &&
              this.geometrytype.equals(other.getGeometrytype()))) &&
            ((this.layerid==null && other.getLayerid()==null) || 
             (this.layerid!=null &&
              this.layerid.equals(other.getLayerid()))) &&
            ((this.layeridgeopista==null && other.getLayeridgeopista()==null) || 
             (this.layeridgeopista!=null &&
              this.layeridgeopista.equals(other.getLayeridgeopista()))) &&
            ((this.layername==null && other.getLayername()==null) || 
             (this.layername!=null &&
              this.layername.equals(other.getLayername()))) &&
            ((this.layerquery==null && other.getLayerquery()==null) || 
             (this.layerquery!=null &&
              this.layerquery.equals(other.getLayerquery()))) &&
            ((this.time==null && other.getTime()==null) || 
             (this.time!=null &&
              this.time.equals(other.getTime()))) &&
            ((this.versionable==null && other.getVersionable()==null) || 
             (this.versionable!=null &&
              this.versionable.equals(other.getVersionable()))) &&
            ((this.versionable2==null && other.getVersionable2()==null) || 
             (this.versionable2!=null &&
              this.versionable2.equals(other.getVersionable2())));
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
        if (getColumnTime() != null) {
            _hashCode += getColumnTime().hashCode();
        }
        if (getGeometrytype() != null) {
            _hashCode += getGeometrytype().hashCode();
        }
        if (getLayerid() != null) {
            _hashCode += getLayerid().hashCode();
        }
        if (getLayeridgeopista() != null) {
            _hashCode += getLayeridgeopista().hashCode();
        }
        if (getLayername() != null) {
            _hashCode += getLayername().hashCode();
        }
        if (getLayerquery() != null) {
            _hashCode += getLayerquery().hashCode();
        }
        if (getTime() != null) {
            _hashCode += getTime().hashCode();
        }
        if (getVersionable() != null) {
            _hashCode += getVersionable().hashCode();
        }
        if (getVersionable2() != null) {
            _hashCode += getVersionable2().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LocalgisLayer.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "LocalgisLayer"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("columnTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "columnTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("geometrytype");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "geometrytype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("layerid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "layerid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("layeridgeopista");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "layeridgeopista"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("layername");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "layername"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("layerquery");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "layerquery"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("time");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("versionable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "versionable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("versionable2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.core.web.localgis.com", "versionable2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
