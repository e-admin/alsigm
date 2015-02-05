/**
 * Cultivo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.catastro.ws.client;

public class Cultivo  extends ieci.tecdoc.sgm.catastro.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String calificacion;

    private java.lang.String denominacion;

    private java.lang.String identificador;

    private java.lang.Integer intensidadProductiva;

    private java.lang.Double superficie;

    public Cultivo() {
    }

    public Cultivo(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String calificacion,
           java.lang.String denominacion,
           java.lang.String identificador,
           java.lang.Integer intensidadProductiva,
           java.lang.Double superficie) {
        super(
            errorCode,
            returnCode);
        this.calificacion = calificacion;
        this.denominacion = denominacion;
        this.identificador = identificador;
        this.intensidadProductiva = intensidadProductiva;
        this.superficie = superficie;
    }


    /**
     * Gets the calificacion value for this Cultivo.
     * 
     * @return calificacion
     */
    public java.lang.String getCalificacion() {
        return calificacion;
    }


    /**
     * Sets the calificacion value for this Cultivo.
     * 
     * @param calificacion
     */
    public void setCalificacion(java.lang.String calificacion) {
        this.calificacion = calificacion;
    }


    /**
     * Gets the denominacion value for this Cultivo.
     * 
     * @return denominacion
     */
    public java.lang.String getDenominacion() {
        return denominacion;
    }


    /**
     * Sets the denominacion value for this Cultivo.
     * 
     * @param denominacion
     */
    public void setDenominacion(java.lang.String denominacion) {
        this.denominacion = denominacion;
    }


    /**
     * Gets the identificador value for this Cultivo.
     * 
     * @return identificador
     */
    public java.lang.String getIdentificador() {
        return identificador;
    }


    /**
     * Sets the identificador value for this Cultivo.
     * 
     * @param identificador
     */
    public void setIdentificador(java.lang.String identificador) {
        this.identificador = identificador;
    }


    /**
     * Gets the intensidadProductiva value for this Cultivo.
     * 
     * @return intensidadProductiva
     */
    public java.lang.Integer getIntensidadProductiva() {
        return intensidadProductiva;
    }


    /**
     * Sets the intensidadProductiva value for this Cultivo.
     * 
     * @param intensidadProductiva
     */
    public void setIntensidadProductiva(java.lang.Integer intensidadProductiva) {
        this.intensidadProductiva = intensidadProductiva;
    }


    /**
     * Gets the superficie value for this Cultivo.
     * 
     * @return superficie
     */
    public java.lang.Double getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this Cultivo.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Double superficie) {
        this.superficie = superficie;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Cultivo)) return false;
        Cultivo other = (Cultivo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.calificacion==null && other.getCalificacion()==null) || 
             (this.calificacion!=null &&
              this.calificacion.equals(other.getCalificacion()))) &&
            ((this.denominacion==null && other.getDenominacion()==null) || 
             (this.denominacion!=null &&
              this.denominacion.equals(other.getDenominacion()))) &&
            ((this.identificador==null && other.getIdentificador()==null) || 
             (this.identificador!=null &&
              this.identificador.equals(other.getIdentificador()))) &&
            ((this.intensidadProductiva==null && other.getIntensidadProductiva()==null) || 
             (this.intensidadProductiva!=null &&
              this.intensidadProductiva.equals(other.getIntensidadProductiva()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie())));
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
        if (getCalificacion() != null) {
            _hashCode += getCalificacion().hashCode();
        }
        if (getDenominacion() != null) {
            _hashCode += getDenominacion().hashCode();
        }
        if (getIdentificador() != null) {
            _hashCode += getIdentificador().hashCode();
        }
        if (getIntensidadProductiva() != null) {
            _hashCode += getIntensidadProductiva().hashCode();
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Cultivo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "Cultivo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("calificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "calificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "denominacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificador");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "identificador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("intensidadProductiva");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "intensidadProductiva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficie");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "superficie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
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
