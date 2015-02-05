/**
 * FieldType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas.sharepoint.soap;

public class FieldType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected FieldType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Invalid = "Invalid";
    public static final java.lang.String _Integer = "Integer";
    public static final java.lang.String _Text = "Text";
    public static final java.lang.String _Note = "Note";
    public static final java.lang.String _DateTime = "DateTime";
    public static final java.lang.String _Counter = "Counter";
    public static final java.lang.String _Choice = "Choice";
    public static final java.lang.String _Lookup = "Lookup";
    public static final java.lang.String _Boolean = "Boolean";
    public static final java.lang.String _Number = "Number";
    public static final java.lang.String _Currency = "Currency";
    public static final java.lang.String _URL = "URL";
    public static final java.lang.String _Computed = "Computed";
    public static final java.lang.String _Threading = "Threading";
    public static final java.lang.String _Guid = "Guid";
    public static final java.lang.String _MultiChoice = "MultiChoice";
    public static final java.lang.String _GridChoice = "GridChoice";
    public static final java.lang.String _Calculated = "Calculated";
    public static final java.lang.String _File = "File";
    public static final java.lang.String _Attachments = "Attachments";
    public static final java.lang.String _User = "User";
    public static final java.lang.String _Recurrence = "Recurrence";
    public static final java.lang.String _CrossProjectLink = "CrossProjectLink";
    public static final java.lang.String _ModStat = "ModStat";
    public static final java.lang.String _AllDayEvent = "AllDayEvent";
    public static final java.lang.String _Error = "Error";
    public static final FieldType Invalid = new FieldType(_Invalid);
    public static final FieldType Integer = new FieldType(_Integer);
    public static final FieldType Text = new FieldType(_Text);
    public static final FieldType Note = new FieldType(_Note);
    public static final FieldType DateTime = new FieldType(_DateTime);
    public static final FieldType Counter = new FieldType(_Counter);
    public static final FieldType Choice = new FieldType(_Choice);
    public static final FieldType Lookup = new FieldType(_Lookup);
    public static final FieldType Boolean = new FieldType(_Boolean);
    public static final FieldType Number = new FieldType(_Number);
    public static final FieldType Currency = new FieldType(_Currency);
    public static final FieldType URL = new FieldType(_URL);
    public static final FieldType Computed = new FieldType(_Computed);
    public static final FieldType Threading = new FieldType(_Threading);
    public static final FieldType Guid = new FieldType(_Guid);
    public static final FieldType MultiChoice = new FieldType(_MultiChoice);
    public static final FieldType GridChoice = new FieldType(_GridChoice);
    public static final FieldType Calculated = new FieldType(_Calculated);
    public static final FieldType File = new FieldType(_File);
    public static final FieldType Attachments = new FieldType(_Attachments);
    public static final FieldType User = new FieldType(_User);
    public static final FieldType Recurrence = new FieldType(_Recurrence);
    public static final FieldType CrossProjectLink = new FieldType(_CrossProjectLink);
    public static final FieldType ModStat = new FieldType(_ModStat);
    public static final FieldType AllDayEvent = new FieldType(_AllDayEvent);
    public static final FieldType Error = new FieldType(_Error);
    public java.lang.String getValue() { return _value_;}
    public static FieldType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        FieldType enumeration = (FieldType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static FieldType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(FieldType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "FieldType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
