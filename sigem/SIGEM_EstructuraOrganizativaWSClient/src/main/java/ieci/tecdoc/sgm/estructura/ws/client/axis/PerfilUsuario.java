/**
 * PerfilUsuario.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public class PerfilUsuario  extends ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private int product;

    private int profile;

    private int userId;

    public PerfilUsuario() {
    }

    public PerfilUsuario(
           java.lang.String errorCode,
           java.lang.String returnCode,
           int product,
           int profile,
           int userId) {
        super(
            errorCode,
            returnCode);
        this.product = product;
        this.profile = profile;
        this.userId = userId;
    }


    /**
     * Gets the product value for this PerfilUsuario.
     * 
     * @return product
     */
    public int getProduct() {
        return product;
    }


    /**
     * Sets the product value for this PerfilUsuario.
     * 
     * @param product
     */
    public void setProduct(int product) {
        this.product = product;
    }


    /**
     * Gets the profile value for this PerfilUsuario.
     * 
     * @return profile
     */
    public int getProfile() {
        return profile;
    }


    /**
     * Sets the profile value for this PerfilUsuario.
     * 
     * @param profile
     */
    public void setProfile(int profile) {
        this.profile = profile;
    }


    /**
     * Gets the userId value for this PerfilUsuario.
     * 
     * @return userId
     */
    public int getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this PerfilUsuario.
     * 
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PerfilUsuario)) return false;
        PerfilUsuario other = (PerfilUsuario) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.product == other.getProduct() &&
            this.profile == other.getProfile() &&
            this.userId == other.getUserId();
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
        _hashCode += getProduct();
        _hashCode += getProfile();
        _hashCode += getUserId();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PerfilUsuario.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "PerfilUsuario"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("product");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "product"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profile");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "profile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "userId"));
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
