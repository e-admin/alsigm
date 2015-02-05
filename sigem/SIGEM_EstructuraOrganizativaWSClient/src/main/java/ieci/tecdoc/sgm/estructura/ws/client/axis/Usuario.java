/**
 * Usuario.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public class Usuario  extends ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private java.util.Calendar creationDate;

    private int creatorId;

    private int deptId;

    private java.lang.String description;

    private int id;

    private boolean isChange;

    private java.lang.String name;

    private java.lang.String oldPassword;

    private java.lang.String password;

    private ieci.tecdoc.sgm.estructura.ws.client.axis.PermisosGenericos perms;

    private ieci.tecdoc.sgm.estructura.ws.client.axis.PerfilesGenericos profiles;

    private long pwdLastUpdTs;

    private java.lang.String pwdmbc;

    private int pwdminlen;

    private java.lang.String pwdvpcheck;

    private int state;

    private java.util.Calendar updateDate;

    private int updaterId;

    private int userConnected;

    private boolean wasAdmin;

    public Usuario() {
    }

    public Usuario(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.util.Calendar creationDate,
           int creatorId,
           int deptId,
           java.lang.String description,
           int id,
           boolean isChange,
           java.lang.String name,
           java.lang.String oldPassword,
           java.lang.String password,
           ieci.tecdoc.sgm.estructura.ws.client.axis.PermisosGenericos perms,
           ieci.tecdoc.sgm.estructura.ws.client.axis.PerfilesGenericos profiles,
           long pwdLastUpdTs,
           java.lang.String pwdmbc,
           int pwdminlen,
           java.lang.String pwdvpcheck,
           int state,
           java.util.Calendar updateDate,
           int updaterId,
           int userConnected,
           boolean wasAdmin) {
        super(
            errorCode,
            returnCode);
        this.creationDate = creationDate;
        this.creatorId = creatorId;
        this.deptId = deptId;
        this.description = description;
        this.id = id;
        this.isChange = isChange;
        this.name = name;
        this.oldPassword = oldPassword;
        this.password = password;
        this.perms = perms;
        this.profiles = profiles;
        this.pwdLastUpdTs = pwdLastUpdTs;
        this.pwdmbc = pwdmbc;
        this.pwdminlen = pwdminlen;
        this.pwdvpcheck = pwdvpcheck;
        this.state = state;
        this.updateDate = updateDate;
        this.updaterId = updaterId;
        this.userConnected = userConnected;
        this.wasAdmin = wasAdmin;
    }


    /**
     * Gets the creationDate value for this Usuario.
     * 
     * @return creationDate
     */
    public java.util.Calendar getCreationDate() {
        return creationDate;
    }


    /**
     * Sets the creationDate value for this Usuario.
     * 
     * @param creationDate
     */
    public void setCreationDate(java.util.Calendar creationDate) {
        this.creationDate = creationDate;
    }


    /**
     * Gets the creatorId value for this Usuario.
     * 
     * @return creatorId
     */
    public int getCreatorId() {
        return creatorId;
    }


    /**
     * Sets the creatorId value for this Usuario.
     * 
     * @param creatorId
     */
    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }


    /**
     * Gets the deptId value for this Usuario.
     * 
     * @return deptId
     */
    public int getDeptId() {
        return deptId;
    }


    /**
     * Sets the deptId value for this Usuario.
     * 
     * @param deptId
     */
    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }


    /**
     * Gets the description value for this Usuario.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Usuario.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the id value for this Usuario.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this Usuario.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the isChange value for this Usuario.
     * 
     * @return isChange
     */
    public boolean isIsChange() {
        return isChange;
    }


    /**
     * Sets the isChange value for this Usuario.
     * 
     * @param isChange
     */
    public void setIsChange(boolean isChange) {
        this.isChange = isChange;
    }


    /**
     * Gets the name value for this Usuario.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Usuario.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the oldPassword value for this Usuario.
     * 
     * @return oldPassword
     */
    public java.lang.String getOldPassword() {
        return oldPassword;
    }


    /**
     * Sets the oldPassword value for this Usuario.
     * 
     * @param oldPassword
     */
    public void setOldPassword(java.lang.String oldPassword) {
        this.oldPassword = oldPassword;
    }


    /**
     * Gets the password value for this Usuario.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this Usuario.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the perms value for this Usuario.
     * 
     * @return perms
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.PermisosGenericos getPerms() {
        return perms;
    }


    /**
     * Sets the perms value for this Usuario.
     * 
     * @param perms
     */
    public void setPerms(ieci.tecdoc.sgm.estructura.ws.client.axis.PermisosGenericos perms) {
        this.perms = perms;
    }


    /**
     * Gets the profiles value for this Usuario.
     * 
     * @return profiles
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.PerfilesGenericos getProfiles() {
        return profiles;
    }


    /**
     * Sets the profiles value for this Usuario.
     * 
     * @param profiles
     */
    public void setProfiles(ieci.tecdoc.sgm.estructura.ws.client.axis.PerfilesGenericos profiles) {
        this.profiles = profiles;
    }


    /**
     * Gets the pwdLastUpdTs value for this Usuario.
     * 
     * @return pwdLastUpdTs
     */
    public long getPwdLastUpdTs() {
        return pwdLastUpdTs;
    }


    /**
     * Sets the pwdLastUpdTs value for this Usuario.
     * 
     * @param pwdLastUpdTs
     */
    public void setPwdLastUpdTs(long pwdLastUpdTs) {
        this.pwdLastUpdTs = pwdLastUpdTs;
    }


    /**
     * Gets the pwdmbc value for this Usuario.
     * 
     * @return pwdmbc
     */
    public java.lang.String getPwdmbc() {
        return pwdmbc;
    }


    /**
     * Sets the pwdmbc value for this Usuario.
     * 
     * @param pwdmbc
     */
    public void setPwdmbc(java.lang.String pwdmbc) {
        this.pwdmbc = pwdmbc;
    }


    /**
     * Gets the pwdminlen value for this Usuario.
     * 
     * @return pwdminlen
     */
    public int getPwdminlen() {
        return pwdminlen;
    }


    /**
     * Sets the pwdminlen value for this Usuario.
     * 
     * @param pwdminlen
     */
    public void setPwdminlen(int pwdminlen) {
        this.pwdminlen = pwdminlen;
    }


    /**
     * Gets the pwdvpcheck value for this Usuario.
     * 
     * @return pwdvpcheck
     */
    public java.lang.String getPwdvpcheck() {
        return pwdvpcheck;
    }


    /**
     * Sets the pwdvpcheck value for this Usuario.
     * 
     * @param pwdvpcheck
     */
    public void setPwdvpcheck(java.lang.String pwdvpcheck) {
        this.pwdvpcheck = pwdvpcheck;
    }


    /**
     * Gets the state value for this Usuario.
     * 
     * @return state
     */
    public int getState() {
        return state;
    }


    /**
     * Sets the state value for this Usuario.
     * 
     * @param state
     */
    public void setState(int state) {
        this.state = state;
    }


    /**
     * Gets the updateDate value for this Usuario.
     * 
     * @return updateDate
     */
    public java.util.Calendar getUpdateDate() {
        return updateDate;
    }


    /**
     * Sets the updateDate value for this Usuario.
     * 
     * @param updateDate
     */
    public void setUpdateDate(java.util.Calendar updateDate) {
        this.updateDate = updateDate;
    }


    /**
     * Gets the updaterId value for this Usuario.
     * 
     * @return updaterId
     */
    public int getUpdaterId() {
        return updaterId;
    }


    /**
     * Sets the updaterId value for this Usuario.
     * 
     * @param updaterId
     */
    public void setUpdaterId(int updaterId) {
        this.updaterId = updaterId;
    }


    /**
     * Gets the userConnected value for this Usuario.
     * 
     * @return userConnected
     */
    public int getUserConnected() {
        return userConnected;
    }


    /**
     * Sets the userConnected value for this Usuario.
     * 
     * @param userConnected
     */
    public void setUserConnected(int userConnected) {
        this.userConnected = userConnected;
    }


    /**
     * Gets the wasAdmin value for this Usuario.
     * 
     * @return wasAdmin
     */
    public boolean isWasAdmin() {
        return wasAdmin;
    }


    /**
     * Sets the wasAdmin value for this Usuario.
     * 
     * @param wasAdmin
     */
    public void setWasAdmin(boolean wasAdmin) {
        this.wasAdmin = wasAdmin;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Usuario)) return false;
        Usuario other = (Usuario) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.creationDate==null && other.getCreationDate()==null) || 
             (this.creationDate!=null &&
              this.creationDate.equals(other.getCreationDate()))) &&
            this.creatorId == other.getCreatorId() &&
            this.deptId == other.getDeptId() &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            this.id == other.getId() &&
            this.isChange == other.isIsChange() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.oldPassword==null && other.getOldPassword()==null) || 
             (this.oldPassword!=null &&
              this.oldPassword.equals(other.getOldPassword()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.perms==null && other.getPerms()==null) || 
             (this.perms!=null &&
              this.perms.equals(other.getPerms()))) &&
            ((this.profiles==null && other.getProfiles()==null) || 
             (this.profiles!=null &&
              this.profiles.equals(other.getProfiles()))) &&
            this.pwdLastUpdTs == other.getPwdLastUpdTs() &&
            ((this.pwdmbc==null && other.getPwdmbc()==null) || 
             (this.pwdmbc!=null &&
              this.pwdmbc.equals(other.getPwdmbc()))) &&
            this.pwdminlen == other.getPwdminlen() &&
            ((this.pwdvpcheck==null && other.getPwdvpcheck()==null) || 
             (this.pwdvpcheck!=null &&
              this.pwdvpcheck.equals(other.getPwdvpcheck()))) &&
            this.state == other.getState() &&
            ((this.updateDate==null && other.getUpdateDate()==null) || 
             (this.updateDate!=null &&
              this.updateDate.equals(other.getUpdateDate()))) &&
            this.updaterId == other.getUpdaterId() &&
            this.userConnected == other.getUserConnected() &&
            this.wasAdmin == other.isWasAdmin();
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
        if (getCreationDate() != null) {
            _hashCode += getCreationDate().hashCode();
        }
        _hashCode += getCreatorId();
        _hashCode += getDeptId();
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        _hashCode += getId();
        _hashCode += (isIsChange() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getOldPassword() != null) {
            _hashCode += getOldPassword().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getPerms() != null) {
            _hashCode += getPerms().hashCode();
        }
        if (getProfiles() != null) {
            _hashCode += getProfiles().hashCode();
        }
        _hashCode += new Long(getPwdLastUpdTs()).hashCode();
        if (getPwdmbc() != null) {
            _hashCode += getPwdmbc().hashCode();
        }
        _hashCode += getPwdminlen();
        if (getPwdvpcheck() != null) {
            _hashCode += getPwdvpcheck().hashCode();
        }
        _hashCode += getState();
        if (getUpdateDate() != null) {
            _hashCode += getUpdateDate().hashCode();
        }
        _hashCode += getUpdaterId();
        _hashCode += getUserConnected();
        _hashCode += (isWasAdmin() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Usuario.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuario"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "creationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creatorId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "creatorId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deptId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "deptId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isChange");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "isChange"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldPassword");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "oldPassword"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("perms");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "perms"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "PermisosGenericos"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profiles");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "profiles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "PerfilesGenericos"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pwdLastUpdTs");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "pwdLastUpdTs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pwdmbc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "pwdmbc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pwdminlen");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "pwdminlen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pwdvpcheck");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "pwdvpcheck"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "state"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "updateDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updaterId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "updaterId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userConnected");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "userConnected"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wasAdmin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "wasAdmin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
