/**
 * Grupo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public class Grupo  extends ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.estructura.ws.client.axis.UsuariosBasicos adminUsers;

    private java.util.Calendar creationDate;

    private int creatorId;

    private java.lang.String description;

    private int id;

    private int managerId;

    private java.lang.String name;

    private ieci.tecdoc.sgm.estructura.ws.client.axis.PermisosGenericos perms;

    private int type;

    private java.util.Calendar updateDate;

    private int updaterId;

    private int userConnected;

    private ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios users;

    public Grupo() {
    }

    public Grupo(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.estructura.ws.client.axis.UsuariosBasicos adminUsers,
           java.util.Calendar creationDate,
           int creatorId,
           java.lang.String description,
           int id,
           int managerId,
           java.lang.String name,
           ieci.tecdoc.sgm.estructura.ws.client.axis.PermisosGenericos perms,
           int type,
           java.util.Calendar updateDate,
           int updaterId,
           int userConnected,
           ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios users) {
        super(
            errorCode,
            returnCode);
        this.adminUsers = adminUsers;
        this.creationDate = creationDate;
        this.creatorId = creatorId;
        this.description = description;
        this.id = id;
        this.managerId = managerId;
        this.name = name;
        this.perms = perms;
        this.type = type;
        this.updateDate = updateDate;
        this.updaterId = updaterId;
        this.userConnected = userConnected;
        this.users = users;
    }


    /**
     * Gets the adminUsers value for this Grupo.
     * 
     * @return adminUsers
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuariosBasicos getAdminUsers() {
        return adminUsers;
    }


    /**
     * Sets the adminUsers value for this Grupo.
     * 
     * @param adminUsers
     */
    public void setAdminUsers(ieci.tecdoc.sgm.estructura.ws.client.axis.UsuariosBasicos adminUsers) {
        this.adminUsers = adminUsers;
    }


    /**
     * Gets the creationDate value for this Grupo.
     * 
     * @return creationDate
     */
    public java.util.Calendar getCreationDate() {
        return creationDate;
    }


    /**
     * Sets the creationDate value for this Grupo.
     * 
     * @param creationDate
     */
    public void setCreationDate(java.util.Calendar creationDate) {
        this.creationDate = creationDate;
    }


    /**
     * Gets the creatorId value for this Grupo.
     * 
     * @return creatorId
     */
    public int getCreatorId() {
        return creatorId;
    }


    /**
     * Sets the creatorId value for this Grupo.
     * 
     * @param creatorId
     */
    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }


    /**
     * Gets the description value for this Grupo.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Grupo.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the id value for this Grupo.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this Grupo.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the managerId value for this Grupo.
     * 
     * @return managerId
     */
    public int getManagerId() {
        return managerId;
    }


    /**
     * Sets the managerId value for this Grupo.
     * 
     * @param managerId
     */
    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }


    /**
     * Gets the name value for this Grupo.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Grupo.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the perms value for this Grupo.
     * 
     * @return perms
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.PermisosGenericos getPerms() {
        return perms;
    }


    /**
     * Sets the perms value for this Grupo.
     * 
     * @param perms
     */
    public void setPerms(ieci.tecdoc.sgm.estructura.ws.client.axis.PermisosGenericos perms) {
        this.perms = perms;
    }


    /**
     * Gets the type value for this Grupo.
     * 
     * @return type
     */
    public int getType() {
        return type;
    }


    /**
     * Sets the type value for this Grupo.
     * 
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }


    /**
     * Gets the updateDate value for this Grupo.
     * 
     * @return updateDate
     */
    public java.util.Calendar getUpdateDate() {
        return updateDate;
    }


    /**
     * Sets the updateDate value for this Grupo.
     * 
     * @param updateDate
     */
    public void setUpdateDate(java.util.Calendar updateDate) {
        this.updateDate = updateDate;
    }


    /**
     * Gets the updaterId value for this Grupo.
     * 
     * @return updaterId
     */
    public int getUpdaterId() {
        return updaterId;
    }


    /**
     * Sets the updaterId value for this Grupo.
     * 
     * @param updaterId
     */
    public void setUpdaterId(int updaterId) {
        this.updaterId = updaterId;
    }


    /**
     * Gets the userConnected value for this Grupo.
     * 
     * @return userConnected
     */
    public int getUserConnected() {
        return userConnected;
    }


    /**
     * Sets the userConnected value for this Grupo.
     * 
     * @param userConnected
     */
    public void setUserConnected(int userConnected) {
        this.userConnected = userConnected;
    }


    /**
     * Gets the users value for this Grupo.
     * 
     * @return users
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios getUsers() {
        return users;
    }


    /**
     * Sets the users value for this Grupo.
     * 
     * @param users
     */
    public void setUsers(ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios users) {
        this.users = users;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Grupo)) return false;
        Grupo other = (Grupo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.adminUsers==null && other.getAdminUsers()==null) || 
             (this.adminUsers!=null &&
              this.adminUsers.equals(other.getAdminUsers()))) &&
            ((this.creationDate==null && other.getCreationDate()==null) || 
             (this.creationDate!=null &&
              this.creationDate.equals(other.getCreationDate()))) &&
            this.creatorId == other.getCreatorId() &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            this.id == other.getId() &&
            this.managerId == other.getManagerId() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.perms==null && other.getPerms()==null) || 
             (this.perms!=null &&
              this.perms.equals(other.getPerms()))) &&
            this.type == other.getType() &&
            ((this.updateDate==null && other.getUpdateDate()==null) || 
             (this.updateDate!=null &&
              this.updateDate.equals(other.getUpdateDate()))) &&
            this.updaterId == other.getUpdaterId() &&
            this.userConnected == other.getUserConnected() &&
            ((this.users==null && other.getUsers()==null) || 
             (this.users!=null &&
              this.users.equals(other.getUsers())));
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
        if (getAdminUsers() != null) {
            _hashCode += getAdminUsers().hashCode();
        }
        if (getCreationDate() != null) {
            _hashCode += getCreationDate().hashCode();
        }
        _hashCode += getCreatorId();
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        _hashCode += getId();
        _hashCode += getManagerId();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getPerms() != null) {
            _hashCode += getPerms().hashCode();
        }
        _hashCode += getType();
        if (getUpdateDate() != null) {
            _hashCode += getUpdateDate().hashCode();
        }
        _hashCode += getUpdaterId();
        _hashCode += getUserConnected();
        if (getUsers() != null) {
            _hashCode += getUsers().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Grupo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Grupo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adminUsers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "adminUsers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "UsuariosBasicos"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("managerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "managerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "name"));
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
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "type"));
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
        elemField.setFieldName("users");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "users"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "Usuarios"));
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
