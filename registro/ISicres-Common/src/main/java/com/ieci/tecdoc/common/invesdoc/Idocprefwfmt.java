package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCPREFWFMT"
 *     
*/
public class Idocprefwfmt implements Serializable {

    /** identifier field */
    private int archid;

    /** identifier field */
    private int fmttype;

    /** identifier field */
    private int userid;

    /** identifier field */
    private int fmtid;

    /** full constructor */
    public Idocprefwfmt(int archid, int fmttype, int userid, int fmtid) {
        this.archid = archid;
        this.fmttype = fmttype;
        this.userid = userid;
        this.fmtid = fmtid;
    }

    /** default constructor */
    public Idocprefwfmt() {
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
     *                 column="FMTTYPE"
     *                 length="10"
     *             
     */
    public int getFmttype() {
        return this.fmttype;
    }

    public void setFmttype(int fmttype) {
        this.fmttype = fmttype;
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
     *                 column="FMTID"
     *                 length="10"
     *             
     */
    public int getFmtid() {
        return this.fmtid;
    }

    public void setFmtid(int fmtid) {
        this.fmtid = fmtid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("archid", getArchid())
            .append("fmttype", getFmttype())
            .append("userid", getUserid())
            .append("fmtid", getFmtid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idocprefwfmt) ) return false;
        Idocprefwfmt castOther = (Idocprefwfmt) other;
        return new EqualsBuilder()
            .append(this.getArchid(), castOther.getArchid())
            .append(this.getFmttype(), castOther.getFmttype())
            .append(this.getUserid(), castOther.getUserid())
            .append(this.getFmtid(), castOther.getFmtid())
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
            .append(getArchid())
            .append(getFmttype())
            .append(getUserid())
            .append(getFmtid())
            .toHashCode();
    }

}
