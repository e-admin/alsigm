/**
 * Construccion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.catastro.ws.client;

public class Construccion  extends ieci.tecdoc.sgm.catastro.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String codigoUso;

    private java.lang.String escalera;

    private java.lang.String identificador;

    private java.lang.String planta;

    private java.lang.String puerta;

    private java.lang.Double superficieTotal;

    public Construccion() {
    }

    public Construccion(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String codigoUso,
           java.lang.String escalera,
           java.lang.String identificador,
           java.lang.String planta,
           java.lang.String puerta,
           java.lang.Double superficieTotal) {
        super(
            errorCode,
            returnCode);
        this.codigoUso = codigoUso;
        this.escalera = escalera;
        this.identificador = identificador;
        this.planta = planta;
        this.puerta = puerta;
        this.superficieTotal = superficieTotal;
    }


    /**
     * Gets the codigoUso value for this Construccion.
     * 
     * @return codigoUso
     */
    public java.lang.String getCodigoUso() {
        return codigoUso;
    }


    /**
     * Sets the codigoUso value for this Construccion.
     * 
     * @param codigoUso
     */
    public void setCodigoUso(java.lang.String codigoUso) {
        this.codigoUso = codigoUso;
    }


    /**
     * Gets the escalera value for this Construccion.
     * 
     * @return escalera
     */
    public java.lang.String getEscalera() {
        return escalera;
    }


    /**
     * Sets the escalera value for this Construccion.
     * 
     * @param escalera
     */
    public void setEscalera(java.lang.String escalera) {
        this.escalera = escalera;
    }


    /**
     * Gets the identificador value for this Construccion.
     * 
     * @return identificador
     */
    public java.lang.String getIdentificador() {
        return identificador;
    }


    /**
     * Sets the identificador value for this Construccion.
     * 
     * @param identificador
     */
    public void setIdentificador(java.lang.String identificador) {
        this.identificador = identificador;
    }


    /**
     * Gets the planta value for this Construccion.
     * 
     * @return planta
     */
    public java.lang.String getPlanta() {
        return planta;
    }


    /**
     * Sets the planta value for this Construccion.
     * 
     * @param planta
     */
    public void setPlanta(java.lang.String planta) {
        this.planta = planta;
    }


    /**
     * Gets the puerta value for this Construccion.
     * 
     * @return puerta
     */
    public java.lang.String getPuerta() {
        return puerta;
    }


    /**
     * Sets the puerta value for this Construccion.
     * 
     * @param puerta
     */
    public void setPuerta(java.lang.String puerta) {
        this.puerta = puerta;
    }


    /**
     * Gets the superficieTotal value for this Construccion.
     * 
     * @return superficieTotal
     */
    public java.lang.Double getSuperficieTotal() {
        return superficieTotal;
    }


    /**
     * Sets the superficieTotal value for this Construccion.
     * 
     * @param superficieTotal
     */
    public void setSuperficieTotal(java.lang.Double superficieTotal) {
        this.superficieTotal = superficieTotal;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Construccion)) return false;
        Construccion other = (Construccion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codigoUso==null && other.getCodigoUso()==null) || 
             (this.codigoUso!=null &&
              this.codigoUso.equals(other.getCodigoUso()))) &&
            ((this.escalera==null && other.getEscalera()==null) || 
             (this.escalera!=null &&
              this.escalera.equals(other.getEscalera()))) &&
            ((this.identificador==null && other.getIdentificador()==null) || 
             (this.identificador!=null &&
              this.identificador.equals(other.getIdentificador()))) &&
            ((this.planta==null && other.getPlanta()==null) || 
             (this.planta!=null &&
              this.planta.equals(other.getPlanta()))) &&
            ((this.puerta==null && other.getPuerta()==null) || 
             (this.puerta!=null &&
              this.puerta.equals(other.getPuerta()))) &&
            ((this.superficieTotal==null && other.getSuperficieTotal()==null) || 
             (this.superficieTotal!=null &&
              this.superficieTotal.equals(other.getSuperficieTotal())));
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
        if (getCodigoUso() != null) {
            _hashCode += getCodigoUso().hashCode();
        }
        if (getEscalera() != null) {
            _hashCode += getEscalera().hashCode();
        }
        if (getIdentificador() != null) {
            _hashCode += getIdentificador().hashCode();
        }
        if (getPlanta() != null) {
            _hashCode += getPlanta().hashCode();
        }
        if (getPuerta() != null) {
            _hashCode += getPuerta().hashCode();
        }
        if (getSuperficieTotal() != null) {
            _hashCode += getSuperficieTotal().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Construccion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "Construccion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoUso");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "codigoUso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("escalera");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "escalera"));
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
        elemField.setFieldName("planta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "planta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("puerta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "puerta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieTotal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catastro.sgm.tecdoc.ieci", "superficieTotal"));
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
