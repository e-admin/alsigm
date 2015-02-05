package ieci.tecdoc.sgm.tram.ws.client.dto;

import java.io.Serializable;
import java.util.Date;

public class OrganoProductor  implements Serializable {
	
    private String id;

    private Date inicioProduccion;

    public OrganoProductor() {
    }

    public OrganoProductor(
           String id,
           Date inicioProduccion) {
           this.id = id;
           this.inicioProduccion = inicioProduccion;
    }


    /**
     * Gets the id value for this OrganoProductor.
     * 
     * @return id
     */
    public String getId() {
        return id;
    }


    /**
     * Sets the id value for this OrganoProductor.
     * 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * Gets the inicioProduccion value for this OrganoProductor.
     * 
     * @return inicioProduccion
     */
    public Date getInicioProduccion() {
        return inicioProduccion;
    }


    /**
     * Sets the inicioProduccion value for this OrganoProductor.
     * 
     * @param inicioProduccion
     */
    public void setInicioProduccion(Date inicioProduccion) {
        this.inicioProduccion = inicioProduccion;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof OrganoProductor)) return false;
        OrganoProductor other = (OrganoProductor) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.inicioProduccion==null && other.getInicioProduccion()==null) || 
             (this.inicioProduccion!=null &&
              this.inicioProduccion.equals(other.getInicioProduccion())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getInicioProduccion() != null) {
            _hashCode += getInicioProduccion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrganoProductor.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "OrganoProductor"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inicioProduccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "inicioProduccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
