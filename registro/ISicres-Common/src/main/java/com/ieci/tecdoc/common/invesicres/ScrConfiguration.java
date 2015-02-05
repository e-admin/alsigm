package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_CONFIGURATION"
 *     
*/
public class ScrConfiguration implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private String version;

    /** identifier field */
    private String options;

    /** full constructor */
    public ScrConfiguration(int id, String version, String options) {
        this.id = id;
        this.version = version;
        this.options = options;
    }

    /** default constructor */
    public ScrConfiguration() {
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
     *                 column="VERSION"
     *                 length="10"
     *             
     */
    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /** 
     *                @hibernate.property
     *                 column="OPTIONS"
     *                 length="0"
     *             
     */
    public String getOptions() {
        return this.options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("version", getVersion())
            .append("options", getOptions())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrConfiguration) ) return false;
        ScrConfiguration castOther = (ScrConfiguration) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getVersion(), castOther.getVersion())
            .append(this.getOptions(), castOther.getOptions())
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
            .append(getVersion())
            .append(getOptions())
            .toHashCode();
    }

}
