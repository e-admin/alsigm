/**
 * Error.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.publicador.ws.client.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.io.Serializable;

public class Error extends RetornoServicio implements Serializable {
	
    private String descripcion;

    private int idAplicacion;

    private int idError;

    private int idHito;

    private String idObjeto;

    private String idSistema;

    public Error() {
    }

    public Error(
           String errorCode,
           String returnCode,
           String descripcion,
           int idAplicacion,
           int idError,
           int idHito,
           String idObjeto,
           String idSistema) {
    	 this.errorCode=errorCode;
         this.returnCode=returnCode;
        this.descripcion = descripcion;
        this.idAplicacion = idAplicacion;
        this.idError = idError;
        this.idHito = idHito;
        this.idObjeto = idObjeto;
        this.idSistema = idSistema;
    }


    /**
     * Gets the descripcion value for this Error.
     * 
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }


    /**
     * Sets the descripcion value for this Error.
     * 
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    /**
     * Gets the idAplicacion value for this Error.
     * 
     * @return idAplicacion
     */
    public int getIdAplicacion() {
        return idAplicacion;
    }


    /**
     * Sets the idAplicacion value for this Error.
     * 
     * @param idAplicacion
     */
    public void setIdAplicacion(int idAplicacion) {
        this.idAplicacion = idAplicacion;
    }


    /**
     * Gets the idError value for this Error.
     * 
     * @return idError
     */
    public int getIdError() {
        return idError;
    }


    /**
     * Sets the idError value for this Error.
     * 
     * @param idError
     */
    public void setIdError(int idError) {
        this.idError = idError;
    }


    /**
     * Gets the idHito value for this Error.
     * 
     * @return idHito
     */
    public int getIdHito() {
        return idHito;
    }


    /**
     * Sets the idHito value for this Error.
     * 
     * @param idHito
     */
    public void setIdHito(int idHito) {
        this.idHito = idHito;
    }


    /**
     * Gets the idObjeto value for this Error.
     * 
     * @return idObjeto
     */
    public String getIdObjeto() {
        return idObjeto;
    }


    /**
     * Sets the idObjeto value for this Error.
     * 
     * @param idObjeto
     */
    public void setIdObjeto(String idObjeto) {
        this.idObjeto = idObjeto;
    }


    /**
     * Gets the idSistema value for this Error.
     * 
     * @return idSistema
     */
    public String getIdSistema() {
        return idSistema;
    }


    /**
     * Sets the idSistema value for this Error.
     * 
     * @param idSistema
     */
    public void setIdSistema(String idSistema) {
        this.idSistema = idSistema;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Error)) return false;
        Error other = (Error) obj;
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
            this.idAplicacion == other.getIdAplicacion() &&
            this.idError == other.getIdError() &&
            this.idHito == other.getIdHito() &&
            ((this.idObjeto==null && other.getIdObjeto()==null) || 
             (this.idObjeto!=null &&
              this.idObjeto.equals(other.getIdObjeto()))) &&
            ((this.idSistema==null && other.getIdSistema()==null) || 
             (this.idSistema!=null &&
              this.idSistema.equals(other.getIdSistema())));
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
        _hashCode += getIdAplicacion();
        _hashCode += getIdError();
        _hashCode += getIdHito();
        if (getIdObjeto() != null) {
            _hashCode += getIdObjeto().hashCode();
        }
        if (getIdSistema() != null) {
            _hashCode += getIdSistema().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Error.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "Error"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "descripcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAplicacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idAplicacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idError"));
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
        elemField.setFieldName("idObjeto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idObjeto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSistema");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.publicador.sgm.tecdoc.ieci", "idSistema"));
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
