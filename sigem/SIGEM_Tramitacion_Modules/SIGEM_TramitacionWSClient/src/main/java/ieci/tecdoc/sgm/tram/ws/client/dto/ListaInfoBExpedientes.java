package ieci.tecdoc.sgm.tram.ws.client.dto;

import java.io.Serializable;
import java.lang.reflect.Array;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ListaInfoBExpedientes  extends RetornoServicio  implements Serializable {
    private InfoBExpediente[] expedientes;

    public ListaInfoBExpedientes() {
    }

    public ListaInfoBExpedientes(
           String errorCode,
           String returnCode,
           InfoBExpediente[] expedientes) {
        setErrorCode(errorCode);
        setReturnCode(returnCode);
        this.expedientes = expedientes;
    }


    /**
     * Gets the expedientes value for this ListaInfoBExpedientes.
     * 
     * @return expedientes
     */
    public InfoBExpediente[] getExpedientes() {
        return expedientes;
    }


    /**
     * Sets the expedientes value for this ListaInfoBExpedientes.
     * 
     * @param expedientes
     */
    public void setExpedientes(InfoBExpediente[] expedientes) {
        this.expedientes = expedientes;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ListaInfoBExpedientes)) return false;
        ListaInfoBExpedientes other = (ListaInfoBExpedientes) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.expedientes==null && other.getExpedientes()==null) || 
             (this.expedientes!=null &&
              java.util.Arrays.equals(this.expedientes, other.getExpedientes())));
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
        if (getExpedientes() != null) {
            for (int i=0; i<Array.getLength(getExpedientes()); i++) {
                Object obj = Array.get(getExpedientes(), i);
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
        new org.apache.axis.description.TypeDesc(ListaInfoBExpedientes.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "ListaInfoBExpedientes"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expedientes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "expedientes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "InfoBExpediente"));
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
