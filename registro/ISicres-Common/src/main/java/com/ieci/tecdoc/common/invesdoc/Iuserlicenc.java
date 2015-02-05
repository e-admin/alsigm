package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IUSERLICENCES"
 *     
*/
public class Iuserlicenc implements Serializable {

    /** identifier field */
    private String info;

    /** full constructor */
    public Iuserlicenc(String info) {
        this.info = info;
    }

    /** default constructor */
    public Iuserlicenc() {
    }

    /** 
     *                @hibernate.property
     *                 column="INFO"
     *                 length="0"
     *             
     */
    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("info", getInfo())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Iuserlicenc) ) return false;
        Iuserlicenc castOther = (Iuserlicenc) other;
        return new EqualsBuilder()
            .append(this.getInfo(), castOther.getInfo())
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
            .append(getInfo())
            .toHashCode();
    }

}
