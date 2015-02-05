/**
 * Solicitante.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.redsara.intermediacion.scsp.esquemas.ws.respuesta;

public class Solicitante  implements java.io.Serializable {
    private java.lang.String identificadorSolicitante;

    private java.lang.String nombreSolicitante;

    private java.lang.String unidadTramitadora;

    private es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Procedimiento procedimiento;

    private java.lang.String finalidad;

    private es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Consentimiento consentimiento;

    private es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Funcionario funcionario;

    private java.lang.String idExpediente;

    public Solicitante() {
    }

    public Solicitante(
           java.lang.String identificadorSolicitante,
           java.lang.String nombreSolicitante,
           java.lang.String unidadTramitadora,
           es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Procedimiento procedimiento,
           java.lang.String finalidad,
           es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Consentimiento consentimiento,
           es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Funcionario funcionario,
           java.lang.String idExpediente) {
           this.identificadorSolicitante = identificadorSolicitante;
           this.nombreSolicitante = nombreSolicitante;
           this.unidadTramitadora = unidadTramitadora;
           this.procedimiento = procedimiento;
           this.finalidad = finalidad;
           this.consentimiento = consentimiento;
           this.funcionario = funcionario;
           this.idExpediente = idExpediente;
    }


    /**
     * Gets the identificadorSolicitante value for this Solicitante.
     * 
     * @return identificadorSolicitante
     */
    public java.lang.String getIdentificadorSolicitante() {
        return identificadorSolicitante;
    }


    /**
     * Sets the identificadorSolicitante value for this Solicitante.
     * 
     * @param identificadorSolicitante
     */
    public void setIdentificadorSolicitante(java.lang.String identificadorSolicitante) {
        this.identificadorSolicitante = identificadorSolicitante;
    }


    /**
     * Gets the nombreSolicitante value for this Solicitante.
     * 
     * @return nombreSolicitante
     */
    public java.lang.String getNombreSolicitante() {
        return nombreSolicitante;
    }


    /**
     * Sets the nombreSolicitante value for this Solicitante.
     * 
     * @param nombreSolicitante
     */
    public void setNombreSolicitante(java.lang.String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }


    /**
     * Gets the unidadTramitadora value for this Solicitante.
     * 
     * @return unidadTramitadora
     */
    public java.lang.String getUnidadTramitadora() {
        return unidadTramitadora;
    }


    /**
     * Sets the unidadTramitadora value for this Solicitante.
     * 
     * @param unidadTramitadora
     */
    public void setUnidadTramitadora(java.lang.String unidadTramitadora) {
        this.unidadTramitadora = unidadTramitadora;
    }


    /**
     * Gets the procedimiento value for this Solicitante.
     * 
     * @return procedimiento
     */
    public es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Procedimiento getProcedimiento() {
        return procedimiento;
    }


    /**
     * Sets the procedimiento value for this Solicitante.
     * 
     * @param procedimiento
     */
    public void setProcedimiento(es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Procedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }


    /**
     * Gets the finalidad value for this Solicitante.
     * 
     * @return finalidad
     */
    public java.lang.String getFinalidad() {
        return finalidad;
    }


    /**
     * Sets the finalidad value for this Solicitante.
     * 
     * @param finalidad
     */
    public void setFinalidad(java.lang.String finalidad) {
        this.finalidad = finalidad;
    }


    /**
     * Gets the consentimiento value for this Solicitante.
     * 
     * @return consentimiento
     */
    public es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Consentimiento getConsentimiento() {
        return consentimiento;
    }


    /**
     * Sets the consentimiento value for this Solicitante.
     * 
     * @param consentimiento
     */
    public void setConsentimiento(es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Consentimiento consentimiento) {
        this.consentimiento = consentimiento;
    }


    /**
     * Gets the funcionario value for this Solicitante.
     * 
     * @return funcionario
     */
    public es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Funcionario getFuncionario() {
        return funcionario;
    }


    /**
     * Sets the funcionario value for this Solicitante.
     * 
     * @param funcionario
     */
    public void setFuncionario(es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Funcionario funcionario) {
        this.funcionario = funcionario;
    }


    /**
     * Gets the idExpediente value for this Solicitante.
     * 
     * @return idExpediente
     */
    public java.lang.String getIdExpediente() {
        return idExpediente;
    }


    /**
     * Sets the idExpediente value for this Solicitante.
     * 
     * @param idExpediente
     */
    public void setIdExpediente(java.lang.String idExpediente) {
        this.idExpediente = idExpediente;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Solicitante)) return false;
        Solicitante other = (Solicitante) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.identificadorSolicitante==null && other.getIdentificadorSolicitante()==null) || 
             (this.identificadorSolicitante!=null &&
              this.identificadorSolicitante.equals(other.getIdentificadorSolicitante()))) &&
            ((this.nombreSolicitante==null && other.getNombreSolicitante()==null) || 
             (this.nombreSolicitante!=null &&
              this.nombreSolicitante.equals(other.getNombreSolicitante()))) &&
            ((this.unidadTramitadora==null && other.getUnidadTramitadora()==null) || 
             (this.unidadTramitadora!=null &&
              this.unidadTramitadora.equals(other.getUnidadTramitadora()))) &&
            ((this.procedimiento==null && other.getProcedimiento()==null) || 
             (this.procedimiento!=null &&
              this.procedimiento.equals(other.getProcedimiento()))) &&
            ((this.finalidad==null && other.getFinalidad()==null) || 
             (this.finalidad!=null &&
              this.finalidad.equals(other.getFinalidad()))) &&
            ((this.consentimiento==null && other.getConsentimiento()==null) || 
             (this.consentimiento!=null &&
              this.consentimiento.equals(other.getConsentimiento()))) &&
            ((this.funcionario==null && other.getFuncionario()==null) || 
             (this.funcionario!=null &&
              this.funcionario.equals(other.getFuncionario()))) &&
            ((this.idExpediente==null && other.getIdExpediente()==null) || 
             (this.idExpediente!=null &&
              this.idExpediente.equals(other.getIdExpediente())));
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
        if (getIdentificadorSolicitante() != null) {
            _hashCode += getIdentificadorSolicitante().hashCode();
        }
        if (getNombreSolicitante() != null) {
            _hashCode += getNombreSolicitante().hashCode();
        }
        if (getUnidadTramitadora() != null) {
            _hashCode += getUnidadTramitadora().hashCode();
        }
        if (getProcedimiento() != null) {
            _hashCode += getProcedimiento().hashCode();
        }
        if (getFinalidad() != null) {
            _hashCode += getFinalidad().hashCode();
        }
        if (getConsentimiento() != null) {
            _hashCode += getConsentimiento().hashCode();
        }
        if (getFuncionario() != null) {
            _hashCode += getFuncionario().hashCode();
        }
        if (getIdExpediente() != null) {
            _hashCode += getIdExpediente().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Solicitante.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Solicitante"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificadorSolicitante");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "IdentificadorSolicitante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">IdentificadorSolicitante"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreSolicitante");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "NombreSolicitante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">NombreSolicitante"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unidadTramitadora");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "UnidadTramitadora"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">UnidadTramitadora"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("procedimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "Procedimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Procedimiento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finalidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "Finalidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Finalidad"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consentimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "Consentimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Consentimiento"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("funcionario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "Funcionario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">Funcionario"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idExpediente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", "IdExpediente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/ws/respuesta", ">IdExpediente"));
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
