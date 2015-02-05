package ieci.tdw.ispac.services.ws.client.dto;

import ieci.tdw.ispac.services.dto.RetornoServicio;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Date;

public class InfoFichero  extends RetornoServicio  implements Serializable {
    private Date fechaAlta;

    private Firma[] firmas;

    private String nombre;

    public InfoFichero() {
    }

    public InfoFichero(
           String errorCode,
           String returnCode,
           Date fechaAlta,
           Firma[] firmas,
           String nombre) {
        setErrorCode(errorCode);
        setReturnCode(returnCode);
        this.fechaAlta = fechaAlta;
        this.firmas = firmas;
        this.nombre = nombre;
    }


    /**
     * Gets the fechaAlta value for this InfoFichero.
     * 
     * @return fechaAlta
     */
    public Date getFechaAlta() {
        return fechaAlta;
    }


    /**
     * Sets the fechaAlta value for this InfoFichero.
     * 
     * @param fechaAlta
     */
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }


    /**
     * Gets the firmas value for this InfoFichero.
     * 
     * @return firmas
     */
    public Firma[] getFirmas() {
        return firmas;
    }


    /**
     * Sets the firmas value for this InfoFichero.
     * 
     * @param firmas
     */
    public void setFirmas(Firma[] firmas) {
        this.firmas = firmas;
    }


    /**
     * Gets the nombre value for this InfoFichero.
     * 
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this InfoFichero.
     * 
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof InfoFichero)) return false;
        InfoFichero other = (InfoFichero) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.fechaAlta==null && other.getFechaAlta()==null) || 
             (this.fechaAlta!=null &&
              this.fechaAlta.equals(other.getFechaAlta()))) &&
            ((this.firmas==null && other.getFirmas()==null) || 
             (this.firmas!=null &&
              java.util.Arrays.equals(this.firmas, other.getFirmas()))) &&
            ((this.nombre==null && other.getNombre()==null) || 
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre())));
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
        if (getFechaAlta() != null) {
            _hashCode += getFechaAlta().hashCode();
        }
        if (getFirmas() != null) {
            for (int i=0; i<Array.getLength(getFirmas()); i++) {
                Object obj = Array.get(getFirmas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InfoFichero.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "InfoFichero"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaAlta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "fechaAlta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firmas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "firmas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "Firma"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
