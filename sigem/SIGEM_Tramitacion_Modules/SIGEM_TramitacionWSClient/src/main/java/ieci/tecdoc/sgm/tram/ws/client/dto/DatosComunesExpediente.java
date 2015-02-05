package ieci.tecdoc.sgm.tram.ws.client.dto;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Date;

public class DatosComunesExpediente  implements Serializable {
	
	private String idOrganismo;
	
	private Date fechaRegistro;

    private InteresadoExpediente[] interesados;

    private String numeroRegistro;

    private String tipoAsunto;

    public DatosComunesExpediente() {
    }

    public DatosComunesExpediente(
    	   String idOrganismo,
           Date fechaRegistro,
           InteresadoExpediente[] interesados,
           String numeroRegistro,
           String tipoAsunto) {
    	   this.idOrganismo = idOrganismo;
           this.fechaRegistro = fechaRegistro;
           this.interesados = interesados;
           this.numeroRegistro = numeroRegistro;
           this.tipoAsunto = tipoAsunto;
    }


    /**
     * Gets the fechaRegistro value for this DatosComunesExpediente.
     * 
     * @return fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }


    /**
     * Sets the fechaRegistro value for this DatosComunesExpediente.
     * 
     * @param fechaRegistro
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }


    /**
     * Gets the interesados value for this DatosComunesExpediente.
     * 
     * @return interesados
     */
    public InteresadoExpediente[] getInteresados() {
        return interesados;
    }


    /**
     * Sets the interesados value for this DatosComunesExpediente.
     * 
     * @param interesados
     */
    public void setInteresados(InteresadoExpediente[] interesados) {
        this.interesados = interesados;
    }


    /**
     * Gets the numeroRegistro value for this DatosComunesExpediente.
     * 
     * @return numeroRegistro
     */
    public String getNumeroRegistro() {
        return numeroRegistro;
    }


    /**
     * Sets the numeroRegistro value for this DatosComunesExpediente.
     * 
     * @param numeroRegistro
     */
    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }


    /**
     * Gets the tipoAsunto value for this DatosComunesExpediente.
     * 
     * @return tipoAsunto
     */
    public String getTipoAsunto() {
        return tipoAsunto;
    }


    /**
     * Sets the tipoAsunto value for this DatosComunesExpediente.
     * 
     * @param tipoAsunto
     */
    public void setTipoAsunto(String tipoAsunto) {
        this.tipoAsunto = tipoAsunto;
    }

	public String getIdOrganismo() {
		return idOrganismo;
	}

	public void setIdOrganismo(String idOrganismo) {
		this.idOrganismo = idOrganismo;
	}

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof DatosComunesExpediente)) return false;
        DatosComunesExpediente other = (DatosComunesExpediente) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
	        ((this.idOrganismo==null && other.getIdOrganismo()==null) || 
	                (this.idOrganismo!=null &&
	                 this.idOrganismo.equals(other.getIdOrganismo()))) &&
            ((this.fechaRegistro==null && other.getFechaRegistro()==null) || 
             (this.fechaRegistro!=null &&
              this.fechaRegistro.equals(other.getFechaRegistro()))) &&
            ((this.interesados==null && other.getInteresados()==null) || 
             (this.interesados!=null &&
              java.util.Arrays.equals(this.interesados, other.getInteresados()))) &&
            ((this.numeroRegistro==null && other.getNumeroRegistro()==null) || 
             (this.numeroRegistro!=null &&
              this.numeroRegistro.equals(other.getNumeroRegistro()))) &&
            ((this.tipoAsunto==null && other.getTipoAsunto()==null) || 
             (this.tipoAsunto!=null &&
              this.tipoAsunto.equals(other.getTipoAsunto())));
              
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
        if (getIdOrganismo() != null) {
            _hashCode += getIdOrganismo().hashCode();
        }
        if (getFechaRegistro() != null) {
            _hashCode += getFechaRegistro().hashCode();
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
        if (getNumeroRegistro() != null) {
            _hashCode += getNumeroRegistro().hashCode();
        }
        if (getTipoAsunto() != null) {
            _hashCode += getTipoAsunto().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatosComunesExpediente.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "DatosComunesExpediente"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idOrganismo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "idOrganismo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "fechaRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interesados");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "interesados"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "InteresadoExpediente"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "numeroRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoAsunto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "tipoAsunto"));
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
