/**
 * Interesado.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.ct.ws.client;

public class Interesado  extends ieci.tecdoc.sgm.ct.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String NIF;

    private ieci.tecdoc.sgm.ct.ws.client.Expedientes expedientes;

    private java.lang.String informacionAuxiliar;

    private java.lang.String nombre;

    private java.lang.String numeroExpediente;

    private java.lang.String principal;

    public Interesado() {
    }

    public Interesado(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String NIF,
           ieci.tecdoc.sgm.ct.ws.client.Expedientes expedientes,
           java.lang.String informacionAuxiliar,
           java.lang.String nombre,
           java.lang.String numeroExpediente,
           java.lang.String principal) {
        super(
            errorCode,
            returnCode);
        this.NIF = NIF;
        this.expedientes = expedientes;
        this.informacionAuxiliar = informacionAuxiliar;
        this.nombre = nombre;
        this.numeroExpediente = numeroExpediente;
        this.principal = principal;
    }


    /**
     * Gets the NIF value for this Interesado.
     * 
     * @return NIF
     */
    public java.lang.String getNIF() {
        return NIF;
    }


    /**
     * Sets the NIF value for this Interesado.
     * 
     * @param NIF
     */
    public void setNIF(java.lang.String NIF) {
        this.NIF = NIF;
    }


    /**
     * Gets the expedientes value for this Interesado.
     * 
     * @return expedientes
     */
    public ieci.tecdoc.sgm.ct.ws.client.Expedientes getExpedientes() {
        return expedientes;
    }


    /**
     * Sets the expedientes value for this Interesado.
     * 
     * @param expedientes
     */
    public void setExpedientes(ieci.tecdoc.sgm.ct.ws.client.Expedientes expedientes) {
        this.expedientes = expedientes;
    }


    /**
     * Gets the informacionAuxiliar value for this Interesado.
     * 
     * @return informacionAuxiliar
     */
    public java.lang.String getInformacionAuxiliar() {
        return informacionAuxiliar;
    }


    /**
     * Sets the informacionAuxiliar value for this Interesado.
     * 
     * @param informacionAuxiliar
     */
    public void setInformacionAuxiliar(java.lang.String informacionAuxiliar) {
        this.informacionAuxiliar = informacionAuxiliar;
    }


    /**
     * Gets the nombre value for this Interesado.
     * 
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this Interesado.
     * 
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }


    /**
     * Gets the numeroExpediente value for this Interesado.
     * 
     * @return numeroExpediente
     */
    public java.lang.String getNumeroExpediente() {
        return numeroExpediente;
    }


    /**
     * Sets the numeroExpediente value for this Interesado.
     * 
     * @param numeroExpediente
     */
    public void setNumeroExpediente(java.lang.String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }


    /**
     * Gets the principal value for this Interesado.
     * 
     * @return principal
     */
    public java.lang.String getPrincipal() {
        return principal;
    }


    /**
     * Sets the principal value for this Interesado.
     * 
     * @param principal
     */
    public void setPrincipal(java.lang.String principal) {
        this.principal = principal;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Interesado)) return false;
        Interesado other = (Interesado) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.NIF==null && other.getNIF()==null) || 
             (this.NIF!=null &&
              this.NIF.equals(other.getNIF()))) &&
            ((this.expedientes==null && other.getExpedientes()==null) || 
             (this.expedientes!=null &&
              this.expedientes.equals(other.getExpedientes()))) &&
            ((this.informacionAuxiliar==null && other.getInformacionAuxiliar()==null) || 
             (this.informacionAuxiliar!=null &&
              this.informacionAuxiliar.equals(other.getInformacionAuxiliar()))) &&
            ((this.nombre==null && other.getNombre()==null) || 
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre()))) &&
            ((this.numeroExpediente==null && other.getNumeroExpediente()==null) || 
             (this.numeroExpediente!=null &&
              this.numeroExpediente.equals(other.getNumeroExpediente()))) &&
            ((this.principal==null && other.getPrincipal()==null) || 
             (this.principal!=null &&
              this.principal.equals(other.getPrincipal())));
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
        if (getNIF() != null) {
            _hashCode += getNIF().hashCode();
        }
        if (getExpedientes() != null) {
            _hashCode += getExpedientes().hashCode();
        }
        if (getInformacionAuxiliar() != null) {
            _hashCode += getInformacionAuxiliar().hashCode();
        }
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        if (getNumeroExpediente() != null) {
            _hashCode += getNumeroExpediente().hashCode();
        }
        if (getPrincipal() != null) {
            _hashCode += getPrincipal().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Interesado.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Interesado"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NIF");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "NIF"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expedientes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "expedientes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Expedientes"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("informacionAuxiliar");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "informacionAuxiliar"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroExpediente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "numeroExpediente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("principal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "principal"));
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
