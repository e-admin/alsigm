/**
 * Tramites.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.CatalogoTramitesWebService;

public class Tramites  extends es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio  implements java.io.Serializable {
    private es.ieci.tecdoc.sigem.CatalogoTramitesWebService.ArrayOfTramite tramites;

    public Tramites() {
    }

    public Tramites(
           java.lang.String errorCode,
           java.lang.String returnCode,
           es.ieci.tecdoc.sigem.CatalogoTramitesWebService.ArrayOfTramite tramites) {
        super(
            errorCode,
            returnCode);
        this.tramites = tramites;
    }


    /**
     * Gets the tramites value for this Tramites.
     * 
     * @return tramites
     */
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.ArrayOfTramite getTramites() {
        return tramites;
    }


    /**
     * Sets the tramites value for this Tramites.
     * 
     * @param tramites
     */
    public void setTramites(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.ArrayOfTramite tramites) {
        this.tramites = tramites;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Tramites)) return false;
        Tramites other = (Tramites) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.tramites==null && other.getTramites()==null) || 
             (this.tramites!=null &&
              this.tramites.equals(other.getTramites())));
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
        if (getTramites() != null) {
            _hashCode += getTramites().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Tramites.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "Tramites"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tramites");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "tramites"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "ArrayOfTramite"));
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
