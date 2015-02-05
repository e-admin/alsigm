/**
 * PerfilesGenericos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService;

public class PerfilesGenericos  extends es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio  implements java.io.Serializable {
    private es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.PerfilesUsuario perfilesUsuario;

    public PerfilesGenericos() {
    }

    public PerfilesGenericos(
           java.lang.String errorCode,
           java.lang.String returnCode,
           es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.PerfilesUsuario perfilesUsuario) {
        super(
            errorCode,
            returnCode);
        this.perfilesUsuario = perfilesUsuario;
    }


    /**
     * Gets the perfilesUsuario value for this PerfilesGenericos.
     * 
     * @return perfilesUsuario
     */
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.PerfilesUsuario getPerfilesUsuario() {
        return perfilesUsuario;
    }


    /**
     * Sets the perfilesUsuario value for this PerfilesGenericos.
     * 
     * @param perfilesUsuario
     */
    public void setPerfilesUsuario(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.PerfilesUsuario perfilesUsuario) {
        this.perfilesUsuario = perfilesUsuario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PerfilesGenericos)) return false;
        PerfilesGenericos other = (PerfilesGenericos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.perfilesUsuario==null && other.getPerfilesUsuario()==null) || 
             (this.perfilesUsuario!=null &&
              this.perfilesUsuario.equals(other.getPerfilesUsuario())));
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
        if (getPerfilesUsuario() != null) {
            _hashCode += getPerfilesUsuario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PerfilesGenericos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "PerfilesGenericos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("perfilesUsuario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "perfilesUsuario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "PerfilesUsuario"));
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
