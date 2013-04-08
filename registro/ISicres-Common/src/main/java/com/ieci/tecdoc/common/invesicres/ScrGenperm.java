package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_GENPERMS"
 *     
*/
public class ScrGenperm implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int perms;

    /** full constructor */
    public ScrGenperm(int id, int perms) {
        this.id = id;
        this.perms = perms;
    }

    /** default constructor */
    public ScrGenperm() {
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
     *                 column="PERMS"
     *                 length="10"
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
            .append("id", getId())
            .append("perms", getPerms())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrGenperm) ) return false;
        ScrGenperm castOther = (ScrGenperm) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getPerms(), castOther.getPerms())
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
            .append(getPerms())
            .toHashCode();
    }

}
