package ieci.tdw.ispac.services.ws.client.dto;

import ieci.tdw.ispac.services.dto.RetornoServicio;

import java.io.Serializable;

public class InfoOcupacion  extends RetornoServicio  implements Serializable {
    private long espacioOcupado;

    private long espacioTotal;

    private long numeroFicheros;

    public InfoOcupacion() {
    }

    public InfoOcupacion(
           java.lang.String errorCode,
           java.lang.String returnCode,
           long espacioOcupado,
           long espacioTotal,
           long numeroFicheros) {
        setErrorCode(errorCode);
        setReturnCode(returnCode);
        this.espacioOcupado = espacioOcupado;
        this.espacioTotal = espacioTotal;
        this.numeroFicheros = numeroFicheros;
    }


    /**
     * Gets the espacioOcupado value for this InfoOcupacion.
     * 
     * @return espacioOcupado
     */
    public long getEspacioOcupado() {
        return espacioOcupado;
    }


    /**
     * Sets the espacioOcupado value for this InfoOcupacion.
     * 
     * @param espacioOcupado
     */
    public void setEspacioOcupado(long espacioOcupado) {
        this.espacioOcupado = espacioOcupado;
    }


    /**
     * Gets the espacioTotal value for this InfoOcupacion.
     * 
     * @return espacioTotal
     */
    public long getEspacioTotal() {
        return espacioTotal;
    }


    /**
     * Sets the espacioTotal value for this InfoOcupacion.
     * 
     * @param espacioTotal
     */
    public void setEspacioTotal(long espacioTotal) {
        this.espacioTotal = espacioTotal;
    }


    /**
     * Gets the numeroFicheros value for this InfoOcupacion.
     * 
     * @return numeroFicheros
     */
    public long getNumeroFicheros() {
        return numeroFicheros;
    }


    /**
     * Sets the numeroFicheros value for this InfoOcupacion.
     * 
     * @param numeroFicheros
     */
    public void setNumeroFicheros(long numeroFicheros) {
        this.numeroFicheros = numeroFicheros;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InfoOcupacion)) return false;
        InfoOcupacion other = (InfoOcupacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.espacioOcupado == other.getEspacioOcupado() &&
            this.espacioTotal == other.getEspacioTotal() &&
            this.numeroFicheros == other.getNumeroFicheros();
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
        _hashCode += new Long(getEspacioOcupado()).hashCode();
        _hashCode += new Long(getEspacioTotal()).hashCode();
        _hashCode += new Long(getNumeroFicheros()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InfoOcupacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "InfoOcupacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("espacioOcupado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "espacioOcupado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("espacioTotal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "espacioTotal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroFicheros");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "numeroFicheros"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
