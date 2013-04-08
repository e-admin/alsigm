/**
 * ObjectType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public class ObjectType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ObjectType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _VirtualServer = "VirtualServer";
    public static final java.lang.String _ContentDatabase = "ContentDatabase";
    public static final java.lang.String _SiteCollection = "SiteCollection";
    public static final java.lang.String _Site = "Site";
    public static final java.lang.String _List = "List";
    public static final java.lang.String _Folder = "Folder";
    public static final java.lang.String _ListItem = "ListItem";
    public static final java.lang.String _ListItemAttachments = "ListItemAttachments";
    public static final ObjectType VirtualServer = new ObjectType(_VirtualServer);
    public static final ObjectType ContentDatabase = new ObjectType(_ContentDatabase);
    public static final ObjectType SiteCollection = new ObjectType(_SiteCollection);
    public static final ObjectType Site = new ObjectType(_Site);
    public static final ObjectType List = new ObjectType(_List);
    public static final ObjectType Folder = new ObjectType(_Folder);
    public static final ObjectType ListItem = new ObjectType(_ListItem);
    public static final ObjectType ListItemAttachments = new ObjectType(_ListItemAttachments);
    public java.lang.String getValue() { return _value_;}
    public static ObjectType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ObjectType enumeration = (ObjectType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ObjectType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ObjectType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "ObjectType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
