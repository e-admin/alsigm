/**
 * UsuarioLdap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public class UsuarioLdap  extends ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private int _id;

    private java.lang.String _ldapfullname;

    private java.lang.String _ldapguid;

    private ieci.tecdoc.sgm.estructura.ws.client.axis.PermisosGenericos _permsImpl;

    private ieci.tecdoc.sgm.estructura.ws.client.axis.PerfilesGenericos _profilesImpl;

    public UsuarioLdap() {
    }

    public UsuarioLdap(
           java.lang.String errorCode,
           java.lang.String returnCode,
           int _id,
           java.lang.String _ldapfullname,
           java.lang.String _ldapguid,
           ieci.tecdoc.sgm.estructura.ws.client.axis.PermisosGenericos _permsImpl,
           ieci.tecdoc.sgm.estructura.ws.client.axis.PerfilesGenericos _profilesImpl) {
        super(
            errorCode,
            returnCode);
        this._id = _id;
        this._ldapfullname = _ldapfullname;
        this._ldapguid = _ldapguid;
        this._permsImpl = _permsImpl;
        this._profilesImpl = _profilesImpl;
    }


    /**
     * Gets the _id value for this UsuarioLdap.
     * 
     * @return _id
     */
    public int get_id() {
        return _id;
    }


    /**
     * Sets the _id value for this UsuarioLdap.
     * 
     * @param _id
     */
    public void set_id(int _id) {
        this._id = _id;
    }


    /**
     * Gets the _ldapfullname value for this UsuarioLdap.
     * 
     * @return _ldapfullname
     */
    public java.lang.String get_ldapfullname() {
        return _ldapfullname;
    }


    /**
     * Sets the _ldapfullname value for this UsuarioLdap.
     * 
     * @param _ldapfullname
     */
    public void set_ldapfullname(java.lang.String _ldapfullname) {
        this._ldapfullname = _ldapfullname;
    }


    /**
     * Gets the _ldapguid value for this UsuarioLdap.
     * 
     * @return _ldapguid
     */
    public java.lang.String get_ldapguid() {
        return _ldapguid;
    }


    /**
     * Sets the _ldapguid value for this UsuarioLdap.
     * 
     * @param _ldapguid
     */
    public void set_ldapguid(java.lang.String _ldapguid) {
        this._ldapguid = _ldapguid;
    }


    /**
     * Gets the _permsImpl value for this UsuarioLdap.
     * 
     * @return _permsImpl
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.PermisosGenericos get_permsImpl() {
        return _permsImpl;
    }


    /**
     * Sets the _permsImpl value for this UsuarioLdap.
     * 
     * @param _permsImpl
     */
    public void set_permsImpl(ieci.tecdoc.sgm.estructura.ws.client.axis.PermisosGenericos _permsImpl) {
        this._permsImpl = _permsImpl;
    }


    /**
     * Gets the _profilesImpl value for this UsuarioLdap.
     * 
     * @return _profilesImpl
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.PerfilesGenericos get_profilesImpl() {
        return _profilesImpl;
    }


    /**
     * Sets the _profilesImpl value for this UsuarioLdap.
     * 
     * @param _profilesImpl
     */
    public void set_profilesImpl(ieci.tecdoc.sgm.estructura.ws.client.axis.PerfilesGenericos _profilesImpl) {
        this._profilesImpl = _profilesImpl;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UsuarioLdap)) return false;
        UsuarioLdap other = (UsuarioLdap) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this._id == other.get_id() &&
            ((this._ldapfullname==null && other.get_ldapfullname()==null) || 
             (this._ldapfullname!=null &&
              this._ldapfullname.equals(other.get_ldapfullname()))) &&
            ((this._ldapguid==null && other.get_ldapguid()==null) || 
             (this._ldapguid!=null &&
              this._ldapguid.equals(other.get_ldapguid()))) &&
            ((this._permsImpl==null && other.get_permsImpl()==null) || 
             (this._permsImpl!=null &&
              this._permsImpl.equals(other.get_permsImpl()))) &&
            ((this._profilesImpl==null && other.get_profilesImpl()==null) || 
             (this._profilesImpl!=null &&
              this._profilesImpl.equals(other.get_profilesImpl())));
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
        _hashCode += get_id();
        if (get_ldapfullname() != null) {
            _hashCode += get_ldapfullname().hashCode();
        }
        if (get_ldapguid() != null) {
            _hashCode += get_ldapguid().hashCode();
        }
        if (get_permsImpl() != null) {
            _hashCode += get_permsImpl().hashCode();
        }
        if (get_profilesImpl() != null) {
            _hashCode += get_profilesImpl().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UsuarioLdap.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuarioLdap"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ldapfullname");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "_ldapfullname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ldapguid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "_ldapguid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_permsImpl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "_permsImpl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "PermisosGenericos"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_profilesImpl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "_profilesImpl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "PerfilesGenericos"));
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
