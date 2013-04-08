package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="TGSS_STATES"
 *     
*/
public class TgssStat implements Serializable {

    /** identifier field */
    private String state;

    /** full constructor */
    public TgssStat(String state) {
        this.state = state;
    }

    /** default constructor */
    public TgssStat() {
    }

    /** 
     *                @hibernate.property
     *                 column="STATE"
     *                 length="32"
     *             
     */
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("state", getState())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TgssStat) ) return false;
        TgssStat castOther = (TgssStat) other;
        return new EqualsBuilder()
            .append(this.getState(), castOther.getState())
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
            .append(getState())
            .toHashCode();
    }

}
