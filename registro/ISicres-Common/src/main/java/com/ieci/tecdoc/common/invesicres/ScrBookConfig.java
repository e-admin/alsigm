package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_BOOKCONFIG"
 *     
*/
public class ScrBookConfig implements Serializable {

    /** identifier field */
    private int bookId;

    /** identifier field */
    private String options;

    /** full constructor */
    public ScrBookConfig(int bookId, String options) {
        this.bookId = bookId;
        this.options = options;
    }

    /** default constructor */
    public ScrBookConfig() {
    }

    /** 
     *                @hibernate.property
     *                 column="BOOKID"
     *                 length="10"
     *             
     */
    public int getBookId() {
        return this.bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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
            .append("bookId", getBookId())
            .append("options", getOptions())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrBookConfig) ) return false;
        ScrBookConfig castOther = (ScrBookConfig) other;
        return new EqualsBuilder()
            .append(this.getBookId(), castOther.getBookId())
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
            .append(getBookId())
            .append(getOptions())
            .toHashCode();
    }

}
