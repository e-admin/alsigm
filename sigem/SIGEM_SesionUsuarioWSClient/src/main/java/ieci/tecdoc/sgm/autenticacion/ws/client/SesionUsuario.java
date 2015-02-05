/**
 * SesionUsuario.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.autenticacion.ws.client;

public class SesionUsuario  extends ieci.tecdoc.sgm.autenticacion.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String hookId;

    private java.lang.String loginDate;

    private ieci.tecdoc.sgm.autenticacion.ws.client.InfoUsuario sender;

    private java.lang.String sessionId;

    public SesionUsuario() {
    }

    public SesionUsuario(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String hookId,
           java.lang.String loginDate,
           ieci.tecdoc.sgm.autenticacion.ws.client.InfoUsuario sender,
           java.lang.String sessionId) {
        super(
            errorCode,
            returnCode);
        this.hookId = hookId;
        this.loginDate = loginDate;
        this.sender = sender;
        this.sessionId = sessionId;
    }


    /**
     * Gets the hookId value for this SesionUsuario.
     * 
     * @return hookId
     */
    public java.lang.String getHookId() {
        return hookId;
    }


    /**
     * Sets the hookId value for this SesionUsuario.
     * 
     * @param hookId
     */
    public void setHookId(java.lang.String hookId) {
        this.hookId = hookId;
    }


    /**
     * Gets the loginDate value for this SesionUsuario.
     * 
     * @return loginDate
     */
    public java.lang.String getLoginDate() {
        return loginDate;
    }


    /**
     * Sets the loginDate value for this SesionUsuario.
     * 
     * @param loginDate
     */
    public void setLoginDate(java.lang.String loginDate) {
        this.loginDate = loginDate;
    }


    /**
     * Gets the sender value for this SesionUsuario.
     * 
     * @return sender
     */
    public ieci.tecdoc.sgm.autenticacion.ws.client.InfoUsuario getSender() {
        return sender;
    }


    /**
     * Sets the sender value for this SesionUsuario.
     * 
     * @param sender
     */
    public void setSender(ieci.tecdoc.sgm.autenticacion.ws.client.InfoUsuario sender) {
        this.sender = sender;
    }


    /**
     * Gets the sessionId value for this SesionUsuario.
     * 
     * @return sessionId
     */
    public java.lang.String getSessionId() {
        return sessionId;
    }


    /**
     * Sets the sessionId value for this SesionUsuario.
     * 
     * @param sessionId
     */
    public void setSessionId(java.lang.String sessionId) {
        this.sessionId = sessionId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SesionUsuario)) return false;
        SesionUsuario other = (SesionUsuario) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.hookId==null && other.getHookId()==null) || 
             (this.hookId!=null &&
              this.hookId.equals(other.getHookId()))) &&
            ((this.loginDate==null && other.getLoginDate()==null) || 
             (this.loginDate!=null &&
              this.loginDate.equals(other.getLoginDate()))) &&
            ((this.sender==null && other.getSender()==null) || 
             (this.sender!=null &&
              this.sender.equals(other.getSender()))) &&
            ((this.sessionId==null && other.getSessionId()==null) || 
             (this.sessionId!=null &&
              this.sessionId.equals(other.getSessionId())));
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
        if (getHookId() != null) {
            _hashCode += getHookId().hashCode();
        }
        if (getLoginDate() != null) {
            _hashCode += getLoginDate().hashCode();
        }
        if (getSender() != null) {
            _hashCode += getSender().hashCode();
        }
        if (getSessionId() != null) {
            _hashCode += getSessionId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SesionUsuario.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "SesionUsuario"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hookId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "hookId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "loginDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sender");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "sender"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "InfoUsuario"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.autenticacion.sgm.tecdoc.ieci", "sessionId"));
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
