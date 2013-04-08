/**
 * Subsanaciones.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.ConsultaExpedientesWebService;

public class Subsanaciones  extends es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio  implements java.io.Serializable {
    private es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ArrayOfSubsanacion subsanaciones;

    public Subsanaciones() {
    }

    public Subsanaciones(
           java.lang.String errorCode,
           java.lang.String returnCode,
           es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ArrayOfSubsanacion subsanaciones) {
        super(
            errorCode,
            returnCode);
        this.subsanaciones = subsanaciones;
    }


    /**
     * Gets the subsanaciones value for this Subsanaciones.
     * 
     * @return subsanaciones
     */
    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ArrayOfSubsanacion getSubsanaciones() {
        return subsanaciones;
    }


    /**
     * Sets the subsanaciones value for this Subsanaciones.
     * 
     * @param subsanaciones
     */
    public void setSubsanaciones(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ArrayOfSubsanacion subsanaciones) {
        this.subsanaciones = subsanaciones;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Subsanaciones)) return false;
        Subsanaciones other = (Subsanaciones) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.subsanaciones==null && other.getSubsanaciones()==null) || 
             (this.subsanaciones!=null &&
              this.subsanaciones.equals(other.getSubsanaciones())));
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
        if (getSubsanaciones() != null) {
            _hashCode += getSubsanaciones().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Subsanaciones.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "Subsanaciones"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subsanaciones");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "subsanaciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "ArrayOfSubsanacion"));
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
