package ieci.tecdoc.sgm.tram.ws.client.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.io.Serializable;
import java.lang.reflect.Array;

public class ListaIdentificadores  extends RetornoServicio  implements Serializable {
    private String[] identificadores;

    public ListaIdentificadores() {
    }

    public ListaIdentificadores(
           String errorCode,
           String returnCode,
           String[] identificadores) {
        setErrorCode(errorCode);
        setReturnCode(returnCode);
        this.identificadores = identificadores;
    }


    /**
     * Gets the identificadores value for this ListaIdentificadores.
     * 
     * @return identificadores
     */
    public String[] getIdentificadores() {
        return identificadores;
    }


    /**
     * Sets the identificadores value for this ListaIdentificadores.
     * 
     * @param identificadores
     */
    public void setIdentificadores(String[] identificadores) {
        this.identificadores = identificadores;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ListaIdentificadores)) return false;
        ListaIdentificadores other = (ListaIdentificadores) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.identificadores==null && other.getIdentificadores()==null) || 
             (this.identificadores!=null &&
              java.util.Arrays.equals(this.identificadores, other.getIdentificadores())));
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
        if (getIdentificadores() != null) {
            for (int i=0; i<Array.getLength(getIdentificadores()); i++) {
                Object obj = Array.get(getIdentificadores(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListaIdentificadores.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "ListaIdentificadores"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificadores");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "identificadores"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "item"));
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
           String mechType, 
           Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType, 
           Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
