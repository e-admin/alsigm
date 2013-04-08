/**
 * RegistroFirma.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.cripto.firma.ws.server;


import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class RegistroFirma  extends RetornoServicio {
	
	  private java.lang.String idRegistro;
	  
	 public RegistroFirma() {
	    }

	    public RegistroFirma(java.lang.String idRegistro) {
	       
	        this.idRegistro = idRegistro;
	    }


	    /**
	     * Gets the idRegistro value for this RegistroFirma.
	     * 
	     * @return idRegistro
	     */
	    public java.lang.String getIdRegistro() {
	        return idRegistro;
	    }


	    /**
	     * Sets the idRegistro value for this RegistroFirma.
	     * 
	     * @param idRegistro
	     */
	    public void setIdRegistro(java.lang.String idRegistro) {
	        this.idRegistro = idRegistro;
	    }

	    private java.lang.Object __equalsCalc = null;
	    public synchronized boolean equals(java.lang.Object obj) {
	        if (!(obj instanceof RegistroFirma)) return false;
	        RegistroFirma other = (RegistroFirma) obj;
	        if (obj == null) return false;
	        if (this == obj) return true;
	        if (__equalsCalc != null) {
	            return (__equalsCalc == obj);
	        }
	        __equalsCalc = obj;
	        boolean _equals;
	        _equals = super.equals(obj) && 
	            ((this.idRegistro==null && other.getIdRegistro()==null) || 
	             (this.idRegistro!=null &&
	              this.idRegistro.equals(other.getIdRegistro())));
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
	        if (getIdRegistro() != null) {
	            _hashCode += getIdRegistro().hashCode();
	        }
	        __hashCodeCalc = false;
	        return _hashCode;
	    }

	    // Type metadata
	    private static org.apache.axis.description.TypeDesc typeDesc =
	        new org.apache.axis.description.TypeDesc(RegistroFirma.class, true);

	    static {
	        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "RegistroFirma"));
	        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("idRegistro");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.firma.cripto.sgm.tecdoc.ieci", "idRegistro"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
