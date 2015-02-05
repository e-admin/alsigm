/**
 * InfoDocumentoCSVRetorno.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.gestioncsv.ws.client.axis;

public class InfoDocumentoCSVRetorno  extends ieci.tecdoc.sgm.core.services.dto.RetornoServicio  implements java.io.Serializable {

    private java.lang.String codigoAplicacion;

    private java.lang.String csv;

    private boolean disponible;

    private java.util.Calendar fechaCSV;

    private java.util.Calendar fechaCaducidad;

    private java.util.Calendar fechaCreacion;

    private java.lang.String id;

    private java.lang.String nombre;

    private java.lang.String nombreAplicacion;

    private java.lang.String tipoMime;

    public InfoDocumentoCSVRetorno() {
    }

    public InfoDocumentoCSVRetorno(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String codigoAplicacion,
           java.lang.String csv,
           boolean disponible,
           java.util.Calendar fechaCSV,
           java.util.Calendar fechaCaducidad,
           java.util.Calendar fechaCreacion,
           java.lang.String id,
           java.lang.String nombre,
           java.lang.String nombreAplicacion,
           java.lang.String tipoMime) {
        super();
        this.codigoAplicacion = codigoAplicacion;
        this.csv = csv;
        this.disponible = disponible;
        this.fechaCSV = fechaCSV;
        this.fechaCaducidad = fechaCaducidad;
        this.fechaCreacion = fechaCreacion;
        this.id = id;
        this.nombre = nombre;
        this.nombreAplicacion = nombreAplicacion;
        this.tipoMime = tipoMime;
    }


    /**
     * Gets the codigoAplicacion value for this InfoDocumentoCSVRetorno.
     *
     * @return codigoAplicacion
     */
    public java.lang.String getCodigoAplicacion() {
        return codigoAplicacion;
    }


    /**
     * Sets the codigoAplicacion value for this InfoDocumentoCSVRetorno.
     *
     * @param codigoAplicacion
     */
    public void setCodigoAplicacion(java.lang.String codigoAplicacion) {
        this.codigoAplicacion = codigoAplicacion;
    }


    /**
     * Gets the csv value for this InfoDocumentoCSVRetorno.
     *
     * @return csv
     */
    public java.lang.String getCsv() {
        return csv;
    }


    /**
     * Sets the csv value for this InfoDocumentoCSVRetorno.
     *
     * @param csv
     */
    public void setCsv(java.lang.String csv) {
        this.csv = csv;
    }


    /**
     * Gets the disponible value for this InfoDocumentoCSVRetorno.
     *
     * @return disponible
     */
    public boolean isDisponible() {
        return disponible;
    }


    /**
     * Sets the disponible value for this InfoDocumentoCSVRetorno.
     *
     * @param disponible
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }


    /**
     * Gets the fechaCSV value for this InfoDocumentoCSVRetorno.
     *
     * @return fechaCSV
     */
    public java.util.Calendar getFechaCSV() {
        return fechaCSV;
    }


    /**
     * Sets the fechaCSV value for this InfoDocumentoCSVRetorno.
     *
     * @param fechaCSV
     */
    public void setFechaCSV(java.util.Calendar fechaCSV) {
        this.fechaCSV = fechaCSV;
    }


    /**
     * Gets the fechaCaducidad value for this InfoDocumentoCSVRetorno.
     *
     * @return fechaCaducidad
     */
    public java.util.Calendar getFechaCaducidad() {
        return fechaCaducidad;
    }


    /**
     * Sets the fechaCaducidad value for this InfoDocumentoCSVRetorno.
     *
     * @param fechaCaducidad
     */
    public void setFechaCaducidad(java.util.Calendar fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }


    /**
     * Gets the fechaCreacion value for this InfoDocumentoCSVRetorno.
     *
     * @return fechaCreacion
     */
    public java.util.Calendar getFechaCreacion() {
        return fechaCreacion;
    }


    /**
     * Sets the fechaCreacion value for this InfoDocumentoCSVRetorno.
     *
     * @param fechaCreacion
     */
    public void setFechaCreacion(java.util.Calendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    /**
     * Gets the id value for this InfoDocumentoCSVRetorno.
     *
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this InfoDocumentoCSVRetorno.
     *
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the nombre value for this InfoDocumentoCSVRetorno.
     *
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this InfoDocumentoCSVRetorno.
     *
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }


    /**
     * Gets the nombreAplicacion value for this InfoDocumentoCSVRetorno.
     *
     * @return nombreAplicacion
     */
    public java.lang.String getNombreAplicacion() {
        return nombreAplicacion;
    }


    /**
     * Sets the nombreAplicacion value for this InfoDocumentoCSVRetorno.
     *
     * @param nombreAplicacion
     */
    public void setNombreAplicacion(java.lang.String nombreAplicacion) {
        this.nombreAplicacion = nombreAplicacion;
    }


    /**
     * Gets the tipoMime value for this InfoDocumentoCSVRetorno.
     *
     * @return tipoMime
     */
    public java.lang.String getTipoMime() {
        return tipoMime;
    }


    /**
     * Sets the tipoMime value for this InfoDocumentoCSVRetorno.
     *
     * @param tipoMime
     */
    public void setTipoMime(java.lang.String tipoMime) {
        this.tipoMime = tipoMime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InfoDocumentoCSVRetorno)) return false;
        InfoDocumentoCSVRetorno other = (InfoDocumentoCSVRetorno) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) &&
            ((this.codigoAplicacion==null && other.getCodigoAplicacion()==null) ||
             (this.codigoAplicacion!=null &&
              this.codigoAplicacion.equals(other.getCodigoAplicacion()))) &&
            ((this.csv==null && other.getCsv()==null) ||
             (this.csv!=null &&
              this.csv.equals(other.getCsv()))) &&
            this.disponible == other.isDisponible() &&
            ((this.fechaCSV==null && other.getFechaCSV()==null) ||
             (this.fechaCSV!=null &&
              this.fechaCSV.equals(other.getFechaCSV()))) &&
            ((this.fechaCaducidad==null && other.getFechaCaducidad()==null) ||
             (this.fechaCaducidad!=null &&
              this.fechaCaducidad.equals(other.getFechaCaducidad()))) &&
            ((this.fechaCreacion==null && other.getFechaCreacion()==null) ||
             (this.fechaCreacion!=null &&
              this.fechaCreacion.equals(other.getFechaCreacion()))) &&
            ((this.id==null && other.getId()==null) ||
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.nombre==null && other.getNombre()==null) ||
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre()))) &&
            ((this.nombreAplicacion==null && other.getNombreAplicacion()==null) ||
             (this.nombreAplicacion!=null &&
              this.nombreAplicacion.equals(other.getNombreAplicacion()))) &&
            ((this.tipoMime==null && other.getTipoMime()==null) ||
             (this.tipoMime!=null &&
              this.tipoMime.equals(other.getTipoMime())));
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
        if (getCodigoAplicacion() != null) {
            _hashCode += getCodigoAplicacion().hashCode();
        }
        if (getCsv() != null) {
            _hashCode += getCsv().hashCode();
        }
        _hashCode += (isDisponible() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getFechaCSV() != null) {
            _hashCode += getFechaCSV().hashCode();
        }
        if (getFechaCaducidad() != null) {
            _hashCode += getFechaCaducidad().hashCode();
        }
        if (getFechaCreacion() != null) {
            _hashCode += getFechaCreacion().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        if (getNombreAplicacion() != null) {
            _hashCode += getNombreAplicacion().hashCode();
        }
        if (getTipoMime() != null) {
            _hashCode += getTipoMime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InfoDocumentoCSVRetorno.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.server.ws.gestioncsv.sgm.tecdoc.ieci", "InfoDocumentoCSVRetorno"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoAplicacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.server.ws.gestioncsv.sgm.tecdoc.ieci", "codigoAplicacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("csv");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.server.ws.gestioncsv.sgm.tecdoc.ieci", "csv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disponible");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.server.ws.gestioncsv.sgm.tecdoc.ieci", "disponible"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaCSV");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.server.ws.gestioncsv.sgm.tecdoc.ieci", "fechaCSV"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaCaducidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.server.ws.gestioncsv.sgm.tecdoc.ieci", "fechaCaducidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaCreacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.server.ws.gestioncsv.sgm.tecdoc.ieci", "fechaCreacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.server.ws.gestioncsv.sgm.tecdoc.ieci", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.server.ws.gestioncsv.sgm.tecdoc.ieci", "nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreAplicacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.server.ws.gestioncsv.sgm.tecdoc.ieci", "nombreAplicacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoMime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.server.ws.gestioncsv.sgm.tecdoc.ieci", "tipoMime"));
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
