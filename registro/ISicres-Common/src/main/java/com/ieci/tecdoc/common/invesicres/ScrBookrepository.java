package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_BOOKREPOSITORY"
 *     
*/
public class ScrBookrepository implements Serializable {

    /** identifier field */
    private int bookid;

    /** identifier field */
    private String configuration;

    /** full constructor */
    public ScrBookrepository(int bookid, String configuration) {
        this.bookid = bookid;
        this.configuration = configuration;
    }

    /** default constructor */
    public ScrBookrepository() {
    }

    /** 
     *                @hibernate.property
     *                 column="BOOKID"
     *                 length="10"
     *             
     */
    public int getBookid() {
        return this.bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
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
            .append("bookid", getBookid())
            .append("configuration", getConfiguration())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrBookrepository) ) return false;
        ScrBookrepository castOther = (ScrBookrepository) other;
        return new EqualsBuilder()
            .append(this.getBookid(), castOther.getBookid())
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
            .append(getBookid())
            .append(getConfiguration())
            .toHashCode();
    }

}
