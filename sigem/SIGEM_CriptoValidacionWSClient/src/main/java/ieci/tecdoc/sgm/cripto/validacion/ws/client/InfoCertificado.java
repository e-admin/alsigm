/**
 * InfoCertificado.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.validacion.ws.client;

public class InfoCertificado  extends ieci.tecdoc.sgm.cripto.validacion.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String apellido1;

    private java.lang.String apellido2;

    private java.lang.String asunto;

    private java.lang.String cif;

    private java.lang.String emisor;

    private java.lang.String nif;

    private java.lang.String nombre;

    private java.lang.String nombreSinApellidos;

    private java.lang.String numeroSerie;

    private java.lang.String razonSocial;

    public InfoCertificado() {
    }

    public InfoCertificado(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String apellido1,
           java.lang.String apellido2,
           java.lang.String asunto,
           java.lang.String cif,
           java.lang.String emisor,
           java.lang.String nif,
           java.lang.String nombre,
           java.lang.String nombreSinApellidos,
           java.lang.String numeroSerie,
           java.lang.String razonSocial) {
        super(
            errorCode,
            returnCode);
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.asunto = asunto;
        this.cif = cif;
        this.emisor = emisor;
        this.nif = nif;
        this.nombre = nombre;
        this.nombreSinApellidos = nombreSinApellidos;
        this.numeroSerie = numeroSerie;
        this.razonSocial = razonSocial;
    }


    /**
     * Gets the apellido1 value for this InfoCertificado.
     *
     * @return apellido1
     */
    public java.lang.String getApellido1() {
        return apellido1;
    }


    /**
     * Sets the apellido1 value for this InfoCertificado.
     *
     * @param apellido1
     */
    public void setApellido1(java.lang.String apellido1) {
        this.apellido1 = apellido1;
    }


    /**
     * Gets the apellido2 value for this InfoCertificado.
     *
     * @return apellido2
     */
    public java.lang.String getApellido2() {
        return apellido2;
    }


    /**
     * Sets the apellido2 value for this InfoCertificado.
     *
     * @param apellido2
     */
    public void setApellido2(java.lang.String apellido2) {
        this.apellido2 = apellido2;
    }


    /**
     * Gets the asunto value for this InfoCertificado.
     *
     * @return asunto
     */
    public java.lang.String getAsunto() {
        return asunto;
    }


    /**
     * Sets the asunto value for this InfoCertificado.
     *
     * @param asunto
     */
    public void setAsunto(java.lang.String asunto) {
        this.asunto = asunto;
    }


    /**
     * Gets the cif value for this InfoCertificado.
     *
     * @return cif
     */
    public java.lang.String getCif() {
        return cif;
    }


    /**
     * Sets the cif value for this InfoCertificado.
     *
     * @param cif
     */
    public void setCif(java.lang.String cif) {
        this.cif = cif;
    }


    /**
     * Gets the emisor value for this InfoCertificado.
     *
     * @return emisor
     */
    public java.lang.String getEmisor() {
        return emisor;
    }


    /**
     * Sets the emisor value for this InfoCertificado.
     *
     * @param emisor
     */
    public void setEmisor(java.lang.String emisor) {
        this.emisor = emisor;
    }


    /**
     * Gets the nif value for this InfoCertificado.
     *
     * @return nif
     */
    public java.lang.String getNif() {
        return nif;
    }


    /**
     * Sets the nif value for this InfoCertificado.
     *
     * @param nif
     */
    public void setNif(java.lang.String nif) {
        this.nif = nif;
    }


    /**
     * Gets the nombreSinApellidos value for this InfoCertificado.
     *
     * @return nombreSinApellidos
     */
    public java.lang.String getNombreSinApellidos() {
        return nombreSinApellidos;
    }


    /**
     * Sets the nombreSinApellidos value for this InfoCertificado.
     *
     * @param nombreSinApellidos
     */
    public void setNombreSinApellidos(java.lang.String nombreSinApellidos) {
        this.nombreSinApellidos = nombreSinApellidos;
    }


    /**
     * Gets the nombre value for this InfoCertificado.
     *
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this InfoCertificado.
     *
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }


    /**
     * Gets the numeroSerie value for this InfoCertificado.
     *
     * @return numeroSerie
     */
    public java.lang.String getNumeroSerie() {
        return numeroSerie;
    }


    /**
     * Sets the numeroSerie value for this InfoCertificado.
     *
     * @param numeroSerie
     */
    public void setNumeroSerie(java.lang.String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }


    /**
     * Gets the razonSocial value for this InfoCertificado.
     *
     * @return razonSocial
     */
    public java.lang.String getRazonSocial() {
        return razonSocial;
    }


    /**
     * Sets the razonSocial value for this InfoCertificado.
     *
     * @param razonSocial
     */
    public void setRazonSocial(java.lang.String razonSocial) {
        this.razonSocial = razonSocial;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InfoCertificado)) return false;
        InfoCertificado other = (InfoCertificado) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) &&
            ((this.apellido1==null && other.getApellido1()==null) ||
             (this.apellido1!=null &&
              this.apellido1.equals(other.getApellido1()))) &&
            ((this.apellido2==null && other.getApellido2()==null) ||
             (this.apellido2!=null &&
              this.apellido2.equals(other.getApellido2()))) &&
            ((this.asunto==null && other.getAsunto()==null) ||
             (this.asunto!=null &&
              this.asunto.equals(other.getAsunto()))) &&
            ((this.cif==null && other.getCif()==null) ||
             (this.cif!=null &&
              this.cif.equals(other.getCif()))) &&
            ((this.emisor==null && other.getEmisor()==null) ||
             (this.emisor!=null &&
              this.emisor.equals(other.getEmisor()))) &&
            ((this.nif==null && other.getNif()==null) ||
             (this.nif!=null &&
              this.nif.equals(other.getNif()))) &&
            ((this.nombre==null && other.getNombre()==null) ||
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre()))) &&
            ((this.nombreSinApellidos==null && other.getNombreSinApellidos()==null) ||
             (this.nombreSinApellidos!=null &&
              this.nombreSinApellidos.equals(other.getNombreSinApellidos()))) &&
            ((this.numeroSerie==null && other.getNumeroSerie()==null) ||
             (this.numeroSerie!=null &&
              this.numeroSerie.equals(other.getNumeroSerie()))) &&
            ((this.razonSocial==null && other.getRazonSocial()==null) ||
             (this.razonSocial!=null &&
              this.razonSocial.equals(other.getRazonSocial())));
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
        if (getApellido1() != null) {
            _hashCode += getApellido1().hashCode();
        }
        if (getApellido2() != null) {
            _hashCode += getApellido2().hashCode();
        }
        if (getAsunto() != null) {
            _hashCode += getAsunto().hashCode();
        }
        if (getCif() != null) {
            _hashCode += getCif().hashCode();
        }
        if (getEmisor() != null) {
            _hashCode += getEmisor().hashCode();
        }
        if (getNif() != null) {
            _hashCode += getNif().hashCode();
        }
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        if (getNombreSinApellidos() != null) {
            _hashCode += getNombreSinApellidos().hashCode();
        }
        if (getNumeroSerie() != null) {
            _hashCode += getNumeroSerie().hashCode();
        }
        if (getRazonSocial() != null) {
            _hashCode += getRazonSocial().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InfoCertificado.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "InfoCertificado"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("apellido1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "apellido1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("apellido2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "apellido2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asunto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "asunto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cif");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "cif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emisor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "emisor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nif");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "nif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreSinApellidos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "nombreSinApellidos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroSerie");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "numeroSerie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("razonSocial");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.validacion.cripto.sgm.tecdoc.ieci", "razonSocial"));
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
