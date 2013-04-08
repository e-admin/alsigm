package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_USRPERMS"
 *     
*/
public class ScrUsrperm implements Serializable {

    /** identifier field */
    private Integer idUsr;

    /** persistent field */
    private Date tmstamp;

    /** persistent field */
    private int perms;

    /** full constructor */
    public ScrUsrperm(Integer idUsr, Date tmstamp, int perms) {
        this.idUsr = idUsr;
        this.tmstamp = tmstamp;
        this.perms = perms;
    }

    /** default constructor */
    public ScrUsrperm() {
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Integer"
     *             column="ID_USR"
     *         
     */
    public Integer getIdUsr() {
        return this.idUsr;
    }

    public void setIdUsr(Integer idUsr) {
        this.idUsr = idUsr;
    }

    /** 
     *            @hibernate.property
     *             column="TMSTAMP"
     *             length="7"
     *             not-null="true"
     *         
     */
    public Date getTmstamp() {
        return this.tmstamp;
    }

    public void setTmstamp(Date tmstamp) {
        this.tmstamp = tmstamp;
    }

    /** 
     *            @hibernate.property
     *             column="PERMS"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getPerms() {
        return this.perms;
    }

    public void setPerms(int perms) {
        this.perms = perms;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("idUsr", getIdUsr())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrUsrperm) ) return false;
        ScrUsrperm castOther = (ScrUsrperm) other;
        return new EqualsBuilder()
            .append(this.getIdUsr(), castOther.getIdUsr())
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
            .append(getIdUsr())
            .toHashCode();
    }

}
