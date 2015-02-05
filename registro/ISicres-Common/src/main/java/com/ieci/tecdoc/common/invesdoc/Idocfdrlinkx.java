package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCFDRLINKX"
 *     
*/
public class Idocfdrlinkx implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private String name;

    /** identifier field */
    private int cntrdbid;

    /** identifier field */
    private int cntrarchid;

    /** identifier field */
    private int cntrfdrid;

    /** identifier field */
    private int cntrclfid;

    /** identifier field */
    private int srvrdbid;

    /** identifier field */
    private int srvrarchid;

    /** identifier field */
    private int srvrfdrid;

    /** identifier field */
    private int flags;

    /** identifier field */
    private int stat;

    /** identifier field */
    private int crtrid;

    /** identifier field */
    private Date crtndate;

    /** full constructor */
    public Idocfdrlinkx(int id, String name, int cntrdbid, int cntrarchid, int cntrfdrid, int cntrclfid, int srvrdbid, int srvrarchid, int srvrfdrid, int flags, int stat, int crtrid, Date crtndate) {
        this.id = id;
        this.name = name;
        this.cntrdbid = cntrdbid;
        this.cntrarchid = cntrarchid;
        this.cntrfdrid = cntrfdrid;
        this.cntrclfid = cntrclfid;
        this.srvrdbid = srvrdbid;
        this.srvrarchid = srvrarchid;
        this.srvrfdrid = srvrfdrid;
        this.flags = flags;
        this.stat = stat;
        this.crtrid = crtrid;
        this.crtndate = crtndate;
    }

    /** default constructor */
    public Idocfdrlinkx() {
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
     *                 column="CNTRDBID"
     *                 length="10"
     *             
     */
    public int getCntrdbid() {
        return this.cntrdbid;
    }

    public void setCntrdbid(int cntrdbid) {
        this.cntrdbid = cntrdbid;
    }

    /** 
     *                @hibernate.property
     *                 column="CNTRARCHID"
     *                 length="10"
     *             
     */
    public int getCntrarchid() {
        return this.cntrarchid;
    }

    public void setCntrarchid(int cntrarchid) {
        this.cntrarchid = cntrarchid;
    }

    /** 
     *                @hibernate.property
     *                 column="CNTRFDRID"
     *                 length="10"
     *             
     */
    public int getCntrfdrid() {
        return this.cntrfdrid;
    }

    public void setCntrfdrid(int cntrfdrid) {
        this.cntrfdrid = cntrfdrid;
    }

    /** 
     *                @hibernate.property
     *                 column="CNTRCLFID"
     *                 length="10"
     *             
     */
    public int getCntrclfid() {
        return this.cntrclfid;
    }

    public void setCntrclfid(int cntrclfid) {
        this.cntrclfid = cntrclfid;
    }

    /** 
     *                @hibernate.property
     *                 column="SRVRDBID"
     *                 length="10"
     *             
     */
    public int getSrvrdbid() {
        return this.srvrdbid;
    }

    public void setSrvrdbid(int srvrdbid) {
        this.srvrdbid = srvrdbid;
    }

    /** 
     *                @hibernate.property
     *                 column="SRVRARCHID"
     *                 length="10"
     *             
     */
    public int getSrvrarchid() {
        return this.srvrarchid;
    }

    public void setSrvrarchid(int srvrarchid) {
        this.srvrarchid = srvrarchid;
    }

    /** 
     *                @hibernate.property
     *                 column="SRVRFDRID"
     *                 length="10"
     *             
     */
    public int getSrvrfdrid() {
        return this.srvrfdrid;
    }

    public void setSrvrfdrid(int srvrfdrid) {
        this.srvrfdrid = srvrfdrid;
    }

    /** 
     *                @hibernate.property
     *                 column="FLAGS"
     *                 length="10"
     *             
     */
    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    /** 
     *                @hibernate.property
     *                 column="STAT"
     *                 length="10"
     *             
     */
    public int getStat() {
        return this.stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("name", getName())
            .append("cntrdbid", getCntrdbid())
            .append("cntrarchid", getCntrarchid())
            .append("cntrfdrid", getCntrfdrid())
            .append("cntrclfid", getCntrclfid())
            .append("srvrdbid", getSrvrdbid())
            .append("srvrarchid", getSrvrarchid())
            .append("srvrfdrid", getSrvrfdrid())
            .append("flags", getFlags())
            .append("stat", getStat())
            .append("crtrid", getCrtrid())
            .append("crtndate", getCrtndate())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idocfdrlinkx) ) return false;
        Idocfdrlinkx castOther = (Idocfdrlinkx) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getName(), castOther.getName())
            .append(this.getCntrdbid(), castOther.getCntrdbid())
            .append(this.getCntrarchid(), castOther.getCntrarchid())
            .append(this.getCntrfdrid(), castOther.getCntrfdrid())
            .append(this.getCntrclfid(), castOther.getCntrclfid())
            .append(this.getSrvrdbid(), castOther.getSrvrdbid())
            .append(this.getSrvrarchid(), castOther.getSrvrarchid())
            .append(this.getSrvrfdrid(), castOther.getSrvrfdrid())
            .append(this.getFlags(), castOther.getFlags())
            .append(this.getStat(), castOther.getStat())
            .append(this.getCrtrid(), castOther.getCrtrid())
            .append(this.getCrtndate(), castOther.getCrtndate())
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
            .append(getCntrdbid())
            .append(getCntrarchid())
            .append(getCntrfdrid())
            .append(getCntrclfid())
            .append(getSrvrdbid())
            .append(getSrvrarchid())
            .append(getSrvrfdrid())
            .append(getFlags())
            .append(getStat())
            .append(getCrtrid())
            .append(getCrtndate())
            .toHashCode();
    }

}
