/**
 * ArchiveFld.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public class ArchiveFld  extends ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private java.lang.String colName;

    private boolean doc;

    private int id;

    private int len;

    private boolean mult;

    private java.lang.String name;

    private boolean nullable;

    private java.lang.String remarks;

    private int type;

    public ArchiveFld() {
    }

    public ArchiveFld(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String colName,
           boolean doc,
           int id,
           int len,
           boolean mult,
           java.lang.String name,
           boolean nullable,
           java.lang.String remarks,
           int type) {
        super(
            errorCode,
            returnCode);
        this.colName = colName;
        this.doc = doc;
        this.id = id;
        this.len = len;
        this.mult = mult;
        this.name = name;
        this.nullable = nullable;
        this.remarks = remarks;
        this.type = type;
    }


    /**
     * Gets the colName value for this ArchiveFld.
     * 
     * @return colName
     */
    public java.lang.String getColName() {
        return colName;
    }


    /**
     * Sets the colName value for this ArchiveFld.
     * 
     * @param colName
     */
    public void setColName(java.lang.String colName) {
        this.colName = colName;
    }


    /**
     * Gets the doc value for this ArchiveFld.
     * 
     * @return doc
     */
    public boolean isDoc() {
        return doc;
    }


    /**
     * Sets the doc value for this ArchiveFld.
     * 
     * @param doc
     */
    public void setDoc(boolean doc) {
        this.doc = doc;
    }


    /**
     * Gets the id value for this ArchiveFld.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this ArchiveFld.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the len value for this ArchiveFld.
     * 
     * @return len
     */
    public int getLen() {
        return len;
    }


    /**
     * Sets the len value for this ArchiveFld.
     * 
     * @param len
     */
    public void setLen(int len) {
        this.len = len;
    }


    /**
     * Gets the mult value for this ArchiveFld.
     * 
     * @return mult
     */
    public boolean isMult() {
        return mult;
    }


    /**
     * Sets the mult value for this ArchiveFld.
     * 
     * @param mult
     */
    public void setMult(boolean mult) {
        this.mult = mult;
    }


    /**
     * Gets the name value for this ArchiveFld.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this ArchiveFld.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the nullable value for this ArchiveFld.
     * 
     * @return nullable
     */
    public boolean isNullable() {
        return nullable;
    }


    /**
     * Sets the nullable value for this ArchiveFld.
     * 
     * @param nullable
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }


    /**
     * Gets the remarks value for this ArchiveFld.
     * 
     * @return remarks
     */
    public java.lang.String getRemarks() {
        return remarks;
    }


    /**
     * Sets the remarks value for this ArchiveFld.
     * 
     * @param remarks
     */
    public void setRemarks(java.lang.String remarks) {
        this.remarks = remarks;
    }


    /**
     * Gets the type value for this ArchiveFld.
     * 
     * @return type
     */
    public int getType() {
        return type;
    }


    /**
     * Sets the type value for this ArchiveFld.
     * 
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArchiveFld)) return false;
        ArchiveFld other = (ArchiveFld) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.colName==null && other.getColName()==null) || 
             (this.colName!=null &&
              this.colName.equals(other.getColName()))) &&
            this.doc == other.isDoc() &&
            this.id == other.getId() &&
            this.len == other.getLen() &&
            this.mult == other.isMult() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            this.nullable == other.isNullable() &&
            ((this.remarks==null && other.getRemarks()==null) || 
             (this.remarks!=null &&
              this.remarks.equals(other.getRemarks()))) &&
            this.type == other.getType();
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
        if (getColName() != null) {
            _hashCode += getColName().hashCode();
        }
        _hashCode += (isDoc() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getId();
        _hashCode += getLen();
        _hashCode += (isMult() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        _hashCode += (isNullable() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getRemarks() != null) {
            _hashCode += getRemarks().hashCode();
        }
        _hashCode += getType();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ArchiveFld.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArchiveFld"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("colName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "colName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("doc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "doc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("len");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "len"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "mult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nullable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "nullable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remarks");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "remarks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
