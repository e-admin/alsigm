/**
 * GrupoLdap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public class GrupoLdap  extends ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private java.lang.String _fullname;

    private java.lang.String _guid;

    private int _id;

    private int _type;

    public GrupoLdap() {
    }

    public GrupoLdap(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String _fullname,
           java.lang.String _guid,
           int _id,
           int _type) {
        super(
            errorCode,
            returnCode);
        this._fullname = _fullname;
        this._guid = _guid;
        this._id = _id;
        this._type = _type;
    }


    /**
     * Gets the _fullname value for this GrupoLdap.
     * 
     * @return _fullname
     */
    public java.lang.String get_fullname() {
        return _fullname;
    }


    /**
     * Sets the _fullname value for this GrupoLdap.
     * 
     * @param _fullname
     */
    public void set_fullname(java.lang.String _fullname) {
        this._fullname = _fullname;
    }


    /**
     * Gets the _guid value for this GrupoLdap.
     * 
     * @return _guid
     */
    public java.lang.String get_guid() {
        return _guid;
    }


    /**
     * Sets the _guid value for this GrupoLdap.
     * 
     * @param _guid
     */
    public void set_guid(java.lang.String _guid) {
        this._guid = _guid;
    }


    /**
     * Gets the _id value for this GrupoLdap.
     * 
     * @return _id
     */
    public int get_id() {
        return _id;
    }


    /**
     * Sets the _id value for this GrupoLdap.
     * 
     * @param _id
     */
    public void set_id(int _id) {
        this._id = _id;
    }


    /**
     * Gets the _type value for this GrupoLdap.
     * 
     * @return _type
     */
    public int get_type() {
        return _type;
    }


    /**
     * Sets the _type value for this GrupoLdap.
     * 
     * @param _type
     */
    public void set_type(int _type) {
        this._type = _type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GrupoLdap)) return false;
        GrupoLdap other = (GrupoLdap) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this._fullname==null && other.get_fullname()==null) || 
             (this._fullname!=null &&
              this._fullname.equals(other.get_fullname()))) &&
            ((this._guid==null && other.get_guid()==null) || 
             (this._guid!=null &&
              this._guid.equals(other.get_guid()))) &&
            this._id == other.get_id() &&
            this._type == other.get_type();
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
        if (get_fullname() != null) {
            _hashCode += get_fullname().hashCode();
        }
        if (get_guid() != null) {
            _hashCode += get_guid().hashCode();
        }
        _hashCode += get_id();
        _hashCode += get_type();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GrupoLdap.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "GrupoLdap"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_fullname");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "_fullname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_guid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "_guid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "_type"));
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
