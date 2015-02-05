/**
 * CopyErrorCode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public class CopyErrorCode implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CopyErrorCode(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Success = "Success";
    public static final java.lang.String _DestinationInvalid = "DestinationInvalid";
    public static final java.lang.String _DestinationMWS = "DestinationMWS";
    public static final java.lang.String _SourceInvalid = "SourceInvalid";
    public static final java.lang.String _DestinationCheckedOut = "DestinationCheckedOut";
    public static final java.lang.String _InvalidUrl = "InvalidUrl";
    public static final java.lang.String _Unknown = "Unknown";
    public static final CopyErrorCode Success = new CopyErrorCode(_Success);
    public static final CopyErrorCode DestinationInvalid = new CopyErrorCode(_DestinationInvalid);
    public static final CopyErrorCode DestinationMWS = new CopyErrorCode(_DestinationMWS);
    public static final CopyErrorCode SourceInvalid = new CopyErrorCode(_SourceInvalid);
    public static final CopyErrorCode DestinationCheckedOut = new CopyErrorCode(_DestinationCheckedOut);
    public static final CopyErrorCode InvalidUrl = new CopyErrorCode(_InvalidUrl);
    public static final CopyErrorCode Unknown = new CopyErrorCode(_Unknown);
    public java.lang.String getValue() { return _value_;}
    public static CopyErrorCode fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        CopyErrorCode enumeration = (CopyErrorCode)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static CopyErrorCode fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(CopyErrorCode.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "CopyErrorCode"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
