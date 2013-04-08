package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IVOLARCHLIST"
 *     
*/
public class Ivolarchlist implements Serializable {

    /** identifier field */
    private int archid;

    /** identifier field */
    private int listid;

    /** full constructor */
    public Ivolarchlist(int archid, int listid) {
        this.archid = archid;
        this.listid = listid;
    }

    /** default constructor */
    public Ivolarchlist() {
    }

    /** 
     *                @hibernate.property
     *                 column="ARCHID"
     *                 length="10"
     *             
     */
    public int getArchid() {
        return this.archid;
    }

    public void setArchid(int archid) {
        this.archid = archid;
    }

    /** 
     *                @hibernate.property
     *                 column="LISTID"
     *                 length="10"
     *             
     */
    public int getListid() {
        return this.listid;
    }

    public void setListid(int listid) {
        this.listid = listid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("archid", getArchid())
            .append("listid", getListid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Ivolarchlist) ) return false;
        Ivolarchlist castOther = (Ivolarchlist) other;
        return new EqualsBuilder()
            .append(this.getArchid(), castOther.getArchid())
            .append(this.getListid(), castOther.getListid())
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
            .append(getArchid())
            .append(getListid())
            .toHashCode();
    }

}
