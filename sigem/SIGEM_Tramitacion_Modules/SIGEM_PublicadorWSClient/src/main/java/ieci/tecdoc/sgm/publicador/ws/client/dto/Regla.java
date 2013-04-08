/**
 * Regla.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.publicador.ws.client.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.io.Serializable;

public class Regla extends RetornoServicio implements Serializable {
	
    private String atributos;

    private int id;

    private int idAccion;

    private int idAplicacion;

    private int idCondicion;

    private int idEvento;

    private int idFase;

    private int idInfo;

    private int idPcd;

    private int idTramite;

    private int orden;

    private int tipoDoc;

    public Regla() {
    }

    public Regla(
           String errorCode,
           String returnCode,
           String atributos,
           int id,
           int idAccion,
           int idAplicacion,
           int idCondicion,
           int idEvento,
           int idFase,
           int idInfo,
           int idPcd,
           int idTramite,
           int orden,
           int tipoDoc) {
       this.errorCode=errorCode;
       this.returnCode=returnCode;
        this.atributos = atributos;
        this.id = id;
        this.idAccion = idAccion;
        this.idAplicacion = idAplicacion;
        this.idCondicion = idCondicion;
        this.idEvento = idEvento;
        this.idFase = idFase;
        this.idInfo = idInfo;
        this.idPcd = idPcd;
        this.idTramite = idTramite;
        this.orden = orden;
        this.tipoDoc = tipoDoc;
    }


    /**
     * Gets the atributos value for this Regla.
     * 
     * @return atributos
     */
    public String getAtributos() {
        return atributos;
    }


    /**
     * Sets the atributos value for this Regla.
     * 
     * @param atributos
     */
    public void setAtributos(String atributos) {
        this.atributos = atributos;
    }


    /**
     * Gets the id value for this Regla.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this Regla.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the idAccion value for this Regla.
     * 
     * @return idAccion
     */
    public int getIdAccion() {
        return idAccion;
    }


    /**
     * Sets the idAccion value for this Regla.
     * 
     * @param idAccion
     */
    public void setIdAccion(int idAccion) {
        this.idAccion = idAccion;
    }


    /**
     * Gets the idAplicacion value for this Regla.
     * 
     * @return idAplicacion
     */
    public int getIdAplicacion() {
        return idAplicacion;
    }


    /**
     * Sets the idAplicacion value for this Regla.
     * 
     * @param idAplicacion
     */
    public void setIdAplicacion(int idAplicacion) {
        this.idAplicacion = idAplicacion;
    }


    /**
     * Gets the idCondicion value for this Regla.
     * 
     * @return idCondicion
     */
    public int getIdCondicion() {
        return idCondicion;
    }


    /**
     * Sets the idCondicion value for this Regla.
     * 
     * @param idCondicion
     */
    public void setIdCondicion(int idCondicion) {
        this.idCondicion = idCondicion;
    }


    /**
     * Gets the idEvento value for this Regla.
     * 
     * @return idEvento
     */
    public int getIdEvento() {
        return idEvento;
    }


    /**
     * Sets the idEvento value for this Regla.
     * 
     * @param idEvento
     */
    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }


    /**
     * Gets the idFase value for this Regla.
     * 
     * @return idFase
     */
    public int getIdFase() {
        return idFase;
    }


    /**
     * Sets the idFase value for this Regla.
     * 
     * @param idFase
     */
    public void setIdFase(int idFase) {
        this.idFase = idFase;
    }


    /**
     * Gets the idInfo value for this Regla.
     * 
     * @return idInfo
     */
    public int getIdInfo() {
        return idInfo;
    }


    /**
     * Sets the idInfo value for this Regla.
     * 
     * @param idInfo
     */
    public void setIdInfo(int idInfo) {
        this.idInfo = idInfo;
    }


    /**
     * Gets the idPcd value for this Regla.
     * 
     * @return idPcd
     */
    public int getIdPcd() {
        return idPcd;
    }


    /**
     * Sets the idPcd value for this Regla.
     * 
     * @param idPcd
     */
    public void setIdPcd(int idPcd) {
        this.idPcd = idPcd;
    }


    /**
     * Gets the idTramite value for this Regla.
     * 
     * @return idTramite
     */
    public int getIdTramite() {
        return idTramite;
    }


    /**
     * Sets the idTramite value for this Regla.
     * 
     * @param idTramite
     */
    public void setIdTramite(int idTramite) {
        this.idTramite = idTramite;
    }


    /**
     * Gets the orden value for this Regla.
     * 
     * @return orden
     */
    public int getOrden() {
        return orden;
    }


    /**
     * Sets the orden value for this Regla.
     * 
     * @param orden
     */
    public void setOrden(int orden) {
        this.orden = orden;
    }


    /**
     * Gets the tipoDoc value for this Regla.
     * 
     * @return tipoDoc
     */
    public int getTipoDoc() {
        return tipoDoc;
    }


    /**
     * Sets the tipoDoc value for this Regla.
     * 
     * @param tipoDoc
     */
    public void setTipoDoc(int tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Regla)) return false;
        Regla other = (Regla) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.atributos==null && other.getAtributos()==null) || 
             (this.atributos!=null &&
              this.atributos.equals(other.getAtributos()))) &&
            this.id == other.getId() &&
            this.idAccion == other.getIdAccion() &&
            this.idAplicacion == other.getIdAplicacion() &&
            this.idCondicion == other.getIdCondicion() &&
            this.idEvento == other.getIdEvento() &&
            this.idFase == other.getIdFase() &&
            this.idInfo == other.getIdInfo() &&
            this.idPcd == other.getIdPcd() &&
            this.idTramite == other.getIdTramite() &&
            this.orden == other.getOrden() &&
            this.tipoDoc == other.getTipoDoc();
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
        if (getAtributos() != null) {
            _hashCode += getAtributos().hashCode();
        }
        _hashCode += getId();
        _hashCode += getIdAccion();
        _hashCode += getIdAplicacion();
        _hashCode += getIdCondicion();
        _hashCode += getIdEvento();
        _hashCode += getIdFase();
        _hashCode += getIdInfo();
        _hashCode += getIdPcd();
        _hashCode += getIdTramite();
        _hashCode += getOrden();
        _hashCode += getTipoDoc();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Regla.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "Regla"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atributos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "atributos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idAccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAplicacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idAplicacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCondicion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idCondicion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEvento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idEvento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFase");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idFase"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPcd");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idPcd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTramite");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idTramite"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orden");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "orden"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDoc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "tipoDoc"));
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
           String mechType, 
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
           String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
