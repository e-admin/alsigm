package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_PINFO"
 *     
*/
public class ScrPinfo implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private int optype;

    /** nullable persistent field */
    private int officeid;

    /** nullable persistent field */
    private String username;

    /** nullable persistent field */
    private Date opdate;

    /** full constructor */
    public ScrPinfo(Integer id, int optype, int officeid, String username, Date opdate) {
        this.id = id;
        this.optype = optype;
        this.officeid = officeid;
        this.username = username;
        this.opdate = opdate;
    }

    /** default constructor */
    public ScrPinfo() {
    }

    /** minimal constructor */
    public ScrPinfo(Integer id) {
        this.id = id;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Integer"
     *             column="ID"
     *         
     */
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 
     *                @hibernate.property
     *                 column="OPTYPE"
     *                 length="10"
     *             
     */
    public int getOptype() {
        return this.optype;
    }

    public void setOptype(int optype) {
        this.optype = optype;
    }

    /** 
     *                @hibernate.property
     *                 column="OFFICEID"
     *                 length="10"
     *             
     */
    public int getOfficeid() {
        return this.officeid;
    }

    public void setOfficeid(int officeid) {
        this.officeid = officeid;
    }

    /** 
     *                @hibernate.property
     *                 column="USERNAME"
     *                 length="32"
     *             
     */
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /** 
     *                @hibernate.property
     *                 column="OPDATE"
     *                 length="7"
     *             
     */
    public Date getOpdate() {
        return this.opdate;
    }

    public void setOpdate(Date opdate) {
        this.opdate = opdate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrPinfo) ) return false;
        ScrPinfo castOther = (ScrPinfo) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
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
            .toHashCode();
    }

}
