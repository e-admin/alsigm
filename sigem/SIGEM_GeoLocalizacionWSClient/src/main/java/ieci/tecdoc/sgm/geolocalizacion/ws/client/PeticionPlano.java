/**
 * PeticionPlano.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.geolocalizacion.ws.client;

public class PeticionPlano  extends ieci.tecdoc.sgm.geolocalizacion.ws.client.RetornoServicio  implements java.io.Serializable {
    private int alto;

    private int ancho;

    private int codigoINEMunicipio;

    private int escala;

    private int idMapa;

    public PeticionPlano() {
    }

    public PeticionPlano(
           java.lang.String errorCode,
           java.lang.String returnCode,
           int alto,
           int ancho,
           int codigoINEMunicipio,
           int escala,
           int idMapa) {
        super(
            errorCode,
            returnCode);
        this.alto = alto;
        this.ancho = ancho;
        this.codigoINEMunicipio = codigoINEMunicipio;
        this.escala = escala;
        this.idMapa = idMapa;
    }


    /**
     * Gets the alto value for this PeticionPlano.
     * 
     * @return alto
     */
    public int getAlto() {
        return alto;
    }


    /**
     * Sets the alto value for this PeticionPlano.
     * 
     * @param alto
     */
    public void setAlto(int alto) {
        this.alto = alto;
    }


    /**
     * Gets the ancho value for this PeticionPlano.
     * 
     * @return ancho
     */
    public int getAncho() {
        return ancho;
    }


    /**
     * Sets the ancho value for this PeticionPlano.
     * 
     * @param ancho
     */
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }


    /**
     * Gets the codigoINEMunicipio value for this PeticionPlano.
     * 
     * @return codigoINEMunicipio
     */
    public int getCodigoINEMunicipio() {
        return codigoINEMunicipio;
    }


    /**
     * Sets the codigoINEMunicipio value for this PeticionPlano.
     * 
     * @param codigoINEMunicipio
     */
    public void setCodigoINEMunicipio(int codigoINEMunicipio) {
        this.codigoINEMunicipio = codigoINEMunicipio;
    }


    /**
     * Gets the escala value for this PeticionPlano.
     * 
     * @return escala
     */
    public int getEscala() {
        return escala;
    }


    /**
     * Sets the escala value for this PeticionPlano.
     * 
     * @param escala
     */
    public void setEscala(int escala) {
        this.escala = escala;
    }


    /**
     * Gets the idMapa value for this PeticionPlano.
     * 
     * @return idMapa
     */
    public int getIdMapa() {
        return idMapa;
    }


    /**
     * Sets the idMapa value for this PeticionPlano.
     * 
     * @param idMapa
     */
    public void setIdMapa(int idMapa) {
        this.idMapa = idMapa;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PeticionPlano)) return false;
        PeticionPlano other = (PeticionPlano) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.alto == other.getAlto() &&
            this.ancho == other.getAncho() &&
            this.codigoINEMunicipio == other.getCodigoINEMunicipio() &&
            this.escala == other.getEscala() &&
            this.idMapa == other.getIdMapa();
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
        _hashCode += getAlto();
        _hashCode += getAncho();
        _hashCode += getCodigoINEMunicipio();
        _hashCode += getEscala();
        _hashCode += getIdMapa();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PeticionPlano.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "PeticionPlano"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "alto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ancho");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "ancho"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoINEMunicipio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "codigoINEMunicipio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("escala");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "escala"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMapa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "idMapa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
