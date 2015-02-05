/**
 * EstadoNotificacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.nt.sisnot.client.types;

public class EstadoNotificacion  implements java.io.Serializable {
    private byte[] acuseRecibo;

    private java.lang.String codError;

    private java.lang.String estado;

    private java.util.Calendar fechaEstado;

    private java.lang.String motivoRechazo;

    private java.lang.String ncc;

    public EstadoNotificacion() {
    }

    public EstadoNotificacion(
           byte[] acuseRecibo,
           java.lang.String codError,
           java.lang.String estado,
           java.util.Calendar fechaEstado,
           java.lang.String motivoRechazo,
           java.lang.String ncc) {
           this.acuseRecibo = acuseRecibo;
           this.codError = codError;
           this.estado = estado;
           this.fechaEstado = fechaEstado;
           this.motivoRechazo = motivoRechazo;
           this.ncc = ncc;
    }


    /**
     * Gets the acuseRecibo value for this EstadoNotificacion.
     * 
     * @return acuseRecibo
     */
    public byte[] getAcuseRecibo() {
        return acuseRecibo;
    }


    /**
     * Sets the acuseRecibo value for this EstadoNotificacion.
     * 
     * @param acuseRecibo
     */
    public void setAcuseRecibo(byte[] acuseRecibo) {
        this.acuseRecibo = acuseRecibo;
    }


    /**
     * Gets the codError value for this EstadoNotificacion.
     * 
     * @return codError
     */
    public java.lang.String getCodError() {
        return codError;
    }


    /**
     * Sets the codError value for this EstadoNotificacion.
     * 
     * @param codError
     */
    public void setCodError(java.lang.String codError) {
        this.codError = codError;
    }


    /**
     * Gets the estado value for this EstadoNotificacion.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this EstadoNotificacion.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }


    /**
     * Gets the fechaEstado value for this EstadoNotificacion.
     * 
     * @return fechaEstado
     */
    public java.util.Calendar getFechaEstado() {
        return fechaEstado;
    }


    /**
     * Sets the fechaEstado value for this EstadoNotificacion.
     * 
     * @param fechaEstado
     */
    public void setFechaEstado(java.util.Calendar fechaEstado) {
        this.fechaEstado = fechaEstado;
    }


    /**
     * Gets the motivoRechazo value for this EstadoNotificacion.
     * 
     * @return motivoRechazo
     */
    public java.lang.String getMotivoRechazo() {
        return motivoRechazo;
    }


    /**
     * Sets the motivoRechazo value for this EstadoNotificacion.
     * 
     * @param motivoRechazo
     */
    public void setMotivoRechazo(java.lang.String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }


    /**
     * Gets the ncc value for this EstadoNotificacion.
     * 
     * @return ncc
     */
    public java.lang.String getNcc() {
        return ncc;
    }


    /**
     * Sets the ncc value for this EstadoNotificacion.
     * 
     * @param ncc
     */
    public void setNcc(java.lang.String ncc) {
        this.ncc = ncc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EstadoNotificacion)) return false;
        EstadoNotificacion other = (EstadoNotificacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.acuseRecibo==null && other.getAcuseRecibo()==null) || 
             (this.acuseRecibo!=null &&
              java.util.Arrays.equals(this.acuseRecibo, other.getAcuseRecibo()))) &&
            ((this.codError==null && other.getCodError()==null) || 
             (this.codError!=null &&
              this.codError.equals(other.getCodError()))) &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.fechaEstado==null && other.getFechaEstado()==null) || 
             (this.fechaEstado!=null &&
              this.fechaEstado.equals(other.getFechaEstado()))) &&
            ((this.motivoRechazo==null && other.getMotivoRechazo()==null) || 
             (this.motivoRechazo!=null &&
              this.motivoRechazo.equals(other.getMotivoRechazo()))) &&
            ((this.ncc==null && other.getNcc()==null) || 
             (this.ncc!=null &&
              this.ncc.equals(other.getNcc())));
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
        if (getAcuseRecibo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAcuseRecibo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAcuseRecibo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCodError() != null) {
            _hashCode += getCodError().hashCode();
        }
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getFechaEstado() != null) {
            _hashCode += getFechaEstado().hashCode();
        }
        if (getMotivoRechazo() != null) {
            _hashCode += getMotivoRechazo().hashCode();
        }
        if (getNcc() != null) {
            _hashCode += getNcc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EstadoNotificacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://org.jboss.ws/samples/rpcstyle/types", "EstadoNotificacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acuseRecibo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "acuseRecibo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codError");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaEstado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaEstado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("motivoRechazo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "motivoRechazo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ncc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ncc"));
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