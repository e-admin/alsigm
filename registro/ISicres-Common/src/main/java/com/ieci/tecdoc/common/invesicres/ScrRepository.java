package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_REPOSITORY"
 *     
*/
public class ScrRepository implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int type;

    /** identifier field */
    private String configuration;

    /** full constructor */
    public ScrRepository(int id, int type, String configuration) {
        this.id = id;
        this.type = type;
        this.configuration = configuration;
    }

    /** default constructor */
    public ScrRepository() {
    }

    /** 
     *                @hibernate.property
     *                 column="ID"
     *                 length="10"
     *             
     */
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /** 
     *                @hibernate.property
     *                 column="TYPE"
     *                 length="10"
     *             
     */
    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /** 
     *                @hibernate.property
     *                 column="CONFIGURATION"
     *                 length="0"
     *             
     */
    public String getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("type", getType())
            .append("configuration", getConfiguration())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrRepository) ) return false;
        ScrRepository castOther = (ScrRepository) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getType(), castOther.getType())
            .append(this.getConfiguration(), castOther.getConfiguration())
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
            .append(getType())
            .append(getConfiguration())
            .toHashCode();
    }

}
