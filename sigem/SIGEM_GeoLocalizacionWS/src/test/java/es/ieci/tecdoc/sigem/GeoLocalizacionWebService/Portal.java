/**
 * Portal.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.GeoLocalizacionWebService;

public class Portal  extends es.ieci.tecdoc.sigem.GeoLocalizacionWebService.RetornoServicio  implements java.io.Serializable {
    private java.lang.String claseNombre;

    private int codigoINEMunicipio;

    private es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Coordenada coords;

    private java.lang.String estatus;

    private java.lang.String fuente;

    private int idPortal;

    private int idVia;

    private java.lang.String idioma;

    private java.lang.String numPortal;

    private int provincia;

    private java.lang.String tipoPortal;

    public Portal() {
    }

    public Portal(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String claseNombre,
           int codigoINEMunicipio,
           es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Coordenada coords,
           java.lang.String estatus,
           java.lang.String fuente,
           int idPortal,
           int idVia,
           java.lang.String idioma,
           java.lang.String numPortal,
           int provincia,
           java.lang.String tipoPortal) {
        super(
            errorCode,
            returnCode);
        this.claseNombre = claseNombre;
        this.codigoINEMunicipio = codigoINEMunicipio;
        this.coords = coords;
        this.estatus = estatus;
        this.fuente = fuente;
        this.idPortal = idPortal;
        this.idVia = idVia;
        this.idioma = idioma;
        this.numPortal = numPortal;
        this.provincia = provincia;
        this.tipoPortal = tipoPortal;
    }


    /**
     * Gets the claseNombre value for this Portal.
     * 
     * @return claseNombre
     */
    public java.lang.String getClaseNombre() {
        return claseNombre;
    }


    /**
     * Sets the claseNombre value for this Portal.
     * 
     * @param claseNombre
     */
    public void setClaseNombre(java.lang.String claseNombre) {
        this.claseNombre = claseNombre;
    }


    /**
     * Gets the codigoINEMunicipio value for this Portal.
     * 
     * @return codigoINEMunicipio
     */
    public int getCodigoINEMunicipio() {
        return codigoINEMunicipio;
    }


    /**
     * Sets the codigoINEMunicipio value for this Portal.
     * 
     * @param codigoINEMunicipio
     */
    public void setCodigoINEMunicipio(int codigoINEMunicipio) {
        this.codigoINEMunicipio = codigoINEMunicipio;
    }


    /**
     * Gets the coords value for this Portal.
     * 
     * @return coords
     */
    public es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Coordenada getCoords() {
        return coords;
    }


    /**
     * Sets the coords value for this Portal.
     * 
     * @param coords
     */
    public void setCoords(es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Coordenada coords) {
        this.coords = coords;
    }


    /**
     * Gets the estatus value for this Portal.
     * 
     * @return estatus
     */
    public java.lang.String getEstatus() {
        return estatus;
    }


    /**
     * Sets the estatus value for this Portal.
     * 
     * @param estatus
     */
    public void setEstatus(java.lang.String estatus) {
        this.estatus = estatus;
    }


    /**
     * Gets the fuente value for this Portal.
     * 
     * @return fuente
     */
    public java.lang.String getFuente() {
        return fuente;
    }


    /**
     * Sets the fuente value for this Portal.
     * 
     * @param fuente
     */
    public void setFuente(java.lang.String fuente) {
        this.fuente = fuente;
    }


    /**
     * Gets the idPortal value for this Portal.
     * 
     * @return idPortal
     */
    public int getIdPortal() {
        return idPortal;
    }


    /**
     * Sets the idPortal value for this Portal.
     * 
     * @param idPortal
     */
    public void setIdPortal(int idPortal) {
        this.idPortal = idPortal;
    }


    /**
     * Gets the idVia value for this Portal.
     * 
     * @return idVia
     */
    public int getIdVia() {
        return idVia;
    }


    /**
     * Sets the idVia value for this Portal.
     * 
     * @param idVia
     */
    public void setIdVia(int idVia) {
        this.idVia = idVia;
    }


    /**
     * Gets the idioma value for this Portal.
     * 
     * @return idioma
     */
    public java.lang.String getIdioma() {
        return idioma;
    }


    /**
     * Sets the idioma value for this Portal.
     * 
     * @param idioma
     */
    public void setIdioma(java.lang.String idioma) {
        this.idioma = idioma;
    }


    /**
     * Gets the numPortal value for this Portal.
     * 
     * @return numPortal
     */
    public java.lang.String getNumPortal() {
        return numPortal;
    }


    /**
     * Sets the numPortal value for this Portal.
     * 
     * @param numPortal
     */
    public void setNumPortal(java.lang.String numPortal) {
        this.numPortal = numPortal;
    }


    /**
     * Gets the provincia value for this Portal.
     * 
     * @return provincia
     */
    public int getProvincia() {
        return provincia;
    }


    /**
     * Sets the provincia value for this Portal.
     * 
     * @param provincia
     */
    public void setProvincia(int provincia) {
        this.provincia = provincia;
    }


    /**
     * Gets the tipoPortal value for this Portal.
     * 
     * @return tipoPortal
     */
    public java.lang.String getTipoPortal() {
        return tipoPortal;
    }


    /**
     * Sets the tipoPortal value for this Portal.
     * 
     * @param tipoPortal
     */
    public void setTipoPortal(java.lang.String tipoPortal) {
        this.tipoPortal = tipoPortal;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Portal)) return false;
        Portal other = (Portal) obj;
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
            ((this.coords==null && other.getCoords()==null) || 
             (this.coords!=null &&
              this.coords.equals(other.getCoords()))) &&
            ((this.estatus==null && other.getEstatus()==null) || 
             (this.estatus!=null &&
              this.estatus.equals(other.getEstatus()))) &&
            ((this.fuente==null && other.getFuente()==null) || 
             (this.fuente!=null &&
              this.fuente.equals(other.getFuente()))) &&
            this.idPortal == other.getIdPortal() &&
            this.idVia == other.getIdVia() &&
            ((this.idioma==null && other.getIdioma()==null) || 
             (this.idioma!=null &&
              this.idioma.equals(other.getIdioma()))) &&
            ((this.numPortal==null && other.getNumPortal()==null) || 
             (this.numPortal!=null &&
              this.numPortal.equals(other.getNumPortal()))) &&
            this.provincia == other.getProvincia() &&
            ((this.tipoPortal==null && other.getTipoPortal()==null) || 
             (this.tipoPortal!=null &&
              this.tipoPortal.equals(other.getTipoPortal())));
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
        if (getCoords() != null) {
            _hashCode += getCoords().hashCode();
        }
        if (getEstatus() != null) {
            _hashCode += getEstatus().hashCode();
        }
        if (getFuente() != null) {
            _hashCode += getFuente().hashCode();
        }
        _hashCode += getIdPortal();
        _hashCode += getIdVia();
        if (getIdioma() != null) {
            _hashCode += getIdioma().hashCode();
        }
        if (getNumPortal() != null) {
            _hashCode += getNumPortal().hashCode();
        }
        _hashCode += getProvincia();
        if (getTipoPortal() != null) {
            _hashCode += getTipoPortal().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Portal.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Portal"));
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
        elemField.setFieldName("coords");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "coords"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Coordenada"));
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
        elemField.setFieldName("idPortal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "idPortal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
        elemField.setFieldName("numPortal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "numPortal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provincia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "provincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoPortal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "tipoPortal"));
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
