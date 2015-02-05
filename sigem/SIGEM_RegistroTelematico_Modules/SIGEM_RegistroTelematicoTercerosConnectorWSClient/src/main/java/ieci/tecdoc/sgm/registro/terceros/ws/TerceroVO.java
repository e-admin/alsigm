/**
 * TerceroVO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registro.terceros.ws;

public class TerceroVO  implements java.io.Serializable {
    private ieci.tecdoc.sgm.registro.terceros.ws.DireccionTerceroVO[] direcciones;

    private java.lang.String identificador;

    private java.lang.String nombre;

    private java.lang.String primerApellido;

    private java.lang.String segundoApellido;

    private java.lang.String terceroId;

    public TerceroVO() {
    }

    public TerceroVO(
           ieci.tecdoc.sgm.registro.terceros.ws.DireccionTerceroVO[] direcciones,
           java.lang.String identificador,
           java.lang.String nombre,
           java.lang.String primerApellido,
           java.lang.String segundoApellido,
           java.lang.String terceroId) {
           this.direcciones = direcciones;
           this.identificador = identificador;
           this.nombre = nombre;
           this.primerApellido = primerApellido;
           this.segundoApellido = segundoApellido;
           this.terceroId = terceroId;
    }


    /**
     * Gets the direcciones value for this TerceroVO.
     * 
     * @return direcciones
     */
    public ieci.tecdoc.sgm.registro.terceros.ws.DireccionTerceroVO[] getDirecciones() {
        return direcciones;
    }


    /**
     * Sets the direcciones value for this TerceroVO.
     * 
     * @param direcciones
     */
    public void setDirecciones(ieci.tecdoc.sgm.registro.terceros.ws.DireccionTerceroVO[] direcciones) {
        this.direcciones = direcciones;
    }


    /**
     * Gets the identificador value for this TerceroVO.
     * 
     * @return identificador
     */
    public java.lang.String getIdentificador() {
        return identificador;
    }


    /**
     * Sets the identificador value for this TerceroVO.
     * 
     * @param identificador
     */
    public void setIdentificador(java.lang.String identificador) {
        this.identificador = identificador;
    }


    /**
     * Gets the nombre value for this TerceroVO.
     * 
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this TerceroVO.
     * 
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }


    /**
     * Gets the primerApellido value for this TerceroVO.
     * 
     * @return primerApellido
     */
    public java.lang.String getPrimerApellido() {
        return primerApellido;
    }


    /**
     * Sets the primerApellido value for this TerceroVO.
     * 
     * @param primerApellido
     */
    public void setPrimerApellido(java.lang.String primerApellido) {
        this.primerApellido = primerApellido;
    }


    /**
     * Gets the segundoApellido value for this TerceroVO.
     * 
     * @return segundoApellido
     */
    public java.lang.String getSegundoApellido() {
        return segundoApellido;
    }


    /**
     * Sets the segundoApellido value for this TerceroVO.
     * 
     * @param segundoApellido
     */
    public void setSegundoApellido(java.lang.String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }


    /**
     * Gets the terceroId value for this TerceroVO.
     * 
     * @return terceroId
     */
    public java.lang.String getTerceroId() {
        return terceroId;
    }


    /**
     * Sets the terceroId value for this TerceroVO.
     * 
     * @param terceroId
     */
    public void setTerceroId(java.lang.String terceroId) {
        this.terceroId = terceroId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TerceroVO)) return false;
        TerceroVO other = (TerceroVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.direcciones==null && other.getDirecciones()==null) || 
             (this.direcciones!=null &&
              java.util.Arrays.equals(this.direcciones, other.getDirecciones()))) &&
            ((this.identificador==null && other.getIdentificador()==null) || 
             (this.identificador!=null &&
              this.identificador.equals(other.getIdentificador()))) &&
            ((this.nombre==null && other.getNombre()==null) || 
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre()))) &&
            ((this.primerApellido==null && other.getPrimerApellido()==null) || 
             (this.primerApellido!=null &&
              this.primerApellido.equals(other.getPrimerApellido()))) &&
            ((this.segundoApellido==null && other.getSegundoApellido()==null) || 
             (this.segundoApellido!=null &&
              this.segundoApellido.equals(other.getSegundoApellido()))) &&
            ((this.terceroId==null && other.getTerceroId()==null) || 
             (this.terceroId!=null &&
              this.terceroId.equals(other.getTerceroId())));
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
        if (getDirecciones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDirecciones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDirecciones(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIdentificador() != null) {
            _hashCode += getIdentificador().hashCode();
        }
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        if (getPrimerApellido() != null) {
            _hashCode += getPrimerApellido().hashCode();
        }
        if (getSegundoApellido() != null) {
            _hashCode += getSegundoApellido().hashCode();
        }
        if (getTerceroId() != null) {
            _hashCode += getTerceroId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TerceroVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.terceros.registro.sgm.tecdoc.ieci", "TerceroVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direcciones");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.terceros.registro.sgm.tecdoc.ieci", "direcciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.terceros.registro.sgm.tecdoc.ieci", "DireccionTerceroVO"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.terceros.registro.sgm.tecdoc.ieci", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificador");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.terceros.registro.sgm.tecdoc.ieci", "identificador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.terceros.registro.sgm.tecdoc.ieci", "nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primerApellido");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.terceros.registro.sgm.tecdoc.ieci", "primerApellido"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segundoApellido");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.terceros.registro.sgm.tecdoc.ieci", "segundoApellido"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terceroId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.terceros.registro.sgm.tecdoc.ieci", "terceroId"));
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
