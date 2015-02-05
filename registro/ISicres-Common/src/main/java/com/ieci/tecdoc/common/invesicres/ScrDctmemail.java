package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_DCTMEMAIL"
 *     
*/
public class ScrDctmemail implements Serializable {

    /** identifier field */
    private Integer id;

    /** identifier field */
    private Date timestamp;

    /** identifier field */
    private String address;

    /** full constructor */
    public ScrDctmemail(Integer id, Date timestamp, String address) {
        this.id = id;
        this.timestamp = timestamp;
        this.address = address;
    }

    /** default constructor */
    public ScrDctmemail() {
    }

    /** 
     *                @hibernate.property
     *                 column="ID"
     *                 length="10"
     *             
     */
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 
     *                @hibernate.property
     *                 column="TIMESTAMP"
     *                 length="7"
     *             
     */
    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /** 
     *                @hibernate.property
     *                 column="ADDRESS"
     *                 length="250"
     *             
     */
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("timestamp", getTimestamp())
            .append("address", getAddress())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrDctmemail) ) return false;
        ScrDctmemail castOther = (ScrDctmemail) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getTimestamp(), castOther.getTimestamp())
            .append(this.getAddress(), castOther.getAddress())
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
            .append(getTimestamp())
            .append(getAddress())
            .toHashCode();
    }

}
