package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IVOLLISTVOL"
 *     
*/
public class Ivollistvol implements Serializable {

    /** identifier field */
    private int listid;

    /** identifier field */
    private int volid;

    /** identifier field */
    private int sortorder;

    /** full constructor */
    public Ivollistvol(int listid, int volid, int sortorder) {
        this.listid = listid;
        this.volid = volid;
        this.sortorder = sortorder;
    }

    /** default constructor */
    public Ivollistvol() {
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

    /** 
     *                @hibernate.property
     *                 column="VOLID"
     *                 length="10"
     *             
     */
    public int getVolid() {
        return this.volid;
    }

    public void setVolid(int volid) {
        this.volid = volid;
    }

    /** 
     *                @hibernate.property
     *                 column="SORTORDER"
     *                 length="10"
     *             
     */
    public int getSortorder() {
        return this.sortorder;
    }

    public void setSortorder(int sortorder) {
        this.sortorder = sortorder;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("listid", getListid())
            .append("volid", getVolid())
            .append("sortorder", getSortorder())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Ivollistvol) ) return false;
        Ivollistvol castOther = (Ivollistvol) other;
        return new EqualsBuilder()
            .append(this.getListid(), castOther.getListid())
            .append(this.getVolid(), castOther.getVolid())
            .append(this.getSortorder(), castOther.getSortorder())
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
            .append(getListid())
            .append(getVolid())
            .append(getSortorder())
            .toHashCode();
    }

}
