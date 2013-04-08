/**
 * Liquidacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.certificacion.ws.client.pago;

public class Liquidacion  extends ieci.tecdoc.sgm.certificacion.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String datosEspecificos;

    private java.lang.String discriminante;

    private java.lang.String ejercicio;

    private java.lang.String estado;

    private java.lang.String fechaPago;

    private java.lang.String finPeriodo;

    private java.lang.String idEntidadEmisora;

    private java.lang.String idTasa;

    private java.lang.String importe;

    private java.lang.String inicioPeriodo;

    private java.lang.String nif;

    private java.lang.String nombre;

    private java.lang.String nrc;

    private java.lang.String referencia;

    private java.lang.String remesa;

    private byte[] solicitud;

    private ieci.tecdoc.sgm.certificacion.ws.client.pago.Tasa tasa;

    private java.lang.String vencimiento;

    public Liquidacion() {
    }

    public Liquidacion(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String datosEspecificos,
           java.lang.String discriminante,
           java.lang.String ejercicio,
           java.lang.String estado,
           java.lang.String fechaPago,
           java.lang.String finPeriodo,
           java.lang.String idEntidadEmisora,
           java.lang.String idTasa,
           java.lang.String importe,
           java.lang.String inicioPeriodo,
           java.lang.String nif,
           java.lang.String nombre,
           java.lang.String nrc,
           java.lang.String referencia,
           java.lang.String remesa,
           byte[] solicitud,
           ieci.tecdoc.sgm.certificacion.ws.client.pago.Tasa tasa,
           java.lang.String vencimiento) {
        super(
            errorCode,
            returnCode);
        this.datosEspecificos = datosEspecificos;
        this.discriminante = discriminante;
        this.ejercicio = ejercicio;
        this.estado = estado;
        this.fechaPago = fechaPago;
        this.finPeriodo = finPeriodo;
        this.idEntidadEmisora = idEntidadEmisora;
        this.idTasa = idTasa;
        this.importe = importe;
        this.inicioPeriodo = inicioPeriodo;
        this.nif = nif;
        this.nombre = nombre;
        this.nrc = nrc;
        this.referencia = referencia;
        this.remesa = remesa;
        this.solicitud = solicitud;
        this.tasa = tasa;
        this.vencimiento = vencimiento;
    }


    /**
     * Gets the datosEspecificos value for this Liquidacion.
     * 
     * @return datosEspecificos
     */
    public java.lang.String getDatosEspecificos() {
        return datosEspecificos;
    }


    /**
     * Sets the datosEspecificos value for this Liquidacion.
     * 
     * @param datosEspecificos
     */
    public void setDatosEspecificos(java.lang.String datosEspecificos) {
        this.datosEspecificos = datosEspecificos;
    }


    /**
     * Gets the discriminante value for this Liquidacion.
     * 
     * @return discriminante
     */
    public java.lang.String getDiscriminante() {
        return discriminante;
    }


    /**
     * Sets the discriminante value for this Liquidacion.
     * 
     * @param discriminante
     */
    public void setDiscriminante(java.lang.String discriminante) {
        this.discriminante = discriminante;
    }


    /**
     * Gets the ejercicio value for this Liquidacion.
     * 
     * @return ejercicio
     */
    public java.lang.String getEjercicio() {
        return ejercicio;
    }


    /**
     * Sets the ejercicio value for this Liquidacion.
     * 
     * @param ejercicio
     */
    public void setEjercicio(java.lang.String ejercicio) {
        this.ejercicio = ejercicio;
    }


    /**
     * Gets the estado value for this Liquidacion.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this Liquidacion.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }


    /**
     * Gets the fechaPago value for this Liquidacion.
     * 
     * @return fechaPago
     */
    public java.lang.String getFechaPago() {
        return fechaPago;
    }


    /**
     * Sets the fechaPago value for this Liquidacion.
     * 
     * @param fechaPago
     */
    public void setFechaPago(java.lang.String fechaPago) {
        this.fechaPago = fechaPago;
    }


    /**
     * Gets the finPeriodo value for this Liquidacion.
     * 
     * @return finPeriodo
     */
    public java.lang.String getFinPeriodo() {
        return finPeriodo;
    }


    /**
     * Sets the finPeriodo value for this Liquidacion.
     * 
     * @param finPeriodo
     */
    public void setFinPeriodo(java.lang.String finPeriodo) {
        this.finPeriodo = finPeriodo;
    }


    /**
     * Gets the idEntidadEmisora value for this Liquidacion.
     * 
     * @return idEntidadEmisora
     */
    public java.lang.String getIdEntidadEmisora() {
        return idEntidadEmisora;
    }


    /**
     * Sets the idEntidadEmisora value for this Liquidacion.
     * 
     * @param idEntidadEmisora
     */
    public void setIdEntidadEmisora(java.lang.String idEntidadEmisora) {
        this.idEntidadEmisora = idEntidadEmisora;
    }


    /**
     * Gets the idTasa value for this Liquidacion.
     * 
     * @return idTasa
     */
    public java.lang.String getIdTasa() {
        return idTasa;
    }


    /**
     * Sets the idTasa value for this Liquidacion.
     * 
     * @param idTasa
     */
    public void setIdTasa(java.lang.String idTasa) {
        this.idTasa = idTasa;
    }


    /**
     * Gets the importe value for this Liquidacion.
     * 
     * @return importe
     */
    public java.lang.String getImporte() {
        return importe;
    }


    /**
     * Sets the importe value for this Liquidacion.
     * 
     * @param importe
     */
    public void setImporte(java.lang.String importe) {
        this.importe = importe;
    }


    /**
     * Gets the inicioPeriodo value for this Liquidacion.
     * 
     * @return inicioPeriodo
     */
    public java.lang.String getInicioPeriodo() {
        return inicioPeriodo;
    }


    /**
     * Sets the inicioPeriodo value for this Liquidacion.
     * 
     * @param inicioPeriodo
     */
    public void setInicioPeriodo(java.lang.String inicioPeriodo) {
        this.inicioPeriodo = inicioPeriodo;
    }


    /**
     * Gets the nif value for this Liquidacion.
     * 
     * @return nif
     */
    public java.lang.String getNif() {
        return nif;
    }


    /**
     * Sets the nif value for this Liquidacion.
     * 
     * @param nif
     */
    public void setNif(java.lang.String nif) {
        this.nif = nif;
    }


    /**
     * Gets the nombre value for this Liquidacion.
     * 
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this Liquidacion.
     * 
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }


    /**
     * Gets the nrc value for this Liquidacion.
     * 
     * @return nrc
     */
    public java.lang.String getNrc() {
        return nrc;
    }


    /**
     * Sets the nrc value for this Liquidacion.
     * 
     * @param nrc
     */
    public void setNrc(java.lang.String nrc) {
        this.nrc = nrc;
    }


    /**
     * Gets the referencia value for this Liquidacion.
     * 
     * @return referencia
     */
    public java.lang.String getReferencia() {
        return referencia;
    }


    /**
     * Sets the referencia value for this Liquidacion.
     * 
     * @param referencia
     */
    public void setReferencia(java.lang.String referencia) {
        this.referencia = referencia;
    }


    /**
     * Gets the remesa value for this Liquidacion.
     * 
     * @return remesa
     */
    public java.lang.String getRemesa() {
        return remesa;
    }


    /**
     * Sets the remesa value for this Liquidacion.
     * 
     * @param remesa
     */
    public void setRemesa(java.lang.String remesa) {
        this.remesa = remesa;
    }


    /**
     * Gets the solicitud value for this Liquidacion.
     * 
     * @return solicitud
     */
    public byte[] getSolicitud() {
        return solicitud;
    }


    /**
     * Sets the solicitud value for this Liquidacion.
     * 
     * @param solicitud
     */
    public void setSolicitud(byte[] solicitud) {
        this.solicitud = solicitud;
    }


    /**
     * Gets the tasa value for this Liquidacion.
     * 
     * @return tasa
     */
    public ieci.tecdoc.sgm.certificacion.ws.client.pago.Tasa getTasa() {
        return tasa;
    }


    /**
     * Sets the tasa value for this Liquidacion.
     * 
     * @param tasa
     */
    public void setTasa(ieci.tecdoc.sgm.certificacion.ws.client.pago.Tasa tasa) {
        this.tasa = tasa;
    }


    /**
     * Gets the vencimiento value for this Liquidacion.
     * 
     * @return vencimiento
     */
    public java.lang.String getVencimiento() {
        return vencimiento;
    }


    /**
     * Sets the vencimiento value for this Liquidacion.
     * 
     * @param vencimiento
     */
    public void setVencimiento(java.lang.String vencimiento) {
        this.vencimiento = vencimiento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Liquidacion)) return false;
        Liquidacion other = (Liquidacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.datosEspecificos==null && other.getDatosEspecificos()==null) || 
             (this.datosEspecificos!=null &&
              this.datosEspecificos.equals(other.getDatosEspecificos()))) &&
            ((this.discriminante==null && other.getDiscriminante()==null) || 
             (this.discriminante!=null &&
              this.discriminante.equals(other.getDiscriminante()))) &&
            ((this.ejercicio==null && other.getEjercicio()==null) || 
             (this.ejercicio!=null &&
              this.ejercicio.equals(other.getEjercicio()))) &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.fechaPago==null && other.getFechaPago()==null) || 
             (this.fechaPago!=null &&
              this.fechaPago.equals(other.getFechaPago()))) &&
            ((this.finPeriodo==null && other.getFinPeriodo()==null) || 
             (this.finPeriodo!=null &&
              this.finPeriodo.equals(other.getFinPeriodo()))) &&
            ((this.idEntidadEmisora==null && other.getIdEntidadEmisora()==null) || 
             (this.idEntidadEmisora!=null &&
              this.idEntidadEmisora.equals(other.getIdEntidadEmisora()))) &&
            ((this.idTasa==null && other.getIdTasa()==null) || 
             (this.idTasa!=null &&
              this.idTasa.equals(other.getIdTasa()))) &&
            ((this.importe==null && other.getImporte()==null) || 
             (this.importe!=null &&
              this.importe.equals(other.getImporte()))) &&
            ((this.inicioPeriodo==null && other.getInicioPeriodo()==null) || 
             (this.inicioPeriodo!=null &&
              this.inicioPeriodo.equals(other.getInicioPeriodo()))) &&
            ((this.nif==null && other.getNif()==null) || 
             (this.nif!=null &&
              this.nif.equals(other.getNif()))) &&
            ((this.nombre==null && other.getNombre()==null) || 
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre()))) &&
            ((this.nrc==null && other.getNrc()==null) || 
             (this.nrc!=null &&
              this.nrc.equals(other.getNrc()))) &&
            ((this.referencia==null && other.getReferencia()==null) || 
             (this.referencia!=null &&
              this.referencia.equals(other.getReferencia()))) &&
            ((this.remesa==null && other.getRemesa()==null) || 
             (this.remesa!=null &&
              this.remesa.equals(other.getRemesa()))) &&
            ((this.solicitud==null && other.getSolicitud()==null) || 
             (this.solicitud!=null &&
              java.util.Arrays.equals(this.solicitud, other.getSolicitud()))) &&
            ((this.tasa==null && other.getTasa()==null) || 
             (this.tasa!=null &&
              this.tasa.equals(other.getTasa()))) &&
            ((this.vencimiento==null && other.getVencimiento()==null) || 
             (this.vencimiento!=null &&
              this.vencimiento.equals(other.getVencimiento())));
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
        if (getDatosEspecificos() != null) {
            _hashCode += getDatosEspecificos().hashCode();
        }
        if (getDiscriminante() != null) {
            _hashCode += getDiscriminante().hashCode();
        }
        if (getEjercicio() != null) {
            _hashCode += getEjercicio().hashCode();
        }
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getFechaPago() != null) {
            _hashCode += getFechaPago().hashCode();
        }
        if (getFinPeriodo() != null) {
            _hashCode += getFinPeriodo().hashCode();
        }
        if (getIdEntidadEmisora() != null) {
            _hashCode += getIdEntidadEmisora().hashCode();
        }
        if (getIdTasa() != null) {
            _hashCode += getIdTasa().hashCode();
        }
        if (getImporte() != null) {
            _hashCode += getImporte().hashCode();
        }
        if (getInicioPeriodo() != null) {
            _hashCode += getInicioPeriodo().hashCode();
        }
        if (getNif() != null) {
            _hashCode += getNif().hashCode();
        }
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        if (getNrc() != null) {
            _hashCode += getNrc().hashCode();
        }
        if (getReferencia() != null) {
            _hashCode += getReferencia().hashCode();
        }
        if (getRemesa() != null) {
            _hashCode += getRemesa().hashCode();
        }
        if (getSolicitud() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSolicitud());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSolicitud(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTasa() != null) {
            _hashCode += getTasa().hashCode();
        }
        if (getVencimiento() != null) {
            _hashCode += getVencimiento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Liquidacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "Liquidacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosEspecificos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "datosEspecificos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("discriminante");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "discriminante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ejercicio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "ejercicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaPago");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "fechaPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finPeriodo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "finPeriodo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEntidadEmisora");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "idEntidadEmisora"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTasa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "idTasa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importe");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "importe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inicioPeriodo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "inicioPeriodo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nif");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "nif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "nrc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "referencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remesa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "remesa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("solicitud");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "solicitud"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tasa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "tasa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "Tasa"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vencimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "vencimiento"));
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
