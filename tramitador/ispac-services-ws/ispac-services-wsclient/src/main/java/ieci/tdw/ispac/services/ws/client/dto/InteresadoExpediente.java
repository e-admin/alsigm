package ieci.tdw.ispac.services.ws.client.dto;

import java.io.Serializable;

public class InteresadoExpediente  implements Serializable {

	private String thirdPartyId;
	
	private String indPrincipal;

    private String name;

    private String nifcif;

    private String notificationAddressType;

    private String placeCity;

    private String postalAddress;

    private String postalCode;

    private String regionCountry;

    private String telematicAddress;
    
    private String phone;
    
    private String mobilePhone;
    

    public InteresadoExpediente() {
    }

    public InteresadoExpediente(
    	   String thirdPartyId,	
           String indPrincipal,
           String name,
           String nifcif,
           String notificationAddressType,
           String placeCity,
           String postalAddress,
           String postalCode,
           String regionCountry,
           String telematicAddress,
           String phone,
           String mobilePhone) {
    	   this.thirdPartyId = thirdPartyId;
           this.indPrincipal = indPrincipal;
           this.name = name;
           this.nifcif = nifcif;
           this.notificationAddressType = notificationAddressType;
           this.placeCity = placeCity;
           this.postalAddress = postalAddress;
           this.postalCode = postalCode;
           this.regionCountry = regionCountry;
           this.telematicAddress = telematicAddress;
           this.phone = phone;
           this.mobilePhone = mobilePhone;
    }

	public String getThirdPartyId() {
		return thirdPartyId;
	}

	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}

    /**
     * Gets the indPrincipal value for this InteresadoExpediente.
     * 
     * @return indPrincipal
     */
    public String getIndPrincipal() {
        return indPrincipal;
    }


    /**
     * Sets the indPrincipal value for this InteresadoExpediente.
     * 
     * @param indPrincipal
     */
    public void setIndPrincipal(String indPrincipal) {
        this.indPrincipal = indPrincipal;
    }


    /**
     * Gets the name value for this InteresadoExpediente.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }


    /**
     * Sets the name value for this InteresadoExpediente.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Gets the nifcif value for this InteresadoExpediente.
     * 
     * @return nifcif
     */
    public String getNifcif() {
        return nifcif;
    }


    /**
     * Sets the nifcif value for this InteresadoExpediente.
     * 
     * @param nifcif
     */
    public void setNifcif(String nifcif) {
        this.nifcif = nifcif;
    }


    /**
     * Gets the notificationAddressType value for this InteresadoExpediente.
     * 
     * @return notificationAddressType
     */
    public String getNotificationAddressType() {
        return notificationAddressType;
    }


    /**
     * Sets the notificationAddressType value for this InteresadoExpediente.
     * 
     * @param notificationAddressType
     */
    public void setNotificationAddressType(String notificationAddressType) {
        this.notificationAddressType = notificationAddressType;
    }


    /**
     * Gets the placeCity value for this InteresadoExpediente.
     * 
     * @return placeCity
     */
    public String getPlaceCity() {
        return placeCity;
    }


    /**
     * Sets the placeCity value for this InteresadoExpediente.
     * 
     * @param placeCity
     */
    public void setPlaceCity(String placeCity) {
        this.placeCity = placeCity;
    }


    /**
     * Gets the postalAddress value for this InteresadoExpediente.
     * 
     * @return postalAddress
     */
    public String getPostalAddress() {
        return postalAddress;
    }


    /**
     * Sets the postalAddress value for this InteresadoExpediente.
     * 
     * @param postalAddress
     */
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }


    /**
     * Gets the postalCode value for this InteresadoExpediente.
     * 
     * @return postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }


    /**
     * Sets the postalCode value for this InteresadoExpediente.
     * 
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * Gets the regionCountry value for this InteresadoExpediente.
     * 
     * @return regionCountry
     */
    public String getRegionCountry() {
        return regionCountry;
    }


    /**
     * Sets the regionCountry value for this InteresadoExpediente.
     * 
     * @param regionCountry
     */
    public void setRegionCountry(String regionCountry) {
        this.regionCountry = regionCountry;
    }


    /**
     * Gets the telematicAddress value for this InteresadoExpediente.
     * 
     * @return telematicAddress
     */
    public String getTelematicAddress() {
        return telematicAddress;
    }


    /**
     * Sets the telematicAddress value for this InteresadoExpediente.
     * 
     * @param telematicAddress
     */
    public void setTelematicAddress(String telematicAddress) {
        this.telematicAddress = telematicAddress;
    }

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof InteresadoExpediente)) return false;
        InteresadoExpediente other = (InteresadoExpediente) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.indPrincipal==null && other.getIndPrincipal()==null) || 
             (this.indPrincipal!=null &&
              this.indPrincipal.equals(other.getIndPrincipal()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.nifcif==null && other.getNifcif()==null) || 
             (this.nifcif!=null &&
              this.nifcif.equals(other.getNifcif()))) &&
            ((this.notificationAddressType==null && other.getNotificationAddressType()==null) || 
             (this.notificationAddressType!=null &&
              this.notificationAddressType.equals(other.getNotificationAddressType()))) &&
            ((this.placeCity==null && other.getPlaceCity()==null) || 
             (this.placeCity!=null &&
              this.placeCity.equals(other.getPlaceCity()))) &&
            ((this.postalAddress==null && other.getPostalAddress()==null) || 
             (this.postalAddress!=null &&
              this.postalAddress.equals(other.getPostalAddress()))) &&
            ((this.postalCode==null && other.getPostalCode()==null) || 
             (this.postalCode!=null &&
              this.postalCode.equals(other.getPostalCode()))) &&
            ((this.regionCountry==null && other.getRegionCountry()==null) || 
             (this.regionCountry!=null &&
              this.regionCountry.equals(other.getRegionCountry()))) &&
            ((this.telematicAddress==null && other.getTelematicAddress()==null) || 
             (this.telematicAddress!=null &&
              this.telematicAddress.equals(other.getTelematicAddress()))) &&
	        ((this.thirdPartyId==null && other.getThirdPartyId()==null) || 
             (this.thirdPartyId!=null &&
              this.thirdPartyId.equals(other.getThirdPartyId()))) &&
	        ((this.phone==null && other.getPhone()==null) || 
	                (this.phone!=null &&
	                 this.phone.equals(other.getPhone()))) &&
 	        ((this.mobilePhone==null && other.getMobilePhone()==null) || 
	                (this.mobilePhone!=null &&
	                 this.mobilePhone.equals(other.getMobilePhone())));

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
        if (getIndPrincipal() != null) {
            _hashCode += getIndPrincipal().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getNifcif() != null) {
            _hashCode += getNifcif().hashCode();
        }
        if (getNotificationAddressType() != null) {
            _hashCode += getNotificationAddressType().hashCode();
        }
        if (getPlaceCity() != null) {
            _hashCode += getPlaceCity().hashCode();
        }
        if (getPostalAddress() != null) {
            _hashCode += getPostalAddress().hashCode();
        }
        if (getPostalCode() != null) {
            _hashCode += getPostalCode().hashCode();
        }
        if (getRegionCountry() != null) {
            _hashCode += getRegionCountry().hashCode();
        }
        if (getTelematicAddress() != null) {
            _hashCode += getTelematicAddress().hashCode();
        }
        if (getThirdPartyId() != null) {
            _hashCode += getThirdPartyId().hashCode();
        }
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        if (getMobilePhone() != null) {
            _hashCode += getMobilePhone().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InteresadoExpediente.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "InteresadoExpediente"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indPrincipal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "indPrincipal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nifcif");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "nifcif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notificationAddressType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "notificationAddressType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("placeCity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "placeCity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postalAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "postalAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "postalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regionCountry");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "regionCountry"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telematicAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "telematicAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("thirdPartyId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "thirdPartyId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);

        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "phone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobilePhone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.tram.sgm.tecdoc.ieci", "mobilePhone"));
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
