/**
 * Pago.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.certificacion.ws.client.pago;

public class Pago  extends ieci.tecdoc.sgm.certificacion.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String acreditacion;

    private java.lang.String ccc;

    private java.lang.String cccDomiciliacion;

    private java.lang.String domiciliacion;

    private java.lang.String ejercicio;

    private java.lang.String entidadBancaria;

    private java.lang.String estado;

    private java.lang.String expediente;

    private java.lang.String fecha;

    private java.lang.String fechaCaducidadTarjetaCredito;

    private java.lang.String fechaDevengo;

    private java.lang.String hora;

    private java.lang.String idEntidadEmisora;

    private java.lang.String idTasa;

    private java.lang.String idioma;

    private java.lang.String importe;

    private java.lang.String informacionEspecifica;

    private ieci.tecdoc.sgm.certificacion.ws.client.pago.Liquidacion liquidacion;

    private java.lang.String medioPago;

    private java.lang.String nif;

    private java.lang.String nrc;

    private java.lang.String numeroTarjetaCredito;

    private java.lang.String referencia;

    private java.lang.String remesa;

    private ieci.tecdoc.sgm.certificacion.ws.client.pago.Tasa tasa;

    public Pago() {
    }

    public Pago(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String acreditacion,
           java.lang.String ccc,
           java.lang.String cccDomiciliacion,
           java.lang.String domiciliacion,
           java.lang.String ejercicio,
           java.lang.String entidadBancaria,
           java.lang.String estado,
           java.lang.String expediente,
           java.lang.String fecha,
           java.lang.String fechaCaducidadTarjetaCredito,
           java.lang.String fechaDevengo,
           java.lang.String hora,
           java.lang.String idEntidadEmisora,
           java.lang.String idTasa,
           java.lang.String idioma,
           java.lang.String importe,
           java.lang.String informacionEspecifica,
           ieci.tecdoc.sgm.certificacion.ws.client.pago.Liquidacion liquidacion,
           java.lang.String medioPago,
           java.lang.String nif,
           java.lang.String nrc,
           java.lang.String numeroTarjetaCredito,
           java.lang.String referencia,
           java.lang.String remesa,
           ieci.tecdoc.sgm.certificacion.ws.client.pago.Tasa tasa) {
        super(
            errorCode,
            returnCode);
        this.acreditacion = acreditacion;
        this.ccc = ccc;
        this.cccDomiciliacion = cccDomiciliacion;
        this.domiciliacion = domiciliacion;
        this.ejercicio = ejercicio;
        this.entidadBancaria = entidadBancaria;
        this.estado = estado;
        this.expediente = expediente;
        this.fecha = fecha;
        this.fechaCaducidadTarjetaCredito = fechaCaducidadTarjetaCredito;
        this.fechaDevengo = fechaDevengo;
        this.hora = hora;
        this.idEntidadEmisora = idEntidadEmisora;
        this.idTasa = idTasa;
        this.idioma = idioma;
        this.importe = importe;
        this.informacionEspecifica = informacionEspecifica;
        this.liquidacion = liquidacion;
        this.medioPago = medioPago;
        this.nif = nif;
        this.nrc = nrc;
        this.numeroTarjetaCredito = numeroTarjetaCredito;
        this.referencia = referencia;
        this.remesa = remesa;
        this.tasa = tasa;
    }


    /**
     * Gets the acreditacion value for this Pago.
     * 
     * @return acreditacion
     */
    public java.lang.String getAcreditacion() {
        return acreditacion;
    }


    /**
     * Sets the acreditacion value for this Pago.
     * 
     * @param acreditacion
     */
    public void setAcreditacion(java.lang.String acreditacion) {
        this.acreditacion = acreditacion;
    }


    /**
     * Gets the ccc value for this Pago.
     * 
     * @return ccc
     */
    public java.lang.String getCcc() {
        return ccc;
    }


    /**
     * Sets the ccc value for this Pago.
     * 
     * @param ccc
     */
    public void setCcc(java.lang.String ccc) {
        this.ccc = ccc;
    }


    /**
     * Gets the cccDomiciliacion value for this Pago.
     * 
     * @return cccDomiciliacion
     */
    public java.lang.String getCccDomiciliacion() {
        return cccDomiciliacion;
    }


    /**
     * Sets the cccDomiciliacion value for this Pago.
     * 
     * @param cccDomiciliacion
     */
    public void setCccDomiciliacion(java.lang.String cccDomiciliacion) {
        this.cccDomiciliacion = cccDomiciliacion;
    }


    /**
     * Gets the domiciliacion value for this Pago.
     * 
     * @return domiciliacion
     */
    public java.lang.String getDomiciliacion() {
        return domiciliacion;
    }


    /**
     * Sets the domiciliacion value for this Pago.
     * 
     * @param domiciliacion
     */
    public void setDomiciliacion(java.lang.String domiciliacion) {
        this.domiciliacion = domiciliacion;
    }


    /**
     * Gets the ejercicio value for this Pago.
     * 
     * @return ejercicio
     */
    public java.lang.String getEjercicio() {
        return ejercicio;
    }


    /**
     * Sets the ejercicio value for this Pago.
     * 
     * @param ejercicio
     */
    public void setEjercicio(java.lang.String ejercicio) {
        this.ejercicio = ejercicio;
    }


    /**
     * Gets the entidadBancaria value for this Pago.
     * 
     * @return entidadBancaria
     */
    public java.lang.String getEntidadBancaria() {
        return entidadBancaria;
    }


    /**
     * Sets the entidadBancaria value for this Pago.
     * 
     * @param entidadBancaria
     */
    public void setEntidadBancaria(java.lang.String entidadBancaria) {
        this.entidadBancaria = entidadBancaria;
    }


    /**
     * Gets the estado value for this Pago.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this Pago.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }


    /**
     * Gets the expediente value for this Pago.
     * 
     * @return expediente
     */
    public java.lang.String getExpediente() {
        return expediente;
    }


    /**
     * Sets the expediente value for this Pago.
     * 
     * @param expediente
     */
    public void setExpediente(java.lang.String expediente) {
        this.expediente = expediente;
    }


    /**
     * Gets the fecha value for this Pago.
     * 
     * @return fecha
     */
    public java.lang.String getFecha() {
        return fecha;
    }


    /**
     * Sets the fecha value for this Pago.
     * 
     * @param fecha
     */
    public void setFecha(java.lang.String fecha) {
        this.fecha = fecha;
    }


    /**
     * Gets the fechaCaducidadTarjetaCredito value for this Pago.
     * 
     * @return fechaCaducidadTarjetaCredito
     */
    public java.lang.String getFechaCaducidadTarjetaCredito() {
        return fechaCaducidadTarjetaCredito;
    }


    /**
     * Sets the fechaCaducidadTarjetaCredito value for this Pago.
     * 
     * @param fechaCaducidadTarjetaCredito
     */
    public void setFechaCaducidadTarjetaCredito(java.lang.String fechaCaducidadTarjetaCredito) {
        this.fechaCaducidadTarjetaCredito = fechaCaducidadTarjetaCredito;
    }


    /**
     * Gets the fechaDevengo value for this Pago.
     * 
     * @return fechaDevengo
     */
    public java.lang.String getFechaDevengo() {
        return fechaDevengo;
    }


    /**
     * Sets the fechaDevengo value for this Pago.
     * 
     * @param fechaDevengo
     */
    public void setFechaDevengo(java.lang.String fechaDevengo) {
        this.fechaDevengo = fechaDevengo;
    }


    /**
     * Gets the hora value for this Pago.
     * 
     * @return hora
     */
    public java.lang.String getHora() {
        return hora;
    }


    /**
     * Sets the hora value for this Pago.
     * 
     * @param hora
     */
    public void setHora(java.lang.String hora) {
        this.hora = hora;
    }


    /**
     * Gets the idEntidadEmisora value for this Pago.
     * 
     * @return idEntidadEmisora
     */
    public java.lang.String getIdEntidadEmisora() {
        return idEntidadEmisora;
    }


    /**
     * Sets the idEntidadEmisora value for this Pago.
     * 
     * @param idEntidadEmisora
     */
    public void setIdEntidadEmisora(java.lang.String idEntidadEmisora) {
        this.idEntidadEmisora = idEntidadEmisora;
    }


    /**
     * Gets the idTasa value for this Pago.
     * 
     * @return idTasa
     */
    public java.lang.String getIdTasa() {
        return idTasa;
    }


    /**
     * Sets the idTasa value for this Pago.
     * 
     * @param idTasa
     */
    public void setIdTasa(java.lang.String idTasa) {
        this.idTasa = idTasa;
    }


    /**
     * Gets the idioma value for this Pago.
     * 
     * @return idioma
     */
    public java.lang.String getIdioma() {
        return idioma;
    }


    /**
     * Sets the idioma value for this Pago.
     * 
     * @param idioma
     */
    public void setIdioma(java.lang.String idioma) {
        this.idioma = idioma;
    }


    /**
     * Gets the importe value for this Pago.
     * 
     * @return importe
     */
    public java.lang.String getImporte() {
        return importe;
    }


    /**
     * Sets the importe value for this Pago.
     * 
     * @param importe
     */
    public void setImporte(java.lang.String importe) {
        this.importe = importe;
    }


    /**
     * Gets the informacionEspecifica value for this Pago.
     * 
     * @return informacionEspecifica
     */
    public java.lang.String getInformacionEspecifica() {
        return informacionEspecifica;
    }


    /**
     * Sets the informacionEspecifica value for this Pago.
     * 
     * @param informacionEspecifica
     */
    public void setInformacionEspecifica(java.lang.String informacionEspecifica) {
        this.informacionEspecifica = informacionEspecifica;
    }


    /**
     * Gets the liquidacion value for this Pago.
     * 
     * @return liquidacion
     */
    public ieci.tecdoc.sgm.certificacion.ws.client.pago.Liquidacion getLiquidacion() {
        return liquidacion;
    }


    /**
     * Sets the liquidacion value for this Pago.
     * 
     * @param liquidacion
     */
    public void setLiquidacion(ieci.tecdoc.sgm.certificacion.ws.client.pago.Liquidacion liquidacion) {
        this.liquidacion = liquidacion;
    }


    /**
     * Gets the medioPago value for this Pago.
     * 
     * @return medioPago
     */
    public java.lang.String getMedioPago() {
        return medioPago;
    }


    /**
     * Sets the medioPago value for this Pago.
     * 
     * @param medioPago
     */
    public void setMedioPago(java.lang.String medioPago) {
        this.medioPago = medioPago;
    }


    /**
     * Gets the nif value for this Pago.
     * 
     * @return nif
     */
    public java.lang.String getNif() {
        return nif;
    }


    /**
     * Sets the nif value for this Pago.
     * 
     * @param nif
     */
    public void setNif(java.lang.String nif) {
        this.nif = nif;
    }


    /**
     * Gets the nrc value for this Pago.
     * 
     * @return nrc
     */
    public java.lang.String getNrc() {
        return nrc;
    }


    /**
     * Sets the nrc value for this Pago.
     * 
     * @param nrc
     */
    public void setNrc(java.lang.String nrc) {
        this.nrc = nrc;
    }


    /**
     * Gets the numeroTarjetaCredito value for this Pago.
     * 
     * @return numeroTarjetaCredito
     */
    public java.lang.String getNumeroTarjetaCredito() {
        return numeroTarjetaCredito;
    }


    /**
     * Sets the numeroTarjetaCredito value for this Pago.
     * 
     * @param numeroTarjetaCredito
     */
    public void setNumeroTarjetaCredito(java.lang.String numeroTarjetaCredito) {
        this.numeroTarjetaCredito = numeroTarjetaCredito;
    }


    /**
     * Gets the referencia value for this Pago.
     * 
     * @return referencia
     */
    public java.lang.String getReferencia() {
        return referencia;
    }


    /**
     * Sets the referencia value for this Pago.
     * 
     * @param referencia
     */
    public void setReferencia(java.lang.String referencia) {
        this.referencia = referencia;
    }


    /**
     * Gets the remesa value for this Pago.
     * 
     * @return remesa
     */
    public java.lang.String getRemesa() {
        return remesa;
    }


    /**
     * Sets the remesa value for this Pago.
     * 
     * @param remesa
     */
    public void setRemesa(java.lang.String remesa) {
        this.remesa = remesa;
    }


    /**
     * Gets the tasa value for this Pago.
     * 
     * @return tasa
     */
    public ieci.tecdoc.sgm.certificacion.ws.client.pago.Tasa getTasa() {
        return tasa;
    }


    /**
     * Sets the tasa value for this Pago.
     * 
     * @param tasa
     */
    public void setTasa(ieci.tecdoc.sgm.certificacion.ws.client.pago.Tasa tasa) {
        this.tasa = tasa;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Pago)) return false;
        Pago other = (Pago) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.acreditacion==null && other.getAcreditacion()==null) || 
             (this.acreditacion!=null &&
              this.acreditacion.equals(other.getAcreditacion()))) &&
            ((this.ccc==null && other.getCcc()==null) || 
             (this.ccc!=null &&
              this.ccc.equals(other.getCcc()))) &&
            ((this.cccDomiciliacion==null && other.getCccDomiciliacion()==null) || 
             (this.cccDomiciliacion!=null &&
              this.cccDomiciliacion.equals(other.getCccDomiciliacion()))) &&
            ((this.domiciliacion==null && other.getDomiciliacion()==null) || 
             (this.domiciliacion!=null &&
              this.domiciliacion.equals(other.getDomiciliacion()))) &&
            ((this.ejercicio==null && other.getEjercicio()==null) || 
             (this.ejercicio!=null &&
              this.ejercicio.equals(other.getEjercicio()))) &&
            ((this.entidadBancaria==null && other.getEntidadBancaria()==null) || 
             (this.entidadBancaria!=null &&
              this.entidadBancaria.equals(other.getEntidadBancaria()))) &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.expediente==null && other.getExpediente()==null) || 
             (this.expediente!=null &&
              this.expediente.equals(other.getExpediente()))) &&
            ((this.fecha==null && other.getFecha()==null) || 
             (this.fecha!=null &&
              this.fecha.equals(other.getFecha()))) &&
            ((this.fechaCaducidadTarjetaCredito==null && other.getFechaCaducidadTarjetaCredito()==null) || 
             (this.fechaCaducidadTarjetaCredito!=null &&
              this.fechaCaducidadTarjetaCredito.equals(other.getFechaCaducidadTarjetaCredito()))) &&
            ((this.fechaDevengo==null && other.getFechaDevengo()==null) || 
             (this.fechaDevengo!=null &&
              this.fechaDevengo.equals(other.getFechaDevengo()))) &&
            ((this.hora==null && other.getHora()==null) || 
             (this.hora!=null &&
              this.hora.equals(other.getHora()))) &&
            ((this.idEntidadEmisora==null && other.getIdEntidadEmisora()==null) || 
             (this.idEntidadEmisora!=null &&
              this.idEntidadEmisora.equals(other.getIdEntidadEmisora()))) &&
            ((this.idTasa==null && other.getIdTasa()==null) || 
             (this.idTasa!=null &&
              this.idTasa.equals(other.getIdTasa()))) &&
            ((this.idioma==null && other.getIdioma()==null) || 
             (this.idioma!=null &&
              this.idioma.equals(other.getIdioma()))) &&
            ((this.importe==null && other.getImporte()==null) || 
             (this.importe!=null &&
              this.importe.equals(other.getImporte()))) &&
            ((this.informacionEspecifica==null && other.getInformacionEspecifica()==null) || 
             (this.informacionEspecifica!=null &&
              this.informacionEspecifica.equals(other.getInformacionEspecifica()))) &&
            ((this.liquidacion==null && other.getLiquidacion()==null) || 
             (this.liquidacion!=null &&
              this.liquidacion.equals(other.getLiquidacion()))) &&
            ((this.medioPago==null && other.getMedioPago()==null) || 
             (this.medioPago!=null &&
              this.medioPago.equals(other.getMedioPago()))) &&
            ((this.nif==null && other.getNif()==null) || 
             (this.nif!=null &&
              this.nif.equals(other.getNif()))) &&
            ((this.nrc==null && other.getNrc()==null) || 
             (this.nrc!=null &&
              this.nrc.equals(other.getNrc()))) &&
            ((this.numeroTarjetaCredito==null && other.getNumeroTarjetaCredito()==null) || 
             (this.numeroTarjetaCredito!=null &&
              this.numeroTarjetaCredito.equals(other.getNumeroTarjetaCredito()))) &&
            ((this.referencia==null && other.getReferencia()==null) || 
             (this.referencia!=null &&
              this.referencia.equals(other.getReferencia()))) &&
            ((this.remesa==null && other.getRemesa()==null) || 
             (this.remesa!=null &&
              this.remesa.equals(other.getRemesa()))) &&
            ((this.tasa==null && other.getTasa()==null) || 
             (this.tasa!=null &&
              this.tasa.equals(other.getTasa())));
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
        if (getAcreditacion() != null) {
            _hashCode += getAcreditacion().hashCode();
        }
        if (getCcc() != null) {
            _hashCode += getCcc().hashCode();
        }
        if (getCccDomiciliacion() != null) {
            _hashCode += getCccDomiciliacion().hashCode();
        }
        if (getDomiciliacion() != null) {
            _hashCode += getDomiciliacion().hashCode();
        }
        if (getEjercicio() != null) {
            _hashCode += getEjercicio().hashCode();
        }
        if (getEntidadBancaria() != null) {
            _hashCode += getEntidadBancaria().hashCode();
        }
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getExpediente() != null) {
            _hashCode += getExpediente().hashCode();
        }
        if (getFecha() != null) {
            _hashCode += getFecha().hashCode();
        }
        if (getFechaCaducidadTarjetaCredito() != null) {
            _hashCode += getFechaCaducidadTarjetaCredito().hashCode();
        }
        if (getFechaDevengo() != null) {
            _hashCode += getFechaDevengo().hashCode();
        }
        if (getHora() != null) {
            _hashCode += getHora().hashCode();
        }
        if (getIdEntidadEmisora() != null) {
            _hashCode += getIdEntidadEmisora().hashCode();
        }
        if (getIdTasa() != null) {
            _hashCode += getIdTasa().hashCode();
        }
        if (getIdioma() != null) {
            _hashCode += getIdioma().hashCode();
        }
        if (getImporte() != null) {
            _hashCode += getImporte().hashCode();
        }
        if (getInformacionEspecifica() != null) {
            _hashCode += getInformacionEspecifica().hashCode();
        }
        if (getLiquidacion() != null) {
            _hashCode += getLiquidacion().hashCode();
        }
        if (getMedioPago() != null) {
            _hashCode += getMedioPago().hashCode();
        }
        if (getNif() != null) {
            _hashCode += getNif().hashCode();
        }
        if (getNrc() != null) {
            _hashCode += getNrc().hashCode();
        }
        if (getNumeroTarjetaCredito() != null) {
            _hashCode += getNumeroTarjetaCredito().hashCode();
        }
        if (getReferencia() != null) {
            _hashCode += getReferencia().hashCode();
        }
        if (getRemesa() != null) {
            _hashCode += getRemesa().hashCode();
        }
        if (getTasa() != null) {
            _hashCode += getTasa().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Pago.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "Pago"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acreditacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "acreditacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "ccc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cccDomiciliacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "cccDomiciliacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("domiciliacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "domiciliacion"));
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
        elemField.setFieldName("entidadBancaria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "entidadBancaria"));
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
        elemField.setFieldName("expediente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "expediente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecha");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "fecha"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaCaducidadTarjetaCredito");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "fechaCaducidadTarjetaCredito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaDevengo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "fechaDevengo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hora");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "hora"));
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
        elemField.setFieldName("idioma");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "idioma"));
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
        elemField.setFieldName("informacionEspecifica");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "informacionEspecifica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liquidacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "liquidacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "Liquidacion"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("medioPago");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "medioPago"));
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
        elemField.setFieldName("nrc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "nrc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroTarjetaCredito");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "numeroTarjetaCredito"));
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
        elemField.setFieldName("tasa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "tasa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://pago.server.ws.certificacion.sgm.tecdoc.ieci", "Tasa"));
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
