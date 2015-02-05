package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_TYPEADDRESS"
 *     
*/
public class ScrTypeaddress implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String description;

    /** persistent field */
    private String code;

    /** persistent field */
    private Set scrAddrtels;

    /** full constructor */
    public ScrTypeaddress(Integer id, String description, String code, Set scrAddrtels) {
        this.id = id;
        this.description = description;
        this.code = code;
        this.scrAddrtels = scrAddrtels;
    }

    /** default constructor */
    public ScrTypeaddress() {
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
     *             column="DESCRIPTION"
     *             length="50"
     *             not-null="true"
     *         
     */
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /** 
     *            @hibernate.property
     *             column="CODE"
     *             length="2"
     *             not-null="true"
     *         
     */
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="TYPE"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrAddrtel"
     *         
     */
    public Set getScrAddrtels() {
        return this.scrAddrtels;
    }

    public void setScrAddrtels(Set scrAddrtels) {
        this.scrAddrtels = scrAddrtels;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrTypeaddress) ) return false;
        ScrTypeaddress castOther = (ScrTypeaddress) other;
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
