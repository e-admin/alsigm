/**
 * ConfirmacionAltaNotificacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.nt.sisnot.client.types.map;

public class ConfirmacionAltaNotificacion  implements java.io.Serializable {
    private java.lang.String codError;

    private java.lang.String idUsuario;

    private java.lang.Long ncc;

    public ConfirmacionAltaNotificacion() {
    }

    public ConfirmacionAltaNotificacion(
           java.lang.String codError,
           java.lang.String idUsuario,
           java.lang.Long ncc) {
           this.codError = codError;
           this.idUsuario = idUsuario;
           this.ncc = ncc;
    }


    /**
     * Gets the codError value for this ConfirmacionAltaNotificacion.
     * 
     * @return codError
     */
    public java.lang.String getCodError() {
        return codError;
    }


    /**
     * Sets the codError value for this ConfirmacionAltaNotificacion.
     * 
     * @param codError
     */
    public void setCodError(java.lang.String codError) {
        this.codError = codError;
    }


    /**
     * Gets the idUsuario value for this ConfirmacionAltaNotificacion.
     * 
     * @return idUsuario
     */
    public java.lang.String getIdUsuario() {
        return idUsuario;
    }


    /**
     * Sets the idUsuario value for this ConfirmacionAltaNotificacion.
     * 
     * @param idUsuario
     */
    public void setIdUsuario(java.lang.String idUsuario) {
        this.idUsuario = idUsuario;
    }


    /**
     * Gets the ncc value for this ConfirmacionAltaNotificacion.
     * 
     * @return ncc
     */
    public java.lang.Long getNcc() {
        return ncc;
    }


    /**
     * Sets the ncc value for this ConfirmacionAltaNotificacion.
     * 
     * @param ncc
     */
    public void setNcc(java.lang.Long ncc) {
        this.ncc = ncc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConfirmacionAltaNotificacion)) return false;
        ConfirmacionAltaNotificacion other = (ConfirmacionAltaNotificacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codError==null && other.getCodError()==null) || 
             (this.codError!=null &&
              this.codError.equals(other.getCodError()))) &&
            ((this.idUsuario==null && other.getIdUsuario()==null) || 
             (this.idUsuario!=null &&
              this.idUsuario.equals(other.getIdUsuario()))) &&
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
        if (getCodError() != null) {
            _hashCode += getCodError().hashCode();
        }
        if (getIdUsuario() != null) {
            _hashCode += getIdUsuario().hashCode();
        }
        if (getNcc() != null) {
            _hashCode += getNcc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConfirmacionAltaNotificacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://correspondencia.sn.map.es/jaws", "ConfirmacionAltaNotificacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codError");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUsuario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idUsuario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ncc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ncc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
