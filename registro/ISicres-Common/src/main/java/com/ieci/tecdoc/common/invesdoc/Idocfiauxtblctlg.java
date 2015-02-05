package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCFIAUXTBLCTLG"
 *     
*/
public class Idocfiauxtblctlg implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private String name;

    /** identifier field */
    private int userid;

    /** identifier field */
    private Date timestamp;

    /** full constructor */
    public Idocfiauxtblctlg(int id, String name, int userid, Date timestamp) {
        this.id = id;
        this.name = name;
        this.userid = userid;
        this.timestamp = timestamp;
    }

    /** default constructor */
    public Idocfiauxtblctlg() {
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
     *                 column="NAME"
     *                 length="16"
     *             
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** 
     *                @hibernate.property
     *                 column="USERID"
     *                 length="10"
     *             
     */
    public int getUserid() {
        return this.userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("name", getName())
            .append("userid", getUserid())
            .append("timestamp", getTimestamp())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idocfiauxtblctlg) ) return false;
        Idocfiauxtblctlg castOther = (Idocfiauxtblctlg) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getName(), castOther.getName())
            .append(this.getUserid(), castOther.getUserid())
            .append(this.getTimestamp(), castOther.getTimestamp())
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
            .append(getName())
            .append(getUserid())
            .append(getTimestamp())
            .toHashCode();
    }

}
