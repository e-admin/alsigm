/**
 * ResultadoValidacionFirma.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.ws.client;

public class ResultadoValidacionFirma  extends ieci.tecdoc.sgm.cripto.firma.ws.client.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.cripto.firma.ws.client.Firmante[] firmantes;

    private java.lang.String resultadoValidacion;

    public ResultadoValidacionFirma() {
    }

    public ResultadoValidacionFirma(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.cripto.firma.ws.client.Firmante[] firmantes,
           java.lang.String resultadoValidacion) {
        super(
            errorCode,
            returnCode);
        this.firmantes = firmantes;
        this.resultadoValidacion = resultadoValidacion;
    }


    /**
     * Gets the firmantes value for this ResultadoValidacionFirma.
     * 
     * @return firmantes
     */
    public ieci.tecdoc.sgm.cripto.firma.ws.client.Firmante[] getFirmantes() {
        return firmantes;
    }


    /**
     * Sets the firmantes value for this ResultadoValidacionFirma.
     * 
     * @param firmantes
     */
    public void setFirmantes(ieci.tecdoc.sgm.cripto.firma.ws.client.Firmante[] firmantes) {
        this.firmantes = firmantes;
    }


    /**
     * Gets the resultadoValidacion value for this ResultadoValidacionFirma.
     * 
     * @return resultadoValidacion
     */
    public java.lang.String getResultadoValidacion() {
        return resultadoValidacion;
    }


    /**
     * Sets the resultadoValidacion value for this ResultadoValidacionFirma.
     * 
     * @param resultadoValidacion
     */
    public void setResultadoValidacion(java.lang.String resultadoValidacion) {
        this.resultadoValidacion = resultadoValidacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResultadoValidacionFirma)) return false;
        ResultadoValidacionFirma other = (ResultadoValidacionFirma) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.firmantes==null && other.getFirmantes()==null) || 
             (this.firmantes!=null &&
              java.util.Arrays.equals(this.firmantes, other.getFirmantes()))) &&
            ((this.resultadoValidacion==null && other.getResultadoValidacion()==null) || 
             (this.resultadoValidacion!=null &&
              this.resultadoValidacion.equals(other.getResultadoValidacion())));
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
        if (getFirmantes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFirmantes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFirmantes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getResultadoValidacion() != null) {
            _hashCode += getResultadoValidacion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResultadoValidacionFirma.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "ResultadoValidacionFirma"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firmantes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "firmantes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "Firmante"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultadoValidacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "resultadoValidacion"));
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
