/**
 * TipoDocumentacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.peticion;

public class TipoDocumentacion implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TipoDocumentacion(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CIF = "CIF";
    public static final java.lang.String _NIF = "NIF";
    public static final java.lang.String _DNI = "DNI";
    public static final java.lang.String _Pasaporte = "Pasaporte";
    public static final java.lang.String _NIE = "NIE";
    public static final TipoDocumentacion CIF = new TipoDocumentacion(_CIF);
    public static final TipoDocumentacion NIF = new TipoDocumentacion(_NIF);
    public static final TipoDocumentacion DNI = new TipoDocumentacion(_DNI);
    public static final TipoDocumentacion Pasaporte = new TipoDocumentacion(_Pasaporte);
    public static final TipoDocumentacion NIE = new TipoDocumentacion(_NIE);
    public java.lang.String getValue() { return _value_;}
    public static TipoDocumentacion fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        TipoDocumentacion enumeration = (TipoDocumentacion)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static TipoDocumentacion fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(TipoDocumentacion.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">TipoDocumentacion"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
