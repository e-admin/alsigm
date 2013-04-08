package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IUSEROBJPERM"
 *     
*/
public class Iuserobjperm implements Serializable {

    /** identifier field */
    private int dsttype;

    /** identifier field */
    private int dstid;

    /** identifier field */
    private int objid;

    /** identifier field */
    private int aperm;

    /** full constructor */
    public Iuserobjperm(int dsttype, int dstid, int objid, int aperm) {
        this.dsttype = dsttype;
        this.dstid = dstid;
        this.objid = objid;
        this.aperm = aperm;
    }

    /** default constructor */
    public Iuserobjperm() {
    }

    /** 
     *                @hibernate.property
     *                 column="DSTTYPE"
     *                 length="10"
     *             
     */
    public int getDsttype() {
        return this.dsttype;
    }

    public void setDsttype(int dsttype) {
        this.dsttype = dsttype;
    }

    /** 
     *                @hibernate.property
     *                 column="DSTID"
     *                 length="10"
     *             
     */
    public int getDstid() {
        return this.dstid;
    }

    public void setDstid(int dstid) {
        this.dstid = dstid;
    }

    /** 
     *                @hibernate.property
     *                 column="OBJID"
     *                 length="10"
     *             
     */
    public int getObjid() {
        return this.objid;
    }

    public void setObjid(int objid) {
        this.objid = objid;
    }

    /** 
     *                @hibernate.property
     *                 column="APERM"
     *                 length="10"
     *             
     */
    public int getAperm() {
        return this.aperm;
    }

    public void setAperm(int aperm) {
        this.aperm = aperm;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("dsttype", getDsttype())
            .append("dstid", getDstid())
            .append("objid", getObjid())
            .append("aperm", getAperm())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Iuserobjperm) ) return false;
        Iuserobjperm castOther = (Iuserobjperm) other;
        return new EqualsBuilder()
            .append(this.getDsttype(), castOther.getDsttype())
            .append(this.getDstid(), castOther.getDstid())
            .append(this.getObjid(), castOther.getObjid())
            .append(this.getAperm(), castOther.getAperm())
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
            .append(getDsttype())
            .append(getDstid())
            .append(getObjid())
            .append(getAperm())
            .toHashCode();
    }

}
