/**
 * LoginExternalUser.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.autenticacion.ws.client;

public class LoginExternalUser  implements java.io.Serializable {
    private java.lang.String actSessionId;

    private java.lang.String apellidos;

    private java.lang.String email;

    private java.lang.String nombre;

    private java.lang.String senderId;

    public LoginExternalUser() {
    }

    public LoginExternalUser(
           java.lang.String actSessionId,
           java.lang.String apellidos,
           java.lang.String email,
           java.lang.String nombre,
           java.lang.String senderId) {
           this.actSessionId = actSessionId;
           this.apellidos = apellidos;
           this.email = email;
           this.nombre = nombre;
           this.senderId = senderId;
    }


    /**
     * Gets the actSessionId value for this LoginExternalUser.
     * 
     * @return actSessionId
     */
    public java.lang.String getActSessionId() {
        return actSessionId;
    }


    /**
     * Sets the actSessionId value for this LoginExternalUser.
     * 
     * @param actSessionId
     */
    public void setActSessionId(java.lang.String actSessionId) {
        this.actSessionId = actSessionId;
    }


    /**
     * Gets the apellidos value for this LoginExternalUser.
     * 
     * @return apellidos
     */
    public java.lang.String getApellidos() {
        return apellidos;
    }


    /**
     * Sets the apellidos value for this LoginExternalUser.
     * 
     * @param apellidos
     */
    public void setApellidos(java.lang.String apellidos) {
        this.apellidos = apellidos;
    }


    /**
     * Gets the email value for this LoginExternalUser.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this LoginExternalUser.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }


    /**
     * Gets the nombre value for this LoginExternalUser.
     * 
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this LoginExternalUser.
     * 
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }


    /**
     * Gets the senderId value for this LoginExternalUser.
     * 
     * @return senderId
     */
    public java.lang.String getSenderId() {
        return senderId;
    }


    /**
     * Sets the senderId value for this LoginExternalUser.
     * 
     * @param senderId
     */
    public void setSenderId(java.lang.String senderId) {
        this.senderId = senderId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoginExternalUser)) return false;
        LoginExternalUser other = (LoginExternalUser) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.actSessionId==null && other.getActSessionId()==null) || 
             (this.actSessionId!=null &&
              this.actSessionId.equals(other.getActSessionId()))) &&
            ((this.apellidos==null && other.getApellidos()==null) || 
             (this.apellidos!=null &&
              this.apellidos.equals(other.getApellidos()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.nombre==null && other.getNombre()==null) || 
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre()))) &&
            ((this.senderId==null && other.getSenderId()==null) || 
             (this.senderId!=null &&
              this.senderId.equals(other.getSenderId())));
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
        if (getActSessionId() != null) {
            _hashCode += getActSessionId().hashCode();
        }
        if (getApellidos() != null) {
            _hashCode += getApellidos().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        if (getSenderId() != null) {
            _hashCode += getSenderId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LoginExternalUser.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "LoginExternalUser"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actSessionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "actSessionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("apellidos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "apellidos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("senderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "senderId"));
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
