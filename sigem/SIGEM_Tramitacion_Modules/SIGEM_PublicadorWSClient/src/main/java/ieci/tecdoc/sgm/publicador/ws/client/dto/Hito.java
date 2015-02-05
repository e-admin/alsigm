/**
 * Hito.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.publicador.ws.client.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.io.Serializable;
import java.util.Date;

public class Hito extends RetornoServicio implements Serializable {
	
    private int estado;

    private Date fecha;

    private int idAplicacion;

    private int idEvento;

    private int idFase;

    private int idHito;

    private int idInfo;

    private String idObjeto;

    private int idPcd;

    private String idSistema;

    private int idTramite;

    private String infoAux;

    private String ipMaquina;

    private int tipoDoc;

    public Hito() {
    }

    public Hito(
           String errorCode,
           String returnCode,
           int estado,
           Date fecha,
           int idAplicacion,
           int idEvento,
           int idFase,
           int idHito,
           int idInfo,
           String idObjeto,
           int idPcd,
           String idSistema,
           int idTramite,
           String infoAux,
           String ipMaquina,
           int tipoDoc) {
    	 this.errorCode=errorCode;
         this.returnCode=returnCode;
        this.estado = estado;
        this.fecha = fecha;
        this.idAplicacion = idAplicacion;
        this.idEvento = idEvento;
        this.idFase = idFase;
        this.idHito = idHito;
        this.idInfo = idInfo;
        this.idObjeto = idObjeto;
        this.idPcd = idPcd;
        this.idSistema = idSistema;
        this.idTramite = idTramite;
        this.infoAux = infoAux;
        this.ipMaquina = ipMaquina;
        this.tipoDoc = tipoDoc;
    }


    /**
     * Gets the estado value for this Hito.
     * 
     * @return estado
     */
    public int getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this Hito.
     * 
     * @param estado
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }


    /**
     * Gets the fecha value for this Hito.
     * 
     * @return fecha
     */
    public Date getFecha() {
        return fecha;
    }


    /**
     * Sets the fecha value for this Hito.
     * 
     * @param date
     */
    public void setFecha(Date date) {
        this.fecha = date;
    }


    /**
     * Gets the idAplicacion value for this Hito.
     * 
     * @return idAplicacion
     */
    public int getIdAplicacion() {
        return idAplicacion;
    }


    /**
     * Sets the idAplicacion value for this Hito.
     * 
     * @param idAplicacion
     */
    public void setIdAplicacion(int idAplicacion) {
        this.idAplicacion = idAplicacion;
    }


    /**
     * Gets the idEvento value for this Hito.
     * 
     * @return idEvento
     */
    public int getIdEvento() {
        return idEvento;
    }


    /**
     * Sets the idEvento value for this Hito.
     * 
     * @param idEvento
     */
    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }


    /**
     * Gets the idFase value for this Hito.
     * 
     * @return idFase
     */
    public int getIdFase() {
        return idFase;
    }


    /**
     * Sets the idFase value for this Hito.
     * 
     * @param idFase
     */
    public void setIdFase(int idFase) {
        this.idFase = idFase;
    }


    /**
     * Gets the idHito value for this Hito.
     * 
     * @return idHito
     */
    public int getIdHito() {
        return idHito;
    }


    /**
     * Sets the idHito value for this Hito.
     * 
     * @param idHito
     */
    public void setIdHito(int idHito) {
        this.idHito = idHito;
    }


    /**
     * Gets the idInfo value for this Hito.
     * 
     * @return idInfo
     */
    public int getIdInfo() {
        return idInfo;
    }


    /**
     * Sets the idInfo value for this Hito.
     * 
     * @param idInfo
     */
    public void setIdInfo(int idInfo) {
        this.idInfo = idInfo;
    }


    /**
     * Gets the idObjeto value for this Hito.
     * 
     * @return idObjeto
     */
    public String getIdObjeto() {
        return idObjeto;
    }


    /**
     * Sets the idObjeto value for this Hito.
     * 
     * @param idObjeto
     */
    public void setIdObjeto(String idObjeto) {
        this.idObjeto = idObjeto;
    }


    /**
     * Gets the idPcd value for this Hito.
     * 
     * @return idPcd
     */
    public int getIdPcd() {
        return idPcd;
    }


    /**
     * Sets the idPcd value for this Hito.
     * 
     * @param idPcd
     */
    public void setIdPcd(int idPcd) {
        this.idPcd = idPcd;
    }


    /**
     * Gets the idSistema value for this Hito.
     * 
     * @return idSistema
     */
    public String getIdSistema() {
        return idSistema;
    }


    /**
     * Sets the idSistema value for this Hito.
     * 
     * @param idSistema
     */
    public void setIdSistema(String idSistema) {
        this.idSistema = idSistema;
    }


    /**
     * Gets the idTramite value for this Hito.
     * 
     * @return idTramite
     */
    public int getIdTramite() {
        return idTramite;
    }


    /**
     * Sets the idTramite value for this Hito.
     * 
     * @param idTramite
     */
    public void setIdTramite(int idTramite) {
        this.idTramite = idTramite;
    }


    /**
     * Gets the infoAux value for this Hito.
     * 
     * @return infoAux
     */
    public String getInfoAux() {
        return infoAux;
    }


    /**
     * Sets the infoAux value for this Hito.
     * 
     * @param infoAux
     */
    public void setInfoAux(String infoAux) {
        this.infoAux = infoAux;
    }


    /**
     * Gets the ipMaquina value for this Hito.
     * 
     * @return ipMaquina
     */
    public String getIpMaquina() {
        return ipMaquina;
    }


    /**
     * Sets the ipMaquina value for this Hito.
     * 
     * @param ipMaquina
     */
    public void setIpMaquina(String ipMaquina) {
        this.ipMaquina = ipMaquina;
    }


    /**
     * Gets the tipoDoc value for this Hito.
     * 
     * @return tipoDoc
     */
    public int getTipoDoc() {
        return tipoDoc;
    }


    /**
     * Sets the tipoDoc value for this Hito.
     * 
     * @param tipoDoc
     */
    public void setTipoDoc(int tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Hito)) return false;
        Hito other = (Hito) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.estado == other.getEstado() &&
            ((this.fecha==null && other.getFecha()==null) || 
             (this.fecha!=null &&
              this.fecha.equals(other.getFecha()))) &&
            this.idAplicacion == other.getIdAplicacion() &&
            this.idEvento == other.getIdEvento() &&
            this.idFase == other.getIdFase() &&
            this.idHito == other.getIdHito() &&
            this.idInfo == other.getIdInfo() &&
            ((this.idObjeto==null && other.getIdObjeto()==null) || 
             (this.idObjeto!=null &&
              this.idObjeto.equals(other.getIdObjeto()))) &&
            this.idPcd == other.getIdPcd() &&
            ((this.idSistema==null && other.getIdSistema()==null) || 
             (this.idSistema!=null &&
              this.idSistema.equals(other.getIdSistema()))) &&
            this.idTramite == other.getIdTramite() &&
            ((this.infoAux==null && other.getInfoAux()==null) || 
             (this.infoAux!=null &&
              this.infoAux.equals(other.getInfoAux()))) &&
            ((this.ipMaquina==null && other.getIpMaquina()==null) || 
             (this.ipMaquina!=null &&
              this.ipMaquina.equals(other.getIpMaquina()))) &&
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
        _hashCode += getEstado();
        if (getFecha() != null) {
            _hashCode += getFecha().hashCode();
        }
        _hashCode += getIdAplicacion();
        _hashCode += getIdEvento();
        _hashCode += getIdFase();
        _hashCode += getIdHito();
        _hashCode += getIdInfo();
        if (getIdObjeto() != null) {
            _hashCode += getIdObjeto().hashCode();
        }
        _hashCode += getIdPcd();
        if (getIdSistema() != null) {
            _hashCode += getIdSistema().hashCode();
        }
        _hashCode += getIdTramite();
        if (getInfoAux() != null) {
            _hashCode += getInfoAux().hashCode();
        }
        if (getIpMaquina() != null) {
            _hashCode += getIpMaquina().hashCode();
        }
        _hashCode += getTipoDoc();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Hito.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "Hito"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecha");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "fecha"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAplicacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idAplicacion"));
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
        elemField.setFieldName("idHito");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idHito"));
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
        elemField.setFieldName("idObjeto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idObjeto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPcd");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idPcd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSistema");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idSistema"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTramite");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idTramite"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("infoAux");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "infoAux"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ipMaquina");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "ipMaquina"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
