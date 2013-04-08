package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_BOOKTYPECONFIG"
 *     
*/
public class ScrBookTypeConfig implements Serializable {

    /** identifier field */
    private int bookType;

    /** identifier field */
    private String options;

    /** full constructor */
    public ScrBookTypeConfig(int bookType, String options) {
        this.bookType = bookType;
        this.options = options;
    }

    /** default constructor */
    public ScrBookTypeConfig() {
    }

    /** 
     *                @hibernate.property
     *                 column="BOOKTYPE"
     *                 length="10"
     *             
     */
    public int getBookType() {
        return this.bookType;
    }

    public void setBookType(int bookType) {
        this.bookType = bookType;
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
            .append("bookType", getBookType())
            .append("options", getOptions())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrBookTypeConfig) ) return false;
        ScrBookTypeConfig castOther = (ScrBookTypeConfig) other;
        return new EqualsBuilder()
            .append(this.getBookType(), castOther.getBookType())
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
            .append(getBookType())
            .append(getOptions())
            .toHashCode();
    }

}
