package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCSRVSTATEDET"
 *     
*/
public class Idocsrvstatedet implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int sessionid;

    /** identifier field */
    private int parentid;

    /** identifier field */
    private String info;

    /** full constructor */
    public Idocsrvstatedet(int id, int sessionid, int parentid, String info) {
        this.id = id;
        this.sessionid = sessionid;
        this.parentid = parentid;
        this.info = info;
    }

    /** default constructor */
    public Idocsrvstatedet() {
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
     *                 column="SESSIONID"
     *                 length="10"
     *             
     */
    public int getSessionid() {
        return this.sessionid;
    }

    public void setSessionid(int sessionid) {
        this.sessionid = sessionid;
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

    /** 
     *                @hibernate.property
     *                 column="INFO"
     *                 length="0"
     *             
     */
    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("sessionid", getSessionid())
            .append("parentid", getParentid())
            .append("info", getInfo())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idocsrvstatedet) ) return false;
        Idocsrvstatedet castOther = (Idocsrvstatedet) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getSessionid(), castOther.getSessionid())
            .append(this.getParentid(), castOther.getParentid())
            .append(this.getInfo(), castOther.getInfo())
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
            .append(getSessionid())
            .append(getParentid())
            .append(getInfo())
            .toHashCode();
    }

}
