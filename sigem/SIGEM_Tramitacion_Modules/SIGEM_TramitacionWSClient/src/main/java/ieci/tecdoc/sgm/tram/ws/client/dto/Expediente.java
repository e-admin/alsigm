package ieci.tecdoc.sgm.tram.ws.client.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Date;

public class Expediente  extends RetornoServicio  implements Serializable {
	
    private String asunto;

    private DocElectronico[] documentosElectronicos;

    private DocFisico[] documentosFisicos;

    private Emplazamiento[] emplazamientos;

    private Date fechaFinalizacion;

    private Date fechaInicio;

    private String idOrgProductor;

    private InfoBExpediente informacionBasica;

    private Interesado[] interesados;

    private String nombreOrgProductor;

    public Expediente() {
    }

    public Expediente(
           String errorCode,
           String returnCode,
           String asunto,
           DocElectronico[] documentosElectronicos,
           DocFisico[] documentosFisicos,
           Emplazamiento[] emplazamientos,
           Date fechaFinalizacion,
           Date fechaInicio,
           String idOrgProductor,
           InfoBExpediente informacionBasica,
           Interesado[] interesados,
           String nombreOrgProductor) {
        setErrorCode(errorCode);
        setReturnCode(returnCode);
        this.asunto = asunto;
        this.documentosElectronicos = documentosElectronicos;
        this.documentosFisicos = documentosFisicos;
        this.emplazamientos = emplazamientos;
        this.fechaFinalizacion = fechaFinalizacion;
        this.fechaInicio = fechaInicio;
        this.idOrgProductor = idOrgProductor;
        this.informacionBasica = informacionBasica;
        this.interesados = interesados;
        this.nombreOrgProductor = nombreOrgProductor;
    }


    /**
     * Gets the asunto value for this Expediente.
     * 
     * @return asunto
     */
    public String getAsunto() {
        return asunto;
    }


    /**
     * Sets the asunto value for this Expediente.
     * 
     * @param asunto
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }


    /**
     * Gets the documentosElectronicos value for this Expediente.
     * 
     * @return documentosElectronicos
     */
    public DocElectronico[] getDocumentosElectronicos() {
        return documentosElectronicos;
    }


    /**
     * Sets the documentosElectronicos value for this Expediente.
     * 
     * @param documentosElectronicos
     */
    public void setDocumentosElectronicos(DocElectronico[] documentosElectronicos) {
        this.documentosElectronicos = documentosElectronicos;
    }


    /**
     * Gets the documentosFisicos value for this Expediente.
     * 
     * @return documentosFisicos
     */
    public DocFisico[] getDocumentosFisicos() {
        return documentosFisicos;
    }


    /**
     * Sets the documentosFisicos value for this Expediente.
     * 
     * @param documentosFisicos
     */
    public void setDocumentosFisicos(DocFisico[] documentosFisicos) {
        this.documentosFisicos = documentosFisicos;
    }


    /**
     * Gets the emplazamientos value for this Expediente.
     * 
     * @return emplazamientos
     */
    public Emplazamiento[] getEmplazamientos() {
        return emplazamientos;
    }


    /**
     * Sets the emplazamientos value for this Expediente.
     * 
     * @param emplazamientos
     */
    public void setEmplazamientos(Emplazamiento[] emplazamientos) {
        this.emplazamientos = emplazamientos;
    }


    /**
     * Gets the fechaFinalizacion value for this Expediente.
     * 
     * @return fechaFinalizacion
     */
    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }


    /**
     * Sets the fechaFinalizacion value for this Expediente.
     * 
     * @param fechaFinalizacion
     */
    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }


    /**
     * Gets the fechaInicio value for this Expediente.
     * 
     * @return fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }


    /**
     * Sets the fechaInicio value for this Expediente.
     * 
     * @param fechaInicio
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }


    /**
     * Gets the idOrgProductor value for this Expediente.
     * 
     * @return idOrgProductor
     */
    public String getIdOrgProductor() {
        return idOrgProductor;
    }


    /**
     * Sets the idOrgProductor value for this Expediente.
     * 
     * @param idOrgProductor
     */
    public void setIdOrgProductor(String idOrgProductor) {
        this.idOrgProductor = idOrgProductor;
    }


    /**
     * Gets the informacionBasica value for this Expediente.
     * 
     * @return informacionBasica
     */
    public InfoBExpediente getInformacionBasica() {
        return informacionBasica;
    }


    /**
     * Sets the informacionBasica value for this Expediente.
     * 
     * @param informacionBasica
     */
    public void setInformacionBasica(InfoBExpediente informacionBasica) {
        this.informacionBasica = informacionBasica;
    }


    /**
     * Gets the interesados value for this Expediente.
     * 
     * @return interesados
     */
    public Interesado[] getInteresados() {
        return interesados;
    }


    /**
     * Sets the interesados value for this Expediente.
     * 
     * @param interesados
     */
    public void setInteresados(Interesado[] interesados) {
        this.interesados = interesados;
    }


    /**
     * Gets the nombreOrgProductor value for this Expediente.
     * 
     * @return nombreOrgProductor
     */
    public String getNombreOrgProductor() {
        return nombreOrgProductor;
    }


    /**
     * Sets the nombreOrgProductor value for this Expediente.
     * 
     * @param nombreOrgProductor
     */
    public void setNombreOrgProductor(String nombreOrgProductor) {
        this.nombreOrgProductor = nombreOrgProductor;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Expediente)) return false;
        Expediente other = (Expediente) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.asunto==null && other.getAsunto()==null) || 
             (this.asunto!=null &&
              this.asunto.equals(other.getAsunto()))) &&
            ((this.documentosElectronicos==null && other.getDocumentosElectronicos()==null) || 
             (this.documentosElectronicos!=null &&
              java.util.Arrays.equals(this.documentosElectronicos, other.getDocumentosElectronicos()))) &&
            ((this.documentosFisicos==null && other.getDocumentosFisicos()==null) || 
             (this.documentosFisicos!=null &&
              java.util.Arrays.equals(this.documentosFisicos, other.getDocumentosFisicos()))) &&
            ((this.emplazamientos==null && other.getEmplazamientos()==null) || 
             (this.emplazamientos!=null &&
              java.util.Arrays.equals(this.emplazamientos, other.getEmplazamientos()))) &&
            ((this.fechaFinalizacion==null && other.getFechaFinalizacion()==null) || 
             (this.fechaFinalizacion!=null &&
              this.fechaFinalizacion.equals(other.getFechaFinalizacion()))) &&
            ((this.fechaInicio==null && other.getFechaInicio()==null) || 
             (this.fechaInicio!=null &&
              this.fechaInicio.equals(other.getFechaInicio()))) &&
            ((this.idOrgProductor==null && other.getIdOrgProductor()==null) || 
             (this.idOrgProductor!=null &&
              this.idOrgProductor.equals(other.getIdOrgProductor()))) &&
            ((this.informacionBasica==null && other.getInformacionBasica()==null) || 
             (this.informacionBasica!=null &&
              this.informacionBasica.equals(other.getInformacionBasica()))) &&
            ((this.interesados==null && other.getInteresados()==null) || 
             (this.interesados!=null &&
              java.util.Arrays.equals(this.interesados, other.getInteresados()))) &&
            ((this.nombreOrgProductor==null && other.getNombreOrgProductor()==null) || 
             (this.nombreOrgProductor!=null &&
              this.nombreOrgProductor.equals(other.getNombreOrgProductor())));
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
        if (getAsunto() != null) {
            _hashCode += getAsunto().hashCode();
        }
        if (getDocumentosElectronicos() != null) {
            for (int i=0; i<Array.getLength(getDocumentosElectronicos());
                 i++) {
                Object obj = Array.get(getDocumentosElectronicos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDocumentosFisicos() != null) {
            for (int i=0; i<Array.getLength(getDocumentosFisicos()); i++) {
                Object obj = Array.get(getDocumentosFisicos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEmplazamientos() != null) {
            for (int i=0; i<Array.getLength(getEmplazamientos()); i++) {
                Object obj = Array.get(getEmplazamientos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFechaFinalizacion() != null) {
            _hashCode += getFechaFinalizacion().hashCode();
        }
        if (getFechaInicio() != null) {
            _hashCode += getFechaInicio().hashCode();
        }
        if (getIdOrgProductor() != null) {
            _hashCode += getIdOrgProductor().hashCode();
        }
        if (getInformacionBasica() != null) {
            _hashCode += getInformacionBasica().hashCode();
        }
        if (getInteresados() != null) {
            for (int i=0; i<Array.getLength(getInteresados()); i++) {
                Object obj = Array.get(getInteresados(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNombreOrgProductor() != null) {
            _hashCode += getNombreOrgProductor().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Expediente.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "Expediente"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asunto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "asunto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentosElectronicos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "documentosElectronicos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "DocElectronico"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentosFisicos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "documentosFisicos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "DocFisico"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emplazamientos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "emplazamientos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "Emplazamiento"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaFinalizacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "fechaFinalizacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaInicio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "fechaInicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idOrgProductor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "idOrgProductor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("informacionBasica");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "informacionBasica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "InfoBExpediente"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interesados");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "interesados"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "Interesado"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreOrgProductor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "nombreOrgProductor"));
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
