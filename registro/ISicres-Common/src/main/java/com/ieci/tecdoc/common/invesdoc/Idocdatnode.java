package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCDATNODE"
 *     
*/
public class Idocdatnode implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int type;

    /** identifier field */
    private int parentid;

    /** full constructor */
    public Idocdatnode(int id, int type, int parentid) {
        this.id = id;
        this.type = type;
        this.parentid = parentid;
    }

    /** default constructor */
    public Idocdatnode() {
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
     *                 column="TYPE"
     *                 length="10"
     *             
     */
    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /** 
     *                @hibernate.property
     *                 column="PARENTID"
     *                 length="10"
     *             
     */
    public int getParentid() {
        return this.parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("type", getType())
            .append("parentid", getParentid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idocdatnode) ) return false;
        Idocdatnode castOther = (Idocdatnode) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getType(), castOther.getType())
            .append(this.getParentid(), castOther.getParentid())
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
            .append(getType())
            .append(getParentid())
            .toHashCode();
    }

}
