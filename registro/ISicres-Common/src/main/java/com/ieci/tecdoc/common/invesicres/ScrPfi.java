package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_PFIS"
 *     
*/
public class ScrPfi implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String nif;

    /** persistent field */
    private String firstName;

    /** nullable persistent field */
    private String secondName;

    /** nullable persistent field */
    private String surname;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrTypedoc scrTypedoc;

    /** full constructor */
    public ScrPfi(Integer id, String nif, String firstName, String secondName, String surname, com.ieci.tecdoc.common.invesicres.ScrTypedoc scrTypedoc) {
        this.id = id;
        this.nif = nif;
        this.firstName = firstName;
        this.secondName = secondName;
        this.surname = surname;
        this.scrTypedoc = scrTypedoc;
    }

    /** default constructor */
    public ScrPfi() {
    }

    /** minimal constructor */
    public ScrPfi(Integer id, String firstName, com.ieci.tecdoc.common.invesicres.ScrTypedoc scrTypedoc) {
        this.id = id;
        this.firstName = firstName;
        this.scrTypedoc = scrTypedoc;
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
     *            @hibernate.property
     *             column="NIF"
     *             length="25"
     *         
     */
    public String getNif() {
        return this.nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
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
     *             length="20"
     *         
     */
    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="TYPE_DOC"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrTypedoc getScrTypedoc() {
        return this.scrTypedoc;
    }

    public void setScrTypedoc(com.ieci.tecdoc.common.invesicres.ScrTypedoc scrTypedoc) {
        this.scrTypedoc = scrTypedoc;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrPfi) ) return false;
        ScrPfi castOther = (ScrPfi) other;
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
