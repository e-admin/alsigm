/**
 * Titular.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.peticion;

public class Titular  implements java.io.Serializable {
    private es.redsara.intermediacion.scsp.esquemas.ws.peticion.TipoDocumentacion tipoDocumentacion;

    private java.lang.String documentacion;

    private java.lang.String nombreCompleto;

    private java.lang.String nombre;

    private java.lang.String apellido1;

    private java.lang.String apellido2;

    public Titular() {
    }

    public Titular(
           es.redsara.intermediacion.scsp.esquemas.ws.peticion.TipoDocumentacion tipoDocumentacion,
           java.lang.String documentacion,
           java.lang.String nombreCompleto,
           java.lang.String nombre,
           java.lang.String apellido1,
           java.lang.String apellido2) {
           this.tipoDocumentacion = tipoDocumentacion;
           this.documentacion = documentacion;
           this.nombreCompleto = nombreCompleto;
           this.nombre = nombre;
           this.apellido1 = apellido1;
           this.apellido2 = apellido2;
    }


    /**
     * Gets the tipoDocumentacion value for this Titular.
     * 
     * @return tipoDocumentacion
     */
    public es.redsara.intermediacion.scsp.esquemas.ws.peticion.TipoDocumentacion getTipoDocumentacion() {
        return tipoDocumentacion;
    }


    /**
     * Sets the tipoDocumentacion value for this Titular.
     * 
     * @param tipoDocumentacion
     */
    public void setTipoDocumentacion(es.redsara.intermediacion.scsp.esquemas.ws.peticion.TipoDocumentacion tipoDocumentacion) {
        this.tipoDocumentacion = tipoDocumentacion;
    }


    /**
     * Gets the documentacion value for this Titular.
     * 
     * @return documentacion
     */
    public java.lang.String getDocumentacion() {
        return documentacion;
    }


    /**
     * Sets the documentacion value for this Titular.
     * 
     * @param documentacion
     */
    public void setDocumentacion(java.lang.String documentacion) {
        this.documentacion = documentacion;
    }


    /**
     * Gets the nombreCompleto value for this Titular.
     * 
     * @return nombreCompleto
     */
    public java.lang.String getNombreCompleto() {
        return nombreCompleto;
    }


    /**
     * Sets the nombreCompleto value for this Titular.
     * 
     * @param nombreCompleto
     */
    public void setNombreCompleto(java.lang.String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }


    /**
     * Gets the nombre value for this Titular.
     * 
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this Titular.
     * 
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }


    /**
     * Gets the apellido1 value for this Titular.
     * 
     * @return apellido1
     */
    public java.lang.String getApellido1() {
        return apellido1;
    }


    /**
     * Sets the apellido1 value for this Titular.
     * 
     * @param apellido1
     */
    public void setApellido1(java.lang.String apellido1) {
        this.apellido1 = apellido1;
    }


    /**
     * Gets the apellido2 value for this Titular.
     * 
     * @return apellido2
     */
    public java.lang.String getApellido2() {
        return apellido2;
    }


    /**
     * Sets the apellido2 value for this Titular.
     * 
     * @param apellido2
     */
    public void setApellido2(java.lang.String apellido2) {
        this.apellido2 = apellido2;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Titular)) return false;
        Titular other = (Titular) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.tipoDocumentacion==null && other.getTipoDocumentacion()==null) || 
             (this.tipoDocumentacion!=null &&
              this.tipoDocumentacion.equals(other.getTipoDocumentacion()))) &&
            ((this.documentacion==null && other.getDocumentacion()==null) || 
             (this.documentacion!=null &&
              this.documentacion.equals(other.getDocumentacion()))) &&
            ((this.nombreCompleto==null && other.getNombreCompleto()==null) || 
             (this.nombreCompleto!=null &&
              this.nombreCompleto.equals(other.getNombreCompleto()))) &&
            ((this.nombre==null && other.getNombre()==null) || 
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre()))) &&
            ((this.apellido1==null && other.getApellido1()==null) || 
             (this.apellido1!=null &&
              this.apellido1.equals(other.getApellido1()))) &&
            ((this.apellido2==null && other.getApellido2()==null) || 
             (this.apellido2!=null &&
              this.apellido2.equals(other.getApellido2())));
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
        if (getTipoDocumentacion() != null) {
            _hashCode += getTipoDocumentacion().hashCode();
        }
        if (getDocumentacion() != null) {
            _hashCode += getDocumentacion().hashCode();
        }
        if (getNombreCompleto() != null) {
            _hashCode += getNombreCompleto().hashCode();
        }
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        if (getApellido1() != null) {
            _hashCode += getApellido1().hashCode();
        }
        if (getApellido2() != null) {
            _hashCode += getApellido2().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Titular.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">Titular"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumentacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "TipoDocumentacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", ">TipoDocumentacion"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "Documentacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreCompleto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "NombreCompleto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "Nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("apellido1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "Apellido1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("apellido2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/peticion", "Apellido2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
