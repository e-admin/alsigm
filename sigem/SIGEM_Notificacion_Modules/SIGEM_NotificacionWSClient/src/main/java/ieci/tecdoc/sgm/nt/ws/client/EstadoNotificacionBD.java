/**
 * EstadoNotificacionBD.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.nt.ws.client;

public class EstadoNotificacionBD  extends ieci.tecdoc.sgm.nt.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String descripcion;

    private java.lang.String id;

    private java.lang.String idSisnot;

    public EstadoNotificacionBD() {
    }

    public EstadoNotificacionBD(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String descripcion,
           java.lang.String id,
           java.lang.String idSisnot) {
        super(
            errorCode,
            returnCode);
        this.descripcion = descripcion;
        this.id = id;
        this.idSisnot = idSisnot;
    }


    /**
     * Gets the descripcion value for this EstadoNotificacionBD.
     * 
     * @return descripcion
     */
    public java.lang.String getDescripcion() {
        return descripcion;
    }


    /**
     * Sets the descripcion value for this EstadoNotificacionBD.
     * 
     * @param descripcion
     */
    public void setDescripcion(java.lang.String descripcion) {
        this.descripcion = descripcion;
    }


    /**
     * Gets the id value for this EstadoNotificacionBD.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this EstadoNotificacionBD.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the idSisnot value for this EstadoNotificacionBD.
     * 
     * @return idSisnot
     */
    public java.lang.String getIdSisnot() {
        return idSisnot;
    }


    /**
     * Sets the idSisnot value for this EstadoNotificacionBD.
     * 
     * @param idSisnot
     */
    public void setIdSisnot(java.lang.String idSisnot) {
        this.idSisnot = idSisnot;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EstadoNotificacionBD)) return false;
        EstadoNotificacionBD other = (EstadoNotificacionBD) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.descripcion==null && other.getDescripcion()==null) || 
             (this.descripcion!=null &&
              this.descripcion.equals(other.getDescripcion()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.idSisnot==null && other.getIdSisnot()==null) || 
             (this.idSisnot!=null &&
              this.idSisnot.equals(other.getIdSisnot())));
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
        if (getDescripcion() != null) {
            _hashCode += getDescripcion().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getIdSisnot() != null) {
            _hashCode += getIdSisnot().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EstadoNotificacionBD.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "EstadoNotificacionBD"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "descripcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSisnot");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "idSisnot"));
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
