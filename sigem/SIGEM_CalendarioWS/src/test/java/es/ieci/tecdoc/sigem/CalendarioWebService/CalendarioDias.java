/** 
 * CalendarioDias.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.CalendarioWebService;

public class CalendarioDias  extends es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio  implements java.io.Serializable {
    private es.ieci.tecdoc.sigem.CalendarioWebService.ArrayOfCalendarioDia dias;

    public CalendarioDias() {
    }

    public CalendarioDias(
           java.lang.String errorCode,
           java.lang.String returnCode,
           es.ieci.tecdoc.sigem.CalendarioWebService.ArrayOfCalendarioDia dias) {
        super(
            errorCode,
            returnCode);
        this.dias = dias;
    }


    /**
     * Gets the dias value for this CalendarioDias.
     * 
     * @return dias
     */
    public es.ieci.tecdoc.sigem.CalendarioWebService.ArrayOfCalendarioDia getDias() {
        return dias;
    }


    /**
     * Sets the dias value for this CalendarioDias.
     * 
     * @param dias
     */
    public void setDias(es.ieci.tecdoc.sigem.CalendarioWebService.ArrayOfCalendarioDia dias) {
        this.dias = dias;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CalendarioDias)) return false;
        CalendarioDias other = (CalendarioDias) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dias==null && other.getDias()==null) || 
             (this.dias!=null &&
              this.dias.equals(other.getDias())));
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
        if (getDias() != null) {
            _hashCode += getDias().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CalendarioDias.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "CalendarioDias"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dias");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "dias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "ArrayOfCalendarioDia"));
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
