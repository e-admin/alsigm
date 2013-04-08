package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_EXPTASKS"
 *     
*/
public class ScrExptask implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int idUser;

    /** identifier field */
    private String description;

    /** identifier field */
    private int idSourcearch;

    /** identifier field */
    private int idTargetarch;

    /** identifier field */
    private int idTargetdb;

    /** identifier field */
    private int idFilter;

    /** identifier field */
    private int idConnection;

    /** identifier field */
    private int status;

    /** identifier field */
    private int deleteSource;

    /** identifier field */
    private Date explastdate;

    /** full constructor */
    public ScrExptask(int id, int idUser, String description, int idSourcearch, int idTargetarch, int idTargetdb, int idFilter, int idConnection, int status, int deleteSource, Date explastdate) {
        this.id = id;
        this.idUser = idUser;
        this.description = description;
        this.idSourcearch = idSourcearch;
        this.idTargetarch = idTargetarch;
        this.idTargetdb = idTargetdb;
        this.idFilter = idFilter;
        this.idConnection = idConnection;
        this.status = status;
        this.deleteSource = deleteSource;
        this.explastdate = explastdate;
    }

    /** default constructor */
    public ScrExptask() {
    }

    /** 
     *                @hibernate.property
     *                 column="ID"
     *                 length="10"
     *             
     */
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_USER"
     *                 length="10"
     *             
     */
    public int getIdUser() {
        return this.idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /** 
     *                @hibernate.property
     *                 column="DESCRIPTION"
     *                 length="50"
     *             
     */
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_SOURCEARCH"
     *                 length="10"
     *             
     */
    public int getIdSourcearch() {
        return this.idSourcearch;
    }

    public void setIdSourcearch(int idSourcearch) {
        this.idSourcearch = idSourcearch;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_TARGETARCH"
     *                 length="10"
     *             
     */
    public int getIdTargetarch() {
        return this.idTargetarch;
    }

    public void setIdTargetarch(int idTargetarch) {
        this.idTargetarch = idTargetarch;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_TARGETDB"
     *                 length="10"
     *             
     */
    public int getIdTargetdb() {
        return this.idTargetdb;
    }

    public void setIdTargetdb(int idTargetdb) {
        this.idTargetdb = idTargetdb;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_FILTER"
     *                 length="10"
     *             
     */
    public int getIdFilter() {
        return this.idFilter;
    }

    public void setIdFilter(int idFilter) {
        this.idFilter = idFilter;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_CONNECTION"
     *                 length="10"
     *             
     */
    public int getIdConnection() {
        return this.idConnection;
    }

    public void setIdConnection(int idConnection) {
        this.idConnection = idConnection;
    }

    /** 
     *                @hibernate.property
     *                 column="STATUS"
     *                 length="10"
     *             
     */
    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /** 
     *                @hibernate.property
     *                 column="DELETE_SOURCE"
     *                 length="10"
     *             
     */
    public int getDeleteSource() {
        return this.deleteSource;
    }

    public void setDeleteSource(int deleteSource) {
        this.deleteSource = deleteSource;
    }

    /** 
     *                @hibernate.property
     *                 column="EXPLASTDATE"
     *                 length="7"
     *             
     */
    public Date getExplastdate() {
        return this.explastdate;
    }

    public void setExplastdate(Date explastdate) {
        this.explastdate = explastdate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("idUser", getIdUser())
            .append("description", getDescription())
            .append("idSourcearch", getIdSourcearch())
            .append("idTargetarch", getIdTargetarch())
            .append("idTargetdb", getIdTargetdb())
            .append("idFilter", getIdFilter())
            .append("idConnection", getIdConnection())
            .append("status", getStatus())
            .append("deleteSource", getDeleteSource())
            .append("explastdate", getExplastdate())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrExptask) ) return false;
        ScrExptask castOther = (ScrExptask) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getIdUser(), castOther.getIdUser())
            .append(this.getDescription(), castOther.getDescription())
            .append(this.getIdSourcearch(), castOther.getIdSourcearch())
            .append(this.getIdTargetarch(), castOther.getIdTargetarch())
            .append(this.getIdTargetdb(), castOther.getIdTargetdb())
            .append(this.getIdFilter(), castOther.getIdFilter())
            .append(this.getIdConnection(), castOther.getIdConnection())
            .append(this.getStatus(), castOther.getStatus())
            .append(this.getDeleteSource(), castOther.getDeleteSource())
            .append(this.getExplastdate(), castOther.getExplastdate())
            .isEquals();
    }

    
         
                                       
//************************************
// Incluido pos ISicres-Common Oracle 9i


public String toXML() {
       String className = getClass().getName();
       className = className.substring(className.lastIndexOf(".") + 1, className.length()).toUpperCase();
       StringBuffer buffer = new StringBuffer();
       buffer.append("<");
       buffer.append(className);
       buffer.append(">");
       try {
           java.lang.reflect.Field[] fields = getClass().getDeclaredFields();
           java.lang.reflect.Field field = null;
           String name = null;
           int size = fields.length;
           for (int i = 0; i < size; i++) {
               field = fields[i];
               name = field.getName();
               buffer.append("<");
               buffer.append(name.toUpperCase());
               buffer.append(">");
               if (field.get(this) != null) {
                   buffer.append(field.get(this));
               }
               buffer.append("</");
               buffer.append(name.toUpperCase());
               buffer.append(">");
           }
       } catch (Exception e) {
           e.printStackTrace(System.err);
       }
       buffer.append("</");
       buffer.append(className);
       buffer.append(">");
       return buffer.toString();
}
                               
//************************************  
                                                                                                                                                                   
public int hashCode() {
      
        return new HashCodeBuilder()
            .append(getId())
            .append(getIdUser())
            .append(getDescription())
            .append(getIdSourcearch())
            .append(getIdTargetarch())
            .append(getIdTargetdb())
            .append(getIdFilter())
            .append(getIdConnection())
            .append(getStatus())
            .append(getDeleteSource())
            .append(getExplastdate())
            .toHashCode();
    }

}
