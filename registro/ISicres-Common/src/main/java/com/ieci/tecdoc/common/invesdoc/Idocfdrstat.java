package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCFDRSTAT"
 *     
*/
public class Idocfdrstat implements Serializable {

    /** identifier field */
    private int fdrid;

    /** identifier field */
    private int archid;

    /** identifier field */
    private int stat;

    /** identifier field */
    private int userid;

    /** identifier field */
    private Date timestamp;

    /** identifier field */
    private int flags;

    /** full constructor */
    public Idocfdrstat(int fdrid, int archid, int stat, int userid, Date timestamp, int flags) {
        this.fdrid = fdrid;
        this.archid = archid;
        this.stat = stat;
        this.userid = userid;
        this.timestamp = timestamp;
        this.flags = flags;
    }

    /** default constructor */
    public Idocfdrstat() {
    }

    /** 
     *                @hibernate.property
     *                 column="FDRID"
     *                 length="10"
     *             
     */
    public int getFdrid() {
        return this.fdrid;
    }

    public void setFdrid(int fdrid) {
        this.fdrid = fdrid;
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
     *                 column="USERID"
     *                 length="10"
     *             
     */
    public int getUserid() {
        return this.userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    /** 
     *                @hibernate.property
     *                 column="TIMESTAMP"
     *                 length="7"
     *             
     */
    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("fdrid", getFdrid())
            .append("archid", getArchid())
            .append("stat", getStat())
            .append("userid", getUserid())
            .append("timestamp", getTimestamp())
            .append("flags", getFlags())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idocfdrstat) ) return false;
        Idocfdrstat castOther = (Idocfdrstat) other;
        return new EqualsBuilder()
            .append(this.getFdrid(), castOther.getFdrid())
            .append(this.getArchid(), castOther.getArchid())
            .append(this.getStat(), castOther.getStat())
            .append(this.getUserid(), castOther.getUserid())
            .append(this.getTimestamp(), castOther.getTimestamp())
            .append(this.getFlags(), castOther.getFlags())
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
            .append(getFdrid())
            .append(getArchid())
            .append(getStat())
            .append(getUserid())
            .append(getTimestamp())
            .append(getFlags())
            .toHashCode();
    }

}
