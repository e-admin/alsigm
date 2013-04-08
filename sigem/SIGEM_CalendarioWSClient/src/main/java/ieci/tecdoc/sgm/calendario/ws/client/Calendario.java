/**
 * Calendario.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.calendario.ws.client;

public class Calendario  extends RetornoServicio  implements java.io.Serializable {
    private java.lang.String[] dias;

    private java.lang.String horaFin;

    private java.lang.String horaInicio;

    private java.lang.String minutoFin;

    private java.lang.String minutoInicio;

    public Calendario() {
    }

    public Calendario(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String[] dias,
           java.lang.String horaFin,
           java.lang.String horaInicio,
           java.lang.String minutoFin,
           java.lang.String minutoInicio) {
        super(
            errorCode,
            returnCode);
        this.dias = dias;
        this.horaFin = horaFin;
        this.horaInicio = horaInicio;
        this.minutoFin = minutoFin;
        this.minutoInicio = minutoInicio;
    }


    /**
     * Gets the dias value for this Calendario.
     * 
     * @return dias
     */
    public java.lang.String[] getDias() {
        return dias;
    }


    /**
     * Sets the dias value for this Calendario.
     * 
     * @param dias
     */
    public void setDias(java.lang.String[] dias) {
        this.dias = dias;
    }


    /**
     * Gets the horaFin value for this Calendario.
     * 
     * @return horaFin
     */
    public java.lang.String getHoraFin() {
        return horaFin;
    }


    /**
     * Sets the horaFin value for this Calendario.
     * 
     * @param horaFin
     */
    public void setHoraFin(java.lang.String horaFin) {
        this.horaFin = horaFin;
    }


    /**
     * Gets the horaInicio value for this Calendario.
     * 
     * @return horaInicio
     */
    public java.lang.String getHoraInicio() {
        return horaInicio;
    }


    /**
     * Sets the horaInicio value for this Calendario.
     * 
     * @param horaInicio
     */
    public void setHoraInicio(java.lang.String horaInicio) {
        this.horaInicio = horaInicio;
    }


    /**
     * Gets the minutoFin value for this Calendario.
     * 
     * @return minutoFin
     */
    public java.lang.String getMinutoFin() {
        return minutoFin;
    }


    /**
     * Sets the minutoFin value for this Calendario.
     * 
     * @param minutoFin
     */
    public void setMinutoFin(java.lang.String minutoFin) {
        this.minutoFin = minutoFin;
    }


    /**
     * Gets the minutoInicio value for this Calendario.
     * 
     * @return minutoInicio
     */
    public java.lang.String getMinutoInicio() {
        return minutoInicio;
    }


    /**
     * Sets the minutoInicio value for this Calendario.
     * 
     * @param minutoInicio
     */
    public void setMinutoInicio(java.lang.String minutoInicio) {
        this.minutoInicio = minutoInicio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Calendario)) return false;
        Calendario other = (Calendario) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dias==null && other.getDias()==null) || 
             (this.dias!=null &&
              java.util.Arrays.equals(this.dias, other.getDias()))) &&
            ((this.horaFin==null && other.getHoraFin()==null) || 
             (this.horaFin!=null &&
              this.horaFin.equals(other.getHoraFin()))) &&
            ((this.horaInicio==null && other.getHoraInicio()==null) || 
             (this.horaInicio!=null &&
              this.horaInicio.equals(other.getHoraInicio()))) &&
            ((this.minutoFin==null && other.getMinutoFin()==null) || 
             (this.minutoFin!=null &&
              this.minutoFin.equals(other.getMinutoFin()))) &&
            ((this.minutoInicio==null && other.getMinutoInicio()==null) || 
             (this.minutoInicio!=null &&
              this.minutoInicio.equals(other.getMinutoInicio())));
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
        if (getDias() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDias());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDias(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getHoraFin() != null) {
            _hashCode += getHoraFin().hashCode();
        }
        if (getHoraInicio() != null) {
            _hashCode += getHoraInicio().hashCode();
        }
        if (getMinutoFin() != null) {
            _hashCode += getMinutoFin().hashCode();
        }
        if (getMinutoInicio() != null) {
            _hashCode += getMinutoInicio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Calendario.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "Calendario"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dias");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "dias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("horaFin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "horaFin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("horaInicio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "horaInicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minutoFin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "minutoFin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minutoInicio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.calendario.sgm.tecdoc.ieci", "minutoInicio"));
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
