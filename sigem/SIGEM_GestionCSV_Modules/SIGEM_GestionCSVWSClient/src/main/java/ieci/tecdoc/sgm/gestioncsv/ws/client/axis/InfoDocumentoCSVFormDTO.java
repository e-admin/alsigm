/**
 * InfoDocumentoCSVFormDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.gestioncsv.ws.client.axis;

public class InfoDocumentoCSVFormDTO  implements java.io.Serializable {

    private java.lang.String codigoAplicacion;

    private boolean disponible;

    private java.util.Calendar fechaCaducidad;

    private java.util.Calendar fechaCreacion;

    private java.lang.String nombre;

    private java.lang.String tipoMime;

    public InfoDocumentoCSVFormDTO() {
    }

    public InfoDocumentoCSVFormDTO(
           java.lang.String codigoAplicacion,
           boolean disponible,
           java.util.Calendar fechaCaducidad,
           java.util.Calendar fechaCreacion,
           java.lang.String nombre,
           java.lang.String tipoMime) {
           this.codigoAplicacion = codigoAplicacion;
           this.disponible = disponible;
           this.fechaCaducidad = fechaCaducidad;
           this.fechaCreacion = fechaCreacion;
           this.nombre = nombre;
           this.tipoMime = tipoMime;
    }


    /**
     * Gets the codigoAplicacion value for this InfoDocumentoCSVFormDTO.
     *
     * @return codigoAplicacion
     */
    public java.lang.String getCodigoAplicacion() {
        return codigoAplicacion;
    }


    /**
     * Sets the codigoAplicacion value for this InfoDocumentoCSVFormDTO.
     *
     * @param codigoAplicacion
     */
    public void setCodigoAplicacion(java.lang.String codigoAplicacion) {
        this.codigoAplicacion = codigoAplicacion;
    }


    /**
     * Gets the disponible value for this InfoDocumentoCSVFormDTO.
     *
     * @return disponible
     */
    public boolean isDisponible() {
        return disponible;
    }


    /**
     * Sets the disponible value for this InfoDocumentoCSVFormDTO.
     *
     * @param disponible
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }


    /**
     * Gets the fechaCaducidad value for this InfoDocumentoCSVFormDTO.
     *
     * @return fechaCaducidad
     */
    public java.util.Calendar getFechaCaducidad() {
        return fechaCaducidad;
    }


    /**
     * Sets the fechaCaducidad value for this InfoDocumentoCSVFormDTO.
     *
     * @param fechaCaducidad
     */
    public void setFechaCaducidad(java.util.Calendar fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }


    /**
     * Gets the fechaCreacion value for this InfoDocumentoCSVFormDTO.
     *
     * @return fechaCreacion
     */
    public java.util.Calendar getFechaCreacion() {
        return fechaCreacion;
    }


    /**
     * Sets the fechaCreacion value for this InfoDocumentoCSVFormDTO.
     *
     * @param fechaCreacion
     */
    public void setFechaCreacion(java.util.Calendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    /**
     * Gets the nombre value for this InfoDocumentoCSVFormDTO.
     *
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this InfoDocumentoCSVFormDTO.
     *
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }


    /**
     * Gets the tipoMime value for this InfoDocumentoCSVFormDTO.
     *
     * @return tipoMime
     */
    public java.lang.String getTipoMime() {
        return tipoMime;
    }


    /**
     * Sets the tipoMime value for this InfoDocumentoCSVFormDTO.
     *
     * @param tipoMime
     */
    public void setTipoMime(java.lang.String tipoMime) {
        this.tipoMime = tipoMime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InfoDocumentoCSVFormDTO)) return false;
        InfoDocumentoCSVFormDTO other = (InfoDocumentoCSVFormDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.codigoAplicacion==null && other.getCodigoAplicacion()==null) ||
             (this.codigoAplicacion!=null &&
              this.codigoAplicacion.equals(other.getCodigoAplicacion()))) &&
            this.disponible == other.isDisponible() &&
            ((this.fechaCaducidad==null && other.getFechaCaducidad()==null) ||
             (this.fechaCaducidad!=null &&
              this.fechaCaducidad.equals(other.getFechaCaducidad()))) &&
            ((this.fechaCreacion==null && other.getFechaCreacion()==null) ||
             (this.fechaCreacion!=null &&
              this.fechaCreacion.equals(other.getFechaCreacion()))) &&
            ((this.nombre==null && other.getNombre()==null) ||
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre()))) &&
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
        int _hashCode = 1;
        if (getCodigoAplicacion() != null) {
            _hashCode += getCodigoAplicacion().hashCode();
        }
        _hashCode += (isDisponible() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getFechaCaducidad() != null) {
            _hashCode += getFechaCaducidad().hashCode();
        }
        if (getFechaCreacion() != null) {
            _hashCode += getFechaCreacion().hashCode();
        }
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        if (getTipoMime() != null) {
            _hashCode += getTipoMime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InfoDocumentoCSVFormDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.server.ws.gestioncsv.sgm.tecdoc.ieci", "InfoDocumentoCSVFormDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoAplicacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.server.ws.gestioncsv.sgm.tecdoc.ieci", "codigoAplicacion"));
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
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.server.ws.gestioncsv.sgm.tecdoc.ieci", "nombre"));
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
