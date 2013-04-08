package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCRPT"
 *     
*/
public class Idocrpt implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private String name;

    /** identifier field */
    private int archid;

    /** identifier field */
    private int edittype;

    /** identifier field */
    private Blob data;

    /** identifier field */
    private String remarks;

    /** identifier field */
    private int accesstype;

    /** identifier field */
    private int acsid;

    /** identifier field */
    private int crtrid;

    /** identifier field */
    private Date crtndate;

    /** identifier field */
    private Integer updrid;

    /** identifier field */
    private Date upddate;

    /** full constructor */
    public Idocrpt(int id, String name, int archid, int edittype, Blob data, String remarks, int accesstype, int acsid, int crtrid, Date crtndate, Integer updrid, Date upddate) {
        this.id = id;
        this.name = name;
        this.archid = archid;
        this.edittype = edittype;
        this.data = data;
        this.remarks = remarks;
        this.accesstype = accesstype;
        this.acsid = acsid;
        this.crtrid = crtrid;
        this.crtndate = crtndate;
        this.updrid = updrid;
        this.upddate = upddate;
    }

    /** default constructor */
    public Idocrpt() {
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
     *                 column="NAME"
     *                 length="32"
     *             
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** 
     *                @hibernate.property
     *                 column="ARCHID"
     *                 length="10"
     *             
     */
    public int getArchid() {
        return this.archid;
    }

    public void setArchid(int archid) {
        this.archid = archid;
    }

    /** 
     *                @hibernate.property
     *                 column="EDITTYPE"
     *                 length="10"
     *             
     */
    public int getEdittype() {
        return this.edittype;
    }

    public void setEdittype(int edittype) {
        this.edittype = edittype;
    }

    /** 
     *                @hibernate.property
     *                 column="DATA"
     *                 length="0"
     *             
     */
    public Blob getData() {
        return this.data;
    }

    public void setData(Blob data) {
        this.data = data;
    }

    /** 
     *                @hibernate.property
     *                 column="REMARKS"
     *                 length="254"
     *             
     */
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /** 
     *                @hibernate.property
     *                 column="ACCESSTYPE"
     *                 length="10"
     *             
     */
    public int getAccesstype() {
        return this.accesstype;
    }

    public void setAccesstype(int accesstype) {
        this.accesstype = accesstype;
    }

    /** 
     *                @hibernate.property
     *                 column="ACSID"
     *                 length="10"
     *             
     */
    public int getAcsid() {
        return this.acsid;
    }

    public void setAcsid(int acsid) {
        this.acsid = acsid;
    }

    /** 
     *                @hibernate.property
     *                 column="CRTRID"
     *                 length="10"
     *             
     */
    public int getCrtrid() {
        return this.crtrid;
    }

    public void setCrtrid(int crtrid) {
        this.crtrid = crtrid;
    }

    /** 
     *                @hibernate.property
     *                 column="CRTNDATE"
     *                 length="7"
     *             
     */
    public Date getCrtndate() {
        return this.crtndate;
    }

    public void setCrtndate(Date crtndate) {
        this.crtndate = crtndate;
    }

    /** 
     *                @hibernate.property
     *                 column="UPDRID"
     *                 length="10"
     *             
     */
    public Integer getUpdrid() {
        return this.updrid;
    }

    public void setUpdrid(Integer updrid) {
        this.updrid = updrid;
    }

    /** 
     *                @hibernate.property
     *                 column="UPDDATE"
     *                 length="7"
     *             
     */
    public Date getUpddate() {
        return this.upddate;
    }

    public void setUpddate(Date upddate) {
        this.upddate = upddate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("name", getName())
            .append("archid", getArchid())
            .append("edittype", getEdittype())
            .append("data", getData())
            .append("remarks", getRemarks())
            .append("accesstype", getAccesstype())
            .append("acsid", getAcsid())
            .append("crtrid", getCrtrid())
            .append("crtndate", getCrtndate())
            .append("updrid", getUpdrid())
            .append("upddate", getUpddate())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idocrpt) ) return false;
        Idocrpt castOther = (Idocrpt) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getName(), castOther.getName())
            .append(this.getArchid(), castOther.getArchid())
            .append(this.getEdittype(), castOther.getEdittype())
            .append(this.getData(), castOther.getData())
            .append(this.getRemarks(), castOther.getRemarks())
            .append(this.getAccesstype(), castOther.getAccesstype())
            .append(this.getAcsid(), castOther.getAcsid())
            .append(this.getCrtrid(), castOther.getCrtrid())
            .append(this.getCrtndate(), castOther.getCrtndate())
            .append(this.getUpdrid(), castOther.getUpdrid())
            .append(this.getUpddate(), castOther.getUpddate())
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
            .append(getName())
            .append(getArchid())
            .append(getEdittype())
            .append(getData())
            .append(getRemarks())
            .append(getAccesstype())
            .append(getAcsid())
            .append(getCrtrid())
            .append(getCrtndate())
            .append(getUpdrid())
            .append(getUpddate())
            .toHashCode();
    }

}
