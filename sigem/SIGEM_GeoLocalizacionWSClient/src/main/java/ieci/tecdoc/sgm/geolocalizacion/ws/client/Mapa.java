/**
 * Mapa.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.geolocalizacion.ws.client;

public class Mapa  extends ieci.tecdoc.sgm.geolocalizacion.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.Short mapdefault;

    private java.lang.Integer mapid;

    private java.lang.Integer mapidgeopista;

    private java.lang.Integer mapidentidad;

    private java.lang.Short mappublic;

    private java.lang.Double maxx;

    private java.lang.Double maxy;

    private java.lang.Double minx;

    private java.lang.Double miny;

    private java.lang.String name;

    private java.lang.String srid;

    public Mapa() {
    }

    public Mapa(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.Short mapdefault,
           java.lang.Integer mapid,
           java.lang.Integer mapidgeopista,
           java.lang.Integer mapidentidad,
           java.lang.Short mappublic,
           java.lang.Double maxx,
           java.lang.Double maxy,
           java.lang.Double minx,
           java.lang.Double miny,
           java.lang.String name,
           java.lang.String srid) {
        super(
            errorCode,
            returnCode);
        this.mapdefault = mapdefault;
        this.mapid = mapid;
        this.mapidgeopista = mapidgeopista;
        this.mapidentidad = mapidentidad;
        this.mappublic = mappublic;
        this.maxx = maxx;
        this.maxy = maxy;
        this.minx = minx;
        this.miny = miny;
        this.name = name;
        this.srid = srid;
    }


    /**
     * Gets the mapdefault value for this Mapa.
     * 
     * @return mapdefault
     */
    public java.lang.Short getMapdefault() {
        return mapdefault;
    }


    /**
     * Sets the mapdefault value for this Mapa.
     * 
     * @param mapdefault
     */
    public void setMapdefault(java.lang.Short mapdefault) {
        this.mapdefault = mapdefault;
    }


    /**
     * Gets the mapid value for this Mapa.
     * 
     * @return mapid
     */
    public java.lang.Integer getMapid() {
        return mapid;
    }


    /**
     * Sets the mapid value for this Mapa.
     * 
     * @param mapid
     */
    public void setMapid(java.lang.Integer mapid) {
        this.mapid = mapid;
    }


    /**
     * Gets the mapidgeopista value for this Mapa.
     * 
     * @return mapidgeopista
     */
    public java.lang.Integer getMapidgeopista() {
        return mapidgeopista;
    }


    /**
     * Sets the mapidgeopista value for this Mapa.
     * 
     * @param mapidgeopista
     */
    public void setMapidgeopista(java.lang.Integer mapidgeopista) {
        this.mapidgeopista = mapidgeopista;
    }


    /**
     * Gets the mapidentidad value for this Mapa.
     * 
     * @return mapidentidad
     */
    public java.lang.Integer getMapidentidad() {
        return mapidentidad;
    }


    /**
     * Sets the mapidentidad value for this Mapa.
     * 
     * @param mapidentidad
     */
    public void setMapidentidad(java.lang.Integer mapidentidad) {
        this.mapidentidad = mapidentidad;
    }


    /**
     * Gets the mappublic value for this Mapa.
     * 
     * @return mappublic
     */
    public java.lang.Short getMappublic() {
        return mappublic;
    }


    /**
     * Sets the mappublic value for this Mapa.
     * 
     * @param mappublic
     */
    public void setMappublic(java.lang.Short mappublic) {
        this.mappublic = mappublic;
    }


    /**
     * Gets the maxx value for this Mapa.
     * 
     * @return maxx
     */
    public java.lang.Double getMaxx() {
        return maxx;
    }


    /**
     * Sets the maxx value for this Mapa.
     * 
     * @param maxx
     */
    public void setMaxx(java.lang.Double maxx) {
        this.maxx = maxx;
    }


    /**
     * Gets the maxy value for this Mapa.
     * 
     * @return maxy
     */
    public java.lang.Double getMaxy() {
        return maxy;
    }


    /**
     * Sets the maxy value for this Mapa.
     * 
     * @param maxy
     */
    public void setMaxy(java.lang.Double maxy) {
        this.maxy = maxy;
    }


    /**
     * Gets the minx value for this Mapa.
     * 
     * @return minx
     */
    public java.lang.Double getMinx() {
        return minx;
    }


    /**
     * Sets the minx value for this Mapa.
     * 
     * @param minx
     */
    public void setMinx(java.lang.Double minx) {
        this.minx = minx;
    }


    /**
     * Gets the miny value for this Mapa.
     * 
     * @return miny
     */
    public java.lang.Double getMiny() {
        return miny;
    }


    /**
     * Sets the miny value for this Mapa.
     * 
     * @param miny
     */
    public void setMiny(java.lang.Double miny) {
        this.miny = miny;
    }


    /**
     * Gets the name value for this Mapa.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Mapa.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the srid value for this Mapa.
     * 
     * @return srid
     */
    public java.lang.String getSrid() {
        return srid;
    }


    /**
     * Sets the srid value for this Mapa.
     * 
     * @param srid
     */
    public void setSrid(java.lang.String srid) {
        this.srid = srid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Mapa)) return false;
        Mapa other = (Mapa) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.mapdefault==null && other.getMapdefault()==null) || 
             (this.mapdefault!=null &&
              this.mapdefault.equals(other.getMapdefault()))) &&
            ((this.mapid==null && other.getMapid()==null) || 
             (this.mapid!=null &&
              this.mapid.equals(other.getMapid()))) &&
            ((this.mapidgeopista==null && other.getMapidgeopista()==null) || 
             (this.mapidgeopista!=null &&
              this.mapidgeopista.equals(other.getMapidgeopista()))) &&
            ((this.mapidentidad==null && other.getMapidentidad()==null) || 
             (this.mapidentidad!=null &&
              this.mapidentidad.equals(other.getMapidentidad()))) &&
            ((this.mappublic==null && other.getMappublic()==null) || 
             (this.mappublic!=null &&
              this.mappublic.equals(other.getMappublic()))) &&
            ((this.maxx==null && other.getMaxx()==null) || 
             (this.maxx!=null &&
              this.maxx.equals(other.getMaxx()))) &&
            ((this.maxy==null && other.getMaxy()==null) || 
             (this.maxy!=null &&
              this.maxy.equals(other.getMaxy()))) &&
            ((this.minx==null && other.getMinx()==null) || 
             (this.minx!=null &&
              this.minx.equals(other.getMinx()))) &&
            ((this.miny==null && other.getMiny()==null) || 
             (this.miny!=null &&
              this.miny.equals(other.getMiny()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.srid==null && other.getSrid()==null) || 
             (this.srid!=null &&
              this.srid.equals(other.getSrid())));
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
        if (getMapdefault() != null) {
            _hashCode += getMapdefault().hashCode();
        }
        if (getMapid() != null) {
            _hashCode += getMapid().hashCode();
        }
        if (getMapidgeopista() != null) {
            _hashCode += getMapidgeopista().hashCode();
        }
        if (getMapidentidad() != null) {
            _hashCode += getMapidentidad().hashCode();
        }
        if (getMappublic() != null) {
            _hashCode += getMappublic().hashCode();
        }
        if (getMaxx() != null) {
            _hashCode += getMaxx().hashCode();
        }
        if (getMaxy() != null) {
            _hashCode += getMaxy().hashCode();
        }
        if (getMinx() != null) {
            _hashCode += getMinx().hashCode();
        }
        if (getMiny() != null) {
            _hashCode += getMiny().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getSrid() != null) {
            _hashCode += getSrid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Mapa.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "Mapa"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mapdefault");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "mapdefault"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mapid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "mapid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mapidgeopista");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "mapidgeopista"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mapidentidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "mapidentidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mappublic");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "mappublic"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxx");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "maxx"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "maxy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minx");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "minx"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("miny");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "miny"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("srid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.geolocalizacion.sgm.tecdoc.ieci", "srid"));
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
