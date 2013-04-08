/**
 * Sesion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.sesiones.administrador.ws.client;

public class Sesion  implements java.io.Serializable {
	
	public static final int TIPO_USUARIO_ADMINISTRADOR = 1;
	public static final int TIPO_USUARIO_INTERNO = 2;
	
    private java.lang.String datosEspecificos;

    private java.lang.String idEntidad;

    private java.lang.String idSesion;

    private int tipoUsuario;

    private java.lang.String usuario;

    public Sesion() {
    }

    public Sesion(
           java.lang.String datosEspecificos,
           java.lang.String idEntidad,
           java.lang.String idSesion,
           int tipoUsuario,
           java.lang.String usuario) {
           this.datosEspecificos = datosEspecificos;
           this.idEntidad = idEntidad;
           this.idSesion = idSesion;
           this.tipoUsuario = tipoUsuario;
           this.usuario = usuario;
    }


    /**
     * Gets the datosEspecificos value for this Sesion.
     * 
     * @return datosEspecificos
     */
    public java.lang.String getDatosEspecificos() {
        return datosEspecificos;
    }


    /**
     * Sets the datosEspecificos value for this Sesion.
     * 
     * @param datosEspecificos
     */
    public void setDatosEspecificos(java.lang.String datosEspecificos) {
        this.datosEspecificos = datosEspecificos;
    }


    /**
     * Gets the idEntidad value for this Sesion.
     * 
     * @return idEntidad
     */
    public java.lang.String getIdEntidad() {
        return idEntidad;
    }


    /**
     * Sets the idEntidad value for this Sesion.
     * 
     * @param idEntidad
     */
    public void setIdEntidad(java.lang.String idEntidad) {
        this.idEntidad = idEntidad;
    }


    /**
     * Gets the idSesion value for this Sesion.
     * 
     * @return idSesion
     */
    public java.lang.String getIdSesion() {
        return idSesion;
    }


    /**
     * Sets the idSesion value for this Sesion.
     * 
     * @param idSesion
     */
    public void setIdSesion(java.lang.String idSesion) {
        this.idSesion = idSesion;
    }


    /**
     * Gets the tipoUsuario value for this Sesion.
     * 
     * @return tipoUsuario
     */
    public int getTipoUsuario() {
        return tipoUsuario;
    }


    /**
     * Sets the tipoUsuario value for this Sesion.
     * 
     * @param tipoUsuario
     */
    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }


    /**
     * Gets the usuario value for this Sesion.
     * 
     * @return usuario
     */
    public java.lang.String getUsuario() {
        return usuario;
    }


    /**
     * Sets the usuario value for this Sesion.
     * 
     * @param usuario
     */
    public void setUsuario(java.lang.String usuario) {
        this.usuario = usuario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Sesion)) return false;
        Sesion other = (Sesion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.datosEspecificos==null && other.getDatosEspecificos()==null) || 
             (this.datosEspecificos!=null &&
              this.datosEspecificos.equals(other.getDatosEspecificos()))) &&
            ((this.idEntidad==null && other.getIdEntidad()==null) || 
             (this.idEntidad!=null &&
              this.idEntidad.equals(other.getIdEntidad()))) &&
            ((this.idSesion==null && other.getIdSesion()==null) || 
             (this.idSesion!=null &&
              this.idSesion.equals(other.getIdSesion()))) &&
            this.tipoUsuario == other.getTipoUsuario() &&
            ((this.usuario==null && other.getUsuario()==null) || 
             (this.usuario!=null &&
              this.usuario.equals(other.getUsuario())));
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
        if (getDatosEspecificos() != null) {
            _hashCode += getDatosEspecificos().hashCode();
        }
        if (getIdEntidad() != null) {
            _hashCode += getIdEntidad().hashCode();
        }
        if (getIdSesion() != null) {
            _hashCode += getIdSesion().hashCode();
        }
        _hashCode += getTipoUsuario();
        if (getUsuario() != null) {
            _hashCode += getUsuario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Sesion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.administrador.sesiones.sgm.tecdoc.ieci", "Sesion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosEspecificos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.administrador.sesiones.sgm.tecdoc.ieci", "datosEspecificos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEntidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.administrador.sesiones.sgm.tecdoc.ieci", "idEntidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSesion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.administrador.sesiones.sgm.tecdoc.ieci", "idSesion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoUsuario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.administrador.sesiones.sgm.tecdoc.ieci", "tipoUsuario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.administrador.sesiones.sgm.tecdoc.ieci", "usuario"));
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
