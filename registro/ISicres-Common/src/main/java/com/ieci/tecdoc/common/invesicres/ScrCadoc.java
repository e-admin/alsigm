package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_CADOCS"
 *     
*/
public class ScrCadoc implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private int idMatter;

    /** persistent field */
    private String description;

    /** nullable persistent field */
    private Integer mandatory;

    /** full constructor */
    public ScrCadoc(Integer id, int idMatter, String description, Integer mandatory) {
        this.id = id;
        this.idMatter = idMatter;
        this.description = description;
        this.mandatory = mandatory;
    }

    /** default constructor */
    public ScrCadoc() {
    }

    /** minimal constructor */
    public ScrCadoc(Integer id, int idMatter, String description) {
        this.id = id;
        this.idMatter = idMatter;
        this.description = description;
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
     *             column="ID_MATTER"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getIdMatter() {
        return this.idMatter;
    }

    public void setIdMatter(int idMatter) {
        this.idMatter = idMatter;
    }

    /** 
     *            @hibernate.property
     *             column="DESCRIPTION"
     *             length="32"
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
     *             column="MANDATORY"
     *             length="10"
     *         
     */
    public Integer getMandatory() {
        return this.mandatory;
    }

    public void setMandatory(Integer mandatory) {
        this.mandatory = mandatory;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrCadoc) ) return false;
        ScrCadoc castOther = (ScrCadoc) other;
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
