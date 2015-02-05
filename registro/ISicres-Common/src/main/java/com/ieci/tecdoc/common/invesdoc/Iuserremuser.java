package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IUSERREMUSER"
 *     
*/
public class Iuserremuser implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private String name;

    /** identifier field */
    private String remarks;

    /** identifier field */
    private Date remdate;

    /** full constructor */
    public Iuserremuser(int id, String name, String remarks, Date remdate) {
        this.id = id;
        this.name = name;
        this.remarks = remarks;
        this.remdate = remdate;
    }

    /** default constructor */
    public Iuserremuser() {
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
     *                 length="32"
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
     *                 column="REMARKS"
     *                 length="254"
     *             
     */
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /** 
     *                @hibernate.property
     *                 column="REMDATE"
     *                 length="7"
     *             
     */
    public Date getRemdate() {
        return this.remdate;
    }

    public void setRemdate(Date remdate) {
        this.remdate = remdate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("name", getName())
            .append("remarks", getRemarks())
            .append("remdate", getRemdate())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Iuserremuser) ) return false;
        Iuserremuser castOther = (Iuserremuser) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getName(), castOther.getName())
            .append(this.getRemarks(), castOther.getRemarks())
            .append(this.getRemdate(), castOther.getRemdate())
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
            .append(getRemarks())
            .append(getRemdate())
            .toHashCode();
    }

}
