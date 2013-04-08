package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_PJUR"
 *     
*/
public class ScrPjur implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String cif;

    /** persistent field */
    private String name;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrTypedoc scrTypedoc;

    /** full constructor */
    public ScrPjur(Integer id, String cif, String name, com.ieci.tecdoc.common.invesicres.ScrTypedoc scrTypedoc) {
        this.id = id;
        this.cif = cif;
        this.name = name;
        this.scrTypedoc = scrTypedoc;
    }

    /** default constructor */
    public ScrPjur() {
    }

    /** minimal constructor */
    public ScrPjur(Integer id, String name, com.ieci.tecdoc.common.invesicres.ScrTypedoc scrTypedoc) {
        this.id = id;
        this.name = name;
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
     *             column="CIF"
     *             length="17"
     *         
     */
    public String getCif() {
        return this.cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    /** 
     *            @hibernate.property
     *             column="NAME"
     *             length="72"
     *             not-null="true"
     *         
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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
        if ( !(other instanceof ScrPjur) ) return false;
        ScrPjur castOther = (ScrPjur) other;
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
