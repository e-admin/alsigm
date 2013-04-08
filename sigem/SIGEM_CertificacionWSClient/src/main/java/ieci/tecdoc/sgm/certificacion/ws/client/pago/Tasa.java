/**
 * Tasa.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.certificacion.ws.client.pago;

public class Tasa  extends ieci.tecdoc.sgm.certificacion.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String captura;

    private java.lang.String codigo;

    private java.lang.String datosEspecificos;

    private java.lang.String idEntidadEmisora;

    private java.lang.String modelo;

    private java.lang.String nombre;

    private java.lang.String tipo;

    public Tasa() {
    }

    public Tasa(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String captura,
           java.lang.String codigo,
           java.lang.String datosEspecificos,
           java.lang.String idEntidadEmisora,
           java.lang.String modelo,
           java.lang.String nombre,
           java.lang.String tipo) {
        super(
            errorCode,
            returnCode);
        this.captura = captura;
        this.codigo = codigo;
        this.datosEspecificos = datosEspecificos;
        this.idEntidadEmisora = idEntidadEmisora;
        this.modelo = modelo;
        this.nombre = nombre;
        this.tipo = tipo;
    }


    /**
     * Gets the captura value for this Tasa.
     * 
     * @return captura
     */
    public java.lang.String getCaptura() {
        return captura;
    }


    /**
     * Sets the captura value for this Tasa.
     * 
     * @param captura
     */
    public void setCaptura(java.lang.String captura) {
        this.captura = captura;
    }


    /**
     * Gets the codigo value for this Tasa.
     * 
     * @return codigo
     */
    public java.lang.String getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this Tasa.
     * 
     * @param codigo
     */
    public void setCodigo(java.lang.String codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the datosEspecificos value for this Tasa.
     * 
     * @return datosEspecificos
     */
    public java.lang.String getDatosEspecificos() {
        return datosEspecificos;
    }


    /**
     * Sets the datosEspecificos value for this Tasa.
     * 
     * @param datosEspecificos
     */
    public void setDatosEspecificos(java.lang.String datosEspecificos) {
        this.datosEspecificos = datosEspecificos;
    }


    /**
     * Gets the idEntidadEmisora value for this Tasa.
     * 
     * @return idEntidadEmisora
     */
    public java.lang.String getIdEntidadEmisora() {
        return idEntidadEmisora;
    }


    /**
     * Sets the idEntidadEmisora value for this Tasa.
     * 
     * @param idEntidadEmisora
     */
    public void setIdEntidadEmisora(java.lang.String idEntidadEmisora) {
        this.idEntidadEmisora = idEntidadEmisora;
    }


    /**
     * Gets the modelo value for this Tasa.
     * 
     * @return modelo
     */
    public java.lang.String getModelo() {
        return modelo;
    }


    /**
     * Sets the modelo value for this Tasa.
     * 
     * @param modelo
     */
    public void setModelo(java.lang.String modelo) {
        this.modelo = modelo;
    }


    /**
     * Gets the nombre value for this Tasa.
     * 
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this Tasa.
     * 
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }


    /**
     * Gets the tipo value for this Tasa.
     * 
     * @return tipo
     */
    public java.lang.String getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this Tasa.
     * 
     * @param tipo
     */
    public void setTipo(java.lang.String tipo) {
        this.tipo = tipo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Tasa)) return false;
        Tasa other = (Tasa) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.captura==null && other.getCaptura()==null) || 
             (this.captura!=null &&
              this.captura.equals(other.getCaptura()))) &&
            ((this.codigo==null && other.getCodigo()==null) || 
             (this.codigo!=null &&
              this.codigo.equals(other.getCodigo()))) &&
            ((this.datosEspecificos==null && other.getDatosEspecificos()==null) || 
             (this.datosEspecificos!=null &&
              this.datosEspecificos.equals(other.getDatosEspecificos()))) &&
            ((this.idEntidadEmisora==null && other.getIdEntidadEmisora()==null) || 
             (this.idEntidadEmisora!=null &&
              this.idEntidadEmisora.equals(other.getIdEntidadEmisora()))) &&
            ((this.modelo==null && other.getModelo()==null) || 
             (this.modelo!=null &&
              this.modelo.equals(other.getModelo()))) &&
            ((this.nombre==null && other.getNombre()==null) || 
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre()))) &&
            ((this.tipo==null && other.getTipo()==null) || 
             (this.tipo!=null &&
              this.tipo.equals(other.getTipo())));
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
        if (getCaptura() != null) {
            _hashCode += getCaptura().hashCode();
        }
        if (getCodigo() != null) {
            _hashCode += getCodigo().hashCode();
        }
        if (getDatosEspecificos() != null) {
            _hashCode += getDatosEspecificos().hashCode();
        }
        if (getIdEntidadEmisora() != null) {
            _hashCode += getIdEntidadEmisora().hashCode();
        }
        if (getModelo() != null) {
            _hashCode += getModelo().hashCode();
        }
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Tasa.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "Tasa"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("captura");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "captura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "codigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosEspecificos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "datosEspecificos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEntidadEmisora");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "idEntidadEmisora"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modelo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "modelo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "tipo"));
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
