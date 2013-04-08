package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCARCHDET"
 *     
*/
public class Idocarchdet implements Serializable {

    /** identifier field */
    private int archid;

    /** identifier field */
    private int dettype;

    /** identifier field */
    private String detval;

    /** full constructor */
    public Idocarchdet(int archid, int dettype, String detval) {
        this.archid = archid;
        this.dettype = dettype;
        this.detval = detval;
    }

    /** default constructor */
    public Idocarchdet() {
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
     *                 column="DETTYPE"
     *                 length="10"
     *             
     */
    public int getDettype() {
        return this.dettype;
    }

    public void setDettype(int dettype) {
        this.dettype = dettype;
    }

    /** 
     *                @hibernate.property
     *                 column="DETVAL"
     *                 length="0"
     *             
     */
    public String getDetval() {
        return this.detval;
    }

    public void setDetval(String detval) {
        this.detval = detval;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("archid", getArchid())
            .append("dettype", getDettype())
            .append("detval", getDetval())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idocarchdet) ) return false;
        Idocarchdet castOther = (Idocarchdet) other;
        return new EqualsBuilder()
            .append(this.getArchid(), castOther.getArchid())
            .append(this.getDettype(), castOther.getDettype())
            .append(this.getDetval(), castOther.getDetval())
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
            .append(getDettype())
            .append(getDetval())
            .toHashCode();
    }

}
