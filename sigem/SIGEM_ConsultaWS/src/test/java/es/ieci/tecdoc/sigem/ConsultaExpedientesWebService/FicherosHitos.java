/**
 * FicherosHitos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.ConsultaExpedientesWebService;

public class FicherosHitos  extends es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio  implements java.io.Serializable {
    private es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ArrayOfFicherosHito ficherosHitos;

    public FicherosHitos() {
    }

    public FicherosHitos(
           java.lang.String errorCode,
           java.lang.String returnCode,
           es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ArrayOfFicherosHito ficherosHitos) {
        super(
            errorCode,
            returnCode);
        this.ficherosHitos = ficherosHitos;
    }


    /**
     * Gets the ficherosHitos value for this FicherosHitos.
     * 
     * @return ficherosHitos
     */
    public es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ArrayOfFicherosHito getFicherosHitos() {
        return ficherosHitos;
    }


    /**
     * Sets the ficherosHitos value for this FicherosHitos.
     * 
     * @param ficherosHitos
     */
    public void setFicherosHitos(es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ArrayOfFicherosHito ficherosHitos) {
        this.ficherosHitos = ficherosHitos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FicherosHitos)) return false;
        FicherosHitos other = (FicherosHitos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.ficherosHitos==null && other.getFicherosHitos()==null) || 
             (this.ficherosHitos!=null &&
              this.ficherosHitos.equals(other.getFicherosHitos())));
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
        if (getFicherosHitos() != null) {
            _hashCode += getFicherosHitos().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FicherosHitos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "FicherosHitos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ficherosHitos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "ficherosHitos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.ct.sgm.tecdoc.ieci", "ArrayOfFicherosHito"));
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
