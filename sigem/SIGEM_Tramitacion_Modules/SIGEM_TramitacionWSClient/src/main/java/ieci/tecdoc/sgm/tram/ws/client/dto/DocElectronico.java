package ieci.tecdoc.sgm.tram.ws.client.dto;

import java.io.Serializable;

public class DocElectronico  extends DocFisico  implements Serializable {
	
    private String extension;

    private String localizador;

    private String repositorio;

    public DocElectronico() {
    }

    public DocElectronico(
           String asunto,
           String tipoDocumento,
           String extension,
           String localizador,
           String repositorio) {
        super(
            asunto,
            tipoDocumento);
        this.extension = extension;
        this.localizador = localizador;
        this.repositorio = repositorio;
    }


    /**
     * Gets the extension value for this DocElectronico.
     * 
     * @return extension
     */
    public String getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this DocElectronico.
     * 
     * @param extension
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }


    /**
     * Gets the localizador value for this DocElectronico.
     * 
     * @return localizador
     */
    public String getLocalizador() {
        return localizador;
    }


    /**
     * Sets the localizador value for this DocElectronico.
     * 
     * @param localizador
     */
    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }


    /**
     * Gets the repositorio value for this DocElectronico.
     * 
     * @return repositorio
     */
    public String getRepositorio() {
        return repositorio;
    }


    /**
     * Sets the repositorio value for this DocElectronico.
     * 
     * @param repositorio
     */
    public void setRepositorio(String repositorio) {
        this.repositorio = repositorio;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof DocElectronico)) return false;
        DocElectronico other = (DocElectronico) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.extension==null && other.getExtension()==null) || 
             (this.extension!=null &&
              this.extension.equals(other.getExtension()))) &&
            ((this.localizador==null && other.getLocalizador()==null) || 
             (this.localizador!=null &&
              this.localizador.equals(other.getLocalizador()))) &&
            ((this.repositorio==null && other.getRepositorio()==null) || 
             (this.repositorio!=null &&
              this.repositorio.equals(other.getRepositorio())));
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
        if (getExtension() != null) {
            _hashCode += getExtension().hashCode();
        }
        if (getLocalizador() != null) {
            _hashCode += getLocalizador().hashCode();
        }
        if (getRepositorio() != null) {
            _hashCode += getRepositorio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocElectronico.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "DocElectronico"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "extension"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localizador");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "localizador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("repositorio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "repositorio"));
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
           String mechType, 
           Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType, 
           Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
