package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCCOMPUSG"
 *     
*/
public class Idoccompusg implements Serializable {

    /** identifier field */
    private int compid;

    /** identifier field */
    private int comptype;

    /** identifier field */
    private int usrid;

    /** identifier field */
    private int usrtype;

    /** identifier field */
    private int archid;

    /** full constructor */
    public Idoccompusg(int compid, int comptype, int usrid, int usrtype, int archid) {
        this.compid = compid;
        this.comptype = comptype;
        this.usrid = usrid;
        this.usrtype = usrtype;
        this.archid = archid;
    }

    /** default constructor */
    public Idoccompusg() {
    }

    /** 
     *                @hibernate.property
     *                 column="COMPID"
     *                 length="10"
     *             
     */
    public int getCompid() {
        return this.compid;
    }

    public void setCompid(int compid) {
        this.compid = compid;
    }

    /** 
     *                @hibernate.property
     *                 column="COMPTYPE"
     *                 length="10"
     *             
     */
    public int getComptype() {
        return this.comptype;
    }

    public void setComptype(int comptype) {
        this.comptype = comptype;
    }

    /** 
     *                @hibernate.property
     *                 column="USRID"
     *                 length="10"
     *             
     */
    public int getUsrid() {
        return this.usrid;
    }

    public void setUsrid(int usrid) {
        this.usrid = usrid;
    }

    /** 
     *                @hibernate.property
     *                 column="USRTYPE"
     *                 length="10"
     *             
     */
    public int getUsrtype() {
        return this.usrtype;
    }

    public void setUsrtype(int usrtype) {
        this.usrtype = usrtype;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("compid", getCompid())
            .append("comptype", getComptype())
            .append("usrid", getUsrid())
            .append("usrtype", getUsrtype())
            .append("archid", getArchid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idoccompusg) ) return false;
        Idoccompusg castOther = (Idoccompusg) other;
        return new EqualsBuilder()
            .append(this.getCompid(), castOther.getCompid())
            .append(this.getComptype(), castOther.getComptype())
            .append(this.getUsrid(), castOther.getUsrid())
            .append(this.getUsrtype(), castOther.getUsrtype())
            .append(this.getArchid(), castOther.getArchid())
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
            .append(getCompid())
            .append(getComptype())
            .append(getUsrid())
            .append(getUsrtype())
            .append(getArchid())
            .toHashCode();
    }

}
