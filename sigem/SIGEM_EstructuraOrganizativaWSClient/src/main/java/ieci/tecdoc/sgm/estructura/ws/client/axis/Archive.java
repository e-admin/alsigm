/**
 * Archive.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public class Archive  extends ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private int adminUserId;

    private ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveFlds fldsDef;

    private boolean ftsInContents;

    private ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveIdxs idxsDef;

    private ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveMisc miscDef;

    private java.lang.String name;

    private int parentId;

    private java.lang.String remarks;

    private int typeId;

    public Archive() {
    }

    public Archive(
           java.lang.String errorCode,
           java.lang.String returnCode,
           int adminUserId,
           ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveFlds fldsDef,
           boolean ftsInContents,
           ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveIdxs idxsDef,
           ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveMisc miscDef,
           java.lang.String name,
           int parentId,
           java.lang.String remarks,
           int typeId) {
        super(
            errorCode,
            returnCode);
        this.adminUserId = adminUserId;
        this.fldsDef = fldsDef;
        this.ftsInContents = ftsInContents;
        this.idxsDef = idxsDef;
        this.miscDef = miscDef;
        this.name = name;
        this.parentId = parentId;
        this.remarks = remarks;
        this.typeId = typeId;
    }


    /**
     * Gets the adminUserId value for this Archive.
     * 
     * @return adminUserId
     */
    public int getAdminUserId() {
        return adminUserId;
    }


    /**
     * Sets the adminUserId value for this Archive.
     * 
     * @param adminUserId
     */
    public void setAdminUserId(int adminUserId) {
        this.adminUserId = adminUserId;
    }


    /**
     * Gets the fldsDef value for this Archive.
     * 
     * @return fldsDef
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveFlds getFldsDef() {
        return fldsDef;
    }


    /**
     * Sets the fldsDef value for this Archive.
     * 
     * @param fldsDef
     */
    public void setFldsDef(ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveFlds fldsDef) {
        this.fldsDef = fldsDef;
    }


    /**
     * Gets the ftsInContents value for this Archive.
     * 
     * @return ftsInContents
     */
    public boolean isFtsInContents() {
        return ftsInContents;
    }


    /**
     * Sets the ftsInContents value for this Archive.
     * 
     * @param ftsInContents
     */
    public void setFtsInContents(boolean ftsInContents) {
        this.ftsInContents = ftsInContents;
    }


    /**
     * Gets the idxsDef value for this Archive.
     * 
     * @return idxsDef
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveIdxs getIdxsDef() {
        return idxsDef;
    }


    /**
     * Sets the idxsDef value for this Archive.
     * 
     * @param idxsDef
     */
    public void setIdxsDef(ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveIdxs idxsDef) {
        this.idxsDef = idxsDef;
    }


    /**
     * Gets the miscDef value for this Archive.
     * 
     * @return miscDef
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveMisc getMiscDef() {
        return miscDef;
    }


    /**
     * Sets the miscDef value for this Archive.
     * 
     * @param miscDef
     */
    public void setMiscDef(ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveMisc miscDef) {
        this.miscDef = miscDef;
    }


    /**
     * Gets the name value for this Archive.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Archive.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the parentId value for this Archive.
     * 
     * @return parentId
     */
    public int getParentId() {
        return parentId;
    }


    /**
     * Sets the parentId value for this Archive.
     * 
     * @param parentId
     */
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }


    /**
     * Gets the remarks value for this Archive.
     * 
     * @return remarks
     */
    public java.lang.String getRemarks() {
        return remarks;
    }


    /**
     * Sets the remarks value for this Archive.
     * 
     * @param remarks
     */
    public void setRemarks(java.lang.String remarks) {
        this.remarks = remarks;
    }


    /**
     * Gets the typeId value for this Archive.
     * 
     * @return typeId
     */
    public int getTypeId() {
        return typeId;
    }


    /**
     * Sets the typeId value for this Archive.
     * 
     * @param typeId
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Archive)) return false;
        Archive other = (Archive) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.adminUserId == other.getAdminUserId() &&
            ((this.fldsDef==null && other.getFldsDef()==null) || 
             (this.fldsDef!=null &&
              this.fldsDef.equals(other.getFldsDef()))) &&
            this.ftsInContents == other.isFtsInContents() &&
            ((this.idxsDef==null && other.getIdxsDef()==null) || 
             (this.idxsDef!=null &&
              this.idxsDef.equals(other.getIdxsDef()))) &&
            ((this.miscDef==null && other.getMiscDef()==null) || 
             (this.miscDef!=null &&
              this.miscDef.equals(other.getMiscDef()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            this.parentId == other.getParentId() &&
            ((this.remarks==null && other.getRemarks()==null) || 
             (this.remarks!=null &&
              this.remarks.equals(other.getRemarks()))) &&
            this.typeId == other.getTypeId();
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
        _hashCode += getAdminUserId();
        if (getFldsDef() != null) {
            _hashCode += getFldsDef().hashCode();
        }
        _hashCode += (isFtsInContents() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getIdxsDef() != null) {
            _hashCode += getIdxsDef().hashCode();
        }
        if (getMiscDef() != null) {
            _hashCode += getMiscDef().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        _hashCode += getParentId();
        if (getRemarks() != null) {
            _hashCode += getRemarks().hashCode();
        }
        _hashCode += getTypeId();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Archive.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Archive"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adminUserId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "adminUserId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fldsDef");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "fldsDef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArchiveFlds"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ftsInContents");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ftsInContents"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idxsDef");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "idxsDef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArchiveIdxs"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("miscDef");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "miscDef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArchiveMisc"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "parentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remarks");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "remarks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("typeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "typeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
