/**
 * Via.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.GeoLocalizacionWebService;

public class Via  extends es.ieci.tecdoc.sigem.GeoLocalizacionWebService.RetornoServicio  implements java.io.Serializable {
    private java.lang.String claseNombre;

    private int codigoINEMunicipio;

    private es.ieci.tecdoc.sigem.GeoLocalizacionWebService.ConjuntoCoordenadas coordenadas;

    private java.lang.String estatus;

    private java.lang.String fuente;

    private int idVia;

    private java.lang.String idioma;

    private java.lang.String nombreVia;

    private es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Portales portales;

    private int provincia;

    private java.lang.String tipoVia;

    public Via() {
    }

    public Via(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String claseNombre,
           int codigoINEMunicipio,
           es.ieci.tecdoc.sigem.GeoLocalizacionWebService.ConjuntoCoordenadas coordenadas,
           java.lang.String estatus,
           java.lang.String fuente,
           int idVia,
           java.lang.String idioma,
           java.lang.String nombreVia,
           es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Portales portales,
           int provincia,
           java.lang.String tipoVia) {
        super(
            errorCode,
            returnCode);
        this.claseNombre = claseNombre;
        this.codigoINEMunicipio = codigoINEMunicipio;
        this.coordenadas = coordenadas;
        this.estatus = estatus;
        this.fuente = fuente;
        this.idVia = idVia;
        this.idioma = idioma;
        this.nombreVia = nombreVia;
        this.portales = portales;
        this.provincia = provincia;
        this.tipoVia = tipoVia;
    }


    /**
     * Gets the claseNombre value for this Via.
     * 
     * @return claseNombre
     */
    public java.lang.String getClaseNombre() {
        return claseNombre;
    }


    /**
     * Sets the claseNombre value for this Via.
     * 
     * @param claseNombre
     */
    public void setClaseNombre(java.lang.String claseNombre) {
        this.claseNombre = claseNombre;
    }


    /**
     * Gets the codigoINEMunicipio value for this Via.
     * 
     * @return codigoINEMunicipio
     */
    public int getCodigoINEMunicipio() {
        return codigoINEMunicipio;
    }


    /**
     * Sets the codigoINEMunicipio value for this Via.
     * 
     * @param codigoINEMunicipio
     */
    public void setCodigoINEMunicipio(int codigoINEMunicipio) {
        this.codigoINEMunicipio = codigoINEMunicipio;
    }


    /**
     * Gets the coordenadas value for this Via.
     * 
     * @return coordenadas
     */
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.ConjuntoCoordenadas getCoordenadas() {
        return coordenadas;
    }


    /**
     * Sets the coordenadas value for this Via.
     * 
     * @param coordenadas
     */
    public void setCoordenadas(es.ieci.tecdoc.sigem.GeoLocalizacionWebService.ConjuntoCoordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }


    /**
     * Gets the estatus value for this Via.
     * 
     * @return estatus
     */
    public java.lang.String getEstatus() {
        return estatus;
    }


    /**
     * Sets the estatus value for this Via.
     * 
     * @param estatus
     */
    public void setEstatus(java.lang.String estatus) {
        this.estatus = estatus;
    }


    /**
     * Gets the fuente value for this Via.
     * 
     * @return fuente
     */
    public java.lang.String getFuente() {
        return fuente;
    }


    /**
     * Sets the fuente value for this Via.
     * 
     * @param fuente
     */
    public void setFuente(java.lang.String fuente) {
        this.fuente = fuente;
    }


    /**
     * Gets the idVia value for this Via.
     * 
     * @return idVia
     */
    public int getIdVia() {
        return idVia;
    }


    /**
     * Sets the idVia value for this Via.
     * 
     * @param idVia
     */
    public void setIdVia(int idVia) {
        this.idVia = idVia;
    }


    /**
     * Gets the idioma value for this Via.
     * 
     * @return idioma
     */
    public java.lang.String getIdioma() {
        return idioma;
    }


    /**
     * Sets the idioma value for this Via.
     * 
     * @param idioma
     */
    public void setIdioma(java.lang.String idioma) {
        this.idioma = idioma;
    }


    /**
     * Gets the nombreVia value for this Via.
     * 
     * @return nombreVia
     */
    public java.lang.String getNombreVia() {
        return nombreVia;
    }


    /**
     * Sets the nombreVia value for this Via.
     * 
     * @param nombreVia
     */
    public void setNombreVia(java.lang.String nombreVia) {
        this.nombreVia = nombreVia;
    }


    /**
     * Gets the portales value for this Via.
     * 
     * @return portales
     */
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Portales getPortales() {
        return portales;
    }


    /**
     * Sets the portales value for this Via.
     * 
     * @param portales
     */
    public void setPortales(es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Portales portales) {
        this.portales = portales;
    }


    /**
     * Gets the provincia value for this Via.
     * 
     * @return provincia
     */
    public int getProvincia() {
        return provincia;
    }


    /**
     * Sets the provincia value for this Via.
     * 
     * @param provincia
     */
    public void setProvincia(int provincia) {
        this.provincia = provincia;
    }


    /**
     * Gets the tipoVia value for this Via.
     * 
     * @return tipoVia
     */
    public java.lang.String getTipoVia() {
        return tipoVia;
    }


    /**
     * Sets the tipoVia value for this Via.
     * 
     * @param tipoVia
     */
    public void setTipoVia(java.lang.String tipoVia) {
        this.tipoVia = tipoVia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Via)) return false;
        Via other = (Via) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.claseNombre==null && other.getClaseNombre()==null) || 
             (this.claseNombre!=null &&
              this.claseNombre.equals(other.getClaseNombre()))) &&
            this.codigoINEMunicipio == other.getCodigoINEMunicipio() &&
            ((this.coordenadas==null && other.getCoordenadas()==null) || 
             (this.coordenadas!=null &&
              this.coordenadas.equals(other.getCoordenadas()))) &&
            ((this.estatus==null && other.getEstatus()==null) || 
             (this.estatus!=null &&
              this.estatus.equals(other.getEstatus()))) &&
            ((this.fuente==null && other.getFuente()==null) || 
             (this.fuente!=null &&
              this.fuente.equals(other.getFuente()))) &&
            this.idVia == other.getIdVia() &&
            ((this.idioma==null && other.getIdioma()==null) || 
             (this.idioma!=null &&
              this.idioma.equals(other.getIdioma()))) &&
            ((this.nombreVia==null && other.getNombreVia()==null) || 
             (this.nombreVia!=null &&
              this.nombreVia.equals(other.getNombreVia()))) &&
            ((this.portales==null && other.getPortales()==null) || 
             (this.portales!=null &&
              this.portales.equals(other.getPortales()))) &&
            this.provincia == other.getProvincia() &&
            ((this.tipoVia==null && other.getTipoVia()==null) || 
             (this.tipoVia!=null &&
              this.tipoVia.equals(other.getTipoVia())));
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
        if (getClaseNombre() != null) {
            _hashCode += getClaseNombre().hashCode();
        }
        _hashCode += getCodigoINEMunicipio();
        if (getCoordenadas() != null) {
            _hashCode += getCoordenadas().hashCode();
        }
        if (getEstatus() != null) {
            _hashCode += getEstatus().hashCode();
        }
        if (getFuente() != null) {
            _hashCode += getFuente().hashCode();
        }
        _hashCode += getIdVia();
        if (getIdioma() != null) {
            _hashCode += getIdioma().hashCode();
        }
        if (getNombreVia() != null) {
            _hashCode += getNombreVia().hashCode();
        }
        if (getPortales() != null) {
            _hashCode += getPortales().hashCode();
        }
        _hashCode += getProvincia();
        if (getTipoVia() != null) {
            _hashCode += getTipoVia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Via.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Via"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claseNombre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "claseNombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoINEMunicipio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "codigoINEMunicipio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coordenadas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "coordenadas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "ConjuntoCoordenadas"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "estatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fuente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "fuente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idVia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "idVia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idioma");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "idioma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreVia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "nombreVia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portales");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "portales"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Portales"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provincia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "provincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoVia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "tipoVia"));
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
