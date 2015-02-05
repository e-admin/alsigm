package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_USRIDENT"
 *     
*/
public class ScrUsrIdent implements Serializable {

    /** identifier field */
    private Integer userId;

    /** persistent field */
    private Date tmstamp;

    /** persistent field */
    private String firstName;

    /** nullable persistent field */
    private String secondName;

    /** persistent field */
    private String surName;

    /** full constructor */
    public ScrUsrIdent(Integer userId, Date tmstamp, String firstName, String secondName, String surName) {
        this.userId = userId;
        this.tmstamp = tmstamp;
        this.firstName = firstName;
        this.secondName = secondName;
        this.surName = surName;
    }

    /** default constructor */
    public ScrUsrIdent() {
    }

    /** minimal constructor */
    public ScrUsrIdent(Integer userId, Date tmstamp, String firstName, String surName) {
        this.userId = userId;
        this.tmstamp = tmstamp;
        this.firstName = firstName;
        this.surName = surName;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Integer"
     *             column="USERID"
     *         
     */
    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
     *             column="FIRST_NAME"
     *             length="25"
     *             not-null="true"
     *         
     */
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** 
     *            @hibernate.property
     *             column="SECOND_NAME"
     *             length="25"
     *             not-null="false"
     *         
     */
    public String getSecondName() {
        return this.secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /** 
     *            @hibernate.property
     *             column="SURNAME"
     *             length="25"
     *             not-null="true"
     *         
     */
    public String getSurName() {
        return this.surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("userId", getUserId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrUsrIdent) ) return false;
        ScrUsrIdent castOther = (ScrUsrIdent) other;
        return new EqualsBuilder()
            .append(this.getUserId(), castOther.getUserId())
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
            .append(getUserId())
            .toHashCode();
    }

}
