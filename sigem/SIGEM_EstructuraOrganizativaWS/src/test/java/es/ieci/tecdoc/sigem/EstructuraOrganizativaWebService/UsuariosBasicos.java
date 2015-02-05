/**
 * UsuariosBasicos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService;

public class UsuariosBasicos  extends es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio  implements java.io.Serializable {
    private es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArrayOfUsuarioBasico usuariosBasicos;

    public UsuariosBasicos() {
    }

    public UsuariosBasicos(
           java.lang.String errorCode,
           java.lang.String returnCode,
           es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArrayOfUsuarioBasico usuariosBasicos) {
        super(
            errorCode,
            returnCode);
        this.usuariosBasicos = usuariosBasicos;
    }


    /**
     * Gets the usuariosBasicos value for this UsuariosBasicos.
     * 
     * @return usuariosBasicos
     */
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArrayOfUsuarioBasico getUsuariosBasicos() {
        return usuariosBasicos;
    }


    /**
     * Sets the usuariosBasicos value for this UsuariosBasicos.
     * 
     * @param usuariosBasicos
     */
    public void setUsuariosBasicos(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.ArrayOfUsuarioBasico usuariosBasicos) {
        this.usuariosBasicos = usuariosBasicos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UsuariosBasicos)) return false;
        UsuariosBasicos other = (UsuariosBasicos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.usuariosBasicos==null && other.getUsuariosBasicos()==null) || 
             (this.usuariosBasicos!=null &&
              this.usuariosBasicos.equals(other.getUsuariosBasicos())));
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
        if (getUsuariosBasicos() != null) {
            _hashCode += getUsuariosBasicos().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UsuariosBasicos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuariosBasicos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuariosBasicos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "usuariosBasicos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArrayOfUsuarioBasico"));
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
